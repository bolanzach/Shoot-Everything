package event;

import java.awt.*;
import java.util.*;


/**
 * <p>
 * A Metronome "ticks" at regular intervals, informing any
 * registered listeners
 * </p>
 *
 * <p>
 * We use a Metronome instead of a java.util.Timer or a 
 * javax.swing.Timer object for two reasons:
 * </p>
 *
 * <ol>
 *   <li> It is more generic (i.e., the micro edition of Java does
 *        not have a javax.swing.Timer)</li>
 *
 *   <li> It is instructive with regard to multi-threading, 
 *        the observer pattern, the Java event-dispatch thread,
 *        and inner classes.</li>
 *
 *   <li> It gives us the option of using both fixed-delay and
 *        fixed-rate execution.</li>
 * </ol>
 *
 * <p>
 * The biggest disadvantage of this class is that it does not use thread
 * sharing.  Hence, an application should not use many different
 * Metronome objects.
 * </p>
 *
 * <p>
 * A Metronome may "drift" since the sleep() method is not guaranteed
 * to return in exactly the right amount of time.  A "fixed rate"
 * metronome will attempt to fix this (by notifying listeners in quick
 * succession).
 * </p>
 *
 * <p>
 * The time that is reported to listeners will depend on the operating
 * mode.  A "fixed interval" Metronome will report the time as if it
 * is drift-free.  That is, the difference in reported times will be
 * exactly the delay (even when the Metronome has drifted).  A "fixed
 * rate" Metronome will report the time as if it is correcting the
 * drift.  That is, the different in reported times will reflect the
 * amount of time it tried to wait between reports.  Neither will
 * report the actual time between reports.  A listener that wants to
 * know this information must use System.currentTimeMillis() itself.
 * </p>
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class Metronome implements Runnable
{
    private ArrayList<MetronomeListener>   listeners;
    private volatile boolean               adjusting, keepRunning;
    protected volatile int                 delay, multiplier, time;
    private volatile long                  lastTick;    
    private MetronomeListener[]            copy;
    private MetronomeTickDispatcher        dispatcher;    
    protected Thread                       timerThread;


    /**
     * Default Constructor
     */
    public Metronome()
    {
       delay          = 1000;
       adjusting      = false;       
       keepRunning    = false;
       multiplier     = 1;       
       dispatcher     = new MetronomeTickDispatcher();       
       listeners      = new ArrayList<MetronomeListener>();
       copy           = new MetronomeListener[0];       

       reset();       
    }

    /**
     * Explicit Value Constructor
     *
     * Constructs a "fixed interval" Metronome with the given delay
     *
     * @param delay      The number of milliseconds between ticks
     */
    public Metronome(int delay)
    {
       this(delay, false);
    }
    
    /**
     * Explicit Value Constructor
     *
     * @param delay      The number of milliseconds between ticks
     * @param adjusting  true for "fixed rate"; false for "fixed interval"
     */
    public Metronome(int delay, boolean adjusting)
    {
       this();       
       this.delay     = delay;
       this.adjusting = adjusting;       
    }

    /**
     * Add a MetronomeListener
     *
     * @param ml   The MetronomeListener to add
     */
    public synchronized void addListener(MetronomeListener ml)
    {
       listeners.add(ml);              
       copyListeners();
    }

    /**
     * Copy the collection of listeners
     *
     * The copy is used in the event dispatch thread to avoid
     * concurrent modification problems.
     *
     * This method is called infrequently (and, in general,
     * before events are dispatched) so it does not cause
     * any real efficiency problems.
     */
    private void copyListeners()
    {
       copy = new MetronomeListener[listeners.size()];
       listeners.toArray(copy);
    }
    
    /**
     * Get the current delay
     */
    public int getDelay()
    {
       return delay;       
    }
    

    /**
     * Get the number of listeners
     */
    public synchronized int getNumberOfListeners()
    {
       return listeners.size();       
    }
    
    /**
     * Get the current amount of time elapsed (in milliseconds)
     * 
     * @return time	The elapsed time
     */
    public synchronized int getTime() {
    	return time;
    }

    /**
     * Notify observers in the GUI/event-dispatch thread.
     *
     * Note: Listeners are notified in the REVERSE order  
     * in which they are added.
     */
    protected synchronized void notifyListeners()
    {
       // Setup the state of the MetronomeTickDispatcher
       dispatcher.setup(copy, time);
       
       // Cause the run() method of the dispatcher to be
       // called in the GUI/event-dispatch thread
       EventQueue.invokeLater(dispatcher);
    }

    /**
     * Remove a MetronomeListener
     *
     * @param ml   The MetronomeListener to remove
     */
    public synchronized void removeListener(MetronomeListener ml)
    {
       listeners.remove(ml);       
       copyListeners();
    }

    /**
     * Reset the time
     *
     * Note: This method should only be called when the
     * Metronome is not running
     */
    public void reset()
    {
       time = 0;       
    }

    /**
     * The code that is executed in the timer thread
     * (required by Runnable)
     */
    public void run()
    {
       int       currentDelay;       
       long      currentTick, drift;       

       currentDelay = delay;       
       if (adjusting) lastTick = System.currentTimeMillis();

       while (keepRunning)
       {
          try
          {
             timerThread.sleep(currentDelay);
             time += currentDelay * multiplier;              
             
             if (adjusting) // Need to compensate for drift
             {
                currentTick = System.currentTimeMillis();
                drift = (currentTick - lastTick) - currentDelay;       
                currentDelay = (int)Math.max(0, delay-drift);
                lastTick = currentTick;                
             }
             notifyListeners();
          }
          catch (InterruptedException ie)
          {
             // stop() was called
          }
       }
       timerThread = null;       
    }

    /**
     * Set the multiplier (i.e., the apparent speed-up factor)
     *
     * Note: This method should only be called when the
     * Metronome is not running
     *
     * @param multiplier   The multiplier
     */
    public void setMultiplier(int multiplier)
    {
       this.multiplier = multiplier;       
    }

    /**
     * Set the current time
     *
     * Note: This method should only be called when the
     * Metronome is not running
     *
     * @param time   The current time
     */
    public void setTime(int time)
    {
       this.time = time;       
    }    

    /**
     * Start this Metronome
     */
    public void start()
    {
       if (timerThread == null)
       {
          keepRunning = true;
          timerThread = new Thread(this);
          timerThread.start();
       }
    }
    
    /**
     * Stop this Metronome "immediately"
     */
    public void stop()
    {
       keepRunning = false;
       if (timerThread != null) timerThread.interrupt();       
    }

    /**
     * A MetronomeTickDispatcher is used by a Metronome to
     * inform listeners of a tick.
     *
     * This class implements Runnable because its run()
     * method will be called in the GUI/event-dispatch thread.
     */
    private class MetronomeTickDispatcher implements Runnable
    {
        private MetronomeListener[]   listeners;
        private int                   time;
        
        
        /**
         * Code to be executed in the event-dispatch thread
         * (required by Runnable)
         *
         * Specifically, notify the listeners
         */
        public void run()
        {
           int    n;
           
           n = listeners.length;           
           for (int i=n-1; i>=0; i--)
           {
              if (listeners[i] != null) 
                 listeners[i].handleTick(time);
           }
        }
        
        /**
         * Setup this MetronomeTickDispatcher for the next
         * dispatch
         *
         * @param listeners  The collection of MetronomeListener objects
         * @param time       The (relative) time of the tick
         */
        public void setup(MetronomeListener[] listeners, 
                          int time)
        {
           this.listeners = listeners;           
           this.time      = time;           
        }
    }
}
