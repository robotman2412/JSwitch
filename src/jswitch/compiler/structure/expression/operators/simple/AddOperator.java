package jswitch.compiler.structure.expression.operators.simple;

import jswitch.compiler.structure.expression.operators.Operator;
import jswitch.compiler.typedef.SimpleNumberType;
import jswitch.compiler.typedef.SimpleStringTypeDef;
import jswitch.compiler.typedef.TypeDef;

public class AddOperator extends Operator {
	
	public AddOperator() {
		super("+", "+=");
	}
	
	@Override
	public TypeDef appliesTwoSided(TypeDef typeLeft, TypeDef typeRight) {
		if (typeLeft == SimpleStringTypeDef.STRING && typeRight instanceof SimpleNumberType ||
				typeRight == SimpleStringTypeDef.STRING && typeLeft instanceof SimpleNumberType) {
			return SimpleStringTypeDef.STRING;
		}
		return SimpleNumberType.getSignificantType(typeLeft, typeRight);
	}
	
	@Override
	public Object resolveTwoSided(TypeDef typeLeft, TypeDef typeRight, Object valueLeft, Object valueRight) {
		if (typeLeft == SimpleStringTypeDef.STRING && typeRight instanceof SimpleNumberType ||
				typeRight == SimpleStringTypeDef.STRING && typeLeft instanceof SimpleNumberType) {
			return ((String) valueLeft) + valueRight;
		}
		switch (SimpleNumberType.getSignificantType(typeLeft, typeRight).typeEnum) {
			case INTEGER:
			case SHORT_INTEGER:
			case LONG_INTEGER:
			case BYTE:
			case UNSIGNED_INTEGER:
			case UNSIGNED_SHORT_INTEGER:
			case UNSIGNED_LONG_INTEGER:
			case UNSIGNED_BYTE:
				return ((Number) valueLeft).longValue() + ((Number) valueRight).longValue();
			case FLOAT:
			case DOUBLE:
				return ((Number) valueLeft).doubleValue() + ((Number) valueRight).doubleValue();
		}
		return null;
	}
	
	@Override
	public TypeDef appliesUnary(TypeDef typeRight) {
		return typeRight instanceof SimpleNumberType ? typeRight : null;
	}
	
	@Override
	public Object resolveUnary(TypeDef typeRight, Object valueRight) {
		return valueRight;
	}
	
	@Override
	public boolean appliesSetter(TypeDef variableType, TypeDef expressionType) {
		if (variableType instanceof SimpleStringTypeDef) {
			return expressionType instanceof SimpleNumberType;
		}
		return variableType instanceof SimpleNumberType && expressionType instanceof SimpleNumberType;
	}
	
}
