package collectionframework.pool;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An abstract implementation of a pool.
 * NOTE: All children of this class must implement:
 * 			create()
 * 			isCorrectObject(T)
 * 			validate(T)
 * 
 * @author Zachary Bolan
 * @version 11/12/13
 *
 * @param <T>
 */
public abstract class AbstractPool<T> implements Pool<T> {

	private List<T> 			thePool, lockedPool;
	
	public AbstractPool() {
		thePool    = new CopyOnWriteArrayList<T>();
		lockedPool = new CopyOnWriteArrayList<T>();
	}
	
	public void checkin() {
		Iterator<T> itr;
		itr = lockedPool.iterator();
		while (itr.hasNext()) {
			T elem = itr.next();
			if (validate(elem)) {
				if (!thePool.contains(elem)) thePool.add(elem);
				lockedPool.remove(elem);
			}
		}
	}
	
	public T checkout() {
		checkin();
		for (T a: thePool)System.out.println(a);
		Iterator<T> itr;
		itr = thePool.iterator();
		while (itr.hasNext()) 
		{
			T elem = itr.next();
			if  (validate(elem) && !lockedPool.contains(elem)) {
				lockedPool.add(elem);
				return elem;
			}
		}
		
		// No valid object found
		T temp = create();
		lockedPool.add(temp);
		return temp;

		
	}
	
	/**
	 * Creates a new instance object of type T. This object can later be borrowed
	 * from the pool if it is not in use.
	 * 
	 * @return The object of type T that was created
	 */
	public abstract T create();
	
	/**
	 * Validates to see if this object can be checked-out by the pool. The 
	 * basis on which an object of type T is valid is object dependant and 
	 * thus the implementation is left for the subclasses. It is essential
	 * to ensure that this method is implemented correctly as a "false
	 * validation" can potentially interfere with the pool itself,
	 * inhibiting its ability to correctly return objects.
	 * 
	 * @param obj	The object to validate
	 * @return		True if the object is valid (can move into or out of
	 * 				the pool) or returns false otherwise.
	 */
	public abstract boolean validate(T t);


}
