package rmiService;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IConversion extends Remote {
    /**
     * Convertit un montant donné en Euros vers le Dinar (DT).
     * @param mt Montant en Euros
     * @return Montant converti en Dinars
     * @throws RemoteException
     */
    double convertirMontant(double mt) throws RemoteException;
}