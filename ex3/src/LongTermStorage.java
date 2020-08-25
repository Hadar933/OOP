import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * a class that represents the long term storage associated with a spaceship
 */
public class LongTermStorage {
	private static final int CAPACITY_SIZE = 1000;
	private static final int ADDITION_ERROR = -1;
	private static final int SUCCESS = 0;
	private static final String CANNOT_ADD_MSG1 = "Error: Your request cannot be completed at this time." +
												  " Problem: no room for ";
	private static final String ERROR_MSG_PREFIX = " items of type ";
	/*
	 * the items that are stored in the locker: key = item's type, value = number of items
	 */
	private final Map<String, Integer> inventory;

	private final int capacity;
	private int availableCapacity;

	/**
	 * a constructor for a lts instance
	 */
	public LongTermStorage() {
		this.inventory = new HashMap<String, Integer>();
		this.capacity = CAPACITY_SIZE;
		this.availableCapacity = capacity;
	}

	/**
	 * this method adds n items to the lts
	 * @param item - some item to add
	 * @param n - the amount of items to add
	 * @return - -1: cannot add the item. 0: added succesfuly
	 */
	public int addItem(Item item, int n) {
		double totalVolume = n * item.getVolume();
		if (totalVolume > availableCapacity || n < 0) {
			System.out.println(CANNOT_ADD_MSG1 + n + ERROR_MSG_PREFIX + item.getType());
			return ADDITION_ERROR;
		} else {
			if (inventory.containsKey(item.getType())) {
				inventory.put(item.getType(), inventory.get(item.getType()) + n);
			} else {
				inventory.put(item.getType(), n);
			}
			availableCapacity -= totalVolume;
			return SUCCESS;
		}
	}

	/**
	 * resets the inventory
	 */
	public void resetInventory() {
		inventory.clear();
		availableCapacity = capacity;
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
	 * @return the lts capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @return - the current available capacity of the lts
	 */
	public int getAvailableCapacity() {
		return availableCapacity;
	}
}



