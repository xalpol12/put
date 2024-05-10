import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomSemaphore extends Remote {
    void acquire(int permits) throws RemoteException;
    void release(int permits) throws RemoteException;
}
