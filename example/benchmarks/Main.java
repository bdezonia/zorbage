package benchmarks;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.algorithm.Fill;
import nom.bdezonia.zorbage.algorithm.GridIterator;
import nom.bdezonia.zorbage.data.DimensionedDataSource;
import nom.bdezonia.zorbage.data.DimensionedStorage;
import nom.bdezonia.zorbage.dataview.FiveDView;
import nom.bdezonia.zorbage.sampling.IntegerIndex;
import nom.bdezonia.zorbage.sampling.SamplingIterator;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

public class Main {

	public static void main(String[] args) {

		long X = 200;
		long Y = 200;
		long Z = 40;
		long C = 4;
		long T = 100;
		
		long[] dims = new long[] {X, Y, Z, C, T};
		DimensionedDataSource<Float32Member> dataSource =
				DimensionedStorage.allocate(G.FLT.construct(), dims);

		Float32Member value = G.FLT.construct();
		
		// fastest approach to filling the dataset

		long t0 = System.currentTimeMillis();
		
		value.setV(1);

		Fill.compute(G.FLT, value, dataSource.rawData());
		
		value.setV(-1);
		
		dataSource.rawData().get(dataSource.rawData().size()-1, value);
		
		if (value.v() != 1) System.out.println("RawData failed");

		// next fastest?
		
		long t1 = System.currentTimeMillis();
		
		value.setV(2);
		
		FiveDView<Float32Member> view = new FiveDView<>(dataSource);
		
		for (long t = 0; t < view.d4(); t++) {
			for (long c = 0; c < view.d3(); c++) {
				for (long z = 0; z < view.d2(); z++) {
					for (long y = 0; y < view.d1(); y++) {
						for (long x = 0; x < view.d0(); x++) {
							view.set(x, y, z, c, t, value);
						}
					}
				}
			}
		}
		
		value.setV(-1);
		
		view.get(X-1, Y-1, Z-1, C-1, T-1, value);
		
		if (value.v() != 2) System.out.println("View failed");

		// slowest?
		
		long t2 = System.currentTimeMillis();
		
		value.setV(3);
		
		SamplingIterator<IntegerIndex> iter = GridIterator.compute(dataSource);
		
		IntegerIndex idx = new IntegerIndex(dataSource.numDimensions());
		
		while (iter.hasNext()) {
		
			iter.next(idx);
			dataSource.set(idx, value);
			
		}

		value.setV(-1);
		
		dataSource.get(idx, value);
		
		if (value.v() != 3) System.out.println("GridIterator failed");
		
		long t3 = System.currentTimeMillis();
		
		System.out.println(" rawData of data source "+(t1-t0));
		
		System.out.println(" view of data source "+(t2-t1));
		
		System.out.println(" data source "+(t3-t2));
	}

}
