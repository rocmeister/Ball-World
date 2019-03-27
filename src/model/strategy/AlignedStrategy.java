package model.strategy;

import model.Ball;
import model.IBallCmd;
import provided.util.dispatcher.IDispatcher;

/**
 * Detects if two balls' velocities are roughly aligned.
 * NOTE: This strategy defines a  "criteria for interaction". The interaction behavior is delegated to the
 * interactWith methods of the individual balls.  
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class AlignedStrategy extends AUpdateStrategy<IBallCmd> {
	
	/**
	 * The error margin.
	 */
	private final double epsilon = 2 * Math.PI * 0.05; // 5 percent error margin over 360 degrees
				
	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> dispatcher) {
		dispatcher.updateAll((other, disp) -> {
			// check if you are receiving your own command 
			if (context != other) {
				// if the balls are overlapping
				if (Math.abs(Math.atan2(context.getVelocity().getY(), context.getVelocity().getX()) -
						Math.atan2(other.getVelocity().getY(), other.getVelocity().getX())) < epsilon) {
					// invoke the interaction strategies of the two interacting balls 
					context.interactWith(other, dispatcher, true);
					other.interactWith(context, dispatcher, false);
				}
			}
		});
	}
}
