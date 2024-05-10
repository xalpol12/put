import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new Exception("Wrong argument count, usage: java Client <server_address> <number_of_permits>");
            }

            Registry registry = LocateRegistry.getRegistry(args[0], Server.PORT);
            CustomSemaphore semaphore = (CustomSemaphore) registry.lookup(Server.NAME);

            int permitsToAcquire = Integer.parseInt(args[1]);
            semaphore.acquire(permitsToAcquire);
            Thread.sleep(4000);
            semaphore.release(permitsToAcquire);
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }
}
