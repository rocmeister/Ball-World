package model;

import provided.util.dispatcher.IDispatcher;

/**
 * The strategy that runs when a Ball updates its state. 
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 * @author Peter
 */
public interface IUpdateStrategy<TMsg> {
	
	/**
	 * Initializes the given ball. Called once by the ball when the strategy is first loaded.
	 * @param context The ball to initialize.
	 */
	public void init(Ball context);

	/**
	 * Update the properties of a ball.
	 * @param b The ball to which the strategy belongs.
	 * @param disp The dispatcher.
	 */
	public void updateState(Ball b, IDispatcher<TMsg> disp);	
}