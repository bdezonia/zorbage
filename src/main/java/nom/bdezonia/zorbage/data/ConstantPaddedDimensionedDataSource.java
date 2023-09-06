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

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.coordinates.CoordinateSpace;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.metadata.MetaDataStore;
import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 * @param <U>
 */
public class ConstantPaddedDimensionedDataSource<T extends Algebra<T,U>, U>

	implements DimensionedDataSource<U>
{
	private final T alg;
	private final U constVal;
	private final DimensionedDataSource<U> referenceData;
	
	public ConstantPaddedDimensionedDataSource(T alg, U constVal, DimensionedDataSource<U> referenceData) {

		this.alg = alg;
		this.constVal = alg.construct();
		this.alg.assign().call(constVal, this.constVal);
		this.referenceData = referenceData;
	}

	@Override
	public void set(IntegerIndex index, U value) {

		if (oob(index)) {
			
			if (!alg.isEqual().call(constVal, value)) {
				
				throw new IllegalArgumentException("Setting out of bounds value to something illegal"); 
			}
		}
		else {
			
			referenceData.set(index, value);
		}
	}

	@Override
	public void get(IntegerIndex index, U value) {
		
		if (oob(index)) {
			
			alg.assign().call(constVal, value);
		}
		else {
			
			referenceData.get(index, value);
		}
	}
	
	@Override
	public long dimension(int d) {

		return referenceData.dimension(d);
	}

	@Override
	public int numDimensions() {

		return referenceData.numDimensions();
	}

	@Override
	public IndexedDataSource<U> rawData() {

		return referenceData.rawData();
	}

	@Override
	public StorageConstruction storageType() {

		return referenceData.storageType();
	}

	@Override
	public long numElements() {
		
		return referenceData.numElements();
	}

	@Override
	public String getName() {

		return referenceData.getName();
	}

	@Override
	public void setName(String name) {

		referenceData.setName(name);
	}

	@Override
	public String getSource() {

		return referenceData.getSource();
	}

	@Override
	public void setSource(String locator) {

		referenceData.setSource(locator);
	}

	@Override
	public String getValueType() {

		return referenceData.getValueType();
	}

	@Override
	public void setValueType(String type) {

		referenceData.setValueType(type);
	}

	@Override
	public String getValueUnit() {

		return referenceData.getValueUnit();
	}

	@Override
	public void setValueUnit(String unit) {

		referenceData.setValueUnit(unit);
	}

	@Override
	public CoordinateSpace getCoordinateSpace() {

		return referenceData.getCoordinateSpace();
	}

	@Override
	public void setCoordinateSpace(CoordinateSpace cspace) {

		referenceData.setCoordinateSpace(cspace);
	}

	@Override
	public String getAxisUnit(int i) {

		return referenceData.getAxisUnit(i);
	}

	@Override
	public void setAxisUnit(int i, String unit) {

		referenceData.setAxisUnit(i, unit);
	}

	@Override
	public String getAxisType(int i) {

		return referenceData.getAxisType(i);
	}

	@Override
	public void setAxisType(int i, String type) {

		referenceData.setAxisType(i, type);
	}

	@Override
	public void safeGet(IntegerIndex index, U value) {

		referenceData.safeGet(index, value);
	}

	@Override
	public void safeSet(IntegerIndex index, U value) {

		referenceData.safeSet(index, value);
	}

	@Override
	public boolean oob(IntegerIndex index) {

		return referenceData.oob(index);
	}

	@Override
	public IndexedDataSource<U> piped(int dim, IntegerIndex coord) {

		return referenceData.piped(dim, coord);
	}

	@Override
	public MetaDataStore metadata() {

		return referenceData.metadata();
	}

}
