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
package example;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.RealIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingCartesianRealGrid;
import nom.bdezonia.zorbage.sampling.SamplingComplement;
import nom.bdezonia.zorbage.sampling.SamplingConditional;
import nom.bdezonia.zorbage.sampling.SamplingCylindricalRealGrid;
import nom.bdezonia.zorbage.sampling.SamplingDifference;
import nom.bdezonia.zorbage.sampling.SamplingGeneral;
import nom.bdezonia.zorbage.sampling.SamplingIntersection;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.sampling.SamplingPolarRealGrid;
import nom.bdezonia.zorbage.sampling.SamplingSphericalRealGrid;
import nom.bdezonia.zorbage.sampling.SamplingSymmetricDifference;
import nom.bdezonia.zorbage.sampling.SamplingUnion;

/**
 * @author Barry DeZonia
 */
class Sampling {
	
	// Zorbage provides may ways to sample your data
	
	// Walk all the points in a n-d integer grid
	
	void example1() {
		
		SamplingCartesianIntegerGrid grid =
				new SamplingCartesianIntegerGrid(new long[] {-1,-2,-3}, new long[] {10,20,30});
		SamplingIterator<IntegerIndex> iter = grid.iterator();
		IntegerIndex index = new IntegerIndex(grid.numDimensions());
		while (iter.hasNext()) {
			iter.next(index);
			// do something with the grid index like lookup values in a data source
		}
	}
	
	// Walk all the points in a n-d real grid
	
	void example2() {
		
		SamplingCartesianRealGrid grid =
				new SamplingCartesianRealGrid(new double[] {-1,-2,-3}, new double[] {10,20,30}, new long[] {50, 50, 50});
		SamplingIterator<RealIndex> iter = grid.iterator();
		RealIndex index = new RealIndex(grid.numDimensions());
		while (iter.hasNext()) {
			iter.next(index);
			// do something with the grid index like lookup values in a data source
		}
	}
	
	// Walk all the points in a 2-d polar grid
	
	void example3() {
		
		SamplingPolarRealGrid grid =
				new SamplingPolarRealGrid(0.25, 50, Math.PI/45, 22);
		SamplingIterator<RealIndex> iter = grid.iterator();
		RealIndex index = new RealIndex(grid.numDimensions());
		while (iter.hasNext()) {
			iter.next(index);
			// do something with the grid index like lookup values in a data source
		}
	}
	
	// Walk all the points in a 3-d cylindrical grid
	
	void example4() {
		
		SamplingCylindricalRealGrid grid =
				new SamplingCylindricalRealGrid(0.25, 50, Math.PI/45, 22, 0.5, 100);
		SamplingIterator<RealIndex> iter = grid.iterator();
		RealIndex index = new RealIndex(grid.numDimensions());
		while (iter.hasNext()) {
			iter.next(index);
			// do something with the grid index like lookup values in a data source
		}
	}
	
	// Walk all the points in a 3-d spherical grid
	
	void example5() {
		
		SamplingSphericalRealGrid grid =
				new SamplingSphericalRealGrid(0.25, 50, Math.PI/45, 22, Math.PI/100, 100);
		SamplingIterator<RealIndex> iter = grid.iterator();
		RealIndex index = new RealIndex(grid.numDimensions());
		while (iter.hasNext()) {
			iter.next(index);
			// do something with the grid index like lookup values in a data source
		}
	}
	
	// Walk all the points in an arbitrary set of n-d points
	
	void example6() {
		
		SamplingGeneral<IntegerIndex> samples = new SamplingGeneral<>(2);

		IntegerIndex point = new IntegerIndex(samples.numDimensions());
		
		point.set(0, 4);
		point.set(1, 2);
		samples.add(point.duplicate());
		
		point.set(0, -2);
		point.set(1, 1);
		samples.add(point.duplicate());
		
		SamplingIterator<IntegerIndex> iter = samples.iterator();
		
		while (iter.hasNext()) {
			iter.next(point);
			// do something with the point
		}
	}

	// Walk all the points in an union (a or b) between two other n-d grids
	
	void example7() {
		
		SamplingCartesianIntegerGrid grid1 = new SamplingCartesianIntegerGrid(new long[] {0,0,0}, new long[] {20,20,20});
		SamplingCartesianIntegerGrid grid2 = new SamplingCartesianIntegerGrid(new long[] {5,5,5}, new long[] {25,25,25});
		SamplingUnion<IntegerIndex> union =
				new SamplingUnion<IntegerIndex>(grid1, grid2, new IntegerIndex(grid1.numDimensions()));
		IntegerIndex index = new IntegerIndex(grid1.numDimensions());
		SamplingIterator<IntegerIndex> iter = union.iterator();
		while (iter.hasNext()) {
			iter.next(index);
			// this code iterates from {0,0,0} to {25,25,25}
		}
	}
	
	// Walk all the points in an intersection (a and b) between two other n-d grids
	
	void example8() {
		
		SamplingCartesianIntegerGrid grid1 = new SamplingCartesianIntegerGrid(new long[] {0,0,0}, new long[] {20,20,20});
		SamplingCartesianIntegerGrid grid2 = new SamplingCartesianIntegerGrid(new long[] {5,5,5}, new long[] {25,25,25});
		SamplingIntersection<IntegerIndex> intersection =
				new SamplingIntersection<IntegerIndex>(grid1, grid2, new IntegerIndex(grid1.numDimensions()));
		IntegerIndex index = new IntegerIndex(grid1.numDimensions());
		SamplingIterator<IntegerIndex> iter = intersection.iterator();
		while (iter.hasNext()) {
			iter.next(index);
			// this code iterates from {5,5,5} to {20,20,20}
		}
	}
	
	// Walk all the points in a difference between two other n-d grids
	
	void example9() {
		
		SamplingCartesianIntegerGrid grid1 = new SamplingCartesianIntegerGrid(new long[] {0,0,0}, new long[] {20,20,20});
		SamplingCartesianIntegerGrid grid2 = new SamplingCartesianIntegerGrid(new long[] {5,5,5}, new long[] {25,25,25});
		SamplingDifference<IntegerIndex> difference =
				new SamplingDifference<IntegerIndex>(grid1, grid2, new IntegerIndex(grid1.numDimensions()));
		IntegerIndex index = new IntegerIndex(grid1.numDimensions());
		SamplingIterator<IntegerIndex> iter = difference.iterator();
		while (iter.hasNext()) {
			iter.next(index);
			// this code iterates from {0,0,0} to {5,5,5}
		}
	}
	
	// Walk all the points in a symmetric difference (xor) between two other n-d grids
	
	void example10() {
		
		SamplingCartesianIntegerGrid grid1 = new SamplingCartesianIntegerGrid(new long[] {0,0,0}, new long[] {20,20,20});
		SamplingCartesianIntegerGrid grid2 = new SamplingCartesianIntegerGrid(new long[] {5,5,5}, new long[] {25,25,25});
		SamplingSymmetricDifference<IntegerIndex> symdifference =
				new SamplingSymmetricDifference<IntegerIndex>(grid1, grid2, new IntegerIndex(grid1.numDimensions()));
		IntegerIndex index = new IntegerIndex(grid1.numDimensions());
		SamplingIterator<IntegerIndex> iter = symdifference.iterator();
		while (iter.hasNext()) {
			iter.next(index);
			// this code iterates from {0,0,0} to {5,5,5} and then {20,20,20} to {25,25,25}
		}
	}

	// Walk all the points in a complement of another n-d grid
	
	void example11() {
		
		SamplingCartesianIntegerGrid grid1 = new SamplingCartesianIntegerGrid(new long[] {0,0,0}, new long[] {20,20,20});
		SamplingCartesianIntegerGrid grid2 = new SamplingCartesianIntegerGrid(new long[] {5,5,5}, new long[] {25,25,25});
		SamplingDifference<IntegerIndex> symdifference =
				new SamplingDifference<IntegerIndex>(grid1, grid2, new IntegerIndex(grid1.numDimensions()));
		SamplingComplement complement = new SamplingComplement(symdifference);
		IntegerIndex index = new IntegerIndex(grid1.numDimensions());
		SamplingIterator<IntegerIndex> iter = complement.iterator();
		while (iter.hasNext()) {
			iter.next(index);
			// this code iterates from {5,5,5} to {20,20,20}
		}
	}

	// Walk all the points in a n-d grids that matches a condition
	
	void example12() {

		SamplingCartesianIntegerGrid grid = new SamplingCartesianIntegerGrid(new long[] {0,0,0}, new long[] {20,20,20});

		Function1<Boolean,IntegerIndex> xIsEven =
				new Function1<Boolean,IntegerIndex>()
		{
			@Override
			public Boolean call(IntegerIndex value) {
				return (value.get(0) & 1) == 0;
			}
		};
		
		SamplingConditional<IntegerIndex> sampCond =
				new SamplingConditional<IntegerIndex>(xIsEven, grid, new IntegerIndex(grid.numDimensions()));
		
		IntegerIndex index = new IntegerIndex(grid.numDimensions());
		
		SamplingIterator<IntegerIndex> iter = sampCond.iterator();
		
		while (iter.hasNext()) {
			iter.next(index);
			// do something with the points
		}
	}
}
