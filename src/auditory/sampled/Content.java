package auditory.sampled;

import javax.sound.sampled.*;

/**
 * The capabiltiies of sampled auditory content
 *
 * @author  Prof. David Bernstein, James Madison Univeristy
 * @version 1.0
 */
public interface Content
{
    /**
     * Render this Content
     *
     * @param clip   The Clip to render
     */
    public abstract void render(Clip clip) throws LineUnavailableException;

    /**
     * Get the AudioFormat for this Content
     *
     * @return  The AudioFormat
     */
    public abstract AudioFormat getAudioFormat();
}
