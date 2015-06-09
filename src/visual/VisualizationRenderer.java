package visual;

import java.awt.*;

/**
 * The requirements of a VisualizationRenderer (i.e., an
 * object that handles the rendering for a particular
 * VisualizationView and Visualization)
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public interface VisualizationRenderer
{
    /**
     * Operations to perform after rendering.
     *
     * @param g      The rendering engine
     * @param model  The Visualization containing the content
     * @param view   The component presenting the content
     */
    public abstract void postRendering(Graphics          g,
                                       Visualization     model,
                                       VisualizationView view);
    
    /**
     * Operations to perform before rendering.
     *
     * @param g      The rendering engine
     * @param model  The Visualization containing the content
     * @param view   The component presenting the content
     */
    public abstract void preRendering(Graphics          g,
                                      Visualization     model,
                                      VisualizationView view);

    /**
     * Render the content contained in the model.
     *
     * @param g      The rendering engine
     * @param model  The Visualization containing the content
     * @param view   The component presenting the content
     */
    public abstract void render(Graphics          g,
                                Visualization     model,
                                VisualizationView view);
}
