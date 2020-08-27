import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * a class that represents a locker, which is a part of the storage of a spaceship
 */
public class Locker {
	private static final double REMOVAL_VOLUME = 0.2; // 20% of the capacity should remain when moving items
	private static final int SUCCESS = 0;
	private static final int ADDITION_ERROR = -1;
	private static final int CONSTRAINTS_ERROR = -2;
	private static final int LTS_ERROR = 1;
	private static final int REMOVE_ERROR = -1;
	private static final String CONSTRAINTS_MSG1 = "Error: Your request cannot be completed at this time. " +
												   "Problem: the locker cannot contain items of type ";
	private static final String CONSTRAINTS_MSG2 = " ,as it contains a contradicting item";
	private static final String CANNOT_DELETE_MSG1 = "Error: Your request cannot be completed at this time."
													 + " Problem: the locker does not contain ";
	private static final String CANNOT_ADD_MSG1 = "Error: Your request cannot be completed at this time." +
												  " Problem: no room for ";
	private static final String NEG_VALUE_ERR = "Error: Your request cannot be completed at this time. " +
												"Problem: cannot remove a negative number of items of type";
	private static final String ERROR_MSG_PREFIX = " items of type ";
	private static final String LTS_MSG = "Warning: Action successful, but has caused items to be moved to" +
										  " storage";
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

	private int availableCapacity;

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
		this.inventory = new HashMap<String, Integer>();
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
			if (tuple[0].getType().equals(item.getType())) { // meaning the item is the first element of
				// some tuple in the constraints
				{
					if (inventory.containsKey(tuple[1].getType())) {
						return true;
					}
				}
			} else if (tuple[1].getType().equals(item.getType())) { // meaning the item is the second
				// element of some tuple in the constraints
				{
					if (inventory.containsKey(tuple[0].getType())) {
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
	 * 		to lts (yet accommodation is possible)
	 */
	public int addItem(Item item, int n) {
		double totalVolume = n * item.getVolume();
		double halfCapacity = (double) capacity / 2;
		if (itemInConstraints(item)) { //CASE 0 -  cannot add items that are in the constraints list
			System.out.println(CONSTRAINTS_MSG1 + item.getType() + CONSTRAINTS_MSG2);
			return CONSTRAINTS_ERROR;
		} else { // items are not in the constraints list

			if (n < 0 || totalVolume > availableCapacity) { // CASE I
				System.out.println(CANNOT_ADD_MSG1 + n + ERROR_MSG_PREFIX + item.getType());
				return ADDITION_ERROR;
			}
			// CASE II - adding n items causes storing in lts and lts CAN contain said n items
			int currentVolume = 0;
			if (inventory.containsKey(item.getType())) {
				currentVolume = inventory.get(item.getType()) * item.getVolume();
			}
			if (currentVolume + totalVolume > halfCapacity &&
				currentVolume + totalVolume <= lts.getAvailableCapacity()) {
				// removing items so that there are 20% left in the locker, and moving all the remaining
				// to the long term
				int nItemsToRemove = (int) (REMOVAL_VOLUME * capacity) / item.getVolume();
				if (inventory.containsKey(item.getType())) {
					inventory.put(item.getType(), inventory.get(item.getType()) - nItemsToRemove);
				}
				lts.addItem(item, n);
				System.out.println(LTS_MSG);
				return LTS_ERROR;
			}
			// CASE III - adding n items causes storing in lts and lts CANNOT contain said n items
			if (currentVolume + totalVolume > halfCapacity &&
				currentVolume + totalVolume > lts.getAvailableCapacity()) {
				System.out.println(CANNOT_ADD_MSG1 + n + ERROR_MSG_PREFIX + item.getType());
				return ADDITION_ERROR;
			}
			// CASE IV - adding n items is possible
			if (currentVolume + totalVolume <= halfCapacity) {
				if (inventory.containsKey(item.getType())) {
					inventory.put(item.getType(), inventory.get(item.getType()) + n);
				} else {
					inventory.put(item.getType(), n);
				}
				availableCapacity -= totalVolume;
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
		if (!inventory.containsKey(item.getType()) || inventory.get(item.getType()) < n) { // not
			// enough items in inventory
			System.out.println(CANNOT_DELETE_MSG1 + n + ERROR_MSG_PREFIX + item.getType());
			return (REMOVE_ERROR);
		}
		if (n < 0) {
			System.out.println(NEG_VALUE_ERR);
			return (REMOVE_ERROR);
		}
		if (inventory.get(item.getType()) == n) { //exactly n items - remove the item completely
			inventory.remove(item.getType());
		} else {
			inventory.put(item.getType(), inventory.get(item.getType()) - n);
		}
		availableCapacity += n * item.getVolume();
		return SUCCESS;
	}

	/**
	 * @param type - some type of an item
	 * @return - returns the amount of items of the given type are in the lts
	 */
	public int getItemCount(String type) {
		if (inventory.containsKey(type)) {
			return inventory.get(type);
		}
		return 0;
	}

	/**
	 * @return - returns the inventory
	 */
	public Map<String, Integer> getInventory() {
		return inventory;
	}

	/**
	 * @return the capacity of the locker
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return the available capacity of the locker
	 */
	public int getAvailableCapacity() {
		return availableCapacity;
	}

}
