/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.character;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Bounded;
import nom.bdezonia.zorbage.algebra.ConstructibleFromChar;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.algebra.PredSucc;
import nom.bdezonia.zorbage.algebra.Random;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CharAlgebra
	implements
		Algebra<CharAlgebra, CharMember>,
		Ordered<CharMember>,
		PredSucc<CharMember>,
		Random<CharMember>,
		Bounded<CharMember>,
		ConstructibleFromChar<CharMember>
{
	@Override
	public String typeDescription() {
		return "Character";
	}
	
	@Override
	public CharMember construct() {
		return new CharMember();
	}

	@Override
	public CharMember construct(CharMember other) {
		return new CharMember(other);
	}

	@Override
	public CharMember construct(String str) {
		return new CharMember(str);
	}

	@Override
	public CharMember construct(char... vals) {
		return new CharMember(vals[0]);
	}

	private final Function2<Boolean, CharMember, CharMember> EQ =
			new Function2<Boolean, CharMember, CharMember>()
	{
		@Override
		public Boolean call(CharMember a, CharMember b) {
			return a.v() == b.v();
		}
	};

	@Override
	public Function2<Boolean, CharMember, CharMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, CharMember, CharMember> NEQ =
			new Function2<Boolean, CharMember, CharMember>()
	{
		@Override
		public Boolean call(CharMember a, CharMember b) {
			return a.v() != b.v();
		}
	};

	@Override
	public Function2<Boolean, CharMember, CharMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<CharMember, CharMember> ASS =
			new Procedure2<CharMember, CharMember>()
	{
		@Override
		public void call(CharMember a, CharMember b) {
			b.set(a);
		}
	};
	@Override
	public Procedure2<CharMember, CharMember> assign() {
		return ASS;
	}

	private final Procedure1<CharMember> ZER =
			new Procedure1<CharMember>()
	{
		@Override
		public void call(CharMember a) {
			a.setV((char)0);
		}
	};

	@Override
	public Procedure1<CharMember> zero() {
		return ZER;
	}

	private final Function1<Boolean, CharMember> ISZER =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember a) {
			return a.v() == 0;
		}
	};

	@Override
	public Function1<Boolean, CharMember> isZero() {
		return ISZER;
	}

	private final Function2<Boolean, CharMember, CharMember> LESS =
			new Function2<Boolean, CharMember, CharMember>()
	{
		@Override
		public Boolean call(CharMember a, CharMember b) {
			return a.v() < b.v();
		}
	};

	@Override
	public Function2<Boolean, CharMember, CharMember> isLess() {
		return LESS;
	}

	private final Function2<Boolean, CharMember, CharMember> LE =
			new Function2<Boolean, CharMember, CharMember>()
	{
		@Override
		public Boolean call(CharMember a, CharMember b) {
			return a.v() <= b.v();
		}
	};

	@Override
	public Function2<Boolean, CharMember, CharMember> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, CharMember, CharMember> GREATER =
			new Function2<Boolean, CharMember, CharMember>()
	{
		@Override
		public Boolean call(CharMember a, CharMember b) {
			return a.v() > b.v();
		}
	};

	@Override
	public Function2<Boolean, CharMember, CharMember> isGreater() {
		return GREATER;
	}

	private final Function2<Boolean, CharMember, CharMember> GE =
			new Function2<Boolean, CharMember, CharMember>()
	{
		@Override
		public Boolean call(CharMember a, CharMember b) {
			return a.v() >= b.v();
		}
	};

	@Override
	public Function2<Boolean, CharMember, CharMember> isGreaterEqual() {
		return GE;
	}

	private final Function2<Integer, CharMember, CharMember>  CMP =
			new Function2<Integer, CharMember, CharMember>()
	{
		@Override
		public Integer call(CharMember a, CharMember b) {
			if (a.v() < b.v())
				return -1;
			else if (a.v() > b.v())
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function2<Integer, CharMember, CharMember> compare() {
		return CMP;
	}

	private final Function1<Integer, CharMember> SIG =
			new Function1<Integer, CharMember>()
	{
		@Override
		public Integer call(CharMember a) {
			if (a.v() < 0)
				return -1;
			else if (a.v() > 9)
				return 1;
			else
				return 0;
		}
	};

	@Override
	public Function1<Integer, CharMember> signum() {
		return SIG;
	}

	private final Procedure3<CharMember, CharMember, CharMember> MIN =
			new Procedure3<CharMember, CharMember, CharMember>()
	{
		@Override
		public void call(CharMember a, CharMember b, CharMember c) {
			if (a.v() < b.v())
				c.set(a);
			else
				c.set(b);
		}
	};
		
	@Override
	public Procedure3<CharMember, CharMember, CharMember> min() {
		return MIN;
	}

	private final Procedure3<CharMember, CharMember, CharMember> MAX =
			new Procedure3<CharMember, CharMember, CharMember>()
	{
		@Override
		public void call(CharMember a, CharMember b, CharMember c) {
			if (a.v() > b.v())
				c.set(a);
			else
				c.set(b);
		}
	};
		
	@Override
	public Procedure3<CharMember, CharMember, CharMember> max() {
		return MAX;
	}

	private final Procedure1<CharMember> RAND =
			new Procedure1<CharMember>()
	{
		@Override
		public void call(CharMember a) {
			int c = ThreadLocalRandom.current().nextInt(65536);
			a.setV((char)c);
		}
	};

	@Override
	public Procedure1<CharMember> random() {
		return RAND;
	}

	private final Procedure2<CharMember, CharMember> PRED =
			new Procedure2<CharMember, CharMember>()
	{
		@Override
		public void call(CharMember a, CharMember b) {
			if (a.v() == Character.MIN_VALUE)
				b.setV(Character.MAX_VALUE);
			else
				b.setV((char)(a.v() - 1));
		}
	};

	@Override
	public Procedure2<CharMember, CharMember> pred() {
		return PRED;
	}

	private final Procedure2<CharMember, CharMember> SUCC =
			new Procedure2<CharMember, CharMember>()
	{
		@Override
		public void call(CharMember a, CharMember b) {
			if (a.v() == Character.MAX_VALUE)
				b.setV(Character.MIN_VALUE);
			else
				b.setV((char)(a.v() + 1));
		}
	};

	@Override
	public Procedure2<CharMember, CharMember> succ() {
		return SUCC;
	}

	private final Procedure1<CharMember> MAXB =
			new Procedure1<CharMember>()
	{
		@Override
		public void call(CharMember a) {
			a.setV(Character.MAX_VALUE);
		}
	};

	@Override
	public Procedure1<CharMember> maxBound() {
		return MAXB;
	}

	private final Procedure1<CharMember> MINB =
			new Procedure1<CharMember>()
	{
		@Override
		public void call(CharMember a) {
			a.setV(Character.MIN_VALUE);
		}
	};

	@Override
	public Procedure1<CharMember> minBound() {
		return MINB;
	}

	private static final Function1<Boolean,CharMember> DEF =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isDefined(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isDefined() {
		return DEF;
	}

	private static final Function1<Boolean,CharMember> DIG =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isDigit(ch.v());
		}
	};
	
	public static Function1<Boolean,CharMember> isDigit() {
		return DIG;
	}

	private static final Function1<Boolean,CharMember> HS =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isHighSurrogate(ch.v());
		}
	};
	
	public static Function1<Boolean,CharMember> isHighSurrogate() {
		return HS;
	}

	private static final Function1<Boolean,CharMember> II =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isIdentifierIgnorable(ch.v());
		}
	};
	
	public static Function1<Boolean,CharMember> isIdentifierIgnorable() {
		return II;
	}

	private static final Function1<Boolean,CharMember> ISOC =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isISOControl(ch.v());
		}
	};
	
	public static Function1<Boolean,CharMember> isISOControl() {
		return ISOC;
	}

	private static final Function1<Boolean,CharMember> JIS =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isJavaIdentifierStart(ch.v());
		}
	};
	
	public static Function1<Boolean,CharMember> isJavaIdentifierStart() {
		return JIS;
	}

	private static final Function1<Boolean,CharMember> JIP =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isJavaIdentifierPart(ch.v());
		}
	};
	
	public static Function1<Boolean,CharMember> isJavaIdentifierPart() {
		return JIP;
	}

	private static final Function1<Boolean,CharMember> LET =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isLetter(ch.v());
		}
	};
	
	public static Function1<Boolean,CharMember> isLetter() {
		return LET;
	}

	private static final Function1<Boolean,CharMember> LETD =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isLetterOrDigit(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isLetterOrDigit() {
		return LETD;
	}

	private static final Function1<Boolean,CharMember> LOWER =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isLowerCase(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isLowerCase() {
		return LOWER;
	}

	private static final Function1<Boolean,CharMember> UPPER =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isUpperCase(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isUpperCase() {
		return UPPER;
	}

	private static final Function1<Boolean,CharMember> WS =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isWhitespace(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isWhitespace() {
		return WS;
	}

	private static final Function1<Boolean,CharMember> TTL =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isTitleCase(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isTitleCase() {
		return TTL;
	}

	private static final Function1<Boolean,CharMember> MIR =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isMirrored(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isMirrored() {
		return MIR;
	}

	private static final Function1<Boolean,CharMember> SURR =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isSurrogate(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isSurrogate() {
		return SURR;
	}

	private static final Function2<Boolean,CharMember,CharMember> SURRP =
			new Function2<Boolean, CharMember, CharMember>()
	{
		@Override
		public Boolean call(CharMember high, CharMember low) {
			return Character.isSurrogatePair(high.v(), low.v());
		}
	};

	public static Function2<Boolean,CharMember,CharMember> isSurrogatePair() {
		return SURRP;
	}

	private static final Function1<Boolean,CharMember> LSURR =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isLowSurrogate(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isLowSurrogate() {
		return LSURR;
	}

	private static final Function1<Boolean,CharMember> UIP =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isUnicodeIdentifierPart(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isUnicodeIdentifierPart() {
		return UIP;
	}

	private static final Function1<Boolean,CharMember> UIS =
			new Function1<Boolean, CharMember>()
	{
		@Override
		public Boolean call(CharMember ch) {
			return Character.isUnicodeIdentifierStart(ch.v());
		}
	};

	public static Function1<Boolean,CharMember> isUnicodeIdentifierStart() {
		return UIS;
	}
	
	private static final Function2<CharMember,Integer, Integer> FD =
			new Function2<CharMember,Integer, Integer>()
	{
		@Override
		public CharMember call(Integer digit, Integer radix) {
			return new CharMember(Character.forDigit(digit, radix));
		}
	};

	public static Function2<CharMember,Integer, Integer> forDigit() {
		return FD;
	}
	
	private static final Function1<Byte,CharMember> GDIR =
			new Function1<Byte, CharMember>()
	{
		@Override
		public Byte call(CharMember ch) {
			return Character.getDirectionality(ch.v());
		}
	};

	public static Function1<Byte,CharMember> getDirectionality() {
		return GDIR;
	}
	
	private static final Function1<Integer,CharMember> GNV =
			new Function1<Integer, CharMember>()
	{
		@Override
		public Integer call(CharMember ch) {
			return Character.getNumericValue(ch.v());
		}
	};

	public static Function1<Integer,CharMember> getNumericValue() {
		return GNV;
	}
	
	private static final Function1<Integer,CharMember> GT =
			new Function1<Integer, CharMember>()
	{
		@Override
		public Integer call(CharMember ch) {
			return Character.getType(ch.v());
		}
	};

	public static Function1<Integer,CharMember> getType() {
		return GT;
	}
	
	private static final Function1<CharMember,CharMember> RB =
			new Function1<CharMember, CharMember>()
	{
		@Override
		public CharMember call(CharMember ch) {
			return new CharMember(Character.reverseBytes(ch.v()));
		}
	};

	public static Function1<CharMember,CharMember> reverseBytes() {
		return RB;
	}
	
	private static final Function1<CharMember,Character> VO =
			new Function1<CharMember, Character>()
	{
		@Override
		public CharMember call(Character ch) {
			return new CharMember(ch);
		}
	};

	public static Function1<CharMember,Character> valueOf() {
		return VO;
	}
	
}
