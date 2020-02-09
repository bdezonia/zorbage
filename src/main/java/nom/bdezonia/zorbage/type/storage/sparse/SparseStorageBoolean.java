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
package nom.bdezonia.zorbage.type.storage.sparse;

import java.util.Arrays;
import java.util.Stack;

import nom.bdezonia.zorbage.type.ctor.Allocatable;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SparseStorageBoolean<U extends BooleanCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<SparseStorageBoolean<U>>
{
	private final RedBlackTree<boolean[]> data;
	private final long numElements;
	private final boolean[] zero, tmp;
	private final U type;
	
	public SparseStorageBoolean(long numElements, U type) {
		this.numElements = numElements;
		this.type = type.allocate();
		this.zero = new boolean[type.booleanCount()];
		this.tmp = new boolean[type.booleanCount()];
		this.data = new RedBlackTree<boolean[]>();
	}
	
	@Override
	public SparseStorageBoolean<U> duplicate() {
		synchronized(this) {
			SparseStorageBoolean<U> list = new SparseStorageBoolean<U>(numElements, type);
			Stack<RedBlackTree<boolean[]>.Node> nodes = new Stack<RedBlackTree<boolean[]>.Node>();
			if (data.root != data.nil) {
				nodes.push(data.root);
				while (!nodes.isEmpty()) {
					RedBlackTree<boolean[]>.Node n = nodes.pop();
					type.fromBooleanArray(n.value, 0);
					list.set(n.key, type);
					if (n.left != data.nil) nodes.push(n.left);
					if (n.right != data.nil) nodes.push(n.right);
				}
			}
			return list;
		}
	}

	@Override
	public void set(long index, U value) {
		synchronized(this) {
			if (index < 0 || index >= numElements)
				throw new IllegalArgumentException("index out of bounds");
			value.toBooleanArray(tmp, 0);
			RedBlackTree<boolean[]>.Node node = data.findElement(index);
			if (Arrays.equals(tmp, zero)) {
				if (node != data.nil)
					data.delete(node);
			}
			else { // nonzero
				if (node == data.nil) {
					RedBlackTree<boolean[]>.Node n = data.new Node();
					n.key = index;
					n.p = data.nil;
					n.left = data.nil;
					n.right = data.nil;
					n.value = new boolean[tmp.length];
					// n.color =? What?
					for (int i = 0; i < tmp.length; i++)
						n.value[i] = tmp[i];
					data.insert(n);
				}
				else {
					value.toBooleanArray(node.value, 0);
				}
			}
		}
	}

	@Override
	public void get(long index, U value) {
		synchronized(this) {
			if (index < 0 || index >= numElements)
				throw new IllegalArgumentException("index out of bounds");
			RedBlackTree<boolean[]>.Node node = data.findElement(index);
			if (node == data.nil) {
				value.fromBooleanArray(zero, 0);
			}
			else { // nonzero
				value.fromBooleanArray(node.value, 0);
			}
		}
	}

	@Override
	public long size() {
		return numElements;
	}

	@Override
	public SparseStorageBoolean<U> allocate() {
		return new SparseStorageBoolean<U>(size(), type);
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_SPARSE;
	}
}
