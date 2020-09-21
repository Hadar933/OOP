/* Package */
package oop.ex6.main;
/* Imports */

import oop.ex6.main.scope.MethodScope;
import oop.ex6.main.scope.ScopeTree;
import oop.ex6.main.utills.CompilingException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;

/**
 * Checks if a file is Compilable by the s-Java compiler
 *
 * @author Shimon Malnick, Chana Chadad
 */
public class Sjavac {
    /**
     * Types of errors that cause an exceptions to be thrown while processing the file
     */
    public enum ErrorType {
        ARGS, PREFIX, FINAL_NOT_INIT, BAD_LINE, IF, WHILE, ILLEGAL_BRACKETS,
        METHOD_INSIDE_METHOD, SCOPE_NOT_IN_METHOD, RETURN_STATEMENT, CALLING_UNKNOWN_METHOD,
        METHOD_PARAMETERS, FINAL_ASSIGNMENT, INVALID_VARIABLE, TYPE_NOT_MATCHING_VALUE,
        INITIALIZING_2METHODS_SAME_NAME, INVALID_VARIABLE_NAME, MISSING_ASSIGNMENT, INVALID_METHOD_NAME, ILLEGAL_LOOP_CONDITION, CLOSING_BRACKET
    }

    /* exit codes */
    private static final int IO_EXCEPTION_CODE = 2;
    private static final int ILLEGAL_CODE = 1;
    private static final int LEGAL_CODE = 0;
    /* Constants */
    private final static int NUM_OF_ARGS = 1;
    private final static int FILE_INDEX = 0;

    /**
     * Runs the analysis on the given file (as an argument)
     *
     * @param args contains a path to the file that needs to be processed
     */
    public static void main(String[] args) {
        try {
            ArrayList<String> allLines = validateArgs(args);
            if (allLines == null) return;  // case of an empty file
            ScopeTree root = processFile(allLines);
            root.analyze();
            parseMethods(root);
            System.out.println(LEGAL_CODE);
        } catch (CompilingException error) {
            error.printMessage();
            System.out.println(ILLEGAL_CODE);
        } finally {
            resetTree();
        }
    }

    /*
    Resets the file's tree data structure when finishes the analysis
     */
    private static void resetTree() {
        ScopeTree.setMethodsList(new HashMap<>());
    }

    /*
    Processes the file's lines to a tree data structure of a global scopes with methods as children
     */
    private static ScopeTree processFile(ArrayList<String> allLines) throws CompilingException {
        Iterator<String> fileIterator = allLines.iterator();
        ScopeTree root = new ScopeTree();
        ScopeTree currentScope = root;
        int openBrackets = 0, closeBrackets = 0;
        while (fileIterator.hasNext()) {
            if (closeBrackets > openBrackets) throw new CompilingException(ErrorType.ILLEGAL_BRACKETS);
            String line = fileIterator.next();
            if (line.trim().length() == 0) continue; // for empty lines
            if (openScope(line)) {
                MethodScope son;
                if (openMethod(line)) {
                    if (currentScope.getParent() != null) throw new CompilingException(ErrorType
                            .METHOD_INSIDE_METHOD);
                    son = new MethodScope();
                    updatePointers(currentScope, son);
                    son.addLine(line);
                    currentScope = son;
                } else {
                    if (openBrackets == closeBrackets) throw new CompilingException(ErrorType
                            .SCOPE_NOT_IN_METHOD);
                    currentScope.addLine(line);
                }
                openBrackets++;
            } else if (closeScope(line)) {
                currentScope.addLine(line);
                closeBrackets++;
                if (closeBrackets == openBrackets) currentScope = currentScope.getParent();
                if (currentScope == null) throw new CompilingException(ErrorType.ILLEGAL_BRACKETS);
            } else currentScope.addLine(line);
        }
        if (closeBrackets != openBrackets) throw new CompilingException(ErrorType.ILLEGAL_BRACKETS);
        verifyStructure(root);
        return root;
    }

    /*
    Returns true if the given line is a declaration of a method
     */
    private static boolean openMethod(String line) {
        Matcher m = RegexAnalyzer.OPEN_METHOD.matcher(line);
        return m.matches();
    }

    /*
    Verifies the structure of the given file (that the file contains only methods and variables
    declarations, and throws an exception when invalid
     */
    private static void verifyStructure(ScopeTree root) throws CompilingException {
        ArrayList<ScopeTree> children = root.getChildren();
        for (ScopeTree child : children) {
            String firstLine = child.getLine(0);
            // Down casting to use the validateMethod method. all scopes in the main file are methods so
            // this casting is verified not to cause runtime errors
            if (!((MethodScope) (child)).validateMethod(firstLine)) throw new CompilingException(ErrorType
                    .SCOPE_NOT_IN_METHOD);
            String lastLine = child.getLine(child.getLines().size() - 2);
            if (!child.checkReturn(lastLine)) throw new CompilingException(ErrorType.RETURN_STATEMENT);
            ArrayList<String> scopeLines = child.getLines();
            // next two lines are used to avoid double checking of the same line inside the scope
            scopeLines.remove(0);
            scopeLines.remove(scopeLines.size() - 2);
        }
    }

    /*
    Returns true if the given line is a closing scope line ( } ) and false otherwise
     */
    private static boolean closeScope(String line) {
        Matcher m = RegexAnalyzer.CLOSE_SCOPE.matcher(line);
        return m.matches();
    }

    /*
    Returns true if the given line is an opening scope line ( { ) and false otherwise
     */
    private static boolean openScope(String line) {
        Matcher m = RegexAnalyzer.OPEN_SCOPE.matcher(line);
        return m.matches();
    }

    /*
    Updates the pointers between a given node(ScopeTree) and it's son
     */
    private static void updatePointers(ScopeTree scopeTree, ScopeTree son) {
        scopeTree.setChild(son);
        son.setParent(scopeTree);
    }

    /*
    Decomposes the main global scope to its method components(creates new MethodScopes)
     */
    private static void parseMethods(ScopeTree root) throws CompilingException {
        int openBrackets = 1, closeBrackets = 0;
        for (ScopeTree methodScope : root.getChildren()) {
            Iterator<String> lines = methodIterator(methodScope);
            ScopeTree currentScope = methodScope;
            while (lines.hasNext()) {
                String line = lines.next();
                if (closeBrackets > openBrackets) {
                    throw new CompilingException(ErrorType.ILLEGAL_BRACKETS);
                }
                if (openScope(line)) {
                    ScopeTree son;
                    if (openMethod(line)) {
                        throw new CompilingException(ErrorType.METHOD_INSIDE_METHOD);
                    }
                    son = new ScopeTree();
                    updatePointers(currentScope, son);
                    son.parsePrefix(line);
                    currentScope = son;
                    openBrackets++;
                } else if (closeScope(line)) {
                    closeBrackets++;
                    currentScope = currentScope.getParent();
                    if (currentScope == null) {
                        throw new CompilingException(ErrorType.ILLEGAL_BRACKETS);
                    }

                } else {
                    currentScope.parsePrefix(line);
                }
            }
        }
    }

    /*
    Returns an iterator to iterate over the lines of a given scope tree node, and resets the lines of the
    scope , in order to accumulate only the relevant lines in the parseMethods method.
     */
    private static Iterator<String> methodIterator(ScopeTree methodScope) {
        ArrayList<String> iteratorList = new ArrayList<>(methodScope.getLines());
        methodScope.resetLines();
        return iteratorList.iterator();
    }

    /*
    Validates the given args from the main method are valid, and throws an appropriate exception when needed
     */
    private static ArrayList<String> validateArgs(String[] args) throws CompilingException {
        if (args.length != NUM_OF_ARGS) {
            throw new CompilingException(ErrorType.ARGS);
        }
        return file2array(args[FILE_INDEX]);
    }

    /*
    Returns an array containing all the lines from the file with the given name, and deals with IOExceptions
     when needed
     */
    private static ArrayList<String> file2array(String fileName) {
        // A list to hold the file's content
        List<String> fileContent = new ArrayList<>();
        // Reader object for reading the file
        BufferedReader reader = null;
        try {
            // Open a reader
            reader = new BufferedReader(new FileReader(fileName));

            // Read the first line
            String line = reader.readLine();

            // Go over the rest of the file
            while (line != null) {

                // Add the line to the list
                fileContent.add(line);

                // Read the next line
                line = reader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.err.println("ERROR: The file: " + fileName + " is not found.");
            System.out.println(IO_EXCEPTION_CODE);
            return null;
        } catch (IOException e) {
            System.err.println("ERROR: An IO error occurred.");
            System.out.println(IO_EXCEPTION_CODE);
            return null;
        } finally {
            // Try to close the file
            try {
                if (reader != null)
                    reader.close();
                else {
                    return null;
                }
            } catch (IOException e) {
                System.err.println("ERROR: Could not close the file " + fileName + ".");
                System.out.println(IO_EXCEPTION_CODE);
                return null;
            }
        }
        // Convert the list to an array and return the array
        ArrayList<String> result = new ArrayList<>();
        result.addAll(fileContent);
        return result;
    }
}
