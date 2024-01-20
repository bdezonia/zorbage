/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.rational;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.CompoundType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.NumberType;
import nom.bdezonia.zorbage.algebra.type.markers.RationalType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
import nom.bdezonia.zorbage.algorithm.PowerAny;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RationalAlgebra
	implements
		OrderedField<RationalAlgebra,RationalMember,RationalMember>,
		Scale<RationalMember,RationalMember>,
		Norm<RationalMember,RationalMember>,
		ScaleByHighPrec<RationalMember>,
		ScaleByRational<RationalMember>,
		ModularDivision<RationalMember>,
		Tolerance<RationalMember,RationalMember>,
		ScaleByOneHalf<RationalMember>,
		ScaleByTwo<RationalMember>,
		ConstructibleFromBytes<RationalMember>,
		ConstructibleFromShorts<RationalMember>,
		ConstructibleFromInts<RationalMember>,
		ConstructibleFromLongs<RationalMember>,
		ConstructibleFromBigIntegers<RationalMember>,
		ExactlyConstructibleFromBytes<RationalMember>,
		ExactlyConstructibleFromShorts<RationalMember>,
		ExactlyConstructibleFromInts<RationalMember>,
		ExactlyConstructibleFromLongs<RationalMember>,
		ExactlyConstructibleFromBigIntegers<RationalMember>,
		CompoundType,
		ExactType,
		NumberType,
		RationalType,
		SignedType,
		UnityIncludedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "Unbounded rational number";
	}

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
			return a.n.signum() == 0;
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

	private final Procedure3<RationalMember, RationalMember, RationalMember> DIVIDE =
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
		return DIVIDE;
	}

	// TODO: I've seen rationals div/mod integers. Not rationals div/mod rationals. Maybe this is wrong.
	
	private final Procedure3<RationalMember, RationalMember, RationalMember> DIV =
			new Procedure3<RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember d) {
			RationalMember tmp = G.RAT.construct();
			divide().call(a, b, tmp);
			BigDecimal untruncated = tmp.v();
			d.setV(untruncated.toBigInteger());
		}
	};

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> div() {
		return DIV;
	}

	private final Procedure3<RationalMember, RationalMember, RationalMember> MOD =
			new Procedure3<RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember m) {
			// mod = a - (a div b) * b
			RationalMember d = G.RAT.construct();
			RationalMember tmp = G.RAT.construct();
			div().call(a, b, d);
			multiply().call(b, d, tmp);
			subtract().call(a, tmp, m);
		}
	};
	
	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> mod() {
		return MOD;
	}

	private final Procedure4<RationalMember, RationalMember, RationalMember, RationalMember> DIVMOD =
			new Procedure4<RationalMember, RationalMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(RationalMember a, RationalMember b, RationalMember d, RationalMember m) {
			RationalMember tmp = G.RAT.construct();
			RationalMember tmpD = G.RAT.construct();
			divide().call(a, b, tmp);
			BigDecimal untruncated = tmp.v();
			tmpD.setV(untruncated.toBigInteger());
			multiply().call(b, tmpD, tmp);
			subtract().call(a, tmp, m);
			assign().call(tmpD, d);
		}
	};

	@Override
	public Procedure4<RationalMember, RationalMember, RationalMember, RationalMember> divMod() {
		return DIVMOD;
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

	private final Function2<Boolean, RationalMember, RationalMember> LESS =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return compare().call(a, b) < 0;
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isLess() {
		return LESS;
	}

	private final Function2<Boolean, RationalMember, RationalMember> LEQ =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return compare().call(a, b) <= 0;
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isLessEqual() {
		return LEQ;
	}

	private final Function2<Boolean, RationalMember, RationalMember> GREAT =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return compare().call(a, b) > 0;
		}
	};

	@Override
	public Function2<Boolean, RationalMember, RationalMember> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, RationalMember, RationalMember> GEQ =
			new Function2<Boolean, RationalMember, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a, RationalMember b) {
			return compare().call(a, b) >= 0;
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
			return a.n.multiply(b.d).compareTo(b.n.multiply(a.d));
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
			return a.n.signum();
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

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> scale() {
		return MUL;
	}

	private final Procedure3<HighPrecisionMember, RationalMember, RationalMember> SBHP =
			new Procedure3<HighPrecisionMember, RationalMember, RationalMember>()
	{
		@Override
		public void call(HighPrecisionMember a, RationalMember b, RationalMember c) {
			BigDecimal tmp = a.v();
			tmp = tmp.multiply(new BigDecimal(b.n()));
			c.setV(tmp.toBigInteger(), b.d());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, RationalMember, RationalMember> scaleByHighPrec() {
		return SBHP;
	}

	@Override
	public Procedure3<RationalMember, RationalMember, RationalMember> scaleByRational() {
		return MUL;
	}

	private final Function3<Boolean, RationalMember, RationalMember, RationalMember> WITHIN =
			new Function3<Boolean, RationalMember, RationalMember, RationalMember>()
	{
		
		@Override
		public Boolean call(RationalMember tol, RationalMember a, RationalMember b) {
			return NumberWithin.compute(G.RAT, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, RationalMember, RationalMember, RationalMember> within() {
		return WITHIN;
	}

	private final Procedure3<java.lang.Integer, RationalMember, RationalMember> STWO =
			new Procedure3<java.lang.Integer, RationalMember, RationalMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, RationalMember a, RationalMember b) {
			ScaleHelper.compute(G.RAT, G.RAT, new RationalMember(2,1), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, RationalMember, RationalMember> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, RationalMember, RationalMember> SHALF =
			new Procedure3<java.lang.Integer, RationalMember, RationalMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, RationalMember a, RationalMember b) {
			ScaleHelper.compute(G.RAT, G.RAT, new RationalMember(1,2), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, RationalMember, RationalMember> scaleByOneHalf() {
		return SHALF;
	}

	private final Function1<Boolean, RationalMember> ISUNITY =
			new Function1<Boolean, RationalMember>()
	{
		@Override
		public Boolean call(RationalMember a) {
			return a.n.equals(BigInteger.ONE) && a.d.equals(BigInteger.ONE);
		}
	};

	@Override
	public Function1<Boolean, RationalMember> isUnity() {
		return ISUNITY;
	}

	@Override
	public RationalMember constructExactly(BigInteger... vals) {
		RationalMember v = construct();
		v.setFromBigIntegersExact(vals);
		return v;
	}

	@Override
	public RationalMember constructExactly(long... vals) {
		RationalMember v = construct();
		v.setFromLongsExact(vals);
		return v;
	}

	@Override
	public RationalMember constructExactly(int... vals) {
		RationalMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}

	@Override
	public RationalMember constructExactly(short... vals) {
		RationalMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public RationalMember constructExactly(byte... vals) {
		RationalMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public RationalMember construct(BigInteger... vals) {
		RationalMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public RationalMember construct(long... vals) {
		RationalMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public RationalMember construct(int... vals) {
		RationalMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public RationalMember construct(short... vals) {
		RationalMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public RationalMember construct(byte... vals) {
		RationalMember v = construct();
		v.setFromBytes(vals);
		return v;
	}
}
