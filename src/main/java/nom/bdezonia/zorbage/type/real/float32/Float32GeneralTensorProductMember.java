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
package nom.bdezonia.zorbage.type.real.float32;

import java.math.BigDecimal;
import java.math.BigInteger;

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
import nom.bdezonia.zorbage.algebra.IndexType;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimals;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegers;
import nom.bdezonia.zorbage.algebra.SetFromBytes;
import nom.bdezonia.zorbage.algebra.SetFromBytesExact;
import nom.bdezonia.zorbage.algebra.SetFromDoubles;
import nom.bdezonia.zorbage.algebra.SetFromFloats;
import nom.bdezonia.zorbage.algebra.SetFromFloatsExact;
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.SetFromShorts;
import nom.bdezonia.zorbage.algebra.SetFromShortsExact;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.algebra.ThreadAccess;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.TensorType;
import nom.bdezonia.zorbage.algebra.type.markers.UnityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
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

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class Float32GeneralTensorProductMember
	implements
		TensorMember<Float32Member>,
		Gettable<Float32GeneralTensorProductMember>,
		Settable<Float32GeneralTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<Float32Member>,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromLongs,
		SetFromFloats,
		SetFromFloatsExact,
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
		GetAlgebra<Float32GeneralTensorProduct, Float32GeneralTensorProductMember>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		NanIncludedType,
		SignedType,
		TensorType,
		UnityIncludedType,
		ZeroIncludedType
{
	private static final Float32Member ZERO = new Float32Member();

	private IndexType[] indexTypes;
	private long[] axisLengths;
	private long[] strides;
	private IndexedDataSource<Float32Member> storage;
	private StorageConstruction s;

	// TODO: I hate this but can't figure out the coding I
	//   need to do to get rid of it.
	
	@Override
	public int numDimensions() {

		return rank();
	}

	// TODO: I hate this but can't figure out the coding I
	//   need to do to get rid of it.
	
	@Override
	public long dimension(int d) {

		return axisSize(d);
	}

	public long axisSize(int index) {
		
		return axisLengths[index];
	}
	
	public void shape(long[] sizes) {

		if (sizes.length != this.axisLengths.length)
			throw new IllegalArgumentException("axis count mismatch in axisLengths() method");
		
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = this.axisLengths[i];
		}
	}
	
	@Override
	public int rank() { return indexTypes.length; }
	
	@Override
	public int lowerRank() {
		
		int rank = 0;
		
		for (int i = 0; i < rank(); i++)  {
		
			if (indexIsLower(i)) rank++;
		}
		
		return rank;
	}
	
	@Override
	public int upperRank() {
		
		int rank = 0;
		
		for (int i = 0; i < rank(); i++)  {
		
			if (indexIsUpper(i)) rank++;
		}
		
		return rank;
	}
	
	@Override
	public boolean indexIsLower(int index) {
		
		if (index < 0 || index >= rank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		
		return indexTypes[index] == IndexType.COVARIANT;
	}
	
	@Override
	public boolean indexIsUpper(int index) {

		if (index < 0 || index >= rank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		
		return indexTypes[index] == IndexType.CONTRAVARIANT;
	}

	@Override
	public IndexType indexType(int index) {

		return indexTypes[index];
	}

	@Override
	public void indexTypes(IndexType[] types) {

		if (types.length != rank())
			throw new IllegalArgumentException();
		for (int i = 0; i < types.length; i++)
			types[i] = indexTypes[i];
	}
	
	public long numElements() {
	
		return storage.size();
	}
	
	public Float32GeneralTensorProductMember() {

		indexTypes = new IndexType[0];
		axisLengths = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new Float32Member(), 1); // one scalar
		strides = IndexUtils.calcMultipliers(axisLengths);
	}

	public Float32GeneralTensorProductMember(IndexType[] indices, long[] sizes) {
		
		if (indices.length != sizes.length)
			throw new IllegalArgumentException("bad input to tensor constructor");
		indexTypes = indices.clone();
		axisLengths = sizes.clone();
		long storageElems;
		if (rank() == 0)
			storageElems = 1;
		else
			storageElems = LongUtils.numElements(axisLengths);
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new Float32Member(), storageElems);
		strides = IndexUtils.calcMultipliers(axisLengths);
	}
	
	public Float32GeneralTensorProductMember(IndexType[] indices, long[] sizes, double... vals) {
		this(indices, sizes);
		setFromDoubles(vals);
	}

	public Float32GeneralTensorProductMember(Float32GeneralTensorProductMember other) {
		set(other);
	}

	public Float32GeneralTensorProductMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.values();
		long[] tmpDims = rep.dimensions().clone();
		this.axisLengths = tmpDims;
		this.indexTypes = indices(tmpDims.length, IndexType.CONTRAVARIANT);
		long storageElems;
		if (rank() == 0)
			storageElems = 1;
		else
			storageElems = LongUtils.numElements(axisLengths);
		this.s = StorageConstruction.MEM_ARRAY;
		this.storage = Storage.allocate(this.s, new Float32Member(), storageElems);
		this.strides = IndexUtils.calcMultipliers(axisLengths);
		Float32Member value = new Float32Member();
		if (rank() == 0) {
			OctonionRepresentation val;
			if (data.size() == 0)
				val = new OctonionRepresentation();
			else
				val = data.get(0);
			value.setV(val.r().floatValue());
			storage.set(0, value);
		}
		else {
			long i = 0;
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(tmpDims);
			IntegerIndex index = new IntegerIndex(axisLengths.length);
			while (iter.hasNext()) {
				iter.next(index);
				OctonionRepresentation val = data.get(i);
				value.setV(val.r().floatValue());
				long idx = IndexUtils.indexToLong(axisLengths, index);
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
	public void set(Float32GeneralTensorProductMember other) {
		if (this == other) return;
		indexTypes = other.indexTypes.clone();
		axisLengths = other.axisLengths.clone();
		strides = other.strides.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(Float32GeneralTensorProductMember other) {
		if (this == other) return;
		other.indexTypes = indexTypes.clone();
		other.axisLengths = axisLengths.clone();
		other.strides = strides.clone();
		other.storage = storage.duplicate();
		other.s = s;
	}

	@Override
	public boolean alloc(long[] newDims, IndexType[] indexTypes) {
		if (newDims.length != indexTypes.length)
			throw new IllegalArgumentException("trying to allocate a "+newDims.length+" rank tensor with "+indexTypes.length+"co/contra/variant designators");
		this.indexTypes = indexTypes.clone();
		boolean theSame = true;
		if (newDims.length != axisLengths.length)
			theSame = false;
		else {
			for (int i = 0; i < newDims.length; i++) {
				if (newDims[i] != axisLengths[i]) {
					theSame = false;
					break;
				}
			}
		}
		if (theSame)
			return false;
		this.axisLengths = newDims.clone();
		this.strides = IndexUtils.calcMultipliers(axisLengths);
		long storageElems;
		if (rank() == 0)
			storageElems = 1;
		else
			storageElems = LongUtils.numElements(axisLengths);
		if (storage == null || storageElems != storage.size()) {
			storage = Storage.allocate(s, new Float32Member(), storageElems);
			return true;
		}
		return false;
	}

	@Override
	public boolean alloc(long[] newDims) {
		return alloc(newDims, indexTypes);
	}
	
	@Override
	public void init(long[] newDims, IndexType[] indexTypes) {
		if (!alloc(newDims, indexTypes)) {
			long storageSize = storage.size();
			for (long i = 0; i < storageSize; i++) {
				storage.set(i, ZERO);
			}
		}
	}

	@Override
	public void init(long[] newDims) {
		init(newDims, indexTypes);
	}

	void v(long index, Float32Member value) {
		storage.get(index, value);
	}
	
	@Override
	public void getV(IntegerIndex index, Float32Member value) {
		long idx = IndexUtils.safeIndexToLong(axisLengths, index);
		storage.get(idx, value);
	}
	
	void setV(long index, Float32Member value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, Float32Member value) {
		long idx = IndexUtils.safeIndexToLong(axisLengths, index);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		Float32Member value = new Float32Member();
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
		rep.setTensor(axisLengths, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		Float32Member value = new Float32Member();
		BigList<OctonionRepresentation> tensor = rep.getTensor();
		init(rep.getTensorDims());
		long tensorSize = tensor.size();
		for (long i = 0; i < tensorSize; i++) {
			OctonionRepresentation o = tensor.get(i);
			value.setV(o.r().floatValue());
			storage.set(i, value);
		}
	}

	private void appendTensor(StringBuilder sb, Float32Member tmp, IntegerIndex index, int axis) {

		if (axis == rank()) {
	        getV(index, tmp);
	        sb.append(tmp.v());
	        return;
	    }

	    sb.append('[');
	    long n = axisSize(axis);
	    for (long i = 0; i < n; i++) {
	        if (i > 0) sb.append(',');
	        index.set(axis, i);
	        appendTensor(sb, tmp, index, axis + 1);
	    }
	    sb.append(']');
	}
	
	// iterate values/indices and write numbers, brackets, and commas in correct order
	// [2,2,2] dims
	// [0,0,0]  [[[num
	// [1,0,0]  [[[num,num
	// [0,1,0]  [[[num,num][num
	// [1,1,0]  [[[num,num][num,num
	// [0,0,1]  [[[num,num][num,num]][[num
	// [1,0,1]  [[[num,num][num,num]][[num,num
	// [0,1,1]  [[[num,num][num,num]][[num,num][num
	// [1,1,1]  [[[num,num][num,num]][[num,num][num,num]]]

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
	    Float32Member tmp = new Float32Member();

	    if (rank() == 0) {
	    	storage.get(0, tmp);
	        sb.append(tmp.v());
	        return sb.toString();
	    }

	    IntegerIndex idx = new IntegerIndex(rank());
	    appendTensor(sb, tmp, idx, 0);
	    return sb.toString();
	}

	private static final ThreadLocal<Float32Member> tmpFloat =
			new ThreadLocal<Float32Member>()
	{
		protected Float32Member initialValue() {
			return new Float32Member();
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
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		Float32Member tmp = tmpFloat.get();
		tmp.setV((float) v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v.floatValue());
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		Float32Member tmp = tmpFloat.get();
		tmp.setV(v.floatValue());
		setV(index, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float32Member tmp = tmpFloat.get();
			tmp.setV((float) v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float32Member tmp = tmpFloat.get();
			tmp.setV(v.floatValue());
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			Float32Member tmp = tmpFloat.get();
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
			Float32Member tmp = tmpFloat.get();
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
			Float32Member tmp = tmpFloat.get();
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
			Float32Member tmp = tmpFloat.get();
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
			Float32Member tmp = tmpFloat.get();
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
			Float32Member tmp = tmpFloat.get();
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
			Float32Member tmp = tmpFloat.get();
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
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return BigDecimal.valueOf(tmp.v()).toBigInteger();
		}
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return BigDecimal.valueOf(tmp.v());
		}
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (byte) tmp.v();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (short) tmp.v();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (int) tmp.v();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return (long) tmp.v();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return tmp.v();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return tmp.v();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return BigInteger.ZERO;
		}
		else {
			Float32Member tmp = tmpFloat.get();
			getV(index, tmp);
			return BigDecimal.valueOf(tmp.v()).toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return BigDecimal.ZERO;
		}
		else {
			Float32Member tmp = tmpFloat.get();
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
	public IndexedDataSource<Float32Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		Float32Member tmp = G.FLT.construct();
		long len;
		if (rank() == 0)
			len = 1;
		else
			len = axisSize(0);
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
		if (o instanceof Float32GeneralTensorProductMember) {
			return G.FLT_TEN.isEqual().call(this, (Float32GeneralTensorProductMember) o);
		}
		return false;
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}
	
	@Override
	public void setFromShortsExact(short... vals) {
		setFromShorts(vals);
	}
	
	@Override
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}
	
	@Override
	public void setFromBytes(byte... vals) {
		int componentCount = 1;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setV( (float) vals[i + 0] );
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
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
		Float32Member value = G.FLT.construct();
		BigInteger[] values = new BigInteger[1 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[i] = BigDecimal.valueOf(value.v()).toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 1))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		Float32Member value = G.FLT.construct();
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
	public Float32GeneralTensorProduct getAlgebra() {

		return G.FLT_TEN;
	}
	
	private static IndexType[] indices(int size, IndexType value) {
		
		IndexType[] values = new IndexType[size];
		for (int i = 0; i < size; i++) {
			values[i] = value;
		}
		return values;
	}
}
