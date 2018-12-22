/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2018 Barry DeZonia
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
package nom.bdezonia.zorbage.groups;

import nom.bdezonia.zorbage.type.data.bigint.UnboundedIntGroup;
import nom.bdezonia.zorbage.type.data.bool.BooleanGroup;
import nom.bdezonia.zorbage.type.data.float16.real.Float16Group;
import nom.bdezonia.zorbage.type.data.float32.real.Float32Group;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Group;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Matrix;
import nom.bdezonia.zorbage.type.data.float64.complex.ComplexFloat64Vector;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Group;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64Matrix;
import nom.bdezonia.zorbage.type.data.float64.octonion.OctonionFloat64RModule;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64Group;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64Matrix;
import nom.bdezonia.zorbage.type.data.float64.quaternion.QuaternionFloat64RModule;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Group;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Matrix;
import nom.bdezonia.zorbage.type.data.float64.real.Float64TensorProduct;
import nom.bdezonia.zorbage.type.data.float64.real.Float64Vector;
import nom.bdezonia.zorbage.type.data.int1.SignedInt1Group;
import nom.bdezonia.zorbage.type.data.int1.UnsignedInt1Group;
import nom.bdezonia.zorbage.type.data.int10.SignedInt10Group;
import nom.bdezonia.zorbage.type.data.int10.UnsignedInt10Group;
import nom.bdezonia.zorbage.type.data.int11.SignedInt11Group;
import nom.bdezonia.zorbage.type.data.int11.UnsignedInt11Group;
import nom.bdezonia.zorbage.type.data.int12.SignedInt12Group;
import nom.bdezonia.zorbage.type.data.int12.UnsignedInt12Group;
import nom.bdezonia.zorbage.type.data.int128.UnsignedInt128Group;
import nom.bdezonia.zorbage.type.data.int13.SignedInt13Group;
import nom.bdezonia.zorbage.type.data.int13.UnsignedInt13Group;
import nom.bdezonia.zorbage.type.data.int16.SignedInt16Group;
import nom.bdezonia.zorbage.type.data.int16.UnsignedInt16Group;
import nom.bdezonia.zorbage.type.data.int2.SignedInt2Group;
import nom.bdezonia.zorbage.type.data.int2.UnsignedInt2Group;
import nom.bdezonia.zorbage.type.data.int3.SignedInt3Group;
import nom.bdezonia.zorbage.type.data.int3.UnsignedInt3Group;
import nom.bdezonia.zorbage.type.data.int32.SignedInt32Group;
import nom.bdezonia.zorbage.type.data.int32.UnsignedInt32Group;
import nom.bdezonia.zorbage.type.data.int4.SignedInt4Group;
import nom.bdezonia.zorbage.type.data.int4.UnsignedInt4Group;
import nom.bdezonia.zorbage.type.data.int5.SignedInt5Group;
import nom.bdezonia.zorbage.type.data.int5.UnsignedInt5Group;
import nom.bdezonia.zorbage.type.data.int6.SignedInt6Group;
import nom.bdezonia.zorbage.type.data.int6.UnsignedInt6Group;
import nom.bdezonia.zorbage.type.data.int64.SignedInt64Group;
import nom.bdezonia.zorbage.type.data.int64.UnsignedInt64Group;
import nom.bdezonia.zorbage.type.data.int7.SignedInt7Group;
import nom.bdezonia.zorbage.type.data.int7.UnsignedInt7Group;
import nom.bdezonia.zorbage.type.data.int8.SignedInt8Group;
import nom.bdezonia.zorbage.type.data.int8.UnsignedInt8Group;
import nom.bdezonia.zorbage.type.data.int9.SignedInt9Group;
import nom.bdezonia.zorbage.type.data.int9.UnsignedInt9Group;

/**
 * G is the container for the global set of defined groups.
 * 
 * @author Barry DeZonia
 *
 */
public class G {
	
	private G() {}
	
	public static final UnboundedIntGroup BIGINT = new UnboundedIntGroup();
	
	public static final BooleanGroup BOOL = new BooleanGroup();
	
	public static final Float16Group HLF = new Float16Group();
	// TODO and num, vec, mat, ten for real, complex, quat, oct
	
	public static final Float32Group FLT = new Float32Group();
	// TODO and num, vec, mat, ten for real, complex, quat, oct
	
	public static final Float64Group DBL = new Float64Group();
	public static final Float64Vector DBL_VEC = new Float64Vector();
	public static final Float64Matrix DBL_MAT = new Float64Matrix();
	public static final Float64TensorProduct DBL_TEN = new Float64TensorProduct();
	
	public static final ComplexFloat64Group CDBL = new ComplexFloat64Group();
	public static final ComplexFloat64Vector CDBL_VEC = new ComplexFloat64Vector();
	public static final ComplexFloat64Matrix CDBL_MAT = new ComplexFloat64Matrix();
	//TODO public static final ComplexFloat64TensorProduct CDBL_TEN = new Float64TensorProduct();

	public static final QuaternionFloat64Group QDBL = new QuaternionFloat64Group();
	public static final QuaternionFloat64RModule QDBL_RMOD = new QuaternionFloat64RModule();
	public static final QuaternionFloat64Matrix QDBL_MAT = new QuaternionFloat64Matrix();
	//TODO public static final QuaternionFloat64TensorProduct QDBL_TEN = new QuaternionFloat64TensorProduct();

	public static final OctonionFloat64Group ODBL = new OctonionFloat64Group();
	public static final OctonionFloat64RModule ODBL_RMOD = new OctonionFloat64RModule();
	public static final OctonionFloat64Matrix ODBL_MAT = new OctonionFloat64Matrix();
	//TODO public static final OctonionFloat64TensorProduct ODBL_TEN = new OctonionFloat64TensorProduct();

	public static final UnsignedInt1Group UINT1 = new UnsignedInt1Group();
	public static final UnsignedInt2Group UINT2 = new UnsignedInt2Group();
	public static final UnsignedInt3Group UINT3 = new UnsignedInt3Group();
	public static final UnsignedInt4Group UINT4 = new UnsignedInt4Group();
	public static final UnsignedInt5Group UINT5 = new UnsignedInt5Group();
	public static final UnsignedInt6Group UINT6 = new UnsignedInt6Group();
	public static final UnsignedInt7Group UINT7 = new UnsignedInt7Group();
	public static final UnsignedInt8Group UINT8 = new UnsignedInt8Group();
	public static final UnsignedInt9Group UINT9 = new UnsignedInt9Group();
	public static final UnsignedInt10Group UINT10 = new UnsignedInt10Group();
	public static final UnsignedInt11Group UINT11 = new UnsignedInt11Group();
	public static final UnsignedInt12Group UINT12 = new UnsignedInt12Group();
	public static final UnsignedInt13Group UINT13 = new UnsignedInt13Group();
	public static final UnsignedInt16Group UINT16 = new UnsignedInt16Group();
	public static final UnsignedInt32Group UINT32 = new UnsignedInt32Group();
	public static final UnsignedInt64Group UINT64 = new UnsignedInt64Group();
	public static final UnsignedInt128Group UINT128 = new UnsignedInt128Group();

	public static final SignedInt1Group INT1 = new SignedInt1Group();
	public static final SignedInt2Group INT2 = new SignedInt2Group();
	public static final SignedInt3Group INT3 = new SignedInt3Group();
	public static final SignedInt4Group INT4 = new SignedInt4Group();
	public static final SignedInt5Group INT5 = new SignedInt5Group();
	public static final SignedInt6Group INT6 = new SignedInt6Group();
	public static final SignedInt7Group INT7 = new SignedInt7Group();
	public static final SignedInt8Group INT8 = new SignedInt8Group();
	public static final SignedInt9Group INT9 = new SignedInt9Group();
	public static final SignedInt10Group INT10 = new SignedInt10Group();
	public static final SignedInt11Group INT11 = new SignedInt11Group();
	public static final SignedInt12Group INT12 = new SignedInt12Group();
	public static final SignedInt13Group INT13 = new SignedInt13Group();
	public static final SignedInt16Group INT16 = new SignedInt16Group();
	public static final SignedInt32Group INT32 = new SignedInt32Group();
	public static final SignedInt64Group INT64 = new SignedInt64Group();
	//TODO public static final SignedInt128Group INT128 = new SignedInt128Group();
}
