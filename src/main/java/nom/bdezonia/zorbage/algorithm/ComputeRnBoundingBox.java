/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.axis.CoordinateSpace;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComputeRnBoundingBox {

	// do not instantiate
	
	private ComputeRnBoundingBox() { }
	
	/**
	 * 
	 * @param data
	 * @param min
	 * @param max
	 */
	public static
		void compute(DimensionedDataSource<?> data,
						RModuleMember<HighPrecisionMember> mins,
						RModuleMember<HighPrecisionMember> maxes)
	{
		HighPrecisionMember mn = G.HP.construct("1");
		HighPrecisionMember mx = G.HP.construct("-1");
		HighPrecisionMember tmp = G.HP.construct();
		int numD = data.numDimensions();
		mins.alloc(numD);
		maxes.alloc(numD);
		for (int i = 0; i < numD; i++) {
			mins.setV(i, mn);
			maxes.setV(i, mx);
		}
		CoordinateSpace cspace = data.getCoordinateSpace();
		IntegerIndex idx = new IntegerIndex(numD);
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(data);
		while (iter.hasNext()) {
			iter.next(idx);
			for (int i = 0; i < numD; i++) {
				tmp.setV(cspace.toRn(idx, i));
				maxes.getV(i, mx);
				mins.getV(i, mn);
				if (G.HP.isLess().call(mx, mn)) {
					maxes.setV(i, tmp);
					mins.setV(i, tmp);
				}
				else {
					if (G.HP.isLess().call(tmp, mn)) {
						mins.setV(i, tmp);
					}
					if (G.HP.isGreater().call(tmp, mx)) {
						maxes.setV(i, tmp);
					}
				}
			}
		}
	}
}
