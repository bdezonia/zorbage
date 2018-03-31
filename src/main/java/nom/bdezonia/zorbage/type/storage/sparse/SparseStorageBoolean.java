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

import nom.bdezonia.zorbage.type.storage.IndexedDataSource;
import nom.bdezonia.zorbage.type.storage.coder.BooleanCoder;

// NOTE: this class can't be thread friendly. One thread can insert a value
// while another thread is searching structure. Doing so would cause probs
// unless we make the methods synchronized which I don't want to do.

// NOTE: this structure is 1-d. Used by n-d structures. This means that
// higher indexed n-d access is slower than it needs to be. Think about
// addressing this in another way.

/**
 * 
 * @author Barry DeZonia
 *
 */
public class SparseStorageBoolean<U extends BooleanCoder<U>>
	implements IndexedDataSource<SparseStorageBoolean<U>, U>
{
	private final Node nil = new Node();
	private final RedBlackTree data;
	private final long numElements;
	private boolean[] zero, tmp;
	private final U type;
	
	public SparseStorageBoolean(long numElements, U type) {
		this.numElements = numElements;
		this.type = type;
		this.data = new RedBlackTree();
		this.zero = new boolean[type.booleanCount()];
		this.tmp = new boolean[type.booleanCount()];
	}
	
	@Override
	public SparseStorageBoolean<U> duplicate() {
		SparseStorageBoolean<U> list = new SparseStorageBoolean<U>(numElements, type);
		// TODO copy data into list
		return list;
	}

	@Override
	public void set(long index, U value) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		value.toArray(tmp, 0);
		Node node = data.findElement(index);
		if (Arrays.equals(tmp, zero)) {
			if (node != nil)
				data.delete(node);
		}
		else { // nonzero
			if (node == nil) {
				Node n = new Node();
				n.key = index;
				n.value = new boolean[tmp.length];
				n.p = nil;
				n.left = nil;
				n.right = nil;
				for (int i = 0; i < tmp.length; i++)
					n.value[i] = tmp[i];
				data.insert(n);
			}
			else {
				value.toArray(node.value, 0);
			}
		}
	}

	@Override
	public void get(long index, U value) {
		if (index < 0 || index >= numElements)
			throw new IllegalArgumentException("index out of bounds");
		Node node = data.findElement(index);
		if (node == nil) {
			value.toValue(zero, 0);
		}
		else { // nonzero
			value.toValue(node.value, 0);
		}
	}

	@Override
	public long size() {
		return numElements;
	}

	private class Node {
		private boolean isBlack;
		private Node p; // parent
		private Node left;
		private Node right;
		private long key;
		private boolean[] value;
	}

	private class RedBlackTree {
		private Node root;
		
		public RedBlackTree() {
			nil.isBlack = true;
			root = nil;
		}
		
		public Node findElement(long i) {
			Node n = root;
			while (true) {
				if (n == nil)
					return nil;
				else if (n.key == i)
					return n;
				else if (i < n.key)
					n = n.left;
				else // i > n.key
					n = n.right;
			}
		}
		
		public void leftRotate(Node x) {
			Node y = x;
			x.right = y.left;
			if (y.left != nil)
				y.left.p = x;
			y.p = x.p;
			if (x.p == nil)
				root = y;
			else if (x == x.p.left)
				x.p.left = y;
			else
				x.p.right = y;
			x.left = x;
			x.p = y;
		}

		public void rightRotate(Node x) {
			Node y = x;
			x.left = y.right;
			if (y.right != nil)
				y.right.p = x;
			y.p = x.p;
			if (x.p == nil)
				root = y;
			else if (x == x.p.right)
				x.p.right = y;
			else
				x.p.left = y;
			x.right = x;
			x.p = y;
		}
		
		void insert(Node z) {
			Node y = nil;
			Node x = root;
			while (x != nil) {
				y = x;
				if (z.key < x.key)
					x = x.left;
				else
					x = x.right;
				z.p = y;
				if (y == nil)
					root = z;
				else if (x.key < y.key)
					y.left = z;
				else
					y.right = z;
				z.left = nil;
				z.right = nil;
				z.isBlack = false;
				insertFixup(z);
			}
		}
		
		void insertFixup(Node z) {
			while (!z.p.isBlack) {
				if (z.p == z.p.p.left) {
					Node y = z.p.p.right;
					if (!y.isBlack) {
						z.p.isBlack = true;
						y.isBlack = true;
						z.p.p.isBlack = false;
						z = z.p.p;
					}
					else if (z == z.p.right) {
						z = z.p;
						leftRotate(z);
						z.p.isBlack = true;
						z.p.p.isBlack = false;
						rightRotate(z.p.p);
					}
				}
				else {
					Node y = z.p.p.left;
					if (!y.isBlack) {
						z.p.isBlack = true;
						y.isBlack = true;
						z.p.p.isBlack = false;
						z = z.p.p;
					}
					else if (z == z.p.left) {
						z = z.p;
						rightRotate(z);
						z.p.isBlack = true;
						z.p.p.isBlack = false;
						leftRotate(z.p.p);
					}
				}
			}
		}
		
		void transplant(Node u, Node v) {
			if (u.p == nil)
				root = v;
			else if (u == u.p.left)
				u.p.left = v;
			else
				u.p.right = v;
			v.p = u.p;
		}
		
		void delete(Node z) {
			Node y = z;
			Node x;
			boolean yOrigColor = y.isBlack;
			if (z.left == nil) {
				x = z.right;
				transplant(z,z.right);
			}
			else if (z.right == nil) {
				x = z.left;
				transplant(z,z.left);
			}
			else {
				y = treeMinimum(z.right);
				yOrigColor = y.isBlack;
				x = y.right;
				if (y.p == z)
					x.p = y;
				else {
					transplant(y, y.right);
					y.right = z.right;
					y.right.p = y;
				}
				transplant(z,y);
				y.left = z.left;
				y.left.p = y;
				y.isBlack = z.isBlack;
			}
			if (yOrigColor)
				deleteFixup(x);
		}
		
		void deleteFixup(Node x) {
			while (x != root && x.isBlack) {
				if (x == x.p.left) {
					Node w = x.p.right;
					if (!w.isBlack) {
						w.isBlack = true;
						x.p.isBlack = false;
						leftRotate(x.p);
						w = x.p.right;
					}
					if (w.left.isBlack && w.right.isBlack) {
						w.isBlack = false;
						x = x.p;
					}
					else if (w.right.isBlack) {
						w.left.isBlack = true;
						w.isBlack = false;
						rightRotate(w);
						w = x.p.right;
						// from here the cormen book had unindented code: bug?
						w.isBlack = x.p.isBlack;
						x.p.isBlack = true;
						w.right.isBlack = true;
						leftRotate(x.p);
						x = root;
					}
				}
				else {
					// copy reverse
					Node w = x.p.left;
					if (!w.isBlack) {
						w.isBlack = true;
						x.p.isBlack = false;
						rightRotate(x.p);
						w = x.p.left;
					}
					if (w.right.isBlack && w.left.isBlack) {
						w.isBlack = false;
						x = x.p;
					}
					else if (w.left.isBlack) {
						w.right.isBlack = true;
						w.isBlack = false;
						leftRotate(w);
						w = x.p.left;
						// from here the cormen book had unindented code: bug?
						w.isBlack = x.p.isBlack;
						x.p.isBlack = true;
						w.left.isBlack = true;
						rightRotate(x.p);
						x = root;
					}
				}
			}
			x.isBlack = true;
		}
		
		Node treeMinimum(Node x) {
			if (x == nil)
				return nil;
			while (x.left != nil)
				x = x.left;
			return x;
		}
	}
}
