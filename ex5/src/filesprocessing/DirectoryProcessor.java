package filesprocessing;

import Filters.FilterFactory;
import Orders.CompareFactory;
import Sections.CommandFileParser;
import Sections.Section;
import Sections.SectionFactory;
import Helpers.MergeSort;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * this class runs the program entirely
 */
public class DirectoryProcessor {

	/**
	 * generates an array of files from a given directory
	 * @param sourceDir - a source directory
	 */
	private ArrayList<File> dir2array(String sourceDir) {
		ArrayList<File> result = new ArrayList<File>();
		File files = new File(sourceDir);
		for (File file : Objects.requireNonNull(files.listFiles())) { // iterate if value dir isn't empty
			if (!file.isDirectory()) {
				result.add(file);
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		int validArgs = 2;
		if (args.length < validArgs) {
			System.err.println(""); //TODO: add error msg
		} else if (args.length > validArgs) {
			System.err.println("");
		} else {
			try {
				String sourceDir = args[0];
				String commandFile = args[1];
				ArrayList<String> commandData = new CommandFileParser(commandFile).generateCommandData();
				ArrayList<File> allFiles = new DirectoryProcessor().dir2array(sourceDir);
				ArrayList<Section> allSections = new SectionFactory().generateAllSections(commandData);
				FilterFactory filterFactory = new FilterFactory();

				for (Section section : allSections) {
					ArrayList<File> result = filterFactory.generateFilter(section.getFilter())
							.filter(allFiles, section.getFilter());
					new MergeSort().mergeSort(result,0,result.size()-1,
											  new CompareFactory().generateComparator(section.getOrder()));
					for(String error: section.getErrors()){
						System.err.println(error);
					}
				}

			} catch (Exception e) {
				System.err.println(" " + e);
			}
		}


	}
}
