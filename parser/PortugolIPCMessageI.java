import java.rmi.Remote;
import java.rmi.RemoteException;


public interface PortugolIPCMessageI extends Remote {
    public void printMessage( String txt) throws RemoteException;
}
