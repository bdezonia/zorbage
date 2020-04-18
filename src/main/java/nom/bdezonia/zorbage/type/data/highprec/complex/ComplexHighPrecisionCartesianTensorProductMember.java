/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.highprec.complex;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.algebra.Gettable;
import nom.bdezonia.zorbage.type.algebra.Settable;
import nom.bdezonia.zorbage.type.algebra.TensorMember;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.universal.OctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorOctonionRepresentation;
import nom.bdezonia.zorbage.type.data.universal.TensorStringRepresentation;
import nom.bdezonia.zorbage.type.data.universal.UniversalRepresentation;
import nom.bdezonia.zorbage.type.storage.Storage;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.RawData;


// TODO:
//   rank 0 tensor getting and setting 1 value instead of 0
//   upper and lower indices: only if not CartesianTensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public final class ComplexHighPrecisionCartesianTensorProductMember
	implements
		TensorMember<ComplexHighPrecisionMember>,
		Gettable<ComplexHighPrecisionCartesianTensorProductMember>,
		Settable<ComplexHighPrecisionCartesianTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<ComplexHighPrecisionMember>
{
	private static final ComplexHighPrecisionMember ZERO = new ComplexHighPrecisionMember();

	private int rank;
	private long dimCount;
	private IndexedDataSource<ComplexHighPrecisionMember> storage;
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
		if (index < 0 || index > lowerRank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return true;
	}
	
	@Override
	public boolean indexIsUpper(int index) {
		if (index < 0 || index > upperRank())
			throw new IllegalArgumentException("index of tensor component is outside bounds");
		return false;
	}

	@Override
	public long dimension() { return dimCount; }

	public ComplexHighPrecisionCartesianTensorProductMember() {
		rank = 0;
		dimCount = 0;
		dims = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, 1, new ComplexHighPrecisionMember());
		multipliers = calcMultipliers();
	}

	public ComplexHighPrecisionCartesianTensorProductMember(int rank, long dimCount) {
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
		storage = Storage.allocate(s, numElems, new ComplexHighPrecisionMember());
		multipliers = calcMultipliers();
	}
	
	public ComplexHighPrecisionCartesianTensorProductMember(int rank, long dimCount, BigDecimal[] vals) {
		this(rank, dimCount);
		if (vals.length != storage.size()*2)
			throw new IllegalArgumentException("incorrect number of values given in tensor constructor");
		ComplexHighPrecisionMember value = new ComplexHighPrecisionMember();
		for (int i = 0; i < vals.length; i+=2) {
			value.setR(vals[i]);
			value.setI(vals[i+1]);
			storage.set(i, value);
		}
	}

	public ComplexHighPrecisionCartesianTensorProductMember(long[] dims) {
		this.rank = dims.length;
		long max = 0;
		for (long d : dims) {
			if (max < d)
				max = d;
		}
		this.dimCount = max;
		this.dims = new long[rank];
		for (int i = 0; i < rank; i++) {
			this.dims[i] = dimCount;
		}
		long numElems = LongUtils.numElements(this.dims);
		if (numElems == 0) numElems = 1;
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, numElems, new ComplexHighPrecisionMember());
		multipliers = calcMultipliers();
	}
	
	public ComplexHighPrecisionCartesianTensorProductMember(long[] dims, BigDecimal[] vals) {
		this(dims);
		long numElems = LongUtils.numElements(dims);
		if (numElems == 0) numElems = 1;
		if (vals.length != numElems*2)
			throw new IllegalArgumentException("incorrect number of values provided to tensor constructor");
		ComplexHighPrecisionMember value = new ComplexHighPrecisionMember();
		if (numElems == 1) {
			value.setR(vals[0]);
			value.setI(vals[1]);
			storage.set(0, value);
		}
		else {
			long[] point1 = new long[dims.length];
			long[] point2 = new long[dims.length];
			for (int i = 0; i < dims.length; i++) {
				point2[i] = dims[i] - 1;
			}
			int i = 0;
			SamplingCartesianIntegerGrid sampling = new SamplingCartesianIntegerGrid(point1, point2);
			SamplingIterator<IntegerIndex> iter = sampling.iterator();
			IntegerIndex index = new IntegerIndex(dims.length);
			while (iter.hasNext()) {
				iter.next(index);
				value.setR(vals[i]);
				value.setI(vals[i+1]);
				long idx = indexToLong(index);
				storage.set(idx, value);
				i+=2;
			}
		}
	}
	
	public ComplexHighPrecisionCartesianTensorProductMember(ComplexHighPrecisionCartesianTensorProductMember other) {
		set(other);
	}
	
	public ComplexHighPrecisionCartesianTensorProductMember(String s) {
		TensorStringRepresentation rep = new TensorStringRepresentation(s);
		BigList<OctonionRepresentation> data = rep.values();
		long[] tmpDims = rep.dimensions().clone();
		this.rank = tmpDims.length;
		long max = 0;
		for (long d : tmpDims) {
			if (max < d)
				max = d;
		}
		this.dimCount = max;
		this.dims = new long[rank];
		for (int i = 0; i < rank; i++) {
			this.dims[i] = dimCount;
		}
		long numElems = LongUtils.numElements(this.dims);
		if (numElems == 0) numElems = 1;
		this.s = StorageConstruction.MEM_ARRAY;
		this.storage = Storage.allocate(this.s, numElems, new ComplexHighPrecisionMember());
		this.multipliers = calcMultipliers();
		ComplexHighPrecisionMember value = new ComplexHighPrecisionMember();
		if (numElems == 1) {
			// TODO: does a rank 0 tensor have any values from a parsing?
			OctonionRepresentation val = data.get(0);
			value.setR(val.r());
			value.setI(val.i());
			storage.set(0, value);
		}
		else {
			long[] point1 = new long[tmpDims.length];
			long[] point2 = new long[tmpDims.length];
			for (int i = 0; i < tmpDims.length; i++) {
				point2[i] = tmpDims[i] - 1;
			}
			long i = 0;
			SamplingCartesianIntegerGrid sampling = new SamplingCartesianIntegerGrid(point1, point2);
			SamplingIterator<IntegerIndex> iter = sampling.iterator();
			IntegerIndex index = new IntegerIndex(dims.length);
			while (iter.hasNext()) {
				iter.next(index);
				OctonionRepresentation val = data.get(i);
				value.setR(val.r());
				value.setI(val.i());
				long idx = indexToLong(index);
				storage.set(idx, value);
				i++;
			}
		}
	}
	
	public ComplexHighPrecisionCartesianTensorProductMember(StorageConstruction s, long[] nd) {
		this.rank = dims.length;
		long max = 0;
		for (long d : dims) {
			if (max < d)
				max = d;
		}
		this.dimCount = max;
		this.dims = new long[rank];
		for (int i = 0; i < rank; i++) {
			this.dims[i] = dimCount;
		}
		this.multipliers = calcMultipliers();
		this.s = s;
		long numElems = LongUtils.numElements(this.dims);
		if (numElems == 0) numElems = 1;
		this.storage = Storage.allocate(s, numElems, new ComplexHighPrecisionMember());
	}
	
	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void set(ComplexHighPrecisionCartesianTensorProductMember other) {
		if (this == other) return;
		rank = other.rank;
		dimCount = other.dimCount;
		dims = other.dims.clone();
		multipliers = other.multipliers.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(ComplexHighPrecisionCartesianTensorProductMember other) {
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
		long max = 0;
		for (long d : newDims) {
			if (max < d)
				max = d;
		}
		this.dimCount = max;
		this.dims = new long[rank];
		for (int i = 0; i < rank; i++) {
			this.dims[i] = dimCount;
		}
		this.multipliers = calcMultipliers();
		long newCount = LongUtils.numElements(this.dims);
		if (newCount == 0) newCount = 1;
		if (storage == null || newCount != storage.size()) {
			storage = Storage.allocate(s, newCount, new ComplexHighPrecisionMember());
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
	
	void v(long index, ComplexHighPrecisionMember value) {
		storage.get(index, value);
	}
	
	@Override
	public void v(IntegerIndex index, ComplexHighPrecisionMember value) {
		long idx = indexToLong(index);
		storage.get(idx, value);
	}
	
	void setV(long index, ComplexHighPrecisionMember value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, ComplexHighPrecisionMember value) {
		long idx = indexToLong(index);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		ComplexHighPrecisionMember value = new ComplexHighPrecisionMember();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize);
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = value.r();
			BigDecimal im = value.i();
			OctonionRepresentation o = new OctonionRepresentation(re,im);
			values.set(i, o);
		}
		rep.setTensor(dims, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		ComplexHighPrecisionMember value = new ComplexHighPrecisionMember();
		BigList<OctonionRepresentation> tensor = rep.getTensor();
		init(rep.getTensorDims());
		long tensorSize = tensor.size();
		for (long i = 0; i < tensorSize; i++) {
			OctonionRepresentation o = tensor.get(i);
			value.setR(o.r());
			value.setI(o.i());
			storage.set(i,value);
		}
	}

	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		ComplexHighPrecisionMember tmp = new ComplexHighPrecisionMember();
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
			longToIntegerIndex(i, index);
			int j = 0;
			while (j < index.numDimensions() && index.get(j++) == 0)
				builder.append('[');
			if (index.get(0) != 0)
				builder.append(',');
			builder.append('{');
			builder.append(tmp.r());
			builder.append(',');
			builder.append(tmp.i());
			builder.append('}');
			j = 0;
			while (j < index.numDimensions() && index.get(j) == (dims[j++]-1))
				builder.append(']');
		}
		return builder.toString();
	}

	private long[] calcMultipliers() {
		if (dims.length == 0)
			return new long[0];
		long[] result = new long[dims.length-1];
		long mult = 1;
		for (int i = 0; i < result.length; i++) {
			result[i] = mult;
			mult *= dims[i];
		}
		return result;
	}
	
	/*
	 * dims = [4,5,6]
	 * idx = [1,2,3]
	 * long = 3*5*4 + 2*4 + 1;
	 */
	private long indexToLong(IntegerIndex idx) {
		if (idx.numDimensions() == 0)
			throw new IllegalArgumentException("null index");
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
			result.set(i, 0);
		}
		for (int i = dims.length-1; i >= 0; i--) {
			result.set(i, idx / multipliers[i]);
			idx = idx % multipliers[i];
		}
	}

	private boolean indexOob(IntegerIndex idx, int component) {
		if (component < 0)
			throw new IllegalArgumentException("negative component specified in indexOob");
		if (component > 1)
			return true;
		for (int i = 0; i < dims.length; i++) {
			final long index = idx.get(i);
			if (index < 0)
				throw new IllegalArgumentException("negative index in indexOob");
			if (index >= dims[i])
				return true;
		}
		for (int i = dims.length; i < idx.numDimensions(); i++) {
			final long index = idx.get(i);
			if (index < 0)
				throw new IllegalArgumentException("negative index in indexOob");
			if (index > 0)
				return true;
		}
		return false;
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
	
	private static ThreadLocal<ComplexHighPrecisionMember> tmpComp =
			new ThreadLocal<ComplexHighPrecisionMember>()
	{
		protected ComplexHighPrecisionMember initialValue() {
			return new ComplexHighPrecisionMember();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else if (component == 1)
			tmp.setI(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else if (component == 1)
			tmp.setI(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else if (component == 1)
			tmp.setI(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else if (component == 1)
			tmp.setI(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else if (component == 1)
			tmp.setI(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(BigDecimal.valueOf(v));
		else if (component == 1)
			tmp.setI(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(new BigDecimal(v));
		else if (component == 1)
			tmp.setI(new BigDecimal(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (indexOob(index, component)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else if (component == 1)
				tmp.setI(BigDecimal.valueOf(v));
			setV(index, tmp);
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
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else if (component == 1)
				tmp.setI(BigDecimal.valueOf(v));
			setV(index, tmp);
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
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else if (component == 1)
				tmp.setI(BigDecimal.valueOf(v));
			setV(index, tmp);
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
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else if (component == 1)
				tmp.setI(BigDecimal.valueOf(v));
			setV(index, tmp);
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
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else if (component == 1)
				tmp.setI(BigDecimal.valueOf(v));
			setV(index, tmp);
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
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(BigDecimal.valueOf(v));
			else if (component == 1)
				tmp.setI(BigDecimal.valueOf(v));
			setV(index, tmp);
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
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(new BigDecimal(v));
			else if (component == 1)
				tmp.setI(new BigDecimal(v));
			setV(index, tmp);
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
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			setV(index, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r().byteValue();
		else if (component == 1)
			return tmp.i().byteValue();
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r().shortValue();
		else if (component == 1)
			return tmp.i().shortValue();
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r().intValue();
		else if (component == 1)
			return tmp.i().intValue();
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r().longValue();
		else if (component == 1)
			return tmp.i().longValue();
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r().floatValue();
		else if (component == 1)
			return tmp.i().floatValue();
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r().doubleValue();
		else if (component == 1)
			return tmp.i().doubleValue();
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r().toBigInteger();
		else if (component == 1)
			return tmp.i().toBigInteger();
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexHighPrecisionMember tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r();
		else if (component == 1)
			return tmp.i();
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r().byteValue();
			else
				return tmp.i().byteValue();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r().shortValue();
			else
				return tmp.i().shortValue();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r().intValue();
			else
				return tmp.i().intValue();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r().longValue();
			else
				return tmp.i().longValue();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r().floatValue();
			else
				return tmp.i().floatValue();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return 0;
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r().doubleValue();
			else
				return tmp.i().doubleValue();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return BigInteger.ZERO;
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r().toBigInteger();
			else
				return tmp.i().toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (indexOob(index, component)) {
			return BigDecimal.ZERO;
		}
		else {
			ComplexHighPrecisionMember tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r();
			else
				return tmp.i();
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<ComplexHighPrecisionMember> rawData() {
		return storage;
	}
}