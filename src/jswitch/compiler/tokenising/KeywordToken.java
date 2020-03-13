package jswitch.compiler.tokenising;

public class KeywordToken extends Token {

	Keyword keyword;

	public KeywordToken(Keyword mKeyword, int mLine, int mColumnStart) {
		super(mKeyword.getName(), mLine, mColumnStart, mColumnStart + mKeyword.getName().length());
		keyword = mKeyword;
	}

	@Override
	public TokenType getType() {
		return TokenType.KEYWORD;
	}

	public Keyword getKeyword() {
		return keyword;
	}
}
