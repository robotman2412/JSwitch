package jswitch.compiler.tokenising;

public class CanonicalSeperationToken extends Token {
	
	public CanonicalSeperationToken(int line, int colomn) {
		this.line = line;
		this.columnStart = colomn;
		this.columnEnd = colomn + 1;
		this.rawContent = ";";
	}
	
	@Override
	public TokenType getType() {
		return TokenType.CANONICAL_SEPERATE;
	}
	
}
