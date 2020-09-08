package Sections;

import Exeptions.SectionExeption;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * converts a command file to a strings array and checks its in valid format
 */
public class CommandFileParser {
	private static final String FILTER = "FILTER";
	private static final String ORDER = "ORDER";
	private final Scanner scanner;

	/**
	 * ctor for the class
	 * @param fileName - some command file name
	 * @throws FileNotFoundException - if the file does not exist, an exeption is throwed
	 */
	public CommandFileParser(String fileName) throws FileNotFoundException {
		scanner = new Scanner(new File(fileName));
	}

	/**
	 * converts a file to an array of strings, where each item is a line
	 * @return - array of strings
	 */
	public ArrayList<String> file2array() {
		ArrayList<String> result = new ArrayList<>();
		while (scanner.hasNext()) {
			result.add(scanner.nextLine());
		}
		return result;
	}

	/**
	 * a method that checks if the data (that represents a command file) is in valid format and returns a
	 * valid array of the data
	 * @param data - some data to check
	 * @return - a valid array representing a valid command file
	 */
	public ArrayList<String> checkData(ArrayList<String> data) throws Exception {
		ArrayList<String> result = new ArrayList<>();
		result.add(data.get(0));
		// marking so called "bad" in a different array.
		for (int i = 1; i < data.size(); i++) {
			if ((data.get(i).equals("ORDER") && result.get(i - 1).equals("FILTER")) || (data.get(i).equals(
					"FILTER") && result.get(i - 1).equals("ORDER"))) {
				data.add("BAD_VAL");
			}
		}// now throwing the relevant exceptions:
		int sectionSize = 4;
		int startIndex = 0;
		int currentIndex = 0;
		if (!result.get(0).equals("FILTER")) {
			throw new SectionExeption("FILTER");
		}
		for (int i = 1; i < data.size(); i++) {
			if (data.get(i).equals("FILTER")) {
				currentIndex = i;
				if (currentIndex - startIndex > sectionSize) {
					throw new SectionExeption("FILTER");
				}
			}
			int nextOrderIndex = 2;
			if (!data.get(currentIndex + nextOrderIndex).equals("ORDER")) {
				throw new SectionExeption("ORDER");
			}
			startIndex = currentIndex;
		}
		return result;
	}
}
