package example;

/**
 * @author Barry DeZonia
 */
class Numbers {

	/*
	 * Zorbage has support for many numeric types (well beyond what other libraries
	 * provide). In Zorbage you use Algebras (covered elsewhere in this directory)
	 * to create and manipulate the numeric types. These types have the following
	 * properties:
	 * 
	 * SignedInt1Member
	 *   Min: -1
	 *   Max: 0
	 *   Memory size: 1 byte
	 *   
	 * SignedInt2Member
	 *   Min: -2
	 *   Max: 1
	 *   Memory size: 1 byte
	 *   
	 * SignedInt3Member
	 *   Min: -4
	 *   Max: 3
	 *   Memory size: 1 byte
	 *   
	 * SignedInt4Member
	 *   Min: -8
	 *   Max: 7
	 *   Memory size: 1 byte
	 *   
	 * SignedInt5Member
	 *   Min: -16
	 *   Max: 15
	 *   Memory size: 1 byte
	 *   
	 * SignedInt6Member
	 *   Min: -32
	 *   Max: 31
	 *   Memory size: 1 byte
	 *   
	 * SignedInt7Member
	 *   Min: -64
	 *   Max: 63
	 *   Memory size: 1 byte
	 *   
	 * SignedInt8Member
	 *   Min: -128
	 *   Max: 127
	 *   Memory size: 1 byte
	 *   
	 * SignedInt9Member
	 *   Min: -256
	 *   Max: 255
	 *   Memory size: 2 bytes
	 *   
	 * SignedInt10Member
	 *   Min: -512
	 *   Max: 511
	 *   Memory size: 2 bytes
	 *   
	 * SignedInt11Member
	 *   Min: -1024
	 *   Max: 1023
	 *   Memory size: 2 bytes
	 *   
	 * SignedInt12Member
	 *   Min: -2048
	 *   Max: 2047
	 *   Memory size: 2 bytes
	 *   
	 * SignedInt13Member
	 *   Min: -4096
	 *   Max: 4095
	 *   Memory size: 2 bytes
	 *   
	 * SignedInt14Member
	 *   Min: -8192
	 *   Max: 8191
	 *   Memory size: 2 bytes
	 *   
	 * SignedInt15Member
	 *   Min: -16384
	 *   Max: 16383
	 *   Memory size: 2 bytes
	 *   
	 * SignedInt16Member
	 *   Min: -32768
	 *   Max: 32767
	 *   Memory size: 2 bytes
	 *   
	 * SignedInt32Member
	 *   Min: -2147483648
	 *   Max: 2147483647
	 *   Memory size: 4 bytes
	 *   
	 * SignedInt64Member
	 *   Min: -9223372036854775808
	 *   Max: 9223372036854775807
	 *   Memory size: 8 bytes
	 *   
	 * SignedInt128Member
	 *   Min: -170141183460469231731687303715884105728
	 *   Max: 170141183460469231731687303715884105727
	 *   Memory size: 16 bytes
	 * 
	 * UnsignedInt1Member
	 *   Min: 0
	 *   Max: 1
	 *   Memory size: 1 byte
	 *   
	 * UnsignedInt2Member
	 *   Min: 0
	 *   Max: 3
	 *   Memory size: 1 byte
	 *   
	 * UnsignedInt3Member
	 *   Min: 0
	 *   Max: 7
	 *   Memory size: 1 byte
	 *   
	 * UnsignedInt4Member
	 *   Min: 0
	 *   Max: 15
	 *   Memory size: 1 byte
	 *   
	 * UnsignedInt5Member
	 *   Min: 0
	 *   Max: 31
	 *   Memory size: 1 byte
	 *   
	 * UnsignedInt6Member
	 *   Min: 0
	 *   Max: 63
	 *   Memory size: 1 byte
	 *   
	 * UnsignedInt7Member
	 *   Min: 0
	 *   Max: 127
	 *   Memory size: 1 byte
	 *   
	 * UnsignedInt8Member
	 *   Min: 0
	 *   Max: 255
	 *   Memory size: 1 byte
	 *   
	 * UnsignedInt9Member
	 *   Min: 0
	 *   Max: 511
	 *   Memory size: 2 bytes
	 *   
	 * UnsignedInt10Member
	 *   Min: 0
	 *   Max: 1023
	 *   Memory size: 2 bytes
	 *   
	 * UnsignedInt11Member
	 *   Min: 0
	 *   Max: 2047
	 *   Memory size: 2 bytes
	 *   
	 * UnsignedInt12Member
	 *   Min: 0
	 *   Max: 4095
	 *   Memory size: 2 bytes
	 *   
	 * UnsignedInt13Member
	 *   Min: 0
	 *   Max: 8191
	 *   Memory size: 2 bytes
	 *   
	 * UnsignedInt14Member
	 *   Min: 0
	 *   Max: 16383
	 *   Memory size: 2 bytes
	 *   
	 * UnsignedInt15Member
	 *   Min: 0
	 *   Max: 32767
	 *   Memory size: 2 bytes
	 *   
	 * UnsignedInt16Member
	 *   Min: 0
	 *   Max: 65535
	 *   Memory size: 2 bytes
	 *   
	 * UnsignedInt32Member
	 *   Min: 0
	 *   Max: 4294967295
	 *   Memory size: 4 bytes
	 *   
	 * UnsignedInt64Member
	 *   Min: 0
	 *   Max: 18446744073709551615
	 *   Memory size: 8 bytes
	 *   
	 * UnsignedInt128Member
	 *   Min: 0
	 *   Max: 340282366920938463463374607431768211455
	 *   Memory size: 16 bytes
	 * 
	 * RationalMember (a fraction of arbitrary precision integers)
	 *   Min: no limit
	 *   Max: no limit
	 *   Memory size: unlimited
	 * 
	 * UnboundedMember (an arbitrary precision integer)
	 *   Min: no limit
	 *   Max: no limit
	 *   Memory size: unlimited
	 * 
	 * Float16Member
	 *   1 component
	 *     Min: -65504
	 *     Max: 65504
	 *     Precision: 3 digits
	 *   Memory size: 2 bytes
	 *   
	 * Float32Member
	 *   1 component
	 *     Min: -3.40282347 x 10^38
	 *     Max: 3.40282347 x 10^38
	 *     Precision: 7 digits
	 *   Memory size: 4 bytes
	 *   
	 * Float64Member
	 *   1 component
	 *     Min: -1.7*10^308
	 *     Max: 1.7*10^308
	 *     Precision: 16 digits
	 *   Memory size: 8 bytes
	 *   
	 * HighPrecisionMember
	 *   1 component
	 *     Min: unlimited*
	 *     Max: unlimited*
	 *     Precision: 1 to 4000 decimal places
	 *   Memory size: unlimited*
	 * 
	 * ComplexFloat16Member
	 *   Each (of 2) components
	 *     Min: -65504
	 *     Max: 65504
	 *     Precision: 3 digits
	 *   Memory size: 4 bytes
	 *   
	 * ComplexFloat32Member
	 *   Each (of 2) components
	 *     Min: -3.40282347 x 10^38
	 *     Max: 3.40282347 x 10^38
	 *     Precision: 7 digits
	 *   Memory size: 8 bytes
	 *   
	 * ComplexFloat64Member
	 *   Each (of 2) components
	 *     Min: -1.7*10^308
	 *     Max: 1.7*10^308
	 *     Precision: 16 digits
	 *   Memory size: 16 bytes
	 *   
	 * ComplexHighPrecisionMember
	 *   Each (of 2) components
	 *     Min: unlimited*
	 *     Max: unlimited*
	 *     Precision: 1 to 4000 decimal places
	 *   Memory size: unlimited*
	 * 
	 * QuaternionFloat16Member
	 *   Each (of 4) components
	 *     Min: -65504
	 *     Max: 65504
	 *     Precision: 3 digits
	 *   Memory size: 8 bytes
	 *   
	 * QuaternionFloat32Member
	 *   Each (of 4) components
	 *     Min: -3.40282347 x 10^38
	 *     Max: 3.40282347 x 10^38
	 *     Precision: 7 digits
	 *   Memory size: 16 bytes
	 *   
	 * QuaternionFloat64Member
	 *   Each (of 4) components
	 *     Min: -1.7*10^308
	 *     Max: 1.7*10^308
	 *     Precision: 16 digits
	 *   Memory size: 32 bytes
	 *   
	 * QuaternionHighPrecisionMember
	 *   Each (of 4) components
	 *     Min: unlimited*
	 *     Max: unlimited*
	 *     Precision: 1 to 4000 decimal places
	 *   Memory size: unlimited*
	 * 
	 * OctonionFloat16Member
	 *   Each (of 8) components
	 *     Min: -65504
	 *     Max: 65504
	 *     Precision: 3 digits
	 *   Memory size: 16 bytes
	 *   
	 * OctonionFloat32Member
	 *   Each (of 8) components
	 *     Min: -3.40282347 x 10^38
	 *     Max: 3.40282347 x 10^38
	 *     Precision: 7 digits
	 *   Memory size: 32 bytes
	 *   
	 * OctonionFloat64Member
	 *   Each (of 8) components
	 *     Min: -1.7*10^308
	 *     Max: 1.7*10^308
	 *     Precision: 16 digits
	 *   Memory size: 64 bytes
	 *   
	 * OctonionHighPrecisionMember
	 *   Each (of 8) components
	 *     Min: unlimited*
	 *     Max: unlimited*
	 *     Precision: 1 to 4000 decimal places
	 *   Memory size: unlimited*
	 * 
	 * * Note that unlimited memory size objects are limited to the available RAM
	 *   within your computer. One consequence, as of the writing of this help file,
	 *   is that unlimited precision types cannot be saved to disk based containers
	 *   because their data is not of a fixed size.
	 *   
	 * For more information about some of these types see also (in this directory)
	 *
	 *   IntegerTypes
	 *   FloatingTypes
	 *   Complexes
	 *   Quaternions
	 *   Octonions
	 */
}