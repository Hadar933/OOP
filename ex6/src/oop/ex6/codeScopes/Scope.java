package oop.ex6.codeScopes;

import oop.ex6.FileParsing.RegEx;
import oop.ex6.Tokenizer.*;

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
	private RegEx.SCOPE_TYPE scopeType;

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
		findInnerScopes();
	}

	/**
	 * a helper method that generate a sub array list from given array list
	 */
	private static ArrayList<String> subArrayList(ArrayList<String> data, int startIdx, int endIdx) {
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
	private void findInnerScopes() {
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
	getter for the referenced vars
	 */
	public ArrayList<Variable> getReferVars() {
		return referVars;
	}

	/**
	 * find all the referenced variables from the given variables array
	 * @param vars - some variable array
	 */
	public void findReferenceVars(ArrayList<Variable> vars){
		for(Variable var: vars){
			String value = var.getValue();
			if(var.getValue()!=null){
				if(value.matches(RegEx.rIdentifier)){
					if(!value.matches("true|false")){
						referVars.add(var);
					}
				}
			}
		}
	}

	/**
	getter for the declared variable array
	 */
	public ArrayList<Variable> getDeclareVars() {
		return declareVars;
	}

	/**
	 getter for the assigned variable array
	 */
	public ArrayList<Variable> getAssignVars() {
		return assignVars;
	}

	/**
	 * getter for the scope's code array
	 */
	public ArrayList<String> getScopeCode() {
		return scopeCode;
	}

	/**
	a setter for the scope's type (empty line, method declaration, if, while, etc...)
	 */
	public void setScopeType(RegEx.SCOPE_TYPE scopeType) {
		this.scopeType = scopeType;
	}

	/**
	getter for outer scope
	 */
	public Scope getOuterScope() {
		return outerScope;
	}

	/**
	getter for the type
	 */
	public RegEx.SCOPE_TYPE getScopeType() {
		return scopeType;
	}

	/**
	getter for the inner scopes
	 */
	public ArrayList<Scope> getInnerScopes(){
		return innerScopes;
	}


}

