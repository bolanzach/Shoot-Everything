package visual.dynamic;

import event.*;
import visual.*;
import visual.dynamic.described.*;
import visual.dynamic.sampled.*;

/**
 * A Screen that can also contain described dynamic content
 * (that can be used for creating "special effects")
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class SpecialEffectsScreen extends Screen
{
    SpecialEffectsRenderer    renderer;
    Visualization             stage;
    
    /**
     * Default Constructor
     */
    public SpecialEffectsScreen()
    {
       super();
       stage.setView(getView());       
    }
    
    /**
     * Add a Sprite to this Stage
     *
     * @param sprite   The Sprite to add
     */
    public void add(Sprite sprite)
    {
       // Make the Sprite a MetronomeListener
       metronome.addListener(sprite);

       // Treat the Sprite as a SimpleContent and
       // add it to the Visualization
       stage.add(sprite);       
    }    

    /**
     * Create the default view associated with this Visualization
     *
     * Note: This method should only be called by constructors.
     *       It should be overridden by derived classes that 
     *       need to use a specialized view
     */
    protected VisualizationView createDefaultView()
    {
       stage = new Visualization();       
       
       renderer = new SpecialEffectsRenderer(
                      new ScreenRenderer(
                          new PlainVisualizationRenderer()), 
                      stage);       

       return new VisualizationView(this, renderer);
    }
}
