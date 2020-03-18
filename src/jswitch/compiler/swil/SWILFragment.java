package jswitch.compiler.swil;

public class SWILFragment {
	
	public final SWILInstructionFragment[] instructions;
	
	public SWILFragment(SWILInstructionFragment... instructions) {
		this.instructions = instructions;
	}
	
	public static SWILFragment simple(SWILInstruction... inst) {
		SWILInstructionFragment[] fragments = new SWILInstructionFragment[inst.length];
		for (int i = 0; i < fragments.length; i++) {
			fragments[i] = new SWILInstructionFragment(inst[i]);
		}
		return new SWILFragment(fragments);
	}
	
}
