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
package nom.bdezonia.zorbage.type.data.float64.complex;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestComplexSpeed {

	// Comparing raw speed of my code versus C++:
	//   See 15 min mark of this talk: http://cds.cern.ch/record/2157242?ln=de
	//
	// On BDZ's test machine C++ code is 20% slower than this java code. Surprising.
	// Relevant C++ code compiled with g++ -O3

/*	
	#include <iostream>
	#include <complex>
	#include <sys/time.h>

	std::complex<double> factor(1,1);

	inline std::complex<double>
	f(const std::complex<double> &z)
	{
		return z*z + factor*z - 1.0;
	}

	int main()
	{
	    struct timeval start, end;

	    long mtime, seconds, useconds;    

	    gettimeofday(&start, NULL);

	 	std::complex<double> z = 0.1;
		for (int i = 0; i < 1000000000; i++) {
			z = f(z);
		}
	 
	    gettimeofday(&end, NULL);
	 
	    seconds  = end.tv_sec  - start.tv_sec;
	    useconds = end.tv_usec - start.tv_usec;

	    mtime = ((seconds) * 1000 + useconds/1000.0) + 0.5;

	 	std::cout << mtime << " " << z;
	}
*/
	
	
	private Procedure2<ComplexFloat64Member,ComplexFloat64Member> proc =
			new Procedure2<ComplexFloat64Member, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64Member z, ComplexFloat64Member res) {
			// f(z) = z*z + (1,1)*z - 1
			ComplexFloat64Member t1 = G.CDBL.construct();
			ComplexFloat64Member t2 = G.CDBL.construct();
			ComplexFloat64Member t3 = G.CDBL.construct();
			ComplexFloat64Member linTerm = new ComplexFloat64Member(1,1);
			G.CDBL.multiply().call(z, z, t1);
			G.CDBL.multiply().call(linTerm, z, t2);
			G.CDBL.unity().call(t3);
			G.CDBL.add().call(t1, t2, t1);
			G.CDBL.subtract().call(t1, t3, res);
		}
	};
	
	@Test
	public void test1() {
		long a = System.currentTimeMillis();
		ComplexFloat64Member z = new ComplexFloat64Member(0.1,0);
		for (long i = 0; i < 1000000000; i++) {
			proc.call(z,z);
		}
		long b = System.currentTimeMillis();
		System.out.println((b-a) + " millisecs " + z);
	}
}
