package jswitch.compiler.scope;

import java.util.Stack;
import java.util.function.BiFunction;

public class ScopeStack extends Stack<Scope> implements Scope {
	
	/**
	 * Creates and pushes an empty scope.
	 */
	public Scope push() {
		return push(new ScopeImpl());
	}
	
	protected String deepSearch(BiFunction<Scope, String, String> getter, String key) {
		for (int depth = 0; depth < size(); depth ++) {
			Scope scope = get(size() - 1 - depth);
			String result = getter.apply(scope, key);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	@Override
	public String getLocalVariable(String name) {
		return deepSearch(Scope::getLocalVariable, name);
	}
	
	@Override
	public String getClassAlias(String name) {
		return deepSearch(Scope::getClassAlias, name);
	}
	
	@Override
	public String getFieldAlias(String name) {
		return deepSearch(Scope::getFieldAlias, name);
	}
	
	@Override
	public void appendLocal(String name, String type) {
		
	}
	
	@Override
	public void appendFields(String name, String type) {
		
	}
	
	@Override
	public void appendClass(String name, String type) {
		
	}
	
}
