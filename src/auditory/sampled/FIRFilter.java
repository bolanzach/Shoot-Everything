package auditory.sampled;

/**
 * An encapsulation of a Finite Impulse Response (FIR) filter
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class FIRFilter
{
    private double[]     weights;

    /**
     * Explicit Value Constructor
     *
     * @param weights   The weights to apply
     */
    public FIRFilter(double[] weights)
    {
       this.weights = new double[weights.length];       
       System.arraycopy(weights, 0, this.weights, 0, weights.length);       
    }

    /**
     * Get the number of weights (i.e., coefficients) in this
     * FIR filter
     *
     * @return   The number of weights
     */
    public int getLength()
    {
       int   length;
       
       length = 0;
       if (weights != null) length = weights.length;
       
       return length;       
    }

    /**
     * Get a particular weight (i.e., coefficient)
     *
     * @param index   The index of the weight
     */
    public double getWeight(int index)
    {
       double   weight;
       
       weight = 0.0;
       if ((weights == null) && (index == weights.length-1))
       {
          weight = 1.0;
       }
       else if ((index >=0) && (index < weights.length-1))
       {
          weight = weights[index];
       }
       
       return weight;       
    }
}
