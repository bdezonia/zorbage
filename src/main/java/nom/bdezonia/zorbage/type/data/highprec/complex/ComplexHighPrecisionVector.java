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
package nom.bdezonia.zorbage.type.data.highprec.complex;

import java.math.BigDecimal;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.CrossProduct;
import nom.bdezonia.zorbage.algorithm.DotProduct;
import nom.bdezonia.zorbage.algorithm.PerpDotProduct;
import nom.bdezonia.zorbage.algorithm.RModuleAdd;
import nom.bdezonia.zorbage.algorithm.RModuleAssign;
import nom.bdezonia.zorbage.algorithm.RModuleConjugate;
import nom.bdezonia.zorbage.algorithm.RModuleDirectProduct;
import nom.bdezonia.zorbage.algorithm.RModuleEqual;
import nom.bdezonia.zorbage.algorithm.RModuleNegate;
import nom.bdezonia.zorbage.algorithm.RModuleScale;
import nom.bdezonia.zorbage.algorithm.RModuleSubtract;
import nom.bdezonia.zorbage.algorithm.RModuleZero;
import nom.bdezonia.zorbage.algorithm.SequenceIsZero;
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
import nom.bdezonia.zorbage.type.algebra.Tolerance;
import nom.bdezonia.zorbage.type.algebra.VectorSpace;
import nom.bdezonia.zorbage.type.ctor.Constructible1dLong;
import nom.bdezonia.zorbage.type.ctor.StorageConstruction;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.data.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.storage.datasource.IndexedDataSource;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class ComplexHighPrecisionVector
  implements
    VectorSpace<ComplexHighPrecisionVector,ComplexHighPrecisionVectorMember,ComplexHighPrecisionAlgebra,ComplexHighPrecisionMember>,
    Constructible1dLong<ComplexHighPrecisionVectorMember>,
    Norm<ComplexHighPrecisionVectorMember,HighPrecisionMember>,
    Products<ComplexHighPrecisionVectorMember, ComplexHighPrecisionMember, ComplexHighPrecisionMatrixMember>,
    DirectProduct<ComplexHighPrecisionVectorMember, ComplexHighPrecisionMatrixMember>,
    Tolerance<HighPrecisionMember,ComplexHighPrecisionVectorMember>
{
	public ComplexHighPrecisionVector() { }
	
	private final Procedure1<ComplexHighPrecisionVectorMember> ZER =
			new Procedure1<ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a) {
			RModuleZero.compute(a);
		}
	};
	
	@Override
	public Procedure1<ComplexHighPrecisionVectorMember> zero() {
		return ZER;
	}

	private final Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> NEG =
			new Procedure2<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			RModuleNegate.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> negate() {
		return NEG;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> ADD =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			RModuleAdd.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> add() {
		return ADD;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> SUB =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			RModuleSubtract.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> subtract() {
		return SUB;
	}

	private final Function2<Boolean,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> EQ =
			new Function2<Boolean, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			return (Boolean) RModuleEqual.compute(G.CHP, a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> NEQ =
			new Function2<Boolean, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			return !isEqual().call(a, b);
		}
	};

	@Override
	public Function2<Boolean,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public ComplexHighPrecisionVectorMember construct() {
		return new ComplexHighPrecisionVectorMember();
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(ComplexHighPrecisionVectorMember other) {
		return new ComplexHighPrecisionVectorMember(other);
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(String s) {
		return new ComplexHighPrecisionVectorMember(s);
	}

	@Override
	public ComplexHighPrecisionVectorMember construct(StorageConstruction s, long d1) {
		return new ComplexHighPrecisionVectorMember(s, d1);
	}

	private final Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> ASSIGN =
			new Procedure2<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember from, ComplexHighPrecisionVectorMember to) {
			RModuleAssign.compute(G.CHP, from, to);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> assign() {
		return ASSIGN;
	}

	private final Procedure2<ComplexHighPrecisionVectorMember,HighPrecisionMember> NORM =
			new Procedure2<ComplexHighPrecisionVectorMember, HighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, HighPrecisionMember b) {
			ComplexHighPrecisionMember aTmp = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember sum = new ComplexHighPrecisionMember();
			ComplexHighPrecisionMember tmp = new ComplexHighPrecisionMember();
			// TODO Look into preventing overflow. can do so similar to float case using norms
			for (long i = 0; i < a.length(); i++) {
				a.v(i, aTmp);
				G.CHP.conjugate().call(aTmp, tmp);
				G.CHP.multiply().call(aTmp, tmp, tmp);
				G.CHP.add().call(sum, tmp, sum);
			}
			b.setV(BigDecimalMath.sqrt(sum.r(), HighPrecisionAlgebra.getContext()));
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionVectorMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> SCALE =
			new Procedure3<ComplexHighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionMember scalar, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			RModuleScale.compute(G.CHP, scalar, a, b);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> scale() {
		return SCALE;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> CROSS =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c) {
			CrossProduct.compute(G.CHP_VEC, G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> crossProduct() {
		return CROSS;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> DOT =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionMember c) {
			DotProduct.compute(G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> dotProduct() {
		return DOT;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> PERP =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionMember c) {
			PerpDotProduct.compute(G.CHP_VEC, G.CHP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> perpDotProduct() {
		return PERP;
	}

	private final Procedure4<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> VTRIPLE =
			new Procedure4<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c,
				ComplexHighPrecisionVectorMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*2];
			for (int i = 0; i < arr.length; i++)
				arr[i] = BigDecimal.ZERO;
			ComplexHighPrecisionVectorMember b_cross_c = new ComplexHighPrecisionVectorMember(arr);
			crossProduct().call(b, c, b_cross_c);
			crossProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> vectorTripleProduct()
	{
		return VTRIPLE;
	}

	private final Procedure4<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> STRIPLE =
			new Procedure4<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b, ComplexHighPrecisionVectorMember c,
				ComplexHighPrecisionMember d)
		{
			BigDecimal[] arr = new BigDecimal[3*2];
			for (int i = 0; i < arr.length; i++)
				arr[i] = BigDecimal.ZERO;
			ComplexHighPrecisionVectorMember b_cross_c = new ComplexHighPrecisionVectorMember(arr);
			crossProduct().call(b, c, b_cross_c);
			dotProduct().call(a, b_cross_c, d);
		}
	};
	
	@Override
	public Procedure4<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMember> scalarTripleProduct()
	{
		return STRIPLE;
	}

	private final Procedure2<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember> CONJ =
			new Procedure2<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			RModuleConjugate.compute(G.CHP, a, b);
		}
	};
	
	@Override
	public Procedure2<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> conjugate() {
		return CONJ;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMatrixMember> VDP =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember in1, ComplexHighPrecisionVectorMember in2, ComplexHighPrecisionMatrixMember out) {
			directProduct().call(in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMatrixMember> vectorDirectProduct() {
		return VDP;
	}

	private final Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMatrixMember> DP =
			new Procedure3<ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionMatrixMember>()
	{
		@Override
		public void call(ComplexHighPrecisionVectorMember in1, ComplexHighPrecisionVectorMember in2, ComplexHighPrecisionMatrixMember out) {
			RModuleDirectProduct.compute(G.CHP, in1, in2, out);
		}
	};

	@Override
	public Procedure3<ComplexHighPrecisionVectorMember,ComplexHighPrecisionVectorMember,ComplexHighPrecisionMatrixMember> directProduct()
	{
		return DP;
	}

	private final Function1<Boolean, ComplexHighPrecisionVectorMember> ISZERO =
			new Function1<Boolean, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(ComplexHighPrecisionVectorMember a) {
			return SequenceIsZero.compute(G.CHP, a.rawData());
		}
	};

	@Override
	public Function1<Boolean, ComplexHighPrecisionVectorMember> isZero() {
		return ISZERO;
	}

	private final Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember tol, ComplexHighPrecisionVectorMember a, ComplexHighPrecisionVectorMember b) {
			if (a.length() != b.length())
				return false;
			ComplexHighPrecisionMember elemA = G.CHP.construct();
			ComplexHighPrecisionMember elemB = G.CHP.construct();
			IndexedDataSource<ComplexHighPrecisionMember> lista = a.rawData();
			IndexedDataSource<ComplexHighPrecisionMember> listb = b.rawData();
			for (long i = 0; i < lista.size(); i++) {
				lista.get(i, elemA);
				listb.get(i, elemB);
				if (!G.CHP.within().call(tol, elemA, elemB))
					return false;
			}
			return true;
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, ComplexHighPrecisionVectorMember, ComplexHighPrecisionVectorMember> within() {
		return WITHIN;
	}
}
