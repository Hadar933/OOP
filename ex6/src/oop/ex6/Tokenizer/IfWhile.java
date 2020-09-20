package oop.ex6.Tokenizer;

import oop.ex6.FileParsing.RegEx;

import java.util.regex.Pattern;

/**
 * a class that represents the parsing of an if or while conditions
 */
public class IfWhile {
	private final String condition;
	public IfWhile(String ifWhileCode){
		this.condition = ifWhileCode.substring(ifWhileCode.indexOf("(")+1,ifWhileCode.indexOf(")"));
	}

	/**
	 * a getter for the condition
	 * @return - a string that represents the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * checks if the condition is valid
	 * @return - true: valid.
	 */
	public boolean isConditionValid(){
		return Pattern.compile(RegEx.rIfWhile).matcher(condition).matches();
	}

}
