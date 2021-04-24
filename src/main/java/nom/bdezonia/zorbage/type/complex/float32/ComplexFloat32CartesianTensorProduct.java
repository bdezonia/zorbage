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
package nom.bdezonia.zorbage.type.complex.float32;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Round.Mode;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FillInfinite;
import nom.bdezonia.zorbage.algorithm.FillNaN;
import nom.bdezonia.zorbage.algorithm.FixedTransform2a;
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
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.procedure.Procedure5;
import nom.bdezonia.zorbage.type.rational.RationalMember;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;
import nom.bdezonia.zorbage.type.real.highprec.HighPrecisionMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexFloat32CartesianTensorProduct
	implements
		TensorProduct<ComplexFloat32CartesianTensorProduct,ComplexFloat32CartesianTensorProductMember,ComplexFloat32Algebra,ComplexFloat32Member>,
		Norm<ComplexFloat32CartesianTensorProductMember,Float32Member>,
		Scale<ComplexFloat32CartesianTensorProductMember,ComplexFloat32Member>,
		Rounding<Float32Member,ComplexFloat32CartesianTensorProductMember>,
		Infinite<ComplexFloat32CartesianTensorProductMember>,
		NaN<ComplexFloat32CartesianTensorProductMember>,
		ScaleByHighPrec<ComplexFloat32CartesianTensorProductMember>,
		ScaleByRational<ComplexFloat32CartesianTensorProductMember>,
		ScaleByDouble<ComplexFloat32CartesianTensorProductMember>,
		ScaleByOneHalf<ComplexFloat32CartesianTensorProductMember>,
		ScaleByTwo<ComplexFloat32CartesianTensorProductMember>,
		Tolerance<Float32Member, ComplexFloat32CartesianTensorProductMember>,
		ArrayLikeMethods<ComplexFloat32CartesianTensorProductMember, ComplexFloat32Member>
{
	@Override
	public ComplexFloat32CartesianTensorProductMember construct() {
		return new ComplexFloat32CartesianTensorProductMember();
	}

	@Override
	public ComplexFloat32CartesianTensorProductMember construct(ComplexFloat32CartesianTensorProductMember other) {
		return new ComplexFloat32CartesianTensorProductMember(other);
	}

	@Override
	public ComplexFloat32CartesianTensorProductMember construct(String s) {
		return new ComplexFloat32CartesianTensorProductMember(s);
	}

	private final Function2<Boolean,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> EQ =
			new Function2<Boolean,ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CFLT, G.FLT.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> NEQ =
			new Function2<Boolean,ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> ASSIGN =
			new Procedure2<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember from, ComplexFloat32CartesianTensorProductMember to) {
			if (from == to) return;
			TensorShape.compute(from, to);
			Copy.compute(G.CFLT, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexFloat32CartesianTensorProductMember> ZER =
			new Procedure1<ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32CartesianTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> NEG =
			new Procedure2<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CFLT, G.CFLT.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> ADDEL =
			new Procedure3<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b, ComplexFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> SUBEL =
			new Procedure3<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b, ComplexFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexFloat32CartesianTensorProductMember,Float32Member> NORM =
			new Procedure2<ComplexFloat32CartesianTensorProductMember, Float32Member>() 
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, Float32Member b) {
			TensorNorm.compute(G.CFLT, G.FLT, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexFloat32CartesianTensorProductMember,Float32Member> norm() {
		return NORM;
	}

	private final Procedure2<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> CONJ =
			new Procedure2<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			Transform2.compute(G.CFLT, G.CFLT.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> SCALE =
			new Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.CFLT, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			FixedTransform2a.compute(G.CFLT, scalar, G.CFLT.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexFloat32Member,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member scalar, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			ComplexFloat32Member tmp = G.CFLT.construct();
			G.CFLT.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private final Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> MULEL =
			new Procedure3<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b, ComplexFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private final Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b, ComplexFloat32CartesianTensorProductMember c) {
			if (!ShapesMatch.compute(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			TensorShape.compute(a, c);
			Transform3.compute(G.CFLT, G.CFLT.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> MUL =
			new Procedure3<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b, ComplexFloat32CartesianTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorContract.compute(G.CFLT, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> contract() {
		return CONTRACT;
	}

	private final Procedure3<Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> SEMI =
			new Procedure3<Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorSemicolonDerivative.compute(G.CFLT_TEN, G.CFLT, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> semicolonDerivative() {
		return SEMI;
	}
	
	private final Procedure3<Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> COMMA =
			new Procedure3<Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer index, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorCommaDerivative.compute(G.CFLT_TEN, G.CFLT, index, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> commaDerivative() {
		return COMMA;
	}
	
	private final Procedure3<Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> POWER =
			new Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorPower.compute(G.CFLT_TEN, power, a, b);
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexFloat32CartesianTensorProductMember,ComplexFloat32CartesianTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexFloat32CartesianTensorProductMember> UNITY =
			new Procedure1<ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember result) {
			TensorUnity.compute(G.CFLT_TEN, G.CFLT, result);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32CartesianTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexFloat32CartesianTensorProductMember> ISNAN =
			new Function1<Boolean, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32CartesianTensorProductMember a) {
			return SequenceIsNan.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32CartesianTensorProductMember> isNaN() {
		return ISNAN;
	}

	private final Procedure1<ComplexFloat32CartesianTensorProductMember> NAN =
			new Procedure1<ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a) {
			FillNaN.compute(G.CFLT, a);
		}
	};
	
	@Override
	public Procedure1<ComplexFloat32CartesianTensorProductMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, ComplexFloat32CartesianTensorProductMember> ISINF =
			new Function1<Boolean, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32CartesianTensorProductMember a) {
			return SequenceIsInf.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32CartesianTensorProductMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<ComplexFloat32CartesianTensorProductMember> INF =
			new Procedure1<ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a) {
			FillInfinite.compute(G.CFLT, a);
		}
	};
			
	@Override
	public Procedure1<ComplexFloat32CartesianTensorProductMember> infinite() {
		return INF;
	}
	
	private final Procedure4<Mode, Float32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> ROUND =
			new Procedure4<Mode, Float32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Mode mode, Float32Member delta, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorRound.compute(G.CFLT_TEN, G.CFLT, mode, delta, a, b);
		}
	};

	@Override
	public Procedure4<Mode, Float32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> round() {
		return ROUND;
	}

	private final Function1<Boolean, ComplexFloat32CartesianTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32CartesianTensorProductMember a) {
			return SequenceIsZero.compute(G.CFLT, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexFloat32CartesianTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> SBD =
			new Procedure3<Double, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CFLT, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, Float32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> WITHIN =
			new Function3<Boolean, Float32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(Float32Member tol, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			if (!ShapesMatch.compute(a, b))
				return false;
			return SequencesSimilar.compute(G.CFLT, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, Float32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member factor, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32Member factor, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			ComplexFloat32Member invFactor = G.CFLT.construct();
			G.CFLT.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexFloat32Member, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> RAISE =
		new Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			
			throw new IllegalArgumentException("cannot raise index of a cartesian tensor");

		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> LOWER =
		new Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b, ComplexFloat32CartesianTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexFloat32CartesianTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> OUTER =
			new Procedure3<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b, ComplexFloat32CartesianTensorProductMember c) {

			TensorOuterProduct.compute(G.CFLT_TEN, G.CFLT, a, b, c);

		}
	};
			
	@Override
	public Procedure3<ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> outerProduct() {
		return OUTER;
	}

	private final Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> SCB2 =
			new Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CFLT_TEN, G.CFLT, new ComplexFloat32Member(2, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> scaleByTwo() {
		return SCB2;
	}

	private final Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> SCBH =
			new Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public void call(Integer numTimes, ComplexFloat32CartesianTensorProductMember a, ComplexFloat32CartesianTensorProductMember b) {
			TensorShape.compute(a, b);
			ScaleHelper.compute(G.CFLT_TEN, G.CFLT, new ComplexFloat32Member(0.5f, 0), numTimes, a, b);
		}
	};

	@Override
	public Procedure3<Integer, ComplexFloat32CartesianTensorProductMember, ComplexFloat32CartesianTensorProductMember> scaleByOneHalf() {
		return SCBH;
	}

	private final Function1<Boolean, ComplexFloat32CartesianTensorProductMember> ISUNITY =
			new Function1<Boolean, ComplexFloat32CartesianTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexFloat32CartesianTensorProductMember a) {
			return TensorIsUnity.compute(G.CFLT, a);
		}
	};
	
	@Override
	public Function1<Boolean, ComplexFloat32CartesianTensorProductMember> isUnity() {
		return ISUNITY;
	}
}
