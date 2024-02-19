import java.util.Iterator;
import java.util.ArrayList;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * Also note that the Database class will have a clearer role in Project2, 
 * where we will have two data structures. 
 * The Database class will then determine 
 * which command should be directed to which data structure.  
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class Database {

    // this is the SkipList object that we are using
    // a string for the name of the rectangle and then
    // a rectangle object, these are stored in a KVPair,
    // see the KVPair class for more information
    private SkipList<String, Rectangle> list;
    
    //This is an Iterator object over the SkipList 
    // to loop through it from outside the class.
    //You will need to define an extra Iterator for the intersections method.
    private Iterator<KVPair<String, Rectangle>> itr1;
    

    /**
     * The constructor for this class initializes a SkipList object with String
     * and Rectangle a its parameters.
     */
    public Database() {
        list = new SkipList<String, Rectangle>();
    }


    /**
     * Inserts the KVPair in the SkipList if the rectangle has valid coordinates
     * and dimensions, that is that the coordinates are non-negative and that
     * the rectangle object has some area (not 0, 0, 0, 0). This insert will
     * add the KVPair specified into the sorted SkipList appropriately
     * 
     * @param pair
     *            the KVPair to be inserted
     */
    public void insert(KVPair<String, Rectangle> pair) {
        Rectangle rec = pair.getValue();
        // Check if rectangle is valid
        if (rec.isInvalid()) {
            System.out.println("Insert ERROR: Rectangle is invalid: ("
                    + pair.getKey() +
                    ", " + rec.getxCoordinate() + ", " 
                    + rec.getyCoordinate() + ", " + 
                    rec.getWidth() + ", " + rec.getHeight() + ")");
            return;
        }
        // if rectangle is valid, insert it and display it to console
        else {
            list.insert(pair);
            System.out.println("Rectangle has been inserted: (" + 
                    pair.getKey() + ", " + rec.getxCoordinate() + ", " + 
                    rec.getyCoordinate() + ", " + rec.getWidth()
                    + ", " + rec.getHeight() + ")");
        }
 
    }


    /**
     * Removes a rectangle with the name "name" if available. If not an error
     * message is printed to the console.
     * 
     * @param name
     *            the name of the rectangle to be removed
     */
    public void remove(String name) {
    	// call skipList remove by name function
    	KVPair<String, Rectangle> result = list.remove(name);
    	
    	// display error if rectangle not found
    	if (result == null) {
    		System.out.println("ERROR: Rectangle not found: " + name);
    	}
    	else {
    		// display rectangle that was removed
    		Rectangle rectInfo = result.getValue();
    		System.out.println("Rectangle has been removed: (" + 
    				name + ", " + rectInfo.getxCoordinate() + ", " + 
    	    	    rectInfo.getyCoordinate() + ", " + rectInfo.getWidth()
    	    	    + ", " + rectInfo.getHeight() + ")");
    	}
    		
    	
    }


    /**
     * Removes a rectangle with the specified coordinates if available. If not
     * an error message is printed to the console.
     * 
     * @param x
     *            x-coordinate of the rectangle to be removed
     * @param y
     *            x-coordinate of the rectangle to be removed
     * @param w
     *            width of the rectangle to be removed
     * @param h
     *            height of the rectangle to be removed
     */
    public void remove(int x, int y, int w, int h) {
        Rectangle rec = new Rectangle(x, y, w, h);
        
        // Call skipList remove by value function
        KVPair<String, Rectangle> result = list.removeByValue(rec);
        
        // skipList remove by value will return if rectangle does
        // not exist, in this case print an Error
        if (result == null) {
            System.out.println("Error: Rectangle not found: (" + x 
                    + ", " + y + ", " + w + ", " + h + ")");
        }
        else {
            System.out.println("Rectangle has been removed: (" + 
                    result.getKey() + ", " + x + ", " + y + ", " + w + 
                    ", " + h + ")");
        }
    }


    /**
     * Displays all the rectangles inside the specified region. The rectangle
     * must have some area inside the area that is created by the region,
     * meaning, Rectangles that only touch a side or corner of the region
     * specified will not be said to be in the region.
     * 
     * @param x
     *            x-Coordinate of the region
     * @param y
     *            y-Coordinate of the region
     * @param w
     *            width of the region
     * @param h
     *            height of the region
     */
    public void regionsearch(int x, int y, int w, int h) {
        Rectangle region = new Rectangle(x, y, w, h);
        // if region is not a valid rectangle, show error
        if (h <= 0 || w <= 0) {
            System.out.println("Search Region is Invalid: (" + 
                    x + ", " + y + ", " + w + ", " + h + ")");
            return;
        }
        // Print search region
        System.out.println("Region search for: (" + x + ", "
                + y + ", " + w + ", " + h + ")");
        // iterate through entire skipList
        for (KVPair<String, Rectangle> pair : list) {
            Rectangle rec = pair.getValue();
            // if rectangle intersects region, print it.
            if (rec.intersect(region)) {
                System.out.println("(" + pair.getKey() 
                    + ", " + rec.getxCoordinate()
                    + ", " + rec.getyCoordinate() + ", " 
                    + rec.getWidth() + ", " + 
                    rec.getHeight() + ")");
            }
    	}
    	
    }

    /**
     * Prints out all the rectangles that intersect each other. Note that 
     * it is better not to implement an intersections 
     * method in the SkipList class
     * as the SkipList needs to be agnostic about the 
     * fact that it is storing Rectangles. 
     */
    public void intersections() {
        System.out.println("All Intersection pairs: ");
  
        // Basically a nested for loop, first iterator pulls the pair
	    // The second will iterate through the list to check intersections
        Iterator<KVPair<String, Rectangle>> itrOne = list.iterator();

        while (itrOne.hasNext()) {
    		KVPair<String, Rectangle> pairOne = itrOne.next();
    		Rectangle recOne = pairOne.getValue();
    		
    		// Define second iterator for the skipList
        	Iterator<KVPair<String, Rectangle>> itr2 = list.iterator();
    		
    		while (itr2.hasNext()) {
                KVPair<String, Rectangle> pairTwo = itr2.next();
                Rectangle recTwo = pairTwo.getValue();
               
                // avoid self comparison
                if (pairOne != pairTwo) {
                    // if they intersect, print the pair
                    if (recOne.intersect(recTwo)) {
                        System.out.println("(" + pairOne.getKey() 
                            + ", " + recOne.getxCoordinate()
                            + ", " + recOne.getyCoordinate() + ", " 
                            + recOne.getWidth() + ", " + 
                            recOne.getHeight() + ") and " + "(" 
                            + pairTwo.getKey() + ", " + 
                            recTwo.getxCoordinate() + ", " 
                            + recTwo.getyCoordinate() + ", " + 
                            recTwo.getWidth() + ", " 
                            + recTwo.getHeight() + ")");
                    }
                }
    				
            }
    		
        }
    }


    /**
     * Prints out all the rectangles with the specified name in the SkipList.
     * This method will delegate the searching to the SkipList class completely.
     * 
     * @param name
     *            name of the Rectangle to be searched for
     */
    public void search(String name) {
        // Call skipList searchByName function
        ArrayList<KVPair<String, Rectangle>> result = list.search(name);
        // if the rectangle doesn't exist, show error
        if (result == null || result.isEmpty()) {
            System.out.println("Rectangle with name '" + name + "' not found.");
        } 
        else {
        	// Print out all rectangles with that name 
            System.out.println("Rectangles with name '" + name + "': ");
            for (KVPair<String, Rectangle> pair : result) {
            	Rectangle rectInfo = pair.getValue();
                System.out.println("(" + rectInfo.getxCoordinate() + ", "
                         + rectInfo.getyCoordinate() + ", " 
                         + rectInfo.getWidth()
                         + ", " + rectInfo.getHeight() + ") ");
            }
        }
    }


    /**
     * Prints out a dump of the SkipList which includes information about the
     * size of the SkipList and shows all of the contents of the SkipList. This
     * will all be delegated to the SkipList.
     */
    public void dump() {
        list.dump();
    }

}
