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
package nom.bdezonia.zorbage.type.complex.float64;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.SequenceIsInf;
import nom.bdezonia.zorbage.algorithm.SequenceIsNan;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.ShapesMatch;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorFlipIndex;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
import nom.bdezonia.zorbage.algorithm.TensorShape;
import nom.bdezonia.zorbage.algorithm.TensorUnity;
import nom.bdezonia.zorbage.algorithm.Transform2;
import nom.bdezonia.zorbage.algorithm.Transform3;
import nom.bdezonia.zorbage.algorithm.TransformWithConstant;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.procedure.Procedure5;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float64.Float64Algebra;
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64GeneralTensorProduct
	implements
		TensorProduct<ComplexFloat64GeneralTensorProduct,ComplexFloat64GeneralTensorProductMember,Float64Algebra,Float64Member>,
		Norm<ComplexFloat64GeneralTensorProductMember,Float64Member>,
		Scale<ComplexFloat64GeneralTensorProductMember,ComplexFloat64Member>,
		Rounding<Float64Member,ComplexFloat64GeneralTensorProductMember>,
		Infinite<ComplexFloat64GeneralTensorProductMember>,
		NaN<ComplexFloat64GeneralTensorProductMember>,
		ScaleByHighPrec<ComplexFloat64GeneralTensorProductMember>,
		ScaleByRational<ComplexFloat64GeneralTensorProductMember>,
		ScaleByDouble<ComplexFloat64GeneralTensorProductMember>,
		ScaleByOneHalf<ComplexFloat64GeneralTensorProductMember>,
		ScaleByTwo<ComplexFloat64GeneralTensorProductMember>,
		Tolerance<Float64Member, ComplexFloat64GeneralTensorProductMember>,
		ArrayLikeMethods<ComplexFloat64GeneralTensorProductMember, ComplexFloat64Member>,
		MadeOfElements<ComplexFloat64Algebra,ComplexFloat64Member>,
		ConstructibleTensor<ComplexFloat64GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "64-bit based complex tensor";
	}

	@Override
	public ComplexFloat64GeneralTensorProductMember construct() {
		return new ComplexFloat64GeneralTensorProductMember();
	}

	@Override
	public ComplexFloat64GeneralTensorProductMember construct(ComplexFloat64GeneralTensorProductMember other) {
		return new ComplexFloat64GeneralTensorProductMember(other);
	}

	@Override
	public ComplexFloat64GeneralTensorProductMember construct(String s) {
		return new ComplexFloat64GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> EQ =
			new Function2<Boolean,ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CDBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> NEQ =
			new Function2<Boolean,ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> ASSIGN =
			new Procedure2<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember from, ComplexFloat64GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.CDBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat64GeneralTensorProductMember> ZER =
			new Procedure1<ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> NEG =
			new Procedure2<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CDBL, G.CDBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> ADDEL =
			new Procedure3<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b, ComplexFloat64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CDBL, G.CDBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> SUBEL =
			new Procedure3<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b, ComplexFloat64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CDBL, G.CDBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexFloat64GeneralTensorProductMember,Float64Member> NORM =
			new Procedure2<ComplexFloat64GeneralTensorProductMember, Float64Member>() 
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.CDBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64GeneralTensorProductMember,Float64Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> SCALE =
			new Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CDBL, G.CDBL.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CDBL, G.CDBL.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			ComplexFloat64Member tmp = G.CDBL.construct();
			G.CDBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> MULEL =
			new Procedure3<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b, ComplexFloat64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CDBL, G.CDBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b, ComplexFloat64GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CDBL, G.CDBL.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> MUL =
			new Procedure3<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b, ComplexFloat64GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorContract.compute(G.CDBL, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> POWER =
			new Procedure3<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorPower.compute(G.CDBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat64GeneralTensorProductMember,ComplexFloat64GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat64GeneralTensorProductMember> UNITY =
			new Procedure1<ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember result) {
			TensorUnity.compute(G.CDBL_TEN, G.CDBL, result);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexFloat64GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat64GeneralTensorProductMember> NAN =
			new Procedure1<ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a) {
			FillNaN.compute(G.CDBL, a.rawData());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat64GeneralTensorProductMember> ISINF =
			new Function1<Boolean, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat64GeneralTensorProductMember> INF =
			new Procedure1<ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a) {
			FillInfinite.compute(G.CDBL, a.rawData());
		}
	};
			
	@Override
	public Procedure1<ComplexFloat64GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorRound.compute(G.CDBL_TEN, G.CDBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat64GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> SBD =
			new Procedure3<Double, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CDBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member factor, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member factor, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			ComplexFloat64Member invFactor = G.CDBL.construct();
			G.CDBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, ComplexFloat64GeneralTensorProductMember metricInverse, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.CDBL, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat64GeneralTensorProductMember metric, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.CDBL, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b, ComplexFloat64GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexFloat64GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> OUTER =
			new Procedure3<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b, ComplexFloat64GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.CDBL_TEN, G.CDBL, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CDBL_TEN, G.CDBL, new ComplexFloat64Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat64GeneralTensorProductMember a, ComplexFloat64GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CDBL_TEN, G.CDBL, new ComplexFloat64Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat64GeneralTensorProductMember, ComplexFloat64GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat64GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, ComplexFloat64GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat64GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public ComplexFloat64Algebra getElementAlgebra() {
		return G.CDBL;
	}

	@Override
	public ComplexFloat64GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new ComplexFloat64GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
