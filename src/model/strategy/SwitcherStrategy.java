package model.strategy;

import model.Ball;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * This class is a wrapper over a single arbitrary strategy which can be changed.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 * @author Peter Dulworth (psd2)
 */
public class SwitcherStrategy<TMsg> implements IUpdateStrategy<TMsg> {

	/**
	 * The strategy.
	 */
	private IUpdateStrategy<TMsg> strategy = new StraightStrategy<TMsg>();

	@Override
	public void updateState(Ball context, IDispatcher<TMsg> disp) {
		this.strategy.updateState(context, disp);
	}

	/**
	 * @param newStrategy The new strategy to set.
	 */
	public void setStrategy(IUpdateStrategy<TMsg> newStrategy) {
		this.strategy = newStrategy;
	}

	/**
	 * Called every time a new switcher ball is created.
	 */
	@Override
	public void init(Ball host) {
		strategy.init(host);
	}

}
