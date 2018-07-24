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
package nom.bdezonia.zorbage.type.data.float64.real;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.groups.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFloat64Group {

	@Test
	public void testFloats() {
		  
		Float64Member a = G.DBL.construct("1.1");
		Float64Member b = G.DBL.construct("4.2");
		Float64Member sum = G.DBL.construct("99.3");

		G.DBL.add().call(a,b,sum);
		  
		assertEquals(5.3, sum.v(), 0.000000001);
	}
	
	@Test
	public void testSinCos() {
		//timingSinCos();
		Float64Member angle = new Float64Member();
		Float64Member s1 = new Float64Member();
		Float64Member c1 = new Float64Member();
		Float64Member s2 = new Float64Member();
		Float64Member c2 = new Float64Member();
		
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.sin().call(angle, s1);
			G.DBL.cos().call(angle, c1);
			G.DBL.sinAndCos().call(angle, s2, c2);
			// TODO: is this good enough? Any more precise and it fails.
			//assertEquals(s1.v(), s2.v(), 0.00000000000226);
			//assertEquals(c1.v(), c2.v(), 0.00000000000226);
			assertEquals(s1.v(), s2.v(), 0);
			assertEquals(c1.v(), c2.v(), 0);
		}
	}
	
	@Test
	public void testSinhCosh() {
		//timingSinhCosh();
		Float64Member angle = new Float64Member();
		Float64Member s1 = new Float64Member();
		Float64Member c1 = new Float64Member();
		Float64Member s2 = new Float64Member();
		Float64Member c2 = new Float64Member();
		
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.sinh().call(angle, s1);
			G.DBL.cosh().call(angle, c1);
			G.DBL.sinhAndCosh().call(angle, s2, c2);
			// TODO: is this good enough? Any more precise and it fails.
			//assertEquals(s1.v(), s2.v(), 0.000000015);
			//assertEquals(c1.v(), c2.v(), 0.000000015);
			assertEquals(s1.v(), s2.v(), 0);
			assertEquals(c1.v(), c2.v(), 0);
		}
	}

	/*
	
	private void timingSinCos() {
		Float64Member angle = new Float64Member();
		Float64Member s1 = new Float64Member();
		Float64Member c1 = new Float64Member();
		
		long p = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.sin(angle, s1);
		}
		long q = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.cos(angle, c1);
		}
		long r = System.currentTimeMillis();
		
		System.out.println("Times: sin "+(q-p)+" cos "+(r-q));
	}

	private void timingSinhCosh() {
		Float64Member angle = new Float64Member();
		Float64Member s1 = new Float64Member();
		Float64Member c1 = new Float64Member();
		
		long p = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.sinh(angle, s1);
		}
		long q = System.currentTimeMillis();
		for (double a = -6*Math.PI; a <= 6*Math.PI; a += (Math.PI) / 720) {
			angle.setV(a);
			G.DBL.cosh(angle, c1);
		}
		long r = System.currentTimeMillis();
		
		System.out.println("Times: sinh "+(q-p)+" cosh "+(r-q));
	}
	*/
	
	@Test
	public void divmod() {
		
		final double tol = 0.000000000000001;
		
		Float64Member a = G.DBL.construct();
		Float64Member b = G.DBL.construct();
		Float64Member d = G.DBL.construct();
		Float64Member m = G.DBL.construct();
		
		a.setV(0);
		b.setV(66);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), 0);
		assertEquals(0, m.v(), 0);

		a.setV(44);
		b.setV(66);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), 0);
		assertEquals(44, m.v(), 0);

		a.setV(0.5);
		b.setV(1);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(0.5, m.v(), tol);
		
		a.setV(-0.5);
		b.setV(1);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(0, d.v(), tol);
		assertEquals(-0.5, m.v(), tol);
		
		a.setV(3.3);
		b.setV(2);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(1, d.v(), tol);
		assertEquals(1.3, m.v(), tol);
		
		a.setV(-3.3);
		b.setV(2);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(-1, d.v(), tol);
		assertEquals(-1.3, m.v(), tol);
		
		a.setV(-4);
		b.setV(-2);
		G.DBL.divMod().call(a, b, d, m);
		assertEquals(2, d.v(), 0);
		assertEquals(0, m.v(), 0);
	}
}
