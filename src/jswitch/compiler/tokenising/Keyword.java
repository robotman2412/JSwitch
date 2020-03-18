package jswitch.compiler.tokenising;

import com.sun.istack.internal.Nullable;

public enum Keyword {
	PUBLIC,
	PRIVATE,
	PROTECTED,

	STATIC,
	FINAL,
	SYNCHRONIZED("synchronised", "synchronized"),
	ABSTRACT,

	CLASS,
	ENUM,
	INTERFACE,

	EXTENDS,
	IMPLEMENTS,

	FOR,
	WHILE,
	IF,
	ELSE,
	
	INSTANCEOF("instanceof", "is");

	public final String name;
	public final @Nullable String altName;
	Keyword() {
		name = name().toLowerCase();
		this.altName = null;
	}
	Keyword(String name, String altName) {
		this.name = name;
		this.altName = altName;
	}
}
