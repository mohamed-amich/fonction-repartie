package com.serverpackage;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class server {

    private static final int port = 8080;
    private static AtomicInteger clientCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        System.out.println("[Serveur] Démarrage sur le port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                
                Socket clientSocket = serverSocket.accept();

               
                int clientNum = clientCounter.incrementAndGet();

                
                System.out.println("[Serveur] Client n°" + clientNum + " connecté depuis " +
                        clientSocket.getRemoteSocketAddress());

                
                new Thread(new ClientHandler(clientSocket, clientNum)).start();
            }
        } catch (IOException e) {
            System.err.println("[Erreur Serveur] " + e.getMessage());
        }
    }
}


class ClientHandler implements Runnable {
    private Socket socket;
    private int clientNum;

    public ClientHandler(Socket socket, int clientNum) {
        this.socket = socket;
        this.clientNum = clientNum;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
           
            out.println("Bienvenue, vous êtes le client n°" + clientNum);

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("[Client " + clientNum + "] : " + message);

               
                out.println("Serveur → Reçu : " + message);

                
                if (message.equalsIgnoreCase("bye")) {
                    out.println("Déconnexion du client n°" + clientNum);
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("[Erreur] Client n°" + clientNum + " : " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
            System.out.println("[Serveur] Client n°" + clientNum + " déconnecté.");
        }
    }
}
