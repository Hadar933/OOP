package filesprocessing;


import commandfile.CommandFileParser;
import commandfile.Section;
import Helpers.validateFilters;
import Orders.CompareFactory;
import Helpers.MergeSort;
import commandfile.SectionFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * a class that initiates the program and performs the entire operation
 */
public class DirectoryProcessor {
	private final static String MISSING_ARGS = "ERROR: Missing arguments.";
	private final static String MANY_ARGS = "ERROR: Too many arguments";
	private final static String ERROR = "ERROR: ";


	/**
	 * generates an array of files from a given directory
	 * @param sourceDir - a source directory
	 */
	static ArrayList<File> dir2array(String sourceDir) {
		ArrayList<File> result = new ArrayList<File>();
		File files = new File(sourceDir);
		for (File file : Objects.requireNonNull(files.listFiles())) { // iterate if value dir isn't empty
			if (!file.isDirectory()) {
				result.add(file);
			}
		}
		return result;
	}

	/*
	a static method that prints all of the current section errors thus far
	 */
	static void printErrors(Section section) {
		for (String error : section.getErrors()) {
			System.err.println(error);
		}
	}

	public static void main(String[] args) {
		int validArgs = 2;
		if (args.length < validArgs) {
			System.err.println(MISSING_ARGS);
		} else if (args.length > validArgs) {
			System.err.println(MANY_ARGS);
		} else {
			try {
				String sourceDir = args[0];
				String commandFile = args[1];
				if (new File(commandFile).length() == 0) {
					return;
				}
				ArrayList<Section> allSections = new SectionFactory()
						.generateAllSections(new CommandFileParser(commandFile).getValidData());
				ArrayList<File> allDirFiles = dir2array(sourceDir);
				for (Section section : allSections) { // sorts each section:
					ArrayList<File> result = new validateFilters()
							.filterFiles(allDirFiles, section.getFilter(), section.isFilterNot());
					Comparator<File> comparator =
							new CompareFactory().generateComparator(section.getOrder());
					result = new MergeSort().mergeSort(result, comparator, section.isOrderReverse());
					for (File file : result) { // print the file names in order:
						System.out.println(file.getName());
					}
					printErrors(section);
				}
			} catch (Exception e) {
				System.err.println(ERROR + e);
			}
		}
	}
}
