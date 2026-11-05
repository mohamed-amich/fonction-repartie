package com.clientpackage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            System.out.print("Entrez votre nom d'utilisateur : ");
            String username = sc.nextLine();

            InetAddress serverAddress = InetAddress.getByName("localhost");
            int serverPort = 1234;

            System.out.println("Vous pouvez maintenant envoyer des messages au serveur :");

            while (true) {
                String message = sc.nextLine();
                String fullMessage = "[" + username + "] : " + message;

                byte[] buffer = fullMessage.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, serverPort);
                socket.send(packet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
