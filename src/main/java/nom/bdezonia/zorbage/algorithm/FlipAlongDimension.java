package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class FlipAlongDimension {

	// do not instantiate
	
	private FlipAlongDimension() { }

	/**
	 * 
	 * @param <T>
	 * @param <U>
	 * @param alg
	 * @param dim
	 * @param data
	 */
	public static <T extends Algebra<T,U>,U>
		void compute(T alg, int axisNumber, DimensionedDataSource<U> data)
	{
		int numD = data.numDimensions();
		
		if (axisNumber < 0 || axisNumber >= numD) {
			
			throw new IllegalArgumentException("axis number out of bounds");
		}

		long[] shrunkenDims = new long[numD];

		for (int d = 0; d < numD; d++) {
		
			shrunkenDims[d] = data.dimension(d);
		}
		
		shrunkenDims[axisNumber] /= 2;
		
		if (data.dimension(axisNumber) % 2 == 1) {
		
			shrunkenDims[axisNumber]++;
		}
			
		// move the sample data
		
		IntegerIndex regularIndex = new IntegerIndex(numD);
		
		IntegerIndex flippedIndex = new IntegerIndex(numD);
		
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(shrunkenDims);
		
		U value1 = alg.construct();
		
		U value2 = alg.construct();
		
		while (iter.hasNext()) {
		
			iter.next(regularIndex);
			
			for (int d = 0; d < numD; d++) {
				flippedIndex.set(d, regularIndex.get(d));
			}
			
			flippedIndex.set(axisNumber, data.dimension(axisNumber) - regularIndex.get(axisNumber) - 1);
			
			data.get(regularIndex, value1);
			data.get(flippedIndex, value2);
			
			data.set(regularIndex, value2);
			data.set(flippedIndex, value1);
		}
	}
}
