package jswitch.compiler.structure.expression.operators;

import com.sun.istack.internal.Nullable;
import jswitch.compiler.typedef.TypeDef;

public abstract class Operator {
	
	public final String seperator;
	public final @Nullable String setterSeperator;
	
	protected Operator(String seperator, String setterSeperator) {
		this.seperator = seperator;
		this.setterSeperator = setterSeperator;
	}
	
	protected Operator(String seperator) {
		this.seperator = seperator;
		this.setterSeperator = null;
	}
	
	/**
	 * @param typeLeft type name of the left hand operand
	 * @param typeRight type name of the right hand operand
	 * @return the output type name, or null if not applicable
	 */
	public abstract TypeDef appliesTwoSided(TypeDef typeLeft, TypeDef typeRight);
	
	public Object resolveTwoSided(TypeDef typeLeft, TypeDef typeRight, Object valueLeft, Object valueRight) {return null;}
	
	/**
	 * @param typeRight the type name of the right hand operand
	 * @return the output type name, or null if not applicable
	 */
	public abstract TypeDef appliesUnary(TypeDef typeRight);
	
	public Object resolveUnary(TypeDef typeRight, Object valueRight) {return null;}
	
	/**
	 * @param variableType type name of the varialbe, as input and output
	 * @param expressionType type name of the expression
	 * @return whether or not this setter operator applies to the situation
	 */
	public abstract boolean appliesSetter(TypeDef variableType, TypeDef expressionType);
	
	public Object resolveSetter(TypeDef variableType, TypeDef expressionType, Object initialVariable, Object expressionResult) {return null;}
	
}
