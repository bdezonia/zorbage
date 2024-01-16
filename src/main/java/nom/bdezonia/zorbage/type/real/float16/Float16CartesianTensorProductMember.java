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
package nom.bdezonia.zorbage.type.real.float16;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetAlgebra;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArray;
import nom.bdezonia.zorbage.algebra.GetAsBigDecimalArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsBigIntegerArray;
import nom.bdezonia.zorbage.algebra.GetAsByteArray;
import nom.bdezonia.zorbage.algebra.GetAsDoubleArray;
import nom.bdezonia.zorbage.algebra.GetAsDoubleArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsFloatArray;
import nom.bdezonia.zorbage.algebra.GetAsFloatArrayExact;
import nom.bdezonia.zorbage.algebra.GetAsIntArray;
import nom.bdezonia.zorbage.algebra.GetAsLongArray;
import nom.bdezonia.zorbage.algebra.GetAsShortArray;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimals;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegers;
import nom.bdezonia.zorbage.algebra.SetFromBytes;
import nom.bdezonia.zorbage.algebra.SetFromBytesExact;
import nom.bdezonia.zorbage.algebra.SetFromDoubles;
import nom.bdezonia.zorbage.algebra.SetFromFloats;
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.SetFromShorts;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.algebra.ThreadAccess;
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.data.IndexUtils;
import nom.bdezonia.zorbage.type.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.misc.Hasher;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.RawData;


// TODO:
//   rank 0 tensor getting and setting 1 value instead of 0
//   upper and lower indices: only if not CartesianTensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float16CartesianTensorProductMember
	implements
		TensorMember<Float16Member>,
		Gettable<Float16CartesianTensorProductMember>,
		Settable<Float16CartesianTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<Float16Member>,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromInts,
		SetFromLongs,
		SetFromFloats,
		SetFromDoubles,
		SetFromBigIntegers,
		SetFromBigDecimals,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsFloatArrayExact,
		GetAsDoubleArray,
		GetAsDoubleArrayExact,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		ThreadAccess,
		GetAlgebra<Float16CartesianTensorProduct, Float16CartesianTensorProductMember>
{
	private static final Float16Member ZERO = new Float16Member();

	private int rank;
	private long dimCount;
	private IndexedDataSource<Float16Member> storage;
	private long[] dims;
	private long[] multipliers;
	private StorageConstruction s;

	// rank() is also numDimensions(). Confusing. TODO - fix
	
	@Override
	public int rank() { return lowerRank() + upperRank(); }
	
	@Override
	public int lowerRank() { return rank; }
	
	@Override
	public int upperRank() { return 0; }
	
	@Override
	public boolean indexIsLower(int index) {
		if (index < 0 || index >= rank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return true;
	}
	
	@Override
	public boolean indexIsUpper(int index) {
		if (index < 0 || index >= rank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return false;
	}

	@Override
	public long dimension() { return dimCount; }

	public Float16CartesianTensorProductMember() {
		rank = 0;
		dimCount = 0;
		dims = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new Float16Member(), 1);
		this.multipliers = IndexUtils.calcMultipliers(dims);
	}

	public Float16CartesianTensorProductMember(int rank, long dimCount) {
		if (rank < 0)
			throw new IllegalArgumentException("bad rank in tensor constructor");
		if (dimCount < 0)
			throw new IllegalArgumentException("bad dimensionality in tensor constructor");
		this.rank = rank;
		this.dimCount = dimCount;
		dims = new long[rank];
		for (int i = 0; i < rank; i++) {
			dims[i] = dimCount;
		}
		long numElems = LongUtils.numElements(this.dims);
		if (numElems == 0) numElems = 1;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new Float16Member(), numElems);
		this.multipliers = IndexUtils.calcMultipliers(dims);
	}
	
	public Float16CartesianTensorProductMember(int rank, long dimCount, float... vals) {
		this(rank, dimCount);
		setFromFloats(vals);
	}

	public Float16CartesianTensorProductMember(Float16CartesianTensorProductMember other) {
		set(other);
	}
	
	public Float16CartesianTensorProductMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.values();
		long[] tmpDims = rep.dimensions().clone();
		this.rank = tmpDims.length;
		if (tmpDims.length == 0) {
			this.dimCount = 1;
		}
		else {
			long d0 = tmpDims[0];
			for (int i = 1; i < tmpDims.length; i++) {
				if (tmpDims[i] != d0)
					throw new IllegalArgumentException("tensors must be the same in all dimensions");
			}
			this.dimCount = d0;
		}
		this.dims = new long[rank];
		for (int i = 0; i < rank; i++) {
			this.dims[i] = dimCount;
		}
		long numElems = LongUtils.numElements(this.dims);
		if (numElems == 0) numElems = 1;
		this.s = StorageConstruction.MEM_ARRAY;
		this.storage = Storage.allocate(this.s, new Float16Member(), numElems);
		this.multipliers = IndexUtils.calcMultipliers(dims);
		Float16Member value = new Float16Member();
		if (numElems == 1) {
			// TODO: does a rank 0 tensor have any values from a parsing?
			OctonionRepresentation val = data.get(0);
			value.setV(val.r().floatValue());
			storage.set(0, value);
		}
		else {
			long i = 0;
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(tmpDims);
			IntegerIndex index = new IntegerIndex(dims.length);
			while (iter.hasNext()) {
				iter.next(index);
				OctonionRepresentation val = data.get(i);
				value.setV(val.r().floatValue());
				long idx = IndexUtils.indexToLong(dims, index);
				storage.set(idx, value);
				i++;
			}
		}
	}
	
	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void set(Float16CartesianTensorProductMember other) {
		if (this == other) return;
		rank = other.rank;
		dimCount = other.dimCount;
		dims = other.dims.clone();
		multipliers = other.multipliers.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(Float16CartesianTensorProductMember other) {
		if (this == other) return;
		other.rank = rank;
		other.dimCount = dimCount;
		other.dims = dims.clone();
		other.multipliers = multipliers.clone();
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public boolean alloc(long[] newDims) {
		boolean theSame = true;
		if (newDims.length != dims.length)
			theSame = false;
		else {
			for (int i = 0; i < newDims.length; i++) {
				if (newDims[i] != dims[i]) {
					theSame = false;
					break;
				}
			}
		}
		if (theSame)
			return false;
		this.rank = newDims.length;
		if (rank == 0) {
			this.dimCount = 1;
		}
		else {
			long d0 = newDims[0];
			for (int i = 1; i < newDims.length; i++) {
				if (newDims[i] != d0)
					throw new IllegalArgumentException("tensors must be the same in all dimensions");
			}
			this.dimCount = d0;
		}
		this.dims = new long[rank];
		for (int i = 0; i < rank; i++) {
			this.dims[i] = dimCount;
		}
		this.multipliers = IndexUtils.calcMultipliers(dims);
		long newCount = LongUtils.numElements(this.dims);
		if (newCount == 0) newCount = 1;
		if (storage == null || newCount != storage.size()) {
			storage = Storage.allocate(s, new Float16Member(), newCount);
			return true;
		}
		return false;
	}
	
	@Override
	public void init(long[] newDims) {
		if (!alloc(newDims)) {
			long storageSize = storage.size();
			for (long i = 0; i < storageSize; i++) {
				storage.set(i, ZERO);
			}
		}
	}
	
	public long numElems() {
		return storage.size();
	}
	
	void v(long index, Float16Member value) {
		storage.get(index, value);
	}
	
	@Override
	public void getV(IntegerIndex index, Float16Member value) {
		long idx = IndexUtils.safeIndexToLong(dims, index);
		storage.get(idx, value);
	}
	
	void setV(long index, Float16Member value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, Float16Member value) {
		long idx = IndexUtils.safeIndexToLong(dims, index);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		Float16Member value = new Float16Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = BigDecimal.valueOf(value.v());
			OctonionRepresentation o = values.get(i);
			o.setR(re);
			o.setI(BigDecimal.ZERO);
			o.setJ(BigDecimal.ZERO);
			o.setK(BigDecimal.ZERO);
			o.setL(BigDecimal.ZERO);
			o.setI0(BigDecimal.ZERO);
			o.setJ0(BigDecimal.ZERO);
			o.setK0(BigDecimal.ZERO);
		}
		rep.setTensor(dims, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		Float16Member value = new Float16Member();
		BigList<OctonionRepresentation> tensor = rep.getTensor();
		init(rep.getTensorDims());
		long tensorSize = tensor.size();
		for (long i = 0; i < tensorSize; i++) {
			OctonionRepresentation o = tensor.get(i);
			value.setV(o.r().floatValue());
			storage.set(i, value);
		}
	}

	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		Float16Member tmp = new Float16Member();
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
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, tmp);
			IndexUtils.longToIntegerIndex(multipliers, dims.length, storageSize, i, index);
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

	@Override
	public int numDimensions() {
		return dims.length;
	}

	@Override
	public void reshape(long[] dims) {
		// the idea here is to change dims and preserve values that
		// overlap old dims / new dims.
		if (Arrays.equals(this.dims, dims)) return;
		// the previous line makes sure that tensor add(a,a,a) will work
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
	
	private static final ThreadLocal<Float16Member> tmpFloat =
			new ThreadLocal<Float16Member>()
	{
		protected Float16Member initialValue() {
			return new Float16Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.FLOAT;
	}

	@Override
	public int componentCount() {
		return 1;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		Float16Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		Float16Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		Float16Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		Float16Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		Float16Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		Float16Member tmp = tmpFloat.get();
		tmp.setV((float) v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		Float16Member tmp = tmpFloat.get();
		tmp.setV(v.floatValue());
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		Float16Member tmp = tmpFloat.get();
		tmp.setV(v.floatValue());
		setV(index, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float16Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float16Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float16Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float16Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float16Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float16Member tmp = tmpFloat.get();
			tmp.setV((float) v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float16Member tmp = tmpFloat.get();
			tmp.setV(v.floatValue());
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float16Member tmp = tmpFloat.get();
			tmp.setV(v.floatValue());
			setV(index, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (byte) tmp.v();
		}
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (short) tmp.v();
		}
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (int) tmp.v();
		}
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (long) tmp.v();
		}
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return tmp.v();
		}
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return tmp.v();
		}
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return BigInteger.valueOf((long) tmp.v());
		}
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return BigDecimal.valueOf(tmp.v());
		}
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (byte) tmp.v();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (short) tmp.v();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (int) tmp.v();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (long) tmp.v();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return tmp.v();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return tmp.v();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return BigInteger.ZERO;
		}
		else {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return BigInteger.valueOf((long) tmp.v());
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return BigDecimal.ZERO;
		}
		else {
			Float16Member tmp = tmpFloat.get();
			getV(index, tmp);
			return BigDecimal.valueOf(tmp.v());
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<Float16Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		Float16Member tmp = G.HLF.construct();
		long len = dimension(0);
		int v = 1;
		v = Hasher.PRIME * v + Hasher.hashCode(len);
		if (len > 0) {
			storage.get(0, tmp);
			v = Hasher.PRIME * v + tmp.hashCode();
		}
		return v;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Float16CartesianTensorProductMember) {
			return G.HLF_TEN.isEqual().call(this, (Float16CartesianTensorProductMember) o);
		}
		return false;
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}
	
	@Override
	public void setFromBytes(byte... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float16Member value = G.HLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromShorts(short... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float16Member value = G.HLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromInts(int... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float16Member value = G.HLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromLongs(long... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float16Member value = G.HLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromFloats(float... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float16Member value = G.HLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDoubles(double... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float16Member value = G.HLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  (float) vals[i + 0] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float16Member value = G.HLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0].floatValue() );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float16Member value = G.HLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV(  vals[i + 0].floatValue() );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public float[] getAsFloatArrayExact() {
		return getAsFloatArray();
	}
	
	@Override
	public double[] getAsDoubleArrayExact() {
		return getAsDoubleArray();
	}
	
	@Override
	public BigDecimal[] getAsBigDecimalArrayExact() {
		return getAsBigDecimalArray();
	}
	
	@Override
	public byte[] getAsByteArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float16Member value = G.HLF.construct();
		byte[] values = new byte[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = (byte) value.v();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float16Member value = G.HLF.construct();
		short[] values = new short[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = (short) value.v();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float16Member value = G.HLF.construct();
		int[] values = new int[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = (int) value.v();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float16Member value = G.HLF.construct();
		long[] values = new long[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = (long) value.v();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float16Member value = G.HLF.construct();
		float[] values = new float[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = value.v();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float16Member value = G.HLF.construct();
		double[] values = new double[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = value.v();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float16Member value = G.HLF.construct();
		BigInteger[] values = new BigInteger[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = BigInteger.valueOf((long) value.v());
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float16Member value = G.HLF.construct();
		BigDecimal[] values = new BigDecimal[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = BigDecimal.valueOf(value.v());
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public Float16CartesianTensorProduct getAlgebra() {

		return G.HLF_TEN;
	}
}
