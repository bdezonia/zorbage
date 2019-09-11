/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Tolerance;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class WithinTolerance<T extends Algebra<T,U> & Tolerance<U,W>, U, V extends Algebra<V,W>, W>
	implements Function2<Boolean,U,U>
{
	private final T uAlgebra;
	private final V tolAlgebra;
	private final W tolerance;
	
	/**
	 * 
	 * @param uAlgebra
	 * @param tolAlgebra
	 * @param tolerance
	 */
	public WithinTolerance(T uAlgebra, V tolAlgebra, W tolerance) {
		this.uAlgebra = uAlgebra;
		this.tolAlgebra = tolAlgebra;
		this.tolerance = tolAlgebra.construct(tolerance);
	}

	/**
	 * 
	 * @param tol
	 */
	public void setTolerance(W tol) {
		tolAlgebra.assign().call(tol, tolerance);
	}
	
	/**
	 * 
	 * @param tol
	 */
	public void getTolerance(W tol) {
		tolAlgebra.assign().call(tolerance, tol);
	}
	
	@Override
	public Boolean call(U a, U b) {
		return uAlgebra.within().call(a, b, tolerance);
	}
}
