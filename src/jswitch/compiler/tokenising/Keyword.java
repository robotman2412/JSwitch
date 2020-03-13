package jswitch.compiler.tokenising;

public enum Keyword {
	PUBLIC,
	PRIVATE,
	PROTECTED,

	STATIC,
	FINAL,
	SYNCHRONIZED,
	ABSTRACT,

	CLASS,
	ENUM,
	INTERFACE,

	EXTENDS,
	IMPLEMENTS,

	FOR,
	WHILE,
	IF,
	ELSE;

	private String name;
	Keyword() {
		name = name().toLowerCase();
	}
	public String getName() {
		return name;
	}
}
