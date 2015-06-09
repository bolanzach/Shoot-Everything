package auditory.sampled;

/**
 * A BufferedSoundUnaryOp that applies a FIRFilter to a
 * BufferedSound
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class FIRFilterOp extends AbstractBufferedSoundUnaryOp
{
    private FIRFilter          fir;
    
    /**
     * Explicit Value Constructor
     *
     * @param fir    The FIRFilter to use
     */
    public FIRFilterOp(FIRFilter fir)
    {
       this.fir = fir;       
    }

    /**
     * Apply a FIRFilter
     *
     * @param source      The source signal
     * @param destination The resulting signal
     */
    public void applyFilter(double[] source, double[] destination)
    {
       double    weight;       
       int       length, n;
       
       n         = fir.getLength();
       length    = source.length;
       
       // Copy the first n-2 samples
       for (int i=0; i<n-1; i++)
       {
          destination[i]  = source[i];
       }
       
       // Filter the remaining samples
       for (int i=n-1; i<length; i++)
       {
          for (int k=0; k<n; k++)
          {
             weight        = fir.getWeight(k);
             
             destination[i]  += source[i-k]  * weight;
          }
       }
    }
}
