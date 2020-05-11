package jswitch.compiler.tokenising;

public class LiteralToken extends Token {
	
	protected LiteralType literalType;
	protected Object value;
	
	public LiteralToken(String raw, LiteralType type, Object value, int line, int colomnStart) {
		rawContent = raw;
		literalType = type;
		this.value = value;
		this.line = line;
		this.columnStart = colomnStart;
		columnEnd = colomnStart + raw.length();
	}
	
	public Object getValue() {
		return value;
	}
	
	@Override
	public TokenType getType() {
		return TokenType.LITERAL;
	}
	
	public LiteralType getLiteralType() {
		return literalType;
	}
	
}
