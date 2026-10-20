package rmiService;
import java.rmi.Remote;
import java.rmi.RemoteException;
import metier.compte;

public interface IBanque extends Remote {
    String creerCompte(compte c) throws RemoteException;
    String getInfoCompte(int code) throws RemoteException;
}
