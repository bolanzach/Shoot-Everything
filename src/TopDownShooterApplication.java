import javax.swing.*;
import app.MultimediaApplication;


/**
 * A Top Down Shooter Application
 *
 * @author  Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class      TopDownShooterApplication
       extends    MultimediaApplication
{
    /**
     * The entry-point of the application
     *
     * @param args    The command-line arguments
     */
    public static void main(String[] args) throws Exception
    {
       SwingUtilities.invokeAndWait(
          new TopDownShooterApplication(args, 800, 600));
    }

    /**
     * Explicit Value Constructor
     *
     * @param args    The command-line aguments
     * @param width   The width of the content (in pixels)
     * @param height  The height of the content (in pixels)
     */
    public TopDownShooterApplication(String[] args,
                            int width, int height)
    {
       super(args, new Controller(), width, height);       
    }
}
