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
package nom.bdezonia.zorbage.misc;

import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.algebra.Allocatable;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <T>
 */
public class BigList<T extends Allocatable<T>> {
	
	public static long MAX_ITEMS = 1L * Integer.MAX_VALUE * Integer.MAX_VALUE;

	private List<List<T>> lists;
	
	public BigList() {
		lists = new ArrayList<List<T>>();
	}
	
	public BigList(long size, T example) {
		if (size < 0)
			throw new IllegalArgumentException("negative index error");
		if (size > MAX_ITEMS)
			throw new IllegalArgumentException("BigList can only handle at most "+MAX_ITEMS+" items. "+size+" items were requested.");
		lists = new ArrayList<List<T>>();
		while (size > 0) {
			List<T> l;
			if ((size / Integer.MAX_VALUE) > 0) {
				l = new ArrayList<T>(Integer.MAX_VALUE);
				for (int i = 0; i < Integer.MAX_VALUE; i++) {
					l.add(example.allocate());
				}
			}
			else {
				int sz = (int) size;
				l = new ArrayList<T>(sz);
				for (int i = 0; i < sz; i++) {
					l.add(example.allocate());
				}
			}
			lists.add(l);
			size -= Integer.MAX_VALUE;
		}
	}
	
	public long size() {
		if (lists.size() == 0)
			return 0;
		long size = 1;
		size *= lists.size()-1;
		size *= Integer.MAX_VALUE;
		size += lists.get(lists.size()-1).size();
		return size;
	}
	
	public T get(long i) {
		if (i < 0)
			throw new IllegalArgumentException("negative index error");
		if (i >= MAX_ITEMS)
			throw new IllegalArgumentException("BigList can only handle "+MAX_ITEMS+" items");
		int list = (int)(i / Integer.MAX_VALUE);
		int offset = (int)(i % Integer.MAX_VALUE);
		List<T> l = lists.get(list);
		return l.get(offset);
	}

	public void set(long i, T v) {
		if (v == null)
			throw new IllegalArgumentException("biglists cannot accept null values");
		if (i < 0)
			throw new IllegalArgumentException("negative index error");
		if (i >= MAX_ITEMS)
			throw new IllegalArgumentException("BigList can only handle "+MAX_ITEMS+" items");
		int list = (int)(i / Integer.MAX_VALUE);
		int offset = (int)(i % Integer.MAX_VALUE);
		List<T> l = lists.get(list);
		l.set(offset, v);
	}
	
	public void add(T val) {
		if (v == null)
			throw new IllegalArgumentException("biglists cannot accept null values");
		if ((lists.size() == 0) ||
			(lists.get(lists.size()-1).size() == Integer.MAX_VALUE))
		{
			lists.add(new ArrayList<T>());
		}
		lists.get(lists.size()-1).add(val);
	}

}
