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

import nom.bdezonia.zorbage.accessor.AccessorA;
import nom.bdezonia.zorbage.accessor.AccessorB;
import nom.bdezonia.zorbage.accessor.AccessorC;
import nom.bdezonia.zorbage.accessor.AccessorD;
import nom.bdezonia.zorbage.accessor.AccessorE;
import nom.bdezonia.zorbage.accessor.AccessorF;
import nom.bdezonia.zorbage.accessor.AccessorG;
import nom.bdezonia.zorbage.accessor.AccessorH;
import nom.bdezonia.zorbage.type.algebra.GetOctonion;
import nom.bdezonia.zorbage.type.algebra.SetOctonion;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Splat8 {

	/**
	 * 
	 * @param value
	 * @param tuple
	 */
	public static <U extends GetOctonion<W>,
					V extends AccessorA<W> & AccessorB<W> & AccessorC<W> & AccessorD<W> &
								AccessorE<W> & AccessorF<W> & AccessorG<W> & AccessorH<W>,
					W>
		void toTuple(U value, V tuple)
	{
		value.getR(tuple.a());
		value.getI(tuple.b());
		value.getJ(tuple.c());
		value.getK(tuple.d());
		value.getL(tuple.e());
		value.getI0(tuple.f());
		value.getJ0(tuple.g());
		value.getK0(tuple.h());
	}

	/**
	 * 
	 * @param tuple
	 * @param value
	 */
	public static <U extends SetOctonion<W>,
					V extends AccessorA<W> & AccessorB<W> & AccessorC<W> & AccessorD<W> &
								AccessorE<W> & AccessorF<W> & AccessorG<W> & AccessorH<W>,
					W>
		void toValue(V tuple, U value)
	{
		value.setR(tuple.a());
		value.setI(tuple.b());
		value.setJ(tuple.c());
		value.setK(tuple.d());
		value.setL(tuple.e());
		value.setI0(tuple.f());
		value.setJ0(tuple.g());
		value.setK0(tuple.h());
	}
}
