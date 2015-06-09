package math;

/**
 * <p>
 * A Metric is a function that satisfies the following properties
 * for all x,y, z:
 * </p>
 *
 * <ul>
 *     distance(x,y) >= 0 </br>
 *     distance(x,y) == 0 iff x == y </br>
 *     distance(x,y) == distance(y,x) </br>
 *     distance(x,y) <= distance(x,z) + distance(y,z) </br>
 * </ul>
 *
 * <p>
 * (The last of these properties is called the triangle inequality.)
 * </p>
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public interface Metric
{
    /**
     * Calculate the distance between two n-dimensional points
     *
     * @param x   One n-dimensional point
     * @param y   Another n-dimensional point
     * @return    The distance
     */
    public abstract double distance(double[] x, double[] y);    
}
