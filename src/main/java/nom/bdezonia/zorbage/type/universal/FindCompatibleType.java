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

import nom.bdezonia.zorbage.algebra.G;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FindCompatibleType {

	// do not instantiate
	
	private FindCompatibleType() { }
	
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
			if (components <= 2)
				return (T) G.CHP;
			if (components <= 4)
				return (T) G.QHP;
			if (components <= 8)
				return (T) G.OHP;
			break;
		case BIGINTEGER:
			if (components == 1)
				return (T) G.UNBOUND;
			if (components == 2)
				return (T) G.GAUSSU;
			break;
		case DOUBLE:
			if (components == 1)
				return (T) G.DBL;
			if (components <= 2)
				return (T) G.CDBL;
			if (components <= 4)
				return (T) G.QDBL;
			if (components <= 8)
				return (T) G.ODBL;
			break;
		case FLOAT:
			if (components == 1)
				return (T) G.FLT;
			if (components <= 2)
				return (T) G.CFLT;
			if (components <= 4)
				return (T) G.QFLT;
			if (components <= 8)
				return (T) G.OFLT;
			break;
		case LONG:
			if (components == 1)
				return (T) G.INT64;
			if (components == 2)
				return (T) G.GAUSS64;
			break;
		case INT:
			if (components == 1)
				return (T) G.INT32;
			if (components == 2)
				return (T) G.GAUSS32;
			break;
		case SHORT:
			if (components == 1)
				return (T) G.INT16;
			if (components == 2)
				return (T) G.GAUSS16;
			break;
		case BYTE:
			if (components == 1)
				return (T) G.INT8;
			if (components == 2)
				return (T) G.GAUSS8;
			if (components == 3)
				return (T) G.RGB;
			if (components == 4)
				return (T) G.ARGB;
			break;
		default:
			throw new IllegalArgumentException("unknown primitive rep "+rep);
		}
		
		throw new IllegalArgumentException("can't find appropriate type");
	}
}
