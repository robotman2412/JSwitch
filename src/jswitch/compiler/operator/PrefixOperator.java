package jswitch.compiler.operator;

import jswitch.compiler.structure.expression.Operator;

public class PrefixOperator {
	
	public static PrefixOperator UNARY_MINUS = new PrefixOperator(Operator.UNARY_MINUS, "-");
	public static PrefixOperator INVERSION = new PrefixOperator(Operator.NOT, "!", "~");
	
	public static PrefixOperator[] ALL = {
			UNARY_MINUS,
			INVERSION
	};
	
	public String[] names;
	public Operator operator;
	
	public PrefixOperator(Operator operator, String... names) {
		this.operator = operator;
		this.names = names;
	}
	
	//TODO: fragment generation
	
}
