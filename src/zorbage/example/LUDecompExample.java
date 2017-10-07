/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.example;

import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;
import zorbage.type.data.float64.real.Float64Group;
import zorbage.type.data.float64.real.Float64Matrix;
import zorbage.type.data.float64.real.Float64MatrixMember;
import zorbage.type.data.float64.real.Float64Member;
import zorbage.type.data.float64.real.Float64Vector;
import zorbage.type.data.float64.real.Float64VectorMember;
import zorbage.type.data.int64.SignedInt64Member;
import zorbage.type.storage.linear.LinearStorage;
import zorbage.type.storage.linear.array.ArrayStorageFloat64;
import zorbage.type.storage.linear.array.ArrayStorageSignedInt64;

// TODO: test. Not sure I got this right.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class LUDecompExample {

	Float64Matrix dblMat = new Float64Matrix();
	Float64Vector dblVec = new Float64Vector();
	Float64Group dbl = new Float64Group();

	public void run() {
		
		Float64Member val = dbl.construct();

		Float64MatrixMember a = dblMat.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, 3, 3);
		dbl.unity(val);
		a.setV(0, 0, val);
		a.setV(1, 1, val);
		a.setV(1, 2, val);
		a.setV(2, 0, val);
		a.setV(2, 2, val);
		
		Float64VectorMember b = dblVec.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, 3);
		val.setV(4);
		b.setV(0, val);
		val.setV(10);
		b.setV(1, val);
		val.setV(20);
		b.setV(2, val);
		
		Float64VectorMember x = dblVec.construct(MemoryConstruction.DENSE, StorageConstruction.ARRAY, 3);

		if (run(a,b,0.000001,x) != -1)
			System.out.println("Success");
		else
			System.out.println("Error");
		
		System.out.println(x.toString());
	}
	
	public int run(Float64MatrixMember a, Float64VectorMember b, double tol, Float64VectorMember x) {
		
		final long n = x.length();
		
		LinearStorage<?,SignedInt64Member> o =
				new ArrayStorageSignedInt64<SignedInt64Member>(n, new SignedInt64Member());

		LinearStorage<?,Float64Member> s =
				new ArrayStorageFloat64<Float64Member>(n, new Float64Member());

		if (decompose(a,tol,o,s) != -1) {
			substitute(a,o,b,x);
			return 0;
		} else {
			return -1;
		}
	}

	// TODO: this code changes matrix a. Not ideal.
	
	private int decompose(Float64MatrixMember a, double tol, LinearStorage<?,SignedInt64Member> o, LinearStorage<?,Float64Member> s) {
		SignedInt64Member v = new SignedInt64Member();
		SignedInt64Member iIndex = new SignedInt64Member();
		SignedInt64Member kIndex = new SignedInt64Member();
		Float64Member tmp1 = dbl.construct();
		Float64Member tmp2 = dbl.construct();
		Float64Member factor = dbl.construct();
		Float64Member entry = dbl.construct();
		Float64Member aval = dbl.construct();
		Float64Member sval = dbl.construct();

		for (long i = 0; i < o.size(); i++) {
			v.setV(i);
			o.set(i,v);
			a.v(i, 0, tmp1);
			dbl.abs(tmp1, tmp1);
			s.set(i, tmp1);
			for (long j = 1; j < o.size(); j++) {
				a.v(i, j, entry);
				dbl.abs(entry,  entry);
				s.get(i, tmp1);
				if (dbl.isGreater(entry, tmp1)) {
					s.set(i, entry);
				}
			}
		}
		for (long k = 0; k < o.size()-1; k++) {
			pivot(a, o, s, k);
			o.get(k, kIndex);
			a.v(kIndex.v(), k, aval);
			s.get(kIndex.v(), sval);
			dbl.divide(aval, sval, tmp1);
			if (Math.abs(tmp1.v()) < tol) {
				System.out.println(tmp1.v());
				return -1;
			}
			for (long i = k+1; i < o.size(); i++) {
				o.get(i, iIndex);
				a.v(iIndex.v(), k, tmp1);
				a.v(kIndex.v(), k, tmp2);
				dbl.divide(tmp1, tmp2, factor);
				a.setV(iIndex.v(), k, factor);
				for (long j = k+1; j < o.size(); j++) {
					a.v(kIndex.v(), j, tmp1);
					dbl.multiply(factor, tmp1, tmp1);
					a.v(iIndex.v(), j, tmp2);
					dbl.subtract(tmp2, tmp1, tmp2);
					a.setV(iIndex.v(), j, tmp2);
				}
			}
			a.v(kIndex.v(), k, tmp1);
			s.get(kIndex.v(), tmp2);
			dbl.divide(tmp1, tmp2, tmp1);
			dbl.abs(tmp1, tmp1);
			tmp2.setV(tol);
			if (dbl.isLess(tmp1, tmp2)) {
				System.out.println(tmp1);
				return -1;
			}
		}

		return 0;
	}
	
	/*
		for (int i = 0; i < n; i++) {
			o[i] = i;
			s[i] = Math.abs(a[i][0]);
			for (j = 1; j < n; j++) {
				double entry = Math.abs(a[i][j]);
				if (entry > s[i])
					s[i] = entry;
			}
		}
		for (int k = 0; k < n-1; k++) {
			pivot(a, o, s, n, k);
			if (Math.abs(a[o[k]][k] / s[o[k]]) < tol) {
				System.out.println(a[o[k]][k] / s[o[k]]);
				return -1;
			}
			for (int i = k+1; i < n; i++) {
				double factor = a[o[i]][k] / a[o[k]][k];
				a[o[i]][k] = factor;
				for (int j = k+1; j < n; j++) {
					a[o[i]][j] -= factor * a[o[k]][j];
				}
			}
		}
		
		if (Math.abs(a[o[k]][k]/s[o[k]]) < tol) {
			System.out.println(a[o[k]][k] / s[o[k]]);
			return -1;
		}
		return 0;

	 */
	
	private void pivot(Float64MatrixMember a, LinearStorage<?,SignedInt64Member> o, LinearStorage<?,Float64Member> s, long k) {
		SignedInt64Member kIndex = new SignedInt64Member();
		SignedInt64Member qIndex = new SignedInt64Member();
		Float64Member aval = new Float64Member();
		Float64Member sval = new Float64Member();
		Float64Member tmp1 = new Float64Member();
		Float64Member max = new Float64Member();
		Float64Member entry = new Float64Member();

		long p = k;
		o.get(k, kIndex);
		a.v(kIndex.v(), k, aval);
		s.get(kIndex.v(), sval);
		dbl.divide(aval, sval, tmp1);
		dbl.abs(tmp1, max);
		for (long q=k+1; q < o.size(); q++) {
			o.get(q, qIndex);
			a.v(qIndex.v(), k, aval);
			s.get(qIndex.v(), sval);
			dbl.divide(aval, sval, tmp1);
			dbl.abs(tmp1, entry);
			if (dbl.isGreater(entry, max)) {
				max.set(entry);
				p = q;
			}
		}
		if (p != k) {
			o.get(p, qIndex);
			o.get(k, kIndex);
			o.set(p, kIndex);
			o.set(k, qIndex);
		}
	}	
	
	/*
	
	private void pivot(double[][] a, int[] o, double[] s, int n, int k) {
		int p = k;
		double max = Math.abs(a[o[k]][k]/s[o[k]]);
		for (int q = k+1; q < n; q++) {
			double entry = Math.abs(a[o[q]][k]/s[o[q]]);
			if (entry > max) {
				max = entry;
				p = q;
			}
		}
		int tmp = o[p];
		o[p] = o[k];
		o[k] = tmp;
	}

	*/
	
	private void substitute(Float64MatrixMember a, LinearStorage<?,SignedInt64Member> o, Float64VectorMember b, Float64VectorMember x) {
		Float64Member sum = new Float64Member();
		Float64Member term = new Float64Member();
		Float64Member aTerm = new Float64Member();
		Float64Member bTerm = new Float64Member();
		SignedInt64Member iIndex = new SignedInt64Member();
		SignedInt64Member jIndex = new SignedInt64Member();
		SignedInt64Member n1Index = new SignedInt64Member();
		for (long i = 1; i < o.size(); i++) {
			o.get(i, iIndex);
			b.v(iIndex.v(), sum);
			for (long j = 0; j < i-1; j++) {
				a.v(iIndex.v(), j, aTerm);
				o.get(j, jIndex);
				b.v(jIndex.v(), bTerm);
				dbl.multiply(aTerm, bTerm, term);
				dbl.subtract(sum, term, sum);
			}
			b.setV(iIndex.v(), sum);
		}
		o.get(o.size()-1, n1Index);
		b.v(n1Index.v(), bTerm);
		a.v(n1Index.v(), o.size()-1, aTerm);
		dbl.divide(bTerm, aTerm, term);
		x.setV(o.size()-1, term);
		for (long i = o.size()-2; i >= 0; i--) {
			o.get(i, iIndex);
			dbl.zero(sum);
			for (long j = i+1; j < o.size(); j++) {
				a.v(iIndex.v(), j, aTerm);
				x.v(j, bTerm);
				dbl.multiply(aTerm, bTerm, term);
				dbl.add(sum,  term, sum);
			}
			a.v(iIndex.v(), i, aTerm);
			b.v(iIndex.v(), bTerm);
			dbl.subtract(bTerm, sum, bTerm);
			dbl.divide(bTerm, aTerm, term);
			x.setV(i, term);
		}
	}

	/*
	private void substitute(double[][] a, int[] o, int n, double[] b, double[] x) {
		for (int i = 1; i < n; i++) {
			double sum = b[o[i]];
			for (int j = 0; j < i-1; j++) {
				sum -= a[o[i]][j] * b[o[j]];
			}
			b[o[i]] = sum;
		}
		x[n-1] = b[o[n-1]]/a[o[n-1]][n-1];
		for (int i = n-2; i >= 0; i--) {
			double sum = 0;
			for (int j = i+1; j < n; j++) {
				sum += a[o[i]][j] * x[j];
			}
			x[i] = (b[o[i]] - sum) / a[o[i]][i];
		}
	}
	*/
}
