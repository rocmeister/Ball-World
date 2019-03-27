package model.paint.strategy;

import model.paint.ImagePaintStrategy;

/**
 * Paint strategy that paints a FIFA soccer ball as its image.
 * @author sgj1
 * @author psd2
 */
public class SoccerImagePaintStrategy extends ImagePaintStrategy {

	/**
	 * Constructor that loads the soccer ball image.
	 */
	public SoccerImagePaintStrategy() {
		super("FIFA_Soccer_Ball.png", .75);
	}

}
