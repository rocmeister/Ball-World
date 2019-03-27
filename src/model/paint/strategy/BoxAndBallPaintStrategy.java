package model.paint.strategy;

import java.awt.geom.AffineTransform;

import model.paint.UprightMultiPaintStrategy;

/**
 * A composite upright strategy that contains a box and a ball.
 * @author Peter	
 */
public class BoxAndBallPaintStrategy extends UprightMultiPaintStrategy {

	/**
	 * No-parameter constructor that instantiates the AffineTransform for internal use.
	 */
	public BoxAndBallPaintStrategy() {
		this(new AffineTransform());
	}

	/**
	 * @param at The AffineTransform to use.
	 */
	public BoxAndBallPaintStrategy(AffineTransform at) {
		super(at, new RectanglePaintStrategy(at, -0.5, -0.5, 0.5, 0.5),
				new EllipsePaintStrategy(at, 0.5, 0.5, 0.5, 0.5));
	}
}
