package oop.ex6.codeScopes;

import oop.ex6.Tokenizer.ReGex;
import oop.ex6.Tokenizer.Variable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * any scope in the sJava file is represented by an instance of this class
 * a scope consists of local variables (either an assignment, declaration, or reference to)
 * a scope might also consist some inner scopes, and an encapsulating scope
 */
public class Scope{

	/* length of a one line code  */
	private final static int ONE_LINER = 1;
	private final static int FIRST_CODE_LINE = 0;

	private final ArrayList<Scope> innerScopes;
	private Scope outerScope;

	private final ArrayList<Variable> declareVars;
	private final ArrayList<Variable> referVars;
	private final ArrayList<Variable> assignVars;

	private final ArrayList<String> scopeCode;

	Scope(ArrayList<String> codeLines){
		this.scopeCode = codeLines;
		declareVars = new ArrayList<>();
		referVars = new ArrayList<>();
		assignVars = new ArrayList<>();
		innerScopes = getInnerScopes();
	}

	/*
	a helper method that generate a sub array list from given array list
	 */
	private ArrayList<String> subArrayList(ArrayList<String> data, int startIdx, int endIdx){
		ArrayList<String> result = new ArrayList<>();
		for(int i=startIdx;i<endIdx;i++){
			result.add(data.get(i));
		}
		return result;
	}

	public void setOuterScope(Scope outerScope) {
		this.outerScope = outerScope;
	}

	/**
	 * find the inner scopes based on the "{" and "}" brackets
	 * @return an arrayList of scopes, which represents all the inner scopes of this scope
	 */
	private ArrayList<Scope> getInnerScopes() {
		boolean validParentheses;
		ArrayList<Integer> openBracketIndex = new ArrayList<>();
		ArrayList<Integer> closingBracketIndex = new ArrayList<>();
		for(int i=1;i<scopeCode.size();i++){
			if(scopeCode.get(i).contains("{")){
				 openBracketIndex.add(i);
			}
			else if(scopeCode.get(i).contains("}")){
				closingBracketIndex.add(i);
			}
		}
		if(openBracketIndex.size()==closingBracketIndex.size()){
			for(int i=0;i<openBracketIndex.size();i++){
				ArrayList<String> innerCode = subArrayList(scopeCode, openBracketIndex.size()-i,i);
				Scope innerScope = new Scope(innerCode);
				innerScope.setOuterScope(this);
				// TODO: continue from here
				innerScopes.add(innerScope);
			}

		}
	}

	/**
	getter for the scope's code array
	 */
	public ArrayList<String> getScopeCode(){
		return scopeCode;
	}

	/**
	 * this method initializes the local variables of this scope
	 */
	private void initializeLocals(){
		Variable var;
		if(scopeCode.size()==ONE_LINER){
			addVarToScope(this);
		}
		for(Scope InnerScope: innerScopes){
			addVarToScope(InnerScope);
		}
	}

	/**
	 * initiates a variable and adds it to the relevant scope
	 * @param scope - some scope instance
	 */
	private void addVarToScope(Scope scope) {
		Variable var;
		if(Variable.checkAssignVar(scope) != ReGex.TYPE.BAD_FLAG){
			var = new Variable(scope.getScopeCode().get(FIRST_CODE_LINE));
			if(outerScope!=null){
				outerScope.declareVars.add(var);
			}
		}
		if(Variable.checkDeclareVar(this) != ReGex.TYPE.BAD_FLAG){
			var = new Variable(scopeCode.get(FIRST_CODE_LINE));
			if(outerScope!=null){
				outerScope.declareVars.add(var);
			}
		}
	}


}
