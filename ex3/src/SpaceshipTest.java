import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import static org.junit.Assert.*;

public class SpaceshipTest {
	private static final int SUCCESS = 0;
	private static final int INVALID_ID = -1;
	private static final int INVALID_CAPACITY = -2;
	private static final int REACHED_MAX_LOCKER_CAPACITY = -3;
	private Spaceship spaceship;
	private final String name = "Abc";
	private final int[] crewIDs = {1,2,3,4,5};
	private final int numOfLockers = 10;
	private final Item[][] constraints = ItemFactory.getConstraintPairs();

	@Before
	public void initializeSpaceship(){
		this.spaceship = new Spaceship(name,crewIDs,numOfLockers,constraints);
	}

	@Test
	public void testInitialization() {
		assertNotNull("test 1 failed", spaceship);
	}
}
