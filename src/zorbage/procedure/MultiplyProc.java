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
package zorbage.procedure;

import zorbage.basic.procedure.Procedure2;
import zorbage.basic.procedure.Procedure3;
import zorbage.groups.G;
import zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MultiplyProc implements Procedure3<Float64Member,Float64Member,Float64Member> {

	private final Procedure2<Float64Member,Float64Member> p1;
	private final Procedure2<Float64Member,Float64Member> p2;
    
    private static final ThreadLocal<Float64Member> x =
        new ThreadLocal<Float64Member>() {
            @Override protected Float64Member initialValue() {
                return new Float64Member();
        }
    };
    private static final ThreadLocal<Float64Member> y =
            new ThreadLocal<Float64Member>() {
                @Override protected Float64Member initialValue() {
                    return new Float64Member();
            }
        };
    
	public MultiplyProc(Procedure2<Float64Member,Float64Member> p1, Procedure2<Float64Member,Float64Member> p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	@Override
	public void call(Float64Member a, Float64Member b, Float64Member c) {
		p1.call(a, x.get());
		p2.call(b, y.get());
		G.DBL.multiply(x.get(), y.get(), c);
	}

}
