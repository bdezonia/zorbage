/*
 */

public class AnalyzeFunctions {

	// This is an example showing some of the things you can do with functions and procedures.

	// A function takes input values and returns a computed value. A procedure is very similar.
	// It is a function that takes input values and rather than returning an output it places
	// its output value in the last input passed to the procedure.

	public void example1() {

		// Let's define a procedure that squares an input floating point number

		Procedure2<Float64Member, Float64Member> squarer =
				new Procedure2<Float64Member, Float64Member>()
		{
			public void call(Float64Member in, Float64Member out) {
				out.setV(in.v() * in.v());
			}
		};

		// Now let's exercise it

		Float64Member in = G.DBL.construct("4");
		Float64Member out = G.DBL.construct();
		squarer.call(in, out);
		System.out.println(out); // prints 16.0
	}

	public void example2() {

		// Let's define a procedure that cubes an input integer

		Procedure2<Long, Float64Member> cuber =
				new Procedure2<Long, Float64Member>()
		{
			public void call(Long in, Float64Member out) {
				out.setV(in.v() * in.v() * in.v());
			}
		};

		// Let's treat this procedure as a source of data

		ProcedureDataSource<Float64Member> source = new ProcedureDataSource<>(cuber);

		// And specify that we want to look at the 10 cubes starting at 5^3

		TrimmedDataSource<Float64Member> trimmed = new TrimmedDataSource<>(source, 5, 10);

		// Now calc the mean of the first 10 cubes starting with 5^3

		Float64Member mean = G.DBL.construct();

		Mean.compute(trimmed, mean);

		System.out.println(mean);  // prints X: TODO test and use actual value here
	}

	public void example3() {

		// Let's define a procedure that computes the numbers on the line 4*x + 7

		Procedure2<Float64Member, Float64Member> line =
				new Procedure2<Float64Member, Float64Member>()
		{
			public void call(Float64Member in, Float64Member out) {
				out.setV(4 * in.v() + 7);
			}
		};

		// now let's calculate it's derivative at the point 14.0

		Float64Member point = new Float64Member(14.0);
		Float64Member delta = new Float64Member(0.001);
		Float64Member value = new Float64Member();

		Derivative<Float64Algebra,Float64Member> deriv = new Derivative<>(G.DBL, line, delta);

		deriv.call(point, value);

		System.out.println(value);  // prints 4.0
	}

	public void example4() {

		// let's calculate a root of a procedure near a point

		NewtonRaphson

	}

	public void example5() {

		// let's find a fixed point of a procedure

		FindFixedPoint
	}
}
