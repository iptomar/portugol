
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import utils.NullSecurity;

public class PortugolInterpretorCodeViewer
{
    
    PortugolIPCMessage obj;
    Registry registry;
    
    public PortugolInterpretorCodeViewer()
    {
        try {
            //System.setSecurityManager( new RMISecurityManager());            
						System.setSecurityManager( new NullSecurity() );            
            obj = new PortugolIPCMessage();

            PortugolIPCMessageI stub = (PortugolIPCMessageI) UnicastRemoteObject.exportObject(obj,0);            

            registry = LocateRegistry.createRegistry(10000);
            registry.bind( "PortugolIPCMessage", stub );
            
            System.out.println("PortugolInterpretorCodeViewer\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    void stop()
    {
        try 
        {
            registry.unbind("PortugolIPCMessage");
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
        
    public static void main(String[] args) {
        PortugolInterpretorCodeViewer cv = new PortugolInterpretorCodeViewer();
        
        while( cv.obj.isActive() )
        {
        	Thread.yield();
        }
        
        cv.stop();
                
        System.exit(0);
    }
    
}
