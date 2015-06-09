package visual.dynamic.described;

import io.ResourceFinder;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import auditory.sampled.SoundFactory;
import visual.statik.TransformableContent;

/**
 * An abstract rule-based sprite that is the superclass of many 
 * character sprites within the game. Specializes the RuleBasedSprite to
 * add further funtionality and more relevance to the Top Down Shooter
 *
 * @author  Prof. David Bernstein, James Madison University
 * @author Zachary Bolan
 * 
 * @version 11/13/13
 *
 */
public abstract class AbstractGameSprite extends RuleBasedSprite
{
	boolean								isDestroyed, isAvailable;
	double								x, y, dX, dY;
	private ResourceFinder				finder;
    protected LinkedList<Sprite>        antagonists; 
    protected SoundFactory				sf;
    protected TransformableContent      content;
    int									speed, lifespan;
    final int							TIME_TO_DIE = 1000;


    /**
     * Explicit Value Constructor. Must include a relevant ResourceFinder
     * in order to play audio controlled by this AbstractGameSprite (such as
     * death or birth sound effects).
     *
     * @param content   The static visual content
     */
    public AbstractGameSprite(TransformableContent content, ResourceFinder finder)
    {
       super(content);
       this.finder = finder;
       antagonists = new LinkedList<Sprite>();       
       this.content = content;
       setVisible(true);
	   isAvailable = false;
	   lifespan = 0;
	   sf = new SoundFactory(finder);
    }

    /**
     * Add a "antagonist" Sprite to this Sprite
     *
     * @param antagonist   The "antagonist" Sprite to add
     */
    public void addAntagonist(Sprite antagonist)
    {
       antagonists.add(antagonist);       
    }
    
    /**
     * Handle a collision between this object and another object. Future iterations
     * of this method should override intersects() to provide more functionality.
     */
    public abstract void doCollisions();
    
    /**
     * Returns a LinkedList containing all the antagonists that
     * this AbstractGameSprite has.
     * 
     * @return		The LinkedList
     */
    public LinkedList<Sprite> getAntagonisits() {
    	LinkedList<Sprite>				retList;
    	retList = antagonists;
    	
    	return retList;
    }
    
    /**
     * Returns the approximate center X location of this AbstractGameSprite
     * in Double precision. 
     */
    public double getCenterX() {
    	return getBounds2D().getCenterX();
    }
    
    /**
     * Returns the approximate center Y location of this AbstractGameSprite
     * in Double precision.
     */
    public double getCenterY() {
    	return getBounds2D().getCenterY();
    }
    
    /**
     * Get the visual content associated with this Sprite
     * (required by Sprite)
     */
    public TransformableContent getContent()
    {
       return content;
    }
    
    /** 
     * Get the x location of this sprite
     */
    public double getX() {
    	return this.x;
    }
    
    /**
     * Get the y location of this sprite
     */
    public double getY() {
    	return this.y;
    }
    
    /** 
     * Get the x destination location
     */
    public double getDX() {
    	return this.dX;
    }
    
    /**
     * Get the y destinaiton location
     */
    public double getDY() {
    	return this.dY;
    }
    
    
    /**
     * Handle a tick event (required by MetronomeListener)
     *
     * @param time  The current time (in milliseconds)
     */
    public abstract void handleTick(int time);
    
    /**
     * Setup this AbstractGameSprite. Acts as a constructor once this object
     * has already been created.
     * 
     * @param startX			Initial location
     * @param startY			Initial location
     * @param dX				The destination point
     * @param dY				The destination point
     */
    abstract void init(double startX, double startY, double dX, double dY);
    
    
    public boolean intersects(AbstractGameSprite s) {

    	boolean          retval;
        double           maxx, maxy, minx, miny;
        double           maxxO, maxyO, minxO, minyO;
        Rectangle2D      r;

        retval = true;

        r = getBounds2D(true);
        minx = r.getX();
        miny = r.getY();
        
        minx += 0.3 * (minx - r.getCenterX());
        miny += 0.3 * (miny - r.getCenterY());
        maxx = minx + r.getWidth() - 15;
        maxy = miny + r.getHeight() - 15;

        r = s.getBounds2D(true);
        minxO = r.getX();
        minyO = r.getY();
        
        // Added logic for TopDownShooter
        minxO += 0.3 * (minxO - r.getCenterX());
        minyO += 0.3 * (minyO - r.getCenterY());
        maxxO = minxO + r.getWidth() - 15;
        maxyO = minyO + r.getHeight() - 15;
        if (s.equals(this)) return false;

        if ( (maxx < minxO) || (minx > maxxO) ||
             (maxy < minyO) || (miny > maxyO) ) retval = false;
        
        return retval;
    	
    }
    
    
    /**
     * Returns whether or not this Bullet should be removed from 
     * the game
     * 
     * @return isDestoryed	True if this object needs to be removed,
     * 						false otherwise
     */
    public boolean isDestroyed() {
    	return isDestroyed;
    }
    
    
    /**
     * Is this AbstractGameSprite ready? (ie Is this object completely removed
     * from the game such that no other game objects reference it?)
     * 
     */
    public boolean isAvailable() {
    	return isAvailable;
    }
    
    public void reset() {
    	removeAllAntagonists();
    	isAvailable = true;
    }
    
    
    /**
     * Sets the X and Y destination locations
     * @param x		location
     * @param y		location
     */
    public void setDestinationLocation(float x, float y) {
    	dX = x;
    	dY = y;
    }
    
    public synchronized void setAvailable(boolean b) {
    	isAvailable = b;
    }


    /**
     * Remove a "antagonist" Sprite from the collection of
     * "antagonists" this Sprite is concerned with
     *
     * @param antagonist   The "antagonist" Sprite to remove
     */
    public void removeAntagonist(Sprite antagonist)
    {
       antagonists.remove(antagonist);       
    }
    
    /**
     * Remove all antagonists from this AbstractGameSprite
     */
    public void removeAllAntagonists() {
    	antagonists.removeAll(antagonists);
    }
    
    /**
     * Set the speed of this AbstractGameSprite
     * @param speed		The desired speed
     */
    public void setSpeed(int speed) {
    	this.speed = speed;
    }
    
}
