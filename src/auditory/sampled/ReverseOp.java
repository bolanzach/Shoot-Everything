package auditory.sampled;

/**
 * A BufferedSoundUnaryOp that reverses a BufferedSound
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class ReverseOp extends    AbstractBufferedSoundUnaryOp
{
    /**
     * Reverse the signal
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
          destination[i]  = source[length-1-i];
       }
    }
}
