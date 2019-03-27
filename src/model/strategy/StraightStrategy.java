package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * An update strategy that does nothing.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 * @author Peter Dulworth (psd2)
 */
public class StraightStrategy<TMsg> implements IUpdateStrategy<TMsg> {
	
	@Override
	public void init(Ball host) {
		// No-op.
	}

	@Override
	public void updateState(Ball context, IDispatcher<TMsg> disp) {
		// No-op.
	}
	
}
