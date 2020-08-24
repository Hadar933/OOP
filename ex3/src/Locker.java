import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;


public class Locker {
	private static final int SUCCESS = 0;
	private static final int ADDITION_ERROR = -1;
	private static final int LTS_ERROR = 1;
	private static final int REMOVE_ERROR = -1;
	private static final String CANNOT_DELETE_MSG1 = "Error: Your request cannot be completed at this time." +
													 " Problem: the locker does not contain ";
	private static final String CANNOT_ADD_MSG1 = "Error: Your request cannot be completed at this time." +
												  " Problem: no room for ";
	private static final String NEG_VALUE_ERR = "Error: Your request cannot be completed at this time. " +
												"Problem: cannot remove a negative number of items of type";
	private static final String ERROR_MSG_PREFIX = "items of type ";
	private static final String LTS_MSG = "Warning: Action successful, but has caused items to be moved to" +
										  "storage";


	/*
	 * list of constraints between items
	 */
	private final Item[][] constraints;

	/*
	 * maximum capacity associated with the locker
	 */
	private final int capacity;
	/*
	 * long term storage object associated with the locker
	 */
	private final LongTermStorage lts;

	/*
	 * the items that are stored in the locker: key = item's type, value = number of items
	 */
	private final Map<String, Integer> inventory;

	/*
	current capacity of the locker
	 */

	private final int availableCapacity;


	/**
	 * constructor for a Locker instance
	 * @param lts - long term storage associated with the locker
	 * @param capacity - the maximum capacity of the locker
	 * @param constraints - the items that must not be stored together in this locker
	 */
	public Locker(LongTermStorage lts, int capacity, Item[][] constraints) {

		this.capacity = capacity;
		this.constraints = constraints;
		this.lts = lts;
		this.inventory = new HashMap<>();
		this.availableCapacity = capacity;
	}

	/*
	 * checks if the item is in the constraints and weve already have the paired item of the given item
	 * stored in our locker ( in that case we cannot store both of them)
	 * @param item - some item to check its constraints
	 * @return - true: both item cannot be stored together. false: otherwise
	 */
	private boolean itemInConstraints(Item item) {
		for (Item[] tuple : constraints) {
			if (tuple[0] == item) { // meaning the item is the first element of some tuple in the constraints
				{
					if (inventory.get(tuple[1].getType()) != null) {
						return true;
					}
				}
			} else if (tuple[1] == item) { // meaning the item is the second element of some tuple in the
				// constraints
				{
					if (inventory.get(tuple[0].getType()) != null) {
						return true;
					}
				}
			}
		}
		return false;
	}


	/**
	 * adds n items of the given type to the locker
	 * @param item - some Item object to add
	 * @param n - number of items to insert
	 * @return - 0: successfully added n items. -1: could not add n items. 1: if the action causes to be
	 * moved
	 * 		to lts  (yet accomodation is possible)
	 */
	public int addItem(Item item, int n) {
		double totalVolume = n * item.getVolume();
		double halfCapacity = (double) getCapacity() / 2;
		if (itemInConstraints(item)) { // cannot add items that are in the constraints list
			System.out.println(CANNOT_ADD_MSG1 + n + ERROR_MSG_PREFIX + item.getType());
			return ADDITION_ERROR;
		} else { // items are not in the constraints list

			// CASE I - locker cannot contain n items
			if (totalVolume > getCapacity()) {
				System.out.println(CANNOT_ADD_MSG1 + n + ERROR_MSG_PREFIX + item.getType());
				return ADDITION_ERROR;
			}
			// CASE II - adding n items causes storing in lts and lts CAN contain said n items
			int currentVolume = 0;
			if (inventory.containsKey(item.getType())) {
				currentVolume = inventory.get(item.getType());
			}
			if (currentVolume + totalVolume > halfCapacity &&
				currentVolume + totalVolume <= lts.getCapacity()) {
				lts.addItem(item, n);
				System.out.println(LTS_MSG);
				return LTS_ERROR;
			}
			// CASE III - adding n items causes storing in lts and lts CANNOT contain said n items
			if (currentVolume + totalVolume > halfCapacity &&
				currentVolume + totalVolume > lts.getCapacity()) {
				System.out.println(CANNOT_ADD_MSG1 + n + ERROR_MSG_PREFIX + item.getType());
				return ADDITION_ERROR;
			}
			// CASE IV - adding n items is possible
			if (currentVolume + totalVolume < halfCapacity) {
				inventory.put(item.getType(), inventory.get(item.getType()) + n);
				return SUCCESS;
			}
		}
		return SUCCESS;
	}


	/**
	 * removes n items from the locker
	 * @param item - some item to remove
	 * @param n - n copies to remove
	 * @return - 0: success. -1: there are less than n items in the locker or n is negative
	 */
	public int removeItem(Item item, int n) {
		if (inventory.get(item.getType()) < n) { // not enough items in inventory
			System.out.println(CANNOT_DELETE_MSG1+n+ERROR_MSG_PREFIX+item.getType());
			return(REMOVE_ERROR);
		}
		if(n<0){
			System.out.println(NEG_VALUE_ERR);
			return(REMOVE_ERROR);
		}
		if( inventory.get(item.getType())==n){ // we have exactly n items - so we remove the item completely
			inventory.remove(item.getType());
		}
		else{
			inventory.put(item.getType(), inventory.get(item.getType()) - n);
		}
		return SUCCESS;
	}

	public int getItemCount(String type) {
		return 0;
	}

	public Map<Item, Integer> getInventory() {
		return inventory;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getAvailableCapacity() {
		return availableCapacity;
	}

}
