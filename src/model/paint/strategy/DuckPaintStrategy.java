package model.paint.strategy;

import model.paint.UprightImagePaintStrategy;

/**
 * Paint strategy that paints a duck that always stays upright.
 * @author Peter
 */
public class DuckPaintStrategy extends UprightImagePaintStrategy {

	/**
	 * Constructor that loads the duck ball gif.
	 */
	public DuckPaintStrategy() {
		super("duck.gif", 0.6);
	}
}
