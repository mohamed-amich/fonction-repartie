package com.serverpackage;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 1234;
        System.out.println("[Serveur] En attente de connexion sur le port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("[Serveur] Client connecté : " + clientSocket.getRemoteSocketAddress());

            
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            Operation op;
            while ((op = (Operation) in.readObject()) != null) {
                System.out.println("[Serveur] Opération reçue : " +
                        op.getOp1() + " " + op.getOperator() + " " + op.getOp2());

                double resultat = calculer(op);
                System.out.println("[Serveur] Résultat calculé : " + resultat);

                out.writeObject(resultat);
                out.flush();
            }

        } catch (EOFException e) {
            System.out.println("[Serveur] Le client s’est déconnecté.");
        } catch (IOException e) {
            System.err.println("[Erreur Serveur] " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("[Erreur] Classe Operation non trouvée : " + e.getMessage());
        }

        System.out.println("[Serveur] Fermeture du serveur.");
    }

    
    private static double calculer(Operation op) {
        switch (op.getOperator()) {
            case "+": return op.getOp1() + op.getOp2();
            case "-": return op.getOp1() - op.getOp2();
            case "*": return op.getOp1() * op.getOp2();
            case "/": return (op.getOp2() == 0) ? Double.NaN : op.getOp1() / op.getOp2();
            default: return Double.NaN;
        }
    }
}
