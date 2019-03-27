package view;

import java.awt.Graphics;

/**
 * Adapter that the view uses to communicate to the model for repetitive update tasks such as painting.
 * @author Peter Dulworth (psd2)
 */
public interface IV2MUpdateAdapter {
	/**
	 * No-op singleton implementation of IV2MUpdateAdapter.
	 */
	public static final IV2MUpdateAdapter NULL = new IV2MUpdateAdapter() {
		public void paint(Graphics g) {
		}
	};

	/**
	 * This is the method the view will call to update its canvas based on the model.
	 * @param g The {@link java.awt.Graphics Graphics} object to paint on.
	 */
	public void paint(Graphics g);
}