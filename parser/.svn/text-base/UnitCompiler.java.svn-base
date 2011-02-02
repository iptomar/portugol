
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.List;
import java.util.LinkedList;
   
import dynamic_lib.*;

class UnitCompiler
{
		
	//unit Symbols
	private Hashtable imports_included;// links to imported units
	private Hashtable struct_declaration;
	private Hashtable func_declaration;
	
	private Hashtable globals;
	//--------------
	
	private Node root = null;	
	private String name = "";
	
	//lib stuff
	private boolean is_lib;
	Library lib;	
	//	
	
	public UnitCompiler(String name)
	{	
		this.name = name;
		imports_included = new Hashtable();
		struct_declaration = new Hashtable();
		func_declaration = new Hashtable(); 
		globals = new Hashtable();	
		is_lib = false;
	}
	
	public UnitCompiler( String name, boolean is_lib )
	{	
		this.name = name;
		imports_included = new Hashtable();
		struct_declaration = new Hashtable();
		func_declaration = new Hashtable(); 
		globals = new Hashtable();	
		
		this.is_lib = is_lib;
		
		if( isLibrary() )
		{
			lib = new Library( name );
								
			//fill functions
			Vector<String> funcs = lib.getMethodsNames();
			for(int i=0; i< funcs.size() ;i++)
			{			
				//System.out.print( funcs.get( i ) );
					
				int return_type = convertTypeToInt( lib.getMethodReturnType( funcs.get( i ) ) ) ;
				
				FunctionSymbol f = new FunctionSymbol( funcs.get( i ), this, return_type, lib ); 								
				
				Vector<String> params = lib.getParametersTypeNames( funcs.get( i ) );
				for(int j=0; j<params.size(); j++)
				{
					int type = convertTypeToInt( params.get(j) );

					//VariableSymbol var = new VariableSymbol( type );					
					//f.insertParameter( Integer.toString( j ) , var );					
					//f.insertParameter( var );
					
					//System.out.print( PortugolParserConstants.tokenImage[ type ] + " - " + params.get(j) );
					
					f.insertParameter( type );
				}
				
				//System.out.println("");
			
				insertFunction( funcs.get( i ), f );
			}			
				
			//fill globals
			Vector<String> consts = lib.getConstsNames();
			Object[] consts_values = null;

			try
			{
				consts_values = lib.getConstsValues();
			}
			catch( IllegalAccessException e )
			{			
				System.out.println( e.getMessage() );
			}
			
			for(int i=0; i< consts.size() ;i++)
			{	
				int type = convertTypeToInt( lib.getConstType( consts.get(i) ) );
				VariableSymbol var = new VariableSymbol( PortugolParserConstants.CONSTANT,  type );
				
				if( consts_values != null )
					var.setValue( consts_values[i] );
				
				insertGlobal( consts.get(i), var );
			}
		}
	}
	
	private int convertTypeToInt( String type_str )
	{
		int type = PortugolParserConstants.VOID;

		if( type_str.equals("double") || type_str.equals("java.lang.Double") )
			type = PortugolParserConstants.REAL;
		
		else if ( type_str.equals("int") || type_str.equals("java.lang.Integer") )
			type = PortugolParserConstants.INT;
	
		else if ( type_str.equals("boolean") || type_str.equals("java.lang.Boolean")  )
			type = PortugolParserConstants.BOOL;
		
		else if ( type_str.equals("java.lang.String") )
			type = PortugolParserConstants.STRING;
		
		else if ( type_str.equals("char") || type_str.equals("java.lang.Character")  )
			type = PortugolParserConstants.CHAR;

		return type;		
	}
	
	public void setTree(Node root)
	{
		this.root = root;
	}
	
	public Node getTree()
	{
		return root;
	}
	
	public String toString() 
	{
		return name;
	}
	
	//global symbols
	public boolean insertGlobal( String name, Object global )
	{
		if( globals.put(name, global) == null )		
			return true;
		return false;
	}
	
	public Object lookupGlobal( String name )
	{	
		if( globals.get( name ) != null )
			return 	globals.get( name );
			
		Collection units = imports_included.values();		
		Iterator it = units.iterator();
 
		while(it.hasNext())
		{
			UnitCompiler unit = ( UnitCompiler ) it.next();
			if( unit.lookupUnitGlobal( name ) != null )
				return unit.lookupUnitGlobal( name );
		}
	  
		return null;
	}
	
	public Object lookupUnitGlobal( String name )
	{
		return 	globals.get( name );
	}
	
	//import symblos
	public boolean insertImport( String name, Object unit )
	{
		if( imports_included.put(name, unit) == null )
			return true;
		return false;
	}
	
	//
	public Object lookupImport( String name )
	{
		return imports_included.get( name );			
	}
	
	//struct declaration
	public boolean insertStruct( String name, Object struct )
	{
		if( struct_declaration.put(name, struct) == null )
			return true;
		return false;
	}
	
	public Object lookupStruct( String name )
	{
		return struct_declaration.get( name );	
	}

/*
	//func declaratiomn
	public boolean insertFunction( String name, Object function )
	{
		if( func_declaration.put( name, function ) == null )
			return true;
		return false;
	}		
	
	public Object lookupFunction( String name )
	{
		if( func_declaration.get( name ) != null )
			return 	func_declaration.get( name );
			
		Collection units = imports_included.values();		
		Iterator it = units.iterator();
 
		while(it.hasNext())
		{
			UnitCompiler unit = ( UnitCompiler ) it.next();
			if( unit.lookupUnitFunction( name ) != null )
				return unit.lookupUnitFunction( name );
		}
	  
		return null;
		
	}
	
	public Object lookupUnitFunction( String name )
	{
		return func_declaration.get( name );
	}
*/
	
	//--------------------
		//func declaratiomn
	public boolean insertFunction( String name, Object function )
	{
	
		if( func_declaration.containsKey(name) )//se já existe uma função com esse nome:	
		{
			//get the list and.. insert new function if it is ok..	
			List funcs = (List)func_declaration.get( name );
			
			for( Object func : funcs )
			{		
				List params1 = ((FunctionSymbol)func).getParameters();
				List params2 = ((FunctionSymbol)function).getParameters();

				if( params1.size() == params2.size() )
				{														
					int count = 0;
					for( int i = 0; i < params1.size(); i++ )
					{										
						if( params1.get(i) == params2.get(i) )
							count++;
					}
					if(count == params1.size() )
						return false;
				}				
			}							
			
			funcs.add( function );
				
  		return true;
		}	
		//senao cria uma lista espeta lá para dentro..
			
		List funcs = new LinkedList();
		funcs.add( function	);
		func_declaration.put( name, funcs );				
		
		return true;
		
		//if( func_declaration.put( name, function ) == null )
		//	return true;
		//return false;
	}		
		
	public Object lookupUnitFunction( String name, List params )
	{
		if( func_declaration.containsKey( name ) )
		{
			List funcs = (List)func_declaration.get( name );
			
			for( Object func : funcs )
			{		
				List saved_func_params = ((FunctionSymbol)func).getParameters();				

				if( params.size() == saved_func_params.size() )
				{														
					int count = 0;
					for( int i = 0; i < params.size(); i++ )
					{										
						if( params.get(i) == saved_func_params.get(i) )
							count++;
					}
					if(count == params.size() )
						return func;
				}				
			}										
		}
				
		return null;
	}
	
	
	public Object lookupFunction( String name, List params )
	{		
		
		Object ret = lookupUnitFunction( name, params );
		
		if( ret != null )
			return 	ret;
			
		Collection units = imports_included.values();		
		Iterator it = units.iterator();
 
		while(it.hasNext())
		{
			UnitCompiler unit = ( UnitCompiler ) it.next();
			ret = unit.lookupUnitFunction( name, params );
			if( ret != null )
				return unit.lookupUnitFunction( name, params );
		}
	  
		return null;
	}
	
	
	
	//--------
	public boolean isLibrary()
	{
		return is_lib;
	}

}