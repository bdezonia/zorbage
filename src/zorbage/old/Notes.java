/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016 Barry DeZonia
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
package zorbage.old;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class Notes {

	public enum Mode {READ, WRITE, READ_WRITE}; // this is not functional in design
    
	/*

	An op has a name
	* An op takes a list of inputs of varying types and sets outputs of
	* various types. Outputs are passed by reference.
	* A computation maps outputs to inputs.
	* This is a functional approach.
	* 
	*/

	  public interface GroupMember<T> {
		  void set(T from);
	  }
	  
	  public class Float32 implements GroupMember<Float32> {
		  public float value;
		  
		  public Float32(float f) { value = f; }
	  
		  public void set(Float32 from) { value = from.value; }
		  
		  public void add(Float32 a, Float32 b) {
			  value = a.value + b.value;
		  }
	  }
	  
	  public class Parameter<T extends GroupMember<T>> {
	    
	    //public String name;
	    private T v;
	    private Mode mode;
	    
	    public Parameter(T v, Mode m) {
			this.v = v;
			this.mode = m;
		}
	  }
	  
	  public interface Computation {
//	    void run(Parameter<?> ... params);
	    void run(Object ... params);
	  }

	  public class Sum2Floats implements Computation {

	    private void typeCheck(Parameter<?> ... params) {
			if (params.length != 3) throw new IllegalArgumentException();
			if (!Float32.class.isAssignableFrom(params[0].v.getClass())) throw new IllegalArgumentException();
			if (!Float32.class.isAssignableFrom(params[1].v.getClass())) throw new IllegalArgumentException();
			if (!Float32.class.isAssignableFrom(params[2].v.getClass())) throw new IllegalArgumentException();
			if (params[0].mode == Mode.WRITE) throw new IllegalArgumentException();
			if (params[1].mode == Mode.WRITE) throw new IllegalArgumentException();
			if (params[2].mode == Mode.READ) throw new IllegalArgumentException();
		}
		
//	    public void run(Parameter<?> ... params) {
	    public void run(Object ... params) {
	      // make optional
	      //typeCheck(params);
	      //Parameter<Float32> a = (Parameter<Float32>) params[0];
	      //Parameter<Float32> b = (Parameter<Float32>) params[1];
	      //Parameter<Float32> c = (Parameter<Float32>) params[2];

	      Float32 a, b, c;
	      a = (Float32) params[0];
	      b = (Float32) params[1];
	      c = (Float32) params[2];
	      c.add(a,b);
	    }
	  }
	  
	  public static void junk(String[] args) {
		  Notes m = new Notes();
		  Float32 a = m.new Float32(6);
		  Float32 b = m.new Float32(7);
		  Float32 c = m.new Float32(0);
		  
		  Sum2Floats op = m.new Sum2Floats();
//		  op.run(m.new Parameter<Float32>(a, Mode.READ),
//				  m.new Parameter<Float32>(b,Mode.READ),
//				  m.new Parameter<Float32>(c, Mode.WRITE));
		  op.run(a,b,c);
		  
		  System.out.println(a.value + " plus " + b.value + " = " + c.value);
	  }

}
