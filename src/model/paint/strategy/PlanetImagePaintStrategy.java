package model.paint.strategy;

import model.paint.ImagePaintStrategy;
import provided.util.valueGenerator.impl.Randomizer;

/**
* Paint strategy that upon instantiation, randomly picks an image.
* @author sgj1
* @author psd2
*
*/
public class PlanetImagePaintStrategy extends ImagePaintStrategy {
	
	/**
	 * Randomly pick one of the image files to use when being constructed.
	 */
	public PlanetImagePaintStrategy() {
		super((new String[]{ "Earth.png", "Jupiter.png", "Mars.png", "Saturn.png", "Venus.png" }) // done to avoid using a static array
				[Randomizer.Singleton.randomInt(0, 4)], .75);
	}

}
