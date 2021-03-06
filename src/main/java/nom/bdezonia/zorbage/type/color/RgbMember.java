/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.color;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RgbMember
	implements
		ByteCoder, Allocatable<RgbMember>, Duplicatable<RgbMember>,
		Settable<RgbMember>, Gettable<RgbMember>, NumberMember<RgbMember>,
		SetFromInt, GetAsIntArray
{
	private byte r, g, b;
	
	public RgbMember() {
	}
	
	public RgbMember(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		long valueCount = rep.firstVectorDimension();
		BigList<OctonionRepresentation> values = rep.firstVectorValues();
		int r = 0;
		int g = 0;
		int b = 0;
		if (valueCount > 0) r = values.get(0).r().intValue();
		if (valueCount > 1) g = values.get(1).r().intValue();
		if (valueCount > 2) b = values.get(2).r().intValue();
		setR(r);
		setG(g);
		setB(b);
	}
	
	public RgbMember(int... vals) {
		setFromInt(vals);
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
		return 0;
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
		if (o instanceof RgbMember) {
			return G.RGB.isEqual().call(this, (RgbMember) o);
		}
		return false;
	}

	@Override
	public void setFromInt(int... vals) {
		setR(vals[0]);
		setG(vals[1]);
		setB(vals[2]);
	}

	@Override
	public int[] getAsIntArray() {
		return new int[] {r(), g(), b()};
	}
}
