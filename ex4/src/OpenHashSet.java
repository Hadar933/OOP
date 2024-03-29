import java.util.Arrays;
import java.util.LinkedList;

/**
 * a class that extends the simple hash set using an open hash set implementaion
 */
public class OpenHashSet extends SimpleHashSet {

	/*
	a class that enables accessing protected members
	 */
	public class facade extends CollectionFacadeSet {
		/*
		creating a wrapper
		 */
		private facade() {
			super(new LinkedList<java.lang.String>());
		}
	}

	/*
	defining a wrapper-class that has a linkedlist<string> and delegating methods to it
	and having an array of that class:
	 */
	private facade[] hashTable;

	/*
	a private method that initializes the hash table as an array of facades
	 */
	private void initializeHashTable(int facadeCapacity) {
		this.hashTable = new facade[facadeCapacity];
		for (int i = 0; i < hashTable.length; i++) {
			hashTable[i] = new facade();
		}
	}

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
	 * @param upperLoadFactor- The upper load factor of the hash table.
	 * @param lowerLoadFactor- The lower load factor of the hash table.
	 */
	public OpenHashSet(float upperLoadFactor, float lowerLoadFactor) {
		super(upperLoadFactor, lowerLoadFactor);
		initializeHashTable(capacity);
	}

	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
	 * factor (0.75) and lower load factor (0.25).
	 */
	public OpenHashSet() {
		super();
		initializeHashTable(capacity);
	}

	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
	 * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
	 * lower load factor (0.25).
	 * @param data- Values to add to the set.
	 */
	public OpenHashSet(java.lang.String[] data) {
		super();
		initializeHashTable(INITIAL_CAPACITY);
		for (String value : data) {
			add(value);
		}
	}

	/*
	 * updates the size of the table to be newSize
	 * @param newSize - some size which is a multiply of 2
	 */
	private void updateSize(int newSize) {
		currentSize = INITIAL_SIZE;
		facade[] hashTableCopy = hashTable.clone();
		hashTable = new facade[newSize];
		capacity = hashTable.length;
		for (int i = 0; i < capacity; i++) {
			hashTable[i] = new facade();
		}
		for (facade linkedList : hashTableCopy) {
			for (String subItem : linkedList.collection) {
				add(subItem);
			}
		}
	}

	/**
	 * for open hashing we use the return value of hash(e) & (tableSize-1) where & is bit-wise AND operator
	 * @param index - an index before clamping
	 * @return - valid clamped index
	 */
	@Override
	protected int clamp(int index) {
		return index & (capacity - 1);
	}

	/**
	 * Add a specified element to the set if it's not already in it.
	 * @param newValue New value to add to the set
	 * @return False iff newValue already exists in the set
	 */
	public boolean add(java.lang.String newValue) {
		if (!contains(newValue)) {
			if (needToAddSpace()) {
				updateSize(hashTable.length * SIZE_FACTOR);
			}
			int index = clamp(newValue.hashCode());
			hashTable[index].add(newValue);
			currentSize++;
			return true;
		}
		return false;
	}

	/**
	 * Look for a specified value in the set.
	 * @param searchVal Value to search for
	 * @return True iff searchVal is found in the set
	 */
	public boolean contains(java.lang.String searchVal) {
		int clamp = this.clamp(searchVal.hashCode());
		if(hashTable[clamp].size() == 0){
			return false;
		}
		return hashTable[clamp].contains(searchVal);
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	public boolean delete(java.lang.String toDelete) {
		if (contains(toDelete)) {
			int index = clamp(toDelete.hashCode());
			hashTable[index].delete(toDelete);
			currentSize--;
			if (capacity != CAPACITY_THRESHOLD && needToRemoveSpace()) {
				updateSize(hashTable.length / SIZE_FACTOR);
			}
			return true;
		}
		return false;
	}

	/**
	 * @return The number of elements currently in the set
	 */
	public int size() {
		return currentSize;
	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	@Override
	public int capacity() {
		return capacity;
	}

}
