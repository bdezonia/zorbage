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
package nom.bdezonia.zorbage.type.highprec.real;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.multidim.IndexUtils;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingCartesianIntegerGrid;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Gettable;
import nom.bdezonia.zorbage.algebra.Settable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algebra.TensorMember;
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
public final class HighPrecisionCartesianTensorProductMember
	implements
		TensorMember<HighPrecisionMember>,
		Gettable<HighPrecisionCartesianTensorProductMember>,
		Settable<HighPrecisionCartesianTensorProductMember>,
		PrimitiveConversion, UniversalRepresentation,
		RawData<HighPrecisionMember>
{
	private static final HighPrecisionMember ZERO = new HighPrecisionMember();

	private int rank;
	private long dimCount;
	private IndexedDataSource<HighPrecisionMember> storage;
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

	public HighPrecisionCartesianTensorProductMember() {
		rank = 0;
		dimCount = 0;
		dims = new long[0];
		s = StorageConstruction.MEM_ARRAY;
		storage = Storage.allocate(s, 1, new HighPrecisionMember());
		multipliers = IndexUtils.calcMultipliers(dims);
	}

	public HighPrecisionCartesianTensorProductMember(int rank, long dimCount) {
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
		storage = Storage.allocate(s, numElems, new HighPrecisionMember());
		multipliers = IndexUtils.calcMultipliers(dims);
	}
	
	public HighPrecisionCartesianTensorProductMember(int rank, long dimCount, BigDecimal[] vals) {
		this(rank, dimCount);
		if (vals.length != storage.size())
			throw new IllegalArgumentException("incorrect number of values given in tensor constructor");
		HighPrecisionMember value = new HighPrecisionMember();
		for (int i = 0; i < vals.length; i++) {
			value.setV(vals[i]);
			storage.set(i/1, value);
		}
	}

	public HighPrecisionCartesianTensorProductMember(HighPrecisionCartesianTensorProductMember other) {
		set(other);
	}
	
	public HighPrecisionCartesianTensorProductMember(String s) {
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
		this.storage = Storage.allocate(this.s, numElems, new HighPrecisionMember());
		multipliers = IndexUtils.calcMultipliers(dims);
		HighPrecisionMember value = new HighPrecisionMember();
		if (numElems == 1) {
			// TODO: does a rank 0 tensor have any values from a parsing?
			OctonionRepresentation val = data.get(0);
			value.setV(val.r());
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
				value.setV(val.r());
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
	public void set(HighPrecisionCartesianTensorProductMember other) {
		if (this == other) return;
		rank = other.rank;
		dimCount = other.dimCount;
		dims = other.dims.clone();
		multipliers = other.multipliers.clone();
		storage = other.storage.duplicate();
		s = other.s;
	}
	
	@Override
	public void get(HighPrecisionCartesianTensorProductMember other) {
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
			storage = Storage.allocate(s, newCount, new HighPrecisionMember());
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
	
	void v(long index, HighPrecisionMember value) {
		storage.get(index, value);
	}
	
	@Override
	public void v(IntegerIndex index, HighPrecisionMember value) {
		long idx = IndexUtils.safeIndexToLong(dims, index);
		storage.get(idx, value);
	}
	
	void setV(long index, HighPrecisionMember value) {
		storage.set(index, value);
	}
	
	@Override
	public void setV(IntegerIndex index, HighPrecisionMember value) {
		long idx = IndexUtils.safeIndexToLong(dims, index);
		storage.set(idx, value);
	}
	
	@Override
	public void toRep(TensorOctonionRepresentation rep) {
		long storageSize = storage.size();
		HighPrecisionMember value = new HighPrecisionMember();
		BigList<OctonionRepresentation> values = new BigList<OctonionRepresentation>(storageSize, new OctonionRepresentation());
		for (long i = 0; i < storageSize; i++) {
			storage.get(i, value);
			BigDecimal re = value.v();
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
		HighPrecisionMember value = new HighPrecisionMember();
		BigList<OctonionRepresentation> tensor = rep.getTensor();
		init(rep.getTensorDims());
		long tensorSize = tensor.size();
		for (long i = 0; i < tensorSize; i++) {
			OctonionRepresentation o = tensor.get(i);
			value.setV(o.r());
			storage.set(i, value);
		}
	}

	// TODO: finish me
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		// iterate values/indices and write numbers, brackets, and commas in correct order
		// something recursive?
		HighPrecisionMember tmp = new HighPrecisionMember();
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
	
	private static ThreadLocal<HighPrecisionMember> tmpFloat =
			new ThreadLocal<HighPrecisionMember>()
	{
		protected HighPrecisionMember initialValue() {
			return new HighPrecisionMember();
		};
		
	};
	
	@Override
	public PrimitiveRepresentation preferredRepresentation() {
		return PrimitiveRepresentation.BIGDECIMAL;
	}

	@Override
	public int componentCount() {
		return 1;
	}

	@Override
	public void primComponentSetByte(IntegerIndex index, int component, byte v) {
		HighPrecisionMember tmp = tmpFloat.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetShort(IntegerIndex index, int component, short v) {
		HighPrecisionMember tmp = tmpFloat.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetInt(IntegerIndex index, int component, int v) {
		HighPrecisionMember tmp = tmpFloat.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetLong(IntegerIndex index, int component, long v) {
		HighPrecisionMember tmp = tmpFloat.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetFloat(IntegerIndex index, int component, float v) {
		HighPrecisionMember tmp = tmpFloat.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetDouble(IntegerIndex index, int component, double v) {
		HighPrecisionMember tmp = tmpFloat.get();
		tmp.setV(BigDecimal.valueOf(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v) {
		HighPrecisionMember tmp = tmpFloat.get();
		tmp.setV(new BigDecimal(v));
		setV(index, tmp);
	}

	@Override
	public void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v) {
		HighPrecisionMember tmp = tmpFloat.get();
		tmp.setV(v);
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
			HighPrecisionMember tmp = tmpFloat.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpFloat.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpFloat.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpFloat.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpFloat.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpFloat.get();
			tmp.setV(BigDecimal.valueOf(v));
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
			HighPrecisionMember tmp = tmpFloat.get();
			tmp.setV(new BigDecimal(v));
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
			HighPrecisionMember tmp = tmpFloat.get();
			tmp.setV(v);
			setV(index, tmp);
		}
	}

	@Override
	public byte primComponentGetAsByte(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().byteValue();
		}
		return 0;
	}

	@Override
	public short primComponentGetAsShort(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().shortValue();
		}
		return 0;
	}

	@Override
	public int primComponentGetAsInt(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().intValue();
		}
		return 0;
	}

	@Override
	public long primComponentGetAsLong(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().longValue();
		}
		return 0;
	}

	@Override
	public float primComponentGetAsFloat(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().floatValue();
		}
		return 0;
	}

	@Override
	public double primComponentGetAsDouble(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().doubleValue();
		}
		return 0;
	}

	@Override
	public BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().toBigInteger();
		}
		return BigInteger.ZERO;
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component) {
		if (component < 0)
			throw new IllegalArgumentException(
					"negative component index error");
		if (component == 0) {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v();
		}
		return BigDecimal.ZERO;
	}

	@Override
	public byte primComponentGetAsByteSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().byteValue();
		}
	}

	@Override
	public short primComponentGetAsShortSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().shortValue();
		}
	}

	@Override
	public int primComponentGetAsIntSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().intValue();
		}
	}

	@Override
	public long primComponentGetAsLongSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().longValue();
		}
	}

	@Override
	public float primComponentGetAsFloatSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().floatValue();
		}
	}

	@Override
	public double primComponentGetAsDoubleSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return 0;
		}
		else {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().doubleValue();
		}
	}

	@Override
	public BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return BigInteger.ZERO;
		}
		else {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v().toBigInteger();
		}
	}

	@Override
	public BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component) {
		if (IndexUtils.componentOob(dims, index, component, 1)) {
			return BigDecimal.ZERO;
		}
		else {
			HighPrecisionMember tmp = tmpFloat.get();
			v(index, tmp);
			return tmp.v();
		}
	}

	@Override
	public void primitiveInit() {
		long storageSize = storage.size();
		for (long i = 0; i < storageSize; i++)
			storage.set(i, ZERO);
	}

	@Override
	public IndexedDataSource<HighPrecisionMember> rawData() {
		return storage;
	}

	@Override
	public int hashCode() {
		HighPrecisionMember tmp = G.HP.construct();
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
		if (this == o)
			return true;
		if (o instanceof HighPrecisionCartesianTensorProductMember) {
			return G.HP_TEN.isEqual().call(this, (HighPrecisionCartesianTensorProductMember) o);
		}
		return false;
	}
}