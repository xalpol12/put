import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static final String NAME = "Semaphore";
    public static final int PORT = 1099;

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new Exception("Wrong argument count, usage: java Server <initial_permits_count>");
            }
            int permits = Integer.parseInt(args[0]);
            CustomSemaphore semaphore = new CustomSemaphoreImpl(permits);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind(NAME, semaphore);
            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e);
            e.printStackTrace();
        }

    }
}
