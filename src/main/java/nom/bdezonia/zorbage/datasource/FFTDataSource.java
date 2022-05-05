package nom.bdezonia.zorbage.datasource;

import nom.bdezonia.zorbage.algebra.Algebra;
import nom.bdezonia.zorbage.algebra.StorageConstruction;
import nom.bdezonia.zorbage.algorithm.FFT;

/**
 * 
 * @author Barry DeZonia
 *
 * @param <M>
 * @param <N>
 */
public class FFTDataSource<M extends Algebra<M,N>, N>
	implements IndexedDataSource<N>
{
	final M alg;
	final IndexedDataSource<N> data;
	final long paddedDataSize;
	final long dataSize;
	
	/**
	 * 
	 * @param alg
	 * @param ds
	 * @param powerOfTwoLimit
	 */
	public FFTDataSource(M alg, IndexedDataSource<N> ds, long powerOfTwoLimit) {
		
		if (powerOfTwoLimit <= 0)
			throw new IllegalArgumentException("power of two limit must be 1 or greater");

		if (powerOfTwoLimit < ds.size())
			throw new IllegalArgumentException("size of source not contained by size limit of piped");
		
		if (FFT.enclosingPowerOf2(powerOfTwoLimit) != powerOfTwoLimit)
			throw new IllegalArgumentException("Provided powerOfTwoLimit is not a power of two");
		
		this.alg = alg;

		this.data = ds;
		
		this.paddedDataSize = powerOfTwoLimit;
		
		this.dataSize = data.size();
	}

	@Override
	public IndexedDataSource<N> duplicate() {

		return new FFTDataSource<>(alg, data, paddedDataSize);
	}

	@Override
	public StorageConstruction storageType() {

		return data.storageType();
	}

	// TODO: this routine is never out of bounds in my testing. Why?
	
	@Override
	public void set(long index, N value) {

		if (index >= 0 && index < dataSize) {
		
			data.set(index, value);
		}
	}

	// TODO: this routine is never out of bounds in my testing. Why?
	
	@Override
	public void get(long index, N value) {
		
		if (index >= 0 && index < dataSize) {
		
			data.get(index, value);
		}
		else {
		
			alg.zero().call(value);
		}
	}

	@Override
	public long size() {

		return paddedDataSize;
	}

	@Override
	public boolean accessWithOneThread() {

		return data.accessWithOneThread();
	}
}
