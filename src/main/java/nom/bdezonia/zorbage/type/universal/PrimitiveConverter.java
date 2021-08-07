/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class PrimitiveConverter {

	// do not instantiate
	
	private PrimitiveConverter() {}

	/**
	 * Primitiveconverter.convert()
	 * 
	 * Translate data from one value to another. This version of convert is very expensive
	 * since it allocates all necessary temp values each time it is called. It is liekly
	 * better to use the other signature for PrimitiveConverter.convert() where temps are
	 * passed in and a reusable from call to call. This routine useful if just converting
	 * one value once.
	 *  
	 * @param from The value being converted from.
	 * @param to The value receiving the conversion.
	 */
	public static void convert(PrimitiveConversion from, PrimitiveConversion to)
	{
		IntegerIndex tmp1 = new IntegerIndex(Math.max(from.numDimensions(), to.numDimensions()));
		IntegerIndex tmp2 = new IntegerIndex(tmp1.numDimensions());
		IntegerIndex tmp3 = new IntegerIndex(tmp1.numDimensions());
		convert(tmp1,  tmp2, tmp3, from, to);
	}
	
	/**
	 * PrimitiveConverter.convert()
	 * 
	 * @param tmp1 An IntegerIndex whose size = max dim of from and to. Contents overwritten.
	 * @param tmp2 An IntegerIndex whose size = max dim of from and to. Contents overwritten.
	 * @param tmp3 An IntegerIndex whose size = max dim of from and to. Contents overwritten.
	 * @param from The value being converted from.
	 * @param to The value receiving the conversion.
	 */
	public static
		void convert(IntegerIndex tmp1, IntegerIndex tmp2, IntegerIndex tmp3,
						PrimitiveConversion from, PrimitiveConversion to)
	{
		final int numD = Math.max(from.numDimensions(), to.numDimensions());
		if (tmp1.numDimensions() != numD)
			throw new IllegalArgumentException("invalid tmp1 space");
		if (tmp2.numDimensions() != numD)
			throw new IllegalArgumentException("invalid tmp2 space");
		if (tmp3.numDimensions() != numD)
			throw new IllegalArgumentException("invalid tmp3 space");
		if (numD == 0) {
			// just a number: it won't iterate so do one direct conversion
			convertOneValue(tmp1, from, to);
		}
		else { // has nonzero dimensionality: will iterate
	
			IntegerIndex min = tmp1;
			IntegerIndex max = tmp2;
			IntegerIndex tmp = tmp3;

			// should dest data change shape? or stay fixed? I think fixed.
			// determine minimum overlap between the two objects

			final int minD = Math.min(from.numDimensions(), to.numDimensions());
			
			for (int i = 0; i < minD; i++) {
				min.set(i, 0);
				max.set(i, Math.min(from.dimension(i), to.dimension(i))-1);
				// TODO: is the -1 correct? will min {0,0} max {0,0} iterate one
				// spot. Must check.
			}

			for (int i = minD; i < numD; i++) {
				min.set(i, 0);
				max.set(i, 0);
			}
			
			// Make sure dest is 0 in all components so that those outside
			// the overlap are correctly initialized. Could only set those
			// to 0 that need it (saving cpu cycles) but that makes code more
			// complex. Skip that approach for now.
			
			to.primitiveInit();

			// iterate the overlap and set values
			
			SamplingIterator<IntegerIndex> iter = GridIterator.compute(min, max);
			while (iter.hasNext()) {
				iter.next(tmp);
				convertOneValue(tmp, from, to);
			}
		}
	}
	
	private static
		void convertOneValue(IntegerIndex index, PrimitiveConversion from, PrimitiveConversion to)
	{
		switch (from.preferredRepresentation()) {
			case BYTE:
				for (int j = 0; j < to.componentCount(); j++) {
					byte v = from.primComponentGetAsByte(index, j);
					to.primComponentSetByte(index, j, v);
				}
				break;
			case SHORT:
				for (int j = 0; j < to.componentCount(); j++) {
					short v = from.primComponentGetAsShort(index, j);
					to.primComponentSetShort(index, j, v);
				}
				break;
			case INT:
				for (int j = 0; j < to.componentCount(); j++) {
					int v = from.primComponentGetAsInt(index, j);
					to.primComponentSetInt(index, j, v);
				}
				break;
			case LONG:
				for (int j = 0; j < to.componentCount(); j++) {
					long v = from.primComponentGetAsLong(index, j);
					to.primComponentSetLong(index, j, v);
				}
				break;
			case FLOAT:
				for (int j = 0; j < to.componentCount(); j++) {
					float v = from.primComponentGetAsFloat(index, j);
					to.primComponentSetFloat(index, j, v);
				}
				break;
			case DOUBLE:
				for (int j = 0; j < to.componentCount(); j++) {
					double v = from.primComponentGetAsDouble(index, j);
					to.primComponentSetDouble(index, j, v);
				}
				break;
			case BIGINTEGER:
				for (int j = 0; j < to.componentCount(); j++) {
					BigInteger v = from.primComponentGetAsBigInteger(index, j);
					to.primComponentSetBigInteger(index, j, v);
				}
				break;
			case BIGDECIMAL:
				for (int j = 0; j < to.componentCount(); j++) {
					BigDecimal v = from.primComponentGetAsBigDecimal(index, j);
					to.primComponentSetBigDecimal(index, j, v);
				}
				break;
			default:
				throw new IllegalArgumentException("unknown primitive transfer type");
		}
	}
}
