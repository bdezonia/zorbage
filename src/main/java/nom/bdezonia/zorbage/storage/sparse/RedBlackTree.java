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

/**
 * 
 * @author Barry DeZonia
 *
 */
class RedBlackTree<T> {

	// Code adapted from Cormen, Leiserson, Rivest, & Stein, 3rd Edition

	enum Color { RED, BLACK };
	
	class Node {
		Color color;
		Node p; // parent
		Node left;
		Node right;
		long key;
		T value;
	}

	Node root;
	final Node nil;

	private Node nilNode() {
		Node nil = new Node();
		nil.color = Color.BLACK;
		nil.p = nil;
		nil.left = nil;
		nil.right = nil;
		nil.key = Long.MIN_VALUE;
		nil.value = null;
		return nil;
	}
	
	RedBlackTree() {
		nil = nilNode();
		root = nil;
	}
	
	Node findElement(long i) {
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
	
	void leftRotate(Node x) {
		if (x == nil) return; // my code
		Node y = x.right;
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
		y.left = x;
		x.p = y;
	}

	void rightRotate(Node x) {
		if (x == nil) return; // my code
		Node y = x.left;
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
		y.right = x;
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
		}
		z.p = y;
		if (y == nil)
			root = z;
		else if (z.key < y.key)
			y.left = z;
		else
			y.right = z;
		z.left = nil;
		z.right = nil;
		z.color = Color.RED;
		insertFixup(z);
	}
	
	void insertFixup(Node z) {
		while (z.p.color == Color.RED) {
			if (z.p == z.p.p.left) {
				Node y = z.p.p.right;
				if (y.color == Color.RED) {
					z.p.color = Color.BLACK;
					y.color = Color.BLACK;
					z.p.p.color = Color.RED;
					z = z.p.p;
				}
				else {
					if (z == z.p.right) {
						z = z.p;
						leftRotate(z);
					}
					z.p.color = Color.BLACK;
					z.p.p.color = Color.RED;
					rightRotate(z.p.p);
				}
			}
			else {
				Node y = z.p.p.left;
				if (y.color == Color.RED) {
					z.p.color = Color.BLACK;
					y.color = Color.BLACK;
					z.p.p.color = Color.RED;
					z = z.p.p;
				}
				else {
					if (z == z.p.left) {
						z = z.p;
						rightRotate(z);
					}
					z.p.color = Color.BLACK;
					z.p.p.color = Color.RED;
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
		Color yOrigColor = y.color;
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
			yOrigColor = y.color;
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
			y.color = z.color;
		}
		if (yOrigColor == Color.BLACK)
			deleteFixup(x);
	}
	
	void deleteFixup(Node x) {
		while (x != root && x.color == Color.BLACK) {
			if (x == x.p.left) {
				Node w = x.p.right;
				if (w.color == Color.RED) {
					w.color = Color.BLACK;
					x.p.color = Color.RED;
					leftRotate(x.p);
					w = x.p.right;
				}
				if (w.left.color == Color.BLACK && w.right.color == Color.BLACK) {
					w.color = Color.RED;
					x = x.p;
				}
				else {
					if (w.right.color == Color.BLACK) {
						w.left.color = Color.BLACK;
						w.color = Color.RED;
						rightRotate(w);
						w = x.p.right;
					}
					w.color = x.p.color;
					x.p.color = Color.BLACK;
					w.right.color = Color.BLACK;
					leftRotate(x.p);
					x = root;
				}
			}
			else {
				Node w = x.p.left;
				if (w.color == Color.RED) {
					w.color = Color.BLACK;
					x.p.color = Color.RED;
					rightRotate(x.p);
					w = x.p.left;
				}
				if (w.right.color == Color.BLACK && w.left.color == Color.BLACK) {
					w.color = Color.RED;
					x = x.p;
				}
				else {
					if (w.left.color == Color.BLACK) {
						w.right.color = Color.BLACK;
						w.color = Color.RED;
						leftRotate(w);
						w = x.p.left;
					}
					w.color = x.p.color;
					x.p.color = Color.BLACK;
					w.left.color = Color.BLACK;
					rightRotate(x.p);
					x = root;
				}
			}
		}
		x.color = Color.BLACK;
	}
	
	Node treeMinimum(Node x) {
		if (x == nil)    // my code
			return nil;  // my code
		while (x.left != nil)
			x = x.left;
		return x;
	}
	
}
