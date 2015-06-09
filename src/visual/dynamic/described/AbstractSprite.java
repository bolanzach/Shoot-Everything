package visual.dynamic.described;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

import visual.statik.TransformableContent;

/**
 * A Sprite is an "actor" on a Stage.
 *
 * In essence, a Sprite decorates a TransformableContent object,
 * providing it with additional capabilities.
 *
 * Note: We don't immediately delegate since the content and the
 * location/rotation/scale may change at any time and in any order.
 * Instead, we set the location/rotation/scale before rendering.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public abstract class      AbstractSprite 
                implements Sprite
{
    protected boolean     rotationPoint, visible;
    protected double      angle, rotationX, rotationY;
    protected double      scaleX, scaleY, x, y;

    /**
     * Default Constructor
     */
    public AbstractSprite()
    {       
       super();       

       x      = 0.0;
       y      = 0.0;
       angle  = 0.0;
       scaleX = 1.0;       
       scaleY = 1.0;
       
       rotationPoint = false;       
       rotationX     = 0.0;
       rotationY     = 0.0;       
    }

    /**
     * Returns a high precision bounding box of the Content
     * either before or after it is transformed
     *
     * @param  ofTransformed  true to get the bounds of the transformed content
     * @return                The bounding box
     */
    public Rectangle2D getBounds2D(boolean ofTransformed)
    {
       return getContent().getBounds2D(ofTransformed);
       
    }
    
    /**
     * Returns a high precision bounding box of the 
     * transformed Content
     *
     * @return   The bounding box
     */
    public Rectangle2D getBounds2D()
    {
       return getBounds2D(true);       
    }

    /**
     * Gets the (current) visual content for this Sprite
     *
     * This method is called by various setters and the render()
     * method.
     */
    protected abstract TransformableContent getContent();
    
    
    /**
     * Gets the rotation angle this Sprite is currently 'facing'
     * 
     * @return	The rotation angle
     */
    public double getRotation() {
    	return this.angle;
    }

    /**
     * Handle a tick event 
     * (required by MetronomeListener)
     *
     * @param time  The current time (in milliseconds)
     */
    public abstract void handleTick(int time);

    /**
     * Initialize state variables
     */
    protected void reinitialize()
    {
       setScale(1.0);
       setRotation(0.0);
       setLocation(0.0, 0.0);

       setVisible(false);
    }

    /**
     * Does the bounding box of this Sprite intersect the 
     * bounding box of the given Sprite? (given their current state)
     *
     * @param s  The other Sprite
     * @return   true if the two intersect
     */
    public boolean intersects(Sprite s)
    {
    	boolean          retval;
        double           maxx, maxy, minx, miny;
        double           maxxO, maxyO, minxO, minyO;
        Rectangle2D      r;

        retval = true;

        r = getBounds2D(true);
        minx = r.getX();
        miny = r.getY();
        maxx = minx + r.getWidth();
        maxy = miny + r.getHeight();

        r = s.getBounds2D(true);
        minxO = r.getX();
        minyO = r.getY();
        maxxO = minxO + r.getWidth();
        maxyO = minyO + r.getHeight();

        if ( (maxx < minxO) || (minx > maxxO) ||
             (maxy < minyO) || (miny > maxyO) ) retval = false;
        
        return retval;
    }

    /**
     * Render this Sprite
     *
     * @param g   The rendering engine to use
     */
    public void render(Graphics g)
    {
       double                   rx, ry;       
       Rectangle2D              bounds;       
       TransformableContent     tc;

       if (visible)
       {
          tc = getContent();          

          if (tc != null) 
          {
             // Find the point to rotate around
             if (rotationPoint)
             {
                rx = rotationX;
                ry = rotationY;                
             }
             else
             {
                bounds = tc.getBounds2D(false);             
                rx     = bounds.getWidth()/2.0;
                ry     = bounds.getHeight()/2.0;                
             }

             // Transform
             tc.setLocation(x, y);
             tc.setRotation(angle, rx, ry);
             tc.setScale(scaleX, scaleY);             

             // Render
             tc.render(g);       
          }
       }
    }

    /**
     * Set the location (on the Stage) of the Sprite 
     *
     * @param x   The horizontal location
     * @param y   The vertical location
     */
    public void setLocation(double x, double y)
    {
       this.x = x;
       this.y = y;
    }

    /**
     * Set the rotation angle and point to rotate around
     *
     * @param r  The new rotation angle
     * @param x  The x-coordinate of the point to rotate around
     * @param y  The y-coordinate of the point to rotate around
     */
    public void setRotation(double r, double x, double y)
    {
       rotationPoint = true;
       this.angle    = r;
       this.x        = x;
       this.y        = y;
    }

    /**
     * Set the rotation angle (the Sprite will rotate
     * around its midpoint)
     *
     * @param r  The new rotation angle
     */
    public void setRotation(double r)
    {
       rotationPoint = false;
       this.angle    = r;
    }

    /**
     * Set the scaling (enlargement, reduction) of the Sprite 
     *
     * @param sx  The scale in the x-dimension
     * @param sy  The scale in the y-dimension
     */
    public void setScale(double sx, double sy)
    {
       scaleX = sx;
       scaleY = sy;
    }

    /**
     * Set the scaling (enlargement, reduction) of the Sprite 
     *
     * @param s  The new scale
     */
    public void setScale(double s)
    {
       setScale(s, s);
    }

    /**
     * Set the visibility of this Sprite
     *
     * @param v true for visible, false for invisible
     */
    public void setVisible(boolean v)
    {
        visible = v;
    }
}
