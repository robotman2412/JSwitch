package jswitch.compiler.structure.expression.operators;

import jswitch.compiler.structure.expression.operators.simple.AddOperator;
import jswitch.compiler.structure.expression.operators.simple.NumericOperator;

public class Operators {
	
	public static final Operator ADD = new AddOperator();
	public static final Operator SUBTRACT = new NumericOperator.SubtractOperator();
	public static final Operator MULTIPLY = new NumericOperator.MultiplyOperator();
	public static final Operator DIVIDE = new NumericOperator.DivideOperator();
	public static final Operator REMAINDER = new NumericOperator.RemainderOperator();
	public static final Operator AND = new NumericOperator.ANDOperator();
	public static final Operator OR = new NumericOperator.OROperator();
	public static final Operator XOR = new NumericOperator.XOROperator();
	public static final Operator SHIFT_LEFT = new NumericOperator.ShiftLOperator();
	public static final Operator SHIFT_RIGHT = new NumericOperator.ShiftROperator();
	
}
