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
package nom.bdezonia.zorbage.type.data.float16.complex;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.multidim.IndexUtils;
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
public final class ComplexFloat16CartesianTensorProductMember
	implements
		TensorMember<ComplexFloat16Member>,
		Gettable<ComplexFloat16CartesianTensorProductMember>,
		Settable<ComplexFloat16CartesianTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<ComplexFloat16Member>
{
	private static final ComplexFloat16Member ZERO = new ComplexFloat16Member();

	private int rank;
	private long dimCount;
	private IndexedDataSource<ComplexFloat16Member> storage;
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

	public ComplexFloat16CartesianTensorProductMember() {
		rank = 0;
		dimCount = 0;
		dims = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, 1, new ComplexFloat16Member());
		this.multipliers = IndexUtils.calcMultipliers(dims);
	}

	public ComplexFloat16CartesianTensorProductMember(int rank, long dimCount) {
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
		storage = Storage.allocate(s, numElems, new ComplexFloat16Member());
		this.multipliers = IndexUtils.calcMultipliers(dims);
	}
	
	public ComplexFloat16CartesianTensorProductMember(int rank, long dimCount, float[] vals) {
		this(rank, dimCount);
		if (vals.length != storage.size()*2)
			throw new IllegalArgumentException("incorrect number of values given in tensor constructor");
		ComplexFloat16Member value = new ComplexFloat16Member();
		for (int i = 0; i < vals.length; i+=2) {
			value.setR(vals[i]);
			value.setI(vals[i+1]);
			storage.set(i, value);
		}
	}

	public ComplexFloat16CartesianTensorProductMember(ComplexFloat16CartesianTensorProductMember other) {
		set(other);
	}
	
	public ComplexFloat16CartesianTensorProductMember(String s) {
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
		this.storage = Storage.allocate(this.s, numElems, new ComplexFloat16Member());
		this.multipliers = IndexUtils.calcMultipliers(dims);
		ComplexFloat16Member value = new ComplexFloat16Member();
		if (numElems == 1) {
			// TODO: does a rank 0 tensor have any values from a parsing?
			OctonionRepresentation val = data.get(0);
			value.setR(val.r().floatValue());
			value.setI(val.i().floatValue());
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
				value.setR(val.r().floatValue());
				value.setI(val.i().floatValue());
				long idx = IndexUtils.safeIndexToLong(dims, index, 0, 2);
				storage.set(idx, value);
				i++;
			}
		}
	}
	
	public ComplexFloat16CartesianTensorProductMember(StorageConstruction s, long[] nd) {
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
		this.multipliers = IndexUtils.calcMultipliers(dims);
		this.s = s;
		long numElems = LongUtils.numElements(this.dims);
		if (numElems == 0) numElems = 1;
		this.storage = Storage.allocate(s, numElems, new ComplexFloat16Member());
	}
	
	@Override
	public StorageConstruction storageType() {
		return s;
	}
	
	@Override
	public void set(ComplexFloat16CartesianTensorProductMember other) {
		if (this == other) return;
		rank = other.rank;
		dimCount = other.dimCount;
		dims = other.dims.clone();
		multipliers = other.multipliers.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(ComplexFloat16CartesianTensorProductMember other) {
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
		this.multipliers = IndexUtils.calcMultipliers(dims);
		long newCount = LongUtils.numElements(this.dims);
		if (newCount == 0) newCount = 1;
		if (storage == null || newCount != storage.size()) {
			storage = Storage.allocate(s, newCount, new ComplexFloat16Member());
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
	
	void v(long index, ComplexFloat16Member value) {
		storage.get(index, value);
	}
	
	@Override
	public void v(IntegerIndex index, ComplexFloat16Member value) {
		long idx = IndexUtils.safeIndexToLong(dims, index, 0, 2);
		storage.get(idx, value);
	}
	
	void setV(long index, ComplexFloat16Member value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, ComplexFloat16Member value) {
		long idx = IndexUtils.safeIndexToLong(dims, index, 0, 2);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		ComplexFloat16Member value = new ComplexFloat16Member();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize);
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = BigDecimal.valueOf(value.r());
			BigDecimal im = BigDecimal.valueOf(value.i());
			OctonionRepresentation o = new OctonionRepresentation(re,im);
			values.set(i, o);
		}
		rep.setTensor(dims, values);
	}

	@Override
	public void fromRep(TensorOctonionRepresentation rep) {
		ComplexFloat16Member value = new ComplexFloat16Member();
		BigList<OctonionRepresentation> tensor = rep.getTensor();
		init(rep.getTensorDims());
		long tensorSize = tensor.size();
		for (long i = 0; i < tensorSize; i++) {
			OctonionRepresentation o = tensor.get(i);
			value.setR(o.r().floatValue());
			value.setI(o.i().floatValue());
			storage.set(i,value);
		}
	}

	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		ComplexFloat16Member tmp = new ComplexFloat16Member();
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
	
	private static ThreadLocal<ComplexFloat16Member> tmpComp =
			new ThreadLocal<ComplexFloat16Member>()
	{
		protected ComplexFloat16Member initialValue() {
			return new ComplexFloat16Member();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.FLOAT;
	}

	@Override
	public int componentCount() {
		return 2;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(v);
		else if (component == 1)
			tmp.setI(v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR((float) v);
		else if (component == 1)
			tmp.setI((float) v);
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(v.floatValue());
		else if (component == 1)
			tmp.setI(v.floatValue());
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			tmp.setR(v.floatValue());
		else if (component == 1)
			tmp.setI(v.floatValue());
		setV(index, tmp);
	}

	@Override
	public void primComponentSetByteSafe(IntegerIndex index, int component, byte v) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetShortSafe(IntegerIndex index, int component, short v) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetIntSafe(IntegerIndex index, int component, int v) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetLongSafe(IntegerIndex index, int component, long v) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetFloatSafe(IntegerIndex index, int component, float v) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(v);
			else if (component == 1)
				tmp.setI(v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetDoubleSafe(IntegerIndex index, int component, double v) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			if (v != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR((float) v);
			else if (component == 1)
				tmp.setI((float) v);
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(v.floatValue());
			else if (component == 1)
				tmp.setI(v.floatValue());
			setV(index, tmp);
		}
	}

	@Override
	public void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			if (v.signum() != 0)
				throw new IllegalArgumentException(
						"cannot set nonzero value outside extents");
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				tmp.setR(v.floatValue());
			else if (component == 1)
				tmp.setI(v.floatValue());
			setV(index, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return (byte) tmp.r();
		else if (component == 1)
			return (byte) tmp.i();
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return (short) tmp.r();
		else if (component == 1)
			return (short) tmp.i();
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return (int) tmp.r();
		else if (component == 1)
			return (int) tmp.i();
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return (long) tmp.r();
		else if (component == 1)
			return (long) tmp.i();
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r();
		else if (component == 1)
			return tmp.i();
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return tmp.r();
		else if (component == 1)
			return tmp.i();
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return BigDecimal.valueOf(tmp.r()).toBigInteger();
		else if (component == 1)
			return BigDecimal.valueOf(tmp.i()).toBigInteger();
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		ComplexFloat16Member tmp = tmpComp.get();
		v(index, tmp);
		if (component == 0)
			return BigDecimal.valueOf(tmp.r());
		else if (component == 1)
			return BigDecimal.valueOf(tmp.i());
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			return 0;
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return (byte) tmp.r();
			else
				return (byte) tmp.i();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			return 0;
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return (short) tmp.r();
			else
				return (short) tmp.i();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			return 0;
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return (int) tmp.r();
			else
				return (int) tmp.i();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			return 0;
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return (long) tmp.r();
			else
				return (long) tmp.i();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			return 0;
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r();
			else
				return tmp.i();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			return 0;
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return tmp.r();
			else
				return tmp.i();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			return BigInteger.ZERO;
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return BigDecimal.valueOf(tmp.r()).toBigInteger();
			else
				return BigDecimal.valueOf(tmp.i()).toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (IndexUtils.indexOob(dims, index, component, 2)) {
			return BigDecimal.ZERO;
		}
		else {
			ComplexFloat16Member tmp = tmpComp.get();
			v(index, tmp);
			if (component == 0)
				return BigDecimal.valueOf(tmp.r());
			else
				return BigDecimal.valueOf(tmp.i());
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<ComplexFloat16Member> rawData() {
		return storage;
	}
}
