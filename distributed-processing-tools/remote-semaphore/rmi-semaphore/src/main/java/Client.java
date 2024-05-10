import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", Server.PORT);
            CustomSemaphore semaphore = (CustomSemaphore) registry.lookup(Server.NAME);

            int permitsToAcquire = 3;
            semaphore.acquire(permitsToAcquire);
            Thread.sleep(2000);
            semaphore.release(permitsToAcquire);
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}
