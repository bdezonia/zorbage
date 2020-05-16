/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2020 Barry DeZonia
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
package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * @author Barry DeZonia
 */
public class Nth {

	// do not instantiate

	private Nth() { }

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void first(IndexedDataSource<U> data, U value)
	{
		data.get(0, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void second(IndexedDataSource<U> data, U value)
	{
		data.get(1, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void third(IndexedDataSource<U> data, U value)
	{
		data.get(2, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fourth(IndexedDataSource<U> data, U value)
	{
		data.get(3, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fifth(IndexedDataSource<U> data, U value)
	{
		data.get(4, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void sixth(IndexedDataSource<U> data, U value)
	{
		data.get(5, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void seventh(IndexedDataSource<U> data, U value)
	{
		data.get(6, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eighth(IndexedDataSource<U> data, U value)
	{
		data.get(7, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void ninth(IndexedDataSource<U> data, U value)
	{
		data.get(8, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void tenth(IndexedDataSource<U> data, U value)
	{
		data.get(9, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eleventh(IndexedDataSource<U> data, U value)
	{
		data.get(10, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twelth(IndexedDataSource<U> data, U value)
	{
		data.get(11, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void thirteenth(IndexedDataSource<U> data, U value)
	{
		data.get(12, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fourteenth(IndexedDataSource<U> data, U value)
	{
		data.get(13, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void fifteenth(IndexedDataSource<U> data, U value)
	{
		data.get(14, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void sixteenth(IndexedDataSource<U> data, U value)
	{
		data.get(15, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void seventeenth(IndexedDataSource<U> data, U value)
	{
		data.get(16, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void eighteenth(IndexedDataSource<U> data, U value)
	{
		data.get(17, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void nineteenth(IndexedDataSource<U> data, U value)
	{
		data.get(18, value);
	}

	/**
	 *
	 * @param data
	 * @param value
	 * @param <U>
	 */
	public static <U>
	void twentieth(IndexedDataSource<U> data, U value)
	{
		data.get(19, value);
	}

        /**
         *
         * @param data
         * @param value
         * @param <U>
         */
        public static <U>
        void last(IndexedDataSource<U> data, U value)
        {
		long sz = data.size();
		if (sz == 0)
			throw new IllegalArgumentException("cannot get last element from empty list");
                data.get(sz-1, value);
        }
}
