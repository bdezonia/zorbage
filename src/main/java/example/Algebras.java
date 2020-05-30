package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.Multiplication;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.int11.UnsignedInt11Member;
import nom.bdezonia.zorbage.type.int4.SignedInt4Member;
import nom.bdezonia.zorbage.type.universal.FindCompatibleType;

/**
 * @author Barry DeZonia
 */
class Algebras {

	/*
	 * Zorbage at it's heart is organized around algebra. The simplest way to describe Zorbage's organization
	 * of numerical objects is to classify types of data (one Algebra for integers and one Algebra for real
	 * numbers etc.) and the operations that are legal for those entities. The Float64Algebra defines what are
	 * the legal operations you can do with Float64s. You can divide() two Float64s without leaving a remainder.
	 * This is not true of integers. 5 / 4 leaves a remainder of 1. So Integer types do not support this kind
	 * of divide() operation.
	 * 
	 * Zorbage uses Algebras to collect the operations that are allowed for a type. Let's use 4 bit integers
	 * as an example. The algebra for 4-bit integers will contain the add() and subtract() and multiply()
	 * operations that can be called for instances of 4-bit integers.
	 * 
	 * Zorbage will use an Algebra to do operations on types within that Algebra. If I have a reference to an
	 * Algebra called ALG then I can add numbers using the algebra like this: ALG.add().call(in1, in2, out).
	 * If the Algebra is for 4-bit integers then it will add two 4-bit integers (contained in "in1" and "in2")
	 * and put the result in the 4-bit integer (called "out").
	 * 
	 * One of the strengths of this algebra approach is that you can define algorithms that do things (like
	 * one that add()s two numbers) and use it with many kinds of numbers provided their algebra has an add()
	 * operation.
	 * 
	 * To make algorithms general we pass in the Algebras the algorithm will need and also numbers that are
	 * members of that Algebra. The algorithms use the Algebras to compute values from the input values.
	 * Zorbage's approach is somewhat similar to C++'s ability to do operator overloading but Zorbage code
	 * is much easier to read and follow along with. It has a feeling of numerical assembly language. This
	 * low level approach results in significant speed.
	 * 
	 */
	
	// Let's illustrate these concepts with some code. Here is an algorithm that checks numbers for equality.
	
	<T extends nom.bdezonia.zorbage.algebra.Algebra<T,U>, U>
		void compute(T algebra, U algMemberOne, U algMemberTwo)
	{
		// isEqual() is a method all Algebras must implement
		
		if (algebra.isEqual().call(algMemberOne, algMemberTwo))
			System.out.println("the two inputs are equal");
		else
			System.out.println("the two inputs are not equal");
	}
	
	// And here is a test program
	
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
	 * One thing you'll see with algorithms using Algebras is that they specify the operators they will be
	 * using to solve the problem they are designed for.
	 */
	
	<T extends nom.bdezonia.zorbage.algebra.Algebra<T,U> & Multiplication<U>, U>
		void test2(T algebra, U a, U b, U c)
	{
		// The above declaration says T is an Algebra. The Algebra is defined by T and U. T is basically
		// its own name and U is type of values the Algebra will manipulate.
		
		// In addition to tying the T's and U's together it also delineates that the Algebra supports
		// the Multiplication operation. This allows the next line to be sensible.
		
		algebra.multiply().call(a, b, c);  // c = a * b
		
		// When you write code like this you can now pass in any Algebra and elements that are tied
		// together. If the Algebra does not support a multiplication operator for elements of type U
		// the code will not compile. Zorbage protects you from making logic errors in passing numbers
		// and operators around. Python would simply crash here at runtime.
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
	
	/*
	 * One style Zorbage has adopted in the relationships between T and U with Algebras is that in
	 * general U's should be pretty dumb. They know how to set and get their contents and they might
	 * have some conversion ability to other types. But otherwise they are just things that get passed
	 * to Algebras by algorithms. They act much like C/C++ structs and Java can usually convert them
	 * into variables on the stack rather than as objects on the heap. This further accelerates
	 * Zorbage.
	 */
	
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

	/* Zorbage also has a basic discovery mechanism for algebras. This is not something you'd usually
	 * do but you can if you like. (You can find more enhanced examples of this in Zorbage's test code
	 * for the FindCompatibleType class.
	 */
	
	<T extends nom.bdezonia.zorbage.algebra.Algebra<T,U>, U>
		void test3()
	{
		Float64Member num = G.DBL.construct();
		
		T alg = FindCompatibleType.bestAlgebra(1, num.preferredRepresentation());
		
		U num1 = alg.construct("44.32");
		U num2 = alg.construct();
		
		System.out.println(alg.isEqual().call(num1, num2)); // prints false
		
		alg.assign().call(num1, num2);

		System.out.println(alg.isEqual().call(num1, num2)); // prints true
	}
}
