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
package nom.bdezonia.zorbage.type.bool;

import java.lang.Integer;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BooleanAlgebra
	implements
		Algebra<BooleanAlgebra, BooleanMember>,
		Unity<BooleanMember>,
		Bounded<BooleanMember>,
		Ordered<BooleanMember>,
		LogicalOperations<BooleanMember>,
		PredSucc<BooleanMember>,
		Random<BooleanMember>,
		ConstructibleFromBooleans<BooleanMember>,
		ExactlyConstructibleFromBooleans<BooleanMember>
{
	@Override
	public String typeDescription() {
		return "Boolean";
	}
	
	public BooleanAlgebra() { }
	
	@Override
	public BooleanMember construct() {
		return new BooleanMember();
	}

	@Override
	public BooleanMember construct(BooleanMember other) {
		return new BooleanMember(other);
	}

	@Override
	public BooleanMember construct(String s) {
		return new BooleanMember(s);
	}
	
	@Override
	public BooleanMember construct(boolean... vals) {
		return new BooleanMember(vals);
	}

	private final Procedure3<BooleanMember, BooleanMember, BooleanMember> AND =
			new Procedure3<BooleanMember, BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember a, BooleanMember b, BooleanMember c) {
			c.setV(a.v() && b.v());
		}
	};

	@Override
	public Procedure3<BooleanMember, BooleanMember, BooleanMember> logicalAnd() {
		return AND;
	}

	private final Procedure3<BooleanMember, BooleanMember, BooleanMember> OR =
			new Procedure3<BooleanMember, BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember a, BooleanMember b, BooleanMember c) {
			c.setV(a.v() || b.v());
		}
	};

	@Override
	public Procedure3<BooleanMember, BooleanMember, BooleanMember> logicalOr() {
		return OR;
	}

	private final Procedure3<BooleanMember, BooleanMember, BooleanMember> XOR =
			new Procedure3<BooleanMember, BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember a, BooleanMember b, BooleanMember c) {
			c.setV(a.v() ^ b.v());
		}
	};

	@Override
	public Procedure3<BooleanMember, BooleanMember, BooleanMember> logicalXor() {
		return XOR;
	}

	private final Procedure2<BooleanMember, BooleanMember> NOT =
			new Procedure2<BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember a, BooleanMember b) {
			b.setV(!a.v());
		}
	};

	@Override
	public Procedure2<BooleanMember, BooleanMember> logicalNot() {
		return NOT;
	}
	
	private final Procedure3<BooleanMember, BooleanMember, BooleanMember> ANDNOT =
			new Procedure3<BooleanMember, BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember a, BooleanMember b, BooleanMember c) {
			c.setV(a.v() && !b.v());
		}
	};

	@Override
	public Procedure3<BooleanMember, BooleanMember, BooleanMember> logicalAndNot() {
		return ANDNOT;
	}

	private final Procedure4<BooleanMember, BooleanMember, BooleanMember, BooleanMember> TERN =
			new Procedure4<BooleanMember, BooleanMember, BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember a, BooleanMember b, BooleanMember c, BooleanMember d) {
			d.setV(a.v() ? b.v() : c.v());
		}
	};

	// not an override
	public Procedure4<BooleanMember, BooleanMember, BooleanMember, BooleanMember> ternary() {
		return TERN;
	}

	private final Function2<Boolean,BooleanMember,BooleanMember> LS =
			new Function2<Boolean, BooleanMember, BooleanMember>()
	{
		@Override
		public Boolean call(BooleanMember a, BooleanMember b) {
			return !a.v() && b.v();
		}
	};
			
	@Override
	public Function2<Boolean,BooleanMember,BooleanMember> isLess() {
		return LS;
	}

	private final Function2<Boolean,BooleanMember,BooleanMember> LSE =
			new Function2<Boolean, BooleanMember, BooleanMember>()
	{
		@Override
		public Boolean call(BooleanMember a, BooleanMember b) {
			if (a.v()) {
				return b.v();
			}
			else {
				return true;
			}
		}
	};
			
	@Override
	public Function2<Boolean,BooleanMember,BooleanMember> isLessEqual() {
		return LSE;
	}


	private final Function2<Boolean,BooleanMember,BooleanMember> GR =
			new Function2<Boolean, BooleanMember, BooleanMember>()
	{
		@Override
		public Boolean call(BooleanMember a, BooleanMember b) {
			return a.v() && !b.v();
		}
	};
			
	@Override
	public Function2<Boolean,BooleanMember,BooleanMember> isGreater() {
		return GR;
	}

	private final Function2<Boolean,BooleanMember,BooleanMember> GRE =
			new Function2<Boolean, BooleanMember, BooleanMember>()
	{
		@Override
		public Boolean call(BooleanMember a, BooleanMember b) {
			if (a.v()) {
				return true;
			}
			else {
				return !b.v();
			}
		}
	};
			
	@Override
	public Function2<Boolean,BooleanMember,BooleanMember> isGreaterEqual() {
		return GRE;
	}

	private final Function2<java.lang.Integer,BooleanMember,BooleanMember> CMP =
			new Function2<Integer, BooleanMember, BooleanMember>()
	{
		@Override
		public Integer call(BooleanMember a, BooleanMember b) {
			if (isLess().call(a, b)) return -1;
			if (isGreater().call(a, b)) return 1;
			return 0;
		}
	};
	
	@Override
	public Function2<java.lang.Integer,BooleanMember,BooleanMember> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,BooleanMember> SIG =
			new Function1<Integer, BooleanMember>()
	{
		@Override
		public Integer call(BooleanMember a) {
			if (a.v()) return 1;
			return 0;
		}
	};
	
	@Override
	public Function1<java.lang.Integer,BooleanMember> signum() {
		return SIG;
	}

	private final Procedure1<BooleanMember> MAXB =
			new Procedure1<BooleanMember>()
	{
		@Override
		public void call(BooleanMember a) {
			a.setV(true);
		}
	};

	@Override
	public Procedure1<BooleanMember> maxBound() {
		return MAXB;
	}

	private final Procedure1<BooleanMember> MINB =
			new Procedure1<BooleanMember>()
	{
		@Override
		public void call(BooleanMember a) {
			a.setV(false);
		}
	};

	@Override
	public Procedure1<BooleanMember> minBound() {
		return MINB;
	}

	private final Function2<Boolean,BooleanMember,BooleanMember> EQ =
			new Function2<Boolean, BooleanMember, BooleanMember>()
	{
		@Override
		public Boolean call(BooleanMember a, BooleanMember b) {
			return a.v() == b.v();
		}
	};

	@Override
	public Function2<Boolean,BooleanMember,BooleanMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,BooleanMember,BooleanMember> NEQ =
			new Function2<Boolean, BooleanMember, BooleanMember>()
	{
		@Override
		public Boolean call(BooleanMember a, BooleanMember b) {
			return a.v() != b.v();
		}
	};

	@Override
	public Function2<Boolean,BooleanMember,BooleanMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<BooleanMember,BooleanMember> ASSIGN =
			new Procedure2<BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember from, BooleanMember to) {
			to.set(from);
		}
	};

	@Override
	public Procedure2<BooleanMember,BooleanMember> assign() {
		return ASSIGN;
	}

	private final Procedure3<BooleanMember, BooleanMember, BooleanMember> MIN =
			new Procedure3<BooleanMember, BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember a, BooleanMember b, BooleanMember c) {
			Min.compute(G.BOOL, a, b, c);
		}
	};

	@Override
	public Procedure3<BooleanMember, BooleanMember, BooleanMember> min() {
		return MIN;
	}

	private final Procedure3<BooleanMember, BooleanMember, BooleanMember> MAX =
			new Procedure3<BooleanMember, BooleanMember, BooleanMember>()
	{
		@Override
		public void call(BooleanMember a, BooleanMember b, BooleanMember c) {
			Max.compute(G.BOOL, a, b, c);
		}
	};

	@Override
	public Procedure3<BooleanMember, BooleanMember, BooleanMember> max() {
		return MAX;
	}

	private final Procedure1<BooleanMember> ONE =
			new Procedure1<BooleanMember>()
	{
		@Override
		public void call(BooleanMember a) {
			a.setV(true);
		}
	};

	@Override
	public Procedure1<BooleanMember> unity() {
		return ONE;
	}

	private final Procedure1<BooleanMember> RAND =
			new Procedure1<BooleanMember>()
	{
		@Override
		public void call(BooleanMember a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			a.setV(rng.nextBoolean());
		}
	};

	@Override
	public Procedure1<BooleanMember> random() {
		return RAND;
	}

	private final Function1<Boolean, BooleanMember> ISZERO =
			new Function1<Boolean, BooleanMember>()
	{
		@Override
		public Boolean call(BooleanMember a) {
			return a.v() == false;
		}
	};

	@Override
	public Function1<Boolean, BooleanMember> isZero() {
		return ISZERO;
	}

	private final Procedure1<BooleanMember> ZERO =
			new Procedure1<BooleanMember>()
	{
		@Override
		public void call(BooleanMember a) {
			a.setV(false);
		}
	};

	@Override
	public Procedure1<BooleanMember> zero() {
		return ZERO;
	}

	@Override
	public Procedure2<BooleanMember, BooleanMember> pred() {
		return NOT;
	}

	@Override
	public Procedure2<BooleanMember, BooleanMember> succ() {
		return NOT;
	}

	private final Function1<Boolean, BooleanMember> ISUNITY =
			new Function1<Boolean, BooleanMember>()
	{
		@Override
		public Boolean call(BooleanMember a) {
			return a.v() == true;
		}
	};

	@Override
	public Function1<Boolean, BooleanMember> isUnity() {
		return ISUNITY;
	}

	@Override
	public BooleanMember constructExactly(boolean... vals) {
		BooleanMember v = construct();
		v.setFromBooleansExact(vals);
		return v;
	}

}
