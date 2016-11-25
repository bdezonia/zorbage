/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.type.data;

import zorbage.type.algebra.Bounded;
import zorbage.type.algebra.Group;
import zorbage.type.algebra.LogicalOperations;
import zorbage.type.algebra.Ordered;

// TODO - do I need a BitType that is stored within an int? Or just use BooleanMember? toString to return false/true
// or 1/0 ?

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BooleanGroup
  implements
    Group<BooleanGroup, BooleanMember>,
    Bounded<BooleanMember>,
    Ordered<BooleanMember>,
    LogicalOperations<BooleanMember>{

	@Override
	public void logicalAnd(BooleanMember a, BooleanMember b, BooleanMember c) {
		c.v = a.v && b.v;
	}

	@Override
	public void logicalOr(BooleanMember a, BooleanMember b, BooleanMember c) {
		c.v = a.v || b.v;
	}

	@Override
	public void logicalXor(BooleanMember a, BooleanMember b, BooleanMember c) {
		c.v = a.v ^ b.v;
	}

	@Override
	public void logicalNot(BooleanMember a, BooleanMember b) {
		b.v = !a.v;
	}
	
	// not an override
	public void ternary(BooleanMember a, BooleanMember b, BooleanMember c, BooleanMember d) {
		d.v = a.v ? b.v : c.v;
	}

	@Override
	public boolean isLess(BooleanMember a, BooleanMember b) {
		return !a.v && b.v;
	}

	@Override
	public boolean isLessEqual(BooleanMember a, BooleanMember b) {
		return !a.v;
	}

	@Override
	public boolean isGreater(BooleanMember a, BooleanMember b) {
		return a.v && !b.v;
	}

	@Override
	public boolean isGreaterEqual(BooleanMember a, BooleanMember b) {
		return a.v;
	}

	@Override
	public int compare(BooleanMember a, BooleanMember b) {
		if (isLess(a,b)) return -1;
		if (isGreater(a,b)) return 1;
		return 0;
	}

	@Override
	public int signum(BooleanMember a) {
		if (a.v) return 1;
		return 0;
	}

	@Override
	public void max(BooleanMember a) {
		a.v = true;
	}

	@Override
	public void min(BooleanMember a) {
		a.v = false;
	}

	@Override
	public boolean isEqual(BooleanMember a, BooleanMember b) {
		return a.v == b.v;
	}

	@Override
	public boolean isNotEqual(BooleanMember a, BooleanMember b) {
		return a.v != b.v;
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
		to.v = from.v;
	}

	@Override
	public void min(BooleanMember a, BooleanMember b, BooleanMember c) {
		if (isLess(a, b))
			c.v = a.v;
		else
			c.v = b.v;
	}

	@Override
	public void max(BooleanMember a, BooleanMember b, BooleanMember c) {
		if (isGreater(a, b))
			c.v = a.v;
		else
			c.v = b.v;
	}

}
