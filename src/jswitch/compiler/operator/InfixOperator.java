package jswitch.compiler.operator;

import jswitch.compiler.structure.expression.Operator;

public class InfixOperator {
	
	public static InfixOperator MULTIPLY = new InfixOperator("*=", Operator.MULTIPLY, "*");
	public static InfixOperator DIVIDE = new InfixOperator("/=", Operator.DIVIDE, "/");
	public static InfixOperator REMAINDER = new InfixOperator("%=", Operator.REMAINDER, "%");
	
	public static InfixOperator ADD = new InfixOperator("+=", Operator.ADD, "+");
	public static InfixOperator SUBTRACT = new InfixOperator("-=", Operator.SUBTRACT, "-");
	
	public static InfixOperator SHIFT_LEFT = new InfixOperator("<<=", Operator.SHIFT_L, "<<");
	public static InfixOperator SHIFT_RIGHT = new InfixOperator(">>=", Operator.SHIFT_R, ">>");
	public static InfixOperator ROTATE_LEFT = new InfixOperator("<<<=", Operator.ROT_L, "<<<");
	public static InfixOperator ROTATE_RIGHT = new InfixOperator(">>>=", Operator.ROT_R, ">>>");
	
	public static InfixOperator OVER_EQUALS = new InfixOperator(Operator.OVER_OR_EQUAL, ">=");
	public static InfixOperator UNDER_EQUALS = new InfixOperator(Operator.UNDER_OR_EQUAL, "<=");
	public static InfixOperator OVER = new InfixOperator(Operator.OVER, ">");
	public static InfixOperator UNDER = new InfixOperator(Operator.UNDER, "<");
	public static InfixOperator INSTANCE = new InfixOperator(Operator.INSTANCE, "instanceof");
	
	public static InfixOperator EQUALS = new InfixOperator(Operator.EQUAL, "==");
	public static InfixOperator NOT_EQUALS = new InfixOperator(Operator.NOT_EQUAL, "!=");
	
	public static InfixOperator AND = new InfixOperator("&=", Operator.AND, "&&", "&");
	public static InfixOperator XOR = new InfixOperator("^=", Operator.XOR, "^");
	public static InfixOperator OR = new InfixOperator("|=", Operator.OR, "||", "|");
	
	public static InfixOperator[][] HIGHEST_PRIORITY_FIRST = {
			new InfixOperator[] {MULTIPLY, DIVIDE, REMAINDER},
			new InfixOperator[] {ADD, SUBTRACT},
			new InfixOperator[] {SHIFT_LEFT, SHIFT_RIGHT, ROTATE_LEFT, ROTATE_RIGHT},
			new InfixOperator[] {OVER_EQUALS, UNDER_EQUALS, OVER, UNDER, INSTANCE},
			new InfixOperator[] {AND},
			new InfixOperator[] {XOR},
			new InfixOperator[] {OR}
	};
	
	public static final InfixOperator[] ALL = {
			MULTIPLY, DIVIDE, REMAINDER,
			ADD, SUBTRACT,
			SHIFT_LEFT, SHIFT_RIGHT, ROTATE_LEFT, ROTATE_RIGHT,
			OVER_EQUALS, UNDER_EQUALS, OVER, UNDER, INSTANCE,
			EQUALS, NOT_EQUALS,
			AND, XOR, OR
	};
	
	public Operator operator;
	public String[] names;
	public String assignment;
	
	public InfixOperator(Operator operator, String... names) {
		this.operator = operator;
		this.names = names;
	}
	
	public InfixOperator(String assignment, Operator operator, String... names) {
		this.operator = operator;
		this.assignment = assignment;
		this.names = names;
	}
	
	//TODO: fragment generation
	
	
}
