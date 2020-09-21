package oop.ex6.main;

/**
 * exception for syntax problems in sjava file
 */
public class SyntaxException extends CodeException{

	/**
	 * constructor
	 * @param exceptionMessage informative exception message
	 */
	public SyntaxException(String exceptionMessage) { super(exceptionMessage); }
}
