package Projects.TCPSecureChatServer.Try02;

import java.util.*;
import java.net.*;
import javax.net.*;
import javax.net.ssl.*;
import java.io.*;

public class ChatServer {

    private static final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try (SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(Integer.parseInt(args[0]))) {
            while (true) {
                ClientHandler client = new ClientHandler((SSLSocket) serverSocket.accept());
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            if (clients.size() > 1) {
                for (ClientHandler client : clients)
                    if (client != sender)
                        client.sendMessage(message);
                System.out.println(message);
            } else
                System.out.println(message);
        }
    }

    private static class ClientHandler extends Thread {

        private final SSLSocket clientSocket;
        private final PrintWriter out;

        public ClientHandler(SSLSocket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null)
                    ChatServer.broadcast(line, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}