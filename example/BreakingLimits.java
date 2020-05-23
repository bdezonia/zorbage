
public class BreakingLimits {
	
	/* 
	 * Zorbage breaks through a lot of limitations that Java normally imposes on its programs:
	 * 
	 * Array/List lengths
	 *   Java imposes a 2^31 limit on list lengths. Arrays are indexed by 32-bit ints.
	 *   Zorbage allows a 2^63 limit on list lengths. Zorbage arrays are indexed by 64-bit ints.
	 *     In memory arrays can be glued together if needed to make huge in memory data structures.
	 *     Other list abstractions exist to provide ways of making huge lists of data.
	 *   
	 * Unsigned numbers
	 *   Java does not support unsigned integers
	 *   Zorbage does support unsigned integers (from 1 bit to 64 bit)
	 *   
	 * Function pointers
	 *   Java does not support function pointers
	 *   Zorbage supports passing around 1st class type safe function and procedure pointers
	 *   
	 * Many floating point types
	 *   Java supports 32 bit and 64 bit floating types
	 *   Zorbage supports 16 bit, 32 bit, 64 bit, and seemingly limitless precision floating bit numbers.
	 *     One nice thing about floating point in Zorbage is that you can write one algorithm and by
	 *     passing it different parameters you can compute a result with 3 places precision or 7 places
	 *     precision or 16 places or even 4000.
	 *   
	 * Pass by reference of primitive types
	 *   In Java you cannot write a method that changes the value of a primitive type (int, float, etc.)
	 *   In Zorbage pass by reference even works with primitive types
	 *   
	 * Non byte-aligned integers
	 *   In Java the integers are always byte aligned: 8 bit byte, 16 bit short, 32 bit int, 64 bit long
	 *   In Zorbage all these types are present but also ints of depth 1 bit, 2 bit, 3 bit, up to 16-bit
	 *   
	 * Specialized numeric types
	 *   Zorbage supports things Java does not:
	 *   - 128 bit signed integers (SignedInt128Member)
	 *   - unbounded integers (UnboundedIntMember)
	 *   - rational numbers (RationalMember)
	 *       Rational numbers are great for safely scaling lists of integers while avoiding overflow and
	 *       underflow and double precision math and rounding.
	 */
}
