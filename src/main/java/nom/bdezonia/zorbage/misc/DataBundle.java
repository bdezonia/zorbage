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

import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.character.FixedStringMember;
import nom.bdezonia.zorbage.type.float16.complex.ComplexFloat16Member;
import nom.bdezonia.zorbage.type.float16.octonion.OctonionFloat16Member;
import nom.bdezonia.zorbage.type.float16.quaternion.QuaternionFloat16Member;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.float32.octonion.OctonionFloat32Member;
import nom.bdezonia.zorbage.type.float32.quaternion.QuaternionFloat32Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.float64.octonion.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.float64.quaternion.QuaternionFloat64Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.complex.ComplexHighPrecisionMember;
import nom.bdezonia.zorbage.type.highprec.octonion.OctonionHighPrecisionMember;
import nom.bdezonia.zorbage.type.highprec.quaternion.QuaternionHighPrecisionMember;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int1.SignedInt1Member;
import nom.bdezonia.zorbage.type.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.int10.SignedInt10Member;
import nom.bdezonia.zorbage.type.int10.UnsignedInt10Member;
import nom.bdezonia.zorbage.type.int11.SignedInt11Member;
import nom.bdezonia.zorbage.type.int11.UnsignedInt11Member;
import nom.bdezonia.zorbage.type.int12.SignedInt12Member;
import nom.bdezonia.zorbage.type.int12.UnsignedInt12Member;
import nom.bdezonia.zorbage.type.int128.SignedInt128Member;
import nom.bdezonia.zorbage.type.int128.UnsignedInt128Member;
import nom.bdezonia.zorbage.type.int13.SignedInt13Member;
import nom.bdezonia.zorbage.type.int13.UnsignedInt13Member;
import nom.bdezonia.zorbage.type.int14.SignedInt14Member;
import nom.bdezonia.zorbage.type.int14.UnsignedInt14Member;
import nom.bdezonia.zorbage.type.int15.SignedInt15Member;
import nom.bdezonia.zorbage.type.int15.UnsignedInt15Member;
import nom.bdezonia.zorbage.type.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.int2.SignedInt2Member;
import nom.bdezonia.zorbage.type.int2.UnsignedInt2Member;
import nom.bdezonia.zorbage.type.int3.SignedInt3Member;
import nom.bdezonia.zorbage.type.int3.UnsignedInt3Member;
import nom.bdezonia.zorbage.type.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.int4.SignedInt4Member;
import nom.bdezonia.zorbage.type.int4.UnsignedInt4Member;
import nom.bdezonia.zorbage.type.int5.SignedInt5Member;
import nom.bdezonia.zorbage.type.int5.UnsignedInt5Member;
import nom.bdezonia.zorbage.type.int6.SignedInt6Member;
import nom.bdezonia.zorbage.type.int6.UnsignedInt6Member;
import nom.bdezonia.zorbage.type.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.int64.UnsignedInt64Member;
import nom.bdezonia.zorbage.type.int7.SignedInt7Member;
import nom.bdezonia.zorbage.type.int7.UnsignedInt7Member;
import nom.bdezonia.zorbage.type.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.int9.SignedInt9Member;
import nom.bdezonia.zorbage.type.int9.UnsignedInt9Member;
import nom.bdezonia.zorbage.type.point.Point;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.rgb.ArgbMember;
import nom.bdezonia.zorbage.type.rgb.RgbMember;
import nom.bdezonia.zorbage.type.unbounded.UnboundedIntMember;

/**
 * @author Barry DeZonia
 */
public class DataBundle {
	public List<DimensionedDataSource<UnsignedInt1Member>> uint1s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt2Member>> uint2s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt3Member>> uint3s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt4Member>> uint4s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt5Member>> uint5s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt6Member>> uint6s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt7Member>> uint7s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt8Member>> uint8s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt9Member>> uint9s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt10Member>> uint10s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt11Member>> uint11s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt12Member>> uint12s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt13Member>> uint13s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt14Member>> uint14s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt15Member>> uint15s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt16Member>> uint16s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt32Member>> uint32s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt64Member>> uint64s = new ArrayList<>();
	public List<DimensionedDataSource<UnsignedInt128Member>> uint128s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt1Member>> int1s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt2Member>> int2s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt3Member>> int3s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt4Member>> int4s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt5Member>> int5s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt6Member>> int6s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt7Member>> int7s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt8Member>> int8s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt9Member>> int9s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt10Member>> int10s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt11Member>> int11s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt12Member>> int12s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt13Member>> int13s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt14Member>> int14s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt15Member>> int15s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt16Member>> int16s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt32Member>> int32s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt64Member>> int64s = new ArrayList<>();
	public List<DimensionedDataSource<SignedInt128Member>> int128s = new ArrayList<>();
	public List<DimensionedDataSource<Float16Member>> flt16s = new ArrayList<>();
	public List<DimensionedDataSource<Float32Member>> flt32s = new ArrayList<>();
	public List<DimensionedDataSource<Float64Member>> flt64s = new ArrayList<>();
	public List<DimensionedDataSource<HighPrecisionMember>> hps = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat16Member>> cflt16s = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat32Member>> cflt32s = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat64Member>> cflt64s = new ArrayList<>();
	public List<DimensionedDataSource<ComplexHighPrecisionMember>> chps = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat16Member>> qflt16s = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat32Member>> qflt32s = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat64Member>> qflt64s = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionHighPrecisionMember>> qhps = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat16Member>> oflt16s = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat32Member>> oflt32s = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat64Member>> oflt64s = new ArrayList<>();
	public List<DimensionedDataSource<OctonionHighPrecisionMember>> ohps = new ArrayList<>();
	public List<DimensionedDataSource<RationalMember>> rats = new ArrayList<>();
	public List<DimensionedDataSource<UnboundedIntMember>> bigs = new ArrayList<>();
	public List<DimensionedDataSource<ArgbMember>> argbs = new ArrayList<>();
	public List<DimensionedDataSource<RgbMember>> rgbs = new ArrayList<>();
	public List<DimensionedDataSource<FixedStringMember>> fstrs = new ArrayList<>();
	public List<DimensionedDataSource<Point>> points = new ArrayList<>();

	// TODO
	//   add vectors/matrices/tensors of real/comp/quat/oct elements
	
	public void mergeUInt1(DimensionedDataSource<UnsignedInt1Member> ds) {
		if (ds != null)
			uint1s.add(ds);
	}
	
	public void mergeUInt2(DimensionedDataSource<UnsignedInt2Member> ds) {
		if (ds != null)
			uint2s.add(ds);
	}
	
	public void mergeUInt3(DimensionedDataSource<UnsignedInt3Member> ds) {
		if (ds != null)
			uint3s.add(ds);
	}
	
	public void mergeUInt4(DimensionedDataSource<UnsignedInt4Member> ds) {
		if (ds != null)
			uint4s.add(ds);
	}
	
	public void mergeUInt5(DimensionedDataSource<UnsignedInt5Member> ds) {
		if (ds != null)
			uint5s.add(ds);
	}
	
	public void mergeUInt6(DimensionedDataSource<UnsignedInt6Member> ds) {
		if (ds != null)
			uint6s.add(ds);
	}
	
	public void mergeUInt7(DimensionedDataSource<UnsignedInt7Member> ds) {
		if (ds != null)
			uint7s.add(ds);
	}
	
	public void mergeUInt8(DimensionedDataSource<UnsignedInt8Member> ds) {
		if (ds != null)
			uint8s.add(ds);
	}
	
	public void mergeUInt9(DimensionedDataSource<UnsignedInt9Member> ds) {
		if (ds != null)
			uint9s.add(ds);
	}
	
	public void mergeUInt10(DimensionedDataSource<UnsignedInt10Member> ds) {
		if (ds != null)
			uint10s.add(ds);
	}
	
	public void mergeUInt11(DimensionedDataSource<UnsignedInt11Member> ds) {
		if (ds != null)
			uint11s.add(ds);
	}
	
	public void mergeUInt12(DimensionedDataSource<UnsignedInt12Member> ds) {
		if (ds != null)
			uint12s.add(ds);
	}

	public void mergeUInt13(DimensionedDataSource<UnsignedInt13Member> ds) {
		if (ds != null)
			uint13s.add(ds);
	}
	
	public void mergeUInt14(DimensionedDataSource<UnsignedInt14Member> ds) {
		if (ds != null)
			uint14s.add(ds);
	}
	
	public void mergeUInt15(DimensionedDataSource<UnsignedInt15Member> ds) {
		if (ds != null)
			uint15s.add(ds);
	}
	
	public void mergeUInt16(DimensionedDataSource<UnsignedInt16Member> ds) {
		if (ds != null)
			uint16s.add(ds);
	}
	
	public void mergeUInt32(DimensionedDataSource<UnsignedInt32Member> ds) {
		if (ds != null)
			uint32s.add(ds);
	}
	
	public void mergeUInt64(DimensionedDataSource<UnsignedInt64Member> ds) {
		if (ds != null)
			uint64s.add(ds);
	}
	
	public void mergeUInt128(DimensionedDataSource<UnsignedInt128Member> ds) {
		if (ds != null)
			uint128s.add(ds);
	}
	
	public void mergeInt1(DimensionedDataSource<SignedInt1Member> ds) {
		if (ds != null)
			int1s.add(ds);
	}
	
	public void mergeInt2(DimensionedDataSource<SignedInt2Member> ds) {
		if (ds != null)
			int2s.add(ds);
	}
	
	public void mergeInt3(DimensionedDataSource<SignedInt3Member> ds) {
		if (ds != null)
			int3s.add(ds);
	}
	
	public void mergeInt4(DimensionedDataSource<SignedInt4Member> ds) {
		if (ds != null)
			int4s.add(ds);
	}
	
	public void mergeInt5(DimensionedDataSource<SignedInt5Member> ds) {
		if (ds != null)
			int5s.add(ds);
	}
	
	public void mergeInt6(DimensionedDataSource<SignedInt6Member> ds) {
		if (ds != null)
			int6s.add(ds);
	}
	
	public void mergeInt7(DimensionedDataSource<SignedInt7Member> ds) {
		if (ds != null)
			int7s.add(ds);
	}
	
	public void mergeInt8(DimensionedDataSource<SignedInt8Member> ds) {
		if (ds != null)
			int8s.add(ds);
	}
	
	public void mergeInt9(DimensionedDataSource<SignedInt9Member> ds) {
		if (ds != null)
			int9s.add(ds);
	}
	
	public void mergeInt10(DimensionedDataSource<SignedInt10Member> ds) {
		if (ds != null)
			int10s.add(ds);
	}
	
	public void mergeInt11(DimensionedDataSource<SignedInt11Member> ds) {
		if (ds != null)
			int11s.add(ds);
	}
	
	public void mergeInt12(DimensionedDataSource<SignedInt12Member> ds) {
		if (ds != null)
			int12s.add(ds);
	}

	public void mergeInt13(DimensionedDataSource<SignedInt13Member> ds) {
		if (ds != null)
			int13s.add(ds);
	}
	
	public void mergeInt14(DimensionedDataSource<SignedInt14Member> ds) {
		if (ds != null)
			int14s.add(ds);
	}
	
	public void mergeInt15(DimensionedDataSource<SignedInt15Member> ds) {
		if (ds != null)
			int15s.add(ds);
	}
	
	public void mergeInt16(DimensionedDataSource<SignedInt16Member> ds) {
		if (ds != null)
			int16s.add(ds);
	}
	
	public void mergeInt32(DimensionedDataSource<SignedInt32Member> ds) {
		if (ds != null)
			int32s.add(ds);
	}
	
	public void mergeInt64(DimensionedDataSource<SignedInt64Member> ds) {
		if (ds != null)
			int64s.add(ds);
	}
	
	public void mergeInt128(DimensionedDataSource<SignedInt128Member> ds) {
		if (ds != null)
			int128s.add(ds);
	}
	
	public void mergeBigInt(DimensionedDataSource<UnboundedIntMember> ds) {
		if (ds != null)
			bigs.add(ds);
	}
	
	public void mergeRational(DimensionedDataSource<RationalMember> ds) {
		if (ds != null)
			rats.add(ds);
	}

	public void mergeFloat16(DimensionedDataSource<Float16Member> ds) {
		if (ds != null)
			flt16s.add(ds);
	}
	
	public void mergeFloat32(DimensionedDataSource<Float32Member> ds) {
		if (ds != null)
			flt32s.add(ds);
	}

	public void mergeFloat64(DimensionedDataSource<Float64Member> ds) {
		if (ds != null)
			flt64s.add(ds);
	}
	
	public void mergeHighPrec(DimensionedDataSource<HighPrecisionMember> ds) {
		if (ds != null)
			hps.add(ds);
	}
	
	public void mergeComplexFloat16(DimensionedDataSource<ComplexFloat16Member> ds) {
		if (ds != null)
			cflt16s.add(ds);
	}
	
	public void mergeComplexFloat32(DimensionedDataSource<ComplexFloat32Member> ds) {
		if (ds != null)
			cflt32s.add(ds);
	}

	public void mergeComplexFloat64(DimensionedDataSource<ComplexFloat64Member> ds) {
		if (ds != null)
			cflt64s.add(ds);
	}
	
	public void mergeComplexHighPrec(DimensionedDataSource<ComplexHighPrecisionMember> ds) {
		if (ds != null)
			chps.add(ds);
	}
	
	public void mergeQuaternionFloat16(DimensionedDataSource<QuaternionFloat16Member> ds) {
		if (ds != null)
			qflt16s.add(ds);
	}
	
	public void mergeQuaternionFloat32(DimensionedDataSource<QuaternionFloat32Member> ds) {
		if (ds != null)
			qflt32s.add(ds);
	}

	public void mergeQuaternionFloat64(DimensionedDataSource<QuaternionFloat64Member> ds) {
		if (ds != null)
			qflt64s.add(ds);
	}
	
	public void mergeQuaternionHighPrec(DimensionedDataSource<QuaternionHighPrecisionMember> ds) {
		if (ds != null)
			qhps.add(ds);
	}
	
	public void mergeOctonionFloat16(DimensionedDataSource<OctonionFloat16Member> ds) {
		if (ds != null)
			oflt16s.add(ds);
	}
	
	public void mergeOctonionFloat32(DimensionedDataSource<OctonionFloat32Member> ds) {
		if (ds != null)
			oflt32s.add(ds);
	}

	public void mergeOctonionFloat64(DimensionedDataSource<OctonionFloat64Member> ds) {
		if (ds != null)
			oflt64s.add(ds);
	}
	
	public void mergeOctonionHighPrec(DimensionedDataSource<OctonionHighPrecisionMember> ds) {
		if (ds != null)
			ohps.add(ds);
	}
	
	public void mergeArgb(DimensionedDataSource<ArgbMember> ds) {
		if (ds != null)
			argbs.add(ds);
	}
	
	public void mergeRgb(DimensionedDataSource<RgbMember> ds) {
		if (ds != null)
			rgbs.add(ds);
	}
	
	public void mergeFixedString(DimensionedDataSource<FixedStringMember> ds) {
		if (ds != null)
			fstrs.add(ds);
	}
	
	public void mergePoint(DimensionedDataSource<Point> ds) {
		if (ds != null)
			points.add(ds);
	}
	
	public void mergeAll(DataBundle other) {
		if (this == other) return;

		this.argbs.addAll(other.argbs);
		this.bigs.addAll(other.bigs);
		this.cflt16s.addAll(other.cflt16s);
		this.cflt32s.addAll(other.cflt32s);
		this.cflt64s.addAll(other.cflt64s);
		this.chps.addAll(other.chps);
		this.flt16s.addAll(other.flt16s);
		this.flt32s.addAll(other.flt32s);
		this.flt64s.addAll(other.flt64s);
		this.fstrs.addAll(other.fstrs);
		this.hps.addAll(other.hps);
		this.int1s.addAll(other.int1s);
		this.int2s.addAll(other.int2s);
		this.int3s.addAll(other.int3s);
		this.int4s.addAll(other.int4s);
		this.int5s.addAll(other.int5s);
		this.int6s.addAll(other.int6s);
		this.int7s.addAll(other.int7s);
		this.int8s.addAll(other.int8s);
		this.int9s.addAll(other.int9s);
		this.int10s.addAll(other.int10s);
		this.int11s.addAll(other.int11s);
		this.int12s.addAll(other.int12s);
		this.int13s.addAll(other.int13s);
		this.int14s.addAll(other.int14s);
		this.int15s.addAll(other.int15s);
		this.int16s.addAll(other.int16s);
		this.int32s.addAll(other.int32s);
		this.int64s.addAll(other.int64s);
		this.int128s.addAll(other.int128s);
		this.oflt16s.addAll(other.oflt16s);
		this.oflt32s.addAll(other.oflt32s);
		this.oflt64s.addAll(other.oflt64s);
		this.ohps.addAll(other.ohps);
		this.points.addAll(other.points);
		this.qflt16s.addAll(other.qflt16s);
		this.qflt32s.addAll(other.qflt32s);
		this.qflt64s.addAll(other.qflt64s);
		this.qhps.addAll(other.qhps);
		this.rats.addAll(other.rats);
		this.rgbs.addAll(other.rgbs);
		this.uint1s.addAll(other.uint1s);
		this.uint2s.addAll(other.uint2s);
		this.uint3s.addAll(other.uint3s);
		this.uint4s.addAll(other.uint4s);
		this.uint5s.addAll(other.uint5s);
		this.uint6s.addAll(other.uint6s);
		this.uint7s.addAll(other.uint7s);
		this.uint8s.addAll(other.uint8s);
		this.uint9s.addAll(other.uint9s);
		this.uint10s.addAll(other.uint10s);
		this.uint11s.addAll(other.uint11s);
		this.uint12s.addAll(other.uint12s);
		this.uint13s.addAll(other.uint13s);
		this.uint14s.addAll(other.uint14s);
		this.uint15s.addAll(other.uint15s);
		this.uint16s.addAll(other.uint16s);
		this.uint32s.addAll(other.uint32s);
		this.uint64s.addAll(other.uint64s);
		this.uint128s.addAll(other.uint128s);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Algebra<T,U>, U>
		List<Tuple2<T,DimensionedDataSource<U>>> bundle()
	{
		ArrayList<Tuple2<T,DimensionedDataSource<U>>> fullList = new ArrayList<>();
		for (DimensionedDataSource<?> ds : this.argbs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.ARGB, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.bigs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UNBOUND, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cflt16s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHLF, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cflt32s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CFLT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cflt64s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CDBL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.chps) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHP, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.flt16s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HLF, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.flt32s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.FLT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cflt64s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.DBL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.fstrs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.FSTRING, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.hps) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HP, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int1s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT1, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int2s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT2, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int3s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT3, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int4s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT4, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int5s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT5, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int6s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT6, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int7s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT7, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int8s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT8, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int9s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT9, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int10s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT10, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int11s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT11, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int12s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT12, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int13s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT13, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int14s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT14, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int15s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT15, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int16s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT16, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int32s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT32, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int64s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT64, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.int128s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.INT128, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oflt16s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHLF, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oflt32s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OFLT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oflt64s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.ODBL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.ohps) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHP, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.points) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.POINT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qflt16s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHLF, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qflt32s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QFLT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qflt64s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QDBL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qhps) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHP, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.rats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.RAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.rgbs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.RGB, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint1s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT1, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint2s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT2, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint3s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT3, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint4s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT4, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint5s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT5, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint6s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT6, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint7s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT7, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint8s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT8, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint9s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT9, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint10s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT10, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint11s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT11, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint12s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT12, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint13s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT13, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint14s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT14, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint15s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT15, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint16s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT16, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint32s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT32, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint64s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT64, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.uint128s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UINT128, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		return fullList;
	}
}
