import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import static org.junit.Assert.*;

import java.util.Map;


public class LockerTest {
	private static final int SUCCESS = 0;
	private static final int ADDITION_ERROR = -1;
	private static final int LTS_ERROR = 1;
	private static final int REMOVE_ERROR = -1;

	LongTermStorage lts = new LongTermStorage();
	int capacity = 10;
	Item[][] constraints;
	private Item[] allItems;
	private Locker locker;
	Item baseballBat;
	Item helmetSize1;
	Item helmetSize3;
	Item engine;
	Item football;

	@Before
	public void lockerTest() {
		this.constraints = ItemFactory.getConstraintPairs();
		this.locker = new Locker(lts, capacity, constraints);
		this.allItems = ItemFactory.createAllLegalItems();

		baseballBat = allItems[0];
		helmetSize1 = allItems[1];
		helmetSize3 = allItems[2];
		engine = allItems[3];
		football = allItems[4];
	}

	@Test
	public void testInitialization() {
		assertEquals(10, locker.getCapacity());
	}

	@Test
	public void testAddItems() {
		//CASE 1 -  items with constraints:
		locker.addItem(baseballBat,1);
		assertEquals("test 1 failed",ADDITION_ERROR, locker.addItem(football,1));

		// resetting locker for further use
		locker = new Locker(lts,capacity,constraints);


		// CASE 2 - adding n items causes storing in lts and lts CAN contain said n items
		assertEquals("test 2 failed",LTS_ERROR,locker.addItem(engine,1));
		assertEquals("test 2.1 failed",10,locker.getCapacity());
		assertEquals("test 2.2 failed",990,lts.getAvailableCapacity());

		//resetting inventory
		lts.resetInventory();
		assertEquals("test 3 failed",1000,lts.getAvailableCapacity());

		// CASE 3 - adding n items causes storing in lts and lts CANNOT contain said n items
		assertEquals("test 4 failed",ADDITION_ERROR,locker.addItem(engine,200));
		assertEquals("test 4.1 failed",10,locker.getCapacity());
		assertEquals("test 4.2 failed",1000,lts.getAvailableCapacity());

		// CASE 4 - consecutive addition of all items:
		locker = new Locker(lts,capacity,constraints);
		assertEquals("test 5 failed",SUCCESS,locker.addItem(baseballBat,1));
		assertEquals("test 5.1 failed",SUCCESS,locker.addItem(helmetSize1,1));
		assertEquals("test 5.2 failed",LTS_ERROR,locker.addItem(helmetSize3,1));
		assertEquals("test 5.3 failed",LTS_ERROR,locker.addItem(engine,1));
		assertEquals("test 5.4 failed",ADDITION_ERROR,locker.addItem(football,1));
		assertEquals("test 5.5 failed",ADDITION_ERROR,locker.addItem(helmetSize3,1000));
		assertEquals("test 5.6 failed",5,locker.getAvailableCapacity());
		assertEquals("test 5.7 failed",985,lts.getAvailableCapacity());

		// CASE 5 - invalid inputs
		assertEquals("test 6 failed",ADDITION_ERROR, locker.addItem(helmetSize3,-1));
	}

	@Test
	public void testDeleteItems(){
		lts.resetInventory();
		locker = new Locker(lts,capacity,constraints);

		//CASE 1 - not enough items in inventory ( 0 items or more - treated differently)
		assertEquals("test 7 failed",REMOVE_ERROR,locker.removeItem(baseballBat,50));
		locker.addItem(baseballBat,2);
		assertEquals("test 7.1 failed",REMOVE_ERROR,locker.removeItem(baseballBat,50));
		assertEquals("test 7.2 failed",6,locker.getAvailableCapacity());

		//CASE 2 - remove an exact number of items
		assertEquals("test 8 failed",SUCCESS,locker.removeItem(baseballBat,2));
		assertEquals("test 8.1 failed",10,locker.getAvailableCapacity());

		//CASE 3 - remove some number of items
		locker.addItem(baseballBat,2);
		assertEquals("test 9 failed",SUCCESS,locker.removeItem(baseballBat,1));
		assertEquals("test 9.1 failed",8,locker.getAvailableCapacity());

		//CASE 4 - consecutive removal
		locker = new Locker(lts,capacity,constraints);
		lts.resetInventory();
		locker.addItem(baseballBat,1);
		locker.addItem(helmetSize1,1);
		locker.addItem(helmetSize3,1);
		assertEquals("test 10 failed",SUCCESS,locker.removeItem(baseballBat,1));
		assertEquals("test 10.1 failed",REMOVE_ERROR,locker.removeItem(helmetSize3,1));
		assertEquals("test 10.2 failed",REMOVE_ERROR,locker.removeItem(helmetSize1,2));
		assertEquals("test 10.3 failed",SUCCESS,locker.removeItem(helmetSize1,1));
		
	}
}