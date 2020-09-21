package oop.ex6.FileParsing;

import java.util.Arrays;
import java.util.List;

/**
 * this class consists all the relevant regexes and relevant keywords we name each regex with an r prefix for
 * shorter use
 */
public class RegEx {
	public static final List<String> TypeArray = Arrays.asList("boolean", "char", "int", "double", "String");

	/* represents the type of some variable as enum */
	public enum TYPE {
		BAD_FLAG, GOOD_FLAG, REF, BOOLEAN, INT, DOUBLE, STRING, CHAR
	}

	/* represents the type of some code scope as enum */
	public enum SCOPE_TYPE {
		BAD_TYPE, METHOD_CALL, METHOD_DECLARE, RETURN, EMPTY, VAR_DECLARE, VAR_ASSIGN, IF_WHILE
	}

	// Variable related RegEx:
	public static final String rSpace = " ";
	public static final String rLine = "\\s=\\s|\\s";
	public static final String rString = "\"\\w*\"";
	public static final String rChar = "'.'";
	public static final String rInt = "-?\\d+";
	public static final String rDouble = "-?\\d+(\\.\\d+)?";
	public static final String rBoolean = "true|false|" + rInt + "|" + rDouble;
	public static final String rIdentifier = "_*[a-zA-Z]*\\w*";

	// Scope type related regex
	public static final String rMethodCall = "\\s*\\w+\\(\\w*\\);\\s*";
	public static final String rReturn = "\\s*return;";
}

