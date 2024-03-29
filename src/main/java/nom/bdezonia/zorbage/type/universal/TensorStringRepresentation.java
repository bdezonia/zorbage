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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nom.bdezonia.zorbage.misc.BigList;
import nom.bdezonia.zorbage.misc.LongUtils;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorStringRepresentation {

	private final long[] dimensions;
	private final BigList<OctonionRepresentation> elements;
	private BigDecimal tmp;
	
	public TensorStringRepresentation(String s) {
		List<Character> chars = preprocessCharacters(s);
		dimensions = determineDimensions(chars);
		elements = new BigList<OctonionRepresentation>();
		gatherOctonions(chars);
		if (elements.size() != LongUtils.numElements(dimensions)) {
			System.out.println("Parsed "+elements.size()+" values");
			System.out.println("Dims "+Arrays.toString(dimensions));
			throw new IllegalArgumentException("numbers parsed does not match dimensions of input tensor");
		}
	}
	
	public long[] dimensions() {
		return dimensions;
	}

	public OctonionRepresentation value(long[] index) {
		long idx = 0;
		long increment = 1;
		for (int i = 0; i < index.length; i++) {
			idx += index[i] * increment;
			increment *= dimensions[i];
		}
		return elements.get(idx);
	}
	
	public BigList<OctonionRepresentation> values() { return elements; }
	
	public OctonionRepresentation firstValue() {
		return elements.get(0);
	}
	
	public long firstVectorDimension() {
		long dim = 1;
		if (dimensions.length >= 0) dim = dimensions[0];
		return dim;
	}

	public BigList<OctonionRepresentation> firstVectorValues() {
		long count = firstVectorDimension();
		BigList<OctonionRepresentation> vectorElements = new BigList<OctonionRepresentation>(count, new OctonionRepresentation());
		for (long i = 0; i < count; i++)
			vectorElements.set(i,elements.get(i));
		return vectorElements;
	}

	public long[] firstMatrixDimensions() {
		long[] dims = new long[]{1,1};
		if (dimensions.length >= 0) dims[0] = dimensions[0];
		if (dimensions.length >= 1) dims[1] = dimensions[1];
		return dims;
	}
	
	public BigList<OctonionRepresentation> firstMatrixValues() {
		long[] dims = firstMatrixDimensions();
		long count = dims[0] * dims[1];
		BigList<OctonionRepresentation> matrixElements = new BigList<OctonionRepresentation>(count, new OctonionRepresentation());
		for (long i = 0; i < count; i++)
			matrixElements.set(i,elements.get(i));
		return matrixElements;
	}
	
	private List<Character> preprocessCharacters(String s) {
		List<Character> chars = new ArrayList<Character>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!Character.isWhitespace(c))
				chars.add(c);
		}
		return chars;
	}

	// ugly but works
	
	private long[] determineDimensions(List<Character> chars) {
		List<Long> reverseDims = new ArrayList<Long>();
		int unmatchedBrackets = 0;  // TODO: does this need to be a long?
		long innermostDim = 1;
		boolean inOct = false;
		for (int i = 0; i < chars.size(); i++) {
			char ch = chars.get(i);
			if (ch == '[') {
				innermostDim = 1;
				if (unmatchedBrackets < reverseDims.size())
					reverseDims.set(unmatchedBrackets, reverseDims.get(unmatchedBrackets) + 1);
				else
					reverseDims.add(Long.valueOf(1));
				unmatchedBrackets++;
			}
			else if (ch == ']') {
				unmatchedBrackets--;
				if (unmatchedBrackets < 0)
					throw new IllegalArgumentException("unexpected ] bracket in number parsing at index " + i + " of string " + Arrays.toString(chars.toArray()));
			}
			else if (ch == '{')
				inOct = true;
			else if (ch == '}')
				inOct = false;
			else if (ch == ',' && inOct == false)
				innermostDim++;
		}
		if (unmatchedBrackets != 0)
			throw new IllegalArgumentException("unmatched [ ] brackets in tensor definition");
		for (int i = reverseDims.size()-1; i > 1; i--) {
			// new
			long sz = reverseDims.get(i-1);
			// old
			//long sz = 1;
			//for (int j = 0; j < i; j++) {
			//	sz *= reverseDims.get(j);
			//}
			reverseDims.set(i, reverseDims.get(i) / sz);
		}
		reverseDims.add(innermostDim);
		long[] dims = new long[reverseDims.size()];
		int x = 0;
		for (int i = reverseDims.size()-1; i >= 0; i--)
			dims[x++] = reverseDims.get(i);
		if ((dims.length > 1) && (dims[dims.length-1] == 1)) {
			long[] tmp = new long[dims.length-1];
			for (int i = 0; i < tmp.length; i++)
				tmp[i] = dims[i];
			dims = tmp;
		}
		return dims;
	}

	private int findNextNumber(List<Character> chars, int ptr) {
		do {
			if (ptr >= chars.size()) return chars.size();
			char ch = chars.get(ptr);
			if (ch == '[' || ch == ']' || ch == ',')
				ptr++;
			else
				return ptr;
		} while (true);
	}
	
	private void gatherOctonions(List<Character> chars) {
		//tensor(chars);
		int ptr = 0;
		while (ptr < chars.size()) {
			ptr = findNextNumber(chars, ptr);
			if (ptr < chars.size())
				ptr = octonion(chars, ptr);
		}
	}

	// tensor = number | numberAlgebras
	// numberAlgebras = [ numberAlgebras ] | numbers
	// numberAlgebra = numbers | numberAlgebras
	// numbers = number | number , numbers
    // number = num | ( 1-8 csv nums )
	// num = +|- digits . digits 1.4e+05 etc.
	
	private int octonion(List<Character> input, int ptr) {
		BigDecimal[] vals = new BigDecimal[8];
		if (input.get(ptr) == '{') {
			int count = 0;
			while ((ptr+1) < input.size() && input.get(ptr+1) != '}' && input.get(ptr+1) != ','  && input.get(ptr+1) != ']'){
				ptr = number(input, ptr+1);
				if (count < 8) {
					vals[count++] = tmp;
				}
				//if (input.get(ptr) == ',')
				//	ptr++;
			}
			ptr = ptr + 1; // skip '}'
		}
		else {
			ptr = number(input, ptr);
			vals[0] = tmp;
		}
		elements.add(new OctonionRepresentation(vals[0], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6], vals[7]));
		return ptr;
	}
	
	private int number(List<Character> input, int ptr) {
		StringBuilder buff = new StringBuilder();
		do {
			if (ptr == input.size()) break;
			char ch = input.get(ptr++);
			// parse between number delimiters
			// could append non-legal chars but let BigDecimal constructor handle it
			// hopefully this allows +/- Inf and NaN
			// (NOPE: BigD can't handle these but does support sci notation)
			if ((ch == '}') || (ch == ',') || (ch == ']')) {
				ptr--;
				break;
			}
			else
				buff.append(ch);
		} while (true);
		tmp = new BigDecimal(buff.toString());
		return ptr;
	}
}
