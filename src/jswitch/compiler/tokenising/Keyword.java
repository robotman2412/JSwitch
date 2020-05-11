package jswitch.compiler.tokenising;

import com.sun.istack.internal.Nullable;

import java.util.LinkedList;
import java.util.List;

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
	
	public static String[] getAllNames() {
		List<String> names = new LinkedList<>();
		for (Keyword k : values()) {
			names.add(k.name);
			if (k.altName != null) {
				names.add(k.altName);
			}
		}
		return names.toArray(new String[0]);
	}
	
}
