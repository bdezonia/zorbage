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

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Dimensioned;

/**
 * 
 * @author Barry DeZonia
 *
 */
public interface PrimitiveConversion extends Dimensioned{
	
	PrimitiveRepresentation preferredRepresentation();


	// Dimensions
	//
	// 0, [] : number
	// 1, [m] : rmodule
	// 2, [m,n] : matrix
	// 3, [m,n,...] : tensor
	
	//int numDimensions();    // from Dimensioned interface
	
	//long dimension(int i);  // from Dimensioned interface
	
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
	
	void primComponentSetByte(IntegerIndex index, int component, byte v);
	void primComponentSetShort(IntegerIndex index, int component, short v);
	void primComponentSetInt(IntegerIndex index, int component, int v);
	void primComponentSetLong(IntegerIndex index, int component, long v);
	void primComponentSetFloat(IntegerIndex index, int component, float v);
	void primComponentSetDouble(IntegerIndex index, int component, double v);
	void primComponentSetBigInteger(IntegerIndex index, int component, BigInteger v);
	void primComponentSetBigDecimal(IntegerIndex index, int component, BigDecimal v);

	// NOTE: any index possible. dest looks at all dims. If pushed outside
	// the bounds in any dim throw excep if v is not zero. Meant for general
	// use by any class.
	
	void primComponentSetByteSafe(IntegerIndex index, int component, byte v);
	void primComponentSetShortSafe(IntegerIndex index, int component, short v);
	void primComponentSetIntSafe(IntegerIndex index, int component, int v);
	void primComponentSetLongSafe(IntegerIndex index, int component, long v);
	void primComponentSetFloatSafe(IntegerIndex index, int component, float v);
	void primComponentSetDoubleSafe(IntegerIndex index, int component, double v);
	void primComponentSetBigIntegerSafe(IntegerIndex index, int component, BigInteger v);
	void primComponentSetBigDecimalSafe(IntegerIndex index, int component, BigDecimal v);

	// NOTE: index's numDims >= to src object. src only looks at dims
	// it knows. Returns 0 otherwise. Meant for use by PrimitiveConverter.
	
	byte primComponentGetAsByte(IntegerIndex index, int component);
	short primComponentGetAsShort(IntegerIndex index, int component);
	int primComponentGetAsInt(IntegerIndex index, int component);
	long primComponentGetAsLong(IntegerIndex index, int component);
	float primComponentGetAsFloat(IntegerIndex index, int component);
	double primComponentGetAsDouble(IntegerIndex index, int component);
	BigInteger primComponentGetAsBigInteger(IntegerIndex index, int component);
	BigDecimal primComponentGetAsBigDecimal(IntegerIndex index, int component);
	
	// NOTE: any index possible. src looks at all dims. If pushed outside
	// the bounds in any dim returns 0. Meant for general use by any class.
	
	byte primComponentGetAsByteSafe(IntegerIndex index, int component);
	short primComponentGetAsShortSafe(IntegerIndex index, int component);
	int primComponentGetAsIntSafe(IntegerIndex index, int component);
	long primComponentGetAsLongSafe(IntegerIndex index, int component);
	float primComponentGetAsFloatSafe(IntegerIndex index, int component);
	double primComponentGetAsDoubleSafe(IntegerIndex index, int component);
	BigInteger primComponentGetAsBigIntegerSafe(IntegerIndex index, int component);
	BigDecimal primComponentGetAsBigDecimalSafe(IntegerIndex index, int component);
	
	// Utility method: this is not meant for general use but only in the
	//   context of PrimitiveConverter and it's use there. Implementing
	//   classes make assumptions about how it is called.
	
	void primitiveInit();
}
