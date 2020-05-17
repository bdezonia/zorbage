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
package nom.bdezonia.zorbage.algebra;

import nom.bdezonia.zorbage.type.bool.BooleanAlgebra;
import nom.bdezonia.zorbage.type.float16.complex.ComplexFloat16Algebra;
import nom.bdezonia.zorbage.type.float16.complex.ComplexFloat16CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float16.complex.ComplexFloat16Matrix;
import nom.bdezonia.zorbage.type.float16.complex.ComplexFloat16Vector;
import nom.bdezonia.zorbage.type.float16.octonion.OctonionFloat16Algebra;
import nom.bdezonia.zorbage.type.float16.octonion.OctonionFloat16CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float16.octonion.OctonionFloat16Matrix;
import nom.bdezonia.zorbage.type.float16.octonion.OctonionFloat16RModule;
import nom.bdezonia.zorbage.type.float16.quaternion.QuaternionFloat16Algebra;
import nom.bdezonia.zorbage.type.float16.quaternion.QuaternionFloat16CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float16.quaternion.QuaternionFloat16Matrix;
import nom.bdezonia.zorbage.type.float16.quaternion.QuaternionFloat16RModule;
import nom.bdezonia.zorbage.type.float16.real.Float16Algebra;
import nom.bdezonia.zorbage.type.float16.real.Float16CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float16.real.Float16Matrix;
import nom.bdezonia.zorbage.type.float16.real.Float16Vector;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32Algebra;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32Matrix;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32Vector;
import nom.bdezonia.zorbage.type.float32.octonion.OctonionFloat32Algebra;
import nom.bdezonia.zorbage.type.float32.octonion.OctonionFloat32CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float32.octonion.OctonionFloat32Matrix;
import nom.bdezonia.zorbage.type.float32.octonion.OctonionFloat32RModule;
import nom.bdezonia.zorbage.type.float32.quaternion.QuaternionFloat32Algebra;
import nom.bdezonia.zorbage.type.float32.quaternion.QuaternionFloat32CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float32.quaternion.QuaternionFloat32Matrix;
import nom.bdezonia.zorbage.type.float32.quaternion.QuaternionFloat32RModule;
import nom.bdezonia.zorbage.type.float32.real.Float32Algebra;
import nom.bdezonia.zorbage.type.float32.real.Float32CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float32.real.Float32Matrix;
import nom.bdezonia.zorbage.type.float32.real.Float32Vector;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Algebra;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Matrix;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Vector;
import nom.bdezonia.zorbage.type.float64.octonion.OctonionFloat64Algebra;
import nom.bdezonia.zorbage.type.float64.octonion.OctonionFloat64CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float64.octonion.OctonionFloat64Matrix;
import nom.bdezonia.zorbage.type.float64.octonion.OctonionFloat64RModule;
import nom.bdezonia.zorbage.type.float64.quaternion.QuaternionFloat64Algebra;
import nom.bdezonia.zorbage.type.float64.quaternion.QuaternionFloat64CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float64.quaternion.QuaternionFloat64Matrix;
import nom.bdezonia.zorbage.type.float64.quaternion.QuaternionFloat64RModule;
import nom.bdezonia.zorbage.type.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.float64.real.Float64CartesianTensorProduct;
import nom.bdezonia.zorbage.type.float64.real.Float64Matrix;
import nom.bdezonia.zorbage.type.float64.real.Float64Vector;
import nom.bdezonia.zorbage.type.highprec.complex.ComplexHighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.complex.ComplexHighPrecisionCartesianTensorProduct;
import nom.bdezonia.zorbage.type.highprec.complex.ComplexHighPrecisionMatrix;
import nom.bdezonia.zorbage.type.highprec.complex.ComplexHighPrecisionVector;
import nom.bdezonia.zorbage.type.highprec.octonion.OctonionHighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.octonion.OctonionHighPrecisionCartesianTensorProduct;
import nom.bdezonia.zorbage.type.highprec.octonion.OctonionHighPrecisionMatrix;
import nom.bdezonia.zorbage.type.highprec.octonion.OctonionHighPrecisionRModule;
import nom.bdezonia.zorbage.type.highprec.quaternion.QuaternionHighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.quaternion.QuaternionHighPrecisionCartesianTensorProduct;
import nom.bdezonia.zorbage.type.highprec.quaternion.QuaternionHighPrecisionMatrix;
import nom.bdezonia.zorbage.type.highprec.quaternion.QuaternionHighPrecisionRModule;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionCartesianTensorProduct;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMatrix;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionVector;
import nom.bdezonia.zorbage.type.int1.SignedInt1Algebra;
import nom.bdezonia.zorbage.type.int1.UnsignedInt1Algebra;
import nom.bdezonia.zorbage.type.int10.SignedInt10Algebra;
import nom.bdezonia.zorbage.type.int10.UnsignedInt10Algebra;
import nom.bdezonia.zorbage.type.int11.SignedInt11Algebra;
import nom.bdezonia.zorbage.type.int11.UnsignedInt11Algebra;
import nom.bdezonia.zorbage.type.int12.SignedInt12Algebra;
import nom.bdezonia.zorbage.type.int12.UnsignedInt12Algebra;
import nom.bdezonia.zorbage.type.int128.SignedInt128Algebra;
import nom.bdezonia.zorbage.type.int128.UnsignedInt128Algebra;
import nom.bdezonia.zorbage.type.int13.SignedInt13Algebra;
import nom.bdezonia.zorbage.type.int13.UnsignedInt13Algebra;
import nom.bdezonia.zorbage.type.int14.SignedInt14Algebra;
import nom.bdezonia.zorbage.type.int14.UnsignedInt14Algebra;
import nom.bdezonia.zorbage.type.int15.SignedInt15Algebra;
import nom.bdezonia.zorbage.type.int15.UnsignedInt15Algebra;
import nom.bdezonia.zorbage.type.int16.SignedInt16Algebra;
import nom.bdezonia.zorbage.type.int16.UnsignedInt16Algebra;
import nom.bdezonia.zorbage.type.int2.SignedInt2Algebra;
import nom.bdezonia.zorbage.type.int2.UnsignedInt2Algebra;
import nom.bdezonia.zorbage.type.int3.SignedInt3Algebra;
import nom.bdezonia.zorbage.type.int3.UnsignedInt3Algebra;
import nom.bdezonia.zorbage.type.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.int32.UnsignedInt32Algebra;
import nom.bdezonia.zorbage.type.int4.SignedInt4Algebra;
import nom.bdezonia.zorbage.type.int4.UnsignedInt4Algebra;
import nom.bdezonia.zorbage.type.int5.SignedInt5Algebra;
import nom.bdezonia.zorbage.type.int5.UnsignedInt5Algebra;
import nom.bdezonia.zorbage.type.int6.SignedInt6Algebra;
import nom.bdezonia.zorbage.type.int6.UnsignedInt6Algebra;
import nom.bdezonia.zorbage.type.int64.SignedInt64Algebra;
import nom.bdezonia.zorbage.type.int64.UnsignedInt64Algebra;
import nom.bdezonia.zorbage.type.int7.SignedInt7Algebra;
import nom.bdezonia.zorbage.type.int7.UnsignedInt7Algebra;
import nom.bdezonia.zorbage.type.int8.SignedInt8Algebra;
import nom.bdezonia.zorbage.type.int8.UnsignedInt8Algebra;
import nom.bdezonia.zorbage.type.int9.SignedInt9Algebra;
import nom.bdezonia.zorbage.type.int9.UnsignedInt9Algebra;
import nom.bdezonia.zorbage.type.point.PointAlgebra;
import nom.bdezonia.zorbage.type.rational.RationalAlgebra;
import nom.bdezonia.zorbage.type.rgb.ArgbAlgebra;
import nom.bdezonia.zorbage.type.rgb.RgbAlgebra;
import nom.bdezonia.zorbage.type.unbounded.UnboundedIntAlgebra;

import java.math.BigDecimal;

/**
 * G is the container for the global set of defined Algebras.
 *
 * @author Barry DeZonia
 *
 */
public class G {

	private G() {}

	public static final BigDecimal ONE_HALF = BigDecimal.valueOf(0.5);

	public static final UnboundedIntAlgebra UNBOUND = new UnboundedIntAlgebra();

	public static final RationalAlgebra RAT = new RationalAlgebra();

	public static final BooleanAlgebra BOOL = new BooleanAlgebra();

	public static final PointAlgebra POINT = new PointAlgebra();

	public static final ArgbAlgebra ARGB = new ArgbAlgebra();
	public static final RgbAlgebra RGB = new RgbAlgebra();

	public static final Float16Algebra HLF = new Float16Algebra();
	public static final Float16Vector HLF_VEC = new Float16Vector();
	public static final Float16Matrix HLF_MAT = new Float16Matrix();
	public static final Float16CartesianTensorProduct HLF_TEN = new Float16CartesianTensorProduct();

	public static final ComplexFloat16Algebra CHLF = new ComplexFloat16Algebra();
	public static final ComplexFloat16Vector CHLF_VEC = new ComplexFloat16Vector();
	public static final ComplexFloat16Matrix CHLF_MAT = new ComplexFloat16Matrix();
	public static final ComplexFloat16CartesianTensorProduct CHLF_TEN = new ComplexFloat16CartesianTensorProduct();

	public static final QuaternionFloat16Algebra QHLF = new QuaternionFloat16Algebra();
	public static final QuaternionFloat16RModule QHLF_RMOD = new QuaternionFloat16RModule();
	public static final QuaternionFloat16Matrix QHLF_MAT = new QuaternionFloat16Matrix();
	public static final QuaternionFloat16CartesianTensorProduct QHLF_TEN = new QuaternionFloat16CartesianTensorProduct();

	public static final OctonionFloat16Algebra OHLF = new OctonionFloat16Algebra();
	public static final OctonionFloat16RModule OHLF_RMOD = new OctonionFloat16RModule();
	public static final OctonionFloat16Matrix OHLF_MAT = new OctonionFloat16Matrix();
	public static final OctonionFloat16CartesianTensorProduct OHLF_TEN = new OctonionFloat16CartesianTensorProduct();

	public static final Float32Algebra FLT = new Float32Algebra();
	public static final Float32Vector FLT_VEC = new Float32Vector();
	public static final Float32Matrix FLT_MAT = new Float32Matrix();
	public static final Float32CartesianTensorProduct FLT_TEN = new Float32CartesianTensorProduct();

	public static final ComplexFloat32Algebra CFLT = new ComplexFloat32Algebra();
	public static final ComplexFloat32Vector CFLT_VEC = new ComplexFloat32Vector();
	public static final ComplexFloat32Matrix CFLT_MAT = new ComplexFloat32Matrix();
	public static final ComplexFloat32CartesianTensorProduct CFLT_TEN = new ComplexFloat32CartesianTensorProduct();

	public static final QuaternionFloat32Algebra QFLT = new QuaternionFloat32Algebra();
	public static final QuaternionFloat32RModule QFLT_RMOD = new QuaternionFloat32RModule();
	public static final QuaternionFloat32Matrix QFLT_MAT = new QuaternionFloat32Matrix();
	public static final QuaternionFloat32CartesianTensorProduct QFLT_TEN = new QuaternionFloat32CartesianTensorProduct();

	public static final OctonionFloat32Algebra OFLT = new OctonionFloat32Algebra();
	public static final OctonionFloat32RModule OFLT_RMOD = new OctonionFloat32RModule();
	public static final OctonionFloat32Matrix OFLT_MAT = new OctonionFloat32Matrix();
	public static final OctonionFloat32CartesianTensorProduct OFLT_TEN = new OctonionFloat32CartesianTensorProduct();

	public static final Float64Algebra DBL = new Float64Algebra();
	public static final Float64Vector DBL_VEC = new Float64Vector();
	public static final Float64Matrix DBL_MAT = new Float64Matrix();
	public static final Float64CartesianTensorProduct DBL_TEN = new Float64CartesianTensorProduct();

	public static final ComplexFloat64Algebra CDBL = new ComplexFloat64Algebra();
	public static final ComplexFloat64Vector CDBL_VEC = new ComplexFloat64Vector();
	public static final ComplexFloat64Matrix CDBL_MAT = new ComplexFloat64Matrix();
	public static final ComplexFloat64CartesianTensorProduct CDBL_TEN = new ComplexFloat64CartesianTensorProduct();

	public static final QuaternionFloat64Algebra QDBL = new QuaternionFloat64Algebra();
	public static final QuaternionFloat64RModule QDBL_RMOD = new QuaternionFloat64RModule();
	public static final QuaternionFloat64Matrix QDBL_MAT = new QuaternionFloat64Matrix();
	public static final QuaternionFloat64CartesianTensorProduct QDBL_TEN = new QuaternionFloat64CartesianTensorProduct();

	public static final OctonionFloat64Algebra ODBL = new OctonionFloat64Algebra();
	public static final OctonionFloat64RModule ODBL_RMOD = new OctonionFloat64RModule();
	public static final OctonionFloat64Matrix ODBL_MAT = new OctonionFloat64Matrix();
	public static final OctonionFloat64CartesianTensorProduct ODBL_TEN = new OctonionFloat64CartesianTensorProduct();

	public static final HighPrecisionAlgebra HP = new HighPrecisionAlgebra();
	public static final HighPrecisionVector HP_VEC = new HighPrecisionVector();
	public static final HighPrecisionMatrix HP_MAT = new HighPrecisionMatrix();
	public static final HighPrecisionCartesianTensorProduct HP_TEN = new HighPrecisionCartesianTensorProduct();

	public static final ComplexHighPrecisionAlgebra CHP = new ComplexHighPrecisionAlgebra();
	public static final ComplexHighPrecisionVector CHP_VEC = new ComplexHighPrecisionVector();
	public static final ComplexHighPrecisionMatrix CHP_MAT = new ComplexHighPrecisionMatrix();
	public static final ComplexHighPrecisionCartesianTensorProduct CHP_TEN = new ComplexHighPrecisionCartesianTensorProduct();

	public static final QuaternionHighPrecisionAlgebra QHP = new QuaternionHighPrecisionAlgebra();
	public static final QuaternionHighPrecisionRModule QHP_RMOD = new QuaternionHighPrecisionRModule();
	public static final QuaternionHighPrecisionMatrix QHP_MAT = new QuaternionHighPrecisionMatrix();
	public static final QuaternionHighPrecisionCartesianTensorProduct QHP_TEN = new QuaternionHighPrecisionCartesianTensorProduct();

	public static final OctonionHighPrecisionAlgebra OHP = new OctonionHighPrecisionAlgebra();
	public static final OctonionHighPrecisionRModule OHP_RMOD = new OctonionHighPrecisionRModule();
	public static final OctonionHighPrecisionMatrix OHP_MAT = new OctonionHighPrecisionMatrix();
	public static final OctonionHighPrecisionCartesianTensorProduct OHP_TEN = new OctonionHighPrecisionCartesianTensorProduct();

	public static final UnsignedInt1Algebra UINT1 = new UnsignedInt1Algebra();
	public static final UnsignedInt2Algebra UINT2 = new UnsignedInt2Algebra();
	public static final UnsignedInt3Algebra UINT3 = new UnsignedInt3Algebra();
	public static final UnsignedInt4Algebra UINT4 = new UnsignedInt4Algebra();
	public static final UnsignedInt5Algebra UINT5 = new UnsignedInt5Algebra();
	public static final UnsignedInt6Algebra UINT6 = new UnsignedInt6Algebra();
	public static final UnsignedInt7Algebra UINT7 = new UnsignedInt7Algebra();
	public static final UnsignedInt8Algebra UINT8 = new UnsignedInt8Algebra();
	public static final UnsignedInt9Algebra UINT9 = new UnsignedInt9Algebra();
	public static final UnsignedInt10Algebra UINT10 = new UnsignedInt10Algebra();
	public static final UnsignedInt11Algebra UINT11 = new UnsignedInt11Algebra();
	public static final UnsignedInt12Algebra UINT12 = new UnsignedInt12Algebra();
	public static final UnsignedInt13Algebra UINT13 = new UnsignedInt13Algebra();
	public static final UnsignedInt14Algebra UINT14 = new UnsignedInt14Algebra();
	public static final UnsignedInt15Algebra UINT15 = new UnsignedInt15Algebra();
	public static final UnsignedInt16Algebra UINT16 = new UnsignedInt16Algebra();
	public static final UnsignedInt32Algebra UINT32 = new UnsignedInt32Algebra();
	public static final UnsignedInt64Algebra UINT64 = new UnsignedInt64Algebra();
	public static final UnsignedInt128Algebra UINT128 = new UnsignedInt128Algebra();

	public static final SignedInt1Algebra INT1 = new SignedInt1Algebra();
	public static final SignedInt2Algebra INT2 = new SignedInt2Algebra();
	public static final SignedInt3Algebra INT3 = new SignedInt3Algebra();
	public static final SignedInt4Algebra INT4 = new SignedInt4Algebra();
	public static final SignedInt5Algebra INT5 = new SignedInt5Algebra();
	public static final SignedInt6Algebra INT6 = new SignedInt6Algebra();
	public static final SignedInt7Algebra INT7 = new SignedInt7Algebra();
	public static final SignedInt8Algebra INT8 = new SignedInt8Algebra();
	public static final SignedInt9Algebra INT9 = new SignedInt9Algebra();
	public static final SignedInt10Algebra INT10 = new SignedInt10Algebra();
	public static final SignedInt11Algebra INT11 = new SignedInt11Algebra();
	public static final SignedInt12Algebra INT12 = new SignedInt12Algebra();
	public static final SignedInt13Algebra INT13 = new SignedInt13Algebra();
	public static final SignedInt14Algebra INT14 = new SignedInt14Algebra();
	public static final SignedInt15Algebra INT15 = new SignedInt15Algebra();
	public static final SignedInt16Algebra INT16 = new SignedInt16Algebra();
	public static final SignedInt32Algebra INT32 = new SignedInt32Algebra();
	public static final SignedInt64Algebra INT64 = new SignedInt64Algebra();
	public static final SignedInt128Algebra INT128 = new SignedInt128Algebra();
}