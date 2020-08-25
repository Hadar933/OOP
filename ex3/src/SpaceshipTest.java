import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * a class that tests the class spaceship
 */
public class SpaceshipTest {
	private static final int SUCCESS = 0;
	private static final int INVALID_ID = -1;
	private static final int INVALID_CAPACITY = -2;
	private static final int REACHED_MAX_LOCKER_CAPACITY = -3;
	private Spaceship spaceship;
	private final int[] crewIDs = {0, 1, 2, 3, 4, 5};
	private final Item[][] constraints = ItemFactory.getConstraintPairs();

	/**
	 * a method that initializes a new spaceship
	 */
	@Before
	public void initializeSpaceship() {
		String name = "Abc";
		int numOfLockers = 5;
		this.spaceship = new Spaceship(name, crewIDs, numOfLockers, constraints);
	}

	/**
	 * a method that tests the initialization of the data
	 */
	@Test
	public void testInitialization() {
		assertNotNull("test 1 failed", spaceship);
	}

	/**
	 * a method that tests the creation of new lockers
	 */
	@Test
	public void testCreateLocker() {
		// CASE 1 - invalid id
		assertEquals("test 2 failed", INVALID_ID, spaceship.createLocker(6, 5));
		// CASE 2 - invalid capacity
		assertEquals("test 2.1 failed", INVALID_CAPACITY, spaceship.createLocker(1, -1));
		// CASE 3 - basic addition of locker
		assertEquals("test 2.2 failed", SUCCESS, spaceship.createLocker(0, 1));
		// CASE 4 - reached maximum number of lockers:
		for (int i = 1; i < 5; i++) {
			spaceship.createLocker(i, i);
		}
		assertEquals("test 2.3 failed", REACHED_MAX_LOCKER_CAPACITY, spaceship.createLocker(0, 1));

	}

}
