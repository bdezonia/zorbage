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
package nom.bdezonia.zorbage.type.gaussian.int64;

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
import nom.bdezonia.zorbage.algebra.ScaleByOneHalf;
import nom.bdezonia.zorbage.algebra.ScaleByTwo;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
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
			BigInteger r = BigInteger.valueOf(a.r).subtract(BigInteger.valueOf(b.r)).abs();
			BigInteger i = BigInteger.valueOf(a.i).subtract(BigInteger.valueOf(b.i)).abs();
			return r.compareTo(BigInteger.valueOf(tol.r)) <= 0 && i.compareTo(BigInteger.valueOf(tol.i)) <= 0;
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
			throw new IllegalArgumentException("code not done yet");
			//Gcd.compute(G.GAUSS16, a, b, c);
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
			throw new IllegalArgumentException("code not done yet");
			//GaussianInt16Member m = G.GAUSS16.construct();
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			throw new IllegalArgumentException("code not done yet");
			//GaussianInt16Member d = G.GAUSS16.construct();
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			throw new IllegalArgumentException("code not done yet");
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			return norm.v().and(BigInteger.ONE) == BigInteger.ZERO;
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
			return norm.v().and(BigInteger.ONE) == BigInteger.ONE;
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
}
