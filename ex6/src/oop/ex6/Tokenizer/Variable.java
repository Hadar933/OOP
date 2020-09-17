package oop.ex6.Tokenizer;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a class that represents a line of variable declaration or initialization
 */
public class Variable {
	private final static String EMPTY_LINE = "";
	private final static String END_LINE = ";";
	/* these indicate the size of the line */
	private final static int TWO_ARGS = 2; //ex. int x; or x=2;
	private final static int THREE_ARGS = 3; //ex. final int x; or int x = 2;
	private final static int FOUR_ARGS = 4; // ex. final int x = 2;

	/* these are the allowed types */
	private final static List<String> TYPES = Arrays.asList("boolean", "char", "int", "double", "String");

	/* indicates if the first item is the keyword final */
	private final boolean isFinal;

	/* of the form {type,identifier,value} */
	private final String[] lineArray;

	/* does the type of the value corresponds with the lines title? */
	private final boolean doesValueMatchesType;

	/**
	 * @param line - (final)?(String|char|boolean|int|double)? identifier (=value)?;
	 */
	Variable(String line) {
		this.lineArray = new String[3];
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
		return lineArray[0];
	}

	/**
	 * getter for the identifier
	 */

	public String getIdentifier() {
		return lineArray[1];
	}

	/**
	 * getter for the value
	 */

	public String getValue() {
		return lineArray[2];
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
		lineArray[0] = type;
		lineArray[1] = identifier;
		lineArray[2] = value;
	}

	/**
	 * assigns the type, identifier and value from the given line
	 * @param line - some line
	 */
	private void initializeLineArray(String line) {
		line = line.replaceAll(END_LINE,EMPTY_LINE);
		String[] data = line.split("\\s=\\s|\\s"); // assume valid spaces here
		switch (data.length) {
		case TWO_ARGS:
			if (TYPES.contains(data[0])) {
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
		if (getType() == null || getValue()==null) {
			return true;
		} // if there isn't a type in the line, it is true vacuously
		Pattern p;
		switch (getType()) {
		case "char":
			p = Pattern.compile(".");
			return findMatch(p, value);
		case "String":
			p = Pattern.compile(".+");
			return findMatch(p, value);
		case "boolean":
			p = Pattern.compile("true|false|\\d+\\.\\d+"); // true false int or double
			return findMatch(p, value);
		case "int":
			p = Pattern.compile("\\d+");
			return findMatch(p, value);
		case "double":
			p = Pattern.compile("\\d+\\.\\d+");
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
			lineArray[2] = value; //assigning valud value
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		String line = "String x = \"avc\";";
		Variable var = new Variable(line);
		System.out.println("input line --> "+line);
		System.out.println("isFinal --> "+var.getFinal());
		System.out.println("type --> "+var.getType());
		System.out.println("identifier --> "+var.getIdentifier());
		System.out.println("value --> "+var.getValue());
		System.out.println("is value type ok? --> "+var.doesValueMatchesType());
	}


}
