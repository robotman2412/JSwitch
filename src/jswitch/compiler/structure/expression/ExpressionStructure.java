package jswitch.compiler.structure.expression;

import jswitch.compiler.operator.InfixOperator;
import jswitch.compiler.operator.PrefixOperator;
import jswitch.compiler.tokenising.Token;

public class ExpressionStructure {
	
	public OperatorType operatorType;
	public InfixOperator infix;
	public PrefixOperator prefix;
	
	public ExpressionPartType type;
	
	public Token[] tokens;
	
	/**
	 * Either:
	 * The arguments of a function or constructor.
	 * Or the expression inside parenthesies.
	 */
	public ExpressionStructure[] arguments;
	public ExpressionStructure ternaryCondition;
	public ExpressionStructure left;
	public ExpressionStructure right;
	public Object constant;
	
	public String className;
	public String fieldName;
	public String methodName;
	
	public ExpressionStructure() {
		type = ExpressionPartType.UNPARSED;
	}
	
	public String getRawContent() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < tokens.length; i++) {
			s.append(tokens[i].getRawContent());
			if (i < tokens.length - 1) {
				s.append(' ');
			}
		}
		return s.toString();
	}
	
}
