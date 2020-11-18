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
package nom.bdezonia.zorbage.type.real.float64;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2b;
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
public class Float64Vector
	implements
		VectorSpace<Float64Vector,Float64VectorMember,Float64Algebra,Float64Member>,
		Constructible1dLong<Float64VectorMember>,
		Norm<Float64VectorMember,Float64Member>,
		Products<Float64VectorMember, Float64Member, Float64MatrixMember>,
		DirectProduct<Float64VectorMember, Float64MatrixMember>,
		Rounding<Float64Member,Float64VectorMember>, Infinite<Float64VectorMember>,
		NaN<Float64VectorMember>,
		ScaleByHighPrec<Float64VectorMember>,
		ScaleByRational<Float64VectorMember>,
		ScaleByDouble<Float64VectorMember>,
		ScaleByOneHalf<Float64VectorMember>,
		ScaleByTwo<Float64VectorMember>,
		Tolerance<Float64Member,Float64VectorMember>,
		ArrayLikeMethods<Float64VectorMember,Float64Member>
{
	public Float64Vector() { }
	
	private final Procedure1<Float64VectorMember> ZER =
			new Procedure1<Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<Float64VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<Float64VectorMember,Float64VectorMember> NEG =
			new Procedure2<Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b) {
			RModuleNegate.compute(G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64VectorMember,Float64VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<Float64VectorMember,Float64VectorMember,Float64VectorMember> ADD =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
			RModuleAdd.compute(G.DBL, a, b, c);
		}
	};

	@Override
	public Procedure3<Float64VectorMember,Float64VectorMember,Float64VectorMember> add() {
		return ADD;
	}

	private final Procedure3<Float64VectorMember,Float64VectorMember,Float64VectorMember> SUB =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
			RModuleSubtract.compute(G.DBL, a, b, c);
		}
	};

	@Override
	public Procedure3<Float64VectorMember,Float64VectorMember,Float64VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,Float64VectorMember,Float64VectorMember> EQ =
			new Function2<Boolean, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public Boolean call(Float64VectorMember a, Float64VectorMember b) {
			return RModuleEqual.compute(G.DBL, a, b);
		}
	};

	@Override
	public Function2<Boolean,Float64VectorMember,Float64VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,Float64VectorMember,Float64VectorMember> NEQ =
			new Function2<Boolean, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public Boolean call(Float64VectorMember a, Float64VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,Float64VectorMember,Float64VectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public Float64VectorMember construct() {
		return new Float64VectorMember();
	}

	@Override
	public Float64VectorMember construct(Float64VectorMember other) {
		return new Float64VectorMember(other);
	}

	@Override
	public Float64VectorMember construct(String s) {
		return new Float64VectorMember(s);
	}

	@Override
	public Float64VectorMember construct(StorageConstruction s, long d1) {
		return new Float64VectorMember(s, d1);
	}

	private final Procedure2<Float64VectorMember,Float64VectorMember> ASSIGN =
			new Procedure2<Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember from, Float64VectorMember to) {
			RModuleAssign.compute(G.DBL, from, to);
		}
	};
	
	@Override
	public Procedure2<Float64VectorMember,Float64VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<Float64VectorMember,Float64Member> NORM =
			new Procedure2<Float64VectorMember, Float64Member>()
	{
		@Override
		public void call(Float64VectorMember a, Float64Member b) {
			RModuleDefaultNorm.compute(G.DBL, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<Float64VectorMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure3<Float64Member,Float64VectorMember,Float64VectorMember> SCALE =
			new Procedure3<Float64Member, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64VectorMember a, Float64VectorMember b) {
			RModuleScale.compute(G.DBL, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<Float64Member,Float64VectorMember,Float64VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<Float64VectorMember,Float64VectorMember,Float64VectorMember> CROSS =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
			CrossProduct.compute(G.DBL_VEC, G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64VectorMember,Float64VectorMember,Float64VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<Float64VectorMember,Float64VectorMember,Float64Member> DOT =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64Member>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64Member c) {
			DotProduct.compute(G.DBL_VEC, G.DBL, G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64VectorMember,Float64VectorMember,Float64Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<Float64VectorMember,Float64VectorMember,Float64Member> PERP =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64Member>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64Member c) {
			PerpDotProduct.compute(G.DBL_VEC, G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Float64VectorMember,Float64VectorMember,Float64Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<Float64VectorMember,Float64VectorMember,Float64VectorMember,Float64VectorMember> VTRIPLE =
			new Procedure4<Float64VectorMember, Float64VectorMember, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c, Float64VectorMember d) {
			Float64VectorMember b_cross_c = new Float64VectorMember(new double[3]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float64VectorMember,Float64VectorMember,Float64VectorMember,Float64VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<Float64VectorMember,Float64VectorMember,Float64VectorMember,Float64Member> STRIPLE =
			new Procedure4<Float64VectorMember, Float64VectorMember, Float64VectorMember, Float64Member>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c, Float64Member d) {
			Float64VectorMember b_cross_c = new Float64VectorMember(new double[3]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<Float64VectorMember,Float64VectorMember,Float64VectorMember,Float64Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	@Override
	public Procedure2<Float64VectorMember,Float64VectorMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<Float64VectorMember,Float64VectorMember,Float64MatrixMember> VDP =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64VectorMember in1, Float64VectorMember in2, Float64MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float64VectorMember,Float64VectorMember,Float64MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<Float64VectorMember,Float64VectorMember,Float64MatrixMember> DP =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64MatrixMember>()
	{
		@Override
		public void call(Float64VectorMember in1, Float64VectorMember in2, Float64MatrixMember out) {
			RModuleDirectProduct.compute(G.DBL, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<Float64VectorMember,Float64VectorMember,Float64MatrixMember> directProduct() {
		return DP;
	}

	private final Function1<Boolean, Float64VectorMember> ISNAN =
			new Function1<Boolean, Float64VectorMember>()
	{
		@Override
		public Boolean call(Float64VectorMember a) {
			return SequenceIsNan.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<Float64VectorMember> NAN =
			new Procedure1<Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a) {
			FillNaN.compute(G.DBL, a);
		}
	};
	
	@Override
	public Procedure1<Float64VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, Float64VectorMember> ISINF =
			new Function1<Boolean, Float64VectorMember>()
	{
		@Override
		public Boolean call(Float64VectorMember a) {
			return SequenceIsInf.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<Float64VectorMember> INF =
			new Procedure1<Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a) {
			FillInfinite.compute(G.DBL, a);
		}
	};

	@Override
	public Procedure1<Float64VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, Float64VectorMember, Float64VectorMember> ROUND =
			new Procedure4<Mode, Float64Member, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, Float64VectorMember a, Float64VectorMember b) {
			RModuleRound.compute(G.DBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, Float64VectorMember, Float64VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, Float64VectorMember> ISZERO =
			new Function1<Boolean, Float64VectorMember>()
	{
		@Override
		public Boolean call(Float64VectorMember a) {
			return SequenceIsZero.compute(G.DBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, Float64VectorMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, Float64VectorMember, Float64VectorMember> SBHP =
			new Procedure3<HighPrecisionMember, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(HighPrecisionMember a, Float64VectorMember b, Float64VectorMember c) {
			RModuleScaleByHighPrec.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, Float64VectorMember, Float64VectorMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, Float64VectorMember, Float64VectorMember> SBR =
			new Procedure3<RationalMember, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(RationalMember a, Float64VectorMember b, Float64VectorMember c) {
			RModuleScaleByRational.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, Float64VectorMember, Float64VectorMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, Float64VectorMember, Float64VectorMember> SBD =
			new Procedure3<Double, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Double a, Float64VectorMember b, Float64VectorMember c) {
			RModuleScaleByDouble.compute(G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, Float64VectorMember, Float64VectorMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float64Member, Float64VectorMember, Float64VectorMember> WITHIN =
			new Function3<Boolean, Float64Member, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public Boolean call(Float64Member tol, Float64VectorMember a, Float64VectorMember b) {
			return SequencesSimilar.compute(G.DBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, Float64VectorMember, Float64VectorMember> within() {
		return WITHIN;
	}

	private final Procedure3<Float64Member, Float64VectorMember, Float64VectorMember> ADDS =
			new Procedure3<Float64Member, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64VectorMember a, Float64VectorMember b) {
			FixedTransform2b.compute(G.DBL, scalar, G.DBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64VectorMember, Float64VectorMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<Float64Member, Float64VectorMember, Float64VectorMember> SUBS =
			new Procedure3<Float64Member, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64VectorMember a, Float64VectorMember b) {
			FixedTransform2b.compute(G.DBL, scalar, G.DBL.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64VectorMember, Float64VectorMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<Float64Member, Float64VectorMember, Float64VectorMember> MULS =
			new Procedure3<Float64Member, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64VectorMember a, Float64VectorMember b) {
			FixedTransform2b.compute(G.DBL, scalar, G.DBL.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64VectorMember, Float64VectorMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<Float64Member, Float64VectorMember, Float64VectorMember> DIVS =
			new Procedure3<Float64Member, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64Member scalar, Float64VectorMember a, Float64VectorMember b) {
			FixedTransform2b.compute(G.DBL, scalar, G.DBL.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64Member, Float64VectorMember, Float64VectorMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember> MULTELEM =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
			Transform3.compute(G.DBL, G.DBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember> DIVELEM =
			new Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Float64VectorMember a, Float64VectorMember b, Float64VectorMember c) {
			Transform3.compute(G.DBL, G.DBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<Float64VectorMember, Float64VectorMember, Float64VectorMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, Float64VectorMember, Float64VectorMember> SCB2 =
			new Procedure3<Integer, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Integer numTimes, Float64VectorMember a, Float64VectorMember b) {
			ScaleHelper.compute(G.DBL_VEC, G.DBL, new Float64Member(2), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float64VectorMember, Float64VectorMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, Float64VectorMember, Float64VectorMember> SCBH =
			new Procedure3<Integer, Float64VectorMember, Float64VectorMember>()
	{
		@Override
		public void call(Integer numTimes, Float64VectorMember a, Float64VectorMember b) {
			ScaleHelper.compute(G.DBL_VEC, G.DBL, new Float64Member(0.5), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, Float64VectorMember, Float64VectorMember> scaleByOneHalf() {
		return SCBH;
	}

}
