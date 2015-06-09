package math;

/**
 * The rectilinear metric (i.e., the sum of the absolute values of the
 * differences between the elements).  This is sometimes also
 * called the Manhattan metric (because it is the distance you have to walk
 * between two points in a city that is layed out on a grid).
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      RectilinearMetric
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
       double  result;       
       int     n;
       
       result = 0.0;       
       n      = Math.min(x.length, y.length);

       for (int i=0; i<n; i++)
       {
          result += Math.abs(x[i]-y[i]);          
       }

       return result;       
    }
}
