package provided.util.valueGenerator.impl;

import java.awt.*;

import provided.util.valueGenerator.IRandomizer;

/**
 * Concrete implementation of IRandomizer
 */
public class Randomizer implements IRandomizer {
	
	/**
	 * Singleton instance of this class
	 */
	public static Randomizer Singleton = new Randomizer();
	
	/**
	 * Private constructor for use by the Singleton only..
	 */
	private Randomizer() {
	}


	@Override
	public Point randomLoc( Rectangle rect) {
		return (new Point( randomInt(rect.x, rect.x+rect.width), randomInt(rect.y, rect.y+rect.height)));
	}


	@Override
	public Point randomLoc( Dimension dim) {
		return (new Point( randomInt(0, dim.width), randomInt(0, dim.height)));
	}

	@Override
	public int randomInt(int min, int max) {
		return (int)Math.floor((Math.random()*(1+max-min))+min);
	}

	@Override
	public double randomDouble(double min, double max) {
		return (Math.random()*(max-min))+min;
	}

	@Override
	public Point randomVel( Rectangle rect) {
		return (new Point (randomInt(-rect.width, rect.width), randomInt(-rect.height, rect.height)));
	}

	@Override
	public Dimension randomDim( Dimension maxDim) {
		int x =  randomInt(maxDim.width/2,maxDim.width);
		return new Dimension(x,x);
	}

	@Override
	public Rectangle randomBounds( Rectangle rect, Dimension maxDim) {
		return new Rectangle(randomLoc(rect), randomDim(maxDim));
	}

	@Override
	public Color randomColor() {
		return new Color( randomInt(0,255),randomInt(0,255),randomInt(0,255));
	}

	@Override
	public Object randomChoice(Object x, Object y, double probX) {
		return (Math.random()<probX) ? x: y;

	}
}


