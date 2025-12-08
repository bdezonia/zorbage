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
package nom.bdezonia.zorbage.type.quaternion.highprec;

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
import nom.bdezonia.zorbage.algebra.GetAsFloatArray;
import nom.bdezonia.zorbage.algebra.GetAsIntArray;
import nom.bdezonia.zorbage.algebra.GetAsLongArray;
import nom.bdezonia.zorbage.algebra.GetAsShortArray;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimals;
import nom.bdezonia.zorbage.algebra.SetFromBigDecimalsExact;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegers;
import nom.bdezonia.zorbage.algebra.SetFromBigIntegersExact;
import nom.bdezonia.zorbage.algebra.SetFromBytes;
import nom.bdezonia.zorbage.algebra.SetFromBytesExact;
import nom.bdezonia.zorbage.algebra.SetFromDoubles;
import nom.bdezonia.zorbage.algebra.SetFromDoublesExact;
import nom.bdezonia.zorbage.algebra.SetFromFloats;
import nom.bdezonia.zorbage.algebra.SetFromFloatsExact;
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromIntsExact;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.SetFromLongsExact;
import nom.bdezonia.zorbage.algebra.SetFromShorts;
import nom.bdezonia.zorbage.algebra.SetFromShortsExact;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.TensorMember;
import nom.bdezonia.zorbage.algebra.ThreadAccess;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
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


// TODO:
//   rank 0 tensor getting and setting 1 value instead of 0
//   upper and lower indices: only if not CartesianTensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class QuaternionHighPrecisionCartesianTensorProductMember
	implements
		TensorMember<QuaternionHighPrecisionMember>,
		Gettable<QuaternionHighPrecisionCartesianTensorProductMember>,
		Settable<QuaternionHighPrecisionCartesianTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<QuaternionHighPrecisionMember>,
		SetFromBytes,
		SetFromBytesExact,
		SetFromShorts,
		SetFromShortsExact,
		SetFromInts,
		SetFromIntsExact,
		SetFromLongs,
		SetFromLongsExact,
		SetFromFloats,
		SetFromFloatsExact,
		SetFromDoubles,
		SetFromDoublesExact,
		SetFromBigIntegers,
		SetFromBigIntegersExact,
		SetFromBigDecimals,
		SetFromBigDecimalsExact,
		GetAsByteArray,
		GetAsShortArray,
		GetAsIntArray,
		GetAsLongArray,
		GetAsFloatArray,
		GetAsDoubleArray,
		GetAsBigIntegerArray,
		GetAsBigDecimalArray,
		GetAsBigDecimalArrayExact,
		ThreadAccess,
		GetAlgebra<QuaternionHighPrecisionCartesianTensorProduct, QuaternionHighPrecisionCartesianTensorProductMember>,
		CompositeType,
		ExactType,
		SignedType,
		TensorType,
		UnityIncludedType,
		ZeroIncludedType
{
	private static final QuaternionHighPrecisionMember ZERO = new QuaternionHighPrecisionMember();

	private int rank;
	private long dimCount;
	private IndexedDataSource<QuaternionHighPrecisionMember> storage;
	private long[] dims;
	private long[] multipliers;
	private StorageConstruction s;

	// rank() is also numDimensions(). Confusing. TODO - fix
	
	@Override
	public int rank() { return lowerRank() + upperRank(); }
	
	@Override
	public int lowerRank() {
		return rank;
	}
	
	@Override
	public int upperRank() {
		return 0;
	}
	
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

	public QuaternionHighPrecisionCartesianTensorProductMember() {
		rank = 0;
		dimCount = 0;
		dims = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), 1);
		multipliers = IndexUtils.calcMultipliers(dims);
	}

	public QuaternionHighPrecisionCartesianTensorProductMember(int rank, long dimCount) {
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
		storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), numElems);
		multipliers = IndexUtils.calcMultipliers(dims);
	}
	
	public QuaternionHighPrecisionCartesianTensorProductMember(int rank, long dimCount, BigDecimal... vals) {
		this(rank, dimCount);
		setFromBigDecimals(vals);
	}
	
	public QuaternionHighPrecisionCartesianTensorProductMember(int rank, long dimCount, BigInteger... vals) {
		this(rank, dimCount);
		setFromBigIntegers(vals);
	}
	
	public QuaternionHighPrecisionCartesianTensorProductMember(int rank, long dimCount, double... vals) {
		this(rank, dimCount);
		setFromDoubles(vals);
	}
	
	public QuaternionHighPrecisionCartesianTensorProductMember(int rank, long dimCount, long... vals) {
		this(rank, dimCount);
		setFromLongs(vals);
	}

	public QuaternionHighPrecisionCartesianTensorProductMember(QuaternionHighPrecisionCartesianTensorProductMember other) {
		set(other);
	}
	
	public QuaternionHighPrecisionCartesianTensorProductMember(String s) {
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
		this.storage = Storage.allocate(this.s, new QuaternionHighPrecisionMember(), numElems);
		this.multipliers = IndexUtils.calcMultipliers(dims);
		QuaternionHighPrecisionMember value = new QuaternionHighPrecisionMember();
		if (numElems == 1) {
			// TODO: does a rank 0 tensor have any values from a parsing?
			OctonionRepresentation val = data.get(0);
			value.setR(val.r());
			value.setI(val.i());
			value.setJ(val.j());
			value.setK(val.k());
			storage.set(0, value);
		}
		else {
			long i = 0;
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(tmpDims);
			IntegerIndex index = new IntegerIndex(dims.length);
			while (iter.hasNext()) {
				iter.next(index);
				OctonionRepresentation val = data.get(i);
				value.setR(val.r());
				value.setI(val.i());
				value.setJ(val.j());
				value.setK(val.k());
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
	public void set(QuaternionHighPrecisionCartesianTensorProductMember other) {
		if (this == other) return;
		rank = other.rank;
		dimCount = other.dimCount;
		dims = other.dims.clone();
		multipliers = other.multipliers.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(QuaternionHighPrecisionCartesianTensorProductMember other) {
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
			storage = Storage.allocate(s, new QuaternionHighPrecisionMember(), newCount);
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
	
	void v(long index, QuaternionHighPrecisionMember value) {
		storage.get(index, value);
	}
	
	@Override
	public void getV(IntegerIndex index, QuaternionHighPrecisionMember value) {
		long idx = IndexUtils.safeIndexToLong(dims, index);
		storage.get(idx, value);
	}
	
	void setV(long index, QuaternionHighPrecisionMember value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, QuaternionHighPrecisionMember value) {
		long idx = IndexUtils.safeIndexToLong(dims, index);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		QuaternionHighPrecisionMember value = new QuaternionHighPrecisionMember();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = value.r();
			BigDecimal im = value.i();
			BigDecimal j = value.j();
			BigDecimal k = value.k();
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
		rep.setTensor(dims, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		QuaternionHighPrecisionMember value = new QuaternionHighPrecisionMember();
		BigList<OctonionRepresentation> tensor = rep.getTensor();
		init(rep.getTensorDims());
		long tensorSize = tensor.size();
		for (long i = 0; i < tensorSize; i++) {
			OctonionRepresentation o = tensor.get(i);
			value.setR(o.r());
			value.setI(o.i());
			value.setJ(o.j());
			value.setK(o.k());
			storage.set(i, value);
		}
	}

	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		QuaternionHighPrecisionMember tmp = new QuaternionHighPrecisionMember();
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
			builder.append('{');
			builder.append(tmp.r());
			builder.append(',');
			builder.append(tmp.i());
			builder.append(',');
			builder.append(tmp.j());
			builder.append(',');
			builder.append(tmp.k());
			builder.append('}');
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
	
	private static final ThreadLocal<QuaternionHighPrecisionMember> tmpQuat =
			new ThreadLocal<QuaternionHighPrecisionMember>()
	{
		protected QuaternionHighPrecisionMember initialValue() {
			return new QuaternionHighPrecisionMember();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 4;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
		}
		else { // component >= 2
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
		}
		else { // component >= 2
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
		}
		else { // component >= 2
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
		}
		else { // component >= 2
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
		}
		else { // component >= 2
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else
				tmp.setI(BigDecimal.valueOf(v));
		}
		else { // component >= 2
			if (component == 2)
				tmp.setJ(BigDecimal.valueOf(v));
			else
				tmp.setK(BigDecimal.valueOf(v));
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				tmp.setR(new BigDecimal(v));
			else
				tmp.setI(new BigDecimal(v));
		}
		else { // component >= 2
			if (component == 2)
				tmp.setJ(new BigDecimal(v));
			else
				tmp.setK(new BigDecimal(v));
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				tmp.setR(v);
			else
				tmp.setI(v);
		}
		else { // component >= 2
			if (component == 2)
				tmp.setJ(v);
			else
				tmp.setK(v);
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else
					tmp.setI(BigDecimal.valueOf(v));
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else
					tmp.setI(BigDecimal.valueOf(v));
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else
					tmp.setI(BigDecimal.valueOf(v));
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else
					tmp.setI(BigDecimal.valueOf(v));
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else
					tmp.setI(BigDecimal.valueOf(v));
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					tmp.setR(BigDecimal.valueOf(v));
				else
					tmp.setI(BigDecimal.valueOf(v));
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(BigDecimal.valueOf(v));
				else
					tmp.setK(BigDecimal.valueOf(v));
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					tmp.setR(new BigDecimal(v));
				else
					tmp.setI(new BigDecimal(v));
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(new BigDecimal(v));
				else
					tmp.setK(new BigDecimal(v));
			}
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					tmp.setR(v);
				else
					tmp.setI(v);
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(v);
				else
					tmp.setK(v);
			}
			setV(index, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				return tmp.r().byteValue();
			else
				return tmp.i().byteValue();
		}
		else { // component >= 2
			if (component == 2)
				return tmp.j().byteValue();
			else
				return tmp.k().byteValue();
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				return tmp.r().shortValue();
			else
				return tmp.i().shortValue();
		}
		else { // component >= 2
			if (component == 2)
				return tmp.j().shortValue();
			else
				return tmp.k().shortValue();
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				return tmp.r().intValue();
			else
				return tmp.i().intValue();
		}
		else { // component >= 2
			if (component == 2)
				return tmp.j().intValue();
			else
				return tmp.k().intValue();
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				return tmp.r().longValue();
			else
				return tmp.i().longValue();
		}
		else { // component >= 2
			if (component == 2)
				return tmp.j().longValue();
			else
				return tmp.k().longValue();
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				return tmp.r().floatValue();
			else
				return tmp.i().floatValue();
		}
		else { // component >= 2
			if (component == 2)
				return tmp.j().floatValue();
			else
				return tmp.k().floatValue();
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				return tmp.r().doubleValue();
			else
				return tmp.i().doubleValue();
		}
		else { // component >= 2
			if (component == 2)
				return tmp.j().doubleValue();
			else
				return tmp.k().doubleValue();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				return tmp.r().toBigInteger();
			else
				return tmp.i().toBigInteger();
		}
		else { // component >= 2
			if (component == 2)
				return tmp.j().toBigInteger();
			else
				return tmp.k().toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		QuaternionHighPrecisionMember tmp = tmpQuat.get();
		getV(index, tmp);
		if (component < 2) {
			if (component == 0)
				return tmp.r();
			else
				return tmp.i();
		}
		else { // component >= 2
			if (component == 2)
				return tmp.j();
			else
				return tmp.k();
		}
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			return 0;
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					return tmp.r().byteValue();
				else
					return tmp.i().byteValue();
			}
			else { // component >= 2
				if (component == 2)
					return tmp.j().byteValue();
				else
					return tmp.k().byteValue();
			}
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			return 0;
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					return tmp.r().shortValue();
				else
					return tmp.i().shortValue();
			}
			else { // component >= 2
				if (component == 2)
					return tmp.j().shortValue();
				else
					return tmp.k().shortValue();
			}
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			return 0;
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					return tmp.r().intValue();
				else
					return tmp.i().intValue();
			}
			else { // component >= 2
				if (component == 2)
					return tmp.j().intValue();
				else
					return tmp.k().intValue();
			}
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			return 0;
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					return tmp.r().longValue();
				else
					return tmp.i().longValue();
			}
			else { // component >= 2
				if (component == 2)
					return tmp.j().longValue();
				else
					return tmp.k().longValue();
			}
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			return 0;
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					return tmp.r().floatValue();
				else
					return tmp.i().floatValue();
			}
			else { // component >= 2
				if (component == 2)
					return tmp.j().floatValue();
				else
					return tmp.k().floatValue();
			}
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			return 0;
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					return tmp.r().doubleValue();
				else
					return tmp.i().doubleValue();
			}
			else { // component >= 2
				if (component == 2)
					return tmp.j().doubleValue();
				else
					return tmp.k().doubleValue();
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			return BigInteger.ZERO;
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					return tmp.r().toBigInteger();
				else
					return tmp.i().toBigInteger();
			}
			else { // component >= 2
				if (component == 2)
					return tmp.j().toBigInteger();
				else
					return tmp.k().toBigInteger();
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 4)) {
			return BigDecimal.ZERO;
		}
		else {
			QuaternionHighPrecisionMember tmp = tmpQuat.get();
			getV(index, tmp);
			if (component < 2) {
				if (component == 0)
					return tmp.r();
				else
					return tmp.i();
			}
			else { // component >= 2
				if (component == 2)
					return tmp.j();
				else
					return tmp.k();
			}
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<QuaternionHighPrecisionMember> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		QuaternionHighPrecisionMember tmp = G.QHP.construct();
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
		if (o instanceof QuaternionHighPrecisionCartesianTensorProductMember) {
			return G.QHP_TEN.isEqual().call(this, (QuaternionHighPrecisionCartesianTensorProductMember) o);
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
	public void setFromLongsExact(long... vals) {
		setFromLongs(vals);
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
	public void setFromBigIntegersExact(BigInteger... vals) {
		setFromBigIntegers(vals);
	}
	
	@Override
	public void setFromBigDecimalsExact(BigDecimal... vals) {
		setFromBigDecimals(vals);
	}

	@Override
	public void setFromBytes(byte... vals) {
		int componentCount = 4;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
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
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
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
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
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
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
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
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
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
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  BigDecimal.valueOf(vals[i + 0]) );
			value.setI(  BigDecimal.valueOf(vals[i + 1]) );
			value.setJ(  BigDecimal.valueOf(vals[i + 2]) );
			value.setK(  BigDecimal.valueOf(vals[i + 3]) );
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
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  new BigDecimal(vals[i + 0]) );
			value.setI(  new BigDecimal(vals[i + 1]) );
			value.setJ(  new BigDecimal(vals[i + 2]) );
			value.setK(  new BigDecimal(vals[i + 3]) );
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
		QuaternionHighPrecisionMember value = G.QHP.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			storage.set(i/componentCount, value);
		}
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
		QuaternionHighPrecisionMember value = G.QHP.construct();
		byte[] values = new byte[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().byteValue();
			values[k++] = value.i().byteValue();
			values[k++] = value.j().byteValue();
			values[k++] = value.k().byteValue();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		short[] values = new short[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().shortValue();
			values[k++] = value.i().shortValue();
			values[k++] = value.j().shortValue();
			values[k++] = value.k().shortValue();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		int[] values = new int[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().intValue();
			values[k++] = value.i().intValue();
			values[k++] = value.j().intValue();
			values[k++] = value.k().intValue();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		long[] values = new long[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().longValue();
			values[k++] = value.i().longValue();
			values[k++] = value.j().longValue();
			values[k++] = value.k().longValue();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		float[] values = new float[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().floatValue();
			values[k++] = value.i().floatValue();
			values[k++] = value.j().floatValue();
			values[k++] = value.k().floatValue();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		double[] values = new double[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().doubleValue();
			values[k++] = value.i().doubleValue();
			values[k++] = value.j().doubleValue();
			values[k++] = value.k().doubleValue();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		BigInteger[] values = new BigInteger[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r().toBigInteger();
			values[k++] = value.i().toBigInteger();
			values[k++] = value.j().toBigInteger();
			values[k++] = value.k().toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 4))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		QuaternionHighPrecisionMember value = G.QHP.construct();
		BigDecimal[] values = new BigDecimal[4 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r();
			values[k++] = value.i();
			values[k++] = value.j();
			values[k++] = value.k();
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public QuaternionHighPrecisionCartesianTensorProduct getAlgebra() {

		return G.QHP_TEN;
	}
}
