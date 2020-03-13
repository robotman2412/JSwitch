package jswitch.compiler.tokenising;

import static jswitch.compiler.tokenising.TokenType.SIMPLE;

public class SimpleToken extends Token {

	public SimpleToken(String raw, int mLine, int mColumnStart, int mColumnEnd) {
		super(raw, mLine, mColumnStart, mColumnEnd);
	}

	@Override
	public TokenType getType() {
		return SIMPLE;
	}

}
