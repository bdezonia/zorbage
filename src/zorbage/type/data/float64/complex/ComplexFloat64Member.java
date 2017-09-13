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
package zorbage.type.data.float64.complex;

import java.io.IOException;
import java.io.RandomAccessFile;

import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;
import zorbage.type.storage.coder.DoubleCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class ComplexFloat64Member
	implements DoubleCoder<ComplexFloat64Member>
{
	private double r, i;
	
	public ComplexFloat64Member() {
		r = 0;
		i = 0;
	}
	
	public ComplexFloat64Member(double rvalue, double ivalue) {
		r = rvalue;
		i = ivalue;
	}
	
	public ComplexFloat64Member(ComplexFloat64Member value) {
		r = value.r;
		i = value.i;
	}

	public ComplexFloat64Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		r = val.r().doubleValue();
		i = val.i().doubleValue();
	}

	public double r() { return r; }
	
	public double i() { return i; }
	
    public void setR(double val) { r = val; }
    
    public void setI(double val) { i = val; }	

    public void get(ComplexFloat64Member other) {
    	other.r = r;
    	other.i = i;
    }

    public void set(ComplexFloat64Member other) {
    	r = other.r;
    	i = other.i;
    }
    
    @Override
	public String toString() {
    	StringBuilder builder = new StringBuilder();
    	builder.append('(');
    	builder.append(r);
    	builder.append(',');
    	builder.append(i);
    	builder.append(')');
    	return builder.toString();
    }

	@Override
	public int doubleCount() {
		return 2;
	}

	@Override
	public void arrayToValue(double[] arr, int index, ComplexFloat64Member value) {
		value.r = arr[index];
		value.i = arr[index+1];
	}

	@Override
	public void valueToArray(double[] arr, int index, ComplexFloat64Member value) {
		arr[index] = value.r;
		arr[index+1] = value.i;
	}

	@Override
	public void fileToValue(RandomAccessFile raf, ComplexFloat64Member value) throws IOException {
		value.r = raf.readDouble();
		value.i = raf.readDouble();
	}

	@Override
	public void valueToFile(RandomAccessFile raf, ComplexFloat64Member value) throws IOException {
		raf.writeDouble(value.r);
		raf.writeDouble(value.i);
	}

}
