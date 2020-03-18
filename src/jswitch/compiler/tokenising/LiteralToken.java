package jswitch.compiler.tokenising;

import jswitch.compiler.typedef.SimpleNumberType;
import jswitch.compiler.typedef.SimpleStringTypeDef;
import jswitch.compiler.typedef.TypeDef;

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
	
	public TypeDef getAssociatedTypeDef() {
		if (literalType == LiteralType.STRING) {
			return SimpleStringTypeDef.STRING;
		}
		else if (literalType == LiteralType.CHARACTER) {
			return null; //TODO: character typedef
		}
		else if (literalType == LiteralType.NULL) {
			return null; //TODO: null typedef
		}
		else if (literalType == LiteralType.FLOAT) {
			return SimpleNumberType.Float.SIGNED;
		}
		else if (literalType == LiteralType.DOUBLE) {
			return SimpleNumberType.Double.SIGNED;
		}
		else
		{
			boolean negative = (long) value < 0;
			switch (literalType) {
				case INTEGER:
					return negative ? SimpleNumberType.Integer.SIGNED : SimpleNumberType.Integer.UNSIGNED;
				case SHORT_INTEGER:
					return negative ? SimpleNumberType.Short.SIGNED : SimpleNumberType.Short.UNSIGNED;
				case LONG_INTEGER:
					return negative ? SimpleNumberType.Long.SIGNED : SimpleNumberType.Long.UNSIGNED;
				case BYTE:
					return negative ? SimpleNumberType.Byte.SIGNED : SimpleNumberType.Byte.UNSIGNED;
			}
		}
		return null;
	}
	
}
