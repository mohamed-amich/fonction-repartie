package rmiServer;
import rmiService.BanqueImpl;
import rmiService.IBanque;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class BanqueServer {
    public static void main(String[] args) {
        try {
            // Configuration JNDI
            Properties p = new Properties();
            p.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
            p.setProperty(Context.PROVIDER_URL, "rmi://localhost:1099");
            
            IBanque banque = new BanqueImpl();

            Context ctx = new InitialContext(p);
            ctx.bind("Banque", banque);

            System.out.println("Serveur RMI prêt avec JNDI !");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
