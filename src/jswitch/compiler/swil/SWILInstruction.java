package jswitch.compiler.swil;

import com.sun.istack.internal.Nullable;

public class SWILInstruction {
	
	public final int[] parameterLengths;
	public final byte instructionCode;
	public final String instructionName;
	public final String description;
	
	public @Nullable SWILFragment replacementSWIL;
	public @Nullable String replacementFileName;
	//TODO: public @Nullable ASMFragment replacementASM;
	
	public SWILInstruction(String instructionName, int instructionCode, int... parameterLengths) {
		this.instructionCode = (byte) instructionCode;
		this.parameterLengths = parameterLengths;
		this.instructionName = instructionName;
		this.description = null;
	}
	
	public SWILInstruction(String instructionName, int instructionCode, String description, int... parameterLengths) {
		this.instructionCode = (byte) instructionCode;
		this.parameterLengths = parameterLengths;
		this.instructionName = instructionName;
		this.description = description;
	}
	
}
