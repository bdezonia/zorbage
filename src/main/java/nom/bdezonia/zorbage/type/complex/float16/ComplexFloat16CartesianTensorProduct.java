/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2a;
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
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.procedure.Procedure5;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float16.Float16Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat16CartesianTensorProduct
	implements
		TensorProduct<ComplexFloat16CartesianTensorProduct,ComplexFloat16CartesianTensorProductMember,ComplexFloat16Algebra,ComplexFloat16Member>,
		Norm<ComplexFloat16CartesianTensorProductMember,Float16Member>,
		Scale<ComplexFloat16CartesianTensorProductMember,ComplexFloat16Member>,
		Rounding<Float16Member,ComplexFloat16CartesianTensorProductMember>,
		Infinite<ComplexFloat16CartesianTensorProductMember>,
		NaN<ComplexFloat16CartesianTensorProductMember>,
		ScaleByHighPrec<ComplexFloat16CartesianTensorProductMember>,
		ScaleByRational<ComplexFloat16CartesianTensorProductMember>,
		ScaleByDouble<ComplexFloat16CartesianTensorProductMember>,
		ScaleByOneHalf<ComplexFloat16CartesianTensorProductMember>,
		ScaleByTwo<ComplexFloat16CartesianTensorProductMember>,
		Tolerance<Float16Member, ComplexFloat16CartesianTensorProductMember>,
		ArrayLikeMethods<ComplexFloat16CartesianTensorProductMember, ComplexFloat16Member>
{
	@Override
	public ComplexFloat16CartesianTensorProductMember construct() {
		return new ComplexFloat16CartesianTensorProductMember();
	}

	@Override
	public ComplexFloat16CartesianTensorProductMember construct(ComplexFloat16CartesianTensorProductMember other) {
		return new ComplexFloat16CartesianTensorProductMember(other);
	}

	@Override
	public ComplexFloat16CartesianTensorProductMember construct(String s) {
		return new ComplexFloat16CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> EQ =
			new Function2<Boolean,ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CHLF, G.HLF.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> NEQ =
			new Function2<Boolean,ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> ASSIGN =
			new Procedure2<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember from, ComplexFloat16CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.CHLF, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat16CartesianTensorProductMember> ZER =
			new Procedure1<ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> NEG =
			new Procedure2<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CHLF, G.CHLF.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> ADDEL =
			new Procedure3<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b, ComplexFloat16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHLF, G.CHLF.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> SUBEL =
			new Procedure3<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b, ComplexFloat16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHLF, G.CHLF.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexFloat16CartesianTensorProductMember,Float16Member> NORM =
			new Procedure2<ComplexFloat16CartesianTensorProductMember, Float16Member>() 
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, Float16Member b) {
			TensorNorm.compute(G.CHLF, G.HLF, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexFloat16CartesianTensorProductMember,Float16Member> norm() {
		return NORM;
	}

	private final Procedure2<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> CONJ =
			new Procedure2<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CHLF, G.CHLF.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> SCALE =
			new Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.CHLF, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.CHLF, scalar, G.CHLF.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexFloat16Member,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16Member scalar, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			ComplexFloat16Member tmp = G.CHLF.construct();
			G.CHLF.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> MULEL =
			new Procedure3<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b, ComplexFloat16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHLF, G.CHLF.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b, ComplexFloat16CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CHLF, G.CHLF.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> MUL =
			new Procedure3<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b, ComplexFloat16CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorContract.compute(G.CHLF, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> contract() {
		return CONTRACT;
	}
	
	private final Procedure3<Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.CHLF_TEN, G.CHLF, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.CHLF_TEN, G.CHLF, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> POWER =
			new Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorPower.compute(G.CHLF_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat16CartesianTensorProductMember,ComplexFloat16CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat16CartesianTensorProductMember> UNITY =
			new Procedure1<ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember result) {
			TensorUnity.compute(G.CHLF_TEN, G.CHLF, result);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexFloat16CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat16CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat16CartesianTensorProductMember> NAN =
			new Procedure1<ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a) {
			FillNaN.compute(G.CHLF, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat16CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat16CartesianTensorProductMember> ISINF =
			new Function1<Boolean, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat16CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat16CartesianTensorProductMember> INF =
			new Procedure1<ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a) {
			FillInfinite.compute(G.CHLF, a);
		}
	};
			
	@Override
	public Procedure1<ComplexFloat16CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float16Member delta, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorRound.compute(G.CHLF_TEN, G.CHLF, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat16CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat16CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.CHLF, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat16CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> SBD =
			new Procedure3<Double, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CHLF, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float16Member tol, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CHLF, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16Member factor, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16Member factor, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			ComplexFloat16Member invFactor = G.CHLF.construct();
			G.CHLF.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat16Member, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b, ComplexFloat16CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexFloat16CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> OUTER =
			new Procedure3<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b, ComplexFloat16CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.CHLF_TEN, G.CHLF, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CHLF_TEN, G.CHLF, new ComplexFloat16Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat16CartesianTensorProductMember a, ComplexFloat16CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CHLF_TEN, G.CHLF, new ComplexFloat16Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat16CartesianTensorProductMember, ComplexFloat16CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat16CartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, ComplexFloat16CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat16CartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.CHLF, a);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat16CartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
