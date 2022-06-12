/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.RModuleMember;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class EstimateSpacing {

	// do not instantiate
	
	private EstimateSpacing() { }

	/**
	 * Calculate the average cell spacings of a {@link DimensionedDataSource}.
	 * It works by calculating the min and max extents in coordinate space
	 * (x,y,z,etc) from index space (i,j,k,etc.). Then it divides each extent
	 * by the number of elements along these axes. This gives a linear estimate
	 * of the scalings per cell of the possibly nonlinear space. If the space is
	 * linear these scaling values are exactly equal to the actual spacings.
	 * 
	 * @param data
	 * @param result
	 */
	public static
		void compute(DimensionedDataSource<?> data, RModuleMember<HighPrecisionMember> result)
	{
		RModuleMember<HighPrecisionMember> min = G.HP_VEC.construct();
		RModuleMember<HighPrecisionMember> max = G.HP_VEC.construct();

		ComputeCoordinateBoundingBox.compute(data, min, max);
		
		result.alloc(min.length());
		
		HighPrecisionMember avgSpacing = G.HP.construct();
		HighPrecisionMember numer = G.HP.construct();
		HighPrecisionMember denom = G.HP.construct();
		HighPrecisionMember left = G.HP.construct();
		HighPrecisionMember right = G.HP.construct();
		for (int i = 0; i < data.numDimensions(); i++) {
			if (data.dimension(i) == 1) {
				avgSpacing.setV(BigDecimal.ONE);
			}
			else {
				min.getV(i, left);
				max.getV(i, right);
				G.HP.subtract().call(right, left, numer);
				denom.setV(BigDecimal.valueOf(data.dimension(i)-1));
				G.HP.divide().call(numer, denom, avgSpacing);
			}
			result.setV(i, avgSpacing);
		}
	}
}
