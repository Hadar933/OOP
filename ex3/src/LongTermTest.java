import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * a class that tests the long term storage class
 */
public class LongTermTest {
	private static final int CAPACITY_SIZE = 1000;
	private static final int ADDITION_ERROR = -1;
	private static final int SUCCESS = 0;
	private LongTermStorage lts;
	Item[][] constraints;
	Item baseballBat;
	Item helmetSize1;
	Item helmetSize3;
	Item engine;
	Item football;

	/**
	 * this method initializes the data
	 */
	@Before
	public void lockerTest() {
		this.constraints = ItemFactory.getConstraintPairs();
		Item[] allItems = ItemFactory.createAllLegalItems();
		this.lts = new LongTermStorage();
		baseballBat = allItems[0];
		helmetSize1 = allItems[1];
		helmetSize3 = allItems[2];
		engine = allItems[3];
		football = allItems[4];
		for (Item item : allItems) {
			System.out.println(item);
		}
	}

	/*
	tests the initialization of the data
	 */
	@Test
	public void testInitialization() {
		assertNotNull("test 1 failed", lts);
	}

	/**
	 * this method tests the add item process
	 */
	@Test
	public void testAddItem() {
		// CASE 1 - not enough room in lts
		assertEquals("test 2 failed", ADDITION_ERROR, lts.addItem(engine, 200));
		// CASE 2- negative n
		assertEquals("test 2.1 failed", ADDITION_ERROR, lts.addItem(engine, -1));
		// CASE 3 - adding item that isnt already in the lts
		assertEquals("test 2.2 failed", SUCCESS, lts.addItem(engine, 1));
		// CASE 4 - adding item that is already in the lts
		assertEquals("test 2.3 failed", SUCCESS, lts.addItem(engine, 1));
		// CASE 5 - consecutive insertions
		lts.resetInventory();
		assertEquals("test 2.4 failed", SUCCESS, lts.addItem(baseballBat, 1));
		assertEquals("test 2.5 failed", SUCCESS, lts.addItem(helmetSize1, 4));
		assertEquals("test 2.6 failed", SUCCESS, lts.addItem(helmetSize3, 2));
		assertEquals("test 2.7 failed", SUCCESS, lts.addItem(baseballBat, 3));
	}

	/**
	 * this method cheks some basic functions such as getItemCount, getAvailableCapacity and getInventory
	 */
	@Test
	public void testBasicMethods() {
		lts.resetInventory();
		// item count and available capacity:
		lts.addItem(baseballBat, 10);
		assertEquals("test 3 failed", 10, lts.getItemCount(baseballBat.getType()));
		assertEquals("test 3.1 failed", 980, lts.getAvailableCapacity());
		lts.addItem(helmetSize1, 5);
		assertEquals("test 3.2 failed", 5, lts.getItemCount(helmetSize1.getType()));
		assertEquals("test 3.3 failed", 965, lts.getAvailableCapacity());
		lts.addItem(engine, 90);
		assertEquals("test 3.4 failed", 65, lts.getAvailableCapacity());
		assertEquals("test 3.5 failed", 0, lts.getItemCount(football.getType()));

		//get inventory:
		assertFalse("test 3.6 failed", lts.getInventory().containsKey(football.getType()));
		assertTrue("test 3.6 failed", lts.getInventory().containsKey(helmetSize1.getType()));
		assertTrue("test 3.6 failed", lts.getInventory().containsKey(baseballBat.getType()));
		lts.resetInventory();
		assertEquals("test 3.6 failed", 1000, lts.getAvailableCapacity());
	}
}
