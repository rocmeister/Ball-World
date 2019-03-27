package provided.util.valueGenerator;

/**
 * Top-level abstraction of the generation of sinusoidally varying numerical values.  The range of values 
 * produced and the step between subsequent values are determined by the concrete implementation 
 * of the interface.
 * 
 * To maximally decouple an application from the sine maker's implementation any variable representing a sine maker
 * should be typed to this interface, not to any concrete implementation.  For example:
 * ISineMaker mySineMaker = new SineMaker()
 *  
 * @author swong
 *
 */
public interface ISineMaker {

	/**
	 * Returns a different value each time it is called.  The value varies smoothly in a 
	 * sinusoidal fashion, incrementing each time as per the above specified delta 
	 * angle increase.
	 * @return the next value as a double
	 */
	double getDblVal();

	/**
	 * Same as getDblVal, but returns the result rounded to the nearest integer.
	 * Note that getDblVal and getIntVal are not independent as getIntVal merely calls
	 * getDblVal.
	 * @return the next value as an int
	 */
	int getIntVal();

}