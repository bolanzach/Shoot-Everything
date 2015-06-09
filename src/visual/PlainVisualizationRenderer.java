package visual;

import java.awt.*;
import visual.statik.SimpleContent;
import java.util.*;

/**
 * A "plain" VisualizationRenderer that simply
 * clears the background and renders the content
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      PlainVisualizationRenderer
       implements VisualizationRenderer
{
    
    /**
     * Operations to perform after rendering.
     * This method is called by paint().
     *
     * @param g   The rendering engine
     */
    public void postRendering(Graphics          g,
                              Visualization     model,
                              VisualizationView view)
    {
       // Nothing to do
    }
    
    /**
     * Operations to perform before rendering.
     * This method is called by paint().
     *
     * @param g   The rendering engine
     */
    public void preRendering(Graphics          g,
                             Visualization     model,
                             VisualizationView view)
    {
       Color         background;
       Graphics2D    g2;
       Rectangle     bounds;       
       
       g2 = (Graphics2D)g;    

       // Fill the background
       background = view.getBackground();
       bounds     = g.getClipBounds();       
       
       if ((background != null) && (bounds != null))
       {
          g2.setPaint(background);
          g2.fill(bounds);
       }
    }

    /**
     * Render the content contained in the model.
     * This method is called by paint().
     *
     * @param g   The rendering engine
     */
    public void render(Graphics          g,
                       Visualization     model,
                       VisualizationView view)
    {

       Iterator<SimpleContent>   iter;
       SimpleContent             c;

       iter = model.iterator();
       while (iter.hasNext())
       {
          c  = iter.next();
          if (c != null) c.render(g);
       }
    }
}
