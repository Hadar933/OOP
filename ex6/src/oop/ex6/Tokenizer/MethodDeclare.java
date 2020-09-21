package oop.ex6.Tokenizer;

import oop.ex6.FileParsing.RegEx;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a class that represents the parsing of a method declaration void method_name (type_1 param_1, ..., type_n
 * param_n){
 */
public class MethodDeclare {
	private static final int RETURN_VAL_INDEX = 0;
	private static final int METHOD_NAME_INDEX = 1;
	private static final String BAD_NAME = "";

	private final boolean isVoid;
	private final String methodName;
	private final Map<String, String> params = new HashMap<>(); // key = identifier, value = type
	private final String[] declarationAsArray;

	public MethodDeclare(String declaration) {
		this.declarationAsArray = declaration.split(RegEx.rSpace);
		setParams(declaration);
		this.isVoid = setIsVoid();
		this.methodName = setName();
	}

	/**
	 * getter for is void
	 */
	public boolean isVoid() {
		return isVoid;
	}

	/**
	 * getter for the name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * getter for the pararmeters
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * initializes the parameters map
	 */
	private void setParams(String declaration) {
		String[] allParams = declaration.substring(declaration.indexOf("(") + 1, declaration.indexOf(")"))
				.split(",");
		for (String param : allParams) {
			if (param.startsWith(RegEx.rSpace)) {
				int removeFirstSpaceIndex = 1;
				param = param.substring(removeFirstSpaceIndex);
			}
			String[] splitted = param.split(RegEx.rSpace);
			String key = splitted[1];
			String value = splitted[0];
			params.put(key, value);
		}
	}

	/**
	 * checks if the return value is void
	 * @return true if it is.
	 */
	private boolean setIsVoid() {
		return declarationAsArray[RETURN_VAL_INDEX].equals("void");
	}

	/**
	 * @return the name of the method
	 */
	private String setName() {
		String name = declarationAsArray[METHOD_NAME_INDEX];
		Pattern p = Pattern.compile("[^(]+");
		Matcher m = p.matcher(name);
		if (m.find()) {
			return name.substring(m.start(), m.end());
		}
		return BAD_NAME;
	}

	/**
	 * a method that checks if the declaration is valid
	 */
	public boolean isMethodDeclareValid() {
		if (!Pattern.compile(RegEx.rIdentifier).matcher(methodName).matches() &&
			!methodName.equals(BAD_NAME)) { // bad name
			return false;
		}
		for (String type : params.values()) { // bad type for a parameter
			if (!RegEx.TypeArray.contains(type)) {
				return false;
			}
		}
		return true;
	}

}
