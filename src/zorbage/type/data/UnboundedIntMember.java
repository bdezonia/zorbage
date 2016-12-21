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

import java.math.BigInteger;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class UnboundedIntMember {

	private BigInteger v;
	
	public UnboundedIntMember() {
		v = BigInteger.ZERO;
	}
	
	public UnboundedIntMember(long value) {
		v = BigInteger.valueOf(value);
	}
	
	public UnboundedIntMember(BigInteger value) {
		v = value;
	}
	
	public UnboundedIntMember(UnboundedIntMember value) {
		v = value.v;
	}
	
	public UnboundedIntMember(String value) {
		String[] strs = value.trim().split("\\s+");
		v = new BigInteger(strs[0]);
	}

	public BigInteger v() { return v; }
	public void setV(BigInteger val) { v = val; }
	
	
	public void set(UnboundedIntMember other) {
		v = other.v;
	}
	
	public void get(UnboundedIntMember other) {
		other.v = v;
	}

	@Override
	public String toString() { return "" + v; }
	
}
