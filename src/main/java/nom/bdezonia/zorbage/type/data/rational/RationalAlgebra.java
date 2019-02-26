/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.rational;

import java.math.BigInteger;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.PowerAny;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.OrderedField;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.data.bigint.UnboundedIntMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RationalAlgebra
	implements
		OrderedField<RationalAlgebra,RationalMember>,
		Scale<RationalMember,UnboundedIntMember>,
		Norm<RationalMember,RationalMember>
{
	@Override
	public RationalMember construct() {
		return new RationalMember();
	}

	@Override
	public RationalMember construct(RationalMember other) {
		return new RationalMember(other);
	}

	@Override
	public RationalMember construct(String str) {
		return new RationalMember(str);
	}

	private final Function2<Boolean, RationalMember, RationalMember> EQ =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return a.n.equals(b.n) && a.d.equals(b.d);
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, RationalMember, RationalMember> NEQ =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<RationalMember, RationalMember> ASS =
			new Procedure2<RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<RationalMember, RationalMember> assign() {
		return ASS;
	}

	private final Function1<Boolean, RationalMember> ISZER =
			new Function1<Boolean, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a) {
			return a.n.equals(BigInteger.ZERO);
		}
	};

	@Override
	public Function1<Boolean, RationalMember> isZero() {
		return ISZER;
	}

	private final Procedure1<RationalMember> ZER =
			new Procedure1<RationalMember>()
	{
		@Override
		public void call(RationalMember a) {
			a.primitiveInit();
		}
	};

	@Override
	public Procedure1<RationalMember> zero() {
		return ZER;
	}

	private final Procedure2<RationalMember, RationalMember> NEG =
			new Procedure2<RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b) {
			b.n = a.n.negate();
			b.d = a.d;
		}
	};

	@Override
	public Procedure2<RationalMember, RationalMember> negate() {
		return NEG;
	}

	private final Procedure3<RationalMember, RationalMember, RationalMember> ADD =
			new Procedure3<RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember c) {
			BigInteger n = a.n.multiply(b.d).add(b.n.multiply(a.d));
			BigInteger d = a.d.multiply(b.d);
			c.setV(n, d);
		}
	};

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> add() {
		return ADD;
	}

	private final Procedure3<RationalMember, RationalMember, RationalMember> SUB =
			new Procedure3<RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember c) {
			BigInteger n = a.n.multiply(b.d).subtract(b.n.multiply(a.d));
			BigInteger d = a.d.multiply(b.d);
			c.setV(n, d);
		}
	};

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> subtract() {
		return SUB;
	}

	private final Procedure3<RationalMember, RationalMember, RationalMember> MUL=
			new Procedure3<RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember c) {
			BigInteger n = a.n.multiply(b.n);
			BigInteger d = a.d.multiply(b.d);
			c.setV(n, d);
		}
	};

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> multiply() {
		return MUL;
	}

	private final Procedure3<RationalMember, RationalMember, RationalMember> DIV =
			new Procedure3<RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember c) {
			BigInteger n = a.n.multiply(b.d);
			BigInteger d = a.d.multiply(b.n);
			c.setV(n, d);
		}
	};

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> divide() {
		return DIV;
	}

	private final Procedure3<Integer, RationalMember, RationalMember> POWER =
			new Procedure3<Integer, RationalMember, RationalMember>()
	{
		@Override
		public void call(Integer power, RationalMember a, RationalMember b) {
			PowerAny.compute(G.RAT, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, RationalMember, RationalMember> power() {
		return POWER;
	}

	private final Procedure1<RationalMember> UNITY =
			new Procedure1<RationalMember>()
	{
		@Override
		public void call(RationalMember a) {
			a.n = BigInteger.ONE;
			a.d = BigInteger.ONE;
		}
	};

	@Override
	public Procedure1<RationalMember> unity() {
		return UNITY;
	}

	private final Procedure2<RationalMember, RationalMember> INV =
			new Procedure2<RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b) {
			b.setV(a.d, a.n);
		}
	};

	@Override
	public Procedure2<RationalMember, RationalMember> invert() {
		return INV;
	}

	private Function2<Boolean, RationalMember, RationalMember> LESS =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return compare().call(a,b) < 0;
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isLess() {
		return LESS;
	}

	private Function2<Boolean, RationalMember, RationalMember> LEQ =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return compare().call(a,b) <= 0;
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isLessEqual() {
		return LEQ;
	}

	private Function2<Boolean, RationalMember, RationalMember> GREAT =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return compare().call(a,b) > 0;
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isGreater() {
		return GREAT;
	}

	private Function2<Boolean, RationalMember, RationalMember> GEQ =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return compare().call(a,b) >= 0;
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isGreaterEqual() {
		return GEQ;
	}

	private final Function2<Integer, RationalMember, RationalMember> CMP =
			new Function2<Integer, RationalMember, RationalMember>()
	{
		@Override
		public Integer call(RationalMember a, RationalMember b) {
			int sig_a = signum().call(a);
			int sig_b = signum().call(b);
			if (sig_a < sig_b)
				return -1;
			if (sig_a > sig_b)
				return 1;
			// sig_a == sig_b
			if (sig_a == 0)
				return 0;
			if (a.n.multiply(b.d).compareTo(b.n.multiply(a.d)) < 0)
				return -1;
			return 1;
		}
	};

	@Override
	public Function2<Integer, RationalMember, RationalMember> compare() {
		return CMP;
	}

	private final Function1<Integer, RationalMember> SIG =
			new Function1<Integer, RationalMember>()
	{
		@Override
		public Integer call(RationalMember a) {
			return a.n.compareTo(BigInteger.ZERO);
		}
	};

	@Override
	public Function1<Integer, RationalMember> signum() {
		return SIG;
	}

	private final Procedure3<RationalMember, RationalMember, RationalMember> MIN =
			new Procedure3<RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember c) {
			Min.compute(G.RAT, a, b, c);
		}
	};

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> min() {
		return MIN;
	}

	private final Procedure3<RationalMember, RationalMember, RationalMember> MAX =
			new Procedure3<RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember c) {
			Max.compute(G.RAT, a, b, c);
		}
	};

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> max() {
		return MAX;
	}

	private final Procedure2<RationalMember, RationalMember> ABS =
			new Procedure2<RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b) {
			b.n = a.n.abs();
			b.d = a.d;
		}
	};
	
	@Override
	public Procedure2<RationalMember, RationalMember> abs() {
		return ABS;
	}

	@Override
	public Procedure2<RationalMember, RationalMember> norm() {
		return ABS;
	}

	private Procedure3<UnboundedIntMember, RationalMember, RationalMember> SCALE =
			new Procedure3<UnboundedIntMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(UnboundedIntMember a, RationalMember b, RationalMember c) {
			c.setV(b.n.multiply(a.v()), b.d);
		}
	};

	@Override
	public Procedure3<UnboundedIntMember, RationalMember, RationalMember> scale() {
		return SCALE;
	}

}