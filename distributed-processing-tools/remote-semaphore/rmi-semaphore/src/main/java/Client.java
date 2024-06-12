import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {

            Registry registry = LocateRegistry.getRegistry(args[0], Server.PORT);
            CustomSemaphore semaphore = (CustomSemaphore) registry.lookup(Server.NAME);

            semaphore.acquire(permitsToAcquire);
            Thread.sleep(4000);
            semaphore.release(permitsToAcquire);
        } catch (Exception e) {
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

    public static void handleCommand(String[] args) throws Exception {
        if (args.length != 2 || args.length != 3) {
            throw new Exception("Wrong argument count, usage: java Client <server_address> <'acquire' or 'release'> <(optional) permits>");
        } else if (args.length == 2) {
            switch (args[1]) {
                case "acquire":
                    semaphore.acquire();
                case "release":
                    semaphore.release();
                default:
                    throw new Exception("Illegal operation. Options: 'acquire' or 'release'");
            }
        } else {
            int permitsToAcquire = Integer.parseInt(args[2]);
            switch (args[1]) {
                case "acquire":
                    semaphore.acquire();
                case "release":
                    semaphore.release();
                default:
                    throw new Exception("Illegal operation. Options: 'acquire' or 'release'");
            }
        }

    }
}
