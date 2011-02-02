
/*
interpretor: iterator, runable

   |  
  \|/
   '
interpretVisitor
*/

import java.util.Stack;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map;
import java.util.Vector;
import java.util.List;
import java.util.LinkedList;

class PortugolInterpreter extends Thread implements PortugolParserVisitor //, implements Iterator
{
	private static final int LHS = 1; //the left-hand side of an equation
	private static final int RHS = 2; //the right-hand side	
	
	private static final int GLOBAL_MEM = 3; //goblal memory mode
	private static final int LOCAL_MEM = 4; //local memory mode
	
	TreeMap<String, UnitCompiler> unit_compiler_map;
	
	//------------------------------
	//MEMORY
	Stack<SymbolTable> locals;//mem
	Stack<Object> values; //mem
	Stack<UnitCompiler> unit_stack;//

		
	SymbolTable curr_symtab;
	int curr_mem;
	//UnitSymTab entry_point;
	//SymTab curr_local;
	//UnitSymTab curr_unit;			
	//entry_point UnitCompiler
	//----------------------------
	
	boolean started = false;
	public boolean finished = false;
	int curr_line = 0;
	String curr_line_str = "";
	
	UnitCompiler curr_unit = null;
	
	public PortugolInterpreter( TreeMap<String, UnitCompiler>  unit_compiler_map )
	{	
		this.unit_compiler_map =  unit_compiler_map;
	}
	
	synchronized void interrupt( Token tok )
	{
		this.curr_line = tok.beginLine;
		Token t = tok;
		curr_line_str = "";
		while( t.kind != PortugolParserConstants.EOL && t.kind != PortugolParserConstants.EOF )
		{									
			curr_line_str = curr_line_str + t.image + " ";
			t = t.next;
		}
		
		notify();// <- resume the main				
		
		try {
				wait(); // <- pause the interpretation
			}
			catch (InterruptedException e) {
				System.out.println("InterruptedException caught");
				notifyAll();
			}
	}
	
	synchronized void next()
	{
		if( !started )
		{			
			this.start();
			started = true;
		}
		
		notify();// <- resume the interpretation
		try {
			wait();// <- pause the main			
		}
		catch (InterruptedException e) {
			
			System.out.println("InterruptedException caught");
			notifyAll();
		}

		//return currLine;			
	}
	
	synchronized boolean hasNext()
	{
		return !finished;
	}
	
	synchronized int getCurrLine()
	{
		return curr_line;
	}
	
	synchronized String getCurrLineStr()
	{
		return curr_line_str;
	}
	
	synchronized String getCurrUnit()
	{
		return curr_unit.toString();
	}
	
	public void run()
	{	   
		//---
		//dfd - data flow diagram
			
			//---- this is already made on the semantic passes
			//-reset of symbols of units
			//-imports..
			//-declarations of functions
			//----
			
		//-definição das globais..
		//-call main
		
		//---
		
		//init mem
		locals = new Stack<SymbolTable>();
		values = new Stack<Object>();
		unit_stack = new Stack<UnitCompiler>();
//		params_values = new Vector<Object>();
		curr_mem = GLOBAL_MEM;
		//-------		
		try {
			
			FunctionSymbol main = null;
			Iterator it = unit_compiler_map.values().iterator();
			while( it.hasNext() )
			{
				curr_unit = ( UnitCompiler ) it.next();
				
				if( curr_unit.getTree() != null )//if not a lib	
				{
					curr_unit.getTree().jjtAccept( this, null );					
					
					if( main == null )
						main = (FunctionSymbol) curr_unit.lookupUnitFunction("programa", new LinkedList() );
							
				}
			}

			
			if( main != null )
			{
				curr_mem = LOCAL_MEM;
				curr_symtab = new SymbolTable();
				curr_unit = main.getUnitCompiler();
				main.getTree().jjtAccept( this , null );								
			}

			
		}catch (Exception e) {			
			e.printStackTrace();
			//finished = true;
			notifyAll();
		}
		
		finished = true;
		
		//---
    }

	public Object visit(SimpleNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTStart node, Object data) throws Exception
	{		
			
		//go to globals..
		for(int i=0; i<node.jjtGetNumChildren();i++){
			if( ((SimpleNode)node.jjtGetChild(i)).id == PortugolParserTreeConstants.JJTVARIABLEDECLARATIONLIST )			
				node.jjtGetChild(i).jjtAccept(this, null);
		}
				
		return null;
	}
	
	public Object visit(ASTImportDeclaration node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTVariableDeclarationList node, Object data) throws Exception
	{
		node.childrenAccept(this, data);
		
		return null;
	}
	
	public Object visit(ASTVariableDeclaration node, Object data) throws Exception
	{		
		VariableSymbol var = null;
		if( curr_mem == GLOBAL_MEM )				
		{
			var = (VariableSymbol) curr_unit.lookupUnitGlobal( node.getVarName() );
		}
		else	//if LOCAL_MEM
		{
			interrupt( node.jjtGetFirstToken() );
			
			int type_quali = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTokenTypeQualifier().kind;
			int type_spec = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTokenTypeSpecifier().kind;		
			//String type_spec_custom = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTypeSpecifier();

			var = new VariableSymbol(type_quali, type_spec);
			
			//System.out.println( node.getVarName() );
			
			curr_symtab.insert( node.getVarName(), var );
		}
		
		//-----------------------------------
			
		if( node.jjtGetNumChildren() > 0 )//if initialiazation exists
		{			
			var.setValue( Evaluator.cast( var.getTypeSpecifier() , var.isReference(),
		 		node.jjtGetChild(0).jjtAccept( this, RHS ) ));		
		}
		else //default value
		{
			//if var.getTypeQualifier() != VARIABLE sould do something else...
			
			switch( var.getTypeSpecifier() )
			{
				case PortugolParserConstants.BOOL:
					var.setValue( new Boolean(false) );
					break;
				case PortugolParserConstants.CHAR:
					var.setValue( new Character(' ') );
					break;
				case PortugolParserConstants.INT:
					var.setValue( new Integer(0) );
					break;
				case PortugolParserConstants.REAL:
					var.setValue( new Double(0.0) );
					break;
				case PortugolParserConstants.STRING:
					var.setValue( new String("") );
					break;
				case PortugolParserConstants.IDENTIFIER:
					//structs......
					break;
				default:
				//.... unknown error.. what?? 
			}
		}

		return null;
	}
	
	public Object visit(ASTArrayDims node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTArrayInitializer node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTFunctionDeclaration node, Object data) throws Exception
	{
		
		//visit chidrens
		//node.childrenAccept(this, null);
		//curr_symtab.pushScope(); level = 0
		
		for(int i=0; i<node.jjtGetNumChildren();i++)
		{		
				Object return_obj = node.jjtGetChild(i).jjtAccept(this, null);
				if( return_obj != null && return_obj instanceof ReturnControl )
				{
					//curr_symtab.popScope();
					return ((ReturnControl)return_obj).return_value;				
				}
				
		}
		
		//curr_symtab.popScope();
		return null;
		//return data;		
	}
	
	public Object visit(ASTFormalParametersList node, Object data) throws Exception
	{
		//node.childrenAccept(this, data);
		//for(int i=0; i<node.jjtGetNumChildren();i++)
		
		for(int i = node.jjtGetNumChildren()-1;  i>=0; i--)
		{		
				node.jjtGetChild(i).jjtAccept(this, data);			
		}
		
		return data;
	}
	
	public Object visit(ASTFormalParameter node, Object data) throws Exception
	{		
		VariableSymbol param = new VariableSymbol( node.getTokenParamType().kind ); 		
			
		param.setValue( Evaluator.cast( param.getTypeSpecifier() , param.isReference(), values.pop() ) );		 				
		
		curr_symtab.insert( node.getParamName(), param );
				
		return data;
		
	}
	
	public Object visit(ASTStructDeclaration node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTStatements node, Object data) throws Exception
	{
				
		for(int i=0; i<node.jjtGetNumChildren();i++)
		{		
				Object return_obj = node.jjtGetChild(i).jjtAccept(this, null);
				if( return_obj != null && return_obj instanceof ReturnControl )
				{
					return return_obj;				
				}
								
		}
		
		return null;
	}
	
	public Object visit(ASTReturnStatement node, Object data) throws Exception
	{
		interrupt( node.jjtGetFirstToken() );
		
		if(	node.jjtGetNumChildren() > 0 )
			return new ReturnControl( node.jjtGetChild(0).jjtAccept( this, RHS ) );
		
		return new ReturnControl( null );
	}
	
	public Object visit(ASTIfStatement node, Object data) throws Exception
	{
		interrupt( node.jjtGetFirstToken() );
		
		boolean condicion = (boolean)(Boolean)node.jjtGetChild(0).jjtAccept( this, RHS );
		Object return_obj = null;		 
		
		if( condicion )
		{
			curr_symtab.pushScope();
		  return_obj = node.jjtGetChild(1).jjtAccept( this, null );
		  curr_symtab.popScope(); 
		}
		else if ( node.jjtGetNumChildren() == 3 )
		{
			curr_symtab.pushScope();
		  return_obj = node.jjtGetChild(2).jjtAccept( this, null );        
		  curr_symtab.popScope();
		}
     
		if( return_obj != null && return_obj instanceof ReturnControl )
		{
			return return_obj;				
		}
				   		
		return null;
	}
	
	public Object visit(ASTSwitchStatement node, Object data) throws Exception
	{		
		interrupt( node.jjtGetFirstToken() );
				
		//<SWITCH> Expression() <EOL>	
		Object result = node.jjtGetChild(0).jjtAccept( this, RHS );
		
		for(int i=1; i<node.jjtGetNumChildren();i++)
		{					
			if( i % 2 != 0 )
			{									
				if( (Boolean)node.jjtGetChild(i).jjtAccept(this, result ) )	//SwitchLabel()			
				{										
					Object return_obj = node.jjtGetChild(i+1).jjtAccept(this, null); //Statements()	
					if( return_obj != null && return_obj instanceof ReturnControl )
					{
						return return_obj;				
					}				
					break;
				}
			}
		}
		
		return null;
		
	}
	
	public Object visit(ASTSwitchLabel node, Object data) throws Exception
	{	
		if( !node.isDefault() )
			return ( Evaluator.evaluate( PortugolParserConstants.OP_E, data , node.jjtGetChild(0).jjtAccept( this, RHS ) ) );
				
		return true;
	}
	  
	public Object visit(ASTWhileDoStatement node, Object data) throws Exception
	{
		 interrupt( node.jjtGetFirstToken() );
				
		 boolean condicion = (boolean)(Boolean)node.jjtGetChild(0).jjtAccept( this, RHS );
		 Object return_obj = null;
				 
     while( condicion )
     {
     		curr_symtab.pushScope();
     		    
        return_obj = node.jjtGetChild(1).jjtAccept( this, null );
        
        curr_symtab.popScope();
        
    if( return_obj != null && return_obj instanceof ReturnControl )
		{
					return return_obj;				
		}
				interrupt( node.jjtGetFirstToken() );
        condicion = (boolean)(Boolean)node.jjtGetChild(0).jjtAccept( this, RHS );
     }
        		
		return null;
	}
	
	public Object visit(ASTDoWhileStatement node, Object data) throws Exception
	{  
		 boolean condicion = false;
		 Object return_obj = null;
		 		 
		 do{
		 	
     		curr_symtab.pushScope();
     				
				return_obj = node.jjtGetChild(0).jjtAccept(this, null);
             
        curr_symtab.popScope();
        
        if( return_obj != null && return_obj instanceof ReturnControl )
				{
					return return_obj;				
				}
				
				interrupt( node.jjtGetFirstToken() );
        condicion = (boolean)(Boolean)node.jjtGetChild(1).jjtAccept( this, RHS );
        
     }while( condicion );
     
     return null;
	}
	
	public Object visit(ASTForStatement node, Object data) throws Exception
	{
		interrupt( node.jjtGetFirstToken() );

		boolean condicion = false;
		Object return_obj = null;
		
		VariableSymbol var = (VariableSymbol)node.jjtGetChild(0).jjtAccept(this, LHS);
		
		var.setValue( Evaluator.cast( var.getTypeSpecifier() , var.isReference(),
		node.jjtGetChild(1).jjtAccept( this, RHS ) ));
		
		Object final_value = node.jjtGetChild(2).jjtAccept( this, RHS );
				
		Object step = new Integer( 1 );
		if( node.jjtGetNumChildren() == 5 )
			step = node.jjtGetChild(3).jjtAccept( this, RHS );
		
		boolean inc = ( Boolean )Evaluator.evaluate( PortugolParserConstants.OP_GE, step, 0 );
		 						 				 
		while( (Boolean)Evaluator.evaluate( ( inc ? PortugolParserConstants.OP_LE : PortugolParserConstants.OP_GE ) ,
			 var.getValue(), final_value ) )
		{
     		curr_symtab.pushScope();
     				
				return_obj = node.jjtGetChild(node.jjtGetNumChildren()-1).jjtAccept(this, null );
             
        curr_symtab.popScope();
        
        if( return_obj != null && return_obj instanceof ReturnControl )
				{
					return return_obj;				
				}
				
				interrupt( node.jjtGetFirstToken() );		
				 
				var.setValue( Evaluator.evaluate( PortugolParserConstants.OP_PLUS, var.getValue() , step ) );				
     }
     
     return null;		

	}
	
	public Object visit(ASTCallFunction node, Object data) throws Exception
	{
		/*
		FunctionSymbol func = null;		
		Object return_value = null;
						
		func = (FunctionSymbol) curr_unit.lookupFunction( node.getFuncName() );

		if ( func.isPreCompiled() )
		{
			Object params[] =  new Object[ node.jjtGetNumChildren() ];
		
			for(int i=0; i<node.jjtGetNumChildren(); i++)
			{									
					params[i] = node.jjtGetChild(i).jjtAccept( this, RHS );
			}
			
			return func.Execute( params );
		}		
		// or else
			
	
		//prepare the parameters..	
		//params_values.clear();
		for(int i=0; i<node.jjtGetNumChildren(); i++)
		{
			values.push( node.jjtGetChild(i).jjtAccept( this, RHS ) );
		}
		
		//save the current SymTab on stack
		locals.push( curr_symtab );
		unit_stack.push( curr_unit );
		
		curr_symtab = new SymbolTable();
		curr_unit = func.getUnitCompiler();
		
		//run it..		
		//System.out.println( "call: " + node.getFuncName() );
		
		return_value = func.getTree().jjtAccept( this , null );

		//System.out.println( "r: " +  return_value );
		
		curr_symtab = locals.pop();
		curr_unit = unit_stack.pop();
		
		return return_value;*/
		
		FunctionSymbol func = null;		
		Object return_value = null;
		
		List parameters_types = new LinkedList();
		List parameters_values = new LinkedList();
		
		for(int i=0; i<node.jjtGetNumChildren(); i++)
		{		
			interrupt( node.jjtGetFirstToken() );
												
			Object param_value = node.jjtGetChild(i).jjtAccept( this, RHS );
			
			if( param_value instanceof java.lang.Boolean )
				parameters_types.add( PortugolParserConstants.BOOL );
			else 
			if ( param_value instanceof java.lang.Character )
				parameters_types.add( PortugolParserConstants.CHAR );
			else
			if ( param_value instanceof java.lang.Integer )
				parameters_types.add( PortugolParserConstants.INT );
			else
			if ( param_value instanceof java.lang.Double )
				parameters_types.add( PortugolParserConstants.REAL );
			else 
			if( param_value instanceof java.lang.String )
				parameters_types.add( PortugolParserConstants.STRING );
			else
				parameters_types.add( PortugolParserConstants.VOID );
			
			parameters_values.add( param_value );			
		}				
						
		func = (FunctionSymbol) curr_unit.lookupFunction( node.getFuncName(), parameters_types );		

		if ( func.isPreCompiled() )
		{
			Object params[] = parameters_values.toArray();
				
			return func.Execute( params );
		}		
		// or else
			
		//System.out.println( "call:" + func.getName() );
		//prepare the parameters..	
		for(int i=0; i<parameters_values.size(); i++)
		{
			values.push( parameters_values.get( i ) );			
			//System.out.println( "param: " + func.getParameters().get(i) );			
		}
		
		//save the current SymTab on stack
		locals.push( curr_symtab );
		unit_stack.push( curr_unit );
		
		curr_symtab = new SymbolTable();
		curr_unit = func.getUnitCompiler();
		
		//run it..		
		//System.out.println( "call: " + node.getFuncName() );
		
		return_value = func.getTree().jjtAccept( this , null );

		//System.out.println( "r: " +  return_value );
		
		curr_symtab = locals.pop();
		curr_unit = unit_stack.pop();
		
		return return_value;
	}
	
	public Object visit(ASTAssignmentStatement node, Object data) throws Exception
	{		
		interrupt( node.jjtGetFirstToken() );
		
		VariableSymbol var = (VariableSymbol) node.jjtGetChild(0).jjtAccept( this, LHS );			
		
		var.setValue( Evaluator.cast( var.getTypeSpecifier() , var.isReference(),
		 node.jjtGetChild(1).jjtAccept( this, RHS ) ));
		
		return null;
	}

	public Object visit(ASTOrNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OR, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTAndNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate(PortugolParserConstants.AND, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTXorNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.XOR, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTEqNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_E, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTNEqNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_NEQ, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}	
				
	public Object visit(ASTGNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_G, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTLNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_L, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTGENode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_GE, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTLENode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_LE, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
			
	public Object visit(ASTAddNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_PLUS, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTSubNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_MINUS, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTMulNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_MUL, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTDivNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_DIV, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}
	
	public Object visit(ASTModNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_MOD, node.jjtGetChild(0).jjtAccept(this, data) , node.jjtGetChild(1).jjtAccept(this, data) );
	}

	public Object visit(ASTNegNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.OP_MINUS, node.jjtGetChild(0).jjtAccept(this, data) );
	}
	
	public Object visit(ASTNotNode node, Object data) throws Exception
	{
		return  Evaluator.evaluate( PortugolParserConstants.NOT, node.jjtGetChild(0).jjtAccept(this, data) );
	}
	
	public Object visit(ASTVariableName node, Object data) throws Exception
	{
		
		int hand_side = RHS;
		/*if( data != null )*/ hand_side = (Integer) data;
			
		VariableSymbol var = null;
		
		if( hand_side == LHS ) //STORE
		{
			if( curr_mem == GLOBAL_MEM )
				var = (VariableSymbol) curr_unit.lookupGlobal( node.getVarName() );
			else
			{
			 	var = (VariableSymbol) curr_symtab.lookup( node.getVarName() );
				if( var == null )//if is not local is a global..
					var = (VariableSymbol)curr_unit.lookupGlobal( node.getVarName() );
			}
			
			//handle fields or Array
			//Object obj = var;
			//push(obj)
			//for(int i=0; i<node.jjtGetNumChildren(); i++)
			//{		
			//		push( node.jjtGetChild(i).jjtAccept( this, RHS ) );
			//}
			
			
			return var;			
		}
		
		//else hand_side == RHS , LOAD
		
		if( curr_mem == GLOBAL_MEM )
			var = (VariableSymbol)curr_unit.lookupGlobal( node.getVarName() );
		else //if LOCAL_MEM		
		{
			var = (VariableSymbol)curr_symtab.lookup( node.getVarName() );			
			if( var == null )//if is not local is a global..
				var = (VariableSymbol)curr_unit.lookupGlobal( node.getVarName() );
		}
		
		//System.out.println( "load" + node.getVarName() );
		return var.getValue();		
	}
	
	public Object visit(ASTIndexSelector node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTFieldSelector node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTCharNode node, Object data) throws Exception
	{
		return new Character( (Character)node.value );
	}
	
	public Object visit(ASTIntNode node, Object data) throws Exception
	{
		return new Integer( (Integer)node.value );
	}
	
	public Object visit(ASTRealNode node, Object data) throws Exception
	{
		return new Double( (Double)node.value );
	}
	
	public Object visit(ASTStringNode node, Object data) throws Exception
	{		
		//cortar as " "		
		//return new String( (String)node.value );
		return new String( ((String)(node.value)).substring(1, ((String)(node.value)).length()-1 ) );
	}
	
	public Object visit(ASTTrueNode node, Object data) throws Exception
	{
		return new Boolean( true );
	}
	
	public Object visit(ASTFalseNode node, Object data) throws Exception
	{
		return new Boolean( false );
	}
	
	public Object visit(ASTNullNode node, Object data) throws Exception
	{
		return null;
	}
	
	//ReturnControl - inner class to help on the control flow of staments
	class ReturnControl
	{
		
		Object return_value;
		
		ReturnControl( Object return_value )
		{
				this.return_value = return_value;
		}
		
	}
	
}
	
	
	
	