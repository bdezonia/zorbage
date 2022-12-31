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
package nom.bdezonia.zorbage.type.integer.int15;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.storage.coder.BitCoder;
import nom.bdezonia.zorbage.storage.coder.ShortCoder;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.universal.UniversalRepresentation;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class UnsignedInt15Member
	implements
		BitCoder, ShortCoder,
		Allocatable<UnsignedInt15Member>, Duplicatable<UnsignedInt15Member>,
		Settable<UnsignedInt15Member>, Gettable<UnsignedInt15Member>,
		UniversalRepresentation, NumberMember<UnsignedInt15Member>,
		PrimitiveConversion, HighPrecRepresentation,
		SetReal<Integer>, GetReal<SignedInt16Member>,
		SetFromInt,
		GetAsShort, GetAsShortArray, NativeGetSetShort
{
	short v;

	public UnsignedInt15Member() {
		v = 0;
	}

	public UnsignedInt15Member(UnsignedInt15Member other) {
		set(other);
	}

	public UnsignedInt15Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		int x = val.r().intValue();
		setV(x);
	}
	
	public UnsignedInt15Member(int... vals) {
		setFromInt(vals);
	}
	
	@Override
	public void getV(UnsignedInt15Member value) {
		get(value);
	}

	public short v() {
		return v;
	}
	
	@Override
	public void setV(UnsignedInt15Member value) {
		set(value);
	}

	public void setV(int val) {
		v = (short) (val & 32767);
	}
	
	@Override
	public String toString() {
		return String.valueOf(v);
	}

	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		rep.setValue(new OctonionRepresentation(new BigDecimal(v)));
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		setV(rep.getValue().r().intValue());
	}

	@Override
	public void get(UnsignedInt15Member other) {
		other.v = v;
	}

	@Override
	public void set(UnsignedInt15Member other) {
		v = other.v;
	}

	@Override
	public UnsignedInt15Member duplicate() {
		return new UnsignedInt15Member(this);
	}

	@Override
	public UnsignedInt15Member allocate() {
		return new UnsignedInt15Member();
	}

	@Override
	public int bitCount() {
		return 15;
	}

	@Override
	public void fromBitArray(long[] arr, int index, int offset) {
		if (offset < 50) {
			// 15 bits in 1st long
			long b1b2b3b4b5b6b7b8b9b10b11b12b13b14b15 = (arr[index] >>> offset) & 32767L;
			setV( (int) b1b2b3b4b5b6b7b8b9b10b11b12b13b14b15 );
		}
		else if (offset == 63) {
			// 1 bits in 1st long, 14 bits in second long
			long b1 = (arr[index] >>> 63) & 1L;
			long b2b3b4b5b6b7b8b9b10b11b12b13b14b15 = (arr[index+1] & 16383L);
			setV( (int) ((b1 << 14) | b2b3b4b5b6b7b8b9b10b11b12b13b14b15) );
		}
		else if (offset == 62) {
			// 2 bits in 1st long, 13 bits in second long
			long b1b2 = (arr[index] >>> 62) & 3L;
			long b3b4b5b6b7b8b9b10b11b12b13b14b15 = (arr[index+1] & 8191L);
			setV( (int) ((b1b2 << 13) | b3b4b5b6b7b8b9b10b11b12b13b14b15) );
		}
		else if (offset == 61) {
			// 3 bits in 1st long, 12 bits in second long
			long b1b2b3 = (arr[index] >>> 61) & 7L;
			long b4b5b6b7b8b9b10b11b12b13b14b15 = (arr[index+1] & 4095L);
			setV( (int) ((b1b2b3 << 12) | b4b5b6b7b8b9b10b11b12b13b14b15) );
		}
		else if (offset == 60) {
			// 4 bits in 1st long, 11 bits in second long
			long b1b2b3b4 = (arr[index] >>> 60) & 15L;
			long b5b6b7b8b9b10b11b12b13b14b15 = (arr[index+1] & 2047L);
			setV( (int) ((b1b2b3b4 << 11) | b5b6b7b8b9b10b11b12b13b14b15) );
		}
		else if (offset == 59) {
			// 5 bits in 1st long, 10 bits in second long
			long b1b2b3b4b5 = (arr[index] >>> 59) & 31L;
			long b6b7b8b9b10b11b12b13b14b15 = (arr[index+1] & 1023L);
			setV( (int) ((b1b2b3b4b5 << 10) | b6b7b8b9b10b11b12b13b14b15) );
		}
		else if (offset == 58) {
			// 6 bits in 1st long, 9 bits in second long
			long b1b2b3b4b5b6 = (arr[index] >>> 58) & 63L;
			long b7b8b9b10b11b12b13b14b15 = (arr[index+1] & 511L);
			setV( (int) ((b1b2b3b4b5b6 << 9) | b7b8b9b10b11b12b13b14b15) );
		}
		else if (offset == 57) {
			// 7 bits in 1st long, 8 bits in second long
			long b1b2b3b4b5b6b7 = (arr[index] >>> 57) & 127L;
			long b8b9b10b11b12b13b14b15 = (arr[index+1] & 255L);
			setV( (int) ((b1b2b3b4b5b6b7 << 8) | b8b9b10b11b12b13b14b15) );
		}
		else if (offset == 56) {
			// 8 bits in 1st long, 7 bits in second long
			long b1b2b3b4b5b6b7b8 = (arr[index] >>> 56) & 255L;
			long b9b10b11b12b13b14b15 = (arr[index+1] & 127L);
			setV( (int) ((b1b2b3b4b5b6b7b8 << 7) | b9b10b11b12b13b14b15) );
		}
		else if (offset == 55) {
			// 9 bits in 1st long, 6 bits in second long
			long b1b2b3b4b5b6b7b8b9 = (arr[index] >>> 55) & 511L;
			long b10b11b12b13b14b15 = (arr[index+1] & 63L);
			setV( (int) ((b1b2b3b4b5b6b7b8b9 << 6) | b10b11b12b13b14b15) );
		}
		else if (offset == 54) {
			// 10 bits in 1st long, 5 bits in second long
			long b1b2b3b4b5b6b7b8b9b10 = (arr[index] >>> 54) & 1023L;
			long b11b12b13b14b15 = (arr[index+1] & 31L);
			setV( (int) ((b1b2b3b4b5b6b7b8b9b10 << 5) | b11b12b13b14b15) );
		}
		else if (offset == 53) {
			// 11 bits in 1st long, 4 bits in second long
			long b1b2b3b4b5b6b7b8b9b10b11 = (arr[index] >>> 53) & 2047L;
			long b12b13b14b15 = (arr[index+1] & 15L);
			setV( (int) ((b1b2b3b4b5b6b7b8b9b10b11 << 4) | b12b13b14b15) );
		}
		else if (offset == 52) {
			// 12 bits in 1st long, 3 bits in second long
			long b1b2b3b4b5b6b7b8b9b10b11b12 = (arr[index] >>> 52) & 4095L;
			long b13b14b15 = (arr[index+1] & 7L);
			setV( (int) ((b1b2b3b4b5b6b7b8b9b10b11b12 << 3) | b13b14b15) );
		}
		else if (offset == 51) {
			// 13 bits in 1st long, 3 bits in second long
			long b1b2b3b4b5b6b7b8b9b10b11b1213 = (arr[index] >>> 51) & 8191L;
			long b14b15 = (arr[index+1] & 3L);
			setV( (int) ((b1b2b3b4b5b6b7b8b9b10b11b1213 << 2) | b14b15) );
		}
		else { // (offset == 50) {
			// 14 bits in 1st long, 1 bit in second long
			long b1b2b3b4b5b6b7b8b9b10b11b12b13b14 = (arr[index] >>> 50) & 16383L;
			long b15 = (arr[index+1] & 1L);
			setV( (int) ((b1b2b3b4b5b6b7b8b9b10b11b12b13b14 << 1) | b15) );
		}
	}

	@Override
	public void toBitArray(long[] arr, int index, int offset) {
		if (offset < 50) {
			// 15 bits in 1st long
			long oldVals = arr[index] & ~(32767L << offset);
			long newVals = ((long)v) << offset;
			arr[index] = newVals | oldVals;
		}
		else if (offset == 63) {
			// 1 bits in 1st long, 14 bits in second long
			long b1 = (v & 16384L) >>> 14;
			long b2b3b4b5b6b7b8b9b10b11b12b13b14b15 = (v & 16383L);
			long oldVals = arr[index] & ~(1L << 63);
			long newVals = b1 << 63;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(16383L << 0);
			newVals = b2b3b4b5b6b7b8b9b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 62) {
			// 2 bits in 1st long, 13 bits in second long
			long b1b2 = (v & (16384+8192)) >>> 13;
			long b3b4b5b6b7b8b9b10b11b12b13b14b15 = (v & 8191L);
			long oldVals = arr[index] & ~(3L << 62);
			long newVals = b1b2 << 62;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(8191L << 0);
			newVals = b3b4b5b6b7b8b9b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 61) {
			// 3 bits in 1st long, 12 bits in second long
			long b1b2b3 = (v & (16384+8192+4096)) >>> 12;
			long b4b5b6b7b8b9b10b11b12b13b14b15 = (v & 4095L);
			long oldVals = arr[index] & ~(7L << 61);
			long newVals = b1b2b3 << 61;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(4095L << 0);
			newVals = b4b5b6b7b8b9b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 60) {
			// 4 bits in 1st long, 11 bits in second long
			long b1b2b3b4 = (v & (16384+8192+4096+2048)) >>> 11;
			long b5b6b7b8b9b10b11b12b13b14b15 = (v & 2047L);
			long oldVals = arr[index] & ~(15L << 60);
			long newVals = b1b2b3b4 << 60;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(2047L << 0);
			newVals = b5b6b7b8b9b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 59) {
			// 5 bits in 1st long, 10 bit in second long
			long b1b2b3b4b5 = (v & (16384+8192+4096+2048+1024)) >>> 10;
			long b6b7b8b9b10b11b12b13b14b15 = (v & 1023L);
			long oldVals = arr[index] & ~(31L << 59);
			long newVals = b1b2b3b4b5 << 59;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(1023L << 0);
			newVals = b6b7b8b9b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 58) {
			// 6 bits in 1st long, 9 bit in second long
			long b1b2b3b4b5b6 = (v & (16384+8192+4096+2048+1024+512)) >>> 9;
			long b7b8b9b10b11b12b13b14b15 = (v & 511L);
			long oldVals = arr[index] & ~(63L << 58);
			long newVals = b1b2b3b4b5b6 << 58;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(511L << 0);
			newVals = b7b8b9b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 57) {
			// 7 bits in 1st long, 8 bit in second long
			long b1b2b3b4b5b6b7 = (v & (16384+8192+4096+2048+1024+512+256)) >>> 8;
			long b8b9b10b11b12b13b14b15 = (v & 255L);
			long oldVals = arr[index] & ~(127L << 57);
			long newVals = b1b2b3b4b5b6b7 << 57;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(255L << 0);
			newVals = b8b9b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 56) {
			// 8 bits in 1st long, 7 bit in second long
			long b1b2b3b4b5b6b7b8 = (v & (16384+8192+4096+2048+1024+512+256+128)) >>> 7;
			long b9b10b11b12b13b14b15 = (v & 127L);
			long oldVals = arr[index] & ~(255L << 56);
			long newVals = b1b2b3b4b5b6b7b8 << 56;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(127L << 0);
			newVals = b9b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 55) {
			// 9 bits in 1st long, 6 bit in second long
			long b1b2b3b4b5b6b7b8b9 = (v & (16384+8192+4096+2048+1024+512+256+128+64)) >>> 6;
			long b10b11b12b13b14b15 = (v & 63L);
			long oldVals = arr[index] & ~(511L << 55);
			long newVals = b1b2b3b4b5b6b7b8b9 << 55;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(63L << 0);
			newVals = b10b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 54) {
			// 10 bits in 1st long, 5 bit in second long
			long b1b2b3b4b5b6b7b8b9b10 = (v & (16384+8192+4096+2048+1024+512+256+128+64+32)) >>> 5;
			long b11b12b13b14b15 = (v & 31L);
			long oldVals = arr[index] & ~(1023L << 54);
			long newVals = b1b2b3b4b5b6b7b8b9b10 << 54;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(31L << 0);
			newVals = b11b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 53) {
			// 11 bits in 1st long, 4 bit in second long
			long b1b2b3b4b5b6b7b8b9b10b11 = (v & (16384+8192+4096+2048+1024+512+256+128+64+32+16)) >>> 4;
			long b12b13b14b15 = (v & 15L);
			long oldVals = arr[index] & ~(2047L << 53);
			long newVals = b1b2b3b4b5b6b7b8b9b10b11 << 53;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(15L << 0);
			newVals = b12b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 52) {
			// 12 bits in 1st long, 3 bit in second long
			long b1b2b3b4b5b6b7b8b9b10b11b12 = (v & (16384+8192+4096+2048+1024+512+256+128+64+32+16+8)) >>> 3;
			long b13b14b15 = (v & 7L);
			long oldVals = arr[index] & ~(4095L << 52);
			long newVals = b1b2b3b4b5b6b7b8b9b10b11b12 << 52;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(7L << 0);
			newVals = b13b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 51) {
			// 13 bits in 1st long, 2 bit in second long
			long b1b2b3b4b5b6b7b8b9b10b11b12b13 = (v & (16384+8192+4096+2048+1024+512+256+128+64+32+16+8+4)) >>> 2;
			long b14b15 = (v & 3L);
			long oldVals = arr[index] & ~(8191L << 51);
			long newVals = b1b2b3b4b5b6b7b8b9b10b11b12b13 << 51;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(3L << 0);
			newVals = b14b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else { // (offset == 50) {
			// 14 bits in 1st long, 1 bit in second long
			long b1b2b3b4b5b6b7b8b9b10b11b12b13b14 = (v & (16384+8192+4096+2048+1024+512+256+128+64+32+16+8+4+2)) >>> 1;
			long b15 = (v & 1L);
			long oldVals = arr[index] & ~(16383L << 50);
			long newVals = b1b2b3b4b5b6b7b8b9b10b11b12b13b14 << 50;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(1L << 0);
			newVals = b15 << 0;
			arr[index+1] = newVals | oldVals;
		}
	}

	@Override
	public int shortCount() {
		return 1;
	}

	@Override
	public void fromShortArray(short[] arr, int index) {
		setV(arr[index]);
	}

	@Override
	public void toShortArray(short[] arr, int index) {
		arr[index] = v;
	}

	@Override
	public long dimension(int d) {
		return 0;
	}

	@Override
	public int numDimensions() {
		return 0;
	}

	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.SHORT;
	}

	@Override
	public int componentCount() {
		return 1;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		setV(v);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		setV(v);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		setV(v);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		setV((int)v);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		setV((int)v);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		setV((int)v);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		setV(v.intValue());
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		primComponentSetBigInteger(index, component, v.toBigInteger());
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(v);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(v);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(v);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV((int)v);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV((int)v);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV((int)v);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			setV(v.intValue());
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		primComponentSetBigIntegerSafe(index, component, v.toBigInteger());
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return (byte) v;
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return v;
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigInteger.valueOf(v);
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) return BigDecimal.valueOf(v);
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return (byte) v;
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return v;
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return v;
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return v;
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return v;
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return 0;
		}
		else {
			return v;
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return BigInteger.ZERO;
		}
		else {
			return BigInteger.valueOf(v);
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		boolean oob = component > 0;
		if (!oob) {
			for (int i = 0; i < numDimensions(); i++) {
				if (index.get(i) != 0) {
					oob = true;
					break;
				}
			}
		}
		if (oob) {
			return BigDecimal.ZERO;
		}
		else {
			return BigDecimal.valueOf(v);
		}
	}

	@Override
	public void primitiveInit() {
		v = 0;
	}

	@Override
	public void toHighPrec(HighPrecisionMember result) {
		result.setV(BigDecimal.valueOf(v()));
	}

	@Override
	public void fromHighPrec(HighPrecisionMember input) {
		setV(input.v().intValue());
	}

	@Override
	public void setR(Integer val) {
		setV(val);
	}

	@Override
	public void getR(SignedInt16Member val) {
		val.setV(v);
	}

	@Override
	public int hashCode() {
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(this.v);
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof UnsignedInt15Member) {
			return G.UINT15.isEqual().call(this, (UnsignedInt15Member) o);
		}
		return false;
	}

	@Override
	public short getAsShort() {
		return v();
	}

	@Override
	public void setFromInt(int... vals) {
		if (vals.length != 1)
			throw new IllegalArgumentException("mismatch between component count and input values count");
		setV(vals[0]);
	}

	@Override
	public short[] getAsShortArray() {
		return new short[] {v()};
	}

	@Override
	public short getNative() {

		return v();
	}

	@Override
	public void setNative(short val) {

		setV(val);
	}
}
