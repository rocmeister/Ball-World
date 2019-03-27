package model.paint;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * Abstract class that provides default behavior for subclasses that will decorate another IPaintStrategy to add functionality to that strategy. 
 * All this class's methods do is to simply delegate to the decoree. A subclass should override one or more methods, adding additional 
 * processing either before or after delegating to the decoree. 
 * Note that this class cannot be used by the BallWorld system directly as it lacks a no-parameter constructor.
 * @author Peter
 */
public abstract class ADecoratorPaintStrategy extends APaintStrategy {

	/**
	 * The "decoree" paint strategy whose methods are being augmented by this decorator paint strategy.
	 */
	private APaintStrategy decoree;

	/**
	 * Constructor that takes the decoree paint strategy.
	 * @param decoree The paint strategy that is to be decorated.
	 */
	public ADecoratorPaintStrategy(APaintStrategy decoree) {
		super(decoree.getAT());
		this.decoree = decoree;
	}

	/**
	 * Default behavior is to simply delegate to the decoree's paint method.
	 */
	@Override
	public void paint(Graphics g, Ball host) {
		decoree.paint(g, host);
	}

	/**
	 * Default behavior is to simply delegate to the decoree's paintXfrm method.
	 */
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		decoree.paintXfrm(g, host, at);
	}

	/**
	 * Default behavior is to simply delegate to the decoree's init method.
	 */
	public void init(Ball host) {
		decoree.init(host);
	}

}
