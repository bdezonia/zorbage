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
package nom.bdezonia.zorbage.type.data.float64.complex;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.storage.linear.LinearStorage;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageFloat64;
import nom.bdezonia.zorbage.type.storage.linear.array.ArrayStorageSignedInt32;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat64Complex {

	@Test
	public void run() {
		long[] times = new long[20];
		for (int i = 0; i < times.length; i++) {
			long a = System.currentTimeMillis();
			final int size = 500;
			LinearStorage<?,SignedInt32Member> inputData = makeIntData(size);
			fillIntData(inputData);
			LinearStorage<?,ComplexFloat64Member> outputComplexData = makeComplexData(size);
			dft(inputData, outputComplexData);
			// TODO: apply inverse and test resulting values
			long b = System.currentTimeMillis();
			times[i] = b - a;
		}
		System.out.println("DFT time test");
		for (int i = 0; i < times.length; i++) {
			if (i > 0)
				System.out.print(",");
			System.out.print(times[i]);
		}
		System.out.println("]");
		assertTrue(true);
	}
	
	
	private LinearStorage<?,SignedInt32Member> makeIntData(int size) {
		return new ArrayStorageSignedInt32<SignedInt32Member>(size, new SignedInt32Member());
	}
	
	private LinearStorage<?,ComplexFloat64Member> makeComplexData(int size) {
		return new ArrayStorageFloat64<ComplexFloat64Member>(size, new ComplexFloat64Member());
	}
	
	private void fillIntData(LinearStorage<?,SignedInt32Member> input) {
		// an example of straight index access to low level data
		SignedInt32Member value = new SignedInt32Member();
		int count = 0;
		for (long i = 0; i < input.size(); i++) {
			value.setV(count++);
			input.set(i, value);
		}
	}
	
	// algorithm from https://users.cs.cf.ac.uk/Dave.Marshall/Multimedia/node228.html
	
	// note this could be further optimized by doing some multiplication in reals rather than complexes
	
	private void dft(LinearStorage<?,SignedInt32Member> input, LinearStorage<?,ComplexFloat64Member> output) {
		ComplexFloat64Member FofU = new ComplexFloat64Member();
		ComplexFloat64Member fOfX = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member term = new ComplexFloat64Member();
		ComplexFloat64Member multiplier = new ComplexFloat64Member();
		ComplexFloat64Member power = new ComplexFloat64Member();
		ComplexFloat64Member uTmp = new ComplexFloat64Member();
		ComplexFloat64Member xTmp = new ComplexFloat64Member();
		ComplexFloat64Member I = new ComplexFloat64Member(0,1);
		ComplexFloat64Member MINUS_2_PI = new ComplexFloat64Member(-2*Math.PI,0);
		ComplexFloat64Member N = new ComplexFloat64Member(input.size(), 0);
		ComplexFloat64Member E = new ComplexFloat64Member();
		ComplexFloat64Member PI = new ComplexFloat64Member();
		SignedInt32Member in = new SignedInt32Member();
		
		G.CDBL.E(E);
		G.CDBL.PI(PI);
		
		for (long u = 0; u < input.size(); u++) {
			G.CDBL.zero(sum);
			uTmp.setR(u);
			for (long x = 0; x < input.size(); x++) {
				input.get(x, in);
				fOfX.setR(in.v());
				xTmp.setR(x);
				G.CDBL.multiply(MINUS_2_PI, I, power);
				G.CDBL.multiply(power, xTmp, power);
				G.CDBL.multiply(power, uTmp, power);
				G.CDBL.divide(power, N, power);
				G.CDBL.exp(power, multiplier);
				G.CDBL.multiply(fOfX, multiplier, term);
				G.CDBL.add(sum, term, sum);
			}
			G.CDBL.divide(sum, N, FofU);
			output.set(u, FofU);
		}
	}

}
