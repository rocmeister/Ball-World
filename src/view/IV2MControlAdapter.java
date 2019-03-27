package view;

/**
 * Adapter that the view uses to communicate to the model for non-repetitive control tasks such as manipulating strategies.
 * @param <TDropListItem> The type of the items put on the update strategy drop lists.
 * @param <TPaintDropListItem> The type of the items put on the paint strategy drop lists.
 * @author Peter Dulworth (psd2)
 */
public interface IV2MControlAdapter<TDropListItem, TPaintDropListItem> {
	/**
	 * Take the given short strategy name and return a corresponding something to put onto both drop lists.
	 * @param className The shortened class name of the desired strategy.
	 * @return Something to put onto both the drop lists.
	 */
	public TDropListItem addStrategy(String className);

	/**
	 * Converts a paint strategy's class name in the form of a string, into an object that can be placed onto the paint strategy drop list.
	 * @param className The text from the user's input (text field).
	 * @return An object to put onto the paint strategy drop list.
	 */
	public TPaintDropListItem addPaintStrategy(String className);

	/**
	 * Make a ball with the selected short update and paint strategy names.
	 * @param tDropListItem  A shorten class name for the desired strategy.
	 * @param tPaintDropListItem A shorten class name for the desired paint strategy.
	 */
	public void makeBall(TDropListItem tDropListItem, TPaintDropListItem tPaintDropListItem);

	/**
	 * Return a new object to put on both lists, given two items from the lists.
	 * @param selectedItem1  An object from one drop list
	 * @param selectedItem2 An object from the other drop list
	 * @return An object to put back on both lists.
	 */
	public TDropListItem combineStrategies(TDropListItem selectedItem1, TDropListItem selectedItem2);

	/**
	 * Make a switcher ball.
	 * @param selectedItem The selected paint strategy.
	 */
	public void makeSwitcherBall(TPaintDropListItem selectedItem);

	/**
	 * Switch the strategy of the switcher balls to selectedItem.
	 * @param selectedItem An object from the top drop list to use as the new switcher decoree.
	 */
	public void switchStrategy(TDropListItem selectedItem);

	/**
	 * This is the method the view will call to remove all the balls from the model.
	 */
	public void clearBalls();
}
