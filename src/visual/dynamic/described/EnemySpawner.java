package visual.dynamic.described;

import io.ResourceFinder;

import java.awt.Graphics;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.*;


/**
 * A class that manages all aspects of an "Enemy Spawner". Listens to ticks
 * from the Metronome to determine when to create enemy characters and send them
 * to the Stage within the App. 
 * 
 * An Enemy Spawner is-a Sprite because it is in essance an abstract implementation
 * of an actor on a stage (it is just not a visible sprite);
 *
 * @author  Zachary Bolan, James Madison University
 * @version 1.0
 */
public class EnemySpawner
				 implements Spawner, Sprite
{

	private ActorFactory			af;
	private List<SpawnObserver>		observers;
	public ResourceFinder			finder;
	private int						spawnTimer, spawnInterval;
	CharacterTypes 					enemyType;	
	
		
    /**
     * Constructs a new EnemySpawner
     */
    public EnemySpawner(ActorFactory af)
    {
    	this.af = af;
    	this.observers = new ArrayList<>();
    	spawnTimer = 0;
    	spawnInterval = 40;
    }

    /**
     * Creates an enemy object
     * 
     * @param finder	The ResourceFinder to use
     * @param dx		position of the player's character
     * @param dy		position of the player's character
     * @return			The enemy that was created
     */
    public AbstractGameSprite spawnEnemy(ResourceFinder finder, double dx, double dy) {

    	return af.spawnEnemy(finder, enemyType, dx, dy);
    }
    
	/**
	 * Notify all this Spawner's SpawnObservers that it is time
	 * to spawn an enemy into the game.
	 */
	public void notifyToSpawn() {
		for (SpawnObserver obj : observers) {
			obj.readyToSpawn();
		}
		
	}
	

	/**
	 * Add the specified SpawnObserver to the collection of observers
	 * 
	 * @param obj	The SpawnObserver to be added
	 */
	public void register(SpawnObserver obj) {
		if (obj != null && !(observers.contains(obj))) 
			observers.add(obj);
	}

	/**
	 * Remove the scified SpawnObserver from this Spawner
	 * 
	 * @param obj	The SpawnObserver to remove
	 */
	public void unregister(SpawnObserver obj) {
		observers.remove(obj);
		
	}

	/**
	 * Handle a Metronome tick and check to see if it is time to spawn an enemy
	 */
	public void handleTick(int millis) {
		int choice;
		Random rando;
		
		rando = new Random();
		choice = rando.nextInt(6 - 1 + 1);
		spawnTimer += 1;
		if (spawnTimer == spawnInterval) {
			if (choice == 0) enemyType = CharacterTypes.BERNSTEIN;
			else enemyType = CharacterTypes.ZOMBIE;
			spawnTimer = 0;
			if (spawnInterval >= 12) spawnInterval -= 1;
			notifyToSpawn();
		}
		
	}

	// ****************************************************************
	// These Methods are Arbitrary as the EnemySpawner is Never Visible
	// ****************************************************************
	
	@Override
	public void setLocation(double x, double y) {		
	}

	@Override
	public void setRotation(double angle, double x, double y) {		
	}

	@Override
	public void setScale(double xScale, double yScale) {		
	}

	@Override
	public Rectangle2D getBounds2D(boolean ofTransformed) {
		return null;
	}

	@Override
	public void render(Graphics g) {		
	}

}

