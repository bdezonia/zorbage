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
package nom.bdezonia.zorbage.type.data.point;


import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Group;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PointGroup implements Group<PointGroup,Point> {

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
			if (a.dimension() != b.dimension()) return false;
			for (int i = 0; i < a.dimension(); i++) {
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
			if (a == b) return;
			if (a.dimension() != b.dimension()) {
				a.set(b);
			}
		}
	};

	@Override
	public Procedure2<Point, Point> assign() {
		return ASSIGN;
	}

	private final Function1<Boolean, Point> ZER =
			new Function1<Boolean, Point>()
	{
		@Override
		public Boolean call(Point a) {
			for (int i = 0; i < a.dimension(); i++) {
				if (a.component(i) != 0)
					return false;
			}
			return true;
		}
	};

	@Override
	public Function1<Boolean, Point> isZero() {
		return ZER;
	}
	
}
