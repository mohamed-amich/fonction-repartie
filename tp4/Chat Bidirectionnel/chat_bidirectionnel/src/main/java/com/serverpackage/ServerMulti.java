package com.serverpackage;

import java.net.*;
import java.util.*;

public class ServerMulti {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(1234);
            System.out.println("[Serveur] En attente de messages sur le port 1234...");

            byte[] buffer = new byte[1024];
            Set<SocketAddress> clients = new HashSet<>();

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                SocketAddress senderAddress = packet.getSocketAddress();

               
                if (!clients.contains(senderAddress)) {
                    clients.add(senderAddress);
                    System.out.println("[Serveur] Nouveau client connecté : " + senderAddress);
                }

                System.out.println("[Message reçu] " + message);

                for (SocketAddress client : clients) {
                    if (!client.equals(senderAddress)) {
                        DatagramPacket toSend = new DatagramPacket(
                                message.getBytes(),
                                message.length(),
                                client
                        );
                        socket.send(toSend);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
