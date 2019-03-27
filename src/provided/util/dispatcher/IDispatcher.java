package provided.util.dispatcher;

import java.util.Set;

/**
 * Top-level abstraction of a Dispatcher which is the Observable in the 
 * Observer-Observable Design Pattern without the requirement to set the state 
 * before sending a message to the observers.
 * 
 * A Dispatcher sends IMsg-type messages to all its observers which therefore must 
 * be capable of receiving and processing those messages, i.e. are IObserver&lt;TMSg&gt;.
 *
 * To maximally decouple an application from the randomizer's implementation any variable representing a randomizer
 * should be typed to this interface, not to any concrete implementation.  For example:
 * IDispatcher&lt;Graphics&gt; myDispatcher = new SequentialDispatcher&lt;Graphics&gt;() 
 * 
 * @author swong
 *
 * @param <TMsg>   The type of message to send to all the observers.
 */
public interface IDispatcher<TMsg> {

	/**
	 * Add an observer to the dispatcher.   If the observer is already in the dispatcher, 
	 * as determined by the comparison (equals()) process, the dispatcher is NOT mutated and false is returned. 
	 * @param obs  The IObserver to add
	 * @return true if the given observer was not already in the dispatcher, false otherwise.
	 * @throws ClassCastException If the observer cannot be properly compared against the existing observers
	 * @throws NullPointerException If the supplied value is null
	 */
	public boolean addObserver(IObserver<TMsg> obs);

	/**
	 * Remove an observer from the dispatcher.   The dispatcher does not make any 
	 * assumptions that the observer being removed is identically the same object as 
	 * that it was requested to remove via the input parameter.  The returned object 
	 * is the object that was internally held by the dispatcher.
	 * @param obs  The IObserver to add
	 * @return The observer that was removed or null if it was not found.
	 */
	public IObserver<TMsg> removeObserver(IObserver<TMsg> obs);

	/**
	 * Get a COPY of the set of all the observers currently in the dispatcher.   This is a 
	 * shallow copy, so the observers themselves are not copied.
	 * @return A set of IObservers
	 */
	public Set<IObserver<TMsg>> getAllObservers();

	/**
	 * Removes all the observers currently in the dispatcher
	 * @return A COPY of the set of IObservers in the dispatcher before they were all removed.
	 */
	public Set<IObserver<TMsg>> removeAllObservers();

	/**
	 * Send the given message to all the observers in the dispatcher 
	 * @param msg   The IMsg to send to all the observers
	 */
	public void updateAll(TMsg msg);
}
