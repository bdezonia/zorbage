import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.int11.UnsignedInt11Member;
import nom.bdezonia.zorbage.type.int4.SignedInt4Member;
import nom.bdezonia.zorbage.type.universal.FindCompatibleType;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Algebra {

	/*
	 * Zorbage at it's heart is organized around algebra. The simplest way to describe Zorbage's organization
	 * of numerical objects is to classify types of data (one Algebra for integers and one Algebra for real
	 * numbers etc.) and the operations that are legal for those entities. The Float64Algebra defines what are
	 * the legal operation you can do with Float64s. You can divide() two Float64s without leaving a remainder.
	 * This is not true of integers. 5 / 4 leaves a remainder of 1. So Integer types do not support this kind
	 * of divide() operation.
	 * 
	 * Zorbage uses Algebras to collect the operations that are allowed for a type. Let's say 4 bit integers
	 * for now. The algebra for 4-bit integers will contain the add() and subtract() and multiply() operations
	 * that can be called for instances of 4-bit integers.
	 * 
	 * Zorbage will use an algebra to do operations on types within that algebra. If I have a reference to an
	 * algebra called ALG then I can add numbers using the algebra like this: ALG.add().call(in1, in2, out).
	 * If the algebra is for 4-bit integers then it will add two 4-bit integers (contained in "in1" and "in2")
	 * and put the result in the 4-bit integer called "out".
	 * 
	 * One of the strengths of this algebra approach is that you can define algorithms that do things (like
	 * one that adds two numbers) and use it with many kinds of numbers provided their algebra has an add()
	 * operation.
	 * 
	 * To make algorithms general we pass in the algebras the algorithm will need and also numbers that are
	 * members of that algebra. The algorithms use the algebras to compute values from the input values.
	 * Zorbage's approach is somewhat similar to C++'s ability to do operator overloading but Zorbage code
	 * is much easier to read and follow along.
	 * 
	 */
	
	// Let's illustrate some things with some code. Here is an algorithm that checks numbers for equality
	
	public static <T extends nom.bdezonia.zorbage.algebra.Algebra<T,U>, U>
	void compute(T algebra, U algMemberOne, U algMemberTwo)
	{
		// isEqual() is a method all Algebras must implement
		
		if (algebra.isEqual().call(algMemberOne, algMemberTwo))
			System.out.println("the two inputs are equal");
		else
			System.out.println("the two inputs are not equal");
	}
	
	// And here is a test program
	
	public static
	void test1()
	{
		// G stores a bunch of predefined algebras. G.INT4 is the predefined algebra for 4-bit ints.
		
		// algebras can construct values within their types
		SignedInt4Member a = G.INT4.construct("2");
		SignedInt4Member b = G.INT4.construct("2");
		SignedInt4Member c = G.INT4.construct("3");
		
		compute(G.INT4, a, b);  // prints "the two inputs are equal"
		compute(G.INT4, b, c);  // prints "the two inputs are not equal"
		
		// Now notice the algorithm can be reused with unsigned 11-bit integers

		// G stores a bunch of predefined algebras. G.UINT11 is the predefined algebra for unsigned 11-bit ints.
		
		// algebras can construct values within their types
		UnsignedInt11Member d = G.UINT11.construct("2");
		UnsignedInt11Member e = G.UINT11.construct("2");
		UnsignedInt11Member f = G.UINT11.construct("3");
		
		compute(G.UINT11, d, e);  // prints "the two inputs are equal"
		compute(G.UINT11, e, f);  // prints "the two inputs are not equal"
		
	}
	
	/*
	 * Zorbage has many kinds of Algebras supporting varied numeric (and non-numeric) types. Many are
	 * interrelated. You can use some nonspecific algebras in your algorithms (like Integer) and your
	 * algorithm will work with all the Integer types defined now and any more defined in the future
	 * (whether by you or some other research group).
	 * 
	 * The defined types for algebras do not have to be numeric. One could define a String type with
	 * equal, not equal, and add operations. The add operation would simply do string concatenation.
	 * Any written algorithm that was restricted to addition and equality tests would work with numbers
	 * or with strings.
	 */
	
	// Zorbage also has a basic discovery mechanism for algebras. This is not something you'd usually
	// do but you can. (You can find more enhanced examples in Zorbage's test code for the
	// FindCompatibleType class.
	
	public static <T extends nom.bdezonia.zorbage.algebra.Algebra<T,U>, U>
	void test2()
	{
		Float64Member num = G.DBL.construct();
		
		T alg = FindCompatibleType.bestAlgebra(1, num.preferredRepresentation());
		
		U num1 = alg.construct("44.32");
		U num2 = alg.construct();
		
		System.out.println(alg.isEqual().call(num1, num2)); // prints false
		
		alg.assign().call(num1, num2);

		System.out.println(alg.isEqual().call(num1, num2)); // prints true
	}
	
	/*
	 * Zorbage ships with many different algebras stored in the G class:
	 * 
	 * signed ints from 1 bit to 128 bit
	 * unsigned ints from 1 bit to 64 bit
	 * unbounded ints
	 * floats (16 bit, 32 bit, 64 bit, and unbounded)
	 * booleans
	 * n-dimensional real Points
	 * ARGB and RGB pixels
	 * vectors and matrices and tensors
	 * real, complex, quaternion, and octonion numbers
	 * and more
	 */
}
