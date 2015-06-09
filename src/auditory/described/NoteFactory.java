package auditory.described;

import java.util.*;

/**
 * A factory that can be used to create Note objects
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */ 
public class NoteFactory
{
    /**
     * <p>
     * Parse a String representation of a note and construct a Note
     * from the constituent parts.  In the event of a format error,
     * this method returns null.
     * </p>
     * <p>
     * For example:
     * </p> 
     * <ul>
     * "C ,0,1"  is a middle C whole note <br />
     * "D ,-1,8" is a D (one octave below middle c) eigth note <br />
     * </ul>
     *
     * @param   s   The String to parse
     */
    public static Note parseNote(String s)
    {
        boolean           dotted, sharp;
        char              pitch, sharpChar;
        int               duration, octave, octaveEnd;
        Note              theNote;
        String            durationString, octaveString, token;
        StringTokenizer   st;
        
        st = new StringTokenizer(s, ", ");

        try
        {
           token = st.nextToken();
        
           // Determine the pitch
           pitch = token.charAt(0);
           
           // Determine if this is a sharp or a natural
           sharp = false;        
           if (token.length() == 2)
           {
              sharpChar = token.charAt(1);
              if (sharpChar == '#') sharp = true; // ASCII 35
           }
           
           // Detemine the octave (relative to middle C)
           octaveString = st.nextToken();
           octave       = Integer.parseInt(octaveString);
           
           // Determine the duration (which has an arbitrary length)
           dotted         = false;
           durationString = st.nextToken();
           if (durationString.endsWith(".")) dotted = true;
           duration = (int)Double.parseDouble(durationString);
           
           // Construct a new Note
           theNote = new Note(pitch, sharp, octave, duration, dotted);
        }
        catch (NoSuchElementException nsee)
        {
           theNote = null;           
        }
        
        return theNote;
    }
}
