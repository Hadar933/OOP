/**
 * This class implements an Hash-set using Closed hashing.
 */
public class ClosedHashSet extends SimpleHashSet {

    private final int ACTIONFAILED = - 2;
    /**
     * Holds an array of Object instances which act as the Hash-table.
     */
    private Object[] table;
    /**
     * A default constructor. Constructs a new empty table with initial capacity.
     * upper load factor(0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
        this.table = new Object[this.tableCapacity];
    }

    /**
     * Constructs a new empty table with the specified load factors, and the default initial
     * capacity (16).
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        this.table = new Object[this.tableCapacity];
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one. Duplicate values
     * are ignored. The new table has the default value of initial capacity (16), upper load factor (0.75),
     * and lower load factor(0.25).
     * @param data - Values to add to the set.
     */
    public ClosedHashSet(java.lang.String[] data){
        super(DEFAULT_HIGHER_CAPACITY, DEFAULT_LOWER_CAPACITY);
        this.table = new Object[this.tableCapacity];
        for(String strValue : data){
            if(this.contains(strValue)){
                continue;
            }
            this.add(strValue);
        }
    }

    /**
     * Add a specified element to the set if its not already in it.
     * @param newValue New value to add to the set.
     * @return - False if newValue already exists in the set.
     */
    public boolean add(java.lang.String newValue){
        if(this.contains(newValue)){
            return false;
        }
        if(this.checkLoadFactor(INCREASETABLE) == INCREASETABLE){ // checks future load factor before adding.
            this.resizeTable(INCREASETABLE);
        }
        int clampedIndex = this.clamp(newValue.hashCode());
        if(clampedIndex == ACTIONFAILED){ //should never happen, just in case.
            return false;
        }
        this.table[clampedIndex] = newValue;
        this.tableSize++;
        return true;
    }

    /**
     * This method implements an abstract clamp method from SimpleHashSet.
     * @param index - The hashcode of the item we would like to add.
     * @return - An int representing the fitting index for the item.
     */
    protected int clamp(int index){
        for(int i = 0; i < this.tableCapacity; i ++){
            int clampedIndex = (index + (i + i*i))/2 & (this.tableCapacity - 1);
            if(!(this.table[clampedIndex] instanceof String) || this.table[clampedIndex] == null){
                return clampedIndex;
            }
        }
        return ACTIONFAILED;
        }

    /**
     * Look for a specified value in the set, searches using
     * @param searchVal Value to search for.
     * @return - True if searchVal is found in the set, False otherwise.
     */
    public boolean contains(java.lang.String searchVal) {
        if(this.size() == 0){
            return false;
        }
        for (int i = 0; i < this.tableCapacity; i++) {
            int tempIndex = searchVal.hashCode();
            int clampedIndex = (tempIndex + (i + i*i))/2 & (this.tableCapacity - 1);
            if(this.table[clampedIndex] == null){
                break;
            }
            if(this.table[clampedIndex].equals(searchVal)){
                return true;
            }
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return - True if toDelete is found an deleted, False otherwise.
     */
    public boolean delete(java.lang.String toDelete){
        char deleteChar = 'k';
        if(!this.contains(toDelete)){
            return false;
        }
        int deleteIndex = this.getItemIndex(toDelete);
        if(this.table[deleteIndex].equals(toDelete)){
            this.table[deleteIndex] = deleteChar;
            this.tableSize--;
        }
        if (this.checkLoadFactor(DECREASETABLE) == DECREASETABLE && this.tableCapacity != 1){
            this.resizeTable(DECREASETABLE);
        }
        return true;
    }

    /**
     * @return - The number of elements currently in the set.
     */
    public int size(){
        return this.tableSize;
    }

    /**
     * @return - The current number of cells of the table (its capacity).
     */
    public int capacity(){
        return this.table.length;
    }

    /**
     * This method is in charge of resizing the table according to the need, receives a value which reflects
     * if an increase or a decrease is needed . always resizes by 2, doesn't matter if to a larger or a
     * smaller table.
     * @param resizeValue - An indicative value which lets the method know if it should resize to a bigger or
     *                    smaller table.
     */
    private void resizeTable(int resizeValue){
        int NEWTABLESIZE = 0;
        if(resizeValue == INCREASETABLE){
            Object[] tempTable = this.table;
            this.table = new Object[this.tableCapacity * 2]; // creates new table, double the size.
            this.tableSize = NEWTABLESIZE;
            this.tableCapacity = this.table.length; //updates tableCapacity;
            for(int j = 0; j < tempTable.length; j++ ){
                if(tempTable[j] instanceof String){
                    //avoids adding the chars which mark deleted values;
                    this.add((String)tempTable[j]); //casts object to String.
                }
            }
        }
        else if(resizeValue == DECREASETABLE){
            Object[] tempTable = this.table;
            this.table = new Object[this.tableCapacity / 2];
            this.tableSize = NEWTABLESIZE;
            this.tableCapacity = this.table.length;
            for(int i = 0; i < tempTable.length; i++){
                if(tempTable[i] instanceof String){
                    //avoids adding the chars which mark deleted values;
                    this.add((String)tempTable[i]); //casts Object to string.
                }
            }
        }
    }

    /**
     * This method iterates over the table using a quadratic probing function, returns the first empty
     * index it could find, otherwise returns -2 which means the table is fool, even though it should never
     * happen.
     * @param searchItem - The item which we are searching for.
     * @return - The free index it finds, otherwise -2.
     */
    private int getItemIndex(java.lang.String searchItem){
        for(int k = 0; k < this.tableCapacity; k++){
            int tempIndex = (searchItem.hashCode() +
                    (k + k*k))/2 & (this.tableCapacity - 1);
            if(searchItem.equals(this.table[tempIndex])){
                return tempIndex;
            }
        }
        return ACTIONFAILED; // defaulted value which shows the search has failed.
    }


}
