package model.strategy;

import java.awt.Color;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * Detects if the ball has a similar color to that of any other ball.
 * NOTE: This strategy defines a  "criteria for interaction". The interaction behavior is delegated to the
 * interactWith methods of the individual balls. 
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class SimilarColorStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * Initialize the previous interaction of the context ball to be itself.
	 * We do this because every time a ball sends a command, it checks to make sure
	 * that is not sending it to itself. Thus this is a safe value to initialize to.
	 */
	@Override
	public void init(Ball context) {
		context.setPrevInteraction(context);
	}

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {		
		// send a command to all balls
		dispatcher.updateAll((other, disp) -> {
			// check if you are receiving your own command 
			if (context != other) {
				Color c1 = context.getColor();
				Color c2 = other.getColor();
				
				if (areSimilarColors(c1, c2)) {
					// invoke the interaction strategies of the two interacting balls 
					context.interactWith(other, dispatcher, true);
					other.interactWith(context, dispatcher, false);
				}
			}
		});
	}
	
	/**
	 * Computes the difference between the two colors using a weighted Euclidean metric.
	 * @param c1 The first color to compare.
	 * @param c2 The second color to compare.
	 * @return The difference between the two colors (lower is more similar).
	 */
	private double colorDifference(Color c1, Color c2) {
		double rBar = (c1.getRed() + c2.getRed()) / 2.0;
		double deltaR = c1.getRed() - c2.getRed();
		double deltaG = c1.getGreen() - c2.getGreen();
		double deltaB = c1.getBlue() - c2.getBlue();
		double fac = (rBar * (Math.pow(deltaR, 2) - Math.pow(deltaB, 2))) / 256.0;
		
		return Math.sqrt(2 * Math.pow(deltaR, 2) + 4 * Math.pow(deltaG, 2) + 3 * Math.pow(deltaB, 2) + fac);
	}
	
	/**
	 * Returns whether or not the two colors are similar.
	 * @param c1 The first color.
	 * @param c2 The second color.
	 * @return True if the colors are similar, false otherwise.
	 */
	private Boolean areSimilarColors(Color c1, Color c2) {
		return colorDifference(c1, c2) < 200.0;
	}

}
