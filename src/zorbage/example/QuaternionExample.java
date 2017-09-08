/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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

import zorbage.type.data.float64.Float64Member;
import zorbage.type.data.float64.QuaternionFloat64Group;
import zorbage.type.data.float64.QuaternionFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionExample {

	private boolean near(double d1, double d2) {
		return Math.abs(d1-d2) < 0.000000000000001;
	}
	
	public void run() {
		QuaternionFloat64Group qdbl = new QuaternionFloat64Group();
		
		QuaternionFloat64Member q1 = qdbl.construct();
		
		// TODO define a ctor that takes four doubles
		q1.setR(1);
		q1.setI(-2);
		q1.setJ(3);
		q1.setK(2);
		
		Float64Member tmp = new Float64Member();
		
		qdbl.norm(q1, tmp);
		
		System.out.println("Quaternion 1st norm test is correct: " + (tmp.v() == (3*Math.sqrt(2))));
		
		QuaternionFloat64Member q2 = qdbl.construct();

		q2.setR(11);
		q2.setI(-2);
		q2.setJ(0);
		q2.setK(-2);

		qdbl.norm(q2, tmp);

		System.out.println("Quaternion 2nd norm test is correct: " + (tmp.v() == (Math.sqrt(129))));
		
		QuaternionFloat64Member q3 = qdbl.construct();

		qdbl.add(q1, q2, q3);
		
		System.out.println("Quaternion add worked: " + ((q1.r()+q2.r() == q3.r()) && (q1.i()+q2.i() == q3.i()) && (q1.j()+q2.j() == q3.j()) && (q1.k()+q2.k() == q3.k())));
		
		qdbl.subtract(q1, q2, q3);

		System.out.println("Quaternion subtract worked: " + ((q1.r()-q2.r() == q3.r()) && (q1.i()-q2.i() == q3.i()) && (q1.j()-q2.j() == q3.j()) && (q1.k()-q2.k() == q3.k())));

		qdbl.multiply(q1, q2, q3);
		
		System.out.println("Quaternion multiply worked: " + ((q3.r() == 11) && (q3.i() == -30) && (q3.j() == 25) && (q3.k() == 26)));
		
		qdbl.divide(q3, q2, q1);

		System.out.println("Quaternion divide worked: " + (near(q1.r(),1) && near(q1.i(),-2) && near(q1.j(),3) && near(q1.k(),2)));
		
	}
}
