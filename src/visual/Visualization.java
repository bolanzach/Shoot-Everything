package visual;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import visual.statik.SimpleContent;

/**
 * A collection of Content objects to be rendered
 * (and a collection of GUI components that will
 * render them)
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class Visualization
{
    private   CopyOnWriteArrayList<SimpleContent> content;
    private   LinkedList<VisualizationView>       views;

    /**
     * Default Constructor
     */
    public Visualization()
    {
       content = new CopyOnWriteArrayList<SimpleContent>();
       views   = new LinkedList<VisualizationView>();

       views.addFirst(createDefaultView());       
    }

    /**
     * Add a SimpleContent to the "front" of this Visualization
     *
     * Note: This method only adds the SimpleContent if it is not
     * already in the Visualization.  However, the "underlying"
     * content (e.g., BufferedImage, Shape) in two different
     * SimpleContent objects can be the same.
     *
     * To change the order of a SimpleContent that is already on the canvas
     * use toBack() or toFront().
     * 
     * @param r   The SimpleContent to add
     */
    public void add(SimpleContent r)
    {
       if (!content.contains(r))
       {
          content.add(r);
          repaint();
       }
    }

    /**
     * Add a KeyListener
     *
     * @param kl   The listener to add
     */
    public void addKeyListener(KeyListener kl)
    {
       Iterator<VisualizationView>  i;
       VisualizationView            view;

       i = getViews();
       while (i.hasNext())
       {
          view = i.next();
          view.addKeyListener(kl);       
       }
    }

    /**
     * Add a MouseListener
     *
     * @param ml   The listener to add
     */
    public void addMouseListener(MouseListener ml)
    {
       Iterator<VisualizationView>  i;
       VisualizationView            view;

       i = getViews();
       while (i.hasNext())
       {
          view = i.next();
          view.addMouseListener(ml);       
       }
    }

    /**
     * Add a MouseMotionListener
     *
     * @param mml   The listener to add
     */
    public void addMouseMotionListener(
                        MouseMotionListener mml)
    {
       Iterator<VisualizationView>  i;
       VisualizationView            view;

       i = getViews();
       while (i.hasNext())
       {
          view = i.next();
          view.addMouseMotionListener(mml);       
       }
    }

    /**
     * Add a view to this Visualization
     *
     * @param view    The view to add
     */
    public void addView(VisualizationView view)
    {
       views.addLast(view);       
    }

    /**
     * Clear this Visualization of all SimpleContent objects
     */
    public void clear()
    {
       content.clear();
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
       return  new VisualizationView(this, 
                                     new PlainVisualizationRenderer());       
    }

    /**
     * Get the "main" view (i.e., the "main" VisualizationView
     * that is used to present the SimpleContent objects) associated
     * with this Visualization.
     *
     * Note: A visualization can actually have multiple
     *       views associated with it.  This is a convenience
     *       method that can be used when there is only one such
     *       view.
     *
     * @return   The view
     */
    public VisualizationView getView()
    {
       return views.getFirst();       
    }

    /**
     * Get all of the views associated with this Visualization
     *
     * @return   The views
     */
    public Iterator<VisualizationView> getViews()
    {
       return views.iterator();       
    }

    /**
     * Get an Iterator that contains all of the SimpleContent
     * objects
     *
     * @return   The SimpleContent objects
     */
    public Iterator<SimpleContent> iterator()
    {
       return content.iterator();       
    }

    /**
     * Remove the given SimpleContent from this Canvas
     *
     * @param r    The SimpleContent to remove
     */
    public void remove(SimpleContent r)
    {
       if (content.remove(r)) repaint();
    }

    /**
     * Remove a KeyListener
     *
     * @param kl   The listener to remove
     */
    public synchronized void removeKeyListener(
                                   KeyListener kl)
    {
       Iterator<VisualizationView>  i;
       VisualizationView            view;

       i = getViews();
       while (i.hasNext())
       {
          view = i.next();
          view.removeKeyListener(kl);       
       }
    }

    /**
     * Remove a MouseListener
     *
     * @param ml   The listener to remove
     */
    public synchronized void removeMouseListener(
                                   MouseListener ml)
    {
       Iterator<VisualizationView>  i;
       VisualizationView            view;

       i = getViews();
       while (i.hasNext())
       {
          view = i.next();
          view.removeMouseListener(ml);       
       }
    }

    /**
     * Remove a MouseMotionListener
     *
     * @param mml   The listener to remove
     */
    public synchronized void removeMouseMotionListener(
                                   MouseMotionListener mml)
    {
       Iterator<VisualizationView>  i;
       VisualizationView            view;

       i = getViews();
       while (i.hasNext())
       {
          view = i.next();
          view.removeMouseMotionListener(mml);       
       }
    }

    /**
     * Remove a view from this Visualization
     *
     * @param view    The view to remove
     */
    public void removeView(VisualizationView view)
    {
       views.remove(view);       
    }

    /**
     * Repaint the view(s) assocaited with this Visualization
     */
    protected  void repaint()
    {
       Iterator<VisualizationView>   i;       
       VisualizationView             view;
       
       i = views.iterator();
       while (i.hasNext())
       {
          view = i.next();
          view.repaint();
       }
    }

    /**
     * Set the background color
     *
     * Note: If you want to have a background image just
     * make it the first SimpleContent
     *
     * @param background       The background color
     */
    public void setBackground(Color background)
    {
       Iterator<VisualizationView>    i;
       VisualizationView              view;
       
       i = getViews();
       while (i.hasNext())
       {
          view = i.next();
          view.setBackground(background);
       }
    }

    /**
     * Change the "main" view associated with this Visualization
     *
     * Note: A visualization can actually have multiple
     *       views associated with it.  This is a convenience
     *       method that can be used when there is only one such
     *       view.
     *
     * @param view   The new view to use as the "main" view
     */
    public void setView(VisualizationView view)
    {
       views.removeFirst();
       views.addFirst(view);       
    }

    /**
     * Move the given SimpleContent to the "back"
     *
     * Note: The SimpleContent must have already been added for this
     * method to have an effect.
     *
     * @param r   The SimpleContent to move to the back
     */
    public void toBack(SimpleContent r)
    {
       boolean         removed;
       
       removed = content.remove(r);
       if (removed)
       {
          content.add(r);
       }
    }

    /**
     * Move the given SimpleContent to the "front".
     *
     * Note: The SimpleContent must have already been added for this
     * method to have an effect.
     *
     * @param r   The SimpleContent to move to the front
     */
    public void toFront(SimpleContent r)
    {
       boolean         removed;
       
       removed = content.remove(r);
       if (removed)
       {
          content.add(0, r);
       }
    }
}
