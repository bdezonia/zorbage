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
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Infinite;
import nom.bdezonia.zorbage.algebra.NaN;
import nom.bdezonia.zorbage.algebra.NegInfinite;
import nom.bdezonia.zorbage.algebra.Tolerance;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.function.Function7;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float32.Float32VectorMember;

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
	private static final float ZERO_TOL = 0.00000001f;
	
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
	
	private final Function7<Boolean, Float, Float, Float, Float, Float, Float, PolygonalChainMember> INTERSECT =
	
		new Function7<Boolean, Float, Float, Float, Float, Float, Float, PolygonalChainMember>() {

			@Override
			public Boolean call(Float minx, Float miny, Float minz, Float maxx, Float maxy, Float maxz, PolygonalChainMember pc) {

				// degenerate chains intersect nothing
				
				if (pc.pointCount() == 0) return false;

				// chains whose bounds are outside the specified region don't intersect
				
				if (pc.getMinX() > maxx) return false;
				if (pc.getMinY() > maxy) return false;
				if (pc.getMinZ() > maxz) return false;
				if (pc.getMaxX() < minx) return false;
				if (pc.getMaxY() < miny) return false;
				if (pc.getMaxZ() < minz) return false;

				// there is some overlap in regions: now check point by point
				
				Float32Member x = G.FLT.construct();
				Float32Member y = G.FLT.construct();
				Float32Member z = G.FLT.construct();

				for (int i = 0; i < pc.pointCount(); i++) {
					
					pc.getX(i, x);
					pc.getY(i, y);
					pc.getZ(i, z);
				
					// is point inside the region
					if (minx <= x.v() && x.v() <= maxx &&
							miny <= y.v() && y.v() <= maxy &&
							minz <= z.v() && z.v() <= maxz)
						return true;
				}
				
				// if here then every point failed to be in the bounds
				//   so check for segment intersections with faces
				
				pc.getX(0, x);
				pc.getY(0, y);
				pc.getZ(0, z);
				
				Float32Member x2 = G.FLT.construct();
				Float32Member y2 = G.FLT.construct();
				Float32Member z2 = G.FLT.construct();

				for (int i = 1; i < pc.pointCount(); i++) {
					
					pc.getX(i, x2);
					pc.getY(i, y2);
					pc.getZ(i, z2);
					
					// calc against every face of volume to see if segment intersects

					// eight corner points:
					//   minx,miny,minz
					//   minx,miny,maxz
					//   minx,maxy,minz
					//   minx,maxy,maxz
					//   maxx,miny,minz
					//   maxx,miny,maxz
					//   maxx,maxy,minz
					//   maxx,maxy,maxz
					
					// 6 faces defined by two triangles each : 12 total triangles

					// face 1 : front
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,miny,minz, maxx,miny,minz, maxx,miny,maxz))
						return true;
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,miny,minz, minx,miny,maxz, maxx,miny,maxz))
						return true;

					// face 2 : back
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,maxy,minz, maxx,maxy,minz, maxx,maxy,maxz))
						return true;
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,maxy,minz, minx,maxy,maxz, maxx,maxy,maxz))
						return true;
					
					// face 3 : left
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,miny,minz, minx,miny,maxz, minx,maxy,maxz))
						return true;
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,miny,minz, minx,maxy,minz, minx,maxy,maxz))
						return true;
					
					// face 4 : right
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), maxx,miny,minz, maxx,miny,maxz, maxx,maxy,maxz))
						return true;
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), maxx,miny,minz, maxx,maxy,minz, maxx,maxy,maxz))
						return true;
					
					// face 5 : top
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,miny,maxz, minx,maxy,maxz, maxx,maxy,maxz))
						return true;
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,miny,maxz, maxx,miny,maxz, maxx,maxy,maxz))
						return true;
					
					// face 6 : bottom
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,miny,minz, minx,maxy,minz, maxx,maxy,minz))
						return true;
					
					if (segmentIntersectsTriangle(x.v(),y.v(),z.v(), x2.v(),y2.v(),z2.v(), minx,miny,minz, maxx,miny,minz, maxx,maxy,minz))
						return true;
					
					// prepare to move to next segment
					
					G.FLT.assign().call(x2, x);
					G.FLT.assign().call(y2, y);
					G.FLT.assign().call(z2, z);
				}
				
				return false;
			}
		};

	// from https://stackoverflow.com/questions/53962225/how-to-know-if-a-line-segment-intersects-a-triangle-in-3d-space
	
	// TODO: replace all the high level vector code with low level inline float
	//   code that computes the same stuff.
		
	private final boolean segmentIntersectsTriangle(
			float segx0, float segy0, float segz0,
			float segx1, float segy1, float segz1,
			float trix0, float triy0, float triz0,
			float trix1, float triy1, float triz1,
			float trix2, float triy2, float triz2)
	{
		Float32VectorMember segmentVector = new Float32VectorMember((segx1-segx0), (segy1-segy0), (segz1-segz0));
		
		Float32Member tmp = G.FLT.construct();
		
		G.FLT_VEC.norm().call(segmentVector, tmp);
		
		G.FLT.invert().call(tmp, tmp);
		
		G.FLT_VEC.scale().call(tmp, segmentVector, segmentVector);
		
		Float32VectorMember edge1 = new Float32VectorMember((trix1-trix0),(triy1-triy0),(triz1-triz0));

		Float32VectorMember edge2 = new Float32VectorMember((trix2-trix0),(triy2-triy0),(triz2-triz0));

		Float32VectorMember h = G.FLT_VEC.construct();
		
		G.FLT_VEC.crossProduct().call(segmentVector, edge2, h);
		
		G.FLT_VEC.dotProduct().call(edge1, h, tmp);

		if (tmp.v() > -ZERO_TOL && tmp.v() < ZERO_TOL)
		{
			return false;    // This ray is parallel to this triangle.
		}
		
		float f = 1.0f / tmp.v();

		Float32VectorMember s = new Float32VectorMember((segx0-trix0), (segy0-triy0), (segz0-triz0));
		
		G.FLT_VEC.dotProduct().call(s, h, tmp);
		
		float u = f * tmp.v();
		
		if (u < 0.0 || u > 1.0)
		{
			return false;
		}
		
		Float32VectorMember q = G.FLT_VEC.construct();
		
		G.FLT_VEC.crossProduct().call(s, edge1, q);
		
		G.FLT_VEC.dotProduct().call(segmentVector, q, tmp);
		
		float v = f * tmp.v();
		
		if (v < 0.0 || u + v > 1.0)
		{
			return false;
		}
		
		// At this stage we can compute t to find out where the intersection point is on the line.
		
		G.FLT_VEC.dotProduct().call(edge2, q, tmp);
		
		float t = f * tmp.v();
		
		if (t >= ZERO_TOL && t <= 1.0) // segment intersection
		{
			return true;
		}
		else // This means that there is a line intersection but not a segment intersection.
		{
			return false;
		}
	}
	
	public Function7<Boolean, Float, Float, Float, Float, Float, Float, PolygonalChainMember> intersect() {
		return INTERSECT;
	}

}
