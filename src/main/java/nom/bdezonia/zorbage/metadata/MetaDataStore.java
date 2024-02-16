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
import java.util.Set;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MetaDataStore {

	private final Map<String, Object> blobs;
	private final Map<String, Object[]> blobArrays;
	private final Map<String, Object[][]> blobMatrices;
	private final Map<String, Object[][][]> blobMatrix3ds;
	private final Map<String, List<Object>> blobLists;
	private final Map<String, Set<Object>> blobSets;
	private final Map<String, Byte> bytes;
	private final Map<String, byte[]> byteArrays;
	private final Map<String, byte[][]> byteMatrices;
	private final Map<String, byte[][][]> byteMatrix3ds;
	private final Map<String, List<Byte>> byteLists;
	private final Map<String, Set<Byte>> byteSets;
	private final Map<String, Short> shorts;
	private final Map<String, short[]> shortArrays;
	private final Map<String, short[][]> shortMatrices;
	private final Map<String, short[][][]> shortMatrix3ds;
	private final Map<String, List<Short>> shortLists;
	private final Map<String, Set<Short>> shortSets;
	private final Map<String, Integer> ints;
	private final Map<String, int[]> intArrays;
	private final Map<String, int[][]> intMatrices;
	private final Map<String, int[][][]> intMatrix3ds;
	private final Map<String, List<Integer>> intLists;
	private final Map<String, Set<Integer>> intSets;
	private final Map<String, Long> longs;
	private final Map<String, long[]> longArrays;
	private final Map<String, long[][]> longMatrices;
	private final Map<String, long[][][]> longMatrix3ds;
	private final Map<String, List<Long>> longLists;
	private final Map<String, Set<Long>> longSets;
	private final Map<String, Float> floats;
	private final Map<String, float[]> floatArrays;
	private final Map<String, float[][]> floatMatrices;
	private final Map<String, float[][][]> floatMatrix3ds;
	private final Map<String, List<Float>> floatLists;
	private final Map<String, Set<Float>> floatSets;
	private final Map<String, Double> doubles;
	private final Map<String, double[]> doubleArrays;
	private final Map<String, double[][]> doubleMatrices;
	private final Map<String, double[][][]> doubleMatrix3ds;
	private final Map<String, List<Double>> doubleLists;
	private final Map<String, Set<Double>> doubleSets;
	private final Map<String, BigInteger> bigintegers;
	private final Map<String, BigInteger[]> bigintegerArrays;
	private final Map<String, BigInteger[][]> bigintegerMatrices;
	private final Map<String, BigInteger[][][]> bigintegerMatrix3ds;
	private final Map<String, List<BigInteger>> bigintegerLists;
	private final Map<String, Set<BigInteger>> bigintegerSets;
	private final Map<String, BigDecimal> bigdecimals;
	private final Map<String, BigDecimal[]> bigdecimalArrays;
	private final Map<String, BigDecimal[][]> bigdecimalMatrices;
	private final Map<String, BigDecimal[][][]> bigdecimalMatrix3ds;
	private final Map<String, List<BigDecimal>> bigdecimalLists;
	private final Map<String, Set<BigDecimal>> bigdecimalSets;
	private final Map<String, String> strings;
	private final Map<String, String[]> stringArrays;
	private final Map<String, String[][]> stringMatrices;
	private final Map<String, String[][][]> stringMatrix3ds;
	private final Map<String, List<String>> stringLists;
	private final Map<String, Set<String>> stringSets;
	private final Map<String, Character> chars;
	private final Map<String, char[]> charArrays;
	private final Map<String, char[][]> charMatrices;
	private final Map<String, char[][][]> charMatrix3ds;
	private final Map<String, List<Character>> charLists;
	private final Map<String, Set<Character>> charSets;
	private final Map<String, Boolean> booleans;
	private final Map<String, boolean[]> booleanArrays;
	private final Map<String, boolean[][]> booleanMatrices;
	private final Map<String, boolean[][][]> booleanMatrix3ds;
	private final Map<String, List<Boolean>> booleanLists;
	private final Map<String, Set<Boolean>> booleanSets;
	
	/**
	 * Construct an empty metadata structure
	 */
	public MetaDataStore() {
		
		blobs = new HashMap<>();
		blobArrays = new HashMap<>();
		blobMatrices = new HashMap<>();
		blobMatrix3ds = new HashMap<>();
		blobLists = new HashMap<>();
		blobSets = new HashMap<>();
		bytes = new HashMap<>();
		byteArrays = new HashMap<>();
		byteMatrices = new HashMap<>();
		byteMatrix3ds = new HashMap<>();
		byteLists = new HashMap<>();
		byteSets = new HashMap<>();
		shorts = new HashMap<>();
		shortArrays = new HashMap<>();
		shortMatrices = new HashMap<>();
		shortMatrix3ds = new HashMap<>();
		shortLists = new HashMap<>();
		shortSets = new HashMap<>();
		ints = new HashMap<>();
		intArrays = new HashMap<>();
		intMatrices = new HashMap<>();
		intMatrix3ds = new HashMap<>();
		intLists = new HashMap<>();
		intSets = new HashMap<>();
		longs = new HashMap<>();
		longArrays = new HashMap<>();
		longMatrices = new HashMap<>();
		longMatrix3ds = new HashMap<>();
		longLists = new HashMap<>();
		longSets = new HashMap<>();
		floats = new HashMap<>();
		floatArrays = new HashMap<>();
		floatMatrices = new HashMap<>();
		floatMatrix3ds = new HashMap<>();
		floatLists = new HashMap<>();
		floatSets = new HashMap<>();
		doubles = new HashMap<>();
		doubleArrays = new HashMap<>();
		doubleMatrices = new HashMap<>();
		doubleMatrix3ds = new HashMap<>();
		doubleLists = new HashMap<>();
		doubleSets = new HashMap<>();
		bigintegers = new HashMap<>();
		bigintegerArrays = new HashMap<>();
		bigintegerMatrices = new HashMap<>();
		bigintegerMatrix3ds = new HashMap<>();
		bigintegerLists = new HashMap<>();
		bigintegerSets = new HashMap<>();
		bigdecimals = new HashMap<>();
		bigdecimalArrays = new HashMap<>();
		bigdecimalMatrices = new HashMap<>();
		bigdecimalMatrix3ds = new HashMap<>();
		bigdecimalLists = new HashMap<>();
		bigdecimalSets = new HashMap<>();
		strings = new HashMap<>();
		stringArrays = new HashMap<>();
		stringMatrices = new HashMap<>();
		stringMatrix3ds = new HashMap<>();
		stringLists = new HashMap<>();
		stringSets = new HashMap<>();
		chars = new HashMap<>();
		charArrays = new HashMap<>();
		charMatrices = new HashMap<>();
		charMatrix3ds = new HashMap<>();
		charLists = new HashMap<>();
		charSets = new HashMap<>();
		booleans = new HashMap<>();
		booleanArrays = new HashMap<>();
		booleanMatrices = new HashMap<>();
		booleanMatrix3ds = new HashMap<>();
		booleanLists = new HashMap<>();
		booleanSets = new HashMap<>();
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
		
		if (blobArrays.containsKey(identifier))
			entries.add(MetaDataType.BLOB_ARRAY);
		
		if (blobMatrices.containsKey(identifier))
			entries.add(MetaDataType.BLOB_MATRIX);
		
		if (blobMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.BLOB_MATRIX3D);
		
		if (blobLists.containsKey(identifier))
			entries.add(MetaDataType.BLOB_LIST);
		
		if (blobSets.containsKey(identifier))
			entries.add(MetaDataType.BLOB_SET);
		
		if (bytes.containsKey(identifier))
			entries.add(MetaDataType.BYTE);
		
		if (byteArrays.containsKey(identifier))
			entries.add(MetaDataType.BYTE_ARRAY);
		
		if (byteMatrices.containsKey(identifier))
			entries.add(MetaDataType.BYTE_MATRIX);
		
		if (byteMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.BYTE_MATRIX3D);
		
		if (byteLists.containsKey(identifier))
			entries.add(MetaDataType.BYTE_LIST);
		
		if (byteSets.containsKey(identifier))
			entries.add(MetaDataType.BYTE_SET);
		
		if (shorts.containsKey(identifier))
			entries.add(MetaDataType.SHORT);
		
		if (shortArrays.containsKey(identifier))
			entries.add(MetaDataType.SHORT_ARRAY);
		
		if (shortMatrices.containsKey(identifier))
			entries.add(MetaDataType.SHORT_MATRIX);
		
		if (shortMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.SHORT_MATRIX3D);
		
		if (shortLists.containsKey(identifier))
			entries.add(MetaDataType.SHORT_LIST);
		
		if (shortSets.containsKey(identifier))
			entries.add(MetaDataType.SHORT_SET);
		
		if (ints.containsKey(identifier))
			entries.add(MetaDataType.INT);
		
		if (intArrays.containsKey(identifier))
			entries.add(MetaDataType.INT_ARRAY);
		
		if (intMatrices.containsKey(identifier))
			entries.add(MetaDataType.INT_MATRIX);
		
		if (intMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.INT_MATRIX3D);
		
		if (intLists.containsKey(identifier))
			entries.add(MetaDataType.INT_LIST);
		
		if (intSets.containsKey(identifier))
			entries.add(MetaDataType.INT_SET);
		
		if (longs.containsKey(identifier))
			entries.add(MetaDataType.LONG);
		
		if (longArrays.containsKey(identifier))
			entries.add(MetaDataType.LONG_ARRAY);
		
		if (longMatrices.containsKey(identifier))
			entries.add(MetaDataType.LONG_MATRIX);
		
		if (longMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.LONG_MATRIX3D);
		
		if (longLists.containsKey(identifier))
			entries.add(MetaDataType.LONG_LIST);
		
		if (longSets.containsKey(identifier))
			entries.add(MetaDataType.LONG_SET);
		
		if (floats.containsKey(identifier))
			entries.add(MetaDataType.FLOAT);
		
		if (floatArrays.containsKey(identifier))
			entries.add(MetaDataType.FLOAT_ARRAY);
		
		if (floatMatrices.containsKey(identifier))
			entries.add(MetaDataType.FLOAT_MATRIX);
		
		if (floatMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.FLOAT_MATRIX3D);
		
		if (floatLists.containsKey(identifier))
			entries.add(MetaDataType.FLOAT_LIST);
		
		if (floatSets.containsKey(identifier))
			entries.add(MetaDataType.FLOAT_SET);
		
		if (doubles.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE);
		
		if (doubleArrays.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE_ARRAY);
		
		if (doubleMatrices.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE_MATRIX);
		
		if (doubleMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE_MATRIX3D);
		
		if (doubleLists.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE_LIST);
		
		if (doubleSets.containsKey(identifier))
			entries.add(MetaDataType.DOUBLE_SET);
		
		if (bigintegers.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER);
		
		if (bigintegerArrays.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER_ARRAY);
		
		if (bigintegerMatrices.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER_MATRIX);
		
		if (bigintegerMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER_MATRIX3D);
		
		if (bigintegerLists.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER_LIST);
		
		if (bigintegerSets.containsKey(identifier))
			entries.add(MetaDataType.BIGINTEGER_SET);
		
		if (bigdecimals.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL);
		
		if (bigdecimalArrays.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL_ARRAY);
		
		if (bigdecimalMatrices.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL_MATRIX);
		
		if (bigdecimalMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL_MATRIX3D);
		
		if (bigdecimalLists.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL_LIST);
		
		if (bigdecimalSets.containsKey(identifier))
			entries.add(MetaDataType.BIGDECIMAL_SET);
		
		if (strings.containsKey(identifier))
			entries.add(MetaDataType.STRING);
		
		if (stringArrays.containsKey(identifier))
			entries.add(MetaDataType.STRING_ARRAY);
		
		if (stringMatrices.containsKey(identifier))
			entries.add(MetaDataType.STRING_MATRIX);
		
		if (stringMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.STRING_MATRIX3D);
		
		if (stringLists.containsKey(identifier))
			entries.add(MetaDataType.STRING_LIST);
		
		if (stringSets.containsKey(identifier))
			entries.add(MetaDataType.STRING_SET);
		
		if (chars.containsKey(identifier))
			entries.add(MetaDataType.CHAR);
		
		if (charArrays.containsKey(identifier))
			entries.add(MetaDataType.CHAR_ARRAY);
		
		if (charMatrices.containsKey(identifier))
			entries.add(MetaDataType.CHAR_MATRIX);
		
		if (charMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.CHAR_MATRIX3D);
		
		if (charLists.containsKey(identifier))
			entries.add(MetaDataType.CHAR_LIST);
		
		if (charSets.containsKey(identifier))
			entries.add(MetaDataType.CHAR_SET);
		
		if (booleans.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN);
		
		if (booleanArrays.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN_ARRAY);
		
		if (booleanMatrices.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN_MATRIX);
		
		if (booleanMatrix3ds.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN_MATRIX3D);
		
		if (booleanLists.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN_LIST);
		
		if (booleanSets.containsKey(identifier))
			entries.add(MetaDataType.BOOLEAN_SET);
		
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
		
		for (String key : other.blobArrays.keySet()) {
			blobArrays.put(key, other.blobArrays.get(key));
		}
		
		for (String key : other.blobMatrices.keySet()) {
			blobMatrices.put(key, other.blobMatrices.get(key));
		}
		
		for (String key : other.blobMatrix3ds.keySet()) {
			blobMatrix3ds.put(key, other.blobMatrix3ds.get(key));
		}
		
		for (String key : other.blobLists.keySet()) {
			blobLists.put(key, other.blobLists.get(key));
		}
		
		for (String key : other.blobSets.keySet()) {
			blobSets.put(key, other.blobSets.get(key));
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
		
		for (String key : other.byteMatrix3ds.keySet()) {
			byteMatrix3ds.put(key, other.byteMatrix3ds.get(key));
		}
		
		for (String key : other.byteLists.keySet()) {
			byteLists.put(key, other.byteLists.get(key));
		}
		
		for (String key : other.byteSets.keySet()) {
			byteSets.put(key, other.byteSets.get(key));
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
		
		for (String key : other.shortMatrix3ds.keySet()) {
			shortMatrix3ds.put(key, other.shortMatrix3ds.get(key));
		}
		
		for (String key : other.shortLists.keySet()) {
			shortLists.put(key, other.shortLists.get(key));
		}
		
		for (String key : other.shortSets.keySet()) {
			shortSets.put(key, other.shortSets.get(key));
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
		
		for (String key : other.intMatrix3ds.keySet()) {
			intMatrix3ds.put(key, other.intMatrix3ds.get(key));
		}
		
		for (String key : other.intLists.keySet()) {
			intLists.put(key, other.intLists.get(key));
		}
		
		for (String key : other.intSets.keySet()) {
			intSets.put(key, other.intSets.get(key));
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
		
		for (String key : other.longMatrix3ds.keySet()) {
			longMatrix3ds.put(key, other.longMatrix3ds.get(key));
		}
		
		for (String key : other.longLists.keySet()) {
			longLists.put(key, other.longLists.get(key));
		}
		
		for (String key : other.longSets.keySet()) {
			longSets.put(key, other.longSets.get(key));
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
		
		for (String key : other.floatMatrix3ds.keySet()) {
			floatMatrix3ds.put(key, other.floatMatrix3ds.get(key));
		}
		
		for (String key : other.floatLists.keySet()) {
			floatLists.put(key, other.floatLists.get(key));
		}
		
		for (String key : other.floatSets.keySet()) {
			floatSets.put(key, other.floatSets.get(key));
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
		
		for (String key : other.doubleMatrix3ds.keySet()) {
			doubleMatrix3ds.put(key, other.doubleMatrix3ds.get(key));
		}
		
		for (String key : other.doubleLists.keySet()) {
			doubleLists.put(key, other.doubleLists.get(key));
		}
		
		for (String key : other.doubleSets.keySet()) {
			doubleSets.put(key, other.doubleSets.get(key));
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
		
		for (String key : other.bigintegerMatrix3ds.keySet()) {
			bigintegerMatrix3ds.put(key, other.bigintegerMatrix3ds.get(key));
		}
		
		for (String key : other.bigintegerLists.keySet()) {
			bigintegerLists.put(key, other.bigintegerLists.get(key));
		}
		
		for (String key : other.bigintegerSets.keySet()) {
			bigintegerSets.put(key, other.bigintegerSets.get(key));
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

		for (String key : other.bigdecimalMatrix3ds.keySet()) {
			bigdecimalMatrix3ds.put(key, other.bigdecimalMatrix3ds.get(key));
		}
		
		for (String key : other.bigdecimalLists.keySet()) {
			bigdecimalLists.put(key, other.bigdecimalLists.get(key));
		}
		
		for (String key : other.bigdecimalSets.keySet()) {
			bigdecimalSets.put(key, other.bigdecimalSets.get(key));
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

		for (String key : other.stringMatrix3ds.keySet()) {
			stringMatrix3ds.put(key, other.stringMatrix3ds.get(key));
		}
		
		for (String key : other.stringLists.keySet()) {
			stringLists.put(key, other.stringLists.get(key));
		}
		
		for (String key : other.stringSets.keySet()) {
			stringSets.put(key, other.stringSets.get(key));
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

		for (String key : other.charMatrix3ds.keySet()) {
			charMatrix3ds.put(key, other.charMatrix3ds.get(key));
		}
		
		for (String key : other.charLists.keySet()) {
			charLists.put(key, other.charLists.get(key));
		}
		
		for (String key : other.charSets.keySet()) {
			charSets.put(key, other.charSets.get(key));
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

		for (String key : other.booleanMatrix3ds.keySet()) {
			booleanMatrix3ds.put(key, other.booleanMatrix3ds.get(key));
		}
		
		for (String key : other.booleanLists.keySet()) {
			booleanLists.put(key, other.booleanLists.get(key));
		}
		
		for (String key : other.booleanSets.keySet()) {
			booleanSets.put(key, other.booleanSets.get(key));
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
		keys.addAll(blobArrays.keySet());
		keys.addAll(blobMatrices.keySet());
		keys.addAll(blobMatrix3ds.keySet());
		keys.addAll(blobLists.keySet());
		keys.addAll(blobSets.keySet());
		keys.addAll(bytes.keySet());
		keys.addAll(byteArrays.keySet());
		keys.addAll(byteMatrices.keySet());
		keys.addAll(byteMatrix3ds.keySet());
		keys.addAll(byteLists.keySet());
		keys.addAll(byteSets.keySet());
		keys.addAll(shorts.keySet());
		keys.addAll(shortArrays.keySet());
		keys.addAll(shortMatrices.keySet());
		keys.addAll(shortMatrix3ds.keySet());
		keys.addAll(shortLists.keySet());
		keys.addAll(shortSets.keySet());
		keys.addAll(ints.keySet());
		keys.addAll(intArrays.keySet());
		keys.addAll(intMatrices.keySet());
		keys.addAll(intMatrix3ds.keySet());
		keys.addAll(intLists.keySet());
		keys.addAll(intSets.keySet());
		keys.addAll(longs.keySet());
		keys.addAll(longArrays.keySet());
		keys.addAll(longMatrices.keySet());
		keys.addAll(longMatrix3ds.keySet());
		keys.addAll(longLists.keySet());
		keys.addAll(longSets.keySet());
		keys.addAll(floats.keySet());
		keys.addAll(floatArrays.keySet());
		keys.addAll(floatMatrices.keySet());
		keys.addAll(floatMatrix3ds.keySet());
		keys.addAll(floatLists.keySet());
		keys.addAll(floatSets.keySet());
		keys.addAll(doubles.keySet());
		keys.addAll(doubleArrays.keySet());
		keys.addAll(doubleMatrices.keySet());
		keys.addAll(doubleMatrix3ds.keySet());
		keys.addAll(doubleLists.keySet());
		keys.addAll(doubleSets.keySet());
		keys.addAll(bigintegers.keySet());
		keys.addAll(bigintegerArrays.keySet());
		keys.addAll(bigintegerMatrices.keySet());
		keys.addAll(bigintegerMatrix3ds.keySet());
		keys.addAll(bigintegerLists.keySet());
		keys.addAll(bigintegerSets.keySet());
		keys.addAll(bigdecimals.keySet());
		keys.addAll(bigdecimalArrays.keySet());
		keys.addAll(bigdecimalMatrices.keySet());
		keys.addAll(bigdecimalMatrix3ds.keySet());
		keys.addAll(bigdecimalLists.keySet());
		keys.addAll(bigdecimalSets.keySet());
		keys.addAll(strings.keySet());
		keys.addAll(stringArrays.keySet());
		keys.addAll(stringMatrices.keySet());
		keys.addAll(stringMatrix3ds.keySet());
		keys.addAll(stringLists.keySet());
		keys.addAll(stringSets.keySet());
		keys.addAll(chars.keySet());
		keys.addAll(charArrays.keySet());
		keys.addAll(charMatrices.keySet());
		keys.addAll(charMatrix3ds.keySet());
		keys.addAll(charLists.keySet());
		keys.addAll(charSets.keySet());
		keys.addAll(booleans.keySet());
		keys.addAll(booleanArrays.keySet());
		keys.addAll(booleanMatrices.keySet());
		keys.addAll(booleanMatrix3ds.keySet());
		keys.addAll(booleanLists.keySet());
		keys.addAll(booleanSets.keySet());

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
	 * Put a blob array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobArray(String identifier, Object[] value) {
		
		blobArrays.put(identifier, value);
	}

	/**
	 * Get a blob array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Object[] getBlobArray(String identifier) {
		
		return blobArrays.get(identifier);
	}
	
	/**
	 * Put a blob matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobMatrix(String identifier, Object[][] value) {
		
		blobMatrices.put(identifier, value);
	}

	/**
	 * Get a blob matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Object[][] getBlobMatrix(String identifier) {
		
		return blobMatrices.get(identifier);
	}
	
	/**
	 * Put a blob matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobMatrix3d(String identifier, Object[][][] value) {
		
		blobMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a blob matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Object[][][] getBlobMatrix3d(String identifier) {
		
		return blobMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a blob list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobList(String identifier, List<Object> value) {
		
		blobLists.put(identifier, value);
	}

	/**
	 * Get a blob list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Object> getBlobList(String identifier) {
		
		return blobLists.get(identifier);
	}
	
	/**
	 * Put a blob set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobSet(String identifier, Set<Object> value) {
		
		blobSets.put(identifier, value);
	}

	/**
	 * Get a blob set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Object> getBlobSet(String identifier) {
		
		return blobSets.get(identifier);
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
	 * Put a byte matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteMatrix3d(String identifier, byte[][][] value) {
		
		byteMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a byte matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public byte[][][] getByteMatrix3d(String identifier) {
		
		return byteMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a byte list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteList(String identifier, List<Byte> value) {
		
		byteLists.put(identifier, value);
	}

	/**
	 * Get a byte list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Byte> getByteList(String identifier) {
		
		return byteLists.get(identifier);
	}
	
	/**
	 * Put a byte set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteSet(String identifier, Set<Byte> value) {
		
		byteSets.put(identifier, value);
	}

	/**
	 * Get a byte set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Byte> getByteSet(String identifier) {
		
		return byteSets.get(identifier);
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
	 * Put a short matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortMatrix3d(String identifier, short[][][] value) {
		
		shortMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a short matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public short[][][] getShortMatrix3d(String identifier) {
		
		return shortMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a short list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortList(String identifier, List<Short> value) {
		
		shortLists.put(identifier, value);
	}

	/**
	 * Get a short list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Short> getShortList(String identifier) {
		
		return shortLists.get(identifier);
	}
	
	/**
	 * Put a short set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortSet(String identifier, Set<Short> value) {
		
		shortSets.put(identifier, value);
	}

	/**
	 * Get a short set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Short> getShortSet(String identifier) {
		
		return shortSets.get(identifier);
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
	 * Put an int matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntMatrix3d(String identifier, int[][][] value) {
		
		intMatrix3ds.put(identifier, value);
	}

	/**
	 * Get an int matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public int[][][] getIntMatrix3d(String identifier) {
		
		return intMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a int list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntList(String identifier, List<Integer> value) {
		
		intLists.put(identifier, value);
	}

	/**
	 * Get a int list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Integer> getIntList(String identifier) {
		
		return intLists.get(identifier);
	}
	
	/**
	 * Put a int set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntSet(String identifier, Set<Integer> value) {
		
		intSets.put(identifier, value);
	}

	/**
	 * Get a int set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Integer> getIntSet(String identifier) {
		
		return intSets.get(identifier);
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
	 * Put a long matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongMatrix3d(String identifier, long[][][] value) {
		
		longMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a long matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public long[][][] getLongMatrix3d(String identifier) {
		
		return longMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a long list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongList(String identifier, List<Long> value) {
		
		longLists.put(identifier, value);
	}

	/**
	 * Get a long list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Long> getLongList(String identifier) {
		
		return longLists.get(identifier);
	}
	
	/**
	 * Put a long set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongSet(String identifier, Set<Long> value) {
		
		longSets.put(identifier, value);
	}

	/**
	 * Get a long set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Long> getLongSet(String identifier) {
		
		return longSets.get(identifier);
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
	 * Put a float matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatMatrix3d(String identifier, float[][][] value) {
		
		floatMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a float matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public float[][][] getFloatMatrix3d(String identifier) {
		
		return floatMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a float list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatList(String identifier, List<Float> value) {
		
		floatLists.put(identifier, value);
	}

	/**
	 * Get a float list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Float> getFloatList(String identifier) {
		
		return floatLists.get(identifier);
	}
	
	/**
	 * Put a float set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatSet(String identifier, Set<Float> value) {
		
		floatSets.put(identifier, value);
	}

	/**
	 * Get a float set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Float> getFloatSet(String identifier) {
		
		return floatSets.get(identifier);
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
	 * Put a double matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleMatrix3d(String identifier, double[][][] value) {
		
		doubleMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a double matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public double[][][] getDoubleMatrix3d(String identifier) {
		
		return doubleMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a short list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleList(String identifier, List<Double> value) {
		
		doubleLists.put(identifier, value);
	}

	/**
	 * Get a double list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Double> getDoubleList(String identifier) {
		
		return doubleLists.get(identifier);
	}
	
	/**
	 * Put a double set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleSet(String identifier, Set<Double> value) {
		
		doubleSets.put(identifier, value);
	}

	/**
	 * Get a double set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Double> getDoubleSet(String identifier) {
		
		return doubleSets.get(identifier);
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
	 * Put a BigInteger matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerMatrix3d(String identifier, BigInteger[][][] value) {
		
		bigintegerMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a BigInteger matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigInteger[][][] getBigIntegerMatrix3d(String identifier) {
		
		return bigintegerMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a biginteger list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerList(String identifier, List<BigInteger> value) {
		
		bigintegerLists.put(identifier, value);
	}

	/**
	 * Get a biginteger list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<BigInteger> getBigIntegerList(String identifier) {
		
		return bigintegerLists.get(identifier);
	}
	
	/**
	 * Put a biginteger set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerSet(String identifier, Set<BigInteger> value) {
		
		bigintegerSets.put(identifier, value);
	}

	/**
	 * Get a biginteger set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<BigInteger> getBigIntegerSet(String identifier) {
		
		return bigintegerSets.get(identifier);
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
	 * Put a BigDecimal matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalMatrix3d(String identifier, BigDecimal[][][] value) {
		
		bigdecimalMatrix3ds.put(identifier, value);
	}
	
	/**
	 * Get a BigDecimal matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigDecimal[][][] getBigDecimalMatrix3d(String identifier) {
		
		return bigdecimalMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a bigdecimal list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalList(String identifier, List<BigDecimal> value) {
		
		bigdecimalLists.put(identifier, value);
	}

	/**
	 * Get a bigdecimal list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<BigDecimal> getBigDecimalList(String identifier) {
		
		return bigdecimalLists.get(identifier);
	}
	
	/**
	 * Put a bigdecimal set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalSet(String identifier, Set<BigDecimal> value) {
		
		bigdecimalSets.put(identifier, value);
	}

	/**
	 * Get a bigdecimal set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<BigDecimal> getBigDecimalSet(String identifier) {
		
		return bigdecimalSets.get(identifier);
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
	 * Put a String matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringMatrix3d(String identifier, String[][][] value) {
		
		stringMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a String matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public String[][][] getStringMatrix3d(String identifier) {
		
		return stringMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a string list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringList(String identifier, List<String> value) {
		
		stringLists.put(identifier, value);
	}

	/**
	 * Get a string list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<String> getStringList(String identifier) {
		
		return stringLists.get(identifier);
	}
	
	/**
	 * Put a string set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringSet(String identifier, Set<String> value) {
		
		stringSets.put(identifier, value);
	}

	/**
	 * Get a string set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<String> getStringSet(String identifier) {
		
		return stringSets.get(identifier);
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
	 * Put a char matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharMatrix3d(String identifier, char[][][] value) {
		
		charMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a char matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public char[][][] getCharMatrix3d(String identifier) {
		
		return charMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a char list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharList(String identifier, List<Character> value) {
		
		charLists.put(identifier, value);
	}

	/**
	 * Get a char list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Character> getCharList(String identifier) {
		
		return charLists.get(identifier);
	}
	
	/**
	 * Put a char set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharSet(String identifier, Set<Character> value) {
		
		charSets.put(identifier, value);
	}

	/**
	 * Get a char set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Character> getCharSet(String identifier) {
		
		return charSets.get(identifier);
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
	
	/**
	 * Put a boolean matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanMatrix3ds(String identifier, boolean[][][] value) {
		
		booleanMatrix3ds.put(identifier, value);
	}

	/**
	 * Get a boolean matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public boolean[][][] getBooleanMatrix3d(String identifier) {
		
		return booleanMatrix3ds.get(identifier);
	}
	
	/**
	 * Put a boolean list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanList(String identifier, List<Boolean> value) {
		
		booleanLists.put(identifier, value);
	}

	/**
	 * Get a boolean list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public List<Boolean> getBooleanList(String identifier) {
		
		return booleanLists.get(identifier);
	}
	
	/**
	 * Put a boolean set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanSet(String identifier, Set<Boolean> value) {
		
		booleanSets.put(identifier, value);
	}

	/**
	 * Get a boolean set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Set<Boolean> getBooleanSet(String identifier) {
		
		return booleanSets.get(identifier);
	}
}
