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
package nom.bdezonia.zorbage.type.float64.complex;

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
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64Vector
	implements
		VectorSpace<ComplexFloat64Vector,ComplexFloat64VectorMember,ComplexFloat64Algebra,ComplexFloat64Member>,
		Constructible1dLong<ComplexFloat64VectorMember>,
		Norm<ComplexFloat64VectorMember,Float64Member>,
		Products<ComplexFloat64VectorMember, ComplexFloat64Member, ComplexFloat64MatrixMember>,
		DirectProduct<ComplexFloat64VectorMember, ComplexFloat64MatrixMember>,
		Rounding<Float64Member,ComplexFloat64VectorMember>, Infinite<ComplexFloat64VectorMember>,
		NaN<ComplexFloat64VectorMember>,
		ScaleByHighPrec<ComplexFloat64VectorMember>,
		ScaleByRational<ComplexFloat64VectorMember>,
		ScaleByDouble<ComplexFloat64VectorMember>,
		ScaleByOneHalf<ComplexFloat64VectorMember>,
		ScaleByTwo<ComplexFloat64VectorMember>,
		Tolerance<Float64Member,ComplexFloat64VectorMember>,
		ArrayLikeMethods<ComplexFloat64VectorMember,ComplexFloat64Member>
{
	public ComplexFloat64Vector() { }
	
	private final Procedure1<ComplexFloat64VectorMember> ZER =
			new Procedure1<ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64VectorMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> NEG =
			new Procedure2<ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			RModuleNegate.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> ADD =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			RModuleAdd.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> SUB =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			RModuleSubtract.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexFloat64VectorMember,ComplexFloat64VectorMember> EQ =
			new Function2<Boolean, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			return RModuleEqual.compute(G.CDBL, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat64VectorMember,ComplexFloat64VectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat64VectorMember,ComplexFloat64VectorMember> NEQ =
			new Function2<Boolean, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexFloat64VectorMember,ComplexFloat64VectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public ComplexFloat64VectorMember construct() {
		return new ComplexFloat64VectorMember();
	}

	@Override
	public ComplexFloat64VectorMember construct(ComplexFloat64VectorMember other) {
		return new ComplexFloat64VectorMember(other);
	}

	@Override
	public ComplexFloat64VectorMember construct(String s) {
		return new ComplexFloat64VectorMember(s);
	}

	@Override
	public ComplexFloat64VectorMember construct(StorageConstruction s, long d1) {
		return new ComplexFloat64VectorMember(s, d1);
	}

	private final Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> ASSIGN =
			new Procedure2<ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember from, ComplexFloat64VectorMember to) {
			RModuleAssign.compute(G.CDBL, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexFloat64VectorMember,Float64Member> NORM =
			new Procedure2<ComplexFloat64VectorMember, Float64Member>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, Float64Member b) {
			RModuleDefaultNorm.compute(G.CDBL, G.DBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64VectorMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64VectorMember,ComplexFloat64VectorMember> SCALE =
			new Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			RModuleScale.compute(G.CDBL, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64VectorMember,ComplexFloat64VectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> CROSS =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			CrossProduct.compute(G.CDBL_VEC, G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> DOT =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64Member c) {
			DotProduct.compute(G.CDBL_VEC, G.CDBL, G.DBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> dotProduct() {
		return DOT;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> PERP =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64Member c) {
			PerpDotProduct.compute(G.CDBL_VEC, G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> VTRIPLE =
			new Procedure4<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c,
				ComplexFloat64VectorMember d)
		{
			ComplexFloat64VectorMember b_cross_c = new ComplexFloat64VectorMember(new double[3*2]);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> STRIPLE =
			new Procedure4<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64Member>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c,
				ComplexFloat64Member d)
		{
			ComplexFloat64VectorMember b_cross_c = new ComplexFloat64VectorMember(new double[3*2]);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64Member> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<ComplexFloat64VectorMember,ComplexFloat64VectorMember> CONJ =
			new Procedure2<ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			RModuleConjugate.compute(G.CDBL, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64VectorMember, ComplexFloat64VectorMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64MatrixMember> VDP =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember in1, ComplexFloat64VectorMember in2, ComplexFloat64MatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64MatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64MatrixMember> DP =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64MatrixMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember in1, ComplexFloat64VectorMember in2, ComplexFloat64MatrixMember out) {
			RModuleDirectProduct.compute(G.CDBL, in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexFloat64VectorMember,ComplexFloat64VectorMember,ComplexFloat64MatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, ComplexFloat64VectorMember> ISNAN =
			new Function1<Boolean, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a) {
			return SequenceIsNan.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64VectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<ComplexFloat64VectorMember> NAN =
			new Procedure1<ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a) {
			FillNaN.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64VectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat64VectorMember> ISINF =
			new Function1<Boolean, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a) {
			return SequenceIsInf.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64VectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat64VectorMember> INF =
			new Procedure1<ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a) {
			FillInfinite.compute(G.CDBL, a);
		}
	};

	@Override
	public Procedure1<ComplexFloat64VectorMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> ROUND =
			new Procedure4<Mode, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			RModuleRound.compute(G.CDBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat64VectorMember> ISZERO =
			new Function1<Boolean, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(ComplexFloat64VectorMember a) {
			return SequenceIsZero.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64VectorMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(HighPrecisionMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			RModuleScaleByHighPrec.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Procedure3<RationalMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember> SBR =
			new Procedure3<RationalMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(RationalMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			RModuleScaleByRational.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat64VectorMember, ComplexFloat64VectorMember> SBD =
			new Procedure3<Double, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(Double a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			RModuleScaleByDouble.compute(G.CDBL, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, ComplexFloat64VectorMember, ComplexFloat64VectorMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> WITHIN =
			new Function3<Boolean, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public Boolean call(Float64Member tol, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			return SequencesSimilar.compute(G.CDBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> ADDS =
			new Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			FixedTransform2b.compute(G.CDBL, scalar, G.CDBL.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> addScalar() {
		return ADDS;
	}

	private final Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> SUBS =
			new Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			FixedTransform2b.compute(G.CDBL, scalar, G.CDBL.subtract(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> subtractScalar() {
		return SUBS;
	}

	private final Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> MULS =
			new Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			FixedTransform2b.compute(G.CDBL, scalar, G.CDBL.multiply(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> multiplyByScalar() {
		return MULS;
	}

	private final Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> DIVS =
			new Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			FixedTransform2b.compute(G.CDBL, scalar, G.CDBL.divide(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64VectorMember, ComplexFloat64VectorMember> divideByScalar() {
		return DIVS;
	}

	private final Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember> MULTELEM =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			Transform3.compute(G.CDBL, G.CDBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember> multiplyElements() {
		return MULTELEM;
	}

	private final Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember> DIVELEM =
			new Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(ComplexFloat64VectorMember a, ComplexFloat64VectorMember b, ComplexFloat64VectorMember c) {
			Transform3.compute(G.CDBL, G.CDBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64VectorMember, ComplexFloat64VectorMember, ComplexFloat64VectorMember> divideElements() {
		return DIVELEM;
	}

	private final Procedure3<Integer, ComplexFloat64VectorMember, ComplexFloat64VectorMember> SCB2 =
			new Procedure3<Integer, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			ComplexFloat64Member factor = new ComplexFloat64Member(2, 0);
			ComplexFloat64VectorMember prod = G.CDBL_VEC.construct(a);
			for (int i = 0; i < numTimes; i++) {
				scale().call(factor, prod, prod);
			}
			G.CDBL_VEC.assign().call(prod, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat64VectorMember, ComplexFloat64VectorMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat64VectorMember, ComplexFloat64VectorMember> SCBH =
			new Procedure3<Integer, ComplexFloat64VectorMember, ComplexFloat64VectorMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat64VectorMember a, ComplexFloat64VectorMember b) {
			ComplexFloat64Member factor = new ComplexFloat64Member(0.5, 0);
			ComplexFloat64VectorMember prod = G.CDBL_VEC.construct(a);
			for (int i = 0; i < numTimes; i++) {
				scale().call(factor, prod, prod);
			}
			G.CDBL_VEC.assign().call(prod, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat64VectorMember, ComplexFloat64VectorMember> scaleByOneHalf() {
		return SCBH;
	}

}
