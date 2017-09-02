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
package zorbage.type.data;

import zorbage.type.algebra.MatrixRing;
import zorbage.type.algebra.RingWithUnity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class OctonionFloat64Matrix
	implements
		RingWithUnity<OctonionFloat64Matrix, OctonionFloat64MatrixMember>,
		MatrixRing<OctonionFloat64Matrix, OctonionFloat64MatrixMember, OctonionFloat64Group, OctonionFloat64Member>
{
	private static final OctonionFloat64Group g = new OctonionFloat64Group();
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member();
	
	public OctonionFloat64Matrix() { }

	@Override
	public void multiply(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
		if (c == a || c == b) throw new IllegalArgumentException("dangerous matrix multiply definition");
		if (a.cols() != b.rows()) throw new IllegalArgumentException("incompatible matrix shapes in matrix multiply");
		long rows = a.rows();
		long cols = b.cols();
		long common = a.cols(); 
		OctonionFloat64Member sum = new OctonionFloat64Member();
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		OctonionFloat64Member term = new OctonionFloat64Member();
		for (long row = 0; row < rows; row++) {
			for (long col = 0; col < cols; col++) {
				g.zero(sum);
				for (long i = 0; i < common; i++) {
					a.v(row, i, atmp);
					b.v(i, col, btmp);
					g.multiply(atmp, btmp, term);
					g.add(sum, term, sum);
				}
				c.setV(row, col, sum);
			}
		}
	}

	@Override
	public void power(int power, OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		if (a.rows() != a.cols())
			throw new IllegalArgumentException("power requires a square matrix as input");
		if (power < 0) {
			power = -power;
			OctonionFloat64MatrixMember aInv = new OctonionFloat64MatrixMember();
			invert(a, aInv);
			OctonionFloat64MatrixMember tmp = new OctonionFloat64MatrixMember(aInv);
			OctonionFloat64MatrixMember tmp2 = new OctonionFloat64MatrixMember();
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
			OctonionFloat64MatrixMember tmp = new OctonionFloat64MatrixMember(a);
			OctonionFloat64MatrixMember tmp2 = new OctonionFloat64MatrixMember();
			for (int i = 2; i <= power; i++) {
				multiply(tmp, a, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp, b);
		}
	}

	@Override
	public void zero(OctonionFloat64MatrixMember a) {
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.setV(row, col, ZERO);
			}
		}
	}

	@Override
	public void negate(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		if (a != b)
			b.init(a.rows(), a.cols());
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.negate(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void add(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (c != a && c != b) {
			c.init(a.rows(), a.cols());
		}
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				g.add(atmp, btmp, tmp);
				c.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void subtract(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (c != a && c != b) {
			c.init(a.rows(), a.cols());
		}
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				g.subtract(atmp, btmp, tmp);
				c.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isEqual(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		if (a == b) return true;
		if (a.rows() != b.rows()) return false;
		if (a.cols() != b.cols()) return false;
		OctonionFloat64Member value1 = new OctonionFloat64Member();
		OctonionFloat64Member value2 = new OctonionFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value1);
				b.v(r, c, value2);
				if (g.isNotEqual(value1, value2))
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean isNotEqual(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		return !isEqual(a, b);
	}

	@Override
	public void assign(OctonionFloat64MatrixMember from, OctonionFloat64MatrixMember to) {
		if (from == to) return;
		to.init(from.rows(), from.cols());
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < from.rows(); row++) {
			for (long col = 0; col < from.cols(); col++) {
				from.v(row, col, tmp);
				to.setV(row, col, tmp);
			}
		}
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
	public void norm(OctonionFloat64MatrixMember a, OctonionFloat64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void roundTowardsZero(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundTowardsZero(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundAwayFromZero(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundAwayFromZero(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundPositive(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundPositive(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundNegative(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundNegative(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundNearest(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundNearest(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundNearestEven(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		OctonionFloat64Member tmp = new OctonionFloat64Member();
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundNearestEven(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isNaN(OctonionFloat64MatrixMember a) {
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (g.isNaN(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isInfinite(OctonionFloat64MatrixMember a) {
		OctonionFloat64Member value = new OctonionFloat64Member();
		for (long r = 0; r < a.rows(); r++) {
			for (long c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (g.isInfinite(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public void conjugate(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		OctonionFloat64Member atmp = new OctonionFloat64Member();
		OctonionFloat64Member btmp = new OctonionFloat64Member();
		if (a != b) {
			b.init(a.rows(), a.cols());
		}
		for (long row = 0; row < a.rows(); row++) {
			for (long col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				g.conjugate(atmp, btmp);
				b.setV(row, col, btmp);
			}
		}
	}

	@Override
	public void transpose(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		OctonionFloat64Member value = new OctonionFloat64Member();
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
	public void conjugateTranspose(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		OctonionFloat64MatrixMember tmp = new OctonionFloat64MatrixMember();
		conjugate(a, tmp);
		transpose(tmp, b);
	}

	@Override
	public void det(OctonionFloat64MatrixMember a, OctonionFloat64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void unity(OctonionFloat64MatrixMember a) {
		OctonionFloat64Member one = new OctonionFloat64Member(1, 0, 0, 0, 0, 0, 0, 0);
		zero(a);
		for (long i = 0; i < Math.min(a.rows(), a.cols()); i++) {
			a.setV(i, i, one);
		}
	}

	@Override
	public void invert(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b) {
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void divide(OctonionFloat64MatrixMember a, OctonionFloat64MatrixMember b, OctonionFloat64MatrixMember c) {
		// invert and multiply
		throw new IllegalArgumentException("TODO");
	}

}
