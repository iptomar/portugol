//import Portugol.Core; //the one

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import utils.NullSecurity;

class Portugol
{

		public static void main(String args[]) throws IOException {					

			//System.out.println("###Portugol Core 3.0###");
			//System.out.println("\nInput files: ");
			//for( int i = 0; i<args.length; i++ )
			//{
			//	System.out.println( args[i] );						
			//}			
			
			//show code viewer							
																		
			try {																										
																												
						PortugolCore portugol_core = new PortugolCore();
						
						portugol_core.doIt( args, true, true, false );						
						
						System.out.print("Setup PortugolInterpretorCodeViewer");
																							
						Process p = Runtime.getRuntime().exec("cmd /C start viewer.bat");	

	 
						for( int i=0; i<10; i++ )
						{
         			Thread.sleep(10);
         			System.out.print(".");
         		}
         		        		
            //System.setSecurityManager( new RMISecurityManager() );
            System.setSecurityManager( new NullSecurity() );
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 10000);            
            PortugolIPCMessageI stub = (PortugolIPCMessageI) registry.lookup("PortugolIPCMessage");
            
            System.out.println("!");
            
						System.out.println("Interpreting...\n\n");
											
												
						while( portugol_core.hasNextInstruction() )
						{
							
							portugol_core.nextInstruction();
							
							if( portugol_core.hasNextInstruction() )
							{
								stub.printMessage( ( portugol_core.getCurrUnit() + ":" + 
									Integer.toString( portugol_core.getCurrLine() ) + " -> " + 
									portugol_core.getCurrLineStr() ) );							
							}

							//Thread.sleep(2000);
						}
												
						stub.printMessage("##exit");
						
						//---------------------------------------------										
												
					}catch ( java.io.FileNotFoundException e1 ) {
						System.out.println(e1.getMessage()); 					
					}catch ( TokenMgrError e2 ) {						
						System.out.println(e2.getMessage());					
					}catch ( ParseException e3 ) {					
						System.out.println( e3.getMessage() );													
					} catch( SemanticException e4 ){						
						System.out.println( e4 );										
					}catch( Exception e ){
						e.printStackTrace();							
					}
        }

}