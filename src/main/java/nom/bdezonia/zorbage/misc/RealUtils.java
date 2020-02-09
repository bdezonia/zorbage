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
package nom.bdezonia.zorbage.misc;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RealUtils {

	private RealUtils() { }
	
	public static boolean near(float f1, float f2, float tol) {
		if (tol < 0) throw new IllegalArgumentException("negative tolerance given");
		return Math.abs(f1-f2) <= tol;
	}

	public static boolean near(double f1, double f2, double tol) {
		if (tol < 0) throw new IllegalArgumentException("negative tolerance given");
		return Math.abs(f1-f2) <= tol;
	}
	
	public static double distance1d(double x1, double x2) {
		return Math.abs(x2-x1);
	}

	// TODO: protect from underflow
	
	public static double distance2d(double x1, double y1, double x2, double y2) {
		double dx = Math.abs(x2 - x1);
		double dy = Math.abs(y2 - y1);
		double max = Math.max(dx, dy);
		if (max == 0)
			return 0;
		dx /= max;
		dy /= max;
		return max * Math.sqrt(dx*dx + dy*dy);
	}

	// TODO: protect from underflow
	
	public static double distance3d(double x1, double y1, double z1, double x2, double y2, double z2) {
		double dx = Math.abs(x2 - x1);
		double dy = Math.abs(y2 - y1);
		double dz = Math.abs(z2 - z1);
		double max = Math.max(dx, dy);
		max = Math.max(max, dz);
		if (max == 0)
			return 0;
		dx /= max;
		dy /= max;
		dz /= max;
		return max * Math.sqrt(dx*dx + dy*dy + dz*dz);
	}

	// TODO: protect from underflow
	
	public static double distanceNd(double[] p1, double[] p2, double[] scratchSpace) {
		if (p1.length != p2.length || p1.length != scratchSpace.length)
			throw new IllegalArgumentException("mismatched dimenions in distanceNd()");
		if (p1 == scratchSpace || p2 == scratchSpace)
			throw new IllegalArgumentException("scratch space must be dofferent from inputs");
		if (p1.length == 0) return 0;
		for (int i = 0; i < p1.length; i++) {
			scratchSpace[i] = Math.abs(p2[i] - p1[i]);
		}
		double max = scratchSpace[0];
		for (int i = 1; i < p1.length; i++) {
			max = Math.max(max, scratchSpace[i]);
		}
		if (max == 0)
			return 0;
		for (int i = 0; i < p1.length; i++) {
			scratchSpace[i] /= max;
		}
		double sumSq = 0;
		for (int i = 0; i < p1.length; i++) {
			sumSq += scratchSpace[i] * scratchSpace[i];
		}
		return max * Math.sqrt(sumSq);
	}
}
