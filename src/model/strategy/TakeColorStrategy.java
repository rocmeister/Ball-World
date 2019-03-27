package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * Takes the color of other ball. 
 * NOTE: This strategy only installs a "post-collision interaction behavior" and thus at least needs 
 * to be combined with a strategy that detects an interaction, e.g. Overlap or Collide.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class TakeColorStrategy<TMsg> implements IUpdateStrategy<TMsg> {
	
	@Override
	public void init(Ball context) {
		context.setInteractStrategy(
				new MultiInteractStrategy(
						context.getInteractStrategy(),
						(contextBall, otherBall, disp, interactFirst) -> {
							contextBall.setColor(otherBall.getColor());
						}));
	}

	@Override
	public void updateState(Ball b, IDispatcher<TMsg> disp) {
		// No-op.
	}

}
