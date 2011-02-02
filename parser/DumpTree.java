class DumpTree implements PortugolParserVisitor
{		  
	public Object genericVisit(SimpleNode node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString() );
		node.childrenAccept(this, prefix + " ");
			
		return data;
	}

	public Object visit(SimpleNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTStart node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTImportDeclaration node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString()+ " [ " + node.getUnitName() + " ]");
		node.childrenAccept(this, prefix + " ");
		return data;
	}

	public Object visit(ASTVariableDeclarationList node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTVariableDeclaration node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString()+ " [ " + node.getVarName() + " ]");
		node.childrenAccept(this, prefix + " ");
		return data;
	}
	
	public Object visit(ASTArrayDims node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTArrayInitializer node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTFunctionDeclaration node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString()+ " [ " + node.getFuncName() + " ]");
		node.childrenAccept(this, prefix + " ");
		return data;
	}

	public Object visit(ASTFormalParametersList node, Object data) throws Exception
	{		
		return genericVisit(node, data);
	}

	public Object visit(ASTFormalParameter node, Object data) throws Exception
	{
		String prefix = (String)data;		
		System.out.println( prefix + node.toString()+ " [ " + node.getTokenParamType().image + " " + node.getParamName() + " ]");
		node.childrenAccept(this, prefix + " ");
		 		 
		return data;
	}

	public Object visit(ASTStructDeclaration node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString()+ " [ " + node.getStructName() + " ]");
		node.childrenAccept(this, prefix + " ");
		return data;
	}

	public Object visit(ASTStatements node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
/*
	public Object visit(ASTStatement node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	*/
	public Object visit(ASTReturnStatement node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTIfStatement node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTSwitchStatement node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTSwitchLabel node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTWhileDoStatement node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTDoWhileStatement node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTForStatement node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTCallFunction node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTAssignmentStatement node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTOrNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTAndNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTXorNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTEqNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTNEqNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTGNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTLNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTGENode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTLENode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTAddNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTSubNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTMulNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTDivNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTModNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTNegNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTNotNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTVariableName node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString()+ " [ " + node.getVarName() + " ]");
		node.childrenAccept(this, prefix + " ");
		return data;
	}
  
	public Object visit(ASTIndexSelector node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
	
	public Object visit(ASTFieldSelector node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}
  
	public Object visit(ASTCharNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTIntNode node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString()+ " [ " + node.value + " ]");
		node.childrenAccept(this, prefix + " ");
		return data;
	}

	public Object visit(ASTRealNode node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString()+ " [ " + node.value + " ]");
		node.childrenAccept(this, prefix + " ");
		return data;
	}

	public Object visit(ASTStringNode node, Object data) throws Exception
	{
		String prefix = (String)data;
		System.out.println( prefix + node.toString()+ " [ " + node.value + " ]");
		node.childrenAccept(this, prefix + " ");
		return data;
	}

	public Object visit(ASTTrueNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTFalseNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

	public Object visit(ASTNullNode node, Object data) throws Exception
	{
		return genericVisit(node, data);
	}

}