package model.paint;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import model.Ball;
import model.IPaintStrategy;

/**
 * The top-level affine transform-based paint strategy that provides services for its subclasses, plus default behaviors and abstract behaviors.
 * @author Peter
 */
public abstract class APaintStrategy implements IPaintStrategy {

	/**
	 * The affine transform used by this paint strategy to translate, scale and rotate the image.
	 */
	protected AffineTransform at;

	/**
	 * Constructor that initializes the strategy with an affine transform.
	 * @param at The AffineTransform for this paint strategy to use.
	 */
	public APaintStrategy(AffineTransform at) {
		this.at = at;
	}

	/**
	 * By default, do nothing for initialization.
	 */
	@Override
	public void init(Ball context) {
	}

	/**
	 * Paints on the given graphics context using the color, scale and direction provided by the host. 
	 * This is done by setting up the AffineTransform to rotate then scale then translate. 
	 * Calls paintXfrm to actually perform the painting, using the set up transform. 
	 * Calls paintCfg just before calling paintXfrm.
	 * @param g The Graphics context that will be paint on.
	 * @param host The host Ball that the required information will be pulled from.
	 */
	@Override
	public void paint(Graphics g, Ball host) {
		double scale = host.getRadius();
		at.setToTranslation(host.getLocation().x, host.getLocation().y); // happens third
		at.scale(scale, scale); // happens second
		at.rotate(host.getVelocity().x, host.getVelocity().y); // happens first 
		g.setColor(host.getColor());
		paintCfg(g, host);
		paintXfrm(g, host, this.at);

		// FOR DEBUGGING:
		//		g.drawOval(host.getLocation().x - host.getRadius(), host.getLocation().y - host.getRadius(), 2 * host.getRadius(), 2 * host.getRadius());
		//		g.drawRect(host.getLocation().x - host.getRadius(), host.getLocation().y - host.getRadius(), 2 * host.getRadius(), 2 * host.getRadius());
		//		g.fillOval(host.getLocation().x - 3, host.getLocation().y - 3, 6, 6);
		//		g.fillOval(host.getLocation().x - host.getRadius() - 3, host.getLocation().y - host.getRadius() - 3, 6, 6);

		/*
		 * First, when the painting strategy is created, the prototype is created ONCE.   The prototype is INVARIANT and thus, is never modified.
		 * 
		 * Typically we will reuse the AffineTransform object rather than re-instantiate it for every use.
		 * 
		 * We can summarize the painting process using prototypes in these steps, which occur at EVERY painting event:
		 * 
		 * 	Set the affine transform to translate by the ball's position
		 * 	Add the rotation and scaling to the affine transform as determined by the balls size (radius), velocity and/or other properties at the moment of painting.
		 * 	Add any other desired transformations.
		 * 	Transform the prototype shape into a new Shape object which has the desired position, rotation, size, etc. 
		 * 	Use the provided Graphics2D object to paint the new, transformed Shape onto the screen.
		 * 
		 * Do not confuse the moving of the ball, which is the changing of the value of the ball's location, with the translation of the prototype shape, which is the generation of the desired shape for painting at the current ball location from the origin-centered prototype shape.
		 * 
		 * Moving the ball is a completely separate operation from painting the ball.   
		 * The ball is painted at the position on the screen corresponding to the current location of the ball.   
		 * The ball does not move during painting.    
		 * The prototype is invariant and thus never moves from the origin. 
		 * 
		 * The prototype shape is NOT the ball.   
		 * The prototype shape contains just the shape information of ball, without any location, size or rotational information.     
		 * The location, size and rotational information needed to create the final displayed image of the ball is determined at painting time.   
		 * That information is embodied in the affine transform.    
		 * Thus, it is the combination of the invariant prototype and the variant affine transform that creates the final shape to be painted on the screen.
		 */
	}

	/**
	 * Defined by a subclass if additional processing, e.g. staying upright, is required before the actual painting takes place.
	 * @param g The Graphics context that will be paint on.
	 * @param host The host Ball that the required information will be pulled from.
	 */
	protected void paintCfg(Graphics g, Ball host) {
		/*
		 * The paintCfg method is set to be a concrete no-op that the subclasses may or may not override.   
		 * This method allows the subclass to inject additional processing into the paint method process before the final transformations are performed.     
		 * Since this method is "protected", it is only available for use by the subclasses and not other types of objects.
		 */
	}

	/**
	 * Paints the host onto the given Graphics context. The image is translated, scaled and 
	 * rotated as determined by the given affine transformation. This method is intended to 
	 * be called either by a class's (or superclass's) own paint method or by another paint 
	 * strategy who is sharing the affine transform. This allows the same transformation to 
	 * be shared amongst multiple paint strategies.
	 * @param g The graphics context to draw upon.
	 * @param host The host ball.
	 * @param at The affine transform to use.
	 */
	public abstract void paintXfrm(Graphics g, Ball host, AffineTransform at);

	/*
	 * A secondary paint operation that is the last step of the above paint method, which does not calculate its own affine transform, but instead, uses a supplied affine transform.   
	 * Notice that the translation, rotation and scaling have already been added to the affine transform before it gets to paintXfrm.  
	 * This allows the same affine transform to be shared amongst paint strategies, reducing the number of times that it has to be calculated.   
	 * When an affine transform instance is being shared amongst strategies, then it is invariant across those strategies.  
	 * Thus, this method allows an invariant translation, rotation and scaling to be performed (previously in the paint method) that applies to all composed strategies.    
	 * But as we have seen before, invariant properties cause problems when we try to compose entities together as those invariant properties tend to "run into each other" and make 
	 * composition very difficult.   
	 * This method does not include the invariant parts of the affine transform process, so affine transform-based paint strategies can be combined based on this method 
	 * but not based on the paint method.  
	 * Since APaintStrategy doesn't know what sort of thing is being painted, it has no idea how exactly to apply the affine transform, so the paintXfrm method must 
	 * remain abstract, forcing the subclasses to implement it. 
	 */

	/**
	 * Protected accessor for the internal affine transform.
	 * @return This instance's affine transform.
	 */
	protected AffineTransform getAT() {
		return at;
	}

}
