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
package zorbage.type.data.bool;

import java.math.BigDecimal;

import zorbage.type.parse.OctonionRepresentation;
import zorbage.type.parse.TensorStringRepresentation;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class BooleanMember {
	
	private static final String ZERO = "0";
	private static final String ONE = "1";

	private boolean v;
	
	public BooleanMember() {
		v = false;
	}
	
	public BooleanMember(boolean value) {
		v = value;
	}
	
	public BooleanMember(BooleanMember value) {
		v = value.v;
	}
	
	public BooleanMember(String value) {
		if (value.equalsIgnoreCase("true"))
			v = true;
		else if (value.equalsIgnoreCase("false"))
			v = false;
		else {
			TensorStringRepresentation rep = new TensorStringRepresentation(value);
			OctonionRepresentation val = rep.firstValue();
			v = !val.r().equals(BigDecimal.ZERO);
		}
	}

	public boolean v() { return v; }
	public void setV(boolean val) { v = val; }
	
	public void set(BooleanMember other) {
		v = other.v;
	}
	
	public void get(BooleanMember other) {
		other.v = v;
	}

	@Override
	public String toString() { if (v) return ONE; else return ZERO; }
}
