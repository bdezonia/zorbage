package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.type.float16.real.Float16MatrixMember;
import nom.bdezonia.zorbage.type.float16.real.Float16Member;

/**
 * @author Barry DeZonia
 */
class Matrices {
	
	/*
	 * Zorbage supports various flavors of Matrices:
	 * 
	 * Float16MatrixMember - 16 bit reals
	 * Float32MatrixMember - 32 bit reals
	 * Float64MatrixMember - 64 bit reals
	 * HighPrecisionMatrixMember - high precision reals
	 * 
	 * ComplexFloat16MatrixMember - 16 bit complexes
	 * ComplexFloat32MatrixMember - 32 bit complexes
	 * ComplexFloat64MatrixMember - 64 bit complexes
	 * ComplexHighPrecisionMatrixMember - high precision complexes
	 * 
	 * QuaternionFloat16MatrixMember - 16 bit quaternions
	 * QuaternionFloat32MatrixMember - 32 bit quaternions
	 * QuaternionFloat64MatrixMember - 64 bit quaternions
	 * QuaternionHighPrecisionMatrixMember - high precision quaternions
	 * 
	 * OctonionFloat16MatrixMember - 16 bit octonions
	 * OctonionFloat32MatrixMember - 32 bit octonions
	 * OctonionFloat64MatrixMember - 64 bit octonions
	 * OctonionHighPrecisionMatrixMember - high precision octonions
	 * 
	 */
	
	// Here are some example calls supported by matrices. Float16 reals are shown
	// in the examples below but all the same calls exist for the type combinations:
	// (Precision: 16/32/64/HighPrec Type:real/complex/quaternion/octonion)
	
	@SuppressWarnings("unused")
	void example1() {
		
		Float16Member value = G.HLF.construct();
		
		// construction
		
		Float16MatrixMember a = new Float16MatrixMember();
		Float16MatrixMember b = new Float16MatrixMember(2, 2, new float[] {1,2,3,4});
		Float16MatrixMember c = G.HLF_MAT.construct();
		Float16MatrixMember d = G.HLF_MAT.construct(b);
		Float16MatrixMember e = G.HLF_MAT.construct("[[1,2,3][4,5,6]]");
		Float16MatrixMember f = G.HLF_MAT.construct(StorageConstruction.MEM_SPARSE, 1000, 1000);

		// java support
		
		a.equals(b);
		a.hashCode();
		a.toString();

		// basic functions
		
		b.rows();
		b.cols();
		b.get(e);
		b.set(f);
		b.v(1, 0, value);
		b.setV(0, 1, value);

		// constants
		
		G.HLF_MAT.E();
		G.HLF_MAT.PI();
		G.HLF_MAT.GAMMA();
		G.HLF_MAT.PHI();

		// basic operations
		
		G.HLF_MAT.assign();
		G.HLF_MAT.add();  // add two matrices
		G.HLF_MAT.addScalar();  // add a scalar to all elements of a matrix
		G.HLF_MAT.multiplyByScalar();  // multiply all elements of a matrix by a scalar 
		G.HLF_MAT.multiplyElements(); // do element wise multiplication of two matrices
		G.HLF_MAT.subtract(); // subtract one matrix from another
		G.HLF_MAT.subtractScalar(); // add a scalar from all elements of a matrix
		G.HLF_MAT.divide();  // divide a matrix by another matrix
		G.HLF_MAT.divideByScalar();  // divide all elements of a matrix by a scalar
		G.HLF_MAT.divideElements(); // do element wise division of two matrices
		G.HLF_MAT.conjugate();  // conjugate all the elements of a matrix
		G.HLF_MAT.negate();  // flip the sign of all elements of a matrix
		G.HLF_MAT.norm();  // calculate the magnitude of a matrix
		G.HLF_MAT.power();  // calc a nth-power of a matrix
		G.HLF_MAT.round();  // round all elements of a matrix to specified precision
		G.HLF_MAT.within();  // test that two matrices are within a tolerance of each other

		// test values

		G.HLF_MAT.isEqual();
		G.HLF_MAT.isNotEqual();
		G.HLF_MAT.isInfinite();
		G.HLF_MAT.isNaN();
		G.HLF_MAT.isZero();

		// set values: infinity, nan, identity, zero
		
		G.HLF_MAT.infinite();
		G.HLF_MAT.nan();
		G.HLF_MAT.unity();
		G.HLF_MAT.zero();

		// exponential
		
		G.HLF_MAT.exp();
		G.HLF_MAT.log();

		// transcendentals (regular and hyperbolic)
		
		G.HLF_MAT.cos();
		G.HLF_MAT.cosh();
		G.HLF_MAT.sin();
		G.HLF_MAT.sinh();
		G.HLF_MAT.tan();
		G.HLF_MAT.tanh();
		G.HLF_MAT.sinAndCos();
		G.HLF_MAT.sinhAndCosh();

		// sinc operations
		
		G.HLF_MAT.sinc();
		G.HLF_MAT.sincpi();
		G.HLF_MAT.sinch();
		G.HLF_MAT.sinchpi();

		// scaling
		
		G.HLF_MAT.scale();
		G.HLF_MAT.scaleByDouble();
		G.HLF_MAT.scaleByHighPrec();
		G.HLF_MAT.scaleByHighPrec();
		G.HLF_MAT.scaleByRational();

		// Matrix operations
		
		G.HLF_MAT.conjugateTranspose();
		G.HLF_MAT.det();
		G.HLF_MAT.directProduct();
		G.HLF_MAT.invert();
		G.HLF_MAT.transpose();
	}
}
