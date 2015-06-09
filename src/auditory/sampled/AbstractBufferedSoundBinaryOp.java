package auditory.sampled;

import java.util.*;

/**
 * An abstract class that implements the BufferedSoundBinaryOp
 * interface.  This method can be extended by classes that
 * want to implement the BufferedSoundBinaryOp interface.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public abstract class      AbstractBufferedSoundBinaryOp 
                extends    AbstractBufferedSoundOp
                implements BufferedSoundBinaryOp
{
    /**
     * Apply the filter (sample-by-sample).  This method
     * must be implemented by concrete children
     *
     * @param source1     The signal from source1
     * @param source2     The signal from source2
     * @param destination The destination signals
     */
    public abstract void applyFilter(double[] source1, 
                                     double[] source2, 
                                     double[] destination);

    /**
     * Apply the filter to all of the channels
     *
     * @param source1     The signals from source1
     * @param source2     The signals from source2
     * @param destination The destination signals
     */
    public void applyFilter(Iterator<double[]> source1, 
                            Iterator<double[]> source2, 
                            Iterator<double[]> destination)
    {
       while (source1.hasNext())
       {
          applyFilter(source1.next(), source2.next(), destination.next());
       }
    }

    /**
     * Check to see if two BufferedSound objects are compatible.
     * 
     * @throws IllegalArgumentException  If they are not compatible
     */
    protected void checkArguments(BufferedSound a, 
                                  BufferedSound b) 
                                  throws IllegalArgumentException
    {
       if (!a.matches(b))
           throw(new IllegalArgumentException("Argument Mismatch"));
    }

    /**
     * A two-source/one-destination filter.  If the
     * destination is null, a BufferedSound with an appropriate
     * AudioFormat and length is created and returned.
     *
     * @param src1  One operand (i.e., one sound to operate on)
     * @param src2  The other operand (i.e., other sound to operate on)
     * @param dest  An empty sound to hold the result (or null)
     * @throws  IllegalArgumentException if the sounds don't match
     */
    public BufferedSound filter(BufferedSound src1, 
                                BufferedSound src2,
                                BufferedSound dest)
                         throws IllegalArgumentException
    {
       Iterator<double[]>   source1, source2, destination;


       // Check the properties of the two source sounds
       checkArguments(src1, src2);

       // Construct the destination if necessary; otherwise check it
       if (dest == null) 
          dest = createCompatibleDestinationSound(src1);
       else
          checkArguments(src1, dest);

       
       // Get the source channels
       source1     = src1.getSignals();
       source2     = src2.getSignals();

       // Get the destination channels
       destination = dest.getSignals();
       

       // Apply the filter
       applyFilter(source1, source2, destination);       

       return dest;
    }
}

