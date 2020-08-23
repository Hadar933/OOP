import org.junit.*;
import static org.junit.Assert.*;
import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;


public class LongTermTest {
    private Item[] itemArray;
    private LongTermStorage LTSTestObj;
    private Locker locker1;
    private Locker locker2;

    /**
     * BeforeClass method, called before the whole set of tests runs.
     */
    @Before
    public void createTestObjects(){
        this.itemArray = ItemFactory.createAllLegalItems();
        this.locker1 = new Locker(10);
        this.locker2 = new Locker(20);
        this.LTSTestObj = locker1.getLTS();
    }

    @Before
    public void clearStorage(){
        this.LTSTestObj.resetInventory();
    }

    @Test
    public void testConstructor(){
        assertEquals("Constructor test1 Failed", 1000, this.LTSTestObj.getCapacity());
        assertEquals("Constructor test1 Failed", 1000,this.LTSTestObj.getAvailableCapacity());
    }
    @Test
    public void testAddItem(){
        assertEquals("AddItem1.0 Failed",0,this.LTSTestObj.addItem(this.itemArray[0], 2));
        assertEquals("AddItem1.1 Failed",0,this.LTSTestObj.addItem(this.itemArray[3], 3));
        assertEquals("AddItem1.2 Failed",0,this.LTSTestObj.addItem(this.itemArray[4], 5));
        assertEquals("AddItem1.3 Failed",
                - 1,this.LTSTestObj.addItem(this.itemArray[3], 100));
        assertEquals("AddItem1.4 Failed", 946,this.LTSTestObj.getAvailableCapacity());
        this.LTSTestObj.resetInventory();
        assertEquals("AddItem1.5 Failed", 1000, this.LTSTestObj.getAvailableCapacity());
        this.locker1.addItem(this.itemArray[3], 1); //moves 1 engine to LTS
        this.locker2.addItem(this.itemArray[3], 2);//moves 2 engines to LTS
        assertEquals("AddItem1.6 Failed", 970, this.LTSTestObj.getAvailableCapacity());
        assertEquals("AddItem1.7 Failed",
                -1,this.LTSTestObj.addItem(this.itemArray[3],100));
        this.LTSTestObj.resetInventory();
        this.locker1.addItem(this.itemArray[0], 3); //two bats move straight to LTS.
        this.locker2.addItem(itemArray[2], 3); // all three helmets of size 3 move to LTS.
        assertEquals("AddItem1.8 Failed", 2,
                this.LTSTestObj.getItemCount(this.itemArray[0].getType()));
        assertEquals("AddItem1.9 Failed", 3,
                this.LTSTestObj.getItemCount(this.itemArray[2].getType()));
    }
    @Test
    public void testGetItemCount(){
        assertEquals("getItemCount1.0 Failed", 0 ,
                this.LTSTestObj.getItemCount(itemArray[2].getType())); //checks empty LTS.
        this.LTSTestObj.addItem(itemArray[0], 5); //add straight to LTS.
        this.LTSTestObj.addItem(itemArray[3], 10);
        assertEquals("getItemCount1.1 Failed",5,
                this.LTSTestObj.getItemCount(this.itemArray[0].getType()));
        assertEquals("getItemCount1.2 Failed", 10,
                this.LTSTestObj.getItemCount(this.itemArray[3].getType()));
        this.locker1.addItem(itemArray[1], 1);
        assertEquals("getItemCount1.2 Failed",
                0, this.LTSTestObj.getItemCount(this.itemArray[1].getType()));
        this.locker1.addItem(this.itemArray[1], 1);//moves 2 helmets to LTS.
        assertEquals("getItemCount1.2 Failed",2,
                this.LTSTestObj.getItemCount(this.itemArray[1].getType()) );
        assertEquals("getItemCount1.1 Failed",5,
                this.LTSTestObj.getItemCount(this.itemArray[0].getType()));
        this.locker2.addItem(this.itemArray[0], 3);// nothing changes.
        assertEquals("getItemCount1.1 Failed", 5,
                this.LTSTestObj.getItemCount(this.itemArray[0].getType()));
    }

}
