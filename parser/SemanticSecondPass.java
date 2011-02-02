
//Second Pass od semmantic check
//-check if/variables/structs/functions are declared
//-check is there no multiple declarions in the same unit for one identifier
//-type checking.
//
//-todos os identificadores foram declarados 
//-não existência de declarações múltiplas e incompatíveis para um mesmo identificador 
//-determinar os tipos de expressões e variáveis e a sua compatibilidade quando aparecem numa mesma expressão

import java.util.TreeMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;

class SemanticSecondPass implements PortugolParserVisitor
{		  
	UnitCompiler unit;
	TreeMap<String, UnitCompiler> unit_compiler_map;
	SymbolTable symtab;
	int curr_return_type = PortugolParserConstants.VOID;
	
	SemanticSecondPass(UnitCompiler unit, TreeMap<String, UnitCompiler> unit_compiler_map )	
	{
		this.unit = unit;
		this.unit_compiler_map = unit_compiler_map;
		
		this.symtab = new SymbolTable();
	}
	
	//check the result type of a binary operation
	private int checkBinaryOperation( SimpleNode node, int op ) throws Exception
	{
			int l_type = (Integer)node.jjtGetChild(0).jjtAccept(this, null);
			int r_type = (Integer)node.jjtGetChild(1).jjtAccept(this, null);
			int result_type = TypeCheck.check( op, l_type, r_type );						
			
			if( result_type == TypeCheck.TYPE_ERROR )
			{
				String type_required = PortugolParserConstants.tokenImage[ l_type ];
				String type_found = PortugolParserConstants.tokenImage[ r_type ];
				
				throw new SemanticException( "incompatible types\nfound   : " + type_found + "\nrequired: " + type_required
					, unit.toString(), (SimpleNode)node.jjtGetParent(), node.jjtGetFirstToken() );
			}
			
		return result_type;	
	}
	
	private int checkUnaryOperation( SimpleNode node, int op ) throws Exception
	{
			int r_type = (Integer)node.jjtGetChild(0).jjtAccept(this, null);

			int result_type = TypeCheck.check( op, r_type );						
			
			if( result_type == TypeCheck.TYPE_ERROR )
			{
				String op_string = PortugolParserConstants.tokenImage[ op ];
				String type_found = PortugolParserConstants.tokenImage[ r_type ];
				
				throw new SemanticException( "operator " + op_string + " cannot be applied to " + type_found
					, unit.toString(), (SimpleNode)node.jjtGetParent(), node.jjtGetFirstToken() );
			}
			
		return result_type;	
	}
	
	private void checkExpressionType( SimpleNode node , int expected_type ) throws Exception
	{
		int result_type = (Integer) node.jjtAccept(this, null);	
		
		if( result_type != expected_type )
		{
            String type_required = PortugolParserConstants.tokenImage[  expected_type ];
			String type_found = PortugolParserConstants.tokenImage[ result_type ];
			
			throw new SemanticException( "incompatible types\nfound   : " + type_found + "\nrequired: " + type_required
				, unit.toString(), (SimpleNode)node.jjtGetParent(), node.jjtGetFirstToken() );    
		
		}		
	}
	
	public Object genericVisit(SimpleNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.;
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
		
		//go to funtions body
		for(int i=0; i<node.jjtGetNumChildren();i++){
			if( ((SimpleNode)node.jjtGetChild(i)).id == PortugolParserTreeConstants.JJTFUNCTIONDECLARATION)			
				node.jjtGetChild(i).jjtAccept(this, null);			
		}

		return data;
	}
	
	public Object visit(ASTImportDeclaration node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.		
	}
	
	public Object visit(ASTVariableDeclarationList node, Object data) throws Exception
	{						
		node.childrenAccept(this, data);		
		return data;
	}

	public Object visit(ASTVariableDeclaration node, Object data) throws Exception
	{				
		int type_quali = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTokenTypeQualifier().kind;
		int type_spec = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTokenTypeSpecifier().kind;
		String type_spec_custom = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTypeSpecifier();
		
		VariableSymbol var = null;

		if( type_spec == PortugolParserConstants.IDENTIFIER )
			var = new VariableSymbol(type_quali, type_spec_custom);
		else 
			var = new VariableSymbol(type_quali, type_spec);
						
		if( !symtab.insert( node.getVarName(), var ) )
		{
			throw new SemanticException( "the variable \"" + node.getVarName() + "\" is already declared", unit.toString(), (SimpleNode)node.jjtGetParent(), node.getTokenVarName() );
		}
		
		//check if var not exist
		
		//type check
		if( node.jjtGetNumChildren() > 0 )//if initialiazation exists
		{
			int r_type = (Integer)node.jjtGetChild(0).jjtAccept(this, null);
								
			if( type_spec != TypeCheck.check( PortugolParserConstants.OP_ASSIGN, type_spec, r_type ) )
			{
				String type_found = PortugolParserConstants.tokenImage[ r_type ];//for now only primitive types are supported..
				String type_required = ( type_spec == PortugolParserConstants.IDENTIFIER ? type_spec_custom : PortugolParserConstants.tokenImage[ type_spec ] );
				throw new SemanticException( "incompatible types\nfound   : " + type_found + "\nrequired: " + type_required
					, unit.toString(), (SimpleNode)node.jjtGetParent(), node.jjtGetFirstToken().next );
			}				
		}
		
		return null;
	}
	
	public Object visit(ASTArrayDims node, Object data) throws Exception
	{
		//
		//unimplemented
		//
		throw new UnsupportedOperationException(); // It better not come here.		
	}
	
	public Object visit(ASTArrayInitializer node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTFunctionDeclaration node, Object data) throws Exception
	{
		curr_return_type = node.getTokenReturnType().kind;
		
		if( curr_return_type != PortugolParserConstants.VOID )
		{
			boolean found = false;
			SimpleNode statements_node = (SimpleNode)node.jjtGetChild( node.jjtGetNumChildren() - 1 );
			for(int i=0; i<statements_node.jjtGetNumChildren(); i++)
			{
				if( ((SimpleNode)statements_node.jjtGetChild(i)).id == PortugolParserTreeConstants.JJTRETURNSTATEMENT )
				{
					found = true;
					break;
				}		
			}
			
			if(!found)	
			{
				throw new SemanticException( "This function must return a value."
					, unit.toString(), (SimpleNode)node.jjtGetParent(), node.jjtGetFirstToken() );
				
			}
		}		
		
		//get function
		//push scope		
		symtab.pushScope();
		
		//visit chidrens
		node.childrenAccept(this, null);
		
		//pop scope				
		symtab.popScope();  
		
		return data;
	}

	public Object visit(ASTFormalParametersList node, Object data) throws Exception
	{		
		node.childrenAccept(this, data);
		
		return data;
	}

	public Object visit(ASTFormalParameter node, Object data) throws Exception
	{
		FunctionSymbol func_symb = (FunctionSymbol)data;	
		
		int type = node.getTokenParamType().kind;
		String type_custom = node.getParamType();
						
		VariableSymbol var = null;

		if( type == PortugolParserConstants.IDENTIFIER )
			var = new VariableSymbol(type_custom);
		else 
			var = new VariableSymbol(type);
						
		if( !symtab.insert( node.getParamName(), var ) )
		{
			//
			throw new SemanticException( "the parameter \"" + node.getParamName() + "\" is already defined in \"" 
			+ ((ASTFunctionDeclaration)func_symb.getTree()).getFuncName() +"\"", unit.toString(), 
			(SimpleNode) func_symb.getTree(), node.getTokenParamName() );
		}
		
		return PortugolParserConstants.VOID;
	}

	public Object visit(ASTStructDeclaration node, Object data) throws Exception
	{
		//-------------------
		//unimplemented
		//-------------------
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTStatements node, Object data) throws Exception
	{
		//visit chidrens
		node.childrenAccept(this, null);
				
		return null;
	}

	public Object visit(ASTReturnStatement node, Object data) throws Exception
	{
		
		if( node.jjtGetNumChildren() > 0)
		{			
			checkExpressionType( (SimpleNode) node.jjtGetChild(0) , curr_return_type );
		}
		else if( curr_return_type != PortugolParserConstants.VOID )
		{
			throw new SemanticException( "This function must not return a value.",
				unit.toString(), (SimpleNode)node.jjtGetParent(), node.jjtGetFirstToken() );  
		}
								
		return null;
	}
	
	public Object visit(ASTIfStatement node, Object data) throws Exception
	{				
		checkExpressionType( (SimpleNode) node.jjtGetChild(0) , PortugolParserConstants.BOOL );
		
		symtab.pushScope();
		
		for(int i=1; i<node.jjtGetNumChildren();i++){
			node.jjtGetChild(i).jjtAccept(this, null );
		}
				
		symtab.popScope();
	
		return null;
	}
	
	public Object visit(ASTSwitchStatement node, Object data) throws Exception
	{
		//<SWITCH> Expression() <EOL>	
		int result_type = (Integer) node.jjtGetChild(0).jjtAccept( this, null );
		
		for(int i=1; i<node.jjtGetNumChildren();i++){			
			//SwitchLabel()
			//Statements()		
			if( i % 2 == 0 )
				node.jjtGetChild(i).jjtAccept(this, null );			
			else
				node.jjtGetChild(i).jjtAccept(this, result_type );			
		}			
		
		return null;
	}
	
	public Object visit(ASTSwitchLabel node, Object data) throws Exception
	{
		int switch_type = (Integer) data;
		
		if( !node.isDefault() )
			checkExpressionType( (SimpleNode) node.jjtGetChild(0) , switch_type );
		
		return null;
	}
	
	public Object visit(ASTWhileDoStatement node, Object data) throws Exception
	{		
		checkExpressionType( (SimpleNode) node.jjtGetChild(0) , PortugolParserConstants.BOOL );
		
		symtab.pushScope();	
		
		node.jjtGetChild(1).jjtAccept(this, null );
		
		symtab.popScope();
		
		return null;
	}
	
	public Object visit(ASTDoWhileStatement node, Object data) throws Exception
	{
				
		symtab.pushScope();	
		
		node.jjtGetChild(0).jjtAccept(this, null );
		
		symtab.popScope();
		
		checkExpressionType( (SimpleNode) node.jjtGetChild(1) , PortugolParserConstants.BOOL );
		
		return null;
	}
	
	public Object visit(ASTForStatement node, Object data) throws Exception
	{	
		//only real or int expressions	
				
		symtab.pushScope();
		
		int n_childs = node.jjtGetNumChildren();
		
		//n_childs == 5 // step		
		//n_childs == 4 // no step
				
		int type = (Integer) node.jjtGetChild( 0 ).jjtAccept(this, null );
			
		if( type != PortugolParserConstants.INT && type != PortugolParserConstants.REAL )	
		{				
				ASTVariableName node_var = ( ASTVariableName )node.jjtGetChild( 0 );
				throw new SemanticException( "variable \"" + node_var.getVarName() + "\" must be the type of " + 
					PortugolParserConstants.tokenImage[ PortugolParserConstants.INT ] + " or " +
					PortugolParserConstants.tokenImage[ PortugolParserConstants.REAL ] , unit.toString(), 
					(SimpleNode) unit.getTree(), node_var.getTokenVarName() );
		}
		
		type = (Integer) node.jjtGetChild( 1 ).jjtAccept(this, null );
		if( type != PortugolParserConstants.INT && type != PortugolParserConstants.REAL )	
		{	
				SimpleNode node_var = ( SimpleNode )node.jjtGetChild( 1 );
				throw new SemanticException( "The expression must be the type of " + 
				PortugolParserConstants.tokenImage[ PortugolParserConstants.INT ] + " or " +
				PortugolParserConstants.tokenImage[ PortugolParserConstants.REAL ] , unit.toString(), 
					(SimpleNode) unit.getTree(), node_var.jjtGetFirstToken() );
		}
		
		type = (Integer) node.jjtGetChild( 2 ).jjtAccept(this, null );
		if( type != PortugolParserConstants.INT && type != PortugolParserConstants.REAL )	
		{	
				SimpleNode node_var = ( SimpleNode )node.jjtGetChild( 2 );
				throw new SemanticException( "The expression must be the type of " + 
				PortugolParserConstants.tokenImage[ PortugolParserConstants.INT ] + " or " +
				PortugolParserConstants.tokenImage[ PortugolParserConstants.REAL ] , unit.toString(), 
					(SimpleNode) unit.getTree(), node_var.jjtGetFirstToken() );
		}		
		
		if( n_childs == 5 )
		{			
			type = (Integer) node.jjtGetChild( 3 ).jjtAccept(this, null );
			
			if( type != PortugolParserConstants.INT && type != PortugolParserConstants.REAL )	
			{	
					SimpleNode node_var = ( SimpleNode )node.jjtGetChild( 3 );
					throw new SemanticException( "The expression must be the type of " + 
					PortugolParserConstants.tokenImage[ PortugolParserConstants.INT ] + " or " +
					PortugolParserConstants.tokenImage[ PortugolParserConstants.REAL ] , unit.toString(), 
						(SimpleNode) unit.getTree(), node_var.jjtGetFirstToken() );
			}
		}
	
		//Statements
		node.jjtGetChild(node.jjtGetNumChildren()-1).jjtAccept(this, null );

/*
	<FOR> VariableName() <OF> Expression() <TO> Expression() [ <STEP> Expression() ] <EOL>
		Statements()
	<NEXT>
	
		ForStatement
		  VariableName [ x ]
		  IntNode [ 1 ]
		  IntNode [ 10 ]
		  IntNode [ 1 ]
		  Statements
 */
		symtab.popScope();
		
		return null;
	}
	
	public Object visit(ASTCallFunction node, Object data) throws Exception
	{		

		//if( node.getFuncName().equals("main") )
		//				throw new SemanticException( "function \"" + node.getFuncName() + "\" cannot be found", unit.toString(), 
		//	(SimpleNode) unit.getTree(), node.jjtGetFirstToken() );	

		int func_type = PortugolParserConstants.VOID;
		
		List parameters = new LinkedList();
		
		for(int i=0; i<node.jjtGetNumChildren();i++)//iterate for all parameters..
		{			
			parameters.add( (Integer)node.jjtGetChild(i).jjtAccept(this, null) );			
		}

		FunctionSymbol func =  (FunctionSymbol) unit.lookupFunction( node.getFuncName(),  parameters );	

		if(func == null)
		{
			String params_str = "( ";
			for( Object obj : parameters )
			{
				String type_str = PortugolParserConstants.tokenImage[ (Integer)obj ];
				type_str = type_str.substring( 1 , type_str.length()-1 );				
				params_str = params_str + type_str + ", ";
			}
				
			if( parameters.size() > 0 )			
				params_str = params_str.substring(0, params_str.length()-2) + " )";
			
			throw new SemanticException( "function \"" + node.getFuncName() + params_str + "\" cannot be found", unit.toString(), 
			(SimpleNode) unit.getTree(), node.jjtGetFirstToken() );			
		}

		func_type = func.getReturnType();
		
		return func_type;
		
	}

	public Object visit(ASTAssignmentStatement node, Object data) throws Exception
	{		
		checkBinaryOperation(node, PortugolParserConstants.OP_ASSIGN );
		return PortugolParserConstants.VOID;
	}

	public Object visit(ASTOrNode node, Object data) throws Exception
	{	
		return checkBinaryOperation(node, PortugolParserConstants.OR );
	}

	public Object visit(ASTAndNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.AND );
	}

	public Object visit(ASTXorNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.XOR );
	}

	public Object visit(ASTEqNode node, Object data) throws Exception
	{		
		return checkBinaryOperation(node, PortugolParserConstants.OP_E );
	}

	public Object visit(ASTNEqNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_NEQ );
	}

	public Object visit(ASTGNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_G );
	}

	public Object visit(ASTLNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_L );
	}

	public Object visit(ASTGENode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_GE );
	}

	public Object visit(ASTLENode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_LE );
	}

	public Object visit(ASTAddNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_PLUS );		
	}

	public Object visit(ASTSubNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_MINUS );						
	}

	public Object visit(ASTMulNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_MUL );
	}

	public Object visit(ASTDivNode node, Object data) throws Exception
	{
		return checkBinaryOperation(node, PortugolParserConstants.OP_DIV );		
	}
	
	public Object visit(ASTModNode node, Object data) throws Exception
	{		
		return checkBinaryOperation(node, PortugolParserConstants.OP_MOD );		
	}

	public Object visit(ASTNegNode node, Object data) throws Exception
	{					
		return checkUnaryOperation( node, PortugolParserConstants.OP_MINUS );		
	}

	public Object visit(ASTNotNode node, Object data) throws Exception
	{
		return checkUnaryOperation( node, PortugolParserConstants.NOT );	
	}

	public Object visit(ASTVariableName node, Object data) throws Exception
	{	
		int result_type = TypeCheck.TYPE_ERROR;
		
		if( symtab.getCurrentLevel() == 0 )//global mode
		{
			VariableSymbol var = (VariableSymbol) symtab.lookup( node.getVarName() );
			
			if( var == null && unit.lookupGlobal( node.getVarName() ) != null)
			{						
				throw new SemanticException( "illegal forward reference of " + node.getVarName(), unit.toString(), 
					(SimpleNode) unit.getTree() , node.getTokenVarName() );					
			}
			else if( var == null )
			{
				throw new SemanticException( "variable \"" + node.getVarName() + "\" is not defined" , unit.toString(), 
				(SimpleNode) unit.getTree(), node.getTokenVarName() );								
			}
			
			result_type = var.getTypeSpecifier();
			
		}
		else //local mode
		{
			VariableSymbol var = (VariableSymbol) symtab.lookup( node.getVarName() );

			if( var == null )
				var = (VariableSymbol) unit.lookupGlobal( node.getVarName() );

			
			if( var == null )
				throw new SemanticException( "variable \"" + node.getVarName() + "\" is not defined" , unit.toString(), 
					(SimpleNode) unit.getTree(), node.getTokenVarName() );				
			
			result_type = var.getTypeSpecifier();
		}
		
		return result_type;
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
		return PortugolParserConstants.CHAR;
	}

	public Object visit(ASTIntNode node, Object data) throws Exception
	{
		return PortugolParserConstants.INT;
	}

	public Object visit(ASTRealNode node, Object data) throws Exception
	{
		return PortugolParserConstants.REAL;
	}

	public Object visit(ASTStringNode node, Object data) throws Exception
	{
		return PortugolParserConstants.STRING;
	}

	public Object visit(ASTTrueNode node, Object data) throws Exception
	{
		return PortugolParserConstants.BOOL;
	}

	public Object visit(ASTFalseNode node, Object data) throws Exception
	{
		return PortugolParserConstants.BOOL;
	}

	public Object visit(ASTNullNode node, Object data) throws Exception
	{
		return PortugolParserConstants.VOID;		
	}

}