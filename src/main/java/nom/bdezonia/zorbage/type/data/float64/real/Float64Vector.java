/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.real;

import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.VectorSpace;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

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
			G.DBL.negate(tmp, tmp);
			b.setV(i, tmp);
		}
	}

	@Override
	public void add(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		RModuleAdd.compute(G.DBL_VEC, G.DBL, a, b, c);
	}

	@Override
	public void subtract(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		RModuleSubtract.compute(G.DBL_VEC, G.DBL, a, b, c);
	}

	@Override
	public boolean isEqual(Float64VectorMember a, Float64VectorMember b) {
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		final long max = Math.max(a.length(), b.length());
		for (long i = 0; i < max; i++) {
			a.v(i, atmp);
			b.v(i, btmp);
			if (G.DBL.isNotEqual(atmp, btmp))
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
			G.DBL.divide(tmp, max, tmp);
			G.DBL.multiply(tmp, tmp, tmp);
			G.DBL.add(norm2, tmp, norm2);
		}
		double norm = max.v() * Math.sqrt(norm2.v());
		b.setV(norm);
	}

	@Override
	public void scale(Float64Member scalar, Float64VectorMember a, Float64VectorMember b) {
		RModuleScale.compute(G.DBL_VEC, G.DBL, scalar, a, b);
	}

	@Override
	public void crossProduct(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
		CrossProduct.compute(G.DBL_VEC, G.DBL, a, b, c);
	}

	@Override
	public void dotProduct(Float64VectorMember a, Float64VectorMember b, Float64Member c) {
		DotProduct.compute(G.DBL, a, b, c);
	}

	@Override
	public void perpDotProduct(Float64VectorMember a, Float64VectorMember b, Float64Member c) {
		PerpDotProduct.compute(G.DBL_VEC, G.DBL, a, b, c);
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
