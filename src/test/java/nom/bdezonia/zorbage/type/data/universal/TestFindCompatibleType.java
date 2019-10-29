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
package nom.bdezonia.zorbage.type.data.universal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.type.algebra.Addition;
import nom.bdezonia.zorbage.type.algebra.Constructable;
import nom.bdezonia.zorbage.type.algebra.Equality;
import nom.bdezonia.zorbage.type.algebra.Multiplication;
import nom.bdezonia.zorbage.type.algebra.Random;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;
import nom.bdezonia.zorbage.type.algebra.Unity;
import nom.bdezonia.zorbage.type.data.float16.quaternion.QuaternionFloat16Member;
import nom.bdezonia.zorbage.type.data.int10.SignedInt10Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFindCompatibleType {

	// NOTE: This is the common denominator signature that these suported algebras in the
	// FindCompatibleType share. One could write an algorithm that just used these methods
	// and would be portable among the types.
	
	@Test
	public <T extends Constructable<U> & Equality<U> & Addition<U> & Multiplication<U> & Random<U> &
				Scale<U,U> & ScaleByDouble<U> & ScaleByHighPrec<U> & ScaleByRational<U> & Unity<U>,
			U> 
		void test1()
	{
		T x;
		U a;
		U b;
		
		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.DOUBLE);
		assertTrue(x == G.DBL);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(2, PrimitiveRepresentation.DOUBLE);
		assertTrue(x == G.CDBL);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(4, PrimitiveRepresentation.DOUBLE);
		assertTrue(x == G.QDBL);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(8, PrimitiveRepresentation.DOUBLE);
		assertTrue(x == G.ODBL);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));
		
		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.FLOAT);
		assertTrue(x == G.FLT);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(2, PrimitiveRepresentation.FLOAT);
		assertTrue(x == G.CFLT);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(4, PrimitiveRepresentation.FLOAT);
		assertTrue(x == G.QFLT);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(8, PrimitiveRepresentation.FLOAT);
		assertTrue(x == G.OFLT);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.BIGDECIMAL);
		assertTrue(x == G.HP);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(2, PrimitiveRepresentation.BIGDECIMAL);
		assertTrue(x == G.CHP);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(4, PrimitiveRepresentation.BIGDECIMAL);
		assertTrue(x == G.QHP);
		a = x.construct("3");
		b = x.construct("9");
		//x.multiply().call(a,a,a);
		//assertTrue(x.isEqual().call(a,b));
		assertTrue(x.isNotEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(8, PrimitiveRepresentation.BIGDECIMAL);
		assertTrue(x == G.OHP);
		a = x.construct("3");
		b = x.construct("9");
		//x.multiply().call(a,a,a);
		//assertTrue(x.isEqual().call(a,b));
		assertTrue(x.isNotEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.BYTE);
		assertTrue(x == G.INT8);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.SHORT);
		assertTrue(x == G.INT16);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.INT);
		assertTrue(x == G.INT32);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.LONG);
		assertTrue(x == G.INT64);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.BIGINTEGER);
		assertTrue(x == G.UNBOUND);
		a = x.construct("3");
		b = x.construct("9");
		x.multiply().call(a,a,a);
		assertTrue(x.isEqual().call(a,b));
	}
	
	// Note: a typical way to find a common type follows.
	
	@Test
	public <T> void test2() {
		SignedInt10Member a = G.INT10.construct();
		QuaternionFloat16Member b = G.QHLF.construct();
		PrimitiveRepresentation rep = FindCompatibleType.bestRep(a, b);
		int componentCount = Math.max(a.componentCount(), b.componentCount());
		T alg = FindCompatibleType.bestAlgebra(componentCount, rep);
		assertEquals(G.QFLT, alg);
	}
}
