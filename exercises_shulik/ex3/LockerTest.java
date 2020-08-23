import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;
import java.util.Map;
import static org.junit.Assert.*;


public class LockerTest {
    private Item[] itemArray; //an array of all legal items.
    private Locker LockerObj; //test object.

    @Before
    public void LockerTestObjects(){
        this.LockerObj = new Locker(10);
        this.itemArray = ItemFactory.createAllLegalItems();
    }

    /**
     * This method clears the storageMap before each test runs to keep the tests separated and avoid them
     * effecting each others outcome.
     */
    @Before
    public void clearBefore(){
        this.LockerObj.storageMap.clear();
    }

    @Test
    public void testConstructor(){
        assertEquals("Constructor test Failed", 10, LockerObj.getCapacity());
    }

    @Test
    public void testAddItem1(){
        assertEquals("AddItem1.0 Failed",1, LockerObj.addItem(this.itemArray[3], 1));
        //tries to add engine, moves it to LTS.
        assertEquals("AddItem1.1 Failed", 10,LockerObj.getAvailableCapacity());
        assertEquals("AddItem1.2 Failed",0, LockerObj.addItem(this.itemArray[0], 2));
        //Adds 2 bats, taking 4 storage units
        assertEquals("AddItem1.3 Failed", 6, LockerObj.getAvailableCapacity());
        assertEquals("AddItem1.4 Failed",1, LockerObj.addItem(this.itemArray[0], 1));
        //Adds 1 more bat, bats are now 60% of total volume.
        assertEquals("AddItem1.5 failed", 8, LockerObj.getAvailableCapacity());
        assertEquals("AddItem1.6 failed",-2 ,LockerObj.addItem(this.itemArray[4], 1));
        //tries to put a football with a bat.
        assertEquals("AddItem1.7 failed", 0, LockerObj.addItem(this.itemArray[2], 1));
        assertEquals(0, LockerObj.addItem(this.itemArray[1], 1));
        assertEquals("AddItem1.8 Failed", 0, LockerObj.getAvailableCapacity());
        assertEquals("AddItem1.9 Failed", -1, LockerObj.addItem(this.itemArray[2], 1));
        assertEquals("AddItem2.0 Failed", -1, LockerObj.addItem(this.itemArray[3], 2));
        // more then the volume, nothing is added
    }
    @Test
    public void  testRemoveItem(){
        LockerObj.addItem(this.itemArray[1],1 );//Adds 1 small helmets, total of 3 units.
        LockerObj.addItem(this.itemArray[0], 2);// Adds 2 bats, total of 4 units.
        assertEquals("RemoveTest1.1 failed",0,LockerObj.removeItem(this.itemArray[1],1));
        assertEquals("RemoveTest1.2 failed",0, LockerObj.removeItem(this.itemArray[0],1));
        assertEquals("RemoveTest1.3 failed",0, LockerObj.removeItem(this.itemArray[0],1));
        assertEquals("RemoveTest1.4 failed",10, LockerObj.getAvailableCapacity());
        assertEquals("RemoveTest1.5 failed",-1,LockerObj.removeItem(this.itemArray[3],1));
        LockerObj.addItem(this.itemArray[4], 1);
        assertEquals("RemoveTest1.6 failed",-1,LockerObj.removeItem(this.itemArray[4],2));
        assertEquals("RemoveTest1.6 failed", 6, LockerObj.getAvailableCapacity());
        assertEquals("RemoveTest1.8 failed", -1, LockerObj.removeItem(itemArray[4], -1));
        assertEquals("RemoveTest1.9 Failed", 0, LockerObj.removeItem(itemArray[4],1));
        assertEquals(10, LockerObj.getAvailableCapacity());
    }

    @Test
    public void testItemCount(){
        LockerObj.addItem(this.itemArray[0], 2);
        LockerObj.addItem(this.itemArray[2], 1);
        assertEquals("itemCount1",2, LockerObj.getItemCount(this.itemArray[0].getType()));
        assertEquals("itemCount2",1, LockerObj.getItemCount(this.itemArray[2].getType()));
        LockerObj.removeItem(this.itemArray[0], 2);
        assertEquals("itemCount3",0, LockerObj.getItemCount(this.itemArray[0].getType()));
        assertEquals("itemCount4",1, LockerObj.getItemCount(this.itemArray[2].getType()));
        LockerObj.removeItem(this.itemArray[2], 1);
        assertEquals("itemCount5", 0 ,LockerObj.getItemCount(this.itemArray[2].getType()));
    }
    @Test
    public void testGetInventory(){
        Map<String, Integer> tempMap = this.LockerObj.storageMap; // empty map.
        assertEquals("inventoryTestFailed1",tempMap, this.LockerObj.getInventory());
        this.LockerObj.addItem(this.itemArray[2], 1);
        this.LockerObj.addItem(this.itemArray[0], 1); //now contains 1 big helmet and 1 bat.
        Map<String, Integer> currMap = this.LockerObj.storageMap;
        assertEquals("inventoryTestFailed2", currMap, this.LockerObj.getInventory());
    }
}
