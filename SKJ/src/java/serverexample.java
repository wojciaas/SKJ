package java;

import java.net.*;
import java.io.*;


class Server {

    public static void main(String args[]){
        int port = 4444;
        System.out.println("Rozpoczynam pracę.");

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Utworzyłem swoje gniazdo. Oczekuję na klientów.");

            while (true) {
                // W nieskończonej pętli oczekujemy na klientów.
                try(Socket clientSocket = serverSocket.accept()){
                    System.out.println("Klient się podłączył!");
                    // Gdy klient się podłączy otrzymujemy obiekt Socket reprezentujący
                    // połączenie z tym klientem.
                    // Możemy teraz utworzyć wątek dla tego klienta i odebrać w nim
                    // wiadomość od klienta a następnie wysłać mu odpowiedź
                    ClientHandler clientThread = new ClientHandler(clientSocket);
                    clientThread.run();
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket){
        this.clientSocket = socket;
    }

    @Override
    public void run(){
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Klient wysyła nam wiadomość pierwszy więc ją odbieramy.
            String clientMessage = in.readLine();
            System.out.println("Klient wysłał: " + clientMessage);
            // Po odebraniu wiadomości odpowiadamy
            out.println("Witaj kliencie!");

            // Z racji, że nie jest to w żadnej pętli to wątek się zakończy.
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
