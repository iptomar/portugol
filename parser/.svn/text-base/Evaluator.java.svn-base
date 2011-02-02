


//import java.lang.reflect.Method;


// this operations need to be sync with the tables form TypeChecker
class Evaluator
{
	public static Object evaluate(int op, Object lvalue, Object rvalue)
	{
						
		switch( op )
		{	
			case PortugolParserConstants.OP_PLUS:
				
				if ( lvalue instanceof java.lang.Boolean )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return add( (Boolean)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return add( (Boolean)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return add( (Boolean)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return add( (Boolean)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return add( (Boolean)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Character )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return add( (Character)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return add( (Character)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return add( (Character)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return add( (Character)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return add( (Character)lvalue, (String)rvalue );
					}
				}				
				else if ( lvalue instanceof java.lang.Integer )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return add( (Integer)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return add( (Integer)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{
						return add( (Integer)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return add( (Integer)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return add( (Integer)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Double )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return add( (Double)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return add( (Double)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return add( (Double)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return add( (Double)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return add( (Double)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.String )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return add( (String)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return add( (String)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return add( (String)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return add( (String)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return add( (String)lvalue, (String)rvalue );
					}
				}
								
				break;
			case PortugolParserConstants.OP_MINUS:
				if ( lvalue instanceof java.lang.Boolean )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return sub( (Boolean)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return sub( (Boolean)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return sub( (Boolean)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return sub( (Boolean)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return sub( (Boolean)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Character )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return sub( (Character)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return sub( (Character)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return sub( (Character)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return sub( (Character)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return sub( (Character)lvalue, (String)rvalue );
					}
				}				
				else if ( lvalue instanceof java.lang.Integer )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return sub( (Integer)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return sub( (Integer)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{
						return sub( (Integer)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return sub( (Integer)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return sub( (Integer)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Double )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return sub( (Double)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return sub( (Double)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return sub( (Double)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return sub( (Double)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return sub( (Double)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.String )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return sub( (String)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return sub( (String)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return sub( (String)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return sub( (String)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return sub( (String)lvalue, (String)rvalue );
					}
				}
				
				break;				
			case PortugolParserConstants.OP_MUL:
				if ( lvalue instanceof java.lang.Boolean )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mul( (Boolean)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mul( (Boolean)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return mul( (Boolean)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mul( (Boolean)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mul( (Boolean)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Character )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mul( (Character)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mul( (Character)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return mul( (Character)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mul( (Character)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mul( (Character)lvalue, (String)rvalue );
					}
				}				
				else if ( lvalue instanceof java.lang.Integer )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mul( (Integer)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mul( (Integer)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{
						return mul( (Integer)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mul( (Integer)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mul( (Integer)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Double )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mul( (Double)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mul( (Double)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return mul( (Double)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mul( (Double)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mul( (Double)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.String )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mul( (String)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mul( (String)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return mul( (String)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mul( (String)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mul( (String)lvalue, (String)rvalue );
					}
				}
				
				break;
			case PortugolParserConstants.OP_DIV:
				if ( lvalue instanceof java.lang.Boolean )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return div( (Boolean)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return div( (Boolean)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return div( (Boolean)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return div( (Boolean)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return div( (Boolean)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Character )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return div( (Character)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return div( (Character)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return div( (Character)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return div( (Character)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return div( (Character)lvalue, (String)rvalue );
					}
				}				
				else if ( lvalue instanceof java.lang.Integer )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return div( (Integer)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return div( (Integer)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{
						return div( (Integer)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return div( (Integer)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return div( (Integer)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Double )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return div( (Double)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return div( (Double)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return div( (Double)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return div( (Double)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return div( (Double)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.String )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return div( (String)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return div( (String)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return div( (String)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return div( (String)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return div( (String)lvalue, (String)rvalue );
					}
				}
				
				break;
			case PortugolParserConstants.OP_MOD:		
				if ( lvalue instanceof java.lang.Boolean )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mod( (Boolean)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mod( (Boolean)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return mod( (Boolean)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mod( (Boolean)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mod( (Boolean)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Character )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mod( (Character)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mod( (Character)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return mod( (Character)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mod( (Character)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mod( (Character)lvalue, (String)rvalue );
					}
				}				
				else if ( lvalue instanceof java.lang.Integer )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mod( (Integer)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mod( (Integer)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{
						return mod( (Integer)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mod( (Integer)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mod( (Integer)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Double )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mod( (Double)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mod( (Double)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return mod( (Double)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mod( (Double)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mod( (Double)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.String )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return mod( (String)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return mod( (String)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return mod( (String)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return mod( (String)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return mod( (String)lvalue, (String)rvalue );
					}
				}
				
				break;
			case PortugolParserConstants.OP_GE:
			case PortugolParserConstants.OP_LE:
			case PortugolParserConstants.OP_G:
			case PortugolParserConstants.OP_L:		
				if ( lvalue instanceof java.lang.Boolean )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return relate( op ,(Boolean)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return relate( op, (Boolean)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return relate( op, (Boolean)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return relate( op, (Boolean)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return relate( op, (Boolean)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Character )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return relate( op, (Character)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return relate( op, (Character)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return relate( op, (Character)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return relate( op, (Character)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return relate( op, (Character)lvalue, (String)rvalue );
					}
				}				
				else if ( lvalue instanceof java.lang.Integer )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return relate( op, (Integer)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return relate( op, (Integer)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{
						return relate( op, (Integer)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return relate( op, (Integer)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return relate( op, (Integer)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Double )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return relate( op, (Double)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return relate( op, (Double)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return relate( op, (Double)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return relate( op, (Double)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return relate( op, (Double)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.String )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return relate( op, (String)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return relate( op, (String)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return relate( op, (String)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return relate( op, (String)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return relate( op, (String)lvalue, (String)rvalue );
					}
				}

				break;
			case PortugolParserConstants.OP_E:
			case PortugolParserConstants.OP_NEQ:
				if ( lvalue instanceof java.lang.Boolean )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return equals( op, (Boolean)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return equals( op, (Boolean)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return equals( op, (Boolean)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return equals( op, (Boolean)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return equals( op, (Boolean)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Character )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return equals( op, (Character)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return equals( op, (Character)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return equals( op, (Character)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return equals( op, (Character)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return equals( op, (Character)lvalue, (String)rvalue );
					}
				}				
				else if ( lvalue instanceof java.lang.Integer )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return equals( op, (Integer)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return equals( op, (Integer)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{
						return equals( op, (Integer)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return equals( op, (Integer)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return equals( op, (Integer)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Double )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return equals( op, (Double)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return equals( op, (Double)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return equals( op, (Double)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return equals( op, (Double)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return equals( op, (Double)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.String )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return equals( op, (String)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return equals( op, (String)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return equals( op, (String)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return equals( op, (String)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return equals( op, (String)lvalue, (String)rvalue );
					}
				}

				break;
			case PortugolParserConstants.AND:
			case PortugolParserConstants.OR:
			//case PortugolParserConstants.NAND:
			//case PortugolParserConstants.NOR:
			case PortugolParserConstants.XOR:			
				if ( lvalue instanceof java.lang.Boolean )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return condicional( op, (Boolean)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return condicional( op, (Boolean)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return condicional( op, (Boolean)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return condicional( op, (Boolean)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return condicional( op, (Boolean)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Character )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return condicional( op, (Character)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return condicional( op, (Character)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return condicional( op, (Character)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return condicional( op, (Character)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return condicional( op, (Character)lvalue, (String)rvalue );
					}
				}				
				else if ( lvalue instanceof java.lang.Integer )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return condicional( op, (Integer)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return condicional( op, (Integer)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{
						return condicional( op, (Integer)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return condicional( op, (Integer)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return condicional( op, (Integer)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.Double )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return condicional( op, (Double)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return condicional( op, (Double)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return condicional( op, (Double)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return condicional( op, (Double)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return condicional( op, (Double)lvalue, (String)rvalue );
					}
				}
				else if ( lvalue instanceof java.lang.String )
				{
					if ( rvalue instanceof java.lang.Boolean )
					{
						return condicional( op, (String)lvalue, (Boolean)rvalue );
					}
					else if ( rvalue instanceof java.lang.Character )
					{
						return condicional( op, (String)lvalue, (Character)rvalue );
					}				
					else if ( rvalue instanceof java.lang.Integer )
					{						
						return condicional( op, (String)lvalue, (Integer)rvalue );
					}
					else if ( rvalue instanceof java.lang.Double )
					{
						return condicional( op, (String)lvalue, (Double)rvalue );
					}
					else if ( rvalue instanceof java.lang.String )
					{
						return condicional( op, (String)lvalue, (String)rvalue );
					}
				}

				break;

			default:		
		}
		
		return null;
	}
	
	public static Object evaluate(int op, Object rvalue)
	{
		switch( op )
		{
			//case PortugolParserConstants.OP_PLUS:
			case PortugolParserConstants.OP_MINUS:
				if ( rvalue instanceof java.lang.Boolean )
				{
					return negate( (Boolean)rvalue );
				}
				else if ( rvalue instanceof java.lang.Character )
				{
					return negate( (Character)rvalue );
				}				
				else if ( rvalue instanceof java.lang.Integer )
				{						
					return negate( (Integer)rvalue );
				}
				else if ( rvalue instanceof java.lang.Double )
				{
					return negate( (Double)rvalue );
				}
				else if ( rvalue instanceof java.lang.String )
				{					
					return negate( (String)rvalue );
				}			

				break;
			case PortugolParserConstants.NOT:			
				if ( rvalue instanceof java.lang.Boolean )
				{
					return not( (Boolean)rvalue );
				}
				else if ( rvalue instanceof java.lang.Character )
				{
					return not( (Character)rvalue );
				}				
				else if ( rvalue instanceof java.lang.Integer )
				{						
					return not( (Integer)rvalue );
				}
				else if ( rvalue instanceof java.lang.Double )
				{
					return not( (Double)rvalue );
				}
				else if ( rvalue instanceof java.lang.String )
				{					
					return not( (String)rvalue );
				}			

				break;
		}
	
		return null;
	}
	
	public static Object  cast( int type, boolean reference, Object rvalue )
	{
		switch( type )
		{
			case PortugolParserConstants.BOOL:
			
				if ( rvalue instanceof java.lang.Boolean )
				{
					return cast_to_bool( (Boolean)rvalue, reference);
				}
				else if ( rvalue instanceof java.lang.Character )
				{
					return cast_to_bool( (Character)rvalue, reference);
				}				
				else if ( rvalue instanceof java.lang.Integer )
				{						
					return cast_to_bool( (Integer)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.Double )
				{
					return cast_to_bool( (Double)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.String )
				{					
					return cast_to_bool( (String)rvalue, reference );
				}	
				
				break;
				
			case PortugolParserConstants.CHAR:
			
				if ( rvalue instanceof java.lang.Boolean )
				{
					return cast_to_char( (Boolean)rvalue, reference);
				}
				else if ( rvalue instanceof java.lang.Character )
				{
					return cast_to_char( (Character)rvalue, reference);
				}				
				else if ( rvalue instanceof java.lang.Integer )
				{						
					return cast_to_char( (Integer)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.Double )
				{
					return cast_to_char( (Double)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.String )
				{					
					return cast_to_char( (String)rvalue, reference );
				}	
				
				break;
				
			case PortugolParserConstants.INT:
			
				if ( rvalue instanceof java.lang.Boolean )
				{
					return cast_to_int( (Boolean)rvalue, reference);
				}
				else if ( rvalue instanceof java.lang.Character )
				{
					return cast_to_int( (Character)rvalue, reference);
				}				
				else if ( rvalue instanceof java.lang.Integer )
				{						
					return cast_to_int( (Integer)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.Double )
				{
					return cast_to_int( (Double)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.String )
				{					
					return cast_to_int( (String)rvalue, reference );
				}	
				
				break;
				
			case PortugolParserConstants.REAL:
			
				if ( rvalue instanceof java.lang.Boolean )
				{
					return cast_to_real( (Boolean)rvalue, reference);
				}
				else if ( rvalue instanceof java.lang.Character )
				{
					return cast_to_real( (Character)rvalue, reference);
				}				
				else if ( rvalue instanceof java.lang.Integer )
				{						
					return cast_to_real( (Integer)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.Double )
				{
					return cast_to_real( (Double)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.String )
				{					
					return cast_to_real( (String)rvalue, reference );
				}	
				
				break;
				
			case PortugolParserConstants.STRING:	
			
				if ( rvalue instanceof java.lang.Boolean )
				{
					return cast_to_string( (Boolean)rvalue, reference);
				}
				else if ( rvalue instanceof java.lang.Character )
				{
					return cast_to_string( (Character)rvalue, reference);
				}				
				else if ( rvalue instanceof java.lang.Integer )
				{						
					return cast_to_string( (Integer)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.Double )
				{
					return cast_to_string( (Double)rvalue, reference );
				}
				else if ( rvalue instanceof java.lang.String )
				{					
					return cast_to_string( (String)rvalue, reference );
				}	
		}
		
		return null;	
	}
	
	
	//{ TYPE_ERROR, TYPE_ERROR, INT, REAL, TYPE_ERROR };
	private static Object negate(Object rvalue)
	{
		return null;
	}
	
	private static Object negate(Integer rvalue)
	{
		return new Integer( - rvalue.intValue()  );
	}
	
	private static Object negate(Double rvalue)
	{
		return new Double( - rvalue.doubleValue()  );
	}
		
	//{ BOOL, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR };	
	private static Object not(Object rvalue)
	{
		 return null;
	}
	
	private static Object not(Boolean rvalue)
	{
		 return new Boolean( !rvalue.booleanValue() );
	}
	
	//----------------
	// ADD Operator
	//---------------

	//{ { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, STRING } ,
	//  { TYPE_ERROR,       CHAR,        INT, TYPE_ERROR, STRING } ,
	//  { TYPE_ERROR,        INT,        INT,       REAL, STRING } ,
	//  { TYPE_ERROR, TYPE_ERROR,        REAL,      REAL, STRING } ,
	//  { STRING    ,     STRING,     STRING,     STRING, STRING } };
	
	private static Object add( Object lvalue, Object rvalue)
	{	
		//System.out.println( "obj" );
		//System.out.println( lvalue );
		//System.out.println( rvalue );
		return null;
	}
	
	//-- Character
	private static Object add( Character lvalue, Character rvalue)
	{	
		char r = (char)(lvalue.charValue() + rvalue.charValue());
		return new Character( r );
	}
	
	private static Object add( Character lvalue, Integer rvalue)
	{	
		return new Integer( lvalue.charValue() + rvalue.intValue() );
	}	
	//---
	
	//Integer
	private static Object add( Integer lvalue, Character rvalue)
	{	
		return new Integer( lvalue.intValue() + rvalue.charValue() );
	}
	
	private static Object add( Integer lvalue, Integer rvalue)
	{	
		//System.out.println( "int" );
		//System.out.println( lvalue );
		//System.out.println( rvalue );
		return new Integer( lvalue.intValue() + rvalue.intValue() );
	}
	
	private static Object add( Integer lvalue, Double rvalue)
	{	
		return new Double( lvalue.intValue() + rvalue.doubleValue() );
	}
	
	private static Object add( Integer lvalue, String rvalue)
	{		
		return new String( lvalue.intValue() + rvalue );
	}
	//---
	
	//Double
	private static Object add( Double lvalue, Integer rvalue)
	{	
		return new Double( lvalue.doubleValue() + rvalue.intValue() );
	}
	
	private static Object add( Double lvalue, Double rvalue)
	{	
		return new Double( lvalue.doubleValue() + rvalue.doubleValue() );
	}
	
	private static Object add( Double lvalue, String rvalue)
	{	
		return new String( lvalue.toString() + rvalue.toString() );
	}
	//---
	
	//String
	private static Object add( String lvalue, Boolean rvalue )
	{	
		String str_rvalue = PortugolParserConstants.tokenImage[ PortugolParserConstants.FALSE ].substring(1, PortugolParserConstants.tokenImage[ PortugolParserConstants.FALSE ].length()-1 );
		
		if( rvalue.booleanValue() )
		{
			str_rvalue = PortugolParserConstants.tokenImage[ PortugolParserConstants.TRUE ].substring(1, PortugolParserConstants.tokenImage[ PortugolParserConstants.TRUE ].length()-1 );			
			return new String( lvalue.toString() + str_rvalue );
		}
		
		return new String( lvalue.toString() + str_rvalue );
	}
	
	private static Object add( String lvalue, Character rvalue )
	{					
		return new String( lvalue.toString() + rvalue.toString() );
	}
	
	private static Object add( String lvalue, Integer rvalue )
	{	
		return new String( lvalue.toString() + rvalue.toString() );
	}
		
	private static Object add( String lvalue, Double rvalue )
	{	
		return new String( lvalue.toString() + rvalue.toString() );
	}
	
	private static Object add( String lvalue, String rvalue )
	{	
		return new String( lvalue.toString() + rvalue.toString() );
	}
	
	//---
	
	//----------------
	// SUB Operator -
	//---------------
	// { { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	//  { TYPE_ERROR,       CHAR,        INT, TYPE_ERROR, TYPE_ERROR } ,
	//  { TYPE_ERROR,        INT,        INT,       REAL, TYPE_ERROR } ,
	//  { TYPE_ERROR, TYPE_ERROR,       REAL,       REAL, TYPE_ERROR } ,
	//  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };
	
	//---
	private static Object sub( Object lvalue, Object rvalue)
	{	
		return null;
	}
	
	//-- Character
	private static Object sub( Character lvalue, Character rvalue)
	{	
		char r = (char)(lvalue.charValue() - rvalue.charValue());
		return new Character( r );
	}
	
	private static Object sub( Character lvalue, Integer rvalue)
	{	
		return new Integer( lvalue.charValue() - rvalue.intValue() );
	}	
	//---
	
	//Integer
	private static Object sub( Integer lvalue, Character rvalue)
	{	
		return new Integer( lvalue.intValue() - rvalue.charValue() );
	}
	
	private static Object sub( Integer lvalue, Integer rvalue)
	{	
		return new Integer( lvalue.intValue() - rvalue.intValue() );
	}
	
	private static Object sub( Integer lvalue, Double rvalue)
	{	
		return new Double( lvalue.intValue() - rvalue.doubleValue() );
	}
	
	//---
	
	//Double
	private static Object sub( Double lvalue, Integer rvalue)
	{	
		return new Double( lvalue.doubleValue() - rvalue.intValue() );
	}
	
	private static Object sub( Double lvalue, Double rvalue)
	{	
		return new Double( lvalue.doubleValue() - rvalue.doubleValue() );
	}
		
	//-----------
	
	// MOD Operator %
	// { { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	//  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	//  { TYPE_ERROR, TYPE_ERROR,        INT, TYPE_ERROR, TYPE_ERROR } ,
	//  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };

	//----
	private static Object mod( Object lvalue, Object rvalue)
	{	
		return null;
	}
	
	//Integer
	private static Object mod( Integer lvalue, Integer rvalue)
	{	
		return new Integer( lvalue.intValue() % rvalue.intValue() );
	}
	
	//----------
	
	// MUL Operator *
	
	// { { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	//  { TYPE_ERROR, TYPE_ERROR,        INT,       REAL, TYPE_ERROR } ,
	//  { TYPE_ERROR, TYPE_ERROR,        INT,       REAL, TYPE_ERROR } ,
	//  {  TYPE_ERROR, TYPE_ERROR,       REAL,       REAL, TYPE_ERROR } ,
	//  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };
	
	//------
	private static Object mul( Object lvalue, Object rvalue)
	{	
		return null;
	}	
	
	//-- Character
	
	private static Object mul( Character lvalue, Integer rvalue)
	{	
		return new Integer( lvalue.charValue() * rvalue.intValue() );
	}	
	
	private static Object mul( Character lvalue, Double rvalue)
	{	
		return new Double( lvalue.charValue() * rvalue.doubleValue() );
	}	
	
	//---
	
	//Integer
	/*
	private static Object Mul( Integer lvalue, Character rvalue)
	{	
		return new Integer( lvalue.intValue() * rvalue.charValue() );
	}*/
	
	private static Object mul( Integer lvalue, Integer rvalue)
	{	
		return new Integer( lvalue.intValue() * rvalue.intValue() );
	}
	
	private static Object mul( Integer lvalue, Double rvalue)
	{	
		return new Double( lvalue.intValue() * rvalue.doubleValue() );
	}
	
	//---
	
	//Double
	private static Object mul( Double lvalue, Integer rvalue)
	{	
		return new Double( lvalue.doubleValue() * rvalue.intValue() );
	}
	
	private static Object mul( Double lvalue, Double rvalue)
	{	
		return new Double( lvalue.doubleValue() * rvalue.doubleValue() );
	}
	
	//-----
	
	// DIV operator /
	//----	
	// { { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR,        INT,       REAL, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR,       REAL,       REAL, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };
	
	private static Object div( Object lvalue, Object rvalue)
	{	
		return null;
	}
	
	//Integer
	
	private static Object div( Integer lvalue, Integer rvalue)
	{	
		return new Integer( lvalue.intValue() / rvalue.intValue() );
	}
	
	private static Object div( Integer lvalue, Double rvalue)
	{	
		return new Double( lvalue.intValue() / rvalue.doubleValue() );
	}
	
	//---
	
	//Double
	private static Object div( Double lvalue, Integer rvalue)
	{	
		return new Double( lvalue.doubleValue() / rvalue.intValue() );
	}
	
	private static Object div( Double lvalue, Double rvalue)
	{	
		return new Double( lvalue.doubleValue() / rvalue.doubleValue() );
	}
	
	//---
	//relational operators > , < , >= ,<=
	
	// { { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR,       BOOL,       BOOL, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR,       BOOL,       BOOL,       BOOL, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR,       BOOL,       BOOL, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR,      BOOL } };
	
	private static Object relate( int op, Object lvalue, Object rvalue )
	{	
		return null;
	}
		
	//-- Character
	
	private static Object relate( int op, Character lvalue, Character rvalue )
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_GE:
				return new Boolean( lvalue.charValue() >= rvalue.charValue() );
			case PortugolParserConstants.OP_LE:
				return new Boolean( lvalue.charValue() <= rvalue.charValue() );
			case PortugolParserConstants.OP_G:
				return new Boolean( lvalue.charValue() > rvalue.charValue() );
			case PortugolParserConstants.OP_L:	
				return new Boolean( lvalue.charValue() < rvalue.charValue() );		
		}
		
		return null;		
	}
	
	private static Object  relate( int op, Character lvalue, Integer rvalue )
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_GE:
				return new Boolean( lvalue.charValue() >= rvalue.intValue() );
			case PortugolParserConstants.OP_LE:
				return new Boolean( lvalue.charValue() <= rvalue.intValue() );
			case PortugolParserConstants.OP_G:
				return new Boolean( lvalue.charValue() > rvalue.intValue() );
			case PortugolParserConstants.OP_L:	
				return new Boolean( lvalue.charValue() < rvalue.intValue() );		
		}
		
		return null;
	}		
	
	//Integer
	
	private static Object relate( int op, Integer lvalue, Character rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_GE:
				return new Boolean( lvalue.intValue() >= rvalue.charValue() );
			case PortugolParserConstants.OP_LE:
				return new Boolean( lvalue.intValue() <= rvalue.charValue() );
			case PortugolParserConstants.OP_G:
				return new Boolean( lvalue.intValue() > rvalue.charValue() );
			case PortugolParserConstants.OP_L:	
				return new Boolean( lvalue.intValue() < rvalue.charValue() );		
		}
		
		return null;		
	}
	
	private static Object relate( int op, Integer lvalue, Integer rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_GE:
				return new Boolean( lvalue.intValue() >= rvalue.intValue() );
			case PortugolParserConstants.OP_LE:
				return new Boolean( lvalue.intValue() <= rvalue.intValue() );
			case PortugolParserConstants.OP_G:
				return new Boolean( lvalue.intValue() > rvalue.intValue() );
			case PortugolParserConstants.OP_L:	
				return new Boolean( lvalue.intValue() < rvalue.intValue() );		
		}
		
		return null;		
	}
	
	private static Object relate( int op, Integer lvalue, Double rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_GE:
				return new Boolean( lvalue.intValue() >= rvalue.doubleValue() );
			case PortugolParserConstants.OP_LE:
				return new Boolean( lvalue.intValue() <= rvalue.doubleValue() );
			case PortugolParserConstants.OP_G:
				return new Boolean( lvalue.intValue() > rvalue.doubleValue() );
			case PortugolParserConstants.OP_L:	
				return new Boolean( lvalue.intValue() < rvalue.doubleValue() );		
		}
		
		return null;
	}

	//-------
	//Double
	
	private static Object relate( int op, Double lvalue, Integer rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_GE:
				return new Boolean( lvalue.doubleValue() >= rvalue.intValue() );
			case PortugolParserConstants.OP_LE:
				return new Boolean( lvalue.doubleValue() <= rvalue.intValue() );
			case PortugolParserConstants.OP_G:
				return new Boolean( lvalue.doubleValue() > rvalue.intValue() );
			case PortugolParserConstants.OP_L:	
				return new Boolean( lvalue.doubleValue() < rvalue.intValue() );		
		}
		
		return null;
	}
	
	private static Object relate( int op, Double lvalue, Double rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_GE:
				return new Boolean( lvalue.doubleValue() >= rvalue.doubleValue() );
			case PortugolParserConstants.OP_LE:
				return new Boolean( lvalue.doubleValue() <= rvalue.doubleValue() );
			case PortugolParserConstants.OP_G:
				return new Boolean( lvalue.doubleValue() > rvalue.doubleValue() );
			case PortugolParserConstants.OP_L:	
				return new Boolean( lvalue.doubleValue() < rvalue.doubleValue() );		
		}
		
		return null;
	}
	
	//------
	//String
	
	private static Object relate( int op, String lvalue, String rvalue)
	{	
		//????????????????????????????????????
		switch( op )
		{/*
			case PortugolParserConstants.OP_GE:
				return new Boolean( lvalue >= rvalue );
			case PortugolParserConstants.OP_LE:
				return new Boolean( lvalue <= doubleValue() );
			case PortugolParserConstants.OP_G:
				return new Boolean( lvalue > rvalue );
			case PortugolParserConstants.OP_L:	
				return new Boolean( lvalue < rvalue );		
		*/
		}
		
		//????????????????????????????????
		return null;
	}
	//-----
	
	
	//equals operator  = //==, !=
	//
	// { {       BOOL, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR,       BOOL,       BOOL, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR,       BOOL,       BOOL,       BOOL, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR,       BOOL,       BOOL, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR,      BOOL } };
	
	private static Object equals( int op, Object lvalue, Object rvalue)
	{	
		return null;
	}
	
	//Boolean
	private static Object equals( int op, Boolean lvalue, Boolean rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.booleanValue() == rvalue.booleanValue() );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( lvalue.booleanValue() != rvalue.booleanValue() );		
		}
		return null;
	}
	
	//Character
	
	private static Object equals( int op, Character lvalue, Character rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.charValue() == rvalue.charValue() );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( lvalue.charValue() != rvalue.charValue() );		
		}
		return null;
	}	
	
	private static Object equals( int op, Character lvalue, Integer rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.charValue() == rvalue.intValue() );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( lvalue.charValue() != rvalue.intValue() );		
		}
		return null;
	}

	//Integer
	
	private static Object equals( int op, Integer lvalue, Character rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.intValue() == rvalue.charValue() );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( lvalue.intValue() != rvalue.charValue() );		
		}
		return null;
	}
	
	private static Object equals( int op, Integer lvalue, Integer rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.intValue() == rvalue.intValue() );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( lvalue.intValue() != rvalue.intValue() );		
		}
		return null;
	}	
	
	private static Object equals( int op, Integer lvalue, Double rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.intValue() == rvalue.doubleValue() );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( lvalue.intValue() != rvalue.doubleValue() );		
		}
		return null;
	}
	
	//Double
	
	private static Object equals( int op, Double lvalue, Integer rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.doubleValue() == rvalue.intValue() );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( lvalue.doubleValue() != rvalue.intValue() );		
		}
		return null;
	}	
	
	private static Object equals( int op, Double lvalue, Double rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.doubleValue() == rvalue.doubleValue() );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( lvalue.doubleValue() != rvalue.doubleValue() );		
		}
		return null;
	}
	
	//String
	
	private static Object equals( int op, String lvalue, String rvalue)
	{	
		switch( op )
		{
			case PortugolParserConstants.OP_E:
				return new Boolean( lvalue.equals( rvalue ) );
			case PortugolParserConstants.OP_NEQ:
				return new Boolean( !lvalue.equals( rvalue ) );		
		}
		return null;
	}
	
	//condicional operators    or, and, xor
	// { {       BOOL, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	// { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };

	private static Object condicional( int op , Object lvalue, Object rvalue)
	{	
		return null;
	}
	
	private static Object condicional( int op , Boolean lvalue, Boolean rvalue)
	{	
		switch( op )
		{
				case PortugolParserConstants.AND:
					return new Boolean( lvalue.booleanValue() && rvalue.booleanValue() );
					
				case PortugolParserConstants.OR:
					return new Boolean( lvalue.booleanValue() || rvalue.booleanValue() );
					
				//case PortugolParserConstants.NAND:
				//	return new Boolean( ! ( lvalue.booleanValue() && rvalue.booleanValue() ) );
					
				//case PortugolParserConstants.NOR:
				//	return new Boolean( ! ( lvalue.booleanValue() || rvalue.booleanValue() ) );
					
				case PortugolParserConstants.XOR:
					return new Boolean( lvalue.booleanValue() ^ rvalue.booleanValue() );
		}
		return null;
	}
		  	
	//CASTS
	
	//TO BOOL
	//{ {       BOOL, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
	
	private static Object cast_to_bool( Boolean rvalue, Boolean reference)
	{
		if( reference )
			return rvalue;
			
		return new Boolean( rvalue );
	}

	private static Object cast_to_bool( Character rvalue, Boolean reference)
	{
		return null;
	}
					
	private static Object  cast_to_bool( Integer rvalue, Boolean reference )
	{
		return null;
	}
	
	private static Object  cast_to_bool( Double rvalue, Boolean reference )
	{
		return null;
	}
	
	private static Object  cast_to_bool( String rvalue, Boolean reference )
	{
		return null;
	}
	
	//TO CHAR
	//  { TYPE_ERROR,       CHAR,       CHAR, TYPE_ERROR, TYPE_ERROR } ,
	private static Object  cast_to_char( Boolean rvalue, Boolean reference)
	{
		return null;
	}
	
	private static Object  cast_to_char( Character rvalue, Boolean reference)
	{
		if(reference)
			return rvalue;
				
		return new Character( rvalue ) ;
	}
	
	private static Object  cast_to_char( Integer rvalue, Boolean reference )
	{
		if(reference)
			return null; //how to do this??
			
		char val = (char)((int)rvalue);
		
		return new Character( val );
	}
	
	private static Object  cast_to_char( Double rvalue, Boolean reference )
	{
		return null;
	}
	
	private static Object  cast_to_char( String rvalue, Boolean reference )
	{
		return null;
	}
					
	//TO INT
	//  { TYPE_ERROR,       INT,         INT,        INT, TYPE_ERROR } ,
	private static Object  cast_to_int( Boolean rvalue, Boolean reference)
	{
		return null;
	}
	
	private static Object  cast_to_int( Character rvalue, Boolean reference)
	{
		if(reference)
			return null; //how to do this??
			
		int val = (char)rvalue;
		
		return new Integer( val );
	}	
	
	private static Object  cast_to_int( Integer rvalue, Boolean reference )
	{
		if(reference)
			return rvalue; //how to do this??
				
		return new Integer( rvalue );
	}

	private static Object  cast_to_int( Double rvalue, Boolean reference )
	{
		if(reference)
			return null; //how to do this??
						
		return new Integer( (int)(double)rvalue );
	}
	
	private static Object  cast_to_int( String rvalue, Boolean reference )
	{
		return null;
	}	
	
	//TO REAL
	//  { TYPE_ERROR, TYPE_ERROR,       REAL,       REAL, TYPE_ERROR } ,
	private static Object  cast_to_real( Boolean rvalue, Boolean reference)
	{
		return null;
	}
	
	private static Object  cast_to_real( Character rvalue, Boolean reference)
	{
		return null;
	}
	
	private static Object  cast_to_real( Integer rvalue, Boolean reference )
	{
		if(reference)
			return null; //how to do this??
		
		return new Double( (double)rvalue );
	}	
	
	private static Object  cast_to_real( Double rvalue, Boolean reference )
	{
		if(reference)
			return rvalue;
			
		return new Double( rvalue );
	}
		
	private static Object  cast_to_real( String rvalue, Boolean reference )
	{
		return null;
	}

	//TO STRING
	//  {     STRING,     STRING,     STRING,     STRING,     STRING } }
	private static Object  cast_to_string( Boolean rvalue, Boolean reference)
	{
		if(reference)
			return null; //how to do this??
			
		return new String( rvalue.toString() );
	}
		
	private static Object  cast_to_string( Character rvalue, Boolean reference)
	{
		if(reference)
			return null; //how to do this??
		
		return new String( rvalue.toString() );
	}
		
	private static Object  cast_to_string( Integer rvalue, Boolean reference )
	{
		if(reference)
			return null; //how to do this??
			
		return new String( rvalue.toString() );
	}
		
	private static Object  cast_to_string( Double rvalue, Boolean reference )
	{
		if(reference)
			return null; //how to do this??
			
		return new String( rvalue.toString() );
	}	
	private static Object  cast_to_string( String rvalue, Boolean reference )
	{
		if(reference)
			return null; //how to do this??
			
		return new String( rvalue );
	}
	
}