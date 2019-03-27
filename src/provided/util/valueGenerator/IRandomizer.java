package provided.util.valueGenerator;

import java.awt.*;

/**
 * Top-level abstraction of a utility to generate various random values.   
 * 
 * To maximally decouple an application from the randomizer's implementation any variable representing a randomizer
 * should be typed to this interface, not to any concrete implementation.  For example:
 * IRandomizer myRandomizer = Randomizer.Singleton; 
 *  
 * @author swong
 *
 */
public interface IRandomizer {
	/**
	 * Generates a random location point subject to the constraint that rect.x&lt;=X&lt;=rect.x+rect.width and rect.y&lt;=Y&lt;=rect.y+rect.height.   
	 * Note that the definition of a Rectangle object requires that (x,y) be defined as the upper left corner and that width and height must both 
	 * be non-negative.
	 * @param rect The bounds for the x and y values of the created Point
	 * @return A Point object whose x and y are subject to the given bounds
	 */
	public Point randomLoc(Rectangle rect);

	/**
	 * Generates a random location point subject to the constraint that 0&lt;=X&lt;=dim.width and 0&lt;=Y&lt;=dim.height.
	 * @param dim The bounds for the x and y values of the created Point
	 * @return A Point object whose x and y are subject to the given bounds
	 */
	public Point randomLoc(Dimension dim);

	/**
	 * Returns a random integer greater than or equal to min and less than or equal to max.
	 * @param min The minimum allowed value
	 * @param max The maximum allowed value
	 * @return an int subject to the given bounds
	 */
	public int randomInt(int min, int max);

	/**
	 * Returns a random double greater than or equal to min and less than max.
	 * @param min The minimum allowed value
	 * @param max The maximum allowed value
	 * @return a double subject to the given bounds
	 */
	public double randomDouble(double min, double max);

	/**
	 * Returns a random velocity (as a Point) subject to the constraint that the absolute value of the velocity (speed) 
	 * within the bound (inclusive) defined by rect.   Thus the resultant velocity may be negative.   The given Rectangle
	 * should use all positive values.   To create velocities or any Point with asymmetrical bounds, use the randomLoc(Rectangle rect) 
	 * method with the input rectangle set to the desired bounds.
	 * @param rect The bounds for the absolute value of the velocity in the x and y directions.  
	 * @return a Point object with x and y subject to the given bounds.
	 */
	public Point randomVel(Rectangle rect);

	/**
	 * Returns a random square Dimension, whose width is maxDim.width/2&lt;=width&lt;=maxDim.width
	 * @param maxDim  The bounds on the side of the created Dimension.
	 * @return A random square Dimension subject to the given bound
	 */
	public Dimension randomDim(Dimension maxDim);

	/**
	 * Generates a randomly located and sized rectangle
	 * @param rect  The bounds for the location of the created rectangle
	 * @param maxDim  The bounds for the dimensions of the create rectangle
	 * @return A Rectangle with location and dimensions subject to the given bounds.
	 */
	public Rectangle randomBounds(Rectangle rect, Dimension maxDim);

	/**
	 * Generates a random color
	 * @return a random Color object
	 */
	public Color randomColor();

	/**
	 * Returns a random choice of one of two objects, x and y, where probX is the probability 
	 * that x will be picked (0&lt;=x&lt;=1).
	 * @param x The first of two choices
	 * @param y The second of two choices
	 * @param probX  The probability of the first choice
	 * @return Either x or y as per the probability of choosing them. 
	 */
	public Object randomChoice(Object x, Object y, double probX);
}
