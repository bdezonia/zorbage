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
package nom.bdezonia.zorbage.type.gaussianu;

import java.math.BigDecimal;
import java.math.BigInteger;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.AbsoluteValue;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.EuclideanDomain;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Norm;
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
public class GaussianIntUnboundedAlgebra
	implements
		EuclideanDomain<GaussianIntUnboundedAlgebra, GaussianIntUnboundedMember, UnboundedIntMember>,
		Tolerance<GaussianIntUnboundedMember,GaussianIntUnboundedMember>,
		Norm<GaussianIntUnboundedMember, UnboundedIntMember>,
		Conjugate<GaussianIntUnboundedMember>,
		ScaleByOneHalf<GaussianIntUnboundedMember>,
		ScaleByTwo<GaussianIntUnboundedMember>,
		Scale<GaussianIntUnboundedMember, GaussianIntUnboundedMember>,
		ScaleByDouble<GaussianIntUnboundedMember>,
		ScaleByDoubleAndRound<GaussianIntUnboundedMember>,
		ScaleByHighPrec<GaussianIntUnboundedMember>,
		ScaleByHighPrecAndRound<GaussianIntUnboundedMember>,
		ScaleByRational<GaussianIntUnboundedMember>,
		AbsoluteValue<GaussianIntUnboundedMember, HighPrecisionMember>
{
	@Override
	public GaussianIntUnboundedMember construct() {
		return new GaussianIntUnboundedMember();
	}
	
	@Override
	public GaussianIntUnboundedMember construct(GaussianIntUnboundedMember other) {
		return new GaussianIntUnboundedMember(other);
	}
	
	@Override
	public GaussianIntUnboundedMember construct(String str) {
		return new GaussianIntUnboundedMember(str);
	}
	
	private final Function2<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember> EQ =
		new Function2<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public Boolean call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			return a.r.compareTo(b.r) == 0 && a.i.compareTo(b.i) == 0;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember> isEqual() {
		return EQ;
	}
	
	private final Function2<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember> NEQ =
		new Function2<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public Boolean call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			return a.r.compareTo(b.r) != 0 || a.i.compareTo(b.i) != 0;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember> isNotEqual() {
		return NEQ;
	}
	
	private final Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember> ASS =
			new Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember> assign() {
		return ASS;
	}

	private final Procedure1<GaussianIntUnboundedMember> ZER =
			new Procedure1<GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<GaussianIntUnboundedMember> zero() {
		return ZER;
	}
	
	private final Function1<Boolean, GaussianIntUnboundedMember> ISZ = new
			Function1<Boolean, GaussianIntUnboundedMember>()
	{
		@Override
		public Boolean call(GaussianIntUnboundedMember a) {
			return a.r.compareTo(BigInteger.ZERO) == 0 && a.i.compareTo(BigInteger.ZERO) == 0;
		}
	};
	
	@Override
	public Function1<Boolean, GaussianIntUnboundedMember> isZero() {
		return ISZ;
	}

	private final Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember> NEG =
			new Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			b.setR(a.r.negate());
			b.setI(a.i.negate());
		}
	};
	
	@Override
	public Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember> negate() {
		return NEG;
	}
	
	private final Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> ADD =
			new Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b, GaussianIntUnboundedMember c) {
			c.r = a.r.add(b.r);
			c.i = a.i.add(b.i);
		}
	};

	@Override
	public Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> add() {
		return ADD;
	}
	
	private final Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> SUB =
			new Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b, GaussianIntUnboundedMember c) {
			c.r = a.r.subtract(b.r);
			c.i = a.i.subtract(b.i);
		}
	};
	
	@Override
	public Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> subtract() {
		return SUB;
	}
	
	private final Procedure3<GaussianIntUnboundedMember,GaussianIntUnboundedMember,GaussianIntUnboundedMember> MUL =
			new Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b, GaussianIntUnboundedMember c) {
			// for safety must use tmps
			BigInteger r = a.r.multiply(b.r).subtract(a.i.multiply(b.i));
			BigInteger i = a.i.multiply(b.r).add(a.r.multiply(b.i));
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> multiply() {
		return MUL;
	}
	
	private final Procedure3<java.lang.Integer,GaussianIntUnboundedMember,GaussianIntUnboundedMember> POWER = 
			new Procedure3<java.lang.Integer, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(java.lang.Integer power, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			PowerNonNegative.compute(G.GAUSSU, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianIntUnboundedMember, GaussianIntUnboundedMember> power() {
		return POWER;
	}
	
	private final Procedure1<GaussianIntUnboundedMember> UNITY =
			new Procedure1<GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a) {
			a.r = BigInteger.ONE;
			a.i = BigInteger.ZERO;
		}
	};
			
	@Override
	public Procedure1<GaussianIntUnboundedMember> unity() {
		return UNITY;
	}

	private final Function3<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> WITHIN =
			new Function3<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public Boolean call(GaussianIntUnboundedMember tol, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			if (tol.r.compareTo(BigInteger.ZERO) < 0 || tol.i.compareTo(BigInteger.ZERO) < 0)
				throw new IllegalArgumentException("gaussian tolerances must have nonnegative components");
			BigInteger dr = a.r.subtract(b.r).abs();
			BigInteger di = a.i.subtract(b.i).abs();
			return dr.compareTo(tol.r) <= 0 && di.compareTo(tol.i) <= 0;
		}
	};

	@Override
	public Function3<Boolean, GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> within() {
		return WITHIN;
	}

	private final Procedure2<GaussianIntUnboundedMember, UnboundedIntMember> NORM =
			new Procedure2<GaussianIntUnboundedMember, UnboundedIntMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, UnboundedIntMember b) {
			b.setV(a.r.multiply(a.r).add(a.i.multiply(a.i)));
		}
	};

	@Override
	public Procedure2<GaussianIntUnboundedMember, UnboundedIntMember> norm() {
		return NORM;
	}

	private final Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember> CONJ =
			new Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			b.r = a.r;
			b.i = a.i.negate();
		}
	};

	@Override
	public Procedure2<GaussianIntUnboundedMember, GaussianIntUnboundedMember> conjugate() {
		return CONJ;
	}
	
	private final Procedure3<java.lang.Integer, GaussianIntUnboundedMember, GaussianIntUnboundedMember> STWO =
			new Procedure3<java.lang.Integer, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			b.setR( a.r.shiftLeft(numTimes) );
			b.setI( a.i.shiftLeft(numTimes) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianIntUnboundedMember, GaussianIntUnboundedMember> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, GaussianIntUnboundedMember, GaussianIntUnboundedMember> SHALF =
			new Procedure3<java.lang.Integer, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			b.setR( a.r.shiftRight(numTimes) );
			b.setI( a.i.shiftRight(numTimes) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianIntUnboundedMember, GaussianIntUnboundedMember> scaleByOneHalf() {
		return SHALF;
	}

	private final Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> GCD =
			new Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b, GaussianIntUnboundedMember c) {
			EuclideanGcd.compute(G.GAUSSU, G.UNBOUND, a, b, c);
		}
	};
	
	@Override
	public Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> gcd() {
		return GCD;
	}

	private final Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> LCM =
			new Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b, GaussianIntUnboundedMember c) {
			throw new IllegalArgumentException("code not done yet");
			//Lcm.compute(G.GAUSS16, a, b, c);
		}
	};

	@Override
	public Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> lcm() {
		return LCM;
	}

	private final Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> DIV =
			new Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b, GaussianIntUnboundedMember d) {
			GaussianIntUnboundedMember m = G.GAUSSU.construct();
			divMod().call(a, b, d, m);
		}
	};
	
	@Override
	public Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> div() {
		return DIV;
	}

	private final Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> MOD =
			new Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b, GaussianIntUnboundedMember m) {
			GaussianIntUnboundedMember d = G.GAUSSU.construct();
			divMod().call(a, b, d, m);
		}
	};
	
	@Override
	public Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> mod() {
		return MOD;
	}

	private final Procedure4<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> DIVMOD =
			new Procedure4<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, GaussianIntUnboundedMember b, GaussianIntUnboundedMember d, GaussianIntUnboundedMember m) {
			// rather than define a tricky integer only algo I will project into complex space and translate back.
			GaussianIntUnboundedMember tmp = G.GAUSSU.construct();
			ComplexHighPrecisionMember ca = new ComplexHighPrecisionMember(new BigDecimal(a.r()), new BigDecimal(a.i()));
			ComplexHighPrecisionMember cb = new ComplexHighPrecisionMember(new BigDecimal(b.r()), new BigDecimal(b.i()));
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
			d.setR( cd.r().toBigInteger() );
			d.setI( cd.i().toBigInteger() );
			G.GAUSSU.multiply().call(d, b, tmp);  // Order of ops doesn't matter since complex multiplication commutes
			G.GAUSSU.subtract().call(a, tmp, m);
		}
	};

	@Override
	public Procedure4<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> divMod() {
		return DIVMOD;
	}

	private final Function1<Boolean, GaussianIntUnboundedMember> EVEN =
			new Function1<Boolean, GaussianIntUnboundedMember>()
	{
		@Override
		public Boolean call(GaussianIntUnboundedMember a) {
			UnboundedIntMember norm = G.UNBOUND.construct();
			norm().call(a, norm);
			return norm.v().and(BigInteger.ONE).equals(BigInteger.ZERO);
		}
	};

	@Override
	public Function1<Boolean, GaussianIntUnboundedMember> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, GaussianIntUnboundedMember> ODD =
			new Function1<Boolean, GaussianIntUnboundedMember>()
	{
		@Override
		public Boolean call(GaussianIntUnboundedMember a) {
			UnboundedIntMember norm = G.UNBOUND.construct();
			norm().call(a, norm);
			return norm.v().and(BigInteger.ONE).equals(BigInteger.ONE);
		}
	};

	@Override
	public Function1<Boolean, GaussianIntUnboundedMember> isOdd() {
		return ODD;
	}

	private final Procedure2<GaussianIntUnboundedMember, HighPrecisionMember> ABS =
			new Procedure2<GaussianIntUnboundedMember, HighPrecisionMember>()
	{
		@Override
		public void call(GaussianIntUnboundedMember a, HighPrecisionMember b) {
			UnboundedIntMember norm = G.UNBOUND.construct();
			norm().call(a, norm);
			BigDecimal n = new BigDecimal(norm.v());
			b.setV(BigDecimalMath.sqrt(n, HighPrecisionAlgebra.getContext()));
		}
	};

	@Override
	public Procedure2<GaussianIntUnboundedMember, HighPrecisionMember> abs() {
		return ABS;
	}

	private final Procedure3<RationalMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> SBR =
			new Procedure3<RationalMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(RationalMember factor, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			BigInteger r = a.r();
			BigInteger i = a.i();
			r = r.multiply(factor.n()).divide(factor.d());
			i = i.multiply(factor.n()).divide(factor.d());
			b.setR(r);
			b.setI(i);
		}
	};
	
	@Override
	public Procedure3<RationalMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<HighPrecisionMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> SBHPR =
			new Procedure3<HighPrecisionMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			BigDecimal br = new BigDecimal(a.r()).multiply(factor.v());
			BigDecimal bi = new BigDecimal(a.i()).multiply(factor.v());
			if (br.signum() < 0)
				br = br.subtract(G.ONE_HALF);
			else
				br = br.add(G.ONE_HALF);
			if (bi.signum() < 0)
				bi = bi.subtract(G.ONE_HALF);
			else
				bi = bi.add(G.ONE_HALF);
			b.setR(br.toBigInteger());
			b.setI(bi.toBigInteger());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> scaleByHighPrecAndRound() {
		return SBHPR;
	}

	private final Procedure3<HighPrecisionMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> SBHP =
			new Procedure3<HighPrecisionMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			BigDecimal br = new BigDecimal(a.r()).multiply(factor.v());
			BigDecimal bi = new BigDecimal(a.i()).multiply(factor.v());
			b.setR(br.toBigInteger());
			b.setI(bi.toBigInteger());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<Double, GaussianIntUnboundedMember, GaussianIntUnboundedMember> SBDR =
			new Procedure3<Double, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(Double factor, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
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
			b.setR(br.toBigInteger());
			b.setI(bi.toBigInteger());
		}
	};

	@Override
	public Procedure3<Double, GaussianIntUnboundedMember, GaussianIntUnboundedMember> scaleByDoubleAndRound() {
		return SBDR;
	}

	private final Procedure3<Double, GaussianIntUnboundedMember, GaussianIntUnboundedMember> SBD =
			new Procedure3<Double, GaussianIntUnboundedMember, GaussianIntUnboundedMember>()
	{
		@Override
		public void call(Double factor, GaussianIntUnboundedMember a, GaussianIntUnboundedMember b) {
			BigDecimal scale = new BigDecimal(factor);
			BigDecimal br = new BigDecimal(a.r()).multiply(scale);
			BigDecimal bi = new BigDecimal(a.i()).multiply(scale);
			b.setR(br.toBigInteger());
			b.setI(bi.toBigInteger());
		}
	};

	@Override
	public Procedure3<Double, GaussianIntUnboundedMember, GaussianIntUnboundedMember> scaleByDouble() {
		return SBD;
	}

	@Override
	public Procedure3<GaussianIntUnboundedMember, GaussianIntUnboundedMember, GaussianIntUnboundedMember> scale() {
		return MUL;
	}
}
