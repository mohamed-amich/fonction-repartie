package sevice;

import corbaBanque.*;
import java.util.*;

public class BanqueImpl extends IBanqueRemotePOA {

    private List<Compte> comptes = new ArrayList<>();

    @Override
    public void creerCompte(Compte cpte) {
        comptes.add(cpte);
        System.out.println("Compte créé: code=" + cpte.code + ", solde=" + cpte.solde);
    }

    @Override
    public void verser(float mt, int code) {
        for (Compte c : comptes) {
            if (c.code == code) {
                c.solde += mt;
                System.out.println("Versement: +" + mt + " au compte " + code);
                return;
            }
        }
        System.out.println("Compte non trouvé: " + code);
    }

    @Override
    public void retirer(float mt, int code) {
        for (Compte c : comptes) {
            if (c.code == code) {
                c.solde -= mt;
                System.out.println("Retrait: -" + mt + " du compte " + code);
                return;
            }
        }
        System.out.println("Compte non trouvé: " + code);
    }

    @Override
    public Compte getCompte(int code) {
        for (Compte c : comptes) {
            if (c.code == code) {
                return c;
            }
        }
        System.out.println("Compte non trouvé: " + code);
        return new Compte(code, 0);
    }

    @Override
    public Compte[] getComptes() {
        return comptes.toArray(new Compte[0]);
    }

    @Override
    public double conversion(float mt) {
        double taux = 3.4; // 1 euro = 3.4 dinars
        return mt * taux;
    }
}