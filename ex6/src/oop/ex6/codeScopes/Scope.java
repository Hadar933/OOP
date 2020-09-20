package oop.ex6.codeScopes;

import oop.ex6.FileParsing.RegEx;
import oop.ex6.Tokenizer.Variable;

import java.util.ArrayList;

/**
 * any scope in the sJava file is represented by an instance of this class a scope consists of local variables
 * (either an assignment, declaration, or reference to) a scope might also consist some inner scopes, and an
 * encapsulating scope
 */
public class Scope {

	/* length of a one line code  */
	private final static int ONE_LINER = 1;
	private final static int FIRST_CODE_LINE = 0;

	private final ArrayList<Scope> innerScopes;
	private Scope outerScope;

	private final ArrayList<Variable> declareVars;
	private final ArrayList<Variable> referVars;
	private final ArrayList<Variable> assignVars;

	private final ArrayList<String> scopeCode;

	public Scope(ArrayList<String> codeLines) {
		this.scopeCode = codeLines;
		declareVars = new ArrayList<>();
		referVars = new ArrayList<>();
		assignVars = new ArrayList<>();
		innerScopes = new ArrayList<>();
		getInnerScopes();
	}

	/*
	a helper method that generate a sub array list from given array list
	 */
	private ArrayList<String> subArrayList(ArrayList<String> data, int startIdx, int endIdx) {
		ArrayList<String> result = new ArrayList<>();
		for (int i = startIdx; i < endIdx; i++) {
			result.add(data.get(i));
		}
		return result;
	}

	/**
	a setter for the outer scope
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
				int startIdx = openBracketIndex.get(openBracketIndex.size()-(i+1));
				int endIdx = closingBracketIndex.get(i);
				ArrayList<String> innerCode = subArrayList(scopeCode,startIdx+1,endIdx);
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

}
