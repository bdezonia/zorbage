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
package nom.bdezonia.zorbage.algebra;

import nom.bdezonia.zorbage.type.bool.BooleanAlgebra;
import nom.bdezonia.zorbage.type.character.CharAlgebra;
import nom.bdezonia.zorbage.type.color.ArgbAlgebra;
import nom.bdezonia.zorbage.type.color.CieLabAlgebra;
import nom.bdezonia.zorbage.type.color.RgbAlgebra;
import nom.bdezonia.zorbage.type.complex.float128.ComplexFloat128Algebra;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16Algebra;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16CartesianTensorProduct;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16Matrix;
import nom.bdezonia.zorbage.type.complex.float16.ComplexFloat16Vector;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Algebra;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32CartesianTensorProduct;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Matrix;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Vector;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Algebra;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64CartesianTensorProduct;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Matrix;
import nom.bdezonia.zorbage.type.complex.float64.ComplexFloat64Vector;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionCartesianTensorProduct;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionMatrix;
import nom.bdezonia.zorbage.type.complex.highprec.ComplexHighPrecisionVector;
import nom.bdezonia.zorbage.type.gaussian.int16.GaussianInt16Algebra;
import nom.bdezonia.zorbage.type.gaussian.int32.GaussianInt32Algebra;
import nom.bdezonia.zorbage.type.gaussian.int64.GaussianInt64Algebra;
import nom.bdezonia.zorbage.type.gaussian.int8.GaussianInt8Algebra;
import nom.bdezonia.zorbage.type.gaussian.unbounded.GaussianIntUnboundedAlgebra;
import nom.bdezonia.zorbage.type.integer.int1.SignedInt1Algebra;
import nom.bdezonia.zorbage.type.integer.int1.UnsignedInt1Algebra;
import nom.bdezonia.zorbage.type.integer.int10.SignedInt10Algebra;
import nom.bdezonia.zorbage.type.integer.int10.UnsignedInt10Algebra;
import nom.bdezonia.zorbage.type.integer.int11.SignedInt11Algebra;
import nom.bdezonia.zorbage.type.integer.int11.UnsignedInt11Algebra;
import nom.bdezonia.zorbage.type.integer.int12.SignedInt12Algebra;
import nom.bdezonia.zorbage.type.integer.int12.UnsignedInt12Algebra;
import nom.bdezonia.zorbage.type.integer.int128.SignedInt128Algebra;
import nom.bdezonia.zorbage.type.integer.int128.UnsignedInt128Algebra;
import nom.bdezonia.zorbage.type.integer.int13.SignedInt13Algebra;
import nom.bdezonia.zorbage.type.integer.int13.UnsignedInt13Algebra;
import nom.bdezonia.zorbage.type.integer.int14.SignedInt14Algebra;
import nom.bdezonia.zorbage.type.integer.int14.UnsignedInt14Algebra;
import nom.bdezonia.zorbage.type.integer.int15.SignedInt15Algebra;
import nom.bdezonia.zorbage.type.integer.int15.UnsignedInt15Algebra;
import nom.bdezonia.zorbage.type.integer.int16.SignedInt16Algebra;
import nom.bdezonia.zorbage.type.integer.int16.UnsignedInt16Algebra;
import nom.bdezonia.zorbage.type.integer.int2.SignedInt2Algebra;
import nom.bdezonia.zorbage.type.integer.int2.UnsignedInt2Algebra;
import nom.bdezonia.zorbage.type.integer.int3.SignedInt3Algebra;
import nom.bdezonia.zorbage.type.integer.int3.UnsignedInt3Algebra;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Algebra;
import nom.bdezonia.zorbage.type.integer.int32.UnsignedInt32Algebra;
import nom.bdezonia.zorbage.type.integer.int4.SignedInt4Algebra;
import nom.bdezonia.zorbage.type.integer.int4.UnsignedInt4Algebra;
import nom.bdezonia.zorbage.type.integer.int5.SignedInt5Algebra;
import nom.bdezonia.zorbage.type.integer.int5.UnsignedInt5Algebra;
import nom.bdezonia.zorbage.type.integer.int6.SignedInt6Algebra;
import nom.bdezonia.zorbage.type.integer.int6.UnsignedInt6Algebra;
import nom.bdezonia.zorbage.type.integer.int64.SignedInt64Algebra;
import nom.bdezonia.zorbage.type.integer.int64.UnsignedInt64Algebra;
import nom.bdezonia.zorbage.type.integer.int7.SignedInt7Algebra;
import nom.bdezonia.zorbage.type.integer.int7.UnsignedInt7Algebra;
import nom.bdezonia.zorbage.type.integer.int8.SignedInt8Algebra;
import nom.bdezonia.zorbage.type.integer.int8.UnsignedInt8Algebra;
import nom.bdezonia.zorbage.type.integer.int9.SignedInt9Algebra;
import nom.bdezonia.zorbage.type.integer.int9.UnsignedInt9Algebra;
import nom.bdezonia.zorbage.type.integer.unbounded.UnboundedIntAlgebra;
import nom.bdezonia.zorbage.type.octonion.float16.OctonionFloat16Algebra;
import nom.bdezonia.zorbage.type.octonion.float16.OctonionFloat16CartesianTensorProduct;
import nom.bdezonia.zorbage.type.octonion.float16.OctonionFloat16Matrix;
import nom.bdezonia.zorbage.type.octonion.float16.OctonionFloat16RModule;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32Algebra;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32CartesianTensorProduct;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32Matrix;
import nom.bdezonia.zorbage.type.octonion.float32.OctonionFloat32RModule;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64Algebra;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64CartesianTensorProduct;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64Matrix;
import nom.bdezonia.zorbage.type.octonion.float64.OctonionFloat64RModule;
import nom.bdezonia.zorbage.type.octonion.highprec.OctonionHighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.octonion.highprec.OctonionHighPrecisionCartesianTensorProduct;
import nom.bdezonia.zorbage.type.octonion.highprec.OctonionHighPrecisionMatrix;
import nom.bdezonia.zorbage.type.octonion.highprec.OctonionHighPrecisionRModule;
import nom.bdezonia.zorbage.type.point.PointAlgebra;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16Algebra;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16CartesianTensorProduct;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16Matrix;
import nom.bdezonia.zorbage.type.quaternion.float16.QuaternionFloat16RModule;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32Algebra;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32CartesianTensorProduct;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32Matrix;
import nom.bdezonia.zorbage.type.quaternion.float32.QuaternionFloat32RModule;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64Algebra;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64CartesianTensorProduct;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64Matrix;
import nom.bdezonia.zorbage.type.quaternion.float64.QuaternionFloat64RModule;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionCartesianTensorProduct;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionMatrix;
import nom.bdezonia.zorbage.type.quaternion.highprec.QuaternionHighPrecisionRModule;
import nom.bdezonia.zorbage.type.rational.RationalAlgebra;
import nom.bdezonia.zorbage.type.real.float128.Float128Algebra;
import nom.bdezonia.zorbage.type.real.float16.Float16Algebra;
import nom.bdezonia.zorbage.type.real.float16.Float16CartesianTensorProduct;
import nom.bdezonia.zorbage.type.real.float16.Float16Matrix;
import nom.bdezonia.zorbage.type.real.float16.Float16Vector;
import nom.bdezonia.zorbage.type.real.float32.Float32Algebra;
import nom.bdezonia.zorbage.type.real.float32.Float32CartesianTensorProduct;
import nom.bdezonia.zorbage.type.real.float32.Float32Matrix;
import nom.bdezonia.zorbage.type.real.float32.Float32Vector;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64CartesianTensorProduct;
import nom.bdezonia.zorbage.type.real.float64.Float64Matrix;
import nom.bdezonia.zorbage.type.real.float64.Float64Vector;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionCartesianTensorProduct;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMatrix;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionVector;
import nom.bdezonia.zorbage.type.string.FixedStringAlgebra;
import nom.bdezonia.zorbage.type.string.StringAlgebra;

/**
 * G is the container for the global set of defined Algebras.
 *
 * @author Barry DeZonia
 *
 */
public class G {

	private G() {}

	public static final UnboundedIntAlgebra UNBOUND = new UnboundedIntAlgebra();

	public static final CharAlgebra CHAR = new CharAlgebra();

	public static final FixedStringAlgebra FSTRING = new FixedStringAlgebra();
	
	public static final StringAlgebra STRING = new StringAlgebra();
	
	public static final RationalAlgebra RAT = new RationalAlgebra();

	public static final BooleanAlgebra BOOL = new BooleanAlgebra();

	public static final PointAlgebra POINT = new PointAlgebra();

	public static final ArgbAlgebra ARGB = new ArgbAlgebra();
	public static final RgbAlgebra RGB = new RgbAlgebra();
	public static final CieLabAlgebra LAB = new CieLabAlgebra();

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

	public static final Float128Algebra QUAD = new Float128Algebra();
	public static final ComplexFloat128Algebra CQUAD = new ComplexFloat128Algebra();

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

	public static final GaussianInt8Algebra GAUSS8 = new GaussianInt8Algebra();
	public static final GaussianInt16Algebra GAUSS16 = new GaussianInt16Algebra();
	public static final GaussianInt32Algebra GAUSS32 = new GaussianInt32Algebra();
	public static final GaussianInt64Algebra GAUSS64 = new GaussianInt64Algebra();
	public static final GaussianIntUnboundedAlgebra GAUSSU = new GaussianIntUnboundedAlgebra();
}
