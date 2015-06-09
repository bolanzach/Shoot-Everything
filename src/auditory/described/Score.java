package auditory.described;

import java.util.*;
import javax.sound.midi.*;

import event.*;

/**
 * An encapsulation of a music score
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class Score
{
    private Hashtable<Part, MidiChannel>       channelTable;
    private Hashtable<Part, String>            parts;
    private int                                timeSignatureDenominator, 
                                               timeSignatureNumerator,
                                               millisPerMeasure;

    /**
     * Default Constructor
     */
    public Score()
    {
       channelTable = new Hashtable<Part, MidiChannel>();
       parts        = new Hashtable<Part, String>();       
    }

    /**
     * Add a Part to this Score
     *
     * @param part   The Part to add
     */
    public void addPart(Part part, String instrument)
    {
       parts.put(part, instrument);
    }

    /**
     * Get the Part objects in this Score
     *
     * @return   An Enumeration of the Part objects in this Score
     */
    public Enumeration<Part> getParts()
    {
       return parts.keys();       
    }

    /**
     * Get a String representation of the instrument
     * for a particular Part
     *
     * @param part   The Part of interest
     * @return       The name of the instrument 
     */
    public String getInstrumentName(Part part)
    {
       return parts.get(part);       
    }

    /**
     * Remove a Part from this Score
     *
     * @param part   The Part to remove
     */
    public void removePart(Part part)
    {
       parts.remove(part);
    }    

    /**
     * Render this Score
     */
    public void render()
    {
       Enumeration<Part>           e;
       MidiChannel                 channel;
       Part                        part;
       
       e = parts.keys();
       while (e.hasMoreElements())
       {
          part       = e.nextElement();
          channel    = channelTable.get(part);
          part.render(channel);          
       }
    }

    /**
     * Set the MidiChannel associated with a Part
     *
     * @param part     The Part
     * @param channel  The MidiChannel for that Part
     */
    public void setChannel(Part part, MidiChannel channel)
    {
       channelTable.put(part, channel);             
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
     * Handle an upbeat message
     *
     * @param metronome   The Metronome that the Part objects listen to
     */
    public void upbeat(Metronome metronome)
    {
       Enumeration<Part>           e;
       Part                        part;

       e = parts.keys();
       while (e.hasMoreElements())
       {
          part       = e.nextElement();
          part.upbeat(metronome);
          part.setTimeSignature(timeSignatureNumerator, 
                                timeSignatureDenominator);
          part.setTempo(millisPerMeasure);
       }
    }
}
