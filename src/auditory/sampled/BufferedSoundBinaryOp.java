package auditory.sampled;

/**
 * The requirements of all unary operations that
 * can be performed on BufferedSound objects
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public interface BufferedSoundBinaryOp
{
    /**
     * Performs a dual-input/single-output operation on a 
     * BufferedSound. If the destination is null, 
     * a BufferedSound with an appropriate AudioFormat and length
     * is created and returned.
     *
     * @param src1  One operand (i.e., one sound to operate on)
     * @param src2  The other operand (i.e., other sound to operate on)
     * @param dest  An empty sound to hold the result (or null)
     * @throws  IllegalArgumentException if the sounds don't match
     */
    public BufferedSound filter(BufferedSound src1, BufferedSound src2,
                                BufferedSound dest)
                         throws IllegalArgumentException;
}
