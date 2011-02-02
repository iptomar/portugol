import java.rmi.RemoteException;


public class PortugolIPCMessage implements PortugolIPCMessageI
{
    public PortugolIPCMessage()
    {
    }
    
    public void printMessage( String txt) throws RemoteException
    {
    		if( txt.equals("##exit") )
    		{
    			active = false;
    			return;
    		}
    		
        System.out.println( txt );
    		
    		//Thread.currentThread().getName()    
    }
    
    public synchronized boolean isActive()
    {
    	return active;    	
    }
    
    boolean active = true;
}