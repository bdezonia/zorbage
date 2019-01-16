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
package nom.bdezonia.zorbage.procedure.impl.parse;

import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.impl.ConstantL;
import nom.bdezonia.zorbage.procedure.impl.SinL;
import nom.bdezonia.zorbage.procedure.impl.ZeroL;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.algebra.Algebra;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class EquationParser<T extends Algebra<T,U> & Trigonometric<U>,U> {

	public Tuple2<String,Procedure<U>> parse(T group, String string) {
		Tuple2<String,Procedure<U>> tuple = new Tuple2<String,Procedure<U>>(null, null);
		
		tuple.setA("Unimplemented");
		tuple.setB(new ZeroL<T,U>(group));
		
		//tuple.setA(null);
		//tuple.setB(new SinL<T,U>(group, new ConstantL<T,U>(group,group.construct())));
		
		return tuple;
	}
	
	
}
