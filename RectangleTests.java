import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertFalse;


/**
 * This class holds the coordinates and dimensions of a rectangle and methods to
 * check if it intersects or has the same coordinates as an other rectangle.
 * 
 * @author Rami Benhamida
 * 
 * @version 2024-02-04
 */
public class RectangleTests {
    /**
     * Test Case for rectangle class
     * 
     * @author Rami Benhamida
     * 
     * @version 2024-02-04
     */
    @Test
    public void testEquals() {
        Rectangle rec = new Rectangle(1, 1, 2, 2);
        Rectangle recTwo = new Rectangle(1, 1, 2, 2);
        Rectangle recThree = new Rectangle(2, 1, 6, 2);
        Rectangle recFour = new Rectangle(-2, 1, 6, 2);
        Rectangle recFive = new Rectangle(2, -4, 6, 2);
        Rectangle recSix = new Rectangle(50, 50, 1, 1);
        Rectangle invalid = new Rectangle(10, 10, 2000, 9);
        Rectangle recBorder = new Rectangle(1, 3, 2, 2);
        Rectangle recCorner = new Rectangle(3, 3, 5, 5);
        Rectangle recSeven = new Rectangle(1, 100, 5, 5);
        String recOut = "X: 1, Y: 1, Width: 2, Height: 2";
        // equals() tests
        assertTrue(rec.equals(recTwo));
        assertFalse(recThree.equals(rec));
        assertFalse(recSix.equals(recCorner));
        assertFalse(recFour.equals(recFive));
        assertFalse(recBorder.equals(recThree));
        // isInvalidTests()
        assertTrue(recFour.isInvalid());
        assertTrue(invalid.isInvalid());
        assertFalse(rec.isInvalid());
        assertTrue(recFive.isInvalid());
        // getXCoordinate tests
        assertNotEquals(recFour.getxCoordinate(), 3);
        assertEquals(rec.getxCoordinate(), 1);
        // getyCoordinate tests
        assertEquals(rec.getyCoordinate(), 1);
        assertNotEquals(rec.getyCoordinate(), 50);
        // ToString() Tests
        assertTrue(rec.toString().equals(recOut));
        // Intersect cases
        assertTrue(rec.intersect(recTwo));
        assertFalse(rec.intersect(recSix));
        assertFalse(rec.intersect(recBorder));
        assertFalse(rec.intersect(recCorner));
        assertFalse(recSix.intersect(recBorder));
        assertFalse(rec.intersect(recSeven));
        // invalid rectangle cases
        Rectangle negWidth = new Rectangle(1, 1, -6, 2);
        Rectangle negHeight = new Rectangle(1, 1, 2, -5);
        assertTrue(negWidth.isInvalid());
        assertTrue(negHeight.isInvalid());
        assertFalse(recSix.isInvalid());
        Rectangle hugeHeight = new Rectangle(5, 5, 5, 9000);
        Rectangle hugeHeightandx = new Rectangle(1025, 10, 5, 8000);
        Rectangle hugeWidth = new Rectangle(2, 3, 1050, 8);
        Rectangle zeroHeight = new Rectangle(5, 5, 5, 0);
        Rectangle zeroWidth = new Rectangle(5, 5, 0, 5);
        Rectangle hugeWidthandx = new Rectangle(1025, 10, 5000, 80);
        Rectangle hugeHeightandy = new Rectangle(10, 10, 5, 800);
        Rectangle hugeWidthandy = new Rectangle(800, 200, 300, 900);
        assertTrue(hugeHeight.isInvalid());
        assertTrue(hugeHeightandx.isInvalid());
        assertFalse(recBorder.isInvalid());
        assertFalse(recCorner.isInvalid());
        assertTrue(hugeWidth.isInvalid());
        assertTrue(zeroHeight.isInvalid());
        assertTrue(zeroWidth.isInvalid());
        assertTrue(hugeWidthandx.isInvalid());
        assertTrue(hugeWidthandy.isInvalid());
        assertFalse(hugeHeightandy.isInvalid());
        Rectangle validRectangle = new Rectangle(500, 500, 500, 500);
        assertFalse(validRectangle.isInvalid()); // Should return false
        // Create a rectangle with coordinates and dimensions 
        //that result in an invalid rectangle due to exceeding 1024
        Rectangle invalidRectangle = new Rectangle(1000, 500, 50, 50);
        assertTrue(invalidRectangle.isInvalid()); // Should return true
        Rectangle invalidRectangleTwo = new Rectangle(500, 1000, 50, 50);
        assertTrue(invalidRectangleTwo.isInvalid()); // Should return true
        // more equals() tests
        assertFalse(invalidRectangleTwo.equals(invalidRectangle));
    }
    /**
     * Test Case for rectangle class
     * 
     * @author Rami Benhamida
     * 
     * @version 2024-02-04
     */
    @Test
    public void testEqualsFirstCheckMutatedToTrue() {
        // Create two Rectangle objects that are equal
        Rectangle rec1 = new Rectangle(1, 1, 2, 2);
        Rectangle rec2 = rec1;

        // Assert that equals() returns true
        assertTrue(rec1.equals(rec2));
    }

    /**
     * Test Case for rectangle class
     * 
     * @author Rami Benhamida
     * 
     * @version 2024-02-04
     */
    @Test
    public void testEqualsSecondCheckMutatedToTrue() {
        // Create a Rectangle object and another object of a different class
        Rectangle rec = new Rectangle(1, 1, 2, 2);
        Object other = new Object();

        // Assert that equals() returns false
        assertFalse(rec.equals(other));
    }

    /**
     * Test Case for rectangle class
     * 
     * @author Rami Benhamida
     * 
     * @version 2024-02-04
     */
    @Test
    public void testEqualsMismatchParameters() {
        // Create two Rectangle objects
        //with different coordinates and dimensions
        Rectangle rec1 = new Rectangle(1, 1, 2, 2);
        Rectangle rec2 = new Rectangle(1, 3, 2, 2);
        Rectangle rec3 = new Rectangle(1, 1, 3, 2);
        Rectangle rec4 = new Rectangle(1, 1, 2, 3);
        Rectangle rec5 = new Rectangle(2, 1, 2, 2);
        Rectangle nullRec = null;

        // Assert that equals() returns false
        assertFalse(rec1.equals(rec2));
        assertFalse(rec1.equals(rec3));
        assertFalse(rec1.equals(rec4));
        assertFalse(rec1.equals(rec5));
        assertFalse(rec1.equals(nullRec));
    }
	
    /**
     * Test Case for rectangle class
     * 
     * @author Rami Benhamida
     * 
     * @version 2024-02-04
     */
    @Test
    public void testIntersectFirstLineMutated() {
        // Create two rectangles that intersect along the x-axis
        Rectangle rec1 = new Rectangle(1, 1, 4, 4);
        Rectangle rec2 = new Rectangle(3, 1, 3, 4);

        // Assert that intersect() returns true
        assertTrue(rec1.intersect(rec2));
    }

    /**
     * Test Case for rectangle class
     * 
     * @author Rami Benhamida
     * 
     * @version 2024-02-04
     */
    @Test
    public void testIntersectSecondLineMutated() {
        // Create two rectangles that intersect along the y-axis
        Rectangle rec1 = new Rectangle(1, 1, 4, 4);
        Rectangle rec2 = new Rectangle(1, 3, 4, 3);

        // Assert that intersect() returns true
        assertTrue(rec1.intersect(rec2));
    }

    /**
     * Test Case for rectangle class
     * 
     * @author Rami Benhamida
     * 
     * @version 2024-02-04
     */
    @Test
    public void testIntersectThirdLineMutated() {
        // Create two rectangles with no overlap along the x-axis
        Rectangle rec1 = new Rectangle(1, 1, 2, 2);
        Rectangle rec2 = new Rectangle(5, 1, 2, 2);

        // Assert that intersect() returns false
        assertFalse(rec1.intersect(rec2));
    }
    
    /**
     * Test Case for rectangle class
     * 
     * @author Rami Benhamida
     * 
     * @version 2024-02-04
     */
    @Test
    public void testIntersectFourthLineMutated() {
        // Create two rectangles with no overlap along the y-axis
        Rectangle rec1 = new Rectangle(1, 1, 2, 2);
        Rectangle rec2 = new Rectangle(1, 5, 2, 2);
        Rectangle rec4 = new Rectangle(45, 45, 1, 50);
        Rectangle rec3 = new Rectangle(50, 50, 20, 20);
        
        Rectangle rec5 = new Rectangle(50, 50, 50, 50);
        Rectangle rec6 = new Rectangle(1, 50, 1, 5);
        

        // Assert that intersect() returns false
        assertFalse(rec1.intersect(rec2));
        assertFalse(rec4.intersect(rec3));
        assertFalse(rec5.intersect(rec6));
        
        Rectangle rec7 = new Rectangle(50, 50, 50, 50);
        Rectangle rec8 = new Rectangle(50, 1, 50, 1);
        
        assertFalse(rec7.intersect(rec8));
        
        Rectangle rec9 = new Rectangle(50, 15, 2, 2);
        Rectangle rec10 = new Rectangle(20, 10, 31, 6);
        assertTrue(rec9.intersect(rec10));
    }


}

