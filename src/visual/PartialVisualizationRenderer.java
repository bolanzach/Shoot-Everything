package visual;

import java.awt.*;
import java.util.*;

/**
 * A decorator of a VisualizationRenderer that 
 * only shows part of the content.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      PartialVisualizationRenderer
       implements VisualizationRenderer
{
    private double                  x, y;       
    private VisualizationRenderer   decorated;

    /**
     * Explicit Value Constructor
     *
     * @param decorated  The VisualizatioRenderer to decorate
     * @param x          The left-most point to render
     * @param y          The upper-most point to render
     */
    public PartialVisualizationRenderer(VisualizationRenderer decorated,
                                        double x, double y)
    {
       this.decorated = decorated;       
       this.x         = x;
       this.y         = y;       
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
       g2.translate(x, y);
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
       Graphics2D   g2;
       
       g2 = (Graphics2D)g;       
       g2.translate(-x, -y);
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


