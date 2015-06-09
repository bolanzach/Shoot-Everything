package auditory.sampled;

import java.util.Random;

/**
 * A BufferedSoundUnaryOp that adds noise to a signal
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class NoiseOp extends    AbstractBufferedSoundUnaryOp
{
    private double        max;    
    private Random        rng;

    /**
     * Explicit Value Constructor
     *
     * @param max   The maximum amount of noise to add
     */
    public NoiseOp(double max)
    {
       this.max = max;       
       rng = new Random(System.currentTimeMillis());
       
    }

    /**
     * Add noise to the signal
     *
     * @param source      The source signal
     * @param destination The resulting signal
     */
    public void applyFilter(double[] source, double[] destination)
    {
       int       length;
       
       length    = source.length;

       for (int i=0; i<length; i++)
       {
          destination[i]  = source[i]  + (max - rng.nextDouble()*max*2.0);
       }
    }
}
