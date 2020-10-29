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
package nom.bdezonia.zorbage.type.gaussian64;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.AbsoluteValue;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.EuclideanDomain;
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
import nom.bdezonia.zorbage.algorithm.EuclideanGcd;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.highprec.complex.ComplexHighPrecisionMember;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.unbounded.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GaussianInt64Algebra
	implements
		EuclideanDomain<GaussianInt64Algebra, GaussianInt64Member, UnboundedIntMember>,
		Random<GaussianInt64Member>,
		Tolerance<GaussianInt64Member,GaussianInt64Member>,
		Norm<GaussianInt64Member, UnboundedIntMember>,
		Conjugate<GaussianInt64Member>,
		ScaleByOneHalf<GaussianInt64Member>,
		ScaleByTwo<GaussianInt64Member>,
		Scale<GaussianInt64Member, GaussianInt64Member>,
		ScaleByDouble<GaussianInt64Member>,
		ScaleByDoubleAndRound<GaussianInt64Member>,
		ScaleByHighPrec<GaussianInt64Member>,
		ScaleByHighPrecAndRound<GaussianInt64Member>,
		ScaleByRational<GaussianInt64Member>,
		AbsoluteValue<GaussianInt64Member, HighPrecisionMember>
{
	@Override
	public GaussianInt64Member construct() {
		return new GaussianInt64Member();
	}
	
	@Override
	public GaussianInt64Member construct(GaussianInt64Member other) {
		return new GaussianInt64Member(other);
	}
	
	@Override
	public GaussianInt64Member construct(String str) {
		return new GaussianInt64Member(str);
	}
	
	private final Function2<Boolean, GaussianInt64Member, GaussianInt64Member> EQ =
		new Function2<Boolean, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public Boolean call(GaussianInt64Member a, GaussianInt64Member b) {
			return a.r == b.r && a.i == b.i;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianInt64Member, GaussianInt64Member> isEqual() {
		return EQ;
	}
	
	private final Function2<Boolean, GaussianInt64Member, GaussianInt64Member> NEQ =
		new Function2<Boolean, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public Boolean call(GaussianInt64Member a, GaussianInt64Member b) {
			return a.r != b.r || a.i != b.i;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianInt64Member, GaussianInt64Member> isNotEqual() {
		return NEQ;
	}
	
	private final Procedure2<GaussianInt64Member, GaussianInt64Member> ASS =
			new Procedure2<GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<GaussianInt64Member, GaussianInt64Member> assign() {
		return ASS;
	}

	private final Procedure1<GaussianInt64Member> ZER =
			new Procedure1<GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<GaussianInt64Member> zero() {
		return ZER;
	}
	
	private final Function1<Boolean, GaussianInt64Member> ISZ = new
			Function1<Boolean, GaussianInt64Member>()
	{
		@Override
		public Boolean call(GaussianInt64Member a) {
			return a.r == 0 && a.i == 0;
		}
	};
	
	@Override
	public Function1<Boolean, GaussianInt64Member> isZero() {
		return ISZ;
	}

	private final Procedure2<GaussianInt64Member, GaussianInt64Member> NEG =
			new Procedure2<GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b) {
			b.setR(-a.r);
			b.setI(-a.i);
		}
	};
	
	@Override
	public Procedure2<GaussianInt64Member, GaussianInt64Member> negate() {
		return NEG;
	}
	
	private final Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> ADD =
			new Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b, GaussianInt64Member c) {
			c.r = a.r + b.r;
			c.i = a.i + b.i;
		}
	};

	@Override
	public Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> add() {
		return ADD;
	}
	
	private final Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> SUB =
			new Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b, GaussianInt64Member c) {
			c.r = a.r - b.r;
			c.i = a.i - b.i;
		}
	};
	
	@Override
	public Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> subtract() {
		return SUB;
	}
	
	private final Procedure3<GaussianInt64Member,GaussianInt64Member,GaussianInt64Member> MUL =
			new Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b, GaussianInt64Member c) {
			// for safety must use tmps
			BigInteger ar = BigInteger.valueOf(a.r);
			BigInteger ai = BigInteger.valueOf(a.i);
			BigInteger br = BigInteger.valueOf(b.r);
			BigInteger bi = BigInteger.valueOf(b.i);
			BigInteger r = ar.multiply(br).subtract(ai.multiply(bi));
			BigInteger i = ai.multiply(br).add(ar.multiply(bi));
			c.setR( r.longValue() );
			c.setI( i.longValue() );
		}
	};

	@Override
	public Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> multiply() {
		return MUL;
	}
	
	private final Procedure3<java.lang.Integer,GaussianInt64Member,GaussianInt64Member> POWER = 
			new Procedure3<java.lang.Integer, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(java.lang.Integer power, GaussianInt64Member a, GaussianInt64Member b) {
			PowerNonNegative.compute(G.GAUSS64, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianInt64Member, GaussianInt64Member> power() {
		return POWER;
	}
	
	private final Procedure1<GaussianInt64Member> UNITY =
			new Procedure1<GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a) {
			a.r = 1;
			a.i = 0;
		}
	};
			
	@Override
	public Procedure1<GaussianInt64Member> unity() {
		return UNITY;
	}

	private final Function3<Boolean, GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> WITHIN =
			new Function3<Boolean, GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public Boolean call(GaussianInt64Member tol, GaussianInt64Member a, GaussianInt64Member b) {
			if (tol.r < 0 || tol.i < 0)
				throw new IllegalArgumentException("gaussian tolerances must have nonnegative components");
			// avoid overflow/underflow conditions by using bigInts
			BigInteger dr = BigInteger.valueOf(a.r).subtract(BigInteger.valueOf(b.r)).abs();
			BigInteger di = BigInteger.valueOf(a.i).subtract(BigInteger.valueOf(b.i)).abs();
			return dr.compareTo(BigInteger.valueOf(tol.r)) <= 0 && di.compareTo(BigInteger.valueOf(tol.i)) <= 0;
		}
	};

	@Override
	public Function3<Boolean, GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> within() {
		return WITHIN;
	}

	private final Procedure1<GaussianInt64Member> RAND =
			new Procedure1<GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a) {
			// Safely generate a random long in the right range
			// I'm avoiding nextLong() because it uses a 48-bit generator so it can't cover the
			// entire space. This might not either but it might be a better approach.
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			BigInteger bi = new BigInteger(64, rng);
			a.setR(bi.longValue());
			bi = new BigInteger(64, rng);
			a.setI(bi.longValue());
		}
	};
	
	@Override
	public Procedure1<GaussianInt64Member> random() {
		return RAND;
	}

	private final Procedure2<GaussianInt64Member, UnboundedIntMember> NORM =
			new Procedure2<GaussianInt64Member, UnboundedIntMember>()
	{
		@Override
		public void call(GaussianInt64Member a, UnboundedIntMember b) {
			BigInteger ar = BigInteger.valueOf(a.r);
			BigInteger ai = BigInteger.valueOf(a.i);
			BigInteger val = ar.multiply(ar).add(ai.multiply(ai));
			b.setV(val);
		}
	};

	@Override
	public Procedure2<GaussianInt64Member, UnboundedIntMember> norm() {
		return NORM;
	}

	private final Procedure2<GaussianInt64Member, GaussianInt64Member> CONJ =
			new Procedure2<GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b) {
			b.r = a.r;
			b.i = -a.i;
		}
	};

	@Override
	public Procedure2<GaussianInt64Member, GaussianInt64Member> conjugate() {
		return CONJ;
	}
	
	private final Procedure3<java.lang.Integer, GaussianInt64Member, GaussianInt64Member> STWO =
			new Procedure3<java.lang.Integer, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, GaussianInt64Member a, GaussianInt64Member b) {
			b.setR( a.r << numTimes );
			b.setI( a.i << numTimes );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianInt64Member, GaussianInt64Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, GaussianInt64Member, GaussianInt64Member> SHALF =
			new Procedure3<java.lang.Integer, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(java.lang.Integer numTimes, GaussianInt64Member a, GaussianInt64Member b) {
			b.setR( a.r >> numTimes );
			b.setI( a.i >> numTimes );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianInt64Member, GaussianInt64Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> GCD =
			new Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b, GaussianInt64Member c) {
			EuclideanGcd.compute(G.GAUSS64, G.UNBOUND, a, b, c);
		}
	};
	
	@Override
	public Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> gcd() {
		return GCD;
	}

	private final Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> LCM =
			new Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b, GaussianInt64Member c) {
			throw new IllegalArgumentException("code not done yet");
			//Lcm.compute(G.GAUSS16, a, b, c);
		}
	};

	@Override
	public Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> lcm() {
		return LCM;
	}

	private final Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> DIV =
			new Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b, GaussianInt64Member d) {
			GaussianInt64Member m = G.GAUSS64.construct();
			divMod().call(a, b, d, m);
		}
	};
	
	@Override
	public Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> div() {
		return DIV;
	}

	private final Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> MOD =
			new Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b, GaussianInt64Member m) {
			GaussianInt64Member d = G.GAUSS64.construct();
			divMod().call(a, b, d, m);
		}
	};

	@Override
	public Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> mod() {
		return MOD;
	}

	private final Procedure4<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> DIVMOD =
			new Procedure4<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(GaussianInt64Member a, GaussianInt64Member b, GaussianInt64Member d, GaussianInt64Member m) {
			// rather than define a tricky integer only algo I will project into complex space and translate back.
			GaussianInt64Member tmp = G.GAUSS64.construct();
			ComplexHighPrecisionMember ca = new ComplexHighPrecisionMember(BigDecimal.valueOf(a.r()), BigDecimal.valueOf(a.i()));
			ComplexHighPrecisionMember cb = new ComplexHighPrecisionMember(BigDecimal.valueOf(b.r()), BigDecimal.valueOf(b.i()));
			ComplexHighPrecisionMember cd = new ComplexHighPrecisionMember();
			G.CHP.divide().call(ca, cb, cd);
			if (cd.r().signum() < 0)
				cd.setR(cd.r().subtract(G.ONE_HALF));
			else
				cd.setR(cd.r().add(G.ONE_HALF));
			if (cd.i().signum() < 0)
				cd.setI(cd.i().subtract(G.ONE_HALF));
			else
				cd.setI(cd.i().add(G.ONE_HALF));
			d.setR( cd.r().longValue() );
			d.setI( cd.i().longValue() );
			G.GAUSS64.multiply().call(d, b, tmp);  // TODO: is the order of 1st two args reversed here?
			G.GAUSS64.subtract().call(a, tmp, m);
		}
	};

	@Override
	public Procedure4<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> divMod() {
		return DIVMOD;
	}

	private final Function1<Boolean, GaussianInt64Member> EVEN =
			new Function1<Boolean, GaussianInt64Member>()
	{
		@Override
		public Boolean call(GaussianInt64Member a) {
			UnboundedIntMember norm = G.UNBOUND.construct();
			norm().call(a, norm);
			return norm.v().and(BigInteger.ONE).equals(BigInteger.ZERO);
		}
	};

	@Override
	public Function1<Boolean, GaussianInt64Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, GaussianInt64Member> ODD =
			new Function1<Boolean, GaussianInt64Member>()
	{
		@Override
		public Boolean call(GaussianInt64Member a) {
			UnboundedIntMember norm = G.UNBOUND.construct();
			norm().call(a, norm);
			return norm.v().and(BigInteger.ONE).equals(BigInteger.ONE);
		}
	};

	@Override
	public Function1<Boolean, GaussianInt64Member> isOdd() {
		return ODD;
	}

	private final Procedure2<GaussianInt64Member, HighPrecisionMember> ABS =
			new Procedure2<GaussianInt64Member, HighPrecisionMember>()
	{
		@Override
		public void call(GaussianInt64Member a, HighPrecisionMember b) {
			UnboundedIntMember norm = G.UNBOUND.construct();
			norm().call(a, norm);
			BigDecimal n = new BigDecimal(norm.v());
			b.setV(BigDecimalMath.sqrt(n, HighPrecisionAlgebra.getContext()));
		}
	};

	@Override
	public Procedure2<GaussianInt64Member, HighPrecisionMember> abs() {
		return ABS;
	}

	private final Procedure3<RationalMember, GaussianInt64Member, GaussianInt64Member> SBR =
			new Procedure3<RationalMember, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(RationalMember factor, GaussianInt64Member a, GaussianInt64Member b) {
			long r = a.r();
			long i = a.i();
			r = BigInteger.valueOf(r).multiply(factor.n()).divide(factor.d()).longValue();
			i = BigInteger.valueOf(i).multiply(factor.n()).divide(factor.d()).longValue();
			b.setR(r);
			b.setI(i);
		}
	};
	
	@Override
	public Procedure3<RationalMember, GaussianInt64Member, GaussianInt64Member> scaleByRational() {
		return SBR;
	}

	private final Procedure3<HighPrecisionMember, GaussianInt64Member, GaussianInt64Member> SBHPR =
			new Procedure3<HighPrecisionMember, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, GaussianInt64Member a, GaussianInt64Member b) {
			BigDecimal br = BigDecimal.valueOf(a.r()).multiply(factor.v());
			BigDecimal bi = BigDecimal.valueOf(a.i()).multiply(factor.v());
			if (br.signum() < 0)
				br = br.subtract(G.ONE_HALF);
			else
				br = br.add(G.ONE_HALF);
			if (bi.signum() < 0)
				bi = bi.subtract(G.ONE_HALF);
			else
				bi = bi.add(G.ONE_HALF);
			b.setR(br.longValue());
			b.setI(bi.longValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, GaussianInt64Member, GaussianInt64Member> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<HighPrecisionMember, GaussianInt64Member, GaussianInt64Member> SBHP =
			new Procedure3<HighPrecisionMember, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(HighPrecisionMember factor, GaussianInt64Member a, GaussianInt64Member b) {
			BigDecimal br = BigDecimal.valueOf(a.r()).multiply(factor.v());
			BigDecimal bi = BigDecimal.valueOf(a.i()).multiply(factor.v());
			b.setR(br.longValue());
			b.setI(bi.longValue());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, GaussianInt64Member, GaussianInt64Member> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<Double, GaussianInt64Member, GaussianInt64Member> SBDR =
			new Procedure3<Double, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(Double factor, GaussianInt64Member a, GaussianInt64Member b) {
			BigDecimal scale = new BigDecimal(factor);
			BigDecimal br = new BigDecimal(a.r()).multiply(scale);
			BigDecimal bi = new BigDecimal(a.i()).multiply(scale);
			if (br.signum() < 0)
				br = br.subtract(G.ONE_HALF);
			else
				br = br.add(G.ONE_HALF);
			if (bi.signum() < 0)
				bi = bi.subtract(G.ONE_HALF);
			else
				bi = bi.add(G.ONE_HALF);
			b.setR(br.longValue());
			b.setI(bi.longValue());
		}
	};

	@Override
	public Procedure3<Double, GaussianInt64Member, GaussianInt64Member> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Procedure3<Double, GaussianInt64Member, GaussianInt64Member> SBD =
			new Procedure3<Double, GaussianInt64Member, GaussianInt64Member>()
	{
		@Override
		public void call(Double factor, GaussianInt64Member a, GaussianInt64Member b) {
			BigDecimal scale = new BigDecimal(factor);
			BigDecimal br = new BigDecimal(a.r()).multiply(scale);
			BigDecimal bi = new BigDecimal(a.i()).multiply(scale);
			b.setR(br.longValue());
			b.setI(bi.longValue());
		}
	};

	@Override
	public Procedure3<Double, GaussianInt64Member, GaussianInt64Member> scaleByDouble() {
		return SBD;
	}

	@Override
	public Procedure3<GaussianInt64Member, GaussianInt64Member, GaussianInt64Member> scale() {
		return MUL;
	}
}
