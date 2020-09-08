package commandfile;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * converts a command file to a strings array and checks its in valid format
 */
public class CommandFileParser {
	private static final String FILTER = "FILTER";
	private static final String ORDER = "ORDER";
	private final Scanner scanner;
	private final String FLAG = "FLAG";

	/**
	 * a constructor for the class.
	 * @param commandFileName - the name of the command file
	 * @throws Exception - throws an exception if the initialization failed
	 */
	public CommandFileParser(String commandFileName) throws Exception {
		scanner = new Scanner(new File(commandFileName));
	}

	/**
	 * converts a file to an array of strings, where each item is a line
	 * @return - array of strings
	 */
	public ArrayList<String> file2array() throws Exception {
		ArrayList<String> result = new ArrayList<>();
		while (scanner.hasNext()) {
			result.add(scanner.nextLine());
		}
		return result;
	}

	/**
	 * a method that iterates over the data (that represents a command file) and returns an permuted array
	 * of the data. the process is as followed - whenever theres an unexpected number of
	 * ORDER or FILTER fields, a "BAD_VALUE" is added in the same index, representing the problem.
	 * @param data - some data to check
	 * @return - a valid array representing a valid command file
	 */
	public ArrayList<String> getEditedArray(ArrayList<String> data) throws SectionException {
		ArrayList<String> result = new ArrayList<>();
		if (data.size() == 0) {
			return result;
		}
		result.add(data.get(0));
		for (int i = 1; i < data.size(); i++) {
			int prevIndex = i - 1;
			boolean condition1 = data.get(i).equals(FILTER) && data.get(prevIndex).equals(FILTER);
			boolean condition2 = (data.get(i).equals(ORDER) && (result.get(prevIndex).equals(FILTER) ||
																result.get(prevIndex).equals(ORDER)));
			if (condition1 || (condition2)) {
				result.add("BAD_VALUE"); // adding some arbitrary string value
				continue;
			}
			result.add(data.get(i));
		}
		return result;
	}


	/**
	 * the method iterates over the return value array of the previous method
	 * and throws the relevant exceptions if the data isnt of valid form
	 * @param data the array to check.
	 * @throws Exception if there is a filter or order that are missing.
	 */
	public void checkEditedArray(ArrayList<String> data) throws Exception {
		ArrayList<String> result = getEditedArray(data);
		int sectionSize = 4;
		int startIndex = 0; // start index of the current section
		int currentIndex = 0;
		if (!result.get(0).equals(FILTER)) {
			throw new SectionException(FILTER);
		}
		for (int i = 1; i < data.size(); i++) {
			if (data.get(i).equals(FILTER)) {
				currentIndex = i;
				if (currentIndex - startIndex > sectionSize) {
					throw new SectionException(FILTER);
				}
			}
			int nextOrderIndex = 2;
			if (!data.get(currentIndex + nextOrderIndex).equals(ORDER)) {
				throw new SectionException(ORDER);
			}
			startIndex = currentIndex;
		}
	}

	/**
	 * This method combines all processes implemented in the other methods in this class, and returns a final
	 * legal array of commands.
	 * @return String ArrayList.
	 * @throws Exception if there is a filter or order that are missing.
	 */
	public ArrayList<String> getValidData() throws Exception {
		ArrayList<String> edited = getEditedArray(file2array());
		checkEditedArray(edited);
		return edited;
	}

}
