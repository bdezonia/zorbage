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
package nom.bdezonia.zorbage.type.data.highprec.complex;

import java.lang.Integer;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Copy;
import nom.bdezonia.zorbage.algorithm.FixedTransform2;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.algorithm.TensorContract;
import nom.bdezonia.zorbage.algorithm.TensorNorm;
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
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;

//note that many implementations of tensors on the internet treat them as generalized matrices.
//they do not worry about upper and lower indices or even matching shapes. They do element by
//element ops like sin() of each elem. That will not be my approach.

//do I skip Vector and Matrix and even Scalar?

// this implies it is an OrderedField. Do tensors have an ordering? abs() exists in TensorFlow.
//@Override
//public void abs(TensorMember a, TensorMember b) {}
// tensorflow also has trigonometric and hyperbolic

//public void takeDiagonal(TensorMember a, Object b) {} // change Object to Vector
//many more

import nom.bdezonia.zorbage.type.algebra.TensorProduct;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.ctor.ConstructibleNdLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

// Note that for now the implementation is only for Cartesian tensors

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexHighPrecisionTensorProduct
	implements
		TensorProduct<ComplexHighPrecisionTensorProduct,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionAlgebra,ComplexHighPrecisionMember>,
		ConstructibleNdLong<ComplexHighPrecisionTensorProductMember>,
		Norm<ComplexHighPrecisionTensorProductMember,HighPrecisionMember>,
		Scale<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionMember>,
		ScaleByHighPrec<ComplexHighPrecisionTensorProductMember>,
		ScaleByRational<ComplexHighPrecisionTensorProductMember>,
		ScaleByDouble<ComplexHighPrecisionTensorProductMember>,
		Tolerance<HighPrecisionMember, ComplexHighPrecisionTensorProductMember>
{
	@Override
	public ComplexHighPrecisionTensorProductMember construct() {
		return new ComplexHighPrecisionTensorProductMember();
	}

	@Override
	public ComplexHighPrecisionTensorProductMember construct(ComplexHighPrecisionTensorProductMember other) {
		return new ComplexHighPrecisionTensorProductMember(other);
	}

	@Override
	public ComplexHighPrecisionTensorProductMember construct(String s) {
		return new ComplexHighPrecisionTensorProductMember(s);
	}

	@Override
	public ComplexHighPrecisionTensorProductMember construct(StorageConstruction s, long[] nd) {
		return new ComplexHighPrecisionTensorProductMember(s, nd);
	}

	private final Function2<Boolean,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> EQ =
			new Function2<Boolean,ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			if (!shapesMatch(a, b))
				return false;
			return SequencesSimilar.compute(G.CHP, G.HP.construct(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Function2<Boolean,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> NEQ =
			new Function2<Boolean,ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			return !isEqual().call(a, b);
		}
	};
	
	@Override
	public Function2<Boolean,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> isNotEqual() {
		return NEQ;
	}

	private final Procedure2<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> ASSIGN =
			new Procedure2<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember from, ComplexHighPrecisionTensorProductMember to) {
			shapeResult(from, to);
			Copy.compute(G.CHP, from.rawData(), to.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> assign() {
		return ASSIGN;
	}

	private final Procedure1<ComplexHighPrecisionTensorProductMember> ZER =
			new Procedure1<ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionTensorProductMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> NEG =
			new Procedure2<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			Transform2.compute(G.CHP, G.CHP.negate(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> ADDEL =
			new Procedure3<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b, ComplexHighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("tensor add shape mismatch");
			shapeResult(a, c);
			Transform3.compute(G.CHP, G.CHP.add(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> add() {
		return ADDEL;
	}

	private final Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> SUBEL =
			new Procedure3<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b, ComplexHighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("tensor subtract shape mismatch");
			shapeResult(a, c);
			Transform3.compute(G.CHP, G.CHP.subtract(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> subtract() {
		return SUBEL;
	}

	private final Procedure2<ComplexHighPrecisionTensorProductMember,HighPrecisionMember> NORM =
			new Procedure2<ComplexHighPrecisionTensorProductMember, HighPrecisionMember>() 
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, HighPrecisionMember b) {
			TensorNorm.compute(G.CHP, G.HP, a.rawData(), b);
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionTensorProductMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure2<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> CONJ =
			new Procedure2<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			Transform2.compute(G.CHP, G.CHP.conjugate(), a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure2<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> SCALE =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.Scale.compute(G.CHP, scalar, a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> ADDSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			FixedTransform2.compute(G.CHP, scalar, G.CHP.add(), a.rawData(), b.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> addScalar() {
		return ADDSCALAR;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> SUBSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			ComplexHighPrecisionMember tmp = G.CHP.construct();
			G.CHP.negate().call(scalar, tmp);
			addScalar().call(tmp, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> subtractScalar() {
		return SUBSCALAR;
	}

	private Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> MULEL =
			new Procedure3<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b, ComplexHighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			shapeResult(a, c);
			Transform3.compute(G.CHP, G.CHP.multiply(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> multiplyElements() {
		return MULEL;
	}
	
	private Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> DIVIDEEL =
			new Procedure3<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b, ComplexHighPrecisionTensorProductMember c) {
			if (!shapesMatch(a, b))
				throw new IllegalArgumentException("mismatched shapes");
			shapeResult(a, c);
			Transform3.compute(G.CHP, G.CHP.divide(), a.rawData(), b.rawData(), c.rawData());
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> divideElements() {
		return DIVIDEEL;
	}

	private final Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> MUL =
			new Procedure3<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b, ComplexHighPrecisionTensorProductMember c) {

			// multiplication is a tensor product
			// https://www.math3ma.com/blog/the-tensor-product-demystified
			
			outerProduct().call(a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> multiply() {
		return MUL;
	}
	
	private final Procedure4<Integer,Integer,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> CONTRACT =
			new Procedure4<Integer,Integer,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer i, Integer j, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			TensorContract.compute(G.CHP, a.rank(), i, j, a, b);
		}
	};
	
	@Override
	public Procedure4<Integer,Integer,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> contract() {
		return CONTRACT;
	}
		
	// http://mathworld.wolfram.com/CovariantDerivative.html

	private final Procedure1<Object> SEMI =
			new Procedure1<Object>()
	{
		@Override
		public void call(Object a) {
			throw new IllegalArgumentException("TODO - implement semicolonDerivative()");
		}
	};
	
	@Override
	public Procedure1<Object> semicolonDerivative() {
		return SEMI;
	}
	
	// http://mathworld.wolfram.com/CommaDerivative.html
	
	private final Procedure3<IntegerIndex,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> COMMA =
			new Procedure3<IntegerIndex,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(IntegerIndex index, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			ComplexHighPrecisionMember val = G.CHP.construct();
			a.v(index, val);
			divideByScalar().call(val, a, b);
		}
	};
	
	@Override
	public Procedure3<IntegerIndex,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> commaDerivative() {
		return COMMA;
	}
	// TODO - make much more efficient by copying style of MatrixMultiply algorithm
	
	private final Procedure3<Integer,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> POWER =
			new Procedure3<Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer power, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			if (power < 0) {
				// TODO: is this a valid limitation?
				throw new IllegalArgumentException("negative powers not supported");
			}
			else if (power == 0) {
				if (isZero().call(a)) {
					throw new IllegalArgumentException("0^0 is not a number");
				}
				shapeResult(a, b); // set the shape of result
				unity().call(b); // and make it have value 1
			}
			else if (power == 1) {
				assign().call(a, b);
			}
			else {
				ComplexHighPrecisionTensorProductMember tmp1 = construct();
				ComplexHighPrecisionTensorProductMember tmp2 = construct();
				multiply().call(a, a, tmp1);
				for (int i = 2; i < (power/2)*2; i += 2) {
					multiply().call(tmp1, a, tmp2);
					multiply().call(tmp2, a, tmp1);
				}
				// an odd power
				if (power > 2 && (power&1)==1) {
					assign().call(tmp1, tmp2);
					multiply().call(tmp2, a, tmp1);
				}
				assign().call(tmp1, b);
			}
		}
	};
	
	@Override
	public Procedure3<Integer,ComplexHighPrecisionTensorProductMember,ComplexHighPrecisionTensorProductMember> power() {
		return POWER;
	}

	private final Procedure1<ComplexHighPrecisionTensorProductMember> UNITY =
			new Procedure1<ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember result) {
			ComplexHighPrecisionMember one = G.CHP.construct();
			G.CHP.unity().call(one);
			zero().call(result);
			IntegerIndex index = new IntegerIndex(result.rank());
			for (long d = 0; d < result.dimension(0); d++) {
				for (int r = 0; r < result.rank(); r++) {
					index.set(r, d);
				}
				result.setV(index, one);
			}
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionTensorProductMember> unity() {
		return UNITY;
	}

	private final Function1<Boolean, ComplexHighPrecisionTensorProductMember> ISZERO =
			new Function1<Boolean, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionTensorProductMember a) {
			return SequenceIsZero.compute(G.CHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexHighPrecisionTensorProductMember> isZero() {
		return ISZERO;
	}

	private final Procedure3<RationalMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> SBR =
			new Procedure3<RationalMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(RationalMember factor, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByRational.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<RationalMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> SBD =
			new Procedure3<Double, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Double factor, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByDouble.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<Double, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> scaleByDouble() {
		return SBD;
	}

	private final Procedure3<HighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> SBHP =
			new Procedure3<HighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(HighPrecisionMember factor, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			shapeResult(a, b);
			nom.bdezonia.zorbage.algorithm.ScaleByHighPrec.compute(G.CHP, factor, a.rawData(), b.rawData());
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> scaleByHighPrec() {
		return SBHP;
	}

	private final Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			if (a.numDimensions() != b.numDimensions())
				return false;
			for (int i = 0; i < a.numDimensions(); i++) {
				if (a.dimension(i) != b.dimension(i))
					return false;
			}
			return SequencesSimilar.compute(G.CHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> within() {
		return WITHIN;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> MULBYSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember factor, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			scale().call(factor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> multiplyByScalar() {
		return MULBYSCALAR;
	}

	private final Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> DIVBYSCALAR =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember factor, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			ComplexHighPrecisionMember invFactor = G.CHP.construct();
			G.CHP.invert().call(factor, invFactor);
			scale().call(invFactor, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> divideByScalar() {
		return DIVBYSCALAR;
	}
	
	private final Procedure3<Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> RAISE =
		new Procedure3<Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in raiseIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> raiseIndex() {
		return RAISE;
	}

	private final Procedure3<Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> LOWER =
		new Procedure3<Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer idx, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
			
			if (idx < 0 || idx >= a.rank())
				throw new IllegalArgumentException("index outside rank bounds in lowerIndex");
			
			// this operation should not affect a cartesian tensor
			
			assign().call(a, b);
		}
	};
	
	@Override
	public Procedure3<Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> lowerIndex() {
		return LOWER;
	}

	private final Procedure5<Integer, Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> INNER =
		new Procedure5<Integer, Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(Integer aIndex, Integer bIndex, ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b, ComplexHighPrecisionTensorProductMember c) {
			
			if (aIndex < 0 || bIndex < 0)
				throw new IllegalArgumentException("tensor innerProduct() cannot handle negative indices");
			if (aIndex >= a.rank() || bIndex >= b.rank())
				throw new IllegalArgumentException("tensor innerProduct() cannot handle out of bounds indices");
			ComplexHighPrecisionTensorProductMember tmp = construct();
			outerProduct().call(a, b, tmp);
			contract().call(aIndex, a.rank() + bIndex, tmp, c);
		}
	};
		
	@Override
	public Procedure5<Integer, Integer, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> innerProduct() {
		return INNER;
	}

	private final Procedure3<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> OUTER =
			new Procedure3<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember>()
	{
		@Override
		public void call(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b, ComplexHighPrecisionTensorProductMember c) {

			// how an outer product is calculated:
			//   https://www.math3ma.com/blog/the-tensor-product-demystified
			
			if (c == a || c == b)
				throw new IllegalArgumentException("destination tensor cannot be one of the inputs");
			long dimA = a.dimension(0);
			long dimB = b.dimension(0);
			if (dimA != dimB)
				throw new IllegalArgumentException("dimension of tensors must match");
			int rankA = a.numDimensions();
			int rankB = b.numDimensions();
			int rankC = rankA + rankB;
			long[] cDims = new long[rankC];
			for (int i = 0; i < cDims.length; i++) {
				cDims[i] = dimA;
			}
			c.alloc(cDims);
			ComplexHighPrecisionMember aTmp = G.CHP.construct();
			ComplexHighPrecisionMember bTmp = G.CHP.construct();
			ComplexHighPrecisionMember cTmp = G.CHP.construct();
			long k = 0;
			long numElemsA = a.numElems();
			long numElemsB = b.numElems();
			for (long i = 0; i < numElemsA; i++) {
				a.v(i, aTmp);
				for (long j = 0; j < numElemsB; j++) {
					b.v(j, bTmp);
					G.CHP.multiply().call(aTmp, bTmp, cTmp);
					c.setV(k, cTmp);
					k++;
				}
			}
		}
	};
			
	@Override
	public Procedure3<ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember, ComplexHighPrecisionTensorProductMember> outerProduct() {
		return OUTER;
	}
	
	private void shapeResult(ComplexHighPrecisionTensorProductMember from, ComplexHighPrecisionTensorProductMember to) {
		if (from == to) return;
		long[] dims = new long[from.numDimensions()];
		for (int i = 0; i < dims.length; i++) {
			dims[i] = from.dimension(i);
		}
		to.alloc(dims);
	}

	private boolean shapesMatch(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
		if (a.numDimensions() != b.numDimensions())
			return false;
		for (int i = 0; i < a.numDimensions(); i++) {
			if (a.dimension(i) != b.dimension(i))
				return false;
		}
		return true;
	}

	/* future version
	
	private boolean shapesMatch(ComplexHighPrecisionTensorProductMember a, ComplexHighPrecisionTensorProductMember b) {
		int i = 0;
		int j = 0;
		while (i < a.numDimensions() && j < b.numDimensions()) {
			while (i < a.numDimensions() && a.dimension(i) == 1) i++;
			while (j < b.numDimensions() && b.dimension(i) == 1) j++;
			if (i < a.numDimensions() && j < b.numDimensions() && a.dimension(i) != b.dimension(j))
				return false;
			else {
				i++;
				j++;
			}
		}
		while (i < a.numDimensions() && a.dimension(i) == 1) i++;
		while (j < b.numDimensions() && b.dimension(i) == 1) j++;
		if (i != a.numDimensions() || j != b.numDimensions())
			return false;
		return true;
	}
	
	*/

}
