package collectionframework;

import java.util.*;

/**
 * An Iterator/Enumeration that contains one (or zero) elements
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class      NullIterator<E>
       implements Enumeration<E>, Iterator<E>
{
    private boolean    done;    
    private E          element;
    
    /**
     * Default Constructor
     */
    public NullIterator()
    {
       clear();       
    }
    
    /**
     * Explicit Value Constructor
     *
     * @param  element    The element in the Iterator
     */
    public NullIterator(E element)
    {
       setElement(element);       
    }

    /**
     * Clear this Iterator (so that it no longer has
     * an element)
     */
    public void clear()
    {
       done    = true;
    }

    /**
     * Returns true if this Enumeration has another element
     * (required by Enumeration)
     *
     * @return  true if this Enumeration has another element
     */
    public boolean hasMoreElements()
    {
       return hasNext();       
    }

    /**
     * Returns true if this Iterator has another element
     * (required by Iterator)
     *
     * @return  true if this Iterator has another element
     */
    public boolean hasNext()
    {
       return !done;       
    }


    /**
     * Returns the next element in this Enumeration
     * (required by Enumeration)
     */
    public E nextElement() throws NoSuchElementException
    {
       return next();
    }


    /**
     * Returns the next element in this Iterator
     * (required by Iterator)
     */
    public E next() throws NoSuchElementException
    {
       if (done) throw(new NoSuchElementException());
       
       done = true;       
       return element;       
    }

    /**
     * Remove the last element from the underlying collection
     * (required by Iterator)
     *
     * This method is not supported
     */
    public void remove() throws UnsupportedOperationException
    {
       throw(new UnsupportedOperationException());       
    }

    /**
     * Reset this Iterator so that it (again) contains
     * the single element
     */
    public void reset()
    {
       done = false;       
    }

    /**
     * Set the single element in this Iterator
     *
     * @param element   The element
     */
    public void setElement(E element)
    {
       this.element = element;       
       reset();       
   }
}
