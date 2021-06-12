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

// Note: some code here adapted from Imglib2 code written by Barry DeZonia
//
// * ImgLib2: a general-purpose, multidimensional image processing library.
// * Copyright (C) 2009 - 2013 Stephan Preibisch, Tobias Pietzsch, Barry DeZonia,
// * Stephan Saalfeld, Albert Cardona, Curtis Rueden, Christian Dietz, Jean-Yves
// * Tinevez, Johannes Schindelin, Lee Kamentsky, Larry Lindsey, Grant Harris,
// * Mark Hiner, Aivar Grislis, Martin Horn, Nick Perry, Michael Zinsmaier,
// * Steffen Jaensch, Jan Funke, Mark Longair, and Dimiter Prodanov.

package nom.bdezonia.zorbage.type.complex.float128;

import java.lang.Integer;
import java.math.BigDecimal;

import ch.obermuhlner.math.big.BigDecimalMath;
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
import nom.bdezonia.zorbage.type.real.float128.Float128Algebra;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat128Algebra
	implements
		Field<ComplexFloat128Algebra, ComplexFloat128Member>,
		Norm<ComplexFloat128Member, Float128Member>,
		RealConstants<ComplexFloat128Member>,
		ImaginaryConstants<ComplexFloat128Member>,
		Exponential<ComplexFloat128Member>,
		Trigonometric<ComplexFloat128Member>,
		InverseTrigonometric<ComplexFloat128Member>,
		Hyperbolic<ComplexFloat128Member>,
		InverseHyperbolic<ComplexFloat128Member>,
		Roots<ComplexFloat128Member>,
		Power<ComplexFloat128Member>,
		Rounding<Float128Member,ComplexFloat128Member>,
		Infinite<ComplexFloat128Member>,
		NaN<ComplexFloat128Member>,
		Conjugate<ComplexFloat128Member>,
		Random<ComplexFloat128Member>,
		RealUnreal<ComplexFloat128Member,Float128Member>,
		Scale<ComplexFloat128Member,ComplexFloat128Member>,
		ScaleByHighPrec<ComplexFloat128Member>,
		ScaleByRational<ComplexFloat128Member>,
		ScaleByDouble<ComplexFloat128Member>,
		ScaleComponents<ComplexFloat128Member, Float128Member>,
		Tolerance<Float128Member,ComplexFloat128Member>,
		ScaleByOneHalf<ComplexFloat128Member>,
		ScaleByTwo<ComplexFloat128Member>
{
	private static final ComplexFloat128Member ONE = new ComplexFloat128Member(BigDecimal.valueOf(1),BigDecimal.ZERO);
	private static final ComplexFloat128Member TWO = new ComplexFloat128Member(BigDecimal.valueOf(2),BigDecimal.ZERO);
	private static final ComplexFloat128Member MINUS_ONE = new ComplexFloat128Member(BigDecimal.valueOf(-1),BigDecimal.ZERO);
	private static final ComplexFloat128Member _PI = new ComplexFloat128Member(new BigDecimal(HighPrecisionAlgebra.PI_STR.substring(0,40)),BigDecimal.ZERO);
	private static final ComplexFloat128Member _E = new ComplexFloat128Member(new BigDecimal(HighPrecisionAlgebra.E_STR.substring(0,40)),BigDecimal.ZERO);
	private static final ComplexFloat128Member _GAMMA = new ComplexFloat128Member(new BigDecimal(HighPrecisionAlgebra.GAMMA_STR.substring(0,40)),BigDecimal.ZERO);
	private static final ComplexFloat128Member _PHI = new ComplexFloat128Member(new BigDecimal(HighPrecisionAlgebra.PHI_STR.substring(0,40)),BigDecimal.ZERO);
	private static final ComplexFloat128Member ONE_HALF = new ComplexFloat128Member(BigDecimal.ONE.divide(BigDecimal.valueOf(2),Float128Algebra.CONTEXT),BigDecimal.ZERO);
	private static final ComplexFloat128Member ONE_THIRD = new ComplexFloat128Member(BigDecimal.ONE.divide(BigDecimal.valueOf(3),Float128Algebra.CONTEXT), BigDecimal.ZERO);
	private static final ComplexFloat128Member _I = new ComplexFloat128Member(BigDecimal.ZERO,BigDecimal.ONE);
	private static final ComplexFloat128Member I_OVER_TWO = new ComplexFloat128Member(BigDecimal.ZERO,BigDecimal.ONE.divide(BigDecimal.valueOf(2),Float128Algebra.CONTEXT));
	private static final ComplexFloat128Member TWO_I = new ComplexFloat128Member(BigDecimal.ZERO,BigDecimal.valueOf(2));
	private static final ComplexFloat128Member MINUS_I = new ComplexFloat128Member(BigDecimal.ZERO,BigDecimal.valueOf(-1));
	private static final ComplexFloat128Member MINUS_I_OVER_TWO = new ComplexFloat128Member(BigDecimal.ZERO,BigDecimal.ONE.divide(BigDecimal.valueOf(2),Float128Algebra.CONTEXT).negate());

	private static final Float128Member REAL_PI = new Float128Member(new BigDecimal(HighPrecisionAlgebra.PI_STR.substring(0,40)));
	private static final Float128Member REAL_MINUS_PI = new Float128Member(new BigDecimal(HighPrecisionAlgebra.PI_STR.substring(0,40)).multiply(BigDecimal.valueOf(-1)));
	private static final Float128Member REAL_TWO_PI = new Float128Member(new BigDecimal(HighPrecisionAlgebra.PI_STR.substring(0,40)).multiply(BigDecimal.valueOf(2)));
	private static final Float128Member REAL_PI_OVER_TWO = new Float128Member(new BigDecimal(HighPrecisionAlgebra.PI_STR.substring(0,40)).divide(BigDecimal.valueOf(2)));
	private static final Float128Member REAL_MINUS_PI_OVER_TWO = new Float128Member(new BigDecimal(HighPrecisionAlgebra.PI_STR.substring(0,40)).multiply(BigDecimal.valueOf(-1)).divide(BigDecimal.valueOf(2)));

	public ComplexFloat128Algebra() { }
	
	private final Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> MUL =
			new Procedure3<ComplexFloat128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b, ComplexFloat128Member c) {
			
			// for safety must use tmps
			Float128Member r = new Float128Member();
			Float128Member i = new Float128Member();
			Float128Member tmp1 = new Float128Member();
			Float128Member tmp2 = new Float128Member();
			
			G.QUAD.multiply().call(a.r(), b.r(), tmp1);
			G.QUAD.multiply().call(a.i(), b.i(), tmp2);
			G.QUAD.subtract().call(tmp1, tmp2, r);
			
			G.QUAD.multiply().call(a.i(), b.r(), tmp1);
			G.QUAD.multiply().call(a.r(), b.i(), tmp2);
			G.QUAD.add().call(tmp1, tmp2, i);
			
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexFloat128Member,ComplexFloat128Member> POWER =
			new Procedure3<Integer, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(Integer power, ComplexFloat128Member a, ComplexFloat128Member b) {
			
			if (power == 0 && isZero().call(a)) {
				nan().call(b);
				return;
			}
			Float128Member norm = new Float128Member();
			Float128Member rToTheN = new Float128Member();
			Float128Member nTheta = new Float128Member();
			Float128Member r = new Float128Member();
			Float128Member i = new Float128Member();
			Float128Member tmp = new Float128Member();

			G.CQUAD.norm().call(a, norm);
			G.QUAD.power().call(power, norm, rToTheN);
			
			getArgument(a, tmp);
			G.QUAD.scaleByDouble().call((double)power, tmp, nTheta);
			
			G.QUAD.cos().call(nTheta, tmp);
			G.QUAD.multiply().call(rToTheN, tmp, r);
			
			G.QUAD.sin().call(nTheta, tmp);
			G.QUAD.multiply().call(rToTheN, tmp, i);

			b.setR(r);
			b.setI(i);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexFloat128Member,ComplexFloat128Member> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat128Member> ZER =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<ComplexFloat128Member> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> NEG =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member zero = new ComplexFloat128Member();
			subtract().call(zero, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> ADD =
			new Procedure3<ComplexFloat128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b, ComplexFloat128Member c) {
			
			G.QUAD.add().call(a.r(), b.r(), c.r());
			G.QUAD.add().call(a.i(), b.i(), c.i());
		}
	};

	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> SUB =
			new Procedure3<ComplexFloat128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b, ComplexFloat128Member c) {
			
			G.QUAD.subtract().call(a.r(), b.r(), c.r());
			G.QUAD.subtract().call(a.i(), b.i(), c.i());
		}
	};

	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat128Member,ComplexFloat128Member> EQ =
			new Function2<Boolean, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public Boolean call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			return G.QUAD.isEqual().call(a.r(), b.r()) &&
					G.QUAD.isEqual().call(a.i(), b.i());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat128Member,ComplexFloat128Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat128Member,ComplexFloat128Member> NEQ =
			new Function2<Boolean, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public Boolean call(ComplexFloat128Member a, ComplexFloat128Member b) {
		
			return !G.QUAD.isEqual().call(a.r(), b.r()) ||
				!G.QUAD.isEqual().call(a.i(), b.i());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat128Member,ComplexFloat128Member> isNotEqual() {
		return NEQ;
	}

	@Override
	public ComplexFloat128Member construct() {
		
		return new ComplexFloat128Member();
	}

	@Override
	public ComplexFloat128Member construct(ComplexFloat128Member other) {
		
		return new ComplexFloat128Member(other);
	}

	@Override
	public ComplexFloat128Member construct(String s) {
		
		return new ComplexFloat128Member(s);
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ASSIGN =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member from, ComplexFloat128Member to) {
			
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat128Member> UNITY =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128Member> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> INV =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			divide().call(ONE, a, b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> invert() {
		return INV;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> DIVIDE =
			new Procedure3<ComplexFloat128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b, ComplexFloat128Member c) {
			
			// for safety must use tmps
			Float128Member r = new Float128Member();
			Float128Member i = new Float128Member();
			Float128Member mod2 = new Float128Member();
			Float128Member tmp1 = new Float128Member();
			Float128Member tmp2 = new Float128Member();

			G.QUAD.multiply().call(b.r(), b.r(), tmp1);
			G.QUAD.multiply().call(b.i(), b.i(), tmp2);
			G.QUAD.add().call(tmp1, tmp2, mod2);
			
			G.QUAD.multiply().call(a.r(), b.r(), tmp1);
			G.QUAD.multiply().call(a.i(), b.i(), tmp2);
			G.QUAD.add().call(tmp1, tmp2, r);
			G.QUAD.divide().call(r, mod2, r);
			
			G.QUAD.multiply().call(a.i(), b.r(), tmp1);
			G.QUAD.multiply().call(a.r(), b.i(), tmp2);
			G.QUAD.subtract().call(tmp1, tmp2, i);
			G.QUAD.divide().call(i, mod2, i);
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> divide() {
		return DIVIDE;
	}
	
	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> CONJ =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			G.QUAD.assign().call(a.r(), b.r());
			G.QUAD.negate().call(a.i(), b.i());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexFloat128Member,Float128Member> NORM =
			new Procedure2<ComplexFloat128Member, Float128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, Float128Member b) {
			// a hypot()-like implementation that avoids overflow
			Float128Member max = new Float128Member();
			Float128Member abs1 = new Float128Member();
			Float128Member abs2 = new Float128Member();
			G.QUAD.abs().call(a.r(), abs1);
			G.QUAD.abs().call(a.i(), abs2);
			G.QUAD.max().call(abs1, abs2, max);
			if (G.QUAD.isZero().call(max)) {
				b.setPosZero();
			}
			else {
				Float128Member sum = new Float128Member();
				Float128Member tmp = new Float128Member();
				G.QUAD.divide().call(a.r(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.i(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.sqrt().call(sum, tmp);
				G.QUAD.multiply().call(max, tmp, b);
			}
		}
	};

	@Override
	public Procedure2<ComplexFloat128Member,Float128Member> norm() {
		return NORM;
	}

	private final Procedure1<ComplexFloat128Member> PI =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			assign().call(_PI, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128Member> PI() {
		return PI;
	}

	private final Procedure1<ComplexFloat128Member> E =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			assign().call(_E, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128Member> E() {
		return E;
	}

	private final Procedure1<ComplexFloat128Member> GAMMA =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			assign().call(_GAMMA, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128Member> GAMMA() {
		return GAMMA;
	}

	private final Procedure1<ComplexFloat128Member> PHI =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			assign().call(_PHI, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128Member> PHI() {
		return PHI;
	}

	private final Procedure1<ComplexFloat128Member> I =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			assign().call(_I, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat128Member> I() {
		return I;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ASIN =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member ia = new ComplexFloat128Member();
			ComplexFloat128Member aSquared = new ComplexFloat128Member();
			ComplexFloat128Member miniSum = new ComplexFloat128Member();
			ComplexFloat128Member root = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();
			ComplexFloat128Member logSum = new ComplexFloat128Member();
			
			multiply().call(_I, a, ia);
			multiply().call(a, a, aSquared);
			subtract().call(ONE, aSquared, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(ia, root, sum);
			log().call(sum, logSum);
			multiply().call(MINUS_I, logSum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> asin() {
		return ASIN;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ACOS =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member aSquared = new ComplexFloat128Member();
			ComplexFloat128Member miniSum = new ComplexFloat128Member();
			ComplexFloat128Member root = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();
			ComplexFloat128Member logSum = new ComplexFloat128Member();

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, logSum);
			multiply().call(MINUS_I, logSum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> acos() {
		return ACOS;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ATAN =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member ia = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();
			ComplexFloat128Member diff = new ComplexFloat128Member();
			ComplexFloat128Member quotient = new ComplexFloat128Member();
			ComplexFloat128Member log = new ComplexFloat128Member();
			
			multiply().call(_I, a, ia);
			add().call(ONE, ia, sum);
			subtract().call(ONE, ia, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(MINUS_I_OVER_TWO, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> atan() {
		return ATAN;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ACSC =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member recipA = new ComplexFloat128Member();
			
			invert().call(a, recipA);
			asin().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> acsc() {
		return ACSC;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ASEC =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member recipA = new ComplexFloat128Member();
			
			invert().call(a, recipA);
			acos().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> asec() {
		return ASEC;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ACOT =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member ia = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();
			ComplexFloat128Member diff = new ComplexFloat128Member();
			ComplexFloat128Member quotient = new ComplexFloat128Member();
			ComplexFloat128Member log = new ComplexFloat128Member();

			multiply().call(_I, a, ia);
			add().call(ia, ONE, sum);
			subtract().call(ia, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(I_OVER_TWO, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> acot() {
		return ACOT;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ASINH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member aSquared = new ComplexFloat128Member();
			ComplexFloat128Member miniSum = new ComplexFloat128Member();
			ComplexFloat128Member root = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();

			multiply().call(a, a, aSquared);
			add().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> asinh() {
		return ASINH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ACOSH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member aSquared = new ComplexFloat128Member();
			ComplexFloat128Member miniSum = new ComplexFloat128Member();
			ComplexFloat128Member root = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> acosh() {
		return ACOSH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ATANH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member sum = new ComplexFloat128Member();
			ComplexFloat128Member diff = new ComplexFloat128Member();
			ComplexFloat128Member quotient = new ComplexFloat128Member();
			ComplexFloat128Member log = new ComplexFloat128Member();

			add().call(ONE, a, sum);
			subtract().call(ONE, a, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> atanh() {
		return ATANH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ACSCH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member recipA = new ComplexFloat128Member();

			invert().call(a, recipA);
			asinh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> acsch() {
		return ACSCH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ASECH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member recipA = new ComplexFloat128Member();

			invert().call(a, recipA);
			acosh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> asech() {
		return ASECH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> ACOTH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member sum = new ComplexFloat128Member();
			ComplexFloat128Member diff = new ComplexFloat128Member();
			ComplexFloat128Member quotient = new ComplexFloat128Member();
			ComplexFloat128Member log = new ComplexFloat128Member();

			add().call(a, ONE, sum);
			subtract().call(a, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> acoth() {
		return ACOTH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SIN =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member IA = new ComplexFloat128Member();
			ComplexFloat128Member minusIA = new ComplexFloat128Member();
			ComplexFloat128Member expIA = new ComplexFloat128Member();
			ComplexFloat128Member expMinusIA = new ComplexFloat128Member();
			ComplexFloat128Member diff = new ComplexFloat128Member();

			multiply().call(a, _I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			subtract().call(expIA, expMinusIA, diff);
			divide().call(diff, TWO_I, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sin() {
		return SIN;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> COS =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member IA = new ComplexFloat128Member();
			ComplexFloat128Member minusIA = new ComplexFloat128Member();
			ComplexFloat128Member expIA = new ComplexFloat128Member();
			ComplexFloat128Member expMinusIA = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();

			multiply().call(a, _I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			add().call(expIA, expMinusIA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> cos() {
		return COS;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> SINCOS =
			new Procedure3<ComplexFloat128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member s, ComplexFloat128Member c) {
			
			ComplexFloat128Member IA = new ComplexFloat128Member();
			ComplexFloat128Member minusIA = new ComplexFloat128Member();
			ComplexFloat128Member expIA = new ComplexFloat128Member();
			ComplexFloat128Member expMinusIA = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();
			ComplexFloat128Member diff = new ComplexFloat128Member();

			multiply().call(a, _I, IA);
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
	public Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> sinAndCos() {
		return SINCOS;
	}
	
	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> TAN =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member sin = new ComplexFloat128Member();
			ComplexFloat128Member cos = new ComplexFloat128Member();

			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> tan() {
		return TAN;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> CSC =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member sin = new ComplexFloat128Member();

			sin().call(a, sin);
			invert().call(sin, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> csc() {
		return CSC;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SEC =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member cos = new ComplexFloat128Member();

			cos().call(a, cos);
			invert().call(cos, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sec() {
		return SEC;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> COT =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member tan = new ComplexFloat128Member();

			tan().call(a, tan);
			invert().call(tan, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> cot() {
		return COT;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SINH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member expA = new ComplexFloat128Member();
			ComplexFloat128Member minusA = new ComplexFloat128Member();
			ComplexFloat128Member expMinusA = new ComplexFloat128Member();
			ComplexFloat128Member diff = new ComplexFloat128Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			subtract().call(expA, expMinusA, diff);
			divide().call(diff, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> COSH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member expA = new ComplexFloat128Member();
			ComplexFloat128Member minusA = new ComplexFloat128Member();
			ComplexFloat128Member expMinusA = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			add().call(expA, expMinusA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> SINHCOSH =
			new Procedure3<ComplexFloat128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member s, ComplexFloat128Member c) {
			
			ComplexFloat128Member expA = new ComplexFloat128Member();
			ComplexFloat128Member minusA = new ComplexFloat128Member();
			ComplexFloat128Member expMinusA = new ComplexFloat128Member();
			ComplexFloat128Member sum = new ComplexFloat128Member();

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
	public Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> sinhAndCosh() {
		return SINHCOSH;
	}
	
	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> TANH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member sinh = new ComplexFloat128Member();
			ComplexFloat128Member cosh = new ComplexFloat128Member();

			sinhAndCosh().call(a, sinh, cosh);
			divide().call(sinh, cosh, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> CSCH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member sinh = new ComplexFloat128Member();

			sinh().call(a, sinh);
			invert().call(sinh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> csch() {
		return CSCH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SECH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member cosh = new ComplexFloat128Member();

			cosh().call(a, cosh);
			invert().call(cosh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sech() {
		return SECH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> COTH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member tanh = new ComplexFloat128Member();

			tanh().call(a, tanh);
			invert().call(tanh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> coth() {
		return COTH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> EXP =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			Float128Member constant = new Float128Member();
			Float128Member tmp = new Float128Member();

			G.QUAD.exp().call(a.r(), constant);
			
			G.QUAD.cos().call(a.i(), tmp);;
			G.QUAD.multiply().call(constant, tmp, b.r());
			
			G.QUAD.sin().call(a.i(), tmp);;
			G.QUAD.multiply().call(constant, tmp, b.i());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> exp() {
		return EXP;
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> EXPM1 =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member tmp = new ComplexFloat128Member();
			exp().call(a, tmp);
			subtract().call(tmp, ONE, b);
		}
	};
	
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> expm1() {
		return EXPM1;
	}
	
	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> LOG =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
		
			Float128Member modulus = new Float128Member();
			Float128Member radius = new Float128Member();
			Float128Member argument = new Float128Member();
			
			G.CQUAD.norm().call(a, modulus);
			G.QUAD.log().call(modulus, radius);
			
			getArgument(a, argument);
			makePrincipalArgument(argument);
			
			b.setR( radius );
			b.setI( argument );
		}
	};

	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> log() {
		return LOG;
	}
	
	private void getArgument(ComplexFloat128Member a, Float128Member argument) {

		Float128Member x = a.r();
		Float128Member y = a.i();
		
		if (G.QUAD.isZero().call(x)) {
			int signum = G.QUAD.signum().call(y);
			if (signum > 0)
				argument.set(REAL_PI_OVER_TWO);
			else if (signum < 0)
				argument.set(REAL_MINUS_PI_OVER_TWO);
			else // y == 0 : theta indeterminate
				argument.setNan();
		}
		else if (G.QUAD.isZero().call(y)) {
			int signum = G.QUAD.signum().call(x);
			if (signum > 0)
				argument.setPosZero();
			else // (x < 0)
				argument.set(REAL_PI);
		}
		else // x && y both != 0
			argument.setV(BigDecimalMath.atan2(y.v(),x.v(),Float128Algebra.CONTEXT));
	}
	
	private void makePrincipalArgument(Float128Member angle) {
		
		while (G.QUAD.isLess().call(angle, REAL_MINUS_PI)) {
			G.QUAD.add().call(angle, REAL_TWO_PI, angle);
		}
		
		while (G.QUAD.isGreaterEqual().call(angle, REAL_PI)) {
			G.QUAD.subtract().call(angle, REAL_TWO_PI, angle);
		}
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> LOG1P =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ComplexFloat128Member tmp = new ComplexFloat128Member();
			add().call(a, ONE, tmp);
			log().call(tmp, b);
		}
	};
	
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> log1p() {
		return LOG1P;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SQRT =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sqrt() {
		return SQRT;
	}

	// TODO: make an accurate implementation
	
	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> CBRT =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> cbrt() {
		return CBRT;
	}

	private final Procedure4<Round.Mode,Float128Member,ComplexFloat128Member,ComplexFloat128Member> ROUND =
			new Procedure4<Round.Mode, Float128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, ComplexFloat128Member a, ComplexFloat128Member b) {
			
			G.QUAD.round().call(mode, delta, a.r(), b.r());
			G.QUAD.round().call(mode, delta, a.i(), b.i());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float128Member,ComplexFloat128Member,ComplexFloat128Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,ComplexFloat128Member> ISNAN =
			new Function1<Boolean, ComplexFloat128Member>()
	{
		@Override
		public Boolean call(ComplexFloat128Member a) {
			
			// true if either component is NaN
			return G.QUAD.isNaN().call(a.r()) || G.QUAD.isNaN().call(a.i());
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat128Member> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat128Member> NAN =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			G.QUAD.nan().call(a.r());
			G.QUAD.nan().call(a.i());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,ComplexFloat128Member> ISINF =
			new Function1<Boolean, ComplexFloat128Member>()
	{
		@Override
		public Boolean call(ComplexFloat128Member a) {
			
			// true if neither is NaN and one or both is Inf
			return !isNaN().call(a) &&
					(G.QUAD.isInfinite().call(a.r()) ||
						G.QUAD.isInfinite().call(a.i()));
		}
	};
	
	@Override
	public Function1<Boolean,ComplexFloat128Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat128Member> INF =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			G.QUAD.infinite().call(a.r());
			G.QUAD.infinite().call(a.i());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128Member> infinite() {
		return INF;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> POW =
			new Procedure3<ComplexFloat128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b, ComplexFloat128Member c) {
			
			ComplexFloat128Member logA = new ComplexFloat128Member();
			ComplexFloat128Member bLogA = new ComplexFloat128Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128Member,ComplexFloat128Member> pow() {
		return POW;
	}

	private final Procedure1<ComplexFloat128Member> RAND =
			new Procedure1<ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a) {
			
			G.QUAD.random().call(a.r());
			G.QUAD.random().call(a.i());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128Member> random() {
		return RAND;
	}

	private final Procedure2<ComplexFloat128Member,Float128Member> REAL =
			new Procedure2<ComplexFloat128Member, Float128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, Float128Member b) {
			
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,Float128Member> real() {
		return REAL;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> UNREAL =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			G.QUAD.zero().call(b.r());
			G.QUAD.assign().call(a.i(), b.i());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SINCH =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			Sinch.compute(G.CQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SINCHPI =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			Sinchpi.compute(G.CQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SINC =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			Sinc.compute(G.CQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexFloat128Member,ComplexFloat128Member> SINCPI =
			new Procedure2<ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(ComplexFloat128Member a, ComplexFloat128Member b) {
			
			Sincpi.compute(G.CQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128Member,ComplexFloat128Member> sincpi() {
		return SINCPI;
	}

	private final Function1<Boolean, ComplexFloat128Member> ISZERO =
			new Function1<Boolean, ComplexFloat128Member>()
	{
		@Override
		public Boolean call(ComplexFloat128Member a) {
			
			return G.QUAD.isZero().call(a.r()) && G.QUAD.isZero().call(a.i());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128Member, ComplexFloat128Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat128Member, ComplexFloat128Member> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat128Member b, ComplexFloat128Member c) {
			
			G.QUAD.scaleByHighPrec().call(a, b.r(), c.r());
			G.QUAD.scaleByHighPrec().call(a, b.i(), c.i());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat128Member, ComplexFloat128Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexFloat128Member, ComplexFloat128Member> SBR =
			new Procedure3<RationalMember, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat128Member b, ComplexFloat128Member c) {
			
			G.QUAD.scaleByRational().call(a, b.r(), c.r());
			G.QUAD.scaleByRational().call(a, b.i(), c.i());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat128Member, ComplexFloat128Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat128Member, ComplexFloat128Member> SBD =
			new Procedure3<Double, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(Double a, ComplexFloat128Member b, ComplexFloat128Member c) {
			
			G.QUAD.scaleByDouble().call(a, b.r(), c.r());
			G.QUAD.scaleByDouble().call(a, b.i(), c.i());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat128Member, ComplexFloat128Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float128Member, ComplexFloat128Member, ComplexFloat128Member> SC =
			new Procedure3<Float128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(Float128Member factor, ComplexFloat128Member a, ComplexFloat128Member b) {
			
			G.QUAD.multiply().call(factor, a.r(), b.r());
			G.QUAD.multiply().call(factor, a.i(), b.i());
		}
	};

	@Override
	public Procedure3<Float128Member, ComplexFloat128Member, ComplexFloat128Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float128Member, ComplexFloat128Member, ComplexFloat128Member> WITHIN =
			new Function3<Boolean, Float128Member, ComplexFloat128Member, ComplexFloat128Member>()
	{
		
		@Override
		public Boolean call(Float128Member tol, ComplexFloat128Member a, ComplexFloat128Member b) {
			
			return ComplexNumberWithin.compute(G.QUAD, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float128Member, ComplexFloat128Member, ComplexFloat128Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, ComplexFloat128Member, ComplexFloat128Member> STWO =
			new Procedure3<java.lang.Integer, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ScaleHelper.compute(G.CQUAD, G.CQUAD, TWO, numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, ComplexFloat128Member, ComplexFloat128Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, ComplexFloat128Member, ComplexFloat128Member> SHALF =
			new Procedure3<java.lang.Integer, ComplexFloat128Member, ComplexFloat128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, ComplexFloat128Member a, ComplexFloat128Member b) {
			
			ScaleHelper.compute(G.CQUAD, G.CQUAD, ONE_HALF, numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, ComplexFloat128Member, ComplexFloat128Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, ComplexFloat128Member> ISUNITY =
			new Function1<Boolean, ComplexFloat128Member>()
	{
		@Override
		public Boolean call(ComplexFloat128Member a) {
			
			return G.CQUAD.isEqual().call(a, ONE);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat128Member> isUnity() {
		return ISUNITY;
	}
}
