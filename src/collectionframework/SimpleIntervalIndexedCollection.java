package collectionframework;

import java.util.*;

/**
 * A collection of objects that is indexed by an interval
 *
 * That is, instead of a each element in the collection
 * being associated with a single index i, each element is
 * associated with a closed interval [a, b]
 *
 * NOTE: This is an easy implementation but not a good one.
 * A better approach is to use an interval tree.  See,
 * for example, Cormen et al. (2001) and the references
 * therein.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      SimpleIntervalIndexedCollection<E> 
       implements IntervalIndexedCollection<E>
{
    private ArrayList<ArrayList<E>>   elements;
    private final Iterator<E>         EMPTY_ITERATOR;

    /**
     * Default Constructor
     */
    public SimpleIntervalIndexedCollection()
    {
       ArrayList<E>     temp;
       
       elements       = new ArrayList<ArrayList<E>>();

       temp           = new ArrayList<E>();
       EMPTY_ITERATOR = temp.iterator();       
    }

    /**
     * Add an element to this collection
     *
     * @param element   The element to add
     * @param left      The left-most point in the closed interval
     * @param right     The right-most point in the closed interval
     */
    public void add(E element, int left, int right)
    {
       ArrayList<E>   temp;       

       for (int i=left; i<=right; i++)
       {
          // Get the collection at i (or construct one)
          ensureCapacity(i+1);          
          temp = elements.get(i);
          
          // Add the element to the collection at i
          temp.add(element);          
       }
    }

    /**
     * Ensure that there is sufficient capacity
     *
     * @param size   The capacity to ensure
     */
    private void ensureCapacity(int size)
    {
       ArrayList<E>   temp;       

       for (int i=0; i<size; i++)
       {
          try
          {
             temp = elements.get(i);
          }
          catch (IndexOutOfBoundsException ioobe)
          {
             elements.add(i, new ArrayList<E>());             
          }
       }
    }

    /**
     * Get all of the elements at point i (i.e., all of the
     * elements with intervals that include i)
     *
     * @param i    The point of interest
     * @return     The elements at that point
     */
    public Iterator<E> get(int i)
    {
       ArrayList<E>   temp;       
       Iterator<E>    result;
       
       result = EMPTY_ITERATOR;       

       try
       {
          temp   = elements.get(i);
       }
       catch (IndexOutOfBoundsException ioobe)
       {
          temp = null;          
       }

       if (temp != null) result = temp.iterator();
       
       return result;
    }
}
