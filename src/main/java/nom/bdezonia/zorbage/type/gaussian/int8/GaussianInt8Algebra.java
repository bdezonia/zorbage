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
package nom.bdezonia.zorbage.type.gaussian.int8;

import java.math.BigDecimal;
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
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GaussianInt8Algebra
	implements
		EuclideanDomain<GaussianInt8Algebra, GaussianInt8Member, SignedInt32Member>,
		Random<GaussianInt8Member>,
		Tolerance<GaussianInt8Member,GaussianInt8Member>,
		Norm<GaussianInt8Member, SignedInt32Member>,
		Conjugate<GaussianInt8Member>,
		ScaleByOneHalf<GaussianInt8Member>,
		ScaleByTwo<GaussianInt8Member>,
		AbsoluteValue<GaussianInt8Member, HighPrecisionMember>
{
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

	private final Procedure2<GaussianInt8Member, SignedInt32Member> NORM =
			new Procedure2<GaussianInt8Member, SignedInt32Member>()
	{
		@Override
		public void call(GaussianInt8Member a, SignedInt32Member b) {
			int val = ((int)a.r)*a.r + ((int)a.i)*a.i;
			if (val < 0)
				throw new IllegalArgumentException("overflow in norm calculation");
			b.setV(val);
		}
	};

	@Override
	public Procedure2<GaussianInt8Member, SignedInt32Member> norm() {
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
			throw new IllegalArgumentException("code not done yet");
			//Gcd.compute(G.GAUSS16, a, b, c);
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
			throw new IllegalArgumentException("code not done yet");
			//Lcm.compute(G.GAUSS16, a, b, c);
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
			throw new IllegalArgumentException("code not done yet");
			//GaussianInt16Member m = G.GAUSS16.construct();
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			throw new IllegalArgumentException("code not done yet");
			//GaussianInt16Member d = G.GAUSS16.construct();
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			throw new IllegalArgumentException("code not done yet");
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			SignedInt32Member norm = G.INT32.construct();
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
			SignedInt32Member norm = G.INT32.construct();
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
			SignedInt32Member norm = G.INT32.construct();
			norm().call(a, norm);
			BigDecimal n = new BigDecimal(norm.v());
			b.setV(BigDecimalMath.sqrt(n, HighPrecisionAlgebra.getContext()));
		}
	};

	@Override
	public Procedure2<GaussianInt8Member, HighPrecisionMember> abs() {
		return ABS;
	}
}
