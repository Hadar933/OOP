package Sections;

import java.util.ArrayList;

/**
 * this factory class generates a section object after validating that all the given data is in the right
 * format
 */
public class SectionFactory {
	private static final String WARNING_MSG = "Warning in line ";
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

	/*
	 * combines all the checks to validate a filter
	 * @param filter - some filter to check if it is valid
	 * @return - true: valid. false-invalid
	 */
	private boolean isFilterValid(String[] filter) {
		String name = filter[0];
		switch (name) {
		case "greater_than":
		case "smaller_than":
			return isSizeFilterValid(filter);
		case "between":
			return isBetweenFilterValid(filter);
		case "file":
		case "contains":
		case "prefix":
		case "suffix":
		case "all":
			return isStringFormatFilterValid(filter);
		case "writable":
		case "executable":
		case "hidden":
			return isOperationFormatFilterValid(filter);
		default:
			return false;
		}
	}

	/*
	a helper method that returns true if item in array and false otherwise
	 */
	private boolean itemInArray(String item, String[] array) {
		for (String element : array) {
			if (item.equals(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * generates a section object from a command file data
	 * @param commandFileData - array represents the content of a command file
	 * @param filterIndex - the line in which the filter section occurs
	 * @param orderIndex - the line in which the order section occurs
	 * @return - a relevant section object
	 */
	public Section generateSection(ArrayList<String> commandFileData, int filterIndex, int orderIndex) {
		String delimiter = "#";
		Section section = new Section();
		String[] filter = commandFileData.get(filterIndex).split(delimiter);
		String[] order = commandFileData.get(orderIndex).split(delimiter);
		if (isFilterValid(filter)) { //valid filters
			section.setFilter(filter);
			if (itemInArray(NOT, filter)) { // there is a NOT
				section.setFilterNot(true);
			}
		} else { // invalid filters
			section.setErrors(WARNING_MSG + filterIndex);
		}
		if (isOrderValid(order)) { // valid order
			section.setOrder(order[0]);
			if (itemInArray(REVERSE, order)) { // there is a REVERSE
				section.setOrderReverse(true);
			}
		}
		else{ //invalid order
			section.setErrors(WARNING_MSG + orderIndex);
		}
		return section;
	}

	/**
	 * this method uses the previous one to generate all the sections that are in the file
	 * (multiple calls to the generateSection method)
	 * @param commandFileData - array represents the content of a command file
	 * @return an array consisting of section instances
	 */
	public ArrayList<Section> generateAllSections(ArrayList<String> commandFileData){
		int blockSize = 3;
		ArrayList<Section> allSections = new ArrayList<Section>();
		for(int i=0;i<commandFileData.size();i++){
			if(commandFileData.get(i).equals("FILTER") && commandFileData.get(i+blockSize).equals("ORDER")){
				Section newSection = generateSection(commandFileData,i,i+blockSize);
				allSections.add(newSection);
			}
		}
		return allSections;
	}
}