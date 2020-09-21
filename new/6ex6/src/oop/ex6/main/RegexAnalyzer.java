/* Package */
package oop.ex6.main;
/* Imports */

import java.util.regex.Pattern;

/**
 * Contains static compiled regex Patterns
 *
 * @author Shimon Malnick, Chana Chadad
 */
public final class RegexAnalyzer {
    public static final Pattern SPACES_0 = Pattern.compile("\\s*");
    public static final Pattern CLOSE_SCOPE = Pattern.compile("\\s*\\}\\s*");
    public static final Pattern OPEN_SCOPE = Pattern.compile("[^\\{]*\\{\\s*");
    public static final Pattern OPEN_METHOD = Pattern.compile("\\s*void\\s+([^\\s\\(]+)\\s*\\(([^\\)" +
            "]*)\\)\\s*\\{\\s*");
    public static final Pattern VARIABLE_NAME_ONE_VARIABLE = Pattern.compile("(\\s+)" +
            "([A-Za-z][A-Za-z_\\d]*|[_][A-Za-z_\\d]+)[\\s]*(;\\s*|\\s*=\\s*[^;]+;\\s*)");
    public static final Pattern VARIABLE_NAME_FIRST = Pattern.compile("(\\s+)" +
            "([A-Za-z][A-Za-z_\\d]*|[_][A-Za-z_\\d]+)(\\s*|\\s*=\\s*[^;]+)");
    public static final Pattern VARIABLE_NAME = Pattern.compile("(\\s*)" +
            "([A-Za-z][A-Za-z_\\d]*|[_][A-Za-z_\\d]+)" +
            "(\\s*|\\s*=\\s*[^;]+)");
    public static final Pattern VARIABLE_NAME_LAST = Pattern.compile("(\\s*)" +
            "([A-Za-z][A-Za-z_\\d]*|[_][A-Za-z_\\d]+\\s*)\\s*(;\\s*|\\s*=\\s*[^;]+;\\s*)");
    public static final Pattern STRING_ASSIGNMENT = Pattern.compile("(\\s*)(=\\s*)(\"[^\"]*\")(\\s*;" +
            "\\s*|\\s*)");
    public static final Pattern VARIABLE_ASSIGNMENT = Pattern.compile("(\\s*)(=\\s*)([^\\s;]+)(\\s*;" +
            "\\s*|\\s*)");
    public static final Pattern FILTER_START = Pattern.compile("\\s*^$");
    public static final Pattern VOID = Pattern.compile("\\s*void\\s+([^\\s\\(]+)\\s*\\(([^\\)]*)\\)" +
            "\\s*\\{\\s*");
    public static final Pattern PARAMETERS = Pattern.compile("\\s*(int|String|double|boolean|char)\\s+" +
            "([A-Za-z][A-Za-z_\\d]*|[_][A-Za-z_\\d]+)\\s*");
    public static final Pattern METHOD_NAME = Pattern.compile("[A-Za-z]+[A-Za-z_\\d]*");
    public static final Pattern BOOL = Pattern.compile("true|false|-?[\\d]+|-?[\\d]+\\.[\\d]+");
    public static final Pattern CHAR = Pattern.compile("[\'].?[\']+[\\s]*");
    public static final Pattern DOUBLE = Pattern.compile("-?[\\d]+|-?[\\d]+\\.[\\d]+");
    public static final Pattern STRING = Pattern.compile("[\"].*[\"]+[\\s]*");
    public static final Pattern IF = Pattern.compile("[\\s]*if[\\s]*(\\([^\\)]+\\))[\\s]*\\{[\\s]*");
    public static final Pattern WHILE = Pattern.compile("[\\s]*while[\\s]*(\\([^\\)]+\\))[\\s]*\\{[\\s]*");
    public static final Pattern ACTIVE_VARIABLE = Pattern.compile("\\s*([^\\s]+)\\s*");
    public static final Pattern INITIALIZING = Pattern.compile("(\\s*)(int|String|double|boolean|char)(.*)");
    public static final Pattern FINAL = Pattern.compile("([\\s]*final[\\s]+)(.*)");
    public static final Pattern VARIABLE = Pattern.compile("\\s*([A-Za-z][A-Za-z_\\d]*|[_][A-Za-z_\\d]+)" +
            "(\\s*)(=\\s*)([^\\s;]+)(\\s*;\\s*)");
    public static final Pattern RETURN = Pattern.compile("[\\s]*return[\\s]*;[\\s]*");
    public static final Pattern METHOD_CALL = Pattern.compile("[\\s]*([^\\s\\(]+)[\\s]*\\(([^\\)]*)\\)" +
            "[\\s]*;");
    public static final Pattern CONDITIONAL_1_VARIABLE = Pattern.compile("\\(([^\\)]+)\\)");
    public static final Pattern CONDITIONAL_1_LITERAL = Pattern.compile("\\([\\s]*" +
            "(true|false|-?[\\d]+|-?[\\d]+\\.[\\d]+)[\\s]*\\)");
    public static final Pattern CONDITIONAL_FIRST_VARIABLE = Pattern.compile("\\(([^\\)]*)");
    public static final Pattern CONDITIONAL_FIRST_LITERAL = Pattern.compile("\\([\\s]*" +
            "(true|false|-?[\\d]+|-?[\\d]+\\.[\\d]+)[\\s]*");
    public static final Pattern CONDITIONAL_VARIABLE = Pattern.compile("([^\\)]*)");
    public static final Pattern CONDITIONAL_LITERAL = Pattern.compile("[\\s]*" +
            "(true|false|-?[\\d]+|-?[\\d]+\\.[\\d]+)[\\s]*");
    public static final Pattern CONDITIONAL_LAST_VARIABLE = Pattern.compile("([^\\)]+)\\)");
    public static final Pattern CONDITIONAL_LAST_LITERAL = Pattern.compile("[\\s]*" +
            "(true|false|-?[\\d]+|-?[\\d]+\\.[\\d]+)[\\s]*\\)");
}
