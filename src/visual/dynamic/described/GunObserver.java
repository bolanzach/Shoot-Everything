package visual.dynamic.described;

/**
 * Observers of a Gun object. Provides gunWasShot() method
 * that is invoked when a gun is "shot".
 * 
 * @author Zachary Bolan, James Madison University
 *
 */
public interface GunObserver {
	/**
	 * It is observed that the gun was shot.
	 */
	public void gunWasShot();
}
