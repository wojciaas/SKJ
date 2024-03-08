package ServerUDP;
import java.util.*;
import java.net.*;
import java.io.*;
import util.SocketFactory;

class Main
{
    public static void main(String[] args) throws IOException
    {
        DatagramSocket socket = SocketFactory.buildSocket();

        // your code here
        DatagramPacket packet = new DatagramPacket(new byte[20], 0, 20);
        socket.receive(packet);
        System.out.println(new String(packet.getData(), 0, 20));
    }
}