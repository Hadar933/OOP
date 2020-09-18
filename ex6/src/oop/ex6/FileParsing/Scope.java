package oop.ex6.FileParsing;

import oop.ex6.Tokenizer.Variable;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * any scope in the sJava file is represented by an instance of this class
 * a scope consists of local variables (either an assignment, declaration, or reference to)
 * a scope might also consist some inner scopes, and an encapsulating scope
 */
public class Scope{

	private final ArrayList<Scope> innerScopes;
	private Scope outerScope;

	private final ArrayList<Variable> declareVars;
	private final ArrayList<Variable> referVars;
	private final ArrayList<Variable> assignVars;

	private final ArrayList<String> scopeCode;

	Scope(ArrayList<String> codeLines){
		this.scopeCode = codeLines;
		innerScopes = new ArrayList<>();
		declareVars = new ArrayList<>();
		referVars = new ArrayList<>();
		assignVars = new ArrayList<>();
	}

	public ArrayList<String> getScopeCode(){
		return scopeCode;
	}

}
