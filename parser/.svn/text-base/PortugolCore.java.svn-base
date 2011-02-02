
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;


class PortugolCore
{
		PortugolInterpreter portugolMachine;

		public void doIt(String source_files[], boolean dump_trees, boolean interpret, boolean print_info ) throws Exception {					
		
			PortugolParser parser;
			portugolMachine = null;
			
			TreeMap<String, UnitCompiler> unit_compiler_map = new TreeMap<String, UnitCompiler>();
			
			if( print_info )
			{
				System.out.println("###Portugol Core 3.0###");
				System.out.println("\nInput files: ");
				for( int i = 0; i<source_files.length; i++ )
				{
					System.out.println( source_files[i] );						
				}										
			}

			String file;
           
    	try {																										
											
						//parse all inputed files
						for( int i = 0; i<source_files.length; i++ )
						{
							file = source_files[i];
			
							if( print_info )
							{
							   System.out.println();
							   System.out.println("Parsing file: " + file);
							}
							
							parser = new PortugolParser(new java.io.FileInputStream(file));
							parser.Start();

							if( print_info )
								System.out.println("Done!");
							
							Node root = parser.jjtree.rootNode();
							
							//tira a extenção txt
							file = file.substring(0, file.length()-4 );
							
							UnitCompiler unit_comp = new UnitCompiler(file);
							unit_comp.setTree( root );
							
							unit_compiler_map.put( file , unit_comp );							
						}
	
	
						if( dump_trees )
						{							
							//dump trees													
							for( int i = 0; i<source_files.length; i++ )
							{
								file = source_files[i];
								System.out.println();
								System.out.println("Dump tree of file: " + file);							
								DumpTree v = new DumpTree();							
								
								//tira a extenção txt
								file = file.substring(0, file.length()-4 );
								
								UnitCompiler unit_comp = unit_compiler_map.get(file);							
								
								unit_comp.getTree().jjtAccept(v, "-");
								
								System.out.println("Done!");
							}
						}
						
						//First pass of semantinc check..
						//-check if the imports exist
						//-Fill the symbtab with global/functions/structures/imports
						
						for( int i = 0; i<source_files.length; i++ )
						{
							file = source_files[i];
							
							if( print_info )
							{
							   System.out.println();
							   System.out.println("Check Sematic [ 1st pass ] of file: " + file);
							}
							
							//tira a extenção txt
							file = file.substring(0, file.length()-4 );
							
							UnitCompiler unit_comp = unit_compiler_map.get(file);
							
							SemanticFirstPass v = new SemanticFirstPass(unit_comp, unit_compiler_map);
																												
							unit_comp.getTree().jjtAccept( v, null );
							
							if( print_info )
								System.out.println("Done!");
						}
						
						//Second Pass od semmantic check
						//-check if/variables/structs/functions are declared
						//-check is there no multiple declarions in the same unit for one identifier
						//-type checking.
						//
						//-todos os identificadores foram declarados 
						//-não existência de declarações múltiplas e incompatíveis para um mesmo identificador 
						//-determinar os tipos de expressões e variáveis e a sua compatibilidade quando aparecem numa mesma expressão

						for( int i = 0; i<source_files.length; i++ )
						{
							file = source_files[i];
							
							if( print_info )
							{
							   System.out.println();
							   System.out.println("Check Sematic [ 2nd pass ] of file: " + file);
							}
							
							//tira a extenção txt
							file = file.substring(0, file.length()-4 );
							
							UnitCompiler unit_comp = unit_compiler_map.get(file);
							
							SemanticSecondPass v = new SemanticSecondPass(unit_comp, unit_compiler_map);
																												
							unit_comp.getTree().jjtAccept( v, null );
							
							if( print_info )
								System.out.println("Done!");
						}																	
            
            if(	interpret )
            {             												
							portugolMachine = new PortugolInterpreter( unit_compiler_map );
						}
						
						//---------------------------------------------										
					}catch ( java.io.FileNotFoundException e1 ) {
						throw e1;
					
					}catch ( TokenMgrError e2 ) {						
						throw e2;
						
					}catch ( ParseException e3 ) {					
						throw e3;
														
					} catch( SemanticException e4 ){						
						throw e4;
						
					}catch( Exception e ){
						throw e;
							
					}
		}


	public boolean hasNextInstruction()
	{
		if( portugolMachine != null )	
			return portugolMachine.hasNext();
		
		return false;
	}
							
	public void nextInstruction()
	{
		if( portugolMachine != null )	
			portugolMachine.next();
				
	}

	public String getCurrUnit()
	{
		if( portugolMachine != null )	
			return portugolMachine.getCurrUnit();
		
		return "";		
	}

	public int getCurrLine()
	{
		if( portugolMachine != null )	
			return portugolMachine.getCurrLine();
		
		return -1;
	}

	public String getCurrLineStr()
	{
		if( portugolMachine != null )	
			return portugolMachine.getCurrLineStr();
		
		return "";	
	}

}