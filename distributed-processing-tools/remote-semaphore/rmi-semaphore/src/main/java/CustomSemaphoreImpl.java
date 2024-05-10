import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CustomSemaphoreImpl extends UnicastRemoteObject implements CustomSemaphore {
    private int permits;

    public CustomSemaphoreImpl(int initialPermits) throws RemoteException {
        super();
        permits = initialPermits;
    }

    @Override
    public synchronized void acquire(int permitsToAcquire) throws RemoteException {
        while (permits < permitsToAcquire) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        permits -= permitsToAcquire;
        System.out.println(Thread.currentThread().getName() + " acquired " + permitsToAcquire + " permits.");

    }

    @Override
    public synchronized void release(int permitsToRelease) throws RemoteException {
        permits += permitsToRelease;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " released " + permitsToRelease + " permits.");
    }
}
