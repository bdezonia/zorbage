package zorbage.type.data;

import zorbage.type.algebra.Field;
import zorbage.type.algebra.Hyperbolic;
import zorbage.type.algebra.Trigonometric;

// note that many implementations of tensors on the internet treat them as generalized matrices.
// they do not worry about upper and lower indices or even matching shapes. They do element by
// element ops like sin() of each elem.

//do I skip Vector and Matrix and even Scalar?


class TensorField
  implements
    Field<TensorField,TensorMember>, // TODO: does this make sense
    Hyperbolic<TensorMember>,  // TODO: does this make sense?
    Trigonometric<TensorMember>  // TODO: does this make sense?
{
	@Override
	public void multiply(TensorMember a, TensorMember b, TensorMember c) {}

	@Override
	public void divide(TensorMember a, TensorMember b, TensorMember c) {}

	@Override
	public void add(TensorMember a, TensorMember b, TensorMember c) {}

	@Override
	public void subtract(TensorMember a, TensorMember b, TensorMember c) {}

	// this implies it is an OrderedField. Do tensors have an ordering? abs() exists in TensorFlow.
	//@Override
	//public void abs(TensorMember a, TensorMember b) {}

	@Override
	public void sin(TensorMember a, TensorMember b) {}

	@Override
	public void cos(TensorMember a, TensorMember b) {}

	@Override
	public void tan(TensorMember a, TensorMember b) {}
	
	@Override
	public void csc(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sec(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cot(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sinh(TensorMember a, TensorMember b) {}

	@Override
	public void cosh(TensorMember a, TensorMember b) {}

	@Override
	public void tanh(TensorMember a, TensorMember b) {}

	@Override
	public void csch(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sech(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coth(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void assign(TensorMember a, TensorMember b) {}

	@Override
	public void zero(TensorMember a) {}

	@Override
	public void unity(TensorMember a) {}
	
	@Override
	public void power(int power, TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void negate(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isEqual(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isNotEqual(TensorMember a, TensorMember b) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public TensorMember construct() {
		return new TensorMember();
	}
	
	@Override
	public TensorMember construct(TensorMember other) {
		return new TensorMember(other);
	}
	
	@Override
	public TensorMember construct(String s) {
		return new TensorMember(s);
	}
	
	@Override
	public void invert(TensorMember a, TensorMember b) {
		throw new IllegalArgumentException("TODO is this supported?");
	}

	public void contract(int i, int j, TensorMember a, TensorMember b, TensorMember c) {}
	
	public void takeDiagonal(TensorMember a, Object b) {} // change Object to Vector
	
	//many more
}
