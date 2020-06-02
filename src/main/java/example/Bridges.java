package example;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.type.float64.real.Float64Member;
import nom.bdezonia.zorbage.type.helper.NumberRModuleBridge;

/**
 * @author Barry DeZonia
 */
public class Bridges {

	/*
	 * Zorbage delineates many data types walled off in designations like NumberMember or
	 * Vector/RModuleMember, MatrixMember, and TensorProductMember. However in mathematical
	 * terms each smaller type (say Number) can be treated as a special case of a higher
	 * level type. For instance a Number is the same as a 1-element vector or the same as
	 * a 1x1 shaped matrix or a 1x1x1... shaped tensor. Given these relationships we define
	 * bridge classes that allow elements of one type (vector for instance) to be passed
	 * as another (a rank 1 tensor for instance). There are numerous bridge classes and
	 * they will be detailed below.
	 */
	
	void example1() {
		Float64Member num = new Float64Member(191.6);
		NumberRModuleBridge<Float64Member> bridge = new NumberRModuleBridge<>(G.DBL, num);
		bridge.setV(0, G.DBL.construct());
		System.out.println(num); // prints 0
	}
}
