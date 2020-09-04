/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet implements SimpleSet {

	/**
	 * some capacity and size values will be initialized with 0
	 */
	protected static final int INITIAL_SIZE = 0;

	/**
	 * the capacity cannot be 1
	 */
	protected static final int CAPACITY_THRESHOLD = 1;
	/**
	 * a factor by which we increase or decrease a hash set's size
	 */
	protected static final int SIZE_FACTOR = 2;

	/**
	 * Describes the lower load factor of a newly created hash set.
	 */
	protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;

	/**
	 * Describes the higher load factor of a newly created hash set.
	 */
	protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;

	/**
	 * Describes the capacity of a newly created hash set.
	 */
	protected static final int INITIAL_CAPACITY = 16;

	/**
	 * current size of the hash set
	 */
	protected int currentSize;

	/**
	 * the capacity of the hash set
	 */
	protected int capacity;

	/**
	 * the lower capacity to the load factor
	 */
	protected float lowerCapacity;

	/**
	 * the higher capacity to the load factor
	 */
	protected float higherCapacity;

	/**
	 * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and
	 * DEFAULT_HIGHER_CAPACITY.
	 */
	protected SimpleHashSet() {
		this.currentSize = INITIAL_SIZE;
		this.capacity = INITIAL_CAPACITY;
		this.higherCapacity = DEFAULT_HIGHER_CAPACITY;
		this.lowerCapacity = DEFAULT_LOWER_CAPACITY;
	}

	/**
	 * Constructs a new hash set with capacity INITIAL_CAPACITY.
	 * @param upperLoadFactor - the upper load factor before rehashing
	 * @param lowerLoadFactor - the lower load factor before rehashing
	 */
	protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
		this.currentSize = INITIAL_SIZE;
		this.capacity = INITIAL_CAPACITY;
		this.higherCapacity = upperLoadFactor;
		this.lowerCapacity = lowerLoadFactor;
	}

	/**
	 * each inheriting class will implement the relevant capacity
	 * @return - The current capacity (number of cells) of the table.
	 */
	public abstract int capacity();

	/**
	 * Clamps hashing indices to fit within the current table capacity we set this to abstract - the
	 * inheriting classes will implement this method
	 * @param index - an index before clamping
	 * @return - relevant index value. (different value for open or closed)
	 */
	protected abstract int clamp(int index);

	/**
	 * @return The lower load factor of the table.
	 */
	protected float getLowerLoadFactor() {
		return lowerCapacity;
	}

	/**
	 * @return The higher load factor of the table.
	 */
	protected float getUpperLoadFactor() {
		return higherCapacity;
	}

	/*
	a method that checks if we need to increase the size of the table
	in order to insert more elements
	 */
	protected boolean needToAddSpace() {
		float loadFactor = (float) (size() + 1) / capacity;
		return loadFactor > higherCapacity;
	}

	/*
	a method that checks if we need to decrease the size of the table
	when removing elements
	 */
	protected boolean needToRemoveSpace() {
		float loadFactor = (float) size() / capacity;
		return loadFactor < lowerCapacity;
	}
}
