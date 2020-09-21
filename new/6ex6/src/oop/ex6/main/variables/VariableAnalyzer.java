/* Package */
package oop.ex6.main.variables;
/* Imports */

import oop.ex6.main.RegexAnalyzer;
import oop.ex6.main.Sjavac;
import oop.ex6.main.scope.ScopeTree;
import oop.ex6.main.utills.CompilingException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Analyzes a line that initializes (and can assign) variables from a given type
 *
 * @author Shimon Malnick , Chana Chadad
 * @see Sjavac
 */
public class VariableAnalyzer {
    private static final String VARIABLES_DELIMITER = ",";
    private static final String ASSIGNMENT_DELIMITER = "=";
    private static final String END_LINE = ";";
    /* Data members that store the information of the variables */
    private String type;
    private String subLine;
    private ScopeTree scope;
    private boolean isFinal;
    private String currentName;

    /**
     * Constructor
     *
     * @param type    the type of variables to analyze
     * @param subLine the remaining part of the line in the file containing this variable declaration, when
     *                remaining means without the type of the variable
     * @param scope   The scope that contains the line of the variables
     * @param isFinal true if the given line contained the final word , false otherwise
     * @throws CompilingException When there is invalidity with the given arguments( bad variable name etc.)
     */
    public VariableAnalyzer(String type, String subLine, ScopeTree scope, boolean isFinal) throws CompilingException {
        this.subLine = subLine;
        this.type = type;
        this.scope = scope;
        this.isFinal = isFinal;
        parseName();
    }

    /*
    Parses the names of the variables that are stored in the subLine , when every two names are divided
    with a "," char
     */
    private void parseName() throws CompilingException {
        Pattern p, last, first;
        String[] allInitials = subLine.split(VARIABLES_DELIMITER);
        if (allInitials.length == 1) {
            first = RegexAnalyzer.VARIABLE_NAME_ONE_VARIABLE;
            handlePattern(first, allInitials[0]);
        } else {
            first = RegexAnalyzer.VARIABLE_NAME_FIRST;
            handlePattern(first, allInitials[0]);
            for (int i = 1; i < allInitials.length - 1; i++) {
                // already dealt with the first(specifically), iterating from the second
                p = RegexAnalyzer.VARIABLE_NAME;
                handlePattern(p, allInitials[i]);
            }
            last = RegexAnalyzer.VARIABLE_NAME_LAST;
            handlePattern(last, allInitials[allInitials.length - 1]);
        }
    }

    /*
    Receives a pattern and a string to match that pattern. throws an exception when not matched, and when
    matched calls handleName method with the name in the string, and calls the handleSuffix method with the
     suffix of the string
     */
    private void handlePattern(Pattern p, String string) throws CompilingException {
        Matcher m;
        m = p.matcher(string);
        if (!m.matches()) {
            throw new CompilingException(Sjavac.ErrorType.INVALID_VARIABLE_NAME);
        } else {
            if ((isFinal) && (!m.group(3).contains(ASSIGNMENT_DELIMITER))) throw new CompilingException
                    (Sjavac.ErrorType
                            .FINAL_NOT_INIT);
            handleName(m.group(2));
            handleSuffix(m.group(3));
        }
    }

    /*
    Validates the name of the variable and throws an exception when the name is already used
     */
    private void handleName(String name) throws CompilingException {
        if (scope.getVariablesNamesList().contains(name)) {
            throw new CompilingException(Sjavac.ErrorType.INVALID_VARIABLE_NAME);
        } else {
            currentName = name;
            scope.setVariable(name, false, type, isFinal);
        }
    }

    /*
    Validates the value of the variable and throws an exception when the value is invalid
     */
    private void handleSuffix(String suffix) throws CompilingException {
        String value = null;
        Pattern p1, p2, start;
        Matcher m1, m2, startMatch;
        start = RegexAnalyzer.FILTER_START;
        startMatch = start.matcher(suffix);
        if (!suffix.startsWith(END_LINE) && !startMatch.matches()) {
            // the second condition is for variable declarations that have not been marked as initialized.
            // the first condition is for a value in the end of a line that have not been initialized.
            if (type.equals("String")) {
                // a special regex for string parsing
                p1 = RegexAnalyzer.STRING_ASSIGNMENT;
                m1 = p1.matcher(suffix);
                if (m1.matches()) {
                    value = m1.group(3);
                }
            }
            p2 = RegexAnalyzer.VARIABLE_ASSIGNMENT;
            m2 = p2.matcher(suffix);
            if (m2.matches()) {
                value = m2.group(3);
            }
            if (value == null) throw new CompilingException(Sjavac.ErrorType.MISSING_ASSIGNMENT);
            scope.legalValue(value, type);
            scope.setVariable(currentName, true, type, isFinal);
        }
    }
}