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
package nom.bdezonia.zorbage.type.octonion.highprec;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.OctonionNumberWithin;
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
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
//TODO: is it really a skewfield or something else?  Multiplication is not associative
public class OctonionHighPrecisionAlgebra
	implements
		SkewField<OctonionHighPrecisionAlgebra, OctonionHighPrecisionMember>,
		Conjugate<OctonionHighPrecisionMember>,
		Norm<OctonionHighPrecisionMember,HighPrecisionMember>,
		RealConstants<OctonionHighPrecisionMember>,
		ImaginaryConstants<OctonionHighPrecisionMember>,
		QuaternionConstants<OctonionHighPrecisionMember>,
		OctonionConstants<OctonionHighPrecisionMember>,
		Exponential<OctonionHighPrecisionMember>,
		Trigonometric<OctonionHighPrecisionMember>,
		Hyperbolic<OctonionHighPrecisionMember>,
		Power<OctonionHighPrecisionMember>,
		Roots<OctonionHighPrecisionMember>,
		RealUnreal<OctonionHighPrecisionMember,HighPrecisionMember>,
		Scale<OctonionHighPrecisionMember, OctonionHighPrecisionMember>,
		ScaleByHighPrec<OctonionHighPrecisionMember>,
		ScaleByRational<OctonionHighPrecisionMember>,
		ScaleByDouble<OctonionHighPrecisionMember>,
		ScaleComponents<OctonionHighPrecisionMember, HighPrecisionMember>,
		Tolerance<HighPrecisionMember,OctonionHighPrecisionMember>,
		ScaleByOneHalf<OctonionHighPrecisionMember>,
		ScaleByTwo<OctonionHighPrecisionMember>,
		ConstructibleFromBytes<OctonionHighPrecisionMember>,
		ConstructibleFromShorts<OctonionHighPrecisionMember>,
		ConstructibleFromInts<OctonionHighPrecisionMember>,
		ConstructibleFromLongs<OctonionHighPrecisionMember>,
		ConstructibleFromFloats<OctonionHighPrecisionMember>,
		ConstructibleFromDoubles<OctonionHighPrecisionMember>,
		ConstructibleFromBigIntegers<OctonionHighPrecisionMember>,
		ConstructibleFromBigDecimals<OctonionHighPrecisionMember>,
		ExactlyConstructibleFromBytes<OctonionHighPrecisionMember>,
		ExactlyConstructibleFromShorts<OctonionHighPrecisionMember>,
		ExactlyConstructibleFromInts<OctonionHighPrecisionMember>,
		ExactlyConstructibleFromLongs<OctonionHighPrecisionMember>,
		ExactlyConstructibleFromFloats<OctonionHighPrecisionMember>,
		ExactlyConstructibleFromDoubles<OctonionHighPrecisionMember>,
		ExactlyConstructibleFromBigIntegers<OctonionHighPrecisionMember>,
		ExactlyConstructibleFromBigDecimals<OctonionHighPrecisionMember>
{
	private static final OctonionHighPrecisionMember ZERO = new OctonionHighPrecisionMember();
	private static final OctonionHighPrecisionMember ONE = new OctonionHighPrecisionMember(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionHighPrecisionMember TWO = new OctonionHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionHighPrecisionMember I = new OctonionHighPrecisionMember(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionHighPrecisionMember J = new OctonionHighPrecisionMember(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionHighPrecisionMember K = new OctonionHighPrecisionMember(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionHighPrecisionMember L = new OctonionHighPrecisionMember(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionHighPrecisionMember I0 = new OctonionHighPrecisionMember(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionHighPrecisionMember J0 = new OctonionHighPrecisionMember(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO);
	private static final OctonionHighPrecisionMember K0 = new OctonionHighPrecisionMember(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE);

	private static MathContext context = HighPrecisionAlgebra.getContext();

	@Override
	public String typeDescription() {
		return "Arbitrary precision octonion number";
	}

	public OctonionHighPrecisionAlgebra() { }

	@Override
	public OctonionHighPrecisionMember construct() {
		return new OctonionHighPrecisionMember();
	}

	@Override
	public OctonionHighPrecisionMember construct(OctonionHighPrecisionMember other) {
		return new OctonionHighPrecisionMember(other);
	}

	@Override
	public OctonionHighPrecisionMember construct(String s) {
		return new OctonionHighPrecisionMember(s);
	}
	
	private final Procedure1<OctonionHighPrecisionMember> UNITY =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> unity() {
		return UNITY;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> MUL =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			/*
			 
			×	r	i	j	k	l	i0	j0	k0
				==============================
			r	r	i	j	k	l	i0	j0	k0
			i	i	−r	k	−j	i0	−l	−k0	j0
			j	j	−k	−r	i	j0	k0	−l	−i0
			k	k	j	−i	−r	k0	−j0	i0	−l
			l	l	−i0	−j0	−k0	−r	i	j	k
			i0	i0	l	−k0	j0	−i	−r	−k	j
			j0	j0	k0	l	−i0	−j	k	−r	−i
			k0	k0	−j0	i0	l	−k	−j	i	−r

		 */
			OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember(ZERO);
	
			// r * r = r
			tmp.setR(a.r().multiply(b.r(), context));
			// r * i = i
			tmp.setI(a.r().multiply(b.i(), context));
			// r * j = j
			tmp.setJ(a.r().multiply(b.j(), context));
			// r * k = k
			tmp.setK(a.r().multiply(b.k(), context));
			// r * l = l
			tmp.setL(a.r().multiply(b.l(), context));
			// r * i0 = i0
			tmp.setI0(a.r().multiply(b.i0(), context));
			// r * j0 = j0
			tmp.setJ0(a.r().multiply(b.j0(), context));
			// r * k0 = k0
			tmp.setK0(a.r().multiply(b.k0(), context));
	
			// i * r = i
			tmp.setI(tmp.i().add(a.i().multiply(b.r(), context)));
			// i * i = −r
			tmp.setR(tmp.r().subtract(a.i().multiply(b.i(), context)));
			// i * j = k
			tmp.setK(tmp.k().add(a.i().multiply(b.j(), context)));
			// i * k = −j
			tmp.setJ(tmp.j().subtract(a.i().multiply(b.k(), context)));
			// i * l = i0
			tmp.setI0(tmp.i0().add(a.i().multiply(b.l(), context)));
			// i * i0 = −l
			tmp.setL(tmp.l().subtract(a.i().multiply(b.i0(), context)));
			// i * j0 = −k0
			tmp.setK0(tmp.k0().subtract(a.i().multiply(b.j0(), context)));
			// i * k0 = j0
			tmp.setJ0(tmp.j0().add(a.i().multiply(b.k0(), context)));
	
			// j * r = j
			tmp.setJ(tmp.j().add(a.j().multiply(b.r(), context)));
			// j * i = −k
			tmp.setK(tmp.k().subtract(a.j().multiply(b.i(), context)));
			// j * j = −r
			tmp.setR(tmp.r().subtract(a.j().multiply(b.j(), context)));
			// j * k = i
			tmp.setI(tmp.i().add(a.j().multiply(b.k(), context)));
			// j * l = j0
			tmp.setJ0(tmp.j0().add(a.j().multiply(b.l(), context)));
			// j * i0 = k0
			tmp.setK0(tmp.k0().add(a.j().multiply(b.i0(), context)));
			// j * j0 = −l
			tmp.setL(tmp.l().subtract(a.j().multiply(b.j0(), context)));
			// j * k0 = -i0
			tmp.setI0(tmp.i0().subtract(a.j().multiply(b.k0(), context)));
	
			// k * r = k
			tmp.setK(tmp.k().add(a.k().multiply(b.r(), context)));
			// k * i = j
			tmp.setJ(tmp.j().add(a.k().multiply(b.i(), context)));
			// k * j = −i
			tmp.setI(tmp.i().subtract(a.k().multiply(b.j(), context)));
			// k * k = −r
			tmp.setR(tmp.r().subtract(a.k().multiply(b.k(), context)));
			// k * l = k0
			tmp.setK0(tmp.k0().add(a.k().multiply(b.l(), context)));
			// k * i0 = −j0
			tmp.setJ0(tmp.j0().subtract(a.k().multiply(b.i0(), context)));
			// k * j0 = i0
			tmp.setI0(tmp.i0().add(a.k().multiply(b.j0(), context)));
			// k * k0 = −l
			tmp.setL(tmp.l().subtract(a.k().multiply(b.k0(), context)));
	 
			// l * r = l
			tmp.setL(tmp.l().add(a.l().multiply(b.r(), context)));
			// l * i = −i0
			tmp.setI0(tmp.i0().subtract(a.l().multiply(b.i(), context)));
			// l * j = −j0
			tmp.setJ0(tmp.j0().subtract(a.l().multiply(b.j(), context)));
			// l * k = −k0
			tmp.setK0(tmp.k0().subtract(a.l().multiply(b.k(), context)));
			// l * l = −r
			tmp.setR(tmp.r().subtract(a.l().multiply(b.l(), context)));
			// l * i0 = i
			tmp.setI(tmp.i().add(a.l().multiply(b.i0(), context)));
			// l * j0 = j
			tmp.setJ(tmp.j().add(a.l().multiply(b.j0(), context)));
			// l * k0 = k
			tmp.setK(tmp.k().add(a.l().multiply(b.k0(), context)));
	
			// i0 * r = i0
			tmp.setI0(tmp.i0().add(a.i0().multiply(b.r(), context)));
			// i0 * i = l
			tmp.setL(tmp.l().add(a.i0().multiply(b.i(), context)));
			// i0 * j = −k0
			tmp.setK0(tmp.k0().subtract(a.i0().multiply(b.j(), context)));
			// i0 * k = j0
			tmp.setJ0(tmp.j0().add(a.i0().multiply(b.k(), context)));
			// i0 * l = −i
			tmp.setI(tmp.i().subtract(a.i0().multiply(b.l(), context)));
			// i0 * i0 = −r
			tmp.setR(tmp.r().subtract(a.i0().multiply(b.i0(), context)));
			// i0 * j0 = −k
			tmp.setK(tmp.k().subtract(a.i0().multiply(b.j0(), context)));
			// i0 * k0 = j
			tmp.setJ(tmp.j().add(a.i0().multiply(b.k0(), context)));
			
			// j0 * r = j0
			tmp.setJ0(tmp.j0().add(a.j0().multiply(b.r(), context)));
			// j0 * i = k0
			tmp.setK0(tmp.k0().add(a.j0().multiply(b.i(), context)));
			// j0 * j = l
			tmp.setL(tmp.l().add(a.j0().multiply(b.j(), context)));
			// j0 * k = −i0
			tmp.setI0(tmp.i0().subtract(a.j0().multiply(b.k(), context)));
			// j0 * l = −j
			tmp.setJ(tmp.j().subtract(a.j0().multiply(b.l(), context)));
			// j0 * i0 = k
			tmp.setK(tmp.k().add(a.j0().multiply(b.i0(), context)));
			// j0 * j0 = −r
			tmp.setR(tmp.r().subtract(a.j0().multiply(b.j0(), context)));
			// j0 * k0 = −i
			tmp.setI(tmp.i().subtract(a.j0().multiply(b.k0(), context)));
			
			// k0 * r = k0
			tmp.setK0(tmp.k0().add(a.k0().multiply(b.r(), context)));
			// k0 * i = −j0
			tmp.setJ0(tmp.j0().subtract(a.k0().multiply(b.i(), context)));
			// k0 * j = i0
			tmp.setI0(tmp.i0().add(a.k0().multiply(b.j(), context)));
			// k0 * k = l
			tmp.setL(tmp.l().add(a.k0().multiply(b.k(), context)));
			// k0 * l = −k
			tmp.setK(tmp.k().subtract(a.k0().multiply(b.l(), context)));
			// k0 * i0 = −j
			tmp.setJ(tmp.j().subtract(a.k0().multiply(b.i0(), context)));
			// k0 * j0 = i
			tmp.setI(tmp.i().add(a.k0().multiply(b.j0(), context)));
			// k0 * k0 = −r
			tmp.setR(tmp.r().subtract(a.k0().multiply(b.k0(), context)));
			
			assign().call(tmp, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionHighPrecisionMember,OctonionHighPrecisionMember> POWER =
			new Procedure3<java.lang.Integer, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.OHP, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,OctonionHighPrecisionMember,OctonionHighPrecisionMember> power() {
		return POWER;
	}

	private final Procedure1<OctonionHighPrecisionMember> ZER =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> zero() {
		return ZER;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> NEG =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> negate() {
		return NEG;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> ADD =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			c.setR( a.r().add(b.r()) );
			c.setI( a.i().add(b.i()) );
			c.setJ( a.j().add(b.j()) );
			c.setK( a.k().add(b.k()) );
			c.setL( a.l().add(b.l()) );
			c.setI0( a.i0().add(b.i0()) );
			c.setJ0( a.j0().add(b.j0()) );
			c.setK0( a.k0().add(b.k0()) );
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> add() {
		return ADD;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> SUB =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			c.setR( a.r().subtract(b.r()) );
			c.setI( a.i().subtract(b.i()) );
			c.setJ( a.j().subtract(b.j()) );
			c.setK( a.k().subtract(b.k()) );
			c.setL( a.l().subtract(b.l()) );
			c.setI0( a.i0().subtract(b.i0()) );
			c.setJ0( a.j0().subtract(b.j0()) );
			c.setK0( a.k0().subtract(b.k0()) );
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionHighPrecisionMember,OctonionHighPrecisionMember> EQ =
			new Function2<Boolean, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			return a.r() == b.r() && a.i() == b.i() && a.j() == b.j() && a.k() == b.k() &&
					a.l() == b.l() && a.i0() == b.i0() && a.j0() == b.j0() && a.k0() == b.k0();
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionMember,OctonionHighPrecisionMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionHighPrecisionMember,OctonionHighPrecisionMember> NEQ =
			new Function2<Boolean, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionHighPrecisionMember,OctonionHighPrecisionMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> ASSIGN =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember from, OctonionHighPrecisionMember to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> INV =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			BigDecimal norm2 = norm2(a);
			conjugate().call(a, b);
			b.setR( b.r().divide(norm2, context) );
			b.setI( b.i().divide(norm2, context) );
			b.setJ( b.j().divide(norm2, context) );
			b.setK( b.k().divide(norm2, context) );
			b.setL( b.l().divide(norm2, context) );
			b.setI0( b.i0().divide(norm2, context) );
			b.setJ0( b.j0().divide(norm2, context) );
			b.setK0( b.k0().divide(norm2, context) );
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> invert() {
		return INV;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> DIVIDE =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
			invert().call(b, tmp);
			multiply().call(a, tmp, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> divide() {
		return DIVIDE;
	}

	private final Procedure2<OctonionHighPrecisionMember,HighPrecisionMember> NORM =
			new Procedure2<OctonionHighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, HighPrecisionMember b) {
			b.setV( norm(a) );
		}
	};

	@Override
	public Procedure2<OctonionHighPrecisionMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private BigDecimal norm(OctonionHighPrecisionMember a) {
		BigDecimal sum = (a.r().multiply(a.r(), context));
		sum = sum.add(a.i().multiply(a.i(), context));
		sum = sum.add(a.j().multiply(a.j(), context));
		sum = sum.add(a.k().multiply(a.k(), context));
		sum = sum.add(a.l().multiply(a.l(), context));
		sum = sum.add(a.i0().multiply(a.i0(), context));
		sum = sum.add(a.j0().multiply(a.j0(), context));
		sum = sum.add(a.k0().multiply(a.k0(), context));
		return BigDecimalMath.sqrt(sum, context);
	}
	
	private BigDecimal norm2(OctonionHighPrecisionMember a) {
		BigDecimal norm = norm(a);
		return norm.multiply(norm, context);
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> CONJ =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			b.setR(a.r());
			b.setI(a.i().negate());
			b.setJ(a.j().negate());
			b.setK(a.k().negate());
			b.setL(a.l().negate());
			b.setI0(a.i0().negate());
			b.setJ0(a.j0().negate());
			b.setK0(a.k0().negate());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> conjugate() {
		return CONJ;
	}

	private final Procedure1<OctonionHighPrecisionMember> PI_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			HighPrecisionMember pi = G.HP.construct();
			G.HP.PI().call(pi);
			a.setR(pi.v());
			a.setI(BigDecimal.ZERO);
			a.setJ(BigDecimal.ZERO);
			a.setK(BigDecimal.ZERO);
			a.setL(BigDecimal.ZERO);
			a.setI0(BigDecimal.ZERO);
			a.setJ0(BigDecimal.ZERO);
			a.setK0(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> PI() {
		return PI_;
	}

	private final Procedure1<OctonionHighPrecisionMember> E_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			HighPrecisionMember e = G.HP.construct();
			G.HP.E().call(e);
			a.setR(e.v());
			a.setI(BigDecimal.ZERO);
			a.setJ(BigDecimal.ZERO);
			a.setK(BigDecimal.ZERO);
			a.setL(BigDecimal.ZERO);
			a.setI0(BigDecimal.ZERO);
			a.setJ0(BigDecimal.ZERO);
			a.setK0(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> E() {
		return E_;
	}
	
	private final Procedure1<OctonionHighPrecisionMember> GAMMA_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			HighPrecisionMember gamma = G.HP.construct();
			G.HP.GAMMA().call(gamma);
			a.setR(gamma.v());
			a.setI(BigDecimal.ZERO);
			a.setJ(BigDecimal.ZERO);
			a.setK(BigDecimal.ZERO);
			a.setL(BigDecimal.ZERO);
			a.setI0(BigDecimal.ZERO);
			a.setJ0(BigDecimal.ZERO);
			a.setK0(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<OctonionHighPrecisionMember> PHI_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			HighPrecisionMember phi = G.HP.construct();
			G.HP.PHI().call(phi);
			a.setR(phi.v());
			a.setI(BigDecimal.ZERO);
			a.setJ(BigDecimal.ZERO);
			a.setK(BigDecimal.ZERO);
			a.setL(BigDecimal.ZERO);
			a.setI0(BigDecimal.ZERO);
			a.setJ0(BigDecimal.ZERO);
			a.setK0(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> PHI() {
		return PHI_;
	}

	private final Procedure1<OctonionHighPrecisionMember> I_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> I() {
		return I_;
	}

	private final Procedure1<OctonionHighPrecisionMember> J_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> J() {
		return J_;
	}

	private final Procedure1<OctonionHighPrecisionMember> K_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> K() {
		return K_;
	}

	private final Procedure1<OctonionHighPrecisionMember> L_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			assign().call(L, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> L() {
		return L_;
	}

	private final Procedure1<OctonionHighPrecisionMember> I0_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			assign().call(I0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> I0() {
		return I0_;
	}

	private final Procedure1<OctonionHighPrecisionMember> J0_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			assign().call(J0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> J0() {
		return J0_;
	}

	private final Procedure1<OctonionHighPrecisionMember> K0_ =
			new Procedure1<OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a) {
			assign().call(K0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionHighPrecisionMember> K0() {
		return K0_;
	}

	private final Procedure2<OctonionHighPrecisionMember,HighPrecisionMember> REAL =
			new Procedure2<OctonionHighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, HighPrecisionMember b) {
			b.setV(a.r());
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,HighPrecisionMember> real() {
		return REAL;
	}
	
	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> UNREAL =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			assign().call(a, b);
			b.setR(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> unreal() {
		return UNREAL;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> SINH =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionHighPrecisionMember negA = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember sum = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember tmp1 = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember tmp2 = new OctonionHighPrecisionMember();
		
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> COSH =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionHighPrecisionMember negA = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember sum = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember tmp1 = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember tmp2 = new OctonionHighPrecisionMember();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> cosh() {
		return COSH;
    }

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> SINHANDCOSH =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember s, OctonionHighPrecisionMember c) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionHighPrecisionMember negA = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember sum = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember tmp1 = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember tmp2 = new OctonionHighPrecisionMember();

			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);

			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, s);

			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> TANH =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			OctonionHighPrecisionMember s = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember c = new OctonionHighPrecisionMember();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> SIN =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			HighPrecisionMember z = new HighPrecisionMember();
			HighPrecisionMember z2 = new HighPrecisionMember();
			OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HP.sinch().call(z, z2);
			BigDecimal cos = BigDecimalMath.cos(a.r(), context);
			BigDecimal sin = BigDecimalMath.sin(a.r(), context);
			BigDecimal sinhc_pi = z2.v();
			BigDecimal cosh = BigDecimalMath.cosh(z.v(), context);
			BigDecimal ws = cos.multiply(sinhc_pi, context);
			b.setR((sin.multiply(cosh, context)));
			b.setI((ws.multiply(a.i(), context)));
			b.setJ((ws.multiply(a.j(), context)));
			b.setK((ws.multiply(a.k(), context)));
			b.setL((ws.multiply(a.l(), context)));
			b.setI0((ws.multiply(a.i0(), context)));
			b.setJ0((ws.multiply(a.j0(), context)));
			b.setK0((ws.multiply(a.k0(), context)));
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> sin() {
		return SIN;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> COS =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			HighPrecisionMember z = new HighPrecisionMember();
			HighPrecisionMember z2 = new HighPrecisionMember();
			OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HP.sinch().call(z, z2);
			BigDecimal cos = BigDecimalMath.cos(a.r(), context);
			BigDecimal sin = BigDecimalMath.sin(a.r(), context);
			BigDecimal sinhc_pi = z2.v();
			BigDecimal cosh = BigDecimalMath.cosh(z.v(), context);
			BigDecimal wc = sin.negate().multiply(sinhc_pi, context);
			b.setR((cos.multiply(cosh, context)));
			b.setI((wc.multiply(a.i(), context)));
			b.setJ((wc.multiply(a.j(), context)));
			b.setK((wc.multiply(a.k(), context)));
			b.setL((wc.multiply(a.l(), context)));
			b.setI0((wc.multiply(a.i0(), context)));
			b.setJ0((wc.multiply(a.j0(), context)));
			b.setK0((wc.multiply(a.k0(), context)));
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> cos() {
		return COS;
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> SINANDCOS =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember s, OctonionHighPrecisionMember c) {
			HighPrecisionMember z = new HighPrecisionMember();
			HighPrecisionMember z2 = new HighPrecisionMember();
			OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HP.sinch().call(z, z2);
			BigDecimal cos = BigDecimalMath.cos(a.r(), context);
			BigDecimal sin = BigDecimalMath.sin(a.r(), context);
			BigDecimal sinhc_pi = z2.v();
			BigDecimal cosh = BigDecimalMath.cosh(z.v(), context);
			BigDecimal ws = cos.multiply(sinhc_pi, context);
			BigDecimal wc = sin.negate().multiply(sinhc_pi, context);
			s.setR((sin.multiply(cosh, context)));
			s.setI((ws.multiply(a.i(), context)));
			s.setJ((ws.multiply(a.j(), context)));
			s.setK((ws.multiply(a.k(), context)));
			s.setL((ws.multiply(a.l(), context)));
			s.setI0((ws.multiply(a.i0(), context)));
			s.setJ0((ws.multiply(a.j0(), context)));
			s.setK0((ws.multiply(a.k0(), context)));
			c.setR(((cos.multiply(cosh, context))));
			c.setI(((wc.multiply(a.i(), context))));
			c.setJ(((wc.multiply(a.j(), context))));
			c.setK((wc.multiply(a.k(), context)));
			c.setL((wc.multiply(a.l(), context)));
			c.setI0((wc.multiply(a.i0(), context)));
			c.setJ0((wc.multiply(a.j0(), context)));
			c.setK0((wc.multiply(a.k0(), context)));
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> TAN =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			OctonionHighPrecisionMember sin = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember cos = new OctonionHighPrecisionMember();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> tan() {
		return TAN;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> EXP =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			HighPrecisionMember z = new HighPrecisionMember();
			HighPrecisionMember z2 = new HighPrecisionMember();
			OctonionHighPrecisionMember tmp = new OctonionHighPrecisionMember();
			BigDecimal u = BigDecimalMath.exp(a.r(), context);
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.HP.sinc().call(z, z2);
			BigDecimal w = z2.v();
			BigDecimal uw = u.multiply(w, context);
			b.setR((u.multiply(BigDecimalMath.cos(z.v(), context))));
			b.setI((uw.multiply(a.i(), context)));
			b.setJ((uw.multiply(a.j(), context)));
			b.setK((uw.multiply(a.k(), context)));
			b.setL((uw.multiply(a.l(), context)));
			b.setI0((uw.multiply(a.i0(), context)));
			b.setJ0((uw.multiply(a.j0(), context)));
			b.setK0((uw.multiply(a.k0(), context)));
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> exp() {
		return EXP;
	}

	// reference: https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	//   not sure about this. could not find other reference.
	
	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> LOG =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			BigDecimal factor;
			OctonionHighPrecisionMember unreal = new OctonionHighPrecisionMember();
			ComplexHighPrecisionMember tmp = new ComplexHighPrecisionMember();
			HighPrecisionMember norm = new HighPrecisionMember();
			assign().call(a, b); // this should be safe if two variables or one
			unreal().call(a, unreal);
			norm().call(unreal, norm);
			tmp.setR(a.r());
			tmp.setI(norm.v());
			G.CHP.log().call(tmp, tmp);
			if ( norm.v().signum() == 0 ) {
				factor = tmp.i();
			} else {
				factor = tmp.i().divide(norm.v(), context);
			}

			multiplier(tmp.r(), factor, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> log() {
		return LOG;
	}

	/*
	 
		Here is a source for some methods:
		
		https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	  
	 */
	
	@SuppressWarnings("unused")
	private void multiplier(BigDecimal r, BigDecimal factor, OctonionHighPrecisionMember result) {
		if (false) {
		// TODO enable me when BigDecimalMath supports nan and inf
		//if ( Double.isNaN( factor ) || Double.isInfinite( factor ) ) {
			if ( result.i().signum() == 0 &&
					result.j().signum() == 0 &&
					result.k().signum() == 0 )
			{
				result.setR(r);
				result.setI((result.i().multiply(factor, context)));
				result.setJ((result.j().multiply(factor, context)));
				result.setK((result.k().multiply(factor, context)));
				result.setL((result.l().multiply(factor, context)));
				result.setI0((result.i0().multiply(factor, context)));
				result.setJ0((result.j0().multiply(factor, context)));
				result.setK0((result.k0().multiply(factor, context)));
			}
			else {
				BigDecimal signum = BigDecimal.valueOf(factor.signum());
				result.setR(r);
				if (result.i().signum() == 0)
					result.setI((signum.multiply(result.i(), context)));
				else 
					result.setI((factor.multiply(result.i(), context)));
				if (result.j().signum() == 0)
					result.setJ((signum.multiply(result.j(), context)));
				else
					result.setJ((factor.multiply(result.j(), context)));
				if (result.k().signum() == 0)
					result.setK((signum.multiply(result.k(), context)));
				else 
					result.setK((factor.multiply(result.k(), context)));
				if (result.l().signum() == 0)
					result.setL((signum.multiply(result.l(), context)));
				else
					result.setL((factor.multiply(result.l(), context)));
				if (result.i0().signum() == 0)
					result.setI0((signum.multiply(result.i0(), context)));
				else
					result.setI0((factor.multiply(result.i0(), context)));
				if (result.j0().signum() == 0)
					result.setJ0((signum.multiply(result.j0(), context)));
				else
					result.setJ0((factor.multiply(result.j0(), context)));
				if (result.k0().signum() == 0)
					result.setK0((signum.multiply(result.k0(), context)));
				else
					result.setK0((factor.multiply(result.k0(), context)));
			}
		}
		else {
			result.setR(r);
			result.setI((result.i().multiply(factor, context)));
			result.setJ((result.j().multiply(factor, context)));
			result.setK((result.k().multiply(factor, context)));
			result.setL((result.l().multiply(factor, context)));
			result.setI0((result.i0().multiply(factor, context)));
			result.setJ0((result.j0().multiply(factor, context)));
			result.setK0((result.k0().multiply(factor, context)));
		}
	}

	private final Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> POW =
			new Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			OctonionHighPrecisionMember logA = new OctonionHighPrecisionMember();
			OctonionHighPrecisionMember bLogA = new OctonionHighPrecisionMember();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<OctonionHighPrecisionMember,OctonionHighPrecisionMember,OctonionHighPrecisionMember> pow() {
		return POW;
	}	

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> SINCH =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			Sinch.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> SINCHPI =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			Sinchpi.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> SINC =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			Sinc.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> SINCPI =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			Sincpi.compute(G.OHP, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> SQRT =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			OctonionHighPrecisionMember ONE_HALF = G.OHP.construct();
			ONE_HALF.setR(BigDecimal.ONE.divide(BigDecimal.valueOf(2), context));
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> sqrt() {
		return SQRT;
	}

	private final Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> CBRT =
			new Procedure2<OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			OctonionHighPrecisionMember ONE_THIRD = G.OHP.construct();
			ONE_THIRD.setR(BigDecimal.ONE.divide(BigDecimal.valueOf(3), context));
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<OctonionHighPrecisionMember,OctonionHighPrecisionMember> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, OctonionHighPrecisionMember> ISZERO =
			new Function1<Boolean, OctonionHighPrecisionMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionMember a) {
			return
				a.r().signum() == 0 && a.i().signum() == 0 && a.j().signum() == 0 &&
				a.k().signum() == 0 && a.l().signum() == 0 && a.i0().signum() == 0 &&
				a.j0().signum() == 0 && a.k0().signum() == 0;
		}
	};

	@Override
	public Function1<Boolean, OctonionHighPrecisionMember> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<OctonionHighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> SBHP =
			new Procedure3<HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			BigDecimal tmp;
			tmp = a.v().multiply(b.r(), context);
			c.setR(tmp);
			tmp = a.v().multiply(b.i(), context);
			c.setI(tmp);
			tmp = a.v().multiply(b.j(), context);
			c.setJ(tmp);
			tmp = a.v().multiply(b.k(), context);
			c.setK(tmp);
			tmp = a.v().multiply(b.l(), context);
			c.setL(tmp);
			tmp = a.v().multiply(b.i0(), context);
			c.setI0(tmp);
			tmp = a.v().multiply(b.j0(), context);
			c.setJ0(tmp);
			tmp = a.v().multiply(b.k0(), context);
			c.setK0(tmp);
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> SBR =
			new Procedure3<RationalMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(RationalMember a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			BigDecimal n = new BigDecimal(a.n());
			BigDecimal d = new BigDecimal(a.d());
			BigDecimal tmp;
			tmp = b.r();
			tmp = tmp.multiply(n, context);
			tmp = tmp.divide(d, context);
			c.setR(tmp);
			tmp = b.i();
			tmp = tmp.multiply(n, context);
			tmp = tmp.divide(d, context);
			c.setI(tmp);
			tmp = b.j();
			tmp = tmp.multiply(n, context);
			tmp = tmp.divide(d, context);
			c.setJ(tmp);
			tmp = b.k();
			tmp = tmp.multiply(n, context);
			tmp = tmp.divide(d, context);
			c.setK(tmp);
			tmp = b.l();
			tmp = tmp.multiply(n, context);
			tmp = tmp.divide(d, context);
			c.setL(tmp);
			tmp = b.i0();
			tmp = tmp.multiply(n, context);
			tmp = tmp.divide(d, context);
			c.setI0(tmp);
			tmp = b.j0();
			tmp = tmp.multiply(n, context);
			tmp = tmp.divide(d, context);
			c.setJ0(tmp);
			tmp = b.k0();
			tmp = tmp.multiply(n, context);
			tmp = tmp.divide(d, context);
			c.setK0(tmp);
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionHighPrecisionMember, OctonionHighPrecisionMember> SBD =
			new Procedure3<Double, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(Double a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			BigDecimal d = BigDecimal.valueOf(a);
			BigDecimal tmp;
			tmp = b.r();
			tmp = tmp.multiply(d, context);
			c.setR(tmp);
			tmp = b.i();
			tmp = tmp.multiply(d, context);
			c.setI(tmp);
			tmp = b.j();
			tmp = tmp.multiply(d, context);
			c.setJ(tmp);
			tmp = b.k();
			tmp = tmp.multiply(d, context);
			c.setK(tmp);
			tmp = b.l();
			tmp = tmp.multiply(d, context);
			c.setL(tmp);
			tmp = b.i0();
			tmp = tmp.multiply(d, context);
			c.setI0(tmp);
			tmp = b.j0();
			tmp = tmp.multiply(d, context);
			c.setJ0(tmp);
			tmp = b.k0();
			tmp = tmp.multiply(d, context);
			c.setK0(tmp);
		}
	};

	@Override
	public Procedure3<Double, OctonionHighPrecisionMember, OctonionHighPrecisionMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> SC =
			new Procedure3<HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionHighPrecisionMember b, OctonionHighPrecisionMember c) {
			c.setR(a.v().multiply(b.r(), context));
			c.setI(a.v().multiply(b.i(), context));
			c.setJ(a.v().multiply(b.j(), context));
			c.setK(a.v().multiply(b.k(), context));
			c.setL(a.v().multiply(b.l(), context));
			c.setI0(a.v().multiply(b.i0(), context));
			c.setJ0(a.v().multiply(b.j0(), context));
			c.setK0(a.v().multiply(b.k0(), context));
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		
		@Override
		public Boolean call(HighPrecisionMember tol, OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			return OctonionNumberWithin.compute(G.HP, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, OctonionHighPrecisionMember, OctonionHighPrecisionMember> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, OctonionHighPrecisionMember, OctonionHighPrecisionMember> STWO =
			new Procedure3<java.lang.Integer, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			ScaleHelper.compute(G.OHP, G.OHP, new OctonionHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, OctonionHighPrecisionMember, OctonionHighPrecisionMember> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, OctonionHighPrecisionMember, OctonionHighPrecisionMember> SHALF =
			new Procedure3<java.lang.Integer, OctonionHighPrecisionMember, OctonionHighPrecisionMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, OctonionHighPrecisionMember a, OctonionHighPrecisionMember b) {
			ScaleHelper.compute(G.OHP, G.OHP, new OctonionHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, OctonionHighPrecisionMember, OctonionHighPrecisionMember> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, OctonionHighPrecisionMember> ISUNITY =
			new Function1<Boolean, OctonionHighPrecisionMember>()
	{
		@Override
		public Boolean call(OctonionHighPrecisionMember a) {
			return G.OHP.isEqual().call(a, ONE);
		}
	};

	@Override
	public Function1<Boolean, OctonionHighPrecisionMember> isUnity() {
		return ISUNITY;
	}

	@Override
	public OctonionHighPrecisionMember constructExactly(byte... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember constructExactly(short... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember constructExactly(int... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember constructExactly(long... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromLongsExact(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember constructExactly(float... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember constructExactly(double... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember constructExactly(BigInteger... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromBigIntegersExact(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember constructExactly(BigDecimal... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromBigDecimalsExact(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember construct(BigDecimal... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember construct(BigInteger... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember construct(double... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember construct(float... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember construct(long... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember construct(int... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember construct(short... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public OctonionHighPrecisionMember construct(byte... vals) {
		OctonionHighPrecisionMember v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
