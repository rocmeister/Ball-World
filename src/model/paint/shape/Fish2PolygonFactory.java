package model.paint.shape;

import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 * Concrete PolygonFactory that creates fish-shaped Polygons that have a longer tail and an open mouth.
 * @author Peter
 */
public class Fish2PolygonFactory extends PolygonFactory implements IShapeFactory {

	/**
	 * The polygon points that define the fish prototype. 
	 */
	private static final Point[] fishPoints = { new Point(-10, -6), new Point(-10, 6), new Point(-8, 2),
			new Point(-4, 2), new Point(6, 8), new Point(12, 0), new Point(6, -8), new Point(-4, -8),
			new Point(-8, -2) };

	/**
	 * The value of scale is The ratio of the desired unit size to the defined size of the prototype Polygon.
	 */
	private final static double scale = 1.0 / 12.0;

	/**
	 * Constructor that calls the PolygonFactory superclass constructor with the scale factor and polygon points that define the fish shape.
	 */
	public Fish2PolygonFactory() {
		super(new AffineTransform(), scale, fishPoints);
	}
}
