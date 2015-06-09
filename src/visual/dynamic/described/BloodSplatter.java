package visual.dynamic.described;

import java.awt.geom.Point2D;

import io.*;
import visual.dynamic.described.*;
import visual.statik.sampled.*;

/**
 * The Bus in the transit information system example
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class BloodSplatter extends SampledSprite
{
    /**
     * Default Constructor
     *
     * @param fn   The file containing the bitmap
     */
    public BloodSplatter(ResourceFinder finder, int time, double x, double y)
    {
       super();
       Content              content;
       ContentFactory       factory;

       factory = new ContentFactory(finder);        
       content = factory.createContent("rsc/splatter1.png", 4);
       addKeyTime( time, (int)x, (int)y, content);
       content = factory.createContent("rsc/splatter2.png", 4);
       addKeyTime( time+100, (int)x, (int)y, content);
       content = factory.createContent("rsc/splatter3.png", 4);
       addKeyTime( time+200, (int)x, (int)y, content);
       content = factory.createContent("rsc/splatter4.png", 4);
       addKeyTime( time+300, (int)x, (int)y, content);

       setEndState(REMOVE);
    }

    /**
     * Add a key time
     *
     * @param time     The key time
     * @param x        The x position
     * @param y        The y position
     * @param content  The Content to use
     */
    private void addKeyTime(int time, int x, int y, 
                            Content content)
    {
       addKeyTime(time*1000, new Point2D.Double(x,y), 
                  null, new Double(1.0), content);
    }
}
