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
package nom.bdezonia.zorbage.type.real.float128;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.BigInteger;

import nom.bdezonia.zorbage.algebra.*;
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
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
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
public class Float128Vector
	implements
		VectorSpace<Float128Vector,Float128VectorMember,Float128Algebra,Float128Member>,
		Constructible1dLong<Float128VectorMember>,
		Norm<Float128VectorMember,Float128Member>,
		Products<Float128VectorMember, Float128Member, Float128MatrixMember>,
		DirectProduct<Float128VectorMember, Float128MatrixMember>,
		Rounding<Float128Member,Float128VectorMember>, Infinite<Float128VectorMember>,
		NaN<Float128VectorMember>,
		ScaleByHighPrec<Float128VectorMember>,
		ScaleByRational<Float128VectorMember>,
		ScaleByDouble<Float128VectorMember>,
		ScaleByOneHalf<Float128VectorMember>,
		ScaleByTwo<Float128VectorMember>,
		Tolerance<Float128Member,Float128VectorMember>,
		ArrayLikeMethods<Float128VectorMember,Float128Member>,
		ConstructibleFromBytes<Float128VectorMember>,
		ConstructibleFromShorts<Float128VectorMember>,
		ConstructibleFromInts<Float128VectorMember>,
		ConstructibleFromLongs<Float128VectorMember>,
		ConstructibleFromFloats<Float128VectorMember>,
		ConstructibleFromDoubles<Float128VectorMember>,
		ConstructibleFromBigIntegers<Float128VectorMember>,
		ConstructibleFromBigDecimals<Float128VectorMember>,
		ExactlyConstructibleFromBytes<Float128VectorMember>,
		ExactlyConstructibleFromShorts<Float128VectorMember>,
		ExactlyConstructibleFromInts<Float128VectorMember>,
		ExactlyConstructibleFromFloats<Float128VectorMember>,
		ExactlyConstructibleFromDoubles<Float128VectorMember>
{
	@Override
	public String typeDescription() {
		return "128-bit based real vector";
	}

	public Float128Vector() { }

	@Override
	public Float128VectorMember construct() {
		return new Float128VectorMember();
	}

	@Override
	public Float128VectorMember construct(Float128VectorMember other) {
		return new Float128VectorMember(other);
	}

	@Override
	public Float128VectorMember construct(String s) {
		return new Float128VectorMember(s);
	}

	@Override
	public Float128VectorMember construct(StorageConstruction s, long d1) {
		return new Float128VectorMember(s, d1);
	}

	@Override
	public Float128VectorMember construct(byte... vals) {
		Float128VectorMember v = construct();
		v.setFromBytes(vals);
		return v;
	}
	
	@Override
	public Float128VectorMember constructExactly(byte... vals) {
		Float128VectorMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public Float128VectorMember construct(short... vals) {
		Float128VectorMember v = construct();
		v.setFromShorts(vals);
		return v;
	}
	
	@Override
	public Float128VectorMember constructExactly(short... vals) {
		Float128VectorMember v = construct();
		v.setFromShortsExact(vals);
		return v;
	}

	@Override
	public Float128VectorMember construct(int... vals) {
		Float128VectorMember v = construct();
		v.setFromInts(vals);
		return v;
	}
	
	@Override
	public Float128VectorMember constructExactly(int... vals) {
		Float128VectorMember v = construct();
		v.setFromIntsExact(vals);
		return v;
	}

	@Override
	public Float128VectorMember construct(long... vals) {
		Float128VectorMember v = construct();
		v.setFromLongs(vals);
		return v;
	}
	
	@Override
	public Float128VectorMember construct(float... vals) {
		Float128VectorMember v = construct();
		v.setFromFloats(vals);
		return v;
	}
	
	@Override
	public Float128VectorMember constructExactly(float... vals) {
		Float128VectorMember v = construct();
		v.setFromFloatsExact(vals);
		return v;
	}

	@Override
	public Float128VectorMember construct(double... vals) {
		Float128VectorMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}
	
	@Override
	public Float128VectorMember constructExactly(double... vals) {
		Float128VectorMember v = construct();
		v.setFromDoublesExact(vals);
		return v;
	}

	@Override
	public Float128VectorMember construct(BigInteger... vals) {
		Float128VectorMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public Float128VectorMember construct(BigDecimal... vals) {
		Float128VectorMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	private final Procedure1<Float128VectorMember> ZER =
			new Procedure1<Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float128VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<Float128VectorMember,Float128VectorMember> NEG =
			new Procedure2<Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b) {
			RModuleNegate.compute(G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<Float128VectorMember,Float128VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<Float128VectorMember,Float128VectorMember,Float128VectorMember> ADD =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128VectorMember c) {
			RModuleAdd.compute(G.QUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<Float128VectorMember,Float128VectorMember,Float128VectorMember> add() {
		return ADD;
	}

	private final Procedure3<Float128VectorMember,Float128VectorMember,Float128VectorMember> SUB =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128VectorMember c) {
			RModuleSubtract.compute(G.QUAD, a, b, c);
		}
	};

	@Override
	public Procedure3<Float128VectorMember,Float128VectorMember,Float128VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float128VectorMember,Float128VectorMember> EQ =
			new Function2<Boolean, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public Boolean call(Float128VectorMember a, Float128VectorMember b) {
			return RModuleEqual.compute(G.QUAD, a, b);
		}
	};

	@Override
	public Function2<Boolean,Float128VectorMember,Float128VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float128VectorMember,Float128VectorMember> NEQ =
			new Function2<Boolean, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public Boolean call(Float128VectorMember a, Float128VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,Float128VectorMember,Float128VectorMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<Float128VectorMember,Float128VectorMember> ASSIGN =
			new Procedure2<Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember from, Float128VectorMember to) {
			RModuleAssign.compute(G.QUAD, from, to);
		}
	};
	
	@Override
	public Procedure2<Float128VectorMember,Float128VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<Float128VectorMember,Float128Member> NORM =
			new Procedure2<Float128VectorMember, Float128Member>()
	{
		@Override
		public void call(Float128VectorMember a, Float128Member b) {
			RModuleDefaultNorm.compute(G.QUAD, G.QUAD, a, b);
		}
	};
	
	@Override
	public Procedure2<Float128VectorMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure3<Float128Member,Float128VectorMember,Float128VectorMember> SCALE =
			new Procedure3<Float128Member, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128VectorMember a, Float128VectorMember b) {
			RModuleScale.compute(G.QUAD, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<Float128Member,Float128VectorMember,Float128VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float128VectorMember,Float128VectorMember,Float128VectorMember> CROSS =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128VectorMember c) {
			CrossProduct.compute(G.QUAD_VEC, G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float128VectorMember,Float128VectorMember,Float128VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<Float128VectorMember,Float128VectorMember,Float128Member> DOT =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128Member>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128Member c) {
			DotProduct.compute(G.QUAD_VEC, G.QUAD, G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float128VectorMember,Float128VectorMember,Float128Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<Float128VectorMember,Float128VectorMember,Float128Member> PERP =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128Member>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128Member c) {
			PerpDotProduct.compute(G.QUAD_VEC, G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float128VectorMember,Float128VectorMember,Float128Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<Float128VectorMember,Float128VectorMember,Float128VectorMember,Float128VectorMember> VTRIPLE =
			new Procedure4<Float128VectorMember, Float128VectorMember, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128VectorMember c, Float128VectorMember d) {
			Float128VectorMember b_cross_c = new Float128VectorMember(3);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float128VectorMember,Float128VectorMember,Float128VectorMember,Float128VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<Float128VectorMember,Float128VectorMember,Float128VectorMember,Float128Member> STRIPLE =
			new Procedure4<Float128VectorMember, Float128VectorMember, Float128VectorMember, Float128Member>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128VectorMember c, Float128Member d) {
			Float128VectorMember b_cross_c = new Float128VectorMember(3);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float128VectorMember,Float128VectorMember,Float128VectorMember,Float128Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	@Override
	public Procedure2<Float128VectorMember,Float128VectorMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float128VectorMember,Float128VectorMember,Float128MatrixMember> VDP =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128VectorMember in1, Float128VectorMember in2, Float128MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float128VectorMember,Float128VectorMember,Float128MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<Float128VectorMember,Float128VectorMember,Float128MatrixMember> DP =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128MatrixMember>()
	{
		@Override
		public void call(Float128VectorMember in1, Float128VectorMember in2, Float128MatrixMember out) {
			RModuleDirectProduct.compute(G.QUAD, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float128VectorMember,Float128VectorMember,Float128MatrixMember> directProduct() {
		return DP;
	}

	private final Function1<Boolean, Float128VectorMember> ISNAN =
			new Function1<Boolean, Float128VectorMember>()
	{
		@Override
		public Boolean call(Float128VectorMember a) {
			return SequenceIsNan.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<Float128VectorMember> NAN =
			new Procedure1<Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a) {
			FillNaN.compute(G.QUAD, a.rawData());
		}
	};
	
	@Override
	public Procedure1<Float128VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float128VectorMember> ISINF =
			new Function1<Boolean, Float128VectorMember>()
	{
		@Override
		public Boolean call(Float128VectorMember a) {
			return SequenceIsInf.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float128VectorMember> INF =
			new Procedure1<Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a) {
			FillInfinite.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Procedure1<Float128VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, Float128VectorMember, Float128VectorMember> ROUND =
			new Procedure4<Mode, Float128Member, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, Float128VectorMember a, Float128VectorMember b) {
			RModuleRound.compute(G.QUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, Float128VectorMember, Float128VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float128VectorMember> ISZERO =
			new Function1<Boolean, Float128VectorMember>()
	{
		@Override
		public Boolean call(Float128VectorMember a) {
			return SequenceIsZero.compute(G.QUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float128VectorMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, Float128VectorMember, Float128VectorMember> SBHP =
			new Procedure3<HighPrecisionMember, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(HighPrecisionMember a, Float128VectorMember b, Float128VectorMember c) {
			RModuleScaleByHighPrec.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, Float128VectorMember, Float128VectorMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, Float128VectorMember, Float128VectorMember> SBR =
			new Procedure3<RationalMember, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(RationalMember a, Float128VectorMember b, Float128VectorMember c) {
			RModuleScaleByRational.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, Float128VectorMember, Float128VectorMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float128VectorMember, Float128VectorMember> SBD =
			new Procedure3<Double, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Double a, Float128VectorMember b, Float128VectorMember c) {
			RModuleScaleByDouble.compute(G.QUAD, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, Float128VectorMember, Float128VectorMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float128Member, Float128VectorMember, Float128VectorMember> WITHIN =
			new Function3<Boolean, Float128Member, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public Boolean call(Float128Member tol, Float128VectorMember a, Float128VectorMember b) {
			return SequencesSimilar.compute(G.QUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, Float128VectorMember, Float128VectorMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float128Member, Float128VectorMember, Float128VectorMember> ADDS =
			new Procedure3<Float128Member, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128VectorMember a, Float128VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QUAD, G.QUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128VectorMember, Float128VectorMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<Float128Member, Float128VectorMember, Float128VectorMember> SUBS =
			new Procedure3<Float128Member, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128VectorMember a, Float128VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QUAD, G.QUAD.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128VectorMember, Float128VectorMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<Float128Member, Float128VectorMember, Float128VectorMember> MULS =
			new Procedure3<Float128Member, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128VectorMember a, Float128VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QUAD, G.QUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128VectorMember, Float128VectorMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<Float128Member, Float128VectorMember, Float128VectorMember> DIVS =
			new Procedure3<Float128Member, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128Member scalar, Float128VectorMember a, Float128VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.QUAD, G.QUAD.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128Member, Float128VectorMember, Float128VectorMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember> MULTELEM =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128VectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.QUAD, G.QUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember> DIVELEM =
			new Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Float128VectorMember a, Float128VectorMember b, Float128VectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.QUAD, G.QUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float128VectorMember, Float128VectorMember, Float128VectorMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, Float128VectorMember, Float128VectorMember> SCB2 =
			new Procedure3<Integer, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Integer numTimes, Float128VectorMember a, Float128VectorMember b) {
			ScaleHelper.compute(G.QUAD_VEC, G.QUAD, new Float128Member(BigDecimal.valueOf(2)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float128VectorMember, Float128VectorMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float128VectorMember, Float128VectorMember> SCBH =
			new Procedure3<Integer, Float128VectorMember, Float128VectorMember>()
	{
		@Override
		public void call(Integer numTimes, Float128VectorMember a, Float128VectorMember b) {
			ScaleHelper.compute(G.QUAD_VEC, G.QUAD, new Float128Member(BigDecimal.valueOf(0.5)), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float128VectorMember, Float128VectorMember> scaleByOneHalf() {
		return SCBH;
	}
}
