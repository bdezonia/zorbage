/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.point;


import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PointAlgebra
	implements Algebra<PointAlgebra,Point>, Addition<Point>, Scale<Point, Float64Member>,
		Random<Point>
{

	@Override
	public Point construct() {
		return new Point();
	}

	@Override
	public Point construct(Point other) {
		return new Point(other);
	}

	@Override
	public Point construct(String str) {
		return new Point(str);
	}

	private final Function2<Boolean, Point, Point> EQ =
			new Function2<Boolean, Point, Point>()
	{
		@Override
		public Boolean call(Point a, Point b) {
			if (a == b) return true;
			if (a.numDimensions() != b.numDimensions()) return false;
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.component(i) != b.component(i)) return false;
			}
			return true;
		}
	};

	@Override
	public Function2<Boolean, Point, Point> isEqual() {
		return EQ;
	}

	private final Function2<Boolean, Point, Point> NEQ =
			new Function2<Boolean, Point, Point>()
	{
		@Override
		public Boolean call(Point a, Point b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean, Point, Point> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Point, Point> ASSIGN =
			new Procedure2<Point, Point>()
	{
		@Override
		public void call(Point a, Point b) {
			b.set(a);
		}
	};

	@Override
	public Procedure2<Point, Point> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Point> EQZER =
			new Function1<Boolean, Point>()
	{
		@Override
		public Boolean call(Point a) {
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.component(i) != 0)
					return false;
			}
			return true;
		}
	};

	@Override
	public Function1<Boolean, Point> isZero() {
		return EQZER;
	}

	private final Procedure1<Point> ZER =
			new Procedure1<Point>()
	{
		@Override
		public void call(Point a) {
			for (int i = 0; i < a.numDimensions(); i++) {
				a.setComponent(i, 0);
			}
		}
	};

	@Override
	public Procedure1<Point> zero() {
		return ZER;
	}

	private final Procedure2<Point, Point> NEG =
			new Procedure2<Point, Point>()
	{
		@Override
		public void call(Point a, Point b) {
			if (a.numDimensions() != b.numDimensions())
				throw new IllegalArgumentException("mismatched point dimensions");
			for (int i = 0; i < a.numDimensions(); i++) {
				b.setComponent(i, -a.component(i));
			}
		}
	};

	@Override
	public Procedure2<Point, Point> negate() {
		return NEG;
	}

	private final Procedure3<Point, Point, Point> ADD =
			new Procedure3<Point, Point, Point>()
	{
		@Override
		public void call(Point a, Point b, Point c) {
			if (a.numDimensions() != b.numDimensions() || a.numDimensions() != c.numDimensions())
				throw new IllegalArgumentException("mismatched point dimensions");
			for (int i = 0; i < a.numDimensions(); i++) {
				c.setComponent(i, a.component(i)+b.component(i));
			}
		}
	};

	@Override
	public Procedure3<Point, Point, Point> add() {
		return ADD;
	}

	private final Procedure3<Point, Point, Point> SUB =
			new Procedure3<Point, Point, Point>()
	{
		@Override
		public void call(Point a, Point b, Point c) {
			if (a.numDimensions() != b.numDimensions() || a.numDimensions() != c.numDimensions())
				throw new IllegalArgumentException("mismatched point dimensions");
			for (int i = 0; i < a.numDimensions(); i++) {
				c.setComponent(i, a.component(i)-b.component(i));
			}
		}
	};

	@Override
	public Procedure3<Point, Point, Point> subtract() {
		return SUB;
	}

	
	private final Procedure3<Float64Member, Point, Point> SCALE =
			new Procedure3<Float64Member, Point, Point>()
	{
		@Override
		public void call(Float64Member a, Point b, Point c) {
			if (b.numDimensions() != c.numDimensions())
				throw new IllegalArgumentException("mismatched point dimensionality");
			for (int i = 0; i < b.numDimensions(); i++) {
				c.setComponent(i, a.v() * b.component(i));
			}
		}
	};

	@Override
	public Procedure3<Float64Member, Point, Point> scale() {
		return SCALE;
	}

	private final Procedure1<Point> RAND = new Procedure1<Point>()
	{
		@Override
		public void call(Point a) {
			ThreadLocalRandom rng = ThreadLocalRandom.current();
			for (int i = 0; i < a.numDimensions(); i++) {
				a.setComponent(i, rng.nextDouble());
			}
		}
	};

	@Override
	public Procedure1<Point> random() {
		return RAND;
	}
	
}
