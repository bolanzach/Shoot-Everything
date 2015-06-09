package event;

/**
 * The requirements of an object that listens to a Metronome
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public interface MetronomeListener
{
    /**
     * Handle a Metronome tick
     *
     * @param millis   The number of milliseconds since the Metronome started
     */
    public abstract void handleTick(int millis);    
}
