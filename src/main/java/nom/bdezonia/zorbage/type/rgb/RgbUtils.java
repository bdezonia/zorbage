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
package nom.bdezonia.zorbage.type.rgb;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class RgbUtils {

	/**
	 * 
	 * @param t
	 * @param c1
	 * @param c2
	 * @return
	 */
	public static int blendColor(double t, int c1, int c2) {

		if (t < 0 || t > 1)
			throw new IllegalArgumentException("blending factor must be between 0 and 1 inclusive");

		// blend gamma corrected values
		
		double value = 0;
		value += (1 - t) * Math.pow(c1, 2.2);
		value += (t) * Math.pow(c2, 2.2);
		value = Math.pow(value, (1.0/2.2));
		return (int) Math.round(value);
	}
	
	/**
	 * 
	 * @param t
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static int blendAlpha(double t, int a1, int a2) {

		if (t < 0 || t > 1)
			throw new IllegalArgumentException("blending factor must be between 0 and 1 inclusive");

		return (int) ((1-t)*a1 + t*a2);
	}
	
}
