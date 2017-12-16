/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConversion;
import nom.bdezonia.zorbage.type.data.universal.PrimitiveConverter;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class DataConvert {

	// do not instantiate
	
	private DataConvert() {}
	
	/**
	 * DataConvert.compute()
	 * 
	 * @param fromGroup
	 * @param toGroup
	 * @param input
	 * @param output
	 */
	public static <T extends Group<T,V>, U extends Group<U,W>, V extends PrimitiveConversion, W extends PrimitiveConversion>
		void compute(T fromGroup, U toGroup, LinearStorage<?,V> input, LinearStorage<?,W> output)
	{
		// assumptions: output size is >= input size
		
		V from = fromGroup.construct();
		W to = toGroup.construct();
		int numD = Math.max(from.numDimensions(), to.numDimensions());
		IntegerIndex tmp1 = new IntegerIndex(numD);
		IntegerIndex tmp2 = new IntegerIndex(numD);
		IntegerIndex tmp3 = new IntegerIndex(numD);
		for (long i = 0; i < input.size(); i++) {
			input.get(i, from);
			PrimitiveConverter.convert(tmp1, tmp2, tmp3, from, to);
			output.set(i, to);
		}
	}
}
