package collectionframework;

import java.util.*;

/**
 * A collection of objects that is indexed by an interval
 *
 * That is, instead of a each element in the collection
 * being associated with a single index i, each element is
 * associated with a closed interval [a, b]
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public interface IntervalIndexedCollection<E>
{
    /**
     * Add an element to this collection
     *
     * @param element   The element to add
     * @param left      The left-most point in the closed interval
     * @param right     The right-most point in the closed interval
     */
    public abstract void add(E element, int left, int right);
    
    /**
     * Get all of the elements at point i (i.e., all of the
     * elements with intervals that include i)
     *
     * @param i    The point of interest
     * @return     The elements at that point
     */
    public abstract Iterator<E> get(int i);
}
