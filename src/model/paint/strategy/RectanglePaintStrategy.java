package model.paint.strategy;

import java.awt.geom.AffineTransform;

import model.paint.ShapePaintStrategy;
import model.paint.shape.RectangleShapeFactory;

/**
 * Concrete paint strategy that paints a rectangular shape scaled to the size of the ball.
 * @author Peter
 */
public class RectanglePaintStrategy extends ShapePaintStrategy {

	/**
	 * No-parameter constructor that instantiates the AffineTransform for internal use plus defines a prototype Rectangle of a width = 8/3 and height = 2/3 which is an average radius of 1.
	 */
	public RectanglePaintStrategy() {
		this(new AffineTransform(), 0, 0, 4.0 / 3.0, 2.0 / 3.0);
	}

	/**
	 * Constructor that takes an external AffineTransform for internal use and a Rectangle centered at (x, y) with the given half-width and half-height (= x-radius and y-radius).
	 * @param at The AffineTransform to use.
	 * @param x The x-coordinate of the center of the prototype rectangle.
	 * @param y The y-coordinate of the center of the prototype rectangle.
	 * @param width The half-width of the rectangle, i.e. the width as measured from the center.
	 * @param height The half-height of the rectangle, i.e. the height as measured from the center.
	 */
	public RectanglePaintStrategy(AffineTransform at, double x, double y, double width, double height) {
		super(at, RectangleShapeFactory.Singleton.makeShape(x, y, width, height));
	}
}
