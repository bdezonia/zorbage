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

import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;

// TODO: this class is for a tensor like product that uses a Ring (quats and octs)
// rather than a Field (reals and comlexes). I don't yet know the right name for
// it. Must research.

/**
 * 
 * @author Barry DeZonia
 *
 * @param T the tensor like space
 * @param U the tensor like member
 * @param V the scalar space
 * @param W the scalar member
 */
public interface TensorLikeProduct<T extends TensorLikeProduct<T,U,V,W>, U, V extends Ring<V,W>, W>
  extends
    RModule<T,U,V,W>, Multiplication<U>, Unity<U>
{
	// TODO: copy these methods and those of TensorProduct into a shared interface
	
	Procedure4<java.lang.Integer, java.lang.Integer,U,U> contract();
	Procedure3<W,U,U> addToElements();
	Procedure3<U,U,U> multiplyElements();
	Procedure3<U,U,U> divideElements();
	Procedure1<java.lang.Integer> semicolonDerivative();
	Procedure1<java.lang.Integer> commaDerivative();
}
