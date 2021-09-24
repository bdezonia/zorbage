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
package nom.bdezonia.zorbage.type.geom.polygonalchain;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetAsFloatArray;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.SetFromFloat;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.storage.coder.ByteCoder;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * @author Barry DeZonia
 */
public class PolygonalChainMember
	implements
		ByteCoder, Settable<PolygonalChainMember>, Gettable<PolygonalChainMember>,
		Allocatable<PolygonalChainMember>, Duplicatable<PolygonalChainMember>,
		SetFromFloat, GetAsFloatArray, NumberMember<PolygonalChainMember>
{
	float[] x;
	float[] y;
	float[] z;
	
	public PolygonalChainMember() {
		x = new float[0];
		y = new float[0];
		z = new float[0];
	}

	public PolygonalChainMember(float[] x, float[] y, float[] z) {

		if (x.length != y.length || x.length != z.length)
			throw new IllegalArgumentException(
					"polygonal chain coordinate arrays must be equal in length");
		
		this.x = x.clone();
		this.y = y.clone();
		this.z = z.clone();
	}
	
	public PolygonalChainMember(PolygonalChainMember other) {
		this(other.x, other.y, other.z);
	}

	@Override
	public int byteCount() {

		return (1 + 3 * x.length) * 4;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {

		int numFloats;

		numFloats  = ((arr[0] & 0xff) << 24);
		numFloats |= ((arr[1] & 0xff) << 16);
		numFloats |= ((arr[2] & 0xff) << 8);
		numFloats |= ((arr[3] & 0xff) << 0);
		
		if (numFloats % 3 != 0)
			throw new IllegalArgumentException("Tracks require 3 floats per point");
		
		if (numFloats != 3 * x.length) {
			x = new float[numFloats/3];
			y = new float[numFloats/3];
			z = new float[numFloats/3];
		}
		
		for (int i = 0; i < x.length; i++) {
			int tmp;
			byte b;
			int base = 4 + 12*i;
			
			b = arr[base + 0];
			tmp = (b & 0xff) << 24;
			b = arr[base + 1];
			tmp = (b & 0xff) << 16;
			b = arr[base + 2];
			tmp = (b & 0xff) << 8;
			b = arr[base + 3];
			tmp = (b & 0xff) << 0;
			x[i] = Float.intBitsToFloat(tmp);
			
			b = arr[base + 4];
			tmp = (b & 0xff) << 24;
			b = arr[base + 5];
			tmp = (b & 0xff) << 16;
			b = arr[base + 6];
			tmp = (b & 0xff) << 8;
			b = arr[base + 7];
			tmp = (b & 0xff) << 0;
			y[i] = Float.intBitsToFloat(tmp);
			
			b = arr[base + 8];
			tmp = (b & 0xff) << 24;
			b = arr[base + 9];
			tmp = (b & 0xff) << 16;
			b = arr[base + 10];
			tmp = (b & 0xff) << 8;
			b = arr[base + 11];
			tmp = (b & 0xff) << 0;
			z[i] = Float.intBitsToFloat(tmp);
		}
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		
		int numFloats = 3 * x.length;
		
		byte b = 0;
		
		b = (byte) ((numFloats & 0xff000000) >> 24);
		arr[0] = b;
		
		b = (byte) ((numFloats & 0x00ff0000) >> 16);
		arr[1] = b;

		b = (byte) ((numFloats & 0x0000ff00) >> 8);
		arr[2] = b;

		b = (byte) ((numFloats & 0x000000ff) >> 0);
		arr[3] = b;
		
		for (int i = 0; i < x.length; i++) {
			int tmp;
			int base = 4 + 12 * i;
			
			tmp = Float.floatToIntBits(x[i]);
			b = (byte) ((tmp & 0xff000000) >> 24);
			arr[base + 0] = b;
			b = (byte) ((tmp & 0x00ff0000) >> 16);
			arr[base + 1] = b;
			b = (byte) ((tmp & 0x0000ff00) >> 8);
			arr[base + 2] = b;
			b = (byte) ((tmp & 0x000000ff) >> 0);
			arr[base + 3] = b;
			
			tmp = Float.floatToIntBits(y[i]);
			b = (byte) ((tmp & 0xff000000) >> 24);
			arr[base + 4] = b;
			b = (byte) ((tmp & 0x00ff0000) >> 16);
			arr[base + 5] = b;
			b = (byte) ((tmp & 0x0000ff00) >> 8);
			arr[base + 6] = b;
			b = (byte) ((tmp & 0x000000ff) >> 0);
			arr[base + 7] = b;
			
			tmp = Float.floatToIntBits(z[i]);
			b = (byte) ((tmp & 0xff000000) >> 24);
			arr[base + 8] = b;
			b = (byte) ((tmp & 0x00ff0000) >> 16);
			arr[base + 9] = b;
			b = (byte) ((tmp & 0x0000ff00) >> 8);
			arr[base + 10] = b;
			b = (byte) ((tmp & 0x000000ff) >> 0);
			arr[base + 11] = b;
		}
	}

	@Override
	public void get(PolygonalChainMember other) {

		if (other.x.length != x.length) {
			other.x = x.clone();
			other.y = y.clone();
			other.z = z.clone();
		}
		else {
			for (int i = 0; i < x.length; i++) {
				other.x[i] = x[i];
				other.y[i] = y[i];
				other.z[i] = z[i];
			}
		}
	}

	@Override
	public void set(PolygonalChainMember other) {

		if (other.x.length != x.length) {
			x = other.x.clone();
			y = other.y.clone();
			z = other.z.clone();
		}
		else {
			for (int i = 0; i < x.length; i++) {
				x[i] = other.x[i];
				y[i] = other.y[i];
				z[i] = other.z[i];
			}
		}
	}

	@Override
	public PolygonalChainMember duplicate() {

		return new PolygonalChainMember(this);
	}

	@Override
	public PolygonalChainMember allocate() {

		return new PolygonalChainMember();
	}

	@Override
	public float[] getAsFloatArray() {
		
		float[] vals = new float[3 * x.length];
		
		int idx = 0;
		for (int i = 0; i < x.length; i++, idx+=3) {
			vals[idx + 0] = x[i];
			vals[idx + 1] = y[i];
			vals[idx + 2] = z[i];
		}

		return vals;
	}

	@Override
	public void setFromFloat(float... vals) {

		if (vals.length % 3 != 0)
			throw new IllegalArgumentException("Tracks require 3 floats per point");
		
		int numFloats = vals.length / 3;
		
		if (x.length != numFloats) {
			x = new float[numFloats];
			y = new float[numFloats];
			z = new float[numFloats];
		}
		
		int idx = 0;
		for (int i = 0; i < numFloats; i++, idx+=3) {
			x[i] = vals[idx + 0];
			y[i] = vals[idx + 1];
			z[i] = vals[idx + 2];
		}
	}

	@Override
	public long dimension(int d) {
					// TODO
		return 0;	// is this correct? or x.length for dim 0 and 1 otherwise?
	}

	@Override
	public int numDimensions() {
					// TODO
		return 0;	// is this correct? a number is 0-dimensional. is a track?
	}

	@Override
	public void getV(PolygonalChainMember value) {

		get(value);
	}

	@Override
	public void setV(PolygonalChainMember value) {
		set(value);
	}
	
	public int pointCount() {
		
		return x.length;
	}
	
	public void getX(int pointNum, Float32Member val) {

		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in TrackMember");
		
		val.setV( x[pointNum] );
	}
	
	public void getY(int pointNum, Float32Member val) {
		
		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in TrackMember");
		
		val.setV( y[pointNum] );
	}
	
	public void getZ(int pointNum, Float32Member val) {
		
		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in TrackMember");
		
		val.setV( z[pointNum] );
	}
	
	public void setX(int pointNum, Float32Member val) {

		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in TrackMember");
		
		x[pointNum] = val.v();
	}
	
	public void setY(int pointNum, Float32Member val) {
		
		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in TrackMember");
		
		y[pointNum] = val.v();
	}
	
	public void setZ(int pointNum, Float32Member val) {
		
		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in TrackMember");
		
		z[pointNum] = val.v();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PolygonalChainMember)
			return G.CHAIN.isEqual().call(this, (PolygonalChainMember) obj);
		return false;
	}
	
	@Override
	public int hashCode() {

		int code = Hasher.PRIME;
		
		for (int i = 0; i < Math.max(3, x.length); i++) {
			code = code * Hasher.PRIME + Hasher.hashCode(x[i]);
			code = code * Hasher.PRIME + Hasher.hashCode(y[i]);
			code = code * Hasher.PRIME + Hasher.hashCode(z[i]);
		}

		return code;
	}
	
	@Override
	public String toString() {
		
		// TODO DO SOMETHING ...
		
		return super.toString();
	}
}
