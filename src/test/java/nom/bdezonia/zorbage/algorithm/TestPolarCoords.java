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

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestPolarCoords {

	@Test
	public void test() {

		double TOL = 0.000000000000001;
		
		Float64Member real = G.DBL.construct();
		
		Float64Member imag = G.DBL.construct();

		Float64Member phase = G.DBL.construct();

		double half = 0.5;
		double root22 = Math.sqrt(2) / 2;
		double root32 = Math.sqrt(3) / 2;

		// test basic angles
		
		// 0
		real.setV(1);
		imag.setV(0);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(0, phase.v(), TOL);
		
		// 30
		real.setV(root32);
		imag.setV(half);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(1*Math.PI/6, phase.v(), TOL);
		
		// 45
		real.setV(root22);
		imag.setV(root22);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(1*Math.PI/4, phase.v(), TOL);

		// 60
		real.setV(half);
		imag.setV(root32);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(2*Math.PI/6, phase.v(), TOL);
		
		// 90
		real.setV(0);
		imag.setV(1);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(3*Math.PI/6, phase.v(), TOL);
		
		// 120
		real.setV(-half);
		imag.setV(root32);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(4*Math.PI/6, phase.v(), TOL);
		
		// 135
		real.setV(-root22);
		imag.setV(root22);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(3*Math.PI/4, phase.v(), TOL);
		
		// 150
		real.setV(-root32);
		imag.setV(half);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(5*Math.PI/6, phase.v(), TOL);
		
		// 180
		real.setV(-1);
		imag.setV(0);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(Math.PI, phase.v(), TOL);
		
		// 210
		real.setV(-root32);
		imag.setV(-half);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(-5*Math.PI/6, phase.v(), TOL);
		
		// 225
		real.setV(-root22);
		imag.setV(-root22);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(-3*Math.PI/4, phase.v(), TOL);
		
		// 240
		real.setV(-half);
		imag.setV(-root32);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(-4*Math.PI/6, phase.v(), TOL);
		
		// 270
		real.setV(0);
		imag.setV(-1);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(-3*Math.PI/6, phase.v(), TOL);
		
		// 300
		real.setV(half);
		imag.setV(-root32);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(-2*Math.PI/6, phase.v(), TOL);
		
		// 315
		real.setV(root22);
		imag.setV(-root22);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(-1*Math.PI/4, phase.v(), TOL);
		
		// 330
		real.setV(root32);
		imag.setV(-half);
		PolarCoords.phase(G.DBL, real, imag, phase);
		assertEquals(-1*Math.PI/6, phase.v(), TOL);
	}

	@Test
	public void testFloatPhase() {

		final double tol = 0.0000001;
		
		Float32Member r = G.FLT.construct();
		Float32Member i = G.FLT.construct();
		Float32Member phase = G.FLT.construct();
		
		// 0/12
		
		r.setV(1);
		i.setV(0);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals((0.0 / 6) * Math.PI, phase.v(), tol);
		
		// 1/12
		
		r.setV((float) Math.sqrt(3)/2);
		i.setV(0.5f);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals((1.0/6) * Math.PI, phase.v(), tol);

		// midpoint
		
		r.setV((float) Math.sqrt(2)/2);
		i.setV((float) Math.sqrt(2)/2);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals((1.0 / 4) * Math.PI, phase.v(), tol);

		// 2/12
		
		r.setV(0.5f);
		i.setV((float) Math.sqrt(3)/2);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals((2.0 / 6) * Math.PI, phase.v(), tol);
		
		// 3/12
		
		r.setV(0);
		i.setV(1);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals(Math.PI/2, phase.v(), tol);
		
		// 4/12
		
		r.setV(-0.5f);
		i.setV((float) Math.sqrt(3)/2);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals((4.0 / 6) * Math.PI, phase.v(), tol);
		
		// midpoint
		
		r.setV((float) -Math.sqrt(2)/2);
		i.setV((float) Math.sqrt(2)/2);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals((3.0 / 4) * Math.PI, phase.v(), tol);

		// 5/12
		
		r.setV((float) -Math.sqrt(3)/2);
		i.setV(0.5f);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals((5.0 / 6) * Math.PI, phase.v(), tol);
		
		// 6/12
		
		r.setV(-1);
		i.setV(0);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals((6.0 / 6) * Math.PI, phase.v(), tol);
		
		// 7/12
		
		r.setV((float) -Math.sqrt(3)/2);
		i.setV(-0.5f);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals(2*Math.PI - (7.0 / 6) * Math.PI, -phase.v(), tol);
		
		// midpoint
		
		r.setV((float) -Math.sqrt(2)/2);
		i.setV((float) -Math.sqrt(2)/2);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals(2*Math.PI - (5.0 / 4) * Math.PI, -phase.v(), tol);

		// 8/12
		
		r.setV(-0.5f);
		i.setV((float) -Math.sqrt(3)/2);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals(2*Math.PI - (8.0 / 6) * Math.PI, -phase.v(), tol);
		
		// 9/12
		
		r.setV(0);
		i.setV(-1);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals(2*Math.PI - (9.0 / 6) * Math.PI, -phase.v(), tol);
		
		// 10/12
		
		r.setV(0.5f);
		i.setV((float) -Math.sqrt(3)/2);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals(2*Math.PI - (10.0 / 6) * Math.PI, -phase.v(), tol);
		
		// midpoint
		
		r.setV((float) Math.sqrt(2)/2);
		i.setV((float) -Math.sqrt(2)/2);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals(2*Math.PI - (7.0 / 4) * Math.PI, -phase.v(), tol);

		// 11/12
		
		r.setV((float) Math.sqrt(3)/2);
		i.setV(-0.5f);

		PolarCoords.phase(G.FLT, r, i, phase);
		
		assertEquals(2*Math.PI - (11.0 / 6) * Math.PI, -phase.v(), tol);
	}

	@Test
	public void testDoublePhase() {

		final double tol = 0.000000000000001;
		
		Float64Member r = G.DBL.construct();
		Float64Member i = G.DBL.construct();
		Float64Member phase = G.DBL.construct();
		
		// 0/12
		
		r.setV(1);
		i.setV(0);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals((0.0 / 6) * Math.PI, phase.v(), tol);
		
		// 1/12
		
		r.setV(Math.sqrt(3)/2);
		i.setV(0.5);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals((1.0/6) * Math.PI, phase.v(), tol);

		// midpoint
		
		r.setV(Math.sqrt(2)/2);
		i.setV(Math.sqrt(2)/2);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals((1.0 / 4) * Math.PI, phase.v(), tol);

		// 2/12
		
		r.setV(0.5);
		i.setV(Math.sqrt(3)/2);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals((2.0 / 6) * Math.PI, phase.v(), tol);
		
		// 3/12
		
		r.setV(0);
		i.setV(1);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals(Math.PI/2, phase.v(), tol);
		
		// 4/12
		
		r.setV(-0.5);
		i.setV(Math.sqrt(3)/2);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals((4.0 / 6) * Math.PI, phase.v(), tol);
		
		// midpoint
		
		r.setV(-Math.sqrt(2)/2);
		i.setV(Math.sqrt(2)/2);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals((3.0 / 4) * Math.PI, phase.v(), tol);

		// 5/12
		
		r.setV(-Math.sqrt(3)/2);
		i.setV(0.5);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals((5.0 / 6) * Math.PI, phase.v(), tol);
		
		// 6/12
		
		r.setV(-1);
		i.setV(0);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals((6.0 / 6) * Math.PI, phase.v(), tol);
		
		// 7/12
		
		r.setV(-Math.sqrt(3)/2);
		i.setV(-0.5);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals(2*Math.PI - (7.0 / 6) * Math.PI, -phase.v(), tol);
		
		// midpoint
		
		r.setV(-Math.sqrt(2)/2);
		i.setV(-Math.sqrt(2)/2);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals(2*Math.PI - (5.0 / 4) * Math.PI, -phase.v(), tol);

		// 8/12
		
		r.setV(-0.5);
		i.setV(-Math.sqrt(3)/2);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals(2*Math.PI - (8.0 / 6) * Math.PI, -phase.v(), tol);
		
		// 9/12
		
		r.setV(0);
		i.setV(-1);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals(2*Math.PI - (9.0 / 6) * Math.PI, -phase.v(), tol);
		
		// 10/12
		
		r.setV(0.5);
		i.setV(-Math.sqrt(3)/2);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals(2*Math.PI - (10.0 / 6) * Math.PI, -phase.v(), tol);
		
		// midpoint
		
		r.setV(Math.sqrt(2)/2);
		i.setV(-Math.sqrt(2)/2);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals(2*Math.PI - (7.0 / 4) * Math.PI, -phase.v(), tol);

		// 11/12
		
		r.setV(Math.sqrt(3)/2);
		i.setV(-0.5);

		PolarCoords.phase(G.DBL, r, i, phase);
		
		assertEquals(2*Math.PI - (11.0 / 6) * Math.PI, -phase.v(), tol);
	}

	@Test
	public void testQuadPhase() {

		final double tol = 0.000000000000001;
		
		Float128Member r = G.QUAD.construct();
		Float128Member i = G.QUAD.construct();
		Float128Member phase = G.QUAD.construct();
		
		// 0/12
		
		r.setV(BigDecimal.valueOf(1));
		i.setV(BigDecimal.valueOf(0));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals((0.0 / 6) * Math.PI, phase.v().doubleValue(), tol);
		
		// 1/12
		
		r.setV(BigDecimal.valueOf(Math.sqrt(3)/2));
		i.setV(BigDecimal.valueOf(0.5));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals((1.0/6) * Math.PI, phase.v().doubleValue(), tol);

		// midpoint
		
		r.setV(BigDecimal.valueOf(Math.sqrt(2)/2));
		i.setV(BigDecimal.valueOf(Math.sqrt(2)/2));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals((1.0 / 4) * Math.PI, phase.v().doubleValue(), tol);

		// 2/12
		
		r.setV(BigDecimal.valueOf(0.5));
		i.setV(BigDecimal.valueOf(Math.sqrt(3)/2));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals((2.0 / 6) * Math.PI, phase.v().doubleValue(), tol);
		
		// 3/12
		
		r.setV(BigDecimal.valueOf(0));
		i.setV(BigDecimal.valueOf(1));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals(Math.PI/2, phase.v().doubleValue(), tol);
		
		// 4/12
		
		r.setV(BigDecimal.valueOf(-0.5));
		i.setV(BigDecimal.valueOf(Math.sqrt(3)/2));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals((4.0 / 6) * Math.PI, phase.v().doubleValue(), tol);
		
		// midpoint
		
		r.setV(BigDecimal.valueOf(-Math.sqrt(2)/2));
		i.setV(BigDecimal.valueOf(Math.sqrt(2)/2));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals((3.0 / 4) * Math.PI, phase.v().doubleValue(), tol);

		// 5/12
		
		r.setV(BigDecimal.valueOf(-Math.sqrt(3)/2));
		i.setV(BigDecimal.valueOf(0.5));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals((5.0 / 6) * Math.PI, phase.v().doubleValue(), tol);
		
		// 6/12
		
		r.setV(BigDecimal.valueOf(-1));
		i.setV(BigDecimal.valueOf(0));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals((6.0 / 6) * Math.PI, phase.v().doubleValue(), tol);
		
		// 7/12
		
		r.setV(BigDecimal.valueOf(-Math.sqrt(3)/2));
		i.setV(BigDecimal.valueOf(-0.5));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals(2*Math.PI - (7.0 / 6) * Math.PI, -phase.v().doubleValue(), tol);
		
		// midpoint
		
		r.setV(BigDecimal.valueOf(-Math.sqrt(2)/2));
		i.setV(BigDecimal.valueOf(-Math.sqrt(2)/2));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals(2*Math.PI - (5.0 / 4) * Math.PI, -phase.v().doubleValue(), tol);

		// 8/12
		
		r.setV(BigDecimal.valueOf(-0.5));
		i.setV(BigDecimal.valueOf(-Math.sqrt(3)/2));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals(2*Math.PI - (8.0 / 6) * Math.PI, -phase.v().doubleValue(), tol);
		
		// 9/12
		
		r.setV(BigDecimal.valueOf(0));
		i.setV(BigDecimal.valueOf(-1));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals(2*Math.PI - (9.0 / 6) * Math.PI, -phase.v().doubleValue(), tol);
		
		// 10/12
		
		r.setV(BigDecimal.valueOf(0.5));
		i.setV(BigDecimal.valueOf(-Math.sqrt(3)/2));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals(2*Math.PI - (10.0 / 6) * Math.PI, -phase.v().doubleValue(), tol);
		
		// midpoint
		
		r.setV(BigDecimal.valueOf(Math.sqrt(2)/2));
		i.setV(BigDecimal.valueOf(-Math.sqrt(2)/2));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals(2*Math.PI - (7.0 / 4) * Math.PI, -phase.v().doubleValue(), tol);

		// 11/12
		
		r.setV(BigDecimal.valueOf(Math.sqrt(3)/2));
		i.setV(BigDecimal.valueOf(-0.5));

		PolarCoords.phase(G.QUAD, r, i, phase);
		
		assertEquals(2*Math.PI - (11.0 / 6) * Math.PI, -phase.v().doubleValue(), tol);
	}

}
