package jswitch.compiler.structure;

import jswitch.compiler.tokenising.Token;

public class Structure {

	protected Token[] tokens;

	protected Structure() {

	}

	public Structure(Token[] mTokens) {
		tokens = mTokens;
	}

	public Token[] getTokens() {
		return tokens.clone();
	}

}
