package jswitch.compiler;

import jswitch.compiler.tokenising.SimpleToken;
import jswitch.compiler.tokenising.Token;

public class SyntaxError extends RuntimeException {

	protected Token[] offendingTokens;

	protected int lineStart;
	protected int lineEnd;
	protected int colomnStart;
	protected int colomnEnd;
	protected String fullMessage;
	protected String message;

	public SyntaxError(String mMessage, Token offending) {
		if (offending == null) {
			offending = new SimpleToken("", 0, 0, 0);
		}
		offendingTokens = new Token[] {offending};
		lineStart = lineEnd = offending.getLine();
		colomnStart = offending.getColumnStart();
		colomnEnd = offending.getColumnEnd();
		message = mMessage;
		fullMessage = "Syntax error in line " + lineStart + ", colomn " + colomnStart + ": " + message;
	}

	public SyntaxError(String mMessage, Token[] offending) {
		if (offending == null) {
			offending = new Token[] {new SimpleToken("", 0, 0, 0)};
		}
		offendingTokens = offending;
		lineStart = offending[0].getLine();
		colomnStart = offending[0].getColumnStart();
		colomnEnd = offending[offending.length - 1].getColumnEnd();
		lineEnd = offending[offending.length - 1].getLine();
		message = mMessage;
		fullMessage = "Syntax error in line " + lineStart + ", colomn " + colomnStart + ": " + message;
	}
	
	public Token[] getOffendingTokens() {
		return offendingTokens;
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
