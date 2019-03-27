package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import provided.util.dispatcher.IDispatcher;
import provided.util.dispatcher.IObserver;

/**
 * The Ball class provides methods and fields that are common to all balls.
 * @author Peter Dulworth (psd2)
 */
public class Ball implements IObserver<IBallCmd> {

	/**
	 * The location of the center of the ball.
	 */
	private Point p;

	/**
	 * The radius of the ball.
	 */
	private int r;

	/**
	 * The velocity vector of the ball.
	 */
	private Point v;

	/**
	 * The color of the ball.
	 */
	private Color c;
	
	/**
	 * The ball most recently interacted with. 
	 */
	private Ball prevInteraction;

	/**
	 * The component in which the ball lives.
	 */
	private Component component;
	
	/**
	 * The update strategy of the ball.
	 */
	private IUpdateStrategy<IBallCmd> updateStrategy = (new IUpdateStrategyFac.NullFactory<IBallCmd>()).make();

	/**
	 * The paint strategy of the ball.
	 */
	private IPaintStrategy paintStrategy = IPaintStrategy.NULL;
	
	/**
	 * The interact strategy of the ball.
	 */
	private IInteractStrategy interactStrategy = IInteractStrategy.NULL;
	
	/**
	 * Singleton null ball.
	 */
	public static final Ball NULL = new Ball() {};
	
	/**
	 * No parameter constructor used for creating a null ball.
	 */
	public Ball() {
		setLocation(new Point(0, 0));
		setRadius(0);
		setVelocity(new Point(0, 0));
		setColor(Color.BLACK);
	}

	/**
	 * The following constructor takes all the parameters and assigns them to their respective fields in the ball.
	 * 
	 * @param initialLocation A point representing the initial location of the center of the ball.
	 * @param radius An integer representing the radius of the ball.
	 * @param velocity A point representing the velocity vector of the ball.
	 * @param color A color representing the color of the ball.
	 * @param component The component on which the ball will be painted.
	 * @param updateStrategy The update strategy that the ball uses.
	 * @param paintStrategy The paint strategy that the ball uses.
	 */
	public Ball(Point initialLocation, int radius, Point velocity, Color color, Component component,
			IUpdateStrategy<IBallCmd> updateStrategy, IPaintStrategy paintStrategy) {
		this.p = initialLocation;
		this.r = radius;
		this.v = velocity;
		this.c = color;
		this.component = component;
		
		this.setUpdateStrategy(updateStrategy);

		// IPaintStrategy.init() is used to initialize the strategy and host ball. This method must be run whenever the ball gets a new strategy, 
		// such as in a setPaintStrategy method or even in the constructor of the ball. The safest way to do this is to have 
		// the constructor set the paint strategy field by calling the setPaintStrategy method thus keeping the code to
		// initialize the strategy only in a single location.
		this.setPaintStrategy(paintStrategy);
	}

	@Override
	public void update(IDispatcher<IBallCmd> disp, IBallCmd cmd) {
		cmd.apply(this, disp);
	}
	
	/**
	 * Update the state of the ball. Delegates to the update strategy.
	 * @param disp The Dispatcher that sent the command that is calling this method.
	 */
	public void updateState(IDispatcher<IBallCmd> disp){
	    updateStrategy.updateState(this, disp); // update this ball's state using the strategy.     
	}

	/**
	 * The following method updates the balls {@link #p location} based on its {@link #v velocity vector}.
	 */
	public void move() {
		this.p.translate(v.x, v.y);
	}

	/**
	 * The following method detecting if the edge of the ball is beyond the top/bottom left/right edge of the {@link #component canvas}.
	 * It updates the {@link #p location} of the ball based on which wall it went past.
	 */
	public void bounce() {
		if (p.x + r > component.getWidth()) { // right
			p.x = p.x - 2 * (p.x - component.getWidth() + r);
			v.x = (int) Math.round(-1.0 * v.x);
		}

		if (p.x - r < 0) { // left
			p.x = p.x + 2 * (-p.x + r);
			v.x = (int) Math.round(-1.0 * v.x);
		}

		if (p.y + r > component.getHeight()) { // bottom
			p.y = p.y - 2 * (p.y - component.getHeight() + r);
			v.y = (int) Math.round(-1.0 * v.y);
		}

		if (p.y - r < 0) { // top
			p.y = p.y + 2 * (-p.y + r);
			v.y = (int) Math.round(-1.0 * v.y);
		}
	}

	/**
	 * The following method paints the ball on the {@link #component canvas} based on its {@link #c color} field, {@link #p location} and its {@link #r radius}. 
	 * 
	 * @param g This is the {@link java.awt.Graphics Graphics} object that the ball should paint itself on.
	 */
	public void paint(Graphics g) {
		this.paintStrategy.paint(g, this);
	}
	
	/**
	 * Used to invoke the ball's interaction strategy. 
	 * @param target The Ball that is the "other ball" in the perspective of this processing.
	 * @param disp The dispatcher.
	 * @param interactFirst A boolean denoting whether this is the first in a series (usually two) of interactions.
	 */
	public void interactWith(Ball target, IDispatcher<IBallCmd> disp, Boolean interactFirst) {
		this.interactStrategy.interact(this, target, disp, interactFirst);
	}

	/**
	 * Gets the current velocity.
	 * @return current velocity.
	 */
	public Point getVelocity() {
		return this.v;
	}

	/**
	 * Sets a new velocity.
	 * @param v New velocity to set.
	 */
	public void setVelocity(Point v) {
		this.v = v;
	}

	/**
	 * @return the p
	 */
	public Point getLocation() {
		return p;
	}

	/**
	 * @param p the p to set
	 */
	public void setLocation(Point p) {
		this.p = p;
	}

	/**
	 * @return r, the radius.
	 */
	public int getRadius() {
		return r;
	}

	/**
	 * @param r The radius to set.
	 */
	public void setRadius(int r) {
		this.r = r;
	}

	/**
	 * @return the c
	 */
	public Color getColor() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setColor(Color c) {
		this.c = c;
	}

	/**
	 * @return the component
	 */
	public Component getContainer() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setContainer(Component component) {
		this.component = component;
	}

	/**
	 * @return the strategy
	 */
	public IUpdateStrategy<IBallCmd> getUpdateStrategy() {
		return updateStrategy;
	}

	/**
	 * @param updateStrategy The update strategy to set.
	 */
	public void setUpdateStrategy(IUpdateStrategy<IBallCmd> updateStrategy) {
		this.updateStrategy = updateStrategy;
		this.updateStrategy.init(this);
	}

	/**
	 * @return The instance's paint strategy.
	 */
	public IPaintStrategy getPaintStrategy() {
		return paintStrategy;
	}

	/**
	 * @param paintStrategy The paint strategy to set the instance's paint strategy to.
	 */
	public void setPaintStrategy(IPaintStrategy paintStrategy) {
		this.paintStrategy = paintStrategy;
		this.paintStrategy.init(this);
	}

	/**
	 * @return the interactStrategy
	 */
	public IInteractStrategy getInteractStrategy() {
		return interactStrategy;
	}

	/**
	 * @param interactStrategy the interactStrategy to set
	 */
	public void setInteractStrategy(IInteractStrategy interactStrategy) {
		this.interactStrategy = interactStrategy;
		// TODO: initialize strategy?
	}

	/**
	 * @return the prevInteraction
	 */
	public Ball getPrevInteraction() {
		return prevInteraction;
	}

	/**
	 * @param prevInteraction the prevInteraction to set
	 */
	public void setPrevInteraction(Ball prevInteraction) {
		this.prevInteraction = prevInteraction;
	}
}
