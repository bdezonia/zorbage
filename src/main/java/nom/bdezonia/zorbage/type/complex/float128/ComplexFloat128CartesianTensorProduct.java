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
import nom.bdezonia.zorbage.algorithm.TensorCommaDerivative;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorIsUnity;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
import nom.bdezonia.zorbage.algorithm.TensorOuterProduct;
import nom.bdezonia.zorbage.algorithm.TensorPower;
import nom.bdezonia.zorbage.algorithm.TensorRound;
import nom.bdezonia.zorbage.algorithm.TensorSemicolonDerivative;
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
import nom.bdezonia.zorbage.type.real.float128.Float128Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat128CartesianTensorProduct
	implements
		TensorProduct<ComplexFloat128CartesianTensorProduct,ComplexFloat128CartesianTensorProductMember,ComplexFloat128Algebra,ComplexFloat128Member>,
		Norm<ComplexFloat128CartesianTensorProductMember,Float128Member>,
		Scale<ComplexFloat128CartesianTensorProductMember,ComplexFloat128Member>,
		Rounding<Float128Member,ComplexFloat128CartesianTensorProductMember>,
		Infinite<ComplexFloat128CartesianTensorProductMember>,
		NaN<ComplexFloat128CartesianTensorProductMember>,
		ScaleByHighPrec<ComplexFloat128CartesianTensorProductMember>,
		ScaleByRational<ComplexFloat128CartesianTensorProductMember>,
		ScaleByDouble<ComplexFloat128CartesianTensorProductMember>,
		ScaleByOneHalf<ComplexFloat128CartesianTensorProductMember>,
		ScaleByTwo<ComplexFloat128CartesianTensorProductMember>,
		Tolerance<Float128Member, ComplexFloat128CartesianTensorProductMember>,
		ArrayLikeMethods<ComplexFloat128CartesianTensorProductMember, ComplexFloat128Member>
{
	@Override
	public String typeDescription() {
		return "128-bit based complex tensor";
	}
	
	@Override
	public ComplexFloat128CartesianTensorProductMember construct() {
		return new ComplexFloat128CartesianTensorProductMember();
	}

	@Override
	public ComplexFloat128CartesianTensorProductMember construct(ComplexFloat128CartesianTensorProductMember other) {
		return new ComplexFloat128CartesianTensorProductMember(other);
	}

	@Override
	public ComplexFloat128CartesianTensorProductMember construct(String s) {
		return new ComplexFloat128CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> EQ =
			new Function2<Boolean,ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CQUAD, G.QUAD.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> NEQ =
			new Function2<Boolean,ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> ASSIGN =
			new Procedure2<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember from, ComplexFloat128CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.CQUAD, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat128CartesianTensorProductMember> ZER =
			new Procedure1<ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> NEG =
			new Procedure2<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CQUAD, G.CQUAD.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> ADDEL =
			new Procedure3<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b, ComplexFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CQUAD, G.CQUAD.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> SUBEL =
			new Procedure3<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b, ComplexFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CQUAD, G.CQUAD.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexFloat128CartesianTensorProductMember,Float128Member> NORM =
			new Procedure2<ComplexFloat128CartesianTensorProductMember, Float128Member>() 
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, Float128Member b) {
			TensorNorm.compute(G.CQUAD, G.QUAD, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexFloat128CartesianTensorProductMember,Float128Member> norm() {
		return NORM;
	}

	private final Procedure2<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> CONJ =
			new Procedure2<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CQUAD, G.CQUAD.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> SCALE =
			new Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CQUAD, G.CQUAD.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexFloat128Member,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member scalar, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			ComplexFloat128Member tmp = G.CQUAD.construct();
			G.CQUAD.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> MULEL =
			new Procedure3<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b, ComplexFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CQUAD, G.CQUAD.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b, ComplexFloat128CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CQUAD, G.CQUAD.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> MUL =
			new Procedure3<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b, ComplexFloat128CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorContract.compute(G.CQUAD, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.CQUAD_TEN, G.CQUAD, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.CQUAD_TEN, G.CQUAD, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> POWER =
			new Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorPower.compute(G.CQUAD_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat128CartesianTensorProductMember,ComplexFloat128CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat128CartesianTensorProductMember> UNITY =
			new Procedure1<ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember result) {
			TensorUnity.compute(G.CQUAD_TEN, G.CQUAD, result);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexFloat128CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat128CartesianTensorProductMember> NAN =
			new Procedure1<ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a) {
			FillNaN.compute(G.CQUAD, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat128CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat128CartesianTensorProductMember> ISINF =
			new Function1<Boolean, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat128CartesianTensorProductMember> INF =
			new Procedure1<ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a) {
			FillInfinite.compute(G.CQUAD, a);
		}
	};
			
	@Override
	public Procedure1<ComplexFloat128CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float128Member delta, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorRound.compute(G.CQUAD_TEN, G.CQUAD, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat128CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.CQUAD, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat128CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> SBD =
			new Procedure3<Double, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CQUAD, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float128Member tol, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CQUAD, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member factor, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128Member factor, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			ComplexFloat128Member invFactor = G.CQUAD.construct();
			G.CQUAD.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat128Member, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b, ComplexFloat128CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexFloat128CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> OUTER =
			new Procedure3<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b, ComplexFloat128CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.CQUAD_TEN, G.CQUAD, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CQUAD_TEN, G.CQUAD, new ComplexFloat128Member(BigDecimal.valueOf(2), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat128CartesianTensorProductMember a, ComplexFloat128CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CQUAD_TEN, G.CQUAD, new ComplexFloat128Member(BigDecimal.valueOf(0.5), BigDecimal.ZERO), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat128CartesianTensorProductMember, ComplexFloat128CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat128CartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, ComplexFloat128CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat128CartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.CQUAD, a);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat128CartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
