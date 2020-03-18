package jswitch.compiler.tokenising;

public class KeywordToken extends Token {

	Keyword keyword;

	public KeywordToken(Keyword mKeyword, int mLine, int mColumnStart) {
		super(mKeyword.name, mLine, mColumnStart, mColumnStart + mKeyword.name.length());
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
