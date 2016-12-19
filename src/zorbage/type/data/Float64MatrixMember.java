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
public final class Float64MatrixMember {

	private double[] data;
	private int rows;
	private int cols;
	
	public Float64MatrixMember() {
		rows = -1;
		cols = -1;
		init(0,0);
	}
	
	public Float64MatrixMember(Float64MatrixMember other) {
		init(other.rows,other.cols);
		for (int i = 0; i < data.length; i++) {
			data[i] = other.data[i];
		}
	}
	
	public Float64MatrixMember(String s) {
		throw new IllegalArgumentException("to be implemented");
	}
	
	public int rows() {
		return rows;
	}

	public int cols() {
		return cols;
	}
	
	public void v(int r, int c, Float64Member value) {
		value.setV(data[rows*r + c]);
	}

	public void setV(int r, int c, Float64Member value) {
		data[rows*r + c] = value.v();
	}
	
	public void init(int rows, int cols) {
		if (this.rows != rows || this.cols != cols) {
			this.rows = rows;
			this.cols = cols;
		}
		if (this.data.length != rows*cols) {
			this.data = new double[rows*cols];
		}
		else {
			for (int i = 0; i < data.length; i++) this.data[i] = 0;
		}
	}
}
