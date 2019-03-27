package model.strategy;

import java.awt.Color;

import model.Ball;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * Strategy that only installs an interaction strategy and thus at least needs to be combined with a strategy that detects an interaction, e.g. Overlap or Collide.
 * Takes the color of other ball. 
 * NOTE 1: This strategy only installs a "post-collision interaction behavior" and thus at least needs 
 * to be combined with a strategy that detects an interaction, e.g. Overlap or Collide.
 * NOTE 2: The frequency that a ball flashes increases proportionally to the number of balls that it meets a "criteria of interaction" with.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.   
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class FlashStrategy<TMsg> implements IUpdateStrategy<TMsg> {

	/**
	 * Tick counter that counts out the delay before color changes.
	 */
	private int count = 0; 

	/**
	 * Tick delay.
	 */
	private int delay = 40; 

	/**
	 * The original color of the ball.
	 */
	Color originalColor;
	
	@Override
	public void init(Ball context) {
		
		originalColor = context.getColor();
		
		context.setInteractStrategy(
				new MultiInteractStrategy(
						context.getInteractStrategy(), 			
						(contextBall, otherBall, disp,interactFirst) -> {							
							if (count < delay) {
								if (count < (delay / 2))
									contextBall.setColor(Color.RED);
								else
									contextBall.setColor(originalColor);
								count++;
							} else {
								count = 0;
							}
						}));
	}

	@Override
	public void updateState(Ball b, IDispatcher<TMsg> disp) {
		// No-op.
	}

}
