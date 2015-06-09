package math;

/**
 * The Euclidean metric (i.e., the notion of distance that
 * most people are familiar with)
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      EuclideanMetric
       implements Metric
{
    /**
     * Calculate the distance between two n-dimensional points
     * (required by Metric)
     * 
     * Note: For simplicity, this method does not confirm that the
     * two arrays are the same size.  It uses the smaller size.
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
          result += Math.pow(x[i]-y[i], 2.0);          
       }

       return Math.sqrt(result);       
    }
}
