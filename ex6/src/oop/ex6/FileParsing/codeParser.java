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

	/**
	 * converts sJava to array of string, and remove all spaces
	 * @param fileName - some sJava file name
	 * @return - ArrayList of lines
	 * @throws FileNotFoundException - if the file doesn't exist
	 */
	public static ArrayList<String> sJava2StringArray(String fileName) throws FileNotFoundException {
		File sJava = new File(fileName);
		Scanner scanner = new Scanner(sJava);
		ArrayList<String> data = new ArrayList<>();
		while (scanner.hasNext()) {
			String line = cleanLine(scanner.nextLine());
			if (!line.equals(EMPTY_LINE)) {
				data.add(line);
			}
		}
		return data;
	}

	/*
	given some line, remove all excess spaces and comments
	 */
	private static String cleanLine(String line) {
		return line.replaceAll("\\s+", " ").replaceAll("//.*", "");
	}

	public static void main(String[] args) throws FileNotFoundException {
		String fileName = args[0];
		ArrayList<String> data = sJava2StringArray(fileName);
		for (String line : data) {
			System.out.println(line);
		}
	}

}
