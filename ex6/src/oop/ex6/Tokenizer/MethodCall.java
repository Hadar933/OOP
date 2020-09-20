package oop.ex6.Tokenizer;

import oop.ex6.FileParsing.RegEx;
import oop.ex6.codeScopes.Scope;

import java.util.regex.Pattern;

/**
 * a class that represents the parsing of a method call
 */
public class MethodCall {
	private static final int ONE_LINER = 1;
	private static final int FIRST_CODE_LINE = 0;

	private String call = null;
	public MethodCall(Scope s){
		if(s.getScopeCode().size()!=ONE_LINER){
			call = s.getScopeCode().get(FIRST_CODE_LINE);
		}
	}

	/**
	 * getter for the method call
	 */
	public String getCall() {
		return call;
	}

	/**
	 * checks if the call is valid
	 * @return - true: valid.
	 */
	public boolean isMethodCallValid(){
		return call!=null && Pattern.compile(RegEx.rMethodCall).matcher(call).matches();
	}
}
