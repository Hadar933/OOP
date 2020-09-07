package FileParsing;

import java.util.ArrayList;

/**
 * this factory class generates a section object after validating that all the given data is in the right
 * format
 */
public class SectionFactory {
	/*
	the number of arguments expected for the filters (with or without concatenated NOT at the end)
	 */
	private static final int TERNARY_OP_SIZE = 3;
	private static final int BINARY_OP_SIZE = 2;
	private static final int UNARY_OP_SIZE = 1;
	private static final int TERNARY_NOT_OP_SIZE = 4;
	private static final int BINARY_NOT_OP_SIZE = 3;
	private static final int UNARY_REVERSE_OP_SIZE = 2;

	/*
	minimum size of bytes
	 */
	private static final int MIN_SIZE = 0;

	/*
	relevant filter and order names
	 */
	private static final String NOT = "NOT";
	private static final String REVERSE = "REVERSE";


	/**
	 * a method that checks if the order section arguments are valid
	 * @param orderArgs - the args provided in the order section in command file
	 * @return - true - valid, false- invalid
	 */

	private boolean isOrderValid(String[] orderArgs) {
		return (orderArgs.length == UNARY_OP_SIZE ||
				(orderArgs.length == UNARY_REVERSE_OP_SIZE && orderArgs[1].equals(REVERSE)));
	}

	/**
	 * a method that checks if the filter arguments are valid
	 * @param filterArgs - the args provided in the order section in command file
	 * @return - true - valid, false- invalid
	 */

	private boolean isFilterValid(String[] filterArgs) {


	}


	/**
	 * checks if the greater/smaller than filter is valid
	 * @param FilterArgs - the filter data
	 * @return - true: valid. false: not valid
	 */
	private boolean isSizeFilterValid(String[] FilterArgs) {
		double size = Double.parseDouble(FilterArgs[1]);
		if (FilterArgs.length == BINARY_OP_SIZE && size >= MIN_SIZE) {
			return true;
		} else if (FilterArgs.length == BINARY_NOT_OP_SIZE) {
			return size >= MIN_SIZE && FilterArgs[2].equals(NOT);
		}
		return false;
	}

	/**
	 * checks if the between filter is valid
	 * @param FilterArgs - the filter data
	 * @return - true: valid. false: not valid
	 */
	private boolean isBetweenFilterValid(String[] FilterArgs) {
		double size1 = Double.parseDouble(FilterArgs[1]);
		double size2 = Double.parseDouble(FilterArgs[2]);
		boolean validSize = size1 >= MIN_SIZE && size1 <= size2;
		if (FilterArgs.length == TERNARY_OP_SIZE && validSize) {
			return true;
		} else if (FilterArgs.length == TERNARY_NOT_OP_SIZE && validSize) {
			return FilterArgs[3].equals(NOT);
		}
		return false;
	}

	/**
	 * checks if the file/contains/prefix/suffix filter is valid, simply by checking lengths
	 * @param FilterArgs - the filter data
	 * @return - true: valid. false: not valid
	 */
	private boolean isStringFormatFilterValid(String[] FilterArgs) {
		return FilterArgs.length == BINARY_OP_SIZE ||
			   (FilterArgs.length == BINARY_NOT_OP_SIZE && FilterArgs[2].equals(NOT));
	}

	/**
	 * checks if the writable/executable/hidden filter is valid, simply by checking lengths
	 * @param FilterArgs - the filter data
	 * @return - true: valid. false: not valid
	 */
	private boolean isOperationFormatFilterValid(String[] FilterArgs) {
		if (FilterArgs.length == BINARY_OP_SIZE ||
			(FilterArgs.length == BINARY_NOT_OP_SIZE && FilterArgs[2].equals(NOT))) {
			return FilterArgs[1].equals("YES") || FilterArgs[1].equals("NO");
		}
		return false;
	}


	public Section generateSection(String[] commandFileData) {


	}


}
