package oop.ex6.Tokenizer;

import oop.ex6.FileParsing.RegEx;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * a class that represents the parsing of a method declaration
 * void method_name (type_1 param_1, ..., type_n param_n){
 */
public class MethodDeclare {
	private static final int RETURN_VAL_INDEX = 0;
	private static final int METHOD_NAME_INDEX = 1;
	private static final int PARAMS_INDEX = 2;
	private static final String BAD_NAME = "";


	private final boolean isVoid;
	private final String methodName;
	private final String[] params;
	private final String[] declarationAsArray;

	public MethodDeclare(String declaration){
		this.declarationAsArray = declaration.split(RegEx.rSpace);
		this.params = setParams(declaration);
		this.isVoid = setIsVoid();
		this.methodName = setName();


	}

	/**
	 * @return - the parameters of the declaration
	 */
	private String[] setParams(String declaration) {
		return declaration.substring(declaration.indexOf("(")+1,declaration.indexOf(")")).split(",");

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
		String name = declarationAsArray[METHOD_NAME_INDEX]; //foo(int
		Pattern p = Pattern.compile("[^(]+");
		Matcher m = p.matcher(name);
		if(m.find()){
			return name.substring(m.start(),m.end());
		}
		return BAD_NAME;
	}

	public static void main(String[] a){
		MethodDeclare md = new MethodDeclare("void foo(int x, int y) {");
	}
}
