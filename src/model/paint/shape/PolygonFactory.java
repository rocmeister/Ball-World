package model.paint.shape;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * Concrete IShapeFactory that provides the invariant behavior to instantiate a Shape that is a Polygon. 
 * This class can be instantiated and used simply by supplying the desired points in its constructor, or sub-classed an the constructor overridden. 
 * Note that this class cannot be used directly by the BallWorld system because it does not have a no-parameter constructor.
 * @author Peter Dulworth
 */
public class PolygonFactory implements IShapeFactory {

	/**
	 * The AffineTransform used for internal calculations.
	 * This AffineTransform is used transform the polygon inside its "ball". i.e. it is used locally.
	 */
	private AffineTransform at;

	/**
	 * The Polygon shape to use as the prototype.
	 */
	private Polygon poly = new Polygon();

	/**
	 * Scale factor that scales the integer Point-defined Polygon to a unit size, which requires doubles. 
	 * The value of scaleFactor is The ratio of the desired unit size to the defined size of the prototype Polygon.
	 */
	private double scaleFactor;

	/**
	 * Constructor that uses an externally defined AffineTransform for internal use plus takes the defining points 
	 * of the prototype Polygon and a scale factor to scale the given points to the desired unit size. 
	 * Since Polygons require Point objects for their definition, a prototype Polygon cannot be defined of 
	 * arbitrary size because Points are defined on an integer grid. Thus, a double scale factor is also provided 
	 * that is used to scale the Polygon via the affine transform into a Shape of the desired size.
	 * @param at The AffineTransform to use.
	 * @param scaleFactor The ratio of the desired unit size to the defined size of the prototype Polygon.
	 * @param pts Vararg parameters that are the Points that define the Polygon around the origin as its center.
	 */
	public PolygonFactory(AffineTransform at, double scaleFactor, Point... pts) {
		this.at = at;
		this.scaleFactor = scaleFactor;
		for (Point pt : pts) {
			poly.addPoint(pt.x, pt.y);
		}
	}

	/**
	 * Instantiates a Polygon.
	 * @param x x-coordinate of the center of the resultant Polygon.
	 * @param y y-coordinate of the center of the resultant Polygon.
	 * @param xScale The x-dimension of the polygon, usually the x-radius.
	 * @param yScale The y-dimension of the polygon, usually the y-radius.
	 * @return A Shape object that is the scaled prototype Polygon.
	 */
	@Override
	public Shape makeShape(double x, double y, double xScale, double yScale) {
		at.setToTranslation(x, y);
		at.scale(xScale * scaleFactor, (-1.0) * yScale * scaleFactor);
		return at.createTransformedShape(poly);

	}

}
