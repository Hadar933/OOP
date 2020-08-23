public class LongTermStorage extends GeneralStorage {

    /**
     * Constructor for the LongTerm class. Calss the GeneralStorage constructor but with a defaulted value.
     */
    public LongTermStorage() {
        super(1000);
    }

    /**
     * This method resets the long-term storage's inventory. i.e. after it is called the inventory does not
     * contain any items.
     */
    public void resetInventory(){
        int freedVolume = this.getCapacity() - this.getAvailableCapacity();
        //counts the units which are about to free up
        this.storageMap.clear();//clears storage.
        this.freedCapacity(freedVolume);//updates the available
        }
    }



