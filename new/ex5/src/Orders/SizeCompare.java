package Orders;

import java.io.File;
import java.util.Comparator;

/**
 * compares the size of two given files
 */
public class SizeCompare implements Comparator<File> {
	/**
	 * compares the size of two given files if the size is the same, compare by abs path
	 * @param file1 - some file
	 * @param file2 - some file
	 * @return - if size1 > size2: return val >0. if size1 < size2: return val <0. if size1 == size2: return
	 * 		val =0
	 */
	@Override
	public int compare(File file1, File file2) {
		if (file1.length() != file2.length()) { //not the same type
			return Double.compare(file1.length(), file2.length());
		} else { // same type
			return (file1.getAbsolutePath().compareTo(file2.getAbsolutePath()));
		}
	}
}
