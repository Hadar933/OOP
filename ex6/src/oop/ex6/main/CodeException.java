package oop.ex6.main;

/**
 * exception class for code problems in the sjava file
 */
public class CodeException extends Exception{

	private String message;

	/**
	 * constructor
	 * @param exceptionMessage informative message for the exception
	 */
	public CodeException(String exceptionMessage) { message = exceptionMessage; }

	@Override
	public String toString(){ return message; }
}
