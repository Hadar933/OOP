package Filters;

/**
 * a factory class for a filter object
 */
public class FilterFactory {

	/**
	 * this method generates a filter instance based on given name
	 * @param filter- some filter provided in the command file
	 * @return - an object corresponding to the filter name
	 */
	public Generic generateFilter(String[] filter){
		String filterName = filter[0];
		switch (filterName){
		case "between":
			return new Between();
		case "contains":
			return new Contains();
		case "executable":
			return new Executable();
		case "file":
			return new File();
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
