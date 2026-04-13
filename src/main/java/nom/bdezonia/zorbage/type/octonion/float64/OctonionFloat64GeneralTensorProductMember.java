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
package nom.bdezonia.zorbage.type.octonion.float64;

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
import nom.bdezonia.zorbage.algebra.SetFromDoublesExact;
import nom.bdezonia.zorbage.algebra.SetFromFloats;
import nom.bdezonia.zorbage.algebra.SetFromFloatsExact;
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromIntsExact;
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
public final class OctonionFloat64GeneralTensorProductMember
	implements
		TensorMember<OctonionFloat64Member>,
		Gettable<OctonionFloat64GeneralTensorProductMember>,
		Settable<OctonionFloat64GeneralTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<OctonionFloat64Member>,
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
		ThreadAccess,
		GetAlgebra<OctonionFloat64GeneralTensorProduct, OctonionFloat64GeneralTensorProductMember>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		NanIncludedType,
		SignedType,
		TensorType,
		UnityIncludedType,
		ZeroIncludedType
{
	private static final OctonionFloat64Member ZERO = new OctonionFloat64Member();

	private IndexType[] indexTypes;
	private long[] axisLengths;
	private long[] strides;
	private IndexedDataSource<OctonionFloat64Member> storage;
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
	
	public OctonionFloat64GeneralTensorProductMember() {

		indexTypes = new IndexType[0];
		axisLengths = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat64Member(), 1); // one scalar
		strides = IndexUtils.calcMultipliers(axisLengths);
	}

	public OctonionFloat64GeneralTensorProductMember(IndexType[] indices, long[] sizes) {
		
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
		storage = Storage.allocate(s, new OctonionFloat64Member(), storageElems);
		strides = IndexUtils.calcMultipliers(axisLengths);
	}
	
	public OctonionFloat64GeneralTensorProductMember(IndexType[] indices, long[] sizes, double... vals) {
		this(indices, sizes);
		setFromDoubles(vals);
	}

	public OctonionFloat64GeneralTensorProductMember(OctonionFloat64GeneralTensorProductMember other) {
		set(other);
	}

	public OctonionFloat64GeneralTensorProductMember(String s) {
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
		this.storage = Storage.allocate(this.s, new OctonionFloat64Member(), storageElems);
		this.strides = IndexUtils.calcMultipliers(axisLengths);
		OctonionFloat64Member value = new OctonionFloat64Member();
		if (rank() == 0) {
			OctonionRepresentation val;
			if (data.size() == 0)
				val = new OctonionRepresentation();
			else
				val = data.get(0);
			value.setR(val.r().doubleValue());
			value.setI(val.i().doubleValue());
			value.setJ(val.j().doubleValue());
			value.setK(val.k().doubleValue());
			value.setL(val.l().doubleValue());
			value.setI0(val.i0().doubleValue());
			value.setJ0(val.j0().doubleValue());
			value.setK0(val.k0().doubleValue());
			storage.set(0, value);
		}
		else {
			long i = 0;
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(tmpDims);
			IntegerIndex index = new IntegerIndex(axisLengths.length);
			while (iter.hasNext()) {
				iter.next(index);
				OctonionRepresentation val = data.get(i);
				value.setR(val.r().doubleValue());
				value.setI(val.i().doubleValue());
				value.setJ(val.j().doubleValue());
				value.setK(val.k().doubleValue());
				value.setL(val.l().doubleValue());
				value.setI0(val.i0().doubleValue());
				value.setJ0(val.j0().doubleValue());
				value.setK0(val.k0().doubleValue());
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
	public void set(OctonionFloat64GeneralTensorProductMember other) {
		if (this == other) return;
		indexTypes = other.indexTypes.clone();
		axisLengths = other.axisLengths.clone();
		strides = other.strides.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(OctonionFloat64GeneralTensorProductMember other) {
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
			storage = Storage.allocate(s, new OctonionFloat64Member(), storageElems);
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

	void v(long index, OctonionFloat64Member value) {
		storage.get(index, value);
	}
	
	@Override
	public void getV(IntegerIndex index, OctonionFloat64Member value) {
		long idx = IndexUtils.safeIndexToLong(axisLengths, index);
		storage.get(idx, value);
	}
	
	void setV(long index, OctonionFloat64Member value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, OctonionFloat64Member value) {
		long idx = IndexUtils.safeIndexToLong(axisLengths, index);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		OctonionFloat64Member value = new OctonionFloat64Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = BigDecimal.valueOf(value.r());
			BigDecimal im = BigDecimal.valueOf(value.i());
			BigDecimal j = BigDecimal.valueOf(value.j());
			BigDecimal k = BigDecimal.valueOf(value.k());
			BigDecimal l = BigDecimal.valueOf(value.l());
			BigDecimal i0 = BigDecimal.valueOf(value.i0());
			BigDecimal j0 = BigDecimal.valueOf(value.j0());
			BigDecimal k0 = BigDecimal.valueOf(value.k0());
			OctonionRepresentation o = values.get(i);
			o.setR(re);
			o.setI(im);
			o.setJ(j);
			o.setK(k);
			o.setL(l);
			o.setI0(i0);
			o.setJ0(j0);
			o.setK0(k0);
		}
		rep.setTensor(axisLengths, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionFloat64Member value = new OctonionFloat64Member();
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
			value.setR(o.r().doubleValue());
			value.setI(o.i().doubleValue());
			value.setJ(o.j().doubleValue());
			value.setK(o.k().doubleValue());
			value.setL(o.l().doubleValue());
			value.setI0(o.i0().doubleValue());
			value.setJ0(o.j0().doubleValue());
			value.setK0(o.k0().doubleValue());
			storage.set(i, value);
		}
	}

	private void appendTensor(StringBuilder sb, OctonionFloat64Member tmp, IntegerIndex index, int axis) {

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
	        sb.append(",");
	        sb.append(tmp.l());
	        sb.append(",");
	        sb.append(tmp.i0());
	        sb.append(",");
	        sb.append(tmp.j0());
	        sb.append(",");
	        sb.append(tmp.k0());
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
	    OctonionFloat64Member tmp = new OctonionFloat64Member();

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
	        sb.append(",");
	        sb.append(tmp.l());
	        sb.append(",");
	        sb.append(tmp.i0());
	        sb.append(",");
	        sb.append(tmp.j0());
	        sb.append(",");
	        sb.append(tmp.k0());
	        sb.append("}");
	        return sb.toString();
	    }

	    IntegerIndex idx = new IntegerIndex(rank());
	    appendTensor(sb, tmp, idx, 0);
	    return sb.toString();
	}

	private static final ThreadLocal<OctonionFloat64Member> tmpOct =
			new ThreadLocal<OctonionFloat64Member>()
	{
		protected OctonionFloat64Member initialValue() {
			return new OctonionFloat64Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.DOUBLE;
	}

	@Override
	public int componentCount() {
		return 8;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else if (component == 7)
			tmp.setK0(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else if (component == 7)
			tmp.setK0(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else if (component == 7)
			tmp.setK0(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else if (component == 7)
			tmp.setK0(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else if (component == 7)
			tmp.setK0(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		else if (component == 2)
			tmp.setJ(v);
		else if (component == 3)
			tmp.setK(v);
		else if (component == 4)
			tmp.setL(v);
		else if (component == 5)
			tmp.setI0(v);
		else if (component == 6)
			tmp.setJ0(v);
		else if (component == 7)
			tmp.setK0(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v.doubleValue());
		else if (component == 1)
			tmp.setI(v.doubleValue());
		else if (component == 2)
			tmp.setJ(v.doubleValue());
		else if (component == 3)
			tmp.setK(v.doubleValue());
		else if (component == 4)
			tmp.setL(v.doubleValue());
		else if (component == 5)
			tmp.setI0(v.doubleValue());
		else if (component == 6)
			tmp.setJ0(v.doubleValue());
		else if (component == 7)
			tmp.setK0(v.doubleValue());
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0)
			tmp.setR(v.doubleValue());
		else if (component == 1)
			tmp.setI(v.doubleValue());
		else if (component == 2)
			tmp.setJ(v.doubleValue());
		else if (component == 3)
			tmp.setK(v.doubleValue());
		else if (component == 4)
			tmp.setL(v.doubleValue());
		else if (component == 5)
			tmp.setI0(v.doubleValue());
		else if (component == 6)
			tmp.setJ0(v.doubleValue());
		else if (component == 7)
			tmp.setK0(v.doubleValue());
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else if (component == 7)
				tmp.setK0(v);
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else if (component == 7)
				tmp.setK0(v);
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else if (component == 7)
				tmp.setK0(v);
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else if (component == 7)
				tmp.setK0(v);
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else if (component == 7)
				tmp.setK0(v);
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			else if (component == 2)
				tmp.setJ(v);
			else if (component == 3)
				tmp.setK(v);
			else if (component == 4)
				tmp.setL(v);
			else if (component == 5)
				tmp.setI0(v);
			else if (component == 6)
				tmp.setJ0(v);
			else if (component == 7)
				tmp.setK0(v);
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v.doubleValue());
			else if (component == 1)
				tmp.setI(v.doubleValue());
			else if (component == 2)
				tmp.setJ(v.doubleValue());
			else if (component == 3)
				tmp.setK(v.doubleValue());
			else if (component == 4)
				tmp.setL(v.doubleValue());
			else if (component == 5)
				tmp.setI0(v.doubleValue());
			else if (component == 6)
				tmp.setJ0(v.doubleValue());
			else if (component == 7)
				tmp.setK0(v.doubleValue());
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0)
				tmp.setR(v.doubleValue());
			else if (component == 1)
				tmp.setI(v.doubleValue());
			else if (component == 2)
				tmp.setJ(v.doubleValue());
			else if (component == 3)
				tmp.setK(v.doubleValue());
			else if (component == 4)
				tmp.setL(v.doubleValue());
			else if (component == 5)
				tmp.setI0(v.doubleValue());
			else if (component == 6)
				tmp.setJ0(v.doubleValue());
			else if (component == 7)
				tmp.setK0(v.doubleValue());
			setV(index, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat64Member tmp = tmpOct.get();
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
		else if (component == 4) {
			return (byte) tmp.l();
		}
		else if (component == 5) {
			return (byte) tmp.i0();
		}
		else if (component == 6) {
			return (byte) tmp.j0();
		}
		else if (component == 7) {
			return (byte) tmp.k0();
		}
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat64Member tmp = tmpOct.get();
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
		else if (component == 4) {
			return (short) tmp.l();
		}
		else if (component == 5) {
			return (short) tmp.i0();
		}
		else if (component == 6) {
			return (short) tmp.j0();
		}
		else if (component == 7) {
			return (short) tmp.k0();
		}
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat64Member tmp = tmpOct.get();
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
		else if (component == 4) {
			return (int) tmp.l();
		}
		else if (component == 5) {
			return (int) tmp.i0();
		}
		else if (component == 6) {
			return (int) tmp.j0();
		}
		else if (component == 7) {
			return (int) tmp.k0();
		}
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat64Member tmp = tmpOct.get();
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
		else if (component == 4) {
			return (long) tmp.l();
		}
		else if (component == 5) {
			return (long) tmp.i0();
		}
		else if (component == 6) {
			return (long) tmp.j0();
		}
		else if (component == 7) {
			return (long) tmp.k0();
		}
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat64Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component == 0) {
			return (float) tmp.r();
		}
		else if (component == 1) {
			return (float) tmp.i();
		}
		else if (component == 2) {
			return (float) tmp.j();
		}
		else if (component == 3) {
			return (float) tmp.k();
		}
		else if (component == 4) {
			return (float) tmp.l();
		}
		else if (component == 5) {
			return (float) tmp.i0();
		}
		else if (component == 6) {
			return (float) tmp.j0();
		}
		else if (component == 7) {
			return (float) tmp.k0();
		}
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat64Member tmp = tmpOct.get();
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
		else if (component == 4) {
			return tmp.l();
		}
		else if (component == 5) {
			return tmp.i0();
		}
		else if (component == 6) {
			return tmp.j0();
		}
		else if (component == 7) {
			return tmp.k0();
		}
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat64Member tmp = tmpOct.get();
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
		else if (component == 4) {
			return BigDecimal.valueOf(tmp.l()).toBigInteger();
		}
		else if (component == 5) {
			return BigDecimal.valueOf(tmp.i0()).toBigInteger();
		}
		else if (component == 6) {
			return BigDecimal.valueOf(tmp.j0()).toBigInteger();
		}
		else if (component == 7) {
			return BigDecimal.valueOf(tmp.k0()).toBigInteger();
		}
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat64Member tmp = tmpOct.get();
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
		else if (component == 4) {
			return BigDecimal.valueOf(tmp.l());
		}
		else if (component == 5) {
			return BigDecimal.valueOf(tmp.i0());
		}
		else if (component == 6) {
			return BigDecimal.valueOf(tmp.j0());
		}
		else if (component == 7) {
			return BigDecimal.valueOf(tmp.k0());
		}
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(axisLengths, index, component, 1)) {
			return 0;
		}
		else {
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0) {
				return (byte) tmp.r();
			}
			else if (component == 1) {
				return (byte) tmp.i();
			}
			else if (component == 3) {
				return (byte) tmp.j();
			}
			else if (component == 4) {
				return (byte) tmp.k();
			}
			else if (component == 4) {
				return (byte) tmp.l();
			}
			else if (component == 5) {
				return (byte) tmp.i0();
			}
			else if (component == 6) {
				return (byte) tmp.j0();
			}
			else if (component == 7) {
				return (byte) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
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
			else if (component == 4) {
				return (short) tmp.l();
			}
			else if (component == 5) {
				return (short) tmp.i0();
			}
			else if (component == 6) {
				return (short) tmp.j0();
			}
			else if (component == 7) {
				return (short) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
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
			else if (component == 4) {
				return (int) tmp.l();
			}
			else if (component == 5) {
				return (int) tmp.i0();
			}
			else if (component == 6) {
				return (int) tmp.j0();
			}
			else if (component == 7) {
				return (int) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
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
			else if (component == 4) {
				return (long) tmp.l();
			}
			else if (component == 5) {
				return (long) tmp.i0();
			}
			else if (component == 6) {
				return (long) tmp.j0();
			}
			else if (component == 7) {
				return (long) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component == 0) {
				return (float) tmp.r();
			}
			else if (component == 1) {
				return (float) tmp.i();
			}
			else if (component == 2) {
				return (float) tmp.j();
			}
			else if (component == 3) {
				return (float) tmp.k();
			}
			else if (component == 4) {
				return (float) tmp.l();
			}
			else if (component == 5) {
				return (float) tmp.i0();
			}
			else if (component == 6) {
				return (float) tmp.j0();
			}
			else if (component == 7) {
				return (float) tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
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
			else if (component == 4) {
				return tmp.l();
			}
			else if (component == 5) {
				return tmp.i0();
			}
			else if (component == 6) {
				return tmp.j0();
			}
			else if (component == 7) {
				return tmp.k0();
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
			OctonionFloat64Member tmp = tmpOct.get();
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
			else if (component == 4) {
				return BigDecimal.valueOf(tmp.l()).toBigInteger();
			}
			else if (component == 5) {
				return BigDecimal.valueOf(tmp.i0()).toBigInteger();
			}
			else if (component == 6) {
				return BigDecimal.valueOf(tmp.j0()).toBigInteger();
			}
			else if (component == 7) {
				return BigDecimal.valueOf(tmp.k0()).toBigInteger();
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
			OctonionFloat64Member tmp = tmpOct.get();
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
			else if (component == 4) {
				return BigDecimal.valueOf(tmp.l());
			}
			else if (component == 5) {
				return BigDecimal.valueOf(tmp.i0());
			}
			else if (component == 6) {
				return BigDecimal.valueOf(tmp.j0());
			}
			else if (component == 7) {
				return BigDecimal.valueOf(tmp.k0());
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
	public IndexedDataSource<OctonionFloat64Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		OctonionFloat64Member tmp = G.ODBL.construct();
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
		if (o instanceof OctonionFloat64GeneralTensorProductMember) {
			return G.ODBL_TEN.isEqual().call(this, (OctonionFloat64GeneralTensorProductMember) o);
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
	public void setFromIntsExact(int... vals) {
		setFromInts(vals);
	}
	
	@Override
	public void setFromFloatsExact(float... vals) {
		setFromFloats(vals);
	}
	
	@Override
	public void setFromDoublesExact(double... vals) {
		setFromDoubles(vals);
	}
	
	@Override
	public void setFromBytes(byte... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0(  vals[i + 5] );
			value.setJ0(  vals[i + 6] );
			value.setK0(  vals[i + 7] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromShorts(short... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0(  vals[i + 5] );
			value.setJ0(  vals[i + 6] );
			value.setK0(  vals[i + 7] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromInts(int... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0(  vals[i + 5] );
			value.setJ0(  vals[i + 6] );
			value.setK0(  vals[i + 7] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromLongs(long... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0(  vals[i + 5] );
			value.setJ0(  vals[i + 6] );
			value.setK0(  vals[i + 7] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromFloats(float... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0(  vals[i + 5] );
			value.setJ0(  vals[i + 6] );
			value.setK0(  vals[i + 7] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromDoubles(double... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0(  vals[i + 5] );
			value.setJ0(  vals[i + 6] );
			value.setK0(  vals[i + 7] );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigIntegers(BigInteger... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].doubleValue() );
			value.setI(  vals[i + 1].doubleValue() );
			value.setJ(  vals[i + 2].doubleValue() );
			value.setK(  vals[i + 3].doubleValue() );
			value.setL(  vals[i + 4].doubleValue() );
			value.setI0(  vals[i + 5].doubleValue() );
			value.setJ0(  vals[i + 6].doubleValue() );
			value.setK0(  vals[i + 7].doubleValue() );
			storage.set(i/componentCount, value);
		}
	}

	@Override
	public void setFromBigDecimals(BigDecimal... vals) {
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat64Member value = G.ODBL.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].doubleValue() );
			value.setI(  vals[i + 1].doubleValue() );
			value.setJ(  vals[i + 2].doubleValue() );
			value.setK(  vals[i + 3].doubleValue() );
			value.setL(  vals[i + 4].doubleValue() );
			value.setI0(  vals[i + 5].doubleValue() );
			value.setJ0(  vals[i + 6].doubleValue() );
			value.setK0(  vals[i + 7].doubleValue() );
			storage.set(i/componentCount, value);
		}
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
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		byte[] values = new byte[8 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[8*i + 0] = (byte) value.r();
			values[8*i + 1] = (byte) value.i();
			values[8*i + 2] = (byte) value.j();
			values[8*i + 3] = (byte) value.k();
			values[8*i + 4] = (byte) value.l();
			values[8*i + 5] = (byte) value.i0();
			values[8*i + 6] = (byte) value.j0();
			values[8*i + 7] = (byte) value.k0();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		short[] values = new short[8 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[8*i + 0] = (short) value.r();
			values[8*i + 1] = (short) value.i();
			values[8*i + 2] = (short) value.j();
			values[8*i + 3] = (short) value.k();
			values[8*i + 4] = (short) value.l();
			values[8*i + 5] = (short) value.i0();
			values[8*i + 6] = (short) value.j0();
			values[8*i + 7] = (short) value.k0();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		int[] values = new int[8 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[8*i + 0] = (int) value.r();
			values[8*i + 1] = (int) value.i();
			values[8*i + 2] = (int) value.j();
			values[8*i + 3] = (int) value.k();
			values[8*i + 4] = (int) value.l();
			values[8*i + 5] = (int) value.i0();
			values[8*i + 6] = (int) value.j0();
			values[8*i + 7] = (int) value.k0();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		long[] values = new long[8 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[8*i + 0] = (long) value.r();
			values[8*i + 1] = (long) value.i();
			values[8*i + 2] = (long) value.j();
			values[8*i + 3] = (long) value.k();
			values[8*i + 4] = (long) value.l();
			values[8*i + 5] = (long) value.i0();
			values[8*i + 6] = (long) value.j0();
			values[8*i + 7] = (long) value.k0();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		float[] values = new float[8 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[8*i + 0] = (float) value.r();
			values[8*i + 1] = (float) value.i();
			values[8*i + 2] = (float) value.j();
			values[8*i + 3] = (float) value.k();
			values[8*i + 4] = (float) value.l();
			values[8*i + 5] = (float) value.i0();
			values[8*i + 6] = (float) value.j0();
			values[8*i + 7] = (float) value.k0();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		double[] values = new double[8 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[8*i + 0] = value.r();
			values[8*i + 1] = value.i();
			values[8*i + 2] = value.j();
			values[8*i + 3] = value.k();
			values[8*i + 4] = value.l();
			values[8*i + 5] = value.i0();
			values[8*i + 6] = value.j0();
			values[8*i + 7] = value.k0();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		BigInteger[] values = new BigInteger[8 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[8*i + 0] = BigDecimal.valueOf(value.r()).toBigInteger();
			values[8*i + 1] = BigDecimal.valueOf(value.i()).toBigInteger();
			values[8*i + 2] = BigDecimal.valueOf(value.j()).toBigInteger();
			values[8*i + 3] = BigDecimal.valueOf(value.k()).toBigInteger();
			values[8*i + 4] = BigDecimal.valueOf(value.l()).toBigInteger();
			values[8*i + 5] = BigDecimal.valueOf(value.i0()).toBigInteger();
			values[8*i + 6] = BigDecimal.valueOf(value.j0()).toBigInteger();
			values[8*i + 7] = BigDecimal.valueOf(value.k0()).toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat64Member value = G.ODBL.construct();
		BigDecimal[] values = new BigDecimal[8 * (int) storage.size()];
		for (int i = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[8*i + 0] = BigDecimal.valueOf(value.r());
			values[8*i + 1] = BigDecimal.valueOf(value.i());
			values[8*i + 2] = BigDecimal.valueOf(value.j());
			values[8*i + 3] = BigDecimal.valueOf(value.k());
			values[8*i + 4] = BigDecimal.valueOf(value.l());
			values[8*i + 5] = BigDecimal.valueOf(value.i0());
			values[8*i + 6] = BigDecimal.valueOf(value.j0());
			values[8*i + 7] = BigDecimal.valueOf(value.k0());
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public OctonionFloat64GeneralTensorProduct getAlgebra() {

		return G.ODBL_TEN;
	}
	
	private static IndexType[] indices(int size, IndexType value) {
		
		IndexType[] values = new IndexType[size];
		for (int i = 0; i < size; i++) {
			values[i] = value;
		}
		return values;
	}
}
