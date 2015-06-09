package collectionframework.pool;

/**
 * The requirements for a pool.
 * 
 * This pool is not a "full implementation" of a "true" object pool.
 * Instead, this pool provides the basic underlying mechanics and 
 * functions of a pool. It concept, this pool holds a collection
 * of "expensive-to-create" objects of some type. These stored objects
 * can then be referenced by other classes as to prevent the creation
 * of many objects.
 * 
 * It is significant to note that this pool can naturally store all
 * instances of an object, denoted here by 'T'. This means that any 
 * objects that are instances of T may also be stored and used by this
 * pool. In some scenarios it is desired to return a specific instance
 * or "type" of T and such logic should be handled within the validate(T)
 * method.
 * 
 * This is by no means the most elegant or best implementation of a
 * pool. In fact, my research has revealed that many uses of a pool
 * in modern builds of Java is uneccesary. However, constructing this
 * pattern was a learning experience and provides further functionality
 * when creating objects. 
 * 
 * This class may be extended and modified to further functionality.
 * 
 * @author Zachary Bolan
 * @version 12/11/13
 * @see http://java.dzone.com/articles/generic-and-
 * concurrent-object
 *
 * @param <T>
 */
public interface Pool<T> {
	
	/**
	 * Returns an instance of Object T from the pool. If an object of type T is
	 * found in the pool it is returned. Otherwise, a new object T will be 
	 * created and returned.
	 * 
	 * @param  T	The object of Type T that is to be taken from the pool
	 * @return T	The object returned from the pool
	 */
	T checkout();
	
	/**
	 * Checks every object within a locked pool and validates to see if the objects
	 * should be added back into the pool, thus allowing them to be "borrowed"
	 * for reuse by other objects in the future.
	 */
	void checkin();
	
}