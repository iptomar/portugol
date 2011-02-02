
//expections of semantics

import java.lang.Exception;


public class SemanticException extends Exception {

	String unit_name="";
	int line=-1;
	String message;
	String error_line="";
	String error_pointer="";	
	boolean special_constructor = false;
	
	public SemanticException()
	{
	
	}
	
	public SemanticException(String message)
	{
		super(message);
		this.message = message;	
	}
    
	//for Chained Exceptions
	//public Exception(String message,  Throwable cause)	  
	//public Exception(Throwable cause)	
	
	//special constructor
	public SemanticException(String message, String unit_name, SimpleNode node,  Token t)
	{
		super(message);
		this.unit_name = unit_name;	
		this.message = message;						
		this.line = t.beginLine;
		
		special_constructor = true;
		
		Token it = node.jjtGetFirstToken();
		
		while(it.next != null && !(it.beginLine == t.beginLine || it.kind == PortugolParserConstants.EOF) /*just in case...*/ )
			it = it.next;			
					
		for( ; it != null && !(it.kind == PortugolParserConstants.EOL || it.kind == PortugolParserConstants.EOF); it = it.next )
		{									
			error_line = error_line + it.image + " ";
			
			if( t.equals(it) )
				error_pointer = error_pointer + "^";				
			else
				error_pointer = error_pointer + " ";
							
			for(int i = 0; i < it.image.length(); i++)
				error_pointer = error_pointer + " ";
		}
			
	}
	
	public String getMessage()
	{
		if(special_constructor)
			return unit_name + ":" + line + ":\n" + message + "\n\n" + error_line + "\n" + error_pointer + "\n";			
		else
			return message;
	}
  
}
