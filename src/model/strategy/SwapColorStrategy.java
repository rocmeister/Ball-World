package model.strategy;

import java.awt.Color;

import model.Ball;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * Swaps colors with whatever ball it interacts with.
 * NOTE: This strategy only installs a "post-collision interaction behavior" and thus at least needs 
 * to be combined with a strategy that detects an interaction, e.g. Overlap or Collide.
 * @param <TMsg> The type of message that the supplied IDispatcher can send. 
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class SwapColorStrategy<TMsg> implements IUpdateStrategy<TMsg> {

	@Override
	public void init(Ball context) {
		context.setInteractStrategy(
				new MultiInteractStrategy(
						context.getInteractStrategy(), 
						(contextBall, otherBall, disp, interactFirst) -> {
							if (interactFirst) {
								// store the colors of the ball
								Color contextColor = contextBall.getColor();
								Color otherColor = otherBall.getColor();
								
								// swap the colors
								contextBall.setColor(otherColor);
								otherBall.setColor(contextColor);
							}
						}));
	}

	@Override
	public void updateState(Ball b, IDispatcher<TMsg> disp) {
		// No-op.
	}

}
