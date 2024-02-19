import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.Before;

/**
 * This class contains Junit tests to test all methods inside of the SkipList
 * class.
 * 
 * @author Vihaan Ambre
 * @version 1.0
 * 
 */
public class SkipListTest {
    private SkipList<Integer, String> skipList;

    /**
     * Sets up objects for testing
     */
    @Before
    public void setUp() {
        skipList = new SkipList<>();
    }
    /**
     * tests insert method
     */
    @Test
    public void testInsert() {
        skipList.insert(new KVPair<>(1, "One"));
        skipList.insert(new KVPair<>(2, "Two"));
        assertEquals(2, skipList.size());
    }
    /**
     * tests search method
     */
    @Test
    public void testSearch() {
        skipList.insert(new KVPair<>(1, "One"));
        skipList.insert(new KVPair<>(2, "Two"));
        ArrayList<KVPair<Integer, String>> result = skipList.search(1);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("One", result.get(0).getValue());
    }
    /**
     * tests removebykey method
     */
    @Test
    public void testKeyRemove() {
        skipList.insert(new KVPair<>(1, "One"));
        skipList.insert(new KVPair<>(2, "Two"));
        KVPair<Integer, String> removed = skipList.remove(1);
        assertNotNull(removed);
        assertEquals("One", removed.getValue());
        assertEquals(1, skipList.size());
    }
    /**
     * tests remove by value method
     */
    @Test
    public void testValueRemove() {
        skipList.insert(new KVPair<>(1, "One"));
        skipList.insert(new KVPair<>(3, "Three"));
        KVPair<Integer, String> removed = skipList.removeByValue("Three");
        assertNotNull(removed);
        assertEquals(3, (int) removed.getKey());
        assertEquals(2, skipList.size());
    }
    /**
     * tests dump method
     */
    @Test
    public void testDump() {
        // Set up a new ByteArrayOutputStream to capture output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out; // Hold the original System.out
        // Set System.out to our stream
        System.setOut(new PrintStream(outContent));
        // Insert some elements into the skip list
        skipList.insert(new KVPair<>(1, "One"));
        skipList.insert(new KVPair<>(2, "Two"));
        skipList.insert(new KVPair<>(3, "Three"));
        // Call the dump function, which prints to System.out
        skipList.dump();
        // Use contains or pattern matching to
        // validate the output since levels are random and unpredictable
        assertTrue(outContent.toString().contains("SkipList dump:"));
        assertTrue(outContent.toString().contains("SkipList size is: 3"));
        assertTrue(outContent.toString().contains("(1, One)"));
        assertTrue(outContent.toString().contains("(2, Two)"));
        assertTrue(outContent.toString().contains("(3, Three)"));
        // Optionally, check for more specific patterns if necessary
        // Reset System.out to its original stream
        System.setOut(originalOut);
    }

    /**
     * tests iterator method
     */
    @Test
    public void testIterator() {
        skipList.insert(new KVPair<>(1, "One"));
        Iterator<KVPair<Integer, String>> iterator = skipList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("One", iterator.next().getValue());
        assertFalse(iterator.hasNext());
    }
}