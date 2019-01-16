/*
 * Zorbage: an Algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.int10;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.NumberMember;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.Duplicatable;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.type.storage.coder.BitCoder;
import nom.bdezonia.zorbage.type.storage.coder.ShortCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class UnsignedInt10Member
	implements
		BitCoder, ShortCoder,
		Allocatable<UnsignedInt10Member>, Duplicatable<UnsignedInt10Member>,
		Settable<UnsignedInt10Member>, Gettable<UnsignedInt10Member>,
		UniversalRepresentation, NumberMember<UnsignedInt10Member>,
		PrimitiveConversion
{
	short v;

	public UnsignedInt10Member() {
		v = 0;
	}

	public UnsignedInt10Member(UnsignedInt10Member other) {
		set(other);
	}

	public UnsignedInt10Member(String value) {
		TensorStringRepresentation rep = new TensorStringRepresentation(value);
		OctonionRepresentation val = rep.firstValue();
		int x = val.r().intValue();
		setV(x);
	}
	
	public UnsignedInt10Member(int v) {
		setV(v);
	}
	
	@Override
	public void v(UnsignedInt10Member value) {
		get(value);
	}

	public short v() {
		return v;
	}
	
	@Override
	public void setV(UnsignedInt10Member value) {
		set(value);
	}

	void setV(int val) {
		v = (short) (val & 1023);
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
	public void get(UnsignedInt10Member other) {
		other.v = v;
	}

	@Override
	public void set(UnsignedInt10Member other) {
		v = other.v;
	}

	@Override
	public UnsignedInt10Member duplicate() {
		return new UnsignedInt10Member(this);
	}

	@Override
	public UnsignedInt10Member allocate() {
		return new UnsignedInt10Member();
	}

	@Override
	public int bitCount() {
		return 10;
	}

	@Override
	public void fromBitArray(long[] arr, int index, int offset) {
		if (offset < 55) {
			// 10 bits in 1st long
			long b1b2b3b4b5b6b7b8b9b10 = (arr[index] >>> offset) & 1023L;
			setV( (int) b1b2b3b4b5b6b7b8b9b10 );
		}
		else if (offset == 63) {
			// 1 bits in 1st long, 9 bits in second long
			long b1 = (arr[index] >>> 63) & 1L;
			long b2b3b4b5b6b7b8b9b10 = (arr[index+1] & 511L);
			setV( (int) ((b1 << 9) | b2b3b4b5b6b7b8b9b10) );
		}
		else if (offset == 62) {
			// 2 bits in 1st long, 8 bits in second long
			long b1b2 = (arr[index] >>> 62) & 3L;
			long b3b4b5b6b7b8b9b10 = (arr[index+1] & 255L);
			setV( (int) ((b1b2 << 8) | b3b4b5b6b7b8b9b10) );
		}
		else if (offset == 61) {
			// 3 bits in 1st long, 7 bits in second long
			long b1b2b3 = (arr[index] >>> 61) & 7L;
			long b4b5b6b7b8b9b10 = (arr[index+1] & 127L);
			setV( (int) ((b1b2b3 << 7) | b4b5b6b7b8b9b10) );
		}
		else if (offset == 60) {
			// 4 bits in 1st long, 6 bits in second long
			long b1b2b3b4 = (arr[index] >>> 60) & 15L;
			long b5b6b7b8b9b10 = (arr[index+1] & 63L);
			setV( (int) ((b1b2b3b4 << 6) | b5b6b7b8b9b10) );
		}
		else if (offset == 59) {
			// 5 bits in 1st long, 5 bits in second long
			long b1b2b3b4b5 = (arr[index] >>> 59) & 31L;
			long b6b7b8b9b10 = (arr[index+1] & 31L);
			setV( (int) ((b1b2b3b4b5 << 5) | b6b7b8b9b10) );
		}
		else if (offset == 58) {
			// 6 bits in 1st long, 4 bit in second long
			long b1b2b3b4b5b6 = (arr[index] >>> 58) & 63L;
			long b7b8b9b10 = (arr[index+1] & 15L);
			setV( (int) ((b1b2b3b4b5b6 << 4) | b7b8b9b10) );
		}
		else if (offset == 57) {
			// 7 bits in 1st long, 3 bit in second long
			long b1b2b3b4b5b6b7 = (arr[index] >>> 57) & 127L;
			long b8b9b10 = (arr[index+1] & 7L);
			setV( (int) ((b1b2b3b4b5b6b7 << 3) | b8b9b10) );
		}
		else if (offset == 56) {
			// 8 bits in 1st long, 2 bit in second long
			long b1b2b3b4b5b6b7b8 = (arr[index] >>> 56) & 255L;
			long b9b10 = (arr[index+1] & 3L);
			setV( (int) ((b1b2b3b4b5b6b7b8 << 2) | b9b10) );
		}
		else { // (offset == 55) {
			// 9 bits in 1st long, 1 bit in second long
			long b1b2b3b4b5b6b7b8b9 = (arr[index] >>> 55) & 511L;
			long b10 = (arr[index+1] & 1L);
			setV( (int) ((b1b2b3b4b5b6b7b8b9 << 1) | b10) );
		}
	}

	@Override
	public void toBitArray(long[] arr, int index, int offset) {
		if (offset < 55) {
			// 10 bits in 1st long
			long oldVals = arr[index] & ~(1023L << offset);
			long newVals = ((long)v) << offset;
			arr[index] = newVals | oldVals;
		}
		else if (offset == 63) {
			// 1 bits in 1st long, 9 bits in second long
			long b1 = (v & 512L) >>> 9;
			long b2b3b4b5b6b7b8b9b10 = (v & 511L);
			long oldVals = arr[index] & ~(1L << 63);
			long newVals = b1 << 63;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(511L << 0);
			newVals = b2b3b4b5b6b7b8b9b10 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 62) {
			// 2 bits in 1st long, 8 bits in second long
			long b1b2 = (v & 768L) >>> 8;
			long b3b4b5b6b7b8b9b10 = (v & 255L);
			long oldVals = arr[index] & ~(3L << 62);
			long newVals = b1b2 << 62;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(255L << 0);
			newVals = b3b4b5b6b7b8b9b10 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 61) {
			// 3 bits in 1st long, 7 bits in second long
			long b1b2b3 = (v & 896L) >>> 7;
			long b4b5b6b7b8b9b10 = (v & 127L);
			long oldVals = arr[index] & ~(7L << 61);
			long newVals = b1b2b3 << 61;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(127L << 0);
			newVals = b4b5b6b7b8b9b10 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 60) {
			// 4 bits in 1st long, 6 bits in second long
			long b1b2b3b4 = (v & 960L) >>> 6;
			long b5b6b7b8b9b10 = (v & 63L);
			long oldVals = arr[index] & ~(15L << 60);
			long newVals = b1b2b3b4 << 60;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(63L << 0);
			newVals = b5b6b7b8b9b10 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 59) {
			// 5 bits in 1st long, 5 bit in second long
			long b1b2b3b4b5 = (v & 992L) >>> 5;
			long b6b7b8b9b10 = (v & 31L);
			long oldVals = arr[index] & ~(31L << 59);
			long newVals = b1b2b3b4b5 << 59;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(31L << 0);
			newVals = b6b7b8b9b10 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 58) {
			// 6 bits in 1st long, 4 bit in second long
			long b1b2b3b4b5b6 = (v & 1008L) >>> 4;
			long b7b8b9b10 = (v & 15L);
			long oldVals = arr[index] & ~(63L << 58);
			long newVals = b1b2b3b4b5b6 << 58;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(15L << 0);
			newVals = b7b8b9b10 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 57) {
			// 7 bits in 1st long, 3 bit in second long
			long b1b2b3b4b5b6b7 = (v & 1016L) >>> 3;
			long b8b9b10 = (v & 7L);
			long oldVals = arr[index] & ~(127L << 57);
			long newVals = b1b2b3b4b5b6b7 << 57;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(7L << 0);
			newVals = b8b9b10 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else if (offset == 56) {
			// 8 bits in 1st long, 2 bit in second long
			long b1b2b3b4b5b6b7b8 = (v & 1020L) >>> 2;
			long b9b10 = (v & 3L);
			long oldVals = arr[index] & ~(255L << 56);
			long newVals = b1b2b3b4b5b6b7b8 << 56;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(3L << 0);
			newVals = b9b10 << 0;
			arr[index+1] = newVals | oldVals;
		}
		else { // (offset == 55) {
			// 9 bits in 1st long, 1 bit in second long
			long b1b2b3b4b5b6b7b8b9 = (v & 1022L) >>> 1;
			long b10 = (v & 1L);
			long oldVals = arr[index] & ~(255L << 55);
			long newVals = b1b2b3b4b5b6b7b8b9 << 55;
			arr[index] = newVals | oldVals;
			oldVals = arr[index+1] & ~(1L << 0);
			newVals = b10 << 0;
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
	public void fromShortFile(RandomAccessFile raf) throws IOException {
		setV(raf.readShort());
	}

	@Override
	public void toShortFile(RandomAccessFile raf) throws IOException {
		raf.writeShort(v);
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
}
