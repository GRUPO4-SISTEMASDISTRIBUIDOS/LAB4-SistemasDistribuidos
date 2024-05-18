import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CreditCardService extends Remote {
    String createCard(String holderName) throws RemoteException;
    double checkBalance(String cardNumber) throws RemoteException;
    boolean charge(String cardNumber, double amount) throws RemoteException;
    boolean payBill(String cardNumber, double amount) throws RemoteException;
}