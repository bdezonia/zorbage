package example;

import java.math.BigDecimal;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.GetComplex;
import nom.bdezonia.zorbage.type.float16.complex.ComplexFloat16Member;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float32.complex.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.complex.ComplexFloat64Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.complex.ComplexHighPrecisionMember;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;

/**
 * @author Barry DeZonia
 */
class Complexes {

	// Zorbage supports complex numbers in a variety precisions
	
	@SuppressWarnings("unused")
	void example1() {
		
		// 16-bit float based
		
		ComplexFloat16Member c16 = new ComplexFloat16Member(1.3f, -6.2f);
		
		// 32-bit float based
		
		ComplexFloat32Member c32 = new ComplexFloat32Member(99f, 44f);
		
		// 64-bit float based
		
		ComplexFloat64Member c64 = new ComplexFloat64Member(Math.E, Math.PI);
		
		// unbounded high precision float based
		
		ComplexHighPrecisionMember cBig =
				new ComplexHighPrecisionMember(BigDecimal.valueOf(-6712),BigDecimal.valueOf(1033));
		
		// use code (defined below) that can work with any of them
		//   and use it to extract values from all kinds of complex values
		
		Float16Member r16 = getImaginary(G.HLF, c16);
	
		Float32Member r32 = getImaginary(G.FLT, c32);
		
		Float64Member r64 = getImaginary(G.DBL, c64);
		
		HighPrecisionMember rBig = getImaginary(G.HP, cBig);
		
		// an alternative way to get this data without a reusable approach

		c16.getI(r16);
		c32.getI(r32);
		c64.getI(r64);
		cBig.getI(rBig);
	}
	
	<NUMBER extends GetComplex<COMPONENT>,
		COMPONENT_ALGEBRA extends nom.bdezonia.zorbage.algebra.Algebra<COMPONENT_ALGEBRA,COMPONENT>,
		COMPONENT>
	COMPONENT getImaginary(COMPONENT_ALGEBRA componentAlgebra, NUMBER complexNumber)
	{
		COMPONENT component = componentAlgebra.construct();
		complexNumber.getI(component);
		return component;
	}
	
	// Zorbage can manipulate complex numbers in many ways
	
	void example2() {
		
		// Any of the complex algebras (G.CHLF, G.CFLT, G.CDBL, and G.CHP) implement all of
		// the following methods.
		
		ComplexFloat64Member num = G.CDBL.construct();
		ComplexFloat64Member other = G.CDBL.construct();
		Float64Member val = G.DBL.construct();
		
		// basic java support
		
		num.equals(num);
		num.hashCode();
		num.toString();

		// setters and getters

		num.r();
		num.i();
		num.v(other);
		num.get(other);
		num.getR(val);
		num.getI(val);
		num.setV(other);
		num.set(other);
		num.setR(val);
		num.setI(val);
		
		// construction
		
		num.allocate();  // return a new complex number initialized to zero
		num.duplicate();  // return a new complex number initialized to my own values
		
		// more construction
		
		G.CDBL.construct();  // construct a zero value
		G.CDBL.construct(num);  // construct a copy of a number
		G.CDBL.construct("{44,77}");  // construct a complex number from a string

		// comparisons
		
		G.CDBL.isEqual();
		G.CDBL.isNotEqual();
		G.CDBL.isInfinite();
		G.CDBL.isNaN();
		G.CDBL.isZero();

		// set to one or inf or nan or zero or random value
		
		G.CDBL.unity();
		G.CDBL.infinite();
		G.CDBL.nan();
		G.CDBL.zero();
		G.CDBL.random();

		// get/use complex constants
		
		G.CDBL.E();
		G.CDBL.GAMMA();
		G.CDBL.I();
		G.CDBL.PI();
		G.CDBL.PHI();

		// basic operations
		
		G.CDBL.assign();
		G.CDBL.add();
		G.CDBL.subtract();
		G.CDBL.multiply();
		G.CDBL.divide();
		G.CDBL.negate();
		G.CDBL.invert();
		G.CDBL.pow();
		G.CDBL.power();
		G.CDBL.conjugate();
		G.CDBL.norm();
		G.CDBL.round();

		// query operations

		G.CDBL.real();  // get a real number with real equal my r
		G.CDBL.unreal(); // get a complex number equal to my 0,i

		// scaling
		
		G.CDBL.scale();
		G.CDBL.scaleByDouble();
		G.CDBL.scaleByHighPrec();
		G.CDBL.scaleByRational();
		G.CDBL.scaleComponents();

		// roots
		
		G.CDBL.sqrt();
		G.CDBL.cbrt();

		// exp / log
		
		G.CDBL.exp();
		G.CDBL.expm1();
		G.CDBL.log();
		G.CDBL.log1p();

		// transcendentals (regular and hyperbolic)
		
		G.CDBL.cos();
		G.CDBL.cosh();
		G.CDBL.cot();
		G.CDBL.coth();
		G.CDBL.csc();
		G.CDBL.csch();
		G.CDBL.sec();
		G.CDBL.sech();
		G.CDBL.sin();
		G.CDBL.sinh();
		G.CDBL.tan();
		G.CDBL.tanh();

		// inverse transcendentals (regular and hyperbolic)

		G.CDBL.acos();
		G.CDBL.acosh();
		G.CDBL.acot();
		G.CDBL.acoth();
		G.CDBL.acsc();
		G.CDBL.acsch();
		G.CDBL.asec();
		G.CDBL.asech();
		G.CDBL.asin();
		G.CDBL.asinh();
		G.CDBL.atan();
		G.CDBL.atanh();

		// various sinc functions
		
		G.CDBL.sinc();
		G.CDBL.sinch();
		G.CDBL.sincpi();
		G.CDBL.sinchpi();
		
	}
}
