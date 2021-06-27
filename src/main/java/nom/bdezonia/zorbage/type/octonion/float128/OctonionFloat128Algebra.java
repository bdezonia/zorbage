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
package nom.bdezonia.zorbage.type.octonion.float128;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.OctonionNumberWithin;
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
import nom.bdezonia.zorbage.type.complex.float128.ComplexFloat128Member;
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
//TODO: is it really a skewfield or something else?  Multiplication is not associative
public class OctonionFloat128Algebra
	implements
		SkewField<OctonionFloat128Algebra, OctonionFloat128Member>,
		Conjugate<OctonionFloat128Member>,
		Norm<OctonionFloat128Member,Float128Member>,
		Infinite<OctonionFloat128Member>,
		NaN<OctonionFloat128Member>,
		Rounding<Float128Member,OctonionFloat128Member>,
		RealConstants<OctonionFloat128Member>,
		ImaginaryConstants<OctonionFloat128Member>,
		QuaternionConstants<OctonionFloat128Member>,
		OctonionConstants<OctonionFloat128Member>,
		Random<OctonionFloat128Member>,
		Exponential<OctonionFloat128Member>,
		Trigonometric<OctonionFloat128Member>,
		Hyperbolic<OctonionFloat128Member>,
		Power<OctonionFloat128Member>,
		Roots<OctonionFloat128Member>,
		RealUnreal<OctonionFloat128Member,Float128Member>,
		Scale<OctonionFloat128Member, OctonionFloat128Member>,
		ScaleByHighPrec<OctonionFloat128Member>,
		ScaleByRational<OctonionFloat128Member>,
		ScaleByDouble<OctonionFloat128Member>,
		ScaleComponents<OctonionFloat128Member, Float128Member>,
		Tolerance<Float128Member,OctonionFloat128Member>,
		ScaleByOneHalf<OctonionFloat128Member>,
		ScaleByTwo<OctonionFloat128Member>,
		ConstructibleFromBigDecimal<OctonionFloat128Member>,
		ConstructibleFromBigInteger<OctonionFloat128Member>,
		ConstructibleFromDouble<OctonionFloat128Member>,
		ConstructibleFromLong<OctonionFloat128Member>
{
	private static final OctonionFloat128Member ZERO = new OctonionFloat128Member();
	private static final OctonionFloat128Member ONE_THIRD = new OctonionFloat128Member(BigDecimal.ONE.divide(BigDecimal.valueOf(3), Float128Algebra.CONTEXT), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member ONE_HALF = new OctonionFloat128Member(BigDecimal.valueOf(0.5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member ONE = new OctonionFloat128Member(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member TWO = new OctonionFloat128Member(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member E = new OctonionFloat128Member(new BigDecimal(HighPrecisionAlgebra.E_STR.substring(0,40)), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member PI = new OctonionFloat128Member(new BigDecimal(HighPrecisionAlgebra.PI_STR.substring(0,40)), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member GAMMA = new OctonionFloat128Member(new BigDecimal(HighPrecisionAlgebra.GAMMA_STR.substring(0,40)), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member PHI = new OctonionFloat128Member(new BigDecimal(HighPrecisionAlgebra.PHI_STR.substring(0,40)), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member I = new OctonionFloat128Member(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member J = new OctonionFloat128Member(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member K = new OctonionFloat128Member(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member L = new OctonionFloat128Member(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member I0 = new OctonionFloat128Member(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO);
	private static final OctonionFloat128Member J0 = new OctonionFloat128Member(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.ZERO);
	private static final OctonionFloat128Member K0 = new OctonionFloat128Member(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ONE);

	public OctonionFloat128Algebra() { }

	@Override
	public OctonionFloat128Member construct() {
		return new OctonionFloat128Member();
	}

	@Override
	public OctonionFloat128Member construct(OctonionFloat128Member other) {
		return new OctonionFloat128Member(other);
	}

	@Override
	public OctonionFloat128Member construct(String s) {
		return new OctonionFloat128Member(s);
	}

	@Override
	public OctonionFloat128Member construct(long... vals) {
		return new OctonionFloat128Member(vals);
	}

	@Override
	public OctonionFloat128Member construct(double... vals) {
		return new OctonionFloat128Member(vals);
	}

	@Override
	public OctonionFloat128Member construct(BigInteger... vals) {
		return new OctonionFloat128Member(vals);
	}

	@Override
	public OctonionFloat128Member construct(BigDecimal... vals) {
		return new OctonionFloat128Member(vals);
	}

	private final Procedure1<OctonionFloat128Member> UNITY =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(ONE, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> unity() {
		return UNITY;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> MUL =
			new Procedure3<OctonionFloat128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b, OctonionFloat128Member c) {
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
			OctonionFloat128Member sum = new OctonionFloat128Member();
			Float128Member tmp = new Float128Member();
			
			// r * r = r
			G.QUAD.multiply().call(a.r(), b.r(), sum.r());
			// r * i = i
			G.QUAD.multiply().call(a.r(), b.i(), sum.i());
			// r * j = j
			G.QUAD.multiply().call(a.r(), b.j(), sum.j());
			// r * k = k
			G.QUAD.multiply().call(a.r(), b.k(), sum.k());
			// r * l = l
			G.QUAD.multiply().call(a.r(), b.l(), sum.l());
			// r * i0 = i0
			G.QUAD.multiply().call(a.r(), b.i0(), sum.i0());
			// r * j0 = j0
			G.QUAD.multiply().call(a.r(), b.j0(), sum.j0());
			// r * k0 = k0
			G.QUAD.multiply().call(a.r(), b.k0(), sum.k0());
	
			// i * r = i
			G.QUAD.multiply().call(a.i(), b.r(), tmp);
			G.QUAD.add().call(sum.i(), tmp, sum.i());
			// i * i = −r
			G.QUAD.multiply().call(a.i(), b.i(), tmp);
			G.QUAD.subtract().call(sum.r(), tmp, sum.r());
			// i * j = k
			G.QUAD.multiply().call(a.i(), b.j(), tmp);
			G.QUAD.add().call(sum.k(), tmp, sum.k());
			// i * k = −j
			G.QUAD.multiply().call(a.i(), b.k(), tmp);
			G.QUAD.subtract().call(sum.j(), tmp, sum.j());
			// i * l = i0
			G.QUAD.multiply().call(a.i(), b.l(), tmp);
			G.QUAD.add().call(sum.i0(), tmp, sum.i0());
			// i * i0 = −l
			G.QUAD.multiply().call(a.i(), b.i0(), tmp);
			G.QUAD.subtract().call(sum.l(), tmp, sum.l());
			// i * j0 = −k0
			G.QUAD.multiply().call(a.i(), b.j0(), tmp);
			G.QUAD.subtract().call(sum.k0(), tmp, sum.k0());
			// i * k0 = j0
			G.QUAD.multiply().call(a.i(), b.k0(), tmp);
			G.QUAD.add().call(sum.j0(), tmp, sum.j0());
	
			// j * r = j
			G.QUAD.multiply().call(a.j(), b.r(), tmp);
			G.QUAD.add().call(sum.j(), tmp, sum.j());
			// j * i = −k
			G.QUAD.multiply().call(a.j(), b.i(), tmp);
			G.QUAD.subtract().call(sum.k(), tmp, sum.k());
			// j * j = −r
			G.QUAD.multiply().call(a.j(), b.j(), tmp);
			G.QUAD.subtract().call(sum.r(), tmp, sum.r());
			// j * k = i
			G.QUAD.multiply().call(a.j(), b.k(), tmp);
			G.QUAD.add().call(sum.i(), tmp, sum.i());
			// j * l = j0
			G.QUAD.multiply().call(a.j(), b.l(), tmp);
			G.QUAD.add().call(sum.j0(), tmp, sum.j0());
			// j * i0 = k0
			G.QUAD.multiply().call(a.j(), b.i0(), tmp);
			G.QUAD.add().call(sum.k0(), tmp, sum.k0());
			// j * j0 = −l
			G.QUAD.multiply().call(a.j(), b.j0(), tmp);
			G.QUAD.subtract().call(sum.l(), tmp, sum.l());
			// j * k0 = -i0
			G.QUAD.multiply().call(a.j(), b.k0(), tmp);
			G.QUAD.subtract().call(sum.i0(), tmp, sum.i0());
	
			// k * r = k
			G.QUAD.multiply().call(a.k(), b.r(), tmp);
			G.QUAD.add().call(sum.k(), tmp, sum.k());
			// k * i = j
			G.QUAD.multiply().call(a.k(), b.i(), tmp);
			G.QUAD.add().call(sum.j(), tmp, sum.j());
			// k * j = −i
			G.QUAD.multiply().call(a.k(), b.j(), tmp);
			G.QUAD.subtract().call(sum.i(), tmp, sum.i());
			// k * k = −r
			G.QUAD.multiply().call(a.k(), b.k(), tmp);
			G.QUAD.subtract().call(sum.r(), tmp, sum.r());
			// k * l = k0
			G.QUAD.multiply().call(a.k(), b.l(), tmp);
			G.QUAD.add().call(sum.k0(), tmp, sum.k0());
			// k * i0 = −j0
			G.QUAD.multiply().call(a.k(), b.i0(), tmp);
			G.QUAD.subtract().call(sum.j0(), tmp, sum.j0());
			// k * j0 = i0
			G.QUAD.multiply().call(a.k(), b.j0(), tmp);
			G.QUAD.add().call(sum.i0(), tmp, sum.i0());
			// k * k0 = −l
			G.QUAD.multiply().call(a.k(), b.k0(), tmp);
			G.QUAD.subtract().call(sum.l(), tmp, sum.l());
	 
			// l * r = l
			G.QUAD.multiply().call(a.l(), b.r(), tmp);
			G.QUAD.add().call(sum.l(), tmp, sum.l());
			// l * i = −i0
			G.QUAD.multiply().call(a.l(), b.i(), tmp);
			G.QUAD.subtract().call(sum.i0(), tmp, sum.i0());
			// l * j = −j0
			G.QUAD.multiply().call(a.l(), b.j(), tmp);
			G.QUAD.subtract().call(sum.j0(), tmp, sum.j0());
			// l * k = −k0
			G.QUAD.multiply().call(a.l(), b.k(), tmp);
			G.QUAD.subtract().call(sum.k0(), tmp, sum.k0());
			// l * l = −r
			G.QUAD.multiply().call(a.l(), b.l(), tmp);
			G.QUAD.subtract().call(sum.r(), tmp, sum.r());
			// l * i0 = i
			G.QUAD.multiply().call(a.l(), b.i0(), tmp);
			G.QUAD.add().call(sum.i(), tmp, sum.i());
			// l * j0 = j
			G.QUAD.multiply().call(a.l(), b.j0(), tmp);
			G.QUAD.add().call(sum.j(), tmp, sum.j());
			// l * k0 = k
			G.QUAD.multiply().call(a.l(), b.k0(), tmp);
			G.QUAD.add().call(sum.k(), tmp, sum.k());
	
			// i0 * r = i0
			G.QUAD.multiply().call(a.i0(), b.r(), tmp);
			G.QUAD.add().call(sum.i0(), tmp, sum.i0());
			// i0 * i = l
			G.QUAD.multiply().call(a.i0(), b.i(), tmp);
			G.QUAD.add().call(sum.l(), tmp, sum.l());
			// i0 * j = −k0
			G.QUAD.multiply().call(a.i0(), b.j(), tmp);
			G.QUAD.subtract().call(sum.k0(), tmp, sum.k0());
			// i0 * k = j0
			G.QUAD.multiply().call(a.i0(), b.k(), tmp);
			G.QUAD.add().call(sum.j0(), tmp, sum.j0());
			// i0 * l = −i
			G.QUAD.multiply().call(a.i0(), b.l(), tmp);
			G.QUAD.subtract().call(sum.i(), tmp, sum.i());
			// i0 * i0 = −r
			G.QUAD.multiply().call(a.i0(), b.i0(), tmp);
			G.QUAD.subtract().call(sum.r(), tmp, sum.r());
			// i0 * j0 = −k
			G.QUAD.multiply().call(a.i0(), b.j0(), tmp);
			G.QUAD.subtract().call(sum.k(), tmp, sum.k());
			// i0 * k0 = j
			G.QUAD.multiply().call(a.i0(), b.k0(), tmp);
			G.QUAD.add().call(sum.j(), tmp, sum.j());
			
			// j0 * r = j0
			G.QUAD.multiply().call(a.j0(), b.r(), tmp);
			G.QUAD.add().call(sum.j0(), tmp, sum.j0());
			// j0 * i = k0
			G.QUAD.multiply().call(a.j0(), b.i(), tmp);
			G.QUAD.add().call(sum.k0(), tmp, sum.k0());
			// j0 * j = l
			G.QUAD.multiply().call(a.j0(), b.j(), tmp);
			G.QUAD.add().call(sum.l(), tmp, sum.l());
			// j0 * k = −i0
			G.QUAD.multiply().call(a.j0(), b.k(), tmp);
			G.QUAD.subtract().call(sum.i0(), tmp, sum.i0());
			// j0 * l = −j
			G.QUAD.multiply().call(a.j0(), b.l(), tmp);
			G.QUAD.subtract().call(sum.j(), tmp, sum.j());
			// j0 * i0 = k
			G.QUAD.multiply().call(a.j0(), b.i0(), tmp);
			G.QUAD.add().call(sum.k(), tmp, sum.k());
			// j0 * j0 = −r
			G.QUAD.multiply().call(a.j0(), b.j0(), tmp);
			G.QUAD.subtract().call(sum.r(), tmp, sum.r());
			// j0 * k0 = −i
			G.QUAD.multiply().call(a.j0(), b.k0(), tmp);
			G.QUAD.subtract().call(sum.i(), tmp, sum.i());
			
			// k0 * r = k0
			G.QUAD.multiply().call(a.k0(), b.r(), tmp);
			G.QUAD.add().call(sum.k0(), tmp, sum.k0());
			// k0 * i = −j0
			G.QUAD.multiply().call(a.k0(), b.i(), tmp);
			G.QUAD.subtract().call(sum.j0(), tmp, sum.j0());
			// k0 * j = i0
			G.QUAD.multiply().call(a.k0(), b.j(), tmp);
			G.QUAD.add().call(sum.i0(), tmp, sum.i0());
			// k0 * k = l
			G.QUAD.multiply().call(a.k0(), b.k(), tmp);
			G.QUAD.add().call(sum.l(), tmp, sum.l());
			// k0 * l = −k
			G.QUAD.multiply().call(a.k0(), b.l(), tmp);
			G.QUAD.subtract().call(sum.k(), tmp, sum.k());
			// k0 * i0 = −j
			G.QUAD.multiply().call(a.k0(), b.i0(), tmp);
			G.QUAD.subtract().call(sum.j(), tmp, sum.j());
			// k0 * j0 = i
			G.QUAD.multiply().call(a.k0(), b.j0(), tmp);
			G.QUAD.add().call(sum.i(), tmp, sum.i());
			// k0 * k0 = −r
			G.QUAD.multiply().call(a.k0(), b.k0(), tmp);
			G.QUAD.subtract().call(sum.r(), tmp, sum.r());
			
			assign().call(sum, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,OctonionFloat128Member,OctonionFloat128Member> POWER =
			new Procedure3<java.lang.Integer, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(java.lang.Integer power, OctonionFloat128Member a, OctonionFloat128Member b) {
			nom.bdezonia.zorbage.algorithm.PowerAny.compute(G.OQUAD, power, a, b);
		}
	};

	@Override
	public Procedure3<java.lang.Integer,OctonionFloat128Member,OctonionFloat128Member> power() {
		return POWER;
	}

	private final Procedure1<OctonionFloat128Member> ZER =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> zero() {
		return ZER;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> NEG =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			subtract().call(ZERO, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> negate() {
		return NEG;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> ADD =
			new Procedure3<OctonionFloat128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b, OctonionFloat128Member c) {
			G.QUAD.add().call(a.r(), b.r(), c.r());
			G.QUAD.add().call(a.i(), b.i(), c.i());
			G.QUAD.add().call(a.j(), b.j(), c.j());
			G.QUAD.add().call(a.k(), b.k(), c.k());
			G.QUAD.add().call(a.l(), b.l(), c.l());
			G.QUAD.add().call(a.i0(), b.i0(), c.i0());
			G.QUAD.add().call(a.j0(), b.j0(), c.j0());
			G.QUAD.add().call(a.k0(), b.k0(), c.k0());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> add() {
		return ADD;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> SUB =
			new Procedure3<OctonionFloat128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b, OctonionFloat128Member c) {
			G.QUAD.subtract().call(a.r(), b.r(), c.r());
			G.QUAD.subtract().call(a.i(), b.i(), c.i());
			G.QUAD.subtract().call(a.j(), b.j(), c.j());
			G.QUAD.subtract().call(a.k(), b.k(), c.k());
			G.QUAD.subtract().call(a.l(), b.l(), c.l());
			G.QUAD.subtract().call(a.i0(), b.i0(), c.i0());
			G.QUAD.subtract().call(a.j0(), b.j0(), c.j0());
			G.QUAD.subtract().call(a.k0(), b.k0(), c.k0());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> subtract() {
		return SUB;
	}

	private final Function2<Boolean,OctonionFloat128Member,OctonionFloat128Member> EQ =
			new Function2<Boolean, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public Boolean call(OctonionFloat128Member a, OctonionFloat128Member b) {
			return
				G.QUAD.isEqual().call(a.r(), b.r()) &&
				G.QUAD.isEqual().call(a.i(), b.i()) &&
				G.QUAD.isEqual().call(a.j(), b.j()) &&
				G.QUAD.isEqual().call(a.k(), b.k()) &&
				G.QUAD.isEqual().call(a.l(), b.l()) &&
				G.QUAD.isEqual().call(a.i0(), b.i0()) &&
				G.QUAD.isEqual().call(a.j0(), b.j0()) &&
				G.QUAD.isEqual().call(a.k0(), b.k0());
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat128Member,OctonionFloat128Member> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,OctonionFloat128Member,OctonionFloat128Member> NEQ =
			new Function2<Boolean, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public Boolean call(OctonionFloat128Member a, OctonionFloat128Member b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,OctonionFloat128Member,OctonionFloat128Member> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> ASSIGN =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member from, OctonionFloat128Member to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> assign() {
		return ASSIGN;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> INV =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			OctonionFloat128Member conjA = new OctonionFloat128Member();
			Float128Member norm = new Float128Member();
			Float128Member norm2 = new Float128Member();
			norm().call(a, norm);
			G.QUAD.multiply().call(norm, norm, norm2);
			G.QUAD.invert().call(norm2, norm2);
			conjugate().call(a, conjA);
			G.OQUAD.scaleComponents().call(norm2, conjA, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> invert() {
		return INV;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> DIVIDE =
			new Procedure3<OctonionFloat128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b, OctonionFloat128Member c) {
			OctonionFloat128Member tmp = new OctonionFloat128Member();
			invert().call(b, tmp);
			multiply().call(a, tmp, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> divide() {
		return DIVIDE;
	}

	private final Procedure4<Round.Mode,Float128Member,OctonionFloat128Member,OctonionFloat128Member> ROUND =
			new Procedure4<Round.Mode, Float128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, OctonionFloat128Member a, OctonionFloat128Member b) {
			Float128Member tmp = new Float128Member();
			tmp.setV(a.r());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setR(tmp.v());
			tmp.setV(a.i());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setI(tmp.v());
			tmp.setV(a.j());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setJ(tmp.v());
			tmp.setV(a.k());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setK(tmp.v());
			tmp.setV(a.l());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setL(tmp.v());
			tmp.setV(a.i0());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setI0(tmp.v());
			tmp.setV(a.j0());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setJ0(tmp.v());
			tmp.setV(a.k0());
			Round.compute(G.QUAD, mode, delta, tmp, tmp);
			b.setK0(tmp.v());
		}
	};
	
	@Override
	public Procedure4<Round.Mode,Float128Member,OctonionFloat128Member,OctonionFloat128Member> round() {
		return ROUND;
	}

	private final Function1<Boolean,OctonionFloat128Member> ISNAN =
			new Function1<Boolean, OctonionFloat128Member>()
	{
		@Override
		public Boolean call(OctonionFloat128Member a) {
			return
				G.QUAD.isNaN().call(a.r()) ||
				G.QUAD.isNaN().call(a.i()) ||
				G.QUAD.isNaN().call(a.j()) ||
				G.QUAD.isNaN().call(a.k()) ||
				G.QUAD.isNaN().call(a.l()) ||
				G.QUAD.isNaN().call(a.i0()) ||
				G.QUAD.isNaN().call(a.j0()) ||
				G.QUAD.isNaN().call(a.k0());
		}
	};

	@Override
	public Function1<Boolean,OctonionFloat128Member> isNaN() {
		return ISNAN;
	}

	private final Procedure1<OctonionFloat128Member> NAN =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			G.QUAD.nan().call(a.r());
			G.QUAD.nan().call(a.i());
			G.QUAD.nan().call(a.j());
			G.QUAD.nan().call(a.k());
			G.QUAD.nan().call(a.l());
			G.QUAD.nan().call(a.i0());
			G.QUAD.nan().call(a.j0());
			G.QUAD.nan().call(a.k0());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> nan() {
		return NAN;
	}

	private final Function1<Boolean,OctonionFloat128Member> ISINF =
			new Function1<Boolean, OctonionFloat128Member>()
	{
		@Override
		public Boolean call(OctonionFloat128Member a) {
			return
				!isNaN().call(a) && (
						G.QUAD.isInfinite().call(a.r()) ||
						G.QUAD.isInfinite().call(a.i()) ||
						G.QUAD.isInfinite().call(a.j()) ||
						G.QUAD.isInfinite().call(a.k()) ||
						G.QUAD.isInfinite().call(a.l()) ||
						G.QUAD.isInfinite().call(a.i0()) ||
						G.QUAD.isInfinite().call(a.j0()) ||
						G.QUAD.isInfinite().call(a.k0())
				);
		}
	};
	
	@Override
	public Function1<Boolean,OctonionFloat128Member> isInfinite() {
		return ISINF;
	}

	private final Procedure1<OctonionFloat128Member> INF =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			G.QUAD.infinite().call(a.r());
			G.QUAD.infinite().call(a.i());
			G.QUAD.infinite().call(a.j());
			G.QUAD.infinite().call(a.k());
			G.QUAD.infinite().call(a.l());
			G.QUAD.infinite().call(a.i0());
			G.QUAD.infinite().call(a.j0());
			G.QUAD.infinite().call(a.k0());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> infinite() {
		return INF;
	}

	private final Procedure2<OctonionFloat128Member,Float128Member> NORM =
			new Procedure2<OctonionFloat128Member, Float128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, Float128Member b) {
			// a hypot()-like implementation that avoids overflow
			Float128Member max = new Float128Member();
			Float128Member abs1 = new Float128Member();
			Float128Member abs2 = new Float128Member();
			Float128Member abs3 = new Float128Member();
			Float128Member abs4 = new Float128Member();
			Float128Member abs5 = new Float128Member();
			Float128Member abs6 = new Float128Member();
			Float128Member abs7 = new Float128Member();
			Float128Member abs8 = new Float128Member();
			G.QUAD.abs().call(a.r(), abs1);
			G.QUAD.abs().call(a.i(), abs2);
			G.QUAD.abs().call(a.j(), abs3);
			G.QUAD.abs().call(a.k(), abs4);
			G.QUAD.abs().call(a.l(), abs5);
			G.QUAD.abs().call(a.i0(), abs6);
			G.QUAD.abs().call(a.j0(), abs7);
			G.QUAD.abs().call(a.k0(), abs8);
			G.QUAD.max().call(abs1, abs2, max);
			G.QUAD.max().call(max, abs3, max);
			G.QUAD.max().call(max, abs4, max);
			G.QUAD.max().call(max, abs5, max);
			G.QUAD.max().call(max, abs6, max);
			G.QUAD.max().call(max, abs7, max);
			G.QUAD.max().call(max, abs8, max);
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
				G.QUAD.divide().call(a.j(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.k(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.l(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.i0(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.j0(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.divide().call(a.k0(), max, tmp);
				G.QUAD.multiply().call(tmp, tmp, tmp);
				G.QUAD.add().call(sum, tmp, sum);
				G.QUAD.sqrt().call(sum, tmp);
				G.QUAD.multiply().call(max, tmp, b);
			}
		}
	};

	@Override
	public Procedure2<OctonionFloat128Member,Float128Member> norm() {
		return NORM;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> CONJ =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			G.QUAD.assign().call(a.r(), b.r());
			G.QUAD.negate().call(a.i(), b.i());
			G.QUAD.negate().call(a.j(), b.j());
			G.QUAD.negate().call(a.k(), b.k());
			G.QUAD.negate().call(a.l(), b.l());
			G.QUAD.negate().call(a.i0(), b.i0());
			G.QUAD.negate().call(a.j0(), b.j0());
			G.QUAD.negate().call(a.k0(), b.k0());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> conjugate() {
		return CONJ;
	}

	private final Procedure1<OctonionFloat128Member> PI_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> PI() {
		return PI_;
	}

	private final Procedure1<OctonionFloat128Member> E_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> E() {
		return E_;
	}
	
	private final Procedure1<OctonionFloat128Member> GAMMA_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(GAMMA, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<OctonionFloat128Member> PHI_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(PHI, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> PHI() {
		return PHI_;
	}

	private final Procedure1<OctonionFloat128Member> I_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(I, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> I() {
		return I_;
	}

	private final Procedure1<OctonionFloat128Member> J_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(J, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> J() {
		return J_;
	}

	private final Procedure1<OctonionFloat128Member> K_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(K, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> K() {
		return K_;
	}

	private final Procedure1<OctonionFloat128Member> L_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(L, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> L() {
		return L_;
	}

	private final Procedure1<OctonionFloat128Member> I0_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(I0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> I0() {
		return I0_;
	}

	private final Procedure1<OctonionFloat128Member> J0_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(J0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> J0() {
		return J0_;
	}

	private final Procedure1<OctonionFloat128Member> K0_ =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			assign().call(K0, a);
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> K0() {
		return K0_;
	}

	private final Procedure1<OctonionFloat128Member> RAND =
			new Procedure1<OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a) {
			G.QUAD.random().call(a.r());
			G.QUAD.random().call(a.i());
			G.QUAD.random().call(a.j());
			G.QUAD.random().call(a.k());
			G.QUAD.random().call(a.l());
			G.QUAD.random().call(a.i0());
			G.QUAD.random().call(a.j0());
			G.QUAD.random().call(a.k0());
		}
	};
	
	@Override
	public Procedure1<OctonionFloat128Member> random() {
		return RAND;
	}

	private final Procedure2<OctonionFloat128Member,Float128Member> REAL =
			new Procedure2<OctonionFloat128Member, Float128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, Float128Member b) {
			b.set(a.r());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,Float128Member> real() {
		return REAL;
	}
	
	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> UNREAL =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			assign().call(a, b);
			b.setR(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> unreal() {
		return UNREAL;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> SINH =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat128Member negA = new OctonionFloat128Member();
			OctonionFloat128Member sum = new OctonionFloat128Member();
			OctonionFloat128Member tmp1 = new OctonionFloat128Member();
			OctonionFloat128Member tmp2 = new OctonionFloat128Member();
		
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			subtract().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> sinh() {
		return SINH;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> COSH =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat128Member negA = new OctonionFloat128Member();
			OctonionFloat128Member sum = new OctonionFloat128Member();
			OctonionFloat128Member tmp1 = new OctonionFloat128Member();
			OctonionFloat128Member tmp2 = new OctonionFloat128Member();
			
			negate().call(a, negA);
			exp().call(a, tmp1);
			exp().call(negA, tmp2);
			
			add().call(tmp1, tmp2, sum);
			divide().call(sum, TWO, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> cosh() {
		return COSH;
    }

	private final Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> SINHANDCOSH =
			new Procedure3<OctonionFloat128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member s, OctonionFloat128Member c) {
			// TODO adapted from Complex64Algebra: might be wrong
			OctonionFloat128Member negA = new OctonionFloat128Member();
			OctonionFloat128Member sum = new OctonionFloat128Member();
			OctonionFloat128Member tmp1 = new OctonionFloat128Member();
			OctonionFloat128Member tmp2 = new OctonionFloat128Member();

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
	public Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> TANH =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			OctonionFloat128Member s = new OctonionFloat128Member();
			OctonionFloat128Member c = new OctonionFloat128Member();
			sinhAndCosh().call(a, s, c);
			divide().call(s, c, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> tanh() {
		return TANH;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> SIN =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member s) {
			Float128Member z = new Float128Member();
			Float128Member z2 = new Float128Member();
			OctonionFloat128Member tmp = new OctonionFloat128Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.QUAD.sinch().call(z, z2);
			Float128Member cos = new Float128Member();
			Float128Member sin = new Float128Member();
			Float128Member sinhc_pi = new Float128Member();
			Float128Member cosh = new Float128Member();
			Float128Member ws = new Float128Member();
			G.QUAD.cos().call(a.r(), cos);
			G.QUAD.sin().call(a.r(), sin);
			sinhc_pi.set(z2);
			G.QUAD.cosh().call(z, cosh);
			G.QUAD.multiply().call(cos, sinhc_pi, ws);

			G.QUAD.multiply().call(sin, cosh, s.r());
			G.QUAD.multiply().call(ws, a.i(), s.i());
			G.QUAD.multiply().call(ws, a.j(), s.j());
			G.QUAD.multiply().call(ws, a.k(), s.k());
			G.QUAD.multiply().call(ws, a.l(), s.l());
			G.QUAD.multiply().call(ws, a.i0(), s.i0());
			G.QUAD.multiply().call(ws, a.j0(), s.j0());
			G.QUAD.multiply().call(ws, a.k0(), s.k0());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> sin() {
		return SIN;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> COS =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member c) {
			Float128Member z = new Float128Member();
			Float128Member z2 = new Float128Member();
			OctonionFloat128Member tmp = new OctonionFloat128Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.QUAD.sinch().call(z, z2);
			Float128Member cos = new Float128Member();
			Float128Member sin = new Float128Member();
			Float128Member sinhc_pi = new Float128Member();
			Float128Member cosh = new Float128Member();
			Float128Member wc = new Float128Member();
			G.QUAD.cos().call(a.r(), cos);
			G.QUAD.sin().call(a.r(), sin);
			sinhc_pi.set(z2);
			G.QUAD.cosh().call(z, cosh);
			G.QUAD.negate().call(sin, sin);
			G.QUAD.multiply().call(sin, sinhc_pi, wc);
			
			G.QUAD.multiply().call(cos, cosh, c.r());
			G.QUAD.multiply().call(wc, a.i(), c.i());
			G.QUAD.multiply().call(wc, a.j(), c.j());
			G.QUAD.multiply().call(wc, a.k(), c.k());
			G.QUAD.multiply().call(wc, a.l(), c.l());
			G.QUAD.multiply().call(wc, a.i0(), c.i0());
			G.QUAD.multiply().call(wc, a.j0(), c.j0());
			G.QUAD.multiply().call(wc, a.k0(), c.k0());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> cos() {
		return COS;
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> SINANDCOS =
			new Procedure3<OctonionFloat128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member s, OctonionFloat128Member c) {
			Float128Member z = new Float128Member();
			Float128Member z2 = new Float128Member();
			OctonionFloat128Member tmp = new OctonionFloat128Member();
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.QUAD.sinch().call(z, z2);
			Float128Member cos = new Float128Member();
			Float128Member sin = new Float128Member();
			Float128Member sinhc_pi = new Float128Member();
			Float128Member cosh = new Float128Member();
			Float128Member wc = new Float128Member();
			Float128Member ws = new Float128Member();
			G.QUAD.cos().call(a.r(), cos);
			G.QUAD.sin().call(a.r(), sin);
			sinhc_pi.set(z2);
			G.QUAD.cosh().call(z, cosh);
			G.QUAD.multiply().call(cos, sinhc_pi, ws);

			G.QUAD.multiply().call(sin, cosh, s.r());
			G.QUAD.multiply().call(ws, a.i(), s.i());
			G.QUAD.multiply().call(ws, a.j(), s.j());
			G.QUAD.multiply().call(ws, a.k(), s.k());
			G.QUAD.multiply().call(ws, a.l(), s.l());
			G.QUAD.multiply().call(ws, a.i0(), s.i0());
			G.QUAD.multiply().call(ws, a.j0(), s.j0());
			G.QUAD.multiply().call(ws, a.k0(), s.k0());
			
			G.QUAD.negate().call(sin, sin);
			G.QUAD.multiply().call(sin, sinhc_pi, wc);

			G.QUAD.multiply().call(cos, cosh, c.r());
			G.QUAD.multiply().call(wc, a.i(), c.i());
			G.QUAD.multiply().call(wc, a.j(), c.j());
			G.QUAD.multiply().call(wc, a.k(), c.k());
			G.QUAD.multiply().call(wc, a.l(), c.l());
			G.QUAD.multiply().call(wc, a.i0(), c.i0());
			G.QUAD.multiply().call(wc, a.j0(), c.j0());
			G.QUAD.multiply().call(wc, a.k0(), c.k0());
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> TAN =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			OctonionFloat128Member sin = new OctonionFloat128Member();
			OctonionFloat128Member cos = new OctonionFloat128Member();
			sinAndCos().call(a, sin, cos);
			divide().call(sin, cos, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> tan() {
		return TAN;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> EXP =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			Float128Member z = new Float128Member();
			Float128Member z2 = new Float128Member();
			OctonionFloat128Member tmp = new OctonionFloat128Member();
			Float128Member u = new Float128Member();
			Float128Member w = new Float128Member();
			Float128Member uw = new Float128Member();
			Float128Member cos = new Float128Member();
			G.QUAD.exp().call(a.r(), u);
			unreal().call(a, tmp);
			norm().call(tmp, z);
			G.QUAD.sinc().call(z, z2);
			w.set(z2);
			G.QUAD.multiply().call(u, w, uw);
			G.QUAD.cos().call(z, cos);
			
			G.QUAD.multiply().call(u, cos, b.r());
			G.QUAD.multiply().call(uw, a.i(), b.i());
			G.QUAD.multiply().call(uw, a.j(), b.j());
			G.QUAD.multiply().call(uw, a.k(), b.k());
			G.QUAD.multiply().call(uw, a.l(), b.l());
			G.QUAD.multiply().call(uw, a.i0(), b.i0());
			G.QUAD.multiply().call(uw, a.j0(), b.j0());
			G.QUAD.multiply().call(uw, a.k0(), b.k0());
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> exp() {
		return EXP;
	}

	// reference: https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	//   not sure about this. could not find other reference.
	
	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> LOG =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			Float128Member factor = new Float128Member();
			OctonionFloat128Member unreal = new OctonionFloat128Member();
			ComplexFloat128Member tmp = new ComplexFloat128Member();
			Float128Member norm = new Float128Member();
			assign().call(a, b); // this should be safe if two variables or one
			unreal().call(a, unreal);
			norm().call(unreal, norm);
			tmp.setR(a.r());
			tmp.setI(norm.v());
			G.CQUAD.log().call(tmp, tmp);
			if (G.QUAD.isZero().call(norm)) {
				G.QUAD.assign().call(tmp.i(), factor);
			} else {
				G.QUAD.divide().call(tmp.i(), norm, factor);
			}
			multiplier(tmp.r(), factor, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> log() {
		return LOG;
	}

	/*
	 
		Here is a source for some methods:
		
		https://ece.uwaterloo.ca/~dwharder/C++/CQOST/src/Octonion.cpp
	  
	 */
	
	private void multiplier(Float128Member r, Float128Member factor, OctonionFloat128Member result) {
		if (G.QUAD.isNaN().call(factor) || G.QUAD.isInfinite().call(factor)) {
			if ( G.QUAD.isZero().call(result.i()) &&
					G.QUAD.isZero().call(result.j()) &&
					G.QUAD.isZero().call(result.k()) )
			{
				G.QUAD.assign().call(r, result.r());
				G.QUAD.multiply().call(result.i(), factor, result.i());
				G.QUAD.multiply().call(result.j(), factor, result.j());
				G.QUAD.multiply().call(result.k(), factor, result.k());
				G.QUAD.multiply().call(result.i(), factor, result.l());
				G.QUAD.multiply().call(result.i0(), factor, result.i0());
				G.QUAD.multiply().call(result.j0(), factor, result.j0());
				G.QUAD.multiply().call(result.k0(), factor, result.k0());
			}
			else {
				Float128Member signum = new Float128Member();
				int s = G.QUAD.signum().call(factor);
				signum.setV(BigDecimal.valueOf(s));
				result.setR(r);
				if (G.QUAD.isZero().call(result.i()))
					G.QUAD.multiply().call(signum, result.i(), result.i());
				else
					G.QUAD.multiply().call(factor, result.i(), result.i());
				if (G.QUAD.isZero().call(result.j()))
					G.QUAD.multiply().call(signum, result.j(), result.j());
				else
					G.QUAD.multiply().call(factor, result.j(), result.j());
				if (G.QUAD.isZero().call(result.k()))
					G.QUAD.multiply().call(signum, result.k(), result.k());
				else
					G.QUAD.multiply().call(factor, result.k(), result.k());
				if (G.QUAD.isZero().call(result.l()))
					G.QUAD.multiply().call(signum, result.l(), result.l());
				else
					G.QUAD.multiply().call(factor, result.l(), result.l());
				if (G.QUAD.isZero().call(result.i0()))
					G.QUAD.multiply().call(signum, result.i0(), result.i0());
				else
					G.QUAD.multiply().call(factor, result.i0(), result.i0());
				if (G.QUAD.isZero().call(result.j0()))
					G.QUAD.multiply().call(signum, result.j0(), result.j0());
				else
					G.QUAD.multiply().call(factor, result.j0(), result.j0());
				if (G.QUAD.isZero().call(result.k0()))
					G.QUAD.multiply().call(signum, result.k0(), result.k0());
				else
					G.QUAD.multiply().call(factor, result.k0(), result.k0());
			}
		}
		else {
			G.QUAD.assign().call(r, result.r());
			G.QUAD.multiply().call(result.i(), factor, result.i());
			G.QUAD.multiply().call(result.j(), factor, result.j());
			G.QUAD.multiply().call(result.k(), factor, result.k());
			G.QUAD.multiply().call(result.l(), factor, result.l());
			G.QUAD.multiply().call(result.i0(), factor, result.i0());
			G.QUAD.multiply().call(result.j0(), factor, result.j0());
			G.QUAD.multiply().call(result.k0(), factor, result.k0());
		}
	}

	private final Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> POW =
			new Procedure3<OctonionFloat128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b, OctonionFloat128Member c) {
			OctonionFloat128Member logA = new OctonionFloat128Member();
			OctonionFloat128Member bLogA = new OctonionFloat128Member();
			log().call(a, logA);
			multiply().call(b, logA, bLogA);
			exp().call(bLogA, c);
		}
	};
	
	@Override
	public Procedure3<OctonionFloat128Member,OctonionFloat128Member,OctonionFloat128Member> pow() {
		return POW;
	}	

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> SINCH =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			Sinch.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> sinch() {
		return SINCH;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> SINCHPI =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			Sinchpi.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> sinchpi() {
		return SINCHPI;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> SINC =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			Sinc.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> sinc() {
		return SINC;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> SINCPI =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			Sincpi.compute(G.OQUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> sincpi() {
		return SINCPI;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> SQRT =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			pow().call(a, ONE_HALF, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> sqrt() {
		return SQRT;
	}

	private final Procedure2<OctonionFloat128Member,OctonionFloat128Member> CBRT =
			new Procedure2<OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(OctonionFloat128Member a, OctonionFloat128Member b) {
			pow().call(a, ONE_THIRD, b);
		}
	};
	
	@Override
	public Procedure2<OctonionFloat128Member,OctonionFloat128Member> cbrt() {
		return CBRT;
	}

	private final Function1<Boolean, OctonionFloat128Member> ISZERO =
			new Function1<Boolean, OctonionFloat128Member>()
	{
		@Override
		public Boolean call(OctonionFloat128Member a) {
			return
				G.QUAD.isZero().call(a.r()) &&
				G.QUAD.isZero().call(a.i()) &&
				G.QUAD.isZero().call(a.j()) &&
				G.QUAD.isZero().call(a.k()) &&
				G.QUAD.isZero().call(a.l()) &&
				G.QUAD.isZero().call(a.i0()) &&
				G.QUAD.isZero().call(a.j0()) &&
				G.QUAD.isZero().call(a.k0());
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128Member> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<OctonionFloat128Member, OctonionFloat128Member, OctonionFloat128Member> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, OctonionFloat128Member, OctonionFloat128Member> SBHP =
			new Procedure3<HighPrecisionMember, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(HighPrecisionMember a, OctonionFloat128Member b, OctonionFloat128Member c) {
			G.QUAD.scaleByHighPrec().call(a, b.r(), c.r());
			G.QUAD.scaleByHighPrec().call(a, b.i(), c.i());
			G.QUAD.scaleByHighPrec().call(a, b.j(), c.j());
			G.QUAD.scaleByHighPrec().call(a, b.k(), c.k());
			G.QUAD.scaleByHighPrec().call(a, b.l(), c.l());
			G.QUAD.scaleByHighPrec().call(a, b.i0(), c.i0());
			G.QUAD.scaleByHighPrec().call(a, b.j0(), c.j0());
			G.QUAD.scaleByHighPrec().call(a, b.k0(), c.k0());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, OctonionFloat128Member, OctonionFloat128Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, OctonionFloat128Member, OctonionFloat128Member> SBR =
			new Procedure3<RationalMember, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(RationalMember a, OctonionFloat128Member b, OctonionFloat128Member c) {
			G.QUAD.scaleByRational().call(a, b.r(), c.r());
			G.QUAD.scaleByRational().call(a, b.i(), c.i());
			G.QUAD.scaleByRational().call(a, b.j(), c.j());
			G.QUAD.scaleByRational().call(a, b.k(), c.k());
			G.QUAD.scaleByRational().call(a, b.l(), c.l());
			G.QUAD.scaleByRational().call(a, b.i0(), c.i0());
			G.QUAD.scaleByRational().call(a, b.j0(), c.j0());
			G.QUAD.scaleByRational().call(a, b.k0(), c.k0());
		}
	};

	@Override
	public Procedure3<RationalMember, OctonionFloat128Member, OctonionFloat128Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, OctonionFloat128Member, OctonionFloat128Member> SBD =
			new Procedure3<Double, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(Double a, OctonionFloat128Member b, OctonionFloat128Member c) {
			G.QUAD.scaleByDouble().call(a, b.r(), c.r());
			G.QUAD.scaleByDouble().call(a, b.i(), c.i());
			G.QUAD.scaleByDouble().call(a, b.j(), c.j());
			G.QUAD.scaleByDouble().call(a, b.k(), c.k());
			G.QUAD.scaleByDouble().call(a, b.l(), c.l());
			G.QUAD.scaleByDouble().call(a, b.i0(), c.i0());
			G.QUAD.scaleByDouble().call(a, b.j0(), c.j0());
			G.QUAD.scaleByDouble().call(a, b.k0(), c.k0());
		}
	};

	@Override
	public Procedure3<Double, OctonionFloat128Member, OctonionFloat128Member> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<Float128Member, OctonionFloat128Member, OctonionFloat128Member> SC =
			new Procedure3<Float128Member, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(Float128Member factor, OctonionFloat128Member a, OctonionFloat128Member b) {
			G.QUAD.multiply().call(factor, a.r(), b.r());
			G.QUAD.multiply().call(factor, a.i(), b.i());
			G.QUAD.multiply().call(factor, a.j(), b.j());
			G.QUAD.multiply().call(factor, a.k(), b.k());
			G.QUAD.multiply().call(factor, a.l(), b.l());
			G.QUAD.multiply().call(factor, a.i0(), b.i0());
			G.QUAD.multiply().call(factor, a.j0(), b.j0());
			G.QUAD.multiply().call(factor, a.k0(), b.k0());
		}
	};

	@Override
	public Procedure3<Float128Member, OctonionFloat128Member, OctonionFloat128Member> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, Float128Member, OctonionFloat128Member, OctonionFloat128Member> WITHIN =
			new Function3<Boolean, Float128Member,OctonionFloat128Member, OctonionFloat128Member>()
	{
		
		@Override
		public Boolean call(Float128Member tol, OctonionFloat128Member a, OctonionFloat128Member b) {
			return OctonionNumberWithin.compute(G.QUAD, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, Float128Member, OctonionFloat128Member, OctonionFloat128Member> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, OctonionFloat128Member, OctonionFloat128Member> STWO =
			new Procedure3<java.lang.Integer, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, OctonionFloat128Member a, OctonionFloat128Member b) {
			ScaleHelper.compute(G.OQUAD, G.OQUAD, new OctonionFloat128Member(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, OctonionFloat128Member, OctonionFloat128Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, OctonionFloat128Member, OctonionFloat128Member> SHALF =
			new Procedure3<java.lang.Integer, OctonionFloat128Member, OctonionFloat128Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, OctonionFloat128Member a, OctonionFloat128Member b) {
			ScaleHelper.compute(G.OQUAD, G.OQUAD, new OctonionFloat128Member(BigDecimal.valueOf(0.5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, OctonionFloat128Member, OctonionFloat128Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, OctonionFloat128Member> ISUNITY =
			new Function1<Boolean, OctonionFloat128Member>()
	{
		@Override
		public Boolean call(OctonionFloat128Member a) {
			return G.OQUAD.isEqual().call(a, ONE);
		}
	};

	@Override
	public Function1<Boolean, OctonionFloat128Member> isUnity() {
		return ISUNITY;
	}
}
