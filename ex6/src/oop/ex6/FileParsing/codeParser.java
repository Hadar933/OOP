package oop.ex6.FileParsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * given some sJava file, this class will generate a corresponding String array, in which every item
 * represents a line in the file.
 */
public class codeParser {
	private static final String EMPTY_LINE = "";
	private static final String SPACE = " ";

	/**
	 * converts sJava to array of string, and remove all spaces and comments
	 * @param fileName - some sJava file name
	 * @return - ArrayList of lines
	 * @throws FileNotFoundException - if the file doesn't exist
	 */
	public static ArrayList<String> sJava2StringArray(String fileName) throws FileNotFoundException {
		File sJava = new File(fileName);
		Scanner scanner = new Scanner(sJava);
		ArrayList<String> data = new ArrayList<>();
		while (scanner.hasNext()) {
			String line = scanner.nextLine().replaceAll("\\s+", SPACE).replaceAll("//.*", EMPTY_LINE);
			if (!line.equals(EMPTY_LINE)) {
				data.add(line);
			}
		}
		return data;
	}
}
