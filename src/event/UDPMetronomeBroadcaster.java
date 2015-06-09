package event;

import java.net.*;
import java.io.*;

/**
 * A MetronomeListener that can be used to broadcast Metronome
 * ticks over UDP
 *
 * @author  Prof. David Bernstein, James Madison University
 * @see     "The Design and Implementation of Multimedia Software (c) 2011"
 * @version 1.0
 */
public class UDPMetronomeBroadcaster implements MetronomeListener
{
    private byte[]                      buffer;    
    private DatagramSocket              socket;
    private InetAddress                 address;
    private int                         port;
    
    private static final byte[] multicast = {(byte)255,(byte)255,
                                             (byte)255,(byte)255};
    
    /**
     * Explicit Value Constructor
     *
     * @param port   The UDP port to broadcast to
     */
    public UDPMetronomeBroadcaster(int port) throws IOException
    {
       buffer    = new byte[4];       
       socket    = new DatagramSocket();       

       address   = InetAddress.getByAddress(multicast);       
       this.port = port;       
    }
    
    /*
     * Handle a Metronome tick
     * (required by MetronomeListener)
     *
     * @param millis   The number of milliseconds
     */
    public void handleTick(int millis)
    {
       DatagramPacket     packet;
       
       try
        {
           // Convert the int to a byte array
           fillBuffer(millis);           
           
           // Construct a packet (that includes the address and port)
           packet = new DatagramPacket(buffer,  buffer.length,
                                       address, port);
           
           // Send the packet
           socket.send(packet);
        } 
        catch (IOException ioe) 
        {
           ioe.printStackTrace();
        }
    }
    
    /**
     * Fill the byte[] buffer
     *
     * @param vale   The value to convert
     */
    private void fillBuffer(int value) 
    {
       buffer[0] = (byte)(value >>> 24);
       buffer[1] = (byte)(value >>> 16);
       buffer[2] = (byte)(value >>> 8);
       buffer[3] = (byte)value;
    }
}
