package visual;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import visual.statik.*;

/**
 * A GUI component that presents a collection of SimpleContent
 * objects
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      VisualizationView
       extends    JComponent
       implements MouseListener
{
    // Attributes used for double-buffering
    protected boolean               useDoubleBuffering;    
    protected Graphics2D            bg;    
    protected Image                 offscreenImage;
    protected int                   height, width;    
    protected Visualization         model;
    protected VisualizationRenderer renderer;

    /**
     * Explicit Value Constructor
     *
     * @param model     The Visualization to use
     * @param renderer  The VisualizationRenderer to use
     */
    public VisualizationView(Visualization         model,
                             VisualizationRenderer renderer)
    {
       super();
       this.model    = model;       
       this.renderer = renderer;       

       setLayout(new BorderLayout());

       height = -1;
       width  = -1;       

       // Use "local" double buffering
       setDoubleBuffered(true);       
       super.setDoubleBuffered(false);
       
       addMouseListener(this);       
    }

    /**
     * Create the off-screen buffer if necessary
     * (i.e., if the size has changed)
     *
     * @return  The rendering engine for the off-screen buffer
     */
    private Graphics2D createOffscreenBuffer()
    {
       Dimension                    d;       

       d = getSize();
       if ((d.height != height) || (d.width != width))
       {
          height = d.height;
          width  = d.width;
          
          offscreenImage = createImage(width, height);
          bg = (Graphics2D)offscreenImage.getGraphics();
          bg.setClip(0,0,width,height);
       }

       return bg;       
    }

    /**
     * Get the VisualizationRenderer that this component
     * uses when rendering
     *
     * @return    The VisualizationRenderer
     */
    public VisualizationRenderer getRenderer()
    {
       return renderer;       
    }

    /**
     * Handle mouseClicked messages
     * (required by MouseListener)
     *
     * @param me    The MouseEvent that generated the message
     */
    public void mouseClicked(MouseEvent me)
    {
    }
    
    /**
     * Handle mouseClicked messages
     * (required by MouseListener)
     *
     * @param me    The MouseEvent that generated the message
     */
    public void mouseEntered(MouseEvent me)
    {
       requestFocus(); // To get the focus for keyboard events
    }
    
    /**
     * Handle mouseClicked messages
     * (required by MouseListener)
     *
     * @param me    The MouseEvent that generated the message
     */
    public void mouseExited(MouseEvent me)
    {
    }

    /**
     * Handle mouseClicked messages
     * (required by MouseListener)
     *
     * @param me    The MouseEvent that generated the message
     */
    public void mousePressed(MouseEvent me)
    {
    }
    
    /**
     * Handle mouseClicked messages
     * (required by MouseListener)
     *
     * @param me    The MouseEvent that generated the message
     */
    public void mouseReleased(MouseEvent me)
    {
    }

    /**
     * Paint (i.e., render) this JComponent
     *
     * @param g       The rendering engine to use
     */
    public void paint(Graphics g)
    {
       Graphics2D      bg;
       
       if (useDoubleBuffering) bg = createOffscreenBuffer();          
       else                    bg = (Graphics2D)g;
       
       if (bg != null)
       {
          // Perform necessary operations before rendering
          preRendering(bg);

          // Render the visual content
          render(bg);

          // Perform necessary operations after rendering
          postRendering(bg);

          if (useDoubleBuffering)
          {
             // Put the offscreen image on the screen
             g.drawImage(offscreenImage, 0, 0, null);

             // Reset the clipping area
             bg.setClip(0,0,width,height);
          }
       }
    }

    /**
     * Turn on/off "local" double buffering
     *
     * @param db   true to double buffer; false otherwise
     */
    public void setDoubleBuffered(boolean db)
    {
       useDoubleBuffering = db;       
    }

    /**
     * Set the VisualizationRenderer that this component
     * should use when rendering
     *
     * @param renderer  The VisualizationRenderer
     */
    public void setRenderer(VisualizationRenderer renderer)
    {
       this.renderer = renderer;       
    }
    
    /**
     * Operations to perform after rendering.
     * This method is called by paint().
     *
     * @param g   The rendering engine
     */
    protected void postRendering(Graphics g)
    {
       renderer.postRendering(g, model, this);
    }
    
    /**
     * Operations to perform before rendering.
     * This method is called by paint().
     *
     * @param g   The rendering engine
     */
    protected void preRendering(Graphics g)
    {
       renderer.preRendering(g, model, this);
    }

    /**
     * Render the content contained in the model.
     * This method is called by paint().
     *
     * @param g   The rendering engine
     */
    protected void render(Graphics g)
    {
       renderer.render(g, model, this);
    }

    /**
     * Handle updates
     *
     * This is overriden so that the background is not
     * erased before painting (which is the default)
     */
    public void update(Graphics g)
    {
       paint(g);
    }
}
