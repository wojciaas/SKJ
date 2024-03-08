import java.util.*;
import java.net.*;
import java.io.*;

class Main
{
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int port = sc.nextInt();
        Socket socket = new Socket("127.0.0.1", port);
        byte[] buf = new byte[20];
        socket.getInputStream().read(buf, 0, 20);
        System.out.println(new String(buf));
    }
}