/**
 * a class that extends the simple hash set using a closed hash set implementaion
 */
public class ClosedHashSet extends SimpleHashSet {

	private static final int BAD_INDEX = -1;

	/*
	an array of genetic objects
	 */
	private Object[] hashTable;

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
	 * @param upperLoadFactor - The upper load factor of the hash table.
	 * @param lowerLoadFactor - The lower load factor of the hash table.
	 */
	public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
		super(upperLoadFactor, lowerLoadFactor);
		hashTable = new Object[capacity];
	}

	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
	 * factor (0.75) and lower load factor (0.25).
	 */
	public ClosedHashSet() {
		super();
		hashTable = new Object[capacity];

	}

	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
	 * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
	 * lower load factor (0.25
	 * @param data Values to add to the set.
	 */
	public ClosedHashSet(java.lang.String[] data) {
		super();
		hashTable = new Object[capacity];
		for (String value : data) {
			add(value);
		}
	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	public int capacity() {
		return hashTable.length;
	}

	/**
	 * for closed hashing we use the value (hash(e)+(i+i^2)/2) & (tableSize-1) where & is bit-wise AND
	 * operator
	 * @param index - an index before clamping
	 * @return - valid clamped index
	 */
	protected int clamp(int index) {
		for (int i = 0; i < capacity; i++) {
			int clamp = ((index + (i + i * i) / 2) & (capacity - 1));
			if (hashTable[clamp] == null || !(hashTable[clamp] instanceof String)) {
				return clamp;
			}
		}
		return BAD_INDEX;
	}


	/**
	 * Add a specified element to the set if it's not already in it.
	 * @param newValue New value to add to the set
	 * @return False iff newValue already exists in the set
	 */
	public boolean add(java.lang.String newValue) {
		if (!contains(newValue)) {
			int index = clamp(newValue.hashCode());
			if (needToAddSpace()) {
				updateSize(hashTable.length * SIZE_FACTOR);
			}
			hashTable[index] = newValue;
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
		if (size() != INITIAL_SIZE) {
			int index = searchVal.hashCode();
			for (int i = 0; i < capacity; i++) {
				int clamp = (index + (i + i * i) / 2) & (capacity - 1);
				if(hashTable[clamp]!=null){
					if (hashTable[clamp].equals(searchVal)) {
						return true;
					}
				}
			}
		}
		return false;

	}

	/*
	returns the index of an item in the data
	 */
	private int getIndex(java.lang.String item) {
		for (int i = 0; i < capacity; i++) {
			int clamp = (item.hashCode() + (i + i * i) / 2) & (capacity - 1);
			if (item.equals(hashTable[clamp])) {
				return clamp;
			}
		}
		return BAD_INDEX;
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	public boolean delete(java.lang.String toDelete) {
		if (contains(toDelete)) {
			int index = getIndex(toDelete);
			hashTable[index] = BAD_INDEX; // a strictly non-String arbitrary assignment
			currentSize--;
			if(needToRemoveSpace() || capacity != CAPACITY_THRESHOLD){
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

	/*
	 * updates the size of the table to be newSize
	 * @param newSize - some size which is a multiply of 2
	 */
	private void updateSize(int newSize) {
		Object[] hashTableCopy = hashTable.clone();
		hashTable = new Object[newSize];
		currentSize = INITIAL_SIZE;
		capacity = hashTable.length;
		for (Object o : hashTableCopy) {
			if (o instanceof String) {
				add((String) o);
			}
		}
	}
}
