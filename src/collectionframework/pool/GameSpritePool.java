package collectionframework.pool;

import io.ResourceFinder;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import visual.dynamic.described.AbstractGameSprite;
import visual.dynamic.described.GameSpriteFactory;

/**
 * An abstract implementation of a pool.
 * NOTE: All children of this class must implement:
 * 			create()
 * 			validate(T)
 * 
 * @author Zachary Bolan
 * @version 11/12/13
 *
 * @param <T>
 */
public class GameSpritePool<T extends AbstractGameSprite> {

	private List<AbstractGameSprite> 			thePool, lockedPool;
	private GameSpriteFactory<T>				factory;
	
	
	public GameSpritePool(GameSpriteFactory<T> factory) {
		thePool    = new CopyOnWriteArrayList<AbstractGameSprite>();
		lockedPool = new CopyOnWriteArrayList<AbstractGameSprite>();
		this.factory = factory;
	}
	
	public void checkin() {
		Iterator<AbstractGameSprite> itr;
		itr = lockedPool.iterator();
		while (itr.hasNext()) {
			AbstractGameSprite elem = itr.next();
			if (validate(elem)) {
				if (!thePool.contains(elem)) thePool.add(elem);
				lockedPool.remove(elem);
			}
		}
	}
	
	/**
	 * Borrow an AbstractGameSprite from a pool of available objects. 
	 * Once it finds an available sprite it removes the object from the pool
	 * and sends it to the locked pool. If no object is available to be borrowed 
	 * then it creates and returns a new AbstractGameSprite.
	 * 
	 * @return	The object to be borrowed from the pool.
	 */
	public AbstractGameSprite checkout() {
		checkin();
		Iterator<AbstractGameSprite> itr;
		itr = thePool.iterator();
		while (itr.hasNext()) 
		{
			AbstractGameSprite elem = itr.next();
			if  (validate(elem) && !lockedPool.contains(elem)) {
				lockedPool.add(elem);
				return elem;
			}
		}
		
		// No valid object found - create a new AbstractGameSprite
		AbstractGameSprite temp = create();
		lockedPool.add(temp);
		return temp;
	}
	
	/**
	 * Creates a new instance object of an AbstractGameSprite
	 * 
	 * @return The new AbstrctGameSprite
	 */
	public AbstractGameSprite create() {
		return factory.createSprite();
	}
	
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
	public boolean validate(AbstractGameSprite ags) {
		return ags.isAvailable();
	}


}
