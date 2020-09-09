package Filters;

/**
 * a factory for a filter instance
 */
public class FilterFactory {
	public GenericFilter generateFilter(String[] filter){
		String filterName = filter[0];
		switch (filterName){
		case "between":
			return new Between();
		case "contains":
			return new Contains();
		case "executable":
			return new Executable();
		case "file":
			return new FileFilter();
		case "greater_than":
			return new GreaterThan();
		case "hidden":
			return new Hidden();
		case "prefix":
			return new Prefix();
		case "smaller_than":
			return new SmallerThan();
		case "suffix":
			return new Suffix();
		case "writable":
			return new Writable();
		default:
			return new All();
		}
	}
}
