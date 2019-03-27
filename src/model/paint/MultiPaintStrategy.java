package model.paint;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;

/**
 * A composite design pattern extension of APaintStrategy that paints a set of paint strategies. 
 * Note: This paint strategy cannot be used directly by the BallWorld system because it lacks a no-parameter constructor.
 * @author Peter
 */
public class MultiPaintStrategy extends APaintStrategy {

	/**
	 * The set of paint strategies to paint.
	 */
	private APaintStrategy[] pstrats;

	/**
	 * Constructor that takes the paint strategies that will part of the composite. An AffineTransform is instantiated for internal use.
	 * @param pstrats Vararg parameter that are the paint strategies that will make up the composite.
	 */
	public MultiPaintStrategy(APaintStrategy... pstrats) {
		super(new AffineTransform());
		this.pstrats = pstrats;
	}

	/**
	 * Constructor that takes the paint strategies that will part of the composite. An external AffineTransform is supplied for internal use.
	 * @param at The AffineTransform to use.
	 * @param pstrats Vararg parameter that are the paint strategies that will make up the composite.
	 */
	public MultiPaintStrategy(AffineTransform at, APaintStrategy... pstrats) {
		super(at);
		this.pstrats = pstrats;
	}

	/**
	 * Delegates to all the IPaintStrategies in the composite. Used to initialize the paint strategies.
	 */
	@Override
	public void init(Ball host) {
		for (int i = 0; i < pstrats.length; i++) {
			pstrats[i].init(host);
		}
	}

	/**
	 * Delegates to all the IPaintStrategies in the composite. Paints using given Graphics context using the supplied AffineTransform. Called by the inherited paint method.
	 */
	@Override
	public void paintXfrm(Graphics g, Ball host, AffineTransform at) {
		for (int i = 0; i < pstrats.length; i++) {
			pstrats[i].paintXfrm(g, host, at);
		}
	}

}
