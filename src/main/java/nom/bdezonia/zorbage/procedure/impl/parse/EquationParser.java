/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.procedure.impl.parse;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.impl.AcosL;
import nom.bdezonia.zorbage.procedure.impl.AcoshL;
import nom.bdezonia.zorbage.procedure.impl.AddL;
import nom.bdezonia.zorbage.procedure.impl.AsinL;
import nom.bdezonia.zorbage.procedure.impl.AsinhL;
import nom.bdezonia.zorbage.procedure.impl.AtanL;
import nom.bdezonia.zorbage.procedure.impl.AtanhL;
import nom.bdezonia.zorbage.procedure.impl.CbrtL;
import nom.bdezonia.zorbage.procedure.impl.ConstantL;
import nom.bdezonia.zorbage.procedure.impl.CosL;
import nom.bdezonia.zorbage.procedure.impl.CoshL;
import nom.bdezonia.zorbage.procedure.impl.DivideL;
import nom.bdezonia.zorbage.procedure.impl.ExpL;
import nom.bdezonia.zorbage.procedure.impl.LogL;
import nom.bdezonia.zorbage.procedure.impl.MaxL;
import nom.bdezonia.zorbage.procedure.impl.MinL;
import nom.bdezonia.zorbage.procedure.impl.ModL;
import nom.bdezonia.zorbage.procedure.impl.MultiplyL;
import nom.bdezonia.zorbage.procedure.impl.NegateL;
import nom.bdezonia.zorbage.procedure.impl.PowL;
import nom.bdezonia.zorbage.procedure.impl.RandL;
import nom.bdezonia.zorbage.procedure.impl.SinL;
import nom.bdezonia.zorbage.procedure.impl.SincL;
import nom.bdezonia.zorbage.procedure.impl.SinchL;
import nom.bdezonia.zorbage.procedure.impl.SinchpiL;
import nom.bdezonia.zorbage.procedure.impl.SincpiL;
import nom.bdezonia.zorbage.procedure.impl.SinhL;
import nom.bdezonia.zorbage.procedure.impl.SqrtL;
import nom.bdezonia.zorbage.procedure.impl.SubtractL;
import nom.bdezonia.zorbage.procedure.impl.TanL;
import nom.bdezonia.zorbage.procedure.impl.TanhL;
import nom.bdezonia.zorbage.procedure.impl.VariableConstantL;
import nom.bdezonia.zorbage.procedure.impl.ZeroL;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseHyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EquationParser<T extends Algebra<T,U>,U> {

	/**
	 * 
	 */
	public EquationParser() {}

	/**
	 * 
	 * @param algebra
	 * @param string
	 * @return
	 */
	public Tuple2<String,Procedure<U>> parse(T algebra, String string) {
		
		Tuple2<String,BigList<Token>> lexResult = new Lexer().lex(algebra, string);
		
		if (lexResult.a() == null) {
			return new Parser().parse(algebra, lexResult.b());
		}
		else {
			Tuple2<String,Procedure<U>> tuple = new Tuple2<String,Procedure<U>>(null, null);
			tuple.setA(lexResult.a());
			tuple.setB(new ZeroL<T,U>(algebra));
			return tuple;
		}
	}
	
	private class Token {

		private String text;
		private int start;
		
		void setText(String text) {
			this.text = text;
		}
		
		String getText() {
			return text;
		}
		
		void setStart(int start) {
			this.start = start;
		}
		
		int getStart() {
			return start;
		}
	}
	
	// $0, $1, $99, etc.
	
	private class Index extends Token {

		private int number;
		
		Index(int start, int number) {
			setStart(start);
			this.number = number;
		}
		
		int getNumber() {
			return number;
		}
	}
	
	// sin, cos, log, etc
	
	private class FunctionName extends Token {
		
		FunctionName(int start, String name) {
			setStart(start);
			setText(name);
		}
		
	}
	
	// (
	
	private class OpenParen extends Token {
		
		OpenParen(int start) {
			setStart(start);
		}
	
	}
	
	// )
	
	private class CloseParen extends Token {

		CloseParen(int start) {
			setStart(start);
		}

	}
	
	// +
	
	private class Plus extends Token {
		
		Plus(int start) {
			setStart(start);
		}
		
	}
	
	// -
	
	private class Minus extends Token {

		Minus(int start) {
			setStart(start);
		}
	
	}
	
	// *
	
	private class Times extends Token {
	
		Times(int start) {
			setStart(start);
		}

	}
	
	// /
	
	private class Divide extends Token {
	
		Divide(int start) {
			setStart(start);
		}

	}
	
	// %
	
	private class Mod extends Token {
	
		Mod(int start) {
			setStart(start);
		}

	}

	// ^
	
	private class Power extends Token {
		
		Power(int start) {
			setStart(start);
		}
	
	}
	
	// max
	
	private class Max extends Token {

		Max(int start) {
			setStart(start);
		}

	}
	
	// min
	
	private class Min extends Token {
	
		Min(int start) {
			setStart(start);
		}
	}
	
	// ,
	
	private class Comma extends Token {
		
		Comma(int start) {
			setStart(start);
		}

	}
	
	// nums, vectors, matrices, tensors all made of octonions or less
	
	private class Numeric extends Token {

		private U value;
		
		Numeric(int start, U value) {
			setStart(start);
			this.value = value;
		}
		
	}
	
	// NOTE Just a thought: making E and PI for matrices is hard because we do not
	// have a shape for the matrix. Take a 2x2 and try to add PI. But PI is 0x0. Nuts.
	// The rand function has the same problem.

	private class Lexer {
		
		Tuple2<String,BigList<Token>> lex(T alg, String str) {
			
			Tuple2<String,BigList<Token>> result = new Tuple2<String, BigList<Token>>(null, null);
			BigList<Token> toks = new BigList<Token>();
			result.setA(null);
			result.setB(toks);
			int i = 0;
			while (i < str.length()) {
				char ch = str.charAt(i);
				if (Character.isWhitespace(ch))
					;
				// a open parenthesis
				else if (ch == '(')
					toks.add(new OpenParen(i));
				// a close parenthesis
				else if (ch == ')')
					toks.add(new CloseParen(i));
				// an addition operator
				else if (ch == '+')
					toks.add(new Plus(i));
				// a subtraction operator
				else if (ch == '-')
					toks.add(new Minus(i));
				// a multiply operator
				else if (ch == '*')
					toks.add(new Times(i));
				// a div/ide operator
				else if (ch == '/')
					toks.add(new Divide(i));
				// a mod operator
				else if (ch == '%')
					toks.add(new Mod(i));
				// a exponentiation operator
				else if (ch == '^')
					toks.add(new Power(i));
				// a comma
				else if (ch == ',')
					toks.add(new Comma(i));
				// an index variable reference
				else if (ch == '$') {
					int num = 0;
					int p = i+1;
					while (p < str.length() && Character.isDigit(str.charAt(p))) {
						num = num * 10;
						num += (str.charAt(p) - '0');
						p++;
					}
					if (p == i+1) {
						result.setA("Lex err near position "+p+": $ sign should be followed by one or more digits");
						return result;
					}
					toks.add(new Index(i, num));
					i = p - 1;
				}
				// vector or a matrix or tensor
				else if (ch == '[') {
					StringBuilder sb = new StringBuilder();
					int level = 1;
					int p = i+1;
					sb.append(ch);
					while (level > 0) {
						if (p >= str.length()) {
							result.setA("Lex err near position "+p+": unterminated multidim numeric type");
							return result;
						}
						ch = str.charAt(p);
						if (ch == '[') level++;
						if (ch == ']') level--;
						sb.append(ch);
						p++;
						if (level == 0)
							break;
					}
					U value = alg.construct(sb.toString());
					toks.add(new Numeric(i, value));
					i = p - 1;
				}
				// a complex or quaterniom or octonion
				else if (ch == '{') {
					StringBuilder sb = new StringBuilder();
					int p = i+1;
					sb.append(ch);
					while (ch != '}') {
						if (p >= str.length()) {
							result.setA("Lex err near position "+p+": unterminated multidim numeric type");
							return result;
						}
						ch = str.charAt(p);
						sb.append(ch);
						p++;
					}
					U value = alg.construct(sb.toString());
					toks.add(new Numeric(i, value));
					i = p - 1;
				}
				// a number
				else if (Character.isDigit(ch) || ch == '.') {
					StringBuilder sb = new StringBuilder();
					sb.append(ch);
					int p = i+1;
					while (p < str.length()) {
						ch = str.charAt(p);
						p++;
						if (Character.isDigit(ch)) {
							sb.append(ch);
						}
						else if (ch == '.') {
							if (sb.toString().indexOf('.') == -1)
								sb.append(ch);
							else {
								p--;
								break;
							}
						}
						else if (ch == 'e') {
							if (sb.toString().indexOf('e') == -1)
								sb.append(ch);
							else {
								p--;
								break;
							}
						}
						else if (ch == '+' || ch == '-') {
							int epos = sb.toString().indexOf('e');
							String tmp = sb.toString();
							if (epos > -1 && tmp.charAt(tmp.length()-1) == 'e')
								sb.append(ch);
							else {
								p--;
								break;
							}
						}
						else {
							p--;
							break;
						}
					}
					U value = alg.construct(sb.toString());
					toks.add(new Numeric(i, value));
					i = p - 1;
				}
				else if (ch == 'E') {
					U value = alg.construct();
					if (!(alg instanceof Constants<?>)) {
						result.setA("Lex err near position "+i+": E not defined for given algebra");
						return result;
					}
					@SuppressWarnings("unchecked")
					Constants<U> a = (Constants<U>) alg;
					a.E().call(value);
					toks.add(new Numeric(i, value));
				}
				else if (ch == 'G') {
					if (nextFew(str, i, "GAMMA")) {
						U value = alg.construct();
						if (!(alg instanceof Constants<?>)) {
							result.setA("Lex err near position "+i+": GAMMA not defined for given algebra");
							return result;
						}
						@SuppressWarnings("unchecked")
						Constants<U> a = (Constants<U>) alg;
						a.GAMMA().call(value);
						toks.add(new Numeric(i, value));
						i += 4;
					}
					else {
						result.setA("Lex err near position "+i+": G char should be followed by AMMA");
						return result;
					}
				}
				else if (ch == 'P') {
					if (nextFew(str, i, "PHI")) {
						U value = alg.construct();
						if (!(alg instanceof Constants<?>)) {
							result.setA("Lex err near position "+i+": PHI not defined for given algebra");
							return result;
						}
						@SuppressWarnings("unchecked")
						Constants<U> a = (Constants<U>) alg;
						a.PHI().call(value);
						toks.add(new Numeric(i, value));
						i += 2;
					}
					else if (nextFew(str, i, "PI")) {
						U value = alg.construct();
						if (!(alg instanceof Constants<?>)) {
							result.setA("Lex err near position "+i+": PI not defined for given algebra");
							return result;
						}
						@SuppressWarnings("unchecked")
						Constants<U> a = (Constants<U>) alg;
						a.PI().call(value);
						toks.add(new Numeric(i, value));
						i += 1;
					}
					else {
						result.setA("Lex err near position "+i+": P char should be followed by I");
						return result;
					}
				}
				// treat everything else as a function call
				else {
					if (nextFew(str, i, "sinchpi")) {
						toks.add(new FunctionName(i, "sinchpi"));
						i += 6;
					}
					else if (nextFew(str, i, "sincpi")) {
						toks.add(new FunctionName(i, "sincpi"));
						i += 5;
					}
					else if (nextFew(str, i, "sinch")) {
						toks.add(new FunctionName(i, "sinch"));
						i += 4;
					}
					else if (nextFew(str, i, "acosh")) {
						toks.add(new FunctionName(i, "acosh"));
						i += 4;
					}
					else if (nextFew(str, i, "asinh")) {
						toks.add(new FunctionName(i, "asinh"));
						i += 4;
					}
					else if (nextFew(str, i, "atanh")) {
						toks.add(new FunctionName(i, "atanh"));
						i += 4;
					}
					else if (nextFew(str, i, "tmin")) {
						U value = alg.construct();
						if (!(alg instanceof Bounded<?>)) {
							result.setA("Lex err near position "+i+": tmin not defined for given algebra");
							return result;
						}
						@SuppressWarnings("unchecked")
						Bounded<U> a = (Bounded<U>) alg;
						a.minBound().call(value);
						toks.add(new Numeric(i, value));
						i += 3;
					}
					else if (nextFew(str, i, "tmax")) {
						U value = alg.construct();
						if (!(alg instanceof Bounded<?>)) {
							result.setA("Lex err near position "+i+": tmax not defined for given algebra");
							return result;
						}
						@SuppressWarnings("unchecked")
						Bounded<U> a = (Bounded<U>) alg;
						a.maxBound().call(value);
						toks.add(new Numeric(i, value));
						i += 3;
					}
					else if (nextFew(str, i, "acos")) {
						toks.add(new FunctionName(i, "acos"));
						i += 3;
					}
					else if (nextFew(str, i, "asin")) {
						toks.add(new FunctionName(i, "asin"));
						i += 3;
					}
					else if (nextFew(str, i, "atan")) {
						toks.add(new FunctionName(i, "atan"));
						i += 3;
					}
					else if (nextFew(str, i, "cosh")) {
						toks.add(new FunctionName(i, "cosh"));
						i += 3;
					}
					else if (nextFew(str, i, "sinh")) {
						toks.add(new FunctionName(i, "sinh"));
						i += 3;
					}
					else if (nextFew(str, i, "tanh")) {
						toks.add(new FunctionName(i, "tanh"));
						i += 3;
					}
					else if (nextFew(str, i, "cbrt")) {
						toks.add(new FunctionName(i, "cbrt"));
						i += 3;
					}
					else if (nextFew(str, i, "sqrt")) {
						toks.add(new FunctionName(i, "sqrt"));
						i += 3;
					}
					else if (nextFew(str, i, "rand")) {
						toks.add(new FunctionName(i, "rand"));
						i += 3;
					}
					else if (nextFew(str, i, "sinc")) {
						toks.add(new FunctionName(i, "sinc"));
						i += 3;
					}
					else if (nextFew(str, i, "zero")) {
						U value = alg.construct();
						toks.add(new Numeric(i, value));
						i += 3;
					}
					else if (nextFew(str, i, "min")) {
						toks.add(new Min(i));
						i += 2;
					}
					else if (nextFew(str, i, "max")) {
						toks.add(new Max(i));
						i += 2;
					}
					else if (nextFew(str, i, "cos")) {
						toks.add(new FunctionName(i, "cos"));
						i += 2;
					}
					else if (nextFew(str, i, "sin")) {
						toks.add(new FunctionName(i, "sin"));
						i += 2;
					}
					else if (nextFew(str, i, "tan")) {
						toks.add(new FunctionName(i, "tan"));
						i += 2;
					}
					else if (nextFew(str, i, "exp")) {
						toks.add(new FunctionName(i, "exp"));
						i += 2;
					}
					else if (nextFew(str, i, "log")) {
						toks.add(new FunctionName(i, "log"));
						i += 2;
					}
					else {
						result.setA("Lex err near position "+i+": unknown function name or other bad syntax");
						return result;
					}
				}
				i++;
			}
			return result;
		}
		
		private boolean nextFew(String str, int pos, String funcName) {
			if (pos + funcName.length() > str.length())
				return false;
			for (int i = 0; i < funcName.length(); i++) {
				if (str.charAt(pos+i) != funcName.charAt(i))
					return false;
			}
			return true;
		}
	}
	
	private class Parser {
		
		Tuple2<String,Procedure<U>> parse(T algebra, BigList<Token> tokens) {
		
			ParseStatus result = equation(algebra, tokens, 0);
			Tuple2<String,Procedure<U>> retVal = new Tuple2<String, Procedure<U>>(null, null);
			if (result.errMsg != null) {
				retVal.setA(result.errMsg);
				retVal.setB(new ZeroL<T,U>(algebra));
			}
			else {
				retVal.setA(null);
				retVal.setB(result.procedure);
			}
			return retVal;
		}
	}
	
	private class ParseStatus {
		
		String errMsg;
		long tokenNumber;
		Procedure<U> procedure;
	}
	
	private boolean match(Class<?> tokClass, BigList<Token> tokens, long pos) {
		if (pos >= tokens.size())
			return false;
		return tokens.get(pos).getClass() == tokClass;
	}
	
	private ParseStatus syntaxError(long tokenNumber, BigList<Token> tokens, String errMsg) {
		ParseStatus status = new ParseStatus();
		status.tokenNumber = tokenNumber;
		if (tokenNumber < tokens.size()) {
			Token token = tokens.get(tokenNumber);
			status.errMsg = "Syntax error with token ("+token.getText()+") near column "+token.getStart()+": "+errMsg;
		}
		else {
			Token token = tokens.get(tokenNumber-1);
			status.errMsg = "Unexpected end of input after token ("+token.getText()+") near column "+token.getStart()+": context - "+errMsg;
		}
		return status;
	}
	
	/*
	equation =
	 term |
	 term “+” equation |
	 term “-” equation
	*/
	@SuppressWarnings({"unchecked","rawtypes"})
	private ParseStatus equation(T algebra, BigList<Token> tokens, long pos) {
		ParseStatus status1 = term(algebra, tokens, pos);
		if (status1.errMsg != null) return status1;
		ParseStatus status2 = status1;
		if (match(Plus.class, tokens, status1.tokenNumber)) {
			status2 = equation(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.procedure = new AddL(algebra, status1.procedure, status2.procedure);
		}
		else if (match(Minus.class, tokens, status1.tokenNumber)) {
			status2 = equation(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.procedure = new SubtractL(algebra, status1.procedure, status2.procedure);
		}
		return status2;
	}
	
	/*
	term =
	 factor |
	 factor “*” term |
	 factor “\” term |
	 factor “%” term
	*/
	@SuppressWarnings({"unchecked","rawtypes"})
	private ParseStatus term(T algebra, BigList<Token> tokens, long pos) {
		ParseStatus status1 = factor(algebra, tokens, pos);
		if (status1.errMsg != null) return status1;
		ParseStatus status2 = status1;
		if (match(Times.class, tokens, status1.tokenNumber)) {
			status2 = term(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.procedure = new MultiplyL(algebra, status1.procedure, status2.procedure);
		}
		else if (match(Divide.class, tokens, status1.tokenNumber)) {
			status2 = term(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.procedure = new DivideL(algebra, status1.procedure, status2.procedure);
		}
		else if (match(Mod.class, tokens, status1.tokenNumber)) {
			status2 = term(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.procedure = new ModL(algebra, status1.procedure, status2.procedure);
		}
		return status2;
	}
	
	/*
	factor =
	 signedAtom |
	 signedAtom “^” factor
	*/
	@SuppressWarnings({"unchecked","rawtypes"})
	private ParseStatus factor(T algebra, BigList<Token> tokens, long pos) {
		ParseStatus status1 = signedAtom(algebra, tokens, pos);
		if (status1.errMsg != null) return status1;
		ParseStatus status2 = status1;
		if (match(Power.class, tokens, status1.tokenNumber)) {
			status2 = factor(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.procedure = new PowL(algebra, status1.procedure, status2.procedure);
		}
		return status2;
	}
	
	/*
	signedAtom
	  atom |
	  "+" atom |
	  "-" atom
	*/
	@SuppressWarnings({"unchecked","rawtypes"})
	private ParseStatus signedAtom(T algebra, BigList<Token> tokens, long pos) {
		if (match(Plus.class, tokens, pos)) {
			return atom(algebra, tokens, pos+1);
		}
		else if (match(Minus.class, tokens, pos)) {
			ParseStatus status = atom(algebra, tokens, pos+1);
			if (status.errMsg != null) return status;
			status.procedure = new NegateL(algebra, status.procedure);
			return status;
		}
		else
			return atom(algebra, tokens, pos);
	}
	
	/*
	atom =
	 identifier |
	 function “(“ equation “)” |
	 num |
	 “(“ equation “)” 
	*/
	@SuppressWarnings({"unchecked","rawtypes"})
	private ParseStatus atom(T algebra, BigList<Token> tokens, long pos) {
		if (match(Index.class, tokens, pos)) {
			Index idx = (Index) tokens.get(pos);
			int index = idx.getNumber();
			ParseStatus status = new ParseStatus();
			status.tokenNumber = pos + 1;
			status.procedure = new VariableConstantL(algebra, index);
			return status;
		}
		else if (match(FunctionName.class, tokens, pos)) {
			FunctionName funcCall = (FunctionName) tokens.get(pos);
			// 0 arg functions
			if (funcCall.getText() == "rand") {
				ParseStatus status = new ParseStatus();
				status.procedure = createFunction(algebra, funcCall.getText(), null);
				status.tokenNumber = pos + 1;
				return status;
			}
			else {
				// 1 arg functions
				if (!match(OpenParen.class, tokens, pos+1))
					return syntaxError(pos+1, tokens,
								"Function call definition expected a '('");
				ParseStatus status = equation(algebra, tokens, pos+2);
				if (status.errMsg != null) return status;
				if (!match(CloseParen.class, tokens, status.tokenNumber))
					return syntaxError(
							status.tokenNumber,
							tokens,
							"Function call definition expected a ')'");
				status.procedure = createFunction(algebra, funcCall.getText(), status.procedure);
				status.tokenNumber++;
				return status;
			}
		}
		else if (match(OpenParen.class, tokens, pos)) {
			ParseStatus status = equation(algebra, tokens, pos+1);
			if (status.errMsg != null) return status;
			if (!match(CloseParen.class, tokens, status.tokenNumber))
				return syntaxError(
						status.tokenNumber, tokens, "Expected a ')'");
			status.tokenNumber++;
			return status;
		}
		else if (match(Min.class, tokens, pos) ||
					match(Max.class, tokens, pos))
		{
			// 2 arg functions
			if (!match(OpenParen.class, tokens, pos+1))
				return syntaxError(pos+1, tokens, "Expected a '('.");
			ParseStatus status1 = equation(algebra, tokens, pos+2);
			if (status1.errMsg != null) return status1;
			if (!match(Comma.class, tokens, status1.tokenNumber))
				return syntaxError(status1.tokenNumber, tokens, "Expected a ','.");
			ParseStatus status2 = equation(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			if (!match(CloseParen.class, tokens, status2.tokenNumber))
				return syntaxError(status2.tokenNumber, tokens, "Expected a ')'.");
			ParseStatus status = new ParseStatus();
			status.tokenNumber = status2.tokenNumber+1;
			if (match(Min.class, tokens, pos))
				status.procedure = new MinL(algebra, status1.procedure, status2.procedure);
			else
				status.procedure = new MaxL(algebra, status1.procedure, status2.procedure);
			return status;
		}
		else
			return num(algebra, tokens, pos);
	}
	
	/*
	num = number | vector | matrix | tensor of octonions
	*/
	@SuppressWarnings({"unchecked","rawtypes"})
	private ParseStatus num(T algebra, BigList<Token> tokens, long pos) {
		try {
			Numeric tok = (Numeric) tokens.get(pos);
			ParseStatus status = new ParseStatus();
			status.tokenNumber = pos + 1;
			status.procedure = new ConstantL(algebra, tok.value);
			return status;
		} catch (Exception e) {
			return syntaxError(pos, tokens, "Expected something numeric.");
		}
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	private Procedure<U> createFunction(T algebra, String funcName, Procedure<U> ancestor1) {
		if (algebra instanceof InverseTrigonometric<?>) {
			if (funcName.equals("acos"))
				return new AcosL(algebra,ancestor1);
			if (funcName.equals("asin"))
				return new AsinL(algebra,ancestor1);
			if (funcName.equals("atan"))
				return new AtanL(algebra,ancestor1);
		}
		if (algebra instanceof InverseHyperbolic<?>) {
			if (funcName.equals("acosh"))
				return new AcoshL(algebra,ancestor1);
			if (funcName.equals("asinh"))
				return new AsinhL(algebra,ancestor1);
			if (funcName.equals("atanh"))
				return new AtanhL(algebra,ancestor1);
		}
		if (algebra instanceof Roots<?>) {
			if (funcName.equals("cbrt"))
				return new CbrtL(algebra,ancestor1);
			if (funcName.equals("sqrt"))
				return new SqrtL(algebra,ancestor1);
		}
		if (algebra instanceof Trigonometric<?>) {
			if (funcName.equals("cos"))
				return new CosL(algebra,ancestor1);
			if (funcName.equals("sin"))
				return new SinL(algebra,ancestor1);
			if (funcName.equals("tan"))
				return new TanL(algebra,ancestor1);
			if (funcName.equals("sinc"))
				return new SincL(algebra,ancestor1);
			if (funcName.equals("sincpi"))
				return new SincpiL(algebra,ancestor1);
		}
		if (algebra instanceof Hyperbolic<?>) {
			if (funcName.equals("cosh"))
				return new CoshL(algebra,ancestor1);
			if (funcName.equals("sinh"))
				return new SinhL(algebra,ancestor1);
			if (funcName.equals("tanh"))
				return new TanhL(algebra,ancestor1);
			if (funcName.equals("sinch"))
				return new SinchL(algebra,ancestor1);
			if (funcName.equals("sinchpi"))
				return new SinchpiL(algebra,ancestor1);
		}
		if (algebra instanceof Exponential<?>) {
			if (funcName.equals("exp"))
				return new ExpL(algebra,ancestor1);
			if (funcName.equals("log"))
				return new LogL(algebra,ancestor1);
		}
		if (algebra instanceof Random<?>) {
			if (funcName.equals("rand"))
				return new RandL(algebra);
		}
		
		throw new IllegalArgumentException("Unsupported function type : "+funcName+" for given algebra");
	}
}
