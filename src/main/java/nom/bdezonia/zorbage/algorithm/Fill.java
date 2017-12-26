/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.basic.procedure.Procedure1;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Fill {

	// do not instantiate
	
	private Fill() {}
	
	/**
	 * 
	 * @param group
	 * @param storage
	 * @param value
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, LinearStorage<?,U> storage, U value)
	{
		compute(group, storage, value, 0, storage.size());
	}

	/**
	 * 
	 * @param group
	 * @param storage
	 * @param value
	 * @param start
	 * @param count
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, LinearStorage<?,U> storage, U value, long start, long count)
	{
		for (long i = 0; i < count; i++) {
			storage.set(start+i, value);
		}
	}
	
	/**
	 * 
	 * @param group
	 * @param proc
	 * @param storage
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, Procedure1<U> proc, LinearStorage<?,U> storage)
	{
		compute(group, proc, 0, storage.size(), storage);
	}

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param start
	 * @param count
	 * @param storage
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, Procedure1<U> proc, long start, long count, LinearStorage<?,U> storage)
	{
		U value = group.construct();
		for (long i = 0; i < count; i++) {
			proc.call(value);
			storage.set(start+i, value);
		}
	}
}
