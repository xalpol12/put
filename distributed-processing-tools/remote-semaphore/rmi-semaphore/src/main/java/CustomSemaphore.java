import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomSemaphore extends Remote {
    void acquire() throws RemoteException;
    void acquire(int permits) throws RemoteException;
    void release() throws RemoteException;
    void release(int permits) throws RemoteException;
    int availablePermits() throws RemoteException;
}
