package corbaClient;

import corbaBanque.*;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import java.util.Properties;
import java.util.Scanner;

public class BanqueClient {

    public static void main(String[] args) {
        try {
            // 🔹 Configuration de l'OR
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");
            props.put("org.omg.CORBA.ORBInitialPort", "900");

            ORB orb = ORB.init(args, props);

            // 🔹 Connexion au service de noms
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // 🔹 Recherche du service distant
            String name = "BanqueService";
            IBanqueRemote banque = IBanqueRemoteHelper.narrow(ncRef.resolve_str(name));

            System.out.println("✅ Connexion réussie au service Banque CORBA !");
            System.out.println("--------------------------------------------");

            Scanner sc = new Scanner(System.in);
            int choix;

            do {
                System.out.println("\n=== MENU BANQUE ===");
                System.out.println("1. Créer un compte");
                System.out.println("2. Verser");
                System.out.println("3. Retirer");
                System.out.println("4. Consulter un compte");
                System.out.println("5. Afficher tous les comptes");
                System.out.println("6. Conversion Euro -> DT");
                System.out.println("0. Quitter");
                System.out.print("👉 Votre choix : ");
                choix = sc.nextInt();

                switch (choix) {
                    case 1:
                        System.out.print("Code du compte : ");
                        int code = sc.nextInt(); // ⚠ changé long → int
                        System.out.print("Solde initial : ");
                        float solde = sc.nextFloat();
                        Compte c = new Compte(code, solde);
                        banque.creerCompte(c);
                        break;

                    case 2:
                        System.out.print("Code du compte : ");
                        code = sc.nextInt();
                        System.out.print("Montant à verser : ");
                        float mt = sc.nextFloat();
                        banque.verser(mt, code);
                        break;

                    case 3:
                        System.out.print("Code du compte : ");
                        code = sc.nextInt();
                        System.out.print("Montant à retirer : ");
                        mt = sc.nextFloat();
                        banque.retirer(mt, code);
                        break;

                    case 4:
                        System.out.print("Code du compte : ");
                        code = sc.nextInt();
                        Compte cp = banque.getCompte(code);
                        System.out.println("➡  Compte " + cp.code + " | Solde : " + cp.solde);
                        break;

                    case 5:
                        Compte[] comptes = banque.getComptes();
                        System.out.println("📋 Liste des comptes :");
                        for (Compte cpt : comptes) {
                            System.out.println(" - Compte " + cpt.code + " | Solde : " + cpt.solde);
                        }
                        break;

                    case 6:
                        System.out.print("Montant en Euro : ");
                        mt = sc.nextFloat();
                        double res = banque.conversion(mt);
                        System.out.println("💱 " + mt + " € = " + res + " DT");
                        break;

                    case 0:
                        System.out.println("👋 Fin du programme client.");
                        break;

                    default:
                        System.out.println("⚠ Choix invalide !");
                }

            } while (choix != 0);

        } catch (Exception e) {
            System.err.println("Erreur côté client : " + e);
            e.printStackTrace();
        }
    }
}