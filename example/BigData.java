import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.Mean;
import nom.bdezonia.zorbage.algorithm.Sum;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ReadOnlyHighPrecisionDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionAlgebra;
import nom.bdezonia.zorbage.type.highprec.real.HighPrecisionMember;
import nom.bdezonia.zorbage.type.int16.SignedInt16Member;
import nom.bdezonia.zorbage.type.int16.UnsignedInt16Algebra;
import nom.bdezonia.zorbage.type.int16.UnsignedInt16Member;

import java.math.BigDecimal;

/**
 * @author Barry DeZonia
 */
public class BigData {

	// Zorbage is written to store and accurately calculate upon very large sets of data.

	public void example1() {

		// G contains all the algebras provided by default in Zorbage.
		// G.INT16 is the algebra for signed 16 bit integers

		// construct a temp variable

		SignedInt16Member value = G.INT16.construct();

		// Allocate a huge list: 10 billion short integers (20 billion bytes). Zorbage takes large
		//   requests like this and has a 2000 byte memory buffer containing values that are paged
		//   to a file on disk as needed. The created list is zero filled.

		IndexedDataSource<SignedInt16Member> data = Storage.allocate(10L * 1000 * 1000 * 1000, value);

		// Fill the list with random numbers
		//   G.INT16.random() is the function that returns a random signed 16 bit integer when called
		//   Fill will repeatedly call it; once per element in the array of data.

		Fill.compute(G.INT16, G.INT16.random(), data);

		// Now count the number of fours we found.
		//   Notice the list is indexed by a 64-bit integer. Lists can contain up to 2^63 elements.

		long numOfFours = 0;
		for (long i = 0; i < data.size(); i++) {
			data.get(i, value);
			if (value.v() == 4)
				numOfFours++;
		}

		System.out.println("Number of integers with value of 4 was " + numOfFours);
	}

	public void example2() {

		// One issue with working with lots of data is that doing math with many numbers can result
		// in overflows or underflows or losses of precision. One trick Zorbage uses is to allow
		// one to accumulate values in high precision floating point numbers. The precision of which
		// can be set between 1 and 4000 decimal places. These numbers maintain said precision and
		// do not over/underflow.

		// Allocate a value to pass to the storage allocator

		UnsignedInt16Member value = G.UINT16.construct();

		// Allocate a huge list: 10 billion unsigned short integers (20 billion bytes).
		// The created list is zero filled.

		IndexedDataSource<UnsignedInt16Member> data = Storage.allocate(10L * 1000 * 1000 * 1000, value);

		// Fill it with random data

		Fill.compute(G.UINT16, G.UINT16.random(), data);

		// Let's pull out values as high precision floating point numbers that can't lose precision

		ReadOnlyHighPrecisionDataSource<UnsignedInt16Algebra,UnsignedInt16Member> filter =
				new ReadOnlyHighPrecisionDataSource<UnsignedInt16Algebra,UnsignedInt16Member>(G.UINT16, data);

		// Let's set the decimal place accuracy we want to maintain. Ideally this is called once by your
		// whole program at start up.

		HighPrecisionAlgebra.setPrecision(30); // 30 decimal places

		HighPrecisionMember sum = G.HP.construct();

		Sum.compute(G.HP, filter, sum);  // will not overflow

		System.out.println("sum = " + sum);

		HighPrecisionMember mean = G.HP.construct();

		Mean.compute(G.HP, filter, mean);  // will not lose precision

		System.out.println("mean = " + mean);

		// A quicker way to calculate the mean. Avoid the Mean() call altogether.

		HighPrecisionMember count = G.HP.construct();

		count.setV(BigDecimal.valueOf(data.size()));

		G.HP.divide().call(sum, count, mean);

		System.out.println("mean = " + mean);
	}
}
