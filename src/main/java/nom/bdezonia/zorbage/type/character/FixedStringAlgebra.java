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
package nom.bdezonia.zorbage.type.character;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Norm;
import nom.bdezonia.zorbage.algebra.Ordered;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.character.FixedStringMember;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;

// TODO What about equals ignoring case??

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FixedStringAlgebra
	implements
		Algebra<FixedStringAlgebra, FixedStringMember>,
		Addition<FixedStringMember>,
		Norm<FixedStringMember, SignedInt32Member>,
		Ordered<FixedStringMember>
{
	@Override
	public FixedStringMember construct() {
		return new FixedStringMember();
	}

	@Override
	public FixedStringMember construct(FixedStringMember other) {
		return other.duplicate();
	}

	@Override
	public FixedStringMember construct(String str) {
		return new FixedStringMember(str);
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> EQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) == 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> NEQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) != 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<FixedStringMember, FixedStringMember> ASSIGN =
			new Procedure2<FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b) {
			a.setV(b.v());
		}
	};
	
	@Override
	public Procedure2<FixedStringMember, FixedStringMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<FixedStringMember> ZERO =
			new Procedure1<FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a) {
			a.setV("");
		}
	};
			
	@Override
	public Procedure1<FixedStringMember> zero() {
		return ZERO;
	}

	private final Function1<Boolean, FixedStringMember> ISZERO =
			new Function1<Boolean, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a) {
			return a.v().length() == 0;
		}
	};

	@Override
	public Function1<Boolean, FixedStringMember> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure2<FixedStringMember, FixedStringMember> negate() {
		return ASSIGN;  // ignore negations: unsigned numbers do similar things
	}

	private final Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> ADD =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			c.setV(a.v() + b.v());
		}
	};

	@Override
	public Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> add() {
		return ADD;
	}

	private final Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> SUB =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			String aTr = a.v();
			String bTr = b.v();
			if (aTr.endsWith(bTr)) {
				int offset = aTr.lastIndexOf(bTr);
				c.setV(aTr.substring(0,offset));
			}
			else {
				c.setV(a.v());
			}
		}
	};

	@Override
	public Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> LESS =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isLess() {
		// TODO Auto-generated method stub
		return LESS;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> LESSEQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isLessEqual() {
		return LESSEQ;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> GREAT =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean, FixedStringMember, FixedStringMember> GREATEQ =
			new Function2<Boolean, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Boolean call(FixedStringMember a, FixedStringMember b) {
			return compare().call(a, b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean, FixedStringMember, FixedStringMember> isGreaterEqual() {
		return GREATEQ;
	}

	private final Function2<Integer, FixedStringMember, FixedStringMember> CMP =
			new Function2<Integer, FixedStringMember, FixedStringMember>()
	{
		@Override
		public Integer call(FixedStringMember a, FixedStringMember b) {
			return a.v().compareTo(b.v());
		}
	};

	@Override
	public Function2<Integer, FixedStringMember, FixedStringMember> compare() {
		return CMP;
	}

	private final Function1<Integer, FixedStringMember> SIGNUM =
			new Function1<Integer, FixedStringMember>()
	{
		@Override
		public Integer call(FixedStringMember a) {
			if (a.v().length() == 0)
				return 0;
			return 1;
		}
	};
	
	@Override
	public Function1<Integer, FixedStringMember> signum() {
		return SIGNUM;
	}

	private final Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> MIN =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			if (isLess().call(a, b))
				c.setV(a.v());
			else
				c.setV(b.v());
		}
	};

	@Override
	public Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> min() {
		return MIN;
	}

	private final Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> MAX =
			new Procedure3<FixedStringMember, FixedStringMember, FixedStringMember>()
	{
		@Override
		public void call(FixedStringMember a, FixedStringMember b, FixedStringMember c) {
			if (isGreater().call(a, b))
				c.setV(a.v());
			else
				c.setV(b.v());
		}
	};

	@Override
	public Procedure3<FixedStringMember, FixedStringMember, FixedStringMember> max() {
		return MAX;
	}

	private final Procedure2<FixedStringMember, SignedInt32Member> NORM =
			new Procedure2<FixedStringMember, SignedInt32Member>()
	{
		@Override
		public void call(FixedStringMember a, SignedInt32Member b) {
			b.setV(a.v().length());
		}
	};

	@Override
	public Procedure2<FixedStringMember, SignedInt32Member> norm() {
		return NORM;
	}

}
