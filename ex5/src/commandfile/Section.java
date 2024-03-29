package commandfile;

import java.util.ArrayList;

/**
 * a class that represents either a filter section of an order section, as given in the command file
 */
public class Section {

	/*
	default order
	 */
	private static final String ABS = "abs";

	/*
	default filter
	 */
	private static final String[] ALL = {"all"};

	/*
	the data of filters and orders itself
	 */
	private String[] filter;
	private String order;

	/*
	boolean value represents if a filter is concatenated with #not
	or if an order is concatenated with #reverse
	 */
	private boolean isFilterNot;
	private boolean isOrderReverse;

	/*
	all errors to be printed in the section
	 */
	private final ArrayList<String> errors;

	/**
	 * initializes the data of a section
	 */
	public Section() {
		this.isFilterNot = false;
		this.isOrderReverse = false;
		this.filter = ALL;
		this.order = ABS;
		this.errors = new ArrayList<String>();
	}

	/*
	#not getter
	 */
	public boolean isFilterNot() {
		return isFilterNot;
	}

	/*
	#reverse getter
	 */
	public boolean isOrderReverse() {
		return isOrderReverse;
	}

	/*
	filter getter
	 */
	public String[] getFilter() {
		return filter;
	}

	/*
	order getter
	 */
	public String getOrder() {
		return order;
	}


	/*
	errors list getter
	 */
	public ArrayList<String> getErrors() {
		return errors;
	}

	/*
	order setter
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/*
	filter setter
	 */
	public void setFilter(String[] filter) {
		this.filter = filter;
	}

	/*
	reverse setter
	 */
	public void setOrderReverse(boolean orderReverse) {
		isOrderReverse = orderReverse;
	}

	/*
	errors setter
	 */
	public void setErrors(String error) {
		this.errors.add(error);
	}

	/*
	not setter
	 */
	public void setFilterNot(boolean filterNot) {
		isFilterNot = filterNot;
	}


}
