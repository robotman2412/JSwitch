package jswitch.compiler;

import jswitch.compiler.tokenising.Token;

public class CompilerWarning extends RuntimeException {

	protected Token[] offendingTokens;

	protected int lineStart;
	protected int lineEnd;
	protected int colomnStart;
	protected int colomnEnd;
	protected String fullMessage;
	protected String message;

	public CompilerWarning(String mMessage, Token offending) {
		offendingTokens = new Token[] {offending};
		lineStart = lineEnd = offending.getLine();
		colomnStart = offending.getColumnStart();
		colomnEnd = offending.getColumnEnd();
		message = mMessage;
		fullMessage = "Warning in line " + lineStart + ", colomn " + colomnStart + ": " + message;
	}

	public CompilerWarning(String mMessage, Token[] offending) {
		offendingTokens = offending;
		lineStart = offending[0].getLine();
		colomnStart = offending[0].getColumnStart();
		colomnEnd = offending[offending.length - 1].getColumnEnd();
		lineEnd = offending[offending.length - 1].getLine();
		message = mMessage;
		fullMessage = "Warning in line " + lineStart + ", colomn " + colomnStart + ": " + message;
	}

	public String getSimpleMessage() {
		return message;
	}

	@Override
	public String getMessage() {
		return fullMessage;
	}

	@Override
	public String getLocalizedMessage() {
		return fullMessage;
	}
}
