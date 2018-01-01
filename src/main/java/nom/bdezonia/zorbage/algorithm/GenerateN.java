package nom.bdezonia.zorbage.algorithm;

import nom.bdezonia.zorbage.basic.procedure.Procedure;
import nom.bdezonia.zorbage.basic.procedure.Procedure1;
import nom.bdezonia.zorbage.type.algebra.Group;
import nom.bdezonia.zorbage.type.storage.linear.IndexedDataSource;

public class GenerateN {

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param start
	 * @param count
	 * @param storage
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, Procedure1<U> proc, long start, long count, IndexedDataSource<?,U> storage)
	{
		U value = group.construct();
		for (long i = 0; i < count; i++) {
			proc.call(value);
			storage.set(start+i, value);
		}
	}

	/**
	 * 
	 * @param group
	 * @param proc
	 * @param start
	 * @param count
	 * @param storage
	 * @param inputs
	 */
	public static <T extends Group<T,U>,U>
		void compute(T group, Procedure<U> proc, long start, long count, IndexedDataSource<?,U> storage, U... inputs)
	{
		U value = group.construct();
		for (long i = 0; i < count; i++) {
			proc.call(value, inputs);
			storage.set(start+i, value);
		}
	}

}
