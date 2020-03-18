package jswitch.compiler.swil;

import com.sun.istack.internal.Nullable;

public class SWILInstructionFragment {
	
	public final SWILInstruction ofInstruction;
	public final Object[] parameters;
	public final @Nullable String label;
	
	public SWILInstructionFragment(SWILInstruction ofInstruction, Object... parameters) {
		this.ofInstruction = ofInstruction;
		this.parameters = parameters;
		if (parameters.length != ofInstruction.parameterLengths.length) {
			//TODO: throw new IllegalArgumentException("Number of parameters do not match!");
		}
		label = null;
	}
	
	public SWILInstructionFragment(String label, SWILInstruction ofInstruction, Object... parameters) {
		this.ofInstruction = ofInstruction;
		this.parameters = parameters;
		if (parameters.length != ofInstruction.parameterLengths.length) {
			//TODO: throw new IllegalArgumentException("Number of parameters do not match!");
		}
		this.label = label;
	}
	
}
