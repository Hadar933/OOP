package Orders;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.util.Comparator;

/**
 * compares the type of two given files
 */
public class TypeCompare implements Comparator<File> {
	/**
	 * compares the type of two given files. the the type is the same compare by absolute path.
	 * @param file1 - some file
	 * @param file2 - some file
	 * @return - if arg1 > arg2: return val >0. if arg1 < arg2: return val <0. if arg1 == arg2: return val =0
	 */
	@Override
	public int compare(File file1, File file2) {
		if (!fileType(file1).equals(fileType(file2))) { //not the same type
			return fileType(file1).compareTo(fileType(file2));
		} else { // same type
			return (file1.getAbsolutePath().compareTo(file2.getAbsolutePath()));
		}
	}

	/**
	 * get the type of a file
	 * @param file - some file
	 * @return - file type (as string)
	 */
	private String fileType(File file) {
		String delimiter = "\\.";
		String path = file.getAbsolutePath();
		String[] split = path.split(delimiter);
		return split[split.length - 1];
	}
}
