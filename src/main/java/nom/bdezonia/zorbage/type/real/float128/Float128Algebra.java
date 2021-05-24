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
import java.math.MathContext;

import nom.bdezonia.zorbage.algebra.Bounded;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.Exponential;
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

// TODO remember that I am tracking neg and pos zero separately.
// So i should catch every calc that results in a bigd zero
// and I should transform to neg/pos zero. This is slow but
// maybe the best way to correctly handle zeroes.

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
			
			if ((a.classification == 1 ||  a.classification == -1) &&
					(b.classification == 1 || b.classification == -1)) {
				return true;
			}
			
			// nans never equal anything
			
			if (a.classification == 3 || b.classification == 3) {
				return false;
			}
			
			// if classifications are different amongst remaining choices they can't be equal
			
			if (a.classification != b.classification) {
				return false;
			}
			
			// if here classifications are the same and neither is nan

			// two regular numbers
			
			if (a.classification == 0) {
				return a.num.compareTo(b.num) == 0;
			}
			
			// neg and pos zeroes
			
			if (a.classification == 1 || a.classification == -1) {
				return b.classification == 1 || b.classification == -1;
			}
			
			// pos inf
			
			if (a.classification == 2) {
				return true;
			}
			
			// neg inf
			if (a.classification == -2) {
				return true;
			}
			
			throw new IllegalArgumentException("unknown classifications error");
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
			return a.classification == 1 || a.classification == -1;
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
			if (a.classification == 0)
				b.num = a.num.negate();
			else if (a.classification == 1)
				b.setNegZero();
			else if (a.classification == -1)
				b.setPosZero();
			else if (a.classification == 2)
				b.setNegInf();
			else if (a.classification == -2)
				b.setPosInf();
			else if (a.classification == 3)
				b.setNan();
			else
				throw new IllegalArgumentException("unknown classification error");
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
			
			if (a.classification == 0) {
				
				if (b.classification == 0) {
					BigDecimal tmp = a.num.add(b.num);
					if (tmp.compareTo(BigDecimal.ZERO) == 0)
						c.setPosZero();
					else
						c.setV(tmp);
				}
				else if (b.classification == 1) {
					c.set(a);
				}
				else if (b.classification == -1) {
					c.set(a);
				}
				else if (b.classification == 2) {
					c.setPosInf();
				}
				else if (b.classification == -2) {
					c.setNegInf();
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == 1) {
				
				if (b.classification == 0) {
					c.set(b);
				}
				else if (b.classification == 1) {
					c.setPosZero();
				}
				else if (b.classification == -1) {
					c.setPosZero(); // tested; this is correct
				}
				else if (b.classification == 2) {
					c.setPosInf();
				}
				else if (b.classification == -2) {
					c.setNegInf();
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == -1) {
				
				if (b.classification == 0) {
					c.set(b);
				}
				else if (b.classification == 1) {
					c.setPosZero();
				}
				else if (b.classification == -1) {
					c.setNegZero(); // tested; this is correct
				}
				else if (b.classification == 2) {
					c.setPosInf();
				}
				else if (b.classification == -2) {
					c.setNegInf();
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == 2) {
				
				if (b.classification == 0) {
					c.setPosInf();
				}
				else if (b.classification == 1) {
					c.setPosInf();
				}
				else if (b.classification == -1) {
					c.setPosInf();
				}
				else if (b.classification == 2) {
					c.setPosInf();
				}
				else if (b.classification == -2) {
					c.setNan(); // tested; this is correct
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == -2) {
				
				if (b.classification == 0) {
					c.setNegInf();
				}
				else if (b.classification == 1) {
					c.setNegInf();
				}
				else if (b.classification == -1) {
					c.setNegInf();
				}
				else if (b.classification == 2) {
					c.setNan(); // tested; this is correct
				}
				else if (b.classification == -2) {
					c.setNegInf();
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == 3) {
				c.setNan();
			}
			else
				throw new IllegalArgumentException("unknown classification error");
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
			
			if (a.classification == 0) {
				
				if (b.classification == 0) {
					BigDecimal tmp = a.num.subtract(b.num);
					if (tmp.compareTo(BigDecimal.ZERO) == 0)
						c.setPosZero();
					else
						c.setV(tmp);
				}
				else if (b.classification == 1) {
					c.set(a);
				}
				else if (b.classification == -1) {
					c.set(a);
				}
				else if (b.classification == 2) {
					c.setNegInf();
				}
				else if (b.classification == -2) {
					c.setPosInf();
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == 1) {
				
				if (b.classification == 0) {
					c.setV(b.v().negate());
				}
				else if (b.classification == 1) {
					c.setPosZero();
				}
				else if (b.classification == -1) {
					c.setPosZero(); // TODO test
				}
				else if (b.classification == 2) {
					c.setNegInf();
				}
				else if (b.classification == -2) {
					c.setPosInf();
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == -1) {
				
				if (b.classification == 0) {
					c.setV(b.v().negate());
				}
				else if (b.classification == 1) {
					c.setPosZero(); // test
				}
				else if (b.classification == -1) {
					c.setPosZero(); // test
				}
				else if (b.classification == 2) {
					c.setNegInf();
				}
				else if (b.classification == -2) {
					c.setPosInf();
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == 2) {
				
				if (b.classification == 0) {
					c.setPosInf();
				}
				else if (b.classification == 1) {
					c.setPosInf();
				}
				else if (b.classification == -1) {
					c.setPosInf();
				}
				else if (b.classification == 2) {
					c.setNan(); // test
				}
				else if (b.classification == -2) {
					c.setPosInf(); // test
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == -2) {
				
				if (b.classification == 0) {
					c.setNegInf();
				}
				else if (b.classification == 1) {
					c.setNegInf();
				}
				else if (b.classification == -1) {
					c.setNegInf();
				}
				else if (b.classification == 2) {
					c.setNan(); // test
				}
				else if (b.classification == -2) {
					c.setNegInf();
				}
				else if (b.classification == 3) {
					c.setNan();
				}
				else
					throw new IllegalArgumentException("unknown classification error");
			}
			else if (a.classification == 3) {
				c.setNan();
			}
			else
				throw new IllegalArgumentException("unknown classification error");
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
			
			// Make sure that this routine uses c.setV() which will deal with zeroes
			//   correctly and will clamp as needed.
			
			throw new IllegalArgumentException("implement me");
		}
	};

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> multiply() {
		return MUL;
	}

	@Override
	public Procedure3<Integer, Float128Member, Float128Member> power() {
		// TODO Auto-generated method stub
		return null;
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
			return a.classification == 0 && a.num.compareTo(BigDecimal.ONE) == 0;
		}
	};
			
	@Override
	public Function1<Boolean, Float128Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> invert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> divide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function2<Boolean, Float128Member, Float128Member> isLess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function2<Boolean, Float128Member, Float128Member> isLessEqual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function2<Boolean, Float128Member, Float128Member> isGreater() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function2<Boolean, Float128Member, Float128Member> isGreaterEqual() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function2<Integer, Float128Member, Float128Member> compare() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function1<Integer, Float128Member> signum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> min() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> max() {
		// TODO Auto-generated method stub
		return null;
	}

	private final Procedure2<Float128Member, Float128Member> ABS =
			new Procedure2<Float128Member, Float128Member>()
	{
		@Override
		public void call(Float128Member a, Float128Member b) {
			if (a.classification == 0) {
				b.setV(a.v().abs());
			}
			else if (a.classification == 1) {
				b.setPosZero();
			}
			else if (a.classification == -1) {
				b.setPosZero();
			}
			else if (a.classification == 2) {
				b.setPosInf();
			}
			else if (a.classification == -2) {
				b.setPosInf();
			}
			else if (a.classification == 3) {
				b.setNan();
			}
		}
	};
			
	@Override
	public Procedure2<Float128Member, Float128Member> abs() {
		return ABS;
	}

	@Override
	public Procedure3<Integer, Float128Member, Float128Member> scaleByTwo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Integer, Float128Member, Float128Member> scaleByOneHalf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function3<Boolean, Float128Member, Float128Member, Float128Member> within() {
		// TODO Auto-generated method stub
		return null;
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
				double sign = Math.signum(s);
				if (sign < 0)
					scale.setNegInf();
				else
					scale.setPosInf();
			} else if (s.isNaN()) {
				scale.setNan();
			} else {
				// a is finite
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

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> div() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> mod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure4<Float128Member, Float128Member, Float128Member, Float128Member> divMod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> copySign() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Function1<Integer, Float128Member> getExponent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Integer, Float128Member, Float128Member> scalb() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> ulp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> pred() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> succ() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public Procedure1<Float128Member> random() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure4<Mode, Float128Member, Float128Member, Float128Member> round() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> pow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> sqrt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> cbrt() {
		// TODO Auto-generated method stub
		return null;
	}

	private final Function1<Boolean, Float128Member> ISNAN =
			new Function1<Boolean, Float128Member>()
	{
		@Override
		public Boolean call(Float128Member a) {
			return a.classification == 3;
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
			return a.classification == 2 || a.classification == 3;
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

	@Override
	public Procedure2<Float128Member, Float128Member> asinh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> acosh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> atanh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> sinh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> cosh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> sinhAndCosh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> tanh() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> sinch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> sinchpi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> asin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> acos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> atan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> sin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> cos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> tan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure3<Float128Member, Float128Member, Float128Member> sinAndCos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> sinc() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> sincpi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> exp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Procedure2<Float128Member, Float128Member> log() {
		// TODO Auto-generated method stub
		return null;
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
