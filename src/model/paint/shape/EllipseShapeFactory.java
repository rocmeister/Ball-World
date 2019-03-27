package model.paint.shape;

import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 * Concrete shape factory that instantiates Ellipse2D.Double shapes.
 * @author Peter
 */
public class EllipseShapeFactory implements IShapeFactory {

	/**
	 * Singleton pattern.
	 */
	public static final EllipseShapeFactory Singleton = new EllipseShapeFactory();

	/**
	 * No-op EllipseShapeFactory constructor.
	 */
	private EllipseShapeFactory() {
	}

	/**
	 * Instantiates an ellipse.
	 * @param x x-coordinate of the center of the ellipse.
	 * @param y y-coordinate of the center of the ellipse.
	 * @param xScale The x-radius of the ellipse.
	 * @param yScale The y-radius of the ellipse.
	 * @return An Ellipse2D.Double object.
	 */
	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		return new Ellipse2D.Double(x - xScale, y - yScale, 2 * xScale, 2 * yScale);
	}
}
