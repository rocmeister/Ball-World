package controller;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;

import model.BallModel;
import model.IBallCmd;
import model.IM2VAdapter;
import model.IPaintStrategyFac;
import model.IUpdateStrategyFac;
import view.IV2MControlAdapter;
import view.IV2MUpdateAdapter;
import view.View;

/**
 * This class contains the main method. Its job is to instantiate the model and view and to start them.
 * @author Peter Dulworth (psd2), Sophia Jefferson (sgj1)
 */
public class Controller {

	/**
	 * The view.
	 */
	private View<IUpdateStrategyFac<IBallCmd>, IPaintStrategyFac> view;

	/**
	 * The model.
	 */
	private BallModel model;

	/**
	 * Constructor that builds the system by instantiating the model and the view.
	 */
	public Controller() {

		// instantiate model
		model = new BallModel(new IM2VAdapter() {
			@Override
			public void update() {
				view.update();
			}

			@Override
			public Integer getPnlHeight() {
				return view.getPanelHeight();
			};

			@Override
			public Integer getPnlWidth() {
				return view.getPanelWidth();
			};

			@Override
			public Component getComponent() {
				return view.getComponent();
			}
		});

		// instantiate view
		view = new View<IUpdateStrategyFac<IBallCmd>, IPaintStrategyFac>(new IV2MControlAdapter<IUpdateStrategyFac<IBallCmd>, IPaintStrategyFac>() {
			@Override
			public void clearBalls() {
				model.clearBalls();
			};

			@Override
			/**
			 * Returns an IUpdateStrategyFac that can instantiate the strategy specified
			 * by className. Returns null if className is null. The toString() of
			 * the returned factory is the className.
			 * @param classname  Shortened name of desired strategy 
			 * @return A factory to make that strategy
			 */
			public IUpdateStrategyFac<IBallCmd> addStrategy(String className) {
				return model.makeUpdateStrategyFac(className);
			}

			@Override
			/**
			 * Add a ball to the system with a strategy as given by the given IUpdateStrategyFac
			 * @param selectedItem The fully qualified name of the desired strategy.
			 */
			public void makeBall(IUpdateStrategyFac<IBallCmd> updateStratFac, IPaintStrategyFac paintStratFac) {
				if (null != updateStratFac && null != paintStratFac) {
					model.makeBall(updateStratFac.make(), paintStratFac.make()); // Here, loadBall takes a strategy object, but your method may take the strategy factory instead.
				}
			}

			@Override
			/**
			 * Returns an IUpdateStrategyFac that can instantiate a MultiStrategy with the
			 * two strategies made by the two given IUpdateStrategyFac objects. Returns
			 * null if either supplied factory is null. The toString() of the
			 * returned factory is the toString()'s of the two given factories,
			 * concatenated with "-".             * 
			 * @param selectedItem1 An IUpdateStrategyFac for a strategy
			 * @param selectedItem2 An IUpdateStrategyFac for a strategy
			 * @return An IUpdateStrategyFac for the composition of the two strategies
			 */
			public IUpdateStrategyFac<IBallCmd> combineStrategies(IUpdateStrategyFac<IBallCmd> selectedItem1, IUpdateStrategyFac<IBallCmd> selectedItem2) {
				return model.combineStrategyFacs(selectedItem1, selectedItem2);
			}

			@Override
			public void makeSwitcherBall(IPaintStrategyFac paintStratFac) {
				model.makeBall(model.getSwitcherStrategy(), paintStratFac.make()); // Here, loadBall takes a strategy object, but your method may take the strategy factory instead.
			}

			@Override
			public void switchStrategy(IUpdateStrategyFac<IBallCmd> selectedItem) {
				model.switchSwitcherStrategy(selectedItem.make());

			}

			@Override
			public IPaintStrategyFac addPaintStrategy(String className) {
				return model.makePaintStrategyFac(className);
			}

		}, new IV2MUpdateAdapter() {

			@Override
			public void paint(Graphics g) {
				model.update(g);
			}

		});
	}

	/**
	 * Start the system.	
	 */
	public void start() {
		view.start();
		model.start();
	}

	/**
	 * This is the main entry point for the system. Executing this will instantiate 
	 * the controller then start the controller.
	 * 
	 * @param args No arguments are used at this time.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				(new Controller()).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}
