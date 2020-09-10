package Filters;

import java.util.ArrayList;

/**
 * a class that represents the file filter
 */
public class FileFilter implements GenericFilter {
    /*
	an array of filtered files, according to the class filter
	 */
    private final ArrayList<java.io.File> allFilesFiltered = new ArrayList<>();

    /**
     * adds a file iff its name is the name provided by argument
     * @param allFiles - an array containing all files in some folder
     * @param filterArgs - the arguments the filter is being provided with
     * @return - the filtered files
     */
    @Override
    public ArrayList<java.io.File> filter(ArrayList<java.io.File> allFiles, String[] filterArgs) {
        for (java.io.File file : allFiles) {
            if (filterArgs[1].equals(file.getName())) {
                allFilesFiltered.add(file);
            }
        }
        return allFilesFiltered;
    }
}
