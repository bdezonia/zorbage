/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64.real;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.algebra.TensorMember;
import nom.bdezonia.zorbage.type.ctor.MemoryConstruction;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.parse.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.linear.file.FileStorageFloat64;
import nom.bdezonia.zorbage.util.BigList;


// rank
// dimension
// upper and lower indices

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float64TensorProductMember
	implements
		TensorMember<Float64Member>,
		Gettable<Float64TensorProductMember>,
		Settable<Float64TensorProductMember>,
		PrimitiveConversion
// TODO: UniversalRepresentation
{

	private static final Float64Member ZERO = new Float64Member(0);

	private LinearStorage<?,Float64Member> storage;
	private long[] dims;
	private long[] multipliers;
	private MemoryConstruction m;
	private StorageConstruction s;
	
	public Float64TensorProductMember() {
		dims = new long[0];
		storage = new ArrayStorageFloat64<Float64Member>(0, new Float64Member());
		multipliers = calcMultipliers();
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
	}

	public Float64TensorProductMember(long[] dims, double[] vals) {
		if (vals.length != numElems(dims))
			throw new IllegalArgumentException("incorrect number of values provided to tensor constructor");
		this.dims = dims;
		storage = new ArrayStorageFloat64<Float64Member>(vals.length, new Float64Member());
		multipliers = calcMultipliers();
		m = MemoryConstruction.DENSE;
		s = StorageConstruction.ARRAY;
		Float64Member tmp = new Float64Member();
		for (int i = 0; i < vals.length; i++) {
			tmp.setV(vals[i]);
			storage.set(i, tmp);
		}
	}
	
	public Float64TensorProductMember(Float64TensorProductMember other) { 
		dims = other.dims.clone();
		storage = other.storage.duplicate();
		multipliers = calcMultipliers();
		m = other.m;
		s = other.s;
	}
	
	public Float64TensorProductMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.values();
		storage = new ArrayStorageFloat64<Float64Member>(data.size(), new Float64Member());
		dims = rep.dimensions().clone();
		multipliers = calcMultipliers();
		m = MemoryConstruction.DENSE;
		this.s = StorageConstruction.ARRAY;
		Float64Member tmp = new Float64Member();
		for (long i = 0; i < storage.size(); i++) {
			OctonionRepresentation val = data.get(i);
			tmp.setV(val.r().doubleValue());
			storage.set(i, tmp);
		}
	}
	
	public Float64TensorProductMember(MemoryConstruction m, StorageConstruction s, long[] nd) {
		dims = nd.clone();
		multipliers = calcMultipliers();
		if (s == StorageConstruction.ARRAY) {
			storage = new ArrayStorageFloat64<Float64Member>(numElems(nd), new Float64Member());
		}
		else {
			storage = new FileStorageFloat64<Float64Member>(numElems(nd), new Float64Member());
		}
	}

	@Override
	public void set(Float64TensorProductMember other) {
		if (this == other) return;
		dims = other.dims.clone();
		multipliers = other.multipliers.clone();
		storage = other.storage.duplicate();
		m = other.m;
		s = other.s;
	}
	
	@Override
	public void get(Float64TensorProductMember other) {
		if (this == other) return;
		other.dims = dims.clone();
		other.multipliers = multipliers.clone();
		other.storage = storage.duplicate();
		other.m = m;
		other.s = s;
	}

	@Override
	public void init(long[] newDims) {
		dims = newDims.clone();
		multipliers = calcMultipliers();
		long newCount = numElems(newDims);
		if (newCount != storage.size()) {
			if (s == StorageConstruction.ARRAY) {
				storage = new ArrayStorageFloat64<Float64Member>(newCount, new Float64Member());
			}
			else {
				storage = new FileStorageFloat64<Float64Member>(newCount, new Float64Member());
			}
		}
		else {
			for (long i = 0; i < storage.size(); i++) {
				storage.set(i, ZERO);
			}
		}
	}
	
	public long numElems() {
		return storage.size();
	}
	
	void v(long index, Float64Member value) {
		if (index < 0 || index >= storage.size())
			throw new IllegalArgumentException("invald index in tensor member");
		storage.get(index, value);
	}
	
	@Override
	public void v(IntegerIndex index, Float64Member value) {
		long idx = indexToLong(index);
		storage.get(idx, value);
	}
	
	void setV(long index, Float64Member value) {
		if (index < 0 || index >= storage.size())
			throw new IllegalArgumentException("invald index in tensor member");
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, Float64Member value) {
		long idx = indexToLong(index);
		storage.set(idx, value);
	}
	
	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		Float64Member tmp = new Float64Member();
		IntegerIndex index = new IntegerIndex(this.dims.length);
		// [2,2,2] dims
		// [0,0,0]  [[[num
		// [1,0,0]  [[[num,num
		// [0,1,0]  [[[num,num][num
		// [1,1,0]  [[[num,num][num,num
		// [0,0,1]  [[[num,num][num,num]][[num
		// [1,0,1]  [[[num,num][num,num]][[num,num
		// [0,1,1]  [[[num,num][num,num]][[num,num][num
		// [1,1,1]  [[[num,num][num,num]][[num,num][num,num]]]
		for (long i = 0; i < storage.size(); i++) {
			storage.get(i, tmp);
			longToIntegerIndex(i, index);
			int j = 0;
			while (j < index.numDimensions() && index.get(j++) == 0)
				builder.append('[');
			if (index.get(0) != 0)
				builder.append(',');
			builder.append(tmp.v());
			j = 0;
			while (j < index.numDimensions() && index.get(j) == (dims[j++]-1))
				builder.append(']');
		}
		return builder.toString();
	}

	private long[] calcMultipliers() {
		if (dims.length == 0) return new long[0];
		long[] result = new long[dims.length-1];
		long mult = 1;
		for (int i = 0; i < result.length; i++) {
			result[i] = mult;
			mult *= dims[i];
		}
		return result;
	}
	
	private long numElems(long[] dims) {
		if (dims.length == 0) return 0;
		long count = 1;
		for (long d : dims) {
			count *= d;
		}
		return count;
	}

	/*
	 * dims = [4,5,6]
	 * idx = [1,2,3]
	 * long = 3*5*4 + 2*4 + 1;
	 */
	private long indexToLong(IntegerIndex idx) {
		if (idx.numDimensions() == 0) return 0;
		if ((idx.numDimensions() >= dims.length) && indexOob(idx, 0))
			throw new IllegalArgumentException("index out of bounds");
		long index = 0;
		long mult = 1;
		for (int i = 0; i < dims.length; i++) {
			index += mult * idx.get(i);
			mult *= dims[i];
		}
		return index;
	}

	private void longToIntegerIndex(long idx, IntegerIndex result) {
		if (idx < 0)
			throw new IllegalArgumentException("negative index in tensor addressing");
		if (idx >= storage.size())
			throw new IllegalArgumentException("index beyond end of tensor storage");
		if (result.numDimensions() < this.dims.length)
			throw new IllegalArgumentException("mismatched dims in tensor member");
		for (int i = dims.length; i < result.numDimensions(); i++) {
			result.set(i,0);
		}
		for (int i = dims.length-1; i >= 0; i--) {
			result.set(i, idx / multipliers[i]);
			idx = idx % multipliers[i];
		}
	}

	private boolean indexOob(IntegerIndex idx, int component) {
		if (component < 0)
			throw new IllegalArgumentException("negative component specified in indexOob");
		if (component > 0) return true;
		for (int i = 0; i < dims.length; i++) {
			final long index = idx.get(i);
			if (index < 0)
				throw new IllegalArgumentException("negative index in indexOob");
			if (index >= dims[i]) return true;
		}
		for (int i = dims.length; i < idx.numDimensions(); i++) {
			final long index = idx.get(i);
			if (index < 0)
				throw new IllegalArgumentException("negative index in indexOob");
			if (index > 0) return true;
		}
		return false;
	}
	
	@Override
	public int numDimensions() {
		return dims.length;
	}

	@Override
	public void reshape(long[] dims) {
		// TODO
		throw new IllegalArgumentException("to implement");
	}

	@Override
	public long dimension(int d) {
		if (d < 0)
			throw new IllegalArgumentException("can't query negative dimension");
		if (d >= dims.length) return 1;
		return dims[d];
	}
	
	private static ThreadLocal<Float64Member> tmpFloat =
			new ThreadLocal<Float64Member>()
	{
		protected Float64Member initialValue() {
			return new Float64Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.DOUBLE;
	}

	@Override
	public int componentCount() {
		return 1;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		tmpFloat.get().setV(v);
		setV(index, tmpFloat.get());
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		tmpFloat.get().setV(v);
		setV(index, tmpFloat.get());
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		tmpFloat.get().setV(v);
		setV(index, tmpFloat.get());
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		tmpFloat.get().setV(v);
		setV(index, tmpFloat.get());
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		tmpFloat.get().setV(v);
		setV(index, tmpFloat.get());
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		tmpFloat.get().setV(v);
		setV(index, tmpFloat.get());
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		tmpFloat.get().setV(v.doubleValue());
		setV(index, tmpFloat.get());
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		tmpFloat.get().setV(v.doubleValue());
		setV(index, tmpFloat.get());
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (indexOob(index, component)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			tmpFloat.get().setV(v);
			setV(index, tmpFloat.get());
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (indexOob(index, component)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			tmpFloat.get().setV(v);
			setV(index, tmpFloat.get());
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (indexOob(index, component)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			tmpFloat.get().setV(v);
			setV(index, tmpFloat.get());
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (indexOob(index, component)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			tmpFloat.get().setV(v);
			setV(index, tmpFloat.get());
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (indexOob(index, component)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			tmpFloat.get().setV(v);
			setV(index, tmpFloat.get());
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (indexOob(index, component)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			tmpFloat.get().setV(v);
			setV(index, tmpFloat.get());
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (indexOob(index, component)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			tmpFloat.get().setV(v.doubleValue());
			setV(index, tmpFloat.get());
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (indexOob(index, component)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			tmpFloat.get().setV(v.doubleValue());
			setV(index, tmpFloat.get());
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			v(index, tmpFloat.get());
			return (byte) tmpFloat.get().v();
		}
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			v(index, tmpFloat.get());
			return (short) tmpFloat.get().v();
		}
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			v(index, tmpFloat.get());
			return (int) tmpFloat.get().v();
		}
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			v(index, tmpFloat.get());
			return (long) tmpFloat.get().v();
		}
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			v(index, tmpFloat.get());
			return (float) tmpFloat.get().v();
		}
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			v(index, tmpFloat.get());
			return tmpFloat.get().v();
		}
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			v(index, tmpFloat.get());
			return BigInteger.valueOf((long) tmpFloat.get().v());
		}
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			v(index, tmpFloat.get());
			return BigDecimal.valueOf(tmpFloat.get().v());
		}
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			v(index, tmpFloat.get());
			return (byte) tmpFloat.get().v();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			v(index, tmpFloat.get());
			return (short) tmpFloat.get().v();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			v(index, tmpFloat.get());
			return (int) tmpFloat.get().v();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			v(index, tmpFloat.get());
			return (long) tmpFloat.get().v();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			v(index, tmpFloat.get());
			return (float) tmpFloat.get().v();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			v(index, tmpFloat.get());
			return tmpFloat.get().v();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return BigInteger.ZERO;
		}
		else {
			v(index, tmpFloat.get());
			return BigInteger.valueOf((long) tmpFloat.get().v());
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return BigDecimal.ZERO;
		}
		else {
			v(index, tmpFloat.get());
			return BigDecimal.valueOf(tmpFloat.get().v());
		}
	}

	@Override
	public void primitiveInit() {
		for (long i = 0; i < storage.size(); i++)
			storage.set(i, ZERO);
	}
}
