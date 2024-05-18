
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class CreditCardServiceImpl extends UnicastRemoteObject implements CreditCardService {
    private Map<String, Double> cardDatabase;

    public CreditCardServiceImpl() throws RemoteException {
        super();
        cardDatabase = new HashMap<>();
    }

    @Override
    public String createCard(String holderName) throws RemoteException {
        String cardNumber = "CARD" + (cardDatabase.size() + 1);
        cardDatabase.put(cardNumber, 0.0);
        return cardNumber;
    }

    @Override
    public double checkBalance(String cardNumber) throws RemoteException {
        return cardDatabase.getOrDefault(cardNumber, -1.0);
    }

    @Override
    public boolean charge(String cardNumber, double amount) throws RemoteException {
        if (cardDatabase.containsKey(cardNumber)) {
            double balance = cardDatabase.get(cardNumber);
            cardDatabase.put(cardNumber, balance + amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean payBill(String cardNumber, double amount) throws RemoteException {
        if (cardDatabase.containsKey(cardNumber)) {
            double balance = cardDatabase.get(cardNumber);
            if (balance >= amount) {
                cardDatabase.put(cardNumber, balance - amount);
                return true;
            }
        }
        return false;
    }
}
