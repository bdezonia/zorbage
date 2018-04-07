/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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

import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.coder.ShortCoder;
import nom.bdezonia.zorbage.type.storage.sparse.RedBlackTree.Node;

// NOTE: this class can't be thread friendly. One thread can insert a value
// while another thread is searching structure. Doing so would cause probs
// unless we make the methods synchronized which I don't want to do.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SparseStorageSignedInt16<U extends ShortCoder<U>>
	implements IndexedDataSource<SparseStorageSignedInt16<U>, U>
{
	private final RedBlackTree data;
	private final long numElements;
	private final short[] zero, tmp;
	private final U type;
	
	public SparseStorageSignedInt16(long numElements, U type) {
		this.numElements = numElements;
		this.type = type;
		this.zero = new short[type.shortCount()];
		this.tmp = new short[type.shortCount()];
		this.data = new RedBlackTree();
	}
	
	@Override
	public SparseStorageSignedInt16<U> duplicate() {
		SparseStorageSignedInt16<U> list = new SparseStorageSignedInt16<U>(numElements, type);
		Stack<Node> nodes = new Stack<Node>();
		if (data.root != data.nil) {
			nodes.push(data.root);
			while (!nodes.isEmpty()) {
				Node n = nodes.pop();
				type.toValue((short[])n.value, 0);
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
		value.toArray(tmp, 0);
		Node node = data.findElement(index);
		if (Arrays.equals(tmp, zero)) {
			if (node != data.nil)
				data.delete(node);
		}
		else { // nonzero
			if (node == data.nil) {
				Node n = data.new Node();
				n.key = index;
				n.p = data.nil;
				n.left = data.nil;
				n.right = data.nil;
				n.value = new short[tmp.length];
				// n.color =? What?
				for (int i = 0; i < tmp.length; i++)
					((short[])n.value)[i] = tmp[i];
				data.insert(n);
			}
			else {
				value.toArray((short[])node.value, 0);
			}
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		Node node = data.findElement(index);
		if (node == data.nil) {
			value.toValue(zero, 0);
		}
		else { // nonzero
			value.toValue((short[])node.value, 0);
		}
	}

	@Override
	public long size() {
		return numElements;
	}

}
