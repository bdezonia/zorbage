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

import zorbage.type.algebra.MatrixRing;
import zorbage.type.algebra.RingWithUnity;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64MatrixRing
	implements
		RingWithUnity<ComplexFloat64MatrixRing, ComplexFloat64MatrixMember>,
		MatrixRing<ComplexFloat64MatrixRing, ComplexFloat64MatrixMember, ComplexFloat64Field, ComplexFloat64Member>
{
	private ComplexFloat64Field g;
	
	public ComplexFloat64MatrixRing(ComplexFloat64Field g) {
		this.g = g;
	}

	@Override
	public void multiply(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		if (c == a || c == b) throw new IllegalArgumentException("dangerous matrix multiply definition");
		if (a.cols() != b.rows()) throw new IllegalArgumentException("incompatible matrix shapes in matrix multiply");
		int rows = a.rows();
		int cols = b.cols();
		int common = a.cols(); 
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		ComplexFloat64Member term = new ComplexFloat64Member();
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				g.zero(sum);
				for (int i = 0; i < common; i++) {
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
	public void power(int power, ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
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
			ComplexFloat64MatrixMember tmp = new ComplexFloat64MatrixMember(a);
			ComplexFloat64MatrixMember tmp2 = new ComplexFloat64MatrixMember();
			for (int i = 2; i <= power; i++) {
				multiply(a, tmp, tmp2);
				assign(tmp2, tmp);
			}
			assign(tmp2, b);
		}
	}

	@Override
	public void zero(ComplexFloat64MatrixMember a) {
		ComplexFloat64Member zero = new ComplexFloat64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.setV(row, col, zero);
			}
		}
	}

	@Override
	public void negate(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		if (a != b)
			b.init(a.rows(), a.cols());
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				tmp.setR(-tmp.r());
				tmp.setI(-tmp.i());
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void add(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot add matrices of different shapes");
		if (c != a && c != b) {
			c.init(a.rows(), a.cols());
		}
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				g.add(atmp, btmp, tmp);
				c.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void subtract(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b, ComplexFloat64MatrixMember c) {
		if (a.rows() != b.rows()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (a.cols() != b.cols()) throw new IllegalArgumentException("cannot subtract matrices of different shapes");
		if (c != a && c != b) {
			c.init(a.rows(), a.cols());
		}
		ComplexFloat64Member atmp = new ComplexFloat64Member();
		ComplexFloat64Member btmp = new ComplexFloat64Member();
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				b.v(row, col, btmp);
				g.subtract(atmp, btmp, tmp);
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
		for (int r = 0; r < a.rows(); r++) {
			for (int c = 0; c < a.cols(); c++) {
				a.v(r, c, value1);
				b.v(r, c, value2);
				if (g.isNotEqual(value1, value2))
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
		to.init(from.rows(), from.cols());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int row = 0; row < from.rows(); row++) {
			for (int col = 0; col < from.cols(); col++) {
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
	public void norm(ComplexFloat64MatrixMember a, ComplexFloat64Member b) {
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void roundTowardsZero(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundTowardsZero(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundAwayFromZero(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundAwayFromZero(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundPositive(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundPositive(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundNegative(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundNegative(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public void roundNearest(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		if (a != b)
			b.init(a.rows(), a.cols());
		ComplexFloat64Member tmp = new ComplexFloat64Member();
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, tmp);
				g.roundNearest(tmp, tmp);
				b.setV(row, col, tmp);
			}
		}
	}

	@Override
	public boolean isNaN(ComplexFloat64MatrixMember a) {
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (int r = 0; r < a.rows(); r++) {
			for (int c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (g.isNaN(value))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isInfinite(ComplexFloat64MatrixMember a) {
		ComplexFloat64Member value = new ComplexFloat64Member();
		for (int r = 0; r < a.rows(); r++) {
			for (int c = 0; c < a.cols(); c++) {
				a.v(r, c, value);
				if (g.isInfinite(value))
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
			b.init(a.rows(), a.cols());
		}
		for (int row = 0; row < a.rows(); row++) {
			for (int col = 0; col < a.cols(); col++) {
				a.v(row, col, atmp);
				g.conjugate(atmp, btmp);
				b.setV(row, col, btmp);
			}
		}
	}

	@Override
	public void transpose(ComplexFloat64MatrixMember a, ComplexFloat64MatrixMember b) {
		ComplexFloat64Member value = new ComplexFloat64Member();
		if (a == b) throw new IllegalArgumentException("cannot transpose in place");
		b.init(a.cols(), a.rows());
		for (int r = 0; r < a.rows(); r++) {
			for (int c = 0; c < a.cols(); c++) {
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
		// TODO
		throw new IllegalArgumentException("TODO");
	}

	@Override
	public void unity(ComplexFloat64MatrixMember a) {
		ComplexFloat64Member one = new ComplexFloat64Member(1, 0);
		zero(a);
		for (int i = 0; i < Math.min(a.rows(), a.cols()); i++) {
			a.setV(i, i, one);
		}
	}

}
