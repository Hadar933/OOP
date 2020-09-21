package oop.ex6.codeScopes;

import java.util.ArrayList;
import java.util.HashMap;
import oop.ex6.Tokenizer.Variable;

/**
 * a class which represent the whole sjava code file
 */
public class CodeFile {
	// add make static or singleton
	// add codeFile object and scopes array list to file analyzer method parameters
	private HashMap<String, ArrayList<Variable>> fileMethods;
	private ArrayList<Variable> variablesDeclaredInScope;
	private ArrayList<Variable> variablesAssignedInScope;
	private ArrayList<Variable> variablesReferencedInScope;
	private ArrayList<Scope> fileScopesArray;

	/**
	 * constructor for the CodeFile class
	 */
	public CodeFile(ArrayList<Scope> scopesArray){
		fileMethods = new HashMap<>();
		variablesAssignedInScope = new ArrayList<>();
		variablesDeclaredInScope = new ArrayList<>();
		variablesReferencedInScope = new ArrayList<>();
		fileScopesArray = scopesArray;
	}

	/**
	 * getter for variablesReferencedInScope
	 * @return the array variablesReferencedInScope
	 */
	public ArrayList<Variable> getVariablesReferencedInScope(){ return variablesReferencedInScope; }

	/**
	 * getter for variablesDeclaredInScope
	 * @return the array variablesDeclaredInScope
	 */
	public ArrayList<Variable> getVariablesDeclaredInScope(){ return variablesDeclaredInScope; }

	/**
	 * getter for variablesAssignedInScope
	 * @return the array variablesAssignedInScope
	 */
	public ArrayList<Variable> getVariablesAssignedInScope(){ return variablesAssignedInScope; }

	/**
	 * getter for fileScopesArray
	 * @return array of the scopes in the file
	 */
	public ArrayList<Scope> getFileScopesArray() {
		return fileScopesArray;
	}

	/**
	 * adds given variable to the array of reference variables
	 * @param v reference variable
	 */
	public void addReferenceVariable(Variable v) { variablesReferencedInScope.add(v); }

	/**
	 * adds given variable to the array of declared variables
	 * @param v declared variable
	 */
	public void addDeclaredVariable(Variable v) { variablesDeclaredInScope.add(v); }

	/**
	 * adds given variable to the array of assigned variables
	 * @param v assigned variable
	 */
	public void addAssignedVariable(Variable v) { variablesAssignedInScope.add(v); }

	/**
	 * helper for localReferenceFinder
	 * @param variablesArray variables array
	 */
	private void addLocalReferenceVariablesHelper(ArrayList<Variable> variablesArray){
		for (Variable v : variablesArray){
			if (v.getValue() != null && !v.getValue().matches("false|true")){
				if (v.getValue().matches("[a-zA-Z]+\\w*|[_]+\\w+")){ addReferenceVariable(v); }
			}
		}
	}

	/** finds all local reference variables and adds them to the reference array */
	public void addLocalReferenceVariables(){
		addLocalReferenceVariablesHelper(variablesAssignedInScope);
		addLocalReferenceVariablesHelper(variablesDeclaredInScope);
	}

}
