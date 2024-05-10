import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static final String NAME = "Semaphore";
    public static final int PORT = 1099;
    private static final int INITIAL_PERMITS = 10;

    public static void main(String[] args) {
        try {
            CustomSemaphore semaphore = new CustomSemaphoreImpl(INITIAL_PERMITS);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind(NAME, semaphore);
            System.out.println("Server is running...");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }

    }
}
