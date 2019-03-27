package model.paint.strategy;

import model.paint.UprightImagePaintStrategy;
import provided.util.valueGenerator.impl.Randomizer;

/**
 * Paint strategy that uses either an animated Mario or Sonic image.
 * @author Peter
 */
public class MarioSonicImagePaintStrategy extends UprightImagePaintStrategy {

	/**
	 * No-parameter constructor that instantiates the AffineTransform used internally and the following files with a 50% probability: 
	 * 
	 * model\paint\strategy\images\Sonic_animate.gif 
	 * 
	 * or 
	 * 
	 * model\paint\strategy\images\Mario_animate.gif 
	 * 
	 * with a fill factor of 0.5.
	 */
	public MarioSonicImagePaintStrategy() {
		super((String) Randomizer.Singleton.randomChoice("Mario_animate.gif", "Sonic_animate.gif", 0.5), 0.5);
	}

}
