class VariableSymbol
{
	private int type_qualifier = PortugolParserConstants.VARIABLE;
	private int type_specifier = PortugolParserConstants.IDENTIFIER;
	
	private String type_specifier_custom = "";
	
	private boolean is_array = false;
	
	private Object value = null;
	
	public VariableSymbol(int type_specifier)
	{
		this.type_specifier = type_specifier;
	}
	public VariableSymbol(String type_specifier_custom)
	{
		this.type_specifier_custom = type_specifier_custom;
	}	
	public VariableSymbol(int type_qualifier, int type_specifier)
	{
		this.type_qualifier = type_qualifier;
		this.type_specifier = type_specifier;
	}
	public VariableSymbol(int type_qualifier, String type_specifier_custom )
	{
		this.type_qualifier = type_qualifier;
		this.type_specifier_custom = type_specifier_custom;		
	}
	
	public void setValue( Object value )
	{
		this.value = value;
	}
	
	public Object getValue()
	{
		return value;
	}
	
	public int getTypeQualifier()
	{
		return type_qualifier;
	}
	
	public int getTypeSpecifier()
	{
		return type_specifier;
	}
	
	public boolean isReference()
	{
		return ( type_qualifier == PortugolParserConstants.REFERENCE ? true : false );
	}
	
}