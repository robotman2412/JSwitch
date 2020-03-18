package jswitch.compiler.tokenising;

public enum StructureType {
	CODE_BLOCK_OPEN("{"),
	CODE_BLOCK_CLOSE("}"),
	ARRAY_OPEN("["),
	ARRAY_CLOSE("]"),
	PARENTHESIS_OPEN("("),
	PARENTHESIS_CLOSE(")"),
	SINGLE_LINE_COMMENT("//"),
	DOCUMENTATION_COMMENT_OPEN("/**"),
	COMMENT_OPEN("/*"),
	COMMENT_CLOSE("*/"),
	COMPILER_INSTRUCTION("#"),
	ANNOTATION("@"),
	CANONICAL_LINE_SPLITTER(";");
	private String name;
	StructureType(String mName) {
		name = mName;
	}
	public String getName() {
		return name;
	}
}
