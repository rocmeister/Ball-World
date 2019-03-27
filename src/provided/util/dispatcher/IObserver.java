package provided.util.dispatcher;

/**
 * Top-level abstraction of the Observer in the Observer-Observable Design Pattern.
 * An IObserver receives TMsg-type messages from its associated, matched Observable, 
 * an IDispatcher&lt;TMsg&gt;. 
 * @author swong
 *
 * @param <TMsg>  The type of message this observer is capable of receiving and processing.
 */
public interface IObserver<TMsg> {

	/**
	 * The update method called by the observer's dispatcher (observable)
	 * @param disp  The dispatcher that called this method
	 * @param msg The message being distributed to all the observers.
	 */
	public void update(IDispatcher<TMsg> disp, TMsg msg);

}
