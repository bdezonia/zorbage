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
public class ResampleAveragedCubics {

	/**
	 * Cubicly resamples one multidim dataset into another multidim dataset using a generalized 4 neighborhood.
	 * The algorithm computes a series of cubicly combined values, 1 per 2-dimensional combination, from the four
	 * nearest points along those two dimensions. Then that series of values is averaged.
	 * Note: The input datasource should be padded. This algorithm can poke outside the input boundaries.
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
	
	private static <T extends Algebra<T,U> & Addition<U> & ScaleByHighPrec<U>, U>
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
		
		// must find the various points and do a sum
		alg.zero().call(outVal);
		sum(alg, input, numD, coords, inputPoint, outVal);
		
		// now turn sum into average
		BigDecimal recip = BigDecimal.ONE.divide(BigDecimal.valueOf(numD), HighPrecisionAlgebra.getContext());
		HighPrecisionMember scale = new HighPrecisionMember(recip);
		alg.scaleByHighPrec().call(scale, outVal, outVal);
	}
	
	private static <T extends Algebra<T,U> & Addition<U> & ScaleByHighPrec<U>, U>
		void sum(T alg, MultiDimDataSource<U> input, int numD, BigDecimal[] coords,
							IntegerIndex inputPoint, U outVal)
	{
		U tmp = alg.construct();
		
		for (int i = 0; i < numD; i++) {
				
			// calc u
			BigDecimal t = coords[i].remainder(BigDecimal.ONE);
			
			cubicSolution(alg, input, inputPoint, coords, i, t, tmp);
			
			// add to sum
			alg.add().call(outVal, tmp, outVal);
			
		}
	}
	
	// See https://dsp.stackexchange.com/questions/18265/bicubic-interpolation
	
	private static <T extends Algebra<T,U> & Addition<U> & ScaleByHighPrec<U>, U>
		void cubicSolution(T alg, MultiDimDataSource<U> input, IntegerIndex inputPoint, BigDecimal[] coords,
							int dim, BigDecimal t, U outVal)
	{
		U ym1 = alg.construct();
		U y0 = alg.construct();
		U y1 = alg.construct();
		U y2 = alg.construct();
		
		input.get(inputPoint, y0);
		inputPoint.set(dim, inputPoint.get(dim) + 1);
		input.get(inputPoint, y1);
		inputPoint.set(dim, inputPoint.get(dim) + 1);
		input.get(inputPoint, y2);
		inputPoint.set(dim, inputPoint.get(dim) - 3);
		input.get(inputPoint, ym1);
		inputPoint.set(dim, inputPoint.get(dim) + 1);
		
		U a = alg.construct();
		U b = alg.construct();
		U c = alg.construct();
		U d = alg.construct();

		U tmp1 = alg.construct();
		U tmp2 = alg.construct();
		U tmp3 = alg.construct();
		U tmp4 = alg.construct();
		
		HighPrecisionMember hp = G.HP.construct();
		
		// find d
		alg.assign().call(y0, d);
		
		// find c
		hp.setV(BigDecimal.valueOf(-0.5));
		alg.scaleByHighPrec().call(hp, ym1, tmp1);
		hp.setV(BigDecimal.valueOf(0.5));
		alg.scaleByHighPrec().call(hp, y1, tmp2);
		alg.add().call(tmp1, tmp2, c);
		
		// find b
		alg.assign().call(ym1, tmp1);
		hp.setV(BigDecimal.valueOf(-2.5));
		alg.scaleByHighPrec().call(hp, y0, tmp2);
		hp.setV(BigDecimal.valueOf(2));
		alg.scaleByHighPrec().call(hp, y1, tmp3);
		hp.setV(BigDecimal.valueOf(-0.5));
		alg.scaleByHighPrec().call(hp, y2, tmp4);
		alg.add().call(tmp1, tmp2, tmp1);
		alg.add().call(tmp3, tmp4, tmp3);
		alg.add().call(tmp1, tmp3, b);
		
		// find a
		hp.setV(BigDecimal.valueOf(-0.5));
		alg.scaleByHighPrec().call(hp, ym1, tmp1);
		hp.setV(BigDecimal.valueOf(1.5));
		alg.scaleByHighPrec().call(hp, y0, tmp2);
		hp.setV(BigDecimal.valueOf(-1.5));
		alg.scaleByHighPrec().call(hp, y1, tmp3);
		hp.setV(BigDecimal.valueOf(0.5));
		alg.scaleByHighPrec().call(hp, y2, tmp4);
		alg.add().call(tmp1, tmp2, tmp1);
		alg.add().call(tmp3, tmp4, tmp3);
		alg.add().call(tmp1, tmp3, a);
		
		// combine: f(x) = ax^3 + bx^2 + cx + d
		alg.assign().call(d, tmp2);
		hp.setV(t);
		alg.scaleByHighPrec().call(hp, c, tmp1);
		alg.add().call(tmp2, tmp1, tmp2);
		hp.setV(t.multiply(t));
		alg.scaleByHighPrec().call(hp, b, tmp1);
		alg.add().call(tmp2, tmp1, tmp2);
		hp.setV(t.multiply(t).multiply(t));
		alg.scaleByHighPrec().call(hp, a, tmp1);
		alg.add().call(tmp2, tmp1, tmp2);
		
		// assign the output value
		alg.assign().call(tmp2, outVal);
	}
}
