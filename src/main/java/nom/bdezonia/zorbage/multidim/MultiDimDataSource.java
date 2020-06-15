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

import nom.bdezonia.zorbage.algebra.Dimensioned;
import nom.bdezonia.zorbage.algebra.StorageType;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.RawData;

/**
 * 
 * @author Barry DeZonia
 *
 */
public interface MultiDimDataSource<U>
	extends Dimensioned, RawData<U>, StorageType
{
	long numElements();

	Procedure2<Long,HighPrecisionMember> getAxis(int i);
	
	String getAxisUnit(int i);  // "cm", "mile", "parsec", etc.
	
	String getAxisType(int i);  // "x", "y", "lat", "lon", "t", "chan", "band", "z", etc.
	
	void setAxis(int i, Procedure2<Long,HighPrecisionMember> proc);
	
	void setAxisUnit(int i, String unit);  // "cm", "mile", "parsec", etc.
	
	void setAxisType(int i, String type);  // "x", "y", "lat", "lon", "t", "chan", "band", "z", etc.
	
	IndexedDataSource<U> piped(int dim, IntegerIndex coord);
	
	void set(IntegerIndex index, U value);
	
	void setSafe(IntegerIndex index, U value);
	
	void get(IntegerIndex index, U value);
	
	void getSafe(IntegerIndex index, U value);
	
	boolean oob(IntegerIndex index);
	
	Map<String,String> metadata();
	
}
