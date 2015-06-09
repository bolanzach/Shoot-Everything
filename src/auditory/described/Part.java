package auditory.described;

import java.util.*;
import javax.sound.midi.*;

import event.*;

/**
 * A Part (i.e., a sequence of Content objects) in a Score
 *
 * Note: This class is not thread safe.  Hence, Part objects should
 * be modified as required before they are rendered.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class Part implements MetronomeListener
{
    private ArrayList<Content>     sounds;
    private double                 timeSignatureDenominator, 
                                   timeSignatureNumerator;
    private int                    millisPerMeasure, stopTime;

    private Content                currentContent, 
                                   previousContent;
    private Iterator<Content>      iterator;    
    private Metronome              metronome; // Not Owned    

    /**
     * Default Constructor
     */
    public Part()
    {
       sounds       = new ArrayList<Content>();
    }

    /**
     * Add a Content to this Part
     *
     * @param c  The Content to add
     */
    public void add(Content c)
    {
       if (c != null) sounds.add(c);
    }

    /**
     * Handle a Metronome tick
     * (required by MetronomeListener)
     *
     * @param millis  The number of milliseconds since the start
     */
    public void handleTick(int millis)
    {
       double   beats, millisPerBeat, type;    

       if (iterator == null) 
          throw(new IllegalStateException("No upbeat()"));

       if (millis >= stopTime)
       {
          if (currentContent != null) 
             currentContent.setAudible(false);

          if (iterator.hasNext())
          {
             previousContent = currentContent;             
             currentContent  = iterator.next();

             // This calculation needn't really be done each iteration
             millisPerBeat = 1.0/(double)timeSignatureNumerator *
                             millisPerMeasure;

             beats        =  (1.0/(double)currentContent.getType()) * 
                             (double)timeSignatureDenominator;
 
             if (currentContent.isDotted()) beats = beats * 1.5;
            
             stopTime = millis + (int)(beats * millisPerBeat);

             currentContent.setAudible(true);             
          }
          else
          {
             metronome.removeListener(this);             
          }          
       }
    }

    /**
     * Render the current note in this Part
     *
     * @param channel  The MidiChannel to use
     */
    public void render(MidiChannel channel)
    {
       if (previousContent != null) 
          previousContent.render(channel);       

       if (currentContent  != null) 
          currentContent.render(channel);       
    }

    /**
     * Set the tempo for this Part
     *
     * @param millisPerMeasure  The tempo (in milliseconds per measure)
     */
    public void setTempo(int millisPerMeasure)
    {
       this.millisPerMeasure = millisPerMeasure;       
    }
    

    /**
     * Set the time signature for this Part
     *
     * @param numerator    The numerator of the time signature
     * @param denominator  The denominator of the time signature
     */
    public void setTimeSignature(int numerator, int denominator)
    {
       this.timeSignatureNumerator   = numerator;
       this.timeSignatureDenominator = denominator;
    }

    /**
     * Alert this Part to the fact that is should
     * make itself ready to be played
     *
     * @param metronome   The Metronome it will listen to
     */
    public void upbeat(Metronome metronome)
    {
       this.metronome   = metronome; // For later removal

       iterator         = sounds.iterator();
       currentContent   = null;
       previousContent  = null;
       stopTime         = -1;       

       metronome.addListener(this);       
    }
}
