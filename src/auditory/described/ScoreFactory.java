package auditory.described;

import java.io.*;
import java.util.StringTokenizer;
import javax.sound.midi.MidiUnavailableException;

import io.*;

/**
 * A factory that creates Score objects
 */
public class ScoreFactory
{
    private ResourceFinder         finder;

    /**
     * Default Constructor
     */
    public ScoreFactory()
    {
       finder = ResourceFinder.createInstance();       
    }

    /**
     * Explicit Value Constructor
     *
     * @param finder   The ResourceFinder to used (if needed)
     */
    public ScoreFactory(ResourceFinder finder)
    {
       this.finder = finder;       
    }
    
    /**
     * Create a Score object from a BufferedReader that
     * is reading a stream containing a string representation
     *
     * @param is     The InputStream to read from
     */
    public Score createScore(InputStream is) 
                        throws IOException,
                               MidiUnavailableException 
    {
       BufferedReader         in;
       int                    denominator, numerator, tempo;       
       Note                   note;
       Part                   part;       
       Score                  score;       
       String                 line, voice;
       StringTokenizer        st;
       
       in = new BufferedReader(new InputStreamReader(is));
       
       // Read the time signature and tempo
       line        = in.readLine();
       st          = new StringTokenizer(line, ",/");
       numerator   = Integer.parseInt(st.nextToken());
       denominator = Integer.parseInt(st.nextToken());
       tempo       = Integer.parseInt(st.nextToken());

       score = new Score();
       
       score.setTimeSignature(numerator, denominator);
       score.setTempo(tempo);       

       while ((voice = in.readLine()) != null)
       {
          part = PartFactory.createPart(in);
          score.addPart(part, voice);
       }
       
       return score;       
    }
    
    /**
     * Create a Score object from a file containing a string representation
     *
     * @param filename   The name of the file
     */
    public Score createScore(String filename) 
                        throws IOException,
                               MidiUnavailableException 
    {
       InputStream            is;
       
       is = finder.findInputStream(filename);       
       
       return createScore(is);
    }
}
