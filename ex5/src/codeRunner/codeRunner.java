package codeRunner;

import Sections.CommandFileParser;
import Sections.Section;
import Sections.SectionFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

/**
 * this class runs the program entirely
 */
public class codeRunner {

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

	public static void main(String[] args) throws Exception{
		int validArgs = 2;
		if (args.length < validArgs) {
			System.err.println(""); //TODO: add error msg
		} else if (args.length > validArgs) {
			System.err.println("");
		} else {
			try{
				String sourceDir = args[0];
				String commandFile = args[1];
				ArrayList<File> allFiles = new codeRunner().dir2array(sourceDir);
				ArrayList<String> commandData = new CommandFileParser(commandFile).generateCommandData();
				ArrayList<Section> allSections = new SectionFactory().generateAllSections(commandData);
				for(Section section:allSections){
				}
			}
			catch (Exception e){
				System.err.println(" "+e);
			}
		}


	}
}
