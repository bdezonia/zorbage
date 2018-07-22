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
package nom.bdezonia.zorbage.procedure.impl;

import nom.bdezonia.zorbage.groups.G;
import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.impl.parse.EquationParser;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RealEquation implements Procedure<Float64Member>{

	private final Tuple2<String, Procedure<Float64Member>> tuple;
	
	public RealEquation(String eqn) {
		EquationParser<Float64Group, Float64Member> parser =
				new EquationParser<Float64Group, Float64Member>();
		tuple = parser.parse(G.DBL, eqn);
	}
	
	@Override
	public void call(Float64Member result, Float64Member... inputs) {
		if (error() == null)
			tuple.b().call(result, inputs);
		else
			G.DBL.zero().call(result);
	}
	
	public String error() {
		return tuple.a();
	}

}
