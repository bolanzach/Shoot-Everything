package auditory.sampled;

/**
 * A BufferedSoundBinaryOp that multiplies two (comparable)
 * BufferedSound objects sample-by-sample
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class MultiplyOp extends AbstractBufferedSoundBinaryOp
{
    /**
     * Multiply (sample-by-sample) the two signals
     *
     * @param source1     The signal in source 1
     * @param source2     The signal in source 2
     * @param destination The resulting destination signal
     */
    public void applyFilter(double[] source1, double[] source2,
                            double[] destination)
    {
       for (int i=0; i<source1.length; i++)
       {
             destination[i] = source1[i] * source2[i];             
       }
    }
}
