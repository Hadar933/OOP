/* Package */
package oop.ex6.main.scope;
/* Imports */

import oop.ex6.main.RegexAnalyzer;
import oop.ex6.main.Sjavac;
import oop.ex6.main.utills.CompilingException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;

import static oop.ex6.main.RegexAnalyzer.PARAMETERS;

/**
 * Represents a tree (by a recursive definition, further info on http://cgi.csc.liv.ac
 * .uk/~michele/TEACHING/COMP102/2006/5.4.pdf) scope which is a method inside a file. see the class's
 * methods API for info on the file parsing this data structure can handle
 *
 * @author Shimon Malnick, Chana Chadad
 * @see ScopeTree
 * @see Sjavac
 */
public class MethodScope extends ScopeTree {
    /* this enum is for extensibility , when adding different method types in the future */
    public enum METHOD_TYPE {
        VOID
    }

    /* Constants */
    private static final int METHOD_NAME_GROUP = 1;
    private static final int PARAM_NAME_GROUP = 2;
    private static final int PARAM_TYPE_GROUP = 1;
    private static final int PARAMS_GROUP = 2;
    /* Data member for extensibility (future methods added ( */
    private METHOD_TYPE methodType;

    /**
     * Returns true if the given line defines a method, false otherwise
     *
     * @param line a given String representing a line in a file
     * @return true if the given line defines a method, false otherwise
     * @throws CompilingException when the given line declares a method in an illegal way
     */
    public boolean validateMethod(String line) throws CompilingException {
        if (checkVoid(line)) {
            methodType = METHOD_TYPE.VOID;
            return true;
        }
        return false;
    }

    /**
     * Returns this methods' type
     *
     * @return this methods' type
     */
    public METHOD_TYPE getMethodType() {
        return methodType;
    }

    /*
    Returns true if the given line is the beginning of a void method ,false otherwise (throws an exception
    when the line is indeed a void method declaration but it is invalid)
     */
    private boolean checkVoid(String line) throws CompilingException {
        Matcher m;
        m = RegexAnalyzer.VOID.matcher(line);
        if (!m.matches()) return false;
        String methodName = m.group(METHOD_NAME_GROUP);
        if (ScopeTree.getMethodsList().containsKey(methodName)) throw new CompilingException(Sjavac.ErrorType
                .INITIALIZING_2METHODS_SAME_NAME);
        if (!checkMethodName(methodName)) throw new CompilingException(Sjavac.ErrorType.INVALID_METHOD_NAME);
        validateParams(m, methodName);
        return true;
    }

    /*
    Validates the methods' parameters and throws an exception when finds invalidity
     */
    private void validateParams(Matcher m, String methodName) throws CompilingException {
        ScopeTree.getMethodsList().put(methodName, new LinkedList<>());
        String parameters = m.group(PARAMS_GROUP);
        String[] parametersArray = parameters.split(PARAMETERS_DELIMITER);
        if (parametersArray.length <= 1) {
            // only one or none parameters
            if (parametersArray.length == 0) return;
            else {
                m = RegexAnalyzer.SPACES_0.matcher(parametersArray[0]);
                if (m.matches()) return;
            }
        }
        ArrayList<String> parametersNames = new ArrayList<>();
        for (String parameter : parametersArray) {
            boolean isFinal = checkFinal(parameter);
            m = PARAMETERS.matcher(currentSubLine);
            if (!m.matches()) throw new CompilingException(Sjavac.ErrorType.METHOD_PARAMETERS);
            String paramName = m.group(PARAM_NAME_GROUP);
            String paramType = m.group(PARAM_TYPE_GROUP);
            if (parametersNames.contains(paramName)) throw new CompilingException(Sjavac.ErrorType
                    .METHOD_PARAMETERS);
            parametersNames.add(paramName);
            ScopeTree.getMethodsList().get(methodName).add(paramType);
            variablesList.put(paramName, new Variable(paramType, true, isFinal));
        }
    }

    /*
    Returns true if the given string is a valid method name , false otherwise
     */
    private boolean checkMethodName(String methodName) {
        return RegexAnalyzer.METHOD_NAME.matcher(methodName).matches();
    }


}
