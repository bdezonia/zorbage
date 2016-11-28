/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.type.data;

// TODO - do we nest Float64Members inside Quat<Float64Member>? Is this even possible?

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class QuaternionFloat64Member {
	private double r, i, j, k;
	
	public QuaternionFloat64Member() {
		r = i = j = k = 0;
	}
	
	// TODO do I want more ctors? 1 double = real? 2 doubles = complex?
	
	public QuaternionFloat64Member(double r, double i, double j, double k) {
		this.r = r;
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	public QuaternionFloat64Member(QuaternionFloat64Member value) {
		r = value.r;
		i = value.i;
		j = value.j;
		k = value.k;
	}

	public QuaternionFloat64Member(String value) {
		String[] strs = value.trim().split("\\s+");

		for (int x = 0; x < 4 && x < strs.length; x++) {
		  double d = Double.parseDouble(strs[x]);
		  if (x == 0) r = d;
		  else if (x == 1) i = d;
		  else if (x == 2) j = d;
		  else if (x == 3) k = d;
		}
	}

	public double r() { return r; }
	public double i() { return i; }
	public double j() { return j; }
	public double k() { return k; }
	public void setR(double val) { r = val; }
	public void setI(double val) { i = val; }
	public void setJ(double val) { j = val; }
	public void setK(double val) { k = val; }
	
	@Override
	public String toString() { return "" + r + " " + i + " " + j + " " + k; }

}
