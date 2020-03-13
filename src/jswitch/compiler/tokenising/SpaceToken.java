package jswitch.compiler.tokenising;

public class SpaceToken extends Token {

	public SpaceToken(String content, int line, int columnStart, int columnEnd) {
		super(content, line, columnStart, columnEnd);
	}

	@Override
	public TokenType getType() {
		return TokenType.SPACE;
	}

}
