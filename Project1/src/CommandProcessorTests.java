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
 * inside of the CommandProcessor class.
 * 
 * @author Rami Benhamida
 * @version 1.0
 * 
 */
@SuppressWarnings("unused")
public class CommandProcessorTests {
    private CommandProcessor commandProcessor;
   
    /**
     * sets up objects for testing
     */
    @Before
    public void setUp() {
        commandProcessor = new CommandProcessor();
    }
    
    /**
     * Tests for the insert command
     */
    @Test
    public void testInsertCommand() {
        ByteArrayOutputStream outContent = 
                new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        commandProcessor.processor("insert Rectangle1 0 0 5 5");
        assertTrue(outContent.toString().contains(
                "Rectangle inserted:"));
        
        assertTrue(outContent.toString().contains(
                "(Rectangle1, 0, 0, 5, 5)"));
        
        commandProcessor.processor("insert Rect 0 0 -1 5");
        
        assertTrue(outContent.toString().contains(
                "Rectangle rejected: (Rect, 0, 0, -1, 5)"));
    }
  
    /**
     * Tests for the remove command
     */
    @Test 
    public void testRemoveCommand() {
        ByteArrayOutputStream outContent = 
                new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        commandProcessor.processor("insert Rectangle1 0 0 5 5");
        assertTrue(outContent.toString().contains(
                "Rectangle inserted:"));
        assertTrue(outContent.toString().contains(
                "(Rectangle1, 0, 0, 5, 5)"));

        commandProcessor.processor("remove Rectangle1");
 
        assertTrue(outContent.toString().contains(
                "Rectangle removed: (Rectangle1, 0, 0, 5, 5"));
  
        commandProcessor.processor("insert Rectangle1 0 0 5 5");
   
        commandProcessor.processor("remove 0 0 5 5");

        assertTrue(outContent.toString().contains(
                "Rectangle removed: (Rectangle1, 0, 0, 5, 5"));
    }
    
    /**
     * Tests for the intersection command
     */
    @Test
    public void testIntersections() {
        ByteArrayOutputStream outContent = 
                new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        commandProcessor.processor("insert Rectangle2 0 0 5 5");
        commandProcessor.processor("insert Rectangle1 0 0 10 10");
        
        commandProcessor.processor("intersections");
        
        assertTrue(outContent.toString().contains(
        		"Intersection pairs:"));
        
        assertTrue(outContent.toString().contains(
        		"(Rectangle1, 0, 0, 10, 10"));
        
        assertTrue(outContent.toString().contains(
        		"| Rectangle2, 0, 0, 5, 5)"));
        
    }
    
    /**
     * Tests for the search command
     */
    @Test
    public void testSearch() {
    	ByteArrayOutputStream outContent = 
        		new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        commandProcessor.processor("insert Rectangle2 0 0 5 5");
        commandProcessor.processor("insert Rectangle1 0 0 10 10");
        
        commandProcessor.processor("search Rectangle1");
        
        assertTrue(outContent.toString().contains(
        		"Rectangles found:"));
        
        assertTrue(outContent.toString().contains(
        		"(0, 0, 10, 10)"));
    }
    
    /**
     * Tests for the dump command
     */
    @Test
    public void testDump() {
    	ByteArrayOutputStream outContent = 
        		new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        commandProcessor.processor("insert Rectangle2 0 0 5 5");
        commandProcessor.processor("insert Rectangle1 0 0 10 10");
        
        commandProcessor.processor("dump");
        
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
     * Tests for an invalid input command
     */
    @Test
    public void testInvalidCommand() {
    	ByteArrayOutputStream outContent = 
        		new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        commandProcessor.processor("invalid");
        
        assertTrue(outContent.toString().contains(
        		"Unrecognized command."));
    }
}
