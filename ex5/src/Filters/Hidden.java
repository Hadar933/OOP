package Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * a class that represents the hidden filter
 */
public class Hidden implements Generic {

	/*
	an array of filtered files, according to the class filter
 	*/
	private final ArrayList<File> allFilesFiltered = new ArrayList<>();

	/**
	 * if arg is YES - adds the file if it is hidden. if arg is False - the opposite
	 * @param allFiles - an array containing all files in some folder
	 * @param filterArgs - the arguments the filter is being provided with
	 * @return - the filtered files
	 */
	@Override
	public ArrayList<File> filter(ArrayList<File> allFiles, String[] filterArgs) {
		for (File file : allFiles) {
			if ((filterArgs[1].equals("YES") && file.isHidden()) ||
				(filterArgs[1].equals("NO") && !file.isHidden())) {
				allFilesFiltered.add(file);
			}
		}
		return allFilesFiltered;
	}
}
