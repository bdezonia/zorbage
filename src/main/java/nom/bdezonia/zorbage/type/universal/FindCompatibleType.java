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
}
