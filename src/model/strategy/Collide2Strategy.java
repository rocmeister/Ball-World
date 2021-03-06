package model.strategy;

import java.awt.Point;
import java.awt.geom.Point2D;

import model.Ball;
import model.IBallCmd;
import model.IInteractStrategy;
import model.IUpdateStrategy;
import provided.util.dispatcher.IDispatcher;

/**
 * Elastic collision implemented purely as an interaction strategy. Combine with Overlap to see bouncing balls.
 * NOTE: This strategy only installs a "post-collision interaction behavior" and thus at least needs 
 * to be combined with a strategy that detects an interaction, e.g. Overlap or Collide.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class Collide2Strategy<TMsg> implements IUpdateStrategy<TMsg> {
		
	@Override
	public void init(Ball context) {
		context.setInteractStrategy(new MultiInteractStrategy(context.getInteractStrategy(), new IInteractStrategy() {

			@Override
			public void interact(Ball context, Ball other, IDispatcher<IBallCmd> disp, Boolean interactFirst) {
				// IMPORTANT: this is the main difference between Collide2 and Collide.
				// Because Overlap delegates to BOTH balls interactWith methods, Collide2 uses the
				// interactFirst flag and only runs the collision mathematics on the first call.  
				if (interactFirst) {
					// the distance between the radii of the two balls
					double radiusDistance = context.getLocation().distance(other.getLocation());
					
					// Calculate the reduced mass of the two-ball system using the square of the radius as the mass of the ball (mass is proportional to the size of the ball).
					double rm = reducedMass(Math.pow(context.getRadius(), 2), Math.pow(other.getRadius(), 2));
					
					// The minimum allowed separation(sum of the ball radii) minus the actual separation(distance between ball centers). Should be a 
					// positive value. This is the amount of overlap of the balls as measured along the line between their centers.
					double deltaR = (context.getRadius() + other.getRadius()) - (radiusDistance);
					
					// Use the reduced mass and other parameters to calculate the impulse of the collision. The position of the source ball will be "nudged" out of collision distance during this calculation.
					Point2D.Double imp = impulse(context.getLocation(), context.getVelocity(), other.getLocation(), other.getVelocity(), rm, radiusDistance, deltaR);
					
					// Update the velocities of each ball by taking the impulse divided by the mass (square of the radius) and call its post-collision 
					// interaction behavior (the interactWith method of the Ball).   
					// Note that the same method can be used to update either the source or target balls simply by switching the parameters and negating the impulse.    
					// Careful here! This is simpler than it looks! Use all the resources available to you!
					updateCollision(context, other, imp.x, imp.y, disp);
					updateCollision(other, context, -imp.x, -imp.y, disp);
				}
			}
			
		}));
	}

	@Override
	public void updateState(final Ball context, IDispatcher<TMsg> dispatcher) {
		// No-op.
	}

	/**
	 * Returns the reduced mass of the two balls (m1*m2)/(m1+m2) Gives correct
	 * result if one of the balls has infinite mass.
	 * @param mSource Mass of the source ball.
	 * @param mTarget Mass of the target ball.
	 * @return The reduced mass of the two balls (m1*m2)/(m1+m2).
	 */
	protected double reducedMass(double mSource, double mTarget) {
		if (mSource == Double.POSITIVE_INFINITY)
			return mTarget;
		if (mTarget == Double.POSITIVE_INFINITY)
			return mSource;
		else
			return (mSource * mTarget) / (mSource + mTarget);
	}

	/**
	 * The amount to add to the separation distance to insure that the two balls
	 * are beyond collision distance
	 */
	private double Nudge = 1.1;

	/**
	 * Calculates the impulse (change in momentum) of the collision in the
	 * direction from the source to the target This method calculates the
	 * impulse on the source ball. The impulse on the target ball is the
	 * negative of the result. Also moves source ball out of collision range
	 * along normal direction. The change in velocity of the source ball is the
	 * impulse divided by the source's mass. The change in velocity of the target
	 * ball is the negative of the impulse divided by the target's mass
	 * 
	 * Operational note: Even though theoretically, the difference in velocities
	 * of two balls should be co-linear with the normal line between them, the
	 * discrete nature of animations means that the point where collision is
	 * detected may not be at the same point as the theoretical contact point.
	 * This method calculates the rebound directions as if the two balls were
	 * the appropriate radii such that they had just contacted
	 * _at_the_point_of_collision_detection_. This may give slightly different
	 * rebound direction than one would calculate if they contacted at the
	 * theoretical point given by their actual radii.
	 * 
	 * @param lSource Location of the source ball
	 * @param vSource Velocity of the source ball
	 * @param lTarget Location of the target ball
	 * @param vTarget Velocity of the target ball
	 * @param reducedMass Reduced mass of the two balls
	 * @param distance Distance between the two balls.
	 * @param deltaR The minimum allowed separation(sum of the ball radii) minus the actual separation(distance between ball centers). Should be a
	 * positive value. This is the amount of overlap of the balls as measured along the line between their centers.
	 * @return Return the impulse vector.
	 */
	protected Point2D.Double impulse(Point lSource, Point vSource, Point lTarget, Point vTarget, double reducedMass, double distance, double deltaR) {
		// Calculate the normal vector, from source to target
		double nx = ((double) (lTarget.x - lSource.x)) / distance;
		double ny = ((double) (lTarget.y - lSource.y)) / distance;

		// delta velocity (speed, actually) in normal direction, source to target
		double dvn = (vTarget.x - vSource.x) * nx + (vTarget.y - vSource.y) * ny;

		// move the source ball beyond collision range of the target ball, along the normal direction.
		lSource.translate((int) Math.ceil(-nx * (Nudge * deltaR)), (int) Math.ceil(-ny * (Nudge * deltaR)));

		return new Point2D.Double(2.0 * reducedMass * dvn * nx, 2.0 * reducedMass * dvn * ny);
	}

	/**
	 * Updates the velocity of the source ball, given an impulse, then uses the
	 * context's interactWith method to determine the post collision behavior, from the context
	 * ball's perspective. The change in velocity is the impulse divided by the (source) ball's mass. To change
	 * the velocity of the target ball, switch the source and target input
	 * parameters and negate the impulse values.   This will also run the post collision behavior from 
	 * the other perspective.
	 * 
	 * @param context The ball to update
	 * @param target The ball being collided with
	 * @param impX x-coordinate of the impulse
	 * @param impY y-coordinate of the impulse
	 * @param dispatcher The dispatcher
	 */
	protected void updateCollision(Ball context, Ball target, double impX, double impY, IDispatcher<IBallCmd> dispatcher) {
		int mContext = context.getRadius() * context.getRadius();

		context.getVelocity().translate((int) Math.round(impX / mContext), (int) Math.round(impY / mContext));
	}
}