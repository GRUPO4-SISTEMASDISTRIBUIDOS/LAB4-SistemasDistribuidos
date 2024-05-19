import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class CreditCardClient {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            // Conectar al servicio RMI
            CreditCardService service = (CreditCardService) Naming.lookup("rmi://localhost/CreditCardService");

            int option = 1;

            while (option > 0) {
                System.out.println("\n------------------------------- ");
                System.out.println("Servicio de Tarjeta de Credito: ");
                System.out.println("------------------------------- ");
                System.out.println("Opcion 1: Crear Tarjeta ");
                System.out.println("Opcion 2: Checkear el Balance de la tarjeta ");
                System.out.println("Opcion 3: Cargar la tarjeta ");
                System.out.println("Opcion 4: Pagar una deuda con la tarjeta ");
                System.out.println("Cualquier otra Opcion : Salir");

                System.out.print("\nIngrese una opcion: ");

                option = selecOption(option, service);

                // Esperar 1 segundo entre opciones
                Thread.sleep(1000);
            }

            sc.close(); // Cerrar el escáner al final
        } catch (MalformedURLException | NotBoundException | RemoteException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int selecOption(int option, CreditCardService service) {
        option = sc.nextInt();

        try {
            switch (option) {
                case 1:
                    System.out.print("Ingrese el nombre del titular de la tarjeta: ");
                    String holderName = sc.next();
                    String cardNumber = service.createCard(holderName);
                    System.out.println("Tarjeta creada exitosamente. Numero de tarjeta: " + cardNumber);
                    break;

                case 2:
                    System.out.print("Ingrese el numero de la tarjeta: ");
                    cardNumber = sc.next();
                    double balance = service.checkBalance(cardNumber);
                    if (balance >= 0) {
                        System.out.println("El balance de la tarjeta es: " + balance);
                    } else {
                        System.out.println("Tarjeta no encontrada.");
                    }
                    break;

                case 3:
                    System.out.print("Ingrese el numero de la tarjeta: ");
                    cardNumber = sc.next();
                    System.out.print("Ingrese el monto a cargar: ");
                    double amount = sc.nextDouble();
                    if (service.charge(cardNumber, amount)) {
                        System.out.println("Monto cargado exitosamente.");
                    } else {
                        System.out.println("Error al cargar el monto. Verifique el numero de la tarjeta.");
                    }
                    break;

                case 4:
                    System.out.print("Ingrese el numero de la tarjeta: ");
                    cardNumber = sc.next();
                    System.out.print("Ingrese el monto a pagar: ");
                    amount = sc.nextDouble();
                    if (service.payBill(cardNumber, amount)) {
                        System.out.println("Deuda pagada exitosamente.");
                    } else {
                        System.out.println("Error al pagar la deuda. Verifique el numero de la tarjeta y el balance.");
                    }
                    break;

                default:
                    System.out.println("Saliendo del programa...");
                    option = 0;
                    break;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        return option;
    }
}
