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

// TODO: this impl does not use Float64OrderedField. It also assumes storage is in memory doubles.
// It could use ordered field and an ArrayStorageFloat64. Might want more than ArrayStorage too for
// really giant matrices (or sparse matrices?). I guess this is an optimized implementation.

import zorbage.type.algebra.MatrixRing;
import zorbage.type.algebra.RingWithUnity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64MatrixRing
  implements
    RingWithUnity<Float64MatrixRing, Float64MatrixMember>,
    MatrixRing<Float64MatrixRing, Float64MatrixMember, Float64OrderedField, Float64Member>
{
	public Float64MatrixRing() {
	}
	
	@Override
	public void norm(Float64MatrixMember a, Float64Member b) {
		throw new IllegalArgumentException("TODO");
		// TODO
		// MathWorld.com
		// The spectral norm ||A||_2, which is the square root of the maximum eigenvalue of A^(H)A
		// (where A^(H) is the conjugate transpose), is often referred to as "the" matrix norm.
	}

	@Override
	public void roundTowardsZero(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b) {
			b.init(a.rows(), a.cols());
		}
		Float64Member value1 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				if (value1.v() > 0)
					value1.setV(Math.floor(value1.v()));
				else
					value1.setV(Math.ceil(value1.v()));
				b.setV(row, col, value1);
			}
		}
	}

	@Override
	public void roundAwayFromZero(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b) {
			b.init(a.rows(), a.cols());
		}
		Float64Member value1 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				if (value1.v() < 0)
					value1.setV(Math.floor(value1.v()));
				else
					value1.setV(Math.ceil(value1.v()));
				b.setV(row, col, value1);
			}
		}
	}

	@Override
	public void roundPositive(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b) {
			b.init(a.rows(), a.cols());
		}
		Float64Member value1 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				value1.setV(Math.ceil(value1.v()));
				b.setV(row, col, value1);
			}
		}
	}

	@Override
	public void roundNegative(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b) {
			b.init(a.rows(), a.cols());
		}
		Float64Member value1 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				value1.setV(Math.floor(value1.v()));
				b.setV(row, col, value1);
			}
		}
	}

	@Override
	public void roundNearest(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b) {
			b.init(a.rows(), a.cols());
		}
		Float64Member value1 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				value1.setV(Math.rint(value1.v()));
				b.setV(row, col, value1);
			}
		}
	}

	@Override
	public boolean isNaN(Float64MatrixMember a) {
		Float64Member value1 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				if (Double.isNaN(value1.v()))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isInfinite(Float64MatrixMember a) {
		if (isNaN(a)) return false;
		Float64Member value1 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				if (Double.isInfinite(value1.v()))
					return true;
			}
		}
		return false;
	}

	@Override
	public void conjugate(Float64MatrixMember a, Float64MatrixMember b) {
		assign(a, b); // nothing to do for a real matrix
	}

	@Override
	public void transpose(Float64MatrixMember a, Float64MatrixMember b) {
		if (a.rows() == a.cols() && a.rows() == b.rows() && a.cols() == b.cols())
			assign(a,b);
		else {
			if (a == b)
				throw new IllegalArgumentException("cannot do an in place transpose of a non-square matrix");
			b.init(a.cols(), a.rows());
			Float64Member value = new Float64Member();
			for (int r = 0; r < a.rows(); r++) {
				for (int c = 0; c < a.cols(); c++) {
					a.v(r, c, value);
					b.setV(c, r, value);
				}				
			}
		}
	}

	@Override
	public void conjugateTranspose(Float64MatrixMember a, Float64MatrixMember b) {
		transpose(a,b);
	}

	@Override
	public void det(Float64MatrixMember a, Float64Member b) {
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("determinant only defined for square matrices");
		// TODO
		// reduce to row echelon form and get the trace: I think that is enough. Maybe sign wrong?
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void multiply(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
		if (c == a || c == b) throw new IllegalArgumentException("dangerous matrix multiply definition");
		if (a.cols() != b.rows()) throw new IllegalArgumentException("incompatible matrix shapes in matrix multiply");
		int rows = a.rows();
		int cols = b.cols();
		int common = a.cols(); 
		Float64Member sum = new Float64Member();
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				double s = 0;
				for (int i = 0; i < common; i++) {
					a.v(row, i, atmp);
					b.v(i, col, btmp);
					double term = atmp.v() * btmp.v();
					s += term;
				}
				sum.setV(s);
				c.setV(row, col, sum);
			}
		}
	}

	@Override
	public void power(int power, Float64MatrixMember a, Float64MatrixMember b) {
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("power requires a square matrix as input");
		if (power < 0)
			throw new IllegalArgumentException("power cannot be negative for matricies");
		else if (power == 0) {
			b.init(a.rows(), a.cols());
			unity(b);
		}
		else if (power == 1)
			assign(a,b);
		else { // power >= 2
			Float64MatrixMember tmp = new Float64MatrixMember(a);
			Float64MatrixMember tmp2 = new Float64MatrixMember();
			for (int i = 2; i <= power; i++) {
				multiply(a, tmp, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp2, b);
		}
	}

	@Override
	public void zero(Float64MatrixMember a) {
		Float64Member zero = new Float64Member();
		for (int r = 0; r < a.rows(); r++) {
			for (int c = 0; c < a.cols(); c++) {
				a.setV(r,c,zero);
			}
		}
	}

	@Override
	public void negate(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b) {
			b.init(a.rows(), a.cols());
		}
		Float64Member value = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value);
				value.setV(-value.v());
				b.setV(row, col, value);
			}
		}
	}

	@Override
	public void add(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (c != a && c != b) {
			c.init(a.rows(), a.cols());
		}
		Float64Member value1 = new Float64Member();
		Float64Member value2 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				b.v(row, col, value2);
				value1.setV(value1.v() + value2.v());
				c.setV(row, col, value1);
			}
		}
	}

	@Override
	public void subtract(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (c != a && c != b) {
			c.init(a.rows(), a.cols());
		}
		Float64Member value1 = new Float64Member();
		Float64Member value2 = new Float64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, value1);
				b.v(row, col, value2);
				value1.setV(value1.v() - value2.v());
				c.setV(row, col, value1);
			}
		}
	}

	@Override
	public boolean isEqual(Float64MatrixMember a, Float64MatrixMember b) {
		if (a == b) return true;
		if (a.rows() != b.rows()) return false;
		if (a.cols() != b.cols()) return false;
		Float64Member avalue = new Float64Member();
		Float64Member bvalue = new Float64Member();
		for (int r = 0; r < a.rows(); r++) {
			for (int c = 0; c < a.cols(); c++) {
				a.v(r, c, avalue);
				b.v(r, c, bvalue);
				if (avalue.v() != bvalue.v()) return false;
			}
		}
		return true;
	}

	@Override
	public boolean isNotEqual(Float64MatrixMember a, Float64MatrixMember b) {
		return !isEqual(a, b);
	}

	@Override
	public void assign(Float64MatrixMember from, Float64MatrixMember to) {
		if (to == from) return;
		Float64Member tmp = new Float64Member();
		to.init(from.rows(), from.cols());
		for (int r = 0; r < from.rows(); r++) {
			for (int c = 0; c < from.cols(); c++) {
				from.v(r, c, tmp);
				to.setV(r, c, tmp);
			}
		}
	}

	@Override
	public Float64MatrixMember construct() {
		return new Float64MatrixMember();
	}

	@Override
	public Float64MatrixMember construct(Float64MatrixMember other) {
		return new Float64MatrixMember(other);
	}

	@Override
	public Float64MatrixMember construct(String s) {
		return new Float64MatrixMember(s);
	}

	@Override
	public void unity(Float64MatrixMember a) {
		zero(a);
		Float64Member one = new Float64Member(1);
		for (int i = 0; i < Math.min(a.rows(), a.cols()); i++) {
			a.setV(i, i, one);
		}
	}
}
