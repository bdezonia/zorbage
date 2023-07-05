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

import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.geom.point.Point;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.function.Function2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class KMeans {

	// do not instantiate
	
	private KMeans() { }

	/**
	 * KMeans.compute()
	 * Usage: Pass in a list of points (all of the same dimension greater than 0) that span any region.
	 *        Pass in a list of ints that will track which points go with which clusters.
	 *        The two lists must be of the same length.
	 *        Pass in the number of clusters you want to divide the point set into.
	 *        Run the algorithm. The points are assigned cluster numbers in the clusterNumbers
	 *        list.
	 * @param numClusters The number of clusters to divide the list of Points into.
	 * @param points The list of Points to analyze.
	 * @param clusterIndices The list tracking which Point is in which cluster.
	 */
	public static
		void compute(int numClusters,
						Function2<Double,Point,Point> distFunc,
						IndexedDataSource<Point> points,
						IndexedDataSource<SignedInt32Member> clusterIndices)
	{
		if (numClusters < 2)
			throw new IllegalArgumentException("kmeans: illegal number of clusters. must be >= 2.");
		
		long pointsSize = points.size();
		long clusterIndicesSize = clusterIndices.size();

		if (pointsSize != clusterIndicesSize)
			throw new IllegalArgumentException("points and clusterIndices length must match");

		if (pointsSize < numClusters)
			throw new IllegalArgumentException("number of points given must be >= to the number of clusters");
		
		int MAX_ITERS = 1000;
		
		Point point = G.POINT.construct();
		SignedInt32Member clusterNum = G.INT32.construct();
		double scale;
		double dist = 0;
		double minDist = 0;
		
		// assign initial clusters arbitrarily but somewhat closely in space
		
		int cNum = 0;
		
		int cSize = 0;

		long cutoff;
		
		if (clusterIndicesSize % numClusters == 0)
			cutoff = clusterIndicesSize / numClusters;
		else
			cutoff = clusterIndicesSize / (numClusters-1);
		
		for (long i = 0; i < clusterIndicesSize; i++) {
			clusterNum.setV(cNum);
			clusterIndices.set(i, clusterNum);
			cSize++;
			if (cSize >= cutoff) {
				cSize = 0;
				cNum++;
			}
		}
		
		// set the dimensionality of the set of points
		points.get(0, point);
		
		List<Point> centers = new ArrayList<Point>();
		List<Long> counts = new ArrayList<Long>();
		for (int i = 0; i < numClusters; i++) {
			centers.add(new Point(point.numDimensions()));
			counts.add(0L);
		}

		for (int k = 0; k < MAX_ITERS; k++) {
			
			// calc centroids of clusters
			for (int i = 0; i < numClusters; i++) {
				G.POINT.zero().call(centers.get(i));
			}
			for (long i = 0; i < pointsSize; i++) {
				points.get(i, point);
				clusterIndices.get(i, clusterNum);
				Point ctrSum = centers.get(clusterNum.v());
				G.POINT.add().call(ctrSum, point, ctrSum);
				counts.set(clusterNum.v(), (counts.get(clusterNum.v()))+1);
			}
			for (int i = 0; i < numClusters; i++) {
				Point ctrSum = centers.get(i);
				long count = counts.get(i);
				scale = 1.0 / count;
				G.POINT.scale().call(scale, ctrSum, ctrSum);
			}
			
			// for each point

			boolean converged = true;
			
			for (long i = 0; i < pointsSize; i++) {
				points.get(i,  point);
				Point clusterCtr = centers.get(0);
				minDist = distFunc.call(point, clusterCtr);
				int minCluster = 0;
				//   find closest cluster
				for (int j = 1; j < numClusters; j++) {
					Point ctr = centers.get(j);
					dist = distFunc.call(point, ctr);
					if (dist < minDist) {
						minDist = dist;
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

	public static
		void compute(int numClusters,
						IndexedDataSource<Point> points,
						IndexedDataSource<SignedInt32Member> clusterIndices)
	{
		Function2<Double,Point,Point> defaultDistFunc =
				
			new Function2<Double,Point,Point>()
		
		{
			private Float64Member dist = G.DBL.construct();
			
			public Double call(Point a, Point b) {
			
				PointDistance.compute(a, b, dist);
				
				return dist.v();
			}
		};
		
		compute(numClusters, defaultDistFunc, points, clusterIndices);
	}
}
