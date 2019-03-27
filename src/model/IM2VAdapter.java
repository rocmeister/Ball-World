package model;

import java.awt.Component;

/**
 * This interface defines behavior the model expects from the view.
 * 
 * @author Peter Dulworth (psd2)
 * @author Andrew Hadad (aah6)
 */
public interface IM2VAdapter {
	/**
	 * No-op singleton implementation of IM2VAdapter.
	 */
	public static final IM2VAdapter NULL_OBJECT = new IM2VAdapter() {
		public void update() {
		};

		public Integer getPnlHeight() {
			return null;
		};

		public Integer getPnlWidth() {
			return null;
		};

		public Component getComponent() {
			return null;
		};
	};

	/**
	 * This is the method the model will call when it wants to update the view.
	 */
	public void update();

	/** 
	 * This is the method the model will call to get the height of the view.
	 * @return The height of the view.
	 */
	public Integer getPnlHeight();

	/**
	 * This is the method the model will call to get the width of the view.
	 * @return The width of the view.
	 */
	public Integer getPnlWidth();

	/**
	 * This is the method the model will call to get the main component of the view.
	 * @return The main component of the view.
	 */
	public Component getComponent();
}
