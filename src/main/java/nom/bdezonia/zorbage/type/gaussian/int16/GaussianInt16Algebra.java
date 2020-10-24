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
package nom.bdezonia.zorbage.type.gaussian.int16;

import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.PrincipalIdealDomain;
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
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class GaussianInt16Algebra
	implements
		PrincipalIdealDomain<GaussianInt16Algebra, GaussianInt16Member>,
		Random<GaussianInt16Member>,
		Tolerance<GaussianInt16Member,GaussianInt16Member>,
		Norm<GaussianInt16Member, SignedInt32Member>,
		Conjugate<GaussianInt16Member>,
		ScaleByOneHalf<GaussianInt16Member>,
		ScaleByTwo<GaussianInt16Member>
{

	@Override
	public GaussianInt16Member construct() {
		return new GaussianInt16Member();
	}
	
	@Override
	public GaussianInt16Member construct(GaussianInt16Member other) {
		return new GaussianInt16Member(other);
	}
	
	@Override
	public GaussianInt16Member construct(String str) {
		return new GaussianInt16Member(str);
	}
	
	private final Function2<Boolean, GaussianInt16Member, GaussianInt16Member> EQ =
		new Function2<Boolean, GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public Boolean call(GaussianInt16Member a, GaussianInt16Member b) {
			return a.r == b.r && a.i == b.i;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianInt16Member, GaussianInt16Member> isEqual() {
		return EQ;
	}
	
	private final Function2<Boolean, GaussianInt16Member, GaussianInt16Member> NEQ =
		new Function2<Boolean, GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public Boolean call(GaussianInt16Member a, GaussianInt16Member b) {
			return a.r != b.r || a.i != b.i;
		}
	};
	
	@Override
	public Function2<Boolean, GaussianInt16Member, GaussianInt16Member> isNotEqual() {
		return NEQ;
	}
	
	private final Procedure2<GaussianInt16Member, GaussianInt16Member> ASS =
			new Procedure2<GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a, GaussianInt16Member b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<GaussianInt16Member, GaussianInt16Member> assign() {
		return ASS;
	}

	private final Procedure1<GaussianInt16Member> ZER =
			new Procedure1<GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<GaussianInt16Member> zero() {
		return ZER;
	}
	
	private final Function1<Boolean, GaussianInt16Member> ISZ = new
			Function1<Boolean, GaussianInt16Member>()
	{
		@Override
		public Boolean call(GaussianInt16Member a) {
			return a.r == 0 && a.i == 0;
		}
	};
	
	@Override
	public Function1<Boolean, GaussianInt16Member> isZero() {
		return ISZ;
	}

	private final Procedure2<GaussianInt16Member, GaussianInt16Member> NEG =
			new Procedure2<GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a, GaussianInt16Member b) {
			b.setR(-a.r);
			b.setI(-a.i);
		}
	};
	
	@Override
	public Procedure2<GaussianInt16Member, GaussianInt16Member> negate() {
		return NEG;
	}
	
	private final Procedure3<GaussianInt16Member, GaussianInt16Member, GaussianInt16Member> ADD =
			new Procedure3<GaussianInt16Member, GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a, GaussianInt16Member b, GaussianInt16Member c) {
			c.r = (short) (a.r + b.r);
			c.i = (short) (a.i + b.i);
		}
	};

	@Override
	public Procedure3<GaussianInt16Member, GaussianInt16Member, GaussianInt16Member> add() {
		return ADD;
	}
	
	private final Procedure3<GaussianInt16Member, GaussianInt16Member, GaussianInt16Member> SUB =
			new Procedure3<GaussianInt16Member, GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a, GaussianInt16Member b, GaussianInt16Member c) {
			c.r = (short) (a.r - b.r);
			c.i = (short) (a.i - b.i);
		}
	};
	
	@Override
	public Procedure3<GaussianInt16Member, GaussianInt16Member, GaussianInt16Member> subtract() {
		return SUB;
	}
	
	private final Procedure3<GaussianInt16Member,GaussianInt16Member,GaussianInt16Member> MUL =
			new Procedure3<GaussianInt16Member, GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a, GaussianInt16Member b, GaussianInt16Member c) {
			// for safety must use tmps
			int r = ((int)a.r)*b.r - ((int)a.i*b.i);
			int i = ((int)a.i)*b.r + ((int)a.r*b.i);
			c.setR( r );
			c.setI( i );
		}
	};

	@Override
	public Procedure3<GaussianInt16Member, GaussianInt16Member, GaussianInt16Member> multiply() {
		return MUL;
	}
	
	private final Procedure3<java.lang.Integer,GaussianInt16Member,GaussianInt16Member> POWER = 
			new Procedure3<java.lang.Integer, GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(java.lang.Integer power, GaussianInt16Member a, GaussianInt16Member b) {
			PowerNonNegative.compute(G.GAUSS16, power, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, GaussianInt16Member, GaussianInt16Member> power() {
		return POWER;
	}
	
	private final Procedure1<GaussianInt16Member> UNITY =
			new Procedure1<GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a) {
			a.r = 1;
			a.i = 0;
		}
	};
			
	@Override
	public Procedure1<GaussianInt16Member> unity() {
		return UNITY;
	}

	private final Function3<Boolean, GaussianInt16Member, GaussianInt16Member, GaussianInt16Member> WITHIN =
			new Function3<Boolean, GaussianInt16Member, GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public Boolean call(GaussianInt16Member tol, GaussianInt16Member a, GaussianInt16Member b) {
			if (tol.r < 0 || tol.i < 0)
				throw new IllegalArgumentException("gaussian tolerances must have nonnegative components");
			// avoid overflow/underflow conditions by using longs
			int r = Math.abs(((int) a.r) - b.r);
			int i = Math.abs(((int) a.i) - b.i);
			return r <= tol.r && i <= tol.i;
		}
	};

	@Override
	public Function3<Boolean, GaussianInt16Member, GaussianInt16Member, GaussianInt16Member> within() {
		return WITHIN;
	}

	private final Procedure1<GaussianInt16Member> RAND =
			new Procedure1<GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setR( rng.nextInt(0x10000) );
			a.setI( rng.nextInt(0x10000) );
		}
	};
	
	@Override
	public Procedure1<GaussianInt16Member> random() {
		return RAND;
	}

	private final Procedure2<GaussianInt16Member, SignedInt32Member> NORM =
			new Procedure2<GaussianInt16Member, SignedInt32Member>()
	{
		@Override
		public void call(GaussianInt16Member a, SignedInt32Member b) {
			int val = ((int)a.r)*a.r + ((int)a.i)*a.i;
			if (val < 0)
				throw new IllegalArgumentException("overflow in norm calculation");
			b.setV(val);
		}
	};

	@Override
	public Procedure2<GaussianInt16Member, SignedInt32Member> norm() {
		return NORM;
	}

	private final Procedure2<GaussianInt16Member, GaussianInt16Member> CONJ =
			new Procedure2<GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a, GaussianInt16Member b) {
			b.r = a.r;
			b.i = (short) -a.i;
		}
	};

	@Override
	public Procedure2<GaussianInt16Member, GaussianInt16Member> conjugate() {
		return CONJ;
	}
	
	private final Procedure2<GaussianInt16Member, GaussianInt16Member> STWO =
			new Procedure2<GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a, GaussianInt16Member b) {
			b.setR( a.r << 1);
			b.setI( a.i << 1);
		}
	};
	
	@Override
	public Procedure2<GaussianInt16Member, GaussianInt16Member> scaleByTwo() {
		return STWO;
	}

	private final Procedure2<GaussianInt16Member, GaussianInt16Member> SHALF =
			new Procedure2<GaussianInt16Member, GaussianInt16Member>()
	{
		@Override
		public void call(GaussianInt16Member a, GaussianInt16Member b) {
			b.setR( a.r >> 1);
			b.setI( a.i >> 1);
		}
	};
	
	@Override
	public Procedure2<GaussianInt16Member, GaussianInt16Member> scaleByOneHalf() {
		return SHALF;
	}
}
