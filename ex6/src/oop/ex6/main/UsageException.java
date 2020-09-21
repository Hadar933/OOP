package oop.ex6.main;

/**
 * exception for usage errors in the code
 */
public class UsageException extends CodeException{

	/**
	 * constructor
	 * @param exceptionMessage informative message
	 */
	public UsageException(String exceptionMessage) { super(exceptionMessage); }
}
