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
package nom.bdezonia.zorbage.type.gaussian.int8;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.AbsoluteValue;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBigDecimals;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBigIntegers;
import nom.bdezonia.zorbage.algebra.ConstructibleFromBytes;
import nom.bdezonia.zorbage.algebra.ConstructibleFromDoubles;
import nom.bdezonia.zorbage.algebra.ConstructibleFromFloats;
import nom.bdezonia.zorbage.algebra.ConstructibleFromInts;
import nom.bdezonia.zorbage.algebra.ConstructibleFromLongs;
import nom.bdezonia.zorbage.algebra.ConstructibleFromShorts;
import nom.bdezonia.zorbage.algebra.EuclideanDomain;
import nom.bdezonia.zorbage.algebra.ExactlyConstructibleFromBytes;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Random;
import nom.bdezonia.zorbage.algebra.Scale;
import nom.bdezonia.zorbage.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.algebra.ScaleByDoubleAndRound;
import nom.bdezonia.zorbage.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.algebra.ScaleByHighPrecAndRound;
import nom.bdezonia.zorbage.algebra.ScaleByOneHalf;
import nom.bdezonia.zorbage.algebra.ScaleByRational;
import nom.bdezonia.zorbage.algebra.ScaleByTwo;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.algebra.type.markers.ComplexType;
import nom.bdezonia.zorbage.algebra.type.markers.CompoundType;
import nom.bdezonia.zorbage.algebra.type.markers.EnumerableType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.NumberType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.algorithm.EuclideanGcd;
import nom.bdezonia.zorbage.algorithm.EuclideanLcm;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.misc.BigDecimalUtils;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.integer.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GaussianInt8Algebra
	implements
		EuclideanDomain<GaussianInt8Algebra, GaussianInt8Member, UnsignedInt16Member>,
		Random<GaussianInt8Member>,
		Tolerance<GaussianInt8Member,GaussianInt8Member>,
		Norm<GaussianInt8Member, UnsignedInt16Member>,
		Conjugate<GaussianInt8Member>,
		ScaleByOneHalf<GaussianInt8Member>,
		ScaleByTwo<GaussianInt8Member>,
		Scale<GaussianInt8Member, GaussianInt8Member>,
		ScaleByDouble<GaussianInt8Member>,
		ScaleByDoubleAndRound<GaussianInt8Member>,
		ScaleByHighPrec<GaussianInt8Member>,
		ScaleByHighPrecAndRound<GaussianInt8Member>,
		ScaleByRational<GaussianInt8Member>,
		AbsoluteValue<GaussianInt8Member, HighPrecisionMember>,
		ConstructibleFromBytes<GaussianInt8Member>,
		ConstructibleFromShorts<GaussianInt8Member>,
		ConstructibleFromInts<GaussianInt8Member>,
		ConstructibleFromLongs<GaussianInt8Member>,
		ConstructibleFromFloats<GaussianInt8Member>,
		ConstructibleFromDoubles<GaussianInt8Member>,
		ConstructibleFromBigIntegers<GaussianInt8Member>,
		ConstructibleFromBigDecimals<GaussianInt8Member>,
		ExactlyConstructibleFromBytes<GaussianInt8Member>,
		ComplexType,
		CompoundType,
		EnumerableType,
		ExactType,
		NumberType,
		SignedType,
		UnityIncludedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "8-bit based gaussian int";
	}

	@Override
	public GaussianInt8Member construct() {
		return new GaussianInt8Member();
	}
	
	@Override
	public GaussianInt8Member construct(GaussianInt8Member other) {
		return new GaussianInt8Member(other);
	}
	
	@Override
	public GaussianInt8Member construct(String str) {
		return new GaussianInt8Member(str);
	}
	
	private final Function2<Boolean, GaussianInt8Member, GaussianInt8Member> EQ =
		new Function2<Boolean, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public Boolean call(GaussianInt8Member a, GaussianInt8Member b) {
			return a.r == b.r && a.i == b.i;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianInt8Member, GaussianInt8Member> isEqual() {
		return EQ;
	}
	
	private final Function2<Boolean, GaussianInt8Member, GaussianInt8Member> NEQ =
		new Function2<Boolean, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public Boolean call(GaussianInt8Member a, GaussianInt8Member b) {
			return a.r != b.r || a.i != b.i;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianInt8Member, GaussianInt8Member> isNotEqual() {
		return NEQ;
	}
	
	private final Procedure2<GaussianInt8Member, GaussianInt8Member> ASS =
			new Procedure2<GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<GaussianInt8Member, GaussianInt8Member> assign() {
		return ASS;
	}

	private final Procedure1<GaussianInt8Member> ZER =
			new Procedure1<GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<GaussianInt8Member> zero() {
		return ZER;
	}
	
	private final Function1<Boolean, GaussianInt8Member> ISZ = new
			Function1<Boolean, GaussianInt8Member>()
	{
		@Override
		public Boolean call(GaussianInt8Member a) {
			return a.r == 0 && a.i == 0;
		}
	};
	
	@Override
	public Function1<Boolean, GaussianInt8Member> isZero() {
		return ISZ;
	}

	private final Procedure2<GaussianInt8Member, GaussianInt8Member> NEG =
			new Procedure2<GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b) {
			b.setR(-a.r);
			b.setI(-a.i);
		}
	};
	
	@Override
	public Procedure2<GaussianInt8Member, GaussianInt8Member> negate() {
		return NEG;
	}
	
	private final Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> ADD =
			new Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b, GaussianInt8Member c) {
			c.r = (byte) (a.r + b.r);
			c.i = (byte) (a.i + b.i);
		}
	};

	@Override
	public Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> add() {
		return ADD;
	}
	
	private final Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> SUB =
			new Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b, GaussianInt8Member c) {
			c.r = (byte) (a.r - b.r);
			c.i = (byte) (a.i - b.i);
		}
	};
	
	@Override
	public Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> subtract() {
		return SUB;
	}
	
	private final Procedure3<GaussianInt8Member,GaussianInt8Member,GaussianInt8Member> MUL =
			new Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b, GaussianInt8Member c) {
			// for safety must use tmps
			int r = ((int)a.r)*b.r - ((int)a.i)*b.i;
			int i = ((int)a.i)*b.r + ((int)a.r)*b.i;
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> multiply() {
		return MUL;
	}
	
	private final Procedure3<java.lang.Integer,GaussianInt8Member,GaussianInt8Member> POWER = 
			new Procedure3<java.lang.Integer, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(java.lang.Integer power, GaussianInt8Member a, GaussianInt8Member b) {
			PowerNonNegative.compute(G.GAUSS8, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianInt8Member, GaussianInt8Member> power() {
		return POWER;
	}
	
	private final Procedure1<GaussianInt8Member> UNITY =
			new Procedure1<GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a) {
			a.r = 1;
			a.i = 0;
		}
	};
			
	@Override
	public Procedure1<GaussianInt8Member> unity() {
		return UNITY;
	}

	private final Function3<Boolean, GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> WITHIN =
			new Function3<Boolean, GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public Boolean call(GaussianInt8Member tol, GaussianInt8Member a, GaussianInt8Member b) {
			if (tol.r < 0 || tol.i < 0)
				throw new IllegalArgumentException("gaussian tolerances must have nonnegative components");
			// avoid overflow/underflow conditions by using ints
			int dr = Math.abs(((int) a.r) - b.r);
			int di = Math.abs(((int) a.i) - b.i);
			return dr <= tol.r && di <= tol.i;
		}
	};

	@Override
	public Function3<Boolean, GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> within() {
		return WITHIN;
	}

	private final Procedure1<GaussianInt8Member> RAND =
			new Procedure1<GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR( rng.nextInt() );
			a.setI( rng.nextInt() );
		}
	};
	
	@Override
	public Procedure1<GaussianInt8Member> random() {
		return RAND;
	}

	private final Procedure2<GaussianInt8Member, UnsignedInt16Member> NORM =
			new Procedure2<GaussianInt8Member, UnsignedInt16Member>()
	{
		@Override
		public void call(GaussianInt8Member a, UnsignedInt16Member b) {
			int val = ((int)a.r)*a.r + ((int)a.i)*a.i;
			b.setV(val);
		}
	};

	@Override
	public Procedure2<GaussianInt8Member, UnsignedInt16Member> norm() {
		return NORM;
	}

	private final Procedure2<GaussianInt8Member, GaussianInt8Member> CONJ =
			new Procedure2<GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b) {
			b.r = a.r;
			b.i = (byte) -a.i;
		}
	};

	@Override
	public Procedure2<GaussianInt8Member, GaussianInt8Member> conjugate() {
		return CONJ;
	}
	
	private final Procedure3<java.lang.Integer, GaussianInt8Member, GaussianInt8Member> STWO =
			new Procedure3<java.lang.Integer, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, GaussianInt8Member a, GaussianInt8Member b) {
			b.setR( a.r << numTimes );
			b.setI( a.i << numTimes );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianInt8Member, GaussianInt8Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, GaussianInt8Member, GaussianInt8Member> SHALF =
			new Procedure3<java.lang.Integer, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, GaussianInt8Member a, GaussianInt8Member b) {
			b.setR( a.r >> numTimes );
			b.setI( a.i >> numTimes );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianInt8Member, GaussianInt8Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> GCD =
			new Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b, GaussianInt8Member c) {
			EuclideanGcd.compute(G.GAUSS8, G.UINT16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> gcd() {
		return GCD;
	}

	private final Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> LCM =
			new Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b, GaussianInt8Member c) {
			EuclideanLcm.compute(G.GAUSS8, G.UINT16, a, b, c);
		}
	};

	@Override
	public Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> lcm() {
		return LCM;
	}

	private final Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> DIV =
			new Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b, GaussianInt8Member d) {
			GaussianInt8Member m = G.GAUSS8.construct();
			divMod().call(a, b, d, m);
		}
	};
	
	@Override
	public Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> div() {
		return DIV;
	}

	private final Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> MOD =
			new Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b, GaussianInt8Member m) {
			GaussianInt8Member d = G.GAUSS8.construct();
			divMod().call(a, b, d, m);
		}
	};

	@Override
	public Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> mod() {
		return MOD;
	}

	private final Procedure4<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> DIVMOD =
			new Procedure4<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(GaussianInt8Member a, GaussianInt8Member b, GaussianInt8Member d, GaussianInt8Member m) {
			// rather than define a tricky integer only algo I will project into complex space and translate back.
			GaussianInt8Member tmp = G.GAUSS8.construct();
			ComplexFloat64Member ca = new ComplexFloat64Member(a.r(), a.i());
			ComplexFloat64Member cb = new ComplexFloat64Member(b.r(), b.i());
			ComplexFloat64Member cd = new ComplexFloat64Member();
			G.CDBL.divide().call(ca, cb, cd);
			if (cd.r() < 0)
				cd.setR(cd.r() - 0.5);
			else
				cd.setR(cd.r() + 0.5);
			if (cd.i() < 0)
				cd.setI(cd.i() - 0.5);
			else
				cd.setI(cd.i() + 0.5);
			d.setR( (int) cd.r() );
			d.setI( (int) cd.i() );
			G.GAUSS8.multiply().call(d, b, tmp);  // Order of ops doesn't matter since complex multiplication commutes
			G.GAUSS8.subtract().call(a, tmp, m);
		}
	};

	@Override
	public Procedure4<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> divMod() {
		return DIVMOD;
	}

	private final Function1<Boolean, GaussianInt8Member> EVEN =
			new Function1<Boolean, GaussianInt8Member>()
	{
		@Override
		public Boolean call(GaussianInt8Member a) {
			UnsignedInt16Member norm = G.UINT16.construct();
			norm().call(a, norm);
			return (norm.v() & 1) == 0;
		}
	};

	@Override
	public Function1<Boolean, GaussianInt8Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, GaussianInt8Member> ODD =
			new Function1<Boolean, GaussianInt8Member>()
	{
		@Override
		public Boolean call(GaussianInt8Member a) {
			UnsignedInt16Member norm = G.UINT16.construct();
			norm().call(a, norm);
			return (norm.v() & 1) == 1;
		}
	};

	@Override
	public Function1<Boolean, GaussianInt8Member> isOdd() {
		return ODD;
	}

	private final Procedure2<GaussianInt8Member, HighPrecisionMember> ABS =
			new Procedure2<GaussianInt8Member, HighPrecisionMember>()
	{
		@Override
		public void call(GaussianInt8Member a, HighPrecisionMember b) {
			UnsignedInt16Member norm = G.UINT16.construct();
			norm().call(a, norm);
			BigDecimal n = new BigDecimal(norm.v());
			b.setV(BigDecimalMath.sqrt(n, HighPrecisionAlgebra.getContext()));
		}
	};

	@Override
	public Procedure2<GaussianInt8Member, HighPrecisionMember> abs() {
		return ABS;
	}

	private final Procedure3<RationalMember, GaussianInt8Member, GaussianInt8Member> SBR =
			new Procedure3<RationalMember, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(RationalMember factor, GaussianInt8Member a, GaussianInt8Member b) {
			int r = a.r();
			int i = a.i();
			r = BigInteger.valueOf(r).multiply(factor.n()).divide(factor.d()).intValue();
			i = BigInteger.valueOf(i).multiply(factor.n()).divide(factor.d()).intValue();
			b.setR(r);
			b.setI(i);
		}
	};
	
	@Override
	public Procedure3<RationalMember, GaussianInt8Member, GaussianInt8Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<HighPrecisionMember, GaussianInt8Member, GaussianInt8Member> SBHPR =
			new Procedure3<HighPrecisionMember, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, GaussianInt8Member a, GaussianInt8Member b) {
			BigDecimal br = BigDecimal.valueOf(a.r()).multiply(factor.v());
			BigDecimal bi = BigDecimal.valueOf(a.i()).multiply(factor.v());
			if (br.signum() < 0)
				br = br.subtract(BigDecimalUtils.ONE_HALF);
			else
				br = br.add(BigDecimalUtils.ONE_HALF);
			if (bi.signum() < 0)
				bi = bi.subtract(BigDecimalUtils.ONE_HALF);
			else
				bi = bi.add(BigDecimalUtils.ONE_HALF);
			b.setR(br.intValue());
			b.setI(bi.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, GaussianInt8Member, GaussianInt8Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<HighPrecisionMember, GaussianInt8Member, GaussianInt8Member> SBHP =
			new Procedure3<HighPrecisionMember, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, GaussianInt8Member a, GaussianInt8Member b) {
			BigDecimal br = BigDecimal.valueOf(a.r()).multiply(factor.v());
			BigDecimal bi = BigDecimal.valueOf(a.i()).multiply(factor.v());
			b.setR(br.intValue());
			b.setI(bi.intValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, GaussianInt8Member, GaussianInt8Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<Double, GaussianInt8Member, GaussianInt8Member> SBDR =
			new Procedure3<Double, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(Double factor, GaussianInt8Member a, GaussianInt8Member b) {
			int r = a.r();
			int i = a.i();
			r = (int) Math.round(r * factor);
			i = (int) Math.round(i * factor);
			b.setR(r);
			b.setI(i);
		}
	};

	@Override
	public Procedure3<Double, GaussianInt8Member, GaussianInt8Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Procedure3<Double, GaussianInt8Member, GaussianInt8Member> SBD =
			new Procedure3<Double, GaussianInt8Member, GaussianInt8Member>()
	{
		@Override
		public void call(Double factor, GaussianInt8Member a, GaussianInt8Member b) {
			int r = a.r();
			int i = a.i();
			r = (int) (r * factor);
			i = (int) (i * factor);
			b.setR(r);
			b.setI(i);
		}
	};

	@Override
	public Procedure3<Double, GaussianInt8Member, GaussianInt8Member> scaleByDouble() {
		return SBD;
	}

	@Override
	public Procedure3<GaussianInt8Member, GaussianInt8Member, GaussianInt8Member> scale() {
		return MUL;
	}

	private final Function1<Boolean, GaussianInt8Member> ISUNITY =
			new Function1<Boolean, GaussianInt8Member>()
	{
		@Override
		public Boolean call(GaussianInt8Member a) {
			return a.r() == 1 && a.i() == 0;
		}
	};

	@Override
	public Function1<Boolean,GaussianInt8Member> isUnity() {
		return ISUNITY;
	}

	@Override
	public GaussianInt8Member constructExactly(byte... vals) {
		GaussianInt8Member v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public GaussianInt8Member construct(BigDecimal... vals) {
		GaussianInt8Member v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	@Override
	public GaussianInt8Member construct(BigInteger... vals) {
		GaussianInt8Member v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public GaussianInt8Member construct(double... vals) {
		GaussianInt8Member v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public GaussianInt8Member construct(float... vals) {
		GaussianInt8Member v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public GaussianInt8Member construct(long... vals) {
		GaussianInt8Member v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public GaussianInt8Member construct(int... vals) {
		GaussianInt8Member v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public GaussianInt8Member construct(short... vals) {
		GaussianInt8Member v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public GaussianInt8Member construct(byte... vals) {
		GaussianInt8Member v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
