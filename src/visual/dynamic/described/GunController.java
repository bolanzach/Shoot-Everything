package visual.dynamic.described;

import io.ResourceFinder;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

import auditory.sampled.BufferedSound;
import auditory.sampled.BufferedSoundFactory;
import auditory.sampled.SoundFactory;


/**
 * A class that manages all aspects of a "gun". Detects when a user presses the
 * left mouse button, notifies observers when the gun is "shot", and creates
 * "bullets" to be placed on the game stage.
 *
 * @author  Zachary Bolan, James Madison University
 * @version 1.0
 */
public class GunController
				 implements GunSubject, MouseListener
{

	private ActorFactory			af;
	private float					mouseX, mouseY;
	private List<GunObserver>		observers;
	public ResourceFinder			finder;
	SoundFactory					sf;
		

    /**
     * Explicit Value Constructor
     *
     * @param board		The Visualization object that all Bullets
     * 					are to be rendered on.
     */
    public GunController(ResourceFinder finder, ActorFactory af)
    {
    	this.finder = finder;
    	this.af = af;
    	this.observers = new ArrayList<>();
    	sf = new SoundFactory(finder);
    }

    /**
     * Creates a Bullet object
     * 
     * @param finder	The ResourceFinder to use
     * @param x			position to create bullet
     * @param y			position to create bullet
     * @return			The Bullet that was created
     */
    public AbstractGameSprite createBullet(ResourceFinder finder, 
    							double x, double y) 
    {
    	sf.playAudio("lazor.wav");
    	return af.createBullet(finder, x, y, mouseX, mouseY);
    }
    
	/**
	 * The Gun was "shot". Notify all GunObservers
	 */
	public void notifyShot() {
		for (GunObserver obj : observers) {
			obj.gunWasShot();
		}
	}

    /**
	 * The mouse has been pressed. Runs logic only if the
	 * left mouse button has been pressed (the action for
	 * shooting the gun in the game).
	 * 
	 * @param e		The mouse event
	 */
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			mouseX = e.getX();
			mouseY = e.getY();
			notifyShot();
		}
	}

	/**
	 * Add the specified GunObserver to this GunSubject
	 * 
	 * @param obj	The GunObserver to add
	 */
	public void registerGunObs(GunObserver obj) {
		if (obj != null && !(observers.contains(obj))) 
			observers.add(obj);
	}

	/**
	 * Remove the specified GunObserver from this GunSubject
	 * 
	 * @param obj	The GunObserver to be removed
	 */
	public void unregisterGunObs(GunObserver obj) {
		observers.remove(obj);
		
	}
	
	
	
    // ************* WE DO NOT USE THESE METHODS ******************
	//		  (Inherited from the MouseEvent Interface)
    // ************************************************************
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e){}

}

