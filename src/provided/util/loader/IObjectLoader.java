package provided.util.loader;

/**
 * Abstraction of the dynamic class loading process for a particular type of 
 * entity (class or interface).  
 * 
 * To maximally decouple the application from the implementation of the object loader, 
 * the object loader object should be typed to to this interface, e.g.
 * IObjectLoader&lt;IMyDesiredType&gt; objLoader = new SomeConcreteObjectLoaderImplementation&lt;IMyDesiredType&gt;().   
 * 
 * @author swong
 *
 * @param <ReturnT>  The type of objects being instantiated.
 */
public interface IObjectLoader<ReturnT> {

	/**
	 * Uses dynamic class loading to load and instantiate a subclass or implementation of ReturnT given 
	 * a fully-qualified class name and an array (vararg) of input parameters.
	 * Note that primitive types get auto-boxed into regular classes, e.g. int becomes Integer.   
	 * Concrete implementations of this interface may have the ability to instantiate well-defined 
	 * return instances in the event that an error occurs during the loading process.
	 * The returned object is instantiated using the constructor of the class corresponding to the given classname 
	 * whose signature matches the number, order and types of the given constructor parameters.
	 * 
	 * @param className The fully-qualified name of the desired class which must be assignable to the ReturnT type.
	 * @param args a varargs of input parameter values for the constructor of className
	 * @return An instance of the desired class, as ReturnT 
	 * @throws IllegalArgumentException If no matching constructor can be found.
	 */
	ReturnT loadInstance(String className, Object... args);

}