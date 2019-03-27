package model.strategy;

import model.Ball;
import model.IBallCmd;
import provided.util.dispatcher.IDispatcher;

import java.awt.Point;

/**
 * Strategy that both collides and interacts.
 * Adds an identical ball whenever it overlaps with another ball.
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class SpawnStrategy extends AUpdateStrategy<IBallCmd> {
	
	/**
	 * Tick counter that counts out the delay before another ball can be spawned.
	 */
	private int count = 0; 

	/**
	 * Tick delay which increases at each spawn to keep total spawn rate from exponentially exploding.
	 */
	private int delay = 100; 

	@Override
	public void updateState(final Ball context, IDispatcher<IBallCmd> dispatcher) {
		if (delay < count++) { 
			dispatcher.updateAll(new IBallCmd() {

				@Override
				public void apply(Ball other, IDispatcher<IBallCmd> disp) {
					// check if you are receiving your own command 
					if (count != 0 && context != other) {
						// if the balls are overlapping
						if ((context.getRadius() + other.getRadius()) > context.getLocation().distance(other.getLocation())) {
							// create a copy of the original ball but with a different velocity and a new spawn strategy
							disp.addObserver(new Ball(
									new Point(context.getLocation()),
									context.getRadius(), 
									new Point(-context.getVelocity().x + 1, -context.getVelocity().y + 1),
									context.getColor(),
									context.getContainer(), 
									new SpawnStrategy(), 
									context.getPaintStrategy()));
							count = 0;
							delay *= 5;
						}
					}
				}
			});
		}
	}	
}
