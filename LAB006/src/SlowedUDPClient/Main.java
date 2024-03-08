package SlowedUDPClient;
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.stream.Stream;

class Main
{
    public static void main(String[] args)
    {
        try(DatagramSocket socket = new DatagramSocket()) {
            int port = new Scanner(System.in).nextInt();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramPacket packet = new DatagramPacket(new byte[20], 20, address, port);
            socket.send(packet);
            StringBuffer result = new StringBuffer();
            socket.receive(packet);
            while ()
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}