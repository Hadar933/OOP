package filters;

import java.io.File;
import java.util.ArrayList;

/**
 * a class that represents the smaller than filter
 */
public class smallerThan implements generic {
	/*
	conversion to kilobytes
	 */
	private static final int KILO = 1028;

	/*
	an array of filtered files, according to the class filter
	 */
	private final ArrayList<File> allFilesFiltered = new ArrayList<>();

	/**
	 * adds a file iff its size is smaller then the argument provided
	 * @param allFiles - an array containing all files in some folder
	 * @param filterArgs - the arguments the filter is being provided with
	 * @return - the filtered files
	 */
	@Override
	public ArrayList<File> filter(ArrayList<File> allFiles, String[] filterArgs) {
		double givenSize = Double.parseDouble(filterArgs[1]);
		double sizeInKB = KILO * givenSize;
		for (File file : allFiles) {
			if (sizeInKB > file.length()) {
				allFilesFiltered.add(file);
			}
		}
		return allFilesFiltered;
	}
}
