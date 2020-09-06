package filters;

import java.io.File;
import java.util.ArrayList;

/**
 * a class that represents the executable filter
 */
public class All implements Generic {

	/*
	an array of filtered files, according to the class filter
 	*/
	private final ArrayList<File> allFilesFiltered = new ArrayList<>();

	/**
	 * simply returns the data untouched
	 * @param allFiles - an array containing all files in some folder
	 * @param filterArgs - the arguments the filter is being provided with
	 * @return - the filtered files
	 */
	@Override
	public ArrayList<File> filter(ArrayList<File> allFiles, String[] filterArgs) {
		return allFiles;
	}
}
