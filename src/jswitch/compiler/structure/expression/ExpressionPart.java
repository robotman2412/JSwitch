package jswitch.compiler.structure.expression;

import jswitch.compiler.CompilerWarning;
import jswitch.compiler.SyntaxError;
import jswitch.compiler.structure.expression.operators.Operator;
import jswitch.compiler.tokenising.LiteralToken;
import jswitch.compiler.tokenising.StructureToken;
import jswitch.compiler.tokenising.Token;
import jswitch.compiler.typedef.TypeDef;
import jswitch.util.ListFeeder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpressionPart {

	public ExpressionPartType type;
	public List<Token> tokens;
	public Operator operator;
	public List<ExpressionPart> subParts;
	public Object value;
	public TypeDef valueType;
	
	public static List<ExpressionPart> rawList(List<Token> filteredTokens) {
		List<ExpressionPart> out = new ArrayList<>();
		for (Token token : filteredTokens) {
			ExpressionPart expr = new ExpressionPart();
			expr.tokens = Collections.singletonList(token);
			if (token instanceof StructureToken) {
				StructureToken s = (StructureToken) token;
				switch (s.getStructureType()) {
					default:
						throw new IllegalArgumentException("For structure token type: " + s.getStructureType().getName());
					case ARRAY_OPEN:
						expr.type = ExpressionPartType.ARRAY_OPEN;
						break;
					case ARRAY_CLOSE:
						expr.type = ExpressionPartType.ARRAY_CLOSE;
						break;
					case PARENTHESIS_OPEN:
						expr.type = ExpressionPartType.PARENTHESIS_OPEN;
						break;
					case PARENTHESIS_CLOSE:
						expr.type = ExpressionPartType.PARENTHESIS_CLOSE;
						break;
					
				}
			}
			else if (token instanceof LiteralToken) {
				expr.type = ExpressionPartType.LITERAL;
				LiteralToken literal = (LiteralToken) token;
				expr.value = literal.getValue();
				expr.valueType = literal.getAssociatedTypeDef();
			}
			else
			{
				expr.type = ExpressionPartType.RAW;
			}
			out.add(expr);
		}
		return out;
	}
	
	public void parse(List<CompilerWarning> warnings, List<SyntaxError> syntaxErrors) {
		int depth = 0;
		ListFeeder<ExpressionPart> feeder;
		if (subParts.get(0).type == ExpressionPartType.PARENTHESIS_OPEN && subParts.get(subParts.size() - 1).type == ExpressionPartType.PARENTHESIS_CLOSE) {
			feeder = new ListFeeder<>(subParts.subList(1, tokens.size() - 1));
		}
		else {
			feeder = new ListFeeder<>(subParts);
		}
		List<ExpressionPart> newParts = new ArrayList<>();
		//region parenthesies
		while (feeder.length() > 0) {
			ExpressionPart part = feeder.getOne();
			if (part.type == ExpressionPartType.PARENTHESIS_CLOSE) {
				newParts.add(part);
				syntaxErrors.add(new SyntaxError("Closing parenthesis unexpected here.", part.tokens.toArray(new Token[0])));
			}
			else if (part.type == ExpressionPartType.PARENTHESIS_OPEN) {
				ExpressionPart expr = new ExpressionPart();
				expr.subParts = new ArrayList<>();
				expr.tokens = new ArrayList<>();
				expr.type = ExpressionPartType.EXPRESSION;
				newParts.add(expr);
				depth = 1;
				while (true) {
					if (feeder.length() == 0) {
						syntaxErrors.add(new SyntaxError("Closing parenthesis expected here.", tokens.toArray(new Token[0])));
						return;
					}
					ExpressionPart anotherOne = feeder.getOne();
					if (anotherOne.type == ExpressionPartType.PARENTHESIS_CLOSE) {
						depth --;
						if (depth == 0) {
							expr.recalcTokens();
							break;
						}
					}
					else {
						if (anotherOne.type == ExpressionPartType.PARENTHESIS_OPEN) {
							depth++;
						}
						expr.subParts.add(anotherOne);
					}
				}
			}
			else
			{
				newParts.add(part);
			}
		}
		
		subParts = newParts;
		if (syntaxErrors.size() != 0) {
			return;
		}
		
		for (ExpressionPart part : subParts) {
			if (part.type == ExpressionPartType.EXPRESSION) {
				part.parse(warnings, syntaxErrors);
			}
		}
		//endregion parenthesies
		//TODO: pre/post increment/decrement
		//or not, because it makes bugs
		
		//region unary
		newParts = new ArrayList<>();
		Collections.reverse(subParts);
		feeder = new ListFeeder<>(subParts);
		while (feeder.hasNext()) {
			ExpressionPart part = feeder.getOne();
			if (part.getRawTokens().equals("+")) {
			
			}
		}
		
		Collections.reverse(newParts);
		subParts = newParts;
		//endregion unary
		
		//region multiplicative
		newParts = new ArrayList<>();
		feeder = new ListFeeder<>(subParts);
		while (feeder.hasNext()) {
		
		}
		
		subParts = newParts;
		//endregion
	}
	
	private void recalcTokens() {
		tokens = new ArrayList<>();
		for (ExpressionPart part : subParts) {
			tokens.addAll(part.tokens);
		}
	}
	
	public String getRawTokens() {
		String s = new String();
		for (Token token : tokens) {
			s += token.getRawContent();
		}
		return s;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder(type + ": ");
		if (subParts == null || subParts.size() == 0) {
			for (Token token : tokens) {
				s.append(token.getRawContent());
			}
			s.append("\n");
		}
		else {
			s.append("{\n");
			for (ExpressionPart part : subParts) {
				String[] split = part.toString().split("\n");
				for (String split0 : split) {
					s.append("  ").append(split0).append("\n");
				}
			}
			s.append("}");
		}
		return s.toString();
	}
}
