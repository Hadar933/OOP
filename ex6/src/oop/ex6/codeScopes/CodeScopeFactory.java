package oop.ex6.codeScopes;

import oop.ex6.main.SyntaxException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * factory for Scope objects
 */
public class CodeScopeFactory {

	private static final String OPEN_SCOPE = "{";
	private static final String END_LINE = ";";
	private static final String START_COMMENT = "//";

	/**
	 * creates a single scope from a given code array
	 * @param scopeStrings the scopes code array
	 * @return the scope created
	 */
	private static Scope createScope(ArrayList<String> scopeStrings){
		return new Scope(scopeStrings); // return used empty array
	}

	/**
	 * helper for createScopesArray
	 * @param codeStrings array of code lines
	 * @return an array with the line-length of each section
	 * @throws SyntaxException if syntax problem encountered
	 */
	private static ArrayList<Integer> getScopesLengths(ArrayList<String> codeStrings) throws SyntaxException {
		int codeStringsLength = codeStrings.size();
		int scopeLength, i = 0, brecketsEqualizer;
		ArrayList<Integer> scopesLengths = new ArrayList<>();
		while (i < codeStringsLength){
			String currentString = codeStrings.get(i);
			if (currentString.startsWith(START_COMMENT) || currentString.endsWith(END_LINE) ||
				currentString.endsWith(END_LINE + " ") || currentString.equals("")){ // return
				i++;
				scopesLengths.add(1);
			}
			else if (currentString.endsWith(OPEN_SCOPE)){
				scopeLength = 1; brecketsEqualizer = 1; i++;
				Pattern endScopePattern = Pattern.compile("\\s*}\\s*");
				while (brecketsEqualizer != 0 && codeStrings.get(i) != null){
					if (codeStrings.get(i).endsWith(OPEN_SCOPE)) { brecketsEqualizer += 1; }
					Matcher endScopeMatcher = endScopePattern.matcher(codeStrings.get(i));
					if (endScopeMatcher.matches()){brecketsEqualizer -= 1; }
					i++; scopeLength += 1;
				}
				if (brecketsEqualizer == 0){ scopesLengths.add(scopeLength); }
			}
			else { throw new SyntaxException("Syntax Exception Occurred"); }
		}
		return scopesLengths;
	}

	/**
	 * returns a pool of scopes object (in an array) created from the given code lines
	 * @param codeStrings the given code lines
	 * @return a pool of scopes object (in an array)
	 * @throws SyntaxException if syntax error encountered
	 */
	public static ArrayList<Scope> createScopesArray(ArrayList<String> codeStrings) throws SyntaxException {
		ArrayList<Scope> scopesArray = new ArrayList<>();
		ArrayList<Integer> scopesLengths = getScopesLengths(codeStrings);
		int previousIndex = 0;
		for (int index : scopesLengths){ // create scope from its code lines
			ArrayList<String> currentScopeArray = new ArrayList<>(
					codeStrings.subList(previousIndex, index + previousIndex));
			scopesArray.add(createScope(currentScopeArray));
			previousIndex += index;
		}
		return scopesArray;
	}



}
