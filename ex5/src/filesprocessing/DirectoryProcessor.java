package filesprocessing;

import commandfile.CommandFileParser;
import commandfile.Section;
import Filters.Filter;
import Orders.CompareFactory;
import Helpers.MergeSort;
import commandfile.SectionFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * a class that initiates the program and performs the entire operation
 */
public class DirectoryProcessor {
	private final static String ERROR = "ERROR: ";
	private final static String MANY_ARGS = "ERROR: Too many arguments";
	private final static String MISSING_ARGS = "ERROR: Missing arguments.";

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
				ArrayList<Section> sections = new SectionFactory()
						.generateAllSections(new CommandFileParser(commandFile).getValidData());
				ArrayList<File> allDirFiles = dir2array(sourceDir);
				for (Section section : sections) {
					ArrayList<File> finalArray = new Filter().filterFiles(allDirFiles, section.getFilter(),
																		  section.isFilterNot());
//					finalArray = new MergeSort().finalMergeSort(finalArray, new CompareFactory()
//							.generateComparator(section.getOrder()), section.isOrderReverse());
					finalArray = new MergeSort().mergeSort(finalArray, new CompareFactory()
							.generateComparator(section.getOrder()), section.isOrderReverse());

					for (File file : finalArray) {
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
