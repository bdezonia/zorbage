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

import java.util.ArrayList;
import java.util.List;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.tuple.Tuple2;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.character.CharMember;
import nom.bdezonia.zorbage.type.color.ArgbMember;
import nom.bdezonia.zorbage.type.color.CieLabMember;
import nom.bdezonia.zorbage.type.color.RgbMember;
import nom.bdezonia.zorbage.type.complex.float128.ComplexFloat128CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.complex.float128.ComplexFloat128MatrixMember;
import nom.bdezonia.zorbage.type.complex.float128.ComplexFloat128Member;
import nom.bdezonia.zorbage.type.complex.float128.ComplexFloat128VectorMember;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16MatrixMember;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16Member;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16VectorMember;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32MatrixMember;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32VectorMember;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64MatrixMember;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64VectorMember;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionCartesianTensorProductMember;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionMatrixMember;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionMember;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionVectorMember;
import nom.bdezonia.zorbage.type.gaussian.int16.GaussianInt16Member;
import nom.bdezonia.zorbage.type.gaussian.int32.GaussianInt32Member;
import nom.bdezonia.zorbage.type.gaussian.int64.GaussianInt64Member;
import nom.bdezonia.zorbage.type.gaussian.int8.GaussianInt8Member;
import nom.bdezonia.zorbage.type.gaussian.unbounded.GaussianIntUnboundedMember;
import nom.bdezonia.zorbage.type.geom.point.Point;
import nom.bdezonia.zorbage.type.integer.int1.SignedInt1Member;
import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Member;
import nom.bdezonia.zorbage.type.integer.int10.SignedInt10Member;
import nom.bdezonia.zorbage.type.integer.int10.UnsignedInt10Member;
import nom.bdezonia.zorbage.type.integer.int11.SignedInt11Member;
import nom.bdezonia.zorbage.type.integer.int11.UnsignedInt11Member;
import nom.bdezonia.zorbage.type.integer.int12.SignedInt12Member;
import nom.bdezonia.zorbage.type.integer.int12.UnsignedInt12Member;
import nom.bdezonia.zorbage.type.integer.int128.SignedInt128Member;
import nom.bdezonia.zorbage.type.integer.int128.UnsignedInt128Member;
import nom.bdezonia.zorbage.type.integer.int13.SignedInt13Member;
import nom.bdezonia.zorbage.type.integer.int13.UnsignedInt13Member;
import nom.bdezonia.zorbage.type.integer.int14.SignedInt14Member;
import nom.bdezonia.zorbage.type.integer.int14.UnsignedInt14Member;
import nom.bdezonia.zorbage.type.integer.int15.SignedInt15Member;
import nom.bdezonia.zorbage.type.integer.int15.UnsignedInt15Member;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.integer.int16.UnsignedInt16Member;
import nom.bdezonia.zorbage.type.integer.int2.SignedInt2Member;
import nom.bdezonia.zorbage.type.integer.int2.UnsignedInt2Member;
import nom.bdezonia.zorbage.type.integer.int3.SignedInt3Member;
import nom.bdezonia.zorbage.type.integer.int3.UnsignedInt3Member;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.integer.int32.UnsignedInt32Member;
import nom.bdezonia.zorbage.type.integer.int4.SignedInt4Member;
import nom.bdezonia.zorbage.type.integer.int4.UnsignedInt4Member;
import nom.bdezonia.zorbage.type.integer.int5.SignedInt5Member;
import nom.bdezonia.zorbage.type.integer.int5.UnsignedInt5Member;
import nom.bdezonia.zorbage.type.integer.int6.SignedInt6Member;
import nom.bdezonia.zorbage.type.integer.int6.UnsignedInt6Member;
import nom.bdezonia.zorbage.type.integer.int64.SignedInt64Member;
import nom.bdezonia.zorbage.type.integer.int64.UnsignedInt64Member;
import nom.bdezonia.zorbage.type.integer.int7.SignedInt7Member;
import nom.bdezonia.zorbage.type.integer.int7.UnsignedInt7Member;
import nom.bdezonia.zorbage.type.integer.int8.SignedInt8Member;
import nom.bdezonia.zorbage.type.integer.int8.UnsignedInt8Member;
import nom.bdezonia.zorbage.type.integer.int9.SignedInt9Member;
import nom.bdezonia.zorbage.type.integer.int9.UnsignedInt9Member;
import nom.bdezonia.zorbage.type.integer.unbounded.UnboundedIntMember;
import nom.bdezonia.zorbage.type.octonion.float128.OctonionFloat128CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.octonion.float128.OctonionFloat128MatrixMember;
import nom.bdezonia.zorbage.type.octonion.float128.OctonionFloat128Member;
import nom.bdezonia.zorbage.type.octonion.float128.OctonionFloat128RModuleMember;
import nom.bdezonia.zorbage.type.octonion.float16.OctonionFloat16CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.octonion.float16.OctonionFloat16MatrixMember;
import nom.bdezonia.zorbage.type.octonion.float16.OctonionFloat16Member;
import nom.bdezonia.zorbage.type.octonion.float16.OctonionFloat16RModuleMember;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32MatrixMember;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32Member;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32RModuleMember;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64Member;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.octonion.highprec.OctonionHighPrecisionCartesianTensorProductMember;
import nom.bdezonia.zorbage.type.octonion.highprec.OctonionHighPrecisionMatrixMember;
import nom.bdezonia.zorbage.type.octonion.highprec.OctonionHighPrecisionMember;
import nom.bdezonia.zorbage.type.octonion.highprec.OctonionHighPrecisionRModuleMember;
import nom.bdezonia.zorbage.type.quaternion.float128.QuaternionFloat128CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.quaternion.float128.QuaternionFloat128MatrixMember;
import nom.bdezonia.zorbage.type.quaternion.float128.QuaternionFloat128Member;
import nom.bdezonia.zorbage.type.quaternion.float128.QuaternionFloat128RModuleMember;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16MatrixMember;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16Member;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16RModuleMember;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32MatrixMember;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32Member;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32RModuleMember;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64MatrixMember;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64Member;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64RModuleMember;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionCartesianTensorProductMember;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionMatrixMember;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionMember;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionRModuleMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float128.Float128CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.real.float128.Float128MatrixMember;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.float128.Float128VectorMember;
import nom.bdezonia.zorbage.type.real.float16.Float16CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.real.float16.Float16MatrixMember;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.float16.Float16VectorMember;
import nom.bdezonia.zorbage.type.real.float32.Float32CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.real.float32.Float32MatrixMember;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.float32.Float32VectorMember;
import nom.bdezonia.zorbage.type.real.float64.Float64CartesianTensorProductMember;
import nom.bdezonia.zorbage.type.real.float64.Float64MatrixMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.float64.Float64VectorMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionCartesianTensorProductMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMatrixMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionVectorMember;
import nom.bdezonia.zorbage.type.string.FixedStringMember;
import nom.bdezonia.zorbage.type.string.StringMember;

/**
 * @author Barry DeZonia
 */
public class DataBundle {

	// DECLARATION OF FIELDS
	
	// type.bool
	public List<DimensionedDataSource<BooleanMember>> bools = new ArrayList<>();
	
	// type.character
	public List<DimensionedDataSource<CharMember>> chars = new ArrayList<>();
	
	// type.color
	public List<DimensionedDataSource<ArgbMember>> argbs = new ArrayList<>();
	public List<DimensionedDataSource<CieLabMember>> cielabs = new ArrayList<>();
	public List<DimensionedDataSource<RgbMember>> rgbs = new ArrayList<>();
	
	// type.complex.float128
	public List<DimensionedDataSource<ComplexFloat128Member>> cquads = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat128VectorMember>> cquad_vecs = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat128MatrixMember>> cquad_mats = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat128CartesianTensorProductMember>> cquad_tens = new ArrayList<>();
	
	// type.complex.float16
	public List<DimensionedDataSource<ComplexFloat16Member>> chlfs = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat16VectorMember>> chlf_vecs = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat16MatrixMember>> chlf_mats = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat16CartesianTensorProductMember>> chlf_tens = new ArrayList<>();
	
	// type.complex.float32
	public List<DimensionedDataSource<ComplexFloat32Member>> cflts = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat32VectorMember>> cflt_vecs = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat32MatrixMember>> cflt_mats = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat32CartesianTensorProductMember>> cflt_tens = new ArrayList<>();
	
	// type.complex.float64
	public List<DimensionedDataSource<ComplexFloat64Member>> cdbls = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat64VectorMember>> cdbl_vecs = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat64MatrixMember>> cdbl_mats = new ArrayList<>();
	public List<DimensionedDataSource<ComplexFloat64CartesianTensorProductMember>> cdbl_tens = new ArrayList<>();
	
	// type.complex.highprec
	public List<DimensionedDataSource<ComplexHighPrecisionMember>> chps = new ArrayList<>();
	public List<DimensionedDataSource<ComplexHighPrecisionVectorMember>> chp_vecs = new ArrayList<>();
	public List<DimensionedDataSource<ComplexHighPrecisionMatrixMember>> chp_mats = new ArrayList<>();
	public List<DimensionedDataSource<ComplexHighPrecisionCartesianTensorProductMember>> chp_tens = new ArrayList<>();
	
	// type.gaussian
	public List<DimensionedDataSource<GaussianInt8Member>> gint8s = new ArrayList<>();
	public List<DimensionedDataSource<GaussianInt16Member>> gint16s = new ArrayList<>();
	public List<DimensionedDataSource<GaussianInt32Member>> gint32s = new ArrayList<>();
	public List<DimensionedDataSource<GaussianInt64Member>> gint64s = new ArrayList<>();
	public List<DimensionedDataSource<GaussianIntUnboundedMember>> gintus = new ArrayList<>();
	
	// type.integer
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
	public List<DimensionedDataSource<UnboundedIntMember>> unbounds = new ArrayList<>();
	
	// type.octonion.float128
	public List<DimensionedDataSource<OctonionFloat128Member>> oquads = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat128RModuleMember>> oquad_rmods = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat128MatrixMember>> oquad_mats = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat128CartesianTensorProductMember>> oquad_tens = new ArrayList<>();
	
	// type.octonion.float16
	public List<DimensionedDataSource<OctonionFloat16Member>> ohlfs = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat16RModuleMember>> ohlf_rmods = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat16MatrixMember>> ohlf_mats = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat16CartesianTensorProductMember>> ohlf_tens = new ArrayList<>();
	
	// type.octonion.float32
	public List<DimensionedDataSource<OctonionFloat32Member>> oflts = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat32RModuleMember>> oflt_rmods = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat32MatrixMember>> oflt_mats = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat32CartesianTensorProductMember>> oflt_tens = new ArrayList<>();
	
	// type.octonion.float64
	public List<DimensionedDataSource<OctonionFloat64Member>> odbls = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat64RModuleMember>> odbl_rmods = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat64MatrixMember>> odbl_mats = new ArrayList<>();
	public List<DimensionedDataSource<OctonionFloat64CartesianTensorProductMember>> odbl_tens = new ArrayList<>();
	
	// type.octonion.highprec
	public List<DimensionedDataSource<OctonionHighPrecisionMember>> ohps = new ArrayList<>();
	public List<DimensionedDataSource<OctonionHighPrecisionRModuleMember>> ohp_rmods = new ArrayList<>();
	public List<DimensionedDataSource<OctonionHighPrecisionMatrixMember>> ohp_mats = new ArrayList<>();
	public List<DimensionedDataSource<OctonionHighPrecisionCartesianTensorProductMember>> ohp_tens = new ArrayList<>();
	
	// type.point
	public List<DimensionedDataSource<Point>> points = new ArrayList<>();
	
	// type.quaternion.float128
	public List<DimensionedDataSource<QuaternionFloat128Member>> qquads = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat128RModuleMember>> qquad_rmods = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat128MatrixMember>> qquad_mats = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat128CartesianTensorProductMember>> qquad_tens = new ArrayList<>();
	
	// type.quaternion.float16
	public List<DimensionedDataSource<QuaternionFloat16Member>> qhlfs = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat16RModuleMember>> qhlf_rmods = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat16MatrixMember>> qhlf_mats = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat16CartesianTensorProductMember>> qhlf_tens = new ArrayList<>();
	
	// type.quaternion.float32
	public List<DimensionedDataSource<QuaternionFloat32Member>> qflts = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat32RModuleMember>> qflt_rmods = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat32MatrixMember>> qflt_mats = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat32CartesianTensorProductMember>> qflt_tens = new ArrayList<>();
	
	// type.quaternion.float64
	public List<DimensionedDataSource<QuaternionFloat64Member>> qdbls = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat64RModuleMember>> qdbl_rmods = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat64MatrixMember>> qdbl_mats = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionFloat64CartesianTensorProductMember>> qdbl_tens = new ArrayList<>();
	
	// type.quaternion.highprec
	public List<DimensionedDataSource<QuaternionHighPrecisionMember>> qhps = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionHighPrecisionRModuleMember>> qhp_rmods = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionHighPrecisionMatrixMember>> qhp_mats = new ArrayList<>();
	public List<DimensionedDataSource<QuaternionHighPrecisionCartesianTensorProductMember>> qhp_tens = new ArrayList<>();
	
	// type.rational
	public List<DimensionedDataSource<RationalMember>> rationals = new ArrayList<>();
	
	// type.real.float128
	public List<DimensionedDataSource<Float128Member>> quads = new ArrayList<>();
	public List<DimensionedDataSource<Float128VectorMember>> quad_vecs = new ArrayList<>();
	public List<DimensionedDataSource<Float128MatrixMember>> quad_mats = new ArrayList<>();
	public List<DimensionedDataSource<Float128CartesianTensorProductMember>> quad_tens = new ArrayList<>();
	
	// type.real.float16
	public List<DimensionedDataSource<Float16Member>> hlfs = new ArrayList<>();
	public List<DimensionedDataSource<Float16VectorMember>> hlf_vecs = new ArrayList<>();
	public List<DimensionedDataSource<Float16MatrixMember>> hlf_mats = new ArrayList<>();
	public List<DimensionedDataSource<Float16CartesianTensorProductMember>> hlf_tens = new ArrayList<>();
	
	// type.real.float32
	public List<DimensionedDataSource<Float32Member>> flts = new ArrayList<>();
	public List<DimensionedDataSource<Float32VectorMember>> flt_vecs = new ArrayList<>();
	public List<DimensionedDataSource<Float32MatrixMember>> flt_mats = new ArrayList<>();
	public List<DimensionedDataSource<Float32CartesianTensorProductMember>> flt_tens = new ArrayList<>();
	
	// type.real.float64
	public List<DimensionedDataSource<Float64Member>> dbls = new ArrayList<>();
	public List<DimensionedDataSource<Float64VectorMember>> dbl_vecs = new ArrayList<>();
	public List<DimensionedDataSource<Float64MatrixMember>> dbl_mats = new ArrayList<>();
	public List<DimensionedDataSource<Float64CartesianTensorProductMember>> dbl_tens = new ArrayList<>();
	
	// type.real.highprec
	public List<DimensionedDataSource<HighPrecisionMember>> hps = new ArrayList<>();
	public List<DimensionedDataSource<HighPrecisionVectorMember>> hp_vecs = new ArrayList<>();
	public List<DimensionedDataSource<HighPrecisionMatrixMember>> hp_mats = new ArrayList<>();
	public List<DimensionedDataSource<HighPrecisionCartesianTensorProductMember>> hp_tens = new ArrayList<>();
	
	// type.string
	public List<DimensionedDataSource<FixedStringMember>> fstrs = new ArrayList<>();
	public List<DimensionedDataSource<StringMember>> strs = new ArrayList<>();
	
	// METHODS FOR ADDING DATA TO THE STRUCTURE
	
	// type.bool

	public void mergeBool(DimensionedDataSource<BooleanMember> ds) {
		if (ds != null)
			bools.add(ds);
	}
	
	// type.character

	public void mergeChar(DimensionedDataSource<CharMember> ds) {
		if (ds != null)
			chars.add(ds);
	}
	
	// type.color
	
	public void mergeArgb(DimensionedDataSource<ArgbMember> ds) {
		if (ds != null)
			argbs.add(ds);
	}

	public void mergeCieLab(DimensionedDataSource<CieLabMember> ds) {
		if (ds != null)
			cielabs.add(ds);
	}

	public void mergeRgb(DimensionedDataSource<RgbMember> ds) {
		if (ds != null)
			rgbs.add(ds);
	}
	
	// type.complex.float128
	
	public void mergeComplexFlt128(DimensionedDataSource<ComplexFloat128Member> ds) {
		if (ds != null)
			cquads.add(ds);
	}
	
	public void mergeComplexFlt128Vec(DimensionedDataSource<ComplexFloat128VectorMember> ds) {
		if (ds != null)
			cquad_vecs.add(ds);
	}
	
	public void mergeComplexFlt128Mat(DimensionedDataSource<ComplexFloat128MatrixMember> ds) {
		if (ds != null)
			cquad_mats.add(ds);
	}
	
	public void mergeComplexFlt128Tens(DimensionedDataSource<ComplexFloat128CartesianTensorProductMember> ds) {
		if (ds != null)
			cquad_tens.add(ds);
	}
	
	// type.complex.float16
	
	public void mergeComplexFlt16(DimensionedDataSource<ComplexFloat16Member> ds) {
		if (ds != null)
			chlfs.add(ds);
	}
	
	public void mergeComplexFlt16Vec(DimensionedDataSource<ComplexFloat16VectorMember> ds) {
		if (ds != null)
			chlf_vecs.add(ds);
	}
	
	public void mergeComplexFlt16Mat(DimensionedDataSource<ComplexFloat16MatrixMember> ds) {
		if (ds != null)
			chlf_mats.add(ds);
	}
	
	public void mergeComplexFlt16Tens(DimensionedDataSource<ComplexFloat16CartesianTensorProductMember> ds) {
		if (ds != null)
			chlf_tens.add(ds);
	}
	
	// type.complex.float32
	
	public void mergeComplexFlt32(DimensionedDataSource<ComplexFloat32Member> ds) {
		if (ds != null)
			cflts.add(ds);
	}
	
	public void mergeComplexFlt32Vec(DimensionedDataSource<ComplexFloat32VectorMember> ds) {
		if (ds != null)
			cflt_vecs.add(ds);
	}
	
	public void mergeComplexFlt32Mat(DimensionedDataSource<ComplexFloat32MatrixMember> ds) {
		if (ds != null)
			cflt_mats.add(ds);
	}
	
	public void mergeComplexFlt32Tens(DimensionedDataSource<ComplexFloat32CartesianTensorProductMember> ds) {
		if (ds != null)
			cflt_tens.add(ds);
	}
	
	// type.complex.float64
	
	public void mergeComplexFlt64(DimensionedDataSource<ComplexFloat64Member> ds) {
		if (ds != null)
			cdbls.add(ds);
	}
	
	public void mergeComplexFlt64Vec(DimensionedDataSource<ComplexFloat64VectorMember> ds) {
		if (ds != null)
			cdbl_vecs.add(ds);
	}
	
	public void mergeComplexFlt64Mat(DimensionedDataSource<ComplexFloat64MatrixMember> ds) {
		if (ds != null)
			cdbl_mats.add(ds);
	}
	
	public void mergeComplexFlt64Tens(DimensionedDataSource<ComplexFloat64CartesianTensorProductMember> ds) {
		if (ds != null)
			cdbl_tens.add(ds);
	}
	
	// type.complex.highprec

	public void mergeComplexHP(DimensionedDataSource<ComplexHighPrecisionMember> ds) {
		if (ds != null)
			chps.add(ds);
	}
	
	public void mergeComplexHPVec(DimensionedDataSource<ComplexHighPrecisionVectorMember> ds) {
		if (ds != null)
			chp_vecs.add(ds);
	}
	
	public void mergeComplexHPMat(DimensionedDataSource<ComplexHighPrecisionMatrixMember> ds) {
		if (ds != null)
			chp_mats.add(ds);
	}
	
	public void mergeComplexHPTens(DimensionedDataSource<ComplexHighPrecisionCartesianTensorProductMember> ds) {
		if (ds != null)
			chp_tens.add(ds);
	}
	
	// type.gaussian
	
	public void mergeGaussianInt8(DimensionedDataSource<GaussianInt8Member> ds) {
		if (ds != null)
			gint8s.add(ds);
	}
	
	public void mergeGaussianInt16(DimensionedDataSource<GaussianInt16Member> ds) {
		if (ds != null)
			gint16s.add(ds);
	}
	
	public void mergeGaussianInt32(DimensionedDataSource<GaussianInt32Member> ds) {
		if (ds != null)
			gint32s.add(ds);
	}
	
	public void mergeGaussianInt64(DimensionedDataSource<GaussianInt64Member> ds) {
		if (ds != null)
			gint64s.add(ds);
	}
	
	public void mergeGaussianIntUnbounded(DimensionedDataSource<GaussianIntUnboundedMember> ds) {
		if (ds != null)
			gintus.add(ds);
	}

	// type.integer
	
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

	public void mergeBigInt(DimensionedDataSource<UnboundedIntMember> ds) {
		if (ds != null)
			unbounds.add(ds);
	}
	
	// type.octonion.float128
	
	public void mergeOct128(DimensionedDataSource<OctonionFloat128Member> ds) {
		if (ds != null)
			oquads.add(ds);
	}
	
	public void mergeOct128RMod(DimensionedDataSource<OctonionFloat128RModuleMember> ds) {
		if (ds != null)
			oquad_rmods.add(ds);
	}
	
	public void mergeOct128Mat(DimensionedDataSource<OctonionFloat128MatrixMember> ds) {
		if (ds != null)
			oquad_mats.add(ds);
	}
	
	public void mergeOct128Tens(DimensionedDataSource<OctonionFloat128CartesianTensorProductMember> ds) {
		if (ds != null)
			oquad_tens.add(ds);
	}

	// type.octonion.float16
	
	public void mergeOct16(DimensionedDataSource<OctonionFloat16Member> ds) {
		if (ds != null)
			ohlfs.add(ds);
	}
	
	public void mergeOct16RMod(DimensionedDataSource<OctonionFloat16RModuleMember> ds) {
		if (ds != null)
			ohlf_rmods.add(ds);
	}
	
	public void mergeOct16Mat(DimensionedDataSource<OctonionFloat16MatrixMember> ds) {
		if (ds != null)
			ohlf_mats.add(ds);
	}
	
	public void mergeOct16Tens(DimensionedDataSource<OctonionFloat16CartesianTensorProductMember> ds) {
		if (ds != null)
			ohlf_tens.add(ds);
	}

	// type.octonion.float32
	
	public void mergeOct32(DimensionedDataSource<OctonionFloat32Member> ds) {
		if (ds != null)
			oflts.add(ds);
	}
	
	public void mergeOct32RMod(DimensionedDataSource<OctonionFloat32RModuleMember> ds) {
		if (ds != null)
			oflt_rmods.add(ds);
	}
	
	public void mergeOct32Mat(DimensionedDataSource<OctonionFloat32MatrixMember> ds) {
		if (ds != null)
			oflt_mats.add(ds);
	}
	
	public void mergeOct32Tens(DimensionedDataSource<OctonionFloat32CartesianTensorProductMember> ds) {
		if (ds != null)
			oflt_tens.add(ds);
	}

	// type.octonion.float64
	
	public void mergeOct64(DimensionedDataSource<OctonionFloat64Member> ds) {
		if (ds != null)
			odbls.add(ds);
	}
	
	public void mergeOct64RMod(DimensionedDataSource<OctonionFloat64RModuleMember> ds) {
		if (ds != null)
			odbl_rmods.add(ds);
	}
	
	public void mergeOct64Mat(DimensionedDataSource<OctonionFloat64MatrixMember> ds) {
		if (ds != null)
			odbl_mats.add(ds);
	}
	
	public void mergeOct64Tens(DimensionedDataSource<OctonionFloat64CartesianTensorProductMember> ds) {
		if (ds != null)
			odbl_tens.add(ds);
	}

	// type.octonion.highprec
	
	public void mergeOctHP(DimensionedDataSource<OctonionHighPrecisionMember> ds) {
		if (ds != null)
			ohps.add(ds);
	}
	
	public void mergeOctHPRMod(DimensionedDataSource<OctonionHighPrecisionRModuleMember> ds) {
		if (ds != null)
			ohp_rmods.add(ds);
	}
	
	public void mergeOctHPMat(DimensionedDataSource<OctonionHighPrecisionMatrixMember> ds) {
		if (ds != null)
			ohp_mats.add(ds);
	}
	
	public void mergeOctHPTens(DimensionedDataSource<OctonionHighPrecisionCartesianTensorProductMember> ds) {
		if (ds != null)
			ohp_tens.add(ds);
	}

	// type.point
	
	public void mergePoint(DimensionedDataSource<Point> ds) {
		if (ds != null)
			points.add(ds);
	}

	// type.quaternion.float128
	
	public void mergeQuatFlt128(DimensionedDataSource<QuaternionFloat128Member> ds) {
		if (ds != null)
			qquads.add(ds);
	}
	
	public void mergeQuatFlt128RMod(DimensionedDataSource<QuaternionFloat128RModuleMember> ds) {
		if (ds != null)
			qquad_rmods.add(ds);
	}
	
	public void mergeQuatFlt128Mat(DimensionedDataSource<QuaternionFloat128MatrixMember> ds) {
		if (ds != null)
			qquad_mats.add(ds);
	}
	
	public void mergeQuatFlt128Tens(DimensionedDataSource<QuaternionFloat128CartesianTensorProductMember> ds) {
		if (ds != null)
			qquad_tens.add(ds);
	}
	
	// type.quaternion.float16
	
	public void mergeQuat16(DimensionedDataSource<QuaternionFloat16Member> ds) {
		if (ds != null)
			qhlfs.add(ds);
	}
	
	public void mergeQuat16RMod(DimensionedDataSource<QuaternionFloat16RModuleMember> ds) {
		if (ds != null)
			qhlf_rmods.add(ds);
	}
	
	public void mergeQuat16Mat(DimensionedDataSource<QuaternionFloat16MatrixMember> ds) {
		if (ds != null)
			qhlf_mats.add(ds);
	}
	
	public void mergeQuat16Tens(DimensionedDataSource<QuaternionFloat16CartesianTensorProductMember> ds) {
		if (ds != null)
			qhlf_tens.add(ds);
	}
	
	// type.quaternion.float32
	
	public void mergeQuat32(DimensionedDataSource<QuaternionFloat32Member> ds) {
		if (ds != null)
			qflts.add(ds);
	}
	
	public void mergeQuat32RMod(DimensionedDataSource<QuaternionFloat32RModuleMember> ds) {
		if (ds != null)
			qflt_rmods.add(ds);
	}
	
	public void mergeQuat32Mat(DimensionedDataSource<QuaternionFloat32MatrixMember> ds) {
		if (ds != null)
			qflt_mats.add(ds);
	}
	
	public void mergeQuat32Tens(DimensionedDataSource<QuaternionFloat32CartesianTensorProductMember> ds) {
		if (ds != null)
			qflt_tens.add(ds);
	}

	// type.quaternion.float64
	
	public void mergeQuat64(DimensionedDataSource<QuaternionFloat64Member> ds) {
		if (ds != null)
			qdbls.add(ds);
	}
	
	public void mergeQuat64RMod(DimensionedDataSource<QuaternionFloat64RModuleMember> ds) {
		if (ds != null)
			qdbl_rmods.add(ds);
	}
	
	public void mergeQuat64Mat(DimensionedDataSource<QuaternionFloat64MatrixMember> ds) {
		if (ds != null)
			qdbl_mats.add(ds);
	}
	
	public void mergeQuat64Tens(DimensionedDataSource<QuaternionFloat64CartesianTensorProductMember> ds) {
		if (ds != null)
			qdbl_tens.add(ds);
	}

	// type.quaternion.highprec
	
	public void mergeQuatHP(DimensionedDataSource<QuaternionHighPrecisionMember> ds) {
		if (ds != null)
			qhps.add(ds);
	}
	
	public void mergeQuatHPRMod(DimensionedDataSource<QuaternionHighPrecisionRModuleMember> ds) {
		if (ds != null)
			qhp_rmods.add(ds);
	}
	
	public void mergeQuatHPMat(DimensionedDataSource<QuaternionHighPrecisionMatrixMember> ds) {
		if (ds != null)
			qhp_mats.add(ds);
	}
	
	public void mergeQuatHPTens(DimensionedDataSource<QuaternionHighPrecisionCartesianTensorProductMember> ds) {
		if (ds != null)
			qhp_tens.add(ds);
	}
	
	// type.rational
	
	public void mergeRational(DimensionedDataSource<RationalMember> ds) {
		if (ds != null)
			rationals.add(ds);
	}

	// type.real.float128

	public void mergeFlt128(DimensionedDataSource<Float128Member> ds) {
		if (ds != null)
			quads.add(ds);
	}
	
	public void mergeFlt128RMod(DimensionedDataSource<Float128VectorMember> ds) {
		if (ds != null)
			quad_vecs.add(ds);
	}
	
	public void mergeFlt128Mat(DimensionedDataSource<Float128MatrixMember> ds) {
		if (ds != null)
			quad_mats.add(ds);
	}
	
	public void mergeFlt128Tens(DimensionedDataSource<Float128CartesianTensorProductMember> ds) {
		if (ds != null)
			quad_tens.add(ds);
	}
	
	// type.real.float16
	
	public void mergeFlt16(DimensionedDataSource<Float16Member> ds) {
		if (ds != null)
			hlfs.add(ds);
	}
	
	public void mergeFlt16Vec(DimensionedDataSource<Float16VectorMember> ds) {
		if (ds != null)
			hlf_vecs.add(ds);
	}
	
	public void mergeFlt16Mat(DimensionedDataSource<Float16MatrixMember> ds) {
		if (ds != null)
			hlf_mats.add(ds);
	}
	
	public void mergeFlt16Tens(DimensionedDataSource<Float16CartesianTensorProductMember> ds) {
		if (ds != null)
			hlf_tens.add(ds);
	}

	// type.real.float32

	public void mergeFlt32(DimensionedDataSource<Float32Member> ds) {
		if (ds != null)
			flts.add(ds);
	}

	public void mergeFlt32Vec(DimensionedDataSource<Float32VectorMember> ds) {
		if (ds != null)
			flt_vecs.add(ds);
	}

	public void mergeFlt32Mat(DimensionedDataSource<Float32MatrixMember> ds) {
		if (ds != null)
			flt_mats.add(ds);
	}

	public void mergeFlt32Tens(DimensionedDataSource<Float32CartesianTensorProductMember> ds) {
		if (ds != null)
			flt_tens.add(ds);
	}
	
	// type.real.float64
	
	public void mergeFlt64(DimensionedDataSource<Float64Member> ds) {
		if (ds != null)
			dbls.add(ds);
	}
	
	public void mergeFlt64Vec(DimensionedDataSource<Float64VectorMember> ds) {
		if (ds != null)
			dbl_vecs.add(ds);
	}
	
	public void mergeFlt64Mat(DimensionedDataSource<Float64MatrixMember> ds) {
		if (ds != null)
			dbl_mats.add(ds);
	}
	
	public void mergeFlt64Tens(DimensionedDataSource<Float64CartesianTensorProductMember> ds) {
		if (ds != null)
			dbl_tens.add(ds);
	}
	
	// type.real.highprec

	public void mergeHP(DimensionedDataSource<HighPrecisionMember> ds) {
		if (ds != null)
			hps.add(ds);
	}
	
	public void mergeHPVec(DimensionedDataSource<HighPrecisionVectorMember> ds) {
		if (ds != null)
			hp_vecs.add(ds);
	}
	
	public void mergeHPMat(DimensionedDataSource<HighPrecisionMatrixMember> ds) {
		if (ds != null)
			hp_mats.add(ds);
	}
	
	public void mergeHPTens(DimensionedDataSource<HighPrecisionCartesianTensorProductMember> ds) {
		if (ds != null)
			hp_tens.add(ds);
	}
	
	// type.string
	
	public void mergeFixedString(DimensionedDataSource<FixedStringMember> ds) {
		if (ds != null)
			fstrs.add(ds);
	}
	
	public void mergeString(DimensionedDataSource<StringMember> ds) {
		if (ds != null)
			strs.add(ds);
	}

	public void mergeAll(DataBundle other) {
		if (this == other) return;

		// type.bool

		bools.addAll(other.bools);
		
		// type.character

		chars.addAll(other.chars);
		
		// type.color
		
		argbs.addAll(other.argbs);
		cielabs.addAll(other.cielabs);
		rgbs.addAll(other.rgbs);
		
		// type.complex.float128
		
		cquads.addAll(other.cquads);
		cquad_vecs.addAll(other.cquad_vecs);
		cquad_mats.addAll(other.cquad_mats);
		cquad_tens.addAll(other.cquad_tens);
		
		// type.complex.float16
		
		chlfs.addAll(other.chlfs);
		chlf_vecs.addAll(other.chlf_vecs);
		chlf_mats.addAll(other.chlf_mats);
		chlf_tens.addAll(other.chlf_tens);
		
		// type.complex.float32
		
		cflts.addAll(other.cflts);
		cflt_vecs.addAll(other.cflt_vecs);
		cflt_mats.addAll(other.cflt_mats);
		cflt_tens.addAll(other.cflt_tens);
		
		// type.complex.float64
		
		cdbls.addAll(other.cdbls);
		cdbl_vecs.addAll(other.cdbl_vecs);
		cdbl_mats.addAll(other.cdbl_mats);
		cdbl_tens.addAll(other.cdbl_tens);
		
		// type.complex.highprec

		chps.addAll(other.chps);
		chp_vecs.addAll(other.chp_vecs);
		chp_mats.addAll(other.chp_mats);
		chp_tens.addAll(other.chp_tens);
		
		// type.gaussian
		
		gint8s.addAll(other.gint8s);
		gint16s.addAll(other.gint16s);
		gint32s.addAll(other.gint32s);
		gint64s.addAll(other.gint64s);
		gintus.addAll(other.gintus);

		// type.integer
		
		int1s.addAll(other.int1s);
		int2s.addAll(other.int2s);
		int3s.addAll(other.int3s);
		int4s.addAll(other.int4s);
		int5s.addAll(other.int5s);
		int6s.addAll(other.int6s);
		int7s.addAll(other.int7s);
		int8s.addAll(other.int8s);
		int9s.addAll(other.int9s);
		int10s.addAll(other.int10s);
		int11s.addAll(other.int11s);
		int12s.addAll(other.int12s);
		int13s.addAll(other.int13s);
		int14s.addAll(other.int14s);
		int15s.addAll(other.int15s);
		int16s.addAll(other.int16s);
		int32s.addAll(other.int32s);
		int64s.addAll(other.int64s);
		int128s.addAll(other.int128s);
		uint1s.addAll(other.uint1s);
		uint2s.addAll(other.uint2s);
		uint3s.addAll(other.uint3s);
		uint4s.addAll(other.uint4s);
		uint5s.addAll(other.uint5s);
		uint6s.addAll(other.uint6s);
		uint7s.addAll(other.uint7s);
		uint8s.addAll(other.uint8s);
		uint9s.addAll(other.uint9s);
		uint10s.addAll(other.uint10s);
		uint11s.addAll(other.uint11s);
		uint12s.addAll(other.uint12s);
		uint13s.addAll(other.uint13s);
		uint14s.addAll(other.uint14s);
		uint15s.addAll(other.uint15s);
		uint16s.addAll(other.uint16s);
		uint32s.addAll(other.uint32s);
		uint64s.addAll(other.uint64s);
		uint128s.addAll(other.uint128s);
		unbounds.addAll(other.unbounds);
		
		// type.octonion.float128
		
		oquads.addAll(other.oquads);
		oquad_rmods.addAll(other.oquad_rmods);
		oquad_mats.addAll(other.oquad_mats);
		oquad_tens.addAll(other.oquad_tens);

		// type.octonion.float16
		
		ohlfs.addAll(other.ohlfs);
		ohlf_rmods.addAll(other.ohlf_rmods);
		ohlf_mats.addAll(other.ohlf_mats);
		ohlf_tens.addAll(other.ohlf_tens);

		// type.octonion.float32
		
		oflts.addAll(other.oflts);
		oflt_rmods.addAll(other.oflt_rmods);
		oflt_mats.addAll(other.oflt_mats);
		oflt_tens.addAll(other.oflt_tens);

		// type.octonion.float64
		
		odbls.addAll(other.odbls);
		odbl_rmods.addAll(other.odbl_rmods);
		odbl_mats.addAll(other.odbl_mats);
		odbl_tens.addAll(other.odbl_tens);

		// type.octonion.highprec
		
		ohps.addAll(other.ohps);
		ohp_rmods.addAll(other.ohp_rmods);
		ohp_mats.addAll(other.ohp_mats);
		ohp_tens.addAll(other.ohp_tens);

		// type.point
		
		points.addAll(other.points);

		// type.quaternion.float128
		
		qquads.addAll(other.qquads);
		qquad_rmods.addAll(other.qquad_rmods);
		qquad_mats.addAll(other.qquad_mats);
		qquad_tens.addAll(other.qquad_tens);
		
		// type.quaternion.float16
		
		qhlfs.addAll(other.qhlfs);
		qhlf_rmods.addAll(other.qhlf_rmods);
		qhlf_mats.addAll(other.qhlf_mats);
		qhlf_tens.addAll(other.qhlf_tens);
		
		// type.quaternion.float32
		
		qflts.addAll(other.qflts);
		qflt_rmods.addAll(other.qflt_rmods);
		qflt_mats.addAll(other.qflt_mats);
		qflt_tens.addAll(other.qflt_tens);

		// type.quaternion.float64
		
		qdbls.addAll(other.qdbls);
		qdbl_rmods.addAll(other.qdbl_rmods);
		qdbl_mats.addAll(other.qdbl_mats);
		qdbl_tens.addAll(other.qdbl_tens);

		// type.quaternion.highprec
		
		qhps.addAll(other.qhps);
		qhp_rmods.addAll(other.qhp_rmods);
		qhp_mats.addAll(other.qhp_mats);
		qhp_tens.addAll(other.qhp_tens);
		
		// type.rational
		
		rationals.addAll(other.rationals);

		// type.real.float128

		quads.addAll(other.quads);
		quad_vecs.addAll(other.quad_vecs);
		quad_mats.addAll(other.quad_mats);
		quad_tens.addAll(other.quad_tens);
		
		// type.real.float16
		
		hlfs.addAll(other.hlfs);
		hlf_vecs.addAll(other.hlf_vecs);
		hlf_mats.addAll(other.hlf_mats);
		hlf_tens.addAll(other.hlf_tens);

		// type.real.float32

		flts.addAll(other.flts);
		flt_vecs.addAll(other.flt_vecs);
		flt_mats.addAll(other.flt_mats);
		flt_tens.addAll(other.flt_tens);
		
		// type.real.float64
		
		dbls.addAll(other.dbls);
		dbl_vecs.addAll(other.dbl_vecs);
		dbl_mats.addAll(other.dbl_mats);
		dbl_tens.addAll(other.dbl_tens);
		
		// type.real.highprec

		hps.addAll(other.hps);
		hp_vecs.addAll(other.hp_vecs);
		hp_mats.addAll(other.hp_mats);
		hp_tens.addAll(other.hp_tens);
		
		// type.string
		
		fstrs.addAll(other.fstrs);
		strs.addAll(other.strs);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Algebra<T,U>, U>
		List<Tuple2<T,DimensionedDataSource<U>>> bundle()
	{
		ArrayList<Tuple2<T,DimensionedDataSource<U>>> fullList = new ArrayList<>();
		
		// type.bool

		for (DimensionedDataSource<?> ds : this.bools) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.BOOL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		// type.character
		
		for (DimensionedDataSource<?> ds : this.chars) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHAR, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.color

		for (DimensionedDataSource<?> ds : this.argbs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.ARGB, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cielabs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.LAB, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.rgbs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.RGB, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.complex.float128
		
		for (DimensionedDataSource<?> ds : this.cquads) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CQUAD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cquad_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CQUAD_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cquad_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CQUAD_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cquad_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CQUAD_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.complex.float16
		
		for (DimensionedDataSource<?> ds : this.chlfs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHLF, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.chlf_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHLF_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.chlf_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHLF_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.chlf_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHLF_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.complex.float32
		
		for (DimensionedDataSource<?> ds : this.cflts) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CFLT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cflt_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CFLT_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cflt_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CFLT_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cflt_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CFLT_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.complex.float64
		
		for (DimensionedDataSource<?> ds : this.cdbls) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CDBL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cdbl_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CDBL_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.cdbl_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CDBL_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		for (DimensionedDataSource<?> ds : this.cdbl_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CDBL_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.complex.highprec
		
		for (DimensionedDataSource<?> ds : this.chps) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHP, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.chp_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHP_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.chp_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHP_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		for (DimensionedDataSource<?> ds : this.chp_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.CHP_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.gaussian
		
		for (DimensionedDataSource<?> ds : this.gint8s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.GAUSS8, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.gint16s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.GAUSS16, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.gint32s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.GAUSS32, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.gint64s) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.GAUSS64, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.gintus) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.GAUSSU, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.integer
		
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

		for (DimensionedDataSource<?> ds : this.unbounds) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.UNBOUND, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		// type.octonion.float128
		
		for (DimensionedDataSource<?> ds : this.oquads) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OQUAD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oquad_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OQUAD_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oquad_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OQUAD_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oquad_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OQUAD_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.octonion.float16
		
		for (DimensionedDataSource<?> ds : this.ohlfs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHLF, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.ohlf_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHLF_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.ohlf_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHLF_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.ohlf_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHLF_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.octonion.float32
		
		for (DimensionedDataSource<?> ds : this.oflts) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OFLT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oflt_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OFLT_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oflt_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OFLT_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.oflt_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OFLT_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.octonion.float64
		
		for (DimensionedDataSource<?> ds : this.odbls) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.ODBL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.odbl_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.ODBL_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.odbl_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.ODBL_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.odbl_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.ODBL_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		// type.octonion.highprec

		for (DimensionedDataSource<?> ds : this.ohps) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHP, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.ohp_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHP_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.ohp_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHP_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.ohp_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.OHP_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.point
		
		for (DimensionedDataSource<?> ds : this.points) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.POINT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.quaternion.float128
		
		for (DimensionedDataSource<?> ds : this.qquads) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QQUAD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qquad_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QQUAD_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qquad_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QQUAD_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qquad_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QQUAD_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.quaternion.float16
		
		for (DimensionedDataSource<?> ds : this.qhlfs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHLF, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qhlf_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHLF_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qhlf_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHLF_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qhlf_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHLF_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.quaternion.float32
		
		for (DimensionedDataSource<?> ds : this.qflts) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QFLT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qflt_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QFLT_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qflt_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QFLT_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qflt_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QFLT_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.quaternion.float64
		
		for (DimensionedDataSource<?> ds : this.qdbls) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QDBL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qdbl_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QDBL_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qdbl_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QDBL_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qdbl_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QDBL_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.quaternion.highprec
		
		for (DimensionedDataSource<?> ds : this.qhps) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHP, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qhp_rmods) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHP_RMOD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qhp_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHP_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.qhp_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QHP_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.rational

		for (DimensionedDataSource<?> ds : this.rationals) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.RAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.real.float128
		
		for (DimensionedDataSource<?> ds : this.quads) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QUAD, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.quad_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QUAD_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.quad_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QUAD_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.quad_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.QUAD_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.real.float16
		
		for (DimensionedDataSource<?> ds : this.hlfs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HLF, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.hlf_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HLF_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.hlf_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HLF_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.hlf_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HLF_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.real.float32
		
		for (DimensionedDataSource<?> ds : this.flts) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.FLT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.flt_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.FLT_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.flt_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.FLT_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.flt_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.FLT_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		// type.real.float64
		
		for (DimensionedDataSource<?> ds : this.dbls) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.DBL, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.dbl_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.DBL_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.dbl_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.DBL_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.dbl_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.DBL_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		// type.real.highprec
		
		for (DimensionedDataSource<?> ds : this.hps) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HP, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.hp_vecs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HP_VEC, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.hp_mats) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HP_MAT, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.hp_tens) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.HP_TEN, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		// type.string
		
		for (DimensionedDataSource<?> ds : this.fstrs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.FSTRING, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}
		
		for (DimensionedDataSource<?> ds : this.strs) {
			Tuple2<T, DimensionedDataSource<U>> tuple =
					new Tuple2<T, DimensionedDataSource<U>>((T)G.STRING, (DimensionedDataSource<U>)ds);
			fullList.add(tuple);
		}

		return fullList;
	}
}
