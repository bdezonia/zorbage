/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2026 Barry DeZonia All rights reserved.
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
package nom.bdezonia.zorbage.type.complex.float16;

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
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
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
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat16Vector
	implements
		VectorSpace<ComplexFloat16Vector,ComplexFloat16VectorMember,ComplexFloat16Algebra,ComplexFloat16Member>,
		Constructible1dLong<ComplexFloat16VectorMember>,
		Norm<ComplexFloat16VectorMember,Float16Member>,
		Products<ComplexFloat16VectorMember, ComplexFloat16Member, ComplexFloat16MatrixMember>,
		DirectProduct<ComplexFloat16VectorMember, ComplexFloat16MatrixMember>,
		Rounding<Float16Member,ComplexFloat16VectorMember>, Infinite<ComplexFloat16VectorMember>,
		NaN<ComplexFloat16VectorMember>,
		ScaleByHighPrec<ComplexFloat16VectorMember>,
		ScaleByRational<ComplexFloat16VectorMember>,
		ScaleByDouble<ComplexFloat16VectorMember>,
		ScaleByOneHalf<ComplexFloat16VectorMember>,
		ScaleByTwo<ComplexFloat16VectorMember>,
		Tolerance<Float16Member,ComplexFloat16VectorMember>,
		ArrayLikeMethods<ComplexFloat16VectorMember, ComplexFloat16Member>,
		ConstructibleFromBytes<ComplexFloat16VectorMember>,
		ConstructibleFromShorts<ComplexFloat16VectorMember>,
		ConstructibleFromInts<ComplexFloat16VectorMember>,
		ConstructibleFromLongs<ComplexFloat16VectorMember>,
		ConstructibleFromFloats<ComplexFloat16VectorMember>,
		ConstructibleFromDoubles<ComplexFloat16VectorMember>,
		ConstructibleFromBigIntegers<ComplexFloat16VectorMember>,
		ConstructibleFromBigDecimals<ComplexFloat16VectorMember>,
		ExactlyConstructibleFromBytes<ComplexFloat16VectorMember>,
		MadeOfElements<ComplexFloat16Algebra,ComplexFloat16Member>
{
	@Override
	public String typeDescription() {
		return "16-bit based complex vector";
	}
	
	public ComplexFloat16Vector() { }
	
	@Override
	public ComplexFloat16VectorMember construct() {
		return new ComplexFloat16VectorMember();
	}

	@Override
	public ComplexFloat16VectorMember construct(ComplexFloat16VectorMember other) {
		return new ComplexFloat16VectorMember(other);
	}

	@Override
	public ComplexFloat16VectorMember construct(String s) {
		return new ComplexFloat16VectorMember(s);
	}

	@Override
	public ComplexFloat16VectorMember construct(StorageConstruction s, long d1) {
		return new ComplexFloat16VectorMember(s, d1);
	}

	@Override
	public ComplexFloat16VectorMember constructExactly(byte... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromBytesExact(vals);
		return v;
	}

	@Override
	public ComplexFloat16VectorMember construct(byte... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromBytes(vals);
		return v;
	}

	@Override
	public ComplexFloat16VectorMember construct(short... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromShorts(vals);
		return v;
	}

	@Override
	public ComplexFloat16VectorMember construct(int... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromInts(vals);
		return v;
	}

	@Override
	public ComplexFloat16VectorMember construct(long... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromLongs(vals);
		return v;
	}

	@Override
	public ComplexFloat16VectorMember construct(float... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromFloats(vals);
		return v;
	}

	@Override
	public ComplexFloat16VectorMember construct(double... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromDoubles(vals);
		return v;
	}

	@Override
	public ComplexFloat16VectorMember construct(BigInteger... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromBigIntegers(vals);
		return v;
	}

	@Override
	public ComplexFloat16VectorMember construct(BigDecimal... vals) {
		ComplexFloat16VectorMember v = construct();
		v.setFromBigDecimals(vals);
		return v;
	}

	private final Procedure1<ComplexFloat16VectorMember> ZER =
			new Procedure1<ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> NEG =
			new Procedure2<ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			RModuleNegate.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> ADD =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			RModuleAdd.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> SUB =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			RModuleSubtract.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat16VectorMember,ComplexFloat16VectorMember> EQ =
			new Function2<Boolean, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			return RModuleEqual.compute(G.CHLF, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat16VectorMember,ComplexFloat16VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat16VectorMember,ComplexFloat16VectorMember> NEQ =
			new Function2<Boolean, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat16VectorMember,ComplexFloat16VectorMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> ASSIGN =
			new Procedure2<ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember from, ComplexFloat16VectorMember to) {
			RModuleAssign.compute(G.CHLF, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexFloat16VectorMember,Float16Member> NORM =
			new Procedure2<ComplexFloat16VectorMember, Float16Member>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, Float16Member b) {
			RModuleDefaultNorm.compute(G.CHLF, G.HLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16VectorMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16VectorMember,ComplexFloat16VectorMember> SCALE =
			new Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			RModuleScale.compute(G.CHLF, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16VectorMember,ComplexFloat16VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> CROSS =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			CrossProduct.compute(G.CHLF_VEC, G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> DOT =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16Member c) {
			DotProduct.compute(G.CHLF_VEC, G.CHLF, G.HLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> PERP =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16Member c) {
			PerpDotProduct.compute(G.CHLF_VEC, G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> VTRIPLE =
			new Procedure4<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c,
				ComplexFloat16VectorMember d)
		{
			ComplexFloat16VectorMember b_cross_c = new ComplexFloat16VectorMember(new float[3*2]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> STRIPLE =
			new Procedure4<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16Member>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c,
				ComplexFloat16Member d)
		{
			ComplexFloat16VectorMember b_cross_c = new ComplexFloat16VectorMember(new float[3*2]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<ComplexFloat16VectorMember,ComplexFloat16VectorMember> CONJ =
			new Procedure2<ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			RModuleConjugate.compute(G.CHLF, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16VectorMember, ComplexFloat16VectorMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16MatrixMember> VDP =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember in1, ComplexFloat16VectorMember in2, ComplexFloat16MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16MatrixMember> DP =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16MatrixMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember in1, ComplexFloat16VectorMember in2, ComplexFloat16MatrixMember out) {
			RModuleDirectProduct.compute(G.CHLF, in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat16VectorMember,ComplexFloat16VectorMember,ComplexFloat16MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, ComplexFloat16VectorMember> ISNAN =
			new Function1<Boolean, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a) {
			return SequenceIsNan.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat16VectorMember> NAN =
			new Procedure1<ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a) {
			FillNaN.compute(G.CHLF, a.rawData());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat16VectorMember> ISINF =
			new Function1<Boolean, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a) {
			return SequenceIsInf.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat16VectorMember> INF =
			new Procedure1<ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a) {
			FillInfinite.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Procedure1<ComplexFloat16VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> ROUND =
			new Procedure4<Mode, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			RModuleRound.compute(G.CHLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat16VectorMember> ISZERO =
			new Function1<Boolean, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat16VectorMember a) {
			return SequenceIsZero.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16VectorMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			RModuleScaleByHighPrec.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember> SBR =
			new Procedure3<RationalMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			RModuleScaleByRational.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat16VectorMember, ComplexFloat16VectorMember> SBD =
			new Procedure3<Double, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(Double a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			RModuleScaleByDouble.compute(G.CHLF, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, ComplexFloat16VectorMember, ComplexFloat16VectorMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> WITHIN =
			new Function3<Boolean, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public Boolean call(Float16Member tol, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			return SequencesSimilar.compute(G.CHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> ADDS =
			new Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CHLF, G.CHLF.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> SUBS =
			new Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CHLF, G.CHLF.subtract(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> MULS =
			new Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CHLF, G.CHLF.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> DIVS =
			new Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			b.alloc(a.length());
			TransformWithConstant.compute(G.CHLF, G.CHLF.divide(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member, ComplexFloat16VectorMember, ComplexFloat16VectorMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember> MULTELEM =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.CHLF, G.CHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember> DIVELEM =
			new Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(ComplexFloat16VectorMember a, ComplexFloat16VectorMember b, ComplexFloat16VectorMember c) {
			c.alloc(a.length());
			Transform3.compute(G.CHLF, G.CHLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16VectorMember, ComplexFloat16VectorMember, ComplexFloat16VectorMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, ComplexFloat16VectorMember, ComplexFloat16VectorMember> SCB2 =
			new Procedure3<Integer, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			ScaleHelper.compute(G.CHLF_VEC, G.CHLF, new ComplexFloat16Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat16VectorMember, ComplexFloat16VectorMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat16VectorMember, ComplexFloat16VectorMember> SCBH =
			new Procedure3<Integer, ComplexFloat16VectorMember, ComplexFloat16VectorMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat16VectorMember a, ComplexFloat16VectorMember b) {
			ScaleHelper.compute(G.CHLF_VEC, G.CHLF, new ComplexFloat16Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat16VectorMember, ComplexFloat16VectorMember> scaleByOneHalf() {
		return SCBH;
	}
	
	@Override
	public ComplexFloat16Algebra getElementAlgebra() {
		return G.CHLF;
	}
}
