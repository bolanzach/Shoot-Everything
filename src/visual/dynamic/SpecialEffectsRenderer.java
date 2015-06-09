package visual.dynamic;

import java.awt.*;
import java.util.*;
import javax.swing.*;

import visual.*;
import visual.statik.*;
import visual.dynamic.described.Sprite;

/**
 * The renderer for SpecialEffectsScreen objects
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      SpecialEffectsRenderer 
       implements VisualizationRenderer
{
    private Visualization           stage;    
    private VisualizationRenderer   decorated;    

    /**
     * Explicit Value Constructor
     */
    public SpecialEffectsRenderer(VisualizationRenderer decorated,
                                  Visualization         stage)
    {
       this.decorated = decorated;
       this.stage     = stage;       
    }

    /**
     * Operations to perform after rendering
     *
     * @param g      The rendering engine
     * @param model  The Visualization containing the content
     * @param view   The component presenting the content
     */
    public void postRendering(Graphics          g,
                              Visualization     model,
                              VisualizationView view)
    {
       decorated.postRendering(g, model, view);
    }

    /**
     * Operations to perform before rendering
     *
     * @param g      The rendering engine
     * @param model  The Visualization containing the content
     * @param view   The component presenting the content
     */
    public void preRendering(Graphics          g,
                             Visualization     model,
                             VisualizationView view)
    {
       decorated.preRendering(g, model, view);
    }

    /**
     * Render the content contained in the model.
     *
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
       decorated.render(g, stage, view);
    }
}
