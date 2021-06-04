/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.real.float128;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.concurrent.ThreadLocalRandom;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.Bounded;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.Exponential;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Hyperbolic;
import nom.bdezonia.zorbage.algebra.Infinite;
import nom.bdezonia.zorbage.algebra.InverseHyperbolic;
import nom.bdezonia.zorbage.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.algebra.MiscFloat;
import nom.bdezonia.zorbage.algebra.ModularDivision;
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.algebra.NegInfinite;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.OrderedField;
import nom.bdezonia.zorbage.algebra.Power;
import nom.bdezonia.zorbage.algebra.PredSucc;
import nom.bdezonia.zorbage.algebra.Random;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.RealUnreal;
import nom.bdezonia.zorbage.algebra.Roots;
import nom.bdezonia.zorbage.algebra.Rounding;
import nom.bdezonia.zorbage.algebra.Scale;
import nom.bdezonia.zorbage.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.algebra.ScaleByOneHalf;
import nom.bdezonia.zorbage.algebra.ScaleByRational;
import nom.bdezonia.zorbage.algebra.ScaleByTwo;
import nom.bdezonia.zorbage.algebra.ScaleComponents;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
import nom.bdezonia.zorbage.algorithm.PowerAny;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// TODO make sure CONTEXT used on all multiplies and all non 2-based divides

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float128Algebra
	implements
		OrderedField<Float128Algebra,Float128Member,Float128Member>,
		Bounded<Float128Member>,
		Norm<Float128Member,Float128Member>,
		RealConstants<Float128Member>,
		Exponential<Float128Member>,
		Trigonometric<Float128Member>,
		InverseTrigonometric<Float128Member>,
		Hyperbolic<Float128Member>,
		InverseHyperbolic<Float128Member>,
		Infinite<Float128Member>,
		NegInfinite<Float128Member>,
		NaN<Float128Member>,
		Roots<Float128Member>,
		Power<Float128Member>,
		Rounding<Float128Member,Float128Member>,
		Random<Float128Member>,
		RealUnreal<Float128Member,Float128Member>,
		PredSucc<Float128Member>,
		MiscFloat<Float128Member>,
		ModularDivision<Float128Member>,
		Conjugate<Float128Member>,
		Scale<Float128Member,Float128Member>,
		ScaleByHighPrec<Float128Member>,
		ScaleByRational<Float128Member>,
		ScaleByDouble<Float128Member>,
		ScaleComponents<Float128Member, Float128Member>,
		Tolerance<Float128Member,Float128Member>,
		ScaleByOneHalf<Float128Member>,
		ScaleByTwo<Float128Member>
{
	public static final MathContext CONTEXT = new MathContext(38);

	private static final BigDecimal _PI    = new BigDecimal("3.141592653589793238462643383279502884197");
	private static final BigDecimal _E     = new BigDecimal("2.718281828459045235360287471352662497757");
	private static final BigDecimal _PHI   = new BigDecimal("0.577215664901532860606512090082402431042");
	private static final BigDecimal _GAMMA = new BigDecimal("1.618033988749894848204586834365638117720");
	
	private static final BigDecimal TWO = BigDecimal.valueOf(2);
	private static final BigDecimal PI_OVER_TWO = _PI.divide(TWO);
	private static final BigDecimal MINUS_PI_OVER_TWO = _PI.negate().divide(TWO);
	
	@Override
	public Float128Member construct() {
		return new Float128Member();
	}

	@Override
	public Float128Member construct(Float128Member other) {
		return new Float128Member(other);
	}

	@Override
	public Float128Member construct(String str) {
		return new Float128Member(str);
	}

	private final Function2<Boolean, Float128Member, Float128Member> EQ =
			new Function2<Boolean, Float128Member, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a, Float128Member b) {

			// neg zero == pos zero == zero
			
			if ((a.classification == Float128Member.POSZERO ||
					a.classification == Float128Member.NEGZERO) &&
				(b.classification == Float128Member.POSZERO ||
					b.classification == Float128Member.NEGZERO))
			{
				return true;
			}
			
			// nans never equal anything
			
			if (a.classification == Float128Member.NAN || b.classification == Float128Member.NAN) {
				return false;
			}
			
			// if classifications are different amongst remaining choices they can't be equal
			
			if (a.classification != b.classification) {
				return false;
			}
			
			// if here classifications are the same and neither is nan

			// two regular numbers
			
			if (a.classification == Float128Member.NORMAL) {
				return a.num.compareTo(b.num) == 0;
			}
			
			// neg and pos zeroes
			
			if (a.classification == Float128Member.POSZERO || a.classification == Float128Member.NEGZERO) {
				return b.classification == Float128Member.POSZERO || b.classification == Float128Member.NEGZERO;
			}
			
			// pos inf
			
			if (a.classification == Float128Member.POSINF) {
				// we know by logic that b has the same type
				return true;
			}
			
			// neg inf
			
			if (a.classification == Float128Member.NEGINF) {
				// we know by logic that b has the same type
				return true;
			}
			
			throw new IllegalArgumentException("unknown classifications error "+a.classification);
		}
	};

	@Override
	public Function2<Boolean, Float128Member, Float128Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Float128Member, Float128Member> NEQ =
			new Function2<Boolean, Float128Member, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a, Float128Member b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, Float128Member, Float128Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float128Member, Float128Member> ASS =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			b.set(a);
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> assign() {
		return ASS;
	}

	private final Procedure1<Float128Member> ZER =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<Float128Member> zero() {
		return ZER;
	}

	private final Function1<Boolean, Float128Member> ISZER =
			new Function1<Boolean, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a) {
			return a.classification == Float128Member.POSZERO || a.classification == Float128Member.NEGZERO;
		}
	};
			
	@Override
	public Function1<Boolean, Float128Member> isZero() {
		return ISZER;
	}

	private final Procedure2<Float128Member, Float128Member> NEG =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch(a.classification) {
			
			case Float128Member.NORMAL:
				b.setV(a.num.negate());
				break;
				
			case Float128Member.POSZERO:
				b.setNegZero();
				break;
				
			case Float128Member.NEGZERO:
				b.setPosZero();
				break;
				
			case Float128Member.POSINF:
				b.setNegInf();
				break;
				
			case Float128Member.NEGINF:
				b.setPosInf();
				break;
				
			case Float128Member.NAN:
				b.setNan();
				break;
				
			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> negate() {
		return NEG;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> ADD =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member c) {
			
			// NOTE: the various special cases were derived from Java's behavior for doubles
			
			switch(a.classification) {
			
			case Float128Member.NORMAL:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setV(a.num.add(b.num));
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.set(a);
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.set(a);
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.POSZERO:
				
				if (b.classification == Float128Member.NORMAL) {
					c.set(b);
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
			
			case Float128Member.NEGZERO:
				
				if (b.classification == Float128Member.NORMAL) {
					c.set(b);
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNegZero();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;
				
			case Float128Member.POSINF:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;
				
			case Float128Member.NEGINF:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.NAN:
				c.setNan();
				break;
			
			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> add() {
		return ADD;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> SUB =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member c) {
			
			// NOTE: the various special cases were derived from Java's behavior for doubles
			
			switch (a.classification) {
			
			case Float128Member.NORMAL:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setV(a.num.subtract(b.num));
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.set(a);
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.set(a);
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.POSZERO:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setV(b.v().negate());
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.NEGZERO:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setV(b.v().negate());
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNegZero();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.POSINF:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.NEGINF:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.NAN:
				c.setNan();
				break;

			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> subtract() {
		return SUB;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> MUL =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member c) {
			
			// NOTE: the various special cases were derived from Java's behavior for doubles
			
			switch (a.classification) {
			
			case Float128Member.NORMAL:

				if (b.classification == Float128Member.NORMAL) {
					c.setV(a.num.multiply(b.num, CONTEXT));
				}
				else if (b.classification == Float128Member.POSZERO) {
					if (a.num.signum() < 0)
						c.setNegZero();
					else
						c.setPosZero();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					if (a.num.signum() < 0)
						c.setPosZero();
					else
						c.setNegZero();
				}
				else if (b.classification == Float128Member.POSINF) {
					if (a.num.signum() < 0)
						c.setNegInf();
					else
						c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					if (a.num.signum() < 0)
						c.setPosInf();
					else
						c.setNegInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.POSZERO:
				
				if (b.classification == Float128Member.NORMAL) {
					if (b.num.signum() < 0)
						c.setNegZero();
					else
						c.setPosZero();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNegZero();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.NEGZERO:
				
				if (b.classification == Float128Member.NORMAL) {
					if (b.num.signum() < 0)
						c.setPosZero();
					else
						c.setNegZero();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNegZero();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.POSINF:
				
				if (b.classification == Float128Member.NORMAL) {
					if (b.num.signum() < 0)
						c.setNegInf();
					else
						c.setPosInf();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNan();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.NEGINF:
				
				if (b.classification == Float128Member.NORMAL) {
					if (b.num.signum() < 0)
						c.setPosInf();
					else
						c.setNegInf();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNan();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.NAN:
				c.setNan();
				break;

			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> multiply() {
		return MUL;
	}

	private final Procedure3<Integer, Float128Member, Float128Member> POWER =
			new Procedure3<Integer, Float128Member, Float128Member>()
	{
		@Override
		public void call(Integer p, Float128Member a, Float128Member b) {
			if (a.classification == Float128Member.NAN) {
				b.setNan();
			}
			else
				PowerAny.compute(G.QUAD, p, a, b);
		}
	};
			
	@Override
	public Procedure3<Integer, Float128Member, Float128Member> power() {
		return POWER;
	}

	private final Procedure1<Float128Member> UNITY =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setV(BigDecimal.ONE);
		}
	};

	@Override
	public Procedure1<Float128Member> unity() {
		return UNITY;
	}

	private final Function1<Boolean, Float128Member> ISUNITY =
			new Function1<Boolean, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a) {
			return a.classification == Float128Member.NORMAL && a.num.compareTo(BigDecimal.ONE) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, Float128Member> isUnity() {
		return ISUNITY;
	}

	private final Procedure2<Float128Member, Float128Member> INV =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
			case Float128Member.NORMAL:
				b.setV(BigDecimal.ONE.divide(a.num, CONTEXT));
				break;
			case Float128Member.POSZERO:
				b.setPosInf();
				break;
			case Float128Member.NEGZERO:
				b.setNegInf();
				break;
			case Float128Member.POSINF:
				b.setPosZero();
				break;
			case Float128Member.NEGINF:
				b.setNegZero();
				break;
			case Float128Member.NAN:
				b.setNan();
				break;
			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> invert() {
		return INV;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> DIVIDE =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member c) {

			// NOTE: the various special cases were derived from Java's behavior for doubles
			
			switch (a.classification) {
			
			case Float128Member.NORMAL:
				
				if (b.classification == Float128Member.NORMAL) {
					c.setV(a.num.divide(b.num, CONTEXT));
				}
				else if (b.classification == Float128Member.POSZERO) {
					if (a.num.signum() < 0)
						c.setNegInf();
					else
						c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					if (a.num.signum() < 0)
						c.setPosInf();
					else
						c.setNegInf();
				}
				else if (b.classification == Float128Member.POSINF) {
					if (a.num.signum() < 0)
						c.setNegZero();
					else
						c.setPosZero();
				}
				else if (b.classification == Float128Member.NEGINF) {
					if (a.num.signum() < 0)
						c.setPosZero();
					else
						c.setNegZero();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;

			case Float128Member.POSZERO:
				
				if (b.classification == Float128Member.NORMAL) {
					if (b.num.signum() < 0)
						c.setNegZero();
					else
						c.setPosZero();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNan();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNegZero();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;
			
			case Float128Member.NEGZERO:
				
				if (b.classification == Float128Member.NORMAL) {
					if (b.num.signum() < 0)
						c.setPosZero();
					else
						c.setNegZero();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNan();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNegZero();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setPosZero();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;
			
			case Float128Member.POSINF:
				
				if (b.classification == Float128Member.NORMAL) {
					if (b.num.signum() < 0)
						c.setNegInf();
					else
						c.setPosInf();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;
			
			case Float128Member.NEGINF:
				
				if (b.classification == Float128Member.NORMAL) {
					if (b.num.signum() < 0)
						c.setPosInf();
					else
						c.setNegInf();
				}
				else if (b.classification == Float128Member.POSZERO) {
					c.setNegInf();
				}
				else if (b.classification == Float128Member.NEGZERO) {
					c.setPosInf();
				}
				else if (b.classification == Float128Member.POSINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NEGINF) {
					c.setNan();
				}
				else if (b.classification == Float128Member.NAN) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error "+b.classification);
				break;
			
			case Float128Member.NAN:
				c.setNan();
				break;

			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> divide() {
		return DIVIDE;
	}

	private final Function2<Boolean, Float128Member, Float128Member> LESSER =
			new Function2<Boolean, Float128Member, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a, Float128Member b) {
			return compare().call(a, b) < 0;
		}
	};
			
	@Override
	public Function2<Boolean, Float128Member, Float128Member> isLess() {
		return LESSER;
	}

	private final Function2<Boolean, Float128Member, Float128Member> LE =
			new Function2<Boolean, Float128Member, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a, Float128Member b) {
			return compare().call(a, b) <= 0;
		}
	};
			
	@Override
	public Function2<Boolean, Float128Member, Float128Member> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean, Float128Member, Float128Member> GREATER =
			new Function2<Boolean, Float128Member, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a, Float128Member b) {
			return compare().call(a, b) > 0;
		}
	};
			
	@Override
	public Function2<Boolean, Float128Member, Float128Member> isGreater() {
		return GREATER;
	}

	private final Function2<Boolean, Float128Member, Float128Member> GE =
			new Function2<Boolean, Float128Member, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a, Float128Member b) {
			return compare().call(a, b) >= 0;
		}
	};
			
	@Override
	public Function2<Boolean, Float128Member, Float128Member> isGreaterEqual() {
		return GE;
	}

	private final Function2<Integer, Float128Member, Float128Member> CMP =
			new Function2<Integer, Float128Member, Float128Member>()
	{
		@Override
		public Integer call(Float128Member a, Float128Member b) {

			switch (a.classification) {
			
			case Float128Member.NORMAL:

				if (b.classification == Float128Member.NORMAL) {
					return a.num.compareTo(b.num);
				}
				else if (b.classification == Float128Member.POSZERO) {
					return a.num.compareTo(BigDecimal.ZERO);
				}
				else if (b.classification == Float128Member.NEGZERO) {
					return a.num.compareTo(BigDecimal.ZERO);
				}
				else if (b.classification == Float128Member.POSINF) {
					return -1;
				}
				else if (b.classification == Float128Member.NEGINF) {
					return 1;
				}
				else if (b.classification == Float128Member.NAN) {
					return -1;
				}
				else
					throw new IllegalArgumentException("unknown classification error "+a.classification);
				
			case Float128Member.POSZERO:

				if (b.classification == Float128Member.NORMAL) {
					return BigDecimal.ZERO.compareTo(b.num);
				}
				else if (b.classification == Float128Member.POSZERO) {
					return 0;
				}
				else if (b.classification == Float128Member.NEGZERO) {
					return 1;
				}
				else if (b.classification == Float128Member.POSINF) {
					return -1;
				}
				else if (b.classification == Float128Member.NEGINF) {
					return 1;
				}
				else if (b.classification == Float128Member.NAN) {
					return -1;
				}
				else
					throw new IllegalArgumentException("unknown classification error "+a.classification);
				
			case Float128Member.NEGZERO:

				if (b.classification == Float128Member.NORMAL) {
					return BigDecimal.ZERO.compareTo(b.num);
				}
				else if (b.classification == Float128Member.POSZERO) {
					return -1;
				}
				else if (b.classification == Float128Member.NEGZERO) {
					return 0;
				}
				else if (b.classification == Float128Member.POSINF) {
					return -1;
				}
				else if (b.classification == Float128Member.NEGINF) {
					return 1;
				}
				else if (b.classification == Float128Member.NAN) {
					return -1;
				}
				else
					throw new IllegalArgumentException("unknown classification error "+a.classification);

			case Float128Member.POSINF:

				if (b.classification == Float128Member.NORMAL) {
					return 1;
				}
				else if (b.classification == Float128Member.POSZERO) {
					return 1;
				}
				else if (b.classification == Float128Member.NEGZERO) {
					return 1;
				}
				else if (b.classification == Float128Member.POSINF) {
					return 0;
				}
				else if (b.classification == Float128Member.NEGINF) {
					return 1;
				}
				else if (b.classification == Float128Member.NAN) {
					return -1;
				}
				else
					throw new IllegalArgumentException("unknown classification error "+a.classification);

			case Float128Member.NEGINF:

				if (b.classification == Float128Member.NORMAL) {
					return -1;
				}
				else if (b.classification == Float128Member.POSZERO) {
					return -1;
				}
				else if (b.classification == Float128Member.NEGZERO) {
					return -1;
				}
				else if (b.classification == Float128Member.POSINF) {
					return -1;
				}
				else if (b.classification == Float128Member.NEGINF) {
					return 0;
				}
				else if (b.classification == Float128Member.NAN) {
					return -1;
				}
				else
					throw new IllegalArgumentException("unknown classification error "+a.classification);

			case Float128Member.NAN:

				if (b.classification == Float128Member.NAN) {
					return 0;
				}
				else if (Math.abs(b.classification) < Float128Member.NAN)
					return -1;
				else
					throw new IllegalArgumentException("unknown classification error "+a.classification);

			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Function2<Integer, Float128Member, Float128Member> compare() {
		return CMP;
	}

	private final Function1<Integer, Float128Member> SIG =
			new Function1<Integer, Float128Member>()
	{
		@Override
		public Integer call(Float128Member a) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					return a.num.signum();
				case Float128Member.POSZERO:
					return 0;
				case Float128Member.NEGZERO:
					return 0;
				case Float128Member.POSINF:
					return 1;
				case Float128Member.NEGINF:
					return -1;
				case Float128Member.NAN:
					throw new IllegalArgumentException("signum of a nan value is impossible");
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
	
	@Override
	public Function1<Integer, Float128Member> signum() {
		return SIG;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> MIN =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member c) {
			Min.compute(G.QUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> min() {
		return MIN;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> MAX =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member c) {
			Max.compute(G.QUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> max() {
		return MAX;
	}

	private final Procedure2<Float128Member, Float128Member> ABS =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			
			switch(a.classification) {

			case Float128Member.NORMAL:
				b.setV(a.v().abs());
				break;

			case Float128Member.POSZERO:
				b.setPosZero();
				break;

			case Float128Member.NEGZERO:
				b.setPosZero();
				break;

			case Float128Member.POSINF:
				b.setPosInf();
				break;

			case Float128Member.NEGINF:
				b.setPosInf();
				break;
			
			case Float128Member.NAN:
				b.setNan();
				break;
			
			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> abs() {
		return ABS;
	}

	private final Procedure3<java.lang.Integer, Float128Member, Float128Member> STWO =
			new Procedure3<java.lang.Integer, Float128Member, Float128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, Float128Member a, Float128Member b) {
			Float128Member scale = new Float128Member(BigDecimal.valueOf(2));
			ScaleHelper.compute(G.QUAD, G.QUAD, scale, numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float128Member, Float128Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, Float128Member, Float128Member> SHALF =
			new Procedure3<java.lang.Integer, Float128Member, Float128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, Float128Member a, Float128Member b) {
			Float128Member scale = new Float128Member(BigDecimal.ONE.divide(BigDecimal.valueOf(2)));
			ScaleHelper.compute(G.QUAD, G.QUAD, scale, numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, Float128Member, Float128Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function3<Boolean, Float128Member, Float128Member, Float128Member> WITHIN =
			new Function3<Boolean, Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member tol, Float128Member a, Float128Member b) {
			return NumberWithin.compute(G.QUAD, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float128Member, Float128Member, Float128Member> within() {
		return WITHIN;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> scaleComponents() {
		return scale();
	}

	private final Procedure3<Double, Float128Member, Float128Member> SBD =
			new Procedure3<Double, Float128Member, Float128Member>()
	{
		@Override
		public void call(Double s, Float128Member a, Float128Member b) {
			Float128Member scale = new Float128Member();
			if (s.isInfinite()) {
				if (Math.signum(s) < 0)
					scale.setNegInf();
				else
					scale.setPosInf();
			} else if (s.isNaN()) {
				scale.setNan();
			} else {
				// s is finite
				scale.setV(BigDecimal.valueOf(s));
			}
			multiply().call(scale, a, b);
		}
	};
	
	@Override
	public Procedure3<Double, Float128Member, Float128Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<RationalMember, Float128Member, Float128Member> SBR =
			new Procedure3<RationalMember, Float128Member, Float128Member>()
	{
		@Override
		public void call(RationalMember r, Float128Member a, Float128Member b) {
			Float128Member scale = new Float128Member();
			scale.setV(new BigDecimal(r.n()).divide(new BigDecimal(r.d()), CONTEXT));
			multiply().call(scale, a, b);
		}
	};
	
	@Override
	public Procedure3<RationalMember, Float128Member, Float128Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<HighPrecisionMember, Float128Member, Float128Member> SBHP =
			new Procedure3<HighPrecisionMember, Float128Member, Float128Member>()
	{
		@Override
		public void call(HighPrecisionMember s, Float128Member a, Float128Member b) {
			Float128Member scale = new Float128Member();
			scale.setV(s.v());
			multiply().call(scale, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, Float128Member, Float128Member> scaleByHighPrec() {
		return SBHP;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> scale() {
		return multiply();
	}

	private final Procedure2<Float128Member, Float128Member> CONJ =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			b.set(a);
		}	
	};
	
	@Override
	public Procedure2<Float128Member, Float128Member> conjugate() {
		return CONJ;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> DIV =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member d) {
			Float128Member tmp = G.QUAD.construct();
			divide().call(a, b, tmp);
			if (tmp.classification == Float128Member.NORMAL) {
				BigDecimal val = tmp.num.divideToIntegralValue(BigDecimal.ONE);
				// TODO test me
				d.setV(val);
			}
			else {
				// TODO: is this bulletproof?
				d.set(tmp);
			}
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> div() {
		return DIV;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> MOD =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member m) {
			Float128Member tmp = G.QUAD.construct();
			divide().call(a, b, tmp);
			if (tmp.classification == Float128Member.NORMAL) {
				BigDecimal val = tmp.num.divideToIntegralValue(BigDecimal.ONE);
				BigDecimal remainder;
				// TODO test me
				if (val.compareTo(tmp.num) <= 0)
					remainder = tmp.num.subtract(val);
				else
					remainder = val.subtract(tmp.num);
				m.setV(remainder);
			}
			else {
				// TODO: is this bulletproof?
				m.setPosZero();
			}
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> mod() {
		return MOD;
	}

	private final Procedure4<Float128Member, Float128Member, Float128Member, Float128Member> DIVMOD =
			new Procedure4<Float128Member, Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b, Float128Member d, Float128Member m) {
			Float128Member tmp = G.QUAD.construct();
			divide().call(a, b, tmp);
			if (tmp.classification == Float128Member.NORMAL) {
				BigDecimal val = tmp.num.divideToIntegralValue(BigDecimal.ONE);
				BigDecimal remainder;
				// TODO test me
				if (val.compareTo(tmp.num) <= 0)
					remainder = tmp.num.subtract(val);
				else
					remainder = val.subtract(tmp.num);
				d.setV(val);
				m.setV(remainder);
			}
			else {
				// TODO: is this bulletproof?
				d.set(tmp);
				m.setPosZero();
			}
		}
	};
			
	@Override
	public Procedure4<Float128Member, Float128Member, Float128Member, Float128Member> divMod() {
		return DIVMOD;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> CSGN =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member magnitude, Float128Member sign, Float128Member result) {
			BigDecimal mag;
			boolean neg;
			switch (sign.classification) {
				case Float128Member.NORMAL:
					neg = (sign.num.signum() >= 0);
					break;
				case Float128Member.POSZERO:
					neg = false;
					break;
				case Float128Member.NEGZERO:
					neg = true;
					break;
				case Float128Member.POSINF:
					neg = false;
					break;
				case Float128Member.NEGINF:
					neg = true;
					break;
				case Float128Member.NAN:
					// copy java StrictMath's approach
					neg = false;
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+magnitude.classification);
			}
			switch (magnitude.classification) {
				case Float128Member.NORMAL:
					mag = magnitude.num.abs();
					if (neg)
						mag = mag.negate();
					result.setV(mag);
					break;
				case Float128Member.POSZERO:
					if (neg)
						result.setNegZero();
					else
						result.setPosZero();
					break;
				case Float128Member.NEGZERO:
					if (neg)
						result.setNegZero();
					else
						result.setPosZero();
					break;
				case Float128Member.POSINF:
					if (neg)
						result.setNegInf();
					else
						result.setPosInf();
					break;
				case Float128Member.NEGINF:
					if (neg)
						result.setNegInf();
					else
						result.setPosInf();
					break;
				case Float128Member.NAN:
					result.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+magnitude.classification);
			}
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> copySign() {
		return CSGN;
	}

	private final Function1<Integer, Float128Member> GETEXP =
			new Function1<Integer, Float128Member>()
	{
		@Override
		public Integer call(Float128Member a) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					return BigDecimalMath.exponent(a.num);
				case Float128Member.POSZERO:
					return -16382 - 1;  // mirror java double behavior but adjusted for 128 bits
				case Float128Member.NEGZERO:
					return -16382 - 1;  // mirror java double behavior but adjusted for 128 bits
				case Float128Member.POSINF:
					return 16383 + 1;  // mirror java double behavior but adjusted for 128 bits
				case Float128Member.NEGINF:
					return 16383 + 1;  // mirror java double behavior but adjusted for 128 bits
				case Float128Member.NAN:
					return 16383 + 1;  // mirror java double behavior but adjusted for 128 bits
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
	
	@Override
	public Function1<Integer, Float128Member> getExponent() {
		return GETEXP;
	}

	private final Procedure3<Integer, Float128Member, Float128Member> SCALB =
			new Procedure3<Integer, Float128Member, Float128Member>()
	{
		@Override
		public void call(Integer scaleFactor, Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(a.num.multiply(TWO.pow(scaleFactor)));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setNegInf();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure3<Integer, Float128Member, Float128Member> scalb() {
		return SCALB;
	}

	private final Procedure2<Float128Member, Float128Member> ULP =
			new Procedure2<Float128Member, Float128Member>()
	{
		// For IEEE 64 power = 1024-53. Which is max exponent - bits in fraction.
		// These can also be found for the 128 bit format by inspection.
		
		private BigDecimal MAX_ULP = TWO.pow(16384-112);
		
		@Override
		public void call(Float128Member a, Float128Member ulp) {
			Float128Member top = G.QUAD.construct();
			Float128Member bottom = G.QUAD.construct();
			switch (a.classification) {
				case Float128Member.NORMAL:
					if (a.num.compareTo(Float128Member.MAX_NORMAL) == 0) {
						// succ() of a is not finite
						ulp.setV(MAX_ULP);    // see constant above
					}
					else {
						G.QUAD.assign().call(a, bottom);
						G.QUAD.succ().call(a, top);
						subtract().call(top, bottom, ulp);
					}
					break;
				case Float128Member.POSZERO:
					ulp.setV(Float128Member.MIN_SUBNORMAL);
					break;
				case Float128Member.NEGZERO:
					ulp.setV(Float128Member.MIN_SUBNORMAL);
					break;
				case Float128Member.POSINF:
					ulp.setPosInf();
					break;
				case Float128Member.NEGINF:
					ulp.setPosInf();
					break;
				case Float128Member.NAN:
					ulp.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
	
	@Override
	public Procedure2<Float128Member, Float128Member> ulp() {
		return ULP;
	}

	private final Procedure2<Float128Member, Float128Member> PRED =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
			case Float128Member.NORMAL:
				// this code is slow but it is very easy to understand and it eliminates
				// a bunch of confusing and probably buggy code below
				if (a.num.compareTo(Float128Member.MIN_NORMAL) == 0)
					b.setV(Float128Member.MAX_SUBNORMAL);
				else if (a.num.compareTo(Float128Member.MIN_SUBNORMAL) == 0)
					b.setPosZero();
				else if (a.num.compareTo(Float128Member.MAX_SUBNORMAL.negate()) == 0)
					b.setV(Float128Member.MIN_NORMAL.negate());
				else if (a.num.compareTo(Float128Member.MAX_NORMAL.negate()) == 0)
					b.setNegInf();
				else {
					// we do not have any tricky boundary conditions
					byte[] bytes = new byte[16];
					a.toByteArray(bytes, 0);
					if (G.QUAD.signum().call(a) > 0) {
						// number is positive
						// move towards zero
						// find a decrementable fraction byte
						int spot = -1;
						for (int i = 0; i <= 13; i++) {  // work from lsb to msb is correct here
							if (bytes[i] != 0) {
								spot = i;
								i = 14;
							}
						}
						// found a decrementable fraction byte
						if (spot != -1) {
							bytes[spot] = (byte) ((bytes[spot] & 0xff) - 1);
							for (int i = spot-1; i >= 0; i--) {
								bytes[i] = (byte) 0xff;
							}
							b.fromByteArray(bytes, 0);
						}
						else {
							// did not find one. find a decremental exponent byte
							if ((bytes[14] & 0xff) != 0) {
								bytes[14] = (byte) ((bytes[14] & 0xff) - 1);
								for (int i = 13; i >= 0; i--) {
									bytes[i] = (byte) 0xff;
								}
								b.fromByteArray(bytes, 0);
							}
							else if ((bytes[15] & 0x7f) != 0) {
								bytes[15] = (byte) ((bytes[15] & 0xff) - 1);
								bytes[14] = (byte) 0xff;
								for (int i = 13; i >= 0; i--) {
									bytes[i] = (byte) 0xff;
								}
								b.fromByteArray(bytes, 0);
							}
							else {
								// this implies that a was some zero and the classification rules that out
								throw new IllegalArgumentException("if we got here my code has a bug");
							}
						}
					}
					else {
						// number is negative
						// move away from zero
						// find an incrementable fraction byte
						int spot = -1;
						for (int i = 0; i <= 13; i++) {  // work from lsb to msb is correct here
							if (bytes[i] != 0xff) {
								spot = i;
								i = 14;
							}
						}
						// found a incrementable fraction byte
						if (spot != -1) {
							bytes[spot] = (byte) ((bytes[spot] & 0xff) + 1);
							for (int i = spot-1; i >= 0; i--) {
								bytes[i] = 0;
							}
							b.fromByteArray(bytes, 0);
						}
						else {
							// did not find one. find an incremental exponent byte
							if ((bytes[14] & 0xff) != 0xff) {
								bytes[14] = (byte) ((bytes[14] & 0xff) + 1);
								for (int i = 13; i >= 0; i--) {
									bytes[i] = 0;
								}
								b.fromByteArray(bytes, 0);
							}
							else if ((bytes[15] & 0x7f) != 0x7f) {
								bytes[15] = (byte) ((bytes[15] & 0xff) + 1);
								bytes[14] = 0;
								for (int i = 13; i >= 0; i--) {
									bytes[i] = 0;
								}
								b.fromByteArray(bytes, 0);
							}
							else {
								// this implies that a was some Infinity and the classification rules that out
								throw new IllegalArgumentException("if we got here my code has a bug");
							}
						}
					}
				}
				break;
			case Float128Member.POSZERO:
				b.setV(Float128Member.MIN_SUBNORMAL.negate());
				break;
			case Float128Member.NEGZERO:
				b.setV(Float128Member.MIN_SUBNORMAL.negate());
				break;
			case Float128Member.POSINF:
				b.setV(Float128Member.MAX_NORMAL);
				break;
			case Float128Member.NEGINF:
				b.setNegInf(); // trap here
				break;
			case Float128Member.NAN:
				b.setNan();
				break;
			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> pred() {
		return PRED;
	}

	private final Procedure2<Float128Member, Float128Member> SUCC =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
			case Float128Member.NORMAL:
				// this code is slow but it is very easy to understand and it eliminates
				// a bunch of confusing and probably buggy code below
				if (a.num.compareTo(Float128Member.MIN_NORMAL.negate()) == 0)
					b.setV(Float128Member.MAX_SUBNORMAL.negate());
				else if (a.num.compareTo(Float128Member.MIN_SUBNORMAL.negate()) == 0)
					b.setNegZero();
				else if (a.num.compareTo(Float128Member.MAX_SUBNORMAL) == 0)
					b.setV(Float128Member.MIN_NORMAL);
				else if (a.num.compareTo(Float128Member.MAX_NORMAL) == 0)
					b.setPosInf();
				else {
					// we do not have any tricky boundary conditions
					byte[] bytes = new byte[16];
					a.toByteArray(bytes, 0);
					if (G.QUAD.signum().call(a) > 0) {
						// number is positive
						// move away from zero
						// find an incrementable fraction byte
						int spot = -1;
						for (int i = 0; i <= 13; i++) {  // work from lsb to msb is correct here
							if (bytes[i] != 0xff) {
								spot = i;
								i = 14;
							}
						}
						// found a incrementable fraction byte
						if (spot != -1) {
							bytes[spot] = (byte) ((bytes[spot] & 0xff) + 1);
							for (int i = spot-1; i >= 0; i--) {
								bytes[i] = 0;
							}
							b.fromByteArray(bytes, 0);
						}
						else {
							// did not find one. find an incremental exponent byte
							if ((bytes[14] & 0xff) != 0xff) {
								bytes[14] = (byte) ((bytes[14] & 0xff) + 1);
								for (int i = 13; i >= 0; i--) {
									bytes[i] = 0;
								}
								b.fromByteArray(bytes, 0);
							}
							else if ((bytes[15] & 0x7f) != 0x7f) {
								bytes[15] = (byte) ((bytes[15] & 0xff) + 1);
								bytes[14] = 0;
								for (int i = 13; i >= 0; i--) {
									bytes[i] = 0;
								}
								b.fromByteArray(bytes, 0);
							}
							else {
								// this implies that a was some Infinity and the classification rules that out
								throw new IllegalArgumentException("if we got here my code has a bug");
							}
						}
					}
					else {
						// number is negative
						// move towards from zero
						// find a decrementable fraction byte
						int spot = -1;
						for (int i = 0; i <= 13; i++) {  // work from lsb to msb is correct here
							if (bytes[i] != 0) {
								spot = i;
								i = 14;
							}
						}
						// found a decrementable fraction byte
						if (spot != -1) {
							bytes[spot] = (byte) ((bytes[spot] & 0xff) - 1);
							for (int i = spot-1; i >= 0; i--) {
								bytes[i] = (byte) 0xff;
							}
							b.fromByteArray(bytes, 0);
						}
						else {
							// did not find one. find a decremental exponent byte
							if ((bytes[14] & 0xff) != 0) {
								bytes[14] = (byte) ((bytes[14] & 0xff) - 1);
								for (int i = 13; i >= 0; i--) {
									bytes[i] = (byte) 0xff;
								}
								b.fromByteArray(bytes, 0);
							}
							else if ((bytes[15] & 0x7f) != 0) {
								bytes[15] = (byte) ((bytes[15] & 0xff) - 1);
								bytes[14] = (byte) 0xff;
								for (int i = 13; i >= 0; i--) {
									bytes[i] = (byte) 0xff;
								}
								b.fromByteArray(bytes, 0);
							}
							else {
								// this implies that a was some zero and the classification rules that out
								throw new IllegalArgumentException("if we got here my code has a bug");
							}
						}
					}
				}
				break;
			case Float128Member.POSZERO:
				b.setV(Float128Member.MIN_SUBNORMAL);
				break;
			case Float128Member.NEGZERO:
				b.setV(Float128Member.MIN_SUBNORMAL);
				break;
			case Float128Member.POSINF:
				b.setPosInf(); // trap here
				break;
			case Float128Member.NEGINF:
				b.setV(Float128Member.MIN_NORMAL);
				break;
			case Float128Member.NAN:
				b.setNan();
				break;
			default:
				throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> succ() {
		return SUCC;
	}

	private final Procedure2<Float128Member, Float128Member> REAL =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> real() {
		return REAL;
	}

	private final Procedure2<Float128Member, Float128Member> UNREAL =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			b.setPosZero();
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> unreal() {
		return UNREAL;
	}

	private final Procedure1<Float128Member> RAND = 
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			byte[] bytes = new byte[16];
			bytes[15] = (byte) 0x3f;
			bytes[14] = (byte) 0xfe;
			for (int i = 13; i >= 0; i--) {
				bytes[i] = (byte) rng.nextInt(256);
			}
			a.fromByteArray(bytes, 0);
		}
	};

	@Override
	public Procedure1<Float128Member> random() {
		return RAND;
	}

	private final Procedure4<Round.Mode,Float128Member,Float128Member,Float128Member> ROUND =
			new Procedure4<Round.Mode, Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, Float128Member a, Float128Member b) {
			Round.compute(G.QUAD, mode, delta, a, b);
		}
	};
	
	@Override
	public Procedure4<Mode, Float128Member, Float128Member, Float128Member> round() {
		return ROUND;
	}

	// TODO
	//   I tested various cases of float64 code to glean correct behavior.
	//   Some of the results returned 0.0 but maybe they were really -0.0s.
	//   I should go over again by hand sometime and detect neg zeroes if
	//   possible.
	
	private final Procedure3<Float128Member, Float128Member, Float128Member> POW =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		/* 
		
		From the java api docs on how this behaves in math.pow():
		 
		Returns the value of the first argument raised to the power of the second argument.
		Special cases:
		1)
		If the second argument is positive or negative zero, then the result is 1.0.
		2)
		If the second argument is 1.0, then the result is the same as the first argument.
		3)
		If the second argument is NaN, then the result is NaN.
		4)
		If the first argument is NaN and the second argument is nonzero, then the result is NaN.
		5)
		If
		the absolute value of the first argument is greater than 1 and the second argument is positive infinity, or
		the absolute value of the first argument is less than 1 and the second argument is negative infinity,
		then the result is positive infinity.
		6)
		If
		the absolute value of the first argument is greater than 1 and the second argument is negative infinity, or
		the absolute value of the first argument is less than 1 and the second argument is positive infinity,
		then the result is positive zero.
		If the absolute value of the first argument equals 1 and the second argument is infinite, then the result is NaN.
		7)
		If
		the first argument is positive zero and the second argument is greater than zero, or
		the first argument is positive infinity and the second argument is less than zero,
		then the result is positive zero.
		8)
		If
		the first argument is positive zero and the second argument is less than zero, or
		the first argument is positive infinity and the second argument is greater than zero,
		then the result is positive infinity.
		9)
		If
		the first argument is negative zero and the second argument is greater than zero but not a finite odd integer, or
		the first argument is negative infinity and the second argument is less than zero but not a finite odd integer,
		then the result is positive zero.
		10)
		If
		the first argument is negative zero and the second argument is a positive finite odd integer, or
		the first argument is negative infinity and the second argument is a negative finite odd integer,
		then the result is negative zero.
		11)
		If
		the first argument is negative zero and the second argument is less than zero but not a finite odd integer, or
		the first argument is negative infinity and the second argument is greater than zero but not a finite odd integer,
		then the result is positive infinity.
		12)
		If
		the first argument is negative zero and the second argument is a negative finite odd integer, or
		the first argument is negative infinity and the second argument is a positive finite odd integer,
		then the result is negative infinity.
		13)
		If the first argument is finite and less than zero
		if the second argument is a finite even integer, the result is equal to the result of raising the absolute value of the first argument to the power of the second argument
		if the second argument is a finite odd integer, the result is equal to the negative of the result of raising the absolute value of the first argument to the power of the second argument
		if the second argument is finite and not an integer, then the result is NaN.
		If both arguments are integers, then the result is exactly equal to the mathematical result of raising the first argument to the power of the second argument if that result can in fact be represented exactly as a double value.
		(In the foregoing descriptions, a floating-point value is considered to be an integer if and only if it is finite and a fixed point of the method ceil or, equivalently, a fixed point of the method floor. A value is a fixed point of a one-argument method if and only if the result of applying the method to the value is equal to the value.)

		*/

		@Override
		public void call(Float128Member a, Float128Member b, Float128Member c) {
			
			// 1) If the second argument is positive or negative zero, then the result is 1.0.
			
			if (b.isZero()) {
				c.setV(BigDecimal.ONE);
				return;
			}
			
			// 2) If the second argument is 1.0, then the result is the same as the first argument.
			
			if (b.num.compareTo(BigDecimal.ONE) == 0) {
				c.set(a);
				return;
			}
			
			// 3) If the second argument is NaN, then the result is NaN.
			
			if (b.isNan()) {
				c.setNan();
				return;
			}
			
			// 4) If the first argument is NaN and the second argument is nonzero, then the result is NaN.
			
			if (a.isNan()) {
				c.setNan();
				return;
			}
			
			// 5)
			// If
			// the absolute value of the first argument is greater than 1 and the second argument is positive infinity, or
			// the absolute value of the first argument is less than 1 and the second argument is negative infinity,
			// then the result is positive infinity.
			
			if ((a.isPosInf() || a.num.abs().compareTo(BigDecimal.ONE) > 0) && b.isPosInf()) {
				c.setPosInf();
				return;
			}
			
			if ((a.isNegInf() || a.num.abs().compareTo(BigDecimal.ONE) < 0) && b.isNegInf()) {
				c.setPosInf();
				return;
			}

			// 6) If the absolute value of the first argument equals 1 and the second argument is infinite, then the result is NaN.

			if ((a.isNormal() && a.num.abs().compareTo(BigDecimal.ONE) == 0) && b.isInfinite()) {
				c.setNan();
				return;
			}

			// 7)
			// If
			// the first argument is positive zero and the second argument is greater than zero, or
			// the first argument is positive infinity and the second argument is less than zero,
			// then the result is positive zero.

			if (a.isPosZero() && (b.num.compareTo(BigDecimal.ZERO) > 0)) {
				c.setPosZero();
				return;
			}

			if (a.isPosInf() && (b.num.compareTo(BigDecimal.ZERO) < 0)) {
				c.setPosZero();
				return;
			}
			
			// 8)
			// If
			// the first argument is positive zero and the second argument is less than zero, or
			// the first argument is positive infinity and the second argument is greater than zero,
			// then the result is positive infinity.

			if (a.isPosZero() && (b.num.compareTo(BigDecimal.ZERO) < 0)) {
				c.setPosInf();
				return;
			}

			if (a.isPosInf() && (b.num.compareTo(BigDecimal.ZERO) > 0)) {
				c.setPosInf();
				return;
			}
			
			// 9)
			// If
			// the first argument is negative zero and the second argument is greater than zero but not a finite odd integer, or
			// the first argument is negative infinity and the second argument is less than zero but not a finite odd integer,
			// then the result is positive zero.
			
			BigInteger intVersion = a.num.toBigInteger();
			BigDecimal intAsBigDecimal = new BigDecimal(intVersion);
			
			int NOT_FINITE_INTEGER = -1;
			int FINITE_ODD_INTEGER = 1;
			int FINITE_EVEN_INTEGER = 0;
			
			int bDescription = NOT_FINITE_INTEGER;
			if (b.isFinite()) {
				// is integer
				if (a.num.compareTo(intAsBigDecimal) == 0) {
					BigInteger num = intVersion.abs();
					if (num.remainder(BigInteger.valueOf(2)).compareTo(BigInteger.ONE) == 0) {
						bDescription = FINITE_ODD_INTEGER;
					}
					else {
						bDescription = FINITE_EVEN_INTEGER;
					}
				}
			}
			
			if (a.isNegZero() && (b.num.compareTo(BigDecimal.ZERO) > 0) && bDescription != FINITE_ODD_INTEGER) {
				c.setPosZero();
				return;
			}
			
			if (a.isNegInf() && (b.num.compareTo(BigDecimal.ZERO) < 0) && bDescription != FINITE_ODD_INTEGER) {
				c.setPosZero();
				return;
			}

			// 10)
			// If
			// the first argument is negative zero and the second argument is a positive finite odd integer, or
			// the first argument is negative infinity and the second argument is a negative finite odd integer,
			// then the result is negative zero.
			
			if (a.isNegZero() && bDescription == FINITE_ODD_INTEGER) {
				c.setNegZero();
				return;
			}
			
			if (a.isNegInf() && bDescription == FINITE_ODD_INTEGER) {
				c.setNegZero();
				return;
			}

			// 11)
			// If
			// the first argument is negative zero and the second argument is less than zero but not a finite odd integer, or
			// the first argument is negative infinity and the second argument is greater than zero but not a finite odd integer,
			// then the result is positive infinity.

			if (a.isNegZero() && (b.num.compareTo(BigDecimal.ZERO) < 0) && bDescription != FINITE_ODD_INTEGER) {
				c.setPosInf();
				return;
			}
			
			if (a.isNegInf() && (b.num.compareTo(BigDecimal.ZERO) > 0) && bDescription != FINITE_ODD_INTEGER) {
				c.setPosInf();
				return;
			}

			// 12)
			// If
			// the first argument is negative zero and the second argument is a negative finite odd integer, or
			// the first argument is negative infinity and the second argument is a positive finite odd integer,
			// then the result is negative infinity.

			if (a.isNegZero() && (b.num.compareTo(BigDecimal.ZERO) < 0) && bDescription == FINITE_ODD_INTEGER) {
				c.setNegInf();
				return;
			}
			
			if (a.isNegInf() && (b.num.compareTo(BigDecimal.ZERO) > 0) && bDescription == FINITE_ODD_INTEGER) {
				c.setNegInf();
				return;
			}

			// 13)
			// If the first argument is finite and less than zero
			// if the second argument is finite and not an integer, then the result is NaN.
			// if the second argument is a finite even integer, the result is equal to the result of raising the absolute value of the first argument to the power of the second argument
			// if the second argument is a finite odd integer, the result is equal to the negative of the result of raising the absolute value of the first argument to the power of the second argument
			// If both arguments are integers, then the result is exactly equal to the mathematical result of raising the first argument to the power of the second argument if that result can in fact be represented exactly as a double value.
			
			// If the first argument is finite and less than zero

			if (a.isFinite() && a.num.compareTo(BigDecimal.ZERO) < 0) {
				
				// if the second argument is finite and not an integer, then the result is NaN.

				if (b.isFinite() && bDescription == NOT_FINITE_INTEGER) {
					c.setNan();
				}
				return;
				
				// the other three cases should be handled fine by the code falling through to below
			}
			
			// one case I found was falling through
			
			if (a.isNormal() && b.isInfinite()) {
				if (a.num.compareTo(BigDecimal.ONE.negate()) < 0) {
					if (b.isPosInf())
						c.setPosInf();
					else // b is neg inf
						c.setPosZero();
				}
				else if (a.num.compareTo(BigDecimal.ONE.negate()) == 0) {
					if (b.isPosInf())
						c.setNan();
					else // b is neg inf
						c.setNan();
				}
				else if (a.num.compareTo(BigDecimal.ZERO) < 0) {
					if (b.isPosInf())
						c.setPosZero();
					else // b is neg inf
						c.setPosInf();
				}
				else if (a.num.compareTo(BigDecimal.ONE) < 0) {
					if (b.isPosInf())
						c.setPosZero();
					else // b is neg inf
						c.setPosInf();
				}
				else if (a.num.compareTo(BigDecimal.ONE) == 0) {
					if (b.isPosInf())
						c.setNan(); // makes no sense but that is what float64 does
					else // b is neg inf
						c.setNan(); // makes no sense but that is what float64 does
				}
				else { // a > 1
					if (b.isPosInf())
						c.setPosInf();
					else // b is neg inf
						c.setPosZero();
				}
				return;
			}
			
			// one case I found was falling through
			
			if (a.isZero() && b.isInfinite()) {
				if (b.isPosInf())
					c.setPosZero();
				else // b is neg inf
					c.setPosInf();
				return;
			}

			// one case I found was falling through
			
			if (a.isNegInf() && b.isPosInf()) {
				c.setPosInf();
				return;
			}
			
			if (a.isInfinite() || b.isInfinite()) {
				throw new IllegalArgumentException("infinity fell through in pow() for one of the arguments");
			}

			// Tested exhaustively: at this point number is zero or finite/normal
			// This allows us to use a.num and b.num since for zero types their
			// value is zero. This is a convenient shortcut but must not be abused.

			c.setV(BigDecimalMath.pow(a.num, b.num, CONTEXT));
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> pow() {
		return POW;
	}

	private final Procedure2<Float128Member, Float128Member> SQRT =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.sqrt(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setNegInf();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
	
	@Override
	public Procedure2<Float128Member, Float128Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<Float128Member, Float128Member> CBRT =
			new Procedure2<Float128Member, Float128Member>()
	{
		private final BigDecimal ONE_THIRD = BigDecimal.ONE.divide(BigDecimal.valueOf(3), CONTEXT);
		
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.pow(a.num, ONE_THIRD, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setNegInf();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, Float128Member> ISNAN =
			new Function1<Boolean, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a) {
			return a.classification == Float128Member.NAN;
		}
	};
	
	@Override
	public Function1<Boolean, Float128Member> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Float128Member> NAN =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setNan();
		}
	};
	
	@Override
	public Procedure1<Float128Member> nan() {
		return NAN;
	}

	private final Procedure1<Float128Member> NEGINF =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setNegInf();
		}
	};
			
	@Override
	public Procedure1<Float128Member> negInfinite() {
		return NEGINF;
	}

	private final Function1<Boolean, Float128Member> ISINF =
			new Function1<Boolean, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a) {
			return a.classification == Float128Member.POSINF || a.classification == Float128Member.NEGINF;
		}
	};

	@Override
	public Function1<Boolean, Float128Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float128Member> INF =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setPosInf();
		}
	};
			
	@Override
	public Procedure1<Float128Member> infinite() {
		return INF;
	}

	private final Procedure2<Float128Member, Float128Member> ASINH =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.asinh(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setV(BigDecimalMath.asinh(a.num, CONTEXT));
					break;
				case Float128Member.NEGZERO:
					b.setV(BigDecimalMath.asinh(a.num, CONTEXT));
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setNegInf();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> asinh() {
		return ASINH;
	}

	private final Procedure2<Float128Member, Float128Member> ACOSH =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					if (a.num.abs().compareTo(BigDecimal.ONE) < 0)
						b.setNan();
					else
						b.setV(BigDecimalMath.acosh(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setNan();
					break;
				case Float128Member.NEGZERO:
					b.setNan();
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setNan();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> acosh() {
		return ACOSH;
	}

	private final Procedure2<Float128Member, Float128Member> ATANH =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					if (a.num.abs().compareTo(BigDecimal.ONE) > 0)
						b.setNan();
					else if (a.num.compareTo(BigDecimal.ONE) == 0)
						b.setPosInf();
					else if (a.num.compareTo(BigDecimal.ONE.negate()) == 0)
						b.setNegInf();
					else
						b.setV(BigDecimalMath.atanh(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setV(BigDecimalMath.atanh(a.num, CONTEXT));
					break;
				case Float128Member.NEGZERO:
					b.setV(BigDecimalMath.atanh(a.num, CONTEXT));
					break;
				case Float128Member.POSINF:
					b.setNan();
					break;
				case Float128Member.NEGINF:
					b.setNan();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> atanh() {
		return ATANH;
	}

	private final Procedure2<Float128Member, Float128Member> SINH =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.sinh(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setNegInf();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> sinh() {
		return SINH;
	}

	private final Procedure2<Float128Member, Float128Member> COSH =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.cosh(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setV(BigDecimal.ONE);
					break;
				case Float128Member.NEGZERO:
					b.setV(BigDecimal.ONE);
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setPosInf();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> cosh() {
		return COSH;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> SINHANDCOSH =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member s, Float128Member c) {
			sinh().call(a, s);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<Float128Member, Float128Member> TANH =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.tanh(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setV(BigDecimal.ONE);
					break;
				case Float128Member.NEGINF:
					b.setV(BigDecimal.ONE.negate());
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> tanh() {
		return TANH;
	}

	private final Procedure2<Float128Member,Float128Member> SINCH =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			Sinch.compute(G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> sinch() {
		return SINCH;
	}

	private final Procedure2<Float128Member,Float128Member> SINCHPI =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			Sinchpi.compute(G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<Float128Member, Float128Member> ASIN =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					if (a.num.abs().compareTo(BigDecimal.ONE) > 0)
						b.setNan();
					else
						b.setV(BigDecimalMath.asin(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setNan();
					break;
				case Float128Member.NEGINF:
					b.setNan();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> asin() {
		return ASIN;
	}

	private final Procedure2<Float128Member, Float128Member> ACOS =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					if (a.num.abs().compareTo(BigDecimal.ONE) > 0)
						b.setNan();
					else
						b.setV(BigDecimalMath.acos(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setV(BigDecimalMath.acos(a.num, CONTEXT));
					break;
				case Float128Member.NEGZERO:
					b.setV(BigDecimalMath.acos(a.num, CONTEXT));
					break;
				case Float128Member.POSINF:
					b.setNan();
					break;
				case Float128Member.NEGINF:
					b.setNan();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> acos() {
		return ACOS;
	}

	private final Procedure2<Float128Member, Float128Member> ATAN =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.atan(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setV(PI_OVER_TWO);
					break;
				case Float128Member.NEGINF:
					b.setV(MINUS_PI_OVER_TWO);
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> atan() {
		return ATAN;
	}

	private final Procedure2<Float128Member, Float128Member> SIN =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.sin(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setNan();
					break;
				case Float128Member.NEGINF:
					b.setNan();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> sin() {
		return SIN;
	}

	private final Procedure2<Float128Member, Float128Member> COS =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.cos(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setV(BigDecimal.ONE);
					break;
				case Float128Member.NEGZERO:
					b.setV(BigDecimal.ONE);
					break;
				case Float128Member.POSINF:
					b.setNan();
					break;
				case Float128Member.NEGINF:
					b.setNan();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> cos() {
		return COS;
	}
	
	private final Procedure2<Float128Member, Float128Member> TAN =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.tan(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setPosZero();
					break;
				case Float128Member.NEGZERO:
					b.setNegZero();
					break;
				case Float128Member.POSINF:
					b.setNan();
					break;
				case Float128Member.NEGINF:
					b.setNan();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> tan() {
		return TAN;
	}

	private final Procedure3<Float128Member, Float128Member, Float128Member> SINANDCOS =
			new Procedure3<Float128Member, Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member s, Float128Member c) {
			sin().call(a, s);
			cos().call(a, c);
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<Float128Member,Float128Member> SINC =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			Sinc.compute(G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> sinc() {
		return SINC;
	}

	private final Procedure2<Float128Member,Float128Member> SINCPI =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			Sincpi.compute(G.QUAD, a, b);
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<Float128Member, Float128Member> EXP =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					b.setV(BigDecimalMath.exp(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setV(BigDecimal.ONE);
					break;
				case Float128Member.NEGZERO:
					b.setV(BigDecimal.ONE);
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setPosZero();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> exp() {
		return EXP;
	}

	private final Procedure2<Float128Member, Float128Member> LOG =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			switch (a.classification) {
				case Float128Member.NORMAL:
					if (a.num.signum() < 0)
						b.setNan();
					else
						b.setV(BigDecimalMath.log(a.num, CONTEXT));
					break;
				case Float128Member.POSZERO:
					b.setNegInf();
					break;
				case Float128Member.NEGZERO:
					b.setNegInf();
					break;
				case Float128Member.POSINF:
					b.setPosInf();
					break;
				case Float128Member.NEGINF:
					b.setNan();
					break;
				case Float128Member.NAN:
					b.setNan();
					break;
				default:
					throw new IllegalArgumentException("unknown classification error "+a.classification);
			}
		}
	};

	@Override
	public Procedure2<Float128Member, Float128Member> log() {
		return LOG;
	}

	private final Procedure1<Float128Member> PI =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setV(_PI);
		}
	};

	@Override
	public Procedure1<Float128Member> PI() {
		return PI;
	}

	private final Procedure1<Float128Member> E =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setV(_E);
		}
	};

	@Override
	public Procedure1<Float128Member> E() {
		return E;
	}

	private final Procedure1<Float128Member> PHI =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setV(_PHI);
		}
	};

	@Override
	public Procedure1<Float128Member> PHI() {
		return PHI;
	}

	private final Procedure1<Float128Member> GAMMA =
			new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setV(_GAMMA);
		}
	};

	@Override
	public Procedure1<Float128Member> GAMMA() {
		return GAMMA;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> norm() {
		return ABS;
	}

	private final Procedure1<Float128Member> MAXB =
		new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setV(Float128Member.MAX_NORMAL);
		}
	};
			
	@Override
	public Procedure1<Float128Member> maxBound() {
		return MAXB;
	}

	private final Procedure1<Float128Member> MINB =
		new Procedure1<Float128Member>()
	{
		@Override
		public void call(Float128Member a) {
			a.setV(Float128Member.MIN_NORMAL);
		}
	};
			
	@Override
	public Procedure1<Float128Member> minBound() {
		return MINB;
	}
}
