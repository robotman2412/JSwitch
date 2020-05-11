package jswitch.compiler.scope;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ScopeImpl implements Scope {
	
	public Map<String, String> classAliases;
	public Map<String, String> localVariables;
	public Map<String, String> fieldAliasses;
	
	public ScopeImpl() {
		classAliases = new HashMap<>();
		localVariables = new HashMap<>();
		fieldAliasses = new HashMap<>();
	}
	
	public @Nullable String getLocalVariable(String name) {
		return localVariables.get(name);
	}
	
	public @Nullable String getClassAlias(String name) {
		return classAliases.get(name);
	}
	
	public @Nullable String getFieldAlias(String name) {
		return fieldAliasses.get(name);
	}
	
	public void appendLocal(String name, String type) {
		localVariables.put(name, type);
	}
	
	public void appendFields(String name, String type) {
		fieldAliasses.put(name, type);
	}
	
	public void appendClass(String name, String type) {
		classAliases.put(name, type);
	}
	
	@Override
	public boolean isEmpty() {
		return localVariables.isEmpty() && classAliases.isEmpty() && fieldAliasses.isEmpty();
	}
	
}
