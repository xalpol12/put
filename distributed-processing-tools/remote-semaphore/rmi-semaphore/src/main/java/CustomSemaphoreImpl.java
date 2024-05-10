import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CustomSemaphoreImpl extends UnicastRemoteObject implements CustomSemaphore {
    private int permits;

    public CustomSemaphoreImpl(int initialPermits) throws RemoteException {
        super();
        permits = initialPermits;
        System.out.println("Initialized semaphore with " + permits + " initial permits");
    }

    @Override
    public void acquire() throws RemoteException {
        while (permits < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RemoteException("Thread interrupted while waiting for permits", e);
            }
        }
        permits--;
        System.out.println(Thread.currentThread().getName() + " acquired 1 permit.");
    }

    @Override
    public synchronized void acquire(int permitsToAcquire) throws RemoteException {
        if (permitsToAcquire < 0) throw new IllegalArgumentException("Permits to acquire cannot be negative.");
        while (permits < permitsToAcquire) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RemoteException("Thread interrupted while waiting for permits", e);
            }
        }
        permits -= permitsToAcquire;
        System.out.println(Thread.currentThread().getName() + " acquired " + permitsToAcquire + " permits.");
    }

    @Override
    public void release() throws RemoteException {
        permits++;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " released 1 permit.");
    }

    @Override
    public synchronized void release(int permitsToRelease) throws RemoteException {
        if (permitsToRelease < 0) throw new IllegalArgumentException("Permits to release cannot be negative.");
        permits += permitsToRelease;
        notifyAll();
        System.out.println(Thread.currentThread().getName() + " released " + permitsToRelease + " permits.");
    }

    @Override
    public synchronized int availablePermits() throws RemoteException {
        return permits;
    }
}
