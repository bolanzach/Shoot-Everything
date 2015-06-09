package auditory.described;

/**
 * A utility class for working with MIDI numbers
 *
 * Note: "Midi" is not "MIDI" to be consistent with the naming
 * convention of exitsing Java classes
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class MidiCalculator
{
    /**
     * Calculate the MIDI number
     *
     * @param pitch    The pitch ('A','B','C','D','E','F', 'G' or 'R')
     * @param sharp    true for a sharp and false for a natural
     * @param octave   The octave (relative to middle C)
     * @return         The MIDI number
     */
    public static int numberFor(char    pitch, 
                                boolean sharp, 
                                int     octave)
    {
       int    midiBase, midiNumber;
       

        // Handle special cases (B sharp and E sharp)
        if ((pitch == 'B') && sharp)
        {
           pitch = 'C';
           sharp = false;           
        }
        else if ((pitch == 'E') && sharp)
        {
           pitch = 'F';
           sharp = false;
        }

        // Calculate the MIDI value
        midiBase = 60;
        if      (pitch == 'A') midiNumber = midiBase +  9;
        else if (pitch == 'B') midiNumber = midiBase + 11;
        else if (pitch == 'C') midiNumber = midiBase +  0;
        else if (pitch == 'D') midiNumber = midiBase +  2;
        else if (pitch == 'E') midiNumber = midiBase +  4;
        else if (pitch == 'F') midiNumber = midiBase +  5;
        else if (pitch == 'G') midiNumber = midiBase +  7;
        else                   midiNumber = -1;  // Rest        

        if (sharp) midiNumber = midiNumber + 1;

        midiNumber = midiNumber + (octave * 12);

        return midiNumber;        
    }
}
