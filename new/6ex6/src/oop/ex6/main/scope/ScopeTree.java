/* Package */
package oop.ex6.main.scope;
/* Imports */

import oop.ex6.main.RegexAnalyzer;
import oop.ex6.main.Sjavac;
import oop.ex6.main.utills.CompilingException;
import oop.ex6.main.variables.VariableAnalyzer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * * Represents a tree (by a recursive definition, further info on http://cgi.csc.liv.ac
 * .uk/~michele/TEACHING/COMP102/2006/5.4.pdf) scope inside a file. see the class's
 * methods API for info on the file parsing this data structure can handle
 *
 * @author Shimon Malnick, Chana Chadad
 * @see Sjavac
 */
public class ScopeTree {


    /**
     * used for determining whether an inner scope is an if or while scope to make the exception more
     * informative in case of an error
     */
    public enum Conditionals {
        IF, WHILE
    }

    /* private data members */
    private static HashMap<String, LinkedList<String>> methodsList = new HashMap<>();
    private ScopeTree parent;
    private ArrayList<ScopeTree> children;
    private ArrayList<String> lines;
    private String currentPrefix;
    /* Constants */
    protected static final String PARAMETERS_DELIMITER = ",";
    private static final int CONDITION_GROUP = 1;
    private static final int PARAMETERS_GROUP = 2;
    private static final int FINAL_GROUP = 1;
    private static final int NAME_GROUP = 2;
    private static final int VALUE_GROUP = 3;
    private static final String AND_CONDITION = "&&";
    private static final String OR_CONDITION = "\\|\\|";
    private static final String COMMENT = "//";

    /* protected data members */
    protected String currentSubLine;
    protected HashMap<String, Variable> variablesList;

    /**
     * Constructor
     */
    public ScopeTree() {
        lines = new ArrayList<>();
        children = new ArrayList<>();
        currentSubLine = "";
        currentPrefix = "";
        variablesList = new HashMap<>();
    }

    /**
     * Represents a variable in this scope
     */
    protected class Variable {
        /* Constants */
        private static final int VALUE_GROUP = 4;
        public static final int NAME_GROUP = 1;
        /**
         * Data members
         **/
        protected String type;
        protected boolean isInitialized, isFinal;

        /**
         * Constructor
         *
         * @param type          the type of the variable
         * @param isInitialized true if the variable has been initialized, false otherwise
         * @param isFinal       true if the variable is final, false otherwise
         */
        protected Variable(String type, boolean isInitialized, boolean isFinal) {
            this.isFinal = isFinal;
            this.type = type;
            this.isInitialized = isInitialized;
        }
    }

    /**
     * @return the methods list of ScopeTree
     */
    public static HashMap<String, LinkedList<String>> getMethodsList() {
        return methodsList;
    }

    /**
     * Sets ScopeTree methods list to the given hash map
     *
     * @param methodsList the given methods list has map
     */
    public static void setMethodsList(HashMap<String, LinkedList<String>> methodsList) {
        ScopeTree.methodsList = methodsList;
    }

    /**
     * Resets the scopes lines
     */
    public void resetLines() {
        lines = new ArrayList<>();
    }

    /**
     * @return The parent of this scope
     */
    public ScopeTree getParent() {
        return parent;
    }


    /**
     * Adds the given line to the lines in this scope
     *
     * @param line the given line
     */
    public void addLine(String line) {
        lines.add(line);
    }

    /**
     * Sets the given scope as a parent to this scope
     *
     * @param parent the given parent ScopeTree
     */
    public void setParent(ScopeTree parent) {
        this.parent = parent;
    }

    /**
     * Sets the given scope as a child of this scope
     *
     * @param son the given son ScopeTree
     */
    public void setChild(ScopeTree son) {
        children.add(son);
    }

    /**
     * @return the children scopes ArrayList
     */
    public ArrayList<ScopeTree> getChildren() {
        return children;
    }

    /**
     * @param i the given index
     * @return the line at index i
     */
    public String getLine(int i) {
        return lines.get(i);
    }

    /**
     * @return the variables names list
     */
    public Set<String> getVariablesNamesList() {
        return variablesList.keySet();
    }

    /**
     * Sets a new variable in this scope
     *
     * @param name          the variable name
     * @param isInitialized true if the variable has been initialized, false otherwise
     * @param type          the variables' type
     * @param isFinal       true if the variable is final, false otherwise
     */
    public void setVariable(String name, boolean isInitialized, String type, boolean isFinal) {
        variablesList.put(name, new Variable(type, isInitialized, isFinal));
    }

    /**
     * Parses a given line in the scope
     *
     * @param line the given line
     * @throws CompilingException when the line is invalid
     */
    public void parsePrefix(String line) throws CompilingException {
        if (parseVariableInitial(line)) return;
        if (!checkComments(currentSubLine) && !checkConditionals(currentSubLine) && !checkVariables
                (currentSubLine) && !checkReturn(currentSubLine) && !checkMethodCall(currentSubLine)) {
            throw new CompilingException(Sjavac.ErrorType.BAD_LINE);
        }
    }

    /*
    Returns true if the given line calls a method 
     */
    private boolean checkMethodCall(String line) throws CompilingException {
        if (findAncestorMethod() == null) return false;
        Matcher m = RegexAnalyzer.METHOD_CALL.matcher(line);
        if (!m.matches()) return false;
        String methodName = m.group(NAME_GROUP - 1);
        if (!ScopeTree.getMethodsList().keySet().contains(methodName)) throw new CompilingException(Sjavac
                .ErrorType.CALLING_UNKNOWN_METHOD);
        String parameters = m.group(PARAMETERS_GROUP);
        String[] parametersArray = parameters.split(PARAMETERS_DELIMITER);
        if (parametersArray.length == 1) {
            // parametersArray size is 1
            m = RegexAnalyzer.SPACES_0.matcher(parametersArray[0]);
            if (m.matches()) {
                if (ScopeTree.getMethodsList().get(methodName).size() != 0) throw new CompilingException
                        (Sjavac.ErrorType.METHOD_PARAMETERS);
                return true;
            }
        }
        if (!(parametersArray.length == ScopeTree.getMethodsList().get(methodName).size())) throw new
                CompilingException(Sjavac.ErrorType.METHOD_PARAMETERS);
        for (int i = 0; i < parametersArray.length; i++) {
            String param = parametersArray[i].trim();
            String currentType = ScopeTree.getMethodsList().get(methodName).get(i);
            legalValue(param, currentType);
        }
        return true;
    }

    /**
     * Returns true if the given line is a valid return statement, false otherwise
     *
     * @param line the given line
     * @return true if the given line is a valid return statement, false otherwise
     */
    public boolean checkReturn(String line) {
        Matcher m = null;
        MethodScope ancestorMethod = findAncestorMethod();
        if (ancestorMethod == null) return false;
        if (ancestorMethod.getMethodType().equals(MethodScope.METHOD_TYPE.VOID))
            m = RegexAnalyzer.RETURN.matcher(line);
        return m.matches();
    }

    /*
    Returns the method that is linked to this scope, or null if this scope is the main global scope
     */
    private MethodScope findAncestorMethod() {
        if (parent == null) return null;
        ScopeTree currentScope = this;
        while (true) {
            // true because our ancestors definitely link to a node that is a method
            // meaning my parent is a method
            if (currentScope.parent.parent == null) return (MethodScope) (currentScope);
            currentScope = currentScope.parent;
        }
    }

    /*
    Returns true if the given line initializes a variable , false otherwise
     */
    private boolean parseVariableInitial(String line) throws CompilingException {
        currentSubLine = line;
        boolean isFinal = checkFinal(currentSubLine);
        if (checkInitializingWords(currentSubLine)) {
            new VariableAnalyzer(currentPrefix, currentSubLine, this, isFinal);
            return true;
        }
        return false;
    }

    /*
     * Returns true if the given line contains a variable declaration ( and assignment when there is one)
     * and false otherwise
     * @param line the given line
     * @throws CompilingException when the line is indeed a variable declaration but an invalid one
     */
    private boolean checkVariables(String line) throws CompilingException {
        Matcher m = RegexAnalyzer.VARIABLE.matcher(line);
        if (!m.matches()) return false;
        Variable var = checkVariableName(m.group(Variable.NAME_GROUP));
        // note: the groups are assigned in the RegexAnalyzer regexes;
        legalValue(m.group(Variable.VALUE_GROUP), var.type);
        if (variablesList.containsValue(var)) var.isInitialized = true;
        return true;
    }

    /*
    Returns a variable with the given name if found, if not throws an exception
     */
    private Variable checkVariableName(String name) throws CompilingException {
        ScopeTree currentNode = this;
        while (currentNode != null) {
            if (currentNode.getVariablesNamesList().contains(name)) {
                Variable variable = currentNode.variablesList.get(name);
                if (variable.isFinal) throw new CompilingException(Sjavac.ErrorType.FINAL_ASSIGNMENT);
                if ((!variable.isInitialized) && currentNode.parent != null) variable.isInitialized = true;
                return variable;
            } else {
                currentNode = currentNode.parent;
            }
        }
        throw new CompilingException(Sjavac.ErrorType.INVALID_VARIABLE);
    }

    /**
     * Returns true if the current line contains the final token, false otherwise
     *
     * @param line the given line
     * @return true if the current line contains the final token, false otherwise
     */
    protected boolean checkFinal(String line) {
        Matcher m = RegexAnalyzer.FINAL.matcher(line);
        if (m.matches()) {
            currentSubLine = line.substring(m.group(FINAL_GROUP).length());
            return true;
        }
        currentSubLine = line;
        return false;
    }

    /**
     * Returns the ArrayList containing the lines of this scope
     *
     * @return the ArrayList containing the lines of this scope
     */
    public ArrayList<String> getLines() {
        return lines;
    }

    /*
     Returns true if the given line initializes a variable
      */
    private boolean checkInitializingWords(String line) {
        Matcher m;
        m = RegexAnalyzer.INITIALIZING.matcher(line);
        if (m.matches()) {
            currentPrefix = m.group(NAME_GROUP);
            currentSubLine = m.group(VALUE_GROUP);
            return true;
        }
        return false;
    }

    /*
    Checks whether the given line is a comment line (starts with two //)
     */
    private boolean checkComments(String line) {
        return line.startsWith(COMMENT);
    }

    /*
    Checks whether the given line is the beginning of a loop
     */
    private boolean checkConditionals(String line) throws CompilingException {
        return checkConditional(line, Conditionals.IF) || checkConditional(line, Conditionals.WHILE);
    }

    /*
    Returns true if the current line is a conditional line is a valid conditional(if or while ) and false
    otherwise , throws an exception when the line contains an invalid conditional
     */
    private boolean checkConditional(String line, Conditionals conditionals) throws CompilingException {
        if (ifOrWhile(line, conditionals)) return false;
        Pattern p1;
        Pattern p2;
        ArrayList<String> allConditions = breakIn2Conditions();
        if (allConditions.size() == 1) {
            // only one condition
            p1 = RegexAnalyzer.CONDITIONAL_1_VARIABLE;
            p2 = RegexAnalyzer.CONDITIONAL_1_LITERAL;
            return checkCondition(allConditions, p1, p2, 0, conditionals);
        }
        p1 = RegexAnalyzer.CONDITIONAL_FIRST_VARIABLE;
        p2 = RegexAnalyzer.CONDITIONAL_FIRST_LITERAL;
        checkCondition(allConditions, p1, p2, 0, conditionals);
        for (int i = 1; i < allConditions.size() - 1; i++) {
            p1 = RegexAnalyzer.CONDITIONAL_VARIABLE;
            p2 = RegexAnalyzer.CONDITIONAL_LITERAL;
            checkCondition(allConditions, p1, p2, i, conditionals);
        }
        p1 = RegexAnalyzer.CONDITIONAL_LAST_VARIABLE;
        p2 = RegexAnalyzer.CONDITIONAL_LAST_LITERAL;
        return checkCondition(allConditions, p1, p2, allConditions.size() - 1, conditionals);
    }

    /*
    Returns an ArrayList containing all the conditions within the currentSubline
     */
    private ArrayList<String> breakIn2Conditions() {
        ArrayList<String> allConditions = new ArrayList<>();
        String[] conditions = currentSubLine.split(AND_CONDITION);
        for (String condition : conditions) {
            String[] orConditions = condition.split(OR_CONDITION);
            allConditions.addAll(Arrays.asList(orConditions));
        }
        return allConditions;
    }

    /*
    Returns false if the given line is a an if or while statement , true otherwise
     */
    private boolean ifOrWhile(String line, Conditionals conditionals) {
        Pattern p;
        if (conditionals.equals(Conditionals.IF))
            p = RegexAnalyzer.IF;
        else p = RegexAnalyzer.WHILE;
        Matcher m = p.matcher(line);
        if (!m.matches()) return true;
        currentSubLine = m.group(CONDITION_GROUP);
        return false;
    }

    /*
    Returns true if the given condition is valid, false otherwise
     */
    private boolean checkCondition(ArrayList<String> allConditions, Pattern p1, Pattern p2, int idx, Conditionals
            conditionals) throws CompilingException {
        Matcher m = p1.matcher(allConditions.get(idx));
        if (!m.matches()) return false;
        if (!isVariableActive(m.group(CONDITION_GROUP))) {
            m = p2.matcher(allConditions.get(idx));
            if (!m.matches()) {
                if (conditionals.equals(Conditionals.IF)) throw new CompilingException(Sjavac.ErrorType.IF);
                else {
                    throw new CompilingException(Sjavac.ErrorType.WHILE);
                }
            }
        }
        return true;
    }

    /*
    Returns true if the given variable is active, false otherwise
     */
    private boolean isVariableActive(String condition) throws CompilingException {
        Matcher m = RegexAnalyzer.ACTIVE_VARIABLE.matcher(condition);
        if (!m.matches()) throw new CompilingException(Sjavac.ErrorType.ILLEGAL_LOOP_CONDITION);
        condition = m.group(CONDITION_GROUP);
        ScopeTree currentScope = this;
        if (searchVariableInCondition(condition, currentScope)) return true;
        while (currentScope != null) {
            // checking in all the global scopes variables
            if (searchVariableInCondition(condition, currentScope)) return true;
            currentScope = currentScope.parent;
        }
        return false;
    }

    /*
    Returns true if the given condition contains a variable from this scope, false otherwise
     */
    private boolean searchVariableInCondition(String condition, ScopeTree currentScope) {
        if (currentScope.variablesList.containsKey(condition)) {
            Variable variable = currentScope.variablesList.get(condition);
            return (variable.isInitialized) && !(variable.type.equals("String")) && !(variable.type.equals
                    ("char"));
        }
        return false;
    }

    /**
     * Parses the scope lines and throws an exception when finds invalidity
     *
     * @throws CompilingException when there is an invalid line in the file
     */
    public void analyze() throws CompilingException {
        for (String line : lines) parsePrefix(line);
    }

    /**
     * validates that the given value and type of a variable are valid, and throws an exception when it is not
     *
     * @param value the given value of the variable
     * @param type  the type of the variable
     * @throws CompilingException when the given value is invalid
     */
    public void legalValue(String value, String type) throws CompilingException {
        Variable var = nameExists(value);
        if (var != null) {
            if (!var.isInitialized || !matchType(type, var)) {
                throw new CompilingException(Sjavac.ErrorType.METHOD_PARAMETERS);
            }
            return;
        }
        switch (type) {
            case "int":
                try {
                    Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    throw new CompilingException(Sjavac.ErrorType.TYPE_NOT_MATCHING_VALUE);
                }
                break;
            case "String":
                Matcher m;
                m = RegexAnalyzer.STRING.matcher(value);
                if (!m.matches()) throw new CompilingException(Sjavac.ErrorType.TYPE_NOT_MATCHING_VALUE);
                break;
            case "double":
                try {
                    m = RegexAnalyzer.DOUBLE.matcher(value);
                    if (!m.matches()) throw new CompilingException(Sjavac.ErrorType.TYPE_NOT_MATCHING_VALUE);
                    Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    throw new CompilingException(Sjavac.ErrorType.TYPE_NOT_MATCHING_VALUE);
                }
                break;
            case "char":
                m = RegexAnalyzer.CHAR.matcher(value);
                if (!m.matches()) throw new CompilingException(Sjavac.ErrorType.TYPE_NOT_MATCHING_VALUE);
                break;
            case "boolean":
                m = RegexAnalyzer.BOOL.matcher(value);
                if (!m.matches()) throw new CompilingException(Sjavac.ErrorType.TYPE_NOT_MATCHING_VALUE);
                break;
            default:
                throw new CompilingException(Sjavac.ErrorType.TYPE_NOT_MATCHING_VALUE);
        }
    }

    /*
    Returns true if the type of the variable matches the given type( match meaning valid, e.g a double
    assigned an int value) and false otherwise
     */
    private boolean matchType(String type, Variable var) {
        switch (type) {
            case "double":
                return var.type.equals("double") || var.type.equals("int");
            case "boolean":
                return var.type.equals("int") || var.type.equals("double") || var.type.equals("boolean");
            default:
                return var.type.equals(type);
        }
    }

    /*
    Returns a Variable with the given name if it exists , null otherwise
     */
    private Variable nameExists(String name) {
        ScopeTree currentNode = this;
        while (currentNode != null) {
            if (currentNode.getVariablesNamesList().contains(name)) {
                return currentNode.variablesList.get(name);
            } else {
                currentNode = currentNode.parent;
            }
        }
        return null;
    }
}
