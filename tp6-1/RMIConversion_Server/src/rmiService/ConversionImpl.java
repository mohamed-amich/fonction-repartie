package rmiService;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class ConversionImpl extends UnicastRemoteObject implements IConversion {

    private static final double TAUX_EURO_DINAR = 3.4; 

    public ConversionImpl() throws RemoteException {
        super();
    }

    @Override
    public double convertirMontant(double mt) throws RemoteException {

        return mt * TAUX_EURO_DINAR;
    }
}