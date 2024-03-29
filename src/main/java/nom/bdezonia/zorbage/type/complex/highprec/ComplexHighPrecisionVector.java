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
package nom.bdezonia.zorbage.type.complex.highprec;

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
public class ComplexHighPrecisionVector
	implements
		VectorSpace<ComplexHighPrecisionVector,ComplexHighPrecisionVectorMember,ComplexHighPrecisionAlgebra,ComplexHighPrecisionMember>,
		Constructible1dLong<ComplexHighPrecisionVectorMember>,
		Norm<ComplexHighPrecisionVectorMember,HighPrecisionMember>,
		Products<ComplexHighPrecisionVectorMember, ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember>,
		DirectProduct<ComplexHighPrecisionVectorMember, ComplexHighPrecisionMatrixMember>,
		ScaleByHighPrec<ComplexHighPrecisionVectorMember>,
		ScaleByRational<ComplexHighPrecisionVectorMember>,
		ScaleByDouble<ComplexHighPrecisionVectorMember>,
		ScaleByOneHalf<ComplexHighPrecisionVectorMember>,
		ScaleByTwo<ComplexHighPrecisionVectorMember>,
		Tolerance<HighPrecisionMember,ComplexHighPrecisionVectorMember>,
		ArrayLikeMethods<ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember>,
		ConstructibleFromBytes<ComplexHighPrecisionVectorMember>,
		ConstructibleFromShorts<ComplexHighPrecisionVectorMember>,
		ConstructibleFromInts<ComplexHighPrecisionVectorMember>,
		ConstructibleFromLongs<ComplexHighPrecisionVectorMember>,
		ConstructibleFromFloats<ComplexHighPrecisionVectorMember>,
		ConstructibleFromDoubles<ComplexHighPrecisionVectorMember>,
		ConstructibleFromBigIntegers<ComplexHighPrecisionVectorMember>,
		ConstructibleFromBigDecimals<ComplexHighPrecisionVectorMember>,
		ExactlyConstructibleFromBytes<ComplexHighPrecisionVectorMember>,
		ExactlyConstructibleFromShorts<ComplexHighPrecisionVectorMember>,
		ExactlyConstructibleFromInts<ComplexHighPrecisionVectorMember>,
		ExactlyConstructibleFromLongs<ComplexHighPrecisionVectorMember>,
		ExactlyConstructibleFromFloats<ComplexHighPrecisionVectorMember>,
		ExactlyConstructibleFromDoubles<ComplexHighPrecisionVectorMember>,
		ExactlyConstructibleFromBigIntegers<ComplexHighPrecisionVectorMember>,
		ExactlyConstructibleFromBigDecimals<ComplexHighPrecisionVectorMember>,
		CompositeType,
		ExactType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "Arbitrary precision complex vector";
	}

	public ComplexHighPrecisionVector() { }

	@Override
	public ComplexHighPrecisionVectorMember construct() {
		return new ComplexHighPrecisionVectorMember();
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(ComplexHighPrecisionVectorMember other) {
		return new ComplexHighPrecisionVectorMember(other);
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(String s) {
		return new ComplexHighPrecisionVectorMember(s);
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(StorageConstruction s, long d1) {
		return new ComplexHighPrecisionVectorMember(s, d1);
	}

	@Override
	public ComplexHighPrecisionVectorMember constructExactly(byte... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember constructExactly(short... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember constructExactly(int... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember constructExactly(long... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromLongsExact(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember constructExactly(float... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember constructExactly(double... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember constructExactly(BigInteger... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromBigIntegersExact(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember constructExactly(BigDecimal... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromBigDecimalsExact(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(byte... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromBytes(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(short... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(int... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(long... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(float... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(double... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(BigInteger... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(BigDecimal... vals) {
		ComplexHighPrecisionVectorMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}
	
	private final Procedure1<ComplexHighPrecisionVectorMember> ZER =
			new Procedure1<ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionVectorMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> NEG =
			new Procedure2<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			RModuleNegate.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> ADD =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			RModuleAdd.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> SUB =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			RModuleSubtract.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> EQ =
			new Function2<Boolean, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			return RModuleEqual.compute(G.CHP, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> NEQ =
			new Function2<Boolean, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> ASSIGN =
			new Procedure2<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember from, ComplexHighPrecisionVectorMember to) {
			RModuleAssign.compute(G.CHP, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexHighPrecisionVectorMember,HighPrecisionMember> NORM =
			new Procedure2<ComplexHighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, HighPrecisionMember b) {
			RModuleDefaultNorm.compute(G.CHP, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionVectorMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> SCALE =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			RModuleScale.compute(G.CHP, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> CROSS =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			CrossProduct.compute(G.CHP_VEC, G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> DOT =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionMember c) {
			DotProduct.compute(G.CHP_VEC, G.CHP, G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> dotProduct() {
		return DOT;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> PERP =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionMember c) {
			PerpDotProduct.compute(G.CHP_VEC, G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> VTRIPLE =
			new Procedure4<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c,
				ComplexHighPrecisionVectorMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*2];
			Arrays.fill(arr, BigDecimal.ZERO);
			ComplexHighPrecisionVectorMember b_cross_c = new ComplexHighPrecisionVectorMember(arr);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> STRIPLE =
			new Procedure4<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c,
				ComplexHighPrecisionMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*2];
			Arrays.fill(arr, BigDecimal.ZERO);
			ComplexHighPrecisionVectorMember b_cross_c = new ComplexHighPrecisionVectorMember(arr);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> CONJ =
			new Procedure2<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			RModuleConjugate.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMatrixMember> VDP =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember in1, ComplexHighPrecisionVectorMember in2, ComplexHighPrecisionMatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMatrixMember> DP =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember in1, ComplexHighPrecisionVectorMember in2, ComplexHighPrecisionMatrixMember out) {
			RModuleDirectProduct.compute(G.CHP, in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, ComplexHighPrecisionVectorMember> ISZERO =
			new Function1<Boolean, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionVectorMember a) {
			return SequenceIsZero.compute(G.CHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexHighPrecisionVectorMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			RModuleScaleByHighPrec.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> SBR =
			new Procedure3<RationalMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(RationalMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			RModuleScaleByRational.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> SBD =
			new Procedure3<Double, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(Double a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			RModuleScaleByDouble.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			return SequencesSimilar.compute(G.CHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> ADDS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CHP, G.CHP.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> SUBS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CHP, G.CHP.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> MULS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CHP, G.CHP.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> DIVS =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CHP, G.CHP.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> MULTELEM =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.CHP, G.CHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> DIVELEM =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.CHP, G.CHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> SCB2 =
			new Procedure3<Integer, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			ScaleHelper.compute(G.CHP_VEC, G.CHP, new ComplexHighPrecisionMember(BigDecimal.valueOf(2), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> SCBH =
			new Procedure3<Integer, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			ScaleHelper.compute(G.CHP_VEC, G.CHP, new ComplexHighPrecisionMember(BigDecimal.valueOf(0.5), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> scaleByOneHalf() {
		return SCBH;
	}

}
