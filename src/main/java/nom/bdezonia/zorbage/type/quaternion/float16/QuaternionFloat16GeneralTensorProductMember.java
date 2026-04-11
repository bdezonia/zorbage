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
package nom.bdezonia.zorbage.type.quaternion.float16;

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
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.SetFromShorts;
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
public final class QuaternionFloat16GeneralTensorProductMember
	implements
		TensorMember<QuaternionFloat16Member>,
		Gettable<QuaternionFloat16GeneralTensorProductMember>,
		Settable<QuaternionFloat16GeneralTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<QuaternionFloat16Member>,
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
		GetAlgebra<QuaternionFloat16GeneralTensorProduct, QuaternionFloat16GeneralTensorProductMember>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		NanIncludedType,
		SignedType,
		TensorType,
		UnityIncludedType,
		ZeroIncludedType
{
	private static final QuaternionFloat16Member ZERO = new QuaternionFloat16Member();

	private IndexType[] indexTypes;
	private long[] axisLengths;
	private long[] strides;
	private IndexedDataSource<QuaternionFloat16Member> storage;
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
	
	public QuaternionFloat16GeneralTensorProductMember() {

		indexTypes = new IndexType[0];
		axisLengths = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new QuaternionFloat16Member(), 1); // one scalar
		strides = IndexUtils.calcMultipliers(axisLengths);
	}

	public QuaternionFloat16GeneralTensorProductMember(IndexType[] indices, long[] sizes) {
		
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
		storage = Storage.allocate(s, new QuaternionFloat16Member(), storageElems);
		strides = IndexUtils.calcMultipliers(axisLengths);
	}
	
	public QuaternionFloat16GeneralTensorProductMember(IndexType[] indices, long[] sizes, double... vals) {
		this(indices, sizes);
		setFromDoubles(vals);
	}

	public QuaternionFloat16GeneralTensorProductMember(QuaternionFloat16GeneralTensorProductMember other) {
		set(other);
	}

	public QuaternionFloat16GeneralTensorProductMember(String s) {
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
		this.storage = Storage.allocate(this.s, new QuaternionFloat16Member(), storageElems);
		this.strides = IndexUtils.calcMultipliers(axisLengths);
		QuaternionFloat16Member value = new QuaternionFloat16Member();
		if (rank() == 0) {
			OctonionRepresentation val;
			if (data.size() == 0)
				val = new OctonionRepresentation();
			else
				val = data.get(0);
			value.setR(val.r().floatValue());
			value.setI(val.i().floatValue());
			value.setJ(val.j().floatValue());
			value.setK(val.k().floatValue());
			storage.set(0, value);
		}
		else {
			long i = 0;
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(tmpDims);
			IntegerIndex index = new IntegerIndex(axisLengths.length);
			while (iter.hasNext()) {
				iter.next(index);
				OctonionRepresentation val = data.get(i);
				value.setR(val.r().floatValue());
				value.setI(val.i().floatValue());
				value.setJ(val.j().floatValue());
				value.setK(val.k().floatValue());
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
	public void set(QuaternionFloat16GeneralTensorProductMember other) {
		if (this == other) return;
		indexTypes = other.indexTypes.clone();
		axisLengths = other.axisLengths.clone();
		strides = other.strides.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(QuaternionFloat16GeneralTensorProductMember other) {
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
			storage = Storage.allocate(s, new QuaternionFloat16Member(), storageElems);
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

	void v(long index, QuaternionFloat16Member value) {
		storage.get(index, value);
	}
	
	@Override
	public void getV(IntegerIndex index, QuaternionFloat16Member value) {
		long idx = IndexUtils.safeIndexToLong(axisLengths, index);
		storage.get(idx, value);
	}
	
	void setV(long index, QuaternionFloat16Member value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, QuaternionFloat16Member value) {
		long idx = IndexUtils.safeIndexToLong(axisLengths, index);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		QuaternionFloat16Member value = new QuaternionFloat16Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = BigDecimal.valueOf(value.r());
			BigDecimal im = BigDecimal.valueOf(value.i());
			BigDecimal j = BigDecimal.valueOf(value.j());
			BigDecimal k = BigDecimal.valueOf(value.k());
			OctonionRepresentation o = values.get(i);
			o.setR(re);
			o.setI(im);
			o.setJ(j);
			o.setK(k);
			o.setL(BigDecimal.ZERO);
			o.setI0(BigDecimal.ZERO);
			o.setJ0(BigDecimal.ZERO);
			o.setK0(BigDecimal.ZERO);
		}
		rep.setTensor(axisLengths, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		QuaternionFloat16Member value = new QuaternionFloat16Member();
		BigList<OctonionRepresentation> tensor = rep.getTensor();
		long[] dims = rep.getTensorDims();
		// NOTE: there is a hole in our tensor rep. It does not
		//   store variation indices. We will treat all passed
		//   tensors as things like positions. not quantities.
		//   So contravariant indices. Note that with this
		//   convention you can lose data. Take a mixed index
		//   tensor. Then tensor.toRep(rep) followed by
		//   tensor.fromRep(rep). Al the mixed indices are now
		//   contravariant.
		IndexType[] indices = new IndexType[dims.length];
		for (int i = 0; i < dims.length; i++) {
			indices[i] = IndexType.CONTRAVARIANT;
		}
		init(dims, indices);
		long tensorSize = tensor.size();
		for (long i = 0; i < tensorSize; i++) {
			OctonionRepresentation o = tensor.get(i);
			value.setR(o.r().floatValue());
			value.setI(o.i().floatValue());
			storage.set(i, value);
		}
	}

	private void appendTensor(StringBuilder sb, QuaternionFloat16Member tmp, IntegerIndex index, int axis) {

		if (axis == rank()) {
	        getV(index, tmp);
	        sb.append("{");
	        sb.append(tmp.r());
	        sb.append(",");
	        sb.append(tmp.i());
	        sb.append(",");
	        sb.append(tmp.j());
	        sb.append(",");
	        sb.append(tmp.k());
	        sb.append("}");
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
	    QuaternionFloat16Member tmp = new QuaternionFloat16Member();

	    if (rank() == 0) {
	    	storage.get(0, tmp);
	        sb.append("{");
	        sb.append(tmp.r());
	        sb.append(",");
	        sb.append(tmp.i());
	        sb.append(",");
	        sb.append(tmp.j());
	        sb.append(",");
	        sb.append(tmp.k());
	        sb.append("}");
	        return sb.toString();
	    }

	    IntegerIndex idx = new IntegerIndex(rank());
	    appendTensor(sb, tmp, idx, 0);
	    return sb.toString();
	}

	private static final ThreadLocal<QuaternionFloat16Member> tmpQuat =
			new ThreadLocal<QuaternionFloat16Member>()
	{
		protected QuaternionFloat16Member initialValue() {
			return new QuaternionFloat16Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.FLOAT;
	}

	@Override
	public int componentCount() {
		return 4;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR((float) v);
		else if (component == 1)
			tmp.setI((float) v);
		else if (component == 2)
			tmp.setJ((float) v);
		else if (component == 3)
			tmp.setK((float) v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v.floatValue());
		else if (component == 1)
			tmp.setI(v.floatValue());
		else if (component == 2)
			tmp.setJ(v.floatValue());
		else if (component == 3)
			tmp.setK(v.floatValue());
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v.floatValue());
		else if (component == 1)
			tmp.setI(v.floatValue());
		else if (component == 2)
			tmp.setJ(v.floatValue());
		else if (component == 3)
			tmp.setK(v.floatValue());
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
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
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
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
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
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
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
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
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
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
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
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR((float) v);
			else if (component == 1)
				tmp.setI((float) v);
			else if (component == 2)
				tmp.setJ((float) v);
			else if (component == 3)
				tmp.setK((float) v);
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
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v.floatValue());
			else if (component == 1)
				tmp.setI(v.floatValue());
			else if (component == 2)
				tmp.setJ(v.floatValue());
			else if (component == 3)
				tmp.setK(v.floatValue());
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
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v.floatValue());
			else if (component == 1)
				tmp.setI(v.floatValue());
			else if (component == 2)
				tmp.setJ(v.floatValue());
			else if (component == 3)
				tmp.setK(v.floatValue());
			setV(index, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0) {
			return (byte) tmp.r();
		}
		else if (component == 1) {
			return (byte) tmp.i();
		}
		else if (component == 2) {
			return (byte) tmp.j();
		}
		else if (component == 3) {
			return (byte) tmp.k();
		}
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0) {
			return (short) tmp.r();
		}
		else if (component == 1) {
			return (short) tmp.i();
		}
		else if (component == 2) {
			return (short) tmp.j();
		}
		else if (component == 3) {
			return (short) tmp.k();
		}
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0) {
			return (int) tmp.r();
		}
		else if (component == 1) {
			return (int) tmp.i();
		}
		else if (component == 2) {
			return (int) tmp.j();
		}
		else if (component == 3) {
			return (int) tmp.k();
		}
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0) {
			return (long) tmp.r();
		}
		else if (component == 1) {
			return (long) tmp.i();
		}
		else if (component == 2) {
			return (long) tmp.j();
		}
		else if (component == 3) {
			return (long) tmp.k();
		}
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0) {
			return tmp.r();
		}
		else if (component == 1) {
			return tmp.i();
		}
		else if (component == 2) {
			return tmp.j();
		}
		else if (component == 3) {
			return tmp.k();
		}
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0) {
			return tmp.r();
		}
		else if (component == 1) {
			return tmp.i();
		}
		else if (component == 2) {
			return tmp.j();
		}
		else if (component == 3) {
			return tmp.k();
		}
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0) {
			return BigDecimal.valueOf(tmp.r()).toBigInteger();
		}
		else if (component == 1) {
			return BigDecimal.valueOf(tmp.i()).toBigInteger();
		}
		else if (component == 2) {
			return BigDecimal.valueOf(tmp.j()).toBigInteger();
		}
		else if (component == 3) {
			return BigDecimal.valueOf(tmp.k()).toBigInteger();
		}
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionFloat16Member tmp = tmpQuat.get();
		getV(index, tmp);
		if (component == 0) {
			return BigDecimal.valueOf(tmp.r());
		}
		else if (component == 1) {
			return BigDecimal.valueOf(tmp.i());
		}
		else if (component == 2) {
			return BigDecimal.valueOf(tmp.j());
		}
		else if (component == 3) {
			return BigDecimal.valueOf(tmp.k());
		}
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0) {
				return (byte) tmp.r();
			}
			else if (component == 1) {
				return (byte) tmp.i();
			}
			else if (component == 2) {
				return (byte) tmp.j();
			}
			else if (component == 3) {
				return (byte) tmp.k();
			}
			return 0;
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0) {
				return (short) tmp.r();
			}
			else if (component == 1) {
				return (short) tmp.i();
			}
			else if (component == 2) {
				return (short) tmp.j();
			}
			else if (component == 3) {
				return (short) tmp.k();
			}
			return 0;
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0) {
				return (int) tmp.r();
			}
			else if (component == 1) {
				return (int) tmp.i();
			}
			else if (component == 2) {
				return (int) tmp.j();
			}
			else if (component == 3) {
				return (int) tmp.k();
			}
			return 0;
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0) {
				return (long) tmp.r();
			}
			else if (component == 1) {
				return (long) tmp.i();
			}
			else if (component == 2) {
				return (long) tmp.j();
			}
			else if (component == 3) {
				return (long) tmp.k();
			}
			return 0;
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0) {
				return tmp.r();
			}
			else if (component == 1) {
				return tmp.i();
			}
			else if (component == 2) {
				return tmp.j();
			}
			else if (component == 3) {
				return tmp.k();
			}
			return 0;
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0) {
				return tmp.r();
			}
			else if (component == 1) {
				return tmp.i();
			}
			else if (component == 2) {
				return tmp.j();
			}
			else if (component == 3) {
				return tmp.k();
			}
			return 0;
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return BigInteger.ZERO;
		}
		else {
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0) {
				return BigDecimal.valueOf(tmp.r()).toBigInteger();
			}
			else if (component == 1) {
				return BigDecimal.valueOf(tmp.i()).toBigInteger();
			}
			else if (component == 2) {
				return BigDecimal.valueOf(tmp.j()).toBigInteger();
			}
			else if (component == 3) {
				return BigDecimal.valueOf(tmp.k()).toBigInteger();
			}
			return BigInteger.ZERO;
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return BigDecimal.ZERO;
		}
		else {
			QuaternionFloat16Member tmp = tmpQuat.get();
			getV(index, tmp);
			if (component == 0) {
				return BigDecimal.valueOf(tmp.r());
			}
			else if (component == 1) {
				return BigDecimal.valueOf(tmp.i());
			}
			else if (component == 2) {
				return BigDecimal.valueOf(tmp.j());
			}
			else if (component == 3) {
				return BigDecimal.valueOf(tmp.k());
			}
			return BigDecimal.ZERO;
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<QuaternionFloat16Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		QuaternionFloat16Member tmp = G.QHLF.construct();
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
		if (o instanceof QuaternionFloat16GeneralTensorProductMember) {
			return G.QHLF_TEN.isEqual().call(this, (QuaternionFloat16GeneralTensorProductMember) o);
		}
		return false;
	}

	@Override
	public void setFromBytesExact(byte... vals) {
		setFromBytes(vals);
	}
	
	@Override
	public void setFromBytes(byte... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat16Member value = G.QHLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromShorts(short... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat16Member value = G.QHLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromInts(int... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat16Member value = G.QHLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromLongs(long... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat16Member value = G.QHLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromFloats(float... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat16Member value = G.QHLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDoubles(double... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat16Member value = G.QHLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  (float) vals[i + 0] );
			value.setI(  (float) vals[i + 1] );
			value.setJ(  (float) vals[i + 2] );
			value.setK(  (float) vals[i + 3] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat16Member value = G.QHLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].floatValue() );
			value.setI(  vals[i + 1].floatValue() );
			value.setJ(  vals[i + 2].floatValue() );
			value.setK(  vals[i + 3].floatValue() );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionFloat16Member value = G.QHLF.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].floatValue() );
			value.setI(  vals[i + 1].floatValue() );
			value.setJ(  vals[i + 2].floatValue() );
			value.setK(  vals[i + 3].floatValue() );
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
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat16Member value = G.QHLF.construct();
		byte[] values = new byte[4 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[2*i + 0] = (byte) value.r();
			values[2*i + 1] = (byte) value.i();
			values[2*i + 2] = (byte) value.j();
			values[2*i + 3] = (byte) value.k();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat16Member value = G.QHLF.construct();
		short[] values = new short[4 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[2*i + 0] = (short) value.r();
			values[2*i + 1] = (short) value.i();
			values[2*i + 2] = (short) value.j();
			values[2*i + 3] = (short) value.k();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat16Member value = G.QHLF.construct();
		int[] values = new int[4 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[2*i + 0] = (int) value.r();
			values[2*i + 1] = (int) value.i();
			values[2*i + 2] = (int) value.j();
			values[2*i + 3] = (int) value.k();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat16Member value = G.QHLF.construct();
		long[] values = new long[4 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[2*i + 0] = (long) value.r();
			values[2*i + 1] = (long) value.i();
			values[2*i + 2] = (long) value.j();
			values[2*i + 3] = (long) value.k();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat16Member value = G.QHLF.construct();
		float[] values = new float[4 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[2*i + 0] = value.r();
			values[2*i + 1] = value.i();
			values[2*i + 2] = value.j();
			values[2*i + 3] = value.k();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat16Member value = G.QHLF.construct();
		double[] values = new double[4 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[2*i + 0] = value.r();
			values[2*i + 1] = value.i();
			values[2*i + 2] = value.j();
			values[2*i + 3] = value.k();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat16Member value = G.QHLF.construct();
		BigInteger[] values = new BigInteger[4 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[2*i + 0] = BigDecimal.valueOf(value.r()).toBigInteger();
			values[2*i + 1] = BigDecimal.valueOf(value.i()).toBigInteger();
			values[2*i + 2] = BigDecimal.valueOf(value.j()).toBigInteger();
			values[2*i + 3] = BigDecimal.valueOf(value.k()).toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionFloat16Member value = G.QHLF.construct();
		BigDecimal[] values = new BigDecimal[4 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[2*i + 0] = BigDecimal.valueOf(value.r());
			values[2*i + 1] = BigDecimal.valueOf(value.i());
			values[2*i + 2] = BigDecimal.valueOf(value.j());
			values[2*i + 3] = BigDecimal.valueOf(value.k());
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public QuaternionFloat16GeneralTensorProduct getAlgebra() {

		return G.QHLF_TEN;
	}
	
	private static IndexType[] indices(int size, IndexType value) {
		
		IndexType[] values = new IndexType[size];
		for (int i = 0; i < size; i++) {
			values[i] = value;
		}
		return values;
	}
}
