

class TypeCheck
{
	public static final int VOID = PortugolParserConstants.VOID;
	public static final int BOOL = PortugolParserConstants.BOOL;
	public static final int CHAR = PortugolParserConstants.CHAR;
	public static final int INT = PortugolParserConstants.INT;
	public static final int REAL = PortugolParserConstants.REAL;
	public static final int STRING = PortugolParserConstants.STRING;
	public static final int TYPE_ERROR = 666;

	//Data Types of Operator Results
	public static final int[] unary_plus = 
		{ TYPE_ERROR, TYPE_ERROR, INT, REAL, TYPE_ERROR };
	
	public static final int[] unary_not = 
		{ BOOL, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR };
	
	public static final int[][] binary_plus = 
		{ { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, STRING } ,
		  { TYPE_ERROR,       CHAR,        INT, TYPE_ERROR, STRING } ,
		  { TYPE_ERROR,        INT,        INT,       REAL, STRING } ,
		  { TYPE_ERROR, TYPE_ERROR,        REAL,      REAL, STRING } ,
		  { STRING    ,     STRING,     STRING,     STRING, STRING } };
	
	public static final int[][] binary_minus = 
		{ { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR,       CHAR,        INT, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR,        INT,        INT,       REAL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,       REAL,       REAL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };
		
	public static final int[][] binary_mod = 
		{ { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,        INT, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };
		
	public static final int[][] binary_mult = 
		{ { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,        INT,       REAL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,        INT,       REAL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,       REAL,       REAL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };
		
	public static final int[][] binary_div = 
		{ { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,        INT,       REAL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,       REAL,       REAL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };
	
	public static final int[][] binary_relational = //> , < , >= ,<=
		{ { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR,       BOOL,       BOOL, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR,       BOOL,       BOOL,       BOOL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,       BOOL,       BOOL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR,      BOOL } };

		public static final int[][] binary_eq  = //==, !=
		{ {       BOOL, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR,       BOOL,       BOOL, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR,       BOOL,       BOOL,       BOOL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,       BOOL,       BOOL, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR,      BOOL } };
		
	public static final int[][] binary_condicional  = //or, and, xor
		{ {       BOOL, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } };

	public static final int[][] binary_assign  = //=
		{ {       BOOL, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR,       CHAR,       CHAR, TYPE_ERROR, TYPE_ERROR } ,
		  { TYPE_ERROR,       INT,         INT,        INT, TYPE_ERROR } ,
		  { TYPE_ERROR, TYPE_ERROR,       REAL,       REAL, TYPE_ERROR } ,
		  {     STRING,     STRING,     STRING,     STRING,     STRING } };

	
	public static final int check( int op, int type )
	{
		if( indexOfType(type) >= 0)
		{
			switch( op )
			{
				case PortugolParserConstants.OP_PLUS:
				case PortugolParserConstants.OP_MINUS:
					return unary_plus[indexOfType(type)];
					
				case PortugolParserConstants.NOT:
					return unary_not[indexOfType(type)];			
			}
		}
		return TYPE_ERROR;
	}
	
	public static final int check(int op, int left_type, int right_type )
	{
		if( indexOfType(left_type) >= 0 && indexOfType(right_type) >= 0 )
		{
			switch( op )
			{		
				case PortugolParserConstants.OP_PLUS:
					return	binary_plus[indexOfType(left_type)][indexOfType(right_type)];		
					
				case PortugolParserConstants.OP_MINUS:
					return binary_minus[indexOfType(left_type)][indexOfType(right_type)];
					
				case PortugolParserConstants.OP_MUL:
					return binary_mult[indexOfType(left_type)][indexOfType(right_type)];
					
				case PortugolParserConstants.OP_DIV:
					return binary_div[indexOfType(left_type)][indexOfType(right_type)];
					
				case PortugolParserConstants.OP_MOD:		
					return binary_mod[indexOfType(left_type)][indexOfType(right_type)];
					
				case PortugolParserConstants.OP_GE:
				case PortugolParserConstants.OP_LE:
				case PortugolParserConstants.OP_G:
				case PortugolParserConstants.OP_L:				
					return binary_relational[indexOfType(left_type)][indexOfType(right_type)];

				case PortugolParserConstants.OP_E:
				case PortugolParserConstants.OP_NEQ:
					return binary_eq[indexOfType(left_type)][indexOfType(right_type)];

				case PortugolParserConstants.AND:
				case PortugolParserConstants.OR:
				//case PortugolParserConstants.NAND:
				//case PortugolParserConstants.NOR:
				case PortugolParserConstants.XOR:
					return binary_condicional[indexOfType(left_type)][indexOfType(right_type)];		

				case PortugolParserConstants.OP_ASSIGN:
					return binary_assign[indexOfType(left_type)][indexOfType(right_type)];
			}
		}
		
		return TYPE_ERROR;	
	}
	
	private static final int indexOfType(int type)
	{
		switch(type)
		{
			case VOID:
				return -1;
			case BOOL:
				return 0;
			case CHAR:
				return 1;
			case INT:
				return 2;				
			case REAL:
				return 3;
			case STRING:
				return 4;
		}
		return -1;
	}

}