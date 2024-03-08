package java;

import java.net.*;
import java.io.*;

class Client {

    public static void main(String args[]){

        int port = 4444;
        // W try tworze trzy obiekty zaczynając od socketa.
        // Próba utworzenia socketa nie powiedzie się jeżeli nie będzie działał serwer.
        // Natomiast bez obiektu clientSocket nie utworzymy kolejnych.
        try(Socket clientSocket = new Socket("127.0.0.1", port);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())))
        {
            System.out.println("Podłączyłem się do serwera!");
            // Wysyłamy do serwera wiadomość hello.
            out.println("hello");

            // Po wysłaniu wiadomości oczekujemy na odpowiedź
            // To jednak zależy od tego jak skonstruowany jest
            // protokół komunikacyjny pomiędzy klientem i serwerem.
            // Możemy spotkać sytuacje w której klient wyśle jedną wiadomość
            // a następnie serwer odpowie jedną lub wieloma wiadomościami itd.

            String message = in.readLine();
            System.out.println("Serwer odpowiedział: " + message);

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
