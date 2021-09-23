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
package nom.bdezonia.zorbage.type.geom.polygonalchain;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Infinite;
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.algebra.NegInfinite;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;

/**
 * @author Barry DeZonia
 */
public class PolygonalChainAlgebra
	implements
		Algebra<PolygonalChainAlgebra, PolygonalChainMember>,
		Infinite<PolygonalChainMember>,
		NegInfinite<PolygonalChainMember>,
		NaN<PolygonalChainMember>,
		Tolerance<Float, PolygonalChainMember>
{
	public PolygonalChainAlgebra() { }

	@Override
	public PolygonalChainMember construct() {

		return new PolygonalChainMember();
	}

	@Override
	public PolygonalChainMember construct(PolygonalChainMember other) {

		return new PolygonalChainMember(other);
	}

	@Override
	public PolygonalChainMember construct(String str) {

		throw new
			UnsupportedOperationException(
				"Ability to construct polygonal chains from a String is not implemented yet."
			);
	}

	private final Function2<Boolean, PolygonalChainMember, PolygonalChainMember> EQ =

		new Function2<Boolean, PolygonalChainMember, PolygonalChainMember>() {
		
			@Override
			public Boolean call(PolygonalChainMember a, PolygonalChainMember b) {
				
				if (a.x.length != b.x.length)
					return false;
				
				for (int i = 0; i < a.x.length; i++) {
					if (a.x[i] != b.x[i]) return false;
					if (a.y[i] != b.y[i]) return false;
					if (a.z[i] != b.z[i]) return false;
				}
				
				return true;
			}
		};
			
	@Override
	public Function2<Boolean, PolygonalChainMember, PolygonalChainMember> isEqual() {

		return EQ;
	}

	private final Function2<Boolean, PolygonalChainMember, PolygonalChainMember> NEQ =

			new Function2<Boolean, PolygonalChainMember, PolygonalChainMember>() {
			
				@Override
				public Boolean call(PolygonalChainMember a, PolygonalChainMember b) {
					
					return !isEqual().call(a, b);
				}
			};
				
	@Override
	public Function2<Boolean, PolygonalChainMember, PolygonalChainMember> isNotEqual() {

		return NEQ;
	}

	private final Procedure2<PolygonalChainMember, PolygonalChainMember> ASS =
		new Procedure2<PolygonalChainMember, PolygonalChainMember>() {
			
			@Override
			public void call(PolygonalChainMember a, PolygonalChainMember b) {
				b.set(a);
			}
		};
			
	@Override
	public Procedure2<PolygonalChainMember, PolygonalChainMember> assign() {

		return ASS;
	}

	private final Procedure1<PolygonalChainMember> ZER =
		new Procedure1<PolygonalChainMember>() {
			
			@Override
			public void call(PolygonalChainMember a) {
				for (int i = 0; i < a.x.length; i++) {
					a.x[i] = 0;
					a.y[i] = 0;
					a.z[i] = 0;
				}
			}
		};
			
	@Override
	public Procedure1<PolygonalChainMember> zero() {

		return ZER;
	}

	private final Function1<Boolean, PolygonalChainMember> ISZER =

		new Function1<Boolean, PolygonalChainMember>() {
			
			@Override
			public Boolean call(PolygonalChainMember a) {

				for (int i = 0; i < a.x.length; i++) {
					if (a.x[i] != 0) return false;
					if (a.y[i] != 0) return false;
					if (a.z[i] != 0) return false;
				}
				
				return true;
			}
		};
	
	@Override
	public Function1<Boolean, PolygonalChainMember> isZero() {

		return ISZER;
	}

	@Override
	public String typeDescription() {

		return "Polygonal chain of 3d float32 points";
	}

	private final Function1<Boolean, PolygonalChainMember> ISNAN =

			new Function1<Boolean, PolygonalChainMember>() {
				
				@Override
				public Boolean call(PolygonalChainMember a) {

					for (int i = 0; i < a.x.length; i++) {
						if (Float.isNaN(a.x[i])) return true;
						if (Float.isNaN(a.y[i])) return true;
						if (Float.isNaN(a.z[i])) return true;
					}
					
					return false;
				}
			};
		
	@Override
	public Function1<Boolean, PolygonalChainMember> isNaN() {

		return ISNAN;
	}

	private final Procedure1<PolygonalChainMember> NAN =

			new Procedure1<PolygonalChainMember>() {
				
				@Override
				public void call(PolygonalChainMember a) {

					for (int i = 0; i < a.x.length; i++) {
						a.x[i] = Float.NaN;
						a.y[i] = Float.NaN;
						a.z[i] = Float.NaN;
					}
					
				}
			};
		
	@Override
	public Procedure1<PolygonalChainMember> nan() {

		return NAN;
	}

	private final Procedure1<PolygonalChainMember> NEGINF =

			new Procedure1<PolygonalChainMember>() {
				
				@Override
				public void call(PolygonalChainMember a) {

					for (int i = 0; i < a.x.length; i++) {
						a.x[i] = Float.NEGATIVE_INFINITY;
						a.y[i] = Float.NEGATIVE_INFINITY;
						a.z[i] = Float.NEGATIVE_INFINITY;
					}
					
				}
			};
		
	@Override
	public Procedure1<PolygonalChainMember> negInfinite() {

		return NEGINF;
	}

	private final Function1<Boolean, PolygonalChainMember> ISINF =

		new Function1<Boolean, PolygonalChainMember>() {
				
			@Override
			public Boolean call(PolygonalChainMember a) {

				for (int i = 0; i < a.x.length; i++) {
					if (Float.isInfinite(a.x[i])) return true;
					if (Float.isInfinite(a.y[i])) return true;
					if (Float.isInfinite(a.z[i])) return true;
				}
					
				return false;
			}
		};
		
	@Override
	public Function1<Boolean, PolygonalChainMember> isInfinite() {

		return ISINF;
	}

	private final Procedure1<PolygonalChainMember> INF =

			new Procedure1<PolygonalChainMember>() {
				
				@Override
				public void call(PolygonalChainMember a) {

					for (int i = 0; i < a.x.length; i++) {
						a.x[i] = Float.POSITIVE_INFINITY;
						a.y[i] = Float.POSITIVE_INFINITY;
						a.z[i] = Float.POSITIVE_INFINITY;
					}
					
				}
			};

	@Override
	public Procedure1<PolygonalChainMember> infinite() {

		return INF;
	}

	private final Function3<Boolean, Float, PolygonalChainMember, PolygonalChainMember> WITHIN =

		new Function3<Boolean, Float, PolygonalChainMember, PolygonalChainMember>() {
			
			@Override
			public Boolean call(Float tol, PolygonalChainMember a, PolygonalChainMember b) {

				if (a.pointCount() != b.pointCount())
					return false;
				
				for (int i = 0; i < a.pointCount(); i++) {
					if (Math.abs(a.x[i] - b.x[i]) > tol) return false;
					if (Math.abs(a.y[i] - b.y[i]) > tol) return false;
					if (Math.abs(a.z[i] - b.z[i]) > tol) return false;
				}
				
				return true;
			}
		};
			
	@Override
	public Function3<Boolean, Float, PolygonalChainMember, PolygonalChainMember> within() {

		return WITHIN;
	}

}
