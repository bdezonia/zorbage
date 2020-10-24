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
package nom.bdezonia.zorbage.type.gaussian.int32;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

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
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GaussianInt32Algebra
	implements
		EuclideanDomain<GaussianInt32Algebra, GaussianInt32Member, SignedInt64Member>,
		Random<GaussianInt32Member>,
		Tolerance<GaussianInt32Member,GaussianInt32Member>,
		Norm<GaussianInt32Member, SignedInt64Member>,
		Conjugate<GaussianInt32Member>,
		ScaleByOneHalf<GaussianInt32Member>,
		ScaleByTwo<GaussianInt32Member>,
		AbsoluteValue<GaussianInt32Member, HighPrecisionMember>
{

	@Override
	public GaussianInt32Member construct() {
		return new GaussianInt32Member();
	}
	
	@Override
	public GaussianInt32Member construct(GaussianInt32Member other) {
		return new GaussianInt32Member(other);
	}
	
	@Override
	public GaussianInt32Member construct(String str) {
		return new GaussianInt32Member(str);
	}
	
	private final Function2<Boolean, GaussianInt32Member, GaussianInt32Member> EQ =
		new Function2<Boolean, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public Boolean call(GaussianInt32Member a, GaussianInt32Member b) {
			return a.r == b.r && a.i == b.i;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianInt32Member, GaussianInt32Member> isEqual() {
		return EQ;
	}
	
	private final Function2<Boolean, GaussianInt32Member, GaussianInt32Member> NEQ =
		new Function2<Boolean, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public Boolean call(GaussianInt32Member a, GaussianInt32Member b) {
			return a.r != b.r || a.i != b.i;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianInt32Member, GaussianInt32Member> isNotEqual() {
		return NEQ;
	}
	
	private final Procedure2<GaussianInt32Member, GaussianInt32Member> ASS =
			new Procedure2<GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<GaussianInt32Member, GaussianInt32Member> assign() {
		return ASS;
	}

	private final Procedure1<GaussianInt32Member> ZER =
			new Procedure1<GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<GaussianInt32Member> zero() {
		return ZER;
	}
	
	private final Function1<Boolean, GaussianInt32Member> ISZ = new
			Function1<Boolean, GaussianInt32Member>()
	{
		@Override
		public Boolean call(GaussianInt32Member a) {
			return a.r == 0 && a.i == 0;
		}
	};
	
	@Override
	public Function1<Boolean, GaussianInt32Member> isZero() {
		return ISZ;
	}

	private final Procedure2<GaussianInt32Member, GaussianInt32Member> NEG =
			new Procedure2<GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b) {
			b.setR(-a.r);
			b.setI(-a.i);
		}
	};
	
	@Override
	public Procedure2<GaussianInt32Member, GaussianInt32Member> negate() {
		return NEG;
	}
	
	private final Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> ADD =
			new Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b, GaussianInt32Member c) {
			c.r = a.r + b.r;
			c.i = a.i + b.i;
		}
	};

	@Override
	public Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> add() {
		return ADD;
	}
	
	private final Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> SUB =
			new Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b, GaussianInt32Member c) {
			c.r = a.r - b.r;
			c.i = a.i - b.i;
		}
	};
	
	@Override
	public Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> subtract() {
		return SUB;
	}
	
	private final Procedure3<GaussianInt32Member,GaussianInt32Member,GaussianInt32Member> MUL =
			new Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b, GaussianInt32Member c) {
			// for safety must use tmps
			long r = ((long)a.r)*b.r - ((long)a.i)*b.i;
			long i = ((long)a.i)*b.r + ((long)a.r)*b.i;
			c.setR( (int) r );
			c.setI( (int) i );
		}
	};

	@Override
	public Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> multiply() {
		return MUL;
	}
	
	private final Procedure3<java.lang.Integer,GaussianInt32Member,GaussianInt32Member> POWER = 
			new Procedure3<java.lang.Integer, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(java.lang.Integer power, GaussianInt32Member a, GaussianInt32Member b) {
			PowerNonNegative.compute(G.GAUSS32, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianInt32Member, GaussianInt32Member> power() {
		return POWER;
	}
	
	private final Procedure1<GaussianInt32Member> UNITY =
			new Procedure1<GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a) {
			a.r = 1;
			a.i = 0;
		}
	};
			
	@Override
	public Procedure1<GaussianInt32Member> unity() {
		return UNITY;
	}

	private final Function3<Boolean, GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> WITHIN =
			new Function3<Boolean, GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public Boolean call(GaussianInt32Member tol, GaussianInt32Member a, GaussianInt32Member b) {
			if (tol.r < 0 || tol.i < 0)
				throw new IllegalArgumentException("gaussian tolerances must have nonnegative components");
			// avoid overflow/underflow conditions by using longs
			long r = Math.abs(((long) a.r) - b.r);
			long i = Math.abs(((long) a.i) - b.i);
			return r <= tol.r && i <= tol.i;
		}
	};

	@Override
	public Function3<Boolean, GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> within() {
		return WITHIN;
	}

	private final Procedure1<GaussianInt32Member> RAND =
			new Procedure1<GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR( rng.nextInt() );
			a.setI( rng.nextInt() );
		}
	};
	
	@Override
	public Procedure1<GaussianInt32Member> random() {
		return RAND;
	}

	private final Procedure2<GaussianInt32Member, SignedInt64Member> NORM =
			new Procedure2<GaussianInt32Member, SignedInt64Member>()
	{
		@Override
		public void call(GaussianInt32Member a, SignedInt64Member b) {
			long val = ((long)a.r)*a.r + ((long)a.i)*a.i;
			if (val < 0)
				throw new IllegalArgumentException("overflow in norm calculation");
			b.setV(val);
		}
	};

	@Override
	public Procedure2<GaussianInt32Member, SignedInt64Member> norm() {
		return NORM;
	}

	private final Procedure2<GaussianInt32Member, GaussianInt32Member> CONJ =
			new Procedure2<GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b) {
			b.r = a.r;
			b.i = -a.i;
		}
	};

	@Override
	public Procedure2<GaussianInt32Member, GaussianInt32Member> conjugate() {
		return CONJ;
	}
	
	private final Procedure2<GaussianInt32Member, GaussianInt32Member> STWO =
			new Procedure2<GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b) {
			b.setR( a.r << 1);
			b.setI( a.i << 1);
		}
	};
	
	@Override
	public Procedure2<GaussianInt32Member, GaussianInt32Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure2<GaussianInt32Member, GaussianInt32Member> SHALF =
			new Procedure2<GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b) {
			b.setR( a.r >> 1);
			b.setI( a.i >> 1);
		}
	};
	
	@Override
	public Procedure2<GaussianInt32Member, GaussianInt32Member> scaleByOneHalf() {
		return SHALF;
	}

	private final Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> GCD =
			new Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b, GaussianInt32Member c) {
			throw new IllegalArgumentException("code not done yet");
			//Gcd.compute(G.GAUSS16, a, b, c);
		}
	};
	
	@Override
	public Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> gcd() {
		return GCD;
	}

	private final Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> LCM =
			new Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b, GaussianInt32Member c) {
			throw new IllegalArgumentException("code not done yet");
			//Lcm.compute(G.GAUSS16, a, b, c);
		}
	};

	@Override
	public Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> lcm() {
		return LCM;
	}

	private final Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> DIV =
			new Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b, GaussianInt32Member d) {
			throw new IllegalArgumentException("code not done yet");
			//GaussianInt16Member m = G.GAUSS16.construct();
			//DivMod.compute(G.GAUSS16, a, b, d, m);
		}
	};
	
	@Override
	public Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> div() {
		return DIV;
	}

	private final Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> MOD =
			new Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b, GaussianInt32Member m) {
			throw new IllegalArgumentException("code not done yet");
			//GaussianInt16Member d = G.GAUSS16.construct();
			//DivMod.compute(G.GAUSS16, a, b, d, m);
		}
	};
	

	@Override
	public Procedure3<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> mod() {
		return MOD;
	}

	private final Procedure4<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> DIVMOD =
			new Procedure4<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member, GaussianInt32Member>()
	{
		@Override
		public void call(GaussianInt32Member a, GaussianInt32Member b, GaussianInt32Member d, GaussianInt32Member m) {
			throw new IllegalArgumentException("code not done yet");
			//DivMod.compute(G.GAUSS16, a, b, d, m);
		}
	};
	

	@Override
	public Procedure4<GaussianInt32Member, GaussianInt32Member, GaussianInt32Member, GaussianInt32Member> divMod() {
		return DIVMOD;
	}

	private final Function1<Boolean, GaussianInt32Member> EVEN =
			new Function1<Boolean, GaussianInt32Member>()
	{
		@Override
		public Boolean call(GaussianInt32Member a) {
			SignedInt64Member norm = G.INT64.construct();
			norm().call(a, norm);
			return (norm.v() & 1) == 0;
		}
	};

	@Override
	public Function1<Boolean, GaussianInt32Member> isEven() {
		return EVEN;
	}

	private final Function1<Boolean, GaussianInt32Member> ODD =
			new Function1<Boolean, GaussianInt32Member>()
	{
		@Override
		public Boolean call(GaussianInt32Member a) {
			SignedInt64Member norm = G.INT64.construct();
			norm().call(a, norm);
			return (norm.v() & 1) == 1;
		}
	};

	@Override
	public Function1<Boolean, GaussianInt32Member> isOdd() {
		return ODD;
	}

	private final Procedure2<GaussianInt32Member, HighPrecisionMember> ABS =
			new Procedure2<GaussianInt32Member, HighPrecisionMember>()
	{
		@Override
		public void call(GaussianInt32Member a, HighPrecisionMember b) {
			SignedInt64Member norm = G.INT64.construct();
			norm().call(a, norm);
			BigDecimal n = BigDecimal.valueOf(norm.v());
			b.setV(n.sqrt(HighPrecisionAlgebra.getContext()));
		}
	};

	@Override
	public Procedure2<GaussianInt32Member, HighPrecisionMember> abs() {
		return ABS;
	}
}
