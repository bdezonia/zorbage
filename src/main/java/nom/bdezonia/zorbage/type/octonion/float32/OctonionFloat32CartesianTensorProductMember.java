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
package nom.bdezonia.zorbage.type.octonion.float32;

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
import nom.bdezonia.zorbage.algebra.SetFromFloatsExact;
import nom.bdezonia.zorbage.algebra.SetFromInts;
import nom.bdezonia.zorbage.algebra.SetFromLongs;
import nom.bdezonia.zorbage.algebra.SetFromShorts;
import nom.bdezonia.zorbage.algebra.SetFromShortsExact;
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
public final class OctonionFloat32CartesianTensorProductMember
	implements
		TensorMember<OctonionFloat32Member>,
		Gettable<OctonionFloat32CartesianTensorProductMember>,
		Settable<OctonionFloat32CartesianTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<OctonionFloat32Member>,
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
		GetAlgebra<OctonionFloat32CartesianTensorProduct, OctonionFloat32CartesianTensorProductMember>
{
	private static final OctonionFloat32Member ZERO = new OctonionFloat32Member();

	private int rank;
	private long dimCount;
	private IndexedDataSource<OctonionFloat32Member> storage;
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

	public OctonionFloat32CartesianTensorProductMember() {
		rank = 0;
		dimCount = 0;
		dims = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, new OctonionFloat32Member(), 1);
		this.multipliers = IndexUtils.calcMultipliers(dims);
	}

	public OctonionFloat32CartesianTensorProductMember(int rank, long dimCount) {
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
		storage = Storage.allocate(s, new OctonionFloat32Member(), numElems);
		this.multipliers = IndexUtils.calcMultipliers(dims);
	}
	
	public OctonionFloat32CartesianTensorProductMember(int rank, long dimCount, float... vals) {
		this(rank, dimCount);
		setFromFloats(vals);
	}

	public OctonionFloat32CartesianTensorProductMember(OctonionFloat32CartesianTensorProductMember other) {
		set(other);
	}
	
	public OctonionFloat32CartesianTensorProductMember(String s) {
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
		this.storage = Storage.allocate(this.s, new OctonionFloat32Member(), numElems);
		this.multipliers = IndexUtils.calcMultipliers(dims);
		OctonionFloat32Member value = new OctonionFloat32Member();
		if (numElems == 1) {
			// TODO: does a rank 0 tensor have any values from a parsing?
			OctonionRepresentation val = data.get(0);
			value.setR(val.r().floatValue());
			value.setI(val.i().floatValue());
			value.setJ(val.j().floatValue());
			value.setK(val.k().floatValue());
			value.setL(val.l().floatValue());
			value.setI0(val.i0().floatValue());
			value.setJ0(val.j0().floatValue());
			value.setK0(val.k0().floatValue());
			storage.set(0, value);
		}
		else {
			long i = 0;
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(tmpDims);
			IntegerIndex index = new IntegerIndex(dims.length);
			while (iter.hasNext()) {
				iter.next(index);
				OctonionRepresentation val = data.get(i);
				value.setR(val.r().floatValue());
				value.setI(val.i().floatValue());
				value.setJ(val.j().floatValue());
				value.setK(val.k().floatValue());
				value.setL(val.l().floatValue());
				value.setI0(val.i0().floatValue());
				value.setJ0(val.j0().floatValue());
				value.setK0(val.k0().floatValue());
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
	public void set(OctonionFloat32CartesianTensorProductMember other) {
		if (this == other) return;
		rank = other.rank;
		dimCount = other.dimCount;
		dims = other.dims.clone();
		multipliers = other.multipliers.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(OctonionFloat32CartesianTensorProductMember other) {
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
			storage = Storage.allocate(s, new OctonionFloat32Member(), newCount);
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
	
	void v(long index, OctonionFloat32Member value) {
		storage.get(index, value);
	}
	
	@Override
	public void getV(IntegerIndex index, OctonionFloat32Member value) {
		long idx = IndexUtils.safeIndexToLong(dims, index);
		storage.get(idx, value);
	}
	
	void setV(long index, OctonionFloat32Member value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, OctonionFloat32Member value) {
		long idx = IndexUtils.safeIndexToLong(dims, index);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		OctonionFloat32Member value = new OctonionFloat32Member();
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
		rep.setTensor(dims, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		OctonionFloat32Member value = new OctonionFloat32Member();
		BigList<OctonionRepresentation> tensor = rep.getTensor();
		init(rep.getTensorDims());
		long tensorSize = tensor.size();
		for (long i = 0; i < tensorSize; i++) {
			OctonionRepresentation o = tensor.get(i);
			value.setR(o.r().floatValue());
			value.setI(o.i().floatValue());
			value.setJ(o.j().floatValue());
			value.setK(o.k().floatValue());
			value.setL(o.l().floatValue());
			value.setI0(o.i0().floatValue());
			value.setJ0(o.j0().floatValue());
			value.setK0(o.k0().floatValue());
			storage.set(i, value);
		}
	}

	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		OctonionFloat32Member tmp = new OctonionFloat32Member();
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
			builder.append(',');
			builder.append(tmp.l());
			builder.append(',');
			builder.append(tmp.i0());
			builder.append(',');
			builder.append(tmp.j0());
			builder.append(',');
			builder.append(tmp.k0());
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
	
	private static final ThreadLocal<OctonionFloat32Member> tmpOct =
			new ThreadLocal<OctonionFloat32Member>()
	{
		protected OctonionFloat32Member initialValue() {
			return new OctonionFloat32Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.FLOAT;
	}

	@Override
	public int componentCount() {
		return 8;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
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
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					tmp.setL(v);
				else
					tmp.setI0(v);
			}
			else { // component == 6 or 7
				if (component == 6)
					tmp.setJ0(v);
				else
					tmp.setK0(v);
			}
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
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
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					tmp.setL(v);
				else
					tmp.setI0(v);
			}
			else { // component == 6 or 7
				if (component == 6)
					tmp.setJ0(v);
				else
					tmp.setK0(v);
			}
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
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
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					tmp.setL(v);
				else
					tmp.setI0(v);
			}
			else { // component == 6 or 7
				if (component == 6)
					tmp.setJ0(v);
				else
					tmp.setK0(v);
			}
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
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
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					tmp.setL(v);
				else
					tmp.setI0(v);
			}
			else { // component == 6 or 7
				if (component == 6)
					tmp.setJ0(v);
				else
					tmp.setK0(v);
			}
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
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
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					tmp.setL(v);
				else
					tmp.setI0(v);
			}
			else { // component == 6 or 7
				if (component == 6)
					tmp.setJ0(v);
				else
					tmp.setK0(v);
			}
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					tmp.setR((float) v);
				else
					tmp.setI((float) v);
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ((float) v);
				else
					tmp.setK((float) v);
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					tmp.setL((float) v);
				else
					tmp.setI0((float) v);
			}
			else { // component == 6 or 7
				if (component == 6)
					tmp.setJ0((float) v);
				else
					tmp.setK0((float) v);
			}
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					tmp.setR(v.floatValue());
				else
					tmp.setI(v.floatValue());
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(v.floatValue());
				else
					tmp.setK(v.floatValue());
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					tmp.setL(v.floatValue());
				else
					tmp.setI0(v.floatValue());
			}
			else { // component == 6 or 7
				if (component == 6)
					tmp.setJ0(v.floatValue());
				else
					tmp.setK0(v.floatValue());
			}
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					tmp.setR(v.floatValue());
				else
					tmp.setI(v.floatValue());
			}
			else { // component >= 2
				if (component == 2)
					tmp.setJ(v.floatValue());
				else
					tmp.setK(v.floatValue());
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					tmp.setL(v.floatValue());
				else
					tmp.setI0(v.floatValue());
			}
			else { // component == 6 or 7
				if (component == 6)
					tmp.setJ0(v.floatValue());
				else
					tmp.setK0(v.floatValue());
			}
		}
		setV(index, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
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
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						tmp.setL(v);
					else
						tmp.setI0(v);
				}
				else { // component == 6 or 7
					if (component == 6)
						tmp.setJ0(v);
					else
						tmp.setK0(v);
				}
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
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
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						tmp.setL(v);
					else
						tmp.setI0(v);
				}
				else { // component == 6 or 7
					if (component == 6)
						tmp.setJ0(v);
					else
						tmp.setK0(v);
				}
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
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
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						tmp.setL(v);
					else
						tmp.setI0(v);
				}
				else { // component == 6 or 7
					if (component == 6)
						tmp.setJ0(v);
					else
						tmp.setK0(v);
				}
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
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
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						tmp.setL(v);
					else
						tmp.setI0(v);
				}
				else { // component == 6 or 7
					if (component == 6)
						tmp.setJ0(v);
					else
						tmp.setK0(v);
				}
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
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
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						tmp.setL(v);
					else
						tmp.setI0(v);
				}
				else { // component == 6 or 7
					if (component == 6)
						tmp.setJ0(v);
					else
						tmp.setK0(v);
				}
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						tmp.setR((float) v);
					else
						tmp.setI((float) v);
				}
				else { // component >= 2
					if (component == 2)
						tmp.setJ((float) v);
					else
						tmp.setK((float) v);
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						tmp.setL((float) v);
					else
						tmp.setI0((float) v);
				}
				else { // component == 6 or 7
					if (component == 6)
						tmp.setJ0((float) v);
					else
						tmp.setK0((float) v);
				}
			}
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						tmp.setR(v.floatValue());
					else
						tmp.setI(v.floatValue());
				}
				else { // component >= 2
					if (component == 2)
						tmp.setJ(v.floatValue());
					else
						tmp.setK(v.floatValue());
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						tmp.setL(v.floatValue());
					else
						tmp.setI0(v.floatValue());
				}
				else { // component == 6 or 7
					if (component == 6)
						tmp.setJ0(v.floatValue());
					else
						tmp.setK0(v.floatValue());
				}
			}
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						tmp.setR(v.floatValue());
					else
						tmp.setI(v.floatValue());
				}
				else { // component >= 2
					if (component == 2)
						tmp.setJ(v.floatValue());
					else
						tmp.setK(v.floatValue());
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						tmp.setL(v.floatValue());
					else
						tmp.setI0(v.floatValue());
				}
				else { // component == 6 or 7
					if (component == 6)
						tmp.setJ0(v.floatValue());
					else
						tmp.setK0(v.floatValue());
				}
			}
			setV(index, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					return (byte) tmp.r();
				else
					return (byte) tmp.i();
			}
			else { // component >= 2
				if (component == 2)
					return (byte) tmp.j();
				else
					return (byte) tmp.k();
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					return (byte) tmp.l();
				else
					return (byte) tmp.i0();
			}
			else { // component == 6 or 7
				if (component == 6)
					return (byte) tmp.j0();
				else
					return (byte) tmp.k0();
			}
		}
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					return (short) tmp.r();
				else
					return (short) tmp.i();
			}
			else { // component >= 2
				if (component == 2)
					return (short) tmp.j();
				else
					return (short) tmp.k();
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					return (short) tmp.l();
				else
					return (short) tmp.i0();
			}
			else { // component == 6 or 7
				if (component == 6)
					return (short) tmp.j0();
				else
					return (short) tmp.k0();
			}
		}
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					return (int) tmp.r();
				else
					return (int) tmp.i();
			}
			else { // component >= 2
				if (component == 2)
					return (int) tmp.j();
				else
					return (int) tmp.k();
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					return (int) tmp.l();
				else
					return (int) tmp.i0();
			}
			else { // component == 6 or 7
				if (component == 6)
					return (int) tmp.j0();
				else
					return (int) tmp.k0();
			}
		}
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					return (long) tmp.r();
				else
					return (long) tmp.i();
			}
			else { // component >= 2
				if (component == 2)
					return (long) tmp.j();
				else
					return (long) tmp.k();
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					return (long) tmp.l();
				else
					return (long) tmp.i0();
			}
			else { // component == 6 or 7
				if (component == 6)
					return (long) tmp.j0();
				else
					return (long) tmp.k0();
			}
		}
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
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
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					return tmp.l();
				else
					return tmp.i0();
			}
			else { // component == 6 or 7
				if (component == 6)
					return tmp.j0();
				else
					return tmp.k0();
			}
		}
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
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
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					return tmp.l();
				else
					return tmp.i0();
			}
			else { // component == 6 or 7
				if (component == 6)
					return tmp.j0();
				else
					return tmp.k0();
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					return BigDecimal.valueOf(tmp.r()).toBigInteger();
				else
					return BigDecimal.valueOf(tmp.i()).toBigInteger();
			}
			else { // component >= 2
				if (component == 2)
					return BigDecimal.valueOf(tmp.j()).toBigInteger();
				else
					return BigDecimal.valueOf(tmp.k()).toBigInteger();
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					return BigDecimal.valueOf(tmp.l()).toBigInteger();
				else
					return BigDecimal.valueOf(tmp.i0()).toBigInteger();
			}
			else { // component == 6 or 7
				if (component == 6)
					return BigDecimal.valueOf(tmp.j0()).toBigInteger();
				else
					return BigDecimal.valueOf(tmp.k0()).toBigInteger();
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		OctonionFloat32Member tmp = tmpOct.get();
		getV(index, tmp);
		if (component < 4) {
			if (component < 2) {
				if (component == 0)
					return BigDecimal.valueOf(tmp.r());
				else
					return BigDecimal.valueOf(tmp.i());
			}
			else { // component >= 2
				if (component == 2)
					return BigDecimal.valueOf(tmp.j());
				else
					return BigDecimal.valueOf(tmp.k());
			}
		}
		else { // component == 4 or 5 or 6 or 7
			if (component < 6) {
				if (component == 4)
					return BigDecimal.valueOf(tmp.l());
				else
					return BigDecimal.valueOf(tmp.i0());
			}
			else { // component == 6 or 7
				if (component == 6)
					return BigDecimal.valueOf(tmp.j0());
				else
					return BigDecimal.valueOf(tmp.k0());
			}
		}
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			return 0;
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						return (byte) tmp.r();
					else
						return (byte) tmp.i();
				}
				else { // component >= 2
					if (component == 2)
						return (byte) tmp.j();
					else
						return (byte) tmp.k();
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						return (byte) tmp.l();
					else
						return (byte) tmp.i0();
				}
				else { // component == 6 or 7
					if (component == 6)
						return (byte) tmp.j0();
					else
						return (byte) tmp.k0();
				}
			}
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			return 0;
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						return (short) tmp.r();
					else
						return (short) tmp.i();
				}
				else { // component >= 2
					if (component == 2)
						return (short) tmp.j();
					else
						return (short) tmp.k();
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						return (short) tmp.l();
					else
						return (short) tmp.i0();
				}
				else { // component == 6 or 7
					if (component == 6)
						return (short) tmp.j0();
					else
						return (short) tmp.k0();
				}
			}
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			return 0;
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						return (int) tmp.r();
					else
						return (int) tmp.i();
				}
				else { // component >= 2
					if (component == 2)
						return (int) tmp.j();
					else
						return (int) tmp.k();
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						return (int) tmp.l();
					else
						return (int) tmp.i0();
				}
				else { // component == 6 or 7
					if (component == 6)
						return (int) tmp.j0();
					else
						return (int) tmp.k0();
				}
			}
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			return 0;
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						return (long) tmp.r();
					else
						return (long) tmp.i();
				}
				else { // component >= 2
					if (component == 2)
						return (long) tmp.j();
					else
						return (long) tmp.k();
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						return (long) tmp.l();
					else
						return (long) tmp.i0();
				}
				else { // component == 6 or 7
					if (component == 6)
						return (long) tmp.j0();
					else
						return (long) tmp.k0();
				}
			}
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			return 0;
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
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
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						return tmp.l();
					else
						return tmp.i0();
				}
				else { // component == 6 or 7
					if (component == 6)
						return tmp.j0();
					else
						return tmp.k0();
				}
			}
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			return 0;
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
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
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						return tmp.l();
					else
						return tmp.i0();
				}
				else { // component == 6 or 7
					if (component == 6)
						return tmp.j0();
					else
						return tmp.k0();
				}
			}
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			return BigInteger.ZERO;
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						return BigDecimal.valueOf(tmp.r()).toBigInteger();
					else
						return BigDecimal.valueOf(tmp.i()).toBigInteger();
				}
				else { // component >= 2
					if (component == 2)
						return BigDecimal.valueOf(tmp.j()).toBigInteger();
					else
						return BigDecimal.valueOf(tmp.k()).toBigInteger();
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						return BigDecimal.valueOf(tmp.l()).toBigInteger();
					else
						return BigDecimal.valueOf(tmp.i0()).toBigInteger();
				}
				else { // component == 6 or 7
					if (component == 6)
						return BigDecimal.valueOf(tmp.j0()).toBigInteger();
					else
						return BigDecimal.valueOf(tmp.k0()).toBigInteger();
				}
			}
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 8)) {
			return BigDecimal.ZERO;
		}
		else {
			OctonionFloat32Member tmp = tmpOct.get();
			getV(index, tmp);
			if (component < 4) {
				if (component < 2) {
					if (component == 0)
						return BigDecimal.valueOf(tmp.r());
					else
						return BigDecimal.valueOf(tmp.i());
				}
				else { // component >= 2
					if (component == 2)
						return BigDecimal.valueOf(tmp.j());
					else
						return BigDecimal.valueOf(tmp.k());
				}
			}
			else { // component == 4 or 5 or 6 or 7
				if (component < 6) {
					if (component == 4)
						return BigDecimal.valueOf(tmp.l());
					else
						return BigDecimal.valueOf(tmp.i0());
				}
				else { // component == 6 or 7
					if (component == 6)
						return BigDecimal.valueOf(tmp.j0());
					else
						return BigDecimal.valueOf(tmp.k0());
				}
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
	public IndexedDataSource<OctonionFloat32Member> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		OctonionFloat32Member tmp = G.OFLT.construct();
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
		if (o instanceof OctonionFloat32CartesianTensorProductMember) {
			return G.OFLT_TEN.isEqual().call(this, (OctonionFloat32CartesianTensorProductMember) o);
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
		int componentCount = 8;
		if (vals.length/componentCount != storage.size()) {
			throw new IllegalArgumentException(
					"number of elements passed in do not fit allocated storage");
		}
		OctonionFloat32Member value = G.OFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0( vals[i + 5] );
			value.setJ0( vals[i + 6] );
			value.setK0( vals[i + 7] );
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
		OctonionFloat32Member value = G.OFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0( vals[i + 5] );
			value.setJ0( vals[i + 6] );
			value.setK0( vals[i + 7] );
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
		OctonionFloat32Member value = G.OFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0( vals[i + 5] );
			value.setJ0( vals[i + 6] );
			value.setK0( vals[i + 7] );
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
		OctonionFloat32Member value = G.OFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0( vals[i + 5] );
			value.setJ0( vals[i + 6] );
			value.setK0( vals[i + 7] );
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
		OctonionFloat32Member value = G.OFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0] );
			value.setI(  vals[i + 1] );
			value.setJ(  vals[i + 2] );
			value.setK(  vals[i + 3] );
			value.setL(  vals[i + 4] );
			value.setI0( vals[i + 5] );
			value.setJ0( vals[i + 6] );
			value.setK0( vals[i + 7] );
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
		OctonionFloat32Member value = G.OFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  (float) vals[i + 0] );
			value.setI(  (float) vals[i + 1] );
			value.setJ(  (float) vals[i + 2] );
			value.setK(  (float) vals[i + 3] );
			value.setL(  (float) vals[i + 4] );
			value.setI0( (float) vals[i + 5] );
			value.setJ0( (float) vals[i + 6] );
			value.setK0( (float) vals[i + 7] );
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
		OctonionFloat32Member value = G.OFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].floatValue() );
			value.setI(  vals[i + 1].floatValue() );
			value.setJ(  vals[i + 2].floatValue() );
			value.setK(  vals[i + 3].floatValue() );
			value.setL(  vals[i + 4].floatValue() );
			value.setI0( vals[i + 5].floatValue() );
			value.setJ0( vals[i + 6].floatValue() );
			value.setK0( vals[i + 7].floatValue() );
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
		OctonionFloat32Member value = G.OFLT.construct();
		for (int i = 0; i < vals.length; i += componentCount) {
			value.setR(  vals[i + 0].floatValue() );
			value.setI(  vals[i + 1].floatValue() );
			value.setJ(  vals[i + 2].floatValue() );
			value.setK(  vals[i + 3].floatValue() );
			value.setL(  vals[i + 4].floatValue() );
			value.setI0( vals[i + 5].floatValue() );
			value.setJ0( vals[i + 6].floatValue() );
			value.setK0( vals[i + 7].floatValue() );
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
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat32Member value = G.OFLT.construct();
		byte[] values = new byte[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (byte) value.r();
			values[k++] = (byte) value.i();
			values[k++] = (byte) value.j();
			values[k++] = (byte) value.k();
			values[k++] = (byte) value.l();
			values[k++] = (byte) value.i0();
			values[k++] = (byte) value.j0();
			values[k++] = (byte) value.k0();
		}
		return values;
	}

	@Override
	public short[] getAsShortArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat32Member value = G.OFLT.construct();
		short[] values = new short[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (short) value.r();
			values[k++] = (short) value.i();
			values[k++] = (short) value.j();
			values[k++] = (short) value.k();
			values[k++] = (short) value.l();
			values[k++] = (short) value.i0();
			values[k++] = (short) value.j0();
			values[k++] = (short) value.k0();
		}
		return values;
	}

	@Override
	public int[] getAsIntArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat32Member value = G.OFLT.construct();
		int[] values = new int[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (int) value.r();
			values[k++] = (int) value.i();
			values[k++] = (int) value.j();
			values[k++] = (int) value.k();
			values[k++] = (int) value.l();
			values[k++] = (int) value.i0();
			values[k++] = (int) value.j0();
			values[k++] = (int) value.k0();
		}
		return values;
	}

	@Override
	public long[] getAsLongArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat32Member value = G.OFLT.construct();
		long[] values = new long[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = (long) value.r();
			values[k++] = (long) value.i();
			values[k++] = (long) value.j();
			values[k++] = (long) value.k();
			values[k++] = (long) value.l();
			values[k++] = (long) value.i0();
			values[k++] = (long) value.j0();
			values[k++] = (long) value.k0();
		}
		return values;
	}

	@Override
	public float[] getAsFloatArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat32Member value = G.OFLT.construct();
		float[] values = new float[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r();
			values[k++] = value.i();
			values[k++] = value.j();
			values[k++] = value.k();
			values[k++] = value.l();
			values[k++] = value.i0();
			values[k++] = value.j0();
			values[k++] = value.k0();
		}
		return values;
	}

	@Override
	public double[] getAsDoubleArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat32Member value = G.OFLT.construct();
		double[] values = new double[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = value.r();
			values[k++] = value.i();
			values[k++] = value.j();
			values[k++] = value.k();
			values[k++] = value.l();
			values[k++] = value.i0();
			values[k++] = value.j0();
			values[k++] = value.k0();
		}
		return values;
	}

	@Override
	public BigInteger[] getAsBigIntegerArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat32Member value = G.OFLT.construct();
		BigInteger[] values = new BigInteger[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = BigDecimal.valueOf(value.r()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.i()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.j()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.k()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.l()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.i0()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.j0()).toBigInteger();
			values[k++] = BigDecimal.valueOf(value.k0()).toBigInteger();
		}
		return values;
	}

	@Override
	public BigDecimal[] getAsBigDecimalArray() {
		if (storage.size() > (Integer.MAX_VALUE / 8))
			throw new IllegalArgumentException(
					"internal data too large to be encoded in an array");
		OctonionFloat32Member value = G.OFLT.construct();
		BigDecimal[] values = new BigDecimal[8 * (int) storage.size()];
		for (int i = 0, k = 0; i < storage.size(); i++) {
			storage.get(i, value);
			values[k++] = BigDecimal.valueOf(value.r());
			values[k++] = BigDecimal.valueOf(value.i());
			values[k++] = BigDecimal.valueOf(value.j());
			values[k++] = BigDecimal.valueOf(value.k());
			values[k++] = BigDecimal.valueOf(value.l());
			values[k++] = BigDecimal.valueOf(value.i0());
			values[k++] = BigDecimal.valueOf(value.j0());
			values[k++] = BigDecimal.valueOf(value.k0());
		}
		return values;
	}

	@Override
	public boolean accessWithOneThread() {

		return storage.accessWithOneThread();
	}
	
	@Override
	public OctonionFloat32CartesianTensorProduct getAlgebra() {

		return G.OFLT_TEN;
	}
}
