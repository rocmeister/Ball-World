package model.paint.shape;

import java.awt.Rectangle;
import java.awt.Shape;

/**
 * Concrete IShapeFactory that instantiates Rectangle2d.Double shapes.
 * @author Peter
 */
public class RectangleShapeFactory implements IShapeFactory {

	/**
	 * Singleton pattern.
	 */
	public static final RectangleShapeFactory Singleton = new RectangleShapeFactory();

	/**
	 * No-op EllipseShapeFactory constructor.
	 */
	private RectangleShapeFactory() {
	}

	/**
	 * Instantiates an ellipse.
	 * @param x The x-coordinate of the center of the prototype rectangle.
	 * @param y The y-coordinate of the center of the prototype rectangle.
	 * @param xScale The half-width of the rectangle, i.e. the width as measured from the center.
	 * @param yScale The half-height of the rectangle, i.e. the height as measured from the center.
	 * @return A Rectangle2D.Double instance.
	 */
	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		return new Rectangle.Double(x - xScale, y - yScale, 2.0 * xScale, 2.0 * yScale);
	}
}
