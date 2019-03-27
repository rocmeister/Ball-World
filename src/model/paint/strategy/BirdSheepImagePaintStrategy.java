package model.paint.strategy;

import model.paint.UprightImagePaintStrategy;
import provided.util.valueGenerator.impl.Randomizer;

/**
 * An example of an ImagePaintStrategy that randomly picks one of two animated image files to display when it is instantiated.
 * @author Peter
 */
public class BirdSheepImagePaintStrategy extends UprightImagePaintStrategy {

	/**
	 * No-parameter constructor that randomly loads one of two files: 
	 * 	model\paint\strategy\images\humbird4.gif 
	 * 	or 
	 * 	model\paint\strategy\images\img104c.gif 
	 * with a fill factor of 0.5.
	 */
	public BirdSheepImagePaintStrategy() {
		super((String) Randomizer.Singleton.randomChoice("humbird_animate.gif", "sheep_animate.gif", 0.5), 0.5);
	}

}
