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

package nom.bdezonia.zorbage.type.complex.highprec;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.ComplexNumberWithin;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.Sinc;
import nom.bdezonia.zorbage.algorithm.Sinch;
import nom.bdezonia.zorbage.algorithm.Sinchpi;
import nom.bdezonia.zorbage.algorithm.Sincpi;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexHighPrecisionAlgebra
	implements
		Field<ComplexHighPrecisionAlgebra, ComplexHighPrecisionMember>,
		Norm<ComplexHighPrecisionMember, HighPrecisionMember>,
		RealConstants<ComplexHighPrecisionMember>,
		ImaginaryConstants<ComplexHighPrecisionMember>,
		Exponential<ComplexHighPrecisionMember>,
		Trigonometric<ComplexHighPrecisionMember>,
		InverseTrigonometric<ComplexHighPrecisionMember>,
		Hyperbolic<ComplexHighPrecisionMember>,
		InverseHyperbolic<ComplexHighPrecisionMember>,
		Roots<ComplexHighPrecisionMember>,
		Power<ComplexHighPrecisionMember>,
		Conjugate<ComplexHighPrecisionMember>,
		RealUnreal<ComplexHighPrecisionMember,HighPrecisionMember>,
		Scale<ComplexHighPrecisionMember,ComplexHighPrecisionMember>,
		ScaleByHighPrec<ComplexHighPrecisionMember>,
		ScaleByRational<ComplexHighPrecisionMember>,
		ScaleByDouble<ComplexHighPrecisionMember>,
		ScaleComponents<ComplexHighPrecisionMember, HighPrecisionMember>,
		Tolerance<HighPrecisionMember,ComplexHighPrecisionMember>,
		ScaleByOneHalf<ComplexHighPrecisionMember>,
		ScaleByTwo<ComplexHighPrecisionMember>,
		ConstructibleFromBigDecimal<ComplexHighPrecisionMember>,
		ConstructibleFromBigInteger<ComplexHighPrecisionMember>,
		ConstructibleFromDouble<ComplexHighPrecisionMember>,
		ConstructibleFromLong<ComplexHighPrecisionMember>
{
	private static final ComplexHighPrecisionMember ONE = new ComplexHighPrecisionMember(BigDecimal.ONE,BigDecimal.ZERO);
	private static final ComplexHighPrecisionMember TWO = new ComplexHighPrecisionMember(BigDecimal.valueOf(2),BigDecimal.ZERO);
	private static final ComplexHighPrecisionMember MINUS_ONE = new ComplexHighPrecisionMember(BigDecimal.valueOf(-1),BigDecimal.ZERO);
	private static final ComplexHighPrecisionMember I = new ComplexHighPrecisionMember(BigDecimal.ZERO, BigDecimal.ONE);
	private static final ComplexHighPrecisionMember TWO_I = new ComplexHighPrecisionMember(BigDecimal.ZERO,BigDecimal.valueOf(2));
	private static final ComplexHighPrecisionMember MINUS_I = new ComplexHighPrecisionMember(BigDecimal.ZERO,BigDecimal.valueOf(-1));

	@Override
	public String typeDescription() {
		return "Arbitrary precision complex number";
	}

	public ComplexHighPrecisionAlgebra() { }

	@Override
	public ComplexHighPrecisionMember construct() {
		return new ComplexHighPrecisionMember();
	}

	@Override
	public ComplexHighPrecisionMember construct(ComplexHighPrecisionMember other) {
		return new ComplexHighPrecisionMember(other);
	}

	@Override
	public ComplexHighPrecisionMember construct(String s) {
		return new ComplexHighPrecisionMember(s);
	}

	@Override
	public ComplexHighPrecisionMember construct(BigDecimal... vals) {
		return new ComplexHighPrecisionMember(vals);
	}

	@Override
	public ComplexHighPrecisionMember construct(BigInteger... vals) {
		return new ComplexHighPrecisionMember(vals);
	}

	@Override
	public ComplexHighPrecisionMember construct(double... vals) {
		return new ComplexHighPrecisionMember(vals);
	}

	@Override
	public ComplexHighPrecisionMember construct(long... vals) {
		return new ComplexHighPrecisionMember(vals);
	}
	
	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> MUL =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			// for safety must use tmps
			BigDecimal r = a.r().multiply(b.r()).subtract(a.i().multiply(b.i()));
			BigDecimal i = a.i().multiply(b.r()).subtract(a.r().multiply(b.i()));
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,ComplexHighPrecisionMember,ComplexHighPrecisionMember> POWER =
			new Procedure3<Integer, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(Integer power, ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			if (power == 0 && isZero().call(a)) {
				throw new IllegalArgumentException("0^0 is not a number");
			}
			BigDecimal r = getModulus(a);
			BigDecimal rToTheN = BigDecimalMath.pow(r, power, HighPrecisionAlgebra.getContext());
			BigDecimal nTheta = BigDecimal.valueOf(power).multiply(getArgument(a));
			b.setR( rToTheN.multiply(BigDecimalMath.cos(nTheta, HighPrecisionAlgebra.getContext())) );
			b.setI( rToTheN.multiply(BigDecimalMath.sin(nTheta, HighPrecisionAlgebra.getContext())) );
		}
	};

	@Override
	public Procedure3<java.lang.Integer,ComplexHighPrecisionMember,ComplexHighPrecisionMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexHighPrecisionMember> ZER =
			new Procedure1<ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> NEG =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			b.setR( a.r().negate() );
			b.setI( a.i().negate() );
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> ADD =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			c.setR( a.r().add(b.r()) );
			c.setI( a.i().add(b.i()) );
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> SUB =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			c.setR( a.r().subtract(b.r()) );
			c.setI( a.i().subtract(b.i()) );
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexHighPrecisionMember,ComplexHighPrecisionMember> EQ =
			new Function2<Boolean, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			return a.r().equals(b.r()) && a.i().equals(b.i());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexHighPrecisionMember,ComplexHighPrecisionMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexHighPrecisionMember,ComplexHighPrecisionMember> NEQ =
			new Function2<Boolean, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			return !a.r().equals(b.r()) || !a.i().equals(b.i());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexHighPrecisionMember,ComplexHighPrecisionMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ASSIGN =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember from, ComplexHighPrecisionMember to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexHighPrecisionMember> UNITY =
			new Procedure1<ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionMember> unity() {
		return UNITY;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> INV =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			divide().call(ONE, a, b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> invert() {
		return INV;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> DIVIDE =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			// for safety must use tmps
			BigDecimal mod2 = getModulus2(b);
			BigDecimal r = a.r().multiply(b.r()).add(a.i().multiply(b.i()));
			BigDecimal i = a.i().multiply(b.r()).subtract(a.r().multiply(b.i()));
			r = r.divide(mod2, HighPrecisionAlgebra.getContext());
			i = i.divide(mod2, HighPrecisionAlgebra.getContext());
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> divide() {
		return DIVIDE;
	}
	
	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> CONJ =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			b.setR( a.r() );
			b.setI( a.i().negate() );
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> conjugate() {
		return CONJ;
	}

	private final Procedure2<ComplexHighPrecisionMember,HighPrecisionMember> NORM =
			new Procedure2<ComplexHighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, HighPrecisionMember b) {
			b.setV( getModulus(a) );
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure1<ComplexHighPrecisionMember> PI_ =
			new Procedure1<ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a) {
			HighPrecisionMember pi = G.HP.construct();
			G.HP.PI().call(pi);
			a.setR(pi.v());
			a.setI(BigDecimal.ZERO);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMember> PI() {
		return PI_;
	}

	private final Procedure1<ComplexHighPrecisionMember> E_ =
			new Procedure1<ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a) {
			HighPrecisionMember e = G.HP.construct();
			G.HP.E().call(e);
			a.setR(e.v());
			a.setI(BigDecimal.ZERO);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMember> E() {
		return E_;
	}

	private final Procedure1<ComplexHighPrecisionMember> GAMMA_ =
			new Procedure1<ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a) {
			HighPrecisionMember gamma = G.HP.construct();
			G.HP.GAMMA().call(gamma);
			a.setR(gamma.v());
			a.setI(BigDecimal.ZERO);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMember> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<ComplexHighPrecisionMember> PHI_ =
			new Procedure1<ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a) {
			HighPrecisionMember phi = G.HP.construct();
			G.HP.PHI().call(phi);
			a.setR(phi.v());
			a.setI(BigDecimal.ZERO);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMember> PHI() {
		return PHI_;
	}

	private final Procedure1<ComplexHighPrecisionMember> I_ =
			new Procedure1<ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a) {
			assign().call(I, a);
		}
	};

	@Override
	public Procedure1<ComplexHighPrecisionMember> I() {
		return I_;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ASIN =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember ia = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember aSquared = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember miniSum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember root = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember logSum = new ComplexHighPrecisionMember();
			BigDecimal one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember ONE_HALF = new ComplexHighPrecisionMember(one_half, BigDecimal.ZERO);
			
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
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> asin() {
		return ASIN;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ACOS =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember aSquared = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember miniSum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember root = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember logSum = new ComplexHighPrecisionMember();
			BigDecimal one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember ONE_HALF = new ComplexHighPrecisionMember(one_half, BigDecimal.ZERO);

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, logSum);
			multiply().call(MINUS_I, logSum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> acos() {
		return ACOS;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ATAN =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember ia = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember diff = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember quotient = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember log = new ComplexHighPrecisionMember();
			BigDecimal minus_one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(-2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember MINUS_I_OVER_TWO = new ComplexHighPrecisionMember(BigDecimal.ZERO, minus_one_half);
			
			multiply().call(I, a, ia);
			add().call(ONE, ia, sum);
			subtract().call(ONE, ia, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(MINUS_I_OVER_TWO, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> atan() {
		return ATAN;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ACSC =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember recipA = new ComplexHighPrecisionMember();
			
			invert().call(a, recipA);
			asin().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> acsc() {
		return ACSC;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ASEC =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember recipA = new ComplexHighPrecisionMember();
			
			invert().call(a, recipA);
			acos().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> asec() {
		return ASEC;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ACOT =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember ia = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember diff = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember quotient = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember log = new ComplexHighPrecisionMember();
			BigDecimal one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember I_OVER_TWO = new ComplexHighPrecisionMember(BigDecimal.ZERO, one_half);

			multiply().call(I, a, ia);
			add().call(ia, ONE, sum);
			subtract().call(ia, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(I_OVER_TWO, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> acot() {
		return ACOT;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ASINH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember aSquared = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember miniSum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember root = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			BigDecimal one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember ONE_HALF = new ComplexHighPrecisionMember(one_half, BigDecimal.ZERO);

			multiply().call(a, a, aSquared);
			add().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> asinh() {
		return ASINH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ACOSH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember aSquared = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember miniSum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember root = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			BigDecimal one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember ONE_HALF = new ComplexHighPrecisionMember(one_half, BigDecimal.ZERO);

			multiply().call(a, a, aSquared);
			subtract().call(aSquared, ONE, miniSum);
			pow().call(miniSum, ONE_HALF, root);
			add().call(a, root, sum);
			log().call(sum, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> acosh() {
		return ACOSH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ATANH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember diff = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember quotient = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember log = new ComplexHighPrecisionMember();
			BigDecimal one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember ONE_HALF = new ComplexHighPrecisionMember(one_half, BigDecimal.ZERO);

			add().call(ONE, a, sum);
			subtract().call(ONE, a, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> atanh() {
		return ATANH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ACSCH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember recipA = new ComplexHighPrecisionMember();

			invert().call(a, recipA);
			asinh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> acsch() {
		return ACSCH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ASECH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember recipA = new ComplexHighPrecisionMember();

			invert().call(a, recipA);
			acosh().call(recipA, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> asech() {
		return ASECH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> ACOTH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember diff = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember quotient = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember log = new ComplexHighPrecisionMember();
			BigDecimal one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember ONE_HALF = new ComplexHighPrecisionMember(one_half, BigDecimal.ZERO);

			add().call(a, ONE, sum);
			subtract().call(a, ONE, diff);
			divide().call(sum, diff, quotient);
			log().call(quotient, log);
			multiply().call(ONE_HALF, log, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> acoth() {
		return ACOTH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SIN =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember IA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember minusIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expMinusIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember diff = new ComplexHighPrecisionMember();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			subtract().call(expIA, expMinusIA, diff);
			divide().call(diff, TWO_I, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sin() {
		return SIN;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> COS =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember IA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember minusIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expMinusIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();

			multiply().call(a, I, IA);
			multiply().call(a, MINUS_I, minusIA);
			exp().call(IA, expIA);
			exp().call(minusIA, expMinusIA);
			
			add().call(expIA, expMinusIA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> cos() {
		return COS;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> SINCOS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember s, ComplexHighPrecisionMember c) {
			ComplexHighPrecisionMember IA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember minusIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expMinusIA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember diff = new ComplexHighPrecisionMember();

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
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> sinAndCos() {
		return SINCOS;
	}
	
	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> TAN =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember sin = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember cos = new ComplexHighPrecisionMember();

			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> tan() {
		return TAN;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> CSC =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember sin = new ComplexHighPrecisionMember();

			sin().call(a, sin);
			invert().call(sin, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> csc() {
		return CSC;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SEC =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember cos = new ComplexHighPrecisionMember();

			cos().call(a, cos);
			invert().call(cos, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sec() {
		return SEC;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> COT =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember tan = new ComplexHighPrecisionMember();

			tan().call(a, tan);
			invert().call(tan, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> cot() {
		return COT;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SINH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember expA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember minusA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expMinusA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember diff = new ComplexHighPrecisionMember();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			subtract().call(expA, expMinusA, diff);
			divide().call(diff, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sinh() {
		return SINH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> COSH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember expA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember minusA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expMinusA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();

			exp().call(a, expA);
			multiply().call(a, MINUS_ONE, minusA);
			exp().call(minusA, expMinusA);
			
			add().call(expA, expMinusA, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> cosh() {
		return COSH;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> SINHCOSH =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember s, ComplexHighPrecisionMember c) {
			ComplexHighPrecisionMember expA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember minusA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember expMinusA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();

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
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> sinhAndCosh() {
		return SINHCOSH;
	}
	
	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> TANH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember sinh = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember cosh = new ComplexHighPrecisionMember();

			sinhAndCosh().call(a, sinh, cosh);
			divide().call(sinh, cosh, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> tanh() {
		return TANH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> CSCH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember sinh = new ComplexHighPrecisionMember();

			sinh().call(a, sinh);
			invert().call(sinh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> csch() {
		return CSCH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SECH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember cosh = new ComplexHighPrecisionMember();

			cosh().call(a, cosh);
			invert().call(cosh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sech() {
		return SECH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> COTH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember tanh = new ComplexHighPrecisionMember();

			tanh().call(a, tanh);
			invert().call(tanh, b);
		}
	};
	
	//@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> coth() {
		return COTH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> EXP =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			BigDecimal constant = BigDecimalMath.exp(a.r(), HighPrecisionAlgebra.getContext());
			b.setR( constant.multiply(BigDecimalMath.cos(a.i(), HighPrecisionAlgebra.getContext())) );
			b.setI( constant.multiply(BigDecimalMath.sin(a.i(), HighPrecisionAlgebra.getContext())) );
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> exp() {
		return EXP;
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> EXPM1 =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember tmp = new ComplexHighPrecisionMember();
			exp().call(a, tmp);
			subtract().call(tmp, ONE, b);
		}
	};
	
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> expm1() {
		return EXPM1;
	}
	
	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> LOG =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			BigDecimal modulus = getModulus(a);
			BigDecimal argument = getArgument(a);
			b.setR( BigDecimalMath.log(modulus, HighPrecisionAlgebra.getContext()) );
			b.setI( getPrincipalArgument(argument) );
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> log() {
		return LOG;
	}
	
	private BigDecimal getModulus2(ComplexHighPrecisionMember a) {
		return a.r().multiply(a.r()).add(a.i().multiply(a.i()));
	}

	private BigDecimal getModulus(ComplexHighPrecisionMember a) {
		return BigDecimalMath.sqrt(getModulus2(a), HighPrecisionAlgebra.getContext());
	}

	private BigDecimal getArgument(ComplexHighPrecisionMember a) {
		HighPrecisionMember pi = G.HP.construct();
		HighPrecisionMember minus_pi = G.HP.construct();
		G.HP.PI().call(pi);
		G.HP.negate().call(pi, minus_pi);
		BigDecimal x = a.r();
		BigDecimal y = a.i();
		BigDecimal theta;
		if (x.equals(BigDecimal.ZERO)) {
			if (y.compareTo(BigDecimal.ZERO) > 0)
				theta = pi.v().divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			else if (y.compareTo(BigDecimal.ZERO) < 0)
				theta = minus_pi.v().divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			else // y == 0 : theta indeterminate
				throw new IllegalArgumentException("HighPrec can't use NaN value");
		}
		else if (y.equals(BigDecimal.ZERO)) {
			if (x.compareTo(BigDecimal.ZERO) > 0)
				theta = BigDecimal.ZERO;
			else // (x < 0)
				theta = pi.v();
		}
		else // x && y both != 0
			theta = BigDecimalMath.atan2(y,x,HighPrecisionAlgebra.getContext());
		
		return theta;
	}
	
	private BigDecimal getPrincipalArgument(BigDecimal angle) {
		HighPrecisionMember pi = G.HP.construct();
		HighPrecisionMember minus_pi = G.HP.construct();
		G.HP.PI().call(pi);
		G.HP.negate().call(pi, minus_pi);
		BigDecimal arg = angle;
		while (arg.compareTo(minus_pi.v()) <= 0) {
			arg = arg.add(pi.v()).add(pi.v());
		}
		while (arg.compareTo(pi.v()) > 0) {
			arg = arg.subtract(pi.v()).subtract(pi.v());
		}
		return arg;
	}

	// TODO: make an accurate implementation

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> LOG1P =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ComplexHighPrecisionMember tmp = new ComplexHighPrecisionMember();
			add().call(a, ONE, tmp);
			log().call(tmp, b);
		}
	};
	
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> log1p() {
		return LOG1P;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SQRT =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			BigDecimal one_half = BigDecimal.ONE.divide(BigDecimal.valueOf(2), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember ONE_HALF = new ComplexHighPrecisionMember(one_half, BigDecimal.ZERO);
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sqrt() {
		return SQRT;
	}

	// TODO: make an accurate implementation
	
	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> CBRT =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			BigDecimal one_third = BigDecimal.ONE.divide(BigDecimal.valueOf(3), HighPrecisionAlgebra.getContext());
			ComplexHighPrecisionMember ONE_THIRD = new ComplexHighPrecisionMember(one_third, BigDecimal.ZERO);
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> cbrt() {
		return CBRT;
	}
	
	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> POW =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			ComplexHighPrecisionMember logA = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember bLogA = new ComplexHighPrecisionMember();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionMember,ComplexHighPrecisionMember> pow() {
		return POW;
	}

	private final Procedure2<ComplexHighPrecisionMember,HighPrecisionMember> REAL =
			new Procedure2<ComplexHighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, HighPrecisionMember b) {
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,HighPrecisionMember> real() {
		return REAL;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> UNREAL =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			b.setR(BigDecimal.ZERO);
			b.setI(a.i());
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> unreal() {
		return UNREAL;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SINCH =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			Sinch.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sinch() {
		return SINCH;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SINCHPI =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			Sinchpi.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SINC =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			Sinc.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sinc() {
		return SINC;
	}

	private final Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> SINCPI =
			new Procedure2<ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			Sincpi.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionMember,ComplexHighPrecisionMember> sincpi() {
		return SINCPI;
	}

	private final Function1<Boolean, ComplexHighPrecisionMember> ISZERO =
			new Function1<Boolean, ComplexHighPrecisionMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionMember a) {
			return a.r().equals(BigDecimal.ZERO) && a.i().equals(BigDecimal.ZERO);
		}
	};

	@Override
	public Function1<Boolean, ComplexHighPrecisionMember> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			BigDecimal tmp;
			tmp = a.v().multiply(b.r());
			c.setR(tmp);
			tmp = a.v().multiply(b.i());
			c.setI(tmp);
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> SBR =
			new Procedure3<RationalMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(RationalMember a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			BigDecimal n = new BigDecimal(a.n());
			BigDecimal d = new BigDecimal(a.d());
			BigDecimal tmp;
			tmp = b.r();
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setR(tmp);
			tmp = b.i();
			tmp = tmp.multiply(n);
			tmp = tmp.divide(d, HighPrecisionAlgebra.getContext());
			c.setI(tmp);
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexHighPrecisionMember, ComplexHighPrecisionMember> SBD =
			new Procedure3<Double, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(Double a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			BigDecimal d = BigDecimal.valueOf(a);
			BigDecimal tmp;
			tmp = b.r();
			tmp = tmp.multiply(d);
			c.setR(tmp);
			tmp = b.i();
			tmp = tmp.multiply(d);
			c.setI(tmp);
		}
	};

	@Override
	public Procedure3<Double, ComplexHighPrecisionMember, ComplexHighPrecisionMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> SC =
			new Procedure3<HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexHighPrecisionMember b, ComplexHighPrecisionMember c) {
			c.setR(a.v().multiply(b.r()));
			c.setI(a.v().multiply(b.i()));
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		
		@Override
		public Boolean call(HighPrecisionMember tol, ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			return ComplexNumberWithin.compute(G.HP, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionMember, ComplexHighPrecisionMember> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, ComplexHighPrecisionMember, ComplexHighPrecisionMember> STWO =
			new Procedure3<java.lang.Integer, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ScaleHelper.compute(G.CHP, G.CHP, new ComplexHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, ComplexHighPrecisionMember, ComplexHighPrecisionMember> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, ComplexHighPrecisionMember, ComplexHighPrecisionMember> SHALF =
			new Procedure3<java.lang.Integer, ComplexHighPrecisionMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, ComplexHighPrecisionMember a, ComplexHighPrecisionMember b) {
			ScaleHelper.compute(G.CHP, G.CHP, new ComplexHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, ComplexHighPrecisionMember, ComplexHighPrecisionMember> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, ComplexHighPrecisionMember> ISUNITY =
			new Function1<Boolean, ComplexHighPrecisionMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionMember a) {
			return G.CHP.isEqual().call(a, ONE);
		}
	};

	@Override
	public Function1<Boolean, ComplexHighPrecisionMember> isUnity() {
		return ISUNITY;
	}
}
