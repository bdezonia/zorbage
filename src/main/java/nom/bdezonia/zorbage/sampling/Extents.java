/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.sampling;

import nom.bdezonia.zorbage.tuple.Tuple2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Extents {

	// do not instantiate
	
	private Extents() { }
	
	/**
	 * Compute the real extents of a real double sampling
	 * 
	 * @param sampling The set of points containing the sample
	 * @return A tuple in the form of (min,max)
	 */
	public static
		Tuple2<double[],double[]> computeDoubles(Sampling<double[]> sampling)
	{
		int numD = sampling.numDimensions();
		
		double[] min = new double[numD];
		double[] max = new double[numD];
		
		for (int i = 0; i < numD; i++) {
			min[i] = Double.MAX_VALUE;
			max[i] = -Double.MAX_VALUE;
		}
		
		double[] point = new double[numD];
		
		SamplingIterator<double[]> iter = sampling.iterator();

		if (iter.hasNext()) {
	
			iter.next(point);
			
			for (int i = 0; i < numD; i++) {
				
				double val = point[i];
				
				min[i] = val;

				max[i] = val;
			}
		}

		while (iter.hasNext()) {
			
			iter.next(point);
		
			for (int i = 0; i < numD; i++) {

				double val = point[i];
				
				if (val < min[i]) {
					
					min[i] = val;
				}
				else if (val > max[i]) {
				
					max[i] = val;
				}
			}
		}
		
		return new Tuple2<double[], double[]>(min, max);
	}
	
	/**
	 * Compute the long extents of an integral long sampling
	 * 
	 * @param sampling The set of points containing the sample
	 * @return A tuple in the form of (min,max)
	 */
	public static
		Tuple2<long[],long[]> computeLongs(Sampling<long[]> sampling)
	{
		int numD = sampling.numDimensions();
		
		long[] min = new long[numD];
		long[] max = new long[numD];
		
		for (int i = 0; i < numD; i++) {
			min[i] = Long.MAX_VALUE;
			max[i] = Long.MIN_VALUE;
		}
		
		long[] point = new long[numD];
		
		SamplingIterator<long[]> iter = sampling.iterator();
		
		if (iter.hasNext()) {
			
			iter.next(point);
			
			for (int i = 0; i < numD; i++) {
				
				long val = point[i];
				
				min[i] = val;

				max[i] = val;
			}
		}

		while (iter.hasNext()) {
			
			iter.next(point);
		
			for (int i = 0; i < numD; i++) {

				long val = point[i];
				
				if (val < min[i]) {
					
					min[i] = val;
				}
				else if (val > max[i]) {
				
					max[i] = val;
				}
			}
		}
		
		return new Tuple2<long[], long[]>(min, max);
	}
	
	/**
	 * Compute the extents of a long index sampling
	 * 
	 * @param sampling The set of points containing the sample
	 * @return A tuple in the form of (min,max)
	 */
	public static
		Tuple2<IntegerIndex,IntegerIndex> computeIntegerIndices(Sampling<IntegerIndex> sampling)
	{
		int numD = sampling.numDimensions();
		
		IntegerIndex min = new IntegerIndex(numD);
		IntegerIndex max = new IntegerIndex(numD);
		
		for (int i = 0; i < numD; i++) {
			min.set(i, Long.MAX_VALUE);
			max.set(i, Long.MIN_VALUE);
		}
		
		IntegerIndex point = new IntegerIndex(numD);
		
		SamplingIterator<IntegerIndex> iter = sampling.iterator();
		
		if (iter.hasNext()) {
			
			iter.next(point);
			
			for (int i = 0; i < numD; i++) {
				
				long val = point.get(i);
				
				min.set(i, val);

				max.set(i, val);
			}
		}

		while (iter.hasNext()) {
			
			iter.next(point);
		
			for (int i = 0; i < numD; i++) {

				long val = point.get(i);
				
				if (val < min.get(i)) {
					
					min.set(i, val);
				}
				else if (val > max.get(i)) {
					
					max.set(i, val);
				}
			}
		}
		
		return new Tuple2<IntegerIndex,IntegerIndex>(min, max);
	}
}
