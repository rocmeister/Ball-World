package model.paint.shape;

import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 * Concrete PolygonFactory that creates fish-shaped Polygons that have a longer tail and an open mouth.
 * @author Peter
 */
public class SpaceShuttlePolygonFactory extends PolygonFactory implements IShapeFactory {

	/**
	 * The polygon points that define the space shuttle prototype. 
	 */
	private static final Point[] spaceShuttlePoints = { new Point(-29, 8), new Point(-35, 20), new Point(-31, 21),
			new Point(-29, 20), new Point(-15, 5), new Point(25, 5), new Point(27, 2), new Point(37, -3),
			new Point(35, -6), new Point(23, -8), new Point(-23, -8), new Point(-32, -7), new Point(-27, -4),
			new Point(-33, -5), new Point(-31, 1), new Point(-27, 0), new Point(-27, 2), new Point(-33, 2),
			new Point(-32, 7), new Point(-27, 6), new Point(-26, 7) };

	/**
	 * The value of scale is the ratio of the desired unit size to the defined size of the prototype Polygon.
	 */
	private final static double scale = 1.0 / 30.0;

	/**
	 * Constructor that calls the PolygonFactory superclass constructor with the scale factor and polygon points that define the fish shape.
	 */
	public SpaceShuttlePolygonFactory() {
		super(new AffineTransform(), scale, spaceShuttlePoints);
	}
}
