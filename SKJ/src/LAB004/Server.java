package LAB004;

import java.util.*;
import java.net.*;
import java.io.*;


public class Server {

    public static void main(String[] args) {
        for (String port : args) {
            try {
                new Thread(new ServerTask(Integer.parseInt(port))).start();
            } catch(NumberFormatException e) {
                System.out.println("Incorrect port: " + port);
            }
        }
    }

    private static class ServerTask implements Runnable {

        private int port;

        public ServerTask(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try(ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(new ClientHandler(clientSocket)).start();
                }
            } catch(IOException e) {
                System.out.println("Failed connecting to server on port: " + port);
                e.printStackTrace();
            }
        }
    }

    private static class ClientHandler implements Runnable {

        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try(InputStream input = clientSocket.getInputStream()) {
                byte[] buf = new byte[20];
                int bytesRead = input.read(buf);
                String receivedData = new String(buf, 0, bytesRead);
                System.out.println(receivedData);
            } catch (IOException e) {
                System.out.println("Error receiving data from client.");
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
