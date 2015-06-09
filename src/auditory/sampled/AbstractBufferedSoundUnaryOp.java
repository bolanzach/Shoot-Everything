package auditory.sampled;

import java.util.*;

/**
 * An abstract class that implements the BufferedSoundUnaryOp
 * interface.  This method can be extended by classes that
 * want to implement the BufferedSoundUnaryOp interface.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public abstract class      AbstractBufferedSoundUnaryOp 
                extends    AbstractBufferedSoundOp
                implements BufferedSoundUnaryOp
{
    /**
     * Apply the filter (sample-by-sample).  This method
     * must be implemented by concrete children
     *
     * @param source      The signal from source
     * @param destination The destination signals
     */
    public abstract void applyFilter(double[] source, 
                                     double[] destination);

    /**
     * Apply the filter to all of the channels
     *
     *
     * @param source      The source signals
     * @param destination The destination signals
     */
    public void applyFilter(Iterator<double[]> source, 
                            Iterator<double[]> destination)
    {
       while (source.hasNext())
       {
          applyFilter(source.next(), destination.next());
       }
    }

    /**
     * A two-source/one-destination filter.  If the
     * destination is null, a BufferedSound with an appropriate
     * AudioFormat and length is created and returned.
     *
     * @param src  The operand (i.e., the sound to operate on)
     * @param dest An empty sound to hold the result (or null)
     */
    public BufferedSound filter(BufferedSound src, 
                                BufferedSound dest)
    {
       Iterator<double[]>    source, destination;

       // Construct the destination if necessary; otherwise check it
       if (dest == null) 
          dest = createCompatibleDestinationSound(src);

       // Get the source channels
       source      = src.getSignals();

       // Get the destination channels
       destination = dest.getSignals();

       // Apply the filter
       applyFilter(source, destination);       

       return dest;
    }
}

