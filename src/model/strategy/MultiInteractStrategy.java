package model.strategy;

import model.Ball;
import model.IBallCmd;
import model.IInteractStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * An interact strategy that combines two interact strategies.
 * @author Peter
 */
public class MultiInteractStrategy implements IInteractStrategy {
	
	/**
	 * The first interaction strategy.
	 */
	private IInteractStrategy interactStrategy1;
	
	/**
	 * The second interaction strategy.
	 */
	private IInteractStrategy interactStrategy2;
	
	/**
	 * Create a new MultiInteractStrategy.
	 * @param interactStrategy1 The first interaction strategy.
	 * @param interactStrategy2 The second interaction strategy.
	 */
	public MultiInteractStrategy(IInteractStrategy interactStrategy1, IInteractStrategy interactStrategy2) {
		this.interactStrategy1 = interactStrategy1;
		this.interactStrategy2 = interactStrategy2;		
	}

	/**
	 * Delegates to the internal strategies interact methods. 
	 */
	@Override
	public void interact(Ball context, Ball target, IDispatcher<IBallCmd> disp, Boolean isSource) {
		interactStrategy1.interact(context, target, disp, isSource);
		interactStrategy2.interact(context, target, disp, isSource);
	}

}
