package jswitch.compiler.structure.declarators;

import jswitch.compiler.CompilerFeedback;

public class DeclaratorOut {

	public DeclaratorStructure declarator;
	public CompilerFeedback feedback;

	public DeclaratorOut(DeclaratorStructure mDeclarator, CompilerFeedback mFeedback) {
		declarator = mDeclarator;
		feedback = mFeedback;
	}

}
