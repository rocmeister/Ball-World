package model.strategy;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import model.Ball;
import model.IBallCmd;
import provided.util.dispatcher.IDispatcher;

/**
 * This strategy causes a ball to fall to the bottom and bounce.
 * @author Peter
 */
public class GravityStrategy extends AUpdateStrategy<IBallCmd> {

	/**
	 * The number of timer cycles to wait before updating state.
	 */
	private int max = 30;

	/**
	 * A counter to  count the number of timer cycles.
	 */
	private int count = max;

	/**
	 * A boolean switch that is only true during the first call to {@link #updateState(Ball, IDispatcher) updateState.}
	 */
	private Map<Ball, Boolean> firstUpdate = new HashMap<Ball, Boolean>();

	@Override
	public void updateState(Ball b, IDispatcher<IBallCmd> disp) {
		
		if (!firstUpdate.containsKey(b) || firstUpdate.get(b) == false) {
			if (b.getVelocity().y == 0) {
				b.setVelocity(new Point(0, 1));
			}
			b.setVelocity(new Point(0, Math.abs(b.getVelocity().y)));
		}
		firstUpdate.putIfAbsent(b, true);

		if (b.getVelocity().y < 0) {
			if (0 > count) {
				b.setVelocity(new Point(0, -b.getVelocity().y));
				count = max;
			} else {
				count--;
			}
		}
	}
}
