import oop.ex3.spaceship.Item;
import java.lang.Math;

/**
 * This class represents a single locker. a locker can have items added to it or removed from it. Its also
 * contains a finite amount of volume and can move items to a long term storage if all conditions are met.
 */
public class Locker extends GeneralStorage {


    /**
     * Holds the static data member which represents the LTS of the spaceship.
     */
    private static LongTermStorage LTS = new LongTermStorage();

    /**
     * Constructor for the Locker class.
     * @param MaxCapacity - The Given volume for the locker, cannot be changed after initialized.
     */
    public Locker(int MaxCapacity){
            super(MaxCapacity);
        }

    /**
     * This method overrides the super's method because it holds different conditions which are more strict
     * then the ones enforced by the super's method.
     * @param item - the item to add into the locker.
     * @param n - the amount of the same item we would like to add.
     * @return - returns 0 if the item was simply inserted. returns 1 if it caused moving some items into LTS,
     * returns -2 if a contradicting item tried to be placed. and returns -1 if the adding was unsuccessful.
     */
    @Override
    public int addItem(Item item, int n) {
        double halfOfCap =(double) this.getCapacity() / 2;
        if(storageMap.containsKey("baseball bat") && item.getType().equals("football") ||
                storageMap.containsKey("football") && item.getType().equals("baseball bat")){
            // checks for the existence of football or bat
            System.out.println(ERRORMSG +
                    CONTRAITEMSMSG1 + item.getType() + CONTRAITEMSMSG2);
            return CONTRADICTINGITEMS;
        }
        if(super.addItem(item, n) == SUCCESS){ //success in adding item.
            if(storageMap.get(item.getType()) * item.getVolume() > halfOfCap){
                // one item holds more then 50% of capacity
                int amountToStay = (int) Math.floor(0.2 * this.getCapacity() / item.getVolume());
                int amountToLTS = storageMap.get(item.getType()) - amountToStay;
                if(this.moveToLTS(item, amountToLTS)){
                    System.out.println(WARNINGMSG);
                    return SUCCESSLTS;
                }
                System.out.println(ERRORMSG +
                        NOROOMMSG + amountToLTS + ITEMSOFTYPEMSG + item.getType());
                return FAIL;// no room at LTS.
            }
            return SUCCESS; // added without sending to LTS.
        }
        return FAIL; // failed to add.
    }

    /**
     * This method serves as a helper for moving a specific amouint of items between the Locker and the LTS.
     * @param item - the item to be moved.
     * @param n - the amount of items to move.
     * @return - true if the move was successful, false otherwise.
     */
    protected boolean moveToLTS(Item item, int n){
        if(Locker.LTS.getAvailableCapacity() >= item.getVolume() * n){
            Locker.LTS.addItem(item, n);
            this.removeItem(item, n);
            return true;
        }
        return false; //moving items failed.
    }
    protected LongTermStorage getLTS(){
        return Locker.LTS;
    }


}





