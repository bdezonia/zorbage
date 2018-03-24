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

import nom.bdezonia.zorbage.algorithm.MatrixDeterminant;
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
public class QuaternionFloat64Matrix
	implements
		RingWithUnity<QuaternionFloat64Matrix, QuaternionFloat64MatrixMember>,
		MatrixRing<QuaternionFloat64Matrix, QuaternionFloat64MatrixMember, QuaternionFloat64Group, QuaternionFloat64Member>,
		Constructible2dLong<QuaternionFloat64MatrixMember>,
		Rounding<Float64Member, QuaternionFloat64MatrixMember>
{
	private static final QuaternionFloat64Member ZERO = new QuaternionFloat64Member();
	
	public QuaternionFloat64Matrix() { }

	@Override
	public void multiply(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
		if (c == a || c == b) throw new IllegalArgumentException("dangerous matrix multiply definition");
		if (a.cols() != b.rows()) throw new IllegalArgumentException("incompatible matrix shapes in matrix multiply");
		long rows = a.rows();
		long cols = b.cols();
		long common = a.cols(); 
		QuaternionFloat64Member sum = new QuaternionFloat64Member();
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		QuaternionFloat64Member term = new QuaternionFloat64Member();
		for (long row = 0; row < rows; row++) {
			for (long col = 0; col < cols; col++) {
				G.QDBL.zero(sum);
				for (long i = 0; i < common; i++) {
					a.v(row, i, atmp);
					b.v(i, col, btmp);
					G.QDBL.multiply(atmp, btmp, term);
					G.QDBL.add(sum, term, sum);
				}
				c.setV(row, col, sum);
			}
		}
	}

	@Override
	public void power(int power, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("power requires a square matrix as input");
		if (power < 0) {
			power = -power;
			QuaternionFloat64MatrixMember aInv = new QuaternionFloat64MatrixMember();
			invert(a, aInv);
			QuaternionFloat64MatrixMember tmp = new QuaternionFloat64MatrixMember(aInv);
			QuaternionFloat64MatrixMember tmp2 = new QuaternionFloat64MatrixMember();
			for (int i = 2; i <= power; i++) {
				multiply(tmp, aInv, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp, b);
		}
		else if (power == 0) {
			// TODO if (isEqual(a, ZERO)) throw new IllegalArgumentException("0^0 is not a number");
			b.alloc(a.rows(), a.cols());
			unity(b);
		}
		else if (power == 1)
			assign(a,b);
		else { // power >= 2
			QuaternionFloat64MatrixMember tmp = new QuaternionFloat64MatrixMember(a);
			QuaternionFloat64MatrixMember tmp2 = new QuaternionFloat64MatrixMember();
			for (int i = 2; i <= power; i++) {
				multiply(tmp, a, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp, b);
		}
	}

	@Override
	public void zero(QuaternionFloat64MatrixMember a) {
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.setV(row, col, ZERO);
			}
		}
	}

	@Override
	public void negate(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		if (a != b)
			b.alloc(a.rows(), a.cols());
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				G.QDBL.negate(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void add(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (c != a && c != b) {
			c.alloc(a.rows(), a.cols());
		}
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				G.QDBL.add(atmp, btmp, tmp);
				c.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void subtract(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b, QuaternionFloat64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (c != a && c != b) {
			c.alloc(a.rows(), a.cols());
		}
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				G.QDBL.subtract(atmp, btmp, tmp);
				c.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isEqual(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		if (a == b) return true;
		if (a.rows() != b.rows()) return false;
		if (a.cols() != b.cols()) return false;
		QuaternionFloat64Member value1 = new QuaternionFloat64Member();
		QuaternionFloat64Member value2 = new QuaternionFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value1);
				b.v(r, c, value2);
				if (G.QDBL.isNotEqual(value1, value2))
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean isNotEqual(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		return !isEqual(a, b);
	}

	@Override
	public void assign(QuaternionFloat64MatrixMember from, QuaternionFloat64MatrixMember to) {
		if (from == to) return;
		to.alloc(from.rows(), from.cols());
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long row = 0; row < from.rows(); row++) {
			for (long col = 0; col < from.cols(); col++) {
				from.v(row, col, tmp);
				to.setV(row, col, tmp);
			}
		}
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
	public QuaternionFloat64MatrixMember construct(MemoryConstruction m, StorageConstruction s, long d1, long d2) {
		return new QuaternionFloat64MatrixMember(m, s, d1, d2);
	}

	@Override
	public void norm(QuaternionFloat64MatrixMember a, QuaternionFloat64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void round(Round.Mode mode, Float64Member delta, QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		if (a != b)
			b.alloc(a.rows(), a.cols());
		QuaternionFloat64Member tmp = new QuaternionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				G.QDBL.round(mode, delta, tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isNaN(QuaternionFloat64MatrixMember a) {
		QuaternionFloat64Member value = new QuaternionFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (G.QDBL.isNaN(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isInfinite(QuaternionFloat64MatrixMember a) {
		QuaternionFloat64Member value = new QuaternionFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (G.QDBL.isInfinite(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public void conjugate(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		QuaternionFloat64Member atmp = new QuaternionFloat64Member();
		QuaternionFloat64Member btmp = new QuaternionFloat64Member();
		if (a != b) {
			b.alloc(a.rows(), a.cols());
		}
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				G.QDBL.conjugate(atmp, btmp);
				b.setV(row, col, btmp);
			}
		}
	}

	@Override
	public void transpose(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		QuaternionFloat64Member value = new QuaternionFloat64Member();
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
		final QuaternionFloat64Member one = new QuaternionFloat64Member(1, 0, 0, 0);
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
	public void invert(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void divide(QuaternionFloat64MatrixMember a, QuaternionFloat64MatrixMember b,
			QuaternionFloat64MatrixMember c)
	{
		// invert and multiply
		throw new IllegalArgumentException("TODO");
	}

}
