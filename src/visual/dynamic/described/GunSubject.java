package visual.dynamic.described;

public interface GunSubject
{
	public void registerGunObs(GunObserver obj);
	public void unregisterGunObs(GunObserver obj);
	
	// Notify observers that the gun was shot
	public void notifyShot();
}
