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
package nom.bdezonia.zorbage.type.algebra;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T> the vector space for the vectors
 * @param <U> the vector member type for the vectors
 * @param <V> the field for the coefficients
 * @param <W> the scalar member type for the coefficients
 */
public interface VectorSpace<T extends VectorSpace<T,U,V,W>, U, V extends Field<V,W>, W>
  extends
  	RModule<T,U,V,W>
    //AdditiveAlgebra<T,U>,
    //Norm<U,W>,
    //Products<U,W>,  // TODO: not necessarily true of all vector spaces?
    //Conjugate<U>
{
	// closed under vector addition
	// closed under scalar multiplication
	// u,v,w vectors, k,l scalars
	//   u + (v + w) = (u + v) + w
	//   u + v = v + u
	//   v + 0 = v
	//   v + (-v) = 0
	//   k(u + v) = ku + kv
	//   (k+l) v = kv + lv
	//   (kl)v = k(lv)
	//   1(v) = v
}
