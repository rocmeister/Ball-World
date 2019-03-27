package model.strategy;

import model.Ball;
import model.IBallCmd;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * Detects if the ball overlaps with any other balls. Does not detect an overlap between a ball and its most recently overlapped ball!
 * NOTE: This strategy defines a  "criteria for interaction". The interaction behavior is delegated to the
 * interactWith methods of the individual balls.
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class OverlapStrategy implements IUpdateStrategy<IBallCmd> {

	/**
	 * Initialize the previous interaction of the context ball to be itself.
	 * We do this because every time a ball sends a command, it checks to make sure
	 * that is not sending it to itself. Thus this is a safe value to initialize to.
	 */
	@Override
	public void init(Ball host) {
		host.setPrevInteraction(host);
	}

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {
		
		// send a command to all balls
		dispatcher.updateAll((other, disp) -> {

			// check if you are receiving your own command 
			if (context != other) {
				double radiusDistance = context.getLocation().distance(other.getLocation());
				Boolean isOverlapping = context.getRadius() + other.getRadius() > radiusDistance; 
				
				// if the balls are overlapping
				if (isOverlapping) {
					
					// if the other ball isn't the most recent ball that the context ball interacted with 
					if (context.getPrevInteraction() != other) {
						// invoke the interaction strategies of the two interacting balls 
						context.interactWith(other, dispatcher, true);
						other.interactWith(context, dispatcher, false);

						// set the previous interaction fields of each ball 
						context.setPrevInteraction(other);
						other.setPrevInteraction(context);
					}
				}
			}
		});
	}
}
