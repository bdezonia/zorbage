/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.bool;

import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.type.algebra.Bounded;
import nom.bdezonia.zorbage.type.algebra.CommutativeRingWithUnity;
import nom.bdezonia.zorbage.type.algebra.LogicalOperations;
import nom.bdezonia.zorbage.type.algebra.Ordered;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.Random;

// TODO - do I need a BitType that is stored within an int? Or just use BooleanMember?

// I want BooleanMembers to be saveable as true/false and as 1/0 depending upon the Container type

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BooleanGroup
  implements
    CommutativeRingWithUnity<BooleanGroup, BooleanMember>,
    Bounded<BooleanMember>,
    Ordered<BooleanMember>,
    LogicalOperations<BooleanMember>,
    Random<BooleanMember>,
    Power<BooleanMember>
{
	
	private static final java.util.Random rng = new java.util.Random(System.currentTimeMillis());

	public BooleanGroup() {
	}
	
	@Override
	public void logicalAnd(BooleanMember a, BooleanMember b, BooleanMember c) {
		c.setV(a.v() && b.v());
	}

	@Override
	public void logicalOr(BooleanMember a, BooleanMember b, BooleanMember c) {
		c.setV(a.v() || b.v());
	}

	@Override
	public void logicalXor(BooleanMember a, BooleanMember b, BooleanMember c) {
		c.setV(a.v() ^ b.v());
	}

	@Override
	public void logicalNot(BooleanMember a, BooleanMember b) {
		b.setV(!a.v());
	}
	
	// not an override
	public void ternary(BooleanMember a, BooleanMember b, BooleanMember c, BooleanMember d) {
		d.setV(a.v() ? b.v() : c.v());
	}

	@Override
	public boolean isLess(BooleanMember a, BooleanMember b) {
		return !a.v() && b.v();
	}

	@Override
	public boolean isLessEqual(BooleanMember a, BooleanMember b) {
		return !a.v();
	}

	@Override
	public boolean isGreater(BooleanMember a, BooleanMember b) {
		return a.v() && !b.v();
	}

	@Override
	public boolean isGreaterEqual(BooleanMember a, BooleanMember b) {
		return a.v();
	}

	@Override
	public int compare(BooleanMember a, BooleanMember b) {
		if (isLess(a,b)) return -1;
		if (isGreater(a,b)) return 1;
		return 0;
	}

	@Override
	public int signum(BooleanMember a) {
		if (a.v()) return 1;
		return 0;
	}

	@Override
	public void maxBound(BooleanMember a) {
		a.setV(true);
	}

	@Override
	public void minBound(BooleanMember a) {
		a.setV(false);
	}

	@Override
	public boolean isEqual(BooleanMember a, BooleanMember b) {
		return a.v() == b.v();
	}

	@Override
	public boolean isNotEqual(BooleanMember a, BooleanMember b) {
		return a.v() != b.v();
	}

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
	public void assign(BooleanMember from, BooleanMember to) {
		to.setV(from.v());
	}

	@Override
	public void min(BooleanMember a, BooleanMember b, BooleanMember c) {
		Min.compute(this, a, b, c);
	}

	@Override
	public void max(BooleanMember a, BooleanMember b, BooleanMember c) {
		Max.compute(this, a, b, c);
	}

	@Override
	public void multiply(BooleanMember a, BooleanMember b, BooleanMember c) {
		c.setV(a.v() && b.v());
	}

	@Override
	public void power(int power, BooleanMember a, BooleanMember b) {
		if (power < 0)
			throw new IllegalArgumentException("booleans cannot handle negative powers");
		if (power == 0) {
			if (a.v() == false) throw new IllegalArgumentException("0^0 is not a number");
			b.setV(true);
		}
		else
			b.setV(a.v());
	}

	@Override
	public void zero(BooleanMember a) {
		a.setV(false);
	}

	@Override
	public void negate(BooleanMember a, BooleanMember b) {
		b.setV(!a.v());
	}

	// overflow possible: not sure if I like the placement of this class in the algebra hierarchy.
	//   Maybe it points out a new type that hase zero() and unity() but not msubtract() or add().
	//   Also note if we support the overflowers then the type should add pred() and succ(). And
	//   maybe also the BitOperations
	
	@Override
	public void add(BooleanMember a, BooleanMember b, BooleanMember c) {
		if (a.v()) {
			c.setV(!b.v()); // includes overflow case
		} else { // a.v == false
			c.setV(b.v());
		}
	}

	// underflow possible: not sure if I like the placement of this class in the algebra hierarchy.
	//   Maybe it points out a new type that hase zero() and unity() but not msubtract() or add().
	//   Also note if we support the overflowers then the type should add pred() and succ(). And
	//   maybe also the BitOperations
	
	@Override
	public void subtract(BooleanMember a, BooleanMember b, BooleanMember c) {
		if (a.v()) {
			c.setV(!b.v());
		} else { // a.v == false
			c.setV(b.v()); // includes underflow case
		}
	}

	@Override
	public void unity(BooleanMember a) {
		a.setV(true);
	}

	@Override
	public void random(BooleanMember a) {
		a.setV(rng.nextBoolean());
	}

	@Override
	public void pow(BooleanMember a, BooleanMember b, BooleanMember c) {
		int p = b.v() ? 1 : 0;
		power(p, a, c);
	}

}
