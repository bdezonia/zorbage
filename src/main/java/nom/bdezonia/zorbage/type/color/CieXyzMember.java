/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.ColorType;
import nom.bdezonia.zorbage.algebra.type.markers.CompoundType;
import nom.bdezonia.zorbage.algebra.type.markers.FixedSize;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
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
public class CieXyzMember
	implements DoubleCoder, Allocatable<CieXyzMember>, Duplicatable<CieXyzMember>,
		Settable<CieXyzMember>, Gettable<CieXyzMember>, NumberMember<CieXyzMember>,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromIntsExact,
		SetFromLongs,
		SetFromFloats,
		SetFromFloatsExact,
		SetFromDoubles,
		SetFromDoublesExact,
		SetFromBigIntegers,
		SetFromBigDecimals,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsDoubleArray,
		GetAsDoubleArrayExact,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		GetAlgebra<CieXyzAlgebra, CieXyzMember>,
		ApproximateType,
		ColorType,
		CompoundType,
		FixedSize,
		ZeroIncludedType
{
	private double x, y, z;
	
	public CieXyzMember() { }
	
	public CieXyzMember(double... vals) {
		setFromDoubles(vals);
	}
	
	public CieXyzMember(String str) {
		TensorStringRepresentation rep = new TensorStringRepresentation(str);
		long valueCount = rep.firstVectorDimension();
		BigList<OctonionRepresentation> values = rep.firstVectorValues();
		double x = 0;
		double y = 0;
		double z = 0;
		if (valueCount > 0) x = values.get(0).r().doubleValue();
		if (valueCount > 1) y = values.get(1).r().doubleValue();
		if (valueCount > 2) z = values.get(2).r().doubleValue();
		setX(x);
		setY(y);
		setZ(z);
	}

	public void setX(double x) {
		if (x < 0.0 || x > 1.0)
			throw new IllegalArgumentException("x must be between 0 and 1");
		this.x = x;
	}
	
	public void setY(double y) {
		if (y < 0.0 || y > 1.0)
			throw new IllegalArgumentException("y must be between 0 and 1");
		this.y = y;
	}
	
	public void setZ(double z) {
		if (z < 0.0 || z > 1.0)
			throw new IllegalArgumentException("z must be between 0 and 1");
		this.z = z;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public double z() {
		return z;
	}

	@Override
	public int doubleCount() {
		return 3;
	}

	@Override
	public void fromDoubleArray(double[] arr, int index) {
		x = arr[index];
		y = arr[index+1];
		z = arr[index+2];
	}

	@Override
	public void toDoubleArray(double[] arr, int index) {
		arr[index] = x;
		arr[index+1] = y;
		arr[index+2] = z;
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
	public void getV(CieXyzMember value) {
		get(value);
	}

	@Override
	public void setV(CieXyzMember value) {
		set(value);
	}

	@Override
	public void get(CieXyzMember other) {
		other.x = x;
		other.y = y;
		other.z = z;
	}

	@Override
	public void set(CieXyzMember other) {
		x = other.x;
		y = other.y;
		z = other.z;
	}

	@Override
	public CieXyzMember duplicate() {
		CieXyzMember val = allocate();
		get(val);
		return val;
	}

	@Override
	public CieXyzMember allocate() {
		return new CieXyzMember();
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(x);
		v = Hasher.PRIME * v + Hasher.hashCode(y);
		v = Hasher.PRIME * v + Hasher.hashCode(z);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof CieXyzMember) {
			return G.CIEXYZ.isEqual().call(this, (CieXyzMember) o);
		}
		return false;
	}

	@Override
	public void setFromDoubles(double... vals) {
		if (vals.length != 3)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setX(vals[0]);
		setY(vals[1]);
		setZ(vals[2]);
	}

	@Override
	public double[] getAsDoubleArray() {

		return new double[] { x(), y(), z() };
	}

	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {

		return getAsBigDecimalArray();
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		
		return new BigDecimal[] { BigDecimal.valueOf(x()), BigDecimal.valueOf(y()), BigDecimal.valueOf(z()) };
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		
		return new BigInteger[] { BigInteger.valueOf((long)x()), BigInteger.valueOf((long)y()), BigInteger.valueOf((long)z()) };
	}

	@Override
	public double[] getAsDoubleArrayExact() {

		return getAsDoubleArray();
	}

	@Override
	public float[] getAsFloatArray() {
		
		return new float[] { (float) x(), (float) y(), (float) z() };
	}

	@Override
	public long[] getAsLongArray() {

		return new long[] { (long) x(), (long) y(), (long) z() };
	}

	@Override
	public int[] getAsIntArray() {
		
		return new int[] { (int) x(), (int) y(), (int) z() };
	}

	@Override
	public short[] getAsShortArray() {
		
		return new short[] { (short) x(), (short) y(), (short) z() };
	}

	@Override
	public byte[] getAsByteArray() {
		
		return new byte[] { (byte) x(), (byte) y(), (byte) z() };
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		if (vals.length != 3)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setX(vals[0].doubleValue());
		setY(vals[1].doubleValue());
		setZ(vals[2].doubleValue());
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		if (vals.length != 3)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setX(vals[0].doubleValue());
		setY(vals[1].doubleValue());
		setZ(vals[2].doubleValue());
	}

	@Override
	public void setFromDoublesExact(double... vals) {
		setFromDoubles(vals);
	}

	@Override
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}

	@Override
	public void setFromFloats(float... vals) {
		if (vals.length != 3)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setX(vals[0]);
		setY(vals[1]);
		setZ(vals[2]);
	}

	@Override
	public void setFromLongs(long... vals) {
		if (vals.length != 3)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setX(vals[0]);
		setY(vals[1]);
		setZ(vals[2]);
	}

	@Override
	public void setFromIntsExact(int... vals) {
		setFromInts(vals);
	}

	@Override
	public void setFromInts(int... vals) {
		if (vals.length != 3)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setX(vals[0]);
		setY(vals[1]);
		setZ(vals[2]);
	}

	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}

	@Override
	public void setFromShorts(short... vals) {
		if (vals.length != 3)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setX(vals[0]);
		setY(vals[1]);
		setZ(vals[2]);
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		if (vals.length != 3)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setX(vals[0]);
		setY(vals[1]);
		setZ(vals[2]);
	}
	
	@Override
	public CieXyzAlgebra getAlgebra() {

		return G.CIEXYZ;
	}
}
