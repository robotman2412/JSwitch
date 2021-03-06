package jswitch.compiler;

import jswitch.compiler.tokenising.Keyword;

public class JSwitchPreProcessor {

	/**
	 * Unescapes a String in the same way the Java compiler would.
	 * Note: Don't include the double quotes for the string.
	 * Note 2: invalid escape sequences are printed, but just put down as the raw input in the output.
	 * Note 3: escape sequences at the end of the string are considered an error.
	 * @param escaped The string to unescape
	 * @return the unescaped string, all escape sequences replaced
	 */
	public static String unescapeString(String escaped) {
		char[] chars = escaped.toCharArray();
		int i = 0;
		StringBuilder produced = new StringBuilder();
		while (i < chars.length) {
			char c = chars[i];
			if (c == '\\') {
				if (i >= chars.length - 1) {
					produced.append(c);
					System.err.println("String escape sequence at end of string!");
					return produced.toString();
				}
				char esx = chars[i + 1];
				switch (esx) {
					//region simple_esx
					case('0'):
						produced.append('\0');
						break;
					case('n'):
						produced.append('\n');
						break;
					case('r'):
						produced.append('\r');
						break;
					case('f'):
						produced.append('\f');
						break;
					case('\\'):
						produced.append('\\');
						break;
					case('\''):
						produced.append('\'');
						break;
					case('\"'):
						produced.append('\"');
						break;
					case('t'):
						produced.append('\t');
						break;
					case('b'):
						produced.append('\b');
						break;
					//endregion simple_esx
					case('u'):
						if (i >= chars.length - 5) {
							System.err.println("Not enough characters left for unicode escape sequence!");
							produced.append("\\u");
							break;
						}
						break;
				}
				i ++;
			}
			else
			{
				produced.append(c);
			}
			i ++;
		}
		return produced.toString();
	}

	/**
	 * Escapes a string in a way the Java and JSwitch compilers would accept it.
	 * Note: Does not include double quotes around the string.
	 * @param input the unescaped string
	 * @return the escaped counterpart
	 */
	public static String escapeString(String input) {
		return input.replace("\\", "\\\\")
				.replace("\t", "\\t")
				.replace("\0", "\\0")
				.replace("\r", "\\r")
				.replace("\n", "\\n")
				.replace("\b", "\\b")
				.replace("\f", "\\f")
				.replace("\t", "\\t")
				.replace("\"", "\\\"")
				.replace("\'", "\\\'");
	}

	/** Characters in this string are not allowe to be in a name. */
	public static String illegalNameChars = "!@#%^&*()+=-{}[]|\\;:\"'<>,.?/~`\t \r\n\b\f\0";
	
	/**
	 * Checks whether the given name is valid.<br>
	 * Any characters in the {@link #illegalNameChars} may not be contained in the input string.<br>
	 * The input string may not be equal to any of the values of {@link Keyword#getAllNames()}.
	 * @param name the input name
	 * @return whether or not the name is legal for JSwitch
	 */
	public static boolean isValidName(String name) {
		if (name == null || name.length() < 1) {
			return false;
		}
		if ("1234567890".indexOf(name.charAt(0)) != -1) {
			return false;
		}
		for (char c : name.toCharArray()) {
			if (illegalNameChars.indexOf(c) != -1) {
				return false;
			}
		}
		for (String keyword : Keyword.getAllNames()) {
			if (keyword.equals(name)) {
				return false;
			}
		}
		return true;
	}
	
}
