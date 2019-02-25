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

import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.data.point.Point;
import nom.bdezonia.zorbage.type.data.point.PointAlgebra;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class KMeans {

	/**
	 * KMeans.compute()
	 * Usage: Pass in a list of points (all of the same dimension > 0) that span any region.
	 *        Pass in a list of ints that will track which points go with which clusters.
	 *        The two lists must be of the same length.
	 *        Pass in the number of clusters you want to divide the point set into.
	 *        Run the algorithm. The points are assigned cluster numbers in the clusterNumbers
	 *        list.
	 * @param algebra The PointAlgebra to be used to manipulate Points.
	 * @param numClusters The number of clusters to divide the list of Points into.
	 * @param points The list of Points to analyze.
	 * @param clusterIndices The list tracking which Point is in which cluster.
	 */
	public static
		void compute(PointAlgebra Algebra, int numClusters, IndexedDataSource<?,Point> points, IndexedDataSource<?,SignedInt32Member> clusterIndices)
	{
		if (numClusters < 1)
			throw new IllegalArgumentException("kmeans: illegal number of clusters. must be >= 1.");
		
		long pointsSize = points.size();
		long clusterIndicesSize = clusterIndices.size();

		if (pointsSize != clusterIndicesSize)
			throw new IllegalArgumentException("points and clusterIndices length must match");

		if (pointsSize < numClusters)
			throw new IllegalArgumentException("number of points given must be >= to the number of clusters");
		
		int MAX_ITERS = 1000;
		
		Point point = Algebra.construct();
		SignedInt32Member clusterNum = G.INT32.construct();
		Float64Member scale = G.DBL.construct();
		Float64Member dist = G.DBL.construct();
		Float64Member minDist = G.DBL.construct();
		
		// assign initial clusters arbitrarily
		for (long i = 0; i < clusterIndicesSize; i++) {
			clusterNum.setV((int)(i % numClusters));
			clusterIndices.set(i, clusterNum);
		}
		
		// set the dimensionality of the set of points
		points.get(0, point);
		
		List<Point> centers = new ArrayList<Point>();
		List<Long> counts = new ArrayList<Long>();
		for (int i = 0; i < numClusters; i++) {
			centers.add(new Point(point.numDimensions()));
			counts.add(new Long(0));
		}

		for (int k = 0; k < MAX_ITERS; k++) {
			
			// calc centroids of clusters
			for (int i = 0; i < numClusters; i++) {
				Algebra.zero().call(centers.get(i));
			}
			for (long i = 0; i < pointsSize; i++) {
				points.get(i, point);
				clusterIndices.get(i, clusterNum);
				Point ctrSum = centers.get(clusterNum.v());
				Algebra.add().call(ctrSum, point, ctrSum);
				counts.set(clusterNum.v(), (counts.get(clusterNum.v()))+1);
			}
			for (int i = 0; i < numClusters; i++) {
				Point ctrSum = centers.get(i);
				long count = counts.get(i);
				scale.setV(1.0/count);
				Algebra.scale().call(scale, ctrSum, ctrSum);
			}
			
			// for each point

			boolean converged = true;
			
			for (long i = 0; i < pointsSize; i++) {
				points.get(i,  point);
				Point clusterCtr = centers.get(0);
				PointDistance.compute(point, clusterCtr, minDist);
				int minCluster = 0;
				//   find closest cluster
				for (int j = 1; j < numClusters; j++) {
					Point ctr = centers.get(j);
					PointDistance.compute(point, ctr, dist);
					if (G.DBL.isLess().call(dist,minDist)) {
						G.DBL.assign().call(dist, minDist);
						minCluster = j;
					}
				}
				clusterIndices.get(i, clusterNum);
				if (minCluster != clusterNum.v()) {
					converged = false;
					clusterNum.setV(minCluster);
					clusterIndices.set(i, clusterNum);
				}
			}
			
			if (converged)
				return;
		}
		
		System.out.println("Did not converge after "+MAX_ITERS+" iterations. Best approximation returned.");
	}
}
