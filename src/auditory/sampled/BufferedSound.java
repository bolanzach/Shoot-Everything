package auditory.sampled;

import java.util.*;
import javax.sound.sampled.*;

/**
 * An in-memory representation of sampled auditory content.
 * Because this is a complete in-memory representation it often uses
 * a lot of memory.  One could, alternatively, keep part of the
 * content in-memory and store the remainder in a file (e.g., using
 * a ring buffer).
 *
 * An individual BufferedSound can only be manipulated by one thread
 * at a time.  This should not be a problem in practice since, most
 * often, a BufferedSound will be manipulated first and then rendered.
 *
 * Note: For simplicity, all BufferedSound objects use signed PCM with
 *       a 16bit sample size, and a big-endian byte order (i.e., network
 *       byte order)
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class BufferedSound implements Content
{
    private ArrayList<double[]>  channels;    
    private AudioFormat          format;    
    private int                  numberOfSamples;    

    private static final double MAX_AMPLITUDE       =  32767.0;
    private static final double MIN_AMPLITUDE       = -32767.0;
    private static final int    SAMPLE_SIZE_IN_BITS = 16;
    private static final int    BYTES_PER_CHANNEL   = SAMPLE_SIZE_IN_BITS/8;

    /**
     * Explicit Value Constructor
     *
     * @param sampleRate   The sampling rate (in Hz)
     */
    public BufferedSound(float sampleRate)
    {
       format = new AudioFormat(
          AudioFormat.Encoding.PCM_SIGNED,
          sampleRate,          // Sample rate in Hz
          SAMPLE_SIZE_IN_BITS, // Sample size in bits
          0,                   // Number of channels
          0,                   // Frame size in bytes
          sampleRate,          // Frame rate in Hz
          true);               // Big-endian or not

       channels = new ArrayList<double[]>();       
       numberOfSamples = 0;       
    }

    /**
     * Add a channel to this BufferedSound
     *
     * One channel corresponds to "mono", two channels corresponds
     * to "stereo", etc...
     */
    public synchronized void addChannel(double[] signal)
    {
       if (numberOfSamples == 0) numberOfSamples = signal.length;       

       if (numberOfSamples == signal.length)
       {
          channels.add(signal);
          updateAudioFormat();       
       }
    }

    /**
     * Append a BufferedSound to this BufferedSound
     * 
     * Note: If the BufferedSound to append does not match
     * this BufferedSound then nothing is done
     *
     * @param other   The BufferedSound to append
     */
    public synchronized void append(BufferedSound other)
    {
       ArrayList<double[]>  temp;       
       double[]             otherSignal, tempSignal, thisSignal;
       Iterator<double[]>   i, j;

       if (matches(other))
       {
          temp = new ArrayList<double[]>();          

          i = channels.iterator();
          j = other.channels.iterator();
          while (i.hasNext())
          {
             thisSignal  = i.next();
             otherSignal = j.next();
             
             // Allocate space for the new signal
             tempSignal = new double[thisSignal.length + 
                                     otherSignal.length];
          
             // Copy the current signal
             System.arraycopy(thisSignal, 0, 
                              tempSignal, 0, thisSignal.length);
             
             // Append the other left signal
             System.arraycopy(otherSignal, 0, 
                              tempSignal, thisSignal.length, 
                              otherSignal.length);
             
             // Save the longer signal
             temp.add(tempSignal);
          }
          channels = temp;          
       }
    }

    /**
     * Get the AudioFormat for this BufferedSound
     *
     * @return  The AudioFormat
     */
    public synchronized AudioFormat getAudioFormat()
    {
       return format;       
    }

    /**
     * Get the signals

     * Note: It is dangerous to provide access to the
     * signal data since it could be modified in
     * inappropriate ways
     *
     * @return  The signal for the left output
     */
    public synchronized Iterator<double[]> getSignals()
    {
       return channels.iterator();       
    }

    /**
     * Get the length of this BufferedSound in microseconds
     *
     * @return  The length in microseconds
     */
    public synchronized int getMicrosecondLength()
    {
       return (int)(getNumberOfSamples() / 
                    getSampleRate()      * 
                    1000000.0             );
    }

    /**
     * Get the length of this BufferedSound in milliseconds
     *
     * @return  The length in milliseconds
     */
    public synchronized int getMillisecondLength()
    {
       return getMicrosecondLength()/1000;
    }

    /**
     * Get the number of channels
     *
     * @return  The number of channels
     */
    public synchronized int getNumberOfChannels()
    {
       return channels.size();       
    }

    /**
     * Get the number of samples (per channel) in this BufferedSound 
     *
     * @return   The number of samples
     */
    public synchronized int getNumberOfSamples()
    {
       return numberOfSamples;
    }
    
    /**
     * Get the sampling rate for this BufferedSound
     *
     * @return  The sampling rate (in Hz)
     */
    public synchronized float getSampleRate()
    {
       return format.getSampleRate();       
    }

    /**
     * Compares this BufferedSound object to another
     *
     * @param other  The BufferedSound to compare to
     * @return       true if the two match; false otherwise
     */
    public synchronized boolean matches(BufferedSound other)
    {
       boolean      result;
       
       result = false;
       result = getAudioFormat().matches(other.getAudioFormat()) &&
               (getNumberOfSamples() == other.getNumberOfSamples());
    

       return result;
    }

    /**
     * Render this BufferedSound on the given Clip
     *
     * @param clip    The Clip to use
     */
    public synchronized void render(Clip clip) 
                        throws LineUnavailableException
    {
       byte[]              rawBytes;       
       double[]            signal;       
       int                 channel, frameSize, length, offset, size;       
       Iterator<double[]>  iterator;       
       short               scaled;

       size   = channels.size();       
       length = getNumberOfSamples();
       frameSize  = format.getFrameSize();

       //  bytes           samples/channel *  bytes/channel     *  channels
       rawBytes = new byte[length          *  BYTES_PER_CHANNEL *     size];

       channel  = 0;       
       iterator = channels.iterator();
       while (iterator.hasNext())
       {
          signal = iterator.next();
          offset = channel * BYTES_PER_CHANNEL;          

          for (int i=0; i<length; i++)
          {
             scaled = scaleSample(signal[i]);

             // Big-endian
             rawBytes[frameSize*i+offset]   = (byte)(scaled >> 8);
             rawBytes[frameSize*i+offset+1] = (byte)(scaled & 0xff);

             // Little-endian
             // rawBytes[frameSize*i+offset+1] = (byte)(scaled >> 8);
             // rawBytes[frameSize*i+offset]   = (byte)(scaled & 0xff);
          }
          ++channel;          
       }

       // Throws LineUnavailableException
       clip.open(format, rawBytes, 0, rawBytes.length);
       
       // Start the Clip
       clip.start();
    }

    /**
     * Scale a sample so that it fits in a signed short
     * (i.e., two bytes)
     *
     * @param sample   The sample to scale
     */
    private short scaleSample(double sample)
    {
       short     scaled;
       
       if      (sample > MAX_AMPLITUDE) scaled=(short)MAX_AMPLITUDE;
       else if (sample < MIN_AMPLITUDE) scaled=(short)MIN_AMPLITUDE;
       else                             scaled=(short)sample;

       return scaled;       
    }

    /**
     * Update the AudioFormat (usually after a channel is added)
     */
    private void updateAudioFormat()
    {
       format = new AudioFormat(
          format.getEncoding(),              // Encoding
          format.getSampleRate(),            // Sample rate in Hz
          format.getSampleSizeInBits(),      // Sample size in bits
          channels.size(),                   // Number of channels
          channels.size()*BYTES_PER_CHANNEL, // Frame size in bytes
          format.getSampleRate(),            // Frame rate in Hz
          format.isBigEndian());             // Big-endian or not
    }
}
