package jswitch.compiler.tokenising;

import static jswitch.compiler.tokenising.TokenType.STRUCTURE;

public class StructureToken extends Token {

	protected StructureType structureType;

	public StructureToken(StructureType type, int mLine, int mColumnStart) {
		super(type.getName(), mLine, mColumnStart, mColumnStart + type.getName().length());
		structureType = type;
	}

	@Override
	public TokenType getType() {
		return STRUCTURE;
	}

	public StructureType getStructureType() {
		return structureType;
	}

}
