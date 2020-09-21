package oop.ex6.codeScopes;

import oop.ex6.FileParsing.RegEx;
import oop.ex6.Tokenizer.*;
import oop.ex6.main.CodeException;
import oop.ex6.main.SyntaxException;
import oop.ex6.main.UsageException;

import java.util.ArrayList;


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
	private RegEx.SCOPE_TYPE checkScopeType(Scope s) {
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
	public void VerifyScope(Scope s) throws CodeException {
		RegEx.SCOPE_TYPE scopeType = checkScopeType(s);
		switch (scopeType) {
		case METHOD_CALL:
			verifyMethodCall(s);
			break;
		case RETURN:
			s.setScopeType(RegEx.SCOPE_TYPE.RETURN);
			break;
		case VAR_DECLARE:
			verifyVarDeclare(s);
			break;
		case VAR_ASSIGN:
			verifyVarAssign(s);
			break;
		case METHOD_DECLARE:
			verifyMethodDeclare(s);
			break;
		case IF_WHILE:
			verifyIfWhie(s);
			break;
		case EMPTY:
			s.setScopeType(RegEx.SCOPE_TYPE.EMPTY);
		}
		throw new SyntaxException("bad scope form");
	}

	private void verifyMethodDeclare(Scope s) {
		if (s.getOuterScope() == null) {
			ArrayList<Scope> subSections = s.getInnerScopes();
			//TODO: finish this somehow
		}
	}

	/**
	 * verifies an if or a while
	 */
	private void verifyIfWhie(Scope s) throws CodeException {
		s.setScopeType(RegEx.SCOPE_TYPE.IF_WHILE);
		ArrayList<Scope> subScopes = s.getInnerScopes();
		for (Scope sub : subScopes) {
			VerifyScope(sub);
		}
		s.findReferenceVars(s.getAssignVars());
		s.findReferenceVars(s.getDeclareVars());
	}

	/**
	 * verifies a variable assignment
	 */
	private void verifyVarAssign(Scope s) {
		s.setScopeType(RegEx.SCOPE_TYPE.VAR_ASSIGN);
		//TODO: need to implement the global scope for this
	}

	/**
	 * verifies a variable declaration
	 */
	private void verifyVarDeclare(Scope s) {
		s.setScopeType(RegEx.SCOPE_TYPE.VAR_DECLARE);
		//TODO: need to implement the global scope for this
	}

	/**
	 * verifies a method call
	 */
	private void verifyMethodCall(Scope s) throws UsageException {
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
		//TODO: continue this part
	}


}
