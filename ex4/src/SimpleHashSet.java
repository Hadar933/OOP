/**
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet implements SimpleSet {

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
		this.currentSize = 0;
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
		this.currentSize = 0;
		this.capacity = INITIAL_CAPACITY;
		this.higherCapacity = upperLoadFactor;
		this.lowerCapacity = lowerLoadFactor;
	}

	/**
	 * @return - The current capacity (number of cells) of the table.
	 */
	public int capacity() {
		return capacity;
	}

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
}
