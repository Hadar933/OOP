import oop.ex3.spaceship.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * a class that represents a Spaceship instance, with storage units
 */
public class Spaceship {
	private static final int SUCCESS = 0;
	private static final int INVALID_ID = -1;
	private static final int INVALID_CAPACITY = -2;
	private static final int REACHED_MAX_LOCKER_CAPACITY = -3;

	/*
	 * list of all crew ids
	 */
	private final int[] crewIDs;

	/*
	 * the number of lockers in the ship
	 */
	private final int numOfLockers;

	/*
	 * list of constraints between items
	 */
	private final Item[][] constraints;

	/*
	 * name of the ship
	 */
	private final String name;

	/**
	 * long term storage object associated with the spaceship
	 */
	private final LongTermStorage lts;

	/*
	 * an array of all lockers in the ship. key = crewID, value = Locker
	 */
	private final Locker[] storage;

	/*
	 * the current number of lockers in the spaceship
	 */
	private int currentNumOfLockers;

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
		this.lts = new LongTermStorage();
		this.storage = new Locker[numOfLockers];
		this.currentNumOfLockers = 0;
	}

	/**
	 * @return - the long term storage obj associated with that Spaceship
	 */
	public LongTermStorage getLongTermStorage() {
		return lts;
	}

	/*
	finds if some id is in the crew ids list
	 */
	private boolean idInArray(int crewID) {
		for (int id : crewIDs) {
			if (id == crewID) {
				return true;
			}
		}
		return false;
	}

	/**
	 * creates a locker obj and adds it as part of the spaceship's storage
	 * @param crewID - the id to which the new locker belongs
	 * @param capacity - the maximum nubmer of items that can be stored in the new locker
	 * @return - -1: invalid id. -2: invalid capacity. -3: reached max amount of lockers allowed. 0: locker
	 * 		added succesfully
	 */
	public int createLocker(int crewID, int capacity) {
		if (!idInArray(crewID)) {
			return INVALID_ID;
		}
		if (capacity < 0) {
			return INVALID_CAPACITY;
		}
		if (currentNumOfLockers == numOfLockers) {
			return REACHED_MAX_LOCKER_CAPACITY;
		}
		storage[currentNumOfLockers] = new Locker(lts, capacity, constraints);
		currentNumOfLockers++;
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
		return storage;
	}

}
