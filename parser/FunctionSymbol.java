//import java.util.TreeMap;
//import java.util.Map;
import dynamic_lib.*;
import java.util.List;
import java.util.LinkedList;

class FunctionSymbol
{
	private int return_type = PortugolParserConstants.VOID;
	
	private String return_type_custom = "";
	 	
	//private TreeMap<String, VariableSymbol> param_list = new TreeMap<String, VariableSymbol>();
	//List< VariableSymbol > params_list = new LinkedList< VariableSymbol >()
	private List params_list = new LinkedList();
		
	private Node tree;
	
	private boolean is_pre_compiled = false;
	private Library lib;	
	
	private String name;
	private UnitCompiler unit_compiler;
	
	public FunctionSymbol( String name, UnitCompiler unit_compiler, Library lib )
	{
		is_pre_compiled = true;
		
		this.name = name;
		
		this.lib = lib;
		
		this.unit_compiler = unit_compiler;
		
	}
	
	public FunctionSymbol( String name, UnitCompiler unit_compiler, int return_type, Library lib )
	{
		is_pre_compiled = true;
		
		this.name = name;
		
		this.lib = lib;
		
		this.return_type = return_type;
		
		this.unit_compiler = unit_compiler;
		
	}
	
	public FunctionSymbol( String name, UnitCompiler unit_compiler, int return_type, Node tree)
	{
		this.name = name;
		this.return_type = return_type;
		this.tree = tree;
		this.unit_compiler = unit_compiler;
	}
	
	public FunctionSymbol( String name, UnitCompiler unit_compiler, String return_type_custom, Node tree)
	{
		this.name = name;
		this.return_type_custom = return_type_custom;
		this.return_type = PortugolParserConstants.IDENTIFIER;
		this.tree = tree;
		this.unit_compiler = unit_compiler;
	}
		
	public Node getTree()
	{
		return tree;
	}
	
	public String getName()
	{
		return name;
	}
	
//	public boolean insertParameter(String param_name, VariableSymbol param)
//	{
//		if( param_list.put(param_name , param) == null )
//			return true;
//		return false;
//	}
	
//	public Map getParametersMap()
//	{
//		return param_list;
//	}

	public boolean insertParameter( int param )
	{
		params_list.add( param );
		return true;
	}
	
	public List getParameters()
	{
		return params_list;
	}
	
	public int getReturnType()
	{
		return return_type;
	}
	
	boolean isPreCompiled()
	{
		return is_pre_compiled;		
	}
	
	public Object Execute( Object params[] ) 
	{ 
		if( isPreCompiled() )
			return  lib.Execute( name, params );
			
		return null;
	}
	
	public UnitCompiler getUnitCompiler()
	{
		return unit_compiler;
	}
	
}