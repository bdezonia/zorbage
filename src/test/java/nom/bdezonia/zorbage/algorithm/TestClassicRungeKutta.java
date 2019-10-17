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
package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.data.float64.real.Float64VectorMember;
import nom.bdezonia.zorbage.type.storage.Storage;
import nom.bdezonia.zorbage.type.storage.datasource.ArrayDataSource;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestClassicRungeKutta {

	private static final double TOL = 0.00000000001;
	
	// Test data derived from http://www2.hawaii.edu/~jmcfatri/math407/RungeKuttaTest.html

	// test in one dimension
	
	@Test
	public void test1() {
		
		Procedure3<Float64Member,Float64Member,Float64Member> realDeriv =
				new Procedure3<Float64Member, Float64Member, Float64Member>()
		{
			@Override
			public void call(Float64Member t, Float64Member y, Float64Member result) {
				double val = y.v() * ((2/(Math.exp(t.v())+1))-1);
				result.setV(val);
			}
		};

		double deltaT = 1.0/256;
		int numSteps = 1536;
		double range = deltaT * (numSteps-1);
		
		// true analytic solution: 12*e^t/(e^t+1)^2
		double expected = 12 * Math.exp(range)/((Math.exp(range)+1)*(Math.exp(range)+1));
	
		Float64Member t0 = new Float64Member(0);
		Float64Member y0 = new Float64Member(3); // 3 == true analytic y value when t = 0
		Float64Member dt = new Float64Member(deltaT);
		Float64Member value = G.DBL.construct();
		
		IndexedDataSource<Float64Member> results = Storage.allocate(numSteps, value);
		
		ClassicRungeKutta.compute(G.DBL, G.DBL, realDeriv, t0, y0, numSteps, dt, results);
		
		results.get(numSteps-1, value);
		
		assertEquals(expected, value.v(), TOL);
	}

	// Test data worked out by hand
	
	// test in multiple dimensions
	
	@Test
	public void test2() {
		
		int NUM_STEPS = 2000;
		double SLOPE = 1.25;
		double DT = 1.0/1024;
		
		Procedure3<Float64Member,Float64VectorMember,Float64VectorMember> vectorDeriv =
				new Procedure3<Float64Member, Float64VectorMember, Float64VectorMember>()
		{
			@Override
			public void call(Float64Member t, Float64VectorMember y, Float64VectorMember result) {
				Float64Member scale = G.DBL.construct(((Double)(SLOPE)).toString());
				G.DBL_VEC.scale().call(scale, y, result);
			}
		};

		Float64Member t0 = G.DBL.construct(); // zero
		Float64VectorMember y0 = G.DBL_VEC.construct("[1,4,7]");
		Float64Member dt = G.DBL.construct(((Double)(DT)).toString());
		
		Float64VectorMember value = G.DBL_VEC.construct();

		Float64Member component = G.DBL.construct();

		IndexedDataSource<Float64VectorMember> results = ArrayDataSource.construct(G.DBL_VEC, NUM_STEPS);
		//IndexedDataSource<Float64VectorMember> results = ListDataSource.construct(G.DBL_VEC, NUM_STEPS);

		ClassicRungeKutta.compute(G.DBL_VEC, G.DBL, vectorDeriv, t0, y0, NUM_STEPS, dt, results);

		results.get(NUM_STEPS-1, value);
		
		assertEquals(3, value.length());

		double expected_value;

		// y = C * e^(at)
		// y(0) = 1
		//   1 = C * e^(SLOPE * 0)
		// y = 1 * e^(SLOPE * t)
		
		expected_value = 1.0 * Math.exp(SLOPE * (NUM_STEPS-1) * DT);
		value.v(0, component);
		assertEquals(expected_value, component.v(), TOL);
		
		// y = C * e^(at)
		// y(0) = 4
		//   4 = C * e^(SLOPE * 0)
		// y = 4 * e^(SLOPE * t)

		expected_value = 4.0 * Math.exp(SLOPE * (NUM_STEPS-1) * DT);
		value.v(1, component);
		assertEquals(expected_value, component.v(), TOL);
		
		// y = C * e^(at)
		// y(0) = 7
		//   7 = C * e^(SLOPE * 0)
		// y = 7 * e^(SLOPE * t)

		expected_value = 7.0 * Math.exp(SLOPE * (NUM_STEPS-1) * DT);
		value.v(2, component);
		assertEquals(expected_value, component.v(), TOL);
	}

}
