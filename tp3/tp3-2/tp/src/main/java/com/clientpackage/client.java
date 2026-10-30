package com.clientpackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.serverpackage.operation;

public class client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner sc = new Scanner(System.in)) {

            System.out.println("[Client] Connecté au serveur " + host + ":" + port);
            System.out.println("Entrez une opération ou 'bye' pour quitter :");

            while (true) {
                System.out.print("> ");
                String line = sc.nextLine();
                if (line.equalsIgnoreCase("bye")) break;

                String[] parts = line.trim().split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Format invalide ");
                    continue;
                }

                double op1 = Double.parseDouble(parts[0]);
                String operator = parts[1];
                double op2 = Double.parseDouble(parts[2]);

                operation operation = new operation(op1, operator, op2);
                out.writeObject(operation);
                out.flush();

               
                operation response = (operation) in.readObject();
                if (response.getError() != null)
                    System.out.println("Erreur: " + response.getError());
                else
                    System.out.println("Résultat = " + response.getResult());
            }

            System.out.println("[Client] Déconnexion...");
        } catch (Exception e) {
            System.err.println("[Erreur Client] " + e.getMessage());
        }
    }
}
