package filters;

import java.io.File;
import java.util.ArrayList;

/**
 * a class that represents the prefix filter
 */
public class Prefix implements Generic {

	/*
	an array of filtered files, according to the class filter
 	*/
	private final ArrayList<File> allFilesFiltered = new ArrayList<>();

	/**
	 * adds a file iff its the argument is prefix of the file's name
	 * @param allFiles - an array containing all files in some folder
	 * @param filterArgs - the arguments the filter is being provided with
	 * @return - the filtered files
	 */
	@Override
	public ArrayList<File> filter(ArrayList<File> allFiles, String[] filterArgs) {
		for (File file : allFiles) {
			if (file.getName().startsWith(filterArgs[1])) {
				allFilesFiltered.add(file);
			}
		}
		return allFilesFiltered;
	}
}
