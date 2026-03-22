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
package nom.bdezonia.zorbage.type.complex.float128;

import java.lang.Integer;
import java.math.BigDecimal;

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
import nom.bdezonia.zorbage.type.real.float128.Float128Algebra;
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat128GeneralTensorProduct
	implements
		TensorProduct<ComplexFloat128GeneralTensorProduct,ComplexFloat128GeneralTensorProductMember,Float128Algebra,Float128Member>,
		Norm<ComplexFloat128GeneralTensorProductMember,Float128Member>,
		Scale<ComplexFloat128GeneralTensorProductMember,ComplexFloat128Member>,
		Rounding<Float128Member,ComplexFloat128GeneralTensorProductMember>,
		Infinite<ComplexFloat128GeneralTensorProductMember>,
		NaN<ComplexFloat128GeneralTensorProductMember>,
		ScaleByHighPrec<ComplexFloat128GeneralTensorProductMember>,
		ScaleByRational<ComplexFloat128GeneralTensorProductMember>,
		ScaleByDouble<ComplexFloat128GeneralTensorProductMember>,
		ScaleByOneHalf<ComplexFloat128GeneralTensorProductMember>,
		ScaleByTwo<ComplexFloat128GeneralTensorProductMember>,
		Tolerance<Float128Member, ComplexFloat128GeneralTensorProductMember>,
		ArrayLikeMethods<ComplexFloat128GeneralTensorProductMember, ComplexFloat128Member>,
		MadeOfElements<ComplexFloat128Algebra,ComplexFloat128Member>,
		ConstructibleTensor<ComplexFloat128GeneralTensorProductMember>
{
	@Override
	public String typeDescription() {
		return "128-bit based complex tensor";
	}

	@Override
	public ComplexFloat128GeneralTensorProductMember construct() {
		return new ComplexFloat128GeneralTensorProductMember();
	}

	@Override
	public ComplexFloat128GeneralTensorProductMember construct(ComplexFloat128GeneralTensorProductMember other) {
		return new ComplexFloat128GeneralTensorProductMember(other);
	}

	@Override
	public ComplexFloat128GeneralTensorProductMember construct(String s) {
		return new ComplexFloat128GeneralTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> EQ =
			new Function2<Boolean,ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CQUAD, G.QUAD.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> NEQ =
			new Function2<Boolean,ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> ASSIGN =
			new Procedure2<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember from, ComplexFloat128GeneralTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.CQUAD, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat128GeneralTensorProductMember> ZER =
			new Procedure1<ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128GeneralTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> NEG =
			new Procedure2<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CQUAD, G.CQUAD.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> ADDEL =
			new Procedure3<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b, ComplexFloat128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CQUAD, G.CQUAD.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> SUBEL =
			new Procedure3<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b, ComplexFloat128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CQUAD, G.CQUAD.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexFloat128GeneralTensorProductMember,Float128Member> NORM =
			new Procedure2<ComplexFloat128GeneralTensorProductMember, Float128Member>() 
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a, Float128Member b) {
			TensorNorm.compute(G.CQUAD, G.QUAD, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128GeneralTensorProductMember,Float128Member> norm() {
		return NORM;
	}

	@Override
	public Procedure2<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> SCALE =
			new Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			ComplexFloat128Member tmp = G.CQUAD.construct();
			G.CQUAD.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> MULEL =
			new Procedure3<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b, ComplexFloat128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CQUAD, G.CQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b, ComplexFloat128GeneralTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CQUAD, G.CQUAD.divide(),a.rawData(),b.rawData(),c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> MUL =
			new Procedure3<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b, ComplexFloat128GeneralTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorContract.compute(G.CQUAD, i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> POWER =
			new Procedure3<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorPower.compute(G.CQUAD_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat128GeneralTensorProductMember,ComplexFloat128GeneralTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat128GeneralTensorProductMember> UNITY =
			new Procedure1<ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember result) {
			TensorUnity.compute(G.CQUAD_TEN, G.CQUAD, result);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128GeneralTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexFloat128GeneralTensorProductMember> ISNAN =
			new Function1<Boolean, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128GeneralTensorProductMember a) {
			return SequenceIsNan.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128GeneralTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat128GeneralTensorProductMember> NAN =
			new Procedure1<ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a) {
			FillNaN.compute(G.CQUAD, a.rawData());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128GeneralTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat128GeneralTensorProductMember> ISINF =
			new Function1<Boolean, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128GeneralTensorProductMember a) {
			return SequenceIsInf.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128GeneralTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat128GeneralTensorProductMember> INF =
			new Procedure1<ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a) {
			FillInfinite.compute(G.CQUAD, a.rawData());
		}
	};
			
	@Override
	public Procedure1<ComplexFloat128GeneralTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> ROUND =
			new Procedure4<Mode, Float128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorRound.compute(G.CQUAD_TEN, G.CQUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat128GeneralTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128GeneralTensorProductMember a) {
			return SequenceIsZero.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128GeneralTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> SBD =
			new Procedure3<Double, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> WITHIN =
			new Function3<Boolean, Float128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(Float128Member tol, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member factor, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member factor, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			ComplexFloat128Member invFactor = G.CQUAD.construct();
			G.CQUAD.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}

	private final Procedure4<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> RAISE =
			new Procedure4<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
		{
			@Override
			public void call(Integer idx, ComplexFloat128GeneralTensorProductMember metricInverse, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {

				TensorFlipIndex.compute(G.CQUAD, metricInverse, idx, IndexType.CONTRAVARIANT, a, b);
			}
		};
		
	@Override
	public Procedure4<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> raiseIndex() {
		return RAISE;
	}
		
	private final Procedure4<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> LOWER =
		new Procedure4<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat128GeneralTensorProductMember metric, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {

			TensorFlipIndex.compute(G.CQUAD, metric, idx, IndexType.COVARIANT, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b, ComplexFloat128GeneralTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexFloat128GeneralTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> OUTER =
			new Procedure3<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b, ComplexFloat128GeneralTensorProductMember c) {

			TensorOuterProduct.compute(G.CQUAD_TEN, G.CQUAD, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CQUAD_TEN, G.CQUAD, new ComplexFloat128Member(BigDecimal.valueOf(2), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat128GeneralTensorProductMember a, ComplexFloat128GeneralTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CQUAD_TEN, G.CQUAD, new ComplexFloat128Member(BigDecimal.valueOf(0.5), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat128GeneralTensorProductMember, ComplexFloat128GeneralTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat128GeneralTensorProductMember> ISUNITY =
			new Function1<Boolean, ComplexFloat128GeneralTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128GeneralTensorProductMember a) {
			return TensorIsUnity.compute(G.CQUAD, a);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat128GeneralTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public ComplexFloat128Algebra getElementAlgebra() {
		return G.CQUAD;
	}

	@Override
	public ComplexFloat128GeneralTensorProductMember construct(IndexType[] indexTypes, long[] axisSizes)
	{
		return new ComplexFloat128GeneralTensorProductMember(indexTypes, axisSizes);
	}
}
