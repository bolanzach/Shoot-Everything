package auditory.described;

import java.io.*;

/**
 * A factory that creates Part objects
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class PartFactory
{
    /**
     * Create a Part object from a BufferedReader that
     * is reading a stream containing a string representation
     *
     * @param in     The BufferedReader
     */
    public static Part createPart(BufferedReader in) 
                       throws IOException
    {
       Note                   note;
       Part                   part;       
       String                 line;

       part = new Part();
       
       while ((line = in.readLine()) != null && 
              (!line.equals("X"))              )
       {
          if (!line.equals(""))
          {
             note = NoteFactory.parseNote(line);
             if (note != null) part.add(note);          
          }
       }
       
       return part;       
    }

    /**
     * Create a Part object from a file containing a 
     * String representation
     *
     * @param filename   The name of the file
     */
    public static Part createPart(String filename) 
                       throws IOException
    {
       BufferedReader         in;
       
       in = new BufferedReader(new FileReader(filename));

       return createPart(in);
    }
}
