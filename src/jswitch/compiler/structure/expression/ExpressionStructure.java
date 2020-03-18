package jswitch.compiler.structure.expression;

import jswitch.compiler.CompilerFeedback;
import jswitch.compiler.CompilerWarning;
import jswitch.compiler.SyntaxError;
import jswitch.compiler.structure.Structure;
import jswitch.compiler.tokenising.Token;
import jswitch.util.Feeder;
import jswitch.util.ListFeeder;

import java.util.ArrayList;
import java.util.List;

public class ExpressionStructure extends Structure {
	
	/**
	 * Present if the expression can be replaced with a constant.<br>
	 * Constants may be used, only if the platform is not marked as being able to dynamically load classes.
	 */
	protected Object constantRepresentation;
	
	public static ExpressionOut parseExpression(Feeder<Token> tokens) {
		List<Token> toUse = new ArrayList<>();
		//find what is actually in the expression
		while (tokens.length() > 0) {
			Token token = tokens.getOne();
			if (token.getRawContent().equals("(")) {
			
			}
		}
		return parseExpressionRaw(toUse);
	}
	
	public static ExpressionOut parseExpressionRaw(List<Token> filteredTokens) {
		ExpressionOut out = null;
		List<ExpressionPart> raw = ExpressionPart.rawList(filteredTokens);
		List<ExpressionPart> parts = raw;//new ArrayList<>();
		//TODO: member access
		ListFeeder<ExpressionPart> feeder = new ListFeeder<>(raw);
		ExpressionPart expression = new ExpressionPart();
		expression.type = ExpressionPartType.EXPRESSION;
		expression.tokens = filteredTokens;
		expression.subParts = parts;
		List<CompilerWarning> warnings = new ArrayList<>();
		List<SyntaxError> syntaxErrors = new ArrayList<>();
		System.out.println(expression);
		expression.parse(warnings, syntaxErrors);
		System.out.println(expression);
		out = new ExpressionOut(null, new CompilerFeedback(syntaxErrors.toArray(new SyntaxError[0]), warnings.toArray(new CompilerWarning[0])));
		return out;
	}
	
}
