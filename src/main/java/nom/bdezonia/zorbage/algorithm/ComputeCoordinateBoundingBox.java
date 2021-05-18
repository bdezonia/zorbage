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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.coordinates.CoordinateSpace;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComputeCoordinateBoundingBox {

	// do not instantiate
	
	private ComputeCoordinateBoundingBox() { }
	
	/**
	 * Compute the bounding box of the coordinate space of a {@link DimensionsDataSource}.
	 * The complete set of the data source's dimensions are transformed using its
	 * {@link CoordinateSpace} and a bounding box in transformed coordinates is computed.
	 * 
	 * @param data The data source of interest.
	 * @param minPt The computed min point (as a rmodule/vector).
	 * @param maxPt The computed max point (as a rmodule/vector).
	 */
	public static
		void compute(DimensionedDataSource<?> data,
						RModuleMember<HighPrecisionMember> minPt,
						RModuleMember<HighPrecisionMember> maxPt)
	{
		HighPrecisionMember mn = G.HP.construct("1");
		HighPrecisionMember mx = G.HP.construct("-1");
		HighPrecisionMember tmp = G.HP.construct();
		int numD = data.numDimensions();
		minPt.alloc(numD);
		maxPt.alloc(numD);
		for (int i = 0; i < numD; i++) {
			minPt.setV(i, mn);
			maxPt.setV(i, mx);
		}
		CoordinateSpace cspace = data.getCoordinateSpace();
		IntegerIndex idx = new IntegerIndex(numD);
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(data);
		while (iter.hasNext()) {
			iter.next(idx);
			for (int i = 0; i < numD; i++) {
				tmp.setV(cspace.project(idx, i));
				maxPt.getV(i, mx);
				minPt.getV(i, mn);
				if (G.HP.isLess().call(mx, mn)) {
					minPt.setV(i, tmp);
					maxPt.setV(i, tmp);
				}
				else {
					if (G.HP.isLess().call(tmp, mn)) {
						minPt.setV(i, tmp);
					}
					if (G.HP.isGreater().call(tmp, mx)) {
						maxPt.setV(i, tmp);
					}
				}
			}
		}
	}
}
