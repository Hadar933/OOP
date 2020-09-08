package commandfile;

import java.util.ArrayList;
import java.util.List;

/**
 * this factory class generates a section object after validating that all the given data is in the right
 * format
 */
public class SectionFactory {

	private final static String WARNING_MSG = "Warning in line ";

	/*
	the number of arguments expected for the filters (with or without concatenated NOT at the end)
	 */
	private static final int TERNARY_OP_SIZE = 3;
	private static final int BINARY_OP_SIZE = 2;
	private static final int UNARY_OP_SIZE = 1;
	private static final int TERNARY_NOT_OP_SIZE = 4;
	private static final int BINARY_NOT_OP_SIZE = 3;
	private static final int UNARY_REVERSE_OP_SIZE = 2;
	private static final int UNARY_NOT_OP_SIZE = 2;


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
	 * checks validity of "all" filter (helper for checkValidityOfFilter)
	 * @param filter to check.
	 * @return true if valid, else false.
	 */
	private boolean isAllFilterValid(String[] filter) {
		if (filter.length == UNARY_OP_SIZE) {
			return true;
		}
		return filter.length == UNARY_NOT_OP_SIZE && filter[1].equals(NOT);
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
			return isStringFormatFilterValid(filter);
		case "writable":
		case "executable":
		case "hidden":
			return isOperationFormatFilterValid(filter);
		case "all":
			return isAllFilterValid(filter);
		default:
			return false;
		}
	}

	/**
	 * a method that checks if the order section arguments are valid
	 * @param orderArgs - the args provided in the order section in command file
	 * @return - true - valid, false- invalid
	 */

	private boolean isOrderValid(String[] orderArgs) {
		String name = orderArgs[0];
		if (name.equals("abs") || name.equals("type") || name.equals("size")) {
			return (orderArgs.length == UNARY_OP_SIZE ||
					(orderArgs.length == UNARY_REVERSE_OP_SIZE && orderArgs[1].equals(REVERSE)));
		}
		return false;
	}


	/**
	 * generates a section object from a command file data
	 * @param commandFileData - array represents the content of a command file
	 * @param commandLineIndex the line in which the filter or order section occurs
	 * @return - a relevant section object
	 */
	public Section generateSection(List<String> commandFileData, int commandLineIndex) {
		Section section = new Section();
		String delimiter = "#";
		int indexOfFilter = 1;
		int indexOfOrder = 3;
		String[] filter = commandFileData.get(indexOfFilter).split(delimiter);
		if (isFilterValid(filter)) { //valid filters
			section.setFilter(filter);
			if (itemInArray(NOT, filter)) { // there is a NOT
				section.setFilterNot(true);
			}
		} else {
			section.setErrors(WARNING_MSG + (commandLineIndex + indexOfFilter));
		}
		if (commandFileData.size() == TERNARY_NOT_OP_SIZE &&
			isOrderValid(commandFileData.get(indexOfOrder).split(delimiter))) {
			String[] order = commandFileData.get(indexOfOrder).split(delimiter);
			if (order.length == UNARY_REVERSE_OP_SIZE) {
				section.setOrderReverse(true);
			}
			section.setOrder(order[0]);
		} else {
			if (commandFileData.size() != BINARY_NOT_OP_SIZE) {
				section.setErrors(WARNING_MSG + (commandLineIndex + indexOfOrder));
			}
		}
		return section;
	}

	/**
	 * this method uses the previous one to generate all the sections that are in the file (multiple calls to
	 * the generateSection method)
	 * @param commandFileData - array represents the content of a command file
	 * @return an array consisting of section instances
	 */
	public ArrayList<Section> generateAllSections(ArrayList<String> commandFileData) {
		ArrayList<Section> sectionArrayList = new ArrayList<Section>();
		int blockSize = 3;
		for (int i = 0; i < commandFileData.size(); i++) {
			if ("FILTER".equals(commandFileData.get(i))) {
				if (commandFileData.size() > i + blockSize) {
					if ("FILTER".equals(commandFileData.get(i + blockSize))) {
						sectionArrayList.add(generateSection(commandFileData.
								subList(i, i + blockSize), i + 1));
					} else {
						sectionArrayList.add(generateSection(new ArrayList<>(commandFileData.subList
								(i, i + blockSize + 1)), i + 1));
					}
				} else {
					sectionArrayList.add(generateSection(commandFileData.subList
							(i, i + blockSize), i + 1));
				}
			}
		}
		return sectionArrayList;
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
}

