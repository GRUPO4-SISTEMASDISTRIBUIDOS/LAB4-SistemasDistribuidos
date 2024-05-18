import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class CreditCardServer {
    public static void main(String[] args) {
        try {
            // Start RMI Registry
            LocateRegistry.createRegistry(1099);
            
            // Create an instance of the service implementation
            CreditCardServiceImpl service = new CreditCardServiceImpl();
            
            // Bind the service to a name in the RMI registry
            Naming.rebind("CreditCardService", service);
            
            System.out.println("Credit Card Service is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
