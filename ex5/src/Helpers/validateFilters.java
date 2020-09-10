package Helpers;

import Filters.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * this class operates on an array of files and a given filter as followed: filter the relevant files
 * according to the specific given filter if a command #not hasn't been concatenated, return all the files
 * that were just filtered. otherwise return all the remaining files
 */
public class validateFilters {

	final static ArrayList<String> allFilters = new ArrayList<>(
			Arrays.asList("all", "between", "contains", "executable", "file", "greater_than", "hidden",
						  "prefix", "suffix", "smaller_than", "writable"));

	/*
	this method returns an array of all items that are in allFiles but not in allFilesFiltered
	 */
	private ArrayList<File> generateDeletedFiles(ArrayList<File> allFiles,ArrayList<File> allFilesFiltered){
		ArrayList<File> result = new ArrayList<File>();
		for(File file:allFiles){
			if(!allFilesFiltered.contains(file)){
				result.add(file);
			}
		}
		return result;
	}
	public ArrayList<File> filterFiles(ArrayList<File> allFiles,String[] filter, boolean isFilterNot){
		String filterName = filter[0];
		ArrayList<File> allFilesFiltered = new ArrayList<File>();
		if (allFilters.contains(filterName)){
			GenericFilter filterObject = new FilterFactory().generateFilter(filter);
			allFilesFiltered = filterObject.filter(allFiles,filter);
		}
		if(isFilterNot){
			return generateDeletedFiles(allFiles,allFilesFiltered);
		}
		else{
			return allFilesFiltered;
		}
	}

}
