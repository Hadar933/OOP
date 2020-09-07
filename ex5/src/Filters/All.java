package Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * a class that represents the all filter
 */
public class All implements Generic {

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
