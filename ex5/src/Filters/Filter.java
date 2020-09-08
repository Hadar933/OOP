package Filters;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/** This class filters files. */
public class Filter {

    HashMap<String, Filters.GenericFilter> filters = new HashMap<>();

    public Filter(){
        filters.put("greater_than", new GreaterThan());
        filters.put("between", new Between());
        filters.put("smaller_than", new SmallerThan());
        filters.put("file", new FileFilter());
        filters.put("contains", new Contains());
        filters.put("prefix", new Prefix());
        filters.put("suffix", new Suffix());
        filters.put("writable", new Writable());
        filters.put("executable", new Executable());
        filters.put("hidden", new Hidden());
        filters.put("all", new All());
    }

    /**
     * This method removes (filters) requested files from a file array (helper to filterFiles).
     * @param unfilteredArray the array to filter.
     * @param toDelete files to remove from the unfiltered files array.
     * @return filtered array (after removing).
     */
    private ArrayList<File> removeFromFileArray(ArrayList<File> unfilteredArray, ArrayList<File> toDelete) {
        ArrayList<File> filteredArray = new ArrayList<>();
        for (File file : unfilteredArray) {
            if (toDelete.contains(file)) {
                continue;
            }
            filteredArray.add(file);
        }
        return filteredArray;
    }

    public ArrayList<File> filterFiles(ArrayList<File> unfilteredArray, String[] filter, boolean not) {
        ArrayList<File> filteredArray = new ArrayList<>();
        if (filters.containsKey(filter[0])) {
            filteredArray = filters.get(filter[0]).filter(unfilteredArray, filter);
        }
        return not ? removeFromFileArray(unfilteredArray, filteredArray) : filteredArray; ////
    }

}
