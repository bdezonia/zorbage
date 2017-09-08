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
package zorbage.type.data.float64;

import zorbage.type.algebra.RModule;
import zorbage.type.ctor.Constructible1dLong;
import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64RModule
  implements
    RModule<QuaternionFloat64RModule,QuaternionFloat64RModuleMember,QuaternionFloat64Group,QuaternionFloat64Member>,
    Constructible1dLong<QuaternionFloat64RModuleMember>
{
	private static final QuaternionFloat64Group qdbl = new QuaternionFloat64Group();
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member();
	
	public QuaternionFloat64RModule() {
	}
	
	@Override
	public void zero(QuaternionFloat64RModuleMember a) {
		for (long i = 0; i < a.length(); i++)
			a.setV(i, ZERO);
	}

	@Override
	public void negate(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, tmp);
			qdbl.negate(tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void add(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			qdbl.add(atmp, btmp, btmp);
			c.setV(i, btmp);
		}
		for (long i = max; i < c.length(); i++)
			c.setV(i, ZERO);
	}

	@Override
	public void subtract(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			qdbl.subtract(atmp, btmp, btmp);
			c.setV(i, btmp);
		}
		for (long i = max; i < c.length(); i++)
			c.setV(i, ZERO);
	}

	@Override
	public boolean isEqual(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			if (qdbl.isNotEqual(atmp, btmp))
				return false;
		}
		return true;
	}

	@Override
	public boolean isNotEqual(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
		return !isEqual(a,b);
	}

	@Override
	public QuaternionFloat64RModuleMember construct() {
		return new QuaternionFloat64RModuleMember();
	}

	@Override
	public QuaternionFloat64RModuleMember construct(QuaternionFloat64RModuleMember other) {
		return new QuaternionFloat64RModuleMember(other);
	}

	@Override
	public QuaternionFloat64RModuleMember construct(String s) {
		return new QuaternionFloat64RModuleMember(s);
	}

	@Override
	public QuaternionFloat64RModuleMember construct(MemoryConstruction m, StorageConstruction s, long d1) {
		return new QuaternionFloat64RModuleMember(m, s, d1);
	}

	@Override
	public void assign(QuaternionFloat64RModuleMember from, QuaternionFloat64RModuleMember to) {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long i = 0; i < from.length(); i++) {
			from.v(i, tmp);
			to.setV(i, tmp);
		}
		for (long i = from.length(); i < to.length(); i++) {
			to.setV(i, ZERO);
		}
	}

	@Override
	public void norm(QuaternionFloat64RModuleMember a, QuaternionFloat64Member b) {
		throw new IllegalArgumentException("TODO");
		// TODO
		//QuaternionFloat64Member norm2 = new QuaternionFloat64Member();
		//QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		//QuaternionFloat64Member tmp2 = new QuaternionFloat64Member();
		//for (int i = 0; i < a.length(); i++) {
		//	a.v(i, tmp);
		//	g.multiply(tmp,tmp,tmp2);
		//	g.add(norm2, tmp2, norm2);
		//}
		//QuaternionFloat64Member norm = Math.sqrt(norm2); // TODO this is the tricky part
		//b.set(norm);
		// is a norm of a Quaternion RModule a Quaternion number or a real number? read.
	}

	@Override
	public void scale(QuaternionFloat64Member scalar, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		// two loops minimizes memory allocations
		final long min = Math.min(a.length(), b.length());
		for (long i = 0; i < min; i++) {
			a.v(i, tmp);
			qdbl.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
		final long max = Math.max(a.length(), b.length());
		for (long i = min; i < max; i++) {
			a.v(i, tmp);
			qdbl.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void crossProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
		// kludgy: 3 dim only. treating lower dim RModules as having zeroes in other positions.
		if (!compatible(3,a) || !compatible(3,b))
			throw new UnsupportedOperationException("RModule cross product defined for 3 dimensions");
		QuaternionFloat64RModuleMember tmp = new QuaternionFloat64RModuleMember(new double[3*4]);
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		QuaternionFloat64Member term1 = new QuaternionFloat64Member();
		QuaternionFloat64Member term2 = new QuaternionFloat64Member();
		QuaternionFloat64Member t = new QuaternionFloat64Member();
		a.v(1, atmp);
		b.v(2, btmp);
		qdbl.multiply(atmp, btmp, term1);
		a.v(2, atmp);
		b.v(1, btmp);
		qdbl.multiply(atmp, btmp, term2);
		qdbl.subtract(term1, term2, t);
		tmp.setV(0, t);
		a.v(2, atmp);
		b.v(0, btmp);
		qdbl.multiply(atmp, btmp, term1);
		a.v(0, atmp);
		b.v(2, btmp);
		qdbl.multiply(atmp, btmp, term2);
		qdbl.subtract(term1, term2, t);
		tmp.setV(1, t);
		a.v(0, atmp);
		b.v(1, btmp);
		qdbl.multiply(atmp, btmp, term1);
		a.v(1, atmp);
		b.v(0, btmp);
		qdbl.multiply(atmp, btmp, term2);
		qdbl.subtract(term1, term2, t);
		tmp.setV(2, t);
		assign(tmp, c);
	}

	private boolean compatible(long dim, QuaternionFloat64RModuleMember v) {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long i = dim; i < v.length(); i++) {
			v.v(i, tmp);
			if (qdbl.isNotEqual(tmp, ZERO))
				return false;
		}
		return true;
	}
	
	@Override
	public void dotProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64Member c) {
		final long min = Math.min(a.length(), b.length());
		QuaternionFloat64Member sum = new QuaternionFloat64Member();
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		for (long i = 0; i < min; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			qdbl.multiply(atmp, btmp, btmp);
			qdbl.add(sum, btmp, sum);
		}
		c.set(sum);
	}

	@Override
	public void perpDotProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64Member c) {
		// kludgy: 2 dim only. treating lower dim RModules as having zeroes in other positions.
		if (!compatible(2,a) || !compatible(2,b))
			throw new UnsupportedOperationException("RModule perp dot product defined for 2 dimensions");
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		QuaternionFloat64Member term1 = new QuaternionFloat64Member();
		QuaternionFloat64Member term2 = new QuaternionFloat64Member();
		a.v(1, atmp);
		b.v(0, btmp);
		qdbl.negate(atmp, atmp);
		qdbl.multiply(atmp, btmp, term1);
		a.v(0, atmp);
		b.v(1, btmp);
		qdbl.multiply(atmp, btmp, term2);
		qdbl.add(term1, term2, c);
	}

	@Override
	public void vectorTripleProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c,
			QuaternionFloat64RModuleMember d)
	{
		QuaternionFloat64RModuleMember b_cross_c = new QuaternionFloat64RModuleMember(new double[3*4]);
		crossProduct(b, c, b_cross_c);
		crossProduct(a, b_cross_c, d);
	}

	@Override
	public void scalarTripleProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c,
			QuaternionFloat64Member d)
	{
		QuaternionFloat64RModuleMember b_cross_c = new QuaternionFloat64RModuleMember(new double[3*4]);
		crossProduct(b, c, b_cross_c);
		dotProduct(a, b_cross_c, d);
	}

	@Override
	public void conjugate(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long i = 0; i < a.length(); i++) {
			a.v(i, tmp);
			qdbl.conjugate(tmp, tmp);
			b.setV(i, tmp);
		}
	}

}
