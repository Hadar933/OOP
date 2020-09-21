/* Package */
package oop.ex6.main.utills;
/* Imports */

import oop.ex6.main.Sjavac;

/**
 * Exceptions that occur during the file compilations(exit code 1 only)
 * @author Shimon Malnick, Chana Chadad
 * @see Sjavac
 */
public class CompilingException extends Exception {
    private Sjavac.ErrorType errorType;

    /**
     * Constructor
     * @param errorType the type of the error cause this instance's creation
     */
    public CompilingException(Sjavac.ErrorType errorType) {
        this.errorType = errorType;
    }

    /**
     * Prints the relevant method(according to the given ErrorType when initialized)
     */
    public void printMessage() {
        switch (errorType) {
            case ARGS:
                System.err.println("Problem with the given arguments");
                break;
            case PREFIX:
                System.err.println("Problem with the prefix of the line");
                break;
            case BAD_LINE:
                System.err.println("Problem with a line in the file");
                break;
            case FINAL_NOT_INIT:
                System.err.println("final variables used for a variable that was not initialized");
                break;
            case IF:
                System.err.println("Problem with the given if line");
                break;
            case WHILE:
                System.err.println("Problem with the given while line");
                break;
            case CLOSING_BRACKET:
                System.err.println("No closing curly bracket for one of the scopes in the file");
                break;
            case ILLEGAL_BRACKETS:
                System.err.println("the number of closing curly brackets is bigger than the opening one");
                break;
            case SCOPE_NOT_IN_METHOD:
                System.err.println("Problem: defining a scope not inside a method");
                break;
            case METHOD_INSIDE_METHOD:
                System.err.println("Problem: defining a method inside a method");
                break;
            case RETURN_STATEMENT:
                System.err.println("Missing return statement");
                break;
            case FINAL_ASSIGNMENT:
                System.err.println("error while assigning a final variable");
                break;
            case INVALID_VARIABLE:
                System.out.println("error - invalid variable given");
                break;
            case METHOD_PARAMETERS:
                System.err.println("Invalid method parameters");
                break;
            case MISSING_ASSIGNMENT:
                System.err.println("Error : missing variable assignment");
                break;
            case INVALID_VARIABLE_NAME:
                System.err.println("Error: invalid variable name");
                break;
            case TYPE_NOT_MATCHING_VALUE:
                System.err.println("Error = variable type not matching its value");
                break;
            case CALLING_UNKNOWN_METHOD:
                System.err.println("Error - calling an unknown method");
                break;
            case INITIALIZING_2METHODS_SAME_NAME:
                System.err.println("Error - trying to initialize another method with the same name( no " +
                        "overriding allowed in Sjavac");
                break;
            case INVALID_METHOD_NAME:
                System.err.println("Problem with one of the files' methods name");
                break;
            case ILLEGAL_LOOP_CONDITION:
                System.err.println("Error: illegal condition inside one of the loops in the file");
                break;
        }
    }
}
