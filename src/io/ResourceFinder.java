package io;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * A ResourceFinder is used to find a "resource" either in
 * the .jar file (containing the ResourceFinder class)
 * or in the local file system
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class ResourceFinder
{
    private Class                 c;

    /**
     * Default Constructor
     */
    private ResourceFinder()
    {
       c = this.getClass();       
    }

    /**
     * Explicit Value Constructor
     *
     * @param   The Object that is using the ResourceFinder
     */
    private ResourceFinder(Object o)
    {
       // Get the Class for the Object that wants the resource
       c = o.getClass();       
    }

    /**
     * Factory method     
     *
     * @return   A ResourceFinder that will search "locally"
     */
    public static ResourceFinder createInstance()
    {
       return new ResourceFinder();
    }

    /**
     * Factory method     
     *
     * @param o  The Object that defines the location of the search
     * @return   A ResourceFinder that will search "near" o
     */
    public static ResourceFinder createInstance(Object o)
    {
       return new ResourceFinder(o);       
    }

    /**
     * Find a resource
     *
     * @return  The InputStream of the resource (or null)
     */
    public InputStream findInputStream(String name)
    {
       InputStream    is;

       is    = c.getResourceAsStream(name);

       return is;       
    }

    /**
     * Find a resource
     *
     * @return  The URL of the resource (or null)
     */
    public URL findURL(String name)
    {
       URL            url;

       url   = c.getResource(name);
       
       return url;       
    }

    /**
     * Load a list of resource names from a list (e.g., file)
     *
     * Note: This method does not return an array of InputStream
     * objects to conserver resources.
     *
     * @param  listName  The name of the list
     * @return           The resource names
     */
    public String[] loadResourceNames(String listName)
    {
       ArrayList<String>        buffer;       
       BufferedReader           in;       
       InputStream              is;
       String                   line;       
       String[]                 names;
       
       names = null;       
       is    = findInputStream(listName);

       if (is != null)
       {
          try
          {
             in     = new BufferedReader(new InputStreamReader(is));
             buffer = new ArrayList<String>();
             
             while ((line=in.readLine()) != null)
             {
                buffer.add(line);
             }
             names = new String[buffer.size()];             
             buffer.toArray(names);             
          }
          catch (IOException ioe)
          {
             // Can't read the list
          }
       }
       return names;       
    }
}
