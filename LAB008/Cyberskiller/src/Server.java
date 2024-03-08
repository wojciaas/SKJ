import java.util.*;
import java.net.*;
import java.io.*;


public class Server {

    public static void main(String[] args) {
        try {
            byte[] data = new byte[1024];
            InputStream in = new DataInputStream(System.in);
            in.read(data, 0, 1024);
            List<byte[]> datagrams = new ArrayList<>();
            for(int i = 0; i < 103; i++) {
                byte[] tmp = new byte[12];
                tmp[0] = (byte) i;
                if(i == 102) tmp[1] = (byte) 4;
                else tmp[1] = (byte) 10;
                for (int j = 0; j < (int)tmp[1]; j++) {
                    tmp[2 + j] = data[i * 10 + j];
                }
                datagrams.add(tmp);
            }
            DatagramPacket packet = new DatagramPacket(new byte[12], 12);
            DatagramSocket socket = new DatagramSocket();
            for (int i = 0; i < datagrams.size(); i++) {
                socket.receive(packet);
                socket.connect(packet.getAddress(), Integer.parseInt(args[0]));
                packet.setData(datagrams.get(i));
                for (int j = 0; j < 10; j++) {
                    socket.send(packet.getData());
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}