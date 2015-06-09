package auditory.sampled;

/**
 * A BufferedSoundBinaryOp that adds two (comparable)
 * BufferedSound objects sample-by-sample
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class AddOp extends    AbstractBufferedSoundBinaryOp
{
    /**
     * Adds (sample-by-sample) the two BufferedSound objects.
     *
     * @param source1     The signal in source 1
     * @param source2     The signal in source 2
     * @param destination The resulting channel
     */
    public void applyFilter(double[] source1, double[] source2,
                            double[] destination)
    {
       for (int i=0; i<source1.length; i++)
       {
             destination[i] = source1[i] + source2[i];             
       }
    }
}
