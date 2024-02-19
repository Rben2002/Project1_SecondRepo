//import src.Rectangle;

/**
 * This class holds the coordinates and dimensions of a rectangle and methods to
 * check if it intersects or has the same coordinates as an other rectangle.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class Rectangle {
    // the x coordinate of the rectangle
    private int xCoordinate;
    // the y coordinate of the rectangle
    private int yCoordinate;
    // the distance from the x coordinate the rectangle spans
    private int width;
    // the distance from the y coordinate the rectangle spans
    private int height;

    /**
     * Creates an object with the values to the parameters given in the
     * xCoordinate, yCoordinate, width, height
     * 
     * @param x
     *            x-coordinate of the rectangle
     * @param y
     *            y-coordinate of the rectangle
     * @param w
     *            width of the rectangle
     * @param h
     *            height of the rectangle
     */
    public Rectangle(int x, int y, int w, int h) {
        xCoordinate = x;
        yCoordinate = y;
        width = w;
        height = h;
    }


    /**
     * Getter for the x coordinate
     *
     * @return the x coordinate
     */
    public int getxCoordinate() {
        return xCoordinate;
    }


    /**
     * Getter for the y coordinate
     *
     * @return the y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }


    /**
     * Getter for the width
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }


    /**
     * Getter for the height
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }


    /**
     * Checks if the invoking rectangle intersects with rec.
     * 
     * @param r2
     *            Rectangle parameter
     * @return true if the rectangle intersects with rec, false if not
     */
    public boolean intersect(Rectangle r2) {
        // Find coordinates of all corners of both rectangles
        // then 
        // Calculate x and y coordinates of the corners for both rectangles
        int x1 = this.getxCoordinate();
        int y1 = this.getyCoordinate();
        int x2 = this.getxCoordinate() + this.getWidth();
        int y2 = this.getyCoordinate() + this.getHeight();
        
        int x3 = r2.getxCoordinate();
        int y3 = r2.getyCoordinate();
        int x4 = r2.getxCoordinate() + r2.getWidth();
        int y4 = r2.getyCoordinate() + r2.getHeight();
        
        // Check for overlap along the x-axis
        boolean xOverlap = x1 < x4 && x2 > x3;
        
        // Check for overlap along the y-axis
        boolean yOverlap = y1 < y4 && y2 > y3;
        
        // Combine the checks
        return xOverlap && yOverlap;

    }


    /**
     * Checks, if the invoking rectangle has the same coordinates as rec.
     * 
     * @param rec
     *            the rectangle parameter
     * @return true if the rectangle has the same coordinates as rec, false if
     *         not
     */
    public boolean equals(Object rec) {
        if (this == rec) {
            return true; // If the object is compared with itself, return true
        }
        if (rec == null || getClass() != rec.getClass()) {
            return false; // If the object is null or
            //of different class, return false
        }
        Rectangle other = (Rectangle) rec; // Cast the Object to Rectangle type
        boolean checkOne = xCoordinate == other.getxCoordinate() &&
                yCoordinate == other.getyCoordinate();
        boolean checkTwo = width == other.getWidth() &&
                height == other.getHeight();
        return checkOne && checkTwo;
    }


    /**
     * Outputs a human readable string with information about the rectangle
     * which includes the x and y coordinate and its height and width
     * 
     * @return a human readable string containing information about the
     *         rectangle
     */
    public String toString() {
        String result = "X: ";
        result += Integer.toString(getxCoordinate());
        result += ", Y: " + Integer.toString(getyCoordinate());
        result += ", Width: " + Integer.toString(getWidth());
        result += ", Height: " + Integer.toString(getHeight());
        return result;
    }


    /**
     * Checks if the rectangle has invalid parameters
     * 
     * @return true if the rectangle has invalid parameters, false if not
     */
    public boolean isInvalid() {
        // Check if width or height are not greater than 0
       
        // Check if the rectangle is out of the world box
        return (xCoordinate < 0 || yCoordinate < 0 || 
            xCoordinate + width >= 1024 || yCoordinate + height >= 1024 
            || width <= 0 || height <= 0);
    
        
    }
}
