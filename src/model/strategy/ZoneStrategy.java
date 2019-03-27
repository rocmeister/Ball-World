package model.strategy;

import java.awt.Color;

import model.Ball;
import model.IBallCmd;
import provided.util.dispatcher.IDispatcher;

/**
 * An update strategy that sets the color based on which quadrant the location is.
 * @author Peter
 */
public class ZoneStrategy extends AUpdateStrategy<IBallCmd> {

	@Override
	public void updateState(Ball context, IDispatcher<IBallCmd> disp) {
		int x = context.getLocation().x;
		int y = context.getLocation().y;
		int w = context.getContainer().getWidth();
		int h = context.getContainer().getHeight();

		// top left
		if (x > 0 && x <= w / 2 && y > 0 && y <= h / 2) {
			context.setColor(Color.GREEN);
		}

		// bottom left
		if (x > 0 && x <= w / 2 && y > h / 2 && y <= h) {
			context.setColor(Color.BLUE);
		}

		// top right
		if (x > w / 2 && x <= w && y > 0 && y <= h / 2) {
			context.setColor(Color.RED);
		}

		// bottom right
		if (x > w / 2 && x <= w && y > h / 2 && y <= h) {
			context.setColor(Color.YELLOW);
		}

	}
}
