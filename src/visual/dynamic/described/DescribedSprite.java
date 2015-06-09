package visual.dynamic.described;

import java.awt.*;
import java.awt.geom.*;
import java.util.Iterator;
import java.util.Vector;

import visual.statik.described.*;

/**
 * A TweeningSprite that uses described visual content
 *
 * Note: This class uses a Vector of AggregateContent
 * objects rather than CompositeContent objects for
 * simplicity.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class DescribedSprite extends TweeningSprite
{
    private   AggregateContent          tweened;
    private   Vector<AggregateContent>  content;

    /**
     * Default Constructor
     */
    public DescribedSprite()
    {
       content = new Vector<AggregateContent>();
       tweened = new AggregateContent();
    }

    /**
     * Add a key time
     *
     * Note: This method does not ensure that the first key
     * time actually contains static visual content
     *
     * @param keyTime     The time
     * @param location    The location of the sprite at this time
     * @param rotation    The rotation of the sprite (null to align with path)
     * @param scaling     The scaling of the sprite at this time
     * @param ctc         The Content to use (or null to use previous Content)
     */
    public void addKeyTime(int keyTime, Point2D location,
                           Double rotation, Double scaling, 
                           AggregateContent ctc)
    {
       int         index;
       
       index = super.addKeyTime(keyTime, location, rotation, scaling);

       if (index >= 0)
       {
          // If ctc is null then re-use the last CompositeContent
          if (ctc == null) ctc = content.get(index-1); 

          content.insertElementAt(ctc, index);
       }
       
    }

    /**
     * Get the visual content associated with this Sprite
     *
     * Note that the visual content created depends on the
     * current state of this Sprite
     *
     * @return     The transformed content
     */
    public visual.statik.TransformableContent getContent()
    {
       int                   current, next;
       AggregateContent      currentCTC, nextCTC, result;


       current = getKeyTimeIndex();
       next    = getNextKeyTimeIndex();
       
       result = null;
       
       if (current >= 0)
       {
          currentCTC = content.get(current);
          nextCTC    = content.get(next);

          result    = currentCTC;

          if (currentCTC != nextCTC)
          {
             tweenShape(currentCTC, nextCTC, getInterpolationFraction());
             result = tweened;
          }
       }

       return result;       
    }

    /**
     * Determine the current 'tweened shape
     *
     * @param a      The first shape
     * @param b      The second shape
     * @param frac   The interpolation fraction
     */
    protected void tweenShape(AggregateContent a, 
                              AggregateContent b,
                              double frac)
    {
       Color                      color;
       float[]                    coords, coordsA, coordsB;
       GeneralPath                gp;
       int                        seg;
       Iterator<Content>          iterA, iterB;
       PathIterator               piA, piB;
       Paint                      paint;
       Content                    shapeA, shapeB;
       Stroke                     stroke;


       tweened = new AggregateContent();
       
       coordsA = new float[6];
       coordsB = new float[6];
       coords  = new float[6];
       
       iterA = a.iterator();
       iterB = b.iterator();
       
       // Loop over all of the TransformableContent objects
       // in the AggregateContent
       while (iterA.hasNext())
       {
          shapeA = iterA.next();
          if (iterB.hasNext()) shapeB = iterB.next();
          else                 shapeB = shapeA;
          
          piA = shapeA.getPathIterator(false);
          piB = shapeB.getPathIterator(false);
          
          gp = new GeneralPath();
          gp.setWindingRule(piA.getWindingRule());

          
          // Loop over all of the segments in the 
          // TransformableContent object
          while (!piA.isDone())
          {
             seg = piA.currentSegment(coordsA);
             if (piB.isDone()) // Use the coordinates of the first shape
             {
                for (int i=0; i < coordsA.length; i++) 
                   coords[i] = coordsA[i];
             }
             else           // Interpolate the coordinates
             {
                piB.currentSegment(coordsB);
                
                for (int i=0; i < coordsA.length; i++)
                {
                   coords[i] = coordsA[i] + 
                               (float)frac*(coordsB[i] - coordsA[i]);
                }
             }
             
             // Add to the General Path object
             if      (seg == PathIterator.SEG_MOVETO)
             {
                gp.moveTo(coords[0], coords[1]);
             }
             else if (seg == PathIterator.SEG_LINETO)
             {
                gp.lineTo(coords[0], coords[1]);
             }
             else if (seg == PathIterator.SEG_QUADTO)
             {
                gp.quadTo(coords[0], coords[1], coords[2], coords[3]);
             }
             else if (seg == PathIterator.SEG_CUBICTO)
             {
                gp.curveTo(coords[0], coords[1], 
                           coords[2], coords[3], 
                           coords[4], coords[5]);
             }
             else if (seg == PathIterator.SEG_CLOSE)
             {
                gp.closePath();
             }
             
             piA.next();
             piB.next();
          }
        
          paint  = shapeA.getPaint();
          color  = shapeA.getColor(); // This could also be tweened
          stroke = shapeA.getStroke();

          tweened.add(new Content(gp, color, paint, stroke));
       }
    }
}
