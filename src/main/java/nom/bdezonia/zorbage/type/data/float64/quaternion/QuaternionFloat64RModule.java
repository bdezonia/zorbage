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
package nom.bdezonia.zorbage.type.data.float64.quaternion;

import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleIsEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Products;
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionFloat64RModule
  implements
    RModule<QuaternionFloat64RModule,QuaternionFloat64RModuleMember,QuaternionFloat64Group,QuaternionFloat64Member>,
    Constructible1dLong<QuaternionFloat64RModuleMember>,
    Norm<QuaternionFloat64RModuleMember,Float64Member>,
    Products<QuaternionFloat64RModuleMember,QuaternionFloat64Member, QuaternionFloat64MatrixMember>,
    DirectProduct<QuaternionFloat64RModuleMember, QuaternionFloat64MatrixMember>
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member();
	
	public QuaternionFloat64RModule() { }
	
	@Override
	public void zero(QuaternionFloat64RModuleMember a) {
		for (long i = 0; i < a.length(); i++)
			a.setV(i, ZERO);
	}

	@Override
	public void negate(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
		RModuleNegate.compute(G.QDBL, a, b);
	}

	@Override
	public void add(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
		RModuleAdd.compute(G.QDBL, a, b, c);
	}

	@Override
	public void subtract(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
		RModuleSubtract.compute(G.QDBL, a, b, c);
	}

	@Override
	public boolean isEqual(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
		return RModuleIsEqual.compute(G.QDBL, a, b);
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
	public QuaternionFloat64RModuleMember construct(StorageConstruction s, long d1) {
		return new QuaternionFloat64RModuleMember(s, d1);
	}

	@Override
	public void assign(QuaternionFloat64RModuleMember from, QuaternionFloat64RModuleMember to) {
		RModuleAssign.compute(G.QDBL, from, to);
	}

	@Override
	public void norm(QuaternionFloat64RModuleMember a, Float64Member b) {
		QuaternionFloat64Member aTmp = new QuaternionFloat64Member();
		QuaternionFloat64Member sum = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		// TODO Look into preventing overflow. can do so similar to float case using norms
		for (long i = 0; i < a.length(); i++) {
			a.v(i, aTmp);
			G.QDBL.conjugate(aTmp, tmp);
			G.QDBL.multiply(aTmp, tmp, tmp);
			G.QDBL.add(sum, tmp, sum);
		}
		b.setV(Math.sqrt(sum.r()));
	}

	@Override
	public void scale(QuaternionFloat64Member scalar, QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b) {
		RModuleScale.compute(G.QDBL, scalar, a, b);
	}

	@Override
	public void crossProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64RModuleMember c) {
		CrossProduct.compute(G.QDBL_MOD, G.QDBL, a, b, c);
	}

	@Override
	public void dotProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64Member c) {
		DotProduct.compute(G.QDBL, a, b, c);
	}

	@Override
	public void perpDotProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64Member c) {
		PerpDotProduct.compute(G.QDBL_MOD, G.QDBL, a, b, c);
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
		RModuleConjugate.compute(G.QDBL, a, b);
	}

	@Override
	public void vectorDirectProduct(QuaternionFloat64RModuleMember a, QuaternionFloat64RModuleMember b, QuaternionFloat64MatrixMember c) {
		directProduct(a, b, c);
	}

	@Override
	public void directProduct(QuaternionFloat64RModuleMember in1, QuaternionFloat64RModuleMember in2,
			QuaternionFloat64MatrixMember out)
	{
		RModuleDirectProduct.compute(G.QDBL, in1, in2, out);
	}

}
