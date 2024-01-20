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
package nom.bdezonia.zorbage.type.real.float16;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algebra.type.markers.ApproximateType;
import nom.bdezonia.zorbage.algebra.type.markers.CompositeType;
import nom.bdezonia.zorbage.algebra.type.markers.InfinityIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.NanIncludedType;
import nom.bdezonia.zorbage.algebra.type.markers.RModuleType;
import nom.bdezonia.zorbage.algebra.type.markers.SignedType;
import nom.bdezonia.zorbage.algebra.type.markers.ZeroIncludedType;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleDefaultNorm;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleRound;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByDouble;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByRational;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
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
public class Float16Vector
	implements
		VectorSpace<Float16Vector,Float16VectorMember,Float16Algebra,Float16Member>,
		Constructible1dLong<Float16VectorMember>,
		ConstructibleFromBytes<Float16VectorMember>,
		ConstructibleFromShorts<Float16VectorMember>,
		ConstructibleFromInts<Float16VectorMember>,
		ConstructibleFromLongs<Float16VectorMember>,
		ConstructibleFromFloats<Float16VectorMember>,
		ConstructibleFromDoubles<Float16VectorMember>,
		ConstructibleFromBigIntegers<Float16VectorMember>,
		ConstructibleFromBigDecimals<Float16VectorMember>,
		ExactlyConstructibleFromBytes<Float16VectorMember>,
		Norm<Float16VectorMember,Float16Member>,
		Products<Float16VectorMember, Float16Member, Float16MatrixMember>,
		DirectProduct<Float16VectorMember, Float16MatrixMember>,
		Rounding<Float16Member,Float16VectorMember>, Infinite<Float16VectorMember>,
		NaN<Float16VectorMember>,
		ScaleByHighPrec<Float16VectorMember>,
		ScaleByRational<Float16VectorMember>,
		ScaleByDouble<Float16VectorMember>,
		ScaleByOneHalf<Float16VectorMember>,
		ScaleByTwo<Float16VectorMember>,
		Tolerance<Float16Member,Float16VectorMember>,
		ArrayLikeMethods<Float16VectorMember,Float16Member>,
		ApproximateType,
		CompositeType,
		InfinityIncludedType,
		NanIncludedType,
		RModuleType,
		SignedType,
		ZeroIncludedType
{
	@Override
	public String typeDescription() {
		return "16-bit based real vector";
	}

	public Float16Vector() { }
	
	private final Procedure1<Float16VectorMember> ZER =
			new Procedure1<Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float16VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<Float16VectorMember,Float16VectorMember> NEG =
			new Procedure2<Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b) {
			RModuleNegate.compute(G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<Float16VectorMember,Float16VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> ADD =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c) {
			RModuleAdd.compute(G.HLF, a, b, c);
		}
	};

	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> add() {
		return ADD;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> SUB =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c) {
			RModuleSubtract.compute(G.HLF, a, b, c);
		}
	};

	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float16VectorMember,Float16VectorMember> EQ =
			new Function2<Boolean, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a, Float16VectorMember b) {
			return RModuleEqual.compute(G.HLF, a, b);
		}
	};

	@Override
	public Function2<Boolean,Float16VectorMember,Float16VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float16VectorMember,Float16VectorMember> NEQ =
			new Function2<Boolean, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a, Float16VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,Float16VectorMember,Float16VectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public Float16VectorMember construct() {
		return new Float16VectorMember();
	}

	@Override
	public Float16VectorMember construct(Float16VectorMember other) {
		return new Float16VectorMember(other);
	}

	@Override
	public Float16VectorMember construct(String s) {
		return new Float16VectorMember(s);
	}

	@Override
	public Float16VectorMember construct(StorageConstruction s, long d1) {
		return new Float16VectorMember(s, d1);
	}

	@Override
	public Float16VectorMember constructExactly(byte...vals) {
		Float16VectorMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public Float16VectorMember construct(byte...vals) {
		Float16VectorMember v = construct();
		v.setFromBytes(vals);
		return v;
	}

	@Override
	public Float16VectorMember construct(short...vals) {
		Float16VectorMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public Float16VectorMember construct(int...vals) {
		Float16VectorMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public Float16VectorMember construct(long...vals) {
		Float16VectorMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public Float16VectorMember construct(float...vals) {
		Float16VectorMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public Float16VectorMember construct(double...vals) {
		Float16VectorMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public Float16VectorMember construct(BigInteger... vals) {
		Float16VectorMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public Float16VectorMember construct(BigDecimal... vals) {
		Float16VectorMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	private final Procedure2<Float16VectorMember,Float16VectorMember> ASSIGN =
			new Procedure2<Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember from, Float16VectorMember to) {
			RModuleAssign.compute(G.HLF, from, to);
		}
	};
	
	@Override
	public Procedure2<Float16VectorMember,Float16VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<Float16VectorMember,Float16Member> NORM =
			new Procedure2<Float16VectorMember, Float16Member>()
	{
		@Override
		public void call(Float16VectorMember a, Float16Member b) {
			RModuleDefaultNorm.compute(G.HLF, G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<Float16VectorMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure3<Float16Member,Float16VectorMember,Float16VectorMember> SCALE =
			new Procedure3<Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16VectorMember a, Float16VectorMember b) {
			RModuleScale.compute(G.HLF, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<Float16Member,Float16VectorMember,Float16VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> CROSS =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c) {
			CrossProduct.compute(G.HLF_VEC, G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16Member> DOT =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16Member>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16Member c) {
			DotProduct.compute(G.HLF_VEC, G.HLF, G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16Member> PERP =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16Member>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16Member c) {
			PerpDotProduct.compute(G.HLF_VEC, G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<Float16VectorMember,Float16VectorMember,Float16VectorMember,Float16VectorMember> VTRIPLE =
			new Procedure4<Float16VectorMember, Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c, Float16VectorMember d) {
			Float16VectorMember b_cross_c = new Float16VectorMember(new float[3]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float16VectorMember,Float16VectorMember,Float16VectorMember,Float16VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<Float16VectorMember,Float16VectorMember,Float16VectorMember,Float16Member> STRIPLE =
			new Procedure4<Float16VectorMember, Float16VectorMember, Float16VectorMember, Float16Member>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c, Float16Member d) {
			Float16VectorMember b_cross_c = new Float16VectorMember(new float[3]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float16VectorMember,Float16VectorMember,Float16VectorMember,Float16Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	@Override
	public Procedure2<Float16VectorMember,Float16VectorMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16MatrixMember> VDP =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16VectorMember in1, Float16VectorMember in2, Float16MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<Float16VectorMember,Float16VectorMember,Float16MatrixMember> DP =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16MatrixMember>()
	{
		@Override
		public void call(Float16VectorMember in1, Float16VectorMember in2, Float16MatrixMember out) {
			RModuleDirectProduct.compute(G.HLF, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember,Float16VectorMember,Float16MatrixMember> directProduct() {
		return DP;
	}

	private final Function1<Boolean, Float16VectorMember> ISNAN =
			new Function1<Boolean, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a) {
			return SequenceIsNan.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<Float16VectorMember> NAN =
			new Procedure1<Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a) {
			FillNaN.compute(G.HLF, a.rawData());
		}
	};
	
	@Override
	public Procedure1<Float16VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float16VectorMember> ISINF =
			new Function1<Boolean, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a) {
			return SequenceIsInf.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float16VectorMember> INF =
			new Procedure1<Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a) {
			FillInfinite.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Procedure1<Float16VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, Float16VectorMember, Float16VectorMember> ROUND =
			new Procedure4<Mode, Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, Float16VectorMember a, Float16VectorMember b) {
			RModuleRound.compute(G.HLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, Float16VectorMember, Float16VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float16VectorMember> ISZERO =
			new Function1<Boolean, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16VectorMember a) {
			return SequenceIsZero.compute(G.HLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float16VectorMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, Float16VectorMember, Float16VectorMember> SBHP =
			new Procedure3<HighPrecisionMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(HighPrecisionMember a, Float16VectorMember b, Float16VectorMember c) {
			RModuleScaleByHighPrec.compute(G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, Float16VectorMember, Float16VectorMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, Float16VectorMember, Float16VectorMember> SBR =
			new Procedure3<RationalMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(RationalMember a, Float16VectorMember b, Float16VectorMember c) {
			RModuleScaleByRational.compute(G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, Float16VectorMember, Float16VectorMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float16VectorMember, Float16VectorMember> SBD =
			new Procedure3<Double, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Double a, Float16VectorMember b, Float16VectorMember c) {
			RModuleScaleByDouble.compute(G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, Float16VectorMember, Float16VectorMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float16Member, Float16VectorMember, Float16VectorMember> WITHIN =
			new Function3<Boolean, Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public Boolean call(Float16Member tol, Float16VectorMember a, Float16VectorMember b) {
			return SequencesSimilar.compute(G.HLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, Float16VectorMember, Float16VectorMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float16Member, Float16VectorMember, Float16VectorMember> ADDS =
			new Procedure3<Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16VectorMember a, Float16VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.HLF, G.HLF.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16VectorMember, Float16VectorMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<Float16Member, Float16VectorMember, Float16VectorMember> SUBS =
			new Procedure3<Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16VectorMember a, Float16VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.HLF, G.HLF.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16VectorMember, Float16VectorMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<Float16Member, Float16VectorMember, Float16VectorMember> MULS =
			new Procedure3<Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16VectorMember a, Float16VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.HLF, G.HLF.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16VectorMember, Float16VectorMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<Float16Member, Float16VectorMember, Float16VectorMember> DIVS =
			new Procedure3<Float16Member, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16Member scalar, Float16VectorMember a, Float16VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.HLF, G.HLF.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16Member, Float16VectorMember, Float16VectorMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember> MULTELEM =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.HLF, G.HLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember> DIVELEM =
			new Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Float16VectorMember a, Float16VectorMember b, Float16VectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.HLF, G.HLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float16VectorMember, Float16VectorMember, Float16VectorMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, Float16VectorMember, Float16VectorMember> SCB2 =
			new Procedure3<Integer, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Integer numTimes, Float16VectorMember a, Float16VectorMember b) {
			ScaleHelper.compute(G.HLF_VEC, G.HLF, new Float16Member(2), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float16VectorMember, Float16VectorMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float16VectorMember, Float16VectorMember> SCBH =
			new Procedure3<Integer, Float16VectorMember, Float16VectorMember>()
	{
		@Override
		public void call(Integer numTimes, Float16VectorMember a, Float16VectorMember b) {
			ScaleHelper.compute(G.HLF_VEC, G.HLF, new Float16Member(0.5f), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float16VectorMember, Float16VectorMember> scaleByOneHalf() {
		return SCBH;
	}

}
