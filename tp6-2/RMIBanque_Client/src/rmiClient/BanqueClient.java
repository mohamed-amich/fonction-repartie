package rmiClient;

import rmiService.IBanque;
import metier.compte;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;
import java.util.Scanner;

public class BanqueClient {
    public static void main(String[] args) {
        try {
            Properties p = new Properties();
            p.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.rmi.registry.RegistryContextFactory");
            p.setProperty(Context.PROVIDER_URL, "rmi://localhost:1099");

            Context ctx = new InitialContext(p);
            IBanque banque = (IBanque) ctx.lookup("Banque");

            Scanner scanner = new Scanner(System.in);
            int choix;

            do {
                System.out.println("\n=== Menu Banque ===");
                System.out.println("1. Créer un compte");
                System.out.println("2. Afficher les infos d'un compte");
                System.out.println("3. Quitter");
                System.out.print("Votre choix : ");
                choix = scanner.nextInt();

                switch (choix) {
                    case 1:
                        System.out.print("Code du compte: ");
                        int code = scanner.nextInt();
                        System.out.print("Solde initial: ");
                        double solde = scanner.nextDouble();
                        compte c = new compte(code, solde);
                        String result = banque.creerCompte(c);
                        System.out.println(result);
                        break;
                    case 2:
                        System.out.print("Code du compte à afficher: ");
                        int codeRecherche = scanner.nextInt();
                        String info = banque.getInfoCompte(codeRecherche);
                        System.out.println(info);
                        break;
                    case 3:
                        System.out.println("Fin du programme.");
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } while (choix != 3);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
