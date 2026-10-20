package serverPackage;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Je suis un serveur en attente de la connexion d'un client...");

            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Un client est connecté ! Adresse : " + clientSocket.getInetAddress());

                try (
                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                ) {
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
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la connexion d'un client : " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ouverture du serveur : " + e.getMessage());
        }
    }
}
