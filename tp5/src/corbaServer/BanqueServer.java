package corbaServer;

import corbaBanque.*;
import sevice.BanqueImpl;
import org.omg.CORBA.*;
import org.omg.CosNaming.*;
import org.omg.PortableServer.*;

import java.util.Properties;

public class BanqueServer {

    public static void main(String[] args) {
        try {
            // 🔹 Chargement des propriétés du service de noms
            Properties props = new Properties();
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");
            props.put("org.omg.CORBA.ORBInitialPort", "900");

            // 🔹 Initialisation de l'ORB
            ORB orb = ORB.init(args, props);

            // 🔹 Référence vers le RootPOA et activation
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootPOA.the_POAManager().activate();

            // 🔹 Création du servant (implémentation)
            BanqueImpl banqueImpl = new BanqueImpl();

            // 🔹 Récupération de la référence CORBA de l'objet servant
            org.omg.CORBA.Object ref = rootPOA.servant_to_reference(banqueImpl);
            IBanqueRemote href = IBanqueRemoteHelper.narrow(ref);

            // 🔹 Connexion au Naming Service (tnameserv)
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // 🔹 Enregistrement de l'objet dans le Naming Service
            String name = "BanqueService";
            NameComponent path[] = ncRef.to_name(name);
            ncRef.rebind(path, href);

            System.out.println("🏦 BanqueServer prêt et en attente des requêtes...");

            // 🔹 Lancer l'ORB (boucle infinie)
            orb.run();

        } catch (Exception e) {
            System.err.println("Erreur serveur : " + e);
            e.printStackTrace();
        }
    }
}