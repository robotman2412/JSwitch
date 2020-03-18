package jswitch.compiler.typedef;

import jswitch.compiler.swil.SWILFragment;

public class SimpleStringTypeDef extends TypeDef {
	
	public static final SimpleStringTypeDef STRING = new SimpleStringTypeDef();
	
	@Override
	public String[] getTypeNames() {
		return new String[] {"string"};
	}
	
	@Override
	public boolean mayCastTo(TypeDef other) {
		return false;
	}
	
	@Override
	public SWILFragment getCastor(TypeDef other) {
		return null;
	}
	
	@Override
	public boolean isSimpleType() {
		return true;
	}
	
}
