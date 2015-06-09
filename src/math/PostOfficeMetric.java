package math;

import java.util.Arrays;

/**
 * The Post Office metric (i.e., a notion of distance that
 * makes sense if, to travel between two points, you have
 * to go through a central post office located at the origin)
 *
 * This is sometimes also called the British Rail metric and/or
 * the shuttle metric.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      PostOfficeMetric
       implements Metric
{
    /**
     * Calculate the distance between two n-dimensional points
     * (required by Metric)
     *
     * Note: For simplicity, this method does not confirm that the
     * two arrays are the same size. It uses the smaller size.
     *
     * @param x   One n-dimensional point
     * @param y   Another n-dimensional point
     * @return    The distance
     */
    public double distance(double[] x, double[] y)
    {
       double  result, xNorm, yNorm;       
       int     n;
       
       if (Arrays.equals(x,y))
       {
          result = 0.0;
       }
       else
       {
          xNorm  = 0.0;       
          yNorm  = 0.0;       
          n      = Math.min(x.length, y.length);

          for (int i=0; i<n; i++)
          {
             xNorm += Math.pow(x[i], 2.0);          
             yNorm += Math.pow(y[i], 2.0);          
          }

          result = Math.sqrt(xNorm) + Math.sqrt(yNorm);
       }

       return result;       
    }
}
