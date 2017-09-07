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

import zorbage.type.data.Float64Member;
import zorbage.type.data.QuaternionFloat64Group;
import zorbage.type.data.QuaternionFloat64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionExample {

	public void run() {
		QuaternionFloat64Group qdbl = new QuaternionFloat64Group();
		
		QuaternionFloat64Member q = qdbl.construct();
		
		// TODO define a ctor that takes four doubles
		q.setR(1);
		q.setI(-2);
		q.setJ(3);
		q.setK(2);
		
		Float64Member tmp = new Float64Member();
		
		qdbl.norm(q, tmp);
		
		System.out.println("Quaternion 1st norm test is correct: " + (tmp.v() == (3*Math.sqrt(2))));
		
		q.setR(11);
		q.setI(-2);
		q.setJ(0);
		q.setK(-2);

		qdbl.norm(q, tmp);

		System.out.println("Quaternion 2nd norm test is correct: " + (tmp.v() == (Math.sqrt(129))));
	}
}
