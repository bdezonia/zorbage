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

import nom.bdezonia.zorbage.algorithm.MatrixAddition;
import nom.bdezonia.zorbage.algorithm.MatrixAssign;
import nom.bdezonia.zorbage.algorithm.MatrixConjugate;
import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixEqual;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.MatrixIsInfinite;
import nom.bdezonia.zorbage.algorithm.MatrixIsNaN;
import nom.bdezonia.zorbage.algorithm.MatrixMultiply;
import nom.bdezonia.zorbage.algorithm.MatrixNegate;
import nom.bdezonia.zorbage.algorithm.MatrixPower;
import nom.bdezonia.zorbage.algorithm.MatrixRound;
import nom.bdezonia.zorbage.algorithm.MatrixSubtraction;
import nom.bdezonia.zorbage.algorithm.MatrixTranspose;
import nom.bdezonia.zorbage.algorithm.MatrixUnity;
import nom.bdezonia.zorbage.algorithm.MatrixZero;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat64Matrix
	implements
		RingWithUnity<OctonionFloat64Matrix, OctonionFloat64MatrixMember>,
		MatrixRing<OctonionFloat64Matrix, OctonionFloat64MatrixMember, OctonionFloat64Group, OctonionFloat64Member>,
		Constructible2dLong<OctonionFloat64MatrixMember>,
		Rounding<Float64Member, OctonionFloat64MatrixMember>,
		Norm<OctonionFloat64MatrixMember,Float64Member>
{
	public OctonionFloat64Matrix() { }

	@Override
	public void multiply(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
		MatrixMultiply.compute(G.ODBL, a, b, c);
	}

	@Override
	public void power(int power, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		MatrixPower.compute(power, G.ODBL, G.ODBL_MOD, G.ODBL_MAT, a, b);
	}

	@Override
	public void zero(OctonionFloat64MatrixMember a) {
		MatrixZero.compute(G.ODBL, a);
	}

	@Override
	public void negate(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		MatrixNegate.compute(G.ODBL, a, b);
	}

	@Override
	public void add(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
		MatrixAddition.compute(G.ODBL, a, b, c);
	}

	@Override
	public void subtract(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
		MatrixSubtraction.compute(G.ODBL, a, b, c);
	}

	@Override
	public boolean isEqual(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		return MatrixEqual.compute(G.ODBL, a, b);
	}

	@Override
	public boolean isNotEqual(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		return !isEqual(a, b);
	}

	@Override
	public void assign(OctonionFloat64MatrixMember from, OctonionFloat64MatrixMember to) {
		MatrixAssign.compute(G.ODBL, from, to);
	}

	@Override
	public OctonionFloat64MatrixMember construct() {
		return new OctonionFloat64MatrixMember();
	}

	@Override
	public OctonionFloat64MatrixMember construct(OctonionFloat64MatrixMember other) {
		return new OctonionFloat64MatrixMember(other);
	}

	@Override
	public OctonionFloat64MatrixMember construct(String s) {
		return new OctonionFloat64MatrixMember(s);
	}

	@Override
	public OctonionFloat64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new OctonionFloat64MatrixMember(s, d1, d2);
	}

	@Override
	public void norm(OctonionFloat64MatrixMember a, Float64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void round(Round.Mode mode, Float64Member delta, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		MatrixRound.compute(G.DBL, G.ODBL, mode, delta, a, b);
	}

	@Override
	public boolean isNaN(OctonionFloat64MatrixMember a) {
		return MatrixIsNaN.compute(G.ODBL, a);
	}

	@Override
	public boolean isInfinite(OctonionFloat64MatrixMember a) {
		return MatrixIsInfinite.compute(G.ODBL, a);
	}

	@Override
	public void conjugate(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		MatrixConjugate.compute(G.ODBL, a, b);
	}

	@Override
	public void transpose(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		MatrixTranspose.compute(G.ODBL, a, b);
	}

	@Override
	public void conjugateTranspose(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		OctonionFloat64MatrixMember tmp = new OctonionFloat64MatrixMember();
		conjugate(a, tmp);
		transpose(tmp, b);
	}

	// TODO - test
	
	@Override
	public void det(OctonionFloat64MatrixMember a, OctonionFloat64Member b) {
		MatrixDeterminant.compute(this, G.ODBL, a, b);
	}

	@Override
	public void unity(OctonionFloat64MatrixMember a) {
		MatrixUnity.compute(G.ODBL, a);
	}

	@Override
	public void invert(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		MatrixInvert.compute(G.ODBL, G.ODBL_MOD, G.ODBL_MAT, a, b);
	}

	@Override
	public void divide(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
		// invert and multiply
		OctonionFloat64MatrixMember invB = construct(b);
		invert(b, invB);
		multiply(a, invB, c);
	}

}
