package LAB003.Client_slowed_stream;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int port = sc.nextInt();
        byte[] buf = new byte[20];
        Socket socket = new Socket("127.0.0.1", port);

    }
}
