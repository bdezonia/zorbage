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

// Note: some code here adapted from Imglib2 code written by Barry DeZonia
//
// * ImgLib2: a general-purpose, multidimensional image processing library.
// * Copyright (C) 2009 - 2013 Stephan Preibisch, Tobias Pietzsch, Barry DeZonia,
// * Stephan Saalfeld, Albert Cardona, Curtis Rueden, Christian Dietz, Jean-Yves
// * Tinevez, Johannes Schindelin, Lee Kamentsky, Larry Lindsey, Grant Harris,
// * Mark Hiner, Aivar Grislis, Martin Horn, Nick Perry, Michael Zinsmaier,
// * Steffen Jaensch, Jan Funke, Mark Longair, and Dimiter Prodanov.


package nom.bdezonia.zorbage.type.data.float64.complex;

import java.util.concurrent.ThreadLocalRandom;

import net.jafama.FastMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Exponential;
import nom.bdezonia.zorbage.type.algebra.Field;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.InverseHyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64Algebra
  implements
    Field<ComplexFloat64Algebra, ComplexFloat64Member>,
    Norm<ComplexFloat64Member, Float64Member>,
    Constants<ComplexFloat64Member>,
    Exponential<ComplexFloat64Member>,
    Trigonometric<ComplexFloat64Member>,
    InverseTrigonometric<ComplexFloat64Member>,
    Hyperbolic<ComplexFloat64Member>,
    InverseHyperbolic<ComplexFloat64Member>,
    Roots<ComplexFloat64Member>,
    Power<ComplexFloat64Member>,
    Rounding<Float64Member,ComplexFloat64Member>,
    Infinite<ComplexFloat64Member>,
    NaN<ComplexFloat64Member>,
    Conjugate<ComplexFloat64Member>,
    Random<ComplexFloat64Member>,
    RealUnreal<ComplexFloat64Member,Float64Member>,
    Scale<ComplexFloat64Member,ComplexFloat64Member>
{
	private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0);
	private static final ComplexFloat64Member ONE = new ComplexFloat64Member(1,0);
	private static final ComplexFloat64Member TWO = new ComplexFloat64Member(2,0);
	private static final ComplexFloat64Member MINUS_ONE = new ComplexFloat64Member(-1,0);
	private static final ComplexFloat64Member PI = new ComplexFloat64Member(Math.PI,0);
	private static final ComplexFloat64Member E = new ComplexFloat64Member(Math.E,0);
	private static final ComplexFloat64Member ONE_HALF = new ComplexFloat64Member(0.5,0);
	private static final ComplexFloat64Member ONE_THIRD = new ComplexFloat64Member(1.0/3,0);
	private static final ComplexFloat64Member I = new ComplexFloat64Member(0,1);
	private static final ComplexFloat64Member I_OVER_TWO = new ComplexFloat64Member(0,0.5);
	private static final ComplexFloat64Member TWO_I = new ComplexFloat64Member(0,2);
	private static final ComplexFloat64Member MINUS_I = new ComplexFloat64Member(0,-1);
	private static final ComplexFloat64Member MINUS_I_OVER_TWO = new ComplexFloat64Member(0,-0.5);
	private static final ComplexFloat64Member NaN_ = new ComplexFloat64Member(Double.NaN,Double.NaN);

	public ComplexFloat64Algebra() { }
	
	private final Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> MUL =
			new Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
			// for safety must use tmps
			double r = a.r()*b.r() - a.i()*b.i();
			double i = a.i()*b.r() + a.r()*b.i();
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat64Member,ComplexFloat64Member> POWER =
			new Procedure3<Integer, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(Integer power, ComplexFloat64Member a, ComplexFloat64Member b) {
			if (power == 0 && isZero().call(a)) {
				assign().call(NaN_, b);
				return;
			}
			double rToTheN = FastMath.pow(FastMath.hypot(a.r(), a.i()), power);
			double nTheta = power * getArgument(a);
			b.setR(rToTheN * FastMath.cos(nTheta));
			b.setI(rToTheN * FastMath.sin(nTheta));
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat64Member,ComplexFloat64Member> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat64Member> ZER =
			new Procedure1<ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a) {
			assign().call(ZERO,a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64Member> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> NEG =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			b.setR( -a.r() );
			b.setI( -a.i() );
		}
	};

	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> ADD =
			new Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
			c.setR( a.r() + b.r() );
			c.setI( a.i() + b.i() );
		}
	};

	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> SUB =
			new Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
			c.setR( a.r() - b.r() );
			c.setI( a.i() - b.i() );
		}
	};

	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat64Member,ComplexFloat64Member> EQ =
			new Function2<Boolean, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public Boolean call(ComplexFloat64Member a, ComplexFloat64Member b) {
			return a.r() == b.r() && a.i() == b.i();
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat64Member,ComplexFloat64Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat64Member,ComplexFloat64Member> NEQ =
			new Function2<Boolean, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public Boolean call(ComplexFloat64Member a, ComplexFloat64Member b) {
			return a.r() != b.r() || a.i() != b.i();
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat64Member,ComplexFloat64Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public ComplexFloat64Member construct() {
		return new ComplexFloat64Member();
	}

	@Override
	public ComplexFloat64Member construct(ComplexFloat64Member other) {
		return new ComplexFloat64Member(other);
	}

	@Override
	public ComplexFloat64Member construct(String s) {
		return new ComplexFloat64Member(s);
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ASSIGN =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member from, ComplexFloat64Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat64Member> UNITY =
			new Procedure1<ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a) {
			assign().call(ONE,a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64Member> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> INV =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			divide().call(ONE,a,b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> DIVIDE =
			new Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
			// for safety must use tmps
			double mod2 = b.r()*b.r() + b.i()*b.i();
			double r = (a.r()*b.r() + a.i()*b.i()) / mod2;
			double i = (a.i()*b.r() - a.r()*b.i()) / mod2;
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> divide() {
		return DIVIDE;
	}
	
	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> CONJ =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			b.setR( a.r() );
			b.setI( -a.i() );
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat64Member,Float64Member> NORM =
			new Procedure2<ComplexFloat64Member, Float64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, Float64Member b) {
			b.setV( FastMath.hypot(a.r(),a.i()) );
		}
	};

	@Override
	public Procedure2<ComplexFloat64Member,Float64Member> norm() {
		return NORM;
	}

	private final Procedure1<ComplexFloat64Member> PI_ =
			new Procedure1<ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a) {
			assign().call(PI, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64Member> PI() {
		return PI_;
	}

	private final Procedure1<ComplexFloat64Member> E_ =
			new Procedure1<ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a) {
			assign().call(E, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64Member> E() {
		return E_;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ASIN =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member ia = new ComplexFloat64Member();
			ComplexFloat64Member aSquared = new ComplexFloat64Member();
			ComplexFloat64Member miniSum = new ComplexFloat64Member();
			ComplexFloat64Member root = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();
			ComplexFloat64Member logSum = new ComplexFloat64Member();
			
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
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> asin() {
		return ASIN;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ACOS =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member aSquared = new ComplexFloat64Member();
			ComplexFloat64Member miniSum = new ComplexFloat64Member();
			ComplexFloat64Member root = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();
			ComplexFloat64Member logSum = new ComplexFloat64Member();

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, logSum);
			multiply().call(MINUS_I, logSum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> acos() {
		return ACOS;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ATAN =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member ia = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();
			ComplexFloat64Member diff = new ComplexFloat64Member();
			ComplexFloat64Member quotient = new ComplexFloat64Member();
			ComplexFloat64Member log = new ComplexFloat64Member();
			
			multiply().call(I, a, ia);
			add().call(ONE, ia, sum);
			subtract().call(ONE, ia, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(MINUS_I_OVER_TWO, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> atan() {
		return ATAN;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ACSC =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member recipA = new ComplexFloat64Member();
			
			invert().call(a, recipA);
			asin().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> acsc() {
		return ACSC;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ASEC =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member recipA = new ComplexFloat64Member();
			
			invert().call(a, recipA);
			acos().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> asec() {
		return ASEC;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ACOT =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member ia = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();
			ComplexFloat64Member diff = new ComplexFloat64Member();
			ComplexFloat64Member quotient = new ComplexFloat64Member();
			ComplexFloat64Member log = new ComplexFloat64Member();

			multiply().call(I, a, ia);
			add().call(ia, ONE, sum);
			subtract().call(ia, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(I_OVER_TWO, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> acot() {
		return ACOT;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ASINH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member aSquared = new ComplexFloat64Member();
			ComplexFloat64Member miniSum = new ComplexFloat64Member();
			ComplexFloat64Member root = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();

			multiply().call(a, a, aSquared);
			add().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> asinh() {
		return ASINH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ACOSH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member aSquared = new ComplexFloat64Member();
			ComplexFloat64Member miniSum = new ComplexFloat64Member();
			ComplexFloat64Member root = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> acosh() {
		return ACOSH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ATANH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member sum = new ComplexFloat64Member();
			ComplexFloat64Member diff = new ComplexFloat64Member();
			ComplexFloat64Member quotient = new ComplexFloat64Member();
			ComplexFloat64Member log = new ComplexFloat64Member();

			add().call(ONE, a, sum);
			subtract().call(ONE, a, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> atanh() {
		return ATANH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ACSCH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member recipA = new ComplexFloat64Member();

			invert().call(a, recipA);
			asinh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> acsch() {
		return ACSCH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ASECH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member recipA = new ComplexFloat64Member();

			invert().call(a, recipA);
			acosh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> asech() {
		return ASECH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> ACOTH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member sum = new ComplexFloat64Member();
			ComplexFloat64Member diff = new ComplexFloat64Member();
			ComplexFloat64Member quotient = new ComplexFloat64Member();
			ComplexFloat64Member log = new ComplexFloat64Member();

			add().call(a, ONE, sum);
			subtract().call(a, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> acoth() {
		return ACOTH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SIN =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member IA = new ComplexFloat64Member();
			ComplexFloat64Member minusIA = new ComplexFloat64Member();
			ComplexFloat64Member expIA = new ComplexFloat64Member();
			ComplexFloat64Member expMinusIA = new ComplexFloat64Member();
			ComplexFloat64Member diff = new ComplexFloat64Member();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			subtract().call(expIA, expMinusIA, diff);
			divide().call(diff, TWO_I, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sin() {
		return SIN;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> COS =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member IA = new ComplexFloat64Member();
			ComplexFloat64Member minusIA = new ComplexFloat64Member();
			ComplexFloat64Member expIA = new ComplexFloat64Member();
			ComplexFloat64Member expMinusIA = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			add().call(expIA, expMinusIA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> cos() {
		return COS;
	}

	private Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> SINCOS =
			new Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member s, ComplexFloat64Member c) {
			ComplexFloat64Member IA = new ComplexFloat64Member();
			ComplexFloat64Member minusIA = new ComplexFloat64Member();
			ComplexFloat64Member expIA = new ComplexFloat64Member();
			ComplexFloat64Member expMinusIA = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();
			ComplexFloat64Member diff = new ComplexFloat64Member();

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
	public Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> sinAndCos() {
		return SINCOS;
	}
	
	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> TAN =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member sin = new ComplexFloat64Member();
			ComplexFloat64Member cos = new ComplexFloat64Member();

			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> tan() {
		return TAN;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> CSC =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member sin = new ComplexFloat64Member();

			sin().call(a, sin);
			invert().call(sin, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> csc() {
		return CSC;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SEC =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member cos = new ComplexFloat64Member();

			cos().call(a, cos);
			invert().call(cos, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sec() {
		return SEC;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> COT =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member tan = new ComplexFloat64Member();

			tan().call(a, tan);
			invert().call(tan, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> cot() {
		return COT;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SINH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member expA = new ComplexFloat64Member();
			ComplexFloat64Member minusA = new ComplexFloat64Member();
			ComplexFloat64Member expMinusA = new ComplexFloat64Member();
			ComplexFloat64Member diff = new ComplexFloat64Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			subtract().call(expA, expMinusA, diff);
			divide().call(diff, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> COSH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member expA = new ComplexFloat64Member();
			ComplexFloat64Member minusA = new ComplexFloat64Member();
			ComplexFloat64Member expMinusA = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			add().call(expA, expMinusA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> SINHCOSH =
			new Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member s, ComplexFloat64Member c) {
			ComplexFloat64Member expA = new ComplexFloat64Member();
			ComplexFloat64Member minusA = new ComplexFloat64Member();
			ComplexFloat64Member expMinusA = new ComplexFloat64Member();
			ComplexFloat64Member sum = new ComplexFloat64Member();

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
	public Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> sinhAndCosh() {
		return SINHCOSH;
	}
	
	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> TANH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member sinh = new ComplexFloat64Member();
			ComplexFloat64Member cosh = new ComplexFloat64Member();

			sinhAndCosh().call(a, sinh, cosh);
			divide().call(sinh, cosh, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> CSCH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member sinh = new ComplexFloat64Member();

			sinh().call(a, sinh);
			invert().call(sinh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> csch() {
		return CSCH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SECH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member cosh = new ComplexFloat64Member();

			cosh().call(a, cosh);
			invert().call(cosh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sech() {
		return SECH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> COTH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member tanh = new ComplexFloat64Member();

			tanh().call(a, tanh);
			invert().call(tanh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> coth() {
		return COTH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> EXP =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			double constant = FastMath.exp(a.r());
			b.setR( constant * FastMath.cos(a.i()) );
			b.setI( constant * FastMath.sin(a.i()) );
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> exp() {
		return EXP;
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> EXPM1 =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member tmp = new ComplexFloat64Member();
			exp().call(a, tmp);
			subtract().call(tmp, ONE, b);
		}
	};
	
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> expm1() {
		return EXPM1;
	}
	
	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> LOG =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			double modulus = FastMath.hypot(a.r(), a.i());
			double argument = getArgument(a);
			b.setR( Math.log(modulus) );
			b.setI( getPrincipalArgument(argument) );
		}
	};

	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> log() {
		return LOG;
	}
	
	private double getArgument(ComplexFloat64Member a) {
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
			theta = FastMath.atan2(y,x);
		
		return theta;
	}
	
	private double getPrincipalArgument(double angle) {
		double arg = angle;
		while (arg <= -Math.PI) arg += 2*Math.PI;
		while (arg > Math.PI) arg -= 2*Math.PI;
		return arg;
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> LOG1P =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			ComplexFloat64Member tmp = new ComplexFloat64Member();
			add().call(a, ONE, tmp);
			log().call(tmp, b);
		}
	};
	
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> log1p() {
		return LOG1P;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SQRT =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			pow().call(a,ONE_HALF,b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sqrt() {
		return SQRT;
	}

	// TODO: make an accurate implementation
	
	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> CBRT =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			pow().call(a,ONE_THIRD,b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> cbrt() {
		return CBRT;
	}

	private final Procedure4<Round.Mode,Float64Member,ComplexFloat64Member,ComplexFloat64Member> ROUND =
			new Procedure4<Round.Mode, Float64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, ComplexFloat64Member a, ComplexFloat64Member b) {
			Float64Member tmp = new Float64Member();
			tmp.setV(a.r());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setR(tmp.v());
			tmp.setV(a.i());
			Round.compute(G.DBL, mode, delta, tmp, tmp);
			b.setI(tmp.v());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float64Member,ComplexFloat64Member,ComplexFloat64Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat64Member> ISNAN =
			new Function1<Boolean, ComplexFloat64Member>()
	{
		@Override
		public Boolean call(ComplexFloat64Member a) {
			// true if either component is NaN
			return Double.isNaN(a.r()) || Double.isNaN(a.i());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat64Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat64Member> NAN =
			new Procedure1<ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a) {
			a.setR(Double.NaN);
			a.setI(Double.NaN);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,ComplexFloat64Member> ISINF =
			new Function1<Boolean, ComplexFloat64Member>()
	{
		@Override
		public Boolean call(ComplexFloat64Member a) {
			// true if neither is NaN and one or both is Inf
			return !isNaN().call(a) && (Double.isInfinite(a.r()) || Double.isInfinite(a.i()));
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat64Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat64Member> INF =
			new Procedure1<ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a) {
			a.setR(Double.POSITIVE_INFINITY);
			a.setI(Double.POSITIVE_INFINITY);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64Member> infinite() {
		return INF;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> POW =
			new Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b, ComplexFloat64Member c) {
			ComplexFloat64Member logA = new ComplexFloat64Member();
			ComplexFloat64Member bLogA = new ComplexFloat64Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64Member,ComplexFloat64Member> pow() {
		return POW;
	}

	private final Procedure1<ComplexFloat64Member> RAND =
			new Procedure1<ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR(rng.nextDouble());
			a.setI(rng.nextDouble());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64Member> random() {
		return RAND;
	}

	private Procedure2<ComplexFloat64Member,Float64Member> REAL =
			new Procedure2<ComplexFloat64Member, Float64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, Float64Member b) {
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,Float64Member> real() {
		return REAL;
	}

	private Procedure2<ComplexFloat64Member,ComplexFloat64Member> UNREAL =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			b.setR(0);
			b.setI(a.i());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SINCH =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			Sinch.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SINCHPI =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			Sinchpi.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SINC =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			Sinc.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexFloat64Member,ComplexFloat64Member> SINCPI =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member a, ComplexFloat64Member b) {
			Sincpi.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64Member,ComplexFloat64Member> sincpi() {
		return SINCPI;
	}

	private final Function1<Boolean, ComplexFloat64Member> ISZERO =
			new Function1<Boolean, ComplexFloat64Member>()
	{
		@Override
		public Boolean call(ComplexFloat64Member a) {
			return a.r() == 0 && a.i() == 0;
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64Member, ComplexFloat64Member> scale() {
		return MUL;
	}

}
