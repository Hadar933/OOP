import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ClosedHashSetTest.class,
        OpenHashSetTest.class,
        MatanTests.class
                    })

public class testsRunner {
}
