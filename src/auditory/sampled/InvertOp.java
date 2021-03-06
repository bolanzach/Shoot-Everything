package auditory.sampled;

/**
 * A BufferedSoundUnaryOp that inverts a BufferedSound
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class InvertOp extends    AbstractBufferedSoundUnaryOp
{
    /**
     * Invert the signal
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
          destination[i]  = -source[i];
       }
    }
}
