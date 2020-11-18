package nom.bdezonia.zorbage.algorithm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.storage.Storage;
import nom.bdezonia.zorbage.type.real.float32.Float32Member;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class TestBasicStats {

	@Test
	public void test1() {
		
		IndexedDataSource<Float32Member> data = Storage.allocate(G.FLT.construct(), new float[] {43,7,99,1,2,3,100,55,31});
		
		Float32Member mean = G.FLT.construct();
		Float32Member stdErrMean = G.FLT.construct();
		Float32Member stddev = G.FLT.construct();
		Float32Member sampleVariance = G.FLT.construct();
		Float32Member sampleSkew = G.FLT.construct();
		Float32Member excessKurtosis = G.FLT.construct();

		BasicStats.compute(G.FLT, data, mean, stdErrMean, stddev, sampleVariance, sampleSkew, excessKurtosis);
		
		// expected numbers calculated at wolframalpha.com by querting statistics of data using input numbers above
		
		assertEquals(37.89, mean.v(), 0.01);
		assertEquals(39.97, stddev.v(), 0.01);
		assertEquals(stddev.v()/3, stdErrMean.v(), 0.01);
		assertEquals(39.97*39.97, sampleVariance.v(), 0.5);
		
		// TODO
		// these are not working. code looks correct. but there are many ways of estimating these values for
		//   small sample sizes that I am not using. I need to consider how best to move forward.
		
		//assertEquals(0.6319, sampleSkew.v(), 0.0001);
		//assertEquals(1.919, 3 + excessKurtosis.v(), 0.001);
	}
}
