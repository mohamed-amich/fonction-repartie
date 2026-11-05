package com.serverpackage;

import java.net.*;

public class serveur {
    public static void main(String[] args) {
        try {
           
            DatagramSocket socket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress(1234);
            socket.bind(address);

            System.out.println("[Serveur UDP] En attente de messages sur le port 1234...");

            byte[] buffer = new byte[1024]; 

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); 

                String message = new String(packet.getData(), 0, packet.getLength());

                
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                
                System.out.println("[Reçu de " + clientAddress.getHostAddress() + ":" + clientPort + "] → " + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
