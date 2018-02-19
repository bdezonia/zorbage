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
package nom.bdezonia.zorbage.type.data.float64.octonion;

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
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat64RModule
  implements
    RModule<OctonionFloat64RModule,OctonionFloat64RModuleMember,OctonionFloat64Group,OctonionFloat64Member>,
    Constructible1dLong<OctonionFloat64RModuleMember>,
	Norm<OctonionFloat64RModuleMember,Float64Member>
{
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member();
	
	public OctonionFloat64RModule() {
	}
	
	@Override
	public void zero(OctonionFloat64RModuleMember a) {
		for (long i = 0; i < a.length(); i++)
			a.setV(i, ZERO);
	}

	@Override
	public void negate(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		RModuleNegate.compute(G.ODBL, a, b);
	}

	@Override
	public void add(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
		RModuleAdd.compute(G.ODBL, a, b, c);
	}

	@Override
	public void subtract(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
		RModuleSubtract.compute(G.ODBL, a, b, c);
	}

	@Override
	public boolean isEqual(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		return RModuleIsEqual.compute(G.ODBL, a, b);
	}

	@Override
	public boolean isNotEqual(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		return !isEqual(a,b);
	}

	@Override
	public OctonionFloat64RModuleMember construct() {
		return new OctonionFloat64RModuleMember();
	}

	@Override
	public OctonionFloat64RModuleMember construct(OctonionFloat64RModuleMember other) {
		return new OctonionFloat64RModuleMember(other);
	}

	@Override
	public OctonionFloat64RModuleMember construct(String s) {
		return new OctonionFloat64RModuleMember(s);
	}

	@Override
	public OctonionFloat64RModuleMember construct(MemoryConstruction m, StorageConstruction s, long d1) {
		return new OctonionFloat64RModuleMember(m, s, d1);
	}

	@Override
	public void assign(OctonionFloat64RModuleMember from, OctonionFloat64RModuleMember to) {
		RModuleAssign.compute(G.ODBL, from, to);
	}

	@Override
	public void norm(OctonionFloat64RModuleMember a, Float64Member b) {
		OctonionFloat64Member aTmp = new OctonionFloat64Member();
		OctonionFloat64Member sum = new OctonionFloat64Member();
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		// TODO Look into preventing overflow. can do so similar to float case using norms
		for (long i = 0; i < a.length(); i++) {
			a.v(i, aTmp);
			G.ODBL.conjugate(aTmp, tmp);
			G.ODBL.multiply(aTmp, tmp, tmp);
			G.ODBL.add(sum, tmp, sum);
		}
		b.setV(Math.sqrt(sum.r()));
	}

	@Override
	public void scale(OctonionFloat64Member scalar, OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		RModuleScale.compute(G.ODBL, scalar, a, b);
	}

	@Override
	public void crossProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c) {
		CrossProduct.compute(G.ODBL_MOD, G.ODBL, a, b, c);
	}

	@Override
	public void dotProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64Member c) {
		DotProduct.compute(G.ODBL, a, b, c);
	}

	@Override
	public void perpDotProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64Member c) {
		PerpDotProduct.compute(G.ODBL_MOD, G.ODBL, a, b, c);
	}

	@Override
	public void vectorTripleProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c,
			OctonionFloat64RModuleMember d)
	{
		OctonionFloat64RModuleMember b_cross_c = new OctonionFloat64RModuleMember(new double[3*8]);
		crossProduct(b, c, b_cross_c);
		crossProduct(a, b_cross_c, d);
	}

	@Override
	public void scalarTripleProduct(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b, OctonionFloat64RModuleMember c,
			OctonionFloat64Member d)
	{
		OctonionFloat64RModuleMember b_cross_c = new OctonionFloat64RModuleMember(new double[3*8]);
		crossProduct(b, c, b_cross_c);
		dotProduct(a, b_cross_c, d);
	}

	@Override
	public void conjugate(OctonionFloat64RModuleMember a, OctonionFloat64RModuleMember b) {
		RModuleConjugate.compute(G.ODBL, a, b);
	}

}
