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
package zorbage.type.data;

import zorbage.type.algebra.VectorSpace;
import zorbage.type.ctor.Constructible1dLong;
import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64Vector
  implements
    VectorSpace<Float64Vector,Float64VectorMember,Float64Group,Float64Member>,
    Constructible1dLong<Float64VectorMember>
{
	private static final Float64Group g = new Float64Group();
	private static final Float64Member ZERO = new Float64Member(0);

	public Float64Vector() {
	}
	
	@Override
	public void zero(Float64VectorMember a) {
		for (long i = 0; i < a.length(); i++)
			a.setV(i, ZERO);
	}

	@Override
	public void negate(Float64VectorMember a, Float64VectorMember b) {
		Float64Member tmp = new Float64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, tmp);
			g.negate(tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void add(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			g.add(atmp, btmp, btmp);
			c.setV(i, btmp);
		}
		for (long i = max; i < c.length(); i++)
			c.setV(i, ZERO);
	}

	@Override
	public void subtract(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			g.subtract(atmp, btmp, btmp);
			c.setV(i, btmp);
		}
		for (long i = max; i < c.length(); i++)
			c.setV(i, ZERO);
	}

	@Override
	public boolean isEqual(Float64VectorMember a, Float64VectorMember b) {
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			if (g.isNotEqual(atmp, btmp))
				return false;
		}
		return true;
	}

	@Override
	public boolean isNotEqual(Float64VectorMember a, Float64VectorMember b) {
		return !isEqual(a,b);
	}

	@Override
	public Float64VectorMember construct() {
		return new Float64VectorMember();
	}

	@Override
	public Float64VectorMember construct(Float64VectorMember other) {
		return new Float64VectorMember(other);
	}

	@Override
	public Float64VectorMember construct(String s) {
		return new Float64VectorMember(s);
	}

	@Override
	public Float64VectorMember construct(MemoryConstruction m, StorageConstruction s, long d1) {
		return new Float64VectorMember(m, s, d1);
	}

	@Override
	public void assign(Float64VectorMember from, Float64VectorMember to) {
		Float64Member tmp = new Float64Member();
		for (long i = 0; i < from.length(); i++) {
			from.v(i, tmp);
			to.setV(i, tmp);
		}
		for (long i = from.length(); i < to.length(); i++) {
			to.setV(i, ZERO);
		}
	}

	@Override
	public void norm(Float64VectorMember a, Float64Member b) {
		Float64Member max = new Float64Member();
		Float64Member tmp = new Float64Member();
		Float64Member norm2 = new Float64Member(0);
		max.setV(Double.NEGATIVE_INFINITY);
		for (long i = 0; i < a.length(); i++) {
			a.v(i,  tmp);
			max.setV(Math.max(Math.abs(tmp.v()), max.v()));
		}
		for (long i = 0; i < a.length(); i++) {
			a.v(i,  tmp);
			g.divide(tmp, max, tmp);
			g.multiply(tmp, tmp, tmp);
			g.add(norm2, tmp, norm2);
		}
		double norm = max.v() * Math.sqrt(norm2.v());
		b.setV(norm);
	}

	@Override
	public void scale(Float64Member scalar, Float64VectorMember a, Float64VectorMember b) {
		Float64Member tmp = new Float64Member();
		// two loops minimizes memory allocations
		final long min = Math.min(a.length(), b.length());
		for (long i = 0; i < min; i++) {
			a.v(i, tmp);
			g.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
		final long max = Math.max(a.length(), b.length());
		for (long i = min; i < max; i++) {
			a.v(i, tmp);
			g.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void crossProduct(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		// kludgy: 3 dim only. treating lower dim vectors as having zeroes in other positions.
		if (!compatible(3,a) || !compatible(3,b))
			throw new UnsupportedOperationException("vector cross product defined for 3 dimensions");
		Float64VectorMember tmp = new Float64VectorMember(new double[3]);
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		Float64Member term1 = new Float64Member();
		Float64Member term2 = new Float64Member();
		Float64Member t = new Float64Member();
		a.v(1, atmp);
		b.v(2, btmp);
		g.multiply(atmp, btmp, term1);
		a.v(2, atmp);
		b.v(1, btmp);
		g.multiply(atmp, btmp, term2);
		g.subtract(term1, term2, t);
		tmp.setV(0, t);
		a.v(2, atmp);
		b.v(0, btmp);
		g.multiply(atmp, btmp, term1);
		a.v(0, atmp);
		b.v(2, btmp);
		g.multiply(atmp, btmp, term2);
		g.subtract(term1, term2, t);
		tmp.setV(1, t);
		a.v(0, atmp);
		b.v(1, btmp);
		g.multiply(atmp, btmp, term1);
		a.v(1, atmp);
		b.v(0, btmp);
		g.multiply(atmp, btmp, term2);
		g.subtract(term1, term2, t);
		tmp.setV(2, t);
		assign(tmp, c);
	}

	private boolean compatible(long dim, Float64VectorMember v) {
		Float64Member tmp = new Float64Member();
		for (long i = dim; i < v.length(); i++) {
			v.v(i, tmp);
			if (g.isNotEqual(tmp, ZERO))
				return false;
		}
		return true;
	}

	@Override
	public void dotProduct(Float64VectorMember a, Float64VectorMember b, Float64Member c) {
		final long min = Math.min(a.length(), b.length());
		Float64Member sum = new Float64Member(0);
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		for (long i = 0; i < min; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			g.multiply(atmp, btmp, btmp);
			g.add(sum, btmp, sum);
		}
		c.set(sum);
	}

	@Override
	public void perpDotProduct(Float64VectorMember a, Float64VectorMember b, Float64Member c) {
		// kludgy: 2 dim only. treating lower dim vectors as having zeroes in other positions.
		if (!compatible(2,a) || !compatible(2,b))
			throw new UnsupportedOperationException("vector perp dot product defined for 2 dimensions");
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		Float64Member term1 = new Float64Member();
		Float64Member term2 = new Float64Member();
		a.v(1, atmp);
		b.v(0, btmp);
		g.negate(atmp, atmp);
		g.multiply(atmp, btmp, term1);
		a.v(0, atmp);
		b.v(1, btmp);
		g.multiply(atmp, btmp, term2);
		g.add(term1, term2, c);
	}

	@Override
	public void vectorTripleProduct(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c,
			Float64VectorMember d)
	{
		Float64VectorMember b_cross_c = new Float64VectorMember(new double[3]);
		crossProduct(b, c, b_cross_c);
		crossProduct(a, b_cross_c, d);
	}

	@Override
	public void scalarTripleProduct(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c,
			Float64Member d)
	{
		Float64VectorMember b_cross_c = new Float64VectorMember(new double[3]);
		crossProduct(b, c, b_cross_c);
		dotProduct(a, b_cross_c, d);
	}

	@Override
	public void conjugate(Float64VectorMember a, Float64VectorMember b) {
		assign(a,b);
	}

}
