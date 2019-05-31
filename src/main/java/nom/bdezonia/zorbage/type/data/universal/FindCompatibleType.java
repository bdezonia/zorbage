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

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FindCompatibleType {

	public static PrimitiveRepresentation find(PrimitiveConversion a, PrimitiveConversion b)
	{
		if ((a.preferredRepresentation() == PrimitiveRepresentation.DOUBLE) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.DOUBLE)) {
			if ((a.preferredRepresentation() == PrimitiveRepresentation.BIGDECIMAL) ||
					(b.preferredRepresentation() == PrimitiveRepresentation.BIGDECIMAL))
				return PrimitiveRepresentation.BIGDECIMAL;
			return PrimitiveRepresentation.DOUBLE;
		}
		if ((a.preferredRepresentation() == PrimitiveRepresentation.FLOAT) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.FLOAT)) {
			if ((a.preferredRepresentation() == PrimitiveRepresentation.BIGDECIMAL) ||
					(b.preferredRepresentation() == PrimitiveRepresentation.BIGDECIMAL))
				return PrimitiveRepresentation.BIGDECIMAL;
			if ((a.preferredRepresentation() == PrimitiveRepresentation.DOUBLE) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.DOUBLE))
				return PrimitiveRepresentation.DOUBLE;
			return PrimitiveRepresentation.FLOAT;
		}
		if ((a.preferredRepresentation() == PrimitiveRepresentation.BIGDECIMAL) ||
				(b.preferredRepresentation() == PrimitiveRepresentation.BIGDECIMAL))
			return PrimitiveRepresentation.BIGDECIMAL;
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
