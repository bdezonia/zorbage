/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 * 
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.dataview;

import nom.bdezonia.zorbage.algebra.Dimensioned;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 * 
 * @param <U>
 */
public class TwentySixDView<U> implements Dimensioned {

	private final long d0;
	private final long d1;
	private final long d2;
	private final long d3;
	private final long d4;
	private final long d5;
	private final long d6;
	private final long d7;
	private final long d8;
	private final long d9;
	private final long d10;
	private final long d11;
	private final long d12;
	private final long d13;
	private final long d14;
	private final long d15;
	private final long d16;
	private final long d17;
	private final long d18;
	private final long d19;
	private final long d20;
	private final long d21;
	private final long d22;
	private final long d23;
	private final long d24;
	private final long d25;
	private final IndexedDataSource<U> list;

	public TwentySixDView(long d0, long d1, long d2, long d3, long d4, long d5, long d6, long d7, long d8, long d9, long d10, long d11, long d12, long d13, long d14, long d15, long d16, long d17, long d18, long d19, long d20, long d21, long d22, long d23, long d24, long d25, IndexedDataSource<U> data) {
		DViewUtils.checkDims(data.size(),d0,d1,d2,d3,d4,d5,d6,d7,d8,d9,d10,d11,d12,d13,d14,d15,d16,d17,d18,d19,d20,d21,d22,d23,d24,d25);
		this.d0 = d0;
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
		this.d4 = d4;
		this.d5 = d5;
		this.d6 = d6;
		this.d7 = d7;
		this.d8 = d8;
		this.d9 = d9;
		this.d10 = d10;
		this.d11 = d11;
		this.d12 = d12;
		this.d13 = d13;
		this.d14 = d14;
		this.d15 = d15;
		this.d16 = d16;
		this.d17 = d17;
		this.d18 = d18;
		this.d19 = d19;
		this.d20 = d20;
		this.d21 = d21;
		this.d22 = d22;
		this.d23 = d23;
		this.d24 = d24;
		this.d25 = d25;
		this.list = data;
	}

	public TwentySixDView(DimensionedDataSource<U> ds) {
		if (ds.numDimensions() != 26)
			throw new IllegalArgumentException("26-d view passed a data source that is "+ds.numDimensions()+"-d");
		this.d0 = ds.dimension(0);
		this.d1 = ds.dimension(1);
		this.d2 = ds.dimension(2);
		this.d3 = ds.dimension(3);
		this.d4 = ds.dimension(4);
		this.d5 = ds.dimension(5);
		this.d6 = ds.dimension(6);
		this.d7 = ds.dimension(7);
		this.d8 = ds.dimension(8);
		this.d9 = ds.dimension(9);
		this.d10 = ds.dimension(10);
		this.d11 = ds.dimension(11);
		this.d12 = ds.dimension(12);
		this.d13 = ds.dimension(13);
		this.d14 = ds.dimension(14);
		this.d15 = ds.dimension(15);
		this.d16 = ds.dimension(16);
		this.d17 = ds.dimension(17);
		this.d18 = ds.dimension(18);
		this.d19 = ds.dimension(19);
		this.d20 = ds.dimension(20);
		this.d21 = ds.dimension(21);
		this.d22 = ds.dimension(22);
		this.d23 = ds.dimension(23);
		this.d24 = ds.dimension(24);
		this.d25 = ds.dimension(25);
		this.list = ds.rawData();
	}

	public long d0() { return d0; }

	public long d1() { return d1; }

	public long d2() { return d2; }

	public long d3() { return d3; }

	public long d4() { return d4; }

	public long d5() { return d5; }

	public long d6() { return d6; }

	public long d7() { return d7; }

	public long d8() { return d8; }

	public long d9() { return d9; }

	public long d10() { return d10; }

	public long d11() { return d11; }

	public long d12() { return d12; }

	public long d13() { return d13; }

	public long d14() { return d14; }

	public long d15() { return d15; }

	public long d16() { return d16; }

	public long d17() { return d17; }

	public long d18() { return d18; }

	public long d19() { return d19; }

	public long d20() { return d20; }

	public long d21() { return d21; }

	public long d22() { return d22; }

	public long d23() { return d23; }

	public long d24() { return d24; }

	public long d25() { return d25; }

	public void get(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, long i12, long i13, long i14, long i15, long i16, long i17, long i18, long i19, long i20, long i21, long i22, long i23, long i24, long i25, U val) {
		long index = i25;
		index = index*d24 + i24;
		index = index*d23 + i23;
		index = index*d22 + i22;
		index = index*d21 + i21;
		index = index*d20 + i20;
		index = index*d19 + i19;
		index = index*d18 + i18;
		index = index*d17 + i17;
		index = index*d16 + i16;
		index = index*d15 + i15;
		index = index*d14 + i14;
		index = index*d13 + i13;
		index = index*d12 + i12;
		index = index*d11 + i11;
		index = index*d10 + i10;
		index = index*d9 + i9;
		index = index*d8 + i8;
		index = index*d7 + i7;
		index = index*d6 + i6;
		index = index*d5 + i5;
		index = index*d4 + i4;
		index = index*d3 + i3;
		index = index*d2 + i2;
		index = index*d1 + i1;
		index = index*d0 + i0;
		list.get(index, val);
	}

	public void set(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, long i12, long i13, long i14, long i15, long i16, long i17, long i18, long i19, long i20, long i21, long i22, long i23, long i24, long i25, U val) {
		long index = i25;
		index = index*d24 + i24;
		index = index*d23 + i23;
		index = index*d22 + i22;
		index = index*d21 + i21;
		index = index*d20 + i20;
		index = index*d19 + i19;
		index = index*d18 + i18;
		index = index*d17 + i17;
		index = index*d16 + i16;
		index = index*d15 + i15;
		index = index*d14 + i14;
		index = index*d13 + i13;
		index = index*d12 + i12;
		index = index*d11 + i11;
		index = index*d10 + i10;
		index = index*d9 + i9;
		index = index*d8 + i8;
		index = index*d7 + i7;
		index = index*d6 + i6;
		index = index*d5 + i5;
		index = index*d4 + i4;
		index = index*d3 + i3;
		index = index*d2 + i2;
		index = index*d1 + i1;
		index = index*d0 + i0;
		list.set(index, val);
	}

	public void safeGet(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, long i12, long i13, long i14, long i15, long i16, long i17, long i18, long i19, long i20, long i21, long i22, long i23, long i24, long i25, U val) {
		if (outOfBounds(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			get(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25, val);
	}

	public void safeSet(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, long i12, long i13, long i14, long i15, long i16, long i17, long i18, long i19, long i20, long i21, long i22, long i23, long i24, long i25, U val) {
		if (outOfBounds(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25)) {
			throw new IllegalArgumentException("view index out of bounds");
		}
		else
			set(i0, i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25, val);
	}

	private boolean outOfBounds(long i0, long i1, long i2, long i3, long i4, long i5, long i6, long i7, long i8, long i9, long i10, long i11, long i12, long i13, long i14, long i15, long i16, long i17, long i18, long i19, long i20, long i21, long i22, long i23, long i24, long i25) {
		if (i0 < 0 || i0 >= d0) return true;
		if (i1 < 0 || i1 >= d1) return true;
		if (i2 < 0 || i2 >= d2) return true;
		if (i3 < 0 || i3 >= d3) return true;
		if (i4 < 0 || i4 >= d4) return true;
		if (i5 < 0 || i5 >= d5) return true;
		if (i6 < 0 || i6 >= d6) return true;
		if (i7 < 0 || i7 >= d7) return true;
		if (i8 < 0 || i8 >= d8) return true;
		if (i9 < 0 || i9 >= d9) return true;
		if (i10 < 0 || i10 >= d10) return true;
		if (i11 < 0 || i11 >= d11) return true;
		if (i12 < 0 || i12 >= d12) return true;
		if (i13 < 0 || i13 >= d13) return true;
		if (i14 < 0 || i14 >= d14) return true;
		if (i15 < 0 || i15 >= d15) return true;
		if (i16 < 0 || i16 >= d16) return true;
		if (i17 < 0 || i17 >= d17) return true;
		if (i18 < 0 || i18 >= d18) return true;
		if (i19 < 0 || i19 >= d19) return true;
		if (i20 < 0 || i20 >= d20) return true;
		if (i21 < 0 || i21 >= d21) return true;
		if (i22 < 0 || i22 >= d22) return true;
		if (i23 < 0 || i23 >= d23) return true;
		if (i24 < 0 || i24 >= d24) return true;
		if (i25 < 0 || i25 >= d25) return true;
		return false;
	}

	@Override
	public int numDimensions() {
		return 26;
	}

	@Override
	public long dimension(int d) {
		if (d == 0) return d0;
		if (d == 1) return d1;
		if (d == 2) return d2;
		if (d == 3) return d3;
		if (d == 4) return d4;
		if (d == 5) return d5;
		if (d == 6) return d6;
		if (d == 7) return d7;
		if (d == 8) return d8;
		if (d == 9) return d9;
		if (d == 10) return d10;
		if (d == 11) return d11;
		if (d == 12) return d12;
		if (d == 13) return d13;
		if (d == 14) return d14;
		if (d == 15) return d15;
		if (d == 16) return d16;
		if (d == 17) return d17;
		if (d == 18) return d18;
		if (d == 19) return d19;
		if (d == 20) return d20;
		if (d == 21) return d21;
		if (d == 22) return d22;
		if (d == 23) return d23;
		if (d == 24) return d24;
		if (d == 25) return d25;
		throw new IllegalArgumentException("dimension out of bounds");
	}
}
