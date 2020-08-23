import org.junit.*;
import static  org.junit.Assert.*;


public class MatanTests{
    private OpenHashSet myOpenSet;
    private ClosedHashSet myClosedSet;

    @Before
    public void createTestObjects(){
        myOpenSet = new OpenHashSet();
        myClosedSet = new ClosedHashSet();
    }

    @Test
    public void checkConstructorsOpen(){
        OpenHashSet myOpenSet1 = new OpenHashSet();
        assertEquals(0, myOpenSet1.size());
        assertEquals(16, myOpenSet1.capacity());

        OpenHashSet myOpenSet2 = new OpenHashSet(0.5f, 0.2f);
        assertEquals(0, myOpenSet2.size());
        for (int i=0; i<10; i++)
            assertTrue(myOpenSet2.add("hello"+i));
        assertEquals(32, myOpenSet2.capacity());

        String[] myArray = {"hello1", "hello2", "hello3"};
        OpenHashSet myOpenSet3 = new OpenHashSet(myArray);
        assertEquals(3, myOpenSet3.size());
        assertTrue(myOpenSet3.contains("hello1"));
        assertTrue(myOpenSet3.contains("hello2"));
        assertTrue(myOpenSet3.contains("hello3"));
    }


    @Test
    public void checkConstructorsClose(){
        ClosedHashSet myClosedSet1 = new ClosedHashSet();
        assertEquals(0, myClosedSet1.size());
        assertEquals(16, myClosedSet1.capacity());

        ClosedHashSet myClosedSet2 = new ClosedHashSet(0.5f, 0.2f);
        assertEquals(0, myClosedSet2.size());
        for (int i=0; i<10; i++)
            assertTrue(myClosedSet2.add("hello"+i));
        assertEquals(32, myClosedSet2.capacity());

        String[] myArray = {"hello1", "hello2", "hello3"};
        ClosedHashSet myClosedSet3 = new ClosedHashSet(myArray);
        assertEquals(3, myClosedSet3.size());
        assertTrue(myClosedSet3.contains("hello1"));
        assertTrue(myClosedSet3.contains("hello2"));
        assertTrue(myClosedSet3.contains("hello3"));
    }

    @Test
    public void testAddingToOpen(){
        myOpenSet.add("hello");
        assertTrue(myOpenSet.contains("hello"));
        assertEquals(1, myOpenSet.size());
    }

    @Test
    public void testAddingToClose(){
        assertTrue(myClosedSet.add("hello"));
        assertTrue(myClosedSet.contains("hello"));
        assertEquals(1, myClosedSet.size());
    }


    @Test
    public void testDeletingFromOpen(){
        myOpenSet.add("hello");
        assertTrue(myOpenSet.delete("hello"));
        assertFalse(myOpenSet.contains("hello"));
        assertEquals(0, myOpenSet.size());
    }

    @Test
    public void testDeletingFromClose(){
        myClosedSet.add("hello");
        assertTrue(myClosedSet.delete("hello"));
        assertFalse(myClosedSet.contains("hello"));
        assertEquals(0, myClosedSet.size());
    }

    @Test
    public void testAddingTwice(){
        myOpenSet.add("hello");
        assertFalse(myOpenSet.add("hello"));
        assertEquals(1, myOpenSet.size());
    }

    @Test
    public void checkCapacityIncrease(){
        for (int i=0; i<13; i++)
            assertTrue(myOpenSet.add("hello"+i));
        assertEquals(13, myOpenSet.size());
        assertEquals(32, myOpenSet.capacity());
    }

    @Test
    public void checkCapacityDecrease(){
        myOpenSet.add("hello");
        myOpenSet.delete("hello");
        assertEquals(8, myOpenSet.capacity());
    }
}