package filters;

import java.io.File;
import java.util.ArrayList;

/**
 * a class that represents the between filter
 */
public class between implements generic {
	/*
	conversion to kilobytes
	 */
	private static final int KILO = 1028;

	/*
	an array of filtered files, according to the class filter
	 */
	private final ArrayList<File> allFilesFiltered = new ArrayList<>();

	/**
	 * adds a file iff its size is larger then the first arg, and smaller than the second arg
	 * @param allFiles - an array containing all files in some folder
	 * @param filterArgs - the arguments the filter is being provided with
	 * @return - the filtered files
	 */
	@Override
	public ArrayList<File> filter(ArrayList<File> allFiles, String[] filterArgs) {
		double smallSize = Double.parseDouble(filterArgs[1]);
		double smallSizeInKB = KILO * smallSize;
		double largeSize = Double.parseDouble(filterArgs[2]);
		double largeSizeInKB = KILO * largeSize;
		for (File file : allFiles) {
			if (smallSizeInKB <= file.length() && file.length() <= largeSizeInKB) {
				allFilesFiltered.add(file);
			}
		}
		return allFilesFiltered;
	}
}
