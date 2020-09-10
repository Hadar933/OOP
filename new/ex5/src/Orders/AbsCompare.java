package Orders;

import java.io.File;
import java.util.Comparator;

/**
 * compares the absolute path of two given files
 */
public class AbsCompare implements Comparator<File> {
	/**
	 * compares the absolute path of two given files
	 * @param file1 - some file
	 * @param file2 - some file
	 * @return - if path1 > path2: return val >0. if path1 < path2: return val <0. if path1 == path2: return
	 * val =0
	 */
	@Override
	public int compare(File file1, File file2) {
		return (file1.getAbsolutePath().compareTo(file2.getAbsolutePath()));
	}
}
