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
package nom.bdezonia.zorbage.type.universal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import nom.bdezonia.zorbage.algebra.*;
import org.junit.Test; import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.integer.int10.SignedInt10Member;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestFindCompatibleType {

	// NOTE: This is the common denominator signature that these supported algebras in the
	// FindCompatibleType share. One could write an algorithm that just used all the methods
	// present in these interfaces and would be portable among the types. You would construct
	// values from strings or from unity and zero via operations.
	
	@Test
	public <T extends Constructible<U> & Equality<U> & Addition<U> & Multiplication<U> &
				Scale<U,U> & ScaleByDouble<U> & ScaleByHighPrec<U> & ScaleByRational<U> & Unity<U>,
			U> 
		void test1()
	{
		T x;
		U a;
		
		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.DOUBLE);
		assertTrue(x == G.DBL);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(2, PrimitiveRepresentation.DOUBLE);
		assertTrue(x == G.CDBL);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(4, PrimitiveRepresentation.DOUBLE);
		assertTrue(x == G.QDBL);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(8, PrimitiveRepresentation.DOUBLE);
		assertTrue(x == G.ODBL);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();
		
		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.FLOAT);
		assertTrue(x == G.FLT);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(2, PrimitiveRepresentation.FLOAT);
		assertTrue(x == G.CFLT);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(4, PrimitiveRepresentation.FLOAT);
		assertTrue(x == G.QFLT);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(8, PrimitiveRepresentation.FLOAT);
		assertTrue(x == G.OFLT);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.BIGDECIMAL);
		assertTrue(x == G.HP);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(2, PrimitiveRepresentation.BIGDECIMAL);
		assertTrue(x == G.CHP);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(4, PrimitiveRepresentation.BIGDECIMAL);
		assertTrue(x == G.QHP);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(8, PrimitiveRepresentation.BIGDECIMAL);
		assertTrue(x == G.OHP);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.BYTE);
		assertTrue(x == G.INT8);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.SHORT);
		assertTrue(x == G.INT16);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.INT);
		assertTrue(x == G.INT32);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.LONG);
		assertTrue(x == G.INT64);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();

		x = FindCompatibleType.bestAlgebra(1, PrimitiveRepresentation.BIGINTEGER);
		assertTrue(x == G.UNBOUND);
		x.add();
		x.assign();
		x.construct();
		a = x.construct("4");
		x.construct(a);
		x.isEqual();
		x.isNotEqual();
		x.isZero();
		x.multiply();
		x.negate();
		x.power();
		x.scale();
		x.scaleByDouble();
		x.scaleByHighPrec();
		x.scaleByRational();
		x.subtract();
		x.unity();
		x.zero();
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
