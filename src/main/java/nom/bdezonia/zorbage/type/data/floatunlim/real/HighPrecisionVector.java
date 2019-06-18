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
package nom.bdezonia.zorbage.type.data.floatunlim.real;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.RModuleZero;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.procedure.Procedure4;
import nom.bdezonia.zorbage.type.algebra.DirectProduct;
import nom.bdezonia.zorbage.type.algebra.Infinite;
import nom.bdezonia.zorbage.type.algebra.NaN;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.Products;
import nom.bdezonia.zorbage.type.algebra.VectorSpace;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionVector
  implements
    VectorSpace<HighPrecisionVector,HighPrecisionVectorMember,HighPrecisionAlgebra,HighPrecisionMember>,
    Constructible1dLong<HighPrecisionVectorMember>,
	Norm<HighPrecisionVectorMember,HighPrecisionMember>,
	Products<HighPrecisionVectorMember, HighPrecisionMember, HighPrecisionMatrixMember>,
	DirectProduct<HighPrecisionVectorMember, HighPrecisionMatrixMember>,
	Infinite<HighPrecisionVectorMember>,
	NaN<HighPrecisionVectorMember>
{
	public HighPrecisionVector() { }
	
	private final Procedure1<HighPrecisionVectorMember> ZER =
			new Procedure1<HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a) {
			RModuleZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionVectorMember> zero() {
		return ZER;
	}

	private final Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> NEG =
			new Procedure2<HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			RModuleNegate.compute(G.FLOAT_UNLIM, a, b);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> negate() {
		return NEG;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> ADD =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			RModuleAdd.compute(G.FLOAT_UNLIM, a, b, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> add() {
		return ADD;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> SUB =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			RModuleSubtract.compute(G.FLOAT_UNLIM, a, b, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,HighPrecisionVectorMember,HighPrecisionVectorMember> EQ =
			new Function2<Boolean, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			return (Boolean) RModuleEqual.compute(G.FLOAT_UNLIM, a, b);
		}
	};

	@Override
	public Function2<Boolean,HighPrecisionVectorMember,HighPrecisionVectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,HighPrecisionVectorMember,HighPrecisionVectorMember> NEQ =
			new Function2<Boolean, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			return !isEqual().call(a,b);
		}
	};

	@Override
	public Function2<Boolean,HighPrecisionVectorMember,HighPrecisionVectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public HighPrecisionVectorMember construct() {
		return new HighPrecisionVectorMember();
	}

	@Override
	public HighPrecisionVectorMember construct(HighPrecisionVectorMember other) {
		return new HighPrecisionVectorMember(other);
	}

	@Override
	public HighPrecisionVectorMember construct(String s) {
		return new HighPrecisionVectorMember(s);
	}

	@Override
	public HighPrecisionVectorMember construct(StorageConstruction s, long d1) {
		return new HighPrecisionVectorMember(s, d1);
	}

	private final Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> ASSIGN =
			new Procedure2<HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember from, HighPrecisionVectorMember to) {
			RModuleAssign.compute(G.FLOAT_UNLIM, from, to);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> assign() {
		return ASSIGN;
	}

	private Procedure2<HighPrecisionVectorMember,HighPrecisionMember> NORM =
			new Procedure2<HighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionMember b) {
			HighPrecisionMember tmp = new HighPrecisionMember();
			HighPrecisionMember norm2 = new HighPrecisionMember();
			for (long i = 0; i < a.length(); i++) {
				a.v(i, tmp);
				G.FLOAT_UNLIM.multiply().call(tmp, tmp, tmp);
				G.FLOAT_UNLIM.add().call(norm2, tmp, norm2);
			}
			G.FLOAT_UNLIM.sqrt().call(norm2, tmp);
			b.setV(tmp);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionVectorMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionVectorMember,HighPrecisionVectorMember> SCALE =
			new Procedure3<HighPrecisionMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionMember scalar, HighPrecisionVectorMember a, HighPrecisionVectorMember b) {
			RModuleScale.compute(G.FLOAT_UNLIM, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionVectorMember,HighPrecisionVectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> CROSS =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c) {
			CrossProduct.compute(G.FLOAT_UNLIM_VEC, G.FLOAT_UNLIM, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> DOT =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionMember c) {
			DotProduct.compute(G.FLOAT_UNLIM, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> dotProduct() {
		return DOT;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> PERP =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionMember c) {
			PerpDotProduct.compute(G.FLOAT_UNLIM_VEC, G.FLOAT_UNLIM, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> VTRIPLE =
			new Procedure4<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c, HighPrecisionVectorMember d) {
			BigDecimal[] vals = new BigDecimal[3];
			vals[0] = BigDecimal.ZERO;
			vals[1] = BigDecimal.ZERO;
			vals[2] = BigDecimal.ZERO;
			HighPrecisionVectorMember b_cross_c = new HighPrecisionVectorMember(vals);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> STRIPLE =
			new Procedure4<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a, HighPrecisionVectorMember b, HighPrecisionVectorMember c, HighPrecisionMember d) {
			BigDecimal[] vals = new BigDecimal[3];
			vals[0] = BigDecimal.ZERO;
			vals[1] = BigDecimal.ZERO;
			vals[2] = BigDecimal.ZERO;
			HighPrecisionVectorMember b_cross_c = new HighPrecisionVectorMember(vals);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMember> scalarTripleProduct()
	{
		return STRIPLE;
	}

	@Override
	public Procedure2<HighPrecisionVectorMember,HighPrecisionVectorMember> conjugate() {
		return ASSIGN;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMatrixMember> VDP =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember in1, HighPrecisionVectorMember in2, HighPrecisionMatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMatrixMember> DP =
			new Procedure3<HighPrecisionVectorMember, HighPrecisionVectorMember, HighPrecisionMatrixMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember in1, HighPrecisionVectorMember in2, HighPrecisionMatrixMember out) {
			RModuleDirectProduct.compute(G.FLOAT_UNLIM, in1, in2, out);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionVectorMember,HighPrecisionVectorMember,HighPrecisionMatrixMember> directProduct() {
		return DP;
	}

	private final Function1<Boolean, HighPrecisionVectorMember> ISNAN =
			new Function1<Boolean, HighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionVectorMember a) {
			// TODO enable me when HighPrec supports NaN's
			// return SequenceIsNan.compute(G.FLOAT_UNLIM, a.rawData());
			return false;
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionVectorMember> isNaN() {
		return ISNAN;
	}
	
	private final Procedure1<HighPrecisionVectorMember> NAN =
			new Procedure1<HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a) {
			// TODO enable me when HighPrec supports NaN's
			//RModuleNaN.compute(G.FLOAT_UNLIM, a);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionVectorMember> nan() {
		return NAN;
	}

	private final Function1<Boolean, HighPrecisionVectorMember> ISINF =
			new Function1<Boolean, HighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionVectorMember a) {
			// TODO enable me when HighPrec supports Inf's
			//return SequenceIsInf.compute(G.FLOAT_UNLIM, a.rawData());
			return false;
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionVectorMember> isInfinite() {
		return ISINF;
	}

	private final Procedure1<HighPrecisionVectorMember> INF =
			new Procedure1<HighPrecisionVectorMember>()
	{
		@Override
		public void call(HighPrecisionVectorMember a) {
			// TODO enable me when HighPrec supports Inf's
			//RModuleInfinite.compute(G.FLOAT_UNLIM, a);
		}
	};

	@Override
	public Procedure1<HighPrecisionVectorMember> infinite() {
		return INF;
	}

	private final Function1<Boolean, HighPrecisionVectorMember> ISZERO =
			new Function1<Boolean, HighPrecisionVectorMember>()
	{
		@Override	
		public Boolean call(HighPrecisionVectorMember a) {
			return SequenceIsZero.compute(G.FLOAT_UNLIM, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionVectorMember> isZero() {
		return ISZERO;
	}
}
