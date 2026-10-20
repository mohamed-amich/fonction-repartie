package rmiServer;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmiService.ConversionImpl;

public class ConversionServer {

    public static void main(String[] args) {
        try {
            ConversionImpl conv = new ConversionImpl();

        
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            registry.rebind("SERVEUR_CONVERSION", conv);

            System.out.println("Serveur RMI lancé et objet 'SERVEUR_CONVERSION' publié !");

        } catch (Exception e) {
            System.err.println("Erreur de lancement du Serveur RMI: " + e.getMessage());
            e.printStackTrace();
        }
    }
}