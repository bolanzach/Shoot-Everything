package auditory.sampled;

/**
 * A BufferedSoundUnaryOp that changes the speed at which
 * a signal is presented
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class SpeedChangeOp extends AbstractBufferedSoundUnaryOp
{
    private double        multiplier;    

    /**
     * Explicit Value Constructor
     *
     * @param multiplier   The multiplier to use
     */
    public SpeedChangeOp(double multiplier)
    {
       this.multiplier = multiplier;
    }

    /**
     * Creates a BufferedSound with a different sampling rate.
     *
     * @param src    The source BufferedSound
     */
    public BufferedSound createCompatibleDestinationSound(BufferedSound src)
    {
       BufferedSound        temp;
       float                sampleRate;
       int                  channels, length;
       
       channels   = src.getNumberOfChannels();       
       length     = src.getNumberOfSamples();       
       sampleRate = src.getSampleRate() * (float)multiplier;
       
       temp = new BufferedSound(sampleRate);

       for (int i=0; i<channels; i++)
       {
          temp.addChannel(new double[length]);          
       }

       return temp;       
    }

    /**
     * Copy the signal
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
          destination[i]  = source[i];
       }
    }
}
