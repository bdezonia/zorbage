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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.Addition;
import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.Invertible;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.algebra.SetI;
import nom.bdezonia.zorbage.algebra.SetR;
import nom.bdezonia.zorbage.algebra.Trigonometric;
import nom.bdezonia.zorbage.algebra.Unity;
import nom.bdezonia.zorbage.datasource.FixedSizeDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ProcedurePaddedDataSource;
import nom.bdezonia.zorbage.oob.oned.ZeroOOB;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BasicFFT {

	// do not instantiate
	
	private BasicFFT() { }
	
	/**
	 * BasicFFT takes a list of any size of real data and returns a power of
	 * 2 sized output list of FFT transformed complex data.
	 * 
	 * @param <CA> Complex algebra type
	 * @param <C> Complex number type
	 * @param <RA> Real algebra type
	 * @param <R> Real number type
	 * @param algR Real algebra
	 * @param algC Complex algebra
	 * @param inputData List of real numbers
	 * @return Power of 2 sized list of FFT transformed complex data
	 */
	public static <CA extends Algebra<CA,C> & Addition<C> & Multiplication<C>,
					C extends SetR<R> & SetI<R> & Allocatable<C>,
					RA extends Algebra<RA,R> & Trigonometric<R> & RealConstants<R> &
								Multiplication<R> & Addition<R> & Invertible<R> & Unity<R>,
					R>
		IndexedDataSource<C> compute(CA algC, RA algR, IndexedDataSource<R> inputData)
	{
		long size = FFT.enclosingPowerOf2(inputData.size());
		
		Procedure2<Long,R> oobProc = new ZeroOOB<RA,R>(algR, inputData.size());
		
		IndexedDataSource<R> paddedReals = new ProcedurePaddedDataSource<RA,R>(algR, inputData, oobProc);
		
		FixedSizeDataSource<R> goodSizeReals = new FixedSizeDataSource<>(size, paddedReals);
		
		IndexedDataSource<C> complexes = Storage.allocate(algC.construct(), size);
		
		SetRValues.compute(algR, algC, goodSizeReals, complexes);
		
		FFT.compute(algC, algR, complexes, complexes);
		
		return complexes;
	}
}
