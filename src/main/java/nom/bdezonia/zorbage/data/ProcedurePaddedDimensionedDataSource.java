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
package nom.bdezonia.zorbage.data;

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.coordinates.CoordinateSpace;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.metadata.MetaDataStore;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ProcedurePaddedDimensionedDataSource<T extends Algebra<T,U>,U>
	implements DimensionedDataSource<U>
{
	private final T algebra;
	private final DimensionedDataSource<U> dd;
	private final Procedure2<IntegerIndex,U> proc;
	private final ThreadLocal<U> tmp;
	
	/**
	 * 
	 * @param alg
	 * @param dd
	 * @param proc
	 */
	public ProcedurePaddedDimensionedDataSource(T alg, DimensionedDataSource<U> dd, Procedure2<IntegerIndex,U> proc) {
		this.algebra = alg;
		this.dd = dd;
		this.proc = proc;
		this.tmp = new ThreadLocal<U>() {
			@Override
			protected U initialValue() {
				return algebra.construct();
			}
		};
	}

	@Override
	public IndexedDataSource<U> rawData() {
		return dd.rawData();
	}

	@Override
	public int numDimensions() {
		return dd.numDimensions();
	}

	@Override
	public long dimension(int d) {
		return dd.dimension(d);
	}
	
	@Override
	public long numElements() {
		return dd.numElements();
	}

	@Override
	public IndexedDataSource<U> piped(int dim, IntegerIndex coord) {
		return dd.piped(dim, coord);
	}
	
	@Override
	public void set(IntegerIndex index, U value) {
		if (dd.oob(index)) {
			U t = tmp.get();
			proc.call(index,t);
			if (!algebra.isEqual().call(t,value))
				throw new IllegalArgumentException("Cannot set out of bounds value in conflict with out of bounds procedure");
		}
		else {
			dd.set(index, value);
		}
	}
	
	public void get(IntegerIndex index, U value) {
		if (dd.oob(index)) {
			proc.call(index, value);
		}
		else {
			dd.get(index, value);
		}
	}

	@Override
	public void safeSet(IntegerIndex index, U value) {
		dd.safeSet(index, value);
	}

	@Override
	public void safeGet(IntegerIndex index, U value) {
		dd.safeGet(index, value);
	}

	@Override
	public boolean oob(IntegerIndex index) {
		return dd.oob(index);
	}

	@Override
	public StorageConstruction storageType() {
		return dd.storageType();
	}
	
	@Override
	public MetaDataStore metadata() {
		return dd.metadata();
	}

	@Override
	public String getAxisUnit(int i) {
		return dd.getAxisUnit(i);
	}

	@Override
	public String getAxisType(int i) {
		return dd.getAxisType(i);
	}

	@Override
	public void setAxisUnit(int i, String unit) {
		dd.setAxisUnit(i, unit);
	}

	@Override
	public void setAxisType(int i, String type) {
		dd.setAxisType(i, type);
	}

	@Override
	public String getValueUnit() {
		return dd.getValueUnit();
	}

	@Override
	public void setValueUnit(String unit) {
		dd.setValueUnit(unit);
	}
	
	@Override
	public String getName() {
		return dd.getName();
	}
	
	@Override
	public void setName(String name) {
		dd.setName(name);
	}
	
	@Override
	public String getSource() {
		return dd.getSource();
	}
	
	@Override
	public void setSource(String locator) {
		dd.setSource(locator);
	}
	
	@Override
	public String getValueType() {
		return dd.getValueType();
	}
	
	@Override
	public void setValueType(String type) {
		dd.setValueType(type);
	}

	@Override
	public CoordinateSpace getCoordinateSpace() {
		return dd.getCoordinateSpace();
	}

	@Override
	public void setCoordinateSpace(CoordinateSpace cspace) {
		dd.setCoordinateSpace(cspace);
	}
}
