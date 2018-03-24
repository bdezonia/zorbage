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

import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
import nom.bdezonia.zorbage.algorithm.MatrixInvert;
import nom.bdezonia.zorbage.algorithm.Round;
import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.algebra.MatrixRing;
import nom.bdezonia.zorbage.type.algebra.RingWithUnity;
import nom.bdezonia.zorbage.type.algebra.Rounding;
import nom.bdezonia.zorbage.type.ctor.Constructible2dLong;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
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
		Rounding<Float64Member, ComplexFloat64MatrixMember>
{
	private static final ComplexFloat64Member ZERO = new ComplexFloat64Member(0,0);
	
	public ComplexFloat64Matrix() {
	}

	@Override
	public void multiply(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		if (c == a || c == b) throw new IllegalArgumentException("dangerous matrix multiply definition");
		if (a.cols() != b.rows()) throw new IllegalArgumentException("incompatible matrix shapes in matrix multiply");
		long rows = a.rows();
		long cols = b.cols();
		long common = a.cols(); 
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		ComplexFloat64Member term = new ComplexFloat64Member();
		for (long row = 0; row < rows; row++) {
			for (long col = 0; col < cols; col++) {
				G.CDBL.zero(sum);
				for (long i = 0; i < common; i++) {
					a.v(row, i, atmp);
					b.v(i, col, btmp);
					G.CDBL.multiply(atmp, btmp, term);
					G.CDBL.add(sum, term, sum);
				}
				c.setV(row, col, sum);
			}
		}
	}

	@Override
	public void power(int power, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("power requires a square matrix as input");
		if (power < 0) {
			power = -power;
			ComplexFloat64MatrixMember aInv = new ComplexFloat64MatrixMember();
			invert(a, aInv);
			ComplexFloat64MatrixMember tmp = new ComplexFloat64MatrixMember(aInv);
			ComplexFloat64MatrixMember tmp2 = new ComplexFloat64MatrixMember();
			for (int i = 2; i <= power; i++) {
				multiply(tmp, aInv, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp, b);
		}
		else if (power == 0) {
			//TODO if (isEqual(a, ZERO)) throw new IllegalArgumentException("0^0 is not a number");
			b.alloc(a.rows(), a.cols());
			unity(b);
		}
		else if (power == 1)
			assign(a,b);
		else { // power >= 2
			ComplexFloat64MatrixMember tmp = new ComplexFloat64MatrixMember(a);
			ComplexFloat64MatrixMember tmp2 = new ComplexFloat64MatrixMember();
			for (int i = 2; i <= power; i++) {
				multiply(tmp, a, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp, b);
		}
	}

	@Override
	public void zero(ComplexFloat64MatrixMember a) {
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.setV(row, col, ZERO);
			}
		}
	}

	@Override
	public void negate(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		if (a != b)
			b.alloc(a.rows(), a.cols());
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				G.CDBL.negate(tmp,tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void add(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (c != a && c != b) {
			c.alloc(a.rows(), a.cols());
		}
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				G.CDBL.add(atmp, btmp, tmp);
				c.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void subtract(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (c != a && c != b) {
			c.alloc(a.rows(), a.cols());
		}
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				G.CDBL.subtract(atmp, btmp, tmp);
				c.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isEqual(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		if (a == b) return true;
		if (a.rows() != b.rows()) return false;
		if (a.cols() != b.cols()) return false;
		ComplexFloat64Member value1 = new ComplexFloat64Member();
		ComplexFloat64Member value2 = new ComplexFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value1);
				b.v(r, c, value2);
				if (G.CDBL.isNotEqual(value1, value2))
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean isNotEqual(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		return !isEqual(a, b);
	}

	@Override
	public void assign(ComplexFloat64MatrixMember from, ComplexFloat64MatrixMember to) {
		if (from == to) return;
		to.alloc(from.rows(), from.cols());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (long row = 0; row < from.rows(); row++) {
			for (long col = 0; col < from.cols(); col++) {
				from.v(row, col, tmp);
				to.setV(row, col, tmp);
			}
		}
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
	public ComplexFloat64MatrixMember construct(MemoryConstruction m, StorageConstruction s, long d1, long d2) {
		return new ComplexFloat64MatrixMember(m, s, d1, d2);
	}

	@Override
	public void norm(ComplexFloat64MatrixMember a, ComplexFloat64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void round(Round.Mode mode, Float64Member delta, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		if (a != b)
			b.alloc(a.rows(), a.cols());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				G.CDBL.round(mode, delta, tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isNaN(ComplexFloat64MatrixMember a) {
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (G.CDBL.isNaN(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isInfinite(ComplexFloat64MatrixMember a) {
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (G.CDBL.isInfinite(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public void conjugate(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		if (a != b) {
			b.alloc(a.rows(), a.cols());
		}
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				G.CDBL.conjugate(atmp, btmp);
				b.setV(row, col, btmp);
			}
		}
	}

	@Override
	public void transpose(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		ComplexFloat64Member value = new ComplexFloat64Member();
		if (a == b) throw new IllegalArgumentException("cannot transpose in place");
		b.alloc(a.cols(), a.rows());
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r,  c, value);
				b.setV(c, r, value);
			}
		}
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
		final ComplexFloat64Member one = new ComplexFloat64Member(1, 0);
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				if (r == c)
					a.setV(r, c, one);
				else
					a.setV(r, c, ZERO);
			}
		}
	}

	@Override
	public void invert(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		MatrixInvert.compute(G.CDBL, G.CDBL_VEC, G.CDBL_MAT, a, b);
	}

	@Override
	public void divide(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		// invert and multiply
		ComplexFloat64MatrixMember invB =
				G.CDBL_MAT.construct(
						MemoryConstruction.DENSE,
						StorageConstruction.ARRAY,
						a.cols(), a.rows());
		MatrixInvert.compute(G.CDBL, G.CDBL_VEC, G.CDBL_MAT, b, invB);
		G.CDBL_MAT.multiply(a, invB, c);
	}

}
