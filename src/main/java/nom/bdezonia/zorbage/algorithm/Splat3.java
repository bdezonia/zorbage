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

import nom.bdezonia.zorbage.accessor.AccessorA;
import nom.bdezonia.zorbage.accessor.AccessorB;
import nom.bdezonia.zorbage.accessor.AccessorC;
import nom.bdezonia.zorbage.algebra.GetQuaternion;
import nom.bdezonia.zorbage.algebra.SetQuaternion;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Splat3 {

	// do not instantiate
	
	private Splat3() { }
	
	/**
	 * Set the 1st three components of a tuple to the r/i/j values of a number.
	 * 
	 * @param value
	 * @param tuple
	 */
	public static <U extends GetQuaternion<W>,
					V extends AccessorA<W> & AccessorB<W> & AccessorC<W>,
					W>
		void toTuple(U value, V tuple)
	{
		value.getR(tuple.a());
		value.getI(tuple.b());
		value.getJ(tuple.c());
	}

	/**
	 * Set the r/i/j values of a number to the 1st three components of a tuple.
	 * 
	 * @param tuple
	 * @param value
	 */
	public static <U extends SetQuaternion<W>,
					V extends AccessorA<W> & AccessorB<W> & AccessorC<W>,
					W>
		void toValue(V tuple, U value)
	{
		value.setR(tuple.a());
		value.setI(tuple.b());
		value.setJ(tuple.c());
	}
}
