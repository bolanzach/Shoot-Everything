package math;

/**
 * The supremum metric.  This is sometimes also
 * called the uniform metric and/or the infinity metric.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      SupremumMetric
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
       double  result, term;       
       int     n;
       
       result = Math.abs(x[0]-y[0]);       
       n      = Math.min(x.length, y.length);

       for (int i=1; i<n; i++)
       {
          term = Math.abs(x[i]-y[i]);

          if (term > result) result = term;          
       }

       return result;       
    }
}
