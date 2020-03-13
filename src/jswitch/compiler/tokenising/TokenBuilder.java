package jswitch.compiler.tokenising;

import jswitch.compiler.CompilerWarning;
import jswitch.compiler.JSwitchPreProcessor;
import jswitch.compiler.SyntaxError;
import jswitch.compiler.structure.declarators.DeclaratorOut;
import jswitch.compiler.structure.declarators.DeclaratorStructure;
import jswitch.util.ArrayFeeder;
import jswitch.util.StringFeeder;
import jswitch.util.text.TextTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TokenBuilder {

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
	}

	public static void main(String[] args) {
		String input = "public final synchronized String someString = null;";
		TokenBuilder builder = new TokenBuilder();
		TextTable table = new TextTable();
		table.add("TYPE", "LINE", "COL", "", "", "RAW");
		Token[] tokens = builder.tokenize(input, 0).toArray(new Token[0]);
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
		DeclaratorOut output = DeclaratorStructure.parseDeclarator(new ArrayFeeder<>(tokens));
		DeclaratorStructure dec = output.declarator;
		System.out.println("Name: " + dec.getName());
		System.out.println("Type name: " + dec.getTypeName());
		System.out.println("Declarator type: " + dec.getType());
		System.out.println("Is static: " + dec.isStatic());
		System.out.println("Is abstract: " + dec.isAbstract());
		System.out.println("Is final: " + dec.isFinal());
		System.out.println("Is synchronized: " + dec.isSynchronized());
		System.out.println("\nErrors:");
		for (SyntaxError error : output.feedback.errors) {
			System.err.println(error.getMessage());
		}
		System.out.println("\nWarnings:");
		for (CompilerWarning warning : output.feedback.warnings) {
			System.err.println(warning.getMessage());
		}
	}

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
			//region comments
			else if (c == '/') {
				if (expect("**")) {
					tokens.add(new StructureToken(SeperationType.DOCUMENTATION_COMMENT_OPEN, line, colomn - 2));
					multiLineComment = true;
					docsComment = true;
				}
				else if (expect("*")) {
					tokens.add(new StructureToken(SeperationType.COMMENT_OPEN, line, colomn - 1));
					multiLineComment = true;
				}
				else if (expect("/")) {
					tokens.add(new StructureToken(SeperationType.SINGLE_LINE_COMMENT, line, colomn - 1));
					comment = true;
				}
			}
			else if (c == '*') {
				if (expect("/")) {
					tokens.add(new StructureToken(SeperationType.COMMENT_CLOSE, line, colomn - 1));
					multiLineComment = false;
					docsComment = false;
				}
				else
				{
					accumulated += c;//TODO
				}
			}
			//endregion comments
			else if (comment || (multiLineComment && !docsComment)) {
				accumulated += c;
			}
			//region blocks
			else if (c == '{') {
				popAccumulate();
				tokens.add(new StructureToken(SeperationType.CODE_BLOCK_OPEN, line, colomn));
			}
			else if (c == '}') {
				popAccumulate();
				tokens.add(new StructureToken(SeperationType.CODE_BLOCK_CLOSE, line, colomn));
			}
			else if (c == '[') {
				popAccumulate();
				tokens.add(new StructureToken(SeperationType.ARRAY_OPEN, line, colomn));
			}
			else if (c == ']') {
				popAccumulate();
				tokens.add(new StructureToken(SeperationType.ARRAY_CLOSE, line, colomn));
			}
			else if (c == '(') {
				popAccumulate();
				tokens.add(new StructureToken(SeperationType.PARENTHESIS_OPEN, line, colomn));
			}
			else if (c == ')') {
				popAccumulate();
				tokens.add(new StructureToken(SeperationType.PARENTHESIS_CLOSE, line, colomn));
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
			else if (expectSeperate(c)) {
				//do nothing
			}
			//TODO: add literals here
			else
			{
				accumulated += c;
			}
			colomn ++;
		}
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
				tokens.add(new SeperationToken(seperator, line, colomn - seperator.length() + 1, colomn));
				return true;
			}
			if (seperator.length() > 1 && c == seperator.charAt(0) && expect(seperator.substring(1))) {
				tokens.add(new SeperationToken(seperator, line, colomn - seperator.length() + 1, colomn));
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
	public static Token getTokenForstring(String in, int line, int colomn) {
		for (Keyword keyword : Keyword.values()) {
			if (in.equals(keyword.getName())) {
				return new KeywordToken(keyword, line, colomn);
			}
		}
		return new SimpleToken(in, line, colomn, colomn + in.length());
	}

	public static boolean isWhiteSpace(char c) {
		return c == ' ' || c == '\t';
	}

}
