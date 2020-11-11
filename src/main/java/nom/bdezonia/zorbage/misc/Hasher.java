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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Hasher {

	public static final int PRIME = 23;
	
	public static int hashCode(String v) {
		return v.hashCode();
	}

	public static int hashCode(char v) {
		return Character.hashCode(v);
	}

	public static int hashCode(boolean v) {
		return Boolean.hashCode(v);
	}

	public static int hashCode(byte v) {
		return Byte.hashCode(v);
	}

	public static int hashCode(short v) {
		return Short.hashCode(v);
	}

	public static int hashCode(int v) {
		return Integer.hashCode(v);
	}

	public static int hashCode(long v) {
		return Long.hashCode(v);
	}

	public static int hashCode(float v) {
		return Float.hashCode(v);
	}

	public static int hashCode(double v) {
		return Double.hashCode(v);
	}

	public static int hashCode(BigInteger v) {
		return v.hashCode();
	}

	public static int hashCode(BigDecimal v) {
		return v.hashCode();
	}
}
