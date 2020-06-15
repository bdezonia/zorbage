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
package nom.bdezonia.zorbage.multidim;

import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

import java.util.Map;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ProcedurePaddedMultiDimDataSource<T extends Algebra<T,U>,U>
	implements MultiDimDataSource<U>
{
	private final T algebra;
	private final MultiDimDataSource<U> md;
	private final Procedure2<IntegerIndex,U> proc;
	private final ThreadLocal<U> tmp;
	
	/**
	 * 
	 * @param alg
	 * @param md
	 * @param proc
	 */
	public ProcedurePaddedMultiDimDataSource(T alg, MultiDimDataSource<U> md, Procedure2<IntegerIndex,U> proc) {
		this.algebra = alg;
		this.md = md;
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
		return md.rawData();
	}

	@Override
	public int numDimensions() {
		return md.numDimensions();
	}

	@Override
	public long dimension(int d) {
		return md.dimension(d);
	}
	
	@Override
	public long numElements() {
		return md.numElements();
	}

	@Override
	public Procedure2<Long,HighPrecisionMember> getAxis(int i) {
		return md.getAxis(i);
	}
	
	@Override
	public void setAxis(int i, Procedure2<Long,HighPrecisionMember> proc) {
		md.setAxis(i, proc);
	}
	
	@Override
	public IndexedDataSource<U> piped(int dim, IntegerIndex coord) {
		return md.piped(dim, coord);
	}
	
	@Override
	public void set(IntegerIndex index, U value) {
		if (md.oob(index)) {
			U t = tmp.get();
			proc.call(index,t);
			if (!algebra.isEqual().call(t,value))
				throw new IllegalArgumentException("Cannot set out of bounds value in conflict with out of bounds procedure");
		}
		else {
			md.set(index, value);
		}
	}
	
	public void get(IntegerIndex index, U value) {
		if (md.oob(index)) {
			proc.call(index, value);
		}
		else {
			md.get(index, value);
		}
	}

	@Override
	public void setSafe(IntegerIndex index, U value) {
		md.setSafe(index, value);
	}

	@Override
	public void getSafe(IntegerIndex index, U value) {
		md.getSafe(index, value);
	}

	@Override
	public boolean oob(IntegerIndex index) {
		return md.oob(index);
	}

	@Override
	public StorageConstruction storageType() {
		return md.storageType();
	}
	
	@Override
	public Map<String, String> metadata() {
		return md.metadata();
	}

	@Override
	public String getAxisUnit(int i) {
		return md.getAxisUnit(i);
	}

	@Override
	public String getAxisType(int i) {
		return md.getAxisType(i);
	}

	@Override
	public void setAxisUnit(int i, String unit) {
		md.setAxisUnit(i, unit);
	}

	@Override
	public void setAxisType(int i, String type) {
		md.setAxisType(i, type);
	}

	@Override
	public String getValueUnit() {
		return md.getValueUnit();
	}

	@Override
	public void setValueUnit(String unit) {
		md.setValueUnit(unit);
	}
}
