package model.paint;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * An image painting strategy that adds a paintCfg override that keeps the image upright no matter which way it is going.
 * @author Peter
 */
public class UprightMultiPaintStrategy extends MultiPaintStrategy {

	/**
	 * Constructor for an upright composite painting strategy Constructor that takes the paint strategies that will part of the composite. An AffineTransform is instantiated for internal use.
	 * @param pstrats Vararg parameter that are the paint strategies that will make up the composite.
	 */
	public UprightMultiPaintStrategy(APaintStrategy... pstrats) {
		this(new AffineTransform(), pstrats);
	}

	/**
	 * Constructor that takes the paint strategies that will part of the composite. An external AffineTransform is supplied for internal use.
	 * @param at The AffineTransform to use.
	 * @param pstrats Vararg parameter that are the paint strategies that will make up the composite.
	 */
	public UprightMultiPaintStrategy(AffineTransform at, APaintStrategy... pstrats) {
		super(at, pstrats);
	}

	/**
	 * Augments the inherited paint method to add flipping of the image to keep it upright whenever the ball is going towards the left.
	 */
	@Override
	protected void paintCfg(Graphics g, Ball host) {
		super.paintCfg(g, host);
		if (Math.abs(Math.atan2(host.getVelocity().y, host.getVelocity().x)) > Math.PI / 2.0) {
			at.scale(1.0, -1.0);
		}
	}
}
