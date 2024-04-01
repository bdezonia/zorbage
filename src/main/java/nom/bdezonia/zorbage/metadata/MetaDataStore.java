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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nom.bdezonia.zorbage.tuple.Tuple2;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class MetaDataStore {

	private final Map<Tuple2<String,String>, Object> data;
	
	/**
	 * Construct an empty metadata structure
	 */
	public MetaDataStore() {
		
		data = new HashMap<>();
	}
	
	/**
	 * Add all the contents of another MetaDataStore to self.
	 * Existing values in self are overwritten if the two
	 * stores share a key with the same type. 
	 */
	public void merge(MetaDataStore other) {

		for (Tuple2<String,String> key : other.data.keySet()) {
			data.put(key, other.data.get(key));
		}
	}

	/**
	 * Return the set of keys that are stored in this MetaDataStore.
	 */
	public Set<Tuple2<String,String>> keySet() {
		
		return data.keySet();
	}

	/**
	 * Put a blob into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlob(String identifier, Object value) {
		
		data.put(new Tuple2<>(identifier,"Object"), value);
	}

	/**
	 * Get a blob from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Object getBlob(String identifier) {
		
		return data.get(new Tuple2<>(identifier,"Object"));
	}
	
	/**
	 * Put a blob array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobArray(String identifier, Object[] value) {
		
		data.put(new Tuple2<>(identifier, "Object[]"), value);
	}

	/**
	 * Get a blob array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Object[] getBlobArray(String identifier) {
		
		return (Object[]) data.get(new Tuple2<>(identifier,"Object[]"));
	}
	
	/**
	 * Put a blob matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobMatrix(String identifier, Object[][] value) {
		
		data.put(new Tuple2<>(identifier,"Object[][]"), value);
	}

	/**
	 * Get a blob matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Object[][] getBlobMatrix(String identifier) {
		
		return (Object[][]) data.get(new Tuple2<>(identifier,"Object[][]"));
	}
	
	/**
	 * Put a blob matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobMatrix3d(String identifier, Object[][][] value) {
		
		data.put(new Tuple2<>(identifier,"Object[][][]"), value);
	}

	/**
	 * Get a blob matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Object[][][] getBlobMatrix3d(String identifier) {
		
		return (Object[][][]) data.get(new Tuple2<>(identifier,"Object[][][]"));
	}
	
	/**
	 * Put a blob list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobList(String identifier, List<Object> value) {
		
		data.put(new Tuple2<>(identifier,"List<Object>"), value);
	}

	/**
	 * Get a blob list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getBlobList(String identifier) {
		
		return (List<Object>) data.get(new Tuple2<>(identifier,"List<Object>"));
	}
	
	/**
	 * Put a blob set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBlobSet(String identifier, Set<Object> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Object>"), value);
	}

	/**
	 * Get a blob set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Object> getBlobSet(String identifier) {
		
		return (Set<Object>) data.get(new Tuple2<>(identifier,"Set<Object>"));
	}
	
	/**
	 * Put a byte into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByte(String identifier, Byte value) {
		
		data.put(new Tuple2<>(identifier,"Byte"), value);
	}

	/**
	 * Get a byte from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Byte getByte(String identifier) {
		
		return (Byte) data.get(new Tuple2<>(identifier, "Byte"));
	}
	
	/**
	 * Put a byte array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteArray(String identifier, byte[] value) {
		
		data.put(new Tuple2<>(identifier,"byte[]"), value);
	}
	
	/**
	 * Get a byte array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public byte[] getByteArray(String identifier) {
		
		return (byte[]) data.get(new Tuple2<>(identifier, "byte[]"));
	}

	/**
	 * Put a byte matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteMatrix(String identifier, byte[][] value) {
		
		data.put(new Tuple2<>(identifier,"byte[][]"), value);
	}

	/**
	 * Get a byte matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public byte[][] getByteMatrix(String identifier) {
		
		return (byte[][]) data.get(new Tuple2<>(identifier, "byte[][]"));
	}

	/**
	 * Put a byte matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteMatrix3d(String identifier, byte[][][] value) {
		
		data.put(new Tuple2<>(identifier,"byte[][][]"), value);
	}

	/**
	 * Get a byte matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public byte[][][] getByteMatrix3d(String identifier) {
		
		return (byte[][][]) data.get(new Tuple2<>(identifier, "byte[][][]"));
	}
	
	/**
	 * Put a byte list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteList(String identifier, List<Byte> value) {
		
		data.put(new Tuple2<>(identifier,"List<Byte>"), value);
	}

	/**
	 * Get a byte list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Byte> getByteList(String identifier) {
		
		return (List<Byte>) data.get(new Tuple2<>(identifier, "List<Byte>"));
	}
	
	/**
	 * Put a byte set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putByteSet(String identifier, Set<Byte> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Byte>"), value);
	}

	/**
	 * Get a byte set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Byte> getByteSet(String identifier) {
		
		return (Set<Byte>) data.get(new Tuple2<>(identifier, "Set<Byte>"));
	}
	
	/**
	 * Put a short into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShort(String identifier, Short value) {
		
		data.put(new Tuple2<>(identifier,"Short"), value);
	}

	/**
	 * Get a short from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Short getShort(String identifier) {
		
		return (Short) data.get(new Tuple2<>(identifier, "Short"));
	}
	
	/**
	 * Put a short array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortArray(String identifier, short[] value) {
		
		data.put(new Tuple2<>(identifier,"short[]"), value);
	}
	
	/**
	 * Get a short array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public short[] getShortArray(String identifier) {
		
		return (short[]) data.get(new Tuple2<>(identifier, "short[]"));
	}

	/**
	 * Put a short matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortMatrix(String identifier, short[][] value) {
		
		data.put(new Tuple2<>(identifier,"short[][]"), value);
	}

	/**
	 * Get a short matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public short[][] getShortMatrix(String identifier) {
		
		return (short[][]) data.get(new Tuple2<>(identifier, "short[][]"));
	}

	/**
	 * Put a short matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortMatrix3d(String identifier, short[][][] value) {
		
		data.put(new Tuple2<>(identifier,"short[][][]"), value);
	}

	/**
	 * Get a short matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public short[][][] getShortMatrix3d(String identifier) {
		
		return (short[][][]) data.get(new Tuple2<>(identifier, "short[][][]"));
	}
	
	/**
	 * Put a short list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortList(String identifier, List<Short> value) {
		
		data.put(new Tuple2<>(identifier,"List<Short>"), value);
	}

	/**
	 * Get a short list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Short> getShortList(String identifier) {
		
		return (List<Short>) data.get(new Tuple2<>(identifier, "List<Short>"));
	}
	
	/**
	 * Put a short set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putShortSet(String identifier, Set<Short> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Short>"), value);
	}

	/**
	 * Get a short set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Short> getShortSet(String identifier) {
		
		return (Set<Short>) data.get(new Tuple2<>(identifier, "Set<Short>"));
	}
	
	/**
	 * Put an int into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putInt(String identifier, Integer value) {
		
		data.put(new Tuple2<>(identifier,"Integer"), value);
	}

	/**
	 * Get an int from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Integer getInt(String identifier) {
		
		return (Integer) data.get(new Tuple2<>(identifier, "Integer"));
	}
	
	/**
	 * Put an int array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntArray(String identifier, int[] value) {
		
		data.put(new Tuple2<>(identifier,"int[]"), value);
	}

	/**
	 * Get an int array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public int[] getIntArray(String identifier) {
		
		return (int[]) data.get(new Tuple2<>(identifier, "int[]"));
	}
	
	/**
	 * Put an int matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntMatrix(String identifier, int[][] value) {
		
		data.put(new Tuple2<>(identifier,"int[][]"), value);
	}

	/**
	 * Get an int matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public int[][] getIntMatrix(String identifier) {
		
		return (int[][]) data.get(new Tuple2<>(identifier, "int[][]"));
	}
	
	/**
	 * Put an int matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntMatrix3d(String identifier, int[][][] value) {
		
		data.put(new Tuple2<>(identifier,"int[][][]"), value);
	}

	/**
	 * Get an int matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public int[][][] getIntMatrix3d(String identifier) {
		
		return (int[][][]) data.get(new Tuple2<>(identifier, "int[][][]"));
	}
	
	/**
	 * Put a int list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntList(String identifier, List<Integer> value) {
		
		data.put(new Tuple2<>(identifier,"List<Integer>"), value);
	}

	/**
	 * Get a int list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getIntList(String identifier) {
		
		return (List<Integer>) data.get(new Tuple2<>(identifier, "List<Integer>"));
	}
	
	/**
	 * Put a int set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putIntSet(String identifier, Set<Integer> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Integer>"), value);
	}

	/**
	 * Get a int set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Integer> getIntSet(String identifier) {
		
		return (Set<Integer>) data.get(new Tuple2<>(identifier, "Set<Integer>"));
	}
	
	/**
	 * Put a long into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLong(String identifier, Long value) {
		
		data.put(new Tuple2<>(identifier,"Long"), value);
	}

	/**
	 * Get a long from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Long getLong(String identifier) {
		
		return (Long) data.get(new Tuple2<>(identifier, "Long"));
	}
	
	/**
	 * Put a long array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongArray(String identifier, long[] value) {
		
		data.put(new Tuple2<>(identifier,"long[]"), value);
	}

	/**
	 * Get a long array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public long[] getLongArray(String identifier) {
		
		return (long[]) data.get(new Tuple2<>(identifier, "long[]"));
	}
	
	/**
	 * Put a long matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongMatrix(String identifier, long[][] value) {
		
		data.put(new Tuple2<>(identifier,"long[][]"), value);
	}

	/**
	 * Get a long matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public long[][] getLongMatrix(String identifier) {
		
		return (long[][]) data.get(new Tuple2<>(identifier, "long[][]"));
	}
	
	/**
	 * Put a long matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongMatrix3d(String identifier, long[][][] value) {
		
		data.put(new Tuple2<>(identifier,"long[][][]"), value);
	}

	/**
	 * Get a long matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public long[][][] getLongMatrix3d(String identifier) {
		
		return (long[][][]) data.get(new Tuple2<>(identifier, "long[][][]"));
	}
	
	/**
	 * Put a long list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongList(String identifier, List<Long> value) {
		
		data.put(new Tuple2<>(identifier,"List<Long>"), value);
	}

	/**
	 * Get a long list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Long> getLongList(String identifier) {
		
		return (List<Long>) data.get(new Tuple2<>(identifier, "List<Long>"));
	}
	
	/**
	 * Put a long set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putLongSet(String identifier, Set<Long> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Long>"), value);
	}

	/**
	 * Get a long set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Long> getLongSet(String identifier) {
		
		return (Set<Long>) data.get(new Tuple2<>(identifier, "Set<Long>"));
	}
	
	/**
	 * Put a float into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloat(String identifier, Float value) {
		
		data.put(new Tuple2<>(identifier,"Float"), value);
	}

	/**
	 * Get a float from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Float getFloat(String identifier) {
		
		return (Float) data.get(new Tuple2<>(identifier, "Float"));
	}
	
	/**
	 * Put a float array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatArray(String identifier, float[] value) {
		
		data.put(new Tuple2<>(identifier,"float[]"), value);
	}

	/**
	 * Get a float array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public float[] getFloatArray(String identifier) {
		
		return (float[]) data.get(new Tuple2<>(identifier, "float[]"));
	}
	
	/**
	 * Put a float matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatMatrix(String identifier, float[][] value) {
		
		data.put(new Tuple2<>(identifier,"float[][]"), value);
	}

	/**
	 * Get a float matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public float[][] getFloatMatrix(String identifier) {
		
		return (float[][]) data.get(new Tuple2<>(identifier, "float[][]"));
	}
	
	/**
	 * Put a float matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatMatrix3d(String identifier, float[][][] value) {
		
		data.put(new Tuple2<>(identifier,"float[][][]"), value);
	}

	/**
	 * Get a float matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public float[][][] getFloatMatrix3d(String identifier) {
		
		return (float[][][]) data.get(new Tuple2<>(identifier, "float[][][]"));
	}
	
	/**
	 * Put a float list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatList(String identifier, List<Float> value) {
		
		data.put(new Tuple2<>(identifier,"List<Float>"), value);
	}

	/**
	 * Get a float list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Float> getFloatList(String identifier) {
		
		return (List<Float>) data.get(new Tuple2<>(identifier, "List<Float>"));
	}
	
	/**
	 * Put a float set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putFloatSet(String identifier, Set<Float> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Float>"), value);
	}

	/**
	 * Get a float set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Float> getFloatSet(String identifier) {
		
		return (Set<Float>) data.get(new Tuple2<>(identifier, "Set<Float>"));
	}
	
	/**
	 * Put a double into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDouble(String identifier, Double value) {
		
		data.put(new Tuple2<>(identifier,"Double"), value);
	}

	/**
	 * Get a double from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Double getDouble(String identifier) {
		
		return (Double) data.get(new Tuple2<>(identifier, "Double"));
	}
	
	/**
	 * Put a double array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleArray(String identifier, double[] value) {
		
		data.put(new Tuple2<>(identifier,"double[]"), value);
	}

	/**
	 * Get a double array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public double[] getDoubleArray(String identifier) {
		
		return (double[]) data.get(new Tuple2<>(identifier, "double[]"));
	}
	
	/**
	 * Put a double matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleMatrix(String identifier, double[][] value) {
		
		data.put(new Tuple2<>(identifier,"double[][]"), value);
	}

	/**
	 * Get a double matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public double[][] getDoubleMatrix(String identifier) {
		
		return (double[][]) data.get(new Tuple2<>(identifier, "double[][]"));
	}
	
	/**
	 * Put a double matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleMatrix3d(String identifier, double[][][] value) {
		
		data.put(new Tuple2<>(identifier,"double[][][]"), value);
	}

	/**
	 * Get a double matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public double[][][] getDoubleMatrix3d(String identifier) {
		
		return (double[][][]) data.get(new Tuple2<>(identifier, "double[][][]"));
	}
	
	/**
	 * Put a short list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleList(String identifier, List<Double> value) {
		
		data.put(new Tuple2<>(identifier,"List<Double>"), value);
	}

	/**
	 * Get a double list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Double> getDoubleList(String identifier) {
		
		return (List<Double>) data.get(new Tuple2<>(identifier, "List<Double>"));
	}
	
	/**
	 * Put a double set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putDoubleSet(String identifier, Set<Double> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Double>"), value);
	}

	/**
	 * Get a double set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Double> getDoubleSet(String identifier) {
		
		return (Set<Double>) data.get(new Tuple2<>(identifier, "Set<Double>"));
	}
	
	/**
	 * Put a BigInteger into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigInteger(String identifier, BigInteger value) {
		
		data.put(new Tuple2<>(identifier,"BigInteger"), value);
	}

	/**
	 * Get a BigInteger from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigInteger getBigInteger(String identifier) {
		
		return (BigInteger) data.get(new Tuple2<>(identifier, "BigInteger"));
	}
	
	/**
	 * Put a BigInteger array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerArray(String identifier, BigInteger[] value) {
		
		data.put(new Tuple2<>(identifier,"BigInteger[]"), value);
	}

	/**
	 * Get a BigInteger array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigInteger[] getBigIntegerArray(String identifier) {
		
		return (BigInteger[]) data.get(new Tuple2<>(identifier, "BigInteger[]"));
	}

	/**
	 * Put a BigInteger matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerMatrix(String identifier, BigInteger[][] value) {
		
		data.put(new Tuple2<>(identifier,"BigInteger[][]"), value);
	}

	/**
	 * Get a BigInteger matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigInteger[][] getBigIntegerMatrix(String identifier) {
		
		return (BigInteger[][]) data.get(new Tuple2<>(identifier, "BigInteger[][]"));
	}

	/**
	 * Put a BigInteger matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerMatrix3d(String identifier, BigInteger[][][] value) {
		
		data.put(new Tuple2<>(identifier,"BigInteger[][][]"), value);
	}

	/**
	 * Get a BigInteger matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigInteger[][][] getBigIntegerMatrix3d(String identifier) {
		
		return (BigInteger[][][]) data.get(new Tuple2<>(identifier, "BigInteger[][][]"));
	}
	
	/**
	 * Put a biginteger list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerList(String identifier, List<BigInteger> value) {
		
		data.put(new Tuple2<>(identifier,"List<BigInteger>"), value);
	}

	/**
	 * Get a biginteger list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<BigInteger> getBigIntegerList(String identifier) {
		
		return (List<BigInteger>) data.get(new Tuple2<>(identifier, "List<BigInteger>"));
	}
	
	/**
	 * Put a biginteger set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigIntegerSet(String identifier, Set<BigInteger> value) {
		
		data.put(new Tuple2<>(identifier,"Set<BigInteger>"), value);
	}

	/**
	 * Get a biginteger set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<BigInteger> getBigIntegerSet(String identifier) {
		
		return (Set<BigInteger>) data.get(new Tuple2<>(identifier, "Set<BigInteger>"));
	}
	
	/**
	 * Put a BigDecimal into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimal(String identifier, BigDecimal value) {
		
		data.put(new Tuple2<>(identifier,"BigDecimal"), value);
	}

	/**
	 * Get a BigDecimal from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigDecimal getBigDecimal(String identifier) {
		
		return (BigDecimal) data.get(new Tuple2<>(identifier, "BigDecimal"));
	}
	
	/**
	 * Put a BigDecimal array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalArray(String identifier, BigDecimal[] value) {
		
		data.put(new Tuple2<>(identifier,"BigDecimal[]"), value);
	}

	/**
	 * Get a BigDecimalArray from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigDecimal[] getBigDecimalArray(String identifier) {
		
		return (BigDecimal[]) data.get(new Tuple2<>(identifier, "BigDecimal[]"));
	}
	
	/**
	 * Put a BigDecimal matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalMatrix(String identifier, BigDecimal[][] value) {
		
		data.put(new Tuple2<>(identifier,"BigDecimal[][]"), value);
	}
	
	/**
	 * Get a BigDecimal matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigDecimal[][] getBigDecimalMatrix(String identifier) {
		
		return (BigDecimal[][]) data.get(new Tuple2<>(identifier, "BigDecimal[][]"));
	}
	
	/**
	 * Put a BigDecimal matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalMatrix3d(String identifier, BigDecimal[][][] value) {
		
		data.put(new Tuple2<>(identifier,"BigDecimal[][][]"), value);
	}
	
	/**
	 * Get a BigDecimal matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public BigDecimal[][][] getBigDecimalMatrix3d(String identifier) {
		
		return (BigDecimal[][][]) data.get(new Tuple2<>(identifier, "BigDecimal[][][]"));
	}
	
	/**
	 * Put a bigdecimal list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalList(String identifier, List<BigDecimal> value) {
		
		data.put(new Tuple2<>(identifier,"List<BigDecimal>"), value);
	}

	/**
	 * Get a bigdecimal list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getBigDecimalList(String identifier) {
		
		return (List<BigDecimal>) data.get(new Tuple2<>(identifier, "List<BigDecimal>"));
	}
	
	/**
	 * Put a bigdecimal set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBigDecimalSet(String identifier, Set<BigDecimal> value) {
		
		data.put(new Tuple2<>(identifier,"Set<BigDecimal>"), value);
	}

	/**
	 * Get a bigdecimal set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<BigDecimal> getBigDecimalSet(String identifier) {
		
		return (Set<BigDecimal>) data.get(new Tuple2<>(identifier, "Set<BigDecimal>"));
	}
	
	/**
	 * Put a String into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putString(String identifier, String value) {
		
		data.put(new Tuple2<>(identifier,"String"), value);
	}

	/**
	 * Get a String from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public String getString(String identifier) {
		
		return (String) data.get(new Tuple2<>(identifier, "String"));
	}
	
	/**
	 * Put a String array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringArray(String identifier, String[] value) {
		
		data.put(new Tuple2<>(identifier,"String[]"), value);
	}

	/**
	 * Get a String array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public String[] getStringArray(String identifier) {
		
		return (String[]) data.get(new Tuple2<>(identifier, "String[]"));
	}
	
	/**
	 * Put a String matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringMatrix(String identifier, String[][] value) {
		
		data.put(new Tuple2<>(identifier,"String[][]"), value);
	}

	/**
	 * Get a String matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public String[][] getStringMatrix(String identifier) {
		
		return (String[][]) data.get(new Tuple2<>(identifier, "String[][]"));
	}
	
	/**
	 * Put a String matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringMatrix3d(String identifier, String[][][] value) {
		
		data.put(new Tuple2<>(identifier,"String[][][]"), value);
	}

	/**
	 * Get a String matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public String[][][] getStringMatrix3d(String identifier) {
		
		return (String[][][]) data.get(new Tuple2<>(identifier, "String[][][]"));
	}
	
	/**
	 * Put a string list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringList(String identifier, List<String> value) {
		
		data.put(new Tuple2<>(identifier,"List<String>"), value);
	}

	/**
	 * Get a string list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<String> getStringList(String identifier) {
		
		return (List<String>) data.get(new Tuple2<>(identifier, "List<String>"));
	}
	
	/**
	 * Put a string set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putStringSet(String identifier, Set<String> value) {
		
		data.put(new Tuple2<>(identifier,"Set<String>"), value);
	}

	/**
	 * Get a string set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getStringSet(String identifier) {
		
		return (Set<String>) data.get(new Tuple2<>(identifier, "Set<String>"));
	}
	
	/**
	 * Put a char into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putChar(String identifier, Character value) {
		
		data.put(new Tuple2<>(identifier,"Character"), value);
	}
	
	/**
	 * Get a char from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Character getChar(String identifier) {
		
		return (Character) data.get(new Tuple2<>(identifier, "Character"));
	}
	
	/**
	 * Put a char array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharArray(String identifier, char[] value) {
		
		data.put(new Tuple2<>(identifier,"char[]"), value);
	}

	/**
	 * Get a char array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public char[] getCharArray(String identifier) {
		
		return (char[]) data.get(new Tuple2<>(identifier, "char[]"));
	}
	
	/**
	 * Put a char matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharMatrix(String identifier, char[][] value) {
		
		data.put(new Tuple2<>(identifier,"char[][]"), value);
	}

	/**
	 * Get a char matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public char[][] getCharMatrix(String identifier) {
		
		return (char[][]) data.get(new Tuple2<>(identifier, "char[][]"));
	}
	
	/**
	 * Put a char matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharMatrix3d(String identifier, char[][][] value) {
		
		data.put(new Tuple2<>(identifier,"char[][][]"), value);
	}

	/**
	 * Get a char matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public char[][][] getCharMatrix3d(String identifier) {
		
		return (char[][][]) data.get(new Tuple2<>(identifier, "char[][][]"));
	}
	
	/**
	 * Put a char list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharList(String identifier, List<Character> value) {
		
		data.put(new Tuple2<>(identifier,"List<Character>"), value);
	}

	/**
	 * Get a char list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Character> getCharList(String identifier) {
		
		return (List<Character>) data.get(new Tuple2<>(identifier, "List<Character>"));
	}
	
	/**
	 * Put a char set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putCharSet(String identifier, Set<Character> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Character>"), value);
	}

	/**
	 * Get a char set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Character> getCharSet(String identifier) {
		
		return (Set<Character>) data.get(new Tuple2<>(identifier, "Set<Character>"));
	}
	
	/**
	 * Put a boolean into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBoolean(String identifier, Boolean value) {
		
		data.put(new Tuple2<>(identifier,"Boolean"), value);
	}
	
	/**
	 * Get a boolean from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public Boolean getBoolean(String identifier) {
		
		return (Boolean) data.get(new Tuple2<>(identifier, "Boolean"));
	}
	
	/**
	 * Put a boolean array into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanArray(String identifier, boolean[] value) {
		
		data.put(new Tuple2<>(identifier,"boolean[]"), value);
	}

	/**
	 * Get a boolean array from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public boolean[] getBooleanArray(String identifier) {
		
		return (boolean[]) data.get(new Tuple2<>(identifier, "boolean[]"));
	}
	
	/**
	 * Put a boolean matrix into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanMatrix(String identifier, boolean[][] value) {
		
		data.put(new Tuple2<>(identifier,"boolean[][]"), value);
	}

	/**
	 * Get a boolean matrix from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public boolean[][] getBooleanMatrix(String identifier) {
		
		return (boolean[][]) data.get(new Tuple2<>(identifier, "boolean[][]"));
	}
	
	/**
	 * Put a boolean matrix3d into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanMatrix3ds(String identifier, boolean[][][] value) {
		
		data.put(new Tuple2<>(identifier,"boolean[][][]"), value);
	}

	/**
	 * Get a boolean matrix3d from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	public boolean[][][] getBooleanMatrix3d(String identifier) {
		
		return (boolean[][][]) data.get(new Tuple2<>(identifier, "boolean[][][]"));
	}
	
	/**
	 * Put a boolean list into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanList(String identifier, List<Boolean> value) {
		
		data.put(new Tuple2<>(identifier,"List<Boolean>"), value);
	}

	/**
	 * Get a boolean list from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public List<Boolean> getBooleanList(String identifier) {
		
		return (List<Boolean>) data.get(new Tuple2<>(identifier, "List<Boolean>"));
	}
	
	/**
	 * Put a boolean set into the MetaDataStore.
	 * 
	 * @param identifier
	 * @param value
	 */
	public void putBooleanSet(String identifier, Set<Boolean> value) {
		
		data.put(new Tuple2<>(identifier,"Set<Boolean>"), value);
	}

	/**
	 * Get a boolean set from the MetaDataStore.
	 * 
	 * @param identifier
	 */
	@SuppressWarnings("unchecked")
	public Set<Boolean> getBooleanSet(String identifier) {

		return (Set<Boolean>) data.get(new Tuple2<>(identifier, "Set<Boolean>"));
	}
}
