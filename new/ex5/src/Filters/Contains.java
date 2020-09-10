package Filters;

import java.io.File;
import java.util.ArrayList;

/**
 * a class that represents the contain filter
 */
public class Contains implements GenericFilter {

    /*
	an array of filtered files, according to the class filter
	 */
    private final ArrayList<File> allFilesFiltered = new ArrayList<>();

    /**
     * adds a file iff its the argument is containd in the file's name
     * @param allFiles - an array containing all files in some folder
     * @param filterArgs - the arguments the filter is being provided with
     * @return - the filtered files
     */
    @Override
    public ArrayList<File> filter(ArrayList<File> allFiles, String[] filterArgs) {
        for (File file : allFiles) {
            if (file.getName().contains(filterArgs[1])) {
                allFilesFiltered.add(file);
            }
        }
        return allFilesFiltered;
    }
}
