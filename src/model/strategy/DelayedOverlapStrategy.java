package model.strategy;

import model.Ball;
import model.IBallCmd;
import provided.util.dispatcher.IDispatcher;

/**
 * Detects if the ball overlaps with any other balls on a delayed timer cycle.
 * NOTE: This strategy defines a  "criteria for interaction". The interaction behavior is delegated to the
 * interactWith methods of the individual balls.
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class DelayedOverlapStrategy extends AUpdateStrategy<IBallCmd> {
	
	/**
	 * Tick counter that counts out the delay before another ball can be spawned.
	 */
	private int count = 0; 

	/**
	 * Number of ticks between checks.
	 */
	private int delay = 15; 

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {
		if (count++ > delay) { // evaluates count > delay then increments count
			// send a command to all balls
			dispatcher.updateAll((other, disp) -> {
				// check if you are receiving your own command 
				if (context != other) {
					double radiusDistance = context.getLocation().distance(other.getLocation());
					// if the balls are overlapping
					Boolean inContact = (context.getRadius() + other.getRadius()) > radiusDistance; 
					if (inContact) {
						// invoke the interaction strategies of the two interacting balls 
						context.interactWith(other, dispatcher, true);
						other.interactWith(context, dispatcher, false); 
					}
					count = 0;
				}
			});
		}
	}
}
