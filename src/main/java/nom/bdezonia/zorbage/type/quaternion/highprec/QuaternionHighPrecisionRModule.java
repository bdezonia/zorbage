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
package nom.bdezonia.zorbage.type.quaternion.highprec;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.ExactType;
import nom.bdezonia.zorbage.algebra.type.markers.RModuleType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDefaultNorm;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByDouble;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByRational;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionHighPrecisionRModule
	implements
		RModule<QuaternionHighPrecisionRModule,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionAlgebra,QuaternionHighPrecisionMember>,
		Constructible1dLong<QuaternionHighPrecisionRModuleMember>,
		Norm<QuaternionHighPrecisionRModuleMember,HighPrecisionMember>,
		Products<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember>,
		DirectProduct<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMatrixMember>,
		ScaleByHighPrec<QuaternionHighPrecisionRModuleMember>,
		ScaleByRational<QuaternionHighPrecisionRModuleMember>,
		ScaleByDouble<QuaternionHighPrecisionRModuleMember>,
		ScaleByOneHalf<QuaternionHighPrecisionRModuleMember>,
		ScaleByTwo<QuaternionHighPrecisionRModuleMember>,
		Tolerance<HighPrecisionMember,QuaternionHighPrecisionRModuleMember>,
		ArrayLikeMethods<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember>,
		ConstructibleFromBytes<QuaternionHighPrecisionRModuleMember>,
		ConstructibleFromShorts<QuaternionHighPrecisionRModuleMember>,
		ConstructibleFromInts<QuaternionHighPrecisionRModuleMember>,
		ConstructibleFromLongs<QuaternionHighPrecisionRModuleMember>,
		ConstructibleFromFloats<QuaternionHighPrecisionRModuleMember>,
		ConstructibleFromDoubles<QuaternionHighPrecisionRModuleMember>,
		ConstructibleFromBigIntegers<QuaternionHighPrecisionRModuleMember>,
		ConstructibleFromBigDecimals<QuaternionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromBytes<QuaternionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromShorts<QuaternionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromInts<QuaternionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromLongs<QuaternionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromFloats<QuaternionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromDoubles<QuaternionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromBigIntegers<QuaternionHighPrecisionRModuleMember>,
		ExactlyConstructibleFromBigDecimals<QuaternionHighPrecisionRModuleMember>,
		CompositeType,
		ExactType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "Arbitrary precicion quaternion rmodule";
	}

	public QuaternionHighPrecisionRModule() { }

	@Override
	public QuaternionHighPrecisionRModuleMember construct() {
		return new QuaternionHighPrecisionRModuleMember();
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(QuaternionHighPrecisionRModuleMember other) {
		return new QuaternionHighPrecisionRModuleMember(other);
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(String s) {
		return new QuaternionHighPrecisionRModuleMember(s);
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(StorageConstruction s, long d1) {
		return new QuaternionHighPrecisionRModuleMember(s, d1);
	}

	@Override
	public QuaternionHighPrecisionRModuleMember constructExactly(byte... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}
	
	@Override
	public QuaternionHighPrecisionRModuleMember constructExactly(short... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}
	
	@Override
	public QuaternionHighPrecisionRModuleMember constructExactly(int... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}
	
	@Override
	public QuaternionHighPrecisionRModuleMember constructExactly(long... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromLongsExact(vals);
		return v;
	}
	
	@Override
	public QuaternionHighPrecisionRModuleMember constructExactly(float... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}
	
	@Override
	public QuaternionHighPrecisionRModuleMember constructExactly(double... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}
	
	@Override
	public QuaternionHighPrecisionRModuleMember constructExactly(BigInteger... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromBigIntegersExact(vals);
		return v;
	}
	
	@Override
	public QuaternionHighPrecisionRModuleMember constructExactly(BigDecimal... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromBigDecimalsExact(vals);
		return v;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(byte... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromBytes(vals);
		return v;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(short... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(int... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(long... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(float... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(double... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(BigInteger... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(BigDecimal... vals) {
		QuaternionHighPrecisionRModuleMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	private final Procedure1<QuaternionHighPrecisionRModuleMember> ZER = 
			new Procedure1<QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionRModuleMember> zero() {
		return ZER;
	}
	
	private final Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> NEG =
			new Procedure2<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			RModuleNegate.compute(G.QHP, a, b);
		}
	};

	@Override
	public Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> ADD =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c)
		{
			RModuleAdd.compute(G.QHP, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> SUB =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c)
		{
			RModuleSubtract.compute(G.QHP, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> EQ =
			new Function2<Boolean, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			return RModuleEqual.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> NEQ =
			new Function2<Boolean, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> ASSIGN = 
			new Procedure2<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember from, QuaternionHighPrecisionRModuleMember to) {
			RModuleAssign.compute(G.QHP, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionHighPrecisionRModuleMember,HighPrecisionMember> NORM =
			new Procedure2<QuaternionHighPrecisionRModuleMember, HighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, HighPrecisionMember b) {
			RModuleDefaultNorm.compute(G.QHP, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionRModuleMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> SCALE =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			RModuleScale.compute(G.QHP, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> CROSS =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c)
		{
			CrossProduct.compute(G.QHP_RMOD, G.QHP, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> DOT =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionMember c) {
			DotProduct.compute(G.QHP_RMOD, G.QHP, G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> dotProduct() {
		return DOT;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> PERP =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionMember c) {
			PerpDotProduct.compute(G.QHP_RMOD, G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> VTRIPLE =
			new Procedure4<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c, QuaternionHighPrecisionRModuleMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*4];
			Arrays.fill(arr, BigDecimal.ZERO);
			QuaternionHighPrecisionRModuleMember b_cross_c = new QuaternionHighPrecisionRModuleMember(arr);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> STRIPLE =
			new Procedure4<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c, QuaternionHighPrecisionMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*4];
			Arrays.fill(arr, BigDecimal.ZERO);
			QuaternionHighPrecisionRModuleMember b_cross_c = new QuaternionHighPrecisionRModuleMember(arr);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> CONJ =
			new Procedure2<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			RModuleConjugate.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMatrixMember> VDP =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember in1, QuaternionHighPrecisionRModuleMember in2,
				QuaternionHighPrecisionMatrixMember out)
		{
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMatrixMember> DP =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember in1, QuaternionHighPrecisionRModuleMember in2,
				QuaternionHighPrecisionMatrixMember out)
		{
			RModuleDirectProduct.compute(G.QHP, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, QuaternionHighPrecisionRModuleMember> ISZERO =
			new Function1<Boolean, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionRModuleMember a) {
			return SequenceIsZero.compute(G.QHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionHighPrecisionRModuleMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionRModuleMember c) {
			RModuleScaleByHighPrec.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SBR =
			new Procedure3<RationalMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionRModuleMember c) {
			RModuleScaleByRational.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SBD =
			new Procedure3<Double, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(Double a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionRModuleMember c) {
			RModuleScaleByDouble.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			return SequencesSimilar.compute(G.QHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> within() {
		return WITHIN;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> ADDS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QHP, G.QHP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SUBS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QHP, G.QHP.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> MULS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QHP, G.QHP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> DIVS =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QHP, G.QHP.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> MULTELEM =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionRModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.QHP, G.QHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> DIVELEM =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionRModuleMember c) {
			c.alloc(a.length());
			Transform3.compute(G.QHP, G.QHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SCB2 =
			new Procedure3<Integer, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			ScaleHelper.compute(G.QHP_RMOD, G.QHP, new QuaternionHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SCBH =
			new Procedure3<Integer, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(Integer numTimes, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			ScaleHelper.compute(G.QHP_RMOD, G.QHP, new QuaternionHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> scaleByOneHalf() {
		return SCBH;
	}

}
