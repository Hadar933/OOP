import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * this class tests the class Locker
 */
public class LockerTest {
	private static final int SUCCESS = 0;
	private static final int CONSTRAINTS_ERROR = -2;
	private static final int ADDITION_ERROR = -1;
	private static final int LTS_ERROR = 1;
	private static final int REMOVE_ERROR = -1;

	private final LongTermStorage lts = new LongTermStorage();
	int capacity = 10;
	private Item[][] constraints;
	private Locker locker;
	private Item baseballBat;
	private Item helmetSize1;
	private Item helmetSize3;
	private Item engine;
	private Item football;

	/**
	 * this method initializes the data
	 */
	@Before
	public void initializeLocker() {
		this.constraints = ItemFactory.getConstraintPairs();
		this.locker = new Locker(lts, capacity, constraints);
		Item[] allItems = ItemFactory.createAllLegalItems();
		baseballBat = allItems[0];
		helmetSize1 = allItems[1];
		helmetSize3 = allItems[2];
		engine = allItems[3];
		football = allItems[4];
	}

	/**
	 * this method checks the initialization was made properly
	 */
	@Test
	public void testInitialization() {
		assertEquals(10, locker.getCapacity());
	}

	/**
	 * this method tests the addition of items to the locker
	 */
	@Test
	public void testAddItems() {
		//CASE 1 -  items with constraints:
		locker.addItem(baseballBat, 1);
		assertEquals("test 1 failed", CONSTRAINTS_ERROR, locker.addItem(football, 1));

		// resetting locker for further use
		locker = new Locker(lts, capacity, constraints);


		// CASE 2 - adding n items causes storing in lts and lts CAN contain said n items
		assertEquals("test 2 failed", LTS_ERROR, locker.addItem(engine, 1));
		assertEquals("test 2.1 failed", 10, locker.getCapacity());
		assertEquals("test 2.2 failed", 990, lts.getAvailableCapacity());

		//resetting inventory
		lts.resetInventory();
		assertEquals("test 3 failed", 1000, lts.getAvailableCapacity());

		// CASE 3 - adding n items causes storing in lts and lts CANNOT contain said n items
		assertEquals("test 4 failed", ADDITION_ERROR, locker.addItem(engine, 200));
		assertEquals("test 4.1 failed", 10, locker.getCapacity());
		assertEquals("test 4.2 failed", 1000, lts.getAvailableCapacity());

		// CASE 4 - consecutive addition of all items:
		locker = new Locker(lts, capacity, constraints);
		assertEquals("test 5 failed", SUCCESS, locker.addItem(baseballBat, 1));
		assertEquals("test 5.1 failed", SUCCESS, locker.addItem(helmetSize1, 1));
		assertEquals("test 5.2 failed", SUCCESS, locker.addItem(helmetSize3, 1));
		assertEquals("test 5.3 failed", ADDITION_ERROR, locker.addItem(engine, 1));
		assertEquals("test 5.4 failed", CONSTRAINTS_ERROR, locker.addItem(football, 1));
		assertEquals("test 5.5 failed", ADDITION_ERROR, locker.addItem(helmetSize3, 1000));
		assertEquals("test 5.6 failed", 0, locker.getAvailableCapacity());
		assertEquals("test 5.7 failed", 1000, lts.getAvailableCapacity());

		// CASE 5 - invalid inputs
		assertEquals("test 6 failed", ADDITION_ERROR, locker.addItem(helmetSize3, -1));
	}

	/**
	 * this method tests deletion of items from the locker
	 */
	@Test
	public void testDeleteItems() {
		lts.resetInventory();
		locker = new Locker(lts, capacity, constraints);

		//CASE 1 - not enough items in inventory ( 0 items or more - treated differently)
		assertEquals("test 7 failed", REMOVE_ERROR, locker.removeItem(baseballBat, 50));
		locker.addItem(baseballBat, 2);
		assertEquals("test 7.1 failed", REMOVE_ERROR, locker.removeItem(baseballBat, 50));
		assertEquals("test 7.2 failed", 6, locker.getAvailableCapacity());

		//CASE 2 - remove an exact number of items
		assertEquals("test 8 failed", SUCCESS, locker.removeItem(baseballBat, 2));
		assertEquals("test 8.1 failed", 10, locker.getAvailableCapacity());

		//CASE 3 - remove some number of items
		locker.addItem(baseballBat, 2);
		assertEquals("test 9 failed", SUCCESS, locker.removeItem(baseballBat, 1));
		assertEquals("test 9.1 failed", 8, locker.getAvailableCapacity());

		//CASE 4 - consecutive removal
		locker = new Locker(lts, capacity, constraints);
		lts.resetInventory();
		locker.addItem(baseballBat, 1);
		locker.addItem(helmetSize1, 1);
		locker.addItem(helmetSize3, 1);
		assertEquals("test 10 failed", SUCCESS, locker.removeItem(baseballBat, 1));
		assertEquals("test 10.1 failed", REMOVE_ERROR, locker.removeItem(helmetSize3, 10));
		assertEquals("test 10.2 failed", REMOVE_ERROR, locker.removeItem(helmetSize1, 2));
		assertEquals("test 10.3 failed", SUCCESS, locker.removeItem(helmetSize1, 1));
	}

	/**
	 * this method tests the inventory of the locker, as well as some other small methods  such as item count
	 */
	@Test
	public void testInventory() {
		locker = new Locker(lts, capacity, constraints);
		lts.resetInventory();
		assertEquals("test 11 failed", 0, locker.getInventory().size());
		locker.addItem(helmetSize1, 1);
		locker.addItem(baseballBat, 1);
		locker.addItem(helmetSize3, 1);
		locker.addItem(engine, 1);
		assertEquals("test 11.1 failed", 3, locker.getInventory().size());
		assertTrue("test 11.2 failed", locker.getInventory().containsKey(helmetSize1.getType()));
		assertTrue("test 11.3 failed", locker.getInventory().containsKey(baseballBat.getType()));
		assertFalse("test 11.3 failed", locker.getInventory().containsKey(engine.getType()));
		assertEquals("test 11.4 failed", 1, locker.getItemCount(helmetSize1.getType()));
		assertEquals("test 11.5 failed", 0, locker.getItemCount(engine.getType()));
		locker.removeItem(baseballBat, 1);
		assertFalse("test 11.6 failed", locker.getInventory().containsKey(baseballBat.getType()));
		assertEquals("test 11.7 failed", 0, locker.getItemCount(baseballBat.getType()));
	}

	/**
	 * when adding items of the same type, if we exceed 50% of the available storage, only 20% should remain
	 * in the locker. this method tests that feature
	 */
	@Test
	public void testPercentage() {
		locker = new Locker(lts, capacity, constraints);
		lts.resetInventory();

		// adding three basket balls (volume 6) should move 2 basketballs from the locker to the lts
		locker.addItem(baseballBat, 2);
		assertEquals("test 12 failed", 2, locker.getItemCount(baseballBat.getType()));
		locker.addItem(baseballBat, 1);
		assertEquals("test 12.1 failed", 1, locker.getItemCount(baseballBat.getType()));
		assertEquals("test 12.2 failed", 1, lts.getItemCount(baseballBat.getType()));
		locker.addItem(baseballBat, 1);
		assertEquals("test 12.3 failed", 2, locker.getItemCount(baseballBat.getType()));
		locker.addItem(baseballBat, 1);
		assertEquals("test 12.3 failed", 1, locker.getItemCount(baseballBat.getType()));
	}


}