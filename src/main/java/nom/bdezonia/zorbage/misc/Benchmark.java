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
package nom.bdezonia.zorbage.misc;

import nom.bdezonia.zorbage.function.Function0;
import nom.bdezonia.zorbage.procedure.Procedure1;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Benchmark {

	/**
	 * Measure the time for a process to run.
	 * @param <X>
	 * @param setupCode The setup code for the timing suite.
	 * @param measuredProcess The "chunk of code' that is being timed.
	 * @return The total milliseconds it takes to run the chunk of code.
	 */
	public static <X>
		long measureTime(Function0<X> setupCode, Procedure1<X> measuredProcess)
	{
		// setup any params the measuredProcess will use
		
		X parameters = setupCode.call();
		
		// warm up the cache and the code optimizer
		
		for (int i = 0; i < 5; i++) {
			measuredProcess.call(parameters);
		}
		
		// now time one run
		
		long start = System.currentTimeMillis();
		
		measuredProcess.call(parameters);
		
		long end = System.currentTimeMillis();
		
		// and return millisecond timing
		
		return (end - start);
	}
}
