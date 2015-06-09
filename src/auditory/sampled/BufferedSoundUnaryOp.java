package auditory.sampled;

/**
 * The requirements of all unary operations that
 * can be performed on BufferedSound objects
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public interface BufferedSoundUnaryOp
{
    /**
     * Performs a single-input/single-output operation on a
     * BufferedSound. If the destination is null, a BufferedSound with
     * an appropriate AudioFormat and length is created and returned.
     *
     * @param src   The operand (i.e., sound to operate on)
     * @param dest  An empty sound to hold the result (or null)
     * @throws      IllegalArgumentException if the sounds don't match
     */
    public BufferedSound filter(BufferedSound src, 
                                BufferedSound dest);
}
