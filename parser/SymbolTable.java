
class SymbolTable
{
	int level;	
	java.util.Vector<java.util.Hashtable> scopes;	
	java.util.Hashtable current_scope;
	
	public SymbolTable() {	
		level=0;
		scopes = new java.util.Vector<java.util.Hashtable>();
		current_scope = new java.util.Hashtable();
		scopes.add(current_scope);
		
	}
	
	public int getCurrentLevel()
	{
		return level;
	}
	
	public void pushScope() {
		level++;
		current_scope = new java.util.Hashtable();
		scopes.add(current_scope);
	}
	
	public boolean insert(String name, Object symbol) {
		if( current_scope.put(name, symbol) == null )
			return true;
		return false;
	}
	
	public Object lookup(String name) {
		for(int i=level; i>=0; i-- ) {
			if( scopes.get(i).get(name) != null )
				return scopes.get(i).get(name);		
		}		
		return null;	
	}
	
	public Object lookupLast(String name) {
		return current_scope.get(name);
	}
	
	public void popScope()	{
	  if (level == 0)
        return;
      scopes.remove(level);
      current_scope = scopes.lastElement();
      level--;				
	}
}