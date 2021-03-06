package jswitch.compiler.tokenising;

import jswitch.compiler.JSwitchPreProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract class Token {

	protected int line;
	protected int columnStart;
	protected int columnEnd;
	protected String rawContent;

	public Token() {

	}

	public Token(String mRawComtent, int mLine, int mColumnStart, int mColumnEnd) {
		rawContent = mRawComtent;
		line = mLine;
		columnEnd = mColumnEnd;
		columnStart = mColumnStart;
	}

	public static Token[] removeWhiteSpace(Token[] tokens) {
		List<Token> out = new ArrayList<>();
		boolean docsComment = false;
		for (Token token : tokens) {
			if (token instanceof StructureToken) {
				StructureType structureType = ((StructureToken) token).getStructureType();
				if (structureType == StructureType.DOCUMENTATION_COMMENT_OPEN) {
					docsComment = true;
				}
				else if (structureType == StructureType.COMMENT_CLOSE) {
					docsComment = false;
				}
			}
			if (!docsComment && token.getType() != TokenType.SPACE) {
				out.add(token);
			}
		}
		return out.toArray(new Token[0]);
	}
	
	public static List<Token> removeWhiteSpace(List<Token> tokens) {
		List<Token> out = new ArrayList<>();
		boolean docsComment = false;
		for (Token token : tokens) {
			if (token instanceof StructureToken) {
				StructureType structureType = ((StructureToken) token).getStructureType();
				if (structureType == StructureType.DOCUMENTATION_COMMENT_OPEN) {
					docsComment = true;
				}
				else if (structureType == StructureType.COMMENT_CLOSE) {
					docsComment = false;
				}
			}
			if (!docsComment && token.getType() != TokenType.SPACE) {
				out.add(token);
			}
		}
		return out;
	}
	
	public boolean isValidName() {
		return JSwitchPreProcessor.isValidName(getRawContent());
	}
	
	public abstract TokenType getType();

	public final int getLine() {
		return line;
	}
	public final int getColumnStart() {
		return columnStart;
	}
	public final int getColumnEnd() {
		return columnEnd;
	}
	public final String getRawContent() {
		return rawContent;
	}

}
