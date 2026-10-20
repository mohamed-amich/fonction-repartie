package clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Je suis un client pas encore connecté...");

            try (Socket socket = new Socket(InetAddress.getByName(host), port)) {
                System.out.println("Je suis un client connecté !");
                try (
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        DataInputStream dis = new DataInputStream(socket.getInputStream());) {
                    while (true) {
                        System.out.println("\n=== Service de calculatrice ===");
                        System.out.println("1. Addition (+)");
                        System.out.println("2. Soustraction (-)");
                        System.out.println("3. Multiplication (*)");
                        System.out.println("4. Division (/)");
                        System.out.println("0. Quitter");
                        System.out.print("Choix : ");
                        int choix = scanner.nextInt();
                        scanner.nextLine();
                        if (choix == 0)
                            break;
                        String operation = "";
                        switch (choix) {
                            case 1:
                                operation = "+";
                                break;
                            case 2:
                                operation = "-";
                                break;
                            case 3:
                                operation = "*";
                                break;
                            case 4:
                                operation = "/";
                                break;
                            default:
                                System.out.println("Choix invalide.");
                                continue;
                        }
                        System.out.print("Donner le premier nombre : ");
                        int number1 = scanner.nextInt();
                        System.out.print("Donner le second nombre : ");
                        int number2 = scanner.nextInt();

                        dos.writeUTF(operation);
                        dos.writeInt(number1);
                        dos.writeInt(number2);
                        dos.flush();

                        String result = dis.readUTF();
                        System.out.println("Résultat reçu : " + result);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la connexion : " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Erreur côté client : " + e.getMessage());
        }
    }
}
