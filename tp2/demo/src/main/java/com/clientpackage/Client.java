package com.clientpackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import com.serverpackage.Operation;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";  // ou 127.0.0.1
        int port = 1234;

        System.out.println("Je suis un client pas encore connecté…");

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {

            System.out.println("je suis un client connecté");
            System.out.println("Connected to server: " + socket.getRemoteSocketAddress());

            while (true) {
                System.out.print("Entrez une opération (les composantes séparées par un espace) ou 'exit' pour quitter : ");
                String input = sc.nextLine();

                if (input.equalsIgnoreCase("exit")) break;

                String[] parts = input.trim().split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Format incorrect. Utilisez : nombre opérateur nombre");
                    continue;
                }

                try {
                    double a = Double.parseDouble(parts[0]);
                    String op = parts[1];
                    double b = Double.parseDouble(parts[2]);

                    Operation operation = new Operation(a, op, b);

                    
                    out.writeObject(operation);
                    out.flush();

                   
                    Object response = in.readObject();
                    if (response instanceof Double) {
                        System.out.println("Résultat : " + response);
                    } else {
                        System.out.println("Réponse inattendue : " + response);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer des nombres valides !");
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + host + " - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error when connecting to " + host + ":" + port + " - " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur de lecture de la réponse : " + e.getMessage());
        }

        System.out.println("Client: socket fermée, fin du programme.");
    }
}
