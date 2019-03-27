package model.paint.strategy;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import model.Ball;
import model.IPaintStrategy;
import model.paint.shape.EllipseShapeFactory;

/**
 * Paint strategy that paints a filled circle with the Ball's radius. This functionality is duplicated by the EllipsePaintStrategy. 
 * The class demonstrates a direct implementation of IPaintStrategy.
 * @author Peter
 */
public class BallPaintStrategy implements IPaintStrategy {

	/**
	 * The AffineTransformed used for internal calculations.
	 */
	private AffineTransform at;

	/**
	 * Unit sized circle used as a prototype.
	 */
	private Ellipse2D.Double ball;

	/**
	 * No parameter constructor that creates a 1 pixel radius ball at the origin. Instantiates a new AffineTransform for internal use.
	 */
	public BallPaintStrategy() {
		this(new AffineTransform(), 0, 0, 1, 1);
	}

	/**
	 * Constructor that allows one to create the prototype ball of arbitrary dimension and location. The AffineTransform is externally supplied.
	 * @param at The AffineTransform to use for internal calculations.
	 * @param x floating point x-coordinate of center of circle.
	 * @param y floating point y-coordinate of center of circle.
	 * @param width floating point x-radius of the circle (ellipse).
	 * @param height floating point y-radius of the circle (ellipse).
	 */
	public BallPaintStrategy(AffineTransform at, double x, double y, double width, double height) {
		this.at = at;
		this.ball = (Ellipse2D.Double) EllipseShapeFactory.Singleton.makeShape(x, y, width, height);
	}

	/**
	 * Paints on the given graphics context using the color, scale and direction provided by the host. 
	 * This is done by setting up the AffineTransform to scale then translate. 
	 * Calls paintXfrm to actually perform the painting, using the set up transform. 
	 * Calls paintCfg just before calling paintXfrm param g The Graphics context that will be paint on param host 
	 * The host Ball that the required information will be pulled from.
	 */
	@Override
	public void paint(Graphics g, Ball host) {
		double scale = host.getRadius();
		at.setToTranslation(host.getLocation().x, host.getLocation().y); // happens third
		at.scale(scale, scale); // happens second
		at.rotate(host.getVelocity().x, host.getVelocity().y); // happens first 
		g.setColor(host.getColor());
		paintXfrm(g, host, this.at);
	}

	/**
	 * Paints a transformed circle, as per the given AffineTransform Uses color already set in Graphics context.
	 * @param g The Graphics context to paint on.
	 * @param host The Ball host.
	 * @param at The AffineTransform to use.
	 */
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		((Graphics2D) g).fill(at.createTransformedShape(this.ball));
	}

	@Override
	public void init(Ball host) {
	}

}
