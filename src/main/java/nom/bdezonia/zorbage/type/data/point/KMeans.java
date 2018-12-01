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
package nom.bdezonia.zorbage.type.data.point;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.coder.ByteCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class KMeans {

	private class Point implements ByteCoder{
		private int clusterNum;
		private double[] vector;
		
		public Point() {
			this(0);
		}
		
		public Point(Point other) {
			clusterNum = other.clusterNum;
			vector = new double[other.dimension()];
			for (int i = 0; i < vector.length; i++) {
				vector[i] = other.vector[i];
			}
		}

		public Point(int dimension) {
			clusterNum = -1;
			vector = new double[dimension];
		}
		
		public int dimension() {
			return vector.length;
		}
		
		public int clusterNumber() {
			return clusterNum;
		}
		
		public void setClusterNumber(int num) {
			clusterNum = num;
		}

		@Override
		public int byteCount() {
			return 4 + dimension() * 8;
		}

		@Override
		public void fromByteArray(byte[] arr, int index) {
			ByteBuffer buff = ByteBuffer.allocate(4);
			buff.put(0, arr[index+0]);
			buff.put(1, arr[index+1]);
			buff.put(2, arr[index+2]);
			buff.put(3, arr[index+3]);
			int n = buff.getInt();
			if (this.dimension() != n) {
				this.vector = new double[n];
			}
			buff.put(0, arr[index+4+0]);
			buff.put(1, arr[index+4+1]);
			buff.put(2, arr[index+4+2]);
			buff.put(3, arr[index+4+3]);
			this.clusterNum = buff.getInt();
			buff = ByteBuffer.allocate(8);
			for (int k = 0; k < n; k++) {
				for (int i = 0; i < 8; i++) {
					buff.put(i, arr[index+8+i]); 
				}
				this.vector[k] = buff.getDouble();
			}
		}

		@Override
		public void toByteArray(byte[] arr, int index) {
			ByteBuffer buff = ByteBuffer.allocate(4);
			byte[] bytes = buff.putInt(this.vector.length).array();
			for (int i = 0; i < 4; i++) {
				arr[index+i] = bytes[i];
			}
			bytes = buff.putInt(this.clusterNum).array();
			for (int i = 0; i < 4; i++) {
				arr[index+4+i] = bytes[i];
			}
			buff = ByteBuffer.allocate(8);
			for (int k = 0; k < vector.length; k++) {
				bytes = buff.putDouble(this.vector[k]).array();
				for (int i = 0; i < 8; i++) {
					arr[index+8+i] = bytes[i];
				}
			}
		}

		@Override
		public void fromByteFile(RandomAccessFile raf) throws IOException {
			ByteBuffer buff = ByteBuffer.allocate(4);
			buff.put(0, raf.readByte());
			buff.put(1, raf.readByte());
			buff.put(2, raf.readByte());
			buff.put(3, raf.readByte());
			int n = buff.getInt();
			if (this.dimension() != n) {
				this.vector = new double[n];
			}
			buff.put(0, raf.readByte());
			buff.put(1, raf.readByte());
			buff.put(2, raf.readByte());
			buff.put(3, raf.readByte());
			this.clusterNum = buff.getInt();
			buff = ByteBuffer.allocate(8);
			for (int k = 0; k < n; k++) {
				for (int i = 0; i < 8; i++) {
					buff.put(i, raf.readByte()); 
				}
				this.vector[k] = buff.getDouble();
			}
		}

		@Override
		public void toByteFile(RandomAccessFile raf) throws IOException {
			ByteBuffer buff = ByteBuffer.allocate(4);
			byte[] bytes = buff.putInt(this.vector.length).array();
			for (int i = 0; i < 4; i++) {
				raf.writeByte(bytes[i]);
			}
			bytes = buff.putInt(this.clusterNum).array();
			for (int i = 0; i < 4; i++) {
				raf.writeByte(bytes[i]);
			}
			buff = ByteBuffer.allocate(8);
			for (int k = 0; k < vector.length; k++) {
				bytes = buff.putDouble(this.vector[k]).array();
				for (int i = 0; i < 8; i++) {
					raf.writeByte(bytes[i]);
				}
			}
		}
	}
	
	private class PointGroup implements Group<PointGroup,Point> {

		@Override
		public Point construct() {
			return new Point();
		}

		@Override
		public Point construct(Point other) {
			return new Point(other);
		}

		@Override
		public Point construct(String str) {
			throw new IllegalArgumentException("not yet implemented");
		}

		private final Function2<Boolean, Point, Point> EQ =
				new Function2<Boolean, KMeans.Point, KMeans.Point>()
		{
			@Override
			public Boolean call(Point a, Point b) {
				if (a == b) return true;
				if (a.dimension() != b.dimension()) return false;
				for (int i = 0; i < a.dimension(); i++) {
					if (a.vector[i] != b.vector[i]) return false;
				}
				return true;
			}
		};

		@Override
		public Function2<Boolean, Point, Point> isEqual() {
			return EQ;
		}

		private final Function2<Boolean, Point, Point> NEQ =
				new Function2<Boolean, Point, Point>()
		{
			@Override
			public Boolean call(Point a, Point b) {
				return !isEqual().call(a, b);
			}
		};
		
		@Override
		public Function2<Boolean, Point, Point> isNotEqual() {
			return NEQ;
		}

		private final Procedure2<Point, Point> ASSIGN =
				new Procedure2<KMeans.Point, KMeans.Point>()
		{
			@Override
			public void call(Point a, Point b) {
				if (a == b) return;
				if (a.dimension() != b.dimension()) {
					a.vector = new double[b.dimension()];
					for (int i = 0; i < b.dimension(); i++) {
						a.vector[i] = b.vector[i];
					}
				}
			}
		};

		@Override
		public Procedure2<Point, Point> assign() {
			return ASSIGN;
		}

		private final Function1<Boolean, Point> ZER =
				new Function1<Boolean, KMeans.Point>()
		{
			@Override
			public Boolean call(Point a) {
				for (int i = 0; i < a.dimension(); i++) {
					if (a.vector[i] != 0)
						return false;
				}
				return true;
			}
		};

		@Override
		public Function1<Boolean, Point> isZero() {
			return ZER;
		}
		
	}
	
	public static double dist(Point a, Point b) {
		if (a.dimension() != b.dimension())
			throw new IllegalArgumentException("all points must share the same dimensionality");
		// TODO: two pass hypot approach to avoid overflow
		double sum = 0;
		for (int i = 0; i < a.dimension(); i++) {
			sum += (a.vector[i]-b.vector[i]) * (a.vector[i]-b.vector[i]); 
		}
		return Math.sqrt(sum);
	}
	
	public static
		void compute(PointGroup group, int numClusters, IndexedDataSource<?,Point> input)
	{
		if (numClusters < 1)
			throw new IllegalArgumentException("kmeans: illegal number of clusters. must be >= 1.");

		KMeans kmn = new KMeans();
		
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
				centers.add(kmn.new Point(point.dimension()));
				counts.add(new Long(0));
			}
			for (long i = 0; i < input.size(); i++) {
				input.get(i, point);
				Point ctrSum = centers.get(point.clusterNum);
				for (int j = 0; j < ctrSum.dimension(); j++) {
					ctrSum.vector[j] += point.vector[j];
				}
				counts.set(point.clusterNum, (counts.get(point.clusterNum))+1);
			}
			for (int i = 0; i < numClusters; i++) {
				Point ctrSum = centers.get(i);
				long count = counts.get(i);
				for (int j = 0; j < ctrSum.dimension(); j++) {
					ctrSum.vector[j] /= count;
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
