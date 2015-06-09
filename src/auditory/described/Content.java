package auditory.described;

import javax.sound.midi.*;

/**
 * The requirements of a piece of described auditory content 
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public interface Content
{
    /**
     * Get the type of this Content [i.e., 1 for whole notes, 
     * 2 for half notes, etc...]
     *
     * @return   The  type
     */
    public abstract int  getType();
    
    /**
     * Is this Content dotted?
     *
     * @return true if dotted; false otherwise
     */
    public abstract boolean isDotted();    
    
    /**
     * Render this Content on the given MidiChannel
     *
     * @param channel   The MIDI channel to use
     */
    public abstract void render(MidiChannel channel);

    /**
     * Set whether this Content is audible or note
     *
     * @param audible  true to make it audible; false otherwise
     */
    public abstract void setAudible(boolean audible);    
}
