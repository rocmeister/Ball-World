package model.strategy;

import java.util.HashMap;
import java.util.Map;

import model.Ball;
import provided.util.dispatcher.IDispatcher;

/**
 * An update strategy that shrinks the radius to a certain size.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 * @author Peter
 */
public class ShrinkStrategy<TMsg> extends AUpdateStrategy<TMsg> {
	
	/**
	 * A boolean switch that is only true during the first call to {@link #updateState(Ball, IDispatcher) updateState.}
	 */
	private Map<Ball, Boolean> firstUpdate = new HashMap<Ball, Boolean>();


	@Override
	public void updateState(Ball b, IDispatcher<TMsg> disp) {
		
		if (!firstUpdate.containsKey(b) || firstUpdate.get(b) == false)
			b.setRadius(100);
		firstUpdate.putIfAbsent(b, true);

		if (b.getRadius() > 10) {
			b.setRadius(b.getRadius() - 1);
		}
	}
}
