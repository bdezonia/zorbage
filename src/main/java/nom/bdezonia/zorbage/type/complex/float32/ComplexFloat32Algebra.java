/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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

// Note: some code here adapted from Imglib2 code written by Barry DeZonia
//
// * ImgLib2: a general-purpose, multidimensional image processing library.
// * Copyright (C) 2009 - 2013 Stephan Preibisch, Tobias Pietzsch, Barry DeZonia,
// * Stephan Saalfeld, Albert Cardona, Curtis Rueden, Christian Dietz, Jean-Yves
// * Tinevez, Johannes Schindelin, Lee Kamentsky, Larry Lindsey, Grant Harris,
// * Mark Hiner, Aivar Grislis, Martin Horn, Nick Perry, Michael Zinsmaier,
// * Steffen Jaensch, Jan Funke, Mark Longair, and Dimiter Prodanov.


package nom.bdezonia.zorbage.type.complex.float32;

import java.lang.Integer;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.ComplexNumberWithin;
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
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat32Algebra
	implements
		Field<ComplexFloat32Algebra, ComplexFloat32Member>,
		Norm<ComplexFloat32Member, Float32Member>,
		RealConstants<ComplexFloat32Member>,
		ImaginaryConstants<ComplexFloat32Member>,
		Exponential<ComplexFloat32Member>,
		Trigonometric<ComplexFloat32Member>,
		InverseTrigonometric<ComplexFloat32Member>,
		Hyperbolic<ComplexFloat32Member>,
		InverseHyperbolic<ComplexFloat32Member>,
		Roots<ComplexFloat32Member>,
		Power<ComplexFloat32Member>,
		Rounding<Float32Member,ComplexFloat32Member>,
		Infinite<ComplexFloat32Member>,
		NaN<ComplexFloat32Member>,
		Conjugate<ComplexFloat32Member>,
		Random<ComplexFloat32Member>,
		RealUnreal<ComplexFloat32Member,Float32Member>,
		Scale<ComplexFloat32Member,ComplexFloat32Member>,
		ScaleByHighPrec<ComplexFloat32Member>,
		ScaleByRational<ComplexFloat32Member>,
		ScaleByDouble<ComplexFloat32Member>,
		ScaleComponents<ComplexFloat32Member, Float32Member>,
		Tolerance<Float32Member,ComplexFloat32Member>,
		ScaleByOneHalf<ComplexFloat32Member>,
		ScaleByTwo<ComplexFloat32Member>
{
	private static final ComplexFloat32Member ONE = new ComplexFloat32Member(1,0);
	private static final ComplexFloat32Member TWO = new ComplexFloat32Member(2,0);
	private static final ComplexFloat32Member MINUS_ONE = new ComplexFloat32Member(-1,0);
	private static final ComplexFloat32Member PI = new ComplexFloat32Member((float)Math.PI,0);
	private static final ComplexFloat32Member E = new ComplexFloat32Member((float)Math.E,0);
	private static final ComplexFloat32Member GAMMA = new ComplexFloat32Member((float)0.57721566490153286060,0);
	private static final ComplexFloat32Member PHI = new ComplexFloat32Member((float)1.61803398874989484820,0);
	private static final ComplexFloat32Member ONE_HALF = new ComplexFloat32Member(0.5f,0);
	private static final ComplexFloat32Member ONE_THIRD = new ComplexFloat32Member(1.0f/3,0);
	private static final ComplexFloat32Member I = new ComplexFloat32Member(0,1);
	private static final ComplexFloat32Member I_OVER_TWO = new ComplexFloat32Member(0,0.5f);
	private static final ComplexFloat32Member TWO_I = new ComplexFloat32Member(0,2);
	private static final ComplexFloat32Member MINUS_I = new ComplexFloat32Member(0,-1);
	private static final ComplexFloat32Member MINUS_I_OVER_TWO = new ComplexFloat32Member(0,-0.5f);
	private static final ComplexFloat32Member NaN_ = new ComplexFloat32Member(Float.NaN,Float.NaN);

	public ComplexFloat32Algebra() { }
	
	private final Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> MUL =
			new Procedure3<ComplexFloat32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b, ComplexFloat32Member c) {
			// for safety must use tmps
			double r = a.r()*b.r() - a.i()*b.i();
			double i = a.i()*b.r() + a.r()*b.i();
			c.setR( (float) r );
			c.setI( (float) i );
		}
	};

	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat32Member,ComplexFloat32Member> POWER =
			new Procedure3<Integer, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(Integer power, ComplexFloat32Member a, ComplexFloat32Member b) {
			if (power == 0 && isZero().call(a)) {
				assign().call(NaN_, b);
				return;
			}
			double rToTheN = Math.pow(Math.hypot(a.r(), a.i()), power);
			double nTheta = power * getArgument(a);
			b.setR( (float) (rToTheN * Math.cos(nTheta)) );
			b.setI( (float) (rToTheN * Math.sin(nTheta)) );
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat32Member,ComplexFloat32Member> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat32Member> ZER =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<ComplexFloat32Member> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> NEG =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			b.setR( -a.r() );
			b.setI( -a.i() );
		}
	};

	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> ADD =
			new Procedure3<ComplexFloat32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b, ComplexFloat32Member c) {
			c.setR( a.r() + b.r() );
			c.setI( a.i() + b.i() );
		}
	};

	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> SUB =
			new Procedure3<ComplexFloat32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b, ComplexFloat32Member c) {
			c.setR( a.r() - b.r() );
			c.setI( a.i() - b.i() );
		}
	};

	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat32Member,ComplexFloat32Member> EQ =
			new Function2<Boolean, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public Boolean call(ComplexFloat32Member a, ComplexFloat32Member b) {
			return a.r() == b.r() && a.i() == b.i();
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat32Member,ComplexFloat32Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat32Member,ComplexFloat32Member> NEQ =
			new Function2<Boolean, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public Boolean call(ComplexFloat32Member a, ComplexFloat32Member b) {
			return a.r() != b.r() || a.i() != b.i();
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat32Member,ComplexFloat32Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public ComplexFloat32Member construct() {
		return new ComplexFloat32Member();
	}

	@Override
	public ComplexFloat32Member construct(ComplexFloat32Member other) {
		return new ComplexFloat32Member(other);
	}

	@Override
	public ComplexFloat32Member construct(String s) {
		return new ComplexFloat32Member(s);
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ASSIGN =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member from, ComplexFloat32Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat32Member> UNITY =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32Member> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> INV =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			divide().call(ONE, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> DIVIDE =
			new Procedure3<ComplexFloat32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b, ComplexFloat32Member c) {
			// for safety must use tmps
			double mod2 = b.r()*b.r() + b.i()*b.i();
			double r = (a.r()*b.r() + a.i()*b.i()) / mod2;
			double i = (a.i()*b.r() - a.r()*b.i()) / mod2;
			c.setR( (float) r );
			c.setI( (float) i );
		}
	};

	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> divide() {
		return DIVIDE;
	}
	
	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> CONJ =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			b.setR( a.r() );
			b.setI( -a.i() );
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat32Member,Float32Member> NORM =
			new Procedure2<ComplexFloat32Member, Float32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, Float32Member b) {
			b.setV( (float) Math.hypot(a.r(),a.i()) );
		}
	};

	@Override
	public Procedure2<ComplexFloat32Member,Float32Member> norm() {
		return NORM;
	}

	private final Procedure1<ComplexFloat32Member> PI_ =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			assign().call(PI, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32Member> PI() {
		return PI_;
	}

	private final Procedure1<ComplexFloat32Member> E_ =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			assign().call(E, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32Member> E() {
		return E_;
	}

	private final Procedure1<ComplexFloat32Member> GAMMA_ =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			assign().call(GAMMA, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<ComplexFloat32Member> PHI_ =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			assign().call(PHI, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32Member> PHI() {
		return PHI_;
	}

	private final Procedure1<ComplexFloat32Member> I_ =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			assign().call(I, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat32Member> I() {
		return I_;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ASIN =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member ia = new ComplexFloat32Member();
			ComplexFloat32Member aSquared = new ComplexFloat32Member();
			ComplexFloat32Member miniSum = new ComplexFloat32Member();
			ComplexFloat32Member root = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();
			ComplexFloat32Member logSum = new ComplexFloat32Member();
			
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
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> asin() {
		return ASIN;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ACOS =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member aSquared = new ComplexFloat32Member();
			ComplexFloat32Member miniSum = new ComplexFloat32Member();
			ComplexFloat32Member root = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();
			ComplexFloat32Member logSum = new ComplexFloat32Member();

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, logSum);
			multiply().call(MINUS_I, logSum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> acos() {
		return ACOS;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ATAN =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member ia = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();
			ComplexFloat32Member diff = new ComplexFloat32Member();
			ComplexFloat32Member quotient = new ComplexFloat32Member();
			ComplexFloat32Member log = new ComplexFloat32Member();
			
			multiply().call(I, a, ia);
			add().call(ONE, ia, sum);
			subtract().call(ONE, ia, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(MINUS_I_OVER_TWO, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> atan() {
		return ATAN;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ACSC =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member recipA = new ComplexFloat32Member();
			
			invert().call(a, recipA);
			asin().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> acsc() {
		return ACSC;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ASEC =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member recipA = new ComplexFloat32Member();
			
			invert().call(a, recipA);
			acos().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> asec() {
		return ASEC;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ACOT =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member ia = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();
			ComplexFloat32Member diff = new ComplexFloat32Member();
			ComplexFloat32Member quotient = new ComplexFloat32Member();
			ComplexFloat32Member log = new ComplexFloat32Member();

			multiply().call(I, a, ia);
			add().call(ia, ONE, sum);
			subtract().call(ia, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(I_OVER_TWO, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> acot() {
		return ACOT;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ASINH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member aSquared = new ComplexFloat32Member();
			ComplexFloat32Member miniSum = new ComplexFloat32Member();
			ComplexFloat32Member root = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();

			multiply().call(a, a, aSquared);
			add().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> asinh() {
		return ASINH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ACOSH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member aSquared = new ComplexFloat32Member();
			ComplexFloat32Member miniSum = new ComplexFloat32Member();
			ComplexFloat32Member root = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> acosh() {
		return ACOSH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ATANH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member sum = new ComplexFloat32Member();
			ComplexFloat32Member diff = new ComplexFloat32Member();
			ComplexFloat32Member quotient = new ComplexFloat32Member();
			ComplexFloat32Member log = new ComplexFloat32Member();

			add().call(ONE, a, sum);
			subtract().call(ONE, a, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> atanh() {
		return ATANH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ACSCH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member recipA = new ComplexFloat32Member();

			invert().call(a, recipA);
			asinh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> acsch() {
		return ACSCH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ASECH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member recipA = new ComplexFloat32Member();

			invert().call(a, recipA);
			acosh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> asech() {
		return ASECH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> ACOTH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member sum = new ComplexFloat32Member();
			ComplexFloat32Member diff = new ComplexFloat32Member();
			ComplexFloat32Member quotient = new ComplexFloat32Member();
			ComplexFloat32Member log = new ComplexFloat32Member();

			add().call(a, ONE, sum);
			subtract().call(a, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> acoth() {
		return ACOTH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SIN =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member IA = new ComplexFloat32Member();
			ComplexFloat32Member minusIA = new ComplexFloat32Member();
			ComplexFloat32Member expIA = new ComplexFloat32Member();
			ComplexFloat32Member expMinusIA = new ComplexFloat32Member();
			ComplexFloat32Member diff = new ComplexFloat32Member();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			subtract().call(expIA, expMinusIA, diff);
			divide().call(diff, TWO_I, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sin() {
		return SIN;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> COS =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member IA = new ComplexFloat32Member();
			ComplexFloat32Member minusIA = new ComplexFloat32Member();
			ComplexFloat32Member expIA = new ComplexFloat32Member();
			ComplexFloat32Member expMinusIA = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			add().call(expIA, expMinusIA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> cos() {
		return COS;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> SINCOS =
			new Procedure3<ComplexFloat32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member s, ComplexFloat32Member c) {
			ComplexFloat32Member IA = new ComplexFloat32Member();
			ComplexFloat32Member minusIA = new ComplexFloat32Member();
			ComplexFloat32Member expIA = new ComplexFloat32Member();
			ComplexFloat32Member expMinusIA = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();
			ComplexFloat32Member diff = new ComplexFloat32Member();

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
	public Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> sinAndCos() {
		return SINCOS;
	}
	
	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> TAN =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member sin = new ComplexFloat32Member();
			ComplexFloat32Member cos = new ComplexFloat32Member();

			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> tan() {
		return TAN;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> CSC =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member sin = new ComplexFloat32Member();

			sin().call(a, sin);
			invert().call(sin, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> csc() {
		return CSC;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SEC =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member cos = new ComplexFloat32Member();

			cos().call(a, cos);
			invert().call(cos, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sec() {
		return SEC;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> COT =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member tan = new ComplexFloat32Member();

			tan().call(a, tan);
			invert().call(tan, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> cot() {
		return COT;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SINH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member expA = new ComplexFloat32Member();
			ComplexFloat32Member minusA = new ComplexFloat32Member();
			ComplexFloat32Member expMinusA = new ComplexFloat32Member();
			ComplexFloat32Member diff = new ComplexFloat32Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			subtract().call(expA, expMinusA, diff);
			divide().call(diff, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> COSH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member expA = new ComplexFloat32Member();
			ComplexFloat32Member minusA = new ComplexFloat32Member();
			ComplexFloat32Member expMinusA = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			add().call(expA, expMinusA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> SINHCOSH =
			new Procedure3<ComplexFloat32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member s, ComplexFloat32Member c) {
			ComplexFloat32Member expA = new ComplexFloat32Member();
			ComplexFloat32Member minusA = new ComplexFloat32Member();
			ComplexFloat32Member expMinusA = new ComplexFloat32Member();
			ComplexFloat32Member sum = new ComplexFloat32Member();

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
	public Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> sinhAndCosh() {
		return SINHCOSH;
	}
	
	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> TANH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member sinh = new ComplexFloat32Member();
			ComplexFloat32Member cosh = new ComplexFloat32Member();

			sinhAndCosh().call(a, sinh, cosh);
			divide().call(sinh, cosh, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> CSCH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member sinh = new ComplexFloat32Member();

			sinh().call(a, sinh);
			invert().call(sinh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> csch() {
		return CSCH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SECH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member cosh = new ComplexFloat32Member();

			cosh().call(a, cosh);
			invert().call(cosh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sech() {
		return SECH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> COTH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member tanh = new ComplexFloat32Member();

			tanh().call(a, tanh);
			invert().call(tanh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> coth() {
		return COTH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> EXP =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			double constant = Math.exp(a.r());
			b.setR( (float) (constant * Math.cos(a.i())) );
			b.setI( (float) (constant * Math.sin(a.i())) );
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> exp() {
		return EXP;
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> EXPM1 =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member tmp = new ComplexFloat32Member();
			exp().call(a, tmp);
			subtract().call(tmp, ONE, b);
		}
	};
	
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> expm1() {
		return EXPM1;
	}
	
	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> LOG =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			double modulus = Math.hypot(a.r(), a.i());
			double argument = getArgument(a);
			b.setR( (float) Math.log(modulus) );
			b.setI( (float) getPrincipalArgument(argument) );
		}
	};

	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> log() {
		return LOG;
	}
	
	private double getArgument(ComplexFloat32Member a) {
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

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> LOG1P =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			ComplexFloat32Member tmp = new ComplexFloat32Member();
			add().call(a, ONE, tmp);
			log().call(tmp, b);
		}
	};
	
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> log1p() {
		return LOG1P;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SQRT =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sqrt() {
		return SQRT;
	}

	// TODO: make an accurate implementation
	
	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> CBRT =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> cbrt() {
		return CBRT;
	}

	private final Procedure4<Round.Mode,Float32Member,ComplexFloat32Member,ComplexFloat32Member> ROUND =
			new Procedure4<Round.Mode, Float32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, ComplexFloat32Member a, ComplexFloat32Member b) {
			Float32Member tmp = new Float32Member();
			tmp.setV(a.r());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setR(tmp.v());
			tmp.setV(a.i());
			Round.compute(G.FLT, mode, delta, tmp, tmp);
			b.setI(tmp.v());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float32Member,ComplexFloat32Member,ComplexFloat32Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat32Member> ISNAN =
			new Function1<Boolean, ComplexFloat32Member>()
	{
		@Override
		public Boolean call(ComplexFloat32Member a) {
			// true if either component is NaN
			return Double.isNaN(a.r()) || Double.isNaN(a.i());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat32Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat32Member> NAN =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			a.setR(Float.NaN);
			a.setI(Float.NaN);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,ComplexFloat32Member> ISINF =
			new Function1<Boolean, ComplexFloat32Member>()
	{
		@Override
		public Boolean call(ComplexFloat32Member a) {
			// true if neither is NaN and one or both is Inf
			return !isNaN().call(a) && (Double.isInfinite(a.r()) || Double.isInfinite(a.i()));
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat32Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat32Member> INF =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			a.setR(Float.POSITIVE_INFINITY);
			a.setI(Float.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32Member> infinite() {
		return INF;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> POW =
			new Procedure3<ComplexFloat32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b, ComplexFloat32Member c) {
			ComplexFloat32Member logA = new ComplexFloat32Member();
			ComplexFloat32Member bLogA = new ComplexFloat32Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32Member,ComplexFloat32Member> pow() {
		return POW;
	}

	private final Procedure1<ComplexFloat32Member> RAND =
			new Procedure1<ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextFloat());
			a.setI(rng.nextFloat());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32Member> random() {
		return RAND;
	}

	private final Procedure2<ComplexFloat32Member,Float32Member> REAL =
			new Procedure2<ComplexFloat32Member, Float32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, Float32Member b) {
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,Float32Member> real() {
		return REAL;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> UNREAL =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			b.setR(0);
			b.setI(a.i());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SINCH =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			Sinch.compute(G.CFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SINCHPI =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			Sinchpi.compute(G.CFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SINC =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			Sinc.compute(G.CFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexFloat32Member,ComplexFloat32Member> SINCPI =
			new Procedure2<ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(ComplexFloat32Member a, ComplexFloat32Member b) {
			Sincpi.compute(G.CFLT, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32Member,ComplexFloat32Member> sincpi() {
		return SINCPI;
	}

	private final Function1<Boolean, ComplexFloat32Member> ISZERO =
			new Function1<Boolean, ComplexFloat32Member>()
	{
		@Override
		public Boolean call(ComplexFloat32Member a) {
			return a.r() == 0 && a.i() == 0;
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32Member, ComplexFloat32Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat32Member, ComplexFloat32Member> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat32Member b, ComplexFloat32Member c) {
			BigDecimal tmp;
			tmp = a.v().multiply(BigDecimal.valueOf(b.r()));
			c.setR(tmp.floatValue());
			tmp = a.v().multiply(BigDecimal.valueOf(b.i()));
			c.setI(tmp.floatValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat32Member, ComplexFloat32Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexFloat32Member, ComplexFloat32Member> SBR =
			new Procedure3<RationalMember, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat32Member b, ComplexFloat32Member c) {
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
	public Procedure3<RationalMember, ComplexFloat32Member, ComplexFloat32Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat32Member, ComplexFloat32Member> SBD =
			new Procedure3<Double, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(Double a, ComplexFloat32Member b, ComplexFloat32Member c) {
			c.setR((float)(a * b.r()));
			c.setI((float)(a * b.i()));
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat32Member, ComplexFloat32Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float32Member, ComplexFloat32Member, ComplexFloat32Member> SC =
			new Procedure3<Float32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(Float32Member factor, ComplexFloat32Member a, ComplexFloat32Member b) {
			b.setR(factor.v() * a.r());
			b.setI(factor.v() * a.i());
		}
	};

	@Override
	public Procedure3<Float32Member, ComplexFloat32Member, ComplexFloat32Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float32Member, ComplexFloat32Member, ComplexFloat32Member> WITHIN =
			new Function3<Boolean, Float32Member, ComplexFloat32Member, ComplexFloat32Member>()
	{
		
		@Override
		public Boolean call(Float32Member tol, ComplexFloat32Member a, ComplexFloat32Member b) {
			return ComplexNumberWithin.compute(G.FLT, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float32Member, ComplexFloat32Member, ComplexFloat32Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, ComplexFloat32Member, ComplexFloat32Member> STWO =
			new Procedure3<java.lang.Integer, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, ComplexFloat32Member a, ComplexFloat32Member b) {
			ScaleHelper.compute(G.CFLT, G.CFLT, new ComplexFloat32Member(2, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, ComplexFloat32Member, ComplexFloat32Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, ComplexFloat32Member, ComplexFloat32Member> SHALF =
			new Procedure3<java.lang.Integer, ComplexFloat32Member, ComplexFloat32Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, ComplexFloat32Member a, ComplexFloat32Member b) {
			ScaleHelper.compute(G.CFLT, G.CFLT, new ComplexFloat32Member(0.5f, 0), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, ComplexFloat32Member, ComplexFloat32Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, ComplexFloat32Member> ISUNITY =
			new Function1<Boolean, ComplexFloat32Member>()
	{
		@Override
		public Boolean call(ComplexFloat32Member a) {
			return G.CFLT.isEqual().call(a, ONE);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat32Member> isUnity() {
		return ISUNITY;
	}

}
