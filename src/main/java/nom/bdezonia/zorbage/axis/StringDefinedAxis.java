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
package nom.bdezonia.zorbage.axis;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.procedure.Procedure;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.impl.ZeroL;
import nom.bdezonia.zorbage.procedure.impl.parse.EquationParser;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.data.floatunlim.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.data.floatunlim.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class StringDefinedAxis implements Procedure2<Long,HighPrecisionMember>
{
	private final Procedure<HighPrecisionMember> parsedAxisProc;
	
	public StringDefinedAxis(String eqn) {
		Tuple2<String, Procedure<HighPrecisionMember>> parseResult = new EquationParser<HighPrecisionAlgebra,HighPrecisionMember>().parse(G.FLOAT_UNLIM, eqn);
		if (parseResult.a() == null)
			parsedAxisProc = parseResult.b();
		else
			parsedAxisProc = new ZeroL<HighPrecisionAlgebra,HighPrecisionMember>(G.FLOAT_UNLIM);
	}
	
	@Override
	public void call(Long in, HighPrecisionMember out) {
		
		// set input value for axis transformation
		out.setV(BigDecimal.valueOf(in));
		
		// left side out set to axis transformation of right side out
		parsedAxisProc.call(out, out);

	}

}
