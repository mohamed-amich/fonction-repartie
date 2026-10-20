package metier;
import java.io.Serializable;
import java.util.Date;

public class compte implements Serializable {
    private int code;
    private double solde;
    private Date dateCreation;

    public compte(int code, double solde) {
        this.code = code;
        this.solde = solde;
        this.dateCreation = new Date();
    }

    public int getCode() { return code; }
    public double getSolde() { return solde; }
    public Date getDateCreation() { return dateCreation; }

    public String toString() {
        return "Compte [code=" + code + ", solde=" + solde + ", dateCreation=" + dateCreation + "]";
    }
}
