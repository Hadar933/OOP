package FileParsing;

public class SectionFactory {
	/*
	the number of arguments expected for the filters (with or without concatenated NOT at the end)
	 */
	private static final int TERNARY_OP_SIZE = 3;
	private static final int BINARY_OP_SIZE = 2;
	private static final int UNRAY_OP_SIZE = 1;
	private static final int TERNARY_NOT_OP_SIZE = 4;
	private static final int BINARY_NOT_OP_SIZE = 3;
	private static final int UNRAY_NOT_OP_SIZE = 2;

	/*
	minimum size of bytes
	 */
	private static final int MIN_SIZE = 0;

	/*
	relevant filter names
	 */
	private static final String NOT = "NOT";

	/**
	 * checks if the greater/smaller than filter is valid
	 * @param args - the filter data
	 * @return - true: valid. false: not valid
	 */
	private boolean isSizeValid(String[] args) {
		double size = Double.parseDouble(args[1]);
		if (args.length == BINARY_OP_SIZE && size >= MIN_SIZE) {
			return true;
		} else if (args.length == BINARY_NOT_OP_SIZE) {
			return size >= MIN_SIZE && args[2].equals(NOT);
		}
		return false;
	}

	/**
	 * checks if the between filter is valid
	 * @param args - the filter data
	 * @return - true: valid. false: not valid
	 */
	private boolean isBetweenValid(String[] args) {
		double size1 = Double.parseDouble(args[1]);
		double size2 = Double.parseDouble(args[2]);
		boolean validSize = size1 >= MIN_SIZE && size1 <= size2;
		if (args.length == TERNARY_OP_SIZE && validSize) {
			return true;
		} else if (args.length == TERNARY_NOT_OP_SIZE && validSize) {
			return args[3].equals(NOT);
		}
		return false;
	}
	

	/**
	 * checks if the file/contains/prefix/suffix filter is valid, simply by checking lengths
	 * @param args - the filter data
	 * @return - true: valid. false: not valid
	 */
	private boolean isStringFormatValid(String[] args) {
		return args.length == BINARY_OP_SIZE || (args.length == BINARY_NOT_OP_SIZE && args[2].equals(NOT));
	}


}
