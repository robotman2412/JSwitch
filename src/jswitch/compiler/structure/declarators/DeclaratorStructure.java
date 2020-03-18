package jswitch.compiler.structure.declarators;

import jswitch.compiler.CompilerFeedback;
import jswitch.compiler.CompilerWarning;
import jswitch.compiler.SyntaxError;
import jswitch.compiler.structure.AccessLevel;
import jswitch.compiler.structure.expression.ExpressionStructure;
import jswitch.compiler.structure.Structure;
import jswitch.compiler.tokenising.*;
import jswitch.util.ArrayFeeder;
import jswitch.util.Feeder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeclaratorStructure extends Structure {

	public static final String[] invalidNames = getInvalidNames().toArray(new String[0]);

	public static List<String> getInvalidNames() {
		List<String> invalid = new ArrayList<>();
		for (Keyword keyword : Keyword.values()) {
			invalid.add(keyword.name);
		}
		invalid.add("void");
		return invalid;
	}

	public static boolean isNameValid(String name) {
		String dec = "0123456789";
		if (dec.indexOf(name.charAt(0)) != 0) {
			return false;
		}
		for (String s : invalidNames) {
			if (s.equals(name)) {
				return false;
			}
		}
		return true;
	}

	protected String name;
	protected String typeName;
	protected AccessLevel accessLevel;
	protected boolean isStatic;
	protected boolean isFinal;
	protected boolean isAbstract;
	protected boolean isSynchronized;
	protected DeclaratorType type;
	protected ExpressionStructure variableExpression;
	//TODO: generic types

	public static void main(String[] args) {
		String input = "public static synchronized int i;";
		Token[] tokensIn = new TokenBuilder().tokenize(input, 0).toArray(new Token[0]);
		DeclaratorOut output = parseDeclarator(new ArrayFeeder<>(tokensIn));
		DeclaratorStructure dec = output.declarator;
		System.out.println("Name: " + dec.getName());
		System.out.println("Type: " + dec.getType().name());
		System.out.println("Is static: " + dec.isStatic());
		System.out.println("Is abstract: " + dec.isAbstract());
		System.out.println("Is final: " + dec.isFinal());
		System.out.println("Is synchronized: " + dec.isSynchronized());
		System.out.println(Arrays.toString(output.feedback.warnings));
		System.out.println(Arrays.toString(output.feedback.errors));
	}

	/**
	 * Parses a declarator structure.
	 * Note: does not always use up all tokens in the feed.
	 * @param tokens the tokens to use
	 * @return an output structure containing all the declared thing, all warnings and all errors
	 */
	public static DeclaratorOut parseDeclarator(Feeder<Token> tokens) {
		DeclaratorStructure out = null;
		AccessLevel accessLevel = AccessLevel.PACKAGE;
		List<SyntaxError> errors = new ArrayList<>();
		List<CompilerWarning> warnings = new ArrayList<>();
		boolean isStatic = false;
		boolean isFinal = false;
		boolean isAbstract = false;
		boolean isSynchronized = false;
		DeclaratorType type = null;
		String declaredName = null;
		String declaredTypeName = null;
		boolean wasNameDeclared = false;
		boolean wasTypeDeclared = false;
		boolean wasImplementationDeclared = false;
		ExpressionStructure variableExpression = null;
		
		while (tokens.length() > 0) {
			Token token = tokens.getOne();
			try {
				if(token instanceof StructureToken) {
					StructureType seperation = ((StructureToken) token).getStructureType();
					switch(seperation) {
						case ANNOTATION:
							Token next = tokens.getOne();
							tokens.goBackOne();
							if (wasNameDeclared) {
								throw new SyntaxError("@ was not expected here, please remove this token.", token);
							}
							if (next.getRawContent().equals("interface")) {
								type = DeclaratorType.ANNOTATION;
							}
							//TODO: capture annotation
							break;
					}
				}
				else if (token instanceof KeywordToken) {
					Keyword keyword = ((KeywordToken) token).getKeyword();
					if (wasNameDeclared) {
						switch (keyword) {
							default:
								throw new SyntaxError("The keyword " + keyword.name + " was unexpected here.", token);
							case EXTENDS:
								if (type == DeclaratorType.ENUM) {
									errors.add(new SyntaxError("Enums cannot extend a class, please remove this token.", token));
								}
								if (type == DeclaratorType.INTERFACE) {
									//TODO: capture multiple class extension structures
								}
								else
								{
									//TODO: capture class extension structure
								}
								break;
							case IMPLEMENTS:
								if (type == DeclaratorType.INTERFACE) {
									errors.add(new SyntaxError("Interfaces cannot use implements, use extends (1 or more types) instead.", token));
								}
								//TODO: capture multiple class extension structures
								break;
						}
					}
					else
					{
						switch (keyword) {
							default:
								throw new SyntaxError("The keyword " + keyword.name + " was unexpected here.", token);
							case CLASS:
								if (type != null) {
									throw new SyntaxError("This is already defined as " + type.name().toLowerCase() + ", please remove this token.", token);
								}
								type = DeclaratorType.CLASS;
							case ENUM:
								if (type != null) {
									throw new SyntaxError("This is already defined as " + type.name().toLowerCase() + ", please remove this token.", token);
								}
								type = DeclaratorType.CLASS;
							case INTERFACE:
								if (type != null) {
									throw new SyntaxError("This is already defined as " + type.name().toLowerCase() + ", please remove this token.", token);
								}
								type = DeclaratorType.CLASS;
							//region access
							case PUBLIC:
								if (accessLevel != AccessLevel.PACKAGE) {
									throw new SyntaxError("An access level of " + accessLevel.name().toLowerCase() + " has alredy been specified, please remove this token.", token);
								}
								accessLevel = AccessLevel.PUBLIC;
								break;
							case PRIVATE:
								if (accessLevel != AccessLevel.PACKAGE) {
									throw new SyntaxError("An access level of " + accessLevel.name().toLowerCase() + " has alredy been specified, please remove this token.", token);
								}
								accessLevel = AccessLevel.PRIVATE;
								break;
							case PROTECTED:
								if (accessLevel != AccessLevel.PACKAGE) {
									throw new SyntaxError("An access level of " + accessLevel.name().toLowerCase() + " has alredy been specified, please remove this token.", token);
								}
								accessLevel = AccessLevel.PROTECTED;
								break;
							//endregion access
							//region modifiers
							case STATIC:
								if (isStatic) {
									throw new SyntaxError("This declaration is already static, please remove this token.", token);
								}
								isStatic = true;
								break;
							case FINAL:
								if (isFinal) {
									throw new SyntaxError("This declaration is already final, please remove this token.", token);
								}
								isFinal = true;
								break;
							case ABSTRACT:
								if (isAbstract) {
									throw new SyntaxError("This declaration is already abstract, please remove this token.", token);
								}
								isAbstract = true;
								break;
							case SYNCHRONIZED:
								if (isSynchronized) {
									throw new SyntaxError("This declaration is already synchronized, please remove this token.", token);
								}
								isSynchronized = true;
								break;
							//endregion modifiers
						}
					}
				}
				else if (token.getType() == TokenType.SEPERATION) {
					switch (token.getRawContent()) {
						case ("="):
							type = DeclaratorType.VARIABLE;
							if (!wasNameDeclared) {
								throw new SyntaxError("No name was defined for variable.", token);
							}
							break;
						default:
							throw new SyntaxError(token.getRawContent() + " was not expected here, please remove this token.", token);
					}
				}
				else if (token.getType() == TokenType.SIMPLE) {
					if (!wasTypeDeclared) {
						declaredTypeName = token.getRawContent();
						wasTypeDeclared = true;
						continue;
					}

					if (wasNameDeclared) {
						if (type == DeclaratorType.VARIABLE) {
							//variableExpression = ExpressionStructure.parseExpression();
							break;
						}
						else
						{
							throw new SyntaxError(token.getRawContent() + " was not expected here, please remove this token.", token);
						}
					}

					declaredName = token.getRawContent();
					if (!isNameValid(declaredName)) {

					}
					if (declaredName.charAt(0) != declaredName.toLowerCase().charAt(0)) {
						warnings.add(new CompilerWarning("It is not recommended to start a name with an uppercase letter.", token));
					}
					wasNameDeclared = true;

					//temporary this

					boolean genericsAllowed = true;
					if (type == null) {
						type = DeclaratorType.VARIABLE;
					}
					switch(type) {
						case ENUM:
						case ANNOTATION:
							genericsAllowed = false;
						case INTERFACE:
						case CLASS:
							//TODO: capture generics
							break;
					}
				}
				else if (token.getType() != TokenType.SPACE && token.getType() != TokenType.NEWLINE) {
					throw new SyntaxError(token.getRawContent() + " was not expected here, please remove this.", token);
				}
			} catch(SyntaxError e) {
				errors.add(e);
			}
		}
		out = new DeclaratorStructure();
		out.typeName = declaredTypeName;
		out.accessLevel = accessLevel;
		out.isAbstract = isAbstract;
		out.isSynchronized = isSynchronized;
		out.isFinal = isFinal;
		out.isStatic = isStatic;
		out.name = declaredName;
		out.type = type;
		return new DeclaratorOut(out, new CompilerFeedback(errors.toArray(new SyntaxError[0]), warnings.toArray(new CompilerWarning[0])));
	}

	public String getTypeName() {
		return typeName;
	}

	public DeclaratorType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public AccessLevel getAccessLevel() {
		return accessLevel;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public boolean isAbstract() {
		return isAbstract;
	}

	public boolean isSynchronized() {
		return isSynchronized;
	}

}
