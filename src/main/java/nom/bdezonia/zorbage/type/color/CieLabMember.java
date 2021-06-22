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
import nom.bdezonia.zorbage.storage.coder.DoubleCoder;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class CieLabMember
	implements DoubleCoder, Allocatable<CieLabMember>, Duplicatable<CieLabMember>,
		Settable<CieLabMember>, Gettable<CieLabMember>, NumberMember<CieLabMember>,
		SetFromDouble
{
	private double l, a, b;
	
	public CieLabMember() { }
	
	public CieLabMember(double... val) {
		setFromDouble(val);
	}
	
	public CieLabMember(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		long valueCount = rep.firstVectorDimension();
		BigList<OctonionRepresentation> values = rep.firstVectorValues();
		double l = 0;
		double a = 0;
		double b = 0;
		if (valueCount > 0) l = values.get(0).r().doubleValue();
		if (valueCount > 1) a = values.get(1).r().doubleValue();
		if (valueCount > 2) b = values.get(2).r().doubleValue();
		setL(l);
		setA(a);
		setB(b);
	}

	public void setL(double l) {
		if (l < 0 || l > 100)
			throw new IllegalArgumentException("CIELAB: cannot set L outside of 0-100 range");
		this.l = l;
	}
	
	public void setA(double a) {
		if (!Double.isFinite(a))
			throw new IllegalArgumentException("CIELAB: cannot set A outside finite range");
		this.a = a;
	}
	
	public void setB(double b) {
		if (!Double.isFinite(b))
			throw new IllegalArgumentException("CIELAB: cannot set B outside finite range");
		this.b = b;
	}
	
	public double l() {
		return l;
	}
	
	public double a() {
		return a;
	}
	
	public double b() {
		return b;
	}

	@Override
	public int doubleCount() {
		return 3;
	}

	@Override
	public void fromDoubleArray(double[] arr, int index) {
		l = arr[index];
		a = arr[index+1];
		b = arr[index+2];
	}

	@Override
	public void toDoubleArray(double[] arr, int index) {
		arr[index] = l;
		arr[index+1] = a;
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
	public void getV(CieLabMember value) {
		get(value);
	}

	@Override
	public void setV(CieLabMember value) {
		set(value);
	}

	@Override
	public void get(CieLabMember other) {
		other.l = l;
		other.a = a;
		other.b = b;
	}

	@Override
	public void set(CieLabMember other) {
		l = other.l;
		a = other.a;
		b = other.b;
	}

	@Override
	public CieLabMember duplicate() {
		CieLabMember val = allocate();
		get(val);
		return val;
	}

	@Override
	public CieLabMember allocate() {
		return new CieLabMember();
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(l);
		v = Hasher.PRIME * v + Hasher.hashCode(a);
		v = Hasher.PRIME * v + Hasher.hashCode(b);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof CieLabMember) {
			return G.LAB.isEqual().call(this, (CieLabMember) o);
		}
		return false;
	}

	@Override
	public void setFromDouble(double... v) {
		setL(v[0]);
		setA(v[1]);
		setB(v[2]);
	}
}
