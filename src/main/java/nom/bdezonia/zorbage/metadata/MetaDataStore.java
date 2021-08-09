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
package nom.bdezonia.zorbage.metadata;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// TODO - must do all the get() methods. Sigh. This file is tiring.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MetaDataStore {

	private final Map<String, Object> blobs;;
	private final Map<String, Byte> bytes;
	private final Map<String, byte[]> byteArrays;
	private final Map<String, byte[][]> byteMatrices;
	private final Map<String, Short> shorts;
	private final Map<String, short[]> shortArrays;
	private final Map<String, short[][]> shortMatrices;
	private final Map<String, Integer> ints;
	private final Map<String, int[]> intArrays;
	private final Map<String, int[][]> intMatrices;
	private final Map<String, Long> longs;
	private final Map<String, long[]> longArrays;
	private final Map<String, long[][]> longMatrices;
	private final Map<String, Float> floats;
	private final Map<String, float[]> floatArrays;
	private final Map<String, float[][]> floatMatrices;
	private final Map<String, Double> doubles;
	private final Map<String, double[]> doubleArrays;
	private final Map<String, double[][]> doubleMatrices;
	private final Map<String, BigInteger> bigintegers;
	private final Map<String, BigInteger[]> bigintegerArrays;
	private final Map<String, BigInteger[][]> bigintegerMatrices;
	private final Map<String, BigDecimal> bigdecimals;
	private final Map<String, BigDecimal[]> bigdecimalArrays;
	private final Map<String, BigDecimal[][]> bigdecimalMatrices;
	private final Map<String, String> strings;
	private final Map<String, String[]> stringArrays;
	private final Map<String, String[][]> stringMatrices;
	private final Map<String, Character> chars;
	private final Map<String, char[]> charArrays;
	private final Map<String, char[][]> charMatrices;
	private final Map<String, Boolean> booleans;
	private final Map<String, boolean[]> booleanArrays;
	private final Map<String, boolean[][]> booleanMatrices;
	
	/**
	 * Construct an empty metadata structure
	 */
	public MetaDataStore() {
		
		blobs = new HashMap<>();
		bytes = new HashMap<>();
		byteArrays = new HashMap<>();
		byteMatrices = new HashMap<>();
		shorts = new HashMap<>();
		shortArrays = new HashMap<>();
		shortMatrices = new HashMap<>();
		ints = new HashMap<>();
		intArrays = new HashMap<>();
		intMatrices = new HashMap<>();
		longs = new HashMap<>();
		longArrays = new HashMap<>();
		longMatrices = new HashMap<>();
		floats = new HashMap<>();
		floatArrays = new HashMap<>();
		floatMatrices = new HashMap<>();
		doubles = new HashMap<>();
		doubleArrays = new HashMap<>();
		doubleMatrices = new HashMap<>();
		bigintegers = new HashMap<>();
		bigintegerArrays = new HashMap<>();
		bigintegerMatrices = new HashMap<>();
		bigdecimals = new HashMap<>();
		bigdecimalArrays = new HashMap<>();
		bigdecimalMatrices = new HashMap<>();
		strings = new HashMap<>();
		stringArrays = new HashMap<>();
		stringMatrices = new HashMap<>();
		chars = new HashMap<>();
		charArrays = new HashMap<>();
		charMatrices = new HashMap<>();
		booleans = new HashMap<>();
		booleanArrays = new HashMap<>();
		booleanMatrices = new HashMap<>();
	}
	
	/**
	 * Get info about a value in the metadata store. Given the
	 * nature of the store someone could use the same identifier
	 * in different ways. The returned data types represent all
	 * the data types associated with this one identifier.
	 * 
	 * @param identifier
	 * @return
	 */
	public List<MetaDataType> howPresent(String identifier) {

		List<MetaDataType> entries = new LinkedList<>();
		
		if (blobs.containsKey(identifier))
			entries.add(MetaDataType.BLOB);
		
		if (bytes.containsKey(identifier))
			entries.add(MetaDataType.BYTE);
		
		if (byteArrays.containsKey(identifier))
			entries.add(MetaDataType.BYTE_ARRAY);
		
		if (byteMatrices.containsKey(identifier))
			entries.add(MetaDataType.BYTE_MATRIX);
		
		if (strings.containsKey(identifier))
			entries.add(MetaDataType.STRING);
		
		if (stringArrays.containsKey(identifier))
			entries.add(MetaDataType.STRING_ARRAY);
		
		if (stringMatrices.containsKey(identifier))
			entries.add(MetaDataType.STRING_MATRIX);
		
		return entries;
	}

	
	// TODO finish me!
	
	public void merge(MetaDataStore other) {

		System.out.println();
		System.out.println("THIS MetaDataStore CODE HAS NOT BEEN WRITTEN YET!");
		System.out.println();
	}
	
	// TODO finish me!
	
	public Map<String, List<MetaDataType>> keysPresent() {
		
		// get a summary of all stored fields and their various types
		
		return null;
	}
	
	/**
	 * Put a blob into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlob(String identifier, Object value) {
		
		blobs.put(identifier, value);
	}
	
	/**
	 * Put a byte into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByte(String identifier, byte value) {
		
		bytes.put(identifier, value);
	}
	
	/**
	 * Put a byte array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteArray(String identifier, byte[] value) {
		
		byteArrays.put(identifier, value);
	}
	
	/**
	 * Put a byte matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteMatrix(String identifier, byte[][] value) {
		
		byteMatrices.put(identifier, value);
	}
	
	/**
	 * Put a short into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShort(String identifier, short value) {
		
		shorts.put(identifier, value);
	}
	
	/**
	 * Put a short array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortArray(String identifier, short[] value) {
		
		shortArrays.put(identifier, value);
	}
	
	/**
	 * Put a short matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortMatrix(String identifier, short[][] value) {
		
		shortMatrices.put(identifier, value);
	}
	
	/**
	 * Put an int into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putInt(String identifier, int value) {
		
		ints.put(identifier, value);
	}
	
	/**
	 * Put an int array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntArray(String identifier, int[] value) {
		
		intArrays.put(identifier, value);
	}
	
	/**
	 * Put an int matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntMatrix(String identifier, int[][] value) {
		
		intMatrices.put(identifier, value);
	}
	
	/**
	 * Put a long into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLong(String identifier, long value) {
		
		longs.put(identifier, value);
	}
	
	/**
	 * Put a long array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongArray(String identifier, long[] value) {
		
		longArrays.put(identifier, value);
	}
	
	/**
	 * Put a long matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongMatrix(String identifier, long[][] value) {
		
		longMatrices.put(identifier, value);
	}
	
	/**
	 * Put a float into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloat(String identifier, float value) {
		
		floats.put(identifier, value);
	}
	
	/**
	 * Put a float array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatArray(String identifier, float[] value) {
		
		floatArrays.put(identifier, value);
	}
	
	/**
	 * Put a float matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatMatrix(String identifier, float[][] value) {
		
		floatMatrices.put(identifier, value);
	}
	
	/**
	 * Put a double into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDouble(String identifier, double value) {
		
		doubles.put(identifier, value);
	}
	
	/**
	 * Put a double array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleArray(String identifier, double[] value) {
		
		doubleArrays.put(identifier, value);
	}
	
	/**
	 * Put a double matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleMatrix(String identifier, double[][] value) {
		
		doubleMatrices.put(identifier, value);
	}
	
	/**
	 * Put a BigInteger into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigInteger(String identifier, BigInteger value) {
		
		bigintegers.put(identifier, value);
	}
	
	/**
	 * Put a BigInteger array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerArray(String identifier, BigInteger[] value) {
		
		bigintegerArrays.put(identifier, value);
	}
	
	/**
	 * Put a BigInteger matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerMatrix(String identifier, BigInteger[][] value) {
		
		bigintegerMatrices.put(identifier, value);
	}
	
	/**
	 * Put a BigInteger into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimal(String identifier, BigDecimal value) {
		
		bigdecimals.put(identifier, value);
	}
	
	/**
	 * Put a BigDecimal array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalArray(String identifier, BigDecimal[] value) {
		
		bigdecimalArrays.put(identifier, value);
	}
	
	/**
	 * Put a BigDecimal matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalMatrix(String identifier, BigDecimal[][] value) {
		
		bigdecimalMatrices.put(identifier, value);
	}
	
	/**
	 * Put a String into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putString(String identifier, String value) {
		
		strings.put(identifier, value);
	}
	
	/**
	 * Put a String array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringArray(String identifier, String[] value) {
		
		stringArrays.put(identifier, value);
	}
	
	/**
	 * Put a String matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringMatrix(String identifier, String[][] value) {
		
		stringMatrices.put(identifier, value);
	}
	
	/**
	 * Put a char into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putChar(String identifier, char value) {
		
		chars.put(identifier, value);
	}
	
	/**
	 * Put a char array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharArray(String identifier, char[] value) {
		
		charArrays.put(identifier, value);
	}
	
	/**
	 * Put a char matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharMatrix(String identifier, char[][] value) {
		
		charMatrices.put(identifier, value);
	}
	
	/**
	 * Put a boolean into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBoolean(String identifier, boolean value) {
		
		booleans.put(identifier, value);
	}
	
	/**
	 * Put a boolean array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanArray(String identifier, boolean[] value) {
		
		booleanArrays.put(identifier, value);
	}
	
	/**
	 * Put a boolean matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanMatrix(String identifier, boolean[][] value) {
		
		booleanMatrices.put(identifier, value);
	}
}
