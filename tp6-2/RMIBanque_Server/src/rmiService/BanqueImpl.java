package rmiService;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.HashMap;
import metier.compte;

public class BanqueImpl extends UnicastRemoteObject implements IBanque {
    private HashMap<Integer, compte> comptes;

    public BanqueImpl() throws RemoteException {
        super();
        comptes = new HashMap<>();
    }

    public String creerCompte(compte c) throws RemoteException {
        if (comptes.containsKey(c.getCode()))
            return "Le compte existe déjà";
        comptes.put(c.getCode(), c);
        return "Compte créé: " + c;
    }

    public String getInfoCompte(int code) throws RemoteException {
        compte c = comptes.get(code);
        return (c != null) ? c.toString() : "Compte non trouvé";
    }
}
