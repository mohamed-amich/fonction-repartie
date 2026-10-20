package com.clientpackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8080;

        System.out.println("[Client] Tentative de connexion à " + host + ":" + port + "...");

        try (
            Socket socket = new Socket(host, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in)
        ) {
            System.out.println("[Client] Connecté au serveur : " + socket.getRemoteSocketAddress());

            
            String welcome = in.readLine();
            if (welcome != null) {
                System.out.println("[Serveur] " + welcome);
            }

            
            while (true) {
                System.out.print("Vous → ");
                String message = sc.nextLine();
                out.println(message); 

                String reponse = in.readLine();
                if (reponse == null) break; 
                System.out.println(reponse);

                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("[Client] Déconnexion...");
                    break;
                }
            }

        } catch (UnknownHostException e) {
            System.err.println("[Erreur] Hôte inconnu : " + host);
        } catch (IOException e) {
            System.err.println("[Erreur I/O] " + e.getMessage());
        }

        System.out.println("[Client] Fin du programme.");
    }
}
