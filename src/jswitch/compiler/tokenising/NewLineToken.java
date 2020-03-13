package jswitch.compiler.tokenising;

import static jswitch.compiler.tokenising.TokenType.NEWLINE;

public class NewLineToken extends Token {

	public NewLineToken(int line, int column) {
		super("\n", line, column, column);
	}

	@Override
	public TokenType getType() {
		return NEWLINE;
	}

}
