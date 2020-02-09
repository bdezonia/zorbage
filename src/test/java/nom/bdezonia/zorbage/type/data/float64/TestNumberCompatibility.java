/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.float64;

import static org.junit.Assert.*;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestNumberCompatibility {

	private static final double TOL = 0.00000000000001;

	// prove that:
	//   the reals are embedded in complex, quat and oct spaces
	//   the complexes are embedded in quat and oct spaces
	//   the quats are embedded in oct space
	
	@Test
	public void testReal() {
		Float64Member dbl1 = G.DBL.construct();
		Float64Member dbl2 = G.DBL.construct();
		Float64Member dbl3 = G.DBL.construct();
		ComplexFloat64Member cdbl1 = G.CDBL.construct();
		ComplexFloat64Member cdbl2 = G.CDBL.construct();
		ComplexFloat64Member cdbl3 = G.CDBL.construct();
		QuaternionFloat64Member qdbl1 = G.QDBL.construct();
		QuaternionFloat64Member qdbl2 = G.QDBL.construct();
		QuaternionFloat64Member qdbl3 = G.QDBL.construct();
		OctonionFloat64Member odbl1 = G.ODBL.construct();
		OctonionFloat64Member odbl2 = G.ODBL.construct();
		OctonionFloat64Member odbl3 = G.ODBL.construct();
		
		dbl1.setV(23);
		dbl2.setV(19);
		
		cdbl1.setR(dbl1.v());
		cdbl1.setI(0);
		cdbl2.setR(dbl2.v());
		cdbl2.setI(0);
		
		qdbl1.setR(dbl1.v());
		qdbl1.setI(0);
		qdbl1.setJ(0);
		qdbl1.setK(0);
		qdbl2.setR(dbl2.v());
		qdbl2.setI(0);
		qdbl2.setJ(0);
		qdbl2.setK(0);
		
		odbl1.setR(dbl1.v());
		odbl1.setI(0);
		odbl1.setJ(0);
		odbl1.setK(0);
		odbl1.setL(0);
		odbl1.setI0(0);
		odbl1.setJ0(0);
		odbl1.setK0(0);
		odbl2.setR(dbl2.v());
		odbl2.setI(0);
		odbl2.setJ(0);
		odbl2.setK(0);
		odbl2.setL(0);
		odbl2.setI0(0);
		odbl2.setJ0(0);
		odbl2.setK0(0);

		// add
		
		G.DBL.add().call(dbl1, dbl2, dbl3);
		G.CDBL.add().call(cdbl1, cdbl2, cdbl3);
		G.QDBL.add().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.add().call(odbl1, odbl2, odbl3);

		assertEquals(dbl3.v(), cdbl3.r(), TOL);
		assertEquals(dbl3.v(), qdbl3.r(), TOL);
		assertEquals(dbl3.v(), odbl3.r(), TOL);
		
		assertEquals(0, cdbl3.i(), TOL);
		assertEquals(0, qdbl3.i(), TOL);
		assertEquals(0, odbl3.i(), TOL);

		assertEquals(0, qdbl3.j(), TOL);
		assertEquals(0, odbl3.j(), TOL);
		
		assertEquals(0, qdbl3.k(), TOL);
		assertEquals(0, odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);

		// subtract

		G.DBL.subtract().call(dbl1, dbl2, dbl3);
		G.CDBL.subtract().call(cdbl1, cdbl2, cdbl3);
		G.QDBL.subtract().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.subtract().call(odbl1, odbl2, odbl3);

		assertEquals(dbl3.v(), cdbl3.r(), TOL);
		assertEquals(dbl3.v(), qdbl3.r(), TOL);
		assertEquals(dbl3.v(), odbl3.r(), TOL);
		
		assertEquals(0, cdbl3.i(), TOL);
		assertEquals(0, qdbl3.i(), TOL);
		assertEquals(0, odbl3.i(), TOL);

		assertEquals(0, qdbl3.j(), TOL);
		assertEquals(0, odbl3.j(), TOL);
		
		assertEquals(0, qdbl3.k(), TOL);
		assertEquals(0, odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);

		
		// multiply
		
		G.DBL.multiply().call(dbl1, dbl2, dbl3);
		G.CDBL.multiply().call(cdbl1, cdbl2, cdbl3);
		G.QDBL.multiply().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.multiply().call(odbl1, odbl2, odbl3);

		assertEquals(dbl3.v(), cdbl3.r(), TOL);
		assertEquals(dbl3.v(), qdbl3.r(), TOL);
		assertEquals(dbl3.v(), odbl3.r(), TOL);
		
		assertEquals(0, cdbl3.i(), TOL);
		assertEquals(0, qdbl3.i(), TOL);
		assertEquals(0, odbl3.i(), TOL);

		assertEquals(0, qdbl3.j(), TOL);
		assertEquals(0, odbl3.j(), TOL);
		
		assertEquals(0, qdbl3.k(), TOL);
		assertEquals(0, odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);

		// divide

		G.DBL.divide().call(dbl1, dbl2, dbl3);
		G.CDBL.divide().call(cdbl1, cdbl2, cdbl3);
		G.QDBL.divide().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.divide().call(odbl1, odbl2, odbl3);

		assertEquals(dbl3.v(), cdbl3.r(), TOL);
		assertEquals(dbl3.v(), qdbl3.r(), TOL);
		assertEquals(dbl3.v(), odbl3.r(), TOL);
		
		assertEquals(0, cdbl3.i(), TOL);
		assertEquals(0, qdbl3.i(), TOL);
		assertEquals(0, odbl3.i(), TOL);

		assertEquals(0, qdbl3.j(), TOL);
		assertEquals(0, odbl3.j(), TOL);
		
		assertEquals(0, qdbl3.k(), TOL);
		assertEquals(0, odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);
	}

	@Test
	public void testComplex() {
		ComplexFloat64Member cdbl1 = G.CDBL.construct();
		ComplexFloat64Member cdbl2 = G.CDBL.construct();
		ComplexFloat64Member cdbl3 = G.CDBL.construct();
		QuaternionFloat64Member qdbl1 = G.QDBL.construct();
		QuaternionFloat64Member qdbl2 = G.QDBL.construct();
		QuaternionFloat64Member qdbl3 = G.QDBL.construct();
		OctonionFloat64Member odbl1 = G.ODBL.construct();
		OctonionFloat64Member odbl2 = G.ODBL.construct();
		OctonionFloat64Member odbl3 = G.ODBL.construct();
		
		cdbl1.setR(43);
		cdbl1.setI(19);
		cdbl2.setR(6);
		cdbl2.setI(-2);
		
		qdbl1.setR(cdbl1.r());
		qdbl1.setI(cdbl1.i());
		qdbl1.setJ(0);
		qdbl1.setK(0);
		qdbl2.setR(cdbl2.r());
		qdbl2.setI(cdbl2.i());
		qdbl2.setJ(0);
		qdbl2.setK(0);
		
		odbl1.setR(cdbl1.r());
		odbl1.setI(cdbl1.i());
		odbl1.setJ(0);
		odbl1.setK(0);
		odbl1.setL(0);
		odbl1.setI0(0);
		odbl1.setJ0(0);
		odbl1.setK0(0);
		odbl2.setR(cdbl2.r());
		odbl2.setI(cdbl2.i());
		odbl2.setJ(0);
		odbl2.setK(0);
		odbl2.setL(0);
		odbl2.setI0(0);
		odbl2.setJ0(0);
		odbl2.setK0(0);

		// add
		
		G.CDBL.add().call(cdbl1, cdbl2, cdbl3);
		G.QDBL.add().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.add().call(odbl1, odbl2, odbl3);

		assertEquals(cdbl3.r(), qdbl3.r(), TOL);
		assertEquals(cdbl3.r(), odbl3.r(), TOL);
		
		assertEquals(cdbl3.i(), qdbl3.i(), TOL);
		assertEquals(cdbl3.i(), odbl3.i(), TOL);

		assertEquals(0, qdbl3.j(), TOL);
		assertEquals(0, odbl3.j(), TOL);
		
		assertEquals(0, qdbl3.k(), TOL);
		assertEquals(0, odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);

		// subtract

		G.CDBL.subtract().call(cdbl1, cdbl2, cdbl3);
		G.QDBL.subtract().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.subtract().call(odbl1, odbl2, odbl3);

		assertEquals(cdbl3.r(), qdbl3.r(), TOL);
		assertEquals(cdbl3.r(), odbl3.r(), TOL);
		
		assertEquals(cdbl3.i(), qdbl3.i(), TOL);
		assertEquals(cdbl3.i(), odbl3.i(), TOL);

		assertEquals(0, qdbl3.j(), TOL);
		assertEquals(0, odbl3.j(), TOL);
		
		assertEquals(0, qdbl3.k(), TOL);
		assertEquals(0, odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);

		
		// multiply
		
		G.CDBL.multiply().call(cdbl1, cdbl2, cdbl3);
		G.QDBL.multiply().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.multiply().call(odbl1, odbl2, odbl3);

		assertEquals(cdbl3.r(), qdbl3.r(), TOL);
		assertEquals(cdbl3.r(), odbl3.r(), TOL);
		
		assertEquals(cdbl3.i(), qdbl3.i(), TOL);
		assertEquals(cdbl3.i(), odbl3.i(), TOL);

		assertEquals(0, qdbl3.j(), TOL);
		assertEquals(0, odbl3.j(), TOL);
		
		assertEquals(0, qdbl3.k(), TOL);
		assertEquals(0, odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);

		// divide

		G.CDBL.divide().call(cdbl1, cdbl2, cdbl3);
		G.QDBL.divide().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.divide().call(odbl1, odbl2, odbl3);

		assertEquals(cdbl3.r(), qdbl3.r(), TOL);
		assertEquals(cdbl3.r(), odbl3.r(), TOL);
		
		assertEquals(cdbl3.i(), qdbl3.i(), TOL);
		assertEquals(cdbl3.i(), odbl3.i(), TOL);

		assertEquals(0, qdbl3.j(), TOL);
		assertEquals(0, odbl3.j(), TOL);
		
		assertEquals(0, qdbl3.k(), TOL);
		assertEquals(0, odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);
	}

	@Test
	public void testQuaternion() {
		QuaternionFloat64Member qdbl1 = G.QDBL.construct();
		QuaternionFloat64Member qdbl2 = G.QDBL.construct();
		QuaternionFloat64Member qdbl3 = G.QDBL.construct();
		OctonionFloat64Member odbl1 = G.ODBL.construct();
		OctonionFloat64Member odbl2 = G.ODBL.construct();
		OctonionFloat64Member odbl3 = G.ODBL.construct();
		
		qdbl1.setR(-22);
		qdbl1.setI(-4);
		qdbl1.setJ(15);
		qdbl1.setK(-7);
		qdbl2.setR(13);
		qdbl2.setI(7);
		qdbl2.setJ(11);
		qdbl2.setK(-8);
		
		odbl1.setR(qdbl1.r());
		odbl1.setI(qdbl1.i());
		odbl1.setJ(qdbl1.j());
		odbl1.setK(qdbl1.k());
		odbl1.setL(0);
		odbl1.setI0(0);
		odbl1.setJ0(0);
		odbl1.setK0(0);
		odbl2.setR(qdbl2.r());
		odbl2.setI(qdbl2.i());
		odbl2.setJ(qdbl2.j());
		odbl2.setK(qdbl2.k());
		odbl2.setL(0);
		odbl2.setI0(0);
		odbl2.setJ0(0);
		odbl2.setK0(0);

		// add
		
		G.QDBL.add().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.add().call(odbl1, odbl2, odbl3);

		assertEquals(qdbl3.r(), odbl3.r(), TOL);

		assertEquals(qdbl3.i(), odbl3.i(), TOL);

		assertEquals(qdbl3.j(), odbl3.j(), TOL);
		
		assertEquals(qdbl3.k(), odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);

		// subtract

		G.QDBL.subtract().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.subtract().call(odbl1, odbl2, odbl3);

		assertEquals(qdbl3.r(), odbl3.r(), TOL);

		assertEquals(qdbl3.i(), odbl3.i(), TOL);

		assertEquals(qdbl3.j(), odbl3.j(), TOL);
		
		assertEquals(qdbl3.k(), odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);
		
		// multiply
		
		G.QDBL.multiply().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.multiply().call(odbl1, odbl2, odbl3);

		assertEquals(qdbl3.r(), odbl3.r(), TOL);

		assertEquals(qdbl3.i(), odbl3.i(), TOL);

		assertEquals(qdbl3.j(), odbl3.j(), TOL);
		
		assertEquals(qdbl3.k(), odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);

		// divide

		G.QDBL.divide().call(qdbl1, qdbl2, qdbl3);
		G.ODBL.divide().call(odbl1, odbl2, odbl3);

		assertEquals(qdbl3.r(), odbl3.r(), TOL);

		assertEquals(qdbl3.i(), odbl3.i(), TOL);

		assertEquals(qdbl3.j(), odbl3.j(), TOL);
		
		assertEquals(qdbl3.k(), odbl3.k(), TOL);

		assertEquals(0, odbl3.l(), TOL);
		
		assertEquals(0, odbl3.i0(), TOL);
		
		assertEquals(0, odbl3.j0(), TOL);
		
		assertEquals(0, odbl3.k0(), TOL);
	}
}
