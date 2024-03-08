package UDPClient;
import java.util.*;
import java.net.*;
import java.io.*;

class Main
{
    public static void main(String[] args)
    {
        try(DatagramSocket datagramSocket = new DatagramSocket()) {
            int port = new Scanner(System.in).nextInt();
            InetAddress address = InetAddress.getByName("127.0.0.1");
            byte[] buf = new byte[20];
            DatagramPacket packet = new DatagramPacket(buf, 20, address, port);
            datagramSocket.send(packet);
            datagramSocket.receive(packet);
            System.out.println(new String(packet.getData(), 0, packet.getLength()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
