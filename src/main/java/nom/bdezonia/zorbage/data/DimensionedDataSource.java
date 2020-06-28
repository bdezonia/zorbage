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
public interface DimensionedDataSource<U>
	extends Dimensioned, RawData<U>, StorageType
{
	long numElements();

	/**
	 * get the optional name of the data source
	 */
	String getName();
	
	/**
	 * set the optional name of the data source
	 */
	void setName(String name);

	/**
	 * get the optional source of the data (i.e. filename or URI etc.)
	 */
	String getSource();
	
	/**
	 * set the optional source of the data (i.e. filename or URI etc.)
	 */
	void setSource(String locator);

	/**
	 * Get the value type of the stored data. For example, "temperature", "density", "distance", etc.
	 */
	String getValueType();
	
	/**
	 * Set the value type of the stored data. For example, "temperature", "density", "distance", etc.
	 */
	void setValueType(String type);
	
	/**
	 * Get the value unit of the stored data. For example, "degrees C", "seconds", "light years", etc. 
	 */
	String getValueUnit();
	
	/**
	 * Set the value unit of the stored data. For example, "degrees C", "seconds", "light years", etc. 
	 */
	void setValueUnit(String unit);
	
	/**
	 * Get the equation for a given dim that scales the long spaced grid into high precision real space
	 */
	Procedure2<Long,HighPrecisionMember> getAxisEquation(int i);
	
	/**
	 * Set the equation for a given dim that scales the long spaced grid into high precision real space
	 */
	void setAxisEquation(int i, Procedure2<Long,HighPrecisionMember> proc);
	
	/**
	 * Get the unit along a given dimension's axis. For example, "cm", "mile", "parsec", etc. 
	 */
	String getAxisUnit(int i);
	
	/**
	 * Set the unit along a given dimension's axis. For example, "cm", "mile", "parsec", etc. 
	 */
	void setAxisUnit(int i, String unit);
	
	/**
	 * Get the type of data along a given axis. For example, "x", "y", "lat", "lon", "t", "chan", "band", "z", etc. 
	 */
	String getAxisType(int i);
	
	/**
	 * Set the type of data along a given axis. For example, "x", "y", "lat", "lon", "t", "chan", "band", "z", etc. 
	 */
	void setAxisType(int i, String type);  // "x", "y", "lat", "lon", "t", "chan", "band", "z", etc.
	
	/**
	 * Get a value in the structure
	 */
	void get(IntegerIndex index, U value);
	
	/**
	 * Get a value in the structure. Throw exceptions for out of bounds access.
	 */
	void getSafe(IntegerIndex index, U value); // get a value in the data structure (exception on oob)
	
	/**
	 * Set a value in the structure
	 */
	void set(IntegerIndex index, U value);
	
	/**
	 * Set a value in the structure. Throw exceptions for out of bounds access.
	 */
	void setSafe(IntegerIndex index, U value);

	/**
	 * Test if a given index is outside the defined bounds of the data structure
	 */
	boolean oob(IntegerIndex index);
	
	/**
	 * Return a 1-d dataset for a given index and dimension within the index
	 */
	IndexedDataSource<U> piped(int dim, IntegerIndex coord); // get a 1-d slice for a given coord along a given dimension

	/**
	 * Provide read/write access to further metadata of the data source
	 */
	Map<String,String> metadata();
}
