/**
 * a class that extends the simple hash set using a closed hash set implementaion
 */
public class ClosedHashSet extends SimpleHashSet {

	/**
	 * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
	 * @param upperLoadFactor - The upper load factor of the hash table.
	 * @param lowerLoadFactor - The lower load factor of the hash table.
	 */
	public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {

	}

	/**
	 * A default constructor. Constructs a new, empty table with default initial capacity (16), upper load
	 * factor (0.75) and lower load factor (0.25).
	 */
	public ClosedHashSet() {

	}

	/**
	 * Data constructor - builds the hash set by adding the elements one by one. Duplicate values should be
	 * ignored. The new table has the default values of initial capacity (16), upper load factor (0.75), and
	 * lower load factor (0.25
	 * @param data  Values to add to the set.
	 */
	public ClosedHashSet(java.lang.String[] data) {

	}

	/**
	 * @return The current capacity (number of cells) of the table.
	 */
	@Override
	public int capacity() {
		return 0;
	}


	/**
	 * Add a specified element to the set if it's not already in it.
	 * @param newValue New value to add to the set
	 * @return False iff newValue already exists in the set
	 */
	public boolean add(java.lang.String newValue) {
		return false;
	}

	/**
	 * Look for a specified value in the set.
	 * @param searchVal Value to search for
	 * @return True iff searchVal is found in the set
	 */
	public boolean contains(java.lang.String searchVal) {
		return false;
	}

	/**
	 * Remove the input element from the set.
	 * @param toDelete Value to delete
	 * @return True iff toDelete is found and deleted
	 */
	public boolean delete(java.lang.String toDelete) {
		return false;
	}

	/**
	 * @return The number of elements currently in the set
	 */
	public int size() {
		return 0;
	}
}
