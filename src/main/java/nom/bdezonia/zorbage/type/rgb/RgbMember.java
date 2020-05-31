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
package nom.bdezonia.zorbage.type.rgb;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RgbMember
	implements ByteCoder, Allocatable<RgbMember>, Duplicatable<RgbMember>,
				Settable<RgbMember>, Gettable<RgbMember>, NumberMember<RgbMember>
{
	private byte r, g, b;
	
	public RgbMember() {
	}
	
	public RgbMember(int r, int g, int b) {
		setR(r);
		setG(g);
		setB(b);
	}
	
	public void setR(int r) {
		this.r = (byte) (r & 0xff);
	}
	
	public void setG(int g) {
		this.g = (byte) (g & 0xff);
	}
	
	public void setB(int b) {
		this.b = (byte) (b & 0xff);
	}
	
	public int r() {
		return r & 0xff;
	}
	
	public int g() {
		return g & 0xff;
	}
	
	public int b() {
		return b & 0xff;
	}

	@Override
	public int byteCount() {
		return 3;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		r = arr[index];
		g = arr[index+1];
		b = arr[index+2];
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		arr[index] = r;
		arr[index+1] = g;
		arr[index+2] = b;
	}

	@Override
	public long dimension(int d) {
		if (d < 0) throw new IllegalArgumentException("negative index");
		return 1;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public void getV(RgbMember value) {
		get(value);
	}

	@Override
	public void setV(RgbMember value) {
		set(value);
	}

	@Override
	public void get(RgbMember other) {
		other.r = r;
		other.g = g;
		other.b = b;
	}

	@Override
	public void set(RgbMember other) {
		r = other.r;
		g = other.g;
		b = other.b;
	}

	@Override
	public RgbMember duplicate() {
		RgbMember val = allocate();
		get(val);
		return val;
	}

	@Override
	public RgbMember allocate() {
		return new RgbMember();
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(r);
		v = Hasher.PRIME * v + Hasher.hashCode(g);
		v = Hasher.PRIME * v + Hasher.hashCode(b);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o instanceof RgbMember) {
			return G.RGB.isEqual().call(this, (RgbMember) o);
		}
		return false;
	}
}
