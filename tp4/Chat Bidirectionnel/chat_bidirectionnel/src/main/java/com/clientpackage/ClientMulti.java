package com.clientpackage;



import java.net.*;
import java.util.Scanner;

public class ClientMulti {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 1234;

            Scanner sc = new Scanner(System.in);
            System.out.print("Entrez votre nom d'utilisateur : ");
            String username = sc.nextLine();

            
            Thread receiveThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                        socket.receive(packet);
                        String msg = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("\n" + msg);
                        System.out.print("> "); 
                    }
                } catch (Exception e) {
                    System.out.println("Erreur réception : " + e.getMessage());
                }
            });
            receiveThread.start();

      
            System.out.println("Vous pouvez maintenant discuter :");
            while (true) {
                System.out.print("> ");
                String message = sc.nextLine();
                String fullMessage = "[" + username + "] : " + message;
                byte[] data = fullMessage.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
                socket.send(packet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

