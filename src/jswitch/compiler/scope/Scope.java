package jswitch.compiler.scope;

import com.sun.istack.internal.Nullable;

public interface Scope {
	
	@Nullable String getLocalVariable(String name);
	
	@Nullable String getClassAlias(String name);
	
	@Nullable String getFieldAlias(String name);
	
	void appendLocal(String name, String type);
	
	void appendFields(String name, String type);
	
	void appendClass(String name, String type);
	
	boolean isEmpty();
	
}
