package auditory.described;

import javax.sound.midi.*;

/**
 * A partial encapsulation of a piece of described auditory
 * content
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public abstract class      AbstractContent
                implements Content
{
    protected boolean                 audible, dotted, playing;
    protected int                     type;

    /**
     * Default Constructor
     */
    public AbstractContent()
    {
        this(1, false);
    }

    /**
     * Explicit Value Constructor
     *
     * @param type     1 for whole notes, 2 for half notes, etc...
     * @param dotted   Whether the note is dotted
     */
    public AbstractContent(int type, boolean dotted)
    {
        this.type     = type;
        this.dotted   = dotted;
        this.audible  = false;
        this.playing  = false;        
    }

    /**
     * Get the type of this AbstractContent [i.e., 1 for whole notes, 
     * 2 for half notes, etc...]
     * (required by MidiSound)
     *
     * @return   The  type
     */
    public int getType()
    {
       return type;
    }

    /**
     * Is this AbstractContent dotted?
     * (required by MidiSound)
     *
     * @return true if dotted; false otherwise
     */
    public boolean isDotted()
    {
       return dotted;       
    }

    /**
     * Render this AbstractContent on the given MidiChannel
     * (required by MidiSound)
     *
     * @param channel   The MIDI channel to use
     */
    public void render(MidiChannel channel)
    {
       if      ( audible && !playing) 
       {
          playing = true;             
          startPlaying(channel);
       }
       else if (!audible &&  playing) 
       {
          playing = false;             
          stopPlaying(channel);
       }
    }

    /**
     * Set whether this AbstractContent is audible or note
     *
     * @param audible  true to make it audible; false otherwise
     */
    public void setAudible(boolean audible)
    {
       this.audible = audible; 
    }

    /** 
     * Set whether this AbstractContent is dotted
     *
     * @param dotted   Whether the note is dotted
     */
    protected void setDotted(boolean dotted)
    {
       this.dotted = dotted;       
    }
    
    /**
     * Set the type of this AbstractContent
     *
     * @param type     1 for whole notes, 2 for half notes, etc...
     */
    protected void setType(int type)
    {
       this.type = type;       
    }

    /**
     * Start playing this AbstractContent on the given MidiChannel
     *
     * @param channel   The MIDI channel to use
     */
    protected abstract void startPlaying(MidiChannel channel);

    /**
     * Start playing this AbstractContent on the given MidiChannel
     *
     * @param channel   The MIDI channel to use
     */
    protected abstract void stopPlaying(MidiChannel channel);
}
