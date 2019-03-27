package model.strategy;

import model.Ball;
import model.IUpdateStrategy;

/**
 * Very simple abstract class that provides invariant behavior to some IUpdateStrategies.
 * Simply implements the IUpdateStrategy init method as a no-op.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public abstract class AUpdateStrategy<TMsg> implements IUpdateStrategy<TMsg> {

	@Override
	public void init(Ball host) {
		// No-op.
	}
	
}
