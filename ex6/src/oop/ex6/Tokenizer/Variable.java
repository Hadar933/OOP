package oop.ex6.Tokenizer;

import oop.ex6.FileParsing.Scope;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a class that represents a line of variable declaration or initialization
 */
public class Variable {
	private final static String EMPTY_LINE = "";
	private final static String END_LINE = ";";

	/* length of a one line code  */
	private final static int ONE_LINER = 1;

	/* these indicate the size of the line */
	private final static int TWO_ARGS = 2; //ex. int x; or x=2;
	private final static int THREE_ARGS = 3; //ex. final int x; or int x = 2;
	private final static int FOUR_ARGS = 4; // ex. final int x = 2;

	/* these are the allowed types */

	/* indicates if the first item is the keyword final */
	private final boolean isFinal;

	/* of the form {type,identifier,value} */
	private final String[] lineArray;
	private final static int TYPE_INDEX = 0;
	private final static int ID_INDEX = 1;
	private final static int VALUE_INDEX = 2;
	private final static int SIZE = 3;


	/* does the type of the value corresponds with the lines title? */
	private final boolean doesValueMatchesType;

	/* types enumerator */;

	private Map<ReGex.TYPE, Pattern> TypeReGex;

	/**
	 * @param line - (final)?(String|char|boolean|int|double)? identifier (=value)?;
	 */
	Variable(String line) {
		this.lineArray = new String[SIZE];
		this.isFinal = line.startsWith("final");
		initializeLineArray(line);
		this.doesValueMatchesType = doesValueMatchesType();

	}

	/**
	 * getter for isFinal
	 */
	public boolean getFinal() {
		return this.isFinal;
	}

	/**
	 * getter for the type
	 */
	public String getType() {
		return lineArray[TYPE_INDEX];
	}

	/**
	 * getter for the identifier
	 */
	public String getIdentifier() {
		return lineArray[ID_INDEX];
	}

	/**
	 * getter for the value
	 */
	public String getValue(){
		return lineArray[VALUE_INDEX];
	}

	/**
	 * getter for the value
	 */
	public boolean getIsLineValid() {
		return doesValueMatchesType;
	}

	/*
	assigns the given values to the lineArray
	 */
	private void assignValuesToArray(String type, String identifier, String value) {
		lineArray[TYPE_INDEX] = type;
		lineArray[ID_INDEX] = identifier;
		lineArray[VALUE_INDEX] = value;
	}

	/**
	 * assigns the type, identifier and value from the given line
	 * @param line - some line
	 */
	private void initializeLineArray(String line) {
		line = line.replaceAll(END_LINE, EMPTY_LINE);
		String[] data = line.split(ReGex.rLine); // assume valid spaces here
		switch (data.length) {
		case TWO_ARGS:
			if (ReGex.TypeArray.contains(data[0])) {
				assignValuesToArray(data[0], data[1], null); // ex. int x;
			} else {
				assignValuesToArray(null, data[0], data[1]); // ex. x = 2;
			}
			break;
		case THREE_ARGS:
			if (isFinal) {
				assignValuesToArray(data[1], data[2], null); // ex. final int x;
			} else {
				assignValuesToArray(data[0], data[1], data[2]); // ex. int x = 2;
			}
			break;
		case FOUR_ARGS:
			if (isFinal) {
				assignValuesToArray(data[1], data[2], data[3]); // ex. final int x = 2;
			}
		}
	}

	/**
	 * checks if the value's type matches the type
	 * @return - true if it does, false otherwise
	 */
	private boolean doesValueMatchesType() {
		String value = getValue();
		if (getType() == null || getValue() == null) {
			return true;
		} // if there isn't a type in the line, it is true vacuously
		Pattern p;
		switch (getType()) {
		case "char":
			p = Pattern.compile(ReGex.rChar);
			return findMatch(p, value);
		case "String":
			p = Pattern.compile(ReGex.rString);
			return findMatch(p, value);
		case "boolean":
			p = Pattern.compile(ReGex.rBoolean); // true false int or double
			return findMatch(p, value);
		case "int":
			p = Pattern.compile(ReGex.rInt);
			return findMatch(p, value);
		case "double":
			p = Pattern.compile(ReGex.rDouble);
			return findMatch(p, value);
		default:
			return false;
		}
	}

	/**
	 * a helper method that checks if a pattern matches some value
	 * @param p - some regex pattern
	 * @param value - a value from the given line
	 * @return - true is matches, false otherwise
	 */
	private boolean findMatch(Pattern p, String value) {
		Matcher m = p.matcher(value);
		if (m.matches()) {
			lineArray[VALUE_INDEX] = value; //assigning given value
			return true;
		}
		return false;
	}

	/**
	 * given some Scope instance, that represents a scope in the sJava code, we parse all the declared
	 * variables
	 * @param s - a Scope
	 * @return - the relevant TYPE (enum)
	 */
	public ReGex.TYPE declareVars(Scope s) {
		int firstLineIndex = 0;
		ArrayList<String> scopeCode = s.getScopeCode();
		if (scopeCode.size() != ONE_LINER) {
			return ReGex.TYPE.BAD_FLAG;
		}
		Variable var = new Variable(scopeCode.get(firstLineIndex));
		String type = var.getType();
		String value = var.getValue();

		if (validDeclare(var) == ReGex.TYPE.BAD_FLAG) {
			return ReGex.TYPE.BAD_FLAG;
		}
		if (ReGex.TypeArray.contains(type)) { // check type
			if (value != null) { // assignment
				if (Pattern.compile(ReGex.rIdentifier).matcher(value).matches()
					&& !value.matches("true|false")) {
					return ReGex.TYPE.REF; // a reference
				}
			}
		} else {
			return ReGex.TYPE.BAD_FLAG;
		}
		return getDeclateType(type, value);
	}

	/**
	 * a helper method that checks for valid type in declaration
	 * @param type - some type to switch on
	 * @param value - a value to check the conent of
	 * @return - given type (enum)
	 */
	private ReGex.TYPE getDeclateType(String type, String value) {
		switch (type) {
		case "int":
			if (value == null || Pattern.compile(ReGex.rInt).matcher(value).matches()) {
				return ReGex.TYPE.INT;
			}
		case "double":
			if (value == null || Pattern.compile(ReGex.rDouble).matcher(value).matches()) {
				return ReGex.TYPE.DOUBLE;
			}
		case "boolean":
			if (value == null || Pattern.compile(ReGex.rBoolean).matcher(value).matches()) {
				return ReGex.TYPE.BOOLEAN;
			}
		case "char":
			if (value == null || Pattern.compile(ReGex.rChar).matcher(value).matches()) {
				return ReGex.TYPE.CHAR;
			}
		case "String":
			if (value == null || Pattern.compile(ReGex.rString).matcher(value).matches()) {
				return ReGex.TYPE.STRING;
			}
		default:
			return ReGex.TYPE.BAD_FLAG;
		}
	}

	/**
	 * a method that checks if a declaration line is valid
	 * @param var - some variable instance (a code line)
	 * @return - a bad flag enum if theres a problem. a good flag enum otherwise.
	 */
	private ReGex.TYPE validDeclare(Variable var) {
		boolean isFinal = var.isFinal;
		String type = var.getType();
		String id = var.getIdentifier();
		String value = var.getValue();
		if (isFinal && value == null) { //when declared a value must be assigned
			return ReGex.TYPE.BAD_FLAG;
		}
		if (type == null) { // bad type
			return ReGex.TYPE.BAD_FLAG;
		}
		Pattern idPattern = Pattern.compile(ReGex.rIdentifier);
		if (!idPattern.matcher(id).matches()) { // bad identifier
			return ReGex.TYPE.BAD_FLAG;
		}
		return ReGex.TYPE.GOOD_FLAG;
	}

	


}
