package filters;

import java.io.File;
import java.util.ArrayList;

/**
 * a class that represents the executable filter
 */
public class Executable implements Generic {

	/*
	an array of filtered files, according to the class filter
 	*/
	private final ArrayList<File> allFilesFiltered = new ArrayList<>();

	/**
	 * if arg is YES - adds the file if it is executable. if arg is False - the opposite
	 * @param allFiles - an array containing all files in some folder
	 * @param filterArgs - the arguments the filter is being provided with
	 * @return - the filtered files
	 */
	@Override
	public ArrayList<File> filter(ArrayList<File> allFiles, String[] filterArgs) {
		for (File file : allFiles) {
			if ((filterArgs[1].equals("YES") && file.canExecute()) ||
				(filterArgs[1].equals("NO") && !file.canExecute())) {
				allFilesFiltered.add(file);
			}
		}
		return allFilesFiltered;
	}
}
