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

import zorbage.type.parse.ComplexNumberRepresentation;
import zorbage.type.parse.NumberRepresentation;
import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.QuaternionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class SignedInt32Member {

	private int v;
	
	public SignedInt32Member() {
		v = 0;
	}
	
	public SignedInt32Member(int value) {
		v = value;
	}
	
	public SignedInt32Member(SignedInt32Member value) {
		v = value.v;
	}
	
	public SignedInt32Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		Object val = rep.firstValue();
		if (val instanceof NumberRepresentation) {
			NumberRepresentation v = (NumberRepresentation) val;
			this.v = v.v().intValue();
		}
		else if (val instanceof ComplexNumberRepresentation) {
			ComplexNumberRepresentation v = (ComplexNumberRepresentation) val;
			this.v = v.r().intValue();
		}
		else if (val instanceof QuaternionRepresentation) {
			QuaternionRepresentation v = (QuaternionRepresentation) val;
			this.v = v.r().intValue();
		}
		else if (val instanceof OctonionRepresentation) {
			OctonionRepresentation v = (OctonionRepresentation) val;
			this.v = v.r().intValue();
		}
		else
			throw new IllegalArgumentException("unknown numeric type in signed int 32 parse");
	}

	public int v() { return v; }
	public void setV(int val) { v = val; }
	
	
	public void set(SignedInt32Member other) {
		v = other.v;
	}
	
	public void get(SignedInt32Member other) {
		other.v = v;
	}

	@Override
	public String toString() { return "" + v; }
}
