package auditory.described;

import java.io.InputStream;
import java.net.URL;
import java.util.*;
import javax.sound.midi.*;

import event.*;
import io.*;

/**
 * An encapsulation of an orchestra
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class Orchestra implements MetronomeListener
{
    private Hashtable<String, Instrument>      instruments;
    private Metronome                          metronome;    
    private MidiChannel[]                      channels;
    private Score                              score;    

    /**
     * Explicit Value Constructor
     *
     * @param score   The Score to play
     */
    public Orchestra(Score score) throws MidiUnavailableException 
    {
       this(score, new Metronome(10));       
    }
    

    /**
     * Explicit Value Constructor
     *
     * @param score      The Score to play
     * @param metronome  The Metronome to use
     */
    public Orchestra(Score score, Metronome metronome)
                                  throws MidiUnavailableException 
    {
       Instrument[]    loaded;
       Soundbank       soundbank;
       Synthesizer     synthesizer;

       this.score     = score;
       this.metronome = metronome;
       metronome.addListener(this);       
              
       instruments  = new Hashtable<String, Instrument>();
       
       synthesizer = MidiSystem.getSynthesizer();
       synthesizer.open();
       soundbank = synthesizer.getDefaultSoundbank();

       if (soundbank == null) soundbank = findSoundbank();

       synthesizer.loadAllInstruments(soundbank);

       channels    = synthesizer.getChannels();

       loaded = synthesizer.getLoadedInstruments();
       for (int i=0; i<loaded.length; i++)
       {
          instruments.put(loaded[i].getName(), loaded[i]);
       }
    }

    /**
     * Find a soundbank resource
     */
    private Soundbank findSoundbank() throws MidiUnavailableException
    {
       InputStream           is;       
       ResourceFinder        finder;
       Soundbank             sb;       
       URL                   url;
       
       sb     = null;
       
       finder = ResourceFinder.createInstance(this);
       is     = finder.findInputStream("soundbank-mid.gm");
       //url    = finder.findURL("soundbank-mid.gm");
       
       try
       {
          sb  = MidiSystem.getSoundbank(is);
          //sb  = MidiSystem.getSoundbank(url);
       }
       catch (Exception e)
       {
          throw(new MidiUnavailableException());          
       }
       

       return sb;       
    }

    /**
     * Handle a Metronome tick
     * (required by MetronomeListener)
     *
     * This method tells the Score to render itself.  In addition, if
     * there are no other objects listening to the Metronome, this
     * method stops the Metronome
     *
     * @param millis   The number of milliseconds since the Metronome started
     */
    public void handleTick(int millis)
    {
       score.render();

       if (metronome.getNumberOfListeners() == 1)
       {
          metronome.stop();          
       }
    }

    /**
     * Start this Orchestra
     */
    public void start()
    {
       Enumeration<Part>           e;
       Instrument                  instrument;       
       int                         i;       
       MidiChannel                 channel;       
       String                      name;       
       Patch                       patch;       
       Part                        part;
       

       e = score.getParts();
       i = 0;       

       while (e.hasMoreElements())
       {
          part       = e.nextElement();
          name       = score.getInstrumentName(part);          
          instrument = instruments.get(name);
          
          // Have the channel use the appropriate instrument
          if (instrument == null)
          {
             channels[i].programChange(0, 0);
          }
          else
          {
             patch = instrument.getPatch();
             channels[i].programChange(patch.getBank(), 
                                       patch.getProgram());
          }
          score.setChannel(part, channels[i]);             

          score.upbeat(metronome);
       }
              
       
       // Start the metronome
       metronome.start();
    }
}
