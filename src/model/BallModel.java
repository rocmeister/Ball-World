package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.Timer;

import model.strategy.MultiStrategy;
import model.strategy.SwitcherStrategy;
import provided.util.dispatcher.IDispatcher;
import provided.util.dispatcher.impl.SequentialDispatcher;
import provided.util.loader.impl.ObjectLoader;
import util.Randomizer;

/**
 * The class that manages the creation, deletion and animation of the balls.
 * @author Peter Dulworth (psd2), Andrew Hadad (aah6)
 */
public class BallModel {

	/**
	 * The model-to-view adapter is initialized to a no-op to insure that system always has well-defined behavior.
	 */
	private IM2VAdapter m2vAdapter = IM2VAdapter.NULL_OBJECT;

	/**
	 * The dispatcher that keeps track of the balls and sends them commands.
	 */
	private IDispatcher<IBallCmd> myDispatcher = new SequentialDispatcher<IBallCmd>();

	/**
	 * The delay between events for the timer.
	 */
	private int timeSlice = 50; // update every 50 milliseconds

	/**
	 * The timer. Used to update the GUI every "timeSlice" milliseconds.
	 */
	private Timer timer = new Timer(timeSlice, (e) -> m2vAdapter.update());

	/**
	 * Single instance of the switcher strategy shared by all switcher balls.
	 */
	private SwitcherStrategy<IBallCmd> switcher = new SwitcherStrategy<IBallCmd>();

	/**
	 * A factory for a beeping error strategy that beeps the speaker every 25 updates.
	 * Either use the _errorStrategyFac variable directly if you need a factory that makes an error strategy,
	 * or call _errorStrategyFac.make() to create an instance of a beeping error strategy.
	 */
	private IUpdateStrategyFac<IBallCmd> _errorStrategyFac = new IUpdateStrategyFac.ErrorFactory<IBallCmd>();
	
	/**
	 * Object loader that loads IUpdateStrategy's.
	 */
	ObjectLoader<IUpdateStrategy<IBallCmd>> updateStrategyLoader = new ObjectLoader<IUpdateStrategy<IBallCmd>>((a) -> _errorStrategyFac.make());
	
	/**
	 * A factory for a beeping error strategy that beeps the speaker every 25 updates.
	 * Either use the _errorStrategyFac variable directly if you need a factory that makes an error strategy,
	 * or call _errorStrategyFac.make() to create an instance of a beeping error strategy.
	 */
	private IPaintStrategyFac _errorPaintStrategyFac = IPaintStrategyFac.NULL;
	
	/**
	 * Object loader that loads IPaintStrategy's.
	 */
	ObjectLoader<IPaintStrategy> paintStrategyLoader = new ObjectLoader<IPaintStrategy>((a) -> _errorPaintStrategyFac.make());

	/**
	 * The BallModel constructor. Creates a new BallModel by taking an adapter.
	 * @param m2vAdapter Constructor is supplied with an instance of the model-to-view adapter.
	 */
	public BallModel(IM2VAdapter m2vAdapter) {
		this.m2vAdapter = m2vAdapter;
	}

	/**
	 * The following method returns an instance of an IUpdateStrategy, given a fully qualified class name (package.classname) of
	 * a class that implements IUpdateStrategy.
	 * The method assumes that there is only one constructor for the supplied class that has the same *number* of
	 * input parameters as specified in the args array and that it conforms to a specific
	 * signature, i.e. specific order and types of input parameters in the args array.
	 * @param strategyName A string that is the fully qualified class name of the desired object.
	 * @return An instance of the supplied class. 
	 */
	private IUpdateStrategy<IBallCmd> loadUpdateStrategy(String strategyName) {
		return this.updateStrategyLoader.loadInstance(strategyName, new Object[] {});
	}

	/**
	 * The following method returns an instance of an IUpdateStrategy, given a fully qualified class name (package.classname) of
	 * a class that implements IUpdateStrategy.
	 * The method assumes that there is only one constructor for the supplied class that has the same *number* of
	 * input parameters as specified in the args array and that it conforms to a specific
	 * signature, i.e. specific order and types of input parameters in the args array.
	 * @param paintStrategyName A string that is the fully qualified class name of the desired object.
	 * @return An instance of the supplied class. 
	 */
	private IPaintStrategy loadPaintStrategy(String paintStrategyName) {
		return this.paintStrategyLoader.loadInstance(paintStrategyName, new Object[] {});
	}

	/**
	 * The following method creates an instance of an Ball (with random parameters), given a shortened class name (model.XXXStrategy) of a 
	 * update strategy. It then adds the ball as an observer to the dispatcher.
	 * @param updateStrategy The update strategy of the ball.
	 * @param paintStrategy An IPaintStrategy for the new ball to use.
	 */
	public void makeBall(IUpdateStrategy<IBallCmd> updateStrategy, IPaintStrategy paintStrategy) {

		// Generate random initial conditions for the ball.
		int r = Randomizer.Singleton.randomInt(15, 30);
		Point p = Randomizer.Singleton
				.randomLoc(new Dimension(m2vAdapter.getPnlWidth() - (2 * r), m2vAdapter.getPnlHeight() - (2 * r)));
		p.x += r; // move p to the right by r
		p.y += r; // move p down by r
		Point v = Randomizer.Singleton.randomVel(new Rectangle(7, 7));
		if (v.x == 0 || v.y == 0) { // Ensure the ball has a non-zero velocity.
			v.x = 1;
			v.y = 1;
		}
		Color c = Randomizer.Singleton.randomColor();
		Component pnlCenter = m2vAdapter.getComponent();

		Ball newBall = new Ball(p, r, v, c, pnlCenter, updateStrategy, paintStrategy);
		myDispatcher.addObserver(newBall); // Add the ball to the dispatcher.
	}

	/**
	 * Sets the strategy of the switcher to newStrategy.
	 * @param newStrategy The new strategy to give the switcher.
	 */
	public void switchSwitcherStrategy(IUpdateStrategy<IBallCmd> newStrategy) {
		switcher.setStrategy(newStrategy);
	}

	/**
	 * The following method removes all {@link model.Ball ABalls} from the dispatcher.
	 */
	public void clearBalls() {
		myDispatcher.removeAllObservers();
	}

	/**
	 * This is the method that is called by the view's adapter to the model, i.e. is called by IV2MUpdateAdapter.paint().
	 * The following method notifies all the balls in the dispatcher (which in turn calls the update method of each ball) and 
	 * passes each update method the correct {@link java.awt.Graphics Graphics} object to paint on.
	 * @param g The Graphics object from the view's paintComponent() call.
	 */
	public void update(Graphics g) {
		myDispatcher.updateAll((context, disp) -> {
			context.move();
			context.bounce();

			// Variant behavior:
			context.paint(g);
			context.updateState(disp);
		});
	}

	/**
	 * The following method starts the model by starting the timer. 
	 */
	public void start() {
		timer.start();
	}

	/**
	 * Returns an IUpdateStrategyFac that can instantiate the strategy specified by
	 * className. Returns a factory for a beeping error strategy if className is null. 
	 * The toString() of the returned factory is the className.
	 * 
	 * @param className Shortened name of desired strategy.
	 * @return A factory to make that strategy.
	 */
	public IUpdateStrategyFac<IBallCmd> makeUpdateStrategyFac(final String className) {
		if (null == loadUpdateStrategy(fixName("model.strategy", className, "Strategy")))
			return _errorStrategyFac;
		return new IUpdateStrategyFac<IBallCmd>() {
			/**
			 * Instantiate a strategy corresponding to the given class name.
			 * @return An IUpdateStrategy instance
			 */
			public IUpdateStrategy<IBallCmd> make() {
				return loadUpdateStrategy(fixName("model.strategy", className, "Strategy"));
			}

			/**
			 * Return the given class name string
			 */
			public String toString() {
				return className;
			}
		};
	}

	/**
	 * Returns an IPaintStrategyFac that can instantiate the strategy specified by 
	 * className. Returns a factory for a beeping error strategy if className is null. 
	 * The toString() of the returned factory is the className.
	 * 
	 * @param className Shortened name of desired strategy.
	 * @return A factory to make that strategy.
	 */
	public IPaintStrategyFac makePaintStrategyFac(final String className) {
		if (null == loadPaintStrategy(fixName("model.paint.strategy", className, "PaintStrategy"))) {
			return null; // TODO: make error paint strategy
			//			return _errorStrategyFac;
		}
		return new IPaintStrategyFac() {
			/**
			 * Instantiate a strategy corresponding to the given class name.
			 * @return An IUpdateStrategy instance
			 */
			public IPaintStrategy make() {
				return loadPaintStrategy(fixName("model.paint.strategy", className, "PaintStrategy"));
			}

			/**
			 * Return the given class name string
			 */
			public String toString() {
				return className;
			}
		};
	}

	/**
	 * Converts a shortened class name (XXX) to a fully qualified class name (folder.XXXappend).
	 * @param folder The name of the folder for fully qualified class name.
	 * @param className The abbreviated, shortened name.
	 * @param append The string to append to the end.
	 * @return The fully qualified class name.
	 */
	private String fixName(String folder, String className, String append) {
		return folder + "." + className + append;
	}

	/**
	 * Returns an IUpdateStrategyFac that can instantiate a MultiStrategy with the two
	 * strategies made by the two given IUpdateStrategyFac objects. Returns null if
	 * either supplied factory is null. The toString() of the returned factory
	 * is the toString()'s of the two given factories, concatenated with "-". 
	 * If either factory is null, then a factory for a beeping error strategy is returned.
	 * 
	 * @param stratFac1 An IUpdateStrategyFac for a strategy
	 * @param stratFac2 An IUpdateStrategyFac for a strategy
	 * @return An IUpdateStrategyFac for the composition of the two strategies
	 */
	public IUpdateStrategyFac<IBallCmd> combineStrategyFacs(final IUpdateStrategyFac<IBallCmd> stratFac1,
			final IUpdateStrategyFac<IBallCmd> stratFac2) {
		if (null == stratFac1 || null == stratFac2)
			return _errorStrategyFac;
		return new IUpdateStrategyFac<IBallCmd>() {
			/**
			 * Instantiate a new MultiStrategy with the strategies from the given strategy factories
			 * @return A MultiStrategy instance
			 */
			public IUpdateStrategy<IBallCmd> make() {
				return new MultiStrategy<IBallCmd>(stratFac1.make(), stratFac2.make());
			}

			/**
			 * Return a string that is the toString()'s of the given strategy factories concatenated with a "-"
			 */
			public String toString() {
				return stratFac1.toString() + "-" + stratFac2.toString();
			}
		};
	}

	/**
	 * @return The switcher field.
	 */
	public SwitcherStrategy<IBallCmd> getSwitcherStrategy() {
		return this.switcher;
	}
}
