package Projects.TCPFileServer;

import java.util.*;
import java.net.*;
import java.io.*;


public class ServerWithoutThreads {

    public static void main(String[] args) {
        String DIRECTORY_PATH = "./files/";

        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]))) {

            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String clientInput;
            while ((clientInput = in.readLine()) != null) {
                if (clientInput.equals("FILE LIST")) {
                    File dir = new File(DIRECTORY_PATH);
                    File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
                    if (files != null)
                        for (File file : files)
                            out.println(file.getName());
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
                clientSocket = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
