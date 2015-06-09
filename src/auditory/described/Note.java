package auditory.described;

import javax.sound.midi.*;

/**
 * An encapsulation of a single note in a song
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class   Note 
       extends AbstractContent
{
    private boolean                 sharp;
    private char                    pitch;
    private int                     midiNumber;

    /**
     * Default Constructor
     *
     * Constructs a whole note at middle C
     */
    public Note()
    {
        this('C', false, 0, 1, false);
    }

    /**
     * Explicit Value Constructor
     *
     * @param pitch    The pitch ('A','B','C','D','E','F', 'G' or 'R')
     * @param sharp    true for a sharp and false for a natural
     * @param octave   The octave (relative to middle C)
     * @param type     1 for whole notes, 2 for half notes, etc...
     * @param dotted   Whether the note is dotted
     */
    public Note(char pitch,   boolean sharp, int octave, 
                int type, boolean dotted)
    {
       super(type, dotted);
       
       this.pitch = Character.toUpperCase(pitch);
       this.sharp = sharp;
       midiNumber = MidiCalculator.numberFor(pitch, sharp, octave);
    }

    /**
     * Get the MIDI number associated with this Note
     *
     * @return  The MIDI number
     */
    public int getMIDI()
    {
       return midiNumber;       
    }

    /**
     * Start playing this Note on the given MidiChannel
     *
     * @param channel   The MIDI channel to use
     */
    protected void startPlaying(MidiChannel channel)
    {
       channel.noteOn(midiNumber, 127);
    }

    /**
     * Start playing this Note on the given MidiChannel
     *
     * @param channel   The MIDI channel to use
     */
    protected void stopPlaying(MidiChannel channel)
    {
       channel.noteOff(midiNumber, 127);
    }
}
