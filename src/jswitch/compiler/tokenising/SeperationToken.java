package jswitch.compiler.tokenising;

public class SeperationToken extends Token {

	public static final String[] seperators = ". , >>>= >>> >>= <<<= <<< <<= >> << >= <= > < == = |= || | &= && & != ! ? : += ++ + -= -- - *= * /= / ^= ^ %= % :: :".split(" ");

	public SeperationToken(String raw, int line, int colomnStart, int colomnEnd) {
		super(raw, line, colomnStart, colomnEnd);
	}

	@Override
	public TokenType getType() {
		return TokenType.SEPERATION;
	}

}
