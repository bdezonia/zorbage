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

import java.util.Map;

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
import nom.bdezonia.zorbage.procedure.impl.ModL;
import nom.bdezonia.zorbage.procedure.impl.MultiplyL;
import nom.bdezonia.zorbage.procedure.impl.NegateL;
import nom.bdezonia.zorbage.procedure.impl.PowL;
import nom.bdezonia.zorbage.procedure.impl.RandL;
import nom.bdezonia.zorbage.procedure.impl.Sin;
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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EquationParser<T extends Algebra<T,U>,U> {

	public Tuple2<String,Procedure<U>> parse(T algebra, String string) {
		
		Tuple2<String,BigList<Token>> lexResult = new Lexer().lex(algebra, string);
		
		if (lexResult.a() == null) {
			return new Parser().parse(algebra, lexResult.b());
		}
		else {
			Tuple2<String,Procedure<U>> tuple = new Tuple2<String,Procedure<U>>(null, null);
			tuple.setA(lexResult.a());
			tuple.setB(new ZeroL(algebra));
			return tuple;
		}
	}
	private class Token {}
	
	private class Index extends Token {
		int number() { return -1; } // TODO
	}
	
	private class FunctionName extends Token {
		
		private final String name;
		
		FunctionName(String name) {
			this.name = name;
		}
		
		String stringValue() { return name; }
	}
	
	private class OpenParen extends Token {}
	
	private class CloseParen extends Token {}
	
	private class Plus extends Token {}
	
	private class Minus extends Token {}
	
	private class Times extends Token {}
	
	private class Divide extends Token {}
	
	private class Power extends Token {}
	
	//private class Comma extends Token {}
	
	private class Numeric extends Token {
		private String value;
		
		Numeric(String value) {
			this.value = value;
		}
		
		String stringValue() { return value; }
	}

	private class Mod extends Token {}

	private class Lexer {
		
		Tuple2<String,BigList<Token>> lex(T alg, String str) {
			Tuple2<String,BigList<Token>> result = new Tuple2<String, BigList<Token>>(null, null);
			result.setA(null);
			result.setB(new BigList<Token>());
			return result;
		}
	}
	
	private class Parser {
		Tuple2<String,Procedure<U>> parse(T algebra, BigList<Token> tokens) {
			ParseStatus result = equation(algebra, tokens, 0);
			Tuple2<String,Procedure<U>> retVal = new Tuple2<String, Procedure<U>>(null, null);
			if (result.errMsg != null) {
				retVal.setA(result.errMsg);
			}
			else {
				retVal.setB(result.function);
			}
			return retVal;
		}
	}
	
	private class ParseStatus {
		String errMsg;
		long tokenNumber;
		Procedure<U> function;
	}
	
	public EquationParser() {}

	private boolean match(Class<?> tokClass, BigList<Token> tokens, long pos) {
		return false;
	}
	
	private ParseStatus syntaxError(long pos, BigList<Token> tokens, String errMsg) {
		return null;
	}
	
	/*
	equation =
	 term |
	 term “+” equation |
	 term “-” equation
	*/
	public ParseStatus equation(T algebra, BigList<Token> tokens, long pos) {
		ParseStatus status1 = term(algebra, tokens, pos);
		if (status1.errMsg != null) return status1;
		ParseStatus status2 = status1;
		if (match(Plus.class, tokens, status1.tokenNumber)) {
			status2 = equation(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.function = new AddL(algebra, status1.function, status2.function);
		}
		else if (match(Minus.class, tokens, status1.tokenNumber)) {
			status2 = equation(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.function = new SubtractL(algebra, status1.function, status2.function);
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
	private ParseStatus term(T algebra, BigList<Token> tokens, long pos) {
		ParseStatus status1 = factor(algebra, tokens, pos);
		if (status1.errMsg != null) return status1;
		ParseStatus status2 = status1;
		if (match(Times.class, tokens, status1.tokenNumber)) {
			status2 = term(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.function = new MultiplyL(algebra, status1.function, status2.function);
		}
		else if (match(Divide.class, tokens, status1.tokenNumber)) {
			status2 = term(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.function = new DivideL(algebra, status1.function, status2.function);
		}
		else if (match(Mod.class, tokens, status1.tokenNumber)) {
			status2 = term(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.function = new ModL(algebra, status1.function, status2.function);
		}
		return status2;
	}
	
	/*
	factor =
	 signedAtom |
	 signedAtom “^” factor
	*/
	private ParseStatus factor(T algebra, BigList<Token> tokens, long pos) {
		ParseStatus status1 = signedAtom(algebra, tokens, pos);
		if (status1.errMsg != null) return status1;
		ParseStatus status2 = status1;
		if (match(Power.class, tokens, status1.tokenNumber)) {
			status2 = factor(algebra, tokens, status1.tokenNumber+1);
			if (status2.errMsg != null) return status2;
			status2.function = new PowL(algebra, status1.function, status2.function);
		}
		return status2;
	}
	
	/*
	signedAtom
	  atom |
	  "+" atom |
	  "-" atom
	*/
	private ParseStatus signedAtom(T algebra, BigList<Token> tokens, long pos) {
		if (match(Plus.class, tokens, pos)) {
			return atom(algebra, tokens, pos+1);
		}
		else if (match(Minus.class, tokens, pos)) {
			ParseStatus status = atom(algebra, tokens, pos+1);
			if (status.errMsg != null) return status;
			status.function = new NegateL(algebra, status.function);
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
	private ParseStatus atom(T algebra, BigList<Token> tokens, long pos) {
		if (match(Index.class, tokens, pos)) {
			Index idx = (Index) tokens.get(pos);
			int index = idx.number();
			ParseStatus status = new ParseStatus();
			status.tokenNumber = pos + 1;
			status.function = new VariableConstantL(algebra, index);
			return status;
		}
		else if (match(FunctionName.class, tokens, pos)) {
			FunctionName funcCall = (FunctionName) tokens.get(pos);
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
			status.function = createFunction(algebra, funcCall.stringValue(), status.function);
			status.tokenNumber++;
			return status;
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
		else
			return num(algebra, tokens, pos);
	}
	
	/*
	num = number | vector | matrix | tensor of octonions
	*/
	private ParseStatus num(T algebra, BigList<Token> tokens, long pos) {
		U value;
		try {
			Numeric tok = (Numeric) tokens.get(pos);
			value = algebra.construct(tok.stringValue());
			ParseStatus status = new ParseStatus();
			status.tokenNumber = pos + 1;
			status.function = new ConstantL(algebra, value);
			return status;
		} catch (Exception e ) {
			return syntaxError(pos, tokens, "Expected something numeric.");
		}
	}

	private Procedure<U> createFunction(T algebra, String funcName, Procedure<U> ancestor1) {
		if (funcName.equals("acos"))
			return new AcosL(algebra,ancestor1);
		if (funcName.equals("acosh"))
			return new AcoshL(algebra,ancestor1);
		if (funcName.equals("asin"))
			return new AsinL(algebra,ancestor1);
		if (funcName.equals("asinh"))
			return new AsinhL(algebra,ancestor1);
		if (funcName.equals("atan"))
			return new AtanL(algebra,ancestor1);
		if (funcName.equals("atanh"))
			return new AtanhL(algebra,ancestor1);
		if (funcName.equals("cbrt"))
			return new CbrtL(algebra,ancestor1);
		if (funcName.equals("cos"))
			return new CosL(algebra,ancestor1);
		if (funcName.equals("cosh"))
			return new CoshL(algebra,ancestor1);
		if (funcName.equals("exp"))
			return new ExpL(algebra,ancestor1);
		if (funcName.equals("log"))
			return new LogL(algebra,ancestor1);
		if (funcName.equals("rand"))
			return new RandL(algebra);
		if (funcName.equals("sin"))
			return new SinL(algebra,ancestor1);
		if (funcName.equals("sinh"))
			return new SinhL(algebra,ancestor1);
		if (funcName.equals("sinc"))
			return new SincL(algebra,ancestor1);
		if (funcName.equals("sinch"))
			return new SinchL(algebra,ancestor1);
		if (funcName.equals("sincpi"))
			return new SincpiL(algebra,ancestor1);
		if (funcName.equals("sinchpi"))
			return new SinchpiL(algebra,ancestor1);
		if (funcName.equals("sqrt"))
			return new SqrtL(algebra,ancestor1);
		if (funcName.equals("tan"))
			return new TanL(algebra,ancestor1);
		if (funcName.equals("tanh"))
			return new TanhL(algebra,ancestor1);
		if (funcName.equals("zero"))
			return new ZeroL(algebra);
		throw new IllegalArgumentException("unsupported function type : "+funcName);
	}
}
