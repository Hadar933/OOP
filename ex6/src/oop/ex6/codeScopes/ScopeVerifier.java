package oop.ex6.codeScopes;

import oop.ex6.FileParsing.RegEx;
import oop.ex6.Tokenizer.*;
import oop.ex6.main.CodeException;
import oop.ex6.main.SyntaxException;
import oop.ex6.main.UsageException;

import java.util.ArrayList;
import java.util.Map;


/**
 * this class checks if a given scope if of valid form
 */
public class ScopeVerifier {

	private static final int EMPTY_LINE = 0;
	private static final int ONE_LINER = 1;
	private static final int FIRST_CODE_LINE = 0;

	/**
	 * updates the scopeType data member
	 */
	private static RegEx.SCOPE_TYPE checkScopeType(Scope s) {
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

	/**
	 * main method of this class, verifies a scope s
	 * @param s - some scope instance
	 */
	public static void VerifyScope(Scope s, CodeFile allCode) throws CodeException {
		RegEx.SCOPE_TYPE scopeType = checkScopeType(s);
		switch (scopeType) {
		case METHOD_CALL:
			verifyMethodCall(s, allCode);
			break;
		case RETURN:
			s.setScopeType(RegEx.SCOPE_TYPE.RETURN);
			break;
		case VAR_DECLARE:
			verifyVarDeclare(s,allCode);
			break;
		case VAR_ASSIGN:
			verifyVarAssign(s, allCode);
			break;
		case METHOD_DECLARE:
			verifyMethodDeclare(s, allCode);
			break;
		case IF_WHILE:
			verifyIfWhie(s, allCode);
			break;
		case EMPTY:
			s.setScopeType(RegEx.SCOPE_TYPE.EMPTY);
		}
		throw new SyntaxException("bad scope form");
	}

	private static void verifyMethodDeclare(Scope s, CodeFile allCode) throws CodeException {
		if (s.getOuterScope() != null) {
			return;
		}
		ArrayList<Scope> subScopes = s.getInnerScopes();
		if (new Return(s).isReturnValValid()) {
			if (subScopes != null) {
				for (Scope sub : subScopes) {
					sub.setOuterScope(s);
					VerifyScope(sub, allCode);
				}
			}
		}
		s.findReferenceVars(s.getAssignVars());
		s.findReferenceVars(s.getDeclareVars());
		boolean exists;
		for (Variable refVar : s.getReferVars()) {
			exists = false;
			for (Variable declareVar : s.getDeclareVars()) {
				if (refVar.getValue().equals(declareVar.getValue())) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				for (Variable declareVar : s.getOuterScope().getDeclareVars()) {
					if (refVar.getValue().equals(declareVar.getValue())) {
						exists = true;
						break;
					}
				}
				if (!exists) {
					throw new SyntaxException("haven't declared a ref var");
				}
			}
		}
	}

	/**
	 * verifies an if or a while
	 */
	private static void verifyIfWhie(Scope s, CodeFile allCode) throws CodeException {
		s.setScopeType(RegEx.SCOPE_TYPE.IF_WHILE);
		ArrayList<Scope> subScopes = s.getInnerScopes();
		for (Scope sub : subScopes) {
			VerifyScope(sub, allCode);
		}
		s.findReferenceVars(s.getAssignVars());
		s.findReferenceVars(s.getDeclareVars());
	}

	/**
	 * verifies a variable assignment
	 */
	private static void verifyVarAssign(Scope s, CodeFile allCode) {
		s.setScopeType(RegEx.SCOPE_TYPE.VAR_ASSIGN);
		//TODO: need to implement the global scope for this
	}

	/**
	 * verifies a variable declaration
	 */
	private static void verifyVarDeclare(Scope s, CodeFile allCode) throws CodeException {
		s.setScopeType(RegEx.SCOPE_TYPE.VAR_DECLARE);
		if (s.getOuterScope() != null) {
			//TODO: theres a need for a setter here
		}
		if(allCode.getFileScopesArray().contains(s)){
			if(Variable.checkDeclareVar(s) == RegEx.TYPE.REF){
				//TODO: theres a need for a setter here
				// TODO: implement IsRefDeclarationInsideSection
			}
			else if(s.getOuterScope()!=null){
				// TODO: implement IsRefDeclarationInsideSection
				throw new CodeException("var not declared");
			}

		}

	}

	/**
	 * verifies a method call
	 */
	private static void verifyMethodCall(Scope s, CodeFile allCode) throws UsageException {
		s.setScopeType(RegEx.SCOPE_TYPE.METHOD_CALL);
		Scope scopy = s;
		while (scopy.getOuterScope() != null) { // getting the most outer scope
			scopy = scopy.getOuterScope();
		}
		if (scopy.getScopeType() != RegEx.SCOPE_TYPE.METHOD_CALL) {
			throw new UsageException("calling method outside of a method");
		}
		MethodDeclare method = new MethodDeclare(s.getScopeCode().get(0));
		String methodName = method.getMethodName();
		if (allCode.getFileMethods().containsKey(methodName)) {
			throw new UsageException("calling method without declaring");
		}
		Map<String, String> params = method.getParams();
		ArrayList<Variable> vars = new ArrayList<>();
		for (String key : params.keySet()) {
			vars.add(new Variable(key)); //TODO: i need to add the variable not only the key. fix this
		}
		ArrayList<Variable> methodsInFile = allCode.getFileMethods().get(params);
		for (Variable callVar : vars) {
			for (Variable declareVar : methodsInFile) {
				if (!callVar.getType().equals(declareVar.getType())) {
					throw new UsageException("argument type mismatch");
				}
			}
		}
	}


}
