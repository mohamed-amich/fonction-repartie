package rmiClient;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import rmiService.IConversion; 

public class ConversionClient {

    public static void main(String[] args) {
        try {
            double montantAEu = 500;
            
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            IConversion stub = (IConversion) registry.lookup("SERVEUR_CONVERSION");

            double res = stub.convertirMontant(montantAEu);

            System.out.println(montantAEu + " euros = " + res + " dinars");
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution du Client RMI.");
            e.printStackTrace();
        }
    }
}