package Projects.TCPSecureFileServer;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.*;
import javax.net.ssl.*;
import java.io.*;


public class Server {

    public static void main(String[] args) {
        String DIRECTORY_PATH = "./files/";

        try {
            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(Integer.parseInt(args[0]));

            while (true) {
                SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
                PrintWriter out = new PrintWriter(sslSocket.getOutputStream(), true);
                String clientInput;
                while ((clientInput = in.readLine()) != null) {
                    if (clientInput.equals("FILE LIST")) {
                        File dir = new File(DIRECTORY_PATH);
                        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
                        String answer = "";
                        for (int i = 0; i < files.length; i++){
                            answer += files[i].getName();
                            if(i+1 < files.length)
                                answer += "\n";
                        }
                        out.println(answer);
                    } else if (clientInput.startsWith("GET ")) {
                        String fileName = clientInput.substring(4);
                        File file = new File(DIRECTORY_PATH + fileName);
                        if (file.exists()) {
                            try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                                String line;
                                while ((line = fileReader.readLine()) != null)
                                    out.println(line);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else
                            out.println("NO SUCH FILE");
                    } else
                        out.println("NO SUCH COMMAND");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}