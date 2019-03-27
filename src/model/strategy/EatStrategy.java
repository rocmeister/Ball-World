package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * Kills whomever the ball collides with and increases its area by the killed ball's area.
 * NOTE: This strategy only installs a "post-collision interaction behavior" and thus at least needs 
 * to be combined with a strategy that detects an interaction, e.g. Overlap or Collide.
 * @param <TMsg> The type of message that the supplied IDispatcher can send. 
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class EatStrategy<TMsg> implements IUpdateStrategy<TMsg> {

	@Override
	public void init(Ball context) {
		context.setInteractStrategy(
				new MultiInteractStrategy(
						context.getInteractStrategy(), 
						(contextBall, otherBall, disp, interactFirst) -> {
							// Calculate the radius of the ball needed to increase the area by the killed balls area
							double otherArea = Math.PI * otherBall.getRadius() * otherBall.getRadius();
							double thisArea = Math.PI * contextBall.getRadius() * contextBall.getRadius();				
							double desiredArea = otherArea + thisArea;
							double desiredRadius = Math.sqrt(desiredArea / Math.PI);
															
							// Kill the other ball and increase the context ball's radius 
							disp.removeObserver(otherBall);
							contextBall.setRadius((int) Math.round(desiredRadius));
						}));
	}

	@Override
	public void updateState(Ball b, IDispatcher<TMsg> disp) {
		// No-op update state method.
	}

}
