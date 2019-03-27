package model;

import java.awt.Graphics;

/**
 * An interface that defines a factory that instantiates a specific IPaintStrategy.
 * @author Peter Dulworth (psd2)
 */
public interface IPaintStrategyFac {

	/**
	 * Instantiate the specific IPaintStrategy for which this factory is defined.
	 * @return An IPaintStrategy instance.
	 */
	public IPaintStrategy make();
	
	/**
     * A factory for null paint strategy objects.
     * Usage: instantiate this factory class and then call its make() method to create the strategy object.
     */
	public static final IPaintStrategyFac NULL = new IPaintStrategyFac() {

		@Override
		public IPaintStrategy make() {
			return new IPaintStrategy() {
				@Override
				public void init(Ball host) {
					// No-op.
				}

				@Override
				public void paint(Graphics g, Ball host) {
					// No-op.
				}
			};
		}
		
		/**
		 * Return the given class name string
		 */
		public String toString() {
			return "NULL PAINT FAC";
		}
	};
	
	/**
     * A factory for error paint strategy objects.
     * Usage: instantiate this factory class and then call its make() method to create the strategy object.
     */
	public static final IPaintStrategyFac ERROR = new IPaintStrategyFac() {

		@Override
		public IPaintStrategy make() {
			return new IPaintStrategy() {
				@Override
				public void init(Ball host) {
					// No-op.
				}

				@Override
				public void paint(Graphics g, Ball host) {
					// No-op.
				}
			};
		}
		
		/**
		 * Return the given class name string
		 */
		public String toString() {
			return "ERROR PAINT FAC";
		}
	};
}
