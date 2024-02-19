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
 * This class contains Junit tests to test all methods
 * inside of the Database class.
 * 
 * @author Rami Benhamida
 * @version 1.0
 * 
 */
@SuppressWarnings("unused")
public class DatabaseTests {
    private Database database;
    /**
     * Sets up objects for testing
     * 
     * @param
     * 
     */
    @Before
    public void setUp() {
        database = new Database();
    }
    /**
     * Tests for inserting a valid rectangle
     */
    @Test
    public void testInsertValidRectangle() {
        // Create a valid rectangle
        Rectangle rectangle = new Rectangle(0, 0, 10, 10);
        Rectangle rectangle2 = new Rectangle(0, 0, 5, 5);
        KVPair<String, Rectangle> pair = 
                new KVPair<>("Rectangle1", rectangle);
        KVPair<String, Rectangle> pair2 = 
        		new KVPair<>("Rectangle2", rectangle2);
        ByteArrayOutputStream outContent = 
        		new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        

        // Perform insertion
        database.insert(pair);
        database.insert(pair2);

        database.dump();

        // Verify the output matches the expected output
        //assertEquals(expectedOutput, outContent.toString());    
        assertTrue(outContent.toString().contains(
        		"SkipList dump:"));
        assertTrue(outContent.toString().contains(
        		"SkipList size is: 2"));
        assertTrue(outContent.toString().contains(
        		"(Rectangle1, X: 0, Y: 0, Width: 10, Height: 10)"));
        assertTrue(outContent.toString().contains(
        		"(Rectangle2, X: 0, Y: 0, Width: 5, Height: 5)"));
    }
	
	/**
	 * Tests for inserting an invalid rectangle
	 */
	@Test
    public void testInsertInvalidRectangle() {
        // Create a valid rectangle
        Rectangle rectangle = new Rectangle(0, 0, -1, 10);
        KVPair<String, Rectangle> pair = new KVPair<>("Rectangle1", rectangle);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        

        // Perform insertion
        database.insert(pair);

        database.dump();
        

        // Verify the output matches the expected output
        //assertEquals(expectedOutput, outContent.toString());    
        assertTrue(outContent.toString().contains(
        		"SkipList dump:"));
        assertTrue(outContent.toString().contains(
        		"SkipList size is: 0"));
        assertTrue(outContent.toString().contains(
        		"Insert ERROR: Rectangle is invalid: "
        		+ "(Rectangle1, 0, 0, -1, 10"));
    }
	
	/**
	 * Tests for the remove method
	 */
	@Test
    public void testRemove() {
        // Create a valid rectangle
        Rectangle rectangle = new Rectangle(0, 0, 10, 10);
        KVPair<String, Rectangle> pair = new KVPair<>("Rectangle1", rectangle);
        
        Rectangle rectangle2 = new Rectangle(5, 1, 70, 70);
        KVPair<String, Rectangle> pair2 = new KVPair<>("Rectangle2", rectangle);
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        database.insert(pair2);
        // Perform insertion
        database.remove("Rectangle1");

        database.dump();
        

        // Verify the output matches the expected output   
        assertTrue(outContent.toString().contains(
        		"SkipList dump:"));
        assertTrue(outContent.toString().contains(
        		"SkipList size is: 1"));
        assertTrue(outContent.toString().contains(
        		"ERROR: Rectangle not found: Rectangle1"));
        
        database.insert(pair);
        
        database.dump();
        
        assertTrue(outContent.toString().contains(
        		"SkipList dump:"));
        assertTrue(outContent.toString().contains(
        		"SkipList size is: 2"));
        assertTrue(outContent.toString().contains(
        		"(Rectangle1, X: 0, Y: 0, Width: 10, Height: 10)"));
        
        database.remove("Rectangle1");
        assertTrue(outContent.toString().contains(
        		"Rectangle has been removed: (Rectangle1, 0, 0, 10, 10"));
        
        outContent.reset();
        
        database.insert(pair);
        database.remove(0, 0, 10, 10);
        
        database.insert(pair);
        
        database.remove(0, 0, 10, 10);
        
        assertTrue(outContent.toString().contains(
        		"Rectangle has been removed: (Rectangle1, 0, 0, 10, 10"));
        
        
    }
	
	/**
	 * Tests for the region search method
	 */
	@Test
	public void testRegionSearch() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 10);
        KVPair<String, Rectangle> pair = new KVPair<>("Rectangle1", rectangle);
        Rectangle rectangle2 = new Rectangle(0, 0, 5, 5);
        KVPair<String, Rectangle> pair2 = 
        		new KVPair<>("Rectangle2", rectangle2);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        database.regionsearch(0, 0, -1, 0);
		
        assertTrue(outContent.toString().contains(
        		"Search Region is Invalid: (0, 0, -1, 0)"));
        
        
        
        database.insert(pair);
        database.insert(pair2);
        
        database.regionsearch(0, 0, 20, 20);
        
        assertTrue(outContent.toString().contains(
        		"Region search for: (0, 0, 20, 20)"));
        
        assertTrue(outContent.toString().contains(
        		"(Rectangle1, 0, 0, 10, 10)"));
        
        assertTrue(outContent.toString().contains(
        		"(Rectangle2, 0, 0, 5, 5)"));
	}
	
	/**
	 * Tests for the intersection method
	 */
	@Test
	public void testIntersections() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 10);
        KVPair<String, Rectangle> pair = 
        		new KVPair<>("Rectangle1", rectangle);
        Rectangle rectangle2 = new Rectangle(0, 0, 5, 5);
        KVPair<String, Rectangle> pair2 = 
        		new KVPair<>("Rectangle2", rectangle2);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
		
        database.insert(pair);
        database.insert(pair2);
        
        database.intersections();
        
        assertTrue(outContent.toString().contains(
        		"All Intersection pairs:"));
        
        assertTrue(outContent.toString().contains(
        		"(Rectangle1, 0, 0, 10, 10)"));
        
        assertTrue(outContent.toString().contains(
        		"(Rectangle2, 0, 0, 5, 5)"));
	}
	
	/**
	 * Tests for the search method
	 */
	@Test
	public void testSearch() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 10);
        KVPair<String, Rectangle> pair = 
        		new KVPair<>("Rectangle1", rectangle);
        Rectangle rectangle2 = new Rectangle(0, 0, 5, 5);
        KVPair<String, Rectangle> pair2 = 
        		new KVPair<>("Rectangle2", rectangle2);
        Rectangle rectangle3 = new Rectangle(0, 0, 60, 60);
        KVPair<String, Rectangle> pair3 = 
        		new KVPair<>("Rectangle1", rectangle3);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        database.insert(pair);
        database.insert(pair2);
        
        database.search("Rectangle1");
        
        assertTrue(outContent.toString().contains(
        		"Rectangles with name 'Rectangle1':"));
        
        assertTrue(outContent.toString().contains(
        		"(0, 0, 10, 10)"));
        
        database.insert(pair3);
        
        database.search("Rectangle1");
        
        assertTrue(outContent.toString().contains(
        		"Rectangles with name 'Rectangle1':"));
        
        assertTrue(outContent.toString().contains(
        		"(0, 0, 10, 10)"));
        
        assertTrue(outContent.toString().contains(
        		"(0, 0, 60, 60)"));
        
        database.search("Test");
        
        assertTrue(outContent.toString().contains(
        		"Rectangle with name 'Test' not found."));
	}

   


}
