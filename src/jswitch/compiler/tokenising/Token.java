package jswitch.compiler.tokenising;

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
				SeperationType seperationType = ((StructureToken) token).getSeperationType();
				if (seperationType == SeperationType.DOCUMENTATION_COMMENT_OPEN) {
					docsComment = true;
				}
				else if (seperationType == SeperationType.COMMENT_CLOSE) {
					docsComment = false;
				}
			}
			if (!docsComment && token.getType() != TokenType.SPACE) {
				out.add(token);
			}
		}
		return out.toArray(new Token[0]);
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
