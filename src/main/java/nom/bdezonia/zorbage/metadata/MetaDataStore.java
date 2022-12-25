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
package nom.bdezonia.zorbage.metadata;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	public List<MetaDataType> keyTypes(String identifier) {

		List<MetaDataType> entries = new LinkedList<>();
		
		if (blobs.containsKey(identifier))
			entries.add(MetaDataType.BLOB);
		
		if (bytes.containsKey(identifier))
			entries.add(MetaDataType.BYTE);
		
		if (byteArrays.containsKey(identifier))
			entries.add(MetaDataType.BYTE_ARRAY);
		
		if (byteMatrices.containsKey(identifier))
			entries.add(MetaDataType.BYTE_MATRIX);
		
		if (shorts.containsKey(identifier))
			entries.add(MetaDataType.SHORT);
		
		if (shortArrays.containsKey(identifier))
			entries.add(MetaDataType.SHORT_ARRAY);
		
		if (shortMatrices.containsKey(identifier))
			entries.add(MetaDataType.SHORT_MATRIX);
		
		if (ints.containsKey(identifier))
			entries.add(MetaDataType.INT);
		
		if (intArrays.containsKey(identifier))
			entries.add(MetaDataType.INT_ARRAY);
		
		if (intMatrices.containsKey(identifier))
			entries.add(MetaDataType.INT_MATRIX);
		
		if (longs.containsKey(identifier))
			entries.add(MetaDataType.LONG);
		
		if (longArrays.containsKey(identifier))
			entries.add(MetaDataType.LONG_ARRAY);
		
		if (longMatrices.containsKey(identifier))
			entries.add(MetaDataType.LONG_MATRIX);
		
		if (floats.containsKey(identifier))
			entries.add(MetaDataType.FLOAT);
		
		if (floatArrays.containsKey(identifier))
			entries.add(MetaDataType.FLOAT_ARRAY);
		
		if (floatMatrices.containsKey(identifier))
			entries.add(MetaDataType.FLOAT_MATRIX);
		
		if (doubles.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE);
		
		if (doubleArrays.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE_ARRAY);
		
		if (doubleMatrices.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE_MATRIX);
		
		if (bigintegers.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER);
		
		if (bigintegerArrays.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER_ARRAY);
		
		if (bigintegerMatrices.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER_MATRIX);
		
		if (bigdecimals.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL);
		
		if (bigdecimalArrays.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL_ARRAY);
		
		if (bigdecimalMatrices.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL_MATRIX);
		
		if (strings.containsKey(identifier))
			entries.add(MetaDataType.STRING);
		
		if (stringArrays.containsKey(identifier))
			entries.add(MetaDataType.STRING_ARRAY);
		
		if (stringMatrices.containsKey(identifier))
			entries.add(MetaDataType.STRING_MATRIX);
		
		if (chars.containsKey(identifier))
			entries.add(MetaDataType.CHAR);
		
		if (charArrays.containsKey(identifier))
			entries.add(MetaDataType.CHAR_ARRAY);
		
		if (charMatrices.containsKey(identifier))
			entries.add(MetaDataType.CHAR_MATRIX);
		
		if (booleans.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN);
		
		if (booleanArrays.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN_ARRAY);
		
		if (booleanMatrices.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN_MATRIX);
		
		return entries;
	}

	/**
	 * Add to self all the contents of another MetaDataStore.
	 * Existing values in self are overwritten if the two
	 * store share a key with the same type. 
	 */
	public void merge(MetaDataStore other) {

		for (String key : other.blobs.keySet()) {
			blobs.put(key, other.blobs.get(key));
		}
		
		for (String key : other.bytes.keySet()) {
			bytes.put(key, other.bytes.get(key));
		}
		
		for (String key : other.byteArrays.keySet()) {
			byteArrays.put(key, other.byteArrays.get(key));
		}

		for (String key : other.byteMatrices.keySet()) {
			byteMatrices.put(key, other.byteMatrices.get(key));
		}
		
		for (String key : other.shorts.keySet()) {
			shorts.put(key, other.shorts.get(key));
		}
		
		for (String key : other.shortArrays.keySet()) {
			shortArrays.put(key, other.shortArrays.get(key));
		}

		for (String key : other.shortMatrices.keySet()) {
			shortMatrices.put(key, other.shortMatrices.get(key));
		}
		
		for (String key : other.ints.keySet()) {
			ints.put(key, other.ints.get(key));
		}
		
		for (String key : other.intArrays.keySet()) {
			intArrays.put(key, other.intArrays.get(key));
		}

		for (String key : other.intMatrices.keySet()) {
			intMatrices.put(key, other.intMatrices.get(key));
		}
		
		for (String key : other.longs.keySet()) {
			longs.put(key, other.longs.get(key));
		}
		
		for (String key : other.longArrays.keySet()) {
			longArrays.put(key, other.longArrays.get(key));
		}

		for (String key : other.longMatrices.keySet()) {
			longMatrices.put(key, other.longMatrices.get(key));
		}
		
		for (String key : other.floats.keySet()) {
			floats.put(key, other.floats.get(key));
		}
		
		for (String key : other.floatArrays.keySet()) {
			floatArrays.put(key, other.floatArrays.get(key));
		}

		for (String key : other.floatMatrices.keySet()) {
			floatMatrices.put(key, other.floatMatrices.get(key));
		}
		
		for (String key : other.doubles.keySet()) {
			doubles.put(key, other.doubles.get(key));
		}
		
		for (String key : other.doubleArrays.keySet()) {
			doubleArrays.put(key, other.doubleArrays.get(key));
		}

		for (String key : other.doubleMatrices.keySet()) {
			doubleMatrices.put(key, other.doubleMatrices.get(key));
		}
		
		for (String key : other.bigintegers.keySet()) {
			bigintegers.put(key, other.bigintegers.get(key));
		}
		
		for (String key : other.bigintegerArrays.keySet()) {
			bigintegerArrays.put(key, other.bigintegerArrays.get(key));
		}

		for (String key : other.bigintegerMatrices.keySet()) {
			bigintegerMatrices.put(key, other.bigintegerMatrices.get(key));
		}
		
		for (String key : other.bigdecimals.keySet()) {
			bigdecimals.put(key, other.bigdecimals.get(key));
		}
		
		for (String key : other.bigdecimalArrays.keySet()) {
			bigdecimalArrays.put(key, other.bigdecimalArrays.get(key));
		}

		for (String key : other.bigdecimalMatrices.keySet()) {
			bigdecimalMatrices.put(key, other.bigdecimalMatrices.get(key));
		}
		
		for (String key : other.strings.keySet()) {
			strings.put(key, other.strings.get(key));
		}
		
		for (String key : other.stringArrays.keySet()) {
			stringArrays.put(key, other.stringArrays.get(key));
		}

		for (String key : other.stringMatrices.keySet()) {
			stringMatrices.put(key, other.stringMatrices.get(key));
		}
		
		for (String key : other.chars.keySet()) {
			chars.put(key, other.chars.get(key));
		}
		
		for (String key : other.charArrays.keySet()) {
			charArrays.put(key, other.charArrays.get(key));
		}

		for (String key : other.charMatrices.keySet()) {
			charMatrices.put(key, other.charMatrices.get(key));
		}
		
		for (String key : other.booleans.keySet()) {
			booleans.put(key, other.booleans.get(key));
		}
		
		for (String key : other.booleanArrays.keySet()) {
			booleanArrays.put(key, other.booleanArrays.get(key));
		}

		for (String key : other.booleanMatrices.keySet()) {
			booleanMatrices.put(key, other.booleanMatrices.get(key));
		}
	}

	/**
	 * Get a summary of all stored fields and their various types. Or can
	 * lookup a key and find all the types of data stored under that key.
	 * One can then choose which type of data they want to retrieve using
	 * the key.  
	 */
	public Map<String, List<MetaDataType>> keysPresent() {
		
		Map<String, List<MetaDataType>> keysPres =
				new HashMap<String, List<MetaDataType>>();
		
		List<String> keys = keys();

		for (String key : keys) {
			keysPres.put(key, keyTypes(key));
		}
		
		return keysPres;
	}

	/**
	 * Return the list of all the keys in the MetaDataStore.
	 */
	public List<String> keys() {

		List<String> keys = new ArrayList<>();
		
		keys.addAll(blobs.keySet());
		keys.addAll(bytes.keySet());
		keys.addAll(byteArrays.keySet());
		keys.addAll(byteMatrices.keySet());
		keys.addAll(shorts.keySet());
		keys.addAll(shortArrays.keySet());
		keys.addAll(shortMatrices.keySet());
		keys.addAll(ints.keySet());
		keys.addAll(intArrays.keySet());
		keys.addAll(intMatrices.keySet());
		keys.addAll(longs.keySet());
		keys.addAll(longArrays.keySet());
		keys.addAll(longMatrices.keySet());
		keys.addAll(floats.keySet());
		keys.addAll(floatArrays.keySet());
		keys.addAll(floatMatrices.keySet());
		keys.addAll(doubles.keySet());
		keys.addAll(doubleArrays.keySet());
		keys.addAll(doubleMatrices.keySet());
		keys.addAll(bigintegers.keySet());
		keys.addAll(bigintegerArrays.keySet());
		keys.addAll(bigintegerMatrices.keySet());
		keys.addAll(bigdecimals.keySet());
		keys.addAll(bigdecimalArrays.keySet());
		keys.addAll(bigdecimalMatrices.keySet());
		keys.addAll(strings.keySet());
		keys.addAll(stringArrays.keySet());
		keys.addAll(stringMatrices.keySet());
		keys.addAll(chars.keySet());
		keys.addAll(charArrays.keySet());
		keys.addAll(charMatrices.keySet());
		keys.addAll(booleans.keySet());
		keys.addAll(booleanArrays.keySet());
		keys.addAll(booleanMatrices.keySet());

		// remove the duplicates and return result
		
		return new ArrayList<>(new HashSet<>(keys));
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
	 * Get a blob from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Object getBlob(String identifier) {
		
		return blobs.get(identifier);
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
	 * Get a byte from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Byte getByte(String identifier) {
		
		return bytes.get(identifier);
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
	 * Get a byte array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public byte[] getByteArray(String identifier) {
		
		return byteArrays.get(identifier);
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
	 * Get a byte matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public byte[][] getByteMatrix(String identifier) {
		
		return byteMatrices.get(identifier);
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
	 * Get a short from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Short getShort(String identifier) {
		
		return shorts.get(identifier);
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
	 * Get a short array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public short[] getShortArray(String identifier) {
		
		return shortArrays.get(identifier);
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
	 * Get a short matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public short[][] getShortMatrix(String identifier) {
		
		return shortMatrices.get(identifier);
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
	 * Get an int from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Integer getInt(String identifier) {
		
		return ints.get(identifier);
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
	 * Get an int array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public int[] getIntArray(String identifier) {
		
		return intArrays.get(identifier);
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
	 * Get an int matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public int[][] getIntMatrix(String identifier) {
		
		return intMatrices.get(identifier);
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
	 * Get a long from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Long getLong(String identifier) {
		
		return longs.get(identifier);
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
	 * Get a long array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public long[] getLongArray(String identifier) {
		
		return longArrays.get(identifier);
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
	 * Get a long matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public long[][] getLongMatrix(String identifier) {
		
		return longMatrices.get(identifier);
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
	 * Get a float from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Float getFloat(String identifier) {
		
		return floats.get(identifier);
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
	 * Get a float array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public float[] getFloatArray(String identifier) {
		
		return floatArrays.get(identifier);
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
	 * Get a float matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public float[][] getFloatMatrix(String identifier) {
		
		return floatMatrices.get(identifier);
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
	 * Get a double from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Double getDouble(String identifier) {
		
		return doubles.get(identifier);
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
	 * Get a double array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public double[] getDoubleArray(String identifier) {
		
		return doubleArrays.get(identifier);
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
	 * Get a double matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public double[][] getDoubleMatrix(String identifier) {
		
		return doubleMatrices.get(identifier);
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
	 * Get a BigInteger from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigInteger getBigInteger(String identifier) {
		
		return bigintegers.get(identifier);
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
	 * Get a BigInteger array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigInteger[] getBigIntegerArray(String identifier) {
		
		return bigintegerArrays.get(identifier);
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
	 * Get a BigInteger matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigInteger[][] getBigIntegerMatrix(String identifier) {
		
		return bigintegerMatrices.get(identifier);
	}
	
	/**
	 * Put a BigDecimal into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimal(String identifier, BigDecimal value) {
		
		bigdecimals.put(identifier, value);
	}

	/**
	 * Get a BigDecimal from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigDecimal getBigDecimal(String identifier) {
		
		return bigdecimals.get(identifier);
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
	 * Get a BigDecimalArray from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigDecimal[] getBigDecimalArray(String identifier) {
		
		return bigdecimalArrays.get(identifier);
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
	 * Get a BigDecimal matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigDecimal[][] getBigDecimalMatrix(String identifier) {
		
		return bigdecimalMatrices.get(identifier);
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
	 * Get a String from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public String getString(String identifier) {
		
		return strings.get(identifier);
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
	 * Get a String array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public String[] getStringArray(String identifier) {
		
		return stringArrays.get(identifier);
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
	 * Get a String matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public String[][] getStringMatrix(String identifier) {
		
		return stringMatrices.get(identifier);
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
	 * Get a char from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Character getChar(String identifier) {
		
		return chars.get(identifier);
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
	 * Get a char array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public char[] getCharArray(String identifier) {
		
		return charArrays.get(identifier);
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
	 * Get a char matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public char[][] getCharMatrix(String identifier) {
		
		return charMatrices.get(identifier);
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
	 * Get a boolean from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Boolean getBoolean(String identifier) {
		
		return booleans.get(identifier);
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
	 * Get a boolean array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public boolean[] getBooleanArray(String identifier) {
		
		return booleanArrays.get(identifier);
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

	/**
	 * Get a boolean matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public boolean[][] getBooleanMatrix(String identifier) {
		
		return booleanMatrices.get(identifier);
	}
}
