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

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.IntegralDomain;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Random;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GaussianInt8Algebra
	implements
		IntegralDomain<GaussianInt8Algebra, GaussianInt8Member>,
		Random<GaussianInt8Member>,
		Tolerance<GaussianInt8Member,GaussianInt8Member>,
		Norm<GaussianInt8Member, SignedInt64Member>,
		Conjugate<GaussianInt8Member>
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
			int r = a.r()*b.r() - a.i()*b.i();
			int i = a.i()*b.r() + a.r()*b.i();
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
			// avoid overflow/underflow conditions by using longs
			long r = Math.abs(((long) a.r) - b.r);
			long i = Math.abs(((long) a.i) - b.i);
			return r <= tol.r && i <= tol.i;
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

	private final Procedure2<GaussianInt8Member, SignedInt64Member> NORM =
			new Procedure2<GaussianInt8Member, SignedInt64Member>()
	{
		@Override
		public void call(GaussianInt8Member a, SignedInt64Member b) {
			long val = ((long)a.r)*a.r + ((long)a.i)*a.i;
			if (val < 0)
				throw new IllegalArgumentException("overflow in norm calculation");
			b.setV(val);
		}
	};

	@Override
	public Procedure2<GaussianInt8Member, SignedInt64Member> norm() {
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
}
