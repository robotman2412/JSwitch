package jswitch.compiler.structure.expression;

import jswitch.compiler.CompilerFeedback;

public class ExpressionOut {
	
	public ExpressionStructure expression;
	public CompilerFeedback feedback;
	
	public ExpressionOut(ExpressionStructure mExpression, CompilerFeedback mFeedback) {
		expression = mExpression;
		feedback = mFeedback;
	}
	
}
