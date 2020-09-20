package oop.ex6.Tokenizer;

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
}
