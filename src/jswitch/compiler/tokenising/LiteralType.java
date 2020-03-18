package jswitch.compiler.tokenising;

public enum LiteralType {
	INTEGER,
	SHORT_INTEGER("short"),
	LONG_INTEGER("long"),
	BYTE,
	FLOAT,
	DOUBLE,
	STRING,
	CHARACTER("char"),
	NULL;
	
	public final String name;
	LiteralType() {
		name = name().toLowerCase();
	}
	LiteralType(String name) {
		this.name = name;
	}
	
	public static LiteralType forNumeric(long in) {
		LiteralType type;
		if (in > 4294967295L || in < -2147483648L) {
			type = LiteralType.LONG_INTEGER;
		}
		else if (in > 65535L || in < -32768L) {
			type = LiteralType.INTEGER;
		}
		else if (in > 255L || in < -128L) {
			type = LiteralType.SHORT_INTEGER;
		}
		else
		{
			type = LiteralType.BYTE;
		}
		return type;
	}
	
}
