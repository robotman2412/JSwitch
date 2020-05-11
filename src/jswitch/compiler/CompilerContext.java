package jswitch.compiler;

import java.util.HashMap;
import java.util.Map;

public class CompilerContext {
	
	public Map<String, String> packageToClassPathMap;
	
	public CompilerContext() {
		packageToClassPathMap = new HashMap<>();
	}
	
}
