package visual;

import java.awt.*;
import java.util.*;

/**
 * A decorator of a VisualizationRenderer that adds
 * scaling capabilities.  That is, a ScaledVisualizationRenderer
 * will scale all of the content to fit its component.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      ScaledVisualizationRenderer
       implements VisualizationRenderer
{
    private double                  height, scaleX, scaleY, width;       
    private VisualizationRenderer   decorated;

    /**
     * Explicit Value Constructor
     *
     * @param decorated  The VisualizatioRenderer to decorate
     * @param width      The full-size width of the content
     * @param height     The full-size height of the content
     */
    public ScaledVisualizationRenderer(VisualizationRenderer decorated,
                                       double width, double height)
    {
       this.decorated = decorated;       
       this.width     = width;
       this.height    = height;       
    }

    /**
     * Operations to perform after rendering.
     *
     * @param g      The rendering engine
     * @param model  The Visualization containing the content
     * @param view   The component presenting the content
     */
    public void postRendering(Graphics          g,
                              Visualization     model,
                              VisualizationView view)
    {
       Graphics2D   g2;
       
       g2 = (Graphics2D)g;       
       g2.scale(1.0/scaleX, 1.0/scaleY);

       decorated.postRendering(g, model, view);       
    }

    /**
     * Operations to perform before rendering.
     *
     * @param g      The rendering engine
     * @param model  The Visualization containing the content
     * @param view   The component presenting the content
     */
    public void preRendering(Graphics          g,
                             Visualization     model,
                             VisualizationView view)
    {
       Dimension         size;       
       Graphics2D        g2;

       g2   = (Graphics2D)g;       
       size = view.getSize();
       scaleX = size.getWidth()  / width;
       scaleY = size.getHeight() / height;

       g2.scale(scaleX, scaleY);

       decorated.preRendering(g, model, view);       
    }

    /**
     * Render the content contained in the model.
     *
     * @param g      The rendering engine
     * @param model  The Visualization containing the content
     * @param view   The component presenting the content
     */
    public void render(Graphics          g,
                       Visualization     model,
                       VisualizationView view)
    {
       decorated.render(g, model, view);       
    }
}
