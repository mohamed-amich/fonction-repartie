package com.serverpackage;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class server {
    private static final int port = 12345;
   
    private static AtomicInteger totalOperations = new AtomicInteger(0);

    public static void main(String[] args) {
        System.out.println("[Serveur] Démarrage sur le port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("[Serveur] Nouveau client : " + clientSocket.getRemoteSocketAddress());
                
               
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("[Erreur Serveur] " + e.getMessage());
        }
    }

   
    static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ) {
                System.out.println("[Serveur] Client connecté : " + socket.getRemoteSocketAddress());

                Object obj;
                while ((obj = in.readObject()) != null) {
                    if (obj instanceof operation) {
                        operation op = (operation) obj;
                        compute(op);

                        
                        int newCount = totalOperations.incrementAndGet();

                      
                        System.out.println("[Serveur] " + op + " | Total opérations = " + newCount);

                      
                        out.writeObject(op);
                        out.flush();
                    }
                }
            } catch (EOFException eof) {
                System.out.println("[Serveur] Client déconnecté : " + socket.getRemoteSocketAddress());
            } catch (Exception e) {
                System.err.println("[Erreur] " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }

        
        private void compute(operation op) {
            double a = op.getOp1();
            double b = op.getOp2();
            String operator = op.getOperator();

            try {
                switch (operator) {
                    case "+": op.setResult(a + b); break;
                    case "-": op.setResult(a - b); break;
                    case "*": op.setResult(a * b); break;
                    case "/":
                        if (b == 0) {
                            op.setError("Division par zéro");
                            op.setResult(null);
                        } else {
                            op.setResult(a / b);
                        }
                        break;
                    default:
                        op.setError("Opérateur inconnu : " + operator);
                        op.setResult(null);
                }
            } catch (Exception e) {
                op.setError("Erreur: " + e.getMessage());
                op.setResult(null);
            }
        }
    }
}
