package provided.util.dispatcher.impl;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import provided.util.dispatcher.IDispatcher;
import provided.util.dispatcher.IObserver;

/**
 * Mid-level abstraction of a dispatcher that establishes the ability to hold and manage
 * observers in a thread-safe manner.
 * @author swong
 *
 * @param <TMsg>  The type of messages that will be sent out to the observers.
 */
public abstract class ADispatcher<TMsg> implements IDispatcher<TMsg> {

	/**
	 * Comparator used to order the IObservers in the ConcurrentSkipListSet.   This is needed
	 * because IObservers are not naturally Comparable.   Returns zero if the two IObservables are 
	 * equal, otherwise returns -1 if the first object's hashcode is less than or equal to the second object's
	 * hashcode or +1 if the first object's hashcode is greater than the second object's hashcode. 
	 */
	private Comparator<IObserver<TMsg>> comparator = new Comparator<IObserver<TMsg>>() {
		@Override
		public int compare(IObserver<TMsg> o1, IObserver<TMsg> o2) {
			if (o1.equals(o2)) {
				return 0;
			} else {
				if (o1.hashCode() <= o2.hashCode()) {
					return -1;
				} else {
					return +1;
				}
			}
		}

	};
	
	/**
	 * The internal data storage of observers.  Needs to be thread-safe.  
	 * For systems that have few mutations of the set, a CopyOnWriteArraySet 
	 * could be used for better read performance and smaller data size.
	 */
	private ConcurrentSkipListSet<IObserver<TMsg>> observers = new ConcurrentSkipListSet<IObserver<TMsg>>(comparator);
	//	private CopyOnWriteArraySet<IObserver<TMsg>> observers = new CopyOnWriteArraySet<IObserver<TMsg>>();

	/**
	 * Protected method to allow implementing subclasses access to the set of observers.
	 * The actual set of observers is returned and is thus mutable.
	 * @return The set of observers currently in use.  This is NOT a copy.
	 */
	protected Set<IObserver<TMsg>> getObserverSet() {
		return observers;
	}

	@Override
	public boolean addObserver(IObserver<TMsg> obs) {
		return observers.add(obs);
	}

	@Override
	public IObserver<TMsg> removeObserver(IObserver<TMsg> obs) {
		IObserver<TMsg> foundObs = null; // in case obs is not in the collection.
		// Note that equality does not guarantee that two objects are identically the same entity, 
		// so must retrieve the actual entity in the collection.
		if (observers.contains(obs)) { // make sure it is in the collection 
			foundObs = observers.floor(obs); // returns the desired obs only because we already know that it is in the collection.
			observers.remove(obs); // remove it from the collection
		}
		return foundObs;

	}

	@Override
	public Set<IObserver<TMsg>> getAllObservers() {
		return observers.clone();
		//		return new CopyOnWriteArraySet<IObserver<TMsg>>(observers);
	}

	@Override
	public Set<IObserver<TMsg>> removeAllObservers() {
		Set<IObserver<TMsg>> original_set = this.getAllObservers();
		observers.clear();
		return original_set;
	}

}
