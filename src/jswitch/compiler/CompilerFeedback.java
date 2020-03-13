package jswitch.compiler;

public class CompilerFeedback {

	public SyntaxError[] errors;
	public CompilerWarning[] warnings;

	public CompilerFeedback(SyntaxError[] mErrors, CompilerWarning[] mWarnings) {
		errors = mErrors;
		warnings = mWarnings;
	}

}
