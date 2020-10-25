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
package nom.bdezonia.zorbage.type.gaussian.unbounded;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.AbsoluteValue;
import nom.bdezonia.zorbage.algebra.Conjugate;
import nom.bdezonia.zorbage.algebra.EuclideanDomain;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Norm;
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
public class GaussianIntUnboundedAlgebra
	implements
		EuclideanDomain<GaussianIntUnboundedAlgebra, GaussianIntUnboundedMember, UnboundedIntMember>,
		Tolerance<GaussianIntUnboundedMember,GaussianIntUnboundedMember>,
		Norm<GaussianIntUnboundedMember, UnboundedIntMember>,
		Conjugate<GaussianIntUnboundedMember>,
		ScaleByOneHalf<GaussianIntUnboundedMember>,
		ScaleByTwo<GaussianIntUnboundedMember>,
		AbsoluteValue<GaussianIntUnboundedMember, HighPrecisionMember>
{
	private static final GaussianIntUnboundedMember TWO = new GaussianIntUnboundedMember(BigInteger.valueOf(2), BigInteger.ZERO);

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
			return a.r.subtract(b.r).compareTo(tol.r) <= 0 && a.i.subtract(b.i).compareTo(tol.i) <= 0;
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
			multiply().call(a, TWO, b);
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
			div().call(a, TWO, b);
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
			throw new IllegalArgumentException("code not done yet");
			//Gcd.compute(G.GAUSS16, a, b, c);
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
			throw new IllegalArgumentException("code not done yet");
			//GaussianInt16Member m = G.GAUSS16.construct();
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			throw new IllegalArgumentException("code not done yet");
			//GaussianInt16Member d = G.GAUSS16.construct();
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			throw new IllegalArgumentException("code not done yet");
			//DivMod.compute(G.GAUSS16, a, b, d, m);
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
			return norm.v().and(BigInteger.ONE) == BigInteger.ZERO;
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
			return norm.v().and(BigInteger.ONE) == BigInteger.ONE;
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
			b.setV(n.sqrt(HighPrecisionAlgebra.getContext()));
		}
	};

	@Override
	public Procedure2<GaussianIntUnboundedMember, HighPrecisionMember> abs() {
		return ABS;
	}}
