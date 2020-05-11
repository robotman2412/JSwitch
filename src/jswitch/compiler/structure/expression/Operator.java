package jswitch.compiler.structure.expression;

public enum Operator {
	
	//numeric
	ADD,
	SUBTRACT,
	UNARY_MINUS,
	MULTIPLY,
	DIVIDE,
	REMAINDER,
	//logical
	OR,
	XOR,
	AND,
	NOT,
	//shifting
	SHIFT_L,
	SHIFT_R,
	ROT_L,
	ROT_R,
	//comparative
	INSTANCE,
	OVER_OR_EQUAL,
	UNDER_OR_EQUAL,
	OVER,
	UNDER,
	EQUAL,
	NOT_EQUAL
	
	
	
}
