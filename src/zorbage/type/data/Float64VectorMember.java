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

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float64VectorMember {

	private double[] v;
	
	public Float64VectorMember() {
		v = new double[0];
	}
	
	public Float64VectorMember(double[] vals) {
		v = new double[vals.length];
		for (int i = 0; i < vals.length; i++)
			v[i] = vals[i];
	}
	
	public Float64VectorMember(Float64VectorMember value) {
		v = new double[value.v.length];
		for (int i = 0; i < value.v.length; i++)
			v[i] = value.v[i];
	}
	
	public Float64VectorMember(String value) {
		throw new IllegalArgumentException("Not yet implemented");
	}

	public double v(int i) { if (i < v.length) return v[i]; else return 0; }

	public void setV(int i, double val) {
		if (i >= v.length) {
			if (val == 0) return;
			double[] tmp = v;
			v = new double[i+1];
			for (int j = 0; j < tmp.length; j++) {
				v[j] = tmp[j];
			}
		}
		v[i] = val;
	}
	
	
	public void set(Float64VectorMember other) {
		if (this == other) return;
		throw new IllegalArgumentException("TODO");
	}
	
	public void get(Float64VectorMember other) {
		if (this == other) return;
		throw new IllegalArgumentException("TODO");
	}

	public int length() { return v.length; }
	
	@Override
	public String toString() { throw new IllegalArgumentException("Not yet implemented"); }

}
