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
package nom.bdezonia.zorbage.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nom.bdezonia.zorbage.axis.IdentityAxisEquation;
import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NdData<U>
	implements DimensionedDataSource<U>
{
	private String name;
	private final long[] dims;
	private final IndexedDataSource<U> data;
	private final List<Procedure2<Long,HighPrecisionMember>> axisEqns;
	private final String[] axisUnits;
	private final String[] axisTypes;
	private String valueType;
	private String valueUnit;
	private final Map<String,String> metadata;

	// Ideally, every U type could have a getRepresentation() that for bytes would return
	// "signed 8-bit byte" and for float64 matrix "64-bit float matrix" etc. Then one can
	// query the U data rep for any DimensionedDataSource. But this violates the goal of
	// having dumb U's everywhere. Changing this would affect a lot of code and might not
	// have a big payoff. Think what is best here.
	
	/**
	 * 
	 * @param dims
	 * @param data
	 */
	public NdData(long[] dims, IndexedDataSource<U> data) {
		this.name = null;
		if (dims.length == 0)
			throw new IllegalArgumentException("dimensioned data source must have 1 or more dimensions");
		if (LongUtils.numElements(dims) != data.size())
			throw new IllegalArgumentException("num elements within stated dimensions do not match size of given data source");
		this.dims = dims;
		this.data = data;
		this.axisEqns = new ArrayList<Procedure2<Long,HighPrecisionMember>>();
		for (int i = 0; i < dims.length; i++)
			this.axisEqns.add(new IdentityAxisEquation());
		this.axisUnits = new String[dims.length];
		this.axisTypes = new String[dims.length];
		this.valueType = null;
		this.valueUnit = null;
		this.metadata = new HashMap<>();
	}
	
	@Override
	public IndexedDataSource<U> rawData() {
		return data;
	}

	@Override
	public int numDimensions() {
		return dims.length;
	}

	@Override
	public long dimension(int d) {
		if (d < 0) throw new IllegalArgumentException("negative index exception");
		if (d < dims.length) return dims[d];
		return 1;
	}
	
	@Override
	public long numElements() {
		return data.size();
	}

	@Override
	public Procedure2<Long,HighPrecisionMember> getAxisEquation(int i) {
		return this.axisEqns.get(i);
	}
	
	@Override
	public void setAxisEquation(int i, Procedure2<Long,HighPrecisionMember> proc) {
		this.axisEqns.set(i, proc);
	}
	
	@Override
	public IndexedDataSource<U> piped(int dim, IntegerIndex coord) {
		return new PipedDataSource<U>(this, dim, coord);
	}
	
	@Override
	public void set(IntegerIndex index, U value) {
		long idx = IndexUtils.indexToLong(dims, index);
		data.set(idx, value);
	}
	
	@Override
	public void setSafe(IntegerIndex index, U value) {
		if (oob(index))
			throw new IllegalArgumentException("index out of bounds of multidim dimensions");
		set(index, value);
	}
	
	@Override
	public void get(IntegerIndex index, U value) {
		long idx = IndexUtils.indexToLong(dims, index);
		data.get(idx, value);
	}
	
	@Override
	public void getSafe(IntegerIndex index, U value) {
		if (oob(index))
			throw new IllegalArgumentException("index out of bounds of multidim dimensions");
		get(index, value);
	}
	
	@Override
	public boolean oob(IntegerIndex index) {
		if (index.numDimensions() != numDimensions())
			throw new IllegalArgumentException("index dimensionality not the same as multidim dimensions");
		for (int i = 0; i < index.numDimensions(); i++) {
			if (index.get(i) < 0 || index.get(i) >= dimension(i))
				return true;
		}
		return false;
	}

	@Override
	public StorageConstruction storageType() {
		return data.storageType();
	}
	
	@Override
	public Map<String,String> metadata() {
		return metadata;
	}

	@Override
	public String getAxisUnit(int i) {
		return axisUnits[i];
	}

	@Override
	public String getAxisType(int i) {
		return axisTypes[i];
	}

	@Override
	public void setAxisUnit(int i, String unit) {
		this.axisUnits[i] = unit;
	}

	@Override
	public void setAxisType(int i, String type) {
		this.axisTypes[i] = type;
	}

	@Override
	public String getValueUnit() {
		return valueUnit;
	}

	@Override
	public void setValueUnit(String unit) {
		this.valueUnit = unit;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getValueType() {
		return valueType;
	}
	
	@Override
	public void setValueType(String type) {
		this.valueType = type;
	}
}