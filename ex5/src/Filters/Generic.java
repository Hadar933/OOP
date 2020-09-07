package Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * an interface representing some type of filter
 */
public interface Generic {

	/**
	 * a method that filters all files according to some filter
	 * @param allFiles - an array containing all files in some folder
	 * @param filterArgs - the arguments the filter is being provided with
	 * @return - the filtered files
	 */
	public ArrayList<File> filter(ArrayList<File> allFiles, String[] filterArgs);
}
