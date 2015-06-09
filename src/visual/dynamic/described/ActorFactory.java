package visual.dynamic.described;

import java.util.Random;

import io.ResourceFinder;
import visual.statik.sampled.ContentFactory;
import collectionframework.pool.AbstractPool;
import collectionframework.pool.GameSpritePool;

/**
 * A factory for creating AbstractGameSprites. Extends {@link}AbstractPool,
 * allowing this factory to dynamically "borrow" objects from a pool
 * rather than have to create each individual AbstractGameSprite every
 * time one is needed.
 * 
 * @author Zachary Bolan
 * @version 11/12/13
 *
 */
public class ActorFactory {

	// Class variables that need to be updated
	private ContentFactory						factory;
	private CharacterTypes						type;
	protected double							x, y, dX, dY, mouseX, mouseY;
	private ResourceFinder						finder;
	
	private GameSpritePool<BlueBullet>			blueBulletPool;
	private GameSpritePool<Zombie>				zombiePool;
	private GameSpritePool<BossBernstein>		bernPool;	

	
	
	public ActorFactory(ContentFactory factory) {
		this.factory = factory;
		blueBulletPool 	= new GameSpritePool<BlueBullet>(new BlueBulletFactory(factory.createContent
														("rsc/blueBeam.png", 4), finder));
		zombiePool 		= new GameSpritePool<Zombie>(new ZombieFactory(factory.createContent
														("rsc/zombie.png", 4), finder));
		bernPool 		= new GameSpritePool<BossBernstein>(new BernsteinFactory(factory.createContent
														("rsc/bernstein.png", 4), finder));
	}
	
	/**
	 * Provides an instance of a Bullet object.
	 * NOTE: This method is synchronized because it is using class specific variables.
	 * 
	 * @param finder	The ResourceFinder to use
	 * @param x			Location to render
	 * @param y			Location to render
	 * @param mouseX	Destination location
	 * @param mouseY	Destination location
	 * @return			The Bullet (AbstractGameSprite) that was created/borrowed
	 */
	public synchronized AbstractGameSprite createBullet(ResourceFinder finder, 
														double x, double y,
														double mouseX, double mouseY) 
	{

		this.finder = finder;
		this.x = x;
		this.y = y;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
		type = CharacterTypes.BLUE_BULLET;
		//AbstractGameSprite ags  = blueBulletPool.checkout();
		//ags.init(x, y, mouseX, mouseY);
		//return ags;
		return new BlueBullet(factory.createContent
				("rsc/blueBeam.png", 4), finder, x,y,mouseX,mouseY);
		
	}

	/**
	 * Provides an instance of an Enemy object.
	 * NOTE: This method is synchronized because it is using class specific variables.
	 * @param finder		The ResourceFinder to use
	 * @param x				Render location x
	 * @param y				Render location y
	 * @param enemyType		enum EnemyTypes containing the enemy "type"
	 * @return				The Enemy (AbstractGameSprite) that was created/borrowed
	 */
	public synchronized AbstractGameSprite spawnEnemy(ResourceFinder finder, 
													CharacterTypes type, double dx, double dy) 
	{
		int choice;
		Random rando;
		
		rando = new Random();
		this.finder = finder;
		this.x = 0;
		this.y = 0;
		this.dX = dx;
		this.dY = dy;
		
		// Randomize Spawn Points
		choice = rando.nextInt(4 - 1 + 1);
		if (choice == 0) {
			this.x = 400;
			this.y = -50;
		}
		else if (choice == 1) {
			this.x = 850;
			this.y = 325;
		}
		else if (choice == 2) {
			this.x = 400;
			this.y = 700;
		}
		else if (choice == 3) {
			this.x = -50;
			this.y = 325;
		}

		this.type = type;
		AbstractGameSprite ags;

		//if (type.equals(CharacterTypes.ZOMBIE)) ags = zombiePool.checkout();
		//else ags = bernPool.checkout();
		// init() gets called twice on newly created objects but this has no adverse effects
		//ags.init(x, y, dX, dY);
		//return ags;
		if (type.equals(CharacterTypes.ZOMBIE)) return new Zombie(factory.createContent
											("rsc/zombie.png", 4), finder, x,y,dX,dY);
		else return new BossBernstein(factory.createContent
											("rsc/bernstein.png", 4), finder, x,y,dX,dY);
	}
	
	
	
}
