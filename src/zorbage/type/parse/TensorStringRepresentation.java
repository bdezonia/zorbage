/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.type.parse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// TODO: capture regular dimensions during parsing

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TensorStringRepresentation {
	private int[] dimensions;
	private List<OctonionRepresentation> elements;
	private BigDecimal tmp;
	
	public TensorStringRepresentation(String s) {
		elements = new ArrayList<OctonionRepresentation>();
		dimensions = determineDimensions(s);
		gatherNumbers(s);
		// TODO: if numbers.length != numElems(dims) throw exception
	}
	
	public int[] dimensions() {
		return dimensions;
	}

	public OctonionRepresentation value(int[] index) {
		int idx = 0;
		int increment = 1;
		for (int i = 0; i < index.length; i++) {
			idx += index[i] * increment;
			increment *= dimensions[i];
		}
		return elements.get(idx);
	}
	
	public List<OctonionRepresentation> values() { return elements; }
	
	public OctonionRepresentation firstNumberValue() {
		return elements.get(0);
	}
	
	public int firstVectorDimension() {
		int dim = 1;
		if (dimensions.length >= 0) dim = dimensions[0];
		return dim;
	}

	public List<OctonionRepresentation> firstVectorValues() {
		int count = firstVectorDimension();
		List<OctonionRepresentation> vectorElements = new ArrayList<OctonionRepresentation>(count);
		for (int i = 0; i < count; i++)
			vectorElements.add(elements.get(i));
		return vectorElements;
	}

	public int[] firstMatrixDimensions() {
		int[] dims = new int[]{1,1};
		if (dimensions.length >= 0) dims[0] = dimensions[0];
		if (dimensions.length >= 1) dims[1] = dimensions[1];
		return dims;
	}
	
	public List<OctonionRepresentation> firstMatrixValues() {
		int[] dims = firstMatrixDimensions();
		int count = dims[0] * dims[1];
		List<OctonionRepresentation> matrixElements = new ArrayList<OctonionRepresentation>(count);
		for (int i = 0; i < count; i++)
			matrixElements.add(elements.get(i));
		return matrixElements;
	}
	
	private int[] determineDimensions(String s) {
		// TODO: fix me
		return new int[]{1};
	}
	
	private void gatherNumbers(String s) {
		List<Character> chars = new ArrayList<Character>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!Character.isWhitespace(c))
				chars.add(c);
		}
		tensor(chars);
	}

	/*

	[
	 [1,2],
	 [3,4]
	]
	
	4
	
	[
	  [
	    [
	      1, 2, 3
	    ]
	    [
	      4, 5, 6
	    ]
	  ]
	  [
	    [
	      2, 4, 6
	    ]
	    [
	      1, 3, 5
	    ]
	  ]
	]
	*/
	
	// tensor = number | numberGroups
	// numberGroups = [ numberGroup ] | nothing
	// numberGroup = numbers | numberGroups
	// numbers = number | number , numbers
    // number = num | ( 1-8 csv nums )
	// num = +|- digits . digits
	//
	// [[1,0,0][0,0,0]]	
	
	private void tensor(List<Character> input) {
		int ptr;
		if (input.get(0) == '[') {
			ptr = numberGroups(input, 0);
		}
		else {
			ptr = number(input,0);
		}
		if (ptr != input.size())
			throw new IllegalArgumentException("illegal input: end = "+ input.size() + " and ptr at " + ptr);
	}
	
	private int numberGroups(List<Character> input, int ptr) {
		if (input.get(ptr) == '[') {
			ptr = numberGroup(input, ptr);
			if (input.get(ptr) != ']')
				throw new IllegalArgumentException("1");
			return ptr+1;
		}
		else
			return ptr;
	}
	
	private int numberGroup(List<Character> input, int ptr) {
		if (input.get(ptr) == '[') {
			ptr = numbers(input, ptr+1);
			if (input.get(ptr) != ']')
				throw new IllegalArgumentException("number group not terminated correctly");
			else
				return ptr;
		}
		else
			return numberGroups(input, ptr);
	}
	
	private int numbers(List<Character> input, int ptr) {
		ptr = number(input, ptr);
		if (input.get(ptr) != ',')
			return ptr;
		else
			return numbers(input, ptr+1);
	}
	
	private int number(List<Character> input, int ptr) {
		BigDecimal[] vals = new BigDecimal[8];
		if (input.get(ptr) == '(') {
			int count = 0;
			while (input.get(ptr+1) != ')') {
				ptr = num(input, ptr+1);
				if (count < 8) {
					vals[count++] = tmp;
				}
				if (input.get(ptr) == ',')
					ptr++;
			}
			ptr = ptr + 2; // skip ')'
		}
		else {
			ptr = num(input, ptr);
			vals[0] = tmp;
		}
		elements.add(new OctonionRepresentation(vals[0], vals[1], vals[2], vals[3], vals[4], vals[5], vals[6], vals[7]));
		return ptr;
	}
	
	private int num(List<Character> input, int ptr) {
		StringBuilder buff = new StringBuilder();
		do {
			if (ptr == input.size()) break;
			char ch = input.get(ptr++);
			// parse between number delimiters
			// could append non-legal chars but let BigDecimal constructor handle it
			// hopefully this allows +/- Inf and NaN
			// (NOPE: BigD can't handle these but does support sci notation)
			if ((ch == ')') || (ch == ',') || (ch == ']')) {
				ptr--;
				break;
			}
			else
				buff.append(ch);
			}
		while (true);
		System.out.println("number being parsed = "+buff.toString());
		tmp = new BigDecimal(buff.toString());
		return ptr;
	}
}
