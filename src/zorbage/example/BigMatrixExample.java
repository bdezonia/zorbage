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

import zorbage.type.ctor.MemoryConstruction;
import zorbage.type.ctor.StorageConstruction;
import zorbage.type.data.float64.Float64Group;
import zorbage.type.data.float64.Float64Matrix;
import zorbage.type.data.float64.Float64MatrixMember;
import zorbage.type.data.float64.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BigMatrixExample {

	public void run() {
		System.out.println("Making a huge virtual matrix > 2 gig entries");
		Float64Matrix mat = new Float64Matrix();
		Float64Group dbl = new Float64Group();
		Float64MatrixMember m = mat.construct(MemoryConstruction.DENSE, StorageConstruction.FILE, 50000, 50000);
		mat.unity(m);
		Float64Member value = dbl.construct();
		Float64Member zero = dbl.construct();
		Float64Member one = dbl.construct();
		dbl.unity(one);
		for (long r = 0; r < m.rows(); r++) {
			for (long c = 0; c < m.cols(); c++) {
				m.v(r, c, value);
				if (r == c) {
					if (!dbl.isEqual(value, one))
						System.out.println("data mismatch error: not one");
				}
				else {
					if (!dbl.isEqual(value, zero))
						System.out.println("data mismatch error: not zero");
				}
			}
		}
		System.out.println("  Success.");
	}
}
