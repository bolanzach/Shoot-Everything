package auditory.sampled;

import java.io.*;
import java.net.*;
import javax.sound.sampled.*;

import io.*;

/**
 * A utility class that can be used to create BufferedSound
 * objects in various ways.
 *
 * Notes: One millisecond is 1/1,000     of a second
 *        One microsecond is 1/1,000,000 of a second
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class BufferedSoundFactory
{
    private ResourceFinder         finder;

    /**
     * Default Constructor
     */
    public BufferedSoundFactory()
    {
       finder = ResourceFinder.createInstance(this);       
    }

    /**
     * Explicit Value Constructor
     *
     * @param finder  The ResourceFinder to use (if needed)
     */
    public BufferedSoundFactory(ResourceFinder finder)
    {
       this.finder = finder;       
    }

    /**
     * Create a BufferedSound from a sine wave with a 
     * particular frequency
     *
     * The length of the sound is measured in microseconds
     * to be consistent with the Clip interface
     *
     * @param frequency   The frequency of the wave (in Hz)
     * @param length      The length of the sound (in microseconds)
     * @param sampleRate  The number of samples per second
     * @param amplitude   The maximum amplitude of the wave in [0.0, 32767.0]
     */
    public BufferedSound createBufferedSound(double frequency,
                                             int    length,
                                             float  sampleRate,
                                             double amplitude)
    {
       BufferedSound     sound;       
       double            radians,radiansPerSample, rmsValue;
       double[]          signal;
       int n;

       //samples =      samples/sec * sec
       n         = (int)(sampleRate * (double)length/1000000.0);
       
       signal    = new double[n];
       //  rads/sample  = ( rads/cycle * cycles/sec)/ samples/sec 
       radiansPerSample = (Math.PI*2.0 * frequency) / sampleRate;       
       for (int i=0; i<signal.length; i++)
       {
          // rad  =   rad/sample     * sample
          radians = radiansPerSample * i;

          signal[i] = amplitude * Math.sin(radians);
       }
       sound = new BufferedSound(sampleRate);
       sound.addChannel(signal);
       return sound;
    }

    /**
     * Create a BufferedSound from a resource/file
     *
     * @param name    The name of the resource
     */
    public BufferedSound createBufferedSound(String name)
                                throws IOException, 
                                       UnsupportedAudioFileException
    {
       AudioInputStream        stream;
       URL                     url;
       
       url    = finder.findURL(name);
       stream = AudioSystem.getAudioInputStream(url);
       
       return createBufferedSound(stream);        
    }

    /**
     * Create a BufferedSound from an AudioInputStream
     *
     * @param inStream    The stream to read from
     */
    public BufferedSound createBufferedSound(AudioInputStream inStream)
                                throws IOException, 
                                       UnsupportedAudioFileException
    {
       AudioFormat        inFormat, pcmFormat;
       AudioInputStream   pcmStream;
       BufferedSound      sound;       
       byte[]             rawBytes;       
       double[]           leftSignal, monoSignal, rightSignal;       
       int                bufferSize, offset, n, sampleLength;
       int[]              signal;       

       inFormat = inStream.getFormat();

        // Convert ULAW and ALAW to PCM
        if ((inFormat.getEncoding() == AudioFormat.Encoding.ULAW) ||
            (inFormat.getEncoding() == AudioFormat.Encoding.ALAW)   ) {

            pcmFormat = new AudioFormat(
                              AudioFormat.Encoding.PCM_SIGNED,
                              inFormat.getSampleRate(),
                              inFormat.getSampleSizeInBits()*2,
                              inFormat.getChannels(),
                              inFormat.getFrameSize()*2,
                              inFormat.getFrameRate(),
                              true);

            pcmStream = AudioSystem.getAudioInputStream(pcmFormat, 
                                                        inStream);
        }
        else // It is PCM
        {
           pcmFormat = inFormat;           
           pcmStream = inStream;           
        }

        // Create a buffer and read the raw bytes
        bufferSize = (int)(pcmStream.getFrameLength()) 
                           * pcmFormat.getFrameSize();

        rawBytes = new byte[bufferSize];
        offset   = 0;  
        n        = 0;        
        while (pcmStream.available() > 0)
        {
           n  = pcmStream.read(rawBytes, offset, bufferSize);
           offset += n;
        }

        // Convert the raw bytes
        if (pcmFormat.getSampleSizeInBits() == 8)
        {
           signal = processEightBitQuantization(rawBytes, pcmFormat);
        }
        else
        {
           signal = processSixteenBitQuantization(rawBytes, pcmFormat);
        }

        sound = new BufferedSound(pcmFormat.getSampleRate());

        // Process the individual channels
        if (pcmFormat.getChannels() == 1)  // Mono
        {
           sampleLength = signal.length;
           monoSignal   = new double[sampleLength];           

           for (int i=0; i<sampleLength; i++)
           {
              monoSignal[i]  = signal[i]; // Convert to double
           }
           sound.addChannel(monoSignal);
        }
        else                               // Stereo
        {
           sampleLength = signal.length/2;
           leftSignal   = new double[sampleLength];           
           rightSignal  = new double[sampleLength];           

           for (int i=0; i<sampleLength; i++)
           {
              leftSignal[i]  = signal[2*i];
              rightSignal[i] = signal[2*i+1];
           }
           sound.addChannel(leftSignal);
           sound.addChannel(rightSignal);
        }
        
        return sound;        
    }

    /**
     * Convert the raw bytes for 8-bit samples
     *
     * @param rawBytes   The array of raw bytes
     * @param format     The AudioFormat
     */
    private int[] processEightBitQuantization(
                                        byte[]      rawBytes,
                                        AudioFormat format)
    {
       int         lsb, msb;       
       int[]       signal;
       String      encoding;
       
       
       signal = new int[rawBytes.length];
       encoding = format.getEncoding().toString();

       if (encoding.startsWith("PCM_SIGN"))
       {
          for (int i=0; i<rawBytes.length; i++) 
             signal[i] = rawBytes[i];
       }
       else
       {
          for (int i=0; i<rawBytes.length; i++) 
             signal[i] = rawBytes[i]-128;
       }
       
       return signal;       
    }

    /**
     * Convert the raw bytes for 16-bit samples
     *
     * @param rawBytes   The array of raw bytes
     * @param format     The AudioFormat
     */
    private int[] processSixteenBitQuantization(
                                          byte[]      rawBytes,
                                          AudioFormat format)
    {
       int         lsb, msb;       
       int[]       signal;

       signal = new int[rawBytes.length / 2];

       if (format.isBigEndian())  // Big-endian
       {
          for (int i=0; i<signal.length; i++)
          {
             // First byte is high-order byte
             msb = (int) rawBytes[2*i];

             // Second byte is low-order byte
             lsb = (int) rawBytes[2*i+1];

             signal[i] = msb << 8 | (255 & lsb);
          }
       } 
       else                       // Little-endian
       {
          for (int i=0; i<signal.length; i++)
          {
             // First byte is low-order byte
             lsb = (int) rawBytes[2*i];

             // Second byte is high-order byte
             msb = (int) rawBytes[2*i+1];

             signal[i] = msb << 8 | (255 & lsb);
          }
       }
       return signal;       
    }
}
