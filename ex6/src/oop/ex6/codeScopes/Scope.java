package oop.ex6.codeScopes;

import oop.ex6.FileParsing.RegEx;
import oop.ex6.Tokenizer.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * any scope in the sJava file is represented by an instance of this class a scope consists of local variables
 * (either an assignment, declaration, or reference to) a scope might also consist some inner scopes, and an
 * encapsulating scope
 */
public class Scope {

	/* length of a one line code  */
	private final static int ONE_LINER = 1;
	private final static int EMPTY_LINE = 0;
	private final static int FIRST_CODE_LINE = 0;

	private final ArrayList<Scope> innerScopes;
	private Scope outerScope;

	/* if or while, return statement, declaration, variable, etc...*/
	private String scopeType;

	/* all the relevant variables */
	private final ArrayList<Variable> declareVars;
	private final ArrayList<Variable> referVars;
	private final ArrayList<Variable> assignVars;

	/* the code that represents the scope */
	private final ArrayList<String> scopeCode;

	/**
	 * constructor for a scope instance
	 * @param codeLines - the code lines that represent the scope
	 */
	public Scope(ArrayList<String> codeLines) {
		this.scopeCode = codeLines;
		declareVars = new ArrayList<>();
		referVars = new ArrayList<>();
		assignVars = new ArrayList<>();
		innerScopes = new ArrayList<>();
		getInnerScopes();
	}

	/**
	 * a helper method that generate a sub array list from given array list
	 */
	private ArrayList<String> subArrayList(ArrayList<String> data, int startIdx, int endIdx) {
		ArrayList<String> result = new ArrayList<>();
		for (int i = startIdx; i < endIdx; i++) {
			result.add(data.get(i));
		}
		return result;
	}

	/**
	 * a setter for the outer scope
	 */
	public void setOuterScope(Scope outerScope) {
		this.outerScope = outerScope;
	}

	/**
	 * find the inner scopes based on the "{" and "}" brackets
	 */
	private void getInnerScopes() {
		ArrayList<Integer> openBracketIndex = new ArrayList<>();
		ArrayList<Integer> closingBracketIndex = new ArrayList<>();
		for (int i = 0; i < scopeCode.size(); i++) {
			if (scopeCode.get(i).contains("{")) {
				openBracketIndex.add(i);
			}
			if (scopeCode.get(i).contains("}")) {
				closingBracketIndex.add(i);
			}
		}
		if (openBracketIndex.size() == closingBracketIndex.size()) { // valid brackets
			for (int i = 0; i < openBracketIndex.size(); i++) {
				int startIdx = openBracketIndex.get(openBracketIndex.size() - (i + 1));
				int endIdx = closingBracketIndex.get(i);
				ArrayList<String> innerCode = subArrayList(scopeCode, startIdx + 1, endIdx);
				Scope innerScope = new Scope(innerCode);
				if (innerCode.size() == ONE_LINER) { // a variable is one line
					if (Variable.checkDeclareVar(innerScope) != RegEx.TYPE.BAD_FLAG) {
						Variable var = new Variable(innerScope.getScopeCode().get(FIRST_CODE_LINE));
						innerScope.setOuterScope(this);
						declareVars.add(var);
					}
				}
				innerScopes.add(innerScope);
			}
		}
	}

	/**
	 * getter for the scope's code array
	 */
	public ArrayList<String> getScopeCode() {
		return scopeCode;
	}

	/**
	 * updates the scopeType data member
	 */
	private RegEx.SCOPE_TYPE setScopeType(Scope s) {
		ArrayList<String> code = s.getScopeCode();
		if (code.size() == EMPTY_LINE) { // zero lines
			return RegEx.SCOPE_TYPE.EMPTY;
		} else if (code.size() == ONE_LINER) { // one line
			if (Variable.checkDeclareVar(s) != RegEx.TYPE.BAD_FLAG) {
				return RegEx.SCOPE_TYPE.VAR_DECLARE;
			}
			if (Variable.checkAssignVar(s) != RegEx.TYPE.BAD_FLAG) {
				return RegEx.SCOPE_TYPE.VAR_ASSIGN;
			}
			if (new Return(s).isReturnValValid()) {
				return RegEx.SCOPE_TYPE.RETURN;
			}
			if (new MethodDeclare(code.get(FIRST_CODE_LINE)).isMethodDeclareValid()) {
				return RegEx.SCOPE_TYPE.METHOD_DECLARE;
			}
			if (new MethodCall(s).isMethodCallValid()) {
				return RegEx.SCOPE_TYPE.METHOD_CALL;
			}
		}// two or more lines
		if (isIfOrWhile(code)) {
			return RegEx.SCOPE_TYPE.IF_WHILE;
		}

		return RegEx.SCOPE_TYPE.BAD_TYPE;
	}

	/**
	 * checks if scope s is an if or while scope
	 * @param code - some scope code
	 * @return - true: yes, false: no
	 */
	private static boolean isIfOrWhile(ArrayList<String> code) {
		if (code.get(FIRST_CODE_LINE).startsWith("if") || code.get(FIRST_CODE_LINE).startsWith("while")) {
			return new IfWhile(code.get(FIRST_CODE_LINE)).isConditionValid();
		}
		return false;
	}
}

