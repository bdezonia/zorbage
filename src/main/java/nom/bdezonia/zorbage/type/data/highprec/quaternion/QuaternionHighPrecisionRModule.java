/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
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
package nom.bdezonia.zorbage.type.data.highprec.quaternion;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDefaultNorm;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByDouble;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByHighPrec;
import nom.bdezonia.zorbage.algorithm.RModuleScaleByRational;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.RModuleZero;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.algorithm.SequencesSimilar;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Products;
import nom.bdezonia.zorbage.type.algebra.RModule;
import nom.bdezonia.zorbage.type.algebra.ScaleByDouble;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class QuaternionHighPrecisionRModule
  implements
    RModule<QuaternionHighPrecisionRModule,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionAlgebra,QuaternionHighPrecisionMember>,
    Constructible1dLong<QuaternionHighPrecisionRModuleMember>,
    Norm<QuaternionHighPrecisionRModuleMember,HighPrecisionMember>,
    Products<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember, QuaternionHighPrecisionMatrixMember>,
    DirectProduct<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMatrixMember>,
    ScaleByHighPrec<QuaternionHighPrecisionRModuleMember>,
    ScaleByRational<QuaternionHighPrecisionRModuleMember>,
    ScaleByDouble<QuaternionHighPrecisionRModuleMember>,
    Tolerance<HighPrecisionMember,QuaternionHighPrecisionRModuleMember>
{
	public QuaternionHighPrecisionRModule() { }
	
	private final Procedure1<QuaternionHighPrecisionRModuleMember> ZER = 
			new Procedure1<QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a) {
			RModuleZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<QuaternionHighPrecisionRModuleMember> zero() {
		return ZER;
	}
	
	private final Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> NEG =
			new Procedure2<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			RModuleNegate.compute(G.QHP, a, b);
		}
	};
	

	@Override
	public Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> negate() {
		return NEG;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> ADD =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c)
		{
			RModuleAdd.compute(G.QHP, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> add() {
		return ADD;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> SUB =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c)
		{
			RModuleSubtract.compute(G.QHP, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> subtract() {
		return SUB;
	}

	private Function2<Boolean,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> EQ =
			new Function2<Boolean, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			return (Boolean) RModuleEqual.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> NEQ =
			new Function2<Boolean, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			return !isEqual().call(a,b);
		}
	};
	
	@Override
	public Function2<Boolean,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct() {
		return new QuaternionHighPrecisionRModuleMember();
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(QuaternionHighPrecisionRModuleMember other) {
		return new QuaternionHighPrecisionRModuleMember(other);
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(String s) {
		return new QuaternionHighPrecisionRModuleMember(s);
	}

	@Override
	public QuaternionHighPrecisionRModuleMember construct(StorageConstruction s, long d1) {
		return new QuaternionHighPrecisionRModuleMember(s, d1);
	}

	private final Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> ASSIGN = 
			new Procedure2<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember from, QuaternionHighPrecisionRModuleMember to) {
			RModuleAssign.compute(G.QHP, from, to);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<QuaternionHighPrecisionRModuleMember,HighPrecisionMember> NORM =
			new Procedure2<QuaternionHighPrecisionRModuleMember, HighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, HighPrecisionMember b) {
			RModuleDefaultNorm.compute(G.QHP, G.HP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionRModuleMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> SCALE =
			new Procedure3<QuaternionHighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionMember scalar, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			RModuleScale.compute(G.QHP, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> scale() {
		return SCALE;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> CROSS =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c)
		{
			CrossProduct.compute(G.QHP_RMOD, G.QHP, a, b, c);
		}
	};

	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> DOT =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionMember c) {
			DotProduct.compute(G.QHP_RMOD, G.QHP, G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> dotProduct() {
		return DOT;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> PERP =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionMember c) {
			PerpDotProduct.compute(G.QHP_RMOD, G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> VTRIPLE =
			new Procedure4<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c, QuaternionHighPrecisionRModuleMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*4];
			for (int i = 0; i < arr.length; i++)
				arr[i] = BigDecimal.ZERO;
			QuaternionHighPrecisionRModuleMember b_cross_c = new QuaternionHighPrecisionRModuleMember(arr);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> STRIPLE =
			new Procedure4<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b,
				QuaternionHighPrecisionRModuleMember c, QuaternionHighPrecisionMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*4];
			for (int i = 0; i < arr.length; i++)
				arr[i] = BigDecimal.ZERO;
			QuaternionHighPrecisionRModuleMember b_cross_c = new QuaternionHighPrecisionRModuleMember(arr);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMember> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> CONJ =
			new Procedure2<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			RModuleConjugate.compute(G.QHP, a, b);
		}
	};
	
	@Override
	public Procedure2<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMatrixMember> VDP =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember in1, QuaternionHighPrecisionRModuleMember in2,
				QuaternionHighPrecisionMatrixMember out)
		{
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMatrixMember> DP =
			new Procedure3<QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionMatrixMember>()
	{
		@Override
		public void call(QuaternionHighPrecisionRModuleMember in1, QuaternionHighPrecisionRModuleMember in2,
				QuaternionHighPrecisionMatrixMember out)
		{
			RModuleDirectProduct.compute(G.QHP, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionRModuleMember,QuaternionHighPrecisionMatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, QuaternionHighPrecisionRModuleMember> ISZERO =
			new Function1<Boolean, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(QuaternionHighPrecisionRModuleMember a) {
			return SequenceIsZero.compute(G.QHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, QuaternionHighPrecisionRModuleMember> isZero() {
		return ISZERO;
	}

	private Procedure3<HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SBHP =
			new Procedure3<HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(HighPrecisionMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionRModuleMember c) {
			RModuleScaleByHighPrec.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> scaleByHighPrec() {
		return SBHP;
	}

	private Procedure3<RationalMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SBR =
			new Procedure3<RationalMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(RationalMember a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionRModuleMember c) {
			RModuleScaleByRational.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<RationalMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> scaleByRational() {
		return SBR;
	}

	private Procedure3<Double, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> SBD =
			new Procedure3<Double, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public void call(Double a, QuaternionHighPrecisionRModuleMember b, QuaternionHighPrecisionRModuleMember c) {
			RModuleScaleByDouble.compute(G.QHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<Double, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> scaleByDouble() {
		return SBD;
	}

	private final Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, QuaternionHighPrecisionRModuleMember a, QuaternionHighPrecisionRModuleMember b) {
			return SequencesSimilar.compute(G.QHP, tol, a.rawData(), b.rawData());
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, QuaternionHighPrecisionRModuleMember, QuaternionHighPrecisionRModuleMember> within() {
		return WITHIN;
	}

}
