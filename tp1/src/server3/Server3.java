package server3;

import java.net.*;
import java.io.*;

public class Server3 {
    public static void main(String[] args) {
        int port = 5555;
        ServerSocket serveurSocket = null;
        try {
            InetAddress ipLocale = InetAddress.getByName("192.168.56.1");
            serveurSocket = new ServerSocket();
            InetSocketAddress adresse = new InetSocketAddress(ipLocale, port);
            serveurSocket.bind(adresse);
            System.out.println("Serveur démarré sur " + ipLocale.getHostAddress() + ":" + port);

            Socket socket = serveurSocket.accept();
            System.out.println("Connexion reçue de : " + socket.getInetAddress());

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            while (true) {
                try {
                    String operation = dis.readUTF();
                    int number1 = dis.readInt();
                    int number2 = dis.readInt();

                    String resultat;
                    switch (operation) {
                        case "+":
                            resultat = Integer.toString(number1 + number2);
                            break;
                        case "-":
                            resultat = Integer.toString(number1 - number2);
                            break;
                        case "*":
                            resultat = Integer.toString(number1 * number2);
                            break;
                        case "/":
                            if (number2 != 0)
                                resultat = Double.toString((double) number1 / number2);
                            else
                                resultat = "Erreur : division par zéro";
                            break;
                        default:
                            resultat = "Erreur d'opération";
                    }
                    dos.writeUTF(resultat);
                    dos.flush();
                } catch (EOFException e) {
                    System.out.println("Le client s'est déconnecté.");
                    break;
                }
            }

            dis.close();
            dos.close();
            socket.close();

        } catch (IOException e) {
            System.err.println("Erreur réseau : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (serveurSocket != null) {
                try {
                    serveurSocket.close();
                } catch (IOException e) {
                    System.err.println("Erreur fermeture serveur : " + e.getMessage());
                }
            }
        }
    }
}
