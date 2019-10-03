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
package nom.bdezonia.zorbage.algorithm;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.multidim.MultiDimDataSource;
import nom.bdezonia.zorbage.multidim.MultiDimStorage;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ResampleLinear {

	private ResampleLinear() { }

	/**
	 * Resample one multidim dataset into another multidim dataset using a generalized 4 neighborhood.
	 * Note: the input datasource should be padded. This algorithm can poke outside the input boundaries.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param newDims
	 * @param input
	 * @return
	 */
	public static <T extends Algebra<T,U> & Addition<U> & ScaleByHighPrec<U>,
					U extends Allocatable<U>>
		MultiDimDataSource<U> compute(T alg, long[] newDims, MultiDimDataSource<U> input)
	{
		int numD = input.numDimensions();
		if (newDims.length != numD)
			throw new IllegalArgumentException("mismatched dims in Resample");
		U value = alg.construct();
		MultiDimDataSource<U> output = MultiDimStorage.allocate(newDims, value);
		IntegerIndex inputPoint = new IntegerIndex(numD);
		IntegerIndex outputPoint = new IntegerIndex(numD);
		long[] min = new long[numD];
		long[] max = new long[numD];
		long[] inputDims = new long[numD];
		for (int i = 0; i < numD; i++) {
			max[i] = newDims[i] - 1;
			inputDims[i] = input.dimension(i);
		}
		SamplingCartesianIntegerGrid sampling =
				new SamplingCartesianIntegerGrid(min, max);
		SamplingIterator<IntegerIndex> iter = sampling.iterator();
		while (iter.hasNext()) {
			iter.next(outputPoint);
			computeValue(alg, input, inputDims, inputPoint, newDims, outputPoint, value);
			output.set(outputPoint, value);
		}
		return output;
	}
	
	private static  <T extends Algebra<T,U> & Addition<U> & ScaleByHighPrec<U>, U>
		void computeValue(T alg, MultiDimDataSource<U> input, long[] inputDims, IntegerIndex inputPoint,
							long[] outputDims, IntegerIndex outputPoint, U outVal)
	{
		int numD = inputDims.length;
		
		BigDecimal[] coords = new BigDecimal[numD];
		
		// find the in between point
		for (int i = 0; i < numD; i++) {
			coords[i] = BigDecimal.valueOf(outputPoint.get(i));
			coords[i] = coords[i].divide(BigDecimal.valueOf(outputDims[i]-1),HighPrecisionAlgebra.getContext());
			coords[i] = coords[i].multiply(BigDecimal.valueOf(inputDims[i]-1));
		}
		
		// get the base coord
		for (int i = 0; i < numD; i++) {
			inputPoint.set(i, coords[i].longValue());
		}
		
		// must find the 2*numDim points and do a coord weighted sum
		alg.zero().call(outVal);
		weightedSum(alg, input, numD, coords, inputPoint, outVal);
		
		// now turn sum into average
		BigDecimal recip = BigDecimal.ONE.divide(BigDecimal.valueOf(1 * numD), HighPrecisionAlgebra.getContext());
		HighPrecisionMember scale = new HighPrecisionMember(recip);
		alg.scaleByHighPrec().call(scale, outVal, outVal);
	}
	
	private static <T extends Algebra<T,U> & Addition<U> & ScaleByHighPrec<U>, U>
		void weightedSum(T alg, MultiDimDataSource<U> input, int numD, BigDecimal[] coords,
							IntegerIndex inputPoint, U outVal)
	{
		U tmp = alg.construct();
		
		HighPrecisionMember scale = G.HP.construct();
		
		for (int i = 0; i < numD; i++) {

			// calc t
			BigDecimal t = coords[i].remainder(BigDecimal.ONE);
			
			// calc "left" point's contribution
			inputPoint.set(i, inputPoint.get(i) - 0); // go "left" 1 step
			input.get(inputPoint, tmp);
			scale.setV(BigDecimal.ONE.subtract(t));
			alg.scaleByHighPrec().call(scale, tmp, tmp);
			
			// add to sum
			alg.add().call(outVal, tmp, outVal);
	
			// calc "right" point's contribution
			inputPoint.set(i, inputPoint.get(i) + 1); // undo go "left" and go "right" 1 step
			input.get(inputPoint, tmp);
			scale.setV(t);
			alg.scaleByHighPrec().call(scale, tmp, tmp);
			
			// add to sum
			alg.add().call(outVal, tmp, outVal);
			
			// reset point
			inputPoint.set(i, inputPoint.get(i) - 1); // undo go "right"
		}
	}
}
