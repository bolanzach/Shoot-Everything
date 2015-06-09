import io.ResourceFinder;

import java.awt.Rectangle;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.*;

import collectionframework.Quadtree;

import event.Metronome;
import event.MetronomeListener;

import visual.VisualizationView;
import visual.dynamic.described.*;
import visual.dynamic.described.BloodSplatter;
import visual.statik.sampled.*;

import app.*;


/**
 * A TopDownShooterApp that controls the most abstract logic for
 * a top down shooter game.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @author Zachary Bolan
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class  Controller
       extends AbstractMultimediaApp
       implements GunObserver, SpawnObserver, MetronomeListener
{
	
	private ActorFactory							af;
	private volatile List<AbstractGameSprite>		enemies, bullets;
	private Content									terrain;
	private EnemySpawner							enemySpawner;
	private ContentFactory							factory;
	private GunController							gun;
	private int										width, height;
	private JPanel									contentPane;
	private Metronome								m;
	private PlayerAvatar							avatar;
	private ResourceFinder							finder;
	private Stage									stage;
	private VisualizationView						stageView;
	
	
	public Controller() {
		width = 800;
		height = 600;
		
		finder = ResourceFinder.createInstance(this);
		stage = new Stage(50);
	    stageView = stage.getView();
	    stageView.setBounds(0,0,width,height);
	    stageView.setSize(width,height);
	    factory = new ContentFactory(finder);
	    af = new ActorFactory(factory);
	    
	    enemies		 = new ArrayList<AbstractGameSprite>();
	    bullets		 = new ArrayList<AbstractGameSprite>();
	    
	    
	    // Make this App "tick" to the Metronome
	    m = stage.getMetronome();
	    m.addListener(this);
	    
	}

    /**
     * The entry-point of the application
     */
    public void init()
    {
    		
        // Background
        terrain = factory.createContent("rsc/tile.png");
        stage.add(terrain);
        
        // Player Avatar
        avatar = new PlayerAvatar(factory.createContent("rsc/character.png", 4), 
        												finder, width, height);
        stage.add(avatar);
        bullets.add(avatar);
        
        // The Gun
        gun = new GunController(finder, af);
        gun.registerGunObs(this);
        
        // Enemy Spawner
        enemySpawner = new EnemySpawner(af);
        stage.add(enemySpawner);	// Added to stage to listen to Metronome
        enemySpawner.register(this);
   
        // ContentPane
        contentPane = (JPanel)rootPaneContainer.getContentPane();
        contentPane.add(stageView);
    	stage.addMouseListener(avatar);
    	stage.addMouseListener(gun);	
     	stage.start();

    }
    

	/**
	 * Notified that the Gun was shot. Add a bullet to the stage.
	 */
	public void gunWasShot() {
		if (!avatar.isDestroyed()) {
			AbstractGameSprite tempBullet = gun.createBullet
											(finder, avatar.getCenterX(), avatar.getCenterY());
			
			bullets.add(tempBullet);
			stage.add(tempBullet);
		}
		
	}

	/**
	 * Notified that it is time to spawn an enemy onto the stage.
	 */
	public void readyToSpawn() {
		if (!avatar.isDestroyed()) {
			AbstractGameSprite tempEnemy = enemySpawner.spawnEnemy
											(finder, avatar.getX(), avatar.getY());
			enemies.add(tempEnemy);
			stage.add(tempEnemy);
		}
	}

	/**
	 * Handles stage events per 'tick'. Additonally handles collision detection 
	 * between objects. Failed to implement a quadtree so this "cheat" variation
	 * gets the job done well enough for this version of the app. It is highly 
	 * recommended that future iterations of this program should include a better and
	 * more fleshed out collision detection system.
	 */
	public void handleTick(int millis) {
		
		for (int i=0; i<enemies.size(); i++) {
			AbstractGameSprite e = enemies.get(i);
			if (e.isDestroyed()) {
				stage.remove(e);
				enemies.remove(e);
				BloodSplatter bs = new BloodSplatter(finder, millis, e.getCenterX(), e.getCenterY());
				stage.add(bs);
			}
			else {
				for (int x=0; x<bullets.size(); x++) {
					AbstractGameSprite b = bullets.get(x);
					if (b.isDestroyed()) {
						stage.remove(b);
						bullets.remove(b);
					}
					else {
						if (e.intersects(b)) {
							b.doCollisions();
							e.doCollisions();
						}
					}
				}
			}
		}

	}
  
    
}
