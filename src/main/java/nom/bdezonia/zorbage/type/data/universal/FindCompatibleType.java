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

import nom.bdezonia.zorbage.algebras.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FindCompatibleType {

	/**
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static PrimitiveRepresentation bestRep(PrimitiveConversion a, PrimitiveConversion b)
	{
		if ((a.preferredRepresentation() == PrimitiveRepresentation.BIGDECIMAL) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.BIGDECIMAL))
			return PrimitiveRepresentation.BIGDECIMAL;

		if ((a.preferredRepresentation() == PrimitiveRepresentation.DOUBLE) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.DOUBLE))
			return PrimitiveRepresentation.DOUBLE;
		
		if ((a.preferredRepresentation() == PrimitiveRepresentation.FLOAT) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.FLOAT))
			return PrimitiveRepresentation.FLOAT;

		if ((a.preferredRepresentation() == PrimitiveRepresentation.BIGINTEGER) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.BIGINTEGER))
			return PrimitiveRepresentation.BIGINTEGER;
		
		if ((a.preferredRepresentation() == PrimitiveRepresentation.LONG) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.LONG))
			return PrimitiveRepresentation.LONG;

		if ((a.preferredRepresentation() == PrimitiveRepresentation.INT) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.INT))
			return PrimitiveRepresentation.INT;
		
		if ((a.preferredRepresentation() == PrimitiveRepresentation.SHORT) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.SHORT))
			return PrimitiveRepresentation.SHORT;
		
		if ((a.preferredRepresentation() == PrimitiveRepresentation.BYTE) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.BYTE))
			return PrimitiveRepresentation.BYTE;
		
		throw new IllegalArgumentException("unknown representations "+a.preferredRepresentation()+" "+b.preferredRepresentation());
	}

	/**
	 * 
	 * @param components
	 * @param rep
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>
		T bestAlgebra(int components, PrimitiveRepresentation rep)
	{
		if (components <= 0)
			throw new IllegalArgumentException("must specify 1 or more components");
		
		if (components > 8)
			throw new IllegalArgumentException("must specify 8 or fewer components");
		
		switch (rep) {
		case BIGDECIMAL:
			if (components == 1)
				return (T) G.HP;
			else if (components == 2)
				return (T) G.CHP;
			else if (components == 4)
				return (T) G.QHP;
			else if (components == 8)
				return (T) G.OHP;
			break;
		case BIGINTEGER:
			if (components == 1)
				return (T) G.UNBOUND;
			break;
		case DOUBLE:
			if (components == 1)
				return (T) G.DBL;
			else if (components <= 2)
				return (T) G.CDBL;
			else if (components <= 4)
				return (T) G.QDBL;
			else if (components <= 8)
				return (T) G.ODBL;
			break;
		case FLOAT:
			if (components == 1)
				return (T) G.FLT;
			else if (components <= 2)
				return (T) G.CFLT;
			else if (components <= 4)
				return (T) G.QFLT;
			else if (components <= 8)
				return (T) G.OFLT;
			break;
		case LONG:
			if (components == 1)
				return (T) G.INT64;
			break;
		case INT:
			if (components == 1)
				return (T) G.INT32;
			break;
		case SHORT:
			if (components == 1)
				return (T) G.INT16;
			break;
		case BYTE:
			if (components == 1)
				return (T) G.INT8;
			break;
		default:
			throw new IllegalArgumentException("unknown primitive rep "+rep);
		}
		
		throw new IllegalArgumentException("can't find appropriate type");
	}
}
