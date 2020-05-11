package jswitch.compiler;

import jswitch.compiler.operator.InfixOperator;
import jswitch.compiler.operator.PrefixOperator;
import jswitch.compiler.scope.ScopeStack;
import jswitch.compiler.structure.expression.ExpressionPartType;
import jswitch.compiler.structure.expression.ExpressionStructure;
import jswitch.compiler.structure.expression.OperatorType;
import jswitch.compiler.tokenising.LiteralToken;
import jswitch.compiler.tokenising.Token;
import jswitch.compiler.tokenising.TokenBuilder;
import jswitch.compiler.tokenising.TokenType;
import jswitch.util.Feeder;
import jswitch.util.ListFeeder;

import java.util.*;

public class JSWitchClassCompiler {
	
	public List<SyntaxError> errors;
	public List<CompilerWarning> warnings;
	
	public String extendsClassName;
	public String className;
	public Map<String, String> importMap;
	public CompilerContext context;
	public ScopeStack scopeStack;
	
	public Feeder<Token> tokens;
	
	public static void main(String[] args) {
		String raw = "net.somecompany.classes.Class.method0(1 + -5.6325, 4, 5) * 4;";
		CompilerContext context = new CompilerContext();
		context.packageToClassPathMap.put("net.somecompany.classes.Class", "net/somecomany/classes/Class");
		context.packageToClassPathMap.put("net.OtherIdiot.classes.Stuff", "net/OtherIdiot/classes/Stuff");
		context.packageToClassPathMap.put("net.OK.classes.Outer.Inner", "net/OK/classes/Outer.Inner");
		JSWitchClassCompiler compiler = new JSWitchClassCompiler(context, raw);
		compiler.importMap.put("Type", "jswitch/lang/type/Types.Type");
		compiler.className = "com/robotman2412/TestClass";
		compiler.extendsClassName = null;
		ExpressionStructure struct = compiler.compileExpression();
		printExpression(struct);
	}
	
	public JSWitchClassCompiler(CompilerContext context, String raw) {
		this.context = context;
		errors = new LinkedList<>();
		warnings = new LinkedList<>();
		importMap = new HashMap<>();
		TokenBuilder builder = new TokenBuilder();
		List<Token> tokenised = Token.removeWhiteSpace(builder.tokenize(raw, 0));
		TokenBuilder.printTokenTable(tokenised);
		tokens = new ListFeeder<>(tokenised);
	}
	
	/**
	 * precedence:
	 *
	 * TODO: parenthesies / invokations / references
	 * unary minus / not (! is both bool and bitwise)
	 * TODO: cast / initialisation
	 * multiply / divide / remainder
	 * add / subtract
	 * comparative and istanceof
	 * equals ant not equals
	 * logical operations (|| and | do both bitwise and boolean or, like other logical operators)
	 * TODO: ternary
	 * assignment is not gonna happen here, it causes bugs
	 */
	public ExpressionStructure compileExpression() {
		List<ExpressionStructure> subParts = rawSimpleExpressionList();
		if (subParts.size() < 1) {
			throw new SyntaxError("Expression expected.", tokens.peek());
		}
		List<ExpressionStructure> newSubParts = new ArrayList<>(subParts.size());
		for (PrefixOperator prefix : PrefixOperator.ALL) {
			for (int i = 0; i < subParts.size(); i++) {
				boolean applies = false;
				ExpressionStructure expr = subParts.get(i);
				for (String name : prefix.names) {
					if (name.equals(expr.getRawContent())) {
						applies = true;
						break;
					}
				}
				if (applies) {
					if (i >= subParts.size() - 1) {
						throw new SyntaxError("Operator is missing right-hand side.", expr.tokens);
					}
					expr.type = ExpressionPartType.OPERATOR;
					expr.operatorType = OperatorType.PREFIX;
					ExpressionStructure right = subParts.get(i + 1);
					if (right.type == ExpressionPartType.UNPARSED) {
						throw new SyntaxError("Missing right hand side, did you mean to insert a value or reference?", right.tokens);
					}
					expr.right = right;
					expr.prefix = prefix;
					newSubParts.add(expr);
					i ++;
				}
				else
				{
					newSubParts.add(expr);
				}
			}
			subParts = newSubParts;
			newSubParts = new ArrayList<>(subParts.size());
		}
		for (int i = 0; i < subParts.size(); i++) {
			ExpressionStructure expr = subParts.get(i);
			if (expr.type == ExpressionPartType.REFERENCE) {
				if (i < subParts.size() - 1) {
					ExpressionStructure next = subParts.get(i + 1);
					if (next.type == ExpressionPartType.PARENTHESIES) {
						expr.type = ExpressionPartType.INVOKATION;
						expr.arguments = next.arguments;
						i ++;
					}
				}
			}
			newSubParts.add(expr);
		}
		subParts = newSubParts;
		newSubParts = new ArrayList<>(subParts.size());
		for (InfixOperator[] infixList : InfixOperator.HIGHEST_PRIORITY_FIRST) {
			for (int i = 0; i < subParts.size(); i++) {
				InfixOperator applies = null;
				ExpressionStructure expr = subParts.get(i);
				label0: for (InfixOperator infix : infixList) {
					for (String name : infix.names) {
						if (name.equals(expr.getRawContent())) {
							applies = infix;
							break label0;
						}
					}
				}
				if (applies != null) {
					if (newSubParts.size() < 1 || i >= subParts.size() - 1) {
						throw new SyntaxError("Operator is missing left-hand or right-hand side.", expr.tokens);
					}
					expr.type = ExpressionPartType.OPERATOR;
					expr.operatorType = OperatorType.INFIX;
					ExpressionStructure left = newSubParts.get(newSubParts.size() - 1);
					ExpressionStructure right = subParts.get(i + 1);
					if (left.type == ExpressionPartType.UNPARSED) {
						throw new SyntaxError("Missing left hand side, did you mean to insert a value or reference?", left.tokens);
					}
					if (right.type == ExpressionPartType.UNPARSED) {
						throw new SyntaxError("Missing right hand side, did you mean to insert a value or reference?", right.tokens);
					}
					expr.left = left;
					expr.right = right;
					expr.infix = applies;
					newSubParts.set(newSubParts.size() - 1, expr);
					i ++;
				}
				else
				{
					newSubParts.add(expr);
				}
			}
			subParts = newSubParts;
			newSubParts = new ArrayList<>(subParts.size());
		}
		return subParts.get(0);
	}
	
	protected List<ExpressionStructure> rawSimpleExpressionList() {
		List<ExpressionStructure> list = new LinkedList<>();
		Token lastToken = null;
		for (Token token : tokens) {
			if (token.getRawContent().equals(",")) {
				tokens.goBackOne();
				return list;
			}
			switch (token.getRawContent()) {
				case "{":
					throw new RuntimeException(); //TODO
				case "[":
					list.add(compileExpression());
					if (!tokens.hasNext()) {
						tokens.goBackOne();
						Token prev = tokens.getOne();
						throw new SyntaxError("Expected ']', got end of file!", prev);
					}
					Token next0 = tokens.getOne();
					if (!next0.getRawContent().equals("]")) {
						throw new SyntaxError("Expected ']', got '" + JSwitchPreProcessor.escapeString(next0.getRawContent()) + "'!", next0);
					}
					continue;
				case "(":
					List<ExpressionStructure> expressions = new LinkedList<>();
					while (true) {
						expressions.add(compileExpression());
						if (!tokens.hasNext()) {
							tokens.goBackOne();
							Token prev = tokens.getOne();
							throw new SyntaxError("Expected ')' or ',', got end of file!", prev);
						}
						Token next = tokens.getOne();
						if (next.getRawContent().equals(",")) {
							//continue
						}
						else if (next.getRawContent().equals(")")) {
							break;
						}
						else
						{
							throw new SyntaxError("Expected ')' or ',', got '" + JSwitchPreProcessor.escapeString(next.getRawContent()) + "'!", next);
						}
					}
					ExpressionStructure expr = new ExpressionStructure();
					expr.type = ExpressionPartType.PARENTHESIES;
					expr.arguments = expressions.toArray(new ExpressionStructure[0]);
					expr.tokens = new Token[] {token};
					list.add(expr);
					continue;
				case "}":
					throw new RuntimeException(); //TODO
				case "]":
				case ")":
					tokens.goBackOne();
					return list;
			}
			if (token.getType() == TokenType.CANONICAL_SEPERATE) {
				tokens.goBackOne();
				return list;
			}
			if (token.isValidName()) {
				StringBuilder nameBuilder = new StringBuilder();
				nameBuilder.append(token.getRawContent());
				Token lastToken0 = token;
				List<Token> nameTokens = new LinkedList<>();
				nameTokens.add(token);
				while (true) {
					if (!tokens.hasNext()) {
						throw new SyntaxError("Expected name or expression, got end of file!", lastToken0);
					}
					Token token0 = lastToken0 = tokens.getOne();
					if (!token0.getRawContent().equals(".")) {
						tokens.goBackOne();
						break;
					}
					nameTokens.add(token0);
					if (!tokens.hasNext()) {
						throw new SyntaxError("Expected name, got end of file!", lastToken0);
					}
					Token token1 = lastToken0 = tokens.getOne();
					if (!token1.isValidName()) {
						tokens.goBackOne();
						break;
					}
					nameTokens.add(token1);
					nameBuilder.append('.').append(token1.getRawContent());
				}
				String name = nameBuilder.toString();
				ExpressionStructure expr = new ExpressionStructure();
				expr.type = ExpressionPartType.REFERENCE;
				expr.className = resolveClassReference(name);
				expr.methodName = resolveMethodReference(name);
				expr.fieldName = null;
				expr.tokens = nameTokens.toArray(new Token[0]);
				list.add(expr);
				lastToken = lastToken0;
			}
			else
			{
				ExpressionStructure expr = new ExpressionStructure();
				expr.type = ExpressionPartType.UNPARSED;
				expr.tokens = new Token[]{token};
				if (token instanceof LiteralToken) {
					expr.type = ExpressionPartType.CONSTANT;
					expr.constant = ((LiteralToken) token).getValue();
				}
				list.add(expr);
				lastToken = token;
			}
		}
		if (lastToken == null) {
			throw new SyntaxError("Expected a stop, got end of file!", (Token) null);
		}
		throw new SyntaxError("Expected a stop, got end of file!", lastToken);
	}
	
	public String resolveMethodReference(String in) {
		int i = in.lastIndexOf('.');
		if (i == -1) {
			return in;
		}
		else
		{
			String className = resolveClassReference(in.substring(0, i));
			String methodName = in.substring(i + 1);
			return className + " " + methodName;
		}
	}
	
	public String resolveClassReference(String in) {
		if (importMap.containsKey(in)) {
			return importMap.get(in);
		}
		if (context.packageToClassPathMap.containsKey(in)) {
			return context.packageToClassPathMap.get(in);
		}
		return in.replace(".", "/");
	}
	
	public static void printExpression(ExpressionStructure expression) {
		for (String s : stringExpression(expression)) {
			System.out.println(s);
		}
	}
	
	public static List<String> stringExpression(ExpressionStructure expression) {
		if (expression.type == ExpressionPartType.CONSTANT) {
			return Collections.singletonList("" + expression.constant);
		}
		else if (expression.type == ExpressionPartType.OPERATOR) {
			if (expression.operatorType == OperatorType.INFIX) {
				List<String> list = new ArrayList<>();
				list.addAll(indent(expression.left));
				list.add(expression.infix.names[0]);
				list.addAll(indent(expression.right));
				return list;
			}
			else if (expression.operatorType == OperatorType.PREFIX) {
				List<String> list = new ArrayList<>();
				list.add(expression.prefix.names[0]);
				list.addAll(indent(expression.right));
				return list;
			}
		}
		else if (expression.type == ExpressionPartType.REFERENCE) {
			return Arrays.asList(
					"Class: " + expression.className,
					"Field: " + expression.fieldName,
					"Method: " + expression.methodName
			);
		}
		else if (expression.type == ExpressionPartType.PARENTHESIES) {
			List<String> list = new ArrayList<>();
			list.addAll(stringExpression(expression.arguments[0]));
			for (int i = 1; i < expression.arguments.length; i++) {
				list.add(",");
				list.addAll(stringExpression(expression.arguments[i]));
			}
			return list;
		}
		else if (expression.type == ExpressionPartType.INVOKATION) {
			List<String> list = new ArrayList<>();
			list.add("Method: " + expression.methodName);
			list.add("");
			list.addAll(stringExpression(expression.arguments[0]));
			for (int i = 1; i < expression.arguments.length; i++) {
				list.add(",");
				list.addAll(stringExpression(expression.arguments[i]));
			}
			return list;
		}
		return Collections.singletonList("?error");
	}
	
	public static List<String> indent(ExpressionStructure expression) {
		List<String> list = stringExpression(expression);
		if (expression.type == ExpressionPartType.CONSTANT) {
			return list;
		}
		List<String> out = new ArrayList<>(list.size());
		out.add("(");
		for (String s : list) {
			out.add("  " + s);
		}
		out.add(")");
		return out;
	}
	
}
