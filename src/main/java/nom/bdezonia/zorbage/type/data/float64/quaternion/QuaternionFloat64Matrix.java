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
public class QuaternionFloat64Matrix
	implements
		RingWithUnity<QuaternionFloat64Matrix, QuaternionFloat64MatrixMember>,
		MatrixRing<QuaternionFloat64Matrix, QuaternionFloat64MatrixMember, QuaternionFloat64Group, QuaternionFloat64Member>,
		Constructible2dLong<QuaternionFloat64MatrixMember>,
		Rounding<Float64Member, QuaternionFloat64MatrixMember>,
		Norm<QuaternionFloat64MatrixMember,Float64Member>
{
	public QuaternionFloat64Matrix() { }

	@Override
	public void multiply(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
		MatrixMultiply.compute(G.QDBL, a, b, c);
	}

	@Override
	public void power(int power, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		MatrixPower.compute(power, G.QDBL, G.QDBL_MOD, G.QDBL_MAT, a, b);
	}

	@Override
	public void zero(QuaternionFloat64MatrixMember a) {
		MatrixZero.compute(G.QDBL, a);
	}

	@Override
	public void negate(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		MatrixNegate.compute(G.QDBL, a, b);
	}

	@Override
	public void add(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
		MatrixAddition.compute(G.QDBL, a, b, c);
	}

	@Override
	public void subtract(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
		MatrixSubtraction.compute(G.QDBL, a, b, c);
	}

	@Override
	public boolean isEqual(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		return MatrixEqual.compute(G.QDBL, a, b);
	}

	@Override
	public boolean isNotEqual(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		return !isEqual(a, b);
	}

	@Override
	public void assign(QuaternionFloat64MatrixMember from, QuaternionFloat64MatrixMember to) {
		MatrixAssign.compute(G.QDBL, from, to);
	}

	@Override
	public QuaternionFloat64MatrixMember construct() {
		return new QuaternionFloat64MatrixMember();
	}

	@Override
	public QuaternionFloat64MatrixMember construct(QuaternionFloat64MatrixMember other) {
		return new QuaternionFloat64MatrixMember(other);
	}

	@Override
	public QuaternionFloat64MatrixMember construct(String s) {
		return new QuaternionFloat64MatrixMember(s);
	}

	@Override
	public QuaternionFloat64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new QuaternionFloat64MatrixMember(s, d1, d2);
	}

	@Override
	public void norm(QuaternionFloat64MatrixMember a, Float64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void round(Round.Mode mode, Float64Member delta, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		MatrixRound.compute(G.DBL, G.QDBL, mode, delta, a, b);
	}

	@Override
	public boolean isNaN(QuaternionFloat64MatrixMember a) {
		return MatrixIsNaN.compute(G.QDBL, a);
	}

	@Override
	public boolean isInfinite(QuaternionFloat64MatrixMember a) {
		return MatrixIsInfinite.compute(G.QDBL, a);
	}

	@Override
	public void conjugate(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		MatrixConjugate.compute(G.QDBL, a, b);
	}

	@Override
	public void transpose(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		MatrixTranspose.compute(G.QDBL, a, b);
	}

	@Override
	public void conjugateTranspose(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		QuaternionFloat64MatrixMember tmp = new QuaternionFloat64MatrixMember();
		conjugate(a, tmp);
		transpose(tmp, b);
	}

	// TODO test
	
	@Override
	public void det(QuaternionFloat64MatrixMember a, QuaternionFloat64Member b) {
		MatrixDeterminant.compute(this, G.QDBL, a, b);
	}

	@Override
	public void unity(QuaternionFloat64MatrixMember a) {
		MatrixUnity.compute(G.QDBL, a);
	}

	@Override
	public void invert(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		MatrixInvert.compute(G.QDBL, G.QDBL_MOD, G.QDBL_MAT, a, b);
	}

	@Override
	public void divide(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b,
			QuaternionFloat64MatrixMember c)
	{
		// invert and multiply
		QuaternionFloat64MatrixMember invB = construct(a.storageType(), a.cols(), a.rows());
		MatrixInvert.compute(G.QDBL, G.QDBL_MOD, G.QDBL_MAT, b, invB);
		MatrixMultiply.compute(G.QDBL, a, invB, c);
	}

}
