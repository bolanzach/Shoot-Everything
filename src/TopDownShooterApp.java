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
public class  TopDownShooterApp
       extends AbstractMultimediaApp
       implements GunObserver, SpawnObserver, MetronomeListener
{
	
	private ActorFactory							af;
	private volatile ArrayList<AbstractGameSprite>			gameObjects, enemies, bullets;
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
	
	
	public TopDownShooterApp() {
		width = 800;
		height = 600;
		
		finder = ResourceFinder.createInstance(this);
		stage = new Stage(50);
	    stageView = stage.getView();
	    stageView.setBounds(0,0,width,height);
	    stageView.setSize(width,height);
	    factory = new ContentFactory(finder);
	    af = new ActorFactory(factory);
	    
	    gameObjects  = new ArrayList<AbstractGameSprite>();
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
        gameObjects.add(avatar);
        
        // The Gun
        gun = new GunController(finder, af);
        gun.registerGunObs(this);
        
        // Enemy Spawner
        enemySpawner = new EnemySpawner(af);
        stage.add(enemySpawner);	// Added to stage to listen to Metronome
        enemySpawner.register(this);
        
     	
		// The Blood Splats TEST
		//BloodSplatter bs = new BloodSplatter(finder, m.getTime(), 100, 100);
		//stage.add(bs);
        
        
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
											(finder, avatar.getX(), avatar.getY());
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

	@Override
	public void handleTick(int millis) {

		for (int b=0; b<bullets.size(); b++) {
			AbstractGameSprite bullet = bullets.get(b);
			if (bullet.isDestroyed()) {
				bullets.remove(bullet);
				stage.remove(bullet);
				bullet.setAvailable(true);
			}
		}
		
		for (int b=0; b<bullets.size(); b++) {
			AbstractGameSprite bullet = bullets.get(b);
			for (int e=0; e<enemies.size(); e++) {
				AbstractGameSprite enemy = enemies.get(e);
				if (bullet.intersects(enemy)) {
					System.out.println("asdf");
				}
			}
		}



	}
  
    
}
