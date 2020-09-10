package commandfile;

/**
 * a class that represents an exception related to a section
 */
public class SectionException extends Exception {
	public SectionException(String type) {
		super(type + " missing");
	}
}
