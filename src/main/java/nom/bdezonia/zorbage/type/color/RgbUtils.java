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
package nom.bdezonia.zorbage.type.color;

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

	/**
	 * 
	 * @param color
	 * @return
	 */
	public static int a(int color) {
		return (color & 0xff000000) >>> 24;
	}

	/**
	 * 
	 * @param color
	 * @return
	 */
	public static int r(int color) {
		return (color & 0x00ff0000) >> 16;
	}

	/**
	 * 
	 * @param color
	 * @return
	 */
	public static int g(int color) {
		return (color & 0x0000ff00) >> 8;
	}

	/**
	 * 
	 * @param color
	 * @return
	 */
	public static int b(int color) {
		return (color & 0x000000ff) >> 0;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public static int a_enc(int v) {
		if (v < 0) v = 0;
		if (v > 255) v = 255;
		return v << 24;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public static int r_enc(int v) {
		if (v < 0) v = 0;
		if (v > 255) v = 255;
		return v << 16;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public static int g_enc(int v) {
		if (v < 0) v = 0;
		if (v > 255) v = 255;
		return v << 8;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public static int b_enc(int v) {
		if (v < 0) v = 0;
		if (v > 255) v = 255;
		return v;
	}

	/**
	 * 
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static int rgb(int r, int g, int b) {
		return r_enc(r) | g_enc(g) | b_enc(b);
	}

	/**
	 * 
	 * @param a
	 * @param r
	 * @param g
	 * @param b
	 * @return
	 */
	public static int argb(int a, int r, int g, int b) {
		return a_enc(a) | r_enc(r) | g_enc(g) | b_enc(b);
	}
}
