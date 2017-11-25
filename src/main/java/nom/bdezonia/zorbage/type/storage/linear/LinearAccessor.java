/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2017 Barry DeZonia
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
package zorbage.type.storage.linear;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <U>
 */
public class LinearAccessor<U> {
	
	private U value;
	private LinearStorage<?,U> storage;
	private long pos;

	public LinearAccessor(U value, LinearStorage<?,U> storage) {
		this.value = value;
		this.storage = storage;
		beforeFirst();
	}
	
	public void get() {
		storage.get(pos, value);
	}
	
	public void put() {
		storage.set(pos, value);
	}
	
	public boolean hasNext() {
		return (pos+1) >= 0 && (pos+1) < storage.size();
	}
	
	public boolean hasPrev() {
		return (pos-1) >= 0 && (pos-1) < storage.size();
	}
	
	public boolean hasNext(long steps) {
		return (pos+steps) >= 0 && (pos+steps) < storage.size();
	}
	
	public boolean hasPrev(long steps) {
		return (pos-steps) >= 0 && (pos-steps) < storage.size();
	}
	
	public void fwd() { pos++; }
	public void back() { pos--; }
	public void fwd(long steps) { pos += steps; }
	public void back(long steps) { pos -= steps; }
	
	public void afterLast() {
		pos = storage.size();
	}
	
	public void beforeFirst() {
		pos = -1;
	}
	
	public long pos() { return pos; }
}
