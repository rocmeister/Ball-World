package model.strategy;

import java.awt.Dimension;
import java.awt.Point;

import model.Ball;
import model.IBallCmd;
import provided.util.dispatcher.IDispatcher;
import util.Randomizer;

/**
 * An update strategy that jumps to a random location.
 * @author Peter
 */
public class TeleportingStrategy extends AUpdateStrategy<IBallCmd> {

	/**
	 * A counter.
	 */
	private int counter = 0;

	/**
	 * The number of period of the teleportation cycle.
	 */
	private int teleportPeriod = 100;

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		counter += 1;
		if (counter % teleportPeriod == 0) {
			Point p = Randomizer.Singleton
					.randomLoc(new Dimension(context.getContainer().getWidth() - (2 * context.getRadius()),
							context.getContainer().getHeight() - (2 * context.getRadius())));
			p.x += context.getRadius(); // move p to the right by r
			p.y += context.getRadius(); // move p down by r
			context.setLocation(p);
		}
	}
}
