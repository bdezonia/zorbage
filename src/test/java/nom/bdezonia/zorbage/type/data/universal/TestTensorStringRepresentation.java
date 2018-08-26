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
package nom.bdezonia.zorbage.type.data.universal;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestTensorStringRepresentation {

	@Test
	public void test0d() {
		TensorStringRepresentation a = new TensorStringRepresentation("1");
		TensorStringRepresentation b = new TensorStringRepresentation("{1}");
		TensorStringRepresentation c = new TensorStringRepresentation("{1,2}");
		TensorStringRepresentation d = new TensorStringRepresentation("{1,2,3}");
		TensorStringRepresentation e = new TensorStringRepresentation("{1,2,3,4}");
		TensorStringRepresentation f = new TensorStringRepresentation("{1,2,3,4,5}");
		TensorStringRepresentation g = new TensorStringRepresentation("{1,2,3,4,5,6}");
		TensorStringRepresentation h = new TensorStringRepresentation("{1,2,3,4,5,6,7}");
		TensorStringRepresentation i = new TensorStringRepresentation("{1,2,3,4,5,6,7,8}");
		TensorStringRepresentation j = new TensorStringRepresentation("{1,2,3,4,5,6,7,8,9}");
		assertTrue(true);
	}

	@Test
	public void test1dx1() {
		TensorStringRepresentation b = new TensorStringRepresentation("[{1}]");
		TensorStringRepresentation c = new TensorStringRepresentation("[{1,2}]");
		TensorStringRepresentation d = new TensorStringRepresentation("[{1,2,3}]");
		TensorStringRepresentation e = new TensorStringRepresentation("[{1,2,3,4}]");
		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5}]");
		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6}]");
		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7}]");
		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8}]");
		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9}]");
		assertTrue(true);
	}

	@Test
	public void test1dx2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[1,2]");
		TensorStringRepresentation b = new TensorStringRepresentation("[{1},{2}]");
		TensorStringRepresentation c = new TensorStringRepresentation("[{1,2},{3,4}]");
		TensorStringRepresentation d = new TensorStringRepresentation("[{1,2,3},{4,5,6}]");
		TensorStringRepresentation e = new TensorStringRepresentation("[{1,2,3,4},{5,6,7,8}]");
		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5},{6,7,8,9,10}]");
		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6},{7,8,9,10,11,12}]");
		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}]");
		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}]");
		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}]");
		assertTrue(true);
	}

	@Test
	public void test1dx3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[1,2,3]");
		TensorStringRepresentation b = new TensorStringRepresentation("[{1},{2},{3}]");
		TensorStringRepresentation c = new TensorStringRepresentation("[{1,2},{3,4},{5,6}]");
		TensorStringRepresentation d = new TensorStringRepresentation("[{1,2,3},{4,5,6},{7,8,9}]");
		TensorStringRepresentation e = new TensorStringRepresentation("[{1,2,3,4},{5,6,7,8},{9,10,11,12}]");
		TensorStringRepresentation f = new TensorStringRepresentation("[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}]");
		TensorStringRepresentation g = new TensorStringRepresentation("[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}]");
		TensorStringRepresentation h = new TensorStringRepresentation("[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}]");
		TensorStringRepresentation i = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}]");
		TensorStringRepresentation j = new TensorStringRepresentation("[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}]");
		assertTrue(true);
	}

	@Test
	public void test2d1x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}]]");
		assertTrue(true);
	}

	@Test
	public void test2d1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2}]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}]]");
		assertTrue(true);
	}

	@Test
	public void test2d1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2,3]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2},{3}]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4},{5,6}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6},{7,8,9}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8},{9,10,11,12}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}]]");
		assertTrue(true);
	}

	@Test
	public void test2d2x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1][2]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}][{2}]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}][{3,4}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}][{4,5,6}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}][{5,6,7,8}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}][{6,7,8,9,10}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}][{7,8,9,10,11,12}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}]]");
		assertTrue(true);
	}

	@Test
	public void test2d2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2][3,4]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2}][{3},{4}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4}][{5,6},{7,8}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6}][{7,8,9},{10,11,12}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8}][{9,10,11,12},{13,14,15,16}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10}][{11,12,13,14,15},{16,17,18,19,20}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12}][{13,14,15,16,17,18},{19,20,21,22,23,24}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21},{22,23,24,25,26,27,28}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24},{25,26,27,28,29,30,31,32}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27},{28,29,30,31,32,33,34,35,36}]]");
		assertTrue(true);
	}

	@Test
	public void test2d2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2,3][4,5,6]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{2},{3}][{4},{5},{6}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2},{3,4},{5,6}][{7,8},{9,10},{11,12}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3},{4,5,6},{7,8,9}][{10,11,12},{13,14,15},{16,17,18}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4},{5,6,7,8},{9,10,11,12}][{13,14,15,16},{17,18,19,20},{21,22,23,24}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15}][{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6},{7,8,9,10,11,12},{13,14,15,16,17,18}][{19,20,21,22,23,24},{25,26,27,28,29,30},{31,32,33,34,35,36}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7},{8,9,10,11,12,13,14},{15,16,17,18,19,20,21}][{22,23,24,25,26,27,28},{29,30,31,32,33,34,35},{36,37,38,39,40,41,42}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8},{9,10,11,12,13,14,15,16},{17,18,19,20,21,22,23,24}][{25,26,27,28,29,30,31,32},{33,34,35,36,37,38,39,40},{41,42,43,44,45,46,47,48}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9},{10,11,12,13,14,15,16,17,18},{19,20,21,22,23,24,25,26,27}][{28,29,30,31,32,33,34,35,36},{37,38,39,40,41,42,43,44,45},{46,47,48,49,50,51,52,53,54}]]");
		assertTrue(true);
	}

	@Test
	public void test2d3x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1][2][3]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1}][{2}][{3}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,2}][{3,4}][{5,6}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,2,3}][{4,5,6}][{7,8,9}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,2,3,4}][{5,6,7,8}][{9,10,11,12}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,2,3,4,5}][{6,7,8,9,10}][{11,12,13,14,15}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,2,3,4,5,6}][{7,8,9,10,11,12}][{13,14,15,16,17,18}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,2,3,4,5,6,7}][{8,9,10,11,12,13,14}][{15,16,17,18,19,20,21}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8}][{9,10,11,12,13,14,15,16}][{17,18,19,20,21,22,23,24}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,2,3,4,5,6,7,8,9}][{10,11,12,13,14,15,16,17,18}][{19,20,21,22,23,24,25,26,27}]]");
		assertTrue(true);
	}

	@Test
	public void test2d3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2][1,2][1,2]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{1}][{1},{1}][{1},{1}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]");
		assertTrue(true);
	}

	@Test
	public void test2d3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[1,2,3][1,2,3][1,2,3]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]]") ;
		TensorStringRepresentation c = new TensorStringRepresentation("[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x1x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{1,2}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{1,2,3}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{1,2,3,4}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{1,2,3,4,5}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{1,2,3,4,5,6}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{1,2,3,4,5,6,7}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,2,3]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,2},{1,2},{1,2}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,2,3},{1,2,3},{1,2,3}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,2,3,4},{1,2,3,4},{1,2,3,4}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,2,3,4,5},{1,2,3,4,5},{1,2,3,4,5}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,2,3,4,5,6},{1,2,3,4,5,6},{1,2,3,4,5,6}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7},{1,2,3,4,5,6,7},{1,2,3,4,5,6,7}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x2x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}][{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}][{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}][{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}][{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1][1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}][{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}][{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1][1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}][{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x3x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][1][1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{1}][{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}][{1,1}][{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}][{1,1,1}][{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1][1,1][1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}][{1},{1}][{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d1x3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1][1,1,1][1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x1x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1]][[1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}]][[{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}]][[{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}]][[{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}]][[{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}]][[{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}]][[{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8}]][[{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9}]][[{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1]][[1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}]][[{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}]][[{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}]][[{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}]][[{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1]][[1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}]][[{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}]][[{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8},{1,2,3,4,5,6,7,8}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}]][[{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9},{1,2,3,4,5,6,7,8,9}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x2x1() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][1]][[1][1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{1}]][[{1}][{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}][{1,1}]][[{1,1}][{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1][1,1]][[1,1][1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}][{1},{1}]][[{1},{1}][{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1][1,1,1]][[1,1,1][1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x3x1() {
		// [[[][][]][[][][]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][1][1]][[1][1][1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{1}][{1}]][[{1}][{1}][{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1][1,1][1,1]][[1,1][1,1][1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}][{1},{1}][{1},{1}]][[{1},{1}][{1},{1}][{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d2x3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1][1,1,1][1,1,1]][[1,1,1][1,1,1][1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}],{1,1,1,1,1,1}][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x1x1() {
		// [[[]][[]][[]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1]][[1]][[1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}]][[{1}]][[{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}]][[{1,1}]][[{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}]][[{1,1,1}]][[{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}]][[{1,1,1,1}]][[{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}]][[{1,1,1,1,1}]][[{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}]][[{1,1,1,1,1,1}]][[{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x1x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1]][[1,1]][[1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}]][[{1},{1}]][[{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}]][[{1,1},{1,1}]][[{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x1x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1]][[1,1,1]][[1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}]][[{1},{1},{1}]][[{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x1() {
		// [[[][]][[][]][[][]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][1]][[1][1]][[1][1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{1}]][[{1}][{1}]][[{1}][{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}][{1,1}]][[{1,1}][{1,1}]][[{1,1}][{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}][]{1,1,1}][[{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}][]{1,1,1,1,1,1}][[{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1][1,1]][[1,1][1,1]][[1,1][1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}][{1},{1}]][[{1},{1}][{1},{1}]][[{1},{1}][{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x2x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1][1,1,1]][[1,1,1][1,1,1]][[1,1,1][1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x1() {
		// [[[][][]][[][][]][[][][]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1][1][1]][[1][1][1]][[1][1][1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1}][{1}][{1}]][[{1}][{1}][{1}]][[{1}][{1}][{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x2() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1][1,1][1,1]][[1,1][1,1][1,1]][[1,1][1,1][1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1}][{1},{1}][{1},{1}]][[{1},{1}][{1},{1}][{1},{1}]][[{1},{1}][{1},{1}][{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]][[{1,1},{1,1}][{1,1},{1,1}][{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1}][{1,1,1},{1,1,1}][{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test3d3x3x3() {
		TensorStringRepresentation a = new TensorStringRepresentation("[[[1,1,1][1,1,1][1,1,1]][[1,1,1][1,1,1][1,1,1]][[1,1,1][1,1,1][1,1,1]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]][[{1},{1},{1}][{1},{1},{1}][{1},{1},{1}]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]][[{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}][{1,1},{1,1},{1,1}]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]][[{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}][{1,1,1},{1,1,1},{1,1,1}]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]][[{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}][{1,1,1,1},{1,1,1,1},{1,1,1,1}]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]][[{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}][{1,1,1,1,1},{1,1,1,1,1},{1,1,1,1,1}]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]][[{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}][{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1}]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1},{1,1,1,1,1,1,1},{1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1}]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1}]]]");
		assertTrue(true);
	}

	@Test
	public void test4d2x3x4x1() {
		// [[[[][][][]][[][][][]][[][][][]]][[[][][][]][[][][][]][[][][][]]]]
		TensorStringRepresentation a = new TensorStringRepresentation("[[[[1][1][1][1]][[1][1][1][1]][[1][1][1][1]]][[[1][1][1][1]][[1][1][1][1]][[1][1][1][1]]]]");
		TensorStringRepresentation b = new TensorStringRepresentation("[[[[{1}][{1}][{1}][{1}]][[{1}][{1}][{1}][{1}]][[{1}][{1}][{1}][{1}]]][[[{1}][{1}][{1}][{1}]][[{1}][{1}][{1}][{1}]][[{1}][{1}][{1}][{1}]]]]");
		TensorStringRepresentation c = new TensorStringRepresentation("[[[[{1,1}][{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}][{1,1}]]][[[{1,1}][{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}][{1,1}]][[{1,1}][{1,1}][{1,1}][{1,1}]]]]");
		TensorStringRepresentation d = new TensorStringRepresentation("[[[[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]]][[[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]][[{1,1,1}][{1,1,1}][{1,1,1}][{1,1,1}]]]]");
		TensorStringRepresentation e = new TensorStringRepresentation("[[[[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]]][[[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]][[{1,1,1,1}][{1,1,1,1}][{1,1,1,1}][{1,1,1,1}]]]]");
		TensorStringRepresentation f = new TensorStringRepresentation("[[[[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]]][[[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]][[{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}][{1,1,1,1,1}]]]]");
		TensorStringRepresentation g = new TensorStringRepresentation("[[[[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]]][[[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]][[{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}][{1,1,1,1,1,1}]]]]");
		TensorStringRepresentation h = new TensorStringRepresentation("[[[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]][[[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}][{1,1,1,1,1,1,1}]]]]");
		TensorStringRepresentation i = new TensorStringRepresentation("[[[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[]{1,1,1,1,1,1,1,1},[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]][[[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1}]]]]");
		TensorStringRepresentation j = new TensorStringRepresentation("[[[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]][[[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]][[{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}][{1,1,1,1,1,1,1,1,1}]]]]");
		assertTrue(true);
	}
}
