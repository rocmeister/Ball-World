package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * Deletes other when it collides.
 * NOTE: This strategy only installs a "post-collision interaction behavior" and thus at least needs 
 * to be combined with a strategy that detects an interaction, e.g. Overlap or Collide.
 * @param <TMsg> The type of message that the supplied IDispatcher can send. 
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class KillStrategy<TMsg> implements IUpdateStrategy<TMsg> {

	public void init(Ball context) {
		context.setInteractStrategy(
				new MultiInteractStrategy(
						context.getInteractStrategy(), 
						(contextBall, targetBall, disp, interactFirst) -> disp.removeObserver(targetBall)));		
	}
	
	@Override
	public void updateState(Ball context, IDispatcher<TMsg> disp) {
		// No-op update state method.
	}
}