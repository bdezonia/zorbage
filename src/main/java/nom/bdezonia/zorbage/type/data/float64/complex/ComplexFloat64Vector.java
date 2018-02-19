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
package nom.bdezonia.zorbage.type.data.float64.complex;

import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleIsEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.VectorSpace;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64Vector
  implements
    VectorSpace<ComplexFloat64Vector,ComplexFloat64VectorMember,ComplexFloat64Group,ComplexFloat64Member>,
    Constructible1dLong<ComplexFloat64VectorMember>,
    Norm<ComplexFloat64VectorMember,Float64Member>
{
	private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0);
	
	public ComplexFloat64Vector() {
	}
	
	@Override
	public void zero(ComplexFloat64VectorMember a) {
		for (int i = 0; i < a.length(); i++)
			a.setV(i, ZERO);
	}

	@Override
	public void negate(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
		RModuleNegate.compute(G.CDBL, a, b);
	}

	@Override
	public void add(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
		RModuleAdd.compute(G.CDBL, a, b, c);
	}

	@Override
	public void subtract(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
		RModuleSubtract.compute(G.CDBL, a, b, c);
	}

	@Override
	public boolean isEqual(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
		return RModuleIsEqual.compute(G.CDBL, a, b);
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
	public ComplexFloat64VectorMember construct(MemoryConstruction m, StorageConstruction s, long d1) {
		return new ComplexFloat64VectorMember(m, s, d1);
	}

	@Override
	public void assign(ComplexFloat64VectorMember from, ComplexFloat64VectorMember to) {
		RModuleAssign.compute(G.CDBL, from, to);
	}

	@Override
	public void norm(ComplexFloat64VectorMember a, Float64Member b) {
		ComplexFloat64Member aTmp = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		// TODO Look into preventing overflow. can do so similar to float case using norms
		for (long i = 0; i < a.length(); i++) {
			a.v(i, aTmp);
			G.CDBL.conjugate(aTmp, tmp);
			G.CDBL.multiply(aTmp, tmp, tmp);
			G.CDBL.add(sum, tmp, sum);
		}
		b.setV(Math.sqrt(sum.r()));
	}

	@Override
	public void scale(ComplexFloat64Member scalar, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
		RModuleScale.compute(G.CDBL, scalar, a, b);
	}

	@Override
	public void crossProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
		CrossProduct.compute(G.CDBL_VEC, G.CDBL, a, b, c);
	}

	@Override
	public void dotProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64Member c) {
		DotProduct.compute(G.CDBL, a, b, c);
	}

	@Override
	public void perpDotProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64Member c) {
		PerpDotProduct.compute(G.CDBL_VEC, G.CDBL, a, b, c);
	}

	@Override
	public void vectorTripleProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c,
			ComplexFloat64VectorMember d)
	{
		ComplexFloat64VectorMember b_cross_c = new ComplexFloat64VectorMember(new double[3*2]);
		crossProduct(b, c, b_cross_c);
		crossProduct(a, b_cross_c, d);
	}

	@Override
	public void scalarTripleProduct(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c,
			ComplexFloat64Member d)
	{
		ComplexFloat64VectorMember b_cross_c = new ComplexFloat64VectorMember(new double[3*2]);
		crossProduct(b, c, b_cross_c);
		dotProduct(a, b_cross_c, d);
	}

	@Override
	public void conjugate(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
		RModuleConjugate.compute(G.CDBL, a, b);
	}

}
