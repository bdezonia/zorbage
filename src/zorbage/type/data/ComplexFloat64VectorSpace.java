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

import zorbage.type.algebra.VectorSpace;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64VectorSpace
  implements
    VectorSpace<ComplexFloat64VectorSpace,ComplexFloat64VectorMember,ComplexFloat64Field,ComplexFloat64Member>
{
	private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0);
	private static final ComplexFloat64Field g = new ComplexFloat64Field();
	
	@Override
	public void zero(ComplexFloat64VectorMember a) {
		for (int i = 0; i < a.length(); i++)
			a.setV(i, ZERO);
	}

	@Override
	public void negate(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		int max = Math.max(a.length(), b.length());
		for (int i = 0; i < max; i++) {
			a.v(i, tmp);
			g.negate(tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void add(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		int max = Math.max(a.length(), b.length());
		for (int i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			g.add(atmp, btmp, btmp);
			c.setV(i, btmp);
		}
		for (int i = max; i < c.length(); i++)
			c.setV(i, ZERO);
	}

	@Override
	public void subtract(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		int max = Math.max(a.length(), b.length());
		for (int i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			g.subtract(atmp, btmp, btmp);
			c.setV(i, btmp);
		}
		for (int i = max; i < c.length(); i++)
			c.setV(i, ZERO);
	}

	@Override
	public boolean isEqual(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		int max = Math.max(a.length(), b.length());
		for (int i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			if (g.isNotEqual(atmp, btmp))
				return false;
		}
		return true;
	}

	@Override
	public boolean isNotEqual(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
		return !isEqual(a,b);
	}

	@Override
	public ComplexFloat64VectorMember construct() {
		return new ComplexFloat64VectorMember();
	}

	@Override
	public ComplexFloat64VectorMember construct(ComplexFloat64VectorMember other) {
		return new ComplexFloat64VectorMember(other);
	}

	@Override
	public ComplexFloat64VectorMember construct(String s) {
		return new ComplexFloat64VectorMember(s);
	}

	@Override
	public void assign(ComplexFloat64VectorMember from, ComplexFloat64VectorMember to) {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int i = 0; i < from.length(); i++) {
			from.v(i, tmp);
			to.setV(i, tmp);
		}
		for (int i = from.length(); i < to.length(); i++) {
			to.setV(i, ZERO);
		}
	}

	@Override
	public void norm(ComplexFloat64VectorMember a, ComplexFloat64Member b) {
		throw new IllegalArgumentException("TODO");
		// TODO
		//ComplexFloat64Member norm2 = new ComplexFloat64Member();
		//ComplexFloat64Member tmp = new ComplexFloat64Member();
		//ComplexFloat64Member tmp2 = new ComplexFloat64Member();
		//for (int i = 0; i < a.length(); i++) {
		//	a.v(i, tmp);
		//	g.multiply(tmp,tmp,tmp2);
		//	g.add(norm2, tmp2, norm2);
		//}
		//ComplexFloat64Member norm = Math.sqrt(norm2); // TODO this is the tricky part
		//b.set(norm);
		// is a norm of a complex vector a complex number or a real number? read.
	}

	@Override
	public void scale(ComplexFloat64Member scalar, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		// two loops minimizes memory allocations
		int min = Math.min(a.length(), b.length());
		for (int i = 0; i < min; i++) {
			a.v(i, tmp);
			g.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
		int max = Math.max(a.length(), b.length());
		for (int i = min; i < max; i++) {
			a.v(i, tmp);
			g.multiply(scalar, tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void crossProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
		// kludgy: 3 dim only. treating lower dim vectors as having zeroes in other positions.
		if (!compatible(3,a) || !compatible(3,b))
			throw new UnsupportedOperationException("vector cross product defined for 3 dimensions");
		ComplexFloat64VectorMember tmp = new ComplexFloat64VectorMember(new double[3], new double[3]);
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		ComplexFloat64Member term1 = new ComplexFloat64Member();
		ComplexFloat64Member term2 = new ComplexFloat64Member();
		ComplexFloat64Member t = new ComplexFloat64Member();
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

	private boolean compatible(int dim, ComplexFloat64VectorMember v) {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int i = dim; i < v.length(); i++) {
			v.v(i, tmp);
			if (g.isNotEqual(tmp, ZERO))
				return false;
		}
		return true;
	}
	
	@Override
	public void dotProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64Member c) {
		int min = Math.min(a.length(), b.length());
		ComplexFloat64Member sum = new ComplexFloat64Member(0,0);
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		for (int i = 0; i < min; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			g.multiply(atmp, btmp, btmp);
			g.add(sum, btmp, sum);
		}
		c.set(sum);
	}

	@Override
	public void perpDotProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64Member c) {
		// kludgy: 2 dim only. treating lower dim vectors as having zeroes in other positions.
		if (!compatible(2,a) || !compatible(2,b))
			throw new UnsupportedOperationException("vector perp dot product defined for 2 dimensions");
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		ComplexFloat64Member term1 = new ComplexFloat64Member();
		ComplexFloat64Member term2 = new ComplexFloat64Member();
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
	public void vectorTripleProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c,
			ComplexFloat64VectorMember d)
	{
		ComplexFloat64VectorMember b_cross_c = new ComplexFloat64VectorMember(new double[3], new double[3]);
		crossProduct(b, c, b_cross_c);
		crossProduct(a, b_cross_c, d);
	}

	@Override
	public void scalarTripleProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c,
			ComplexFloat64Member d)
	{
		ComplexFloat64VectorMember b_cross_c = new ComplexFloat64VectorMember(new double[3], new double[3]);
		crossProduct(b, c, b_cross_c);
		dotProduct(a, b_cross_c, d);
	}

}
