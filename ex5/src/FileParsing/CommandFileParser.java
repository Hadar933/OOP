package FileParsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * converts a command file to a strings array
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

	/*
	checks if the data contains the same amount of filter titles and order titles
	 */
	private boolean equalNumOfFilterAndOrder(ArrayList<String> data) {
		int count = 0;
		for (int i = 0; i < data.size() - 1; i++) {
			if (data.get(i).equals(FILTER)) {
				count++;
			}
			else if(data.get(i).equals(ORDER)){
				count--;
			}
		}
		return count == 0;
	}

	/*
	 * checks if all condition of a valid file are met, and throws the relevant exceptions
	 */
	public void checkValidData(ArrayList<String> data) {

	}
}
