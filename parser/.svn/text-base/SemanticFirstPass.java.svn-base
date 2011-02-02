
/*
First pass of semantinc check..
-check if the imports exist
-Fill the symbtab with global/functions/structures/imports


vai ficar a para fazer a function overloading
*/

import java.util.TreeMap;
import java.io.*;
			
class SemanticFirstPass implements PortugolParserVisitor
{		  
	UnitCompiler unit;
	TreeMap<String, UnitCompiler> unit_compiler_map;
	
	SemanticFirstPass(UnitCompiler unit, TreeMap<String, UnitCompiler> unit_compiler_map )	
	{
		this.unit = unit;
		this.unit_compiler_map = unit_compiler_map;
	}
	
	public Object genericVisit(SimpleNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(SimpleNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTStart node, Object data) throws Exception
	{
		//System.out.println( "start" );
		node.childrenAccept(this, data);
		return data;
	}
	
	public Object visit(ASTImportDeclaration node, Object data) throws Exception
	{
		//------
		//imports
		
		//check if node.unit_name exist in the inut list, if not search on the libs
		//the unit can not import them self..
		
		//ImportSymbol imp = new ImportSymbol(node.unit_name);
		
		//get_unit()	
		
		//System.out.println( "import" );
		
		//System.out.println( unit.toString() + " " + node.getUnitName() );
		
		//if( unit.toString().equals( node.getUnitName() + ".txt" ) )
		if( unit.toString().equals( node.getUnitName() ) )
		{
			throw new SemanticException( "the unit \"" + node.getUnitName() + "\" can not import him self..", node.getUnitName(), node, node.getTokenUnitName() );
			//return data;
		}
		
		//UnitCompiler imp = unit_compiler_map.get( node.getUnitName() + ".txt" );
		UnitCompiler imp = unit_compiler_map.get( node.getUnitName() );
		
		if( imp == null ) //is not a .portugol unit
		{
			
			//is a portugol lib?
			//search on curr dir.. 
			File f = new File( node.getUnitName() + ".class" );
			
			if( f.exists() )
			{
				imp = new UnitCompiler( node.getUnitName(), true );				
			}
			else
			{
				throw new SemanticException( "the unit \"" + node.getUnitName() + "\" do not exist..", node.getUnitName(), node, node.getTokenUnitName() );
			}
			
		}
		
		//if( !unit.insertImport( node.getUnitName() + ".txt", imp ) )
		if( !unit.insertImport( node.getUnitName() , imp ) )
		{
			throw new SemanticException( "the unit \"" + node.getUnitName() + "\" is already included..", node.getUnitName(), node, node.getTokenUnitName()	);
		}
		
		//node.childrenAccept(this, data);
		
		return data;
		
	}
	
	public Object visit(ASTVariableDeclarationList node, Object data) throws Exception
	{
		node.childrenAccept(this, data);
		return data;
	}

	public Object visit(ASTVariableDeclaration node, Object data) throws Exception
	{		
		//System.out.println( "var declaration" );
		
		int type_quali = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTokenTypeQualifier().kind;
		int type_spec = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTokenTypeSpecifier().kind;
		String type_spec_custom = ( (ASTVariableDeclarationList) node.jjtGetParent() ).getTypeSpecifier();
						
		VariableSymbol var = null;

		if( type_spec == PortugolParserConstants.IDENTIFIER )
			var = new VariableSymbol(type_quali, type_spec_custom);
		else 
			var = new VariableSymbol(type_quali, type_spec);
						
		if( !unit.insertGlobal( node.getVarName(), var ) )
		{
			throw new SemanticException( "the variable \"" + node.getVarName() + "\" is already declared", unit.toString(), (SimpleNode)node.jjtGetParent(), node.getTokenVarName() );
		}
			
		return data;
	}
	
	public Object visit(ASTArrayDims node, Object data) throws Exception
	{
		//
		// unimplemented
		//
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTArrayInitializer node, Object data) throws Exception
	{
		//
		// unimplemented
		//
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTFunctionDeclaration node, Object data) throws Exception
	{
		//String prefix = (String)data;
		//System.out.println( prefix + node.toString()+ " [ " + node.getFuncName() + " ]");
		
		int return_type = node.getTokenReturnType().kind;
		String return_type_custom = node.getTokenReturnType().image;
		
		FunctionSymbol func = null;

		if( return_type == PortugolParserConstants.IDENTIFIER )
			func = new FunctionSymbol( node.getFuncName(), unit, return_type_custom, node );
		else 
			func = new FunctionSymbol( node.getFuncName(), unit, return_type, node );
		
		if( !unit.insertFunction( node.getFuncName(), func ) )
		{
			throw new SemanticException( "the function \"" + node.getFuncName() + "\" is already declared", unit.toString(), (SimpleNode)node , node.getTokenFuncName() );
		}
		
		node.jjtGetChild(0).jjtAccept(this, func );		
		
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
						
//		VariableSymbol var = null;
//
//		if( type == PortugolParserConstants.IDENTIFIER )
//			var = new VariableSymbol(type_custom);
//		else 
//			var = new VariableSymbol(type);
						
		//if( !func_symb.insertParameter( node.getParamName(), var ) )
		if( !func_symb.insertParameter( type ) )
		{
			throw new SemanticException( "the parameter \"" + node.getParamName() + "\" is already defined in \"" 
			+ ((ASTFunctionDeclaration)func_symb.getTree()).getFuncName() +"\"", unit.toString(), 
			(SimpleNode) func_symb.getTree(), node.getTokenParamName() );
		}
		
		return null;
	}

	public Object visit(ASTStructDeclaration node, Object data) throws Exception
	{
		//-------------------
		//unimplemented
		//-------------------
		
		//String prefix = (String)data;
		//System.out.println( prefix + node.toString()+ " [ " + node.getStructName() + " ]");
		//node.childrenAccept(this, prefix + " ");
		
		return data;
	}

	public Object visit(ASTStatements node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTReturnStatement node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTIfStatement node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTSwitchStatement node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTSwitchLabel node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTWhileDoStatement node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTDoWhileStatement node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTForStatement node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}
	
	public Object visit(ASTCallFunction node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTAssignmentStatement node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTOrNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTAndNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTXorNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTEqNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTNEqNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTGNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTLNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTGENode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTLENode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTAddNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTSubNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTMulNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTDivNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTModNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTNegNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTNotNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTVariableName node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
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
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTIntNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTRealNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTStringNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTTrueNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTFalseNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

	public Object visit(ASTNullNode node, Object data) throws Exception
	{
		throw new UnsupportedOperationException(); // It better not come here.
	}

}