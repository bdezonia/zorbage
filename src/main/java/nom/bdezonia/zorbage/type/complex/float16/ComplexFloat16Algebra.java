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

// Note: some code here adapted from Imglib2 code written by Barry DeZonia
//
// * ImgLib2: a general-purpose, multidimensional image processing library.
// * Copyright (C) 2009 - 2013 Stephan Preibisch, Tobias Pietzsch, Barry DeZonia,
// * Stephan Saalfeld, Albert Cardona, Curtis Rueden, Christian Dietz, Jean-Yves
// * Tinevez, Johannes Schindelin, Lee Kamentsky, Larry Lindsey, Grant Harris,
// * Mark Hiner, Aivar Grislis, Martin Horn, Nick Perry, Michael Zinsmaier,
// * Steffen Jaensch, Jan Funke, Mark Longair, and Dimiter Prodanov.

package nom.bdezonia.zorbage.type.complex.float16;

import java.lang.Integer;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.ComplexNumberWithin;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat16Algebra
	implements
		Field<ComplexFloat16Algebra, ComplexFloat16Member>,
		Norm<ComplexFloat16Member, Float16Member>,
		RealConstants<ComplexFloat16Member>,
		ImaginaryConstants<ComplexFloat16Member>,
		Exponential<ComplexFloat16Member>,
		Trigonometric<ComplexFloat16Member>,
		InverseTrigonometric<ComplexFloat16Member>,
		Hyperbolic<ComplexFloat16Member>,
		InverseHyperbolic<ComplexFloat16Member>,
		Roots<ComplexFloat16Member>,
		Power<ComplexFloat16Member>,
		Rounding<Float16Member,ComplexFloat16Member>,
		Infinite<ComplexFloat16Member>,
		NaN<ComplexFloat16Member>,
		Conjugate<ComplexFloat16Member>,
		Random<ComplexFloat16Member>,
		RealUnreal<ComplexFloat16Member,Float16Member>,
		Scale<ComplexFloat16Member,ComplexFloat16Member>,
		ScaleByHighPrec<ComplexFloat16Member>,
		ScaleByRational<ComplexFloat16Member>,
		ScaleByDouble<ComplexFloat16Member>,
		ScaleComponents<ComplexFloat16Member, Float16Member>,
		Tolerance<Float16Member,ComplexFloat16Member>,
		ScaleByOneHalf<ComplexFloat16Member>,
		ScaleByTwo<ComplexFloat16Member>,
		ConstructibleFromFloats<ComplexFloat16Member>
{
	private static final ComplexFloat16Member ONE = new ComplexFloat16Member(1,0);
	private static final ComplexFloat16Member TWO = new ComplexFloat16Member(2,0);
	private static final ComplexFloat16Member MINUS_ONE = new ComplexFloat16Member(-1,0);
	private static final ComplexFloat16Member PI = new ComplexFloat16Member((float)Math.PI,0);
	private static final ComplexFloat16Member E = new ComplexFloat16Member((float)Math.E,0);
	private static final ComplexFloat16Member GAMMA = new ComplexFloat16Member((float)0.57721566490153286060,0);
	private static final ComplexFloat16Member PHI = new ComplexFloat16Member((float)1.61803398874989484820,0);
	private static final ComplexFloat16Member ONE_HALF = new ComplexFloat16Member((float)0.5,0);
	private static final ComplexFloat16Member ONE_THIRD = new ComplexFloat16Member((float)1.0/3,0);
	private static final ComplexFloat16Member I = new ComplexFloat16Member(0,1);
	private static final ComplexFloat16Member I_OVER_TWO = new ComplexFloat16Member(0,(float)0.5);
	private static final ComplexFloat16Member TWO_I = new ComplexFloat16Member(0,2);
	private static final ComplexFloat16Member MINUS_I = new ComplexFloat16Member(0,-1);
	private static final ComplexFloat16Member MINUS_I_OVER_TWO = new ComplexFloat16Member(0,(float)-0.5);
	private static final ComplexFloat16Member NaN_ = new ComplexFloat16Member(Float.NaN,Float.NaN);

	@Override
	public String typeDescription() {
		return "16-bit based complex number";
	}
	
	public ComplexFloat16Algebra() { }

	@Override
	public ComplexFloat16Member construct() {
		return new ComplexFloat16Member();
	}

	@Override
	public ComplexFloat16Member construct(ComplexFloat16Member other) {
		return new ComplexFloat16Member(other);
	}

	@Override
	public ComplexFloat16Member construct(String s) {
		return new ComplexFloat16Member(s);
	}

	@Override
	public ComplexFloat16Member construct(float... vals) {
		return new ComplexFloat16Member(vals);
	}
	
	private final Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> MUL =
			new Procedure3<ComplexFloat16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b, ComplexFloat16Member c) {
			double max = Math.max( Math.max(Math.abs(a.r()), Math.abs(a.i())) , Math.max(Math.abs(b.r()), Math.abs(b.i())) );
			if (max == 0) {
				zero().call(c);
			}
			else {
				double ar = a.r() / max;
				double ai = a.i() / max;
				double br = b.r() / max;
				double bi = b.i() / max;
				double r = (ar*br) - (ai*bi);
				double i = (ai*br) + (ar*bi);
				c.setR( (float) (r * max * max) );
				c.setI( (float) (i * max * max) );
			}
		}
	};

	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat16Member,ComplexFloat16Member> POWER =
			new Procedure3<Integer, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(Integer power, ComplexFloat16Member a, ComplexFloat16Member b) {
			if (power == 0 && isZero().call(a)) {
				assign().call(NaN_, b);
				return;
			}
			double rToTheN = Math.pow(Math.hypot(a.r(), a.i()), power);
			double nTheta = power * getArgument(a);
			b.setR((float) (rToTheN * Math.cos(nTheta)));
			b.setI((float) (rToTheN * Math.sin(nTheta)));
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat16Member,ComplexFloat16Member> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat16Member> ZER =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<ComplexFloat16Member> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> NEG =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			b.setR( -a.r() );
			b.setI( -a.i() );
		}
	};

	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> ADD =
			new Procedure3<ComplexFloat16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b, ComplexFloat16Member c) {
			c.setR( a.r() + b.r() );
			c.setI( a.i() + b.i() );
		}
	};

	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> SUB =
			new Procedure3<ComplexFloat16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b, ComplexFloat16Member c) {
			c.setR( a.r() - b.r() );
			c.setI( a.i() - b.i() );
		}
	};

	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat16Member,ComplexFloat16Member> EQ =
			new Function2<Boolean, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public Boolean call(ComplexFloat16Member a, ComplexFloat16Member b) {
			return a.r() == b.r() && a.i() == b.i();
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat16Member,ComplexFloat16Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat16Member,ComplexFloat16Member> NEQ =
			new Function2<Boolean, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public Boolean call(ComplexFloat16Member a, ComplexFloat16Member b) {
			return a.r() != b.r() || a.i() != b.i();
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat16Member,ComplexFloat16Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ASSIGN =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member from, ComplexFloat16Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat16Member> UNITY =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16Member> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> INV =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			divide().call(ONE, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> DIVIDE =
			new Procedure3<ComplexFloat16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b, ComplexFloat16Member c) {
			double max = Math.max( Math.max(Math.abs(a.r()), Math.abs(a.i())) , Math.max(Math.abs(b.r()), Math.abs(b.i())) );
			if (max == 0) {
				nan().call(c);
			}
			else {
				// for safety must use tmps
				double ar = a.r() / max;
				double ai = a.i() / max;
				double br = b.r() / max;
				double bi = b.i() / max;
				double mod2 = (br*br) + (bi*bi);
				double r = (ar*br) + (ai*bi);
				double i = (ai*br) - (ar*bi);
				c.setR( (float) (r / mod2) );
				c.setI( (float) (i / mod2) );
			}
		}
	};

	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> divide() {
		return DIVIDE;
	}
	
	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> CONJ =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			b.setR( a.r() );
			b.setI( -a.i() );
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat16Member,Float16Member> NORM =
			new Procedure2<ComplexFloat16Member, Float16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, Float16Member b) {
			b.setV( (float) Math.hypot(a.r(),a.i()) );
		}
	};

	@Override
	public Procedure2<ComplexFloat16Member,Float16Member> norm() {
		return NORM;
	}

	private final Procedure1<ComplexFloat16Member> PI_ =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			assign().call(PI, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16Member> PI() {
		return PI_;
	}

	private final Procedure1<ComplexFloat16Member> E_ =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			assign().call(E, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16Member> E() {
		return E_;
	}


	private final Procedure1<ComplexFloat16Member> GAMMA_ =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			assign().call(GAMMA, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<ComplexFloat16Member> PHI_ =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			assign().call(PHI, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16Member> PHI() {
		return PHI_;
	}

	private final Procedure1<ComplexFloat16Member> I_ =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			assign().call(I, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat16Member> I() {
		return I_;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ASIN =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member ia = new ComplexFloat16Member();
			ComplexFloat16Member aSquared = new ComplexFloat16Member();
			ComplexFloat16Member miniSum = new ComplexFloat16Member();
			ComplexFloat16Member root = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();
			ComplexFloat16Member logSum = new ComplexFloat16Member();
			
			multiply().call(I, a, ia);
			multiply().call(a, a, aSquared);
			subtract().call(ONE, aSquared, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(ia, root, sum);
			log().call(sum, logSum);
			multiply().call(MINUS_I, logSum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> asin() {
		return ASIN;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ACOS =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member aSquared = new ComplexFloat16Member();
			ComplexFloat16Member miniSum = new ComplexFloat16Member();
			ComplexFloat16Member root = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();
			ComplexFloat16Member logSum = new ComplexFloat16Member();

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, logSum);
			multiply().call(MINUS_I, logSum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> acos() {
		return ACOS;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ATAN =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member ia = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();
			ComplexFloat16Member diff = new ComplexFloat16Member();
			ComplexFloat16Member quotient = new ComplexFloat16Member();
			ComplexFloat16Member log = new ComplexFloat16Member();
			
			multiply().call(I, a, ia);
			add().call(ONE, ia, sum);
			subtract().call(ONE, ia, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(MINUS_I_OVER_TWO, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> atan() {
		return ATAN;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ACSC =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member recipA = new ComplexFloat16Member();
			
			invert().call(a, recipA);
			asin().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> acsc() {
		return ACSC;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ASEC =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member recipA = new ComplexFloat16Member();
			
			invert().call(a, recipA);
			acos().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> asec() {
		return ASEC;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ACOT =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member ia = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();
			ComplexFloat16Member diff = new ComplexFloat16Member();
			ComplexFloat16Member quotient = new ComplexFloat16Member();
			ComplexFloat16Member log = new ComplexFloat16Member();

			multiply().call(I, a, ia);
			add().call(ia, ONE, sum);
			subtract().call(ia, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(I_OVER_TWO, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> acot() {
		return ACOT;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ASINH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member aSquared = new ComplexFloat16Member();
			ComplexFloat16Member miniSum = new ComplexFloat16Member();
			ComplexFloat16Member root = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();

			multiply().call(a, a, aSquared);
			add().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> asinh() {
		return ASINH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ACOSH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member aSquared = new ComplexFloat16Member();
			ComplexFloat16Member miniSum = new ComplexFloat16Member();
			ComplexFloat16Member root = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> acosh() {
		return ACOSH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ATANH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member sum = new ComplexFloat16Member();
			ComplexFloat16Member diff = new ComplexFloat16Member();
			ComplexFloat16Member quotient = new ComplexFloat16Member();
			ComplexFloat16Member log = new ComplexFloat16Member();

			add().call(ONE, a, sum);
			subtract().call(ONE, a, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> atanh() {
		return ATANH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ACSCH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member recipA = new ComplexFloat16Member();

			invert().call(a, recipA);
			asinh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> acsch() {
		return ACSCH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ASECH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member recipA = new ComplexFloat16Member();

			invert().call(a, recipA);
			acosh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> asech() {
		return ASECH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> ACOTH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member sum = new ComplexFloat16Member();
			ComplexFloat16Member diff = new ComplexFloat16Member();
			ComplexFloat16Member quotient = new ComplexFloat16Member();
			ComplexFloat16Member log = new ComplexFloat16Member();

			add().call(a, ONE, sum);
			subtract().call(a, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> acoth() {
		return ACOTH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SIN =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member IA = new ComplexFloat16Member();
			ComplexFloat16Member minusIA = new ComplexFloat16Member();
			ComplexFloat16Member expIA = new ComplexFloat16Member();
			ComplexFloat16Member expMinusIA = new ComplexFloat16Member();
			ComplexFloat16Member diff = new ComplexFloat16Member();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			subtract().call(expIA, expMinusIA, diff);
			divide().call(diff, TWO_I, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sin() {
		return SIN;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> COS =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member IA = new ComplexFloat16Member();
			ComplexFloat16Member minusIA = new ComplexFloat16Member();
			ComplexFloat16Member expIA = new ComplexFloat16Member();
			ComplexFloat16Member expMinusIA = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			add().call(expIA, expMinusIA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> cos() {
		return COS;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> SINCOS =
			new Procedure3<ComplexFloat16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member s, ComplexFloat16Member c) {
			ComplexFloat16Member IA = new ComplexFloat16Member();
			ComplexFloat16Member minusIA = new ComplexFloat16Member();
			ComplexFloat16Member expIA = new ComplexFloat16Member();
			ComplexFloat16Member expMinusIA = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();
			ComplexFloat16Member diff = new ComplexFloat16Member();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			subtract().call(expIA, expMinusIA, diff);
			divide().call(diff, TWO_I, s);

			add().call(expIA, expMinusIA, sum);
			divide().call(sum, TWO, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> sinAndCos() {
		return SINCOS;
	}
	
	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> TAN =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member sin = new ComplexFloat16Member();
			ComplexFloat16Member cos = new ComplexFloat16Member();

			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> tan() {
		return TAN;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> CSC =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member sin = new ComplexFloat16Member();

			sin().call(a, sin);
			invert().call(sin, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> csc() {
		return CSC;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SEC =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member cos = new ComplexFloat16Member();

			cos().call(a, cos);
			invert().call(cos, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sec() {
		return SEC;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> COT =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member tan = new ComplexFloat16Member();

			tan().call(a, tan);
			invert().call(tan, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> cot() {
		return COT;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SINH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member expA = new ComplexFloat16Member();
			ComplexFloat16Member minusA = new ComplexFloat16Member();
			ComplexFloat16Member expMinusA = new ComplexFloat16Member();
			ComplexFloat16Member diff = new ComplexFloat16Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			subtract().call(expA, expMinusA, diff);
			divide().call(diff, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> COSH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member expA = new ComplexFloat16Member();
			ComplexFloat16Member minusA = new ComplexFloat16Member();
			ComplexFloat16Member expMinusA = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			add().call(expA, expMinusA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> SINHCOSH =
			new Procedure3<ComplexFloat16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member s, ComplexFloat16Member c) {
			ComplexFloat16Member expA = new ComplexFloat16Member();
			ComplexFloat16Member minusA = new ComplexFloat16Member();
			ComplexFloat16Member expMinusA = new ComplexFloat16Member();
			ComplexFloat16Member sum = new ComplexFloat16Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			subtract().call(expA, expMinusA, sum);
			divide().call(sum, TWO, s);

			add().call(expA, expMinusA, sum);
			divide().call(sum, TWO, c);
		}
	};

	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> sinhAndCosh() {
		return SINHCOSH;
	}
	
	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> TANH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member sinh = new ComplexFloat16Member();
			ComplexFloat16Member cosh = new ComplexFloat16Member();

			sinhAndCosh().call(a, sinh, cosh);
			divide().call(sinh, cosh, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> CSCH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member sinh = new ComplexFloat16Member();

			sinh().call(a, sinh);
			invert().call(sinh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> csch() {
		return CSCH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SECH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member cosh = new ComplexFloat16Member();

			cosh().call(a, cosh);
			invert().call(cosh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sech() {
		return SECH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> COTH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member tanh = new ComplexFloat16Member();

			tanh().call(a, tanh);
			invert().call(tanh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> coth() {
		return COTH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> EXP =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			double constant = Math.exp(a.r());
			b.setR( (float) (constant * Math.cos(a.i())) );
			b.setI( (float) (constant * Math.sin(a.i())) );
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> exp() {
		return EXP;
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> EXPM1 =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member tmp = new ComplexFloat16Member();
			exp().call(a, tmp);
			subtract().call(tmp, ONE, b);
		}
	};
	
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> expm1() {
		return EXPM1;
	}
	
	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> LOG =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			double modulus = Math.hypot(a.r(), a.i());
			double argument = getArgument(a);
			b.setR( (float) Math.log(modulus) );
			b.setI( (float) getPrincipalArgument(argument) );
		}
	};

	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> log() {
		return LOG;
	}
	
	private double getArgument(ComplexFloat16Member a) {
		double x = a.r();
		double y = a.i();
		double theta;
		if (x == 0) {
			if (y > 0)
				theta = Math.PI / 2;
			else if (y < 0)
				theta = -Math.PI / 2;
			else // y == 0 : theta indeterminate
				theta = Double.NaN;
		}
		else if (y == 0) {
			if (x > 0)
				theta = 0;
			else // (x < 0)
				theta = Math.PI;
		}
		else // x && y both != 0
			theta = Math.atan2(y,x);
		
		return theta;
	}
	
	private double getPrincipalArgument(double angle) {
		double arg = angle;
		while (arg <= -Math.PI) arg += 2*Math.PI;
		while (arg > Math.PI) arg -= 2*Math.PI;
		return arg;
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> LOG1P =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			ComplexFloat16Member tmp = new ComplexFloat16Member();
			add().call(a, ONE, tmp);
			log().call(tmp, b);
		}
	};
	
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> log1p() {
		return LOG1P;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SQRT =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sqrt() {
		return SQRT;
	}

	// TODO: make an accurate implementation
	
	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> CBRT =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> cbrt() {
		return CBRT;
	}

	private final Procedure4<Round.Mode,Float16Member,ComplexFloat16Member,ComplexFloat16Member> ROUND =
			new Procedure4<Round.Mode, Float16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, ComplexFloat16Member a, ComplexFloat16Member b) {
			Float16Member tmp = new Float16Member();
			tmp.setV(a.r());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setR(tmp.v());
			tmp.setV(a.i());
			Round.compute(G.HLF, mode, delta, tmp, tmp);
			b.setI(tmp.v());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float16Member,ComplexFloat16Member,ComplexFloat16Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat16Member> ISNAN =
			new Function1<Boolean, ComplexFloat16Member>()
	{
		@Override
		public Boolean call(ComplexFloat16Member a) {
			// true if either component is NaN
			return Double.isNaN(a.r()) || Double.isNaN(a.i());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat16Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat16Member> NAN =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			a.setR(Float.NaN);
			a.setI(Float.NaN);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,ComplexFloat16Member> ISINF =
			new Function1<Boolean, ComplexFloat16Member>()
	{
		@Override
		public Boolean call(ComplexFloat16Member a) {
			// true if neither is NaN and one or both is Inf
			return !isNaN().call(a) && (Double.isInfinite(a.r()) || Double.isInfinite(a.i()));
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat16Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat16Member> INF =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			a.setR(Float.POSITIVE_INFINITY);
			a.setI(Float.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16Member> infinite() {
		return INF;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> POW =
			new Procedure3<ComplexFloat16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b, ComplexFloat16Member c) {
			ComplexFloat16Member logA = new ComplexFloat16Member();
			ComplexFloat16Member bLogA = new ComplexFloat16Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16Member,ComplexFloat16Member> pow() {
		return POW;
	}

	private final Procedure1<ComplexFloat16Member> RAND =
			new Procedure1<ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextFloat());
			a.setI(rng.nextFloat());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16Member> random() {
		return RAND;
	}

	private final Procedure2<ComplexFloat16Member,Float16Member> REAL =
			new Procedure2<ComplexFloat16Member, Float16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, Float16Member b) {
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,Float16Member> real() {
		return REAL;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> UNREAL =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			b.setR(0);
			b.setI(a.i());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SINCH =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			Sinch.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SINCHPI =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			Sinchpi.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SINC =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			Sinc.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexFloat16Member,ComplexFloat16Member> SINCPI =
			new Procedure2<ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16Member a, ComplexFloat16Member b) {
			Sincpi.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16Member,ComplexFloat16Member> sincpi() {
		return SINCPI;
	}

	private final Function1<Boolean, ComplexFloat16Member> ISZERO =
			new Function1<Boolean, ComplexFloat16Member>()
	{
		@Override
		public Boolean call(ComplexFloat16Member a) {
			return a.r() == 0 && a.i() == 0;
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<ComplexFloat16Member, ComplexFloat16Member, ComplexFloat16Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat16Member, ComplexFloat16Member> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat16Member b, ComplexFloat16Member c) {
			BigDecimal tmp;
			tmp = a.v().multiply(BigDecimal.valueOf(b.r()));
			c.setR(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.i()));
			c.setI(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat16Member, ComplexFloat16Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexFloat16Member, ComplexFloat16Member> SBR =
			new Procedure3<RationalMember, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat16Member b, ComplexFloat16Member c) {
			BigDecimal n = new BigDecimal(a.n());
			BigDecimal d = new BigDecimal(a.d());
			BigDecimal tmp;
			tmp = BigDecimal.valueOf(b.r());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setR(tmp.floatValue());
			tmp = BigDecimal.valueOf(b.i());
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setI(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat16Member, ComplexFloat16Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat16Member, ComplexFloat16Member> SBD =
			new Procedure3<Double, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(Double a, ComplexFloat16Member b, ComplexFloat16Member c) {
			c.setR((float)(a * b.r()));
			c.setI((float)(a * b.i()));
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat16Member, ComplexFloat16Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float16Member, ComplexFloat16Member, ComplexFloat16Member> SC =
			new Procedure3<Float16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(Float16Member factor, ComplexFloat16Member a, ComplexFloat16Member b) {
			b.setR(factor.v() * a.r());
			b.setI(factor.v() * a.i());
		}
	};

	@Override
	public Procedure3<Float16Member, ComplexFloat16Member, ComplexFloat16Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float16Member, ComplexFloat16Member, ComplexFloat16Member> WITHIN =
			new Function3<Boolean, Float16Member, ComplexFloat16Member, ComplexFloat16Member>()
	{
		
		@Override
		public Boolean call(Float16Member tol, ComplexFloat16Member a, ComplexFloat16Member b) {
			return ComplexNumberWithin.compute(G.HLF, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float16Member, ComplexFloat16Member, ComplexFloat16Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, ComplexFloat16Member, ComplexFloat16Member> STWO =
			new Procedure3<java.lang.Integer, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, ComplexFloat16Member a, ComplexFloat16Member b) {
			ScaleHelper.compute(G.CHLF, G.CHLF, new ComplexFloat16Member(2, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, ComplexFloat16Member, ComplexFloat16Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, ComplexFloat16Member, ComplexFloat16Member> SHALF =
			new Procedure3<java.lang.Integer, ComplexFloat16Member, ComplexFloat16Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, ComplexFloat16Member a, ComplexFloat16Member b) {
			ScaleHelper.compute(G.CHLF, G.CHLF, new ComplexFloat16Member(0.5f, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, ComplexFloat16Member, ComplexFloat16Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, ComplexFloat16Member> ISUNITY =
			new Function1<Boolean, ComplexFloat16Member>()
	{
		@Override
		public Boolean call(ComplexFloat16Member a) {
			return G.CHLF.isEqual().call(a, ONE);
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16Member> isUnity() {
		return ISUNITY;
	}

}
