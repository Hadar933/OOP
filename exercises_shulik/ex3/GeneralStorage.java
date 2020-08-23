import oop.ex3.spaceship.Item;
import java.util.HashMap;
import java.util.Map;

public abstract class GeneralStorage {
    protected static final int SUCCESS = 0;
    protected static final int FAIL = -1;
    protected static final int SUCCESSLTS = 1;
    protected static final int CONTRADICTINGITEMS = -2;
    protected  static final String ITEMSOFTYPEMSG = " items of type ";
    protected static final String ERRORMSG = "Error: Your request cannot be completed at this time. ";
    protected static final String NOROOMMSG = "Problem: no room for ";
    protected static final String REMOVEERROR = "Problem: the locket does not contain ";
    protected static final String NEGATIVENUMERROR = "Problem: cannot remove a negative number of items of " +
            "type ";
    protected static final String CONTRAITEMSMSG1 = "Problem: the locker cannot contain items of type ";
    protected static final String CONTRAITEMSMSG2 = " as it contains a contradicting item";
    protected static final String WARNINGMSG = "Warning: Action successful, but has caused" +
            " items to be moved to storage";


    /**
     * Maps out the items of the locker, Strings act as keys while the values are the amount.
     */
    protected Map<String, Integer> storageMap ;
    /**
     * The maximum volume this locker is capable of holding.
     */
    private final int maxCapacity;

    /**
     * Holds the current free volume of the locker.
     */
    private int currentCapacity;

    /**
     * the constructor for each of the classes.
     * @param capacity - the capacity of the storage we would like to initialize, for LTS its 1000 by default.
     */
    public GeneralStorage( int capacity) {
        this.maxCapacity = capacity;
        this.currentCapacity = capacity;
        this.storageMap = new HashMap<>();
    }

    /**
     * this method adds an item to the locker, according to the amount of free capacity and its restrictions.
     * this method serves as a super for Locker and LongTerm classes and combines some of their code. only
     * tries to add the item according to the the free capacity, each of the sub - classes hold an modified
     * version to fit their specific needs.
     * @param item - the item we would like to add into storage.
     * @param n - the amount of that item we would like to add.
     * @return - 0 if the item was added successfully -1 otherwise.
     */
    public int addItem(Item item, int n){
        int totalUnits = item.getVolume() * n;
        if (totalUnits > this.getAvailableCapacity()){ //not enough rooms in locker.
            System.out.println(ERRORMSG +
                    NOROOMMSG + n + ITEMSOFTYPEMSG + item.getType());
            return FAIL;
        }
        if(this.storageMap.containsKey(item.getType())){
            this.storageMap.put(item.getType(), n + this.storageMap.get(item.getType()));

        }
        else if(!this.storageMap.containsKey(item.getType())){ //first time an item of that kind is added.
            this.storageMap.put(item.getType(), n);

        }
        this.currentCapacity -= item.getVolume() * n; //changes the capacity according to the volume taken.
        return SUCCESS;
        }


    public int removeItem(Item item, int n){
        if(n < 0){
            System.out.println(ERRORMSG +
                    NEGATIVENUMERROR + item.getType());
            return FAIL;
        }
        else if(!this.storageMap.containsKey(item.getType()) || this.getItemCount(item.getType()) < n ){
            System.out.println(ERRORMSG +
                    REMOVEERROR + n + ITEMSOFTYPEMSG + item.getType());
            return FAIL;
        }
        else {
            this.storageMap.put(item.getType(), this.storageMap.get(item.getType()) - n);
            if(storageMap.get(item.getType()) == 0){ //the item is no longer in storage.
                storageMap.remove(item.getType());
                this.freedCapacity(item.getVolume() * n);
                return SUCCESS;// .
            }
            this.freedCapacity(item.getVolume() * n);
            return SUCCESS;// .
        }
    }

    /**
     * this method accesses the hash map with the key to a specific item type and the value returned is the
     * count of that item.
     * @param type - The type of item we are looking for.
     * @return - an int representing the amount of item of that type.
     */
    public int getItemCount(String type){
        if(storageMap.containsKey(type)) {
            return this.storageMap.get(type);
        }
        return 0;
    }

    /**
     * this method returns the Hash map of all item types and their respective quantities;
     */
    public Map<String, Integer> getInventory(){
        return this.storageMap;
    }

    /**
     * @return - The total amount of capacity which the locker was initialized with.
     */
    public int getCapacity(){
        return this.maxCapacity;

    }

    /**
     * @return - the lockers available capacity, i.e. how many free storage units are left.
     */
    public int getAvailableCapacity(){
        return this.currentCapacity;
    }

    /**
     * this method updates the free capacity according to the volume freed by removal or moving to LTS.
     * @param amount - The amount of storage units to be made "free" again.
     */
    protected void freedCapacity(int amount) {
        this.currentCapacity += amount;

    }

}
