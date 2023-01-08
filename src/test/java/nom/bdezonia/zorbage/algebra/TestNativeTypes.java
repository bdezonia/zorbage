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
package nom.bdezonia.zorbage.algebra;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNativeTypes {

	@Test
	public void test1() {

		byteImplementation(G.UINT1);
		byteImplementation(G.UINT2);
		byteImplementation(G.UINT3);
		byteImplementation(G.UINT4);
		byteImplementation(G.UINT5);
		byteImplementation(G.UINT6);
		byteImplementation(G.UINT7);
		
		byteImplementation(G.INT1);
		byteImplementation(G.INT2);
		byteImplementation(G.INT3);
		byteImplementation(G.INT4);
		byteImplementation(G.INT5);
		byteImplementation(G.INT6);
		byteImplementation(G.INT7);
		byteImplementation(G.INT8);

		assertTrue(true);
	}

	@Test
	public void test2() {

		shortImplementation(G.UINT8);
		shortImplementation(G.UINT9);
		shortImplementation(G.UINT10);
		shortImplementation(G.UINT11);
		shortImplementation(G.UINT12);
		shortImplementation(G.UINT13);
		shortImplementation(G.UINT14);
		shortImplementation(G.UINT15);
		
		shortImplementation(G.INT9);
		shortImplementation(G.INT10);
		shortImplementation(G.INT11);
		shortImplementation(G.INT12);
		shortImplementation(G.INT13);
		shortImplementation(G.INT14);
		shortImplementation(G.INT15);
		shortImplementation(G.INT16);

		assertTrue(true);
	}

	@Test
	public void test3() {

		floatImplementation(G.HLF);
		floatImplementation(G.FLT);
		
		assertTrue(true);
	}

	private <T extends Algebra<T,U> & Random<U>, U extends NativeFloatSupport>
		void floatImplementation(T alg)
	{
		U value = alg.construct();
		
		Float min = value.componentMin();
		Float max = value.componentMin();
	
		assertFalse(min == null);
		assertFalse(max == null);
		
		// this will return a floating value between 0.0 and 1.0
		
		alg.random().call(value);
		
		System.out.println(alg.typeDescription() + "\t" + min + "\t" + max + "\t" + value.getNative(0));
		
		assertTrue(alg instanceof RandomIsZeroToOne);
	}
	
	private <T extends Algebra<T,U> & Random<U>, U extends NativeShortSupport>
		void shortImplementation(T alg)
	{
		U value = alg.construct();
		
		Short min = value.componentMin();
		Short max = value.componentMax();
		
		assertFalse(min == null);
		assertFalse(max == null);
		
		// this will return a integral value between min and max
		
		alg.random().call(value);
		
		System.out.println(alg.typeDescription() + "\t" + min + "\t" + max + "\t" + value.getNative(0));
		
		assertFalse(alg instanceof RandomIsZeroToOne);
	}
	
	private <T extends Algebra<T,U> & Random<U>, U extends NativeByteSupport>
		void byteImplementation(T alg)
	{
		U value = alg.construct();
		
		Byte min = value.componentMin();
		Byte max = value.componentMax();
		
		assertFalse(min == null);
		assertFalse(max == null);
		
		// this will return a integral value between min and max
		
		alg.random().call(value);
		
		System.out.println(alg.typeDescription() + "\t" + min + "\t" + max + "\t" + value.getNative(0));
		
		assertFalse(alg instanceof RandomIsZeroToOne);
	}
}
