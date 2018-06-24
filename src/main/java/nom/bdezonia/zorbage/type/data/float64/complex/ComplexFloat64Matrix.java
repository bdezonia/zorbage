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
public class ComplexFloat64Matrix
	implements
		RingWithUnity<ComplexFloat64Matrix, ComplexFloat64MatrixMember>,
		MatrixRing<ComplexFloat64Matrix, ComplexFloat64MatrixMember, ComplexFloat64Group, ComplexFloat64Member>,
		Constructible2dLong<ComplexFloat64MatrixMember>,
		Rounding<Float64Member, ComplexFloat64MatrixMember>,
		Norm<ComplexFloat64MatrixMember,Float64Member>
{
	public ComplexFloat64Matrix() {
	}

	@Override
	public void multiply(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		MatrixMultiply.compute(G.CDBL, a, b, c);
	}

	@Override
	public void power(int power, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		MatrixPower.compute(power, G.CDBL, G.CDBL_VEC, G.CDBL_MAT, a, b);
	}

	@Override
	public void zero(ComplexFloat64MatrixMember a) {
		MatrixZero.compute(G.CDBL, a);
	}

	@Override
	public void negate(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		MatrixNegate.compute(G.CDBL, a, b);
	}

	@Override
	public void add(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		MatrixAddition.compute(G.CDBL, a, b, c);
	}

	@Override
	public void subtract(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		MatrixSubtraction.compute(G.CDBL, a, b, c);
	}

	@Override
	public boolean isEqual(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		return MatrixEqual.compute(G.CDBL, a, b);
	}

	@Override
	public boolean isNotEqual(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		return !isEqual(a, b);
	}

	@Override
	public void assign(ComplexFloat64MatrixMember from, ComplexFloat64MatrixMember to) {
		MatrixAssign.compute(G.CDBL, from, to);
	}

	@Override
	public ComplexFloat64MatrixMember construct() {
		return new ComplexFloat64MatrixMember();
	}

	@Override
	public ComplexFloat64MatrixMember construct(ComplexFloat64MatrixMember other) {
		return new ComplexFloat64MatrixMember(other);
	}

	@Override
	public ComplexFloat64MatrixMember construct(String s) {
		return new ComplexFloat64MatrixMember(s);
	}

	@Override
	public ComplexFloat64MatrixMember construct(StorageConstruction s, long d1, long d2) {
		return new ComplexFloat64MatrixMember(s, d1, d2);
	}

	@Override
	public void norm(ComplexFloat64MatrixMember a, Float64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void round(Round.Mode mode, Float64Member delta, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		MatrixRound.compute(G.CDBL, mode, delta, a, b);
	}

	@Override
	public boolean isNaN(ComplexFloat64MatrixMember a) {
		return MatrixIsNaN.compute(G.CDBL, a);
	}

	@Override
	public boolean isInfinite(ComplexFloat64MatrixMember a) {
		return MatrixIsInfinite.compute(G.CDBL, a);
	}

	@Override
	public void conjugate(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		MatrixConjugate.compute(G.CDBL, a, b);
	}

	@Override
	public void transpose(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		MatrixTranspose.compute(G.CDBL, a, b);
	}

	@Override
	public void conjugateTranspose(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		ComplexFloat64MatrixMember tmp = new ComplexFloat64MatrixMember();
		conjugate(a, tmp);
		transpose(tmp, b);
	}

	@Override
	public void det(ComplexFloat64MatrixMember a, ComplexFloat64Member b) {
		MatrixDeterminant.compute(this, G.CDBL, a, b);
	}

	@Override
	public void unity(ComplexFloat64MatrixMember a) {
		MatrixUnity.compute(G.CDBL, a);
	}

	@Override
	public void invert(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		MatrixInvert.compute(G.CDBL, G.CDBL_VEC, G.CDBL_MAT, a, b);
	}

	@Override
	public void divide(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		// invert and multiply
		ComplexFloat64MatrixMember invB = construct(b.storageType(), b.rows(), b.cols());
		invert(b, invB);
		multiply(a, invB, c);
	}

}
