/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.example;

import zorbage.type.converter.ConverterSignedInt32ToComplexFloat64;
import zorbage.type.data.ComplexFloat64;
import zorbage.type.data.ComplexFloat64Member;
import zorbage.type.data.SignedInt32Member;
import zorbage.type.storage.ArrayStorageComplexFloat64;
import zorbage.type.storage.ArrayStorageSignedInt32;
import zorbage.type.storage.Storage;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class DFTExample {

	private ComplexFloat64 g = new ComplexFloat64();
	
	public void run() {
		final int size = 20;
		Storage<SignedInt32Member> inputData = makeIntData(size);
		fillIntData(inputData);
		Storage<ComplexFloat64Member> outputComplexData = makeComplexData(size);
		dft(inputData, outputComplexData);
	}
	
	
	private Storage<SignedInt32Member> makeIntData(int size) {
		return new ArrayStorageSignedInt32(size);
	}
	
	private Storage<ComplexFloat64Member> makeComplexData(int size) {
		return new ArrayStorageComplexFloat64(size);
	}
	
	private void fillIntData(Storage<SignedInt32Member> input) {
		// an example of straight index access to low level data
		SignedInt32Member value = new SignedInt32Member();
		int count = 0;
		for (int i = 0; i < input.size(); i++) {
			value.setV(count++);
			input.put(i, value);
		}
	}
	
	// https://users.cs.cf.ac.uk/Dave.Marshall/Multimedia/node228.html
	
	private void dft(Storage<SignedInt32Member> input, Storage<ComplexFloat64Member> output) {
		ConverterSignedInt32ToComplexFloat64 converter = new ConverterSignedInt32ToComplexFloat64();
		ComplexFloat64Member FofU = new ComplexFloat64Member();
		ComplexFloat64Member fOfX = new ComplexFloat64Member();
		ComplexFloat64Member sum = new ComplexFloat64Member();
		ComplexFloat64Member term = new ComplexFloat64Member();
		ComplexFloat64Member multiplier = new ComplexFloat64Member();
		ComplexFloat64Member power = new ComplexFloat64Member();
		ComplexFloat64Member uTmp = new ComplexFloat64Member();
		ComplexFloat64Member xTmp = new ComplexFloat64Member();
		ComplexFloat64Member I = new ComplexFloat64Member(0,1);
		ComplexFloat64Member MINUS_2 = new ComplexFloat64Member(-2,0);
		ComplexFloat64Member N = new ComplexFloat64Member(input.size(), 0);
		ComplexFloat64Member E = new ComplexFloat64Member();
		ComplexFloat64Member PI = new ComplexFloat64Member();
		SignedInt32Member in = new SignedInt32Member();
		
		g.E(E);
		g.PI(PI);
		
		for (int u = 0; u < input.size(); u++) {
			g.zero(sum);
			uTmp.setR(u);
			for (int x = 0; x < input.size(); x++) {
				input.get(x, in);
				converter.convert(in, fOfX);
				xTmp.setR(x);
				g.multiply(MINUS_2, PI, power);
				g.multiply(power, I, power);
				g.multiply(power, xTmp, power);
				g.multiply(power, uTmp, power);
				g.divide(power, N, power);
				g.exp(power, multiplier);
				g.multiply(fOfX, multiplier, term);
				g.add(sum, term, sum);
			}
			g.divide(sum, N, FofU);
			output.put(u, FofU);
		}
	}

}
