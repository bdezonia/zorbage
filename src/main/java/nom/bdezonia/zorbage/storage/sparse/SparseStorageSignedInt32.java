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
package nom.bdezonia.zorbage.storage.sparse;

import java.util.Arrays;
import java.util.Stack;

import nom.bdezonia.zorbage.algebra.Allocatable;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.coder.IntCoder;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SparseStorageSignedInt32<U extends IntCoder & Allocatable<U>>
	implements IndexedDataSource<U>, Allocatable<SparseStorageSignedInt32<U>>
{
	private final RedBlackTree<int[]> data;
	private final long numElements;
	private final int[] zero, tmp;
	private final U type;
	
	public SparseStorageSignedInt32(U type, long numElements) {
		this.numElements = numElements;
		this.type = type.allocate();
		this.zero = new int[type.intCount()];
		this.tmp = new int[type.intCount()];
		this.data = new RedBlackTree<int[]>();
	}
	
	@Override
	public SparseStorageSignedInt32<U> duplicate() {
		SparseStorageSignedInt32<U> list = new SparseStorageSignedInt32<U>(type, numElements);
		Stack<RedBlackTree<int[]>.Node> nodes = new Stack<RedBlackTree<int[]>.Node>();
		if (data.root != data.nil) {
			nodes.push(data.root);
			while (!nodes.isEmpty()) {
				RedBlackTree<int[]>.Node n = nodes.pop();
				type.fromIntArray(n.value, 0);
				list.set(n.key, type);
				if (n.left != data.nil) nodes.push(n.left);
				if (n.right != data.nil) nodes.push(n.right);
			}
		}
		return list;
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		value.toIntArray(tmp, 0);
		RedBlackTree<int[]>.Node node = data.findElement(index);
		if (Arrays.equals(tmp, zero)) {
			if (node != data.nil)
				data.delete(node);
		}
		else { // nonzero
			if (node == data.nil) {
				RedBlackTree<int[]>.Node n = data.new Node();
				n.key = index;
				n.p = data.nil;
				n.left = data.nil;
				n.right = data.nil;
				n.value = new int[tmp.length];
				// n.color =? What?
				for (int i = 0; i < tmp.length; i++)
					n.value[i] = tmp[i];
				data.insert(n);
			}
			else {
				value.toIntArray(node.value, 0);
			}
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		RedBlackTree<int[]>.Node node = data.findElement(index);
		if (node == data.nil) {
			value.fromIntArray(zero, 0);
		}
		else { // nonzero
			value.fromIntArray(node.value, 0);
		}
	}

	@Override
	public long size() {
		return numElements;
	}

	@Override
	public SparseStorageSignedInt32<U> allocate() {
		return new SparseStorageSignedInt32<U>(type, size());
	}

	@Override
	public StorageConstruction storageType() {
		return StorageConstruction.MEM_SPARSE;
	}

	@Override
	public boolean accessWithOneThread() {
		return true;
	}
}
