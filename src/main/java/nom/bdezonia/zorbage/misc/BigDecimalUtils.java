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
package nom.bdezonia.zorbage.misc;

import java.math.BigDecimal;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class BigDecimalUtils {

	// do not instantiate
	
	private BigDecimalUtils() { }
	
	public static final BigDecimal ONE_HALF = BigDecimal.ONE.divide(BigDecimal.valueOf(2));

	/**
	 * 
	 * @param v
	 * @return
	 */
	public static BigDecimal value(BigDecimal v) {
		if (v == null)
			return BigDecimal.ZERO;
		return v;
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param tol
	 * @return
	 */
	public static boolean isNear(double a, BigDecimal b, BigDecimal tol) {
		return isNear(BigDecimal.valueOf(a), b, tol);
	}

	/**
	 * 
	 * @param a
	 * @param b
	 * @param tol
	 * @return
	 */
	public static boolean isNear(BigDecimal a, BigDecimal b, BigDecimal tol) {
		return a.subtract(b).abs().compareTo(tol) <= 0;
	}

}
