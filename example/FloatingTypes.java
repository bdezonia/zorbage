import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.algebra.RealConstants;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.type.float16.real.Float16Algebra;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;
import nom.bdezonia.zorbage.type.float32.real.Float32Algebra;
import nom.bdezonia.zorbage.type.float32.real.Float32Member;
import nom.bdezonia.zorbage.type.float64.real.Float64Algebra;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FloatingTypes<T extends nom.bdezonia.zorbage.algebra.Algebra<T,U> & RealConstants<U> & Multiplication<U>, U>
{
	/*
	 * Currently Zorbage supports four different floating number types:
	 * 
	 *   16-bit floating point numbers (supported in software): the half type in the IEEE-754 standard
	 *   
	 *     Float16Member: approximately 3 decimal places of precision
	 * 
	 *   32-bit floating point numbers (supported in hardware): the float type in the IEEE-754 standard
	 *     
	 *     Float32Member: approximately 7 decimal places of precision
	 *   
	 *   64-bit floating point numbers (supported in hardware): the double type in the IEEE-754 standard
	 *     
	 *     Float64Member: approximately 16 decimal places of precision
	 *   
	 *   High precision floating point numbers (supported in software):
	 *     
	 *     HighPrecisionMember: 1 to 4000 decimal places of precision (user configurable)
	 *   
	 * With Zorbage you can calculate floating point values at any of these precisions. You can choose to trade
	 * off memory use versus speed versus accuracy as you see fit.
	 * 
	 */
	
	// Now let's define a function that can work at any precision
	
	public Function1<U,T> func = new Function1<U,T>()  // T and U specified in class declaration above
	{

		@Override
		public U call(T algebra) {
			
			// construct some working variables on the stack
			U result = algebra.construct();
			U tmp = algebra.construct();
			U e = algebra.construct();
			U pi = algebra.construct();
			U gamma = algebra.construct();
			U phi = algebra.construct();
			
			// initialize the constants
			algebra.E().call(e);  // get the E constant
			algebra.PI().call(pi);  // get the PI constant
			algebra.PHI().call(phi);  // get the PHI constant
			algebra.GAMMA().call(gamma); // get the GAMMA constant
			
			// multiply them all together
			algebra.multiply().call(e, pi, tmp);
			algebra.multiply().call(tmp, phi, tmp);
			algebra.multiply().call(tmp, gamma, result);
			
			// return the result
			return result;
		}
	
	};

	// Now I am going to show how you can use it
	
	public void example1() {
		
		// Calculate e * pi * phi * gamma for four different accuracies using one algorithm
		
		// Let's push the high precision accuracy to a 1000 decimal places. Ideally this method is called
		// once at your program's startup.
		
		HighPrecisionAlgebra.setPrecision(1000);

		// Calculate the constant in 16 bit float precision
		
		Float16Member hlfVal = new FloatingTypes<Float16Algebra,Float16Member>().func.call(G.HLF);

		// Calculate the constant in 32 bit float precision
		
		Float32Member fltVal = new FloatingTypes<Float32Algebra,Float32Member>().func.call(G.FLT);
		
		// Calculate the constant in 64 bit float precision
		
		Float64Member dblVal = new FloatingTypes<Float64Algebra,Float64Member>().func.call(G.DBL);
		
		// Calculate the constant in 1000 place float precision
		
		HighPrecisionMember hpVal = new FloatingTypes<HighPrecisionAlgebra,HighPrecisionMember>().func.call(G.HP);
		
		// Now print out and compare the results
		
		System.out.println(hlfVal);
		System.out.println(fltVal);
		System.out.println(dblVal);
		System.out.println(hpVal);
	}
	
	/*
	 * The hardware floating types support many basic operations you'd expect:
	 *   sin(), cos(), tan()
	 *   sinh(), cosh(), tanh()
	 *   exp(), log()
	 *   asin(), acos, atan()
	 *   asinh(), acosh(), atanh()
	 *   And many many more. The list of methods is comparable to floating point functions supported by other
	 *   programming languages.
	 */
	
	/*
	 * Calculating other things with floating point: note that each supported floating type represents Real
	 * values. Zorbage also supports composye values like Complex values, Quaternion values, and Octonion
	 * values. Each of these composite types of numbers use the above floating types to support multiple
	 * precision code for their implementations. Finally there are other composite types (Vectors, Matrices,
	 * and Tensors). Again the basic Real floating types are used to calculate operations on Vectors, Matrices,
	 * and Tensors made up of Real, Complex, Quaternion, and Octonion values. All at the various precisions you
	 * choose. You can read more about these composite types and how to work with them in their own example
	 * descriptions in this same directory.
	 */
}
