package auditory.described;

import java.util.*;
import javax.sound.midi.*;

/**
 * A Chord is a collection of Note objects that are
 * rendered simultaneously
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      Chord
       extends    AbstractContent
{
    private ArrayList<Note>       notes;

    /**
     * Default Constructor
     */
    public Chord()
    {
       this(1, false);
    }

    /**
     * Explicit Value Constructor
     *
     * @param type     1 for whole notes, 2 for half notes, etc...
     * @param dotted   Whether the note is dotted
     */
    public Chord(int type, boolean dotted)
    {
       super(type, dotted);
       
       notes = new ArrayList<Note>();       
    }

    /**
     * Add a Note to this Chord
     *
     * @param note  The Note to add
     */
    public void addNote(Note note)
    {
       notes.add(note);       
    }

    /**
     * Start playing this Chord on the given MidiChannel
     *
     * @param channel   The MIDI channel to use
     */
    protected void startPlaying(MidiChannel channel)
    {
       Iterator<Note>     i;
       Note               note;

       i = notes.iterator();
       while (i.hasNext())
       {
          note = i.next();
          if (note != null)
          {
             note.setType(type);
             note.setDotted(dotted);
             note.startPlaying(channel);          
          }
       }
    }

    /**
     * Start playing this Chord on the given MidiChannel
     *
     * @param channel   The MIDI channel to use
     */
    protected void stopPlaying(MidiChannel channel)
    {
       Iterator<Note>     i;
       Note               note;

       i = notes.iterator();
       while (i.hasNext())
       {          
          note = i.next();          
          if (note != null)
          {
             note.stopPlaying(channel);          
          }
       }
    }
}

