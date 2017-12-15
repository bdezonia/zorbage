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
package nom.bdezonia.zorbage.type.data.universal;

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.sampling.IntegerIndex;

/**
 * 
 * @author Barry DeZonia
 *
 */
public interface PrimitiveConversion {
	
	PrimitiveRepresentation preferredRepresentation();


	// Dimensions
	//
	// 0, [] : number
	// 1, [m] : rmodule
	// 2, [m,n] : matrix
	// 3, [m,n,...] : tensor
	
	int numDimensions();
	
	long dimension(int i);
	
	// Components
	//
	// 1: real
	// 2: complex
	// 4: quaternion
	// 8: octonion
	
	int componentCount();
	
	// NOTE: index's numDims >= to dest object. dest only looks at dims it
	// knows. If pushed outside the bounds in dims it knows it should throw
	// excep if v is not zero. Meant to be used by PrimitiveConverter.
	
	void set(IntegerIndex index, int component, byte v);
	void set(IntegerIndex index, int component, short v);
	void set(IntegerIndex index, int component, int v);
	void set(IntegerIndex index, int component, long v);
	void set(IntegerIndex index, int component, float v);
	void set(IntegerIndex index, int component, double v);
	void set(IntegerIndex index, int component, BigInteger v);
	void set(IntegerIndex index, int component, BigDecimal v);

	// NOTE: any index possible. dest looks at all dims. If pushed outside
	// the bounds in any dim throw excep if v is not zero. Meant for general
	// use by any class.
	
	void setSafe(IntegerIndex index, int component, byte v);
	void setSafe(IntegerIndex index, int component, short v);
	void setSafe(IntegerIndex index, int component, int v);
	void setSafe(IntegerIndex index, int component, long v);
	void setSafe(IntegerIndex index, int component, float v);
	void setSafe(IntegerIndex index, int component, double v);
	void setSafe(IntegerIndex index, int component, BigInteger v);
	void setSafe(IntegerIndex index, int component, BigDecimal v);

	// NOTE: index's numDims >= to src object. src only looks at dims
	// it knows. Returns 0 otherwise. Meant for use by PrimitiveConverter.
	
	byte getAsByte(IntegerIndex index, int component);
	short getAsShort(IntegerIndex index, int component);
	int getAsInt(IntegerIndex index, int component);
	long getAsLong(IntegerIndex index, int component);
	float getAsFloat(IntegerIndex index, int component);
	double getAsDouble(IntegerIndex index, int component);
	BigInteger getAsBigInteger(IntegerIndex index, int component);
	BigDecimal getAsBigDecimal(IntegerIndex index, int component);
	
	// NOTE: any index possible. src looks at all dims. If pushed outside
	// the bounds in any dim returns 0. Meant for general use by any class.
	
	byte getAsByteSafe(IntegerIndex index, int component);
	short getAsShortSafe(IntegerIndex index, int component);
	int getAsIntSafe(IntegerIndex index, int component);
	long getAsLongSafe(IntegerIndex index, int component);
	float getAsFloatSafe(IntegerIndex index, int component);
	double getAsDoubleSafe(IntegerIndex index, int component);
	BigInteger getAsBigIntegerSafe(IntegerIndex index, int component);
	BigDecimal getAsBigDecimalSafe(IntegerIndex index, int component);
	
	// utility method
	void setZero();
}
