package auditory.sampled;

import java.util.*;
import javax.sound.sampled.*;

/**
 * A Boombox renders/presents sampled auditory content
 *
 * @author  Prof. David Bernstein, James Madison Univeristy
 * @version 1.0
 */
public class BoomBox implements LineListener
{
    private Content         content;
    private Clip            clip;    
    private final Object    sync = new Object();

    private Vector<LineListener> listeners = new Vector<LineListener>();    

    /**
     * Explicit Value Constructor
     *
     * @param content  The Content
     */
    public BoomBox(Content content)
    {
       this.content = content;       
    }

    /**
     * Add a LineListener to this Content
     *
     * @param listener  The LineListener to add
     */
    public void addLineListener(LineListener listener)
    {
       listeners.add(listener);       
    }

    /**
     * Render the Content without blocking
     */
    public void start() 
                throws LineUnavailableException
    {
       start(false);       
    }

    /**
     * Render this Content
     *
     * @param block   true to block the calling thread until the clip stops
     */
    public void start(boolean block) 
                throws LineUnavailableException
    {
       Clip                clip;       

       clip = AudioSystem.getClip();
       clip.addLineListener(this); // So the calling thread can be informed
       content.render(clip);

       synchronized(sync)
       {
          // Wait until the Clip stops [and notifies us by
          // calling the update() method]
          if (block)
          {
             try
             {
                sync.wait();
             }
             catch (InterruptedException ie)
             {
                // Ignore
             }
          }
       }
    }

    /**
     * Remove a LineListener
     *
     * @param listener  The LineListener to add
     */
    public void removeLineListener(LineListener listener)
    {
       listeners.remove(listener);       
    }

    /**
     * Handle LineEvents (required by LineListener)
     *
     * @param evt  The LineEvent of interest
     */
    public void update(LineEvent evt)
    {
       Enumeration      e;
       LineEvent.Type   type;
       LineListener     listener;

       synchronized(sync)
       {
          // Forward the LineEvent to all LineListener objects
          e = listeners.elements();
          while (e.hasMoreElements())
          {
             listener = (LineListener)e.nextElement();
             listener.update(evt);          
          }

          // Get the type of the event
          type = evt.getType();

          // Process STOP events
          if (type.equals(LineEvent.Type.STOP))
          {
             sync.notifyAll();
             clip.close();
             clip.removeLineListener(this);
             clip = null;             
          }
       }
    }
}
