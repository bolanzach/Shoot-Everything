package auditory.sampled;

/**
 * An abstract class that implements the BufferedSoundOp
 * interface.  This method can be extended by classes that
 * want to implement, for example, the BufferedSoundUnaryOp and
 * BufferedSoundUnaryOp interfaces.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public abstract class AbstractBufferedSoundOp
{
    /**
     * Creates a BufferedSound with the same sampling rate and length
     * as the source.  All of the samples in the new BufferedSound will
     * be 0.
     *
     * @param src    The BufferedSound to mimic
     */
    public BufferedSound createCompatibleDestinationSound(
                                         BufferedSound src)
    {
       BufferedSound        temp;
       float                sampleRate;
       int                  channels, length;
       
       channels   = src.getNumberOfChannels();       
       length     = src.getNumberOfSamples();       
       sampleRate = src.getSampleRate();

       temp = new BufferedSound(sampleRate);

       for (int i=0; i<channels; i++)
       {
          temp.addChannel(new double[length]);          
       }

       return temp;       
    }

    /**
     * Check to see if two BufferedSound objects are compatible.
     * 
     * @throws IllegalArgumentException  If they are not compatible
     */
    protected void checkArguments(BufferedSound a, BufferedSound b) 
                                  throws IllegalArgumentException
    {
       if (!a.matches(b))
           throw(new IllegalArgumentException("Argument Mismatch"));
    }
}
