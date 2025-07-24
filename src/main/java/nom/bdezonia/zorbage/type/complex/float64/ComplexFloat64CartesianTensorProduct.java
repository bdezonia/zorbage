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
import nom.bdezonia.zorbage.type.real.float64.Float64Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat64CartesianTensorProduct
	implements
		TensorProduct<ComplexFloat64CartesianTensorProduct,ComplexFloat64CartesianTensorProductMember,ComplexFloat64Algebra,ComplexFloat64Member>,
		Norm<ComplexFloat64CartesianTensorProductMember,Float64Member>,
		Scale<ComplexFloat64CartesianTensorProductMember,ComplexFloat64Member>,
		Rounding<Float64Member,ComplexFloat64CartesianTensorProductMember>,
		Infinite<ComplexFloat64CartesianTensorProductMember>,
		NaN<ComplexFloat64CartesianTensorProductMember>,
		ScaleByHighPrec<ComplexFloat64CartesianTensorProductMember>,
		ScaleByRational<ComplexFloat64CartesianTensorProductMember>,
		ScaleByDouble<ComplexFloat64CartesianTensorProductMember>,
		ScaleByOneHalf<ComplexFloat64CartesianTensorProductMember>,
		ScaleByTwo<ComplexFloat64CartesianTensorProductMember>,
		Tolerance<Float64Member, ComplexFloat64CartesianTensorProductMember>,
		ArrayLikeMethods<ComplexFloat64CartesianTensorProductMember, ComplexFloat64Member>,
		MadeOfElements<ComplexFloat64Algebra,ComplexFloat64Member>
{

	@Override
	public String typeDescription() {
		return "64-bit based complex tensor";
	}

	@Override
	public ComplexFloat64CartesianTensorProductMember construct() {
		return new ComplexFloat64CartesianTensorProductMember();
	}

	@Override
	public ComplexFloat64CartesianTensorProductMember construct(ComplexFloat64CartesianTensorProductMember other) {
		return new ComplexFloat64CartesianTensorProductMember(other);
	}

	@Override
	public ComplexFloat64CartesianTensorProductMember construct(String s) {
		return new ComplexFloat64CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> EQ =
			new Function2<Boolean,ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CDBL, G.DBL.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> NEQ =
			new Function2<Boolean,ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> ASSIGN =
			new Procedure2<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember from, ComplexFloat64CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.CDBL, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat64CartesianTensorProductMember> ZER =
			new Procedure1<ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> NEG =
			new Procedure2<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CDBL, G.CDBL.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> ADDEL =
			new Procedure3<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b, ComplexFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CDBL, G.CDBL.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> SUBEL =
			new Procedure3<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b, ComplexFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CDBL, G.CDBL.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexFloat64CartesianTensorProductMember,Float64Member> NORM =
			new Procedure2<ComplexFloat64CartesianTensorProductMember, Float64Member>() 
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, Float64Member b) {
			TensorNorm.compute(G.CDBL, G.DBL, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexFloat64CartesianTensorProductMember,Float64Member> norm() {
		return NORM;
	}

	private final Procedure2<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> CONJ =
			new Procedure2<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CDBL, G.CDBL.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> SCALE =
			new Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CDBL, G.CDBL.multiply(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			TransformWithConstant.compute(G.CDBL, G.CDBL.add(), a.rawData(), scalar, b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexFloat64Member,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member scalar, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			ComplexFloat64Member tmp = G.CDBL.construct();
			G.CDBL.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> MULEL =
			new Procedure3<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b, ComplexFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CDBL, G.CDBL.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b, ComplexFloat64CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CDBL, G.CDBL.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> MUL =
			new Procedure3<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b, ComplexFloat64CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorContract.compute(G.CDBL, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.CDBL_TEN, G.CDBL, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.CDBL_TEN, G.CDBL, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> POWER =
			new Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorPower.compute(G.CDBL_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat64CartesianTensorProductMember,ComplexFloat64CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat64CartesianTensorProductMember> UNITY =
			new Procedure1<ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember result) {
			TensorUnity.compute(G.CDBL_TEN, G.CDBL, result);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexFloat64CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat64CartesianTensorProductMember> NAN =
			new Procedure1<ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a) {
			FillNaN.compute(G.CDBL, a.rawData());
		}
	};
	
	@Override
	public Procedure1<ComplexFloat64CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat64CartesianTensorProductMember> ISINF =
			new Function1<Boolean, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat64CartesianTensorProductMember> INF =
			new Procedure1<ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a) {
			FillInfinite.compute(G.CDBL, a.rawData());
		}
	};
			
	@Override
	public Procedure1<ComplexFloat64CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float64Member delta, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorRound.compute(G.CDBL_TEN, G.CDBL, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat64CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.CDBL, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat64CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> SBD =
			new Procedure3<Double, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CDBL, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float64Member tol, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CDBL, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member factor, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64Member factor, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			ComplexFloat64Member invFactor = G.CDBL.construct();
			G.CDBL.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat64Member, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b, ComplexFloat64CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexFloat64CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> OUTER =
			new Procedure3<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b, ComplexFloat64CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.CDBL_TEN, G.CDBL, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CDBL_TEN, G.CDBL, new ComplexFloat64Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat64CartesianTensorProductMember a, ComplexFloat64CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CDBL_TEN, G.CDBL, new ComplexFloat64Member(0.5, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat64CartesianTensorProductMember, ComplexFloat64CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat64CartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, ComplexFloat64CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat64CartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.CDBL, a);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat64CartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
	
	@Override
	public Algebra<ComplexFloat64Algebra, ComplexFloat64Member> getElementAlgebra() {
		return G.CDBL;
	}
}
