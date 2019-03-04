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
package nom.bdezonia.zorbage.type.data.bigdec;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.OrderedField;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.algebra.Scale;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionAlgebra
  implements
    OrderedField<HighPrecisionAlgebra,HighPrecisionMember>,
    Norm<HighPrecisionMember,HighPrecisionMember>,
    Constants<HighPrecisionMember>,
    RealUnreal<HighPrecisionMember,HighPrecisionMember>,
    Conjugate<HighPrecisionMember>,
    Scale<HighPrecisionMember,HighPrecisionMember>
{
	private static MathContext CONTEXT = new MathContext(35, RoundingMode.HALF_EVEN);
	
	public static void setPrecision(int decimalPlaces) {
		if (decimalPlaces < 1)
			throw new IllegalArgumentException("number of decimal places must be > 0");
		if (decimalPlaces > 113)
			throw new IllegalArgumentException("precision too high: beyond E and PI accuracy");
		CONTEXT = new MathContext(decimalPlaces, RoundingMode.HALF_EVEN);
	}

	// Source: wolfram alpha 3-3-2019
	
	private static final HighPrecisionMember PI = new HighPrecisionMember(new BigDecimal("3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706798214808651328"));
	private static final HighPrecisionMember E = new HighPrecisionMember( new BigDecimal("2.71828182845904523536028747135266249775724709369995957496696762772407663035354759457138217852516642742746639193200"));
	
	public HighPrecisionAlgebra() { }
	
	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> EQ =
		new Function2<Boolean,HighPrecisionMember,HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) == 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> NEQ =
			new Function2<Boolean,HighPrecisionMember,HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return !isEqual().call(a, b);
		}
	};
		
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public HighPrecisionMember construct() {
		return new HighPrecisionMember();
	}

	@Override
	public HighPrecisionMember construct(HighPrecisionMember other) {
		return new HighPrecisionMember(other);
	}

	@Override
	public HighPrecisionMember construct(String s) {
		return new HighPrecisionMember(s);
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> ASSIGN =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember from, HighPrecisionMember to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> assign() {
		return ASSIGN;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> ADD =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV( a.v().add(b.v()) );
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> add() {
		return ADD;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> SUB =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV( a.v().subtract(b.v()) );
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> subtract() {
		return SUB;
	}

	private final Procedure1<HighPrecisionMember> ZER =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			a.setV( BigDecimal.ZERO );
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> zero() {
		return ZER;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> NEG =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV( a.v().negate() );
		}
	};

	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> negate() {
		return NEG;
	}

	private final Procedure1<HighPrecisionMember> UNITY =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			a.setV( BigDecimal.ONE );
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> unity() {
		return UNITY;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> INV =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV( BigDecimal.ONE.divide(a.v(), CONTEXT) );
		}
	};

	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> invert() {
		return INV;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> DIVIDE =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV( a.v().divide(b.v(), CONTEXT) );
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> divide() {
		return DIVIDE;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> LESS =
			new Function2<Boolean, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isLess() {
		return LESS;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> LE =
			new Function2<Boolean, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> GREAT =
			new Function2<Boolean, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> GE =
			new Function2<Boolean, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isGreaterEqual() {
		return GE;
	}

	private Function2<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> CMP =
			new Function2<java.lang.Integer, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public java.lang.Integer call(HighPrecisionMember a, HighPrecisionMember b) {
			return a.v().compareTo(b.v());
		}
	};
	
	@Override
	public Function2<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> compare() {
		return CMP;
	}

	private Function1<java.lang.Integer,HighPrecisionMember> SIG =
			new Function1<Integer, HighPrecisionMember>()
	{
		@Override
		public Integer call(HighPrecisionMember a) {
			return a.v().signum();
		}
	};
	
	@Override
	public Function1<java.lang.Integer,HighPrecisionMember> signum() {
		return SIG;
	}
	
	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> MUL =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV( a.v().multiply(b.v(), CONTEXT) );
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> POWER =
			new Procedure3<Integer, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(Integer power, HighPrecisionMember a, HighPrecisionMember b) {
			if (power == 0 && a.v().equals(BigDecimal.ZERO)) {
				throw new IllegalArgumentException("0^0 is not a number");
			}
			else
				b.setV( a.v().pow(power) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> power() {
		return POWER;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> ABS =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV( a.v().abs() );
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> abs() {
		return ABS;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> NORM =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			abs().call(a,b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure1<HighPrecisionMember> PI_ =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			assign().call(PI, a);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> PI() {
		return PI_;
	}

	private final Procedure1<HighPrecisionMember> E_ =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			assign().call(E, a);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> E() {
		return E_;
	}
	
	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> MIN =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			Min.compute(G.BIGDEC, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> min() {
		return MIN;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> MAX =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			Max.compute(G.BIGDEC, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> max() {
		return MAX;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> REAL =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(a.v());
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> real() {
		return REAL;
	}
	
	private final Procedure2<HighPrecisionMember,HighPrecisionMember> UNREAL =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> unreal() {
		return UNREAL;
	}

	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> conjugate() {
		return ASSIGN;
	}

	private final Function1<Boolean, HighPrecisionMember> ISZERO =
			new Function1<Boolean, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a) {
			return a.v().signum() == 0;
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionMember> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> scale() {
		return MUL;
	}

}