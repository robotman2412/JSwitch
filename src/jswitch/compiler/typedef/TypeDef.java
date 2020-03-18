package jswitch.compiler.typedef;

import jswitch.compiler.swil.SWILFragment;

public abstract class TypeDef {
	
	public abstract String[] getTypeNames();
	
	//TODO: generics
	
	//TODO: statics
	
	public abstract boolean mayCastTo(TypeDef other);
	
	public abstract SWILFragment getCastor(TypeDef other);
	
	public abstract boolean isSimpleType();
	
}
