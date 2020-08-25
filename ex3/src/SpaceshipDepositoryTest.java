import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * this class is responsible for running all of the tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
							SpaceshipTest.class, LongTermTest.class, LockerTest.class
					})

public class SpaceshipDepositoryTest {
}
