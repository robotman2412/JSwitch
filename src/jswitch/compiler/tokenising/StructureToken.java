package jswitch.compiler.tokenising;

import static jswitch.compiler.tokenising.TokenType.STRUCTURE;

public class StructureToken extends Token {

	protected SeperationType seperationType;

	public StructureToken(SeperationType type, int mLine, int mColumnStart) {
		super(type.getName(), mLine, mColumnStart, mColumnStart + type.getName().length());
		seperationType = type;
	}

	@Override
	public TokenType getType() {
		return STRUCTURE;
	}

	public SeperationType getSeperationType() {
		return seperationType;
	}

}
