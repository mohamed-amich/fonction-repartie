package client3;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client3 {
    public static void main(String[] args) {
        String ipServeur = "192.168.56.1";
        int port = 5555;
        Socket socket = null;

        try {
            InetAddress adresseServeur = InetAddress.getByName(ipServeur);
            socket = new Socket(adresseServeur, port);
            System.out.println("Connecté au serveur : " + ipServeur + ":" + port);

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

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
                if (choix == 0) break;

                String operation;
                switch (choix) {
                    case 1: operation = "+"; break;
                    case 2: operation = "-"; break;
                    case 3: operation = "*"; break;
                    case 4: operation = "/"; break;
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

            scanner.close();
            dis.close();
            dos.close();

        } catch (IOException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Erreur fermeture socket client : " + e.getMessage());
                }
            }
        }
    }
}
