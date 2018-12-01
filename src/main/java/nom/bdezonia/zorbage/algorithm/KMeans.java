/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.type.data.point.Point;
import nom.bdezonia.zorbage.type.data.point.PointGroup;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class KMeans {

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double dist(Point a, Point b) {
		if (a.dimension() != b.dimension())
			throw new IllegalArgumentException("all points must share the same dimensionality");
		// TODO: two pass hypot approach to avoid overflow
		double sum = 0;
		for (int i = 0; i < a.dimension(); i++) {
			sum += (a.component(i)-b.component(i)) * (a.component(i)-b.component(i)); 
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * KMeans.compute()
	 * Usage: Pass in a list of points (all of the same dimension > 0) that span any region.
	 *        Pass in the number of clusters you want to divide the point set into.
	 *        Run the algorithm. The points are assigned cluster numbers in place. You can
	 *        then iterate the list of points and pull them into separate lists or sort
	 *        the list on clusterNumber.
	 * @param group The PointGroup to be used to manipulate Points.
	 * @param numClusters The number of clusters to fivide the list of Points into.
	 * @param input The list of Points to analyze.
	 */
	public static
		void compute(PointGroup group, int numClusters, IndexedDataSource<?,Point> input)
	{
		if (numClusters < 1)
			throw new IllegalArgumentException("kmeans: illegal number of clusters. must be >= 1.");

		int MAX_ITERS = 1000;
		
		Point point = group.construct();
		
		// assign initial clusters randomly
		ThreadLocalRandom rng = ThreadLocalRandom.current();
		for (long i = 0; i < input.size(); i++) {
			int k = rng.nextInt(numClusters);
			input.get(i, point);
			point.setClusterNumber(k);
			input.set(i, point);
		}
		
		for (int k = 0; k < MAX_ITERS; k++) {
			
			boolean converged = true;
			
			// calc centroids of clusters
			List<Point> centers = new ArrayList<Point>();
			List<Long> counts = new ArrayList<Long>();
			for (int i = 0; i < numClusters; i++) {
				centers.add(new Point(point.dimension()));
				counts.add(new Long(0));
			}
			for (long i = 0; i < input.size(); i++) {
				input.get(i, point);
				Point ctrSum = centers.get(point.clusterNumber());
				for (int j = 0; j < ctrSum.dimension(); j++) {
					ctrSum.setComponents(j, ctrSum.component(j) + point.component(j));
				}
				counts.set(point.clusterNumber(), (counts.get(point.clusterNumber()))+1);
			}
			for (int i = 0; i < numClusters; i++) {
				Point ctrSum = centers.get(i);
				long count = counts.get(i);
				for (int j = 0; j < ctrSum.dimension(); j++) {
					ctrSum.setComponents(j, ctrSum.component(j) / count);
				}
			}
			
			// for each point
			for (long i = 0; i < input.size(); i++) {
				input.get(i,  point);
				Point clusterCtr = centers.get(0);
				double minDist = dist(point, clusterCtr);
				int minIndex = 0;
				//   find closest cluster
				for (int j = 1; j < numClusters; j++) {
					Point pt = centers.get(j);
					double dist = dist(point, pt);
					if (dist < minDist) {
						minDist = dist;
						minIndex = j;
					}
				}
				if (minIndex != point.clusterNumber()) {
					converged = false;
					point.setClusterNumber(minIndex);
					input.set(i, point);
				}
			}
			
			if (converged) return;
		}
		
		System.out.println("Did not converge after "+MAX_ITERS+" iterations. Best approximation returned.");
	}
}
