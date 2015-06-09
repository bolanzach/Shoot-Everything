package event;

import java.net.*;
import java.io.*;


/**
 * A Metronome that is driven by UDP messages.
 *
 * Notmally, a UDPDrivenMetronome receives its time information from
 * a UDPMetronomeBroadcaster.
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class UDPDrivenMetronome extends Metronome
{
    private boolean                   keepRunning;    
    private byte[]                    buffer;    
    private DatagramPacket            packet;    
    private DatagramSocket            socket;    
    private int                       referenceTime;
    
    /**
     * Explicit Value Constructor
     *
     * @param port   The port to receive ticks on
     */
    public UDPDrivenMetronome(int port) throws IOException
    {
       // The delay and adjusting attributes have no impact
       super(0, false);
       referenceTime = 0;       
       keepRunning   = true;       

       buffer = new byte[4];       
       socket = new DatagramSocket(port);       
       packet = new DatagramPacket(buffer, buffer.length);             
    }
    
    
    /**
     * Reset the time
     *
     * Note: This method should only be called when the
     * Metronome is not running
     */
    public void reset()
    {
       referenceTime = time;
       super.reset();       
    }

    /**
     * Set the multiplier  (i.e., the apparent speed-up factor)
     *
     * Note: This method should only be called when the
     * Metronome is not running
     *
     * @param multiplier   The multiplier
     */
    public void setMultiplier(int multiplier)
    {
       super.setMultiplier(multiplier);       
    }

    /**
     * Set the current time
     *
     * Note: This method should only be called when the
     * Metronome is not running
     *
     * @param time   The current time
     */
    public void setTime(int time)
    {
       referenceTime = referenceTime - time;
       super.setTime(time);       
    }    

    /**
     * The code that is executed in the timer thread
     * (required by Runnable)
     */
    public void run()
    {
       int      reportedTime;
       
       while (keepRunning)
       {
          try
          {
             socket.receive(packet);
          }
          catch (IOException ioe)
          {
             // Try again
          }

          reportedTime =   (buffer[0] << 24) + 
                          ((buffer[1] & 0xFF) << 16) +
                          ((buffer[2] & 0xFF) << 8) +
                           (buffer[3] & 0xFF);
          
          time = (reportedTime - referenceTime) * multiplier;
          
          notifyListeners();
       }
       timerThread = null;       
    }

    
}
