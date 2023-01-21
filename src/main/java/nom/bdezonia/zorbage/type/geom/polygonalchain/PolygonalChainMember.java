/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
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

import java.util.List;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Duplicatable;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetAsFloatArray;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.NumberMember;
import nom.bdezonia.zorbage.algebra.SetFromFloats;
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
		NumberMember<PolygonalChainMember>,
		SetFromFloats,
		GetAsFloatArray
{
	float boundsValid;
	float minx, miny, minz, maxx, maxy, maxz;
	float[] x;
	float[] y;
	float[] z;
	
	public PolygonalChainMember() {
		x = new float[0];
		y = new float[0];
		z = new float[0];
		boundsValid = 0;
	}

	public PolygonalChainMember(float[] x, float[] y, float[] z) {

		if (x.length != y.length || x.length != z.length)
			throw new IllegalArgumentException(
					"polygonal chain coordinate arrays must be equal in length");
		
		this.x = x.clone();
		this.y = y.clone();
		this.z = z.clone();
		this.boundsValid = 0;
	}
	
	public PolygonalChainMember(List<Float> x, List<Float> y, List<Float> z) {

		if (x.size() != y.size() || x.size() != z.size())
			throw new IllegalArgumentException(
					"polygonal chain coordinate lists must be equal in length");
		
		int sz = x.size();
		
		this.x = new float[sz];
		this.y = new float[sz];
		this.z = new float[sz];
		
		for (int i = 0; i < sz; i++) {
			
			this.x[i] = x.get(i);
			this.y[i] = y.get(i);
			this.z[i] = z.get(i);
		}
		
		this.boundsValid = 0;
	}
	
	public PolygonalChainMember(float[] triplets) {

		if (triplets.length % 3 != 0)
			throw new IllegalArgumentException(
					"polygonal chain coordinate array length must be a multiple of three");
		
		int sz = triplets.length % 3;
		
		this.x = new float[sz];
		this.y = new float[sz];
		this.z = new float[sz];
		
		for (int i = 0, offset = 0; offset < triplets.length; offset += 3, i++) {
			this.x[i] = triplets[offset + 0];
			this.y[i] = triplets[offset + 1];
			this.z[i] = triplets[offset + 2];
		}

		this.boundsValid = 0;
	}
	
	public PolygonalChainMember(List<Float> triplets) {

		if (triplets.size() % 3 != 0)
			throw new IllegalArgumentException(
					"polygonal chain coordinate list length must be a multiple of three");
		
		int sz = triplets.size() % 3;
		
		this.x = new float[sz];
		this.y = new float[sz];
		this.z = new float[sz];
		
		for (int i = 0, offset = 0; offset < triplets.size(); offset += 3, i++) {
			this.x[i] = triplets.get(offset + 0);
			this.y[i] = triplets.get(offset + 1);
			this.z[i] = triplets.get(offset + 2);
		}

		this.boundsValid = 0;
	}
	
	public PolygonalChainMember(PolygonalChainMember other) {
		this(other.x, other.y, other.z);
	}

	@Override
	public int byteCount() {

		// (1 int to store numFloats value and 7 floats for bounds and
		//   3 floats per x/y/z coord we have)
		//     TIMES 4 bytes per int or float
		
		return (1 + 7 + 3 * x.length) * 4;
	}

	@Override
	public void fromByteArray(byte[] arr, int index) {

		int numFloats;

		numFloats  = ((arr[index + 0] & 0xff) << 24);
		numFloats |= ((arr[index + 1] & 0xff) << 16);
		numFloats |= ((arr[index + 2] & 0xff) << 8);
		numFloats |= ((arr[index + 3] & 0xff) << 0);
		
		if (numFloats < 7)
			throw new IllegalArgumentException("invalid number of floats in chain header");
		
		int numPointFloats = numFloats - 7;
		
		if (numPointFloats % 3 != 0)
			throw new IllegalArgumentException("Tracts require 3 floats per point: numFloats = "+numPointFloats);
	
		int numPoints = numPointFloats / 3;

		if (numPoints != x.length) {
			x = new float[numPoints];
			y = new float[numPoints];
			z = new float[numPoints];
		}

		int tmp;
		byte b;
		
		int base = 4;

		b = arr[index + base + 0];
		tmp = (b & 0xff) << 24;
		b = arr[index + base + 1];
		tmp |= (b & 0xff) << 16;
		b = arr[index + base + 2];
		tmp |= (b & 0xff) << 8;
		b = arr[index + base + 3];
		tmp |= (b & 0xff) << 0;
		boundsValid = Float.intBitsToFloat(tmp);
		
		base += 4;
		
		b = arr[index + base + 0];
		tmp = (b & 0xff) << 24;
		b = arr[index + base + 1];
		tmp |= (b & 0xff) << 16;
		b = arr[index + base + 2];
		tmp |= (b & 0xff) << 8;
		b = arr[index + base + 3];
		tmp |= (b & 0xff) << 0;
		minx = Float.intBitsToFloat(tmp);
		
		base += 4;
		
		b = arr[index + base + 0];
		tmp = (b & 0xff) << 24;
		b = arr[index + base + 1];
		tmp |= (b & 0xff) << 16;
		b = arr[index + base + 2];
		tmp |= (b & 0xff) << 8;
		b = arr[index + base + 3];
		tmp |= (b & 0xff) << 0;
		miny = Float.intBitsToFloat(tmp);
		
		base += 4;
		
		b = arr[index + base + 0];
		tmp = (b & 0xff) << 24;
		b = arr[index + base + 1];
		tmp |= (b & 0xff) << 16;
		b = arr[index + base + 2];
		tmp |= (b & 0xff) << 8;
		b = arr[index + base + 3];
		tmp |= (b & 0xff) << 0;
		minz = Float.intBitsToFloat(tmp);
		
		base += 4;
		
		b = arr[index + base + 0];
		tmp = (b & 0xff) << 24;
		b = arr[index + base + 1];
		tmp |= (b & 0xff) << 16;
		b = arr[index + base + 2];
		tmp |= (b & 0xff) << 8;
		b = arr[index + base + 3];
		tmp |= (b & 0xff) << 0;
		maxx = Float.intBitsToFloat(tmp);
		
		base += 4;
		
		b = arr[index + base + 0];
		tmp = (b & 0xff) << 24;
		b = arr[index + base + 1];
		tmp |= (b & 0xff) << 16;
		b = arr[index + base + 2];
		tmp |= (b & 0xff) << 8;
		b = arr[index + base + 3];
		tmp |= (b & 0xff) << 0;
		maxy = Float.intBitsToFloat(tmp);
		
		base += 4;
		
		b = arr[index + base + 0];
		tmp = (b & 0xff) << 24;
		b = arr[index + base + 1];
		tmp |= (b & 0xff) << 16;
		b = arr[index + base + 2];
		tmp |= (b & 0xff) << 8;
		b = arr[index + base + 3];
		tmp |= (b & 0xff) << 0;
		maxz = Float.intBitsToFloat(tmp);
		
		base += 4;
		
		for (int i = 0; i < numPoints; i++, base += 12) {
			b = arr[index + base + 0];
			tmp = (b & 0xff) << 24;
			b = arr[index + base + 1];
			tmp |= (b & 0xff) << 16;
			b = arr[index + base + 2];
			tmp |= (b & 0xff) << 8;
			b = arr[index + base + 3];
			tmp |= (b & 0xff) << 0;
			x[i] = Float.intBitsToFloat(tmp);
			
			b = arr[index + base + 4];
			tmp = (b & 0xff) << 24;
			b = arr[index + base + 5];
			tmp |= (b & 0xff) << 16;
			b = arr[index + base + 6];
			tmp |= (b & 0xff) << 8;
			b = arr[index + base + 7];
			tmp |= (b & 0xff) << 0;
			y[i] = Float.intBitsToFloat(tmp);
			
			b = arr[index + base + 8];
			tmp = (b & 0xff) << 24;
			b = arr[index + base + 9];
			tmp |= (b & 0xff) << 16;
			b = arr[index + base + 10];
			tmp |= (b & 0xff) << 8;
			b = arr[index + base + 11];
			tmp |= (b & 0xff) << 0;
			z[i] = Float.intBitsToFloat(tmp);
		}
	}

	@Override
	public void toByteArray(byte[] arr, int index) {
		
		int numPoints = x.length;

		int numFloats = 3 * numPoints + 7;
		
		byte b = 0;
		
		b = (byte) ((numFloats & 0xff000000) >> 24);
		arr[index + 0] = b;
		
		b = (byte) ((numFloats & 0x00ff0000) >> 16);
		arr[index + 1] = b;

		b = (byte) ((numFloats & 0x0000ff00) >> 8);
		arr[index + 2] = b;

		b = (byte) ((numFloats & 0x000000ff) >> 0);
		arr[index + 3] = b;
		
		int tmp;

		int base = 4;
		
		tmp = Float.floatToIntBits(boundsValid);
		b = (byte) ((tmp & 0xff000000) >> 24);
		arr[index + base + 0] = b;
		b = (byte) ((tmp & 0x00ff0000) >> 16);
		arr[index + base + 1] = b;
		b = (byte) ((tmp & 0x0000ff00) >> 8);
		arr[index + base + 2] = b;
		b = (byte) ((tmp & 0x000000ff) >> 0);
		arr[index + base + 3] = b;

		base += 4;
		
		tmp = Float.floatToIntBits(minx);
		b = (byte) ((tmp & 0xff000000) >> 24);
		arr[index + base + 0] = b;
		b = (byte) ((tmp & 0x00ff0000) >> 16);
		arr[index + base + 1] = b;
		b = (byte) ((tmp & 0x0000ff00) >> 8);
		arr[index + base + 2] = b;
		b = (byte) ((tmp & 0x000000ff) >> 0);
		arr[index + base + 3] = b;

		base += 4;
		
		tmp = Float.floatToIntBits(miny);
		b = (byte) ((tmp & 0xff000000) >> 24);
		arr[index + base + 0] = b;
		b = (byte) ((tmp & 0x00ff0000) >> 16);
		arr[index + base + 1] = b;
		b = (byte) ((tmp & 0x0000ff00) >> 8);
		arr[index + base + 2] = b;
		b = (byte) ((tmp & 0x000000ff) >> 0);
		arr[index + base + 3] = b;

		base += 4;
		
		tmp = Float.floatToIntBits(minz);
		b = (byte) ((tmp & 0xff000000) >> 24);
		arr[index + base + 0] = b;
		b = (byte) ((tmp & 0x00ff0000) >> 16);
		arr[index + base + 1] = b;
		b = (byte) ((tmp & 0x0000ff00) >> 8);
		arr[index + base + 2] = b;
		b = (byte) ((tmp & 0x000000ff) >> 0);
		arr[index + base + 3] = b;

		base += 4;
		
		tmp = Float.floatToIntBits(maxx);
		b = (byte) ((tmp & 0xff000000) >> 24);
		arr[index + base + 0] = b;
		b = (byte) ((tmp & 0x00ff0000) >> 16);
		arr[index + base + 1] = b;
		b = (byte) ((tmp & 0x0000ff00) >> 8);
		arr[index + base + 2] = b;
		b = (byte) ((tmp & 0x000000ff) >> 0);
		arr[index + base + 3] = b;

		base += 4;
		
		tmp = Float.floatToIntBits(maxy);
		b = (byte) ((tmp & 0xff000000) >> 24);
		arr[index + base + 0] = b;
		b = (byte) ((tmp & 0x00ff0000) >> 16);
		arr[index + base + 1] = b;
		b = (byte) ((tmp & 0x0000ff00) >> 8);
		arr[index + base + 2] = b;
		b = (byte) ((tmp & 0x000000ff) >> 0);
		arr[index + base + 3] = b;

		base += 4;
		
		tmp = Float.floatToIntBits(maxz);
		b = (byte) ((tmp & 0xff000000) >> 24);
		arr[index + base + 0] = b;
		b = (byte) ((tmp & 0x00ff0000) >> 16);
		arr[index + base + 1] = b;
		b = (byte) ((tmp & 0x0000ff00) >> 8);
		arr[index + base + 2] = b;
		b = (byte) ((tmp & 0x000000ff) >> 0);
		arr[index + base + 3] = b;

		base += 4;
		
		for (int i = 0; i < numPoints; i++, base += 12) {
			
			tmp = Float.floatToIntBits(x[i]);
			b = (byte) ((tmp & 0xff000000) >> 24);
			arr[index + base + 0] = b;
			b = (byte) ((tmp & 0x00ff0000) >> 16);
			arr[index + base + 1] = b;
			b = (byte) ((tmp & 0x0000ff00) >> 8);
			arr[index + base + 2] = b;
			b = (byte) ((tmp & 0x000000ff) >> 0);
			arr[index + base + 3] = b;
			
			tmp = Float.floatToIntBits(y[i]);
			b = (byte) ((tmp & 0xff000000) >> 24);
			arr[index + base + 4] = b;
			b = (byte) ((tmp & 0x00ff0000) >> 16);
			arr[index + base + 5] = b;
			b = (byte) ((tmp & 0x0000ff00) >> 8);
			arr[index + base + 6] = b;
			b = (byte) ((tmp & 0x000000ff) >> 0);
			arr[index + base + 7] = b;
			
			tmp = Float.floatToIntBits(z[i]);
			b = (byte) ((tmp & 0xff000000) >> 24);
			arr[index + base + 8] = b;
			b = (byte) ((tmp & 0x00ff0000) >> 16);
			arr[index + base + 9] = b;
			b = (byte) ((tmp & 0x0000ff00) >> 8);
			arr[index + base + 10] = b;
			b = (byte) ((tmp & 0x000000ff) >> 0);
			arr[index + base + 11] = b;
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
		other.boundsValid = boundsValid;
		other.minx = minx;
		other.miny = miny;
		other.minz = minz;
		other.maxx = maxx;
		other.maxy = maxy;
		other.maxz = maxz;
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
		boundsValid = other.boundsValid;
		minx = other.minx;
		miny = other.miny;
		minz = other.minz;
		maxx = other.maxx;
		maxy = other.maxy;
		maxz = other.maxz;
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
		
		int numPoints = x.length;
		
		int numFloats = 3 * numPoints;
		
		float[] vals = new float[numFloats];
		
		int idx = 0;
		for (int i = 0; i < numPoints; i++, idx += 3) {
			vals[idx + 0] = x[i];
			vals[idx + 1] = y[i];
			vals[idx + 2] = z[i];
		}

		return vals;
	}

	@Override
	public void setFromFloats(float... vals) {

		if (vals.length % 3 != 0)
			throw new IllegalArgumentException("Tracts require 3 floats per point");
		
		int numPoints = vals.length / 3;
		
		if (x.length != numPoints) {
			x = new float[numPoints];
			y = new float[numPoints];
			z = new float[numPoints];
		}
		
		int idx = 0;
		for (int i = 0; i < numPoints; i++, idx += 3) {
			x[i] = vals[idx + 0];
			y[i] = vals[idx + 1];
			z[i] = vals[idx + 2];
		}
		
		boundsValid = 0;
	}

	@Override
	public long dimension(int d) {
					// TODO
		return 0;	// is this correct? or x.length for dim 0 and 1 otherwise?
	}

	@Override
	public int numDimensions() {
					// TODO
		return 0;	// is this correct? a number is 0-dimensional. is a tract?
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
			throw new IllegalArgumentException("index out of bounds in PolygonalChainMember");
		
		val.setV( x[pointNum] );
	}
	
	public void getY(int pointNum, Float32Member val) {
		
		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in PolygonalChainMember");
		
		val.setV( y[pointNum] );
	}
	
	public void getZ(int pointNum, Float32Member val) {
		
		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in PolygonalChainMember");
		
		val.setV( z[pointNum] );
	}
	
	public void setX(int pointNum, Float32Member val) {

		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in PolygonalChainMember");

		x[pointNum] = val.v();
		
		boundsValid = 0;  // this could be done much more efficiently
	}
	
	public void setY(int pointNum, Float32Member val) {
		
		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in PolygonalChainMember");
		
		y[pointNum] = val.v();
		
		boundsValid = 0;  // this could be done much more efficiently
	}
	
	public void setZ(int pointNum, Float32Member val) {
		
		if (pointNum < 0 || pointNum >= x.length)
			throw new IllegalArgumentException("index out of bounds in PolygonalChainMember");
		
		z[pointNum] = val.v();
		
		boundsValid = 0;  // this could be done much more efficiently
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
		
		// TODO DO SOMETHING ... a nx3 matrix? (a column of n 3-d rows). is that mathematically correct?
		
		return super.toString();
	}
	
	public float getMinX() {
		
		if (boundsValid == 0) {
			calcBounds();
		}
		
		return minx;
	}
	
	public float getMinY() {
		
		if (boundsValid == 0) {
			calcBounds();
		}
		
		return miny;
	}
	
	public float getMinZ() {
		
		if (boundsValid == 0) {
			calcBounds();
		}
		
		return minz;
	}
	
	public float getMaxX() {
		
		if (boundsValid == 0) {
			calcBounds();
		}
		
		return maxx;
	}
	
	public float getMaxY() {
		
		if (boundsValid == 0) {
			calcBounds();
		}
		
		return maxy;
	}
	
	public float getMaxZ() {
		
		if (boundsValid == 0) {
			calcBounds();
		}
		
		return maxz;
	}
	
	private void calcBounds() {
		
		if (x.length == 0) {
			minx = Float.NaN;
			miny = Float.NaN;
			minz = Float.NaN;
			maxx = Float.NaN;
			maxy = Float.NaN;
			maxz = Float.NaN;
		}
		else {
			minx = maxx = x[0];
			miny = maxy = y[0];
			minz = maxz = z[0];
		}
		
		for (int i = 1; i < x.length; i++) {
			
			float val = x[i];
			
			if (val < minx)
				minx = val;
			else if (val > maxx)
				maxx = val;

			val = y[i];
			
			if (val < miny)
				miny = val;
			else if (val > maxy)
				maxy = val;

			val = z[i];
			
			if (val < minz)
				minz = val;
			else if (val > maxz)
				maxz = val;
		}
		
		boundsValid = 1;
	}
}
