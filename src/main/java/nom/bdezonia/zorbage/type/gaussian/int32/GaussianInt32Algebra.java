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

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.IntegralDomain;
import nom.bdezonia.zorbage.algebra.Random;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.algorithm.PowerNonNegative;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GaussianInt32Algebra
	implements
		IntegralDomain<GaussianInt32Algebra, GaussianInt32Member>,
		Random<GaussianInt32Member>,
		Tolerance<GaussianInt32Member,GaussianInt32Member>
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
			int r = a.r()*b.r() - a.i()*b.i();
			int i = a.i()*b.r() + a.r()*b.i();
			c.setR( r );
			c.setI( i );
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

}
