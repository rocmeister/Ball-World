package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * An update strategy that combines two update strategies.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 * @author Peter
 */
public class MultiStrategy<TMsg> implements IUpdateStrategy<TMsg> {

	/**
	 * The first update strategy.
	 */
	private IUpdateStrategy<TMsg> s1;

	/**
	 * The second update strategy.
	 */
	private IUpdateStrategy<TMsg> s2;

	/**
	 * Creates a new MultiStrategy.
	 * @param s1 The first update strategy.
	 * @param s2 The second update strategy.
	 */
	public MultiStrategy(IUpdateStrategy<TMsg> s1, IUpdateStrategy<TMsg> s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public void updateState(Ball context, IDispatcher<TMsg> disp) {
		s1.updateState(context, disp);
		s2.updateState(context, disp);
	}

	@Override
	public void init(Ball host) {
		s1.init(host);
		s2.init(host);
	}
}