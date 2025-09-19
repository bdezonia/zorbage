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

import nom.bdezonia.zorbage.misc.LongUtils;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.coordinates.CoordinateSpace;
import nom.bdezonia.zorbage.coordinates.IdentityCoordinateSpace;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.metadata.MetaDataStore;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class NdData<U>
	implements DimensionedDataSource<U>
{
	private String name;
	private String source;
	private final long[] dims;
	private final IndexedDataSource<U> data;
	private CoordinateSpace space;
	private final String[] axisUnits;
	private final String[] axisTypes;
	private String valueType;
	private String valueUnit;
	private final MetaDataStore metadata;

	/**
	 * Wrap any 1-d list as a multidimensional n-d data source. The
	 * number of elements in the list must be compatible with the
	 * number of elements represented by the passed in dimensions.
	 * 
	 * @param dims
	 * @param data
	 */
	public NdData(long[] dims, IndexedDataSource<U> data) {
		if (dims.length == 0)
			throw new IllegalArgumentException("dimensioned data source must have 1 or more dimensions");
		if (LongUtils.numElements(dims) != data.size())
			throw new IllegalArgumentException("num elements within stated dimensions do not match size of given data source");
		this.dims = dims;
		this.data = data;
		this.axisUnits = new String[dims.length];
		this.axisTypes = new String[dims.length];
		this.metadata = new MetaDataStore();

		setName(null);
		setSource(null);
		setValueType(null);
		setValueUnit(null);
		setCoordinateSpace(new IdentityCoordinateSpace(dims.length));
		for (int i = 0; i < dims.length; i++) {
			setAxisUnit(i, null);
			setAxisType(i, null);
		}
	}
	
	/**
	 * Returns the 1-d list that resides within the core of this
	 * multidim data set and which stores all its values.
	 */
	@Override
	public IndexedDataSource<U> rawData() {
		return data;
	}

	/**
	 * Returns the number of dimensions in this multidim data set
	 */
	@Override
	public int numDimensions() {
		return dims.length;
	}

	/**
	 * Returns the size of dimension number d in this multidim data set
	 */
	@Override
	public long dimension(int d) {
		if (d < 0) throw new IllegalArgumentException("negative index exception");
		if (d < dims.length) return dims[d];
		return 1;
	}
	
	/**
	 * Returns the total number of elements in this data set
	 */
	@Override
	public long numElements() {
		return data.size();
	}

	/**
	 * Return the coordinate space that maps the grid values to numeric values
	 * (for instance if you wanted to display a coordinate while mousing over
	 * a set of data).
	 */
	@Override
	public CoordinateSpace getCoordinateSpace() {
		return space;
	}
	
	/**
	 * Set the coordinate space that maps the grid values to numeric values
	 * (for instance if you wanted to display a coordinate while mousing over
	 * a set of data).
	 */
	@Override
	public void setCoordinateSpace(CoordinateSpace space) {
		if (space == null)
			throw new IllegalArgumentException("coordinate space cannot be null");
		if (space.numDimensions() != numDimensions())
			throw new IllegalArgumentException("coordinate space dimensions do not match data dimensions");
		this.space = space;
	}
	
	/**
	 * Return a 1-d piped (PIE-PED) "slice" of this data source.
	 */
	@Override
	public IndexedDataSource<U> piped(int dim, IntegerIndex coord) {
		return new PipedDataSource<U>(this, dim, coord);
	}
	
	/**
	 * Sets the value associated with this data source's n-d coord space
	 * as stored in the underlying 1-d list of data. No checking is done
	 * in regards to the validity of the input coordinate.
	 */
	@Override
	public void set(IntegerIndex index, U value) {
		long idx = IndexUtils.indexToLong(dims, index);
		data.set(idx, value);
	}
	
	/**
	 * Sets the value associated with this data source's n-d coord space
	 * as stored in the underlying 1-d list of data. The input coordinate
	 * is checked for validity and if it is out of the bounds of this n-d
	 * data source then an exception is thrown.
	 */
	@Override
	public void safeSet(IntegerIndex index, U value) {
		if (oob(index))
			throw new IllegalArgumentException("index out of bounds of multidim dimensions");
		set(index, value);
	}
	
	/**
	 * Gets the value associated with this data source's n-d coord space
	 * as stored in the underlying 1-d list of data. No checking is done
	 * in regards to the validity of the input coordinate.
	 */
	@Override
	public void get(IntegerIndex index, U value) {
		long idx = IndexUtils.indexToLong(dims, index);
		data.get(idx, value);
	}
	
	/**
	 * Gets the value associated with this data source's n-d coord space
	 * as stored in the underlying 1-d list of data. The input coordinate
	 * is checked for validity and if it is out of the bounds of this n-d
	 * data source then an exception is thrown.
	 */
	@Override
	public void safeGet(IntegerIndex index, U value) {
		if (oob(index))
			throw new IllegalArgumentException("index out of bounds of multidim dimensions");
		get(index, value);
	}
	
	/**
	 * Return true if a given index represents coordinates outside this
	 * data source's bounds.
	 */
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

	/**
	 * Return the storage type of this multidim data set.
	 */
	@Override
	public StorageConstruction storageType() {
		return data.storageType();
	}
	
	/**
	 * Return the metadata structure associated with this data.
	 */
	@Override
	public MetaDataStore metadata() {
		return metadata;
	}

	/**
	 * Get the axis unit associated with a dimension/coordinate axis.
	 * An axis unit might be something like "mm" or "deciliters" etc.
	 */
	@Override
	public String getAxisUnit(int i) {
		return axisUnits[i];
	}

	/**
	 * Get the axis type associated with a dimension/coordinate axis.
	 * An axis type might be something like "pressure" or "acceleration" etc.
	 */
	@Override
	public String getAxisType(int i) {
		return axisTypes[i];
	}

	/**
	 * Set the axis unit associated with a dimension/coordinate axis.
	 * An axis unit might be something like "mm" or "deciliters" etc.
	 */
	@Override
	public void setAxisUnit(int i, String unit) {
		if (unit == null) unit = "unk";
		this.axisUnits[i] = unit;
	}

	/**
	 * Set the axis type associated with a dimension/coordinate axis.
	 * An axis type might be something like "pressure" or "acceleration" etc.
	 */
	@Override
	public void setAxisType(int i, String type) {
		if (type == null || type.equals("")) type = "d" + i;
		this.axisTypes[i] = type;
	}

	/**
	 * Return the value unit for the n-d data source. The value unit is related
	 * to the value type. So if value type is "temperature" then value unit will
	 * be something like "degrees C" or "degrees F", or "degrees K".
	 */
	@Override
	public String getValueUnit() {
		return valueUnit;
	}

	/**
	 * Set the value unit for the n-d data source. The value unit is related
	 * to the value type. So if value type is "temperature" then value unit will
	 * be something like "degrees C" or "degrees F", or "degrees K".
	 */
	@Override
	public void setValueUnit(String unit) {
		if (unit == null) unit = "unk";
		this.valueUnit = unit;
	}
	
	/**
	 * Return the name for the n-d data source (something like "Lateral Brain Scan")
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name for the n-d data source (something like "Lateral Brain Scan")
	 */
	@Override
	public void setName(String name) {
		if (name == null) name = "";
		this.name = name;
	}
	
	/**
	 * Return the source for the n-d data source. The source might be a filename
	 * or a URL.
	 */
	@Override
	public String getSource() {
		return source;
	}
	
	/**
	 * Set the source for the n-d data source. The source might be a filename
	 * or a URL.
	 */
	@Override
	public void setSource(String locator) {
		if (locator == null) locator = "";
		this.source = locator;
	}

	/**
	 * Return the value type for the n-d data source. The value type is typically
	 * the type of data that is represented by the values (temperature, distance,
	 * weight, etc.).
	 */
	@Override
	public String getValueType() {
		return valueType;
	}
	
	/**
	 * Set the value type for the n-d data source. The value type is typically
	 * the type of data that is represented by the values (temperature, distance,
	 * weight, etc.).
	 */
	@Override
	public void setValueType(String type) {
		if (type == null || type.equals("")) type = "unk";
		this.valueType = type;
	}
}
