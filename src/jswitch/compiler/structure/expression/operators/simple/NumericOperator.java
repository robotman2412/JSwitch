package jswitch.compiler.structure.expression.operators.simple;

import jswitch.compiler.structure.expression.operators.Operator;
import jswitch.compiler.typedef.SimpleNumberType;
import jswitch.compiler.typedef.TypeDef;

public abstract class NumericOperator extends Operator {
	
	protected boolean doFloatingTypes = true;
	protected boolean doOneSided = false;
	
	public NumericOperator(String seperator, String setterSeperator) {
		super(seperator, setterSeperator);
	}
	
	public NumericOperator(String seperator) {
		super(seperator);
	}
	
	public abstract long doLong(long left, long right);
	
	public abstract long doLong(long in);
	
	public abstract double doDouble(double left, double right);
	
	public abstract double doDouble(double in);
	
	@Override
	public TypeDef appliesTwoSided(TypeDef typeLeft, TypeDef typeRight) {
		if (!doFloatingTypes && (typeRight == SimpleNumberType.Double.SIGNED || typeRight == SimpleNumberType.Float.SIGNED)) {
			return null;
		}
		if (!doFloatingTypes && (typeLeft == SimpleNumberType.Double.SIGNED || typeLeft == SimpleNumberType.Float.SIGNED)) {
			return null;
		}
		return SimpleNumberType.getSignificantType(typeLeft, typeRight);
	}
	
	@Override
	public TypeDef appliesUnary(TypeDef typeRight) {
		if (!doFloatingTypes && (typeRight == SimpleNumberType.Double.SIGNED || typeRight == SimpleNumberType.Float.SIGNED)) {
			return null;
		}
		return typeRight instanceof SimpleNumberType && doOneSided ? typeRight : null;
	}
	
	@Override
	public boolean appliesSetter(TypeDef variableType, TypeDef expressionType) {
		if (!doFloatingTypes && (variableType == SimpleNumberType.Double.SIGNED || variableType == SimpleNumberType.Float.SIGNED)) {
			return false;
		}
		return variableType instanceof SimpleNumberType && expressionType instanceof SimpleNumberType && setterSeperator != null;
	}
	
	@Override
	public Object resolveTwoSided(TypeDef typeLeft, TypeDef typeRight, Object valueLeft, Object valueRight) {
		SimpleNumberType significantType = SimpleNumberType.getSignificantType(typeLeft, typeRight);
		if (significantType == SimpleNumberType.Float.SIGNED || significantType == SimpleNumberType.Double.SIGNED) {
			return doDouble(((Number) valueLeft).doubleValue(), ((Number) valueRight).doubleValue());
		}
		else
		{
			return doLong(((Number) valueLeft).longValue(), ((Number) valueRight).longValue());
		}
	}
	
	@Override
	public Object resolveSetter(TypeDef variableType, TypeDef expressionType, Object initialVariable, Object expressionResult) {
		if (variableType == SimpleNumberType.Float.SIGNED || variableType == SimpleNumberType.Double.SIGNED) {
			return doDouble(((Number) initialVariable).doubleValue(), ((Number) expressionResult).doubleValue());
		}
		else
		{
			return doLong(((Number) initialVariable).longValue(), ((Number) expressionResult).longValue());
		}
	}
	
	@Override
	public Object resolveUnary(TypeDef typeRight, Object valueRight) {
		if (typeRight == SimpleNumberType.Float.SIGNED || typeRight == SimpleNumberType.Double.SIGNED) {
			return doDouble(((Number) valueRight).doubleValue());
		}
		else
		{
			return doLong(((Number) valueRight).longValue());
		}
	}
	
	public static class SubtractOperator extends NumericOperator {
		
		public SubtractOperator() {
			super("-", "-=");
			doOneSided = true;
		}
		
		@Override
		public long doLong(long left, long right) {
			return left - right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left - right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
	public static class MultiplyOperator extends NumericOperator {
		
		public MultiplyOperator() {
			super("*", "*=");
		}
		
		@Override
		public long doLong(long left, long right) {
			return left * right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left * right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
	public static class DivideOperator extends NumericOperator {
		
		public DivideOperator() {
			super("/", "/=");
		}
		
		@Override
		public long doLong(long left, long right) {
			return left / right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left / right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
	public static class RemainderOperator extends NumericOperator {
		
		public RemainderOperator() {
			super("%", "%=");
			doOneSided = true;
		}
		
		@Override
		public long doLong(long left, long right) {
			return left % right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left % right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
	public static class XOROperator extends NumericOperator {
		
		public XOROperator() {
			super("%", "%=");
			doOneSided = true;
		}
		
		@Override
		public long doLong(long left, long right) {
			return left % right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left % right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
	public static class ANDOperator extends NumericOperator {
		
		public ANDOperator() {
			super("&", "&=");
			doOneSided = true;
		}
		
		@Override
		public long doLong(long left, long right) {
			return left & right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left % right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
	public static class OROperator extends NumericOperator {
		
		public OROperator() {
			super("%", "%=");
			doOneSided = true;
		}
		
		@Override
		public long doLong(long left, long right) {
			return left % right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left % right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
	public static class ShiftLOperator extends NumericOperator {
		
		public ShiftLOperator() {
			super("<<", "<<=");
			doOneSided = true;
		}
		
		@Override
		public long doLong(long left, long right) {
			return left << right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left % right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
	public static class ShiftROperator extends NumericOperator {
		
		public ShiftROperator() {
			super(">>", ">>=");
			doOneSided = true;
		}
		
		@Override
		public long doLong(long left, long right) {
			return left >> right;
		}
		
		@Override
		public long doLong(long in) {
			return -in;
		}
		
		@Override
		public double doDouble(double left, double right) {
			return left % right;
		}
		
		@Override
		public double doDouble(double in) {
			return -in;
		}
		
	}
	
}
