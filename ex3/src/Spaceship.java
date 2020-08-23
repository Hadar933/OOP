import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public abstract class Spaceship {
	private final int SUCCESS = 0;
	private final int INVALID_ID = -1;
	private final int INVALID_CAPACITY = -2;
	private final int REACHED_MAX_LOCKER_CAPACITY = -3;

	/**
	 * list of all crew ids
	 */
	private final int[] crewIDs;

	/**
	 * the number of lockers in the ship
	 */
	private final int numOfLockers;

	/**
	 * list of constraints between items
	 */
	private final Item[][] constraints;

	/**
	 * name of the ship
	 */
	private final String name;

	/**
	 * long term storage object associated with the spaceship
	 */
	private LongTermStorage lts;

	/**
	 * a map of all lockers in the ship. key = crewID, value = Locker
	 */
	private Map<Integer, Locker> allLockers;

	/**
	 * initiates a Spaceship instance
	 * @param name - ships name
	 * @param crewIDs - list of all crew IDs
	 * @param numOfLockers - the number of lockers the ship contains
	 * @param constraints - the constraints on the items that are allowed stored together
	 */
	public Spaceship(String name, int[] crewIDs, int numOfLockers, Item[][] constraints) {
		this.name = name;
		this.crewIDs = crewIDs;
		this.numOfLockers = numOfLockers;
		this.constraints = constraints;
		this.allLockers = new HashMap<>();
	}

	/**
	 * @return - the long term storage obj associated with that Spaceship
	 */
	public LongTermStorage getLongTermStorage() {
		return lts;
	}

	/**
	 * creates a locker obj and adds it as part of the spaceship's storage
	 * @param crewID - the id to which the new locker belongs
	 * @param capacity - the maximum nubmer of items that can be stored in the new locker
	 * @return - -1: invalid id. -2: invalid capacity. -3: reached max amount of lockers allowed. 0: locker
	 * 		added succesfully
	 */
	public int createLocker(int crewID, int capacity) {
		if (crewID < 0) {
			return INVALID_ID;
		}
		//TODO: add cases -2 and -3
		Locker newLocker = new Locker(lts, capacity, constraints);
		allLockers.put(crewID, newLocker);
		return SUCCESS;
	}

	/**
	 * @return - an array with teh crew's ids
	 */
	public int[] getCrewIDs() {
		return crewIDs;
	}

	/**
	 * @return - an array of the lockers, whose length is numoflockers
	 */
	public Locker[] getLockers() {
		int index = 0;
		Locker[] lockersArray = new Locker[numOfLockers];
		for(Locker locker: allLockers.values()){
			lockersArray[index] = locker;
			index++;
		}
		return lockersArray;
	}

}
