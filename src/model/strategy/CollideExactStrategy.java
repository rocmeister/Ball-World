package model.strategy;

import java.awt.Point;
import java.awt.geom.Point2D;

import model.Ball;
import model.IBallCmd;
import provided.util.dispatcher.IDispatcher;

/**
 * A physically correct elastic two-body collision model. 
 * @author Peter Dulworth (psd2), Rocky Wu (lw31)
 */
public class CollideExactStrategy extends AUpdateStrategy<IBallCmd> {

	@Override
	public void updateState(final Ball context, IDispatcher<IBallCmd> dispatcher) {
		dispatcher.updateAll(new IBallCmd() {

			@Override
			public void apply(Ball other, IDispatcher<IBallCmd> disp) {
				// check if you are receiving your own command 
				if (context != other) {
					double radiusDistance = context.getLocation().distance(other.getLocation());
					// if the balls are overlapping
					if ((context.getRadius() + other.getRadius()) > radiusDistance) {

						// The minimum allowed separation(sum of the ball radii) minus the actual separation(distance between ball centers). Should be a 
						// * positive value. This is the amount of overlap of the balls as measured along the line between their centers.
						double deltaR = (context.getRadius() + other.getRadius()) - (radiusDistance);
						
						double collisionTime = collisionTime(context.getLocation(), other.getLocation(), context.getVelocity(), other.getVelocity(), context.getRadius() + other.getRadius());
						
						if (-1.0 <= collisionTime && collisionTime <= 0) {
								
							// Calculate the reduced mass of the two-ball system using the square of the radius as the mass of the ball (mass is proportional to the size of the ball).
							double rm = reducedMass(Math.pow(context.getRadius(), 2), Math.pow(other.getRadius(), 2));
							
							// Use the reduced mass and other parameters to calculate the impulse of the collision. The position of the source ball will be "nudged" out of collision distance during this calculation.
							Point2D.Double imp = impulse(context.getLocation(), context.getVelocity(), other.getLocation(), other.getVelocity(), rm, radiusDistance, deltaR);
													
							// Update the velocities of each ball by taking the impulse divided by the mass (square of the radius) and call its post-collision 
							// interaction behavior (the interactWith method of the Ball).   
							// Note that the same method can be used to update either the source or target balls simply by switching the parameters and negating the impulse.    
							// Careful here! This is simpler than it looks! Use all the resources available to you!
							updateCollision(context, other, imp.x, imp.y, disp, collisionTime);
							updateCollision(other, context, -imp.x, -imp.y, disp, collisionTime);
						}
					}
				}
			}
		});
	}

	/**
	 * Calculates the time of the first contact between two balls. A negative returned time means that the balls 
	 * collided in the past. A positive time value means that either the balls will collide in the future,
	 * or if equal to Double.MAX_VALUE, they will never collide, i.e. are traveling parallel to each other. 
	 * The contact time is returned in units of timer ticks.
	 * 
	 * IMPORTANT NOTE: Only negative time values should be used for collision detection because negative times
	 * indicate that the balls collided in the past. However, any time less than -1.0 means that the collision 
	 * occurred during the previous tick cycle and thus should be ignored.  
	 * 
	 * @param x1 The location of the first ball.
	 * @param x2 The location of the second ball.
	 * @param v1 The velocity of the first ball.
	 * @param v2 The velocity of the second ball.
	 * @param minSeparation  The minimum separation, i.e. contact distance between the balls.
	 * @return The first possible contact time between the two balls.
	 */
	private double collisionTime(Point x1, Point x2, Point v1, Point v2, double minSeparation) {
		Point deltaX = deltaP(x1, x2);
		Point deltaV = deltaP(v1, v2);
		double deltaX2 = deltaX.distanceSq(0.0, 0.0);
		double deltaV2 = deltaV.distanceSq(0.0, 0.0);
		double R2 = minSeparation*minSeparation;
		double dvdx = deltaV.x * deltaX.x + deltaV.y + deltaX.y;

		double root2 = dvdx * dvdx - deltaV2 * (deltaX2 - R2);

		if (root2 < 0.0)
			return Double.MAX_VALUE;   // no solution for t

		double t = (-dvdx - Math.sqrt(root2)) / deltaV2;   // want most negative time solution, i.e. first contact

		return t;
	}

	/**
	 * Returns the vector that points from point p1 to point p2 
	 * @param p1 The first point.
	 * @param p2 The second point.
	 * @return The difference vector between p1 and p2.
	 */
	private Point deltaP(Point p1, Point p2) {
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}

	/**
	 * Returns the reduced mass of the two balls (m1*m2)/(m1+m2). Gives correct
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
	 * Calculates the impulse (change in momentum) of the collision in the
	 * direction from the source to the target This method calculates the
	 * impulse on the source ball. The impulse on the target ball is the
	 * negative of the result. 
	 * 
	 * @param lSource Location of the source ball
	 * @param vSource Velocity of the source ball
	 * @param lTarget Location of the target ball
	 * @param vTarget Velocity of the target ball
	 * @param reducedMass Reduced mass of the two balls
	 * @param distance Distance between the two balls.
	 * @param deltaR The minimum allowed separation (sum of the ball radii) minus the actual separation (distance between ball centers). Should be a
	 * positive value.  This is the amount of overlap of the balls as measured along the line between their centers.
	 * @return The impulse vector.
	 */
	protected Point2D.Double impulse(Point lSource, Point vSource, Point lTarget, Point vTarget, double reducedMass, double distance, double deltaR) {		
		// Calculate the normal vector, from source to target
		double nx = ((double) (lTarget.x - lSource.x)) / distance;
		double ny = ((double) (lTarget.y - lSource.y)) / distance;

		// delta velocity (speed, actually) in normal direction, source to target
		double dvn = (vTarget.x - vSource.x) * nx + (vTarget.y - vSource.y) * ny;

		return new Point2D.Double(2.0 * reducedMass * dvn * nx, 2.0 * reducedMass * dvn * ny);
	}

	/**
	 * Updates the velocity and position of the source ball (context), given an impulse, then uses the
	 * context's interactWith method to determine the post collision behavior, from the context
	 * ball's perspective. The change in velocity is the impulse divided by the (source) ball's mass. To change
	 * the velocity of the target ball, switch the source and target input
	 * parameters and negate the impulse values.   This will also run the post collision behavior from 
	 * the other perspective.
	 * 
	 * @param context The source ball to update.
	 * @param target The ball being collided with.
	 * @param impX x-coordinate of the impulse.
	 * @param impY y-coordinate of the impulse.
	 * @param dispatcher The dispatcher.
	 * @param tContact The first ball contact time in ticks. Should be a negative number.
	 */
	protected void updateCollision(Ball context, Ball target, double impX, double impY, IDispatcher<IBallCmd> dispatcher, double tContact) {
		int mContext = context.getRadius() * context.getRadius();

		double dx = context.getVelocity().x * tContact;
		double dy = context.getVelocity().y * tContact;
		
		context.getVelocity().translate((int) Math.round(impX / mContext),(int) Math.round(impY / mContext));
		dx += -context.getVelocity().x * tContact;
		dy += -context.getVelocity().y * tContact;
		context.getLocation().translate((int)Math.round(dx), (int)Math.round(dy));

		context.interactWith(target, dispatcher, true);
	}
}
