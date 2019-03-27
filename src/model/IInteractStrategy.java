package model;

import provided.util.dispatcher.IDispatcher;

/**
 * Strategy that defines a directed interaction between two balls where the balls are NOT
 * equivalently processed. 
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public interface IInteractStrategy {

	/**
	 * Performs a directed interaction between the context ball and the target Ball from the perspective of the context Ball.
	 * @param context The Ball from whose perspective the interaction processing takes place.
	 * @param target The Ball that is the "other ball" in the perspective of this processing.
	 * @param disp The Dispatcher that is to be used if desired.
	 * @param interactFirst A boolean denoting whether this is the first in a series (usually two) of interactions.
	 */
	public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp, Boolean interactFirst);

	/**
	 * Null strategy with no-op behavior. 
	 * NOTE: We can use a lambda here instead of an anonymous inner class because IInteractStrategy is a functional interface (only has one
	 * abstract method).
	 */
	public static final IInteractStrategy NULL = (context, target, disp, isSource) -> {};
	
}