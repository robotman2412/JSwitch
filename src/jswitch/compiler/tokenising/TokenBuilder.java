package jswitch.compiler.tokenising;

import jswitch.compiler.JSwitchPreProcessor;
import jswitch.compiler.SyntaxError;
import jswitch.util.StringFeeder;
import jswitch.util.text.TextTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TokenBuilder {

	protected List<SyntaxError> errors;
	protected StringFeeder feed;
	protected List<Token> tokens;
	protected String accumulated;
	protected boolean whitespace;
	protected boolean comment;
	protected boolean multiLineComment;
	protected boolean docsComment;
	protected boolean isString;
	protected boolean isChar;
	protected int line;
	protected int colomn;

	/**
	 * Initialize a <code>TokenBuilder</code> with <code>content</code>.
	 * Lines may be seperated by \r\n, just \r or just \n.
	 */
	public TokenBuilder() {
		accumulated = "";
		whitespace = false;
		comment = false;
		multiLineComment = false;
		docsComment = false;
		isString = false;
		isChar = false;
		colomn = 0;
		errors = new ArrayList<>();
	}

	//region unit tests
	public static void main(String[] args) {
		
	}
	
	public static void printTokenTable(Token[] tokens) {
		TextTable table = new TextTable();
		table.add("TYPE", "LINE", "COL", "", "", "RAW");
		for (Token token : tokens) {
			String s = "" + token.getColumnStart();
			String s1 = "";
			String s2 = "";
			if (token.getColumnEnd() > token.getColumnStart() + 1) {
				s2 = "" + (token.getColumnEnd() - 1);
				s1 = "-";
			}
			table.add(token.getType().toString(), token.getLine(), s, s1, s2, "\"" + JSwitchPreProcessor.escapeString(token.getRawContent()) + "\"");
		}
		table.print(2);
	}
	
	
	public static void printTokenTable(List<Token> tokens) {
		TextTable table = new TextTable();
		table.add("TYPE", "LINE", "COL", "", "", "RAW");
		for (Token token : tokens) {
			String s = "" + token.getColumnStart();
			String s1 = "";
			String s2 = "";
			if (token.getColumnEnd() > token.getColumnStart() + 1) {
				s2 = "" + (token.getColumnEnd() - 1);
				s1 = "-";
			}
			table.add(token.getType().toString(), token.getLine(), s, s1, s2, "\"" + JSwitchPreProcessor.escapeString(token.getRawContent()) + "\"");
		}
		table.print(2);
	}
	//endregion unit tests
	
	/**
	 * Tokenises a number of lines, seperated by \r\n, just \r or just \n.
	 * @param lineNumOffset the line number to start at
	 * @return a list of tokens that matches and can be used to reconstruct the file
	 */
	public List<Token> tokenize(String content, int lineNumOffset) {
		feed = new StringFeeder(content);
		tokens = new ArrayList<>();
		line = lineNumOffset;
		while (feed.length() > 0) {
			char c = feed.getOne();
			if (c == '\r') {
				if (comment || multiLineComment) {
					tokens.add(new SpaceToken(accumulated, line, colomn - accumulated.length(), colomn));
					accumulated = "";
					comment = false;
				}
				else
				{
					popAccumulate();
				}
				char next = feed.getOne();
				tokens.add(new NewLineToken(line, colomn));
				if (next != '\n') {
					feed.goBackOne();
				}
				line ++;
				colomn = 0;
				continue;
			}
			else if (c == '\n') {
				if (comment || multiLineComment) {
					tokens.add(new SpaceToken(accumulated, line, colomn - accumulated.length(), colomn));
					accumulated = "";
					comment = false;
				}
				else
				{
					popAccumulate();
				}
				tokens.add(new NewLineToken(line, colomn));
				line ++;
				colomn = 0;
				comment = false;
				continue;
			}
			else if (isChar || isString) {
				if (c == '\"' && isString) {
					tokens.add(new LiteralToken("\"" + accumulated + "\"", LiteralType.STRING, JSwitchPreProcessor.unescapeString(accumulated), line, colomn - accumulated.length()));
					isString = false;
					accumulated = "";
				}
				else if (c == '\'' && isChar) {
					tokens.add(new LiteralToken("\'" + accumulated + "\'", LiteralType.CHARACTER, JSwitchPreProcessor.unescapeString(accumulated), line, colomn - accumulated.length()));
					isChar = false;
					accumulated = "";
				}
				else {
					accumulated += c;
					if (c == '\\') {
						char next = feed.getOne();
						colomn ++;
						if (next == '\"') {
							accumulated += "\"";
						} else if (next == '\'') {
							accumulated += "\'";
						} else if (next == '\\') {
							accumulated += "\\";
						}
						else
						{
							colomn --;
							feed.goBackOne();
						}
					}
				}
			}
			//region comments
			else if (c == '/') {
				if (expect("**")) {
					tokens.add(new StructureToken(StructureType.DOCUMENTATION_COMMENT_OPEN, line, colomn - 2));
					multiLineComment = true;
					docsComment = true;
				}
				else if (expect("*")) {
					tokens.add(new StructureToken(StructureType.COMMENT_OPEN, line, colomn - 1));
					multiLineComment = true;
				}
				else if (expect("/")) {
					tokens.add(new StructureToken(StructureType.SINGLE_LINE_COMMENT, line, colomn - 1));
					comment = true;
				}
				else
				{
					popAccumulate();
					tokens.add(new SeperationToken("/", line, colomn, colomn));
				}
			}
			else if (c == '*') {
				if (expect("/")) {
					tokens.add(new StructureToken(StructureType.COMMENT_CLOSE, line, colomn - 1));
					multiLineComment = false;
					docsComment = false;
				}
				else
				{
					popAccumulate();
					tokens.add(new SeperationToken("*", line, colomn, colomn));
				}
			}
			//endregion comments
			else if (comment || (multiLineComment && !docsComment)) {
				accumulated += c;
			}
			else if (c == ';') {
				popAccumulate();
				tokens.add(new CanonicalSeperationToken(line, colomn));
			}
			//region blocks
			else if (c == '{') {
				popAccumulate();
				tokens.add(new StructureToken(StructureType.CODE_BLOCK_OPEN, line, colomn));
			}
			else if (c == '}') {
				popAccumulate();
				tokens.add(new StructureToken(StructureType.CODE_BLOCK_CLOSE, line, colomn));
			}
			else if (c == '[') {
				popAccumulate();
				tokens.add(new StructureToken(StructureType.ARRAY_OPEN, line, colomn));
			}
			else if (c == ']') {
				popAccumulate();
				tokens.add(new StructureToken(StructureType.ARRAY_CLOSE, line, colomn));
			}
			else if (c == '(') {
				popAccumulate();
				tokens.add(new StructureToken(StructureType.PARENTHESIS_OPEN, line, colomn));
			}
			else if (c == ')') {
				popAccumulate();
				tokens.add(new StructureToken(StructureType.PARENTHESIS_CLOSE, line, colomn));
			}
			//endregion blocks
			else if (isWhiteSpace(c)) {
				if (!whitespace) {
					popAccumulate();
				}
				accumulated += c;
				whitespace = true;
			}
			else if (whitespace) {
				whitespace = false;
				tokens.add(new SpaceToken(accumulated, line, colomn - accumulated.length(), colomn));
				accumulated = "";
				feed.goBack(1);
				continue;
			}
			else if (c == '.') {
				if (accumulated.matches("-?[0-9]+")) { //is a number
					accumulated += c;
				}
				else
				{
					popAccumulate();
					tokens.add(new SeperationToken(".", line, colomn, colomn)); //is just a .
				}
			}
			else if (expectSeperate(c)) {
				//do nothing
			}
			else if (c == '\"') {
				popAccumulate();
				isString = true;
			}
			else if (c == '\'') {
				popAccumulate();
				isChar = true;
			}
			else
			{
				accumulated += c;
			}
			colomn ++;
		}
		//ensure we have no extra random stuff
		popAccumulate();
		return tokens;
	}

	/**
	 * Checks whether or not the feed contains the input string.
	 * If it is found, it does not tell the feed to go back.
	 * It follows all the rules the tokenizer method does including incrementing the colomn accordingly.
	 * @param input the string to check for
	 * @return whether or not this string was found at the beginning of the feed
	 */
	protected boolean expect(String input) {
		if (feed.length() < input.length()) {
			return false;
		}
		if (!feed.getString(input.length()).equals(input)) {
			feed.goBack(input.length());
			return false;
		}
		popAccumulate();
		colomn += input.length();
		return true;
	}

	/**
	 * Checks whether or not the character just pulled from the feed combined <i>with</i> the feed contains a seperator.
	 * If found, the appropriate tokens are added.
	 * @param c the current character
	 * @return whether or not a seperator was found
	 */
	protected boolean expectSeperate(char c) {
		for (String seperator : SeperationToken.seperators) {
			if (seperator.length() == 1 && c == seperator.charAt(0)) {
				popAccumulate();
				tokens.add(new SeperationToken(seperator, line, colomn, colomn));
				return true;
			}
			if (seperator.length() > 1 && c == seperator.charAt(0) && expect(seperator.substring(1))) {
				tokens.add(new SeperationToken(seperator, line, colomn - seperator.length() + 1, colomn + 1));
				return true;
			}
		}
		return false;
	}

	/**
	 * Takes the accumulated string and empties it into a new token.
	 */
	protected void popAccumulate() {
		if (accumulated.length() == 0) {
			return;
		}
		if (whitespace) {
			tokens.add(new SpaceToken(accumulated, line, colomn - accumulated.length(), colomn));
			accumulated = "";
			return;
		}
		tokens.add(getTokenForstring(accumulated, line, colomn - accumulated.length()));
		accumulated = "";
	}
	
	/**
	 * TODO
	 * Gets the token for the input string.
	 * @param in the string for which to get the token for
	 * @return the token, or null if invalid
	 */
	public Token getTokenForstring(String in, int line, int colomn) {
		final String hexChars = "0123456789abcdef";
		final String octalChars = "01234567";
		final String decimalChars = "0123456789";
		if (in.startsWith("0x")) { //hexadecimal literals
			if (!in.matches("0x[0-9a-fA-F]+")) {
				Token token = new SimpleToken(in, line, colomn, colomn + in.length() - 1);
				errors.add(new SyntaxError("This is not a valid hexadecimal literal, please read https://https://en.wikipedia.org/wiki/Hexadecimal/ for more information.", token));
				return token;
			}
			else if (in.length() > 18) {
				Token token = new SimpleToken(in, line, colomn, colomn + in.length() - 1);
				errors.add(new SyntaxError("This hexadecimal literal is too long to fit in even a long integer (64 bits), please shorten it.", token));
				return token;
			}
			else
			{
				long value = 0;
				for (int i = 2; i < in.length(); i++) {
					value <<= 4;
					value |= hexChars.indexOf(in.toLowerCase().charAt(i));
				}
				return new LiteralToken(in, LiteralType.forNumeric(value), value, line, colomn);
			}
		}
		if (in.startsWith("0q") || in.startsWith("0o") || (octalChars.indexOf(in.charAt(0)) != -1 && (in.endsWith("q") || in.endsWith("o")))) { //octal literals
			String octal;
			if (in.startsWith("0q") || in.startsWith("0o")) {
				octal = in.substring(2);
			}
			else
			{
				octal = in.substring(0, in.length() - 1);
			}
			if (!octal.matches("[0-7]+")) {
				Token token = new SimpleToken(in, line, colomn, colomn + in.length() - 1);
				errors.add(new SyntaxError("This is not a valid octal literal, please read https://https://en.wikipedia.org/wiki/Octal/ for more information.", token));
				return token;
			}
			else if (octal.length() > 22) {
				Token token = new SimpleToken(in, line, colomn, colomn + in.length() - 1);
				errors.add(new SyntaxError("This octal literal is too long to possibly fit in even a long integer (64 bits), please shorten it.", token));
				return token;
			}
			else
			{
				long value = 0;
				for (int i = 0; i < octal.length(); i++) {
					value <<= 3;
					value |= octalChars.indexOf(octal.charAt(i));
				}
				return new LiteralToken(in, LiteralType.forNumeric(value), value, line, colomn);
			}
		}
		if (in.endsWith("B")) { //octal literals
			String bin = in.substring(0, in.length() - 1);
			if (!bin.matches("[0-7]+")) {
				Token token = new SimpleToken(in, line, colomn, colomn + in.length() - 1);
				errors.add(new SyntaxError("This is not a valid octal literal, please read https://https://en.wikipedia.org/wiki/Octal/ for more information.", token));
				return token;
			}
			else if (bin.length() > 64) {
				Token token = new SimpleToken(in, line, colomn, colomn + in.length() - 1);
				errors.add(new SyntaxError("This octal literal is too long to fit in even a long integer (64 bits), please shorten it.", token));
				return token;
			}
			else
			{
				long value = 0;
				for (int i = 0; i < bin.length(); i++) {
					value <<= 1;
					value |= bin.charAt(i) == '1' ? 1 : 0;
				}
				return new LiteralToken(in, LiteralType.forNumeric(value), value, line, colomn);
			}
		}
		if (decimalChars.indexOf(in.charAt(0)) != -1 || in.charAt(0) == '-') { //decimal literals
			String decimal = in;
			if (decimal.matches("-?[0-9]+((\\.[0-9]+[fFdD]?)|[fFdD])")) {
				LiteralType type = LiteralType.DOUBLE;
				if (decimal.toLowerCase().endsWith("f")) {
					type = LiteralType.FLOAT;
					decimal = decimal.substring(0, decimal.length() - 1);
				}
				else if (decimal.toLowerCase().endsWith("d")) {
					decimal = decimal.substring(0, decimal.length() - 1);
				}
				double value = Double.parseDouble(decimal);
				return new LiteralToken(in, type, value, line, colomn);
			}
			else if (!decimal.matches("-?[0-9]+")) {
				Token token = new SimpleToken(in, line, colomn, colomn + in.length() - 1);
				errors.add(new SyntaxError("This is not a valid decimal or floating point decimal literal.", token));
				return token;
			}
			else if (decimal.length() > 19) {
				Token token = new SimpleToken(in, line, colomn, colomn + in.length() - 1);
				errors.add(new SyntaxError("This decimal literal is too long to possibly fit in even a long integer (64 bits), please shorten it.", token));
				return token;
			}
			else
			{
				long value = decimal.charAt(0) == '-' ? Long.parseLong(decimal) :  Long.parseUnsignedLong(decimal);
				return new LiteralToken(in, LiteralType.forNumeric(value), value, line, colomn);
			}
		}
		if (in.equals("null")) {
			return new LiteralToken(in, LiteralType.NULL, null, line, colomn);
		}
		for (Keyword keyword : Keyword.values()) {
			if (in.equals(keyword.name) || in.equals(keyword.altName)) {
				return new KeywordToken(keyword, line, colomn);
			}
		}
		return new SimpleToken(in, line, colomn, colomn + in.length());
	}

	public static boolean isWhiteSpace(char c) {
		return c == ' ' || c == '\t';
	}

}
