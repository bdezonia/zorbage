/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.point;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.misc.RealUtils;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.ConstructibleFromDouble;
import nom.bdezonia.zorbage.algebra.Infinite;
import nom.bdezonia.zorbage.algebra.NegInfinite;
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.algebra.Random;
import nom.bdezonia.zorbage.algebra.Scale;
import nom.bdezonia.zorbage.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.algebra.ScaleByOneHalf;
import nom.bdezonia.zorbage.algebra.ScaleByRational;
import nom.bdezonia.zorbage.algebra.ScaleByTwo;
import nom.bdezonia.zorbage.algebra.ScaleComponents;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PointAlgebra
	implements
		Algebra<PointAlgebra,Point>,
		Addition<Point>,
		Scale<Point, Double>,
		ScaleByHighPrec<Point>,
		ScaleByRational<Point>,
		ScaleByDouble<Point>,
		ScaleByOneHalf<Point>,
		ScaleByTwo<Point>,
		ScaleComponents<Point, Double>,
		Tolerance<Double, Point>,
		Infinite<Point>,
		NegInfinite<Point>,
		NaN<Point>,
		Random<Point>,
		ConstructibleFromDouble<Point>
{
	private static final MathContext CONTEXT = new MathContext(18);
	
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

	@Override
	public Point construct(double... vals) {
		return new Point(vals);
	}

	private final Function2<Boolean, Point, Point> EQ =
			new Function2<Boolean, Point, Point>()
	{
		@Override
		public Boolean call(Point a, Point b) {
			if (a == b)
				return true;
			if (a.numDimensions() != b.numDimensions())
				return false;
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.component(i) != b.component(i))
					return false;
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
				c.setComponent(i, a.component(i) + b.component(i));
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
				c.setComponent(i, a.component(i) - b.component(i));
			}
		}
	};

	@Override
	public Procedure3<Point, Point, Point> subtract() {
		return SUB;
	}

	
	private final Procedure3<Double, Point, Point> SCALE =
			new Procedure3<Double, Point, Point>()
	{
		@Override
		public void call(Double factor, Point a, Point b) {
			scaleComponents().call(factor, a, b);
		}
	};

	@Override
	public Procedure3<Double, Point, Point> scale() {
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

	private final Procedure3<RationalMember, Point, Point> SBR =
			new Procedure3<RationalMember, Point, Point>()
	{
		@Override
		public void call(RationalMember factor, Point a, Point b) {
			if (a.numDimensions() != b.numDimensions())
				throw new IllegalArgumentException("mismatched point dimensionality");
			BigDecimal t;
			for (int i = 0; i < a.numDimensions(); i++) {
				t = BigDecimal.valueOf(a.component(i));
				t = t.multiply(new BigDecimal(factor.n()));
				t = t.divide(new BigDecimal(factor.d()), CONTEXT);
				b.setComponent(i, t.doubleValue());
			}
		}
	};

	@Override
	public Procedure3<RationalMember, Point, Point> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Point, Point> SBD =
			new Procedure3<Double, Point, Point>()
	{
		@Override
		public void call(Double factor, Point a, Point b) {
			scaleComponents().call(factor, a, b);
		}
	};

	@Override
	public Procedure3<Double, Point, Point> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, Point, Point> SBH =
			new Procedure3<HighPrecisionMember, Point, Point>()
	{
		@Override
		public void call(HighPrecisionMember factor, Point a, Point b) {
			if (a.numDimensions() != b.numDimensions())
				throw new IllegalArgumentException("mismatched point dimensionality");
			BigDecimal t;
			for (int i = 0; i < a.numDimensions(); i++) {
				t = factor.v().multiply(BigDecimal.valueOf(a.component(i)));
				b.setComponent(i, t.doubleValue());
			}
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, Point, Point> scaleByHighPrec() {
		return SBH;
	}

	private final Function3<Boolean, Double, Point, Point> WITHIN =
			new Function3<Boolean, Double, Point, Point>()
	{
		@Override
		public Boolean call(Double tol, Point a, Point b) {
			if (a.numDimensions() != b.numDimensions())
				throw new IllegalArgumentException("mismatched point dimensionality");
			for (int i = 0; i < a.numDimensions(); i++) {
				if (!RealUtils.near(a.component(i), b.component(i), tol))
					return false;
			}
			return true;
		}
	};

	@Override
	public Function3<Boolean, Double, Point, Point> within() {
		return WITHIN;
	}

	private final Procedure3<Double, Point, Point> SCMP =
			new Procedure3<Double, Point, Point>()
	{
		@Override
		public void call(Double factor, Point a, Point b) {
			if (a.numDimensions() != b.numDimensions())
				throw new IllegalArgumentException("mismatched point dimensionality");
			for (int i = 0; i < a.numDimensions(); i++) {
				b.setComponent(i, factor * a.component(i));
			}
		}
	};

	@Override
	public Procedure3<Double, Point, Point> scaleComponents() {
		return SCMP;
	}

	private final Procedure3<Integer, Point, Point> SBTWO =
			new Procedure3<Integer, Point, Point>()
	{
		@Override
		public void call(Integer numTimes, Point a, Point b) {
			double factor;
			if (numTimes < 0)
				factor = 0.5;
			else
				factor = 2;
			factor = Math.pow(factor, Math.abs(numTimes));
			scaleComponents().call(factor, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Point, Point> scaleByTwo() {
		return SBTWO;
	}

	private final Procedure3<Integer, Point, Point> SBHALF =
			new Procedure3<Integer, Point, Point>()
	{
		@Override
		public void call(Integer numTimes, Point a, Point b) {
			double factor;
			if (numTimes < 0)
				factor = 2;
			else
				factor = 0.5;
			factor = Math.pow(factor, Math.abs(numTimes));
			scaleComponents().call(factor, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Point, Point> scaleByOneHalf() {
		return SBHALF;
	}

	private final Function1<Boolean, Point> ISNAN =
			new Function1<Boolean, Point>()
	{
		@Override
		public Boolean call(Point a) {
			for (int i = 0; i < a.numDimensions(); i++) {
				if (Double.isNaN(a.component(i)))
					return true;
			}
			return false;
		}
	};

	@Override
	public Function1<Boolean, Point> isNaN() {
		return ISNAN;
	}

	private final Procedure1<Point> NAN =
			new Procedure1<Point>()
	{
		@Override
		public void call(Point a) {
			for (int i = 0; i < a.numDimensions(); i++) {
				a.setComponent(i, Double.NaN);
			}
		}
	};

	@Override
	public Procedure1<Point> nan() {
		return NAN;
	}

	private final Function1<Boolean, Point> ISINF =
			new Function1<Boolean, Point>()
	{
		@Override
		public Boolean call(Point a) {
			for (int i = 0; i < a.numDimensions(); i++) {
				if (Double.isInfinite(a.component(i)))
					return true;
			}
			return false;
		}
	};

	@Override
	public Function1<Boolean, Point> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Point> INF =
			new Procedure1<Point>()
	{
		@Override
		public void call(Point a) {
			for (int i = 0; i < a.numDimensions(); i++) {
				a.setComponent(i, Double.POSITIVE_INFINITY);
			}
		}
	};

	@Override
	public Procedure1<Point> infinite() {
		return INF;
	}

	private final Procedure1<Point> NEGINF =
			new Procedure1<Point>()
	{
		@Override
		public void call(Point a) {
			for (int i = 0; i < a.numDimensions(); i++) {
				a.setComponent(i, Double.NEGATIVE_INFINITY);
			}
		}
	};
	
	@Override
	public Procedure1<Point> negInfinite() {
		return NEGINF;
	}

}
