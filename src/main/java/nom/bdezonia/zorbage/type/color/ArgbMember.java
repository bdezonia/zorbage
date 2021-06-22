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
public class ArgbMember
	implements ByteCoder, Allocatable<ArgbMember>, Duplicatable<ArgbMember>,
		Settable<ArgbMember>, Gettable<ArgbMember>, NumberMember<ArgbMember>,
		SetFromInt
{
	private byte a, r, g, b;
	
	public ArgbMember() { }
	
	public ArgbMember(int ... val) {
		setFromInt(val);
	}
	
	public ArgbMember(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		long valueCount = rep.firstVectorDimension();
		BigList<OctonionRepresentation> values = rep.firstVectorValues();
		int a = 0;
		int r = 0;
		int g = 0;
		int b = 0;
		if (valueCount > 0) a = values.get(0).r().intValue();
		if (valueCount > 1) r = values.get(1).r().intValue();
		if (valueCount > 2) g = values.get(2).r().intValue();
		if (valueCount > 3) b = values.get(3).r().intValue();
		setA(a);
		setR(r);
		setG(g);
		setB(b);
	}

	public void setA(int a) {
		this.a = (byte) (a & 0xff);
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
	
	public int a() {
		return a & 0xff;
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
		return 4;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {
		a = arr[index];
		r = arr[index+1];
		g = arr[index+2];
		b = arr[index+3];
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		arr[index] = a;
		arr[index+1] = r;
		arr[index+2] = g;
		arr[index+3] = b;
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
	public void getV(ArgbMember value) {
		get(value);
	}

	@Override
	public void setV(ArgbMember value) {
		set(value);
	}

	@Override
	public void get(ArgbMember other) {
		other.a = a;
		other.r = r;
		other.g = g;
		other.b = b;
	}

	@Override
	public void set(ArgbMember other) {
		a = other.a;
		r = other.r;
		g = other.g;
		b = other.b;
	}

	@Override
	public ArgbMember duplicate() {
		ArgbMember val = allocate();
		get(val);
		return val;
	}

	@Override
	public ArgbMember allocate() {
		return new ArgbMember();
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(a);
		v = Hasher.PRIME * v + Hasher.hashCode(r);
		v = Hasher.PRIME * v + Hasher.hashCode(g);
		v = Hasher.PRIME * v + Hasher.hashCode(b);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof ArgbMember) {
			return G.ARGB.isEqual().call(this, (ArgbMember) o);
		}
		return false;
	}

	@Override
	public void setFromInt(int... v) {
		setA(v[0]);
		setR(v[1]);
		setG(v[2]);
		setB(v[3]);
	}
}
