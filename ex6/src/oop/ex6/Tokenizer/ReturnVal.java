package oop.ex6.Tokenizer;

import oop.ex6.FileParsing.RegEx;
import oop.ex6.codeScopes.Scope;

import java.util.regex.Pattern;

/**
 * a class that represents the parsing of the return value
 */
public class ReturnVal {
	private static final int ONE_LINER = 1;
	private static final int FIRST_CODE_LINE = 0;
	private String returnVal = null;

	public ReturnVal(Scope s) {
		if (s.getScopeCode().size() != ONE_LINER) {
			returnVal = s.getScopeCode().get(FIRST_CODE_LINE);
		}
	}

	/**
	 * checks if the return value is valid
	 * @return - true: valid.
	 */
	public boolean isReturnValValid() {
		return returnVal != null && Pattern.compile(RegEx.rReturn).matcher(returnVal).matches();

	}
}
