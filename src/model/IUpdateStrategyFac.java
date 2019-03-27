package model;

import provided.util.dispatcher.IDispatcher;

/**
 * An interface that defines a factory that instantiates 
 * a specific IUpdateStrategy.
 * @param <TMsg> The type of message that the supplied IDispatcher can send.
 */
public interface IUpdateStrategyFac<TMsg> {
	
	/**
	 * Instantiate the specific IUpdateStrategy for which this factory is defined.
	 * @return An IUpdateStrategy instance.
	 */
	public IUpdateStrategy<TMsg> make();
	
	/**
	 * A factory for a beeping error strategy that beeps the speaker every 25 updates.
	 * Either use the _errorStrategyFac variable directly if you need a factory that makes an error strategy,
	 * or call _errorStrategyFac.make() to create an instance of a beeping error strategy.
	 * @param <TMsg> The type of message that the supplied IDispatcher can send. 
	 */
	public static final class ErrorFactory<TMsg> implements IUpdateStrategyFac<TMsg> {

		/**
		 * Returns a no-op null strategy
		 */
		@Override
		public IUpdateStrategy<TMsg> make() {
			System.out.println("MAKING ERROR STRATEGY");
			return new IUpdateStrategy<TMsg>() {

				private int count = 0; // update counter

				@Override
				/**
				 * Beep the speaker every 25 updates
				 */
				public void updateState(Ball context, IDispatcher<TMsg> disp) {
					if (25 < count++) {
						java.awt.Toolkit.getDefaultToolkit().beep();
						count = 0;
					}
				}

				@Override
				public void init(Ball host) {}
				
			};
		}
		
		/**
		 * Return the given class name string
		 */
		public String toString() {
			return "INVALID!!!";
		}
	}
	
	/**
     * A factory for a typed null strategy object.<br>
     * Usage: instantiate this factory class using the desired TDispMsg type and then call its make() method
     * to create the correctly typed null strategy object.
	 * @param <TMsg> The type of message that the supplied IDispatcher can send.
     */
	public static final class NullFactory<TMsg> implements IUpdateStrategyFac<TMsg> {

		/**
		 * Returns a no-op null strategy
		 */
		@Override
		public IUpdateStrategy<TMsg> make() {
			return new IUpdateStrategy<TMsg>() {

				@Override
				/**
				 * No-op
				 * @param context Ignored
				 */
				public void init(Ball context) {}

				@Override
				/**
				 * No-op
				 * @param context Ignored
				 * @param dispatcher Ignored
				 */
				public void updateState(Ball context, IDispatcher<TMsg> dispatcher) {}		
			};
		}
	}


}
