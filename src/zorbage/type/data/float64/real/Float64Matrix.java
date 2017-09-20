/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.type.data.float64.real;

import zorbage.type.algebra.MatrixRing;
import zorbage.type.algebra.RingWithUnity;
import zorbage.type.ctor.Constructible2dLong;
import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Float64Matrix
	implements
		RingWithUnity<Float64Matrix, Float64MatrixMember>,
		MatrixRing<Float64Matrix, Float64MatrixMember, Float64Group, Float64Member>,
		Constructible2dLong<Float64MatrixMember>
{
	private static final Float64Group dbl = new Float64Group();
	private static final Float64Member ZERO = new Float64Member(0);
	
	public Float64Matrix() { }

	@Override
	public void multiply(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
		if (c == a || c == b) throw new IllegalArgumentException("dangerous matrix multiply definition");
		if (a.cols() != b.rows()) throw new IllegalArgumentException("incompatible matrix shapes in matrix multiply");
		long rows = a.rows();
		long cols = b.cols();
		long common = a.cols(); 
		Float64Member sum = new Float64Member();
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		Float64Member term = new Float64Member();
		for (long row = 0; row < rows; row++) {
			for (long col = 0; col < cols; col++) {
				dbl.zero(sum);
				for (long i = 0; i < common; i++) {
					a.v(row, i, atmp);
					b.v(i, col, btmp);
					dbl.multiply(atmp, btmp, term);
					dbl.add(sum, term, sum);
				}
				c.setV(row, col, sum);
			}
		}
	}

	@Override
	public void power(int power, Float64MatrixMember a, Float64MatrixMember b) {
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("power requires a square matrix as input");
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("power requires a square matrix as input");
		if (power < 0) {
			power = -power;
			Float64MatrixMember aInv = new Float64MatrixMember();
			invert(a, aInv);
			Float64MatrixMember tmp = new Float64MatrixMember(aInv);
			Float64MatrixMember tmp2 = new Float64MatrixMember();
			for (int i = 2; i <= power; i++) {
				multiply(tmp, aInv, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp, b);
		}
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
				multiply(tmp, a, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp, b);
		}
	}

	@Override
	public void zero(Float64MatrixMember a) {
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.setV(row, col, ZERO);
			}
		}
	}

	@Override
	public void negate(Float64MatrixMember a, Float64MatrixMember b) {
		Float64Member tmp = new Float64Member();
		if (a != b)
			b.init(a.rows(), a.cols());
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				dbl.negate(tmp,tmp);
				b.setV(row, col, tmp);
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
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				dbl.add(atmp, btmp, tmp);
				c.setV(row, col, tmp);
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
		Float64Member atmp = new Float64Member();
		Float64Member btmp = new Float64Member();
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				dbl.subtract(atmp, btmp, tmp);
				c.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isEqual(Float64MatrixMember a, Float64MatrixMember b) {
		if (a == b) return true;
		if (a.rows() != b.rows()) return false;
		if (a.cols() != b.cols()) return false;
		Float64Member value1 = new Float64Member();
		Float64Member value2 = new Float64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value1);
				b.v(r, c, value2);
				if (dbl.isNotEqual(value1, value2))
					return false;
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
		if (from == to) return;
		to.init(from.rows(), from.cols());
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < from.rows(); row++) {
			for (long col = 0; col < from.cols(); col++) {
				from.v(row, col, tmp);
				to.setV(row, col, tmp);
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
	public Float64MatrixMember construct(MemoryConstruction m, StorageConstruction s, long d1, long d2) {
		return new Float64MatrixMember(m, s, d1, d2);
	}

	@Override
	public void norm(Float64MatrixMember a, Float64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void roundTowardsZero(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				dbl.roundTowardsZero(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundAwayFromZero(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				dbl.roundAwayFromZero(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundPositive(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				dbl.roundPositive(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundNegative(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				dbl.roundNegative(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundNearest(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				dbl.roundNearest(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundNearestEven(Float64MatrixMember a, Float64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		Float64Member tmp = new Float64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				dbl.roundNearestEven(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isNaN(Float64MatrixMember a) {
		Float64Member value = new Float64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (dbl.isNaN(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isInfinite(Float64MatrixMember a) {
		Float64Member value = new Float64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (dbl.isInfinite(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public void conjugate(Float64MatrixMember a, Float64MatrixMember b) {
		assign(a, b);
	}

	@Override
	public void transpose(Float64MatrixMember a, Float64MatrixMember b) {
		Float64Member value = new Float64Member();
		if (a == b) throw new IllegalArgumentException("cannot transpose in place");
		b.init(a.cols(), a.rows());
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r,  c, value);
				b.setV(c, r, value);
			}
		}
	}

	@Override
	public void conjugateTranspose(Float64MatrixMember a, Float64MatrixMember b) {
		transpose(a, b);
	}

	@Override
	public void det(Float64MatrixMember a, Float64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void unity(Float64MatrixMember a) {
		final Float64Member one = new Float64Member(1);
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
	public void invert(Float64MatrixMember a, Float64MatrixMember b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void divide(Float64MatrixMember a, Float64MatrixMember b, Float64MatrixMember c) {
		// invert and multiply
		throw new IllegalArgumentException("TODO");
	}

}
