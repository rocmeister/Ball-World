package model.paint.strategy;

import java.awt.Color;
import java.awt.geom.AffineTransform;

import model.paint.FixedColorDecoratorPaintStrategy;
import model.paint.UprightMultiPaintStrategy;
import model.paint.strategy.EllipsePaintStrategy;

/**
 * Subclass of MultiPaintStrategy that uses a Fish1PaintStrategy and an EllipsePaintStrategy to paint a fish shape that has an eye.
 * @author Peter
 */
public class NiceFishPaintStrategy extends UprightMultiPaintStrategy {

	/**
	 * No-parameter constructor that instantiates the AffineTransform for internal use.
	 */
	public NiceFishPaintStrategy() {
		this(new AffineTransform());
	}

	/**
	 * Constructor that uses an externally supplied AFfineTransform for internal use.
	 * @param at The AffineTransform to use.
	 */
	public NiceFishPaintStrategy(AffineTransform at) {
		super(at, new Fish1PaintStrategy(), // body
				new FixedColorDecoratorPaintStrategy(Color.WHITE, new EllipsePaintStrategy(at, 0.5, -0.2, 0.15, 0.15)),  // white of the eye
				new FixedColorDecoratorPaintStrategy(Color.BLACK, new EllipsePaintStrategy(at, 0.5, -0.2, 0.08, 0.08))); // pupil
	}
}
